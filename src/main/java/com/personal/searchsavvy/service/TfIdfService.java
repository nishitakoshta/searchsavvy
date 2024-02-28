package com.personal.searchsavvy.service;
import java.util.List;
import java.util.Map;
public interface TfIdfService {
    Map<String, Double> calculateTFIDF(String string, List<String> stringList);
    void saveTFIDFScores(Map<String, Double> tfIdfScores, String fieldType, Integer storyId);
}
