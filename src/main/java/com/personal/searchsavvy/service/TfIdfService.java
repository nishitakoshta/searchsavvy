package com.personal.searchsavvy.service;
import com.personal.searchsavvy.dto.PaginatedBlogResultDTO;

import java.util.List;
import java.util.Map;
public interface TfIdfService {
    Map<String, Double> calculateTFIDF(String string, List<String> stringList);
    void saveTFIDFScores(Map<String, Double> tfIdfScores, String fieldType, Integer storyId);
    PaginatedBlogResultDTO getMatchingBlog(String query, int pageIndex);
}
