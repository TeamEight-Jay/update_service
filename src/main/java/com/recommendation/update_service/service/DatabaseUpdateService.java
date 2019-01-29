package com.recommendation.update_service.service;


import com.recommendation.update_service.entity.CategoryMappingEntity;
import com.recommendation.update_service.entity.UserMappingEntity;
import com.recommendation.update_service.repository.CategoryCorrelationRepository;
import com.recommendation.update_service.repository.UserCorrelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;

public class DatabaseUpdateService {


    private static final double CATEGORY_UPDATE_FACTOR = 0.1;
    private static final double USER_UPDATE_FACTOR = 0.2;

    @Autowired
    UserCorrelationRepository userCorrelationRepository;

    @Autowired
    CategoryCorrelationRepository categoryCorrelationRepository;


    @Autowired
    private HashMap<String,CategoryMappingEntity> surgeCategoryMapping;

    @Autowired
    private HashMap<String,UserMappingEntity> surgeUserMapping;

    public HashMap<String, CategoryMappingEntity> getSurgeCategoryMapping() {
        return surgeCategoryMapping;
    }

    public void setSurgeCategoryMapping(HashMap<String, CategoryMappingEntity> surgeCategoryMapping) {
        this.surgeCategoryMapping = surgeCategoryMapping;
    }

    public HashMap<String, UserMappingEntity> getSurgeUserMapping() {
        return surgeUserMapping;
    }

    public void setSurgeUserMapping(HashMap<String, UserMappingEntity> surgeUserMapping) {
        this.surgeUserMapping = surgeUserMapping;
    }

    @Scheduled(fixedDelay = 5000)
    public void databaseUpdate(){


        for(String userId: surgeCategoryMapping.keySet())
        {
            CategoryMappingEntity databaseMapping=categoryCorrelationRepository.findOne(userId);

            if(databaseMapping==null) continue;

            for(String categoryId: surgeCategoryMapping.get(userId).getCategories().keySet())
            {

                double updatedValue=surgeCategoryMapping.get(userId).getCategories().get(categoryId) +
                        ( CATEGORY_UPDATE_FACTOR * databaseMapping.getCategories().getOrDefault(categoryId,0.0) );
                surgeCategoryMapping.get(userId).getCategories().put(categoryId,updatedValue);
            }

        }

        for(String userId:surgeCategoryMapping.keySet())
        {
            categoryCorrelationRepository.save(surgeCategoryMapping.get(userId));
        }
    }
}
