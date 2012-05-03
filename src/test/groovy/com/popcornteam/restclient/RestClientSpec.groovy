package com.popcornteam.restclient

import com.popcornteam.restclient.request.StringBody as PopcornStringBody

import com.popcornteam.restclient.header.ContentType
import com.popcornteam.restclient.header.HttpHeader
import com.popcornteam.restclient.request.MultipartBody
import com.popcornteam.restclient.response.RestResponse
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.StringEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import org.gmock.WithGMock
import spock.lang.Specification
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpRequestBase

@Mixin(HttpResponseFactory)
@WithGMock
class RestClientSpec extends Specification {

    private HttpClient mockHttpClient

    private HttpHeader header1
    private HttpHeader header2
    private HttpHeader header3
    private HttpHeader header4

    private RestClient restClient

    void setup() {

        mockHttpClient = mock(HttpClient)

        header1 = new HttpHeader('Animal', 'elephant')
        header2 = new HttpHeader('Content-Type', 'rabbit')

        header3 = new HttpHeader('Robot', 'Bender')
        header4 = new HttpHeader('Human', 'Fry')

        restClient = new RestClient("http://www.example.com/hello", [ header1, header2 ], mockHttpClient)
    }

    def 'get'() {
        
        given:
            HttpGet mockHttpGet = mock(HttpGet, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpGet, [ header1 ])
        
            HttpResponse httpResponse = makeHttpResponseWithEntity(234, 'Reason', ContentType.APPLICATION_JSON, "json")

            mockHttpClient.execute(mockHttpGet).returns(httpResponse)
        
        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.get('/somepath')
            }

        then:
            restResponse.status == 234
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'get with additional headers'() {

        given:
            HttpGet mockHttpGet = mock(HttpGet, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpGet, [ header1, header3, header4 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(234, 'Reason', ContentType.APPLICATION_JSON, "json")

            mockHttpClient.execute(mockHttpGet).returns(httpResponse)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.get('/somepath', [ header2, header3, header4 ])
            }

        then:
            restResponse.status == 234
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'post'() {

        given:
            HttpPost mockHttpPost = mock(HttpPost, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpPost, [ header1, header2 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(456, 'Reason', ContentType.APPLICATION_JSON, "json")
            mockHttpClient.execute(mockHttpPost).returns(httpResponse)

            PopcornStringBody stringBody = mock(PopcornStringBody)
            StringEntity mockStringEntity = mock(StringEntity)
            stringBody.httpEntity.returns(mockStringEntity)
            mockHttpPost.setEntity(mockStringEntity)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.post('/somepath', stringBody)
            }

        then:
            restResponse.status == 456
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'post with additional headers'() {

        given:
            HttpPost mockHttpPost = mock(HttpPost, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpPost, [ header1, header2, header3, header4 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(456, 'Reason', ContentType.APPLICATION_JSON, "json")
            mockHttpClient.execute(mockHttpPost).returns(httpResponse)

            PopcornStringBody stringBody = mock(PopcornStringBody)
            StringEntity mockStringEntity = mock(StringEntity)
            stringBody.httpEntity.returns(mockStringEntity)
            mockHttpPost.setEntity(mockStringEntity)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.post('/somepath', stringBody, [ header3, header4 ])
            }

        then:
            restResponse.status == 456
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'post multipart'() {

        given:
            HttpPost mockHttpPost = mock(HttpPost, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpPost, [ header1, header2 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(456, 'Reason', ContentType.TEXT_XML, "<response></response>")
            mockHttpClient.execute(mockHttpPost).returns(httpResponse)

            MultipartBody multipartBody = new MultipartBody()
            multipartBody.addPart('body', new StringBody('body'))
            multipartBody.addPart('content', new StringBody('content'))
            multipartBody.addPart('file', new ByteArrayBody((new String("<xml></xml>").getBytes()),'xmlfile'))

            mockHttpPost.setEntity(multipartBody.httpEntity)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.post('/somepath', multipartBody)
            }

        then:
            restResponse.status == 456
            restResponse.contentType == ContentType.TEXT_XML
    }

    def 'put'() {

        given:
            HttpPut mockHttpPut = mock(HttpPut, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpPut, [ header1, header2 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(567, 'Reason', ContentType.APPLICATION_JSON, "json")
            mockHttpClient.execute(mockHttpPut).returns(httpResponse)

            PopcornStringBody stringBody = mock(PopcornStringBody)
            StringEntity mockStringEntity = mock(StringEntity)
            stringBody.httpEntity.returns(mockStringEntity)
            mockHttpPut.setEntity(mockStringEntity)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.put('/somepath', stringBody)
            }

        then:
            restResponse.status == 567
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'put with additional headers'() {

        given:
            HttpPut mockHttpPut = mock(HttpPut, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpPut, [ header1, header2, header3, header4 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(567, 'Reason', ContentType.APPLICATION_JSON, "json")
            mockHttpClient.execute(mockHttpPut).returns(httpResponse)

            PopcornStringBody stringBody = mock(PopcornStringBody)
            StringEntity mockStringEntity = mock(StringEntity)
            stringBody.httpEntity.returns(mockStringEntity)
            mockHttpPut.setEntity(mockStringEntity)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.put('/somepath', stringBody, [ header3, header4 ])
            }

        then:
            restResponse.status == 567
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'delete'() {

        given:
            HttpDelete mockHttpDelete = mock(HttpDelete, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpDelete, [ header1 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(567, 'Reason', ContentType.APPLICATION_JSON, "json")
            mockHttpClient.execute(mockHttpDelete).returns(httpResponse)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.delete('/somepath')
            }

        then:
            restResponse.status == 567
            restResponse.contentType == ContentType.APPLICATION_JSON
    }

    def 'delete with additional headers'() {

        given:
            HttpDelete mockHttpDelete = mock(HttpDelete, constructor("http://www.example.com/hello/somepath"))
            setHttpHeaderExpectations(mockHttpDelete, [ header1, header3, header4 ])

            HttpResponse httpResponse = makeHttpResponseWithEntity(567, 'Reason', ContentType.APPLICATION_JSON, "json")
            mockHttpClient.execute(mockHttpDelete).returns(httpResponse)

        when:
            RestResponse restResponse = null
            play {
                restResponse = restClient.delete('/somepath', [ header2, header3, header4 ])
            }

        then:
            restResponse.status == 567
            restResponse.contentType == ContentType.APPLICATION_JSON
    }
    
    private void setHttpHeaderExpectations(HttpRequestBase httpRequest, Collection<HttpHeader> httpHeaders) {
        
        httpHeaders.each { HttpHeader httpHeader ->
            httpRequest.setHeader(httpHeader.name, httpHeader.value)
        }
    }
}
