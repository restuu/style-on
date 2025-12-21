package com.catalyst.style_on.domain.downloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public final class Downloader {

    private static final DataBufferFactory DATA_BUFFER_FACTORY = new DefaultDataBufferFactory();
    private final WebClient webClient;

    public Mono<DataBuffer> downloadToMemory(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(byte[].class)
                .map(DATA_BUFFER_FACTORY::wrap);
    }

}
