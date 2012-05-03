package com.popcornteam.restclient.request

import groovy.transform.EqualsAndHashCode
import org.apache.http.HttpEntity
import org.apache.http.entity.StringEntity

@EqualsAndHashCode
class StringBody implements Body {

    final String body

    StringBody(String body) {
        this.body = body
    }

    @Override
    HttpEntity getHttpEntity() {
        return new StringEntity(body)
    }

    @Override
    String toString() {
        return body
    }
}
