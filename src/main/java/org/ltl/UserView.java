package org.ltl;

import org.microjvm.web.template.TemplateView;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.inject.Singleton;

@Singleton
@Path("/users")
public class UserView {

    @Inject
    private UserService userService;

    @GET
    public TemplateView getUser() {
        return TemplateView.of("users", "users", userService.findAll());
    }

}
