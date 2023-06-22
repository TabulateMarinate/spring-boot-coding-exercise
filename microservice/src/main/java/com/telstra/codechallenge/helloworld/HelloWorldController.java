package com.telstra.codechallenge.helloworld;

import java.util.concurrent.atomic.AtomicLong;

import com.telstra.codechallenge.service.HottestRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private HottestRepoService gitService;

  @GetMapping(path = "/hello")
  public HelloWorld hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new HelloWorld(counter.incrementAndGet(), String.format(TEMPLATE, name));
  }

}
