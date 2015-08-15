/*
 * Copyright (c) Bright House Networks. All rights reserved.
 *
 * Created by Steven P. Goldsmith on May 5, 2015
 * steve.goldsmith@mybrighthouse.com
 */
package com.codeferm.services.jaxrs;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * Client request filter to log request.
 *
 * @author sgoldsmith
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClientRequestLoggingFilter implements ClientRequestFilter {

    private static final java.util.logging.Logger log
            = java.util.logging.Logger.getLogger(
                    ClientRequestLoggingFilter.class.getName());

    /**
     * Display request information.
     *
     * @param context Request context.
     * @throws IOException Possible exception.
     */
    @Override
    public void filter(final ClientRequestContext context) throws IOException {
        // Log headers
        context.getHeaders().entrySet().stream().forEach((header) -> {
            header.getValue().stream().forEach((value) -> {
                log.info(String.format("%s:%s ", header.getKey(), value));
            });
        });
        log.info(String.format("URI: %s", context.getUri()));
        log.info(String.format("Method: %s", context.getMethod()));
        log.info(String.format("Headers: %s", context.getStringHeaders()));
        log.info(String.format("Entity: %s", context.getEntity()));
    }
}
