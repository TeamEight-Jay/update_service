package com.recommendation.update_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "CategoryMapping")
public class CategoryMappingEntity {

    @Id
    private String userId;

    private HashMap<String,Double> categories;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Double> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, Double> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CategoryMappingEntity{" +
                "userId='" + userId + '\'' +
                ", categories=" + categories +
                '}';
    }
}
