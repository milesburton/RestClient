package com.popcornteam.restclient.filter

import com.popcornteam.restclient.exception.RestClientException
import com.popcornteam.restclient.response.HttpRestResponse
import spock.lang.Specification
import com.popcornteam.restclient.response.RestResponse

class HttpStatusFilterSpec extends Specification {

    private RestResponse restResponse

    void setup() {
        restResponse = Mock(HttpRestResponse)
    }

    def 'filter when match'() {

        given:
            HttpStatusFilter filter = new HttpStatusFilter([ 321, 456 ])

        when:
            filter.filter(restResponse)

        then:
            1 * restResponse.status >> status
            0 * _._

        where:
            status << [ 321, 456 ]
    }

    def 'filter when no match'() {
        
        given:
            HttpStatusFilter filter = new HttpStatusFilter(statuses)

        when:
            filter.filter(restResponse)

        then:
            3 * restResponse.status >> 123
            0 * _._

        and:
            RestClientException ex = thrown(RestClientException)
            ex.message == 'Unexpected http status: 123'
            ex.status == 123

        where:
            statuses << [
                    [],
                    [ 321, 456 ]
            ]
    }
}
