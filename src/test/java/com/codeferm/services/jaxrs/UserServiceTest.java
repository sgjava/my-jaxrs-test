package com.codeferm.services.jaxrs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.tomee.embedded.Container;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author sgoldsmith
 */
public class UserServiceTest {

    private static final Logger log = Logger.getLogger(UserServiceTest.class.
            getName());
    /**
     * TomEE container.
     */
    private static Container container;
    /**
     * TomEE container tomeeConfig.
     */
    private static org.apache.tomee.embedded.Configuration tomeeConfig;

    /**
     * Start EJB container.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("setUpClass()");
        // App appConfig
        // tomee-embedded tomeeConfig
        tomeeConfig = new org.apache.tomee.embedded.Configuration().
                randomHttpPort();
        container = new Container();
        container.setup(tomeeConfig);
        container.start();
        container.deployClasspathAsWebApp("/my-jaxrs-test", new File(
                "src/main/webapp"));
        log.info(String.format("TomEE embedded started on %s:%s", tomeeConfig.
                getHost(), tomeeConfig.getHttpPort()));
    }

    /**
     * Close EJB container.
     */
    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    /**
     * Set up.
     */
    @Before
    public void setUp() {
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
    }

    /**
     * Test JAX-RS client without proprietary timeouts.
     */
    @Ignore
    @Test
    public final void testUserInfoNoTimeout() {
        log.info("testUserInfoNoTimeout");
        String url = String.format("http://%s:%s%s", tomeeConfig.getHost(),
                tomeeConfig.getHttpPort(), "/my-jaxrs-test/user/v1/userinfo/");
        final Client client = ClientBuilder.newClient();
        // Get back test user's info
        final UserDto userDto = new UserDto(1, "test", "Test User");
        UserDto response = client.target(url).request().post(Entity.entity(
                userDto, MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client with List of DTOs.
     */
    @Test
    public final void testUserList() {
        log.info("testUserList");
        String url = String.format("http://%s:%s%s", tomeeConfig.getHost(),
                tomeeConfig.getHttpPort(),
                "/my-jaxrs-test/user/v1/userinfolist/");
        final Client client = ClientBuilder.newClient();
        final UserDto userDto = new UserDto(1, "test", "Test User");
        final List<UserDto> list = new ArrayList<>();
        list.add(userDto);
        list.add(userDto);
        // Get back test users info list
        List<UserDto> response = client.target(url).request().post(Entity.
                entity(new GenericEntity<List<UserDto>>(list) {
                }, MediaType.APPLICATION_JSON),
                new GenericType<List<UserDto>>() {
        });
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client with List of DTOs.
     */
    @Test
    public final void testUserListDto() {
        log.info("testUserListDto");
        String url = String.format("http://%s:%s%s", tomeeConfig.getHost(),
                tomeeConfig.getHttpPort(),
                "/my-jaxrs-test/user/v1/userdtolist/");
        final Client client = ClientBuilder.newClient();
        final UserDto userDto = new UserDto(1, "test", "Test User");
        final List<UserDto> list = new ArrayList<>();
        list.add(userDto);
        list.add(userDto);
        final UserListDto userListDto = new UserListDto(list);
        // Get back test users info list
        UserListDto response = client.target(url).request().post(Entity.
                entity(userListDto, MediaType.APPLICATION_JSON),
                UserListDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client without proprietary timeouts.
     */
    @Test
    public final void testComplexDto() {
        log.info("testComplexDto");
        String url = String.format("http://%s:%s%s", tomeeConfig.getHost(),
                tomeeConfig.getHttpPort(), "/my-jaxrs-test/user/v1/complex/");
        final Client client = ClientBuilder.newClient();
        // Get back test user's info
        final UserDto userDto = new UserDto(1, "test", "Test User");
        final List<UserDto> list = new ArrayList<>();
        list.add(userDto);
        list.add(userDto);
        final UserListDto userListDto = new UserListDto(list);
        final ComplexDto complexDto = new ComplexDto(userListDto);
        ComplexDto response = client.target(url).request().post(Entity.entity(
                complexDto, MediaType.APPLICATION_JSON), ComplexDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client with proprietary timeouts.
     */
    @Ignore
    @Test(expected = Exception.class)
    public final void testUserInfoTimeoutProprietary() {
        log.info("testUserInfoTimeoutProprietary");
        String url = String.format("http://%s:%s%s", tomeeConfig.getHost(),
                tomeeConfig.getHttpPort(), "/my-jaxrs-test/user/v1/userinfo/");
        final Client client = ClientBuilder.newClient();
        // Timeout not covered by client properties
        final WebTarget target = client.target(url);
        final Invocation.Builder request = target.request();
        final HTTPConduit httpConduit = WebClient.getConfig(target).
                getHttpConduit();
        httpConduit.getClient().setReceiveTimeout(500);
        httpConduit.getClient().setConnectionTimeout(500);
        // Get back test user's info
        final UserDto userDto = new UserDto(1, "test", "Test User");
        UserDto response = null;
        response = request.post(Entity.entity(userDto,
                MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client with generic style timeouts.
     */
    @Ignore
    @Test(expected = Exception.class)
    public final void testUserInfoTimeout() {
        log.info("testUserInfoTimeout");
        String url = String.format("http://%s:%s%s", tomeeConfig.getHost(),
                tomeeConfig.getHttpPort(), "/my-jaxrs-test/user/v1/userinfo/");
        final Client client = ClientBuilder.newClient();
        // Use client properties the generic way
        client.property("http.connection.timeout", 500L);
        client.property("http.receive.timeout", 500L);
        // Get back test user's info
        final UserDto userDto = new UserDto(1, "test", "Test User");
        UserDto response = client.target(url).request().post(Entity.entity(
                userDto, MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }
}
