package com.popcornteam.restclient.response

import com.google.gson.Gson
import com.popcornteam.restclient.header.HttpHeader
import org.apache.http.Header
import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils

class HttpRestResponse implements RestResponse {

    private HttpResponse httpResponse
    
    private String contentType
    private String body

    HttpRestResponse(HttpResponse httpResponse) {
        
        this.httpResponse = httpResponse

        if (httpResponse.entity) {
            this.body = EntityUtils.toString(httpResponse.entity, 'UTF-8')
            this.contentType = httpResponse.entity.contentType?.value
        }
    }

    List<HttpHeader> getHeaders() {
        
        return httpResponse.allHeaders.collect { Header header ->
            new HttpHeader(header.name, header.value)
        }
    }

    String getContentType() {
        return contentType
    }

    int getStatus() {
        return httpResponse.statusLine.statusCode
    }

    String getReasonPhrase() {
        return httpResponse.statusLine.reasonPhrase
    }

    String getBodyAsString() {
        return new String(body)
    }

    Map getBodyAsJsonMap() {
        
        String json = getBodyAsString()
        return new Gson().fromJson(json, HashMap)
    }
    
    boolean hasBody() {
        return body != null
    }
    
}
