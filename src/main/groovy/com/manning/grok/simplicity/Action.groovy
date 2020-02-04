package com.manning.grok.simplicity

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

@Slf4j
final class Action {

    private static final BASE_PATH = 'src/main/resources'
    private static final JsonSlurper JSON_SLURPER = new JsonSlurper()

    static printPizzaPrice(String pizzaName, BigDecimal pizzaPrice) {
        log.info "${pizzaName.toString().padRight(22)} : ${pizzaPrice} euro"
    }

    static readJson(itemName, File file) {
        readAllJson(file)."$itemName"
    }

    static readAllJson(File file) {
        JSON_SLURPER.parse(file) as Map
    }

    static getFile(String path) {
        new File(BASE_PATH + '/' + path)
    }

    private Action() {}
}
