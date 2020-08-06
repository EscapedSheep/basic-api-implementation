package com.thoughtworks.rslist.dto;

import com.thoughtworks.rslist.domain.RsEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rsEvent")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RsEventDto {
    @Id
    @GeneratedValue
    private int id;

    private String eventName;
    private String keyWord;

    @ManyToOne
    private UserDto userDto;

    public RsEvent toRsEvent() {
        return RsEvent.builder().eventName(eventName).keyWord(keyWord).userId(userDto.getId()).rsEventId(id).build();
    }
}
