package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;

import java.util.List;

public interface VoteService {
    public int addVoteRecord(Vote vote, int rsEventId);

    public List<Vote> getVoteRecord(int userId, int rsEventId);

}
