package com.recommendation.update_service.repository;

import com.recommendation.update_service.entity.CategoryMappingEntity;
import com.recommendation.update_service.entity.TrendMappingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrendingCorrelationRepository extends MongoRepository<TrendMappingEntity, String> {
}
