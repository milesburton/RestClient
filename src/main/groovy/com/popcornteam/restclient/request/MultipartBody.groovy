package com.popcornteam.restclient.request

import org.apache.http.HttpEntity
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ContentBody
import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.commons.lang.builder.EqualsBuilder

class MultipartBody implements Body {

    private MultipartEntity multipartEntity = new MultipartEntity()

    void addPart(String name, ContentBody part) {
        multipartEntity.addPart(name, part)
    }

    @Override
    HttpEntity getHttpEntity() {
        return multipartEntity
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
