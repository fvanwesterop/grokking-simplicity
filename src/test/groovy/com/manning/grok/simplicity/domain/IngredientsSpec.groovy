package com.manning.grok.simplicity.domain


import spock.lang.Specification

import static Ingredients.ingredients


class IngredientsSpec extends Specification {

    def "checkout the + operator"() {

        def shoppingListOne = '{"pepperoni":1, "cheese":2}'
        def shoppingListTwo = '{"cheese":2, "flour":3}'

        def completeList = '{"cheese":4, "pepperoni":1, "flour":3}'

        expect:
        ingredients(shoppingListOne) + ingredients(shoppingListTwo) == ingredients(completeList)
    }

    def 'checkout the - operator'() {

        def needed = '{"cheese":2, "flour":3, "olives":4, "tomatoes":4}'
        def supplies = '{"pepperoni":1, "cheese":4, "olives":4, "tomatoes":2}'


        def goBuy = '{"flour":3, "tomatoes":2}'

        expect:
        ingredients(needed) - ingredients(supplies) == ingredients(goBuy)
    }

    def 'checkout the / operator'() {

    }
}
