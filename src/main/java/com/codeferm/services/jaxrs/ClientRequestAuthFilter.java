package com.codeferm.services.jaxrs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author sgoldsmith
 */
public class ClientRequestAuthFilter implements ClientRequestFilter {

    /**
     * User.
     */
    private final String user;
    /**
     * Password.
     */
    private final String password;

    public ClientRequestAuthFilter(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     *
     * @param requestContext
     * @throws IOException
     */
    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        final String basicAuthentication = getBasicAuthentication();
        headers.add("Authorization", basicAuthentication);
    }

    /**
     *
     * @return
     */
    private String getBasicAuthentication() {
        String token = this.user + ":" + this.password;
        try {
            return "Basic " + DatatypeConverter.printBase64Binary(token.
                    getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode with UTF-8", ex);
        }
    }
}
