package com.vitor.amorim.sscon.geospatial.configuration;

import com.vitor.amorim.sscon.geospatial.domain.outputs.AgeOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class StringToAgeOutputConverter implements Converter<String, AgeOutput> {

    @Override
    public AgeOutput convert(String source) {
        try {
            return AgeOutput.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
