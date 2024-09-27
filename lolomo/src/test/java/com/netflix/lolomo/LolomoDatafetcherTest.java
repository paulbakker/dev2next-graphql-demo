package com.netflix.lolomo;

import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.test.EnableDgsTest;
import com.netflix.lolomo.data.ArtworkDataloader;
import com.netflix.lolomo.data.ArtworkService;
import com.netflix.lolomo.data.ShowsRepository;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {LolomoDatafetcher.class, ShowsRepository.class, ArtworkService.class})
@EnableDgsTest
class LolomoDatafetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void search() {
        @Language("GraphQL")
        var query = """
                query Search($title: String) {
                    search(filter: {title: $title}) {
                        title
                    }
                 }
                """;

        List<String> titles =  dgsQueryExecutor.executeAndExtractJsonPath(query, "data.search[*].title", Map.of("title", "the"));
        assertThat(titles).contains("The Witcher");

    }
}