package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @NotNull
    private int userId;

    private int rsEventId;

    @NotNull
    @Builder.Default
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Min(1)
    @NotNull
    private int voteNum;

    public VoteDto toVoteDto(RsEventDto rsEventDto, UserDto userDto) {
        return VoteDto.builder().rsEvent(rsEventDto).user(userDto).voteNum(voteNum).localDateTime(localDateTime).build();
    }

    @JsonIgnore
    public int getRsEventId() {
        return rsEventId;
    }

    @JsonProperty
    public void setRsEventId(int rsEventId) {
        this.rsEventId = rsEventId;
    }

    @JsonIgnore
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @JsonIgnore
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
