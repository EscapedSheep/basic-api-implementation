package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.Exception.InvalidIndexException;
import com.thoughtworks.rslist.Exception.InvalidRequestParamException;
import com.thoughtworks.rslist.Exception.UserNotExistedException;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RsServiceImpl implements RsService{

    @Autowired
    private RsEventRepository rsEventRepository;

    @Autowired
    private UserRepository userRepository;
/*
    @Autowired
    public RsServiceImpl(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

 */

    @Override
    public int addRsEvent(RsEvent rsEvent) {
        Optional<UserDto> userDto = userRepository.findById(rsEvent.getUserId());
        if (!userDto.isPresent()) {
            throw new InvalidIndexException();
        }
        RsEventDto rsEventDto = rsEventRepository.save(rsEvent.toRsEventDto(userDto.get()));
        return rsEventDto.getId();
    }

    @Override
    public List<RsEvent> getRsEventList() {
        return rsEventRepository.findAll().stream().map(rsEventDto -> rsEventDto.toRsEvent()).collect(Collectors.toList());
    }

    @Override
    public RsEvent getRsEvent(int id) {
        Optional<RsEventDto> rsEventDto = rsEventRepository.findById(id);
        if(!rsEventDto.isPresent()) {
            throw new InvalidIndexException();
        }
        return rsEventDto.get().toRsEvent();
    }

    @Override
    public List<RsEvent> getRsEventBetween(int start, int end) {
        if (start < 1 || end < 1 || end < start) {
            throw new InvalidRequestParamException();
        }
        return rsEventRepository.findAllByIdBetween(start, end)
                .stream()
                .map(rsEventDto -> rsEventDto.toRsEvent())
                .collect(Collectors.toList());
    }

    @Override
    public int updateRsEvent(RsEvent rsEvent, int id) {
        Optional<RsEventDto> rsEventDto = rsEventRepository.findById(id);
        if (!rsEventDto.isPresent()) {
            throw new InvalidIndexException();
        }
        if (rsEventDto.get().getUserDto().getId() != rsEvent.getUserId()) {
            throw new UserNotExistedException();
        }

        String updateName = rsEvent.getEventName();
        String updateKeyWord = rsEvent.getKeyWord();
        int affectRow = 0;
        if (updateName != null) {
            affectRow += rsEventRepository.modifyEventNameById(id, updateName);
        }
        if (updateKeyWord != null) {
            affectRow += rsEventRepository.modifyKeyWordById(id, updateKeyWord);
        }
        return affectRow;
    }

    @Override
    public void deleteRsEvent(int id) {
        if (!rsEventRepository.findById(id).isPresent()) {
            throw new InvalidIndexException();
        }
        rsEventRepository.deleteById(id);
    }

    @Override
    public int getRsNumber() {
        return Integer.parseInt(String.valueOf(rsEventRepository.count()));
    }
}
