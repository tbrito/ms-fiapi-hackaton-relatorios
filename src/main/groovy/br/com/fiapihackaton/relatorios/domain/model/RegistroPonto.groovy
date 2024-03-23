package br.com.fiapihackaton.relatorios.domain.model

import java.time.LocalDateTime

import br.com.fiapihackaton.relatorios.domain.model.valueobject.TipoRegistroPonto

import groovy.transform.CompileStatic
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

@CompileStatic
@Entity
@Table(name = "registro_ponto")
class RegistroPonto {

    @Id
    String id
    String usuario
    LocalDateTime horario

    @Enumerated(EnumType.STRING)
    TipoRegistroPonto tipo
}
