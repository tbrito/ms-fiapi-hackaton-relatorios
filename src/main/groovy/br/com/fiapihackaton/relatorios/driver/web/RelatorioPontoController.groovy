package br.com.fiapihackaton.relatorios.driver.web

import java.time.LocalDateTime
import java.time.YearMonth

import br.com.fiapihackaton.relatorios.domain.model.RegistroPonto
import br.com.fiapihackaton.relatorios.domain.model.RelatorioPontoMensal
import br.com.fiapihackaton.relatorios.domain.repository.RelatorioRepository
import br.com.fiapihackaton.relatorios.domain.repository.UsuarioRepository
import br.com.fiapihackaton.relatorios.driver.web.dtos.RegistroPontoDTO
import br.com.fiapihackaton.relatorios.driver.web.dtos.RelatorioPontoMensalDTO

import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.annotation.Nullable
import jakarta.inject.Inject

@CompileStatic
@Controller("/relatorios")
class RelatorioPontoController {

    @Inject
    private RelatorioRepository relatorioRepository

    @Inject
    private UsuarioRepository usuarioRepository

    @Get("/pontos")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consulta registro de pontos", description = "Retorna os registros de pontos com base nos filtros")
    List<RegistroPontoDTO> getRegistrosPonto(
            @Parameter(description = "Nome de usuário") @Nullable @QueryValue String usuario,
            @Parameter(description = "Data de início do período") @Nullable @QueryValue LocalDateTime dataInicio,
            @Parameter(description = "Data de fim do período") @Nullable @QueryValue LocalDateTime dataFim) {
        return toRegistrosPontoDTO(this.relatorioRepository.getRegistrosPonto(usuario, dataInicio, dataFim))
    }

    @Get("/{ano}/{mes}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consulta relatório mensal", description = "Retorna o relatório mensal para o ano e mês especificados")
    List<RelatorioPontoMensalDTO> getRelatorioMensal(int ano, int mes,
                                                     @Parameter(description = "Nome de usuário, se o relatório por usuário") @Nullable @QueryValue String usuario) {
        YearMonth anoMes = YearMonth.of(ano, mes)
        return toRelatorioMensalDTO(this.relatorioRepository.getRelatorioMensal(anoMes, usuario))
    }

    @Post("/{ano}/{mes}/fechamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fechamento do relatório mensal", description = "Gera o fechamento de pontos mensal")
    @ApiResponse(responseCode = "201", description = "Fechamento do mês realizado com sucesso")
    @Status(HttpStatus.CREATED)
    void criarRelatorioMensal(int ano, int mes) {
        YearMonth anoMes = YearMonth.of(ano, mes)
        List<String> usuarios = this.usuarioRepository.findAll().collect { it.nomeUsuario }
        this.relatorioRepository.gerarRelatorioMensal(anoMes, usuarios)
    }

    private static List<RegistroPontoDTO> toRegistrosPontoDTO(List<RegistroPonto> registros) {
        registros.collect {
            return new RegistroPontoDTO(
                    usuario: it.usuario,
                    horario: it.horario,
                    tipo: it.tipo
            )
        }
    }

    private static List<RelatorioPontoMensalDTO> toRelatorioMensalDTO(List<RelatorioPontoMensal> relatorios) {
        relatorios.collect {
            return new RelatorioPontoMensalDTO(
                    ano: it.anoMes.year,
                    mes: it.anoMes.month.value,
                    usuario: it.usuario,
                    totalHoras: it.totalHoras,
                    registroPontos: toRegistrosPontoDTO(it.registroPontos)
            )
        }
    }
}
