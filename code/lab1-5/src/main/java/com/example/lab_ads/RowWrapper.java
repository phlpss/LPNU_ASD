package com.example.lab_ads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RowWrapper {
    private Double sum;
    private List<Double> values;
}
