package com.popcornteam.restclient.header

interface ContentType {

    String APPLICATION_JSON = 'application/json'
    String TEXT_XML = 'text/xml'
    String TEXT_PLAIN = 'text/plain'
    String JSON_WITH_CHARSET = "${ContentType.APPLICATION_JSON};charset=utf-8"
    String XML_WITH_CHARSET = "${ContentType.TEXT_XML};charset=utf-8"
}
