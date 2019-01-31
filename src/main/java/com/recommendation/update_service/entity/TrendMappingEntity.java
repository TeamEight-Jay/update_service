package com.recommendation.update_service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "TrendMapping")
public class TrendMappingEntity {

    @Id
    private String trendId;

    private HashMap<String,Double> categories;

    public TrendMappingEntity() {
        this.trendId="trending";
        categories=new HashMap<String, Double>();
    }

    public String getTrendId() {
        return trendId;
    }

    public void setTrendId(String trendId) {
        this.trendId = trendId;
    }

    public HashMap<String, Double> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, Double> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "TrendMappingEntity{" +
                "trendId='" + trendId + '\'' +
                ", categories=" + categories +
                '}';
    }
}
