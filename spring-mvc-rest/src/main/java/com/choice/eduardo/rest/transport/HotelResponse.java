package com.choice.eduardo.rest.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class HotelResponse {
    private List<HotelDTO> data;
    private Pagination pagination;
}
