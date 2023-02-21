package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 1ommy
 * @version 19.02.2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    public String name;
    public Boolean isDone;
}
