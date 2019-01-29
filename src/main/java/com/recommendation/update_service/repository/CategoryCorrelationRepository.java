package com.recommendation.update_service.repository;

import com.recommendation.update_service.entity.CategoryMappingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryCorrelationRepository extends MongoRepository<CategoryMappingEntity,String> {
}
