package org.ltl;

import java.util.List;

import org.microjvm.di.Provider;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Singleton
@Path("/api/v1/users")
public class UserController {

  @Inject
  private UserService userService;

  @Inject
  private Provider<RequestTracker> requestTracker;

  @GET
  @Produces("application/json")
  public List<User> getAllUsers() {
    System.out.println("[ UserController ] Processing request: " + requestTracker.get().getRequestId());
    return userService.findAll();
  }

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public User createUser(User user) {
    return userService.save(user);
  }


}
