package com.popcornteam.restclient

import org.apache.http.HttpResponse
import org.apache.http.ProtocolVersion
import org.apache.http.message.BasicHttpResponse
import org.apache.http.HttpEntity
import org.apache.http.entity.StringEntity

class HttpResponseFactory {

    HttpResponse makeHttpResponse(int status, String reason) {

        ProtocolVersion protocolVersion = new ProtocolVersion('HTTP', 1, 1)

        return new BasicHttpResponse(protocolVersion, status, reason)
    }

    HttpResponse makeHttpResponseWithEntity(int status, String reason, String contentType, String body) {

        ProtocolVersion protocolVersion = new ProtocolVersion('HTTP', 1, 1)

        HttpResponse response = new BasicHttpResponse(protocolVersion, status, reason)

        HttpEntity entity = new StringEntity(body)
        entity.setContentType(contentType)

        response.entity = entity

        return response
    }
}
