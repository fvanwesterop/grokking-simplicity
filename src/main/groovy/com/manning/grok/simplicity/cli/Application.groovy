package com.manning.grok.simplicity.cli

import com.manning.grok.simplicity.Action
import com.manning.grok.simplicity.Function

class Application {

    static void main(String[] args) {

        def recipesDirectory = Action.getFile('recipe')
        def pricesFile = Action.getFile('prices.json')

        def ingredientPrices = Action.readAllJson(pricesFile)

        recipesDirectory.eachFileRecurse { recipeFile ->

            def ingredients = Action.readJson('ingredients', recipeFile)
            def pizzaName = Action.readJson('name', recipeFile)

            Action.printPizzaPrice pizzaName, Function.unitCost(ingredients, ingredientPrices)
        }
    }
}
