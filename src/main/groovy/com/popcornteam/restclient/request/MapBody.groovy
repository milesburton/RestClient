package com.popcornteam.restclient.request

import com.google.gson.Gson
import groovy.transform.EqualsAndHashCode
import org.apache.http.HttpEntity
import org.apache.http.entity.StringEntity

@EqualsAndHashCode
class MapBody implements Body {
    private Gson gson = new Gson()
    final Map body

    MapBody(Map body) {
        this.body = body
    }

    @Override
    HttpEntity getHttpEntity() {
        return new StringEntity(toString())
    }

    @Override
    String toString() {
        return gson.toJson(body)
    }
}
