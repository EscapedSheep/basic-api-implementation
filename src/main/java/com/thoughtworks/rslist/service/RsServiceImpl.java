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

    private UserServiceImpl userServiceImpl;

    @Autowired
    public RsServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        rsEventList = new ArrayList<>();
        User user = new User("xiaowang", "female", 19, "18888888888", "a@thoughtworks.com");
        userServiceImpl.registerUser(user);
        RsEvent event1 = new RsEvent("第一条事件", "无标签", user);
        RsEvent event2 = new RsEvent("第二条事件", "无标签", user);
        RsEvent event3 = new RsEvent("第三条事件", "无标签", user);
        addRsEvent(event1);
        addRsEvent(event2);
        addRsEvent(event3);
    }

    @Override
    public void addRsEvent(RsEvent rsEvent) {
        rsEventList.add(rsEvent);

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
        if (updateName != null)
            event.setEventName(rsEvent.getEventName());
        if (updateKeyWord != null)
            event.setKeyWord(rsEvent.getKeyWord());
    }

    @Override
    public void deleteRsEvent(int index) {
        rsEventList.remove(index - 1);
    }
}
