package com.popcornteam.restclient.request

import com.google.gson.Gson
import org.apache.http.HttpEntity
import org.apache.http.entity.StringEntity
import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.commons.lang.builder.EqualsBuilder


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

    @Override
    int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this)
    }

    @Override
    boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
