package com.popcornteam.restclient.filter

import com.popcornteam.restclient.exception.RestClientException
import com.popcornteam.restclient.response.RestResponse

class HttpStatusFilter {

    final Set<Integer> statuses

    HttpStatusFilter(Collection statuses) {
        this.statuses = new TreeSet(statuses)
    }

    void filter(RestResponse restResponse) {
        
        if (!(statuses.contains(restResponse.status))) {
            throw new RestClientException("Unexpected http status: ${restResponse.status}", restResponse.status)
        }
    }
}
