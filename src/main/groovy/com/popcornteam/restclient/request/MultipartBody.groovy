package com.popcornteam.restclient.request

import org.apache.http.HttpEntity
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ContentBody

class MultipartBody implements Body {

    private MultipartEntity multipartEntity = new MultipartEntity()

    void addPart(String name, ContentBody part) {
        multipartEntity.addPart(name, part)
    }

    @Override
    HttpEntity getHttpEntity() {
        return multipartEntity
    }
}
