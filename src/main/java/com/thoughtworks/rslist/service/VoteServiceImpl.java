package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.Exception.VoteNumberOverThanOwnException;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {
    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private RsEventRepository rsEventRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }


    @Override
    public List<Vote> getVoteRecord(int userId, int rsEventId) {
        return voteRepository.findAllByUserIdAndRsEventId(userId, rsEventId)
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
}
