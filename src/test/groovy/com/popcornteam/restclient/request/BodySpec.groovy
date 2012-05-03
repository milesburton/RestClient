package com.popcornteam.restclient.request

import spock.lang.Specification

class BodySpec extends Specification {

    def 'to string'() {

        expect:
            body.toString() == expected
        
        where:
            body                              | expected
            new StringBody('{"some":"json"}') | '{"some":"json"}'
            new MapBody([ some: 'json' ])     | '{"some":"json"}'
    }

    def 'equality'() {

        expect:
            body == newBody

        where:
            body                                |newBody
            new MapBody([ some: 'json' ])       |new MapBody([ some: 'json' ])
            new StringBody('{"some":"json"}')   |new StringBody('{"some":"json"}')
    }
}
