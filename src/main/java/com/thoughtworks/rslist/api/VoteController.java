package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<Vote>> getRsRecord(@RequestParam(required = false) Integer userId,
                                                  @RequestParam(required = false) Integer rsEventId,
                                                  @RequestParam(required = false) Integer pageIndex,
                                                  @RequestParam(required = false) String start,
                                                  @RequestParam(required = false) String end) {
        List<Vote> result = new ArrayList<>();
        if (userId != null && rsEventId != null && pageIndex != null) {
            result = voteService.getVoteRecord(userId, rsEventId, pageIndex);
        }
        if (start != null && end != null) {
            result = voteService.getVoteBetween(start, end);
        }
        return ResponseEntity.ok(result);
    }
}
