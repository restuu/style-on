package com.catalyst.style_on;

import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StyleOnApplication {

    public static void main(String[] args) {
        OpenCV.loadLocally();
        log.info("OpenCV Native Library loaded successfully.");

        SpringApplication.run(StyleOnApplication.class, args);
    }

}
