package com.catalyst.style_on.domain.style.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Setter
@Accessors(chain = true)
public class StyleResponseDTO{
    private Long id;
    private String name;
    private String imageUrl;
    private String note;
}

