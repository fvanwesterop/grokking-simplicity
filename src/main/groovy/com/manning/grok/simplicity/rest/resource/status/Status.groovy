package com.manning.grok.simplicity.rest.resource.status

import groovy.transform.Canonical

@Canonical
class Status {
    final String version = '1.0.0-alpha'
    String status
}
