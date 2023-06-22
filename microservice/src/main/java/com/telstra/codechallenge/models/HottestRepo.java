package com.telstra.codechallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HottestRepo {

    private Boolean incomplete_results;
    private int total_count;
    private List<Repo> items;

}
