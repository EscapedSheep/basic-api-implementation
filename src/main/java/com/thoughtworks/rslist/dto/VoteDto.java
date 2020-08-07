package com.thoughtworks.rslist.dto;

import com.thoughtworks.rslist.domain.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vote")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "rs_event_id")
    private RsEventDto rsEvent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDto user;

    @Builder.Default
    private LocalDateTime localDateTime = LocalDateTime.now();
    private int voteNum;

    public Vote toVote() {
        return Vote.builder().userId(user.getId()).localDateTime(localDateTime).voteNum(voteNum).rsEventId(rsEvent.getId()).build();
    }
}
