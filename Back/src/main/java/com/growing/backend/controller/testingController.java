package com.growing.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class testingController {


    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${serverName}")
    private String serverName;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        Map<String, String> responseDate = new TreeMap<>();
        responseDate.put("ServerName", serverName);
        responseDate.put("ServerPort", serverPort);
        responseDate.put("ServerEnv", env);
        responseDate.put("sss", env);

        return ResponseEntity.ok(responseDate);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(env);
    }
}