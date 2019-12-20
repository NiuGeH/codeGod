package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationTopicRepository extends JpaSpecificationExecutor<OperationTopicEntity>, PagingAndSortingRepository<OperationTopicEntity, Long>, JpaRepository<OperationTopicEntity, Long> {

    OperationTopicEntity findByTopicName(String topicName);

    @Query("select max(te.topicOrder) from OperationTopicEntity te")
    Long findMaxTopicOrder();
}
