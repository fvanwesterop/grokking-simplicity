package com.manning.grok.simplicity.rest.resource.ingredients

import groovy.util.logging.Slf4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import static com.manning.grok.simplicity.domain.Ingredients.ingredients

@Slf4j
@Path('/ingredients')
class IngredientsResource {

    @GET
    @Path('/get')
    @Produces(MediaType.APPLICATION_JSON)
    def getIngredients() {

        return Response.ok()
                .entity([supplies: ingredients('{"pepperoni":1, "cheese":2}'), needed: ingredients('{"tomatoes":3, "cheese":1}'), operator:'minus'])
                .build()

    }

}
