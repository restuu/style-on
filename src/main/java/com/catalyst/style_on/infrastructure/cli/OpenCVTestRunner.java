package com.catalyst.style_on.infrastructure.cli;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

// This runner is just to test opencv loaded properly
@Configuration
public class OpenCVTestRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(OpenCVTestRunner.class);


    @Override
    public void run(String... args) throws Exception {
        logger.info("OpenCV Library Version: {}", Core.VERSION);

        // CV_8UC1 stands for 8-bit, Unsigned, 1 Channel (e.g., Grayscale)
        Mat mat = new Mat(5, 5, CvType.CV_8UC1);

        logger.info("OpenCV Mat created (5x5, 8-bit unsigned, 1 channel): {}", mat.dump());
    }
}
