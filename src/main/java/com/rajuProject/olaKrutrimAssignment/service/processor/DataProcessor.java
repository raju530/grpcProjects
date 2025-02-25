package com.rajuProject.olaKrutrimAssignment.service.processor;


import com.rajuProject.olaKrutrimAssignment.exception.DataProcessingException;

import java.util.List;

public interface DataProcessor<T> {
    List<T> process(List<T> data) throws DataProcessingException;

    Class<T> getSupportedType();
}