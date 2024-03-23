package br.com.fiapihackaton.relatorios


import groovy.transform.CompileStatic
import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
        info = @Info(
                title = "fiapi-hackaton-relatorios",
                version = "1.0",
                description = "Microserviço Fiap Hackaton Relatorios"
        ), servers = @Server(url = "http://localhost:8080")
)
@CompileStatic
class Application {

    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}
