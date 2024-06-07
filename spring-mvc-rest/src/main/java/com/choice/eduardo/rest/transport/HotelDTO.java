package com.choice.eduardo.rest.transport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDTO implements Serializable {
    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private List<String> amenities;
}
