package com.popcornteam.restclient.response

import com.popcornteam.restclient.header.HttpHeader

interface RestResponse {

    int getStatus()
    List<HttpHeader> getHeaders()
    String getReasonPhrase()
    String getBodyAsString()
    Map getBodyAsJsonMap()
    String getContentType()
    boolean hasBody()
}
