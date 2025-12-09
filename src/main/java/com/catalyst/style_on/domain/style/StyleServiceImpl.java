package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class StyleServiceImpl implements  StyleService {

    private final StyleRepository styleRepository;

    @Override
    public Flux<StyleResponseDTO> findStylesByTagName(String name) {
        return styleRepository.findByTagName(name)
                .map(StyleMapper::styleToStyleResponseDTO);
    }
}
