package com.rajuProject.olaKrutrimAssignment.service.processor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProcessorFactory {

    private final Map<Class<?>, DataProcessor<?>> processors;

    @Autowired
    public ProcessorFactory(List<DataProcessor<?>> processorList) {
        processors = processorList.stream()
                .collect(Collectors.toMap(
                        DataProcessor::getSupportedType,
                        Function.identity()
                ));
    }

    @SuppressWarnings("unchecked")
    public <T> DataProcessor<T> getProcessor(Class<T> type) {
        DataProcessor<?> processor = processors.get(type);
        if (processor == null) {
            throw new IllegalArgumentException("No processor found for type: " + type);
        }
        return (DataProcessor<T>) processor;
    }
}