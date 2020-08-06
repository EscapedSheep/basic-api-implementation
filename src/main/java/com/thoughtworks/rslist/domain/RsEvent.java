package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RsEvent {
    @NotNull
    private String eventName;

    @NotNull
    private String keyWord;

    @NotNull
    private int userId;

    private int rsEventId;

    public RsEventDto toRsEventDto(UserDto userDto) {
        return RsEventDto.builder().eventName(eventName).keyWord(keyWord).userDto(userDto).build();
    }

    @JsonIgnore
    public int getRsEventId() {
        return rsEventId;
    }

    public void setRsEventId(int rsEventId) {
        this.rsEventId = rsEventId;
    }
}
