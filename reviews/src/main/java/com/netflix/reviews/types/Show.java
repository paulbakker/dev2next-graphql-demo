package com.netflix.reviews.types;

import java.util.List;

public record Show(Integer showId, List<Review> reviews){
}
