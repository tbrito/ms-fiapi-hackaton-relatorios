package br.com.fiapihackaton.relatorios.driver.web.dtos

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class RelatorioPontoMensalDTO {

    Integer ano
    Integer mes
    String usuario
    List<RegistroPontoDTO> registroPontos
    Long totalHoras
}
