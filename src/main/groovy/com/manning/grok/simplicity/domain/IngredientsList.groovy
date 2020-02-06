package com.manning.grok.simplicity.domain


import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class IngredientsList {

    private static JsonSlurper slurper = new JsonSlurper()

    final Map<String, Integer> content

    private IngredientsList(Map<String, Integer> content) {
        this.content = content
    }

    private getContent() {
        return content.asUnmodifiable()
    }

    IngredientsList plus(IngredientsList otherList) {
        Map<String, Integer> result = new HashMap<>(content)
        otherList.content.each { String item, Integer qty ->
            result.put(item, content.getOrDefault(item, 0) + qty)
        }
        new IngredientsList(result)
    }

    @Override
    String toString() {
        return JsonOutput.toJson(content)
    }

    static IngredientsList ingredients(String json) {
        new IngredientsList(slurper.parseText(json))
    }
}
