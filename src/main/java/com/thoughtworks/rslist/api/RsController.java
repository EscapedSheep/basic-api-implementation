package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList;

  public RsController() {
    initRsEventList();
  }

  public List<RsEvent> initRsEventList() {
    rsList = new ArrayList<>();
    rsList.add(new RsEvent("第一条事件", "无标签"));
    rsList.add(new RsEvent("第二条事件", "无标签"));
    rsList.add(new RsEvent("第三条事件", "无标签"));
    return rsList;
  }

  @GetMapping("/rs/{index}")
  public RsEvent getRsEvent(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null && end != null) {
      return rsList.subList(start - 1, end);
    }
    return rsList;
  }

  @PostMapping("/rs/event")
  public void addRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
  }

  @PutMapping("/rs/update/{index}")
  public void updateRsEvent(@RequestBody RsEvent rsEvent, @PathVariable int index) {
    RsEvent event = rsList.get(index - 1);
    String updateName = rsEvent.getEventName();
    String updateKeyWord = rsEvent.getKeyWord();
    if (updateName != null)
      event.setEventName(rsEvent.getEventName());
    if (updateKeyWord != null)
      event.setKeyWord(rsEvent.getKeyWord());
  }

  @DeleteMapping("rs/delete/{index}")
  public void deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
  }
}