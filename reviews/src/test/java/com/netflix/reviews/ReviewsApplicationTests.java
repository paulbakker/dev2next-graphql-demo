package com.netflix.reviews;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.test.EnableDgsTest;
import com.netflix.reviews.datafetchers.ShowDataFetcher;
import com.netflix.reviews.types.Show;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ShowDataFetcher.class)
@EnableDgsTest
class ReviewsApplicationTests {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void federatedQuery() {

        @Language("GraphQL")
        var query =
                """        
          query($representations: [_Any!]!) {
          _entities(representations: $representations) {
            ... on Show {
              showId
              reviews {
                score
                text
              }
            }
          }
        }
        """;

        Map<String, Object> representations = Map.of("representations", List.of(Map.of("__typename", "Show", "showId", 1)));
        var shows = dgsQueryExecutor.executeAndExtractJsonPathAsObject(query, "data._entities[*]", representations, new TypeRef<List<Show>>() {
        });

        var show = shows.get(0);
        assertThat(show.showId()).isEqualTo(1);
        assertThat(show.reviews().get(0).score()).isEqualTo(5);

    }

}
