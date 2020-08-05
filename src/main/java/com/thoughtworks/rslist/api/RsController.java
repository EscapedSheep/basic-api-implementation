package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.Exception.InvalidIndexException;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RsController {

  private RsService rsService;

  @Autowired
  public RsController(RsService rsService) {
    this.rsService = rsService;
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity getRsEvent(@PathVariable int index) {
    if (index < 1 || index > rsService.getRsNumber())
      throw new InvalidIndexException();
    return ResponseEntity.ok(rsService.getRsEvent(index ));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if (start != null && end != null) {
      return ResponseEntity.ok(rsService.getRsEventBetween(start, end));
    }
    return ResponseEntity.ok(rsService.getRsEventList());
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    rsService.addRsEvent(rsEvent);
    return ResponseEntity.created(null).build();
  }

  @PutMapping("/rs/{index}")
  public ResponseEntity updateEvent(@RequestBody RsEvent rsEvent, @PathVariable int index) {
    if (index < 1 || index > rsService.getRsNumber())
      throw new InvalidIndexException();
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("rs/{index}")
  public ResponseEntity deleteRsEvent(@PathVariable int index) {
    if (index < 1 || index > rsService.getRsNumber())
      throw new InvalidIndexException();
    rsService.deleteRsEvent(index);
    return ResponseEntity.ok(null);
  }
}