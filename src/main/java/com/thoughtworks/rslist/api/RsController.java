package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RsController {

  private RsService rsService;

  @Autowired
  public RsController(RsService rsService) {
    this.rsService = rsService;
  }

  @GetMapping("/rs/{id}")
  public ResponseEntity getRsEvent(@PathVariable int id) {
    RsEvent rsEvent = rsService.getRsEvent(id);
    return ResponseEntity.ok(rsEvent);
  }

  @GetMapping("/rs/list")
  public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null && end != null) {
      return ResponseEntity.ok(rsService.getRsEventBetween(start, end));
    }
    return ResponseEntity.ok(rsService.getRsEventList());
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    int id = rsService.addRsEvent(rsEvent);
    return ResponseEntity.created(null).header("id", String.valueOf(id)).build();
  }

  @PutMapping("/rs/{id}")
  public ResponseEntity updateEvent(@RequestBody RsEvent rsEvent, @PathVariable int id) {
    rsService.updateRsEvent(rsEvent, id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("rs/{id}")
  public ResponseEntity deleteRsEvent(@PathVariable int id) {
    rsService.deleteRsEvent(id);
    return ResponseEntity.ok().build();
  }
}