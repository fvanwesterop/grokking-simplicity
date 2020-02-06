package com.manning.grok.simplicity.domain

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Ingredients {

    private static JsonSlurper slurper = new JsonSlurper()

    static Ingredients ingredients(String json) {
        new Ingredients(slurper.parseText(json) as Map<String, Integer>)
    }

    final Map<String, Integer> ingredientQties

    Ingredients plus(Ingredients other) {
        new Ingredients(other.ingredientQties.inject([:] + ingredientQties) { Map summedQties, Map.Entry<String, Integer> ingredientQty ->
            summedQties << [(ingredientQty.key): (ingredientQties.getOrDefault(ingredientQty.key, 0) + ingredientQty.value)]
        })
    }

    @Override
    String toString() {
        return JsonOutput.toJson(ingredientQties)
    }

    private Ingredients(Map<String, Integer> ingredientQties) {
        this.ingredientQties = ingredientQties.asUnmodifiable()
    }
}