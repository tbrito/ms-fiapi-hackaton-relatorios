package br.com.fiapihackaton.relatorios.domain.model

import groovy.transform.CompileStatic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@CompileStatic
@Entity
@Table(name = "usuario")
class Usuario {

    @Id
    @Column(name = "nome_usuario")
    String nomeUsuario
}
