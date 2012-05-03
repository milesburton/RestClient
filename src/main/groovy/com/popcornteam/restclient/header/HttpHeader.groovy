package com.popcornteam.restclient.header

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class HttpHeader {

    final String name
    final String value
    
    HttpHeader(String name, String value) {

        this.name = name
        this.value = value
    }
}

