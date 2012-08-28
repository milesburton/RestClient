package com.popcornteam.restclient.request



import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.http.HttpEntity
import org.apache.http.entity.StringEntity

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

    @Override
    int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this)
    }

    @Override
    boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
