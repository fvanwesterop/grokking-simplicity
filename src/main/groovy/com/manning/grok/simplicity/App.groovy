package com.manning.grok.simplicity

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

@Slf4j
class App {

    static final BASE_PATH = 'src/main/resources'

    static void main(String[] args) {
        new App().run()
    }

    def run() {

        def jsonFile = new File(BASE_PATH + '/recipe/mushroom-pizza.json')
        log.info jsonFile.text

        def json = new JsonSlurper().parse(jsonFile) as Map
        def ingredients = json.'ingredients' as Map

        ingredients.each { name, amount ->
            log.info "${name.toString().padLeft(20)} : ${amount.toString().padRight(5)} (${amount.class.simpleName})"
        }
    }
}
