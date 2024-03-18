package hu.wv.MonkeSwapBackend.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<Long> longList) {
        return longList != null ? longList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(SPLIT_CHAR))
                : "";
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        if (string.isEmpty()) {
            return new ArrayList<>();
        } else {
            return Stream.of(string.split(SPLIT_CHAR))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }
    }
}
