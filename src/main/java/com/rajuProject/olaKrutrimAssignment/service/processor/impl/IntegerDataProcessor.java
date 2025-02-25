package com.rajuProject.olaKrutrimAssignment.service.processor.impl;


import com.rajuProject.olaKrutrimAssignment.service.processor.DataProcessor;
import org.springframework.stereotype.Service;

import java.util.List;


/*
    * service impl for processing integer list
*/
@Service
public class IntegerDataProcessor implements DataProcessor<Integer> {

    @Override
    public List<Integer> process(List<Integer> data) {
        return data.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * 2)
                .sorted()
                .toList();
    }

    @Override
    public Class<Integer> getSupportedType() {
        return Integer.class;
    }
}