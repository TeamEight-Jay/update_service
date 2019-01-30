package com.recommendation.update_service.service;

import com.recommendation.update_service.dto.UpdateMessage;
import com.recommendation.update_service.entity.CategoryMappingEntity;
import com.recommendation.update_service.entity.TrendMappingEntity;
import com.recommendation.update_service.entity.UserMappingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UpdateListenerService {

    @Autowired
    DatabaseUpdateService databaseUpdateService;

    @KafkaListener(topics = "UPDATE",group = "group_update",containerFactory = "updateKafkaListenerFactory")
    public void listenUpdate(UpdateMessage updateMessage)
    {
        if(updateMessage.getTarget().equals("CATEGORY"))
        {
            String userId=updateMessage.getRowId();
            String categoryId=updateMessage.getColumnId();
            double updateValue=updateMessage.getUpdateValue();
            double currentValue=0;
            if(databaseUpdateService.getSurgeUserMapping().containsKey(userId)) {
                databaseUpdateService.getSurgeCategoryMapping().get(userId).getCategories().getOrDefault(categoryId, 0.0);
            }
            else
            {
                databaseUpdateService.getSurgeCategoryMapping().put(userId,new CategoryMappingEntity(userId));
            }
            if(updateMessage.getUpdateUnit().equals("PERCENTAGE"))
            {
                updateValue=(currentValue)*(1+updateValue);
            }
            else
            {
                updateValue+=currentValue;
            }
            databaseUpdateService.getSurgeCategoryMapping().get(userId).getCategories().put(categoryId,updateValue);
        }
        else if(updateMessage.getTarget().equals("USER"))
        {
            String userId=updateMessage.getRowId();
            String otherUserId=updateMessage.getColumnId();
            double updateValue=updateMessage.getUpdateValue();
            double currentValue=0;
            if(databaseUpdateService.getSurgeUserMapping().containsKey(userId)) {
                databaseUpdateService.getSurgeUserMapping().get(userId).getMappedUsers().getOrDefault(otherUserId, 0.0);
            }
            else
            {
                databaseUpdateService.getSurgeUserMapping().put(userId,new UserMappingEntity(userId));
            }


            if(updateMessage.getUpdateUnit().equals("PERCENTAGE"))
            {
                updateValue=(currentValue)*(1+updateValue);
            }
            else
            {
                updateValue+=currentValue;
            }
            databaseUpdateService.getSurgeUserMapping().get(userId).getMappedUsers().put(otherUserId,updateValue);
        }

        else if(updateMessage.getTarget().equals("TRENDING"))
        {
            String userId=updateMessage.getRowId();
            String categoryId=updateMessage.getColumnId();
            double updateValue=updateMessage.getUpdateValue();
            TrendMappingEntity trend=databaseUpdateService.getTrendMapping();
            double currentValue=trend.getCategories().getOrDefault(categoryId,0.0);
            if(updateMessage.getUpdateUnit().equals("PERCENTAGE"))
            {
                updateValue=(currentValue)*(1+updateValue);
            }
            else
            {
                updateValue+=currentValue;
            }
            databaseUpdateService.getTrendMapping().getCategories().put(categoryId,updateValue);

        }
    }

}
