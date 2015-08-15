package com.codeferm.services.jaxrs;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author sgoldsmith
 */
@Path("/user/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class.
            getName());

    @PostConstruct
    public final void init() {
        log.info("PostConstruct");
    }

    @PreDestroy
    public final void done() {
        log.info("PreDestroy");
    }

    /**
     * Get user DTO.
     *
     * @param userDto User DTO.
     * @return Populated User DTO.
     */
    @Path("/userinfo")
    @POST
    public final Response userInfo(final UserDto userDto) {
        log.info(String.format("userDto: %s", userDto.toString()));
        // Set other fields if id = 1
        if (userDto.getId() == 1) {
            userDto.setUserName("test");
            userDto.setFullName("Test User");
        }
        // Force client to timeout by delaying 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.severe(e.getMessage());
        }
        // Return VersionDto
        return Response.ok(userDto).build();
    }

}
