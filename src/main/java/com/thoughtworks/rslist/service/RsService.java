package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;

import java.util.List;

public interface RsService {

    public int addRsEvent(RsEvent rsEvent);

    public List<RsEvent> getRsEventList();

    public RsEvent getRsEvent(int index);

    public List<RsEvent> getRsEventBetween(int start, int end);

    public void updateRsEvent(RsEvent rsEvent, int index);

    public void deleteRsEvent(int index);

    public int getRsNumber();

}
