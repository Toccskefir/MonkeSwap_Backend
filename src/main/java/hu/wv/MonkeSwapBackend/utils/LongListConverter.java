package hu.wv.MonkeSwapBackend.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<Long> longs) {
        return longs != null ? String.join(SPLIT_CHAR, longs.toString()) : "";
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        if (string == null) {
            return emptyList();
        } else {
            return Stream.of(string.split(SPLIT_CHAR))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }
    }
}
