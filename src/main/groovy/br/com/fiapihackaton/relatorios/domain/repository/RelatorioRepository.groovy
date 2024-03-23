package br.com.fiapihackaton.relatorios.domain.repository

import java.time.LocalDateTime
import java.time.YearMonth

import br.com.fiapihackaton.relatorios.domain.model.RegistroPonto
import br.com.fiapihackaton.relatorios.domain.model.RelatorioPontoMensal

interface RelatorioRepository {

    List<RegistroPonto> getRegistrosPonto(String nomeUsuario, LocalDateTime dataInicio, LocalDateTime dataFim)

    List<RelatorioPontoMensal> getRelatorioMensal(YearMonth anoMes, String nomeUsuario)

    void gerarRelatorioMensal(YearMonth anoMes, List<String> nomesUsuario)
}
