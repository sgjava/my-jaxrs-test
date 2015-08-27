package com.codeferm.services.jaxrs;

import java.util.Iterator;
import java.util.logging.Logger;
import javax.cache.Cache;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
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
public class CacheBeanTest {

    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger(CacheBeanTest.class.
            getName());
    /**
     * Injected cache bean.
     */
    @EJB
    private CacheBean cacheBean;
    /**
     * Our key/value bean.
     */
    @EJB
    private KeyValueBean keyValueBean;

    /**
     * EJB container.
     */
    private static EJBContainer container;

    /**
     * Start EJB container.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Close caching provider/manager and EJB container.
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Set up.
     */
    @Before
    public void setUp() throws NamingException {
        log.info("setUp()");
        container = EJBContainer.createEJBContainer();
        container.getContext().bind("inject", this);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() throws NamingException {
        container.getContext().unbind("inject");
        container.close();
    }

    /**
     * Test JSR-107 cache using EHCache provider.
     */
    @Test
    public final void testCache() {
        log.info("testCache");
        assertNotNull(cacheBean);
        log.info(String.format("Cache names: %s", cacheBean.getCacheManager().
                getCacheNames()));
        keyValueBean.add("key1", "value1");
        keyValueBean.add("key2", "value2");
        keyValueBean.add("key3", "value3");
        final Cache<String, String> testCache = cacheBean.getCacheManager().
                getCache("testCache");
        // Nothing displays here
        Iterator<Cache.Entry<String, String>> allCacheEntries = testCache.
                iterator();
        while (allCacheEntries.hasNext()) {
            Cache.Entry<String, String> currentEntry = allCacheEntries.next();
            log.info("Key: " + currentEntry.getKey() + " Value: "
                    + currentEntry.getValue());
        }
        testCache.close();
    }
}
