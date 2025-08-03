package com.vitor.amorim.sscon.geospatial.configuration;

import com.vitor.amorim.sscon.geospatial.domain.outputs.SalaryOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class StringToSalaryCoverter implements Converter<String, SalaryOutput> {

    @Override
    public SalaryOutput convert(String source) {
        try {
            return SalaryOutput.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
