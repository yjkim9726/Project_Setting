package com.project.common.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @fileName SwaggerConfig
 * @author yunjeong
 * @since  2024/04/18
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2024/04/18        yunjeong        최초 작성
 */
@Configuration
class SwaggerConfig {

    @Value("\${application.host}")
    private lateinit var host: String

    @Value("\${application.license}")
    private lateinit var license: String

    @Value("\${application.version}")
    private lateinit var version: String

    @Value("\${application.title}")
    private lateinit var title: String

    @Value("\${application.description}")
    private lateinit var description: String

    @Value("\${application.name}")
    private lateinit var name: String

    @Value("\${application.email}")
    private lateinit var email: String

    @Value("\${application.server-url}")
    private lateinit var serverUrl: String


    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title(title)
            .description(description)
            .version(version)

        // JWT V1
        val jwt = "JWT"
        val securityRequirement = SecurityRequirement().addList(jwt)
        val components = Components().addSecuritySchemes(
            jwt, SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        )
        val server = Server()
        server.url = serverUrl
        return OpenAPI()
            .info(info)
            .servers(listOf(server))
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}