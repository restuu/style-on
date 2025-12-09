package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;
import reactor.core.publisher.Flux;

public interface StyleService {
    Flux<StyleResponseDTO> findStylesByTagName(String name);
}
