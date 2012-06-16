package com.popcornteam.restclient

import com.google.gson.Gson
import com.popcornteam.restclient.header.HttpHeader
import com.popcornteam.restclient.request.Body
import com.popcornteam.restclient.response.HttpRestResponse
import com.popcornteam.restclient.response.RestResponse
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.*

class RestClient {

    private String baseUrl
    private List<HttpHeader> standardHeaders
    private HttpClient httpClient

    private Gson gson = new Gson()

    RestClient(String baseUrl, Collection<HttpHeader> standardHeaders, HttpClient httpClient) {

        this.baseUrl = baseUrl
        this.standardHeaders = standardHeaders
        this.httpClient = httpClient
    }

    RestResponse get(String path, Collection<HttpHeader> additionalHeaders = []) {

        HttpGet httpGet = new HttpGet(toUrl(path))
        List<HttpHeader> headers = filterContentType(standardHeaders + additionalHeaders)
        addHeaders(httpGet, headers)

        return execute(httpGet)
    }
    
    List<HttpHeader> filterContentType(Collection<HttpHeader> headers) {
        return headers.findAll { !it.name.equalsIgnoreCase('Content-Type') }
    }

    RestResponse post(String path, Body body, Collection<HttpHeader> additionalHeaders = []) {

        HttpPost httpPost = new HttpPost(toUrl(path))
        addHeaders(httpPost, standardHeaders + additionalHeaders)
        httpPost.entity = body.httpEntity

        return execute(httpPost)
    }

    RestResponse put(String path, Body body, Collection<HttpHeader> additionalHeaders = []) {

        HttpPut httpPut = new HttpPut(toUrl(path))
        addHeaders(httpPut, standardHeaders + additionalHeaders)
        httpPut.entity = body.httpEntity

        return execute(httpPut)
    }
    
    RestResponse delete(String path, Collection<HttpHeader> additionalHeaders = []) {

        HttpDelete httpDelete = new HttpDelete(toUrl(path))
        List<HttpHeader> headers = filterContentType(standardHeaders + additionalHeaders)
        addHeaders(httpDelete, headers)

        return execute(httpDelete)
    }

    private String toUrl(String path) {
        return "${baseUrl}${path}"
    }

    private void addHeaders(HttpRequestBase httpRequest, Collection<HttpHeader> headers) {

        headers.each { HttpHeader header ->
            httpRequest.setHeader(header.name, header.value)
        }
    }

    protected RestResponse execute(HttpUriRequest httpRequest) {

        RestResponse restResponse
        HttpEntity httpEntity

        try {
            HttpResponse httpResponse = httpClient.execute(httpRequest)
            restResponse = new HttpRestResponse(httpResponse)
            httpEntity = httpResponse.entity
        } finally {
            EntityUtils.consume(httpEntity)
        }

        return restResponse
    }
}
