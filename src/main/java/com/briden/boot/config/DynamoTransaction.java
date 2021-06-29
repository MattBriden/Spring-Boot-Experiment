package com.briden.boot.config;

import com.briden.boot.entity.dynamo.DynamoEntry;
import com.briden.boot.repository.dynamo.DynamoService;
import com.briden.boot.resources.DataResource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamoTransaction {

    @Autowired
    private DynamoService dynamoEntryRepository;

    @Pointcut("@annotation(com.briden.boot.config.DynamoTransactional)")
    public void annotatedMethods() {
    }

    @Around("annotatedMethods()")
    public Object invoke(final ProceedingJoinPoint pjp) throws Throwable {
        DynamoEntry entry = null;
        DynamoEntry oldDynamoEntry = new DynamoEntry();
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof DataResource) {
                DataResource resource = (DataResource) arg;
                oldDynamoEntry.setId(resource.getId());
                entry = dynamoEntryRepository.getItemFromDynamo(oldDynamoEntry);
                break;
            }
        }
        try {
            pjp.proceed();
        } catch (Exception e) {
            if (entry == null && oldDynamoEntry.getId() != null) {
                dynamoEntryRepository.deleteItemFromDynamo(oldDynamoEntry);
            } else if (entry != null) {
                dynamoEntryRepository.saveToDynamo(entry);
            }
            throw e;
        }
        return null;
    }
}
