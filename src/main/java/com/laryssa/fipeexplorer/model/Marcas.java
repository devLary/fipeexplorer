package com.laryssa.fipeexplorer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Marcas(@JsonAlias("nome") String nome,
                     @JsonAlias("codigo") String codigo) {
}
