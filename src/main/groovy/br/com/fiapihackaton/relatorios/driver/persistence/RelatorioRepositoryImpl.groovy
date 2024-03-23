package br.com.fiapihackaton.relatorios.driver.persistence


import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

import br.com.fiapihackaton.relatorios.domain.model.RegistroPonto
import br.com.fiapihackaton.relatorios.domain.model.RelatorioPontoMensal
import br.com.fiapihackaton.relatorios.domain.repository.RelatorioRepository

import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Repository
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional

@CompileStatic
@Repository
class RelatorioRepositoryImpl implements RelatorioRepository {

    @Inject
    private EntityManager em

    @Override
    @ReadOnly
    List<RegistroPonto> getRegistrosPonto(String nomeUsuario, LocalDateTime dataInicio, LocalDateTime dataFim) {
        String sql = """
            SELECT rp FROM RegistroPonto rp 
            WHERE 1=1
            ${nomeUsuario ? 'AND rp.usuario = :usuario' : ''}
            AND rp.horario >= :dataInicio
            AND rp.horario <= :dataFim
            ORDER BY rp.horario
        """
        def query = this.em.createQuery(sql.toString(), RegistroPonto.class)

        if (nomeUsuario) {
            query.setParameter("usuario", nomeUsuario)
        }
        query.setParameter("dataInicio", dataInicio ?: getInicioDoDia())
        query.setParameter("dataFim", dataFim ?: getFimDoDia())

        return query.getResultList()
    }

    @Override
    @ReadOnly
    List<RelatorioPontoMensal> getRelatorioMensal(YearMonth anoMes, String nomeUsuario) {
        String sql = """
            SELECT rpm FROM RelatorioPontoMensal rpm 
            WHERE 1=1
            ${nomeUsuario ? 'AND rpm.usuario = :usuario' : ''}
            AND rpm.anoMes = :anoMes
        """
        def query = this.em.createQuery(sql, RelatorioPontoMensal.class)
        query.setParameter("anoMes", anoMes)
        if (nomeUsuario) {
            query.setParameter("usuario", nomeUsuario)
        }
        return query.getResultList()
    }

    @Override
    @Transactional
    void gerarRelatorioMensal(YearMonth anoMes, List<String> nomesUsuario) {
        LocalDateTime primeiroDiaDoMes = anoMes.atDay(1).atStartOfDay()
        LocalDateTime ultimoDiaDoMes = anoMes.atEndOfMonth().atTime(23, 59, 59)
        nomesUsuario.each { String nomeUsuario ->
            List<RegistroPonto> pontosPorUsuario = this.getRegistrosPonto(nomeUsuario, primeiroDiaDoMes, ultimoDiaDoMes)
            RelatorioPontoMensal relatorioPontoMensal = new RelatorioPontoMensal(anoMes, nomeUsuario, pontosPorUsuario)
            this.em.persist(relatorioPontoMensal)
        }
    }

    private static LocalDateTime getInicioDoDia() {
        return LocalDate.now().atStartOfDay()
    }

    private static LocalDateTime getFimDoDia() {
        return LocalDate.now().plusDays(1).atStartOfDay()
    }

}
