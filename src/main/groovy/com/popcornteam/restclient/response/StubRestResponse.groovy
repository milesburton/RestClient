package com.popcornteam.restclient.response

import com.popcornteam.restclient.header.HttpHeader
import com.google.gson.Gson

class StubRestResponse implements RestResponse {
    Gson gson = new Gson()
    int status
    String body
    String contentType
    String reasonPhrase
    List<HttpHeader> headers

    StubRestResponse() {
    }

    StubRestResponse(int status) {
        this.status = status
    }

    StubRestResponse(int status, String body) {

        this.status = status
        this.body = body
    }

    StubRestResponse(int status, Map body) {

        this.status = status
        this.body = gson.toJson(body)
    }

    String getReasonPhrase() {
        return reasonPhrase
    }

    boolean hasBody() {
        return body != null
    }

    String getBodyAsString() {
        return body
    }

    Map getBodyAsJsonMap() {
        return gson.fromJson(body, Map)
    }

    void setBodyAsJsonMap(Map jsonMap) {
        this.body = gson.toJson(jsonMap)
    }

    void setBodyAsString(String body) {
        this.body = body
    }


}
