package com.popcornteam.restclient.exception

import spock.lang.Specification

class RestClientExceptionSpec extends Specification {

    def 'constructor'() {
        
        when:
            RestClientException ex = new RestClientException('Oh hai')

        then:
            ex.message == 'Oh hai'
    }

    def 'constructor with status'() {

        when:
            RestClientException ex = new RestClientException('Oh hai', 404)

        then:
            ex.message == 'Oh hai'
            ex.status == 404
    }
}
