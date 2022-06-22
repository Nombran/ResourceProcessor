package com.epam.resourceprocessor;

import com.epam.resourceprocessor.processor.SongMetadataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author www.epam.com
 */
@SpringBootApplication(scanBasePackages = "com.epam.resourceprocessor")
public class ResourceProcessorApplication {

    private static SongMetadataProcessor songMetadataProcessor;

    @Autowired
    public void setSongMetadataProcessor(SongMetadataProcessor songMetadataProcessor) {
        ResourceProcessorApplication.songMetadataProcessor = songMetadataProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourceProcessorApplication.class, args);
        songMetadataProcessor.processSongMetadata();
    }

}
