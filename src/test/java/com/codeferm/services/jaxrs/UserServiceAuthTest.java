package com.codeferm.services.jaxrs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sgoldsmith
 */
public class UserServiceAuthTest {

    private static final Logger log = Logger.getLogger(
            UserServiceAuthTest.class.
            getName());
    /**
     * TomEE container.
     */
    private static Container container;
    /**
     * TomEE container configuration.
     */
    private static Configuration configuration;

    /**
     * Start EJB container.
     */
    @BeforeClass
    public static void setUpClass() {
        try {
            log.info("setUpClass()");
            configuration = new Configuration().randomHttpPort();
            final Map<String, String> userMap = new HashMap<>();
            userMap.put("tomcat", "password");
            configuration.setUsers(userMap);
            final Map<String, String> roleMap = new HashMap<>();
            roleMap.put("tomcat", "my-role");
            configuration.setRoles(roleMap);
            container = new Container();
            container.setup(configuration);
            container.start();
            container.deployClasspathAsWebApp("/my-jaxrs-test", new File(
                    "src/main/webapp"));
            log.info(String.format("TomEE embedded started on %s:%s",
                    configuration.getHost(), configuration.getHttpPort()));
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    /**
     * Test event service.
     */
    @Test
    public final void testAuth() {
        log.info("testAuth()");
        final String url = String.format(
                "http://%s:%s/my-jaxrs-test/user/v1/userinfo/",
                configuration.getHost(), configuration.getHttpPort());
        log.info(url);
        final Client client = ClientBuilder.newClient().register(
                ClientRequestLoggingFilter.class).register(
                        new ClientRequestAuthFilter("tomcat", "password"));
        // Get back test user's info
        final UserDto userDto = new UserDto(1, null, null);
        UserDto response = client.target(url).request().post(Entity.entity(
                userDto, MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Close EJB container.
     */
    @AfterClass
    public static void tearDownClass() {
        try {
            log.info("tearDownClass()");
            container.stop();
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }
}
