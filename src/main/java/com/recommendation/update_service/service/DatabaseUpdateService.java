package com.recommendation.update_service.service;


import com.recommendation.update_service.entity.CategoryMappingEntity;
import com.recommendation.update_service.entity.TrendMappingEntity;
import com.recommendation.update_service.entity.UserMappingEntity;
import com.recommendation.update_service.repository.CategoryCorrelationRepository;
import com.recommendation.update_service.repository.TrendingCorrelationRepository;
import com.recommendation.update_service.repository.UserCorrelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DatabaseUpdateService {


    private static final double CATEGORY_UPDATE_FACTOR = 0.1;
    private static final double USER_UPDATE_FACTOR = 0.2;
    private static final double TREND_UPDATE_FACTOR = 0.2;

    @Autowired
    UserCorrelationRepository userCorrelationRepository;

    @Autowired
    CategoryCorrelationRepository categoryCorrelationRepository;

    @Autowired
    TrendingCorrelationRepository trendingCorrelationRepository;


    private HashMap<String,CategoryMappingEntity> surgeCategoryMapping=new HashMap<String,CategoryMappingEntity>();


    private HashMap<String,UserMappingEntity> surgeUserMapping=new HashMap<String,UserMappingEntity>();

    private TrendMappingEntity trendMapping=new TrendMappingEntity();


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

    public TrendMappingEntity getTrendMapping() {
        return trendMapping;
    }

    public void setTrendMapping(TrendMappingEntity trendMapping) {
        this.trendMapping = trendMapping;
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

            for(String dbCategoryId: databaseMapping.getCategories().keySet())
            {
                if(!surgeCategoryMapping.get(userId).getCategories().containsKey(dbCategoryId))
                {
                    surgeCategoryMapping.get(userId).getCategories().put(dbCategoryId,databaseMapping.getCategories().get(dbCategoryId));
                }
            }

        }


        for(String userId:surgeCategoryMapping.keySet())
        {
            categoryCorrelationRepository.save(surgeCategoryMapping.get(userId));
        }


        for(String userId: surgeUserMapping.keySet())
        {
            UserMappingEntity databaseUserMapping=userCorrelationRepository.findOne(userId);

            if(databaseUserMapping==null) continue;

            for(String followedUserId : surgeUserMapping.get(userId).getMappedUsers().keySet())
            {
                double updatedValue=surgeUserMapping.get(userId).getMappedUsers().get(followedUserId) +
                        (USER_UPDATE_FACTOR * databaseUserMapping.getMappedUsers().getOrDefault(followedUserId,0.0));
                surgeUserMapping.get(userId).getMappedUsers().put(followedUserId,updatedValue);
            }

            for(String dbUserId: databaseUserMapping.getMappedUsers().keySet())
            {
                if(!surgeUserMapping.get(userId).getMappedUsers().containsKey(dbUserId))
                {
                    surgeUserMapping.get(userId).getMappedUsers().put(dbUserId,databaseUserMapping.getMappedUsers().get(dbUserId));
                }
            }

        }
        for(String userId:surgeUserMapping.keySet())
        {
            userCorrelationRepository.save(surgeUserMapping.get(userId));
        }


        TrendMappingEntity databaseTrending=trendingCorrelationRepository.findOne(trendMapping.getTrendId());

        if(databaseTrending!=null)
        {
            for(String categoryId:trendMapping.getCategories().keySet())
            {
                double currentDBValue=databaseTrending.getCategories().getOrDefault(categoryId,0.0);
                currentDBValue+=trendMapping.getCategories().get(categoryId);
                databaseTrending.getCategories().put(categoryId,currentDBValue);
            }
            trendingCorrelationRepository.save(databaseTrending);
        }
        else
        {
            trendingCorrelationRepository.save(trendMapping);
        }

        surgeCategoryMapping.clear();
        surgeUserMapping.clear();
        trendMapping.getCategories().clear();

    }

}
