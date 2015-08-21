/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeferm.services.jaxrs;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author sgoldsmith
 */
@Singleton
@Startup
@Lock(READ)
public class CacheBean {

    private static final Logger log = Logger.
            getLogger(CacheBean.class.getName());

    /**
     * Caching provider.
     */
    private CachingProvider cachingProvider;
    /**
     * Cache manager.
     */
    private CacheManager cacheManager;

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * Using EHCache provider configured via ehcache.xml in classpath.
     */
    //CHECKSTYLE:OFF DesignForExtension
    @PostConstruct
    //CHECKSTYLE:ON DesignForExtension
    public void init() {
        log.info("PostConstruct");
        cachingProvider = Caching.getCachingProvider();
        cacheManager = cachingProvider.getCacheManager();
    }

    /**
     * Destroy cache.
     */
    //CHECKSTYLE:OFF DesignForExtension
    @PreDestroy
    //CHECKSTYLE:ON DesignForExtension
    public void destroy() {
        log.info("PreDestroy");
        cacheManager.close();
        cachingProvider.close();
    }
}
