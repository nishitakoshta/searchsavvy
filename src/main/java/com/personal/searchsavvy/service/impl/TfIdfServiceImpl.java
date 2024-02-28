package com.personal.searchsavvy.service.impl;
import com.personal.searchsavvy.entity.TfIdfScores;
import com.personal.searchsavvy.repository.BlogRepository;
import com.personal.searchsavvy.repository.TfIdfRepository;
import com.personal.searchsavvy.service.TfIdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class TfIdfServiceImpl implements TfIdfService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TfIdfRepository tfIdfRepository;
    private final Map<String, Map<String, Double>> tfIdfScores;
    public TfIdfServiceImpl(List<String> stringList) {
        tfIdfScores = new HashMap<>();
        for (String tag : stringList) {
            Map<String, Double> tagTfIdfScores = calculateTFIDF(tag, stringList);
            tfIdfScores.put(tag, tagTfIdfScores);
        }
    }
    private static Map<String, Double> calculateTF(String tag) {
        Map<String, Double> tfMap = new HashMap<>();
        // Check if tag is null
        if (tag != null) {
            String[] words = tag.split(" ");
            int totalWords = 0; // Updated to count non-empty and non-null words
            // Count the occurrence of each unique non-empty and non-null word
            for (String word : words) {
                if (!word.trim().isEmpty() && !word.equalsIgnoreCase("null")) {
                    tfMap.put(word, tfMap.getOrDefault(word, 0.0) + 1.0);
                    totalWords++;
                }
            }
            if (totalWords > 0) {
                // Calculate TF for each word
                for (Map.Entry<String, Double> entry : tfMap.entrySet()) {
                    double tf = entry.getValue() / totalWords;
                    tfMap.put(entry.getKey(), tf);
                }
            }
        }
        return tfMap;
    }
    private static double calculateIDF(List<String> stringList, String termToCheck) {
        int documentFrequency = 0;
        for (String string : stringList) {
            if (string != null && string.contains(termToCheck)) {
                documentFrequency++;
            }
        }
        double idf = Math.log10((double) stringList.size() / (documentFrequency + 1)); // Adding 1 to avoid division by zero
        idf = Math.abs(idf);
        return idf;
    }
    @Override
    public Map<String, Double> calculateTFIDF(String string, List<String> stringList) {
        Map<String, Double> tfIdfScore = new HashMap<>();
        Map<String, Double> tfMap = calculateTF(string); // Calculate TF for the current string
        for (String word : tfMap.keySet()) {
            double tf = tfMap.get(word);
            double idf = calculateIDF(stringList, word); // Calculate IDF for the current word
            double tfIdf = tf * idf;
            tfIdfScore.put(word, tfIdf);
        }
        return tfIdfScore;
    }
    @Override
    public void saveTFIDFScores(Map<String, Double> tfIdfScores, String fieldType, Integer storyId) {
        try{
            for (Map.Entry<String, Double> entry : tfIdfScores.entrySet()) {
                String term = entry.getKey();
                Double tfIdfScore = entry.getValue();
                // Check for Infinity and set a specific value instead
                if (Double.isInfinite(tfIdfScore)) {
                    tfIdfScore = Double.MAX_VALUE;
                }
                List<Object[]> result = blogRepository.checkTermExistence(term, fieldType, storyId);
                if (!result.isEmpty()) {
                    Object[] row = result.get(0);
                    TfIdfScores existScore = createTFIDFScoreFromRow(row);
                    existScore.setBlogId(storyId);
                    existScore.setScore(tfIdfScore);
                    tfIdfRepository.save(existScore);
                }else {
                    TfIdfScores tfidfScoreEntity = new TfIdfScores();
                    tfidfScoreEntity.setTerm(term);
                    tfidfScoreEntity.setScore(tfIdfScore);
                    tfidfScoreEntity.setFieldType(fieldType);
                    tfidfScoreEntity.setBlogId(storyId);
                    tfIdfRepository.save(tfidfScoreEntity);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public TfIdfScores createTFIDFScoreFromRow(Object[] row) {
        TfIdfScores tfidfScore = new TfIdfScores();
        tfidfScore.setId((Integer) row[0]);
        tfidfScore.setFieldType((String) row[2]);
        tfidfScore.setScore((Double) row[3]);
        tfidfScore.setTerm((String) row[4]);
        return tfidfScore;
    }
}
