package com.recommendation.update_service.repository;

import com.recommendation.update_service.entity.UserMappingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCorrelationRepository extends MongoRepository<UserMappingEntity,String> {
}
