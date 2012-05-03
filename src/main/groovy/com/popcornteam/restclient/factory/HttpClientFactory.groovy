package com.popcornteam.restclient.factory

import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.PlainSocketFactory
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient

class HttpClientFactory {

    HttpClient makeThreadSafeHttpClient(int maxConnections) {

        SchemeRegistry schemeRegistry = makeSchemeRegistry()
        ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(schemeRegistry)

        connectionManager.defaultMaxPerRoute = maxConnections
        connectionManager.maxTotal = maxConnections

        return new DefaultHttpClient(connectionManager)
    }

    private SchemeRegistry makeSchemeRegistry() {

        SchemeRegistry schemeRegistry = new SchemeRegistry()

        Scheme httpScheme = new Scheme("http", 80, PlainSocketFactory.socketFactory)
        schemeRegistry.register(httpScheme)

        Scheme httpsScheme = new Scheme("https", 443, SSLSocketFactory.socketFactory)
        schemeRegistry.register(httpsScheme)

        return schemeRegistry
    }
}
