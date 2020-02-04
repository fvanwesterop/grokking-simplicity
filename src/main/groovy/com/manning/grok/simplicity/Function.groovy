package com.manning.grok.simplicity

final class Function {

    static unitCost(Map ingredients, Map priceList) {

        ingredients.inject(0.0) { unitCost, ingredient, quantity ->
            unitCost + (quantity * priceList.get(ingredient))
        }

    }

    private Function() {}
}
