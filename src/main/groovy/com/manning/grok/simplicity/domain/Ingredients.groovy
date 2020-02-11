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
        new Ingredients(other.ingredientQties.inject([:] + this.getIngredientQties()) { Map ourQties, String ingredientName, Integer theirIngredientQty ->
            ourQties << [(ingredientName): (ingredientQties.getOrDefault(ingredientName, 0) + theirIngredientQty)]
        })
    }

    Ingredients minus(Ingredients other) {
        new Ingredients(other.getIngredientQties().inject([:] + this.getIngredientQties()) { Map ourQties, String ingredientName, Integer theirIngredientQty ->
            def newIngredientQty = ourQties.getOrDefault(ingredientName, 0) - theirIngredientQty
            if (newIngredientQty > 0) {
                ourQties << [(ingredientName): newIngredientQty]
            } else {
                ourQties.remove(ingredientName)
            }
            return ourQties
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