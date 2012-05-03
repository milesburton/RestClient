package com.popcornteam.restclient.request

import org.apache.http.HttpEntity

interface Body {

    HttpEntity getHttpEntity()
}
