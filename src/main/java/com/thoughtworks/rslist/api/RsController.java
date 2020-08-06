package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.Exception.InvalidIndexException;
import com.thoughtworks.rslist.Exception.InvalidRequestParamException;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class RsController {

  private RsService rsService;

  @Autowired
  public RsController(RsService rsService) {
    this.rsService = rsService;
  }

  @GetMapping("/rs/{id}")
  public ResponseEntity getRsEvent(@PathVariable int id) {
    RsEvent rsEvent = rsService.getRsEvent(id);
    if (rsEvent == null) {
      throw new InvalidIndexException();
    }
    return ResponseEntity.ok(rsEvent);
  }

  @GetMapping("/rs/list")
  public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null && end != null) {
      if (start < 1 || end > rsService.getMaxId()) {
        throw new InvalidRequestParamException();
      }
      return ResponseEntity.ok(rsService.getRsEventBetween(start, end));
    }
    return ResponseEntity.ok(rsService.getRsEventList());
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody RsEvent rsEvent) {
    int id = rsService.addRsEvent(rsEvent);
    if (id == -1) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.created(null).header("id", String.valueOf(id)).build();
  }

  @PutMapping("/rs/{id}")
  public ResponseEntity updateEvent(@RequestBody RsEvent rsEvent, @PathVariable int id) {
    int affectRow = rsService.updateRsEvent(rsEvent,id);
    if (affectRow < 1) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("rs/{index}")
  public ResponseEntity deleteRsEvent(@PathVariable int index) {
    if (index < 1 || index > rsService.getRsNumber()) {
      throw new InvalidIndexException();
    }
    rsService.deleteRsEvent(index);
    return ResponseEntity.ok(null);
  }
}