package com.alvarosanchez.teams.ratpack

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ServerBackedApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class ApplicationSpec extends Specification {

    @Shared
    ServerBackedApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient testClient = appUnderTest.httpClient

    @Shared
    String token

    void setup() {
        token = postText('login')
    }

    void 'API is protected'() {
        when:
        get('teams')

        then:
        response.statusCode == 401
    }

    void 'it can list teams'() {
        given:
        requestSpec { it.headers.add('Authorization', "Bearer ${token}") }

        when:
        get('teams')

        then:
        response.body.text == '[]'
    }

}
