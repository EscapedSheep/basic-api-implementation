package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {
    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity voteForRsEvent(@PathVariable int rsEventId, @RequestBody Vote vote) {
        int id = voteService.addVoteRecord(vote, rsEventId);
        return ResponseEntity.created(null).header("id", String.valueOf(id)).build();
    }

    @GetMapping("/voteRecord")
    public ResponseEntity<List<Vote>> getRsRecord(@RequestParam int userId, @RequestParam int rsEventId) {
        return ResponseEntity.ok(voteService.getVoteRecord(userId,rsEventId));
    }
}
