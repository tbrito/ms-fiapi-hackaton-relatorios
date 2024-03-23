package br.com.fiapihackaton.relatorios.domain.model

import java.time.Duration
import java.time.LocalDateTime
import java.time.YearMonth

import br.com.fiapihackaton.relatorios.domain.converter.YearMonthConverter
import br.com.fiapihackaton.relatorios.domain.model.valueobject.TipoRegistroPonto

import groovy.transform.CompileStatic
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@CompileStatic
@Entity
@Table(name = "relatorio_ponto_mensal")
class RelatorioPontoMensal {

    @Id
    @GeneratedValue
    Long id

    @Column(name = "ano_mes", columnDefinition = "date")
    @Convert(converter = YearMonthConverter)
    YearMonth anoMes

    @Column(name = "usuario")
    String usuario

    @OneToMany(fetch = FetchType.EAGER)
    @Column(name = "registro_ponto_id")
    List<RegistroPonto> registroPontos

    @Column(name = "total_horas")
    Long totalHoras

    RelatorioPontoMensal() {
        //nothing
    }

    RelatorioPontoMensal(YearMonth anoMes, String usuario, List<RegistroPonto> registroPontos) {
        this.anoMes = anoMes
        this.usuario = usuario
        this.registroPontos = registroPontos
        this.totalHoras = calcularDuracaoEmHoras(registroPontos)
    }

    private static long calcularDuracaoEmHoras(List<RegistroPonto> registros) {
        long duracaoDosIntervalosEmMinutos = 0
        LocalDateTime entradaMaisRecente = null

        for (RegistroPonto registro : registros) {
            if (registro.getTipo() == TipoRegistroPonto.ENTRADA) {
                entradaMaisRecente = registro.getHorario()
            } else if (registro.getTipo() == TipoRegistroPonto.SAIDA && entradaMaisRecente != null) {
                long duracaoDoIntervaloEmMinutos = Duration.between(entradaMaisRecente, registro.getHorario()).toMinutes()
                duracaoDosIntervalosEmMinutos += duracaoDoIntervaloEmMinutos
                entradaMaisRecente = null
            }
        }

        return (long) (duracaoDosIntervalosEmMinutos / 60)
    }
}
