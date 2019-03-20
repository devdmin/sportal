package com.devdmin.rest.controller.dto.converter;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@FunctionalInterface
public interface BaseConverter<F,T> {
    public T convert(F from);

    default public Set<T> convertAll(Collection<F> fElements){
        Set<T> convertedElement = fElements.stream()
                                            .map(element -> convert(element))
                                            .collect(Collectors.toSet());
        return convertedElement;
    }
}
