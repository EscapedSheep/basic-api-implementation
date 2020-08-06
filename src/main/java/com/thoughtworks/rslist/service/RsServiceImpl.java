package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class RsServiceImpl implements RsService{

    private RsEventRepository rsEventRepository;

    private UserRepository userRepository;

    @Autowired
    public RsServiceImpl(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @Override
    public int addRsEvent(RsEvent rsEvent) {
        Optional<UserDto> userDto = userRepository.findById(rsEvent.getUserId());
        if (!userDto.isPresent()) {
            return -1;
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
        if(rsEventDto.isPresent()) {
            return rsEventDto.get().toRsEvent();
        }
        return null;
    }

    @Override
    public List<RsEvent> getRsEventBetween(int start, int end) {
        return rsEventRepository.findAllByIdBetween(start, end)
                .stream()
                .map(rsEventDto -> rsEventDto.toRsEvent())
                .collect(Collectors.toList());
    }

    @Override
    public int updateRsEvent(RsEvent rsEvent, int id) {
        Optional<RsEventDto> rsEventDto = rsEventRepository.findById(id);
        if (!rsEventDto.isPresent()) {
            return -1;
        }
        if (rsEventDto.get().getUserDto().getId() != rsEvent.getUserId()) {
            return -1;
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
        rsEventRepository.deleteById(id);
    }

    @Override
    public int getRsNumber() {
        return Integer.parseInt(String.valueOf(rsEventRepository.count()));
    }

    @Override
    public int getMaxId() {
        AtomicInteger maxId = new AtomicInteger(0);
        rsEventRepository.findAll().forEach(rsEventDto -> maxId.set(rsEventDto.getId() > maxId.get() ? rsEventDto.getId() : maxId.get()));
        return maxId.intValue();
    }
}
