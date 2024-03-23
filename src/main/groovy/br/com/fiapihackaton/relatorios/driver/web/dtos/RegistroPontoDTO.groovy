package br.com.fiapihackaton.relatorios.driver.web.dtos

import java.time.LocalDateTime

import br.com.fiapihackaton.relatorios.domain.model.valueobject.TipoRegistroPonto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class RegistroPontoDTO {

    String usuario
    LocalDateTime horario
    TipoRegistroPonto tipo
}
