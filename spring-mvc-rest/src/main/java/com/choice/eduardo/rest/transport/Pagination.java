package com.choice.eduardo.rest.transport;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {
    private Integer pageNumber;
    private Integer pageSize;
}
