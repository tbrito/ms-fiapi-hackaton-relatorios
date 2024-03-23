package br.com.fiapihackaton.relatorios.driver.web

import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.uri.UriBuilder
import io.swagger.v3.oas.annotations.Hidden

/**
 * Controller para habilitar Swagger
 */
@CompileStatic
@Controller
class HomeController {

    private final static URI SWAGGER_UI = UriBuilder.of("/swagger-ui").path("index.html").build()

    @Get
    @Hidden
    HttpResponse<?> home() {
        return HttpResponse.seeOther(SWAGGER_UI)
    }
}
