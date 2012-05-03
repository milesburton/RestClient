package com.popcornteam.restclient.response

import com.popcornteam.restclient.header.ContentType
import com.popcornteam.restclient.HttpResponseFactory
import com.popcornteam.restclient.header.HttpHeader
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.entity.StringEntity
import org.apache.http.util.EntityUtils
import spock.lang.Specification

@Mixin(HttpResponseFactory)
class HttpRestResponseSpec extends Specification {

    def 'get headers'() {
        
        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')
            httpResponse.addHeader('Nude', 'Zebras')
            httpResponse.addHeader('Clothed', 'Horses')

        and:
            HttpRestResponse restResponse = new HttpRestResponse(httpResponse)
        
        expect:
            restResponse.headers == [
                    new HttpHeader('Nude', 'Zebras'),
                    new HttpHeader('Clothed', 'Horses')
            ]
    }

    def 'get headers when no headers'() {

        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')

        and:
            HttpRestResponse restResponse = new HttpRestResponse(httpResponse)

        expect:
            restResponse.headers == []
    }

    def 'get status'() {
        
        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')

        when:
            HttpRestResponse jsonHttpResponse = new HttpRestResponse(httpResponse)

        then:
            jsonHttpResponse.status == 234
    }

    def 'get body as string'() {

        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')
            HttpEntity stringEntity = new StringEntity("qwerty")
            httpResponse.setEntity(stringEntity)

        when:
            HttpRestResponse jsonHttpResponse = new HttpRestResponse(httpResponse)
            EntityUtils.consume(stringEntity)

        then:
            jsonHttpResponse.bodyAsString == "qwerty"
            jsonHttpResponse.hasBody()
    }

    def 'get body as json map'() {

        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')
            HttpEntity stringEntity = new StringEntity('{"some":"json","qwerty":["abc","def"]}')
            httpResponse.setEntity(stringEntity)

        when:
            HttpRestResponse jsonHttpResponse = new HttpRestResponse(httpResponse)
            EntityUtils.consume(stringEntity)

        then:
            jsonHttpResponse.bodyAsJsonMap == [ some: 'json', qwerty: [ 'abc', 'def' ] ]
            jsonHttpResponse.hasBody()
    }

    def 'get content type'() {

        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')
            HttpEntity stringEntity = new StringEntity('{}')
            stringEntity.setContentType(ContentType.APPLICATION_JSON)
            httpResponse.setEntity(stringEntity)

        when:
            HttpRestResponse jsonHttpResponse = new HttpRestResponse(httpResponse)

        then:
            jsonHttpResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'get content type when entity does not specify one'() {

        given:
            HttpResponse httpResponse = makeHttpResponse(234, 'Reason')
            HttpEntity stringEntity = new StringEntity('{}')
            stringEntity.setContentType(null)
            httpResponse.setEntity(stringEntity)

        when:
            HttpRestResponse jsonHttpResponse = new HttpRestResponse(httpResponse)

        then:
            jsonHttpResponse.contentType == null
    }
    
    def 'get encoded body'() {
        
        given:
            final byte[] encodedData = [169]
            final String expectedString = '©'

            HttpResponse httpResponse = makeHttpResponse(200, 'OK')
            HttpEntity dataEntity = new org.apache.http.entity.ByteArrayEntity(encodedData)
            dataEntity.setContentType('application/xml;charset=ISO-8859-1')
            httpResponse.setEntity(dataEntity)

        when:
            HttpRestResponse httpRestResponse = new HttpRestResponse(httpResponse)
        
        then:
            httpRestResponse.bodyAsString == expectedString
            httpRestResponse.hasBody()
    }

}
