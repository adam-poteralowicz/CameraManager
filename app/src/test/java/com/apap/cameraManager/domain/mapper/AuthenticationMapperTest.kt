package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.AuthenticationResponse
import com.apap.cameraManager.domain.model.Authentication
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AuthenticationMapperTest {

    @Test
    fun `correctly maps authentication response to domain model`() {
        val token = "qwerty1234657890"
        val response = AuthenticationResponse(token)
        val expectedModel = Authentication(token)

        val authentication = response.toAuthentication()

        assertThat(authentication).isEqualTo(expectedModel)
    }

    @Test(expected = ApiParseException::class)
    fun `throws exception if token is null`() {
        val token = null
        val response = AuthenticationResponse(token)

        response.toAuthentication()
    }
}