package com.netflix.lolomo.data;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ArtworkService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArtworkService.class);

    public String generateArtwork(String title) {

        LOGGER.info("Generating artwork for {}", title);

        //Simulate a network call
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return generate(title);
    }

    @NotNull
    private static String generate(String title) {
        return UUID.randomUUID() + "-" + title.toLowerCase().replaceAll(" ", "_") + ".jpg";
    }

    public Map<String, String> batchGenerate(Set<String> titles) {
        LOGGER.info("Batch generating artwork for {}", titles);

        //Simulate a network call
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> result = new HashMap<>();
        for (String title : titles) {
            result.put(title, generate(title));
        }

        return result;
    }
}
