package com.netflix.lolomo.data;

import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(maxBatchSize = 3)
public class ArtworkDataloader implements MappedBatchLoader<String, String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ArtworkDataloader.class);
    private final ArtworkService artworkService;

    public ArtworkDataloader(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @Override
    public CompletionStage<Map<String, String>> load(Set<String> keys) {


        LOGGER.info("Batch load for {}", keys);
        return CompletableFuture.completedFuture(artworkService.batchGenerate(keys));
    }
}
