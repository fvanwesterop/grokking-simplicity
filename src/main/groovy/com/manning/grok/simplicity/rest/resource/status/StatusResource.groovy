package com.manning.grok.simplicity.rest.resource.status

import groovy.util.logging.Slf4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Slf4j
@Path('/status')
class StatusResource {

    @GET
    Response ping() {

        log.info "Serving 'api/status' request"
        return Response.ok()
                .entity(new Status(status: 'Service online'))
                .build()
    }
}
