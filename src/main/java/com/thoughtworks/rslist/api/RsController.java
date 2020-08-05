package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {

  private List<RsEvent> rsList = initRsEventList();

  private List<RsEvent> initRsEventList() {
    User user = new User("xiaowang", "female", 19, "18888888888", "a@thoughtworks.com");
    rsList = new ArrayList<>();
    rsList.add(new RsEvent("第一条事件", "无标签", user));
    rsList.add(new RsEvent("第二条事件", "无标签", user));
    rsList.add(new RsEvent("第三条事件", "无标签", user));
    return rsList;
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity getRsEvent(@PathVariable int index) {
    return ResponseEntity.ok(rsList.get(index - 1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null && end != null) {
      return ResponseEntity.ok(rsList.subList(start - 1, end));
    }
    return ResponseEntity.ok(rsList);
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    rsList.add(rsEvent);
    return ResponseEntity.created(null).build();
  }

  @PutMapping("/rs/{index}")
  public void updateRsEvent(@RequestBody RsEvent rsEvent, @PathVariable int index) {
    RsEvent event = rsList.get(index - 1);
    String updateName = rsEvent.getEventName();
    String updateKeyWord = rsEvent.getKeyWord();
    if (updateName != null)
      event.setEventName(rsEvent.getEventName());
    if (updateKeyWord != null)
      event.setKeyWord(rsEvent.getKeyWord());
  }

  @DeleteMapping("rs/{index}")
  public void deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
  }
}