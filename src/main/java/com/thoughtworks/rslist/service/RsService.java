package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;

import java.util.List;

public interface RsService {

    public int addRsEvent(RsEvent rsEvent);

    public List<RsEvent> getRsEventList();

    public RsEvent getRsEvent(int id);

    public List<RsEvent> getRsEventBetween(int start, int end);

    public int updateRsEvent(RsEvent rsEvent, int id);

    public void deleteRsEvent(int id);

    public int getRsNumber();

    public int getMaxId();
}
