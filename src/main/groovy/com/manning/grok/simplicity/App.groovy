package com.manning.grok.simplicity

class App {

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
