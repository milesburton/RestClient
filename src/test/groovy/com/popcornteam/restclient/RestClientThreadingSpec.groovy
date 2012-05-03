package com.popcornteam.restclient

import com.popcornteam.restclient.factory.HttpClientFactory
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.atomic.AtomicInteger
import org.apache.http.client.HttpClient
import spock.lang.Specification

class RestClientThreadingSpec extends Specification {

    def 'get with concurrency'() {
        
        given:
            HttpClientFactory httpClientFactory = new HttpClientFactory()
            HttpClient httpClient = httpClientFactory.makeThreadSafeHttpClient(2)
            RestClient restClient = new RestClient("http:/google.com", [], httpClient)
            int threads = 4

            CyclicBarrier startGate = new CyclicBarrier(threads + 1)
            CyclicBarrier finishGate = new CyclicBarrier(threads + 1)
        
            AtomicInteger counter = new AtomicInteger(0)

        when:
            threads.times { int i ->

                Thread.start {
                    startGate.await()

                    try {
                        restClient.get("/qwerty")
                    } catch (IllegalStateException ex) {
                        counter.incrementAndGet()
                        throw ex
                    } finally {
                        finishGate.await()
                    }
                }
            }

            startGate.await()
            finishGate.await()

        then:
            counter.intValue() == 0
    }
}
