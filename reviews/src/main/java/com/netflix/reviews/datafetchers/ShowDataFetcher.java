package com.netflix.reviews.datafetchers;

import com.netflix.graphql.dgs.*;
import com.netflix.reviews.types.Review;
import com.netflix.reviews.types.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@DgsComponent
public class ShowDataFetcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowDataFetcher.class);

    @DgsQuery
    public List<Review> recentReviews() {
        return List.of(
                new Review(5, "Great show", new Show(1, null)),
                new Review(1, "Not enough commercials", new Show(2, null)),
                new Review(3, "Too scary", new Show(3, null))
        );
    }

    @DgsData(parentType = "Show")
    public List<Review> reviews(DgsDataFetchingEnvironment dfe) {

        Show show = dfe.getSourceOrThrow();
        LOGGER.info("Get reviews for show {}", show.showId());

        return List.of(new Review(5, "Great show " + show.showId()));
    }


    @DgsEntityFetcher(name = "Show")
    public Show show(Map<String, Object> values) {
        var showId = (Integer)values.get("showId");
        return new Show(showId, null);
    }
}
