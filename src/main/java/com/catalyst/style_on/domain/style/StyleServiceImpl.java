package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;
import com.catalyst.style_on.infrastructure.resource.ResourceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class StyleServiceImpl implements  StyleService {

    private final StyleRepository styleRepository;
    private final ResourceConfig resource;

    @Override
    public Flux<StyleResponseDTO> findStylesByTagName(String name) {
        return styleRepository.findByTagName(name)
                .map(StyleMapper::styleToStyleResponseDTO);
    }

    @Override
    public Flux<StyleResponseDTO> findAllStyles() {
        return styleRepository.findAll()
                .map(StyleMapper::styleToStyleResponseDTO)
                .map(dto -> dto.setImageUrl(resource.getAssetBaseUrl()+dto.getImageUrl()));
    }
}
