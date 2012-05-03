package com.popcornteam.restclient.exception

class RestClientException extends RuntimeException {

    int status

    RestClientException(String msg) {
        super(msg)
    }

    RestClientException(String msg, int status) {
        super(msg)
        this.status = status
    }
}
