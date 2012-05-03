package com.popcornteam.restclient.factory

import org.apache.http.client.HttpClient
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.gmock.WithGMock
import spock.lang.Specification

@WithGMock
class HttpClientFactorySpec extends Specification {

    private HttpClientFactory factory
    
    void setup() {
        factory = new HttpClientFactory()
    }
    
    def 'make thread safe http client'() {
        
        when:
            HttpClient httpClient = factory.makeThreadSafeHttpClient(69)
            ClientConnectionManager connectionManager = httpClient.connectionManager

        then:
            httpClient.getClass() == DefaultHttpClient
            connectionManager.getClass() == ThreadSafeClientConnManager
            connectionManager.schemeRegistry.get('http').defaultPort == 80
            connectionManager.schemeRegistry.get('https').defaultPort == 443
    }

    def 'make thread safe http client configures connection pool'() {

        given:
            ThreadSafeClientConnManager mockConnectionManager = mock(ThreadSafeClientConnManager, constructor(match { it instanceof SchemeRegistry }))
            mockConnectionManager.setMaxTotal(69)
            mockConnectionManager.setDefaultMaxPerRoute(69)

        when:
            HttpClient httpClient = null
            play {
                httpClient = factory.makeThreadSafeHttpClient(69)
            }

        then:
            httpClient.connectionManager.is(mockConnectionManager)
    }
}
