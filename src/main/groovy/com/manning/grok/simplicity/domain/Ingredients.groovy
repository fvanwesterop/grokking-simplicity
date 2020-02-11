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

    List<Ingredients> div(Integer divisor) {

        if (divisor < 1) throw new IllegalArgumentException('divisor must be a positive Integer')

        final List<Map<String, Integer>> outputMaps = new ArrayList<>()
        this.getIngredientQties().each { String ingredientName, Integer ingredientQty ->

            def quotientQty = ingredientQty.intdiv(divisor)
            def remainderQty = ingredientQty % divisor

            if (quotientQty) {
                distributeMapEntriesEvenly(outputMaps, divisor, { it.put(ingredientName, quotientQty) })
            }
            if (remainderQty) {
                distributeMapEntriesEvenly(outputMaps, remainderQty, { it.compute(ingredientName, { name, oldQty -> oldQty == null ? 1 : oldQty + 1 }) })
            }
        }
        outputMaps.collect { new Ingredients(it) }
    }

    private void distributeMapEntriesEvenly(List listOfMaps, Integer numberOfMapsToDistributeOver, Closure entrySupplier) {
        ensureListContainsNumberOfMaps(listOfMaps, numberOfMapsToDistributeOver)
        def iterator = listOfMaps.iterator().reverse()
        numberOfMapsToDistributeOver.times {
            entrySupplier.call(iterator.next())
        }
    }

    private void ensureListContainsNumberOfMaps(List listOfMaps, Integer numberOfMaps) {
        def numberOfMapsToAdd = (numberOfMaps > listOfMaps.size()) ? numberOfMaps - listOfMaps.size() : 0
        numberOfMapsToAdd.times { listOfMaps.add([:]) }
    }

    @Override
    String toString() {
        return JsonOutput.toJson(ingredientQties)
    }

    private Ingredients(Map<String, Integer> ingredientQties) {
        this.ingredientQties = ingredientQties.asUnmodifiable()
    }
}