package com.codeferm.services.jaxrs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import static org.apache.openejb.loader.JarLocation.jarLocation;
import org.apache.tomee.embedded.EmbeddedTomEEContainer;
import org.apache.ziplock.Archive;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sgoldsmith
 */
public class UserServiceTest {

    private static final Logger log = Logger.getLogger(UserServiceTest.class.
            getName());
    /**
     * EJB container.
     */
    private static EJBContainer container;

    /**
     * Start EJB container.
     */
    @BeforeClass
    public static void setUpClass() {
        log.info("setUpClass()");
        final Map p = new HashMap();
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.openejb.core.LocalInitialContextFactory");
        p.put("openejb.embedded.initialcontext.close ", "DESTROY");
        p.put("openejb.embedded.remotable", "true");
        p.put(EJBContainer.APP_NAME, "my-jaxrs-test");
        p.put(EJBContainer.PROVIDER, "tomee-embedded");
        // Add WAR and MDB modules
        p.put(EJBContainer.MODULES, new File[]{Archive.archive().copyTo(
            "WEB-INF/classes", jarLocation(UserService.class)).asDir()});
        // Random port
        p.put(EmbeddedTomEEContainer.TOMEE_EJBCONTAINER_HTTP_PORT, "-1");
        container = EJBContainer.createEJBContainer(p);
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
    @Test
    public final void testUserInfoNoTimeout() {
        log.info("testUserInfoNoTimeout");
        final String url = "http://127.0.0.1:" + System.getProperty(
                EmbeddedTomEEContainer.TOMEE_EJBCONTAINER_HTTP_PORT)
                + "/my-jaxrs-test/user/v1/userinfo/";
        final Client client = ClientBuilder.newClient();
        // Get back test user's info
        final UserDto userDto = new UserDto(1, null, null);
        UserDto response = client.target(url).request().post(Entity.entity(
                userDto, MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client with proprietary timeouts.
     */
    @Test(expected = Exception.class)
    public final void testUserInfoTimeoutProprietary() {
        log.info("testUserInfoTimeoutProprietary");
        final String url = "http://127.0.0.1:" + System.getProperty(
                EmbeddedTomEEContainer.TOMEE_EJBCONTAINER_HTTP_PORT)
                + "/my-jaxrs-test/user/v1/userinfo/";
        final Client client = ClientBuilder.newClient();
        // Timeout not covered by client properties
        final WebTarget target = client.target(url);
        final Invocation.Builder request = target.request();
        final HTTPConduit httpConduit = WebClient.getConfig(target).
                getHttpConduit();
        httpConduit.getClient().setReceiveTimeout(500);
        httpConduit.getClient().setConnectionTimeout(500);
        // Get back test user's info
        final UserDto userDto = new UserDto(1, null, null);
        UserDto response = null;
        response = request.post(Entity.entity(userDto,
                MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }

    /**
     * Test JAX-RS client with generic style timeouts.
     */
    @Test(expected = Exception.class)
    public final void testUserInfoTimeout() {
        log.info("testUserInfoTimeout");
        final String url = "http://127.0.0.1:" + System.getProperty(
                EmbeddedTomEEContainer.TOMEE_EJBCONTAINER_HTTP_PORT)
                + "/my-jaxrs-test/user/v1/userinfo/";
        final Client client = ClientBuilder.newClient();
        // Use client properties the generic way
        client.property("http.connection.timeout", 500L);
        client.property("http.receive.timeout", 500L);
        // Get back test user's info
        final UserDto userDto = new UserDto(1, null, null);
        UserDto response = client.target(url).request().post(Entity.entity(
                userDto, MediaType.APPLICATION_JSON), UserDto.class);
        assertNotNull(response);
        log.info(String.format("Response: %s", response));
    }
}
