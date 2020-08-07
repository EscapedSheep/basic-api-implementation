package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RsEventRepository extends CrudRepository<RsEventDto, Integer> {
    @Override
    List<RsEventDto> findAll();

    @Transactional
    @Modifying
    @Query(value = "update RsEventDto r set r.eventName = :eventName where r.id = :id")
    int modifyEventNameById(int id, String eventName);

    @Transactional
    @Modifying
    @Query(value = "update RsEventDto r set r.keyWord = :keyWord where r.id = :id")
    int modifyKeyWordById(int id, String keyWord);

    List<RsEventDto> findAllByIdBetween(int start, int end);

    @Transactional
    @Modifying
    @Query(value = "update RsEventDto r set r.voteNum = :voteNum where r.id = :id")
    void modifyVoteById(int id, int voteNum);
}
