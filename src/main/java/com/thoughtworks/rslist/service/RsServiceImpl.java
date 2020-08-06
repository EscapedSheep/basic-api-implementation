package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RsServiceImpl implements RsService{

    private List<RsEvent> rsEventList;

    private UserService userService;

    @Autowired
    public RsServiceImpl(UserService userService) {
        this.userService = userService;
        rsEventList = new ArrayList<>();
        User user = new User("xiaowang", "female", 19, "18888888888", "a@thoughtworks.com");
        userService.registerUser(user);
        RsEvent testEvent1 = new RsEvent("第一条事件", "无标签", user);
        RsEvent testEvent2 = new RsEvent("第二条事件", "无标签", user);
        RsEvent testEvent3 = new RsEvent("第三条事件", "无标签", user);
        addRsEvent(testEvent1);
        addRsEvent(testEvent2);
        addRsEvent(testEvent3);
    }

    @Override
    public int addRsEvent(RsEvent rsEvent) {
        rsEventList.add(rsEvent);
        return rsEventList.size() -1;

    }

    @Override
    public List<RsEvent> getRsEventList() {
        return rsEventList;
    }

    @Override
    public RsEvent getRsEvent(int index) {
        return rsEventList.get(index - 1);
    }

    @Override
    public List<RsEvent> getRsEventBetween(int start, int end) {
        return rsEventList.subList(start-1, end);
    }

    @Override
    public void updateRsEvent(RsEvent rsEvent, int index) {
        RsEvent event = rsEventList.get(index - 1);
        String updateName = rsEvent.getEventName();
        String updateKeyWord = rsEvent.getKeyWord();
        if (updateName != null) {
            event.setEventName(rsEvent.getEventName());
        }
        if (updateKeyWord != null) {
            event.setKeyWord(rsEvent.getKeyWord());
        }
    }

    @Override
    public void deleteRsEvent(int index) {
        rsEventList.remove(index - 1);
    }

    @Override
    public int getRsNumber() {
        return rsEventList.size();
    }
}
