package com.popcornteam.restclient.header

import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.commons.lang.builder.EqualsBuilder

class HttpHeader {

    final String name
    final String value
    
    HttpHeader(String name, String value) {

        this.name = name
        this.value = value
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

