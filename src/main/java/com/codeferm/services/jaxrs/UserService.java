package com.codeferm.services.jaxrs;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
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
    public void init() {
        log.info("PostConstruct");
    }

    @PreDestroy
    public void done() {
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
    @ValidateOnExecution
    @Valid
    public Response userInfo(@Valid final UserDto userDto) {
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
        // Return DTO
        return Response.ok(userDto).build();
    }

    /**
     * Get user DTO list.
     *
     * @param userDto User DTO.
     * @return Populated User DTO.
     */
    @Path("/userinfolist")
    @POST
    @ValidateOnExecution
    @Valid
    public Response userInfoList(@Valid final List<UserDto> userDtoList) {
        log.info(String.format("userDtoList: %s", userDtoList.toString()));
        // Return DTO
        return Response.ok(userDtoList).build();
    }

    /**
     * Get user DTO list.
     *
     * @param userDto User DTO.
     * @return Populated User DTO.
     */
    @Path("/userdtolist")
    @POST
    @ValidateOnExecution
    @Valid
    public Response userListDto(@Valid final UserListDto userListDto) {
        log.info(String.format("userListDto: %s", userListDto.toString()));
        // Return DTO
        return Response.ok(userListDto).build();
    }

    /**
     * Get user DTO list.
     *
     * @param userDto User DTO.
     * @return Populated User DTO.
     */
    @Path("/complex")
    @POST
    @ValidateOnExecution
    @Valid
    public Response complex(@Valid final ComplexDto complexDto) {
        log.info(String.format("complexDto: %s", complexDto.toString()));
        // Return DTO
        return Response.ok(complexDto).build();
    }
}
