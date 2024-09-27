package com.netflix.lolomo;

import com.netflix.graphql.dgs.*;
import com.netflix.lolomo.codegen.types.SearchFilter;
import com.netflix.lolomo.codegen.types.Show;
import com.netflix.lolomo.codegen.types.ShowCategory;
import com.netflix.lolomo.data.ArtworkDataloader;
import com.netflix.lolomo.data.ArtworkService;
import com.netflix.lolomo.data.ShowsRepository;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@DgsComponent
public class LolomoDatafetcher {
    private final static Logger LOGGER = LoggerFactory.getLogger(LolomoDatafetcher.class);
    private final ShowsRepository showsRepository;
    private final ArtworkService artworkService;

    public LolomoDatafetcher(ShowsRepository showsRepository, ArtworkService artworkService) {
        this.showsRepository = showsRepository;
        this.artworkService = artworkService;
    }

    @DgsQuery
    public List<ShowCategory> lolomo() {
        return Stream.of("Top 10", "Continue Watching", "Horror", "Action", "Drama")
                .map(name -> ShowCategory.newBuilder().name(name).shows(showsRepository.showsForCategory(name)).build()).toList();
    }

//    @DgsData(parentType = "Show")
//    public String artworkUrl(DgsDataFetchingEnvironment dfe) {
//        Show show = dfe.getSourceOrThrow();
//        return artworkService.generateArtwork(show.getTitle());
//    }

    @DgsData(parentType = "Show")
    public CompletableFuture<String> artworkUrl(DgsDataFetchingEnvironment dfe) {

        Show show = dfe.getSourceOrThrow();
        LOGGER.info("Fetching artwork field for {}", show.getTitle());

        DataLoader<String, String> dataLoader = dfe.getDataLoader(ArtworkDataloader.class);
        return dataLoader.load(show.getTitle());


    }

    @DgsQuery
    public List<Show> search(@InputArgument SearchFilter filter) {
        return showsRepository.allShows().stream()
                .filter(s -> s.getTitle().toLowerCase().startsWith(filter.getTitle().toLowerCase()))
                .toList();
    }



    @DgsEntityFetcher(name = "Show")
    public Show show(Map<String, Object> values) {
        var showId = (Integer)values.get("showId");
        return showsRepository.byId(showId);
    }
}
