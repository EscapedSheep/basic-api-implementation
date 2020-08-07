package com.thoughtworks.rslist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Exception.VoteNumberOverThanOwnException;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    private final int pageSize = 5;


    @Override
    public List<Vote> getVoteRecord(int userId, int rsEventId, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return voteRepository.findAllByUserIdAndRsEventId(userId, rsEventId, pageable)
                .stream()
                .map(voteDto -> voteDto.toVote())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int addVoteRecord(Vote vote, int rsEventId) {
        UserDto userDto= userRepository.findById(vote.getUserId()).get();
        RsEventDto rsEventDto = rsEventRepository.findById(rsEventId).get();
        if (userDto.getVotes() < vote.getVoteNum()) {
            throw new VoteNumberOverThanOwnException();
        }
        int voteNum = rsEventDto.getVoteNum() + vote.getVoteNum();
        rsEventRepository.modifyVoteById(rsEventId, voteNum);
        VoteDto voteDtoSaved = voteRepository.save(vote.toVoteDto(rsEventDto, userDto));
        return voteDtoSaved.getId();
    }

    @Override
    public List<Vote> getVoteBetween(String start, String end) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start,dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);
        return voteRepository.findAllByLocalDateTimeBetween(startTime, endTime)
                .stream()
                .map(voteDto -> voteDto.toVote())
                .collect(Collectors.toList());
    }
}
