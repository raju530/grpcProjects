package com.rajuProject.olaKrutrimAssignment.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProcessDataRequest<T>(
        @NotNull @Valid List<T> data,
        String clientType
) {}