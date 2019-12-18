package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationSubtopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OperationSubtopicRepository extends JpaSpecificationExecutor<OperationSubtopicEntity>, PagingAndSortingRepository<OperationSubtopicEntity, Long>, JpaRepository<OperationSubtopicEntity, Long> {

    @Query("select se from OperationSubtopicEntity se where se.subtopicName=:subtopicName and se.topic.id=:topicId")
    OperationSubtopicEntity findBySubtopicNameAndTopicId(@Param("subtopicName") String subtopicName, @Param("topicId") Long topicId);
}
