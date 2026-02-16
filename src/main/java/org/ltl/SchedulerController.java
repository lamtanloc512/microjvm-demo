package org.ltl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.microjvm.scheduler.ScheduledTask;
import org.microjvm.scheduler.SchedulerBeanPostProcessor;

import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST endpoint to view active scheduled tasks.
 *
 * <pre>
 * GET /api/scheduler/status
 * </pre>
 */
@Singleton
@Path("/api/scheduler")
public class SchedulerController {

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> status() {
        var manager = SchedulerBeanPostProcessor.getSchedulerManager();

        var result = new LinkedHashMap<String, Object>();
        result.put("running", manager.isRunning());
        result.put("taskCount", manager.getTasks().size());
        result.put("tasks", manager.getTasks().stream()
                .map(this::taskToMap)
                .collect(Collectors.toList()));
        return result;
    }

    private Map<String, Object> taskToMap(ScheduledTask task) {
        var map = new LinkedHashMap<String, Object>();
        map.put("name", task.name());
        map.put("type", task.type().name());
        map.put("executionCount", task.executionCount());
        map.put("lastExecutionTime", task.lastExecutionTime() > 0
                ? task.lastExecutionTime() : null);

        switch (task.type()) {
            case FIXED_RATE, FIXED_DELAY -> {
                map.put("interval", task.interval());
                map.put("timeUnit", task.timeUnit().name());
                map.put("initialDelay", task.initialDelay());
            }
            case CRON -> map.put("cronExpression", task.cronExpression());
        }

        return map;
    }
}
