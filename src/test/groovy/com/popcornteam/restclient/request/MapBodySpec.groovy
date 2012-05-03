package com.popcornteam.restclient.request

import spock.lang.Specification

class MapBodySpec extends Specification {


    def 'not equal to' () {

        expect:
            body != newBody

        where:
            body                        | newBody
            new MapBody([_Id: 'blah'])  | new MapBody([Other_Id:'blah'])

    }

    def 'equal to' () {
        expect:
            body == newBody

        where:
            body                        | newBody
            new MapBody([_Id: 'blah'])  | new MapBody([_Id:'blah'])
    }

}
