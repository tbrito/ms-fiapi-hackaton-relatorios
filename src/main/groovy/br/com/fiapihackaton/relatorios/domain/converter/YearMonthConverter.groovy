package br.com.fiapihackaton.relatorios.domain.converter

import java.sql.Date
import java.time.YearMonth

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class YearMonthConverter implements AttributeConverter<YearMonth, Date> {

    @Override
    Date convertToDatabaseColumn(YearMonth yearMonth) {
        return yearMonth == null ? null : Date.valueOf(yearMonth.atDay(1))
    }

    @Override
    YearMonth convertToEntityAttribute(Date date) {
        return date == null ? null : YearMonth.from(date.toLocalDate())
    }
}
