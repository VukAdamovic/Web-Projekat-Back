package com.example.web_projekat;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        // Allow requests from any origin
        headers.add("Access-Control-Allow-Origin", "*");
        // Allow the specified HTTP methods
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        // Allow the specified headers
        headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        // Allow including cookies in CORS requests
        headers.add("Access-Control-Allow-Credentials", "true");

        // Handle OPTIONS preflight request
        if (requestContext.getMethod().equals("OPTIONS")) {
            // Set additional headers for the preflight response
            headers.add("Access-Control-Max-Age", "86400"); // 24 hours
            headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            headers.add("Access-Control-Allow-Credentials", "true");
            responseContext.setStatus(Response.Status.OK.getStatusCode());
            return;
        }
    }
}