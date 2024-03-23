package br.com.fiapihackaton.relatorios.domain.repository

import br.com.fiapihackaton.relatorios.domain.model.Usuario

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UsuarioRepository extends CrudRepository<Usuario, String> {
}
