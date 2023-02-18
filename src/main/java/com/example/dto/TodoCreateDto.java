package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 1ommy
 * @version 18.02.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateDto {
    private String text;
    private String token;
}
