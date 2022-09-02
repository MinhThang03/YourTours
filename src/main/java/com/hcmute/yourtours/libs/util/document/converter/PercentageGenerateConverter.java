package com.hcmute.yourtours.libs.util.document.converter;

public class PercentageGenerateConverter implements GenerateConverter<Float, String> {
    @Override
    public String convert(Float interest) {
        return String.format("%.2f %s", interest * 100, "%");
    }
}
