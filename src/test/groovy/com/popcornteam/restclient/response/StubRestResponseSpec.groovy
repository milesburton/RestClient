package com.popcornteam.restclient.response

import spock.lang.Specification
import com.google.gson.JsonSyntaxException

class StubRestResponseSpec extends Specification {

    def 'constructor with status'() {

        given:
            RestResponse restResponse = new StubRestResponse(123)

        expect:
            restResponse.status == 123
            !restResponse.hasBody()
    }

    def 'constructor with status and body as string'() {

        given:
            RestResponse restResponse = new StubRestResponse(345, 'testContent')

        expect:
            restResponse.status == 345
            restResponse.bodyAsString == 'testContent'
            restResponse.hasBody()

        when:
            restResponse.bodyAsJsonMap

        then:
            thrown(JsonSyntaxException)

    }

    def 'constructor with status and body as json string'() {

        given:
            RestResponse restResponse = new StubRestResponse(345, "{'test':'testContent'}")
        
        expect:
            restResponse.status == 345
            restResponse.bodyAsString == "{'test':'testContent'}"
            restResponse.bodyAsJsonMap == [test:'testContent']
            restResponse.hasBody()
    }

    def 'constructor with status and body as invalid json string'() {

        given:
            RestResponse restResponse = new StubRestResponse(345, "{'test:'testContent'}")

        expect:
            restResponse.status == 345
            restResponse.bodyAsString == "{'test:'testContent'}"
        when:
            restResponse.bodyAsJsonMap == [test:'testContent']
        then:
            thrown(JsonSyntaxException)
    }

    def 'constructor with status and body as json map'() {
        
        given:
            Map jsonMap = [test:'testContent']
            RestResponse restResponse = new StubRestResponse(456, jsonMap)
        
        expect:
            restResponse.status == 456
            restResponse.bodyAsJsonMap.equals(jsonMap)
            restResponse.bodyAsString == '{"test":"testContent"}'
            restResponse.hasBody()
    }

    def 'set body as json map'() {

        given:
            Map jsonMap = [test:'testContent']
            RestResponse restResponse = new StubRestResponse(456)
            restResponse.bodyAsJsonMap = jsonMap

        expect:
            restResponse.status == 456
            restResponse.bodyAsJsonMap.equals(jsonMap)
            restResponse.bodyAsString == '{"test":"testContent"}'
            restResponse.hasBody()
    }

}
