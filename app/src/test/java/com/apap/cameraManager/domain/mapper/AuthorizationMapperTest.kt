package com.apap.cameraManager.domain.mapper

import com.apap.cameraManager.data.AuthorizationResponse
import com.apap.cameraManager.domain.model.Authorization
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AuthorizationMapperTest {

    @Test
    fun `correctly maps authorization response to domain model`() {
        val activeBrandSubdomain = "c000"
        val id = "test"
        val userId = "test"
        val response = AuthorizationResponse(activeBrandSubdomain, id, userId)
        val expectedModel = Authorization(activeBrandSubdomain, id, userId)

        val authorization = response.toAuthorization()

        assertThat(authorization).isEqualTo(expectedModel)
    }

    @Test(expected = ApiParseException::class)
    fun `throws exception if active brand subdomain is null`() {
        val activeBrandSubdomain = null
        val id = "test"
        val response = AuthorizationResponse(activeBrandSubdomain, id, id)

        response.toAuthorization()
    }

    @Test(expected = ApiParseException::class)
    fun `throws exception if user id is null`() {
        val activeBrandSubdomain = "c000"
        val id = null
        val userId = "test"
        val response = AuthorizationResponse(activeBrandSubdomain, id, userId)

        response.toAuthorization()
    }

    @Test(expected = ApiParseException::class)
    fun `throws exception if id is null`() {
        val activeBrandSubdomain = "c000"
        val id = null
        val userId = "test"
        val response = AuthorizationResponse(activeBrandSubdomain, id, userId)

        response.toAuthorization()
    }
}