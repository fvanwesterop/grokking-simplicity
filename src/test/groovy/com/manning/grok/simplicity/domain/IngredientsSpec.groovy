package com.manning.grok.simplicity.domain

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

import static Ingredients.ingredients

@Slf4j
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

    @Unroll('#a / #b = #result')
    def 'checkout the divide-by-Integer / operator'() {

        setup:
        def shoppingList = ingredients(a)
        def expected = result.collect { json -> ingredients(json) }

        expect:
        shoppingList / b == expected

        where:
        a                                                         | b || result
        '{}'                                                      | 1  | []
        '{"apple":0}'                                             | 2 || []
        '{"apple":0}'                                             | 3 || []
        '{"apple":1}'                                             | 1 || ['{"apple":1}']
        '{"apple":1}'                                             | 3 || ['{"apple":1}']
        '{"apple":30}'                                            | 3 || ['{"apple":10}', '{"apple":10}', '{"apple":10}']
        '{"apple":29}'                                            | 3 || ['{"apple":9}', '{"apple":10}', '{"apple":10}']
        '{"apple":1, "pear":1}'                                   | 1 || ['{"apple":1, "pear":1}']
        '{"apple":1, "pear":1}'                                   | 2 || ['{"apple":1, "pear":1}']
        '{"apple":2, "pear":1}'                                   | 2 || ['{"apple":1}', '{"apple":1, "pear":1}']
        '{"apple":2, "pear":1}'                                   | 3 || ['{"apple":1}', '{"apple":1, "pear":1}']
        '{"pepperoni":1, "cheese":4, "olives":3, "tomatoes":723}' | 2 || ['{"pepperoni":1, "cheese":2, "olives":1, "tomatoes":361}', '{"cheese":2, "olives":2, "tomatoes":362}']
        '{"pepperoni":11, "cheese":20}'                           | 2 || ['{"pepperoni":5, "cheese":10}', '{"pepperoni":6, "cheese":10}']
    }

}
