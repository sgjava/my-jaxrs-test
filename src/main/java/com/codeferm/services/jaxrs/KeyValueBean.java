/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeferm.services.jaxrs;

import java.util.logging.Logger;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.ejb.Stateless;

/**
 *
 * @author sgoldsmith
 */
@Stateless
@CacheDefaults(cacheName = "testCache")
public class KeyValueBean {

    private static final Logger log = Logger.
            getLogger(KeyValueBean.class.getName());

    @CacheResult
    public String add(@CacheKey String key, String value) {
        log.info(String.format("Adding key: %s, value: %s", key, value));
        return value;
    }

    @CacheRemoveAll
    public void invalidateCache() {
        log.info("Cache invalidated");
    }
}
