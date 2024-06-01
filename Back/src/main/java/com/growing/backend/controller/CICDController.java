package com.growing.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class CICDController {

    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${serverName}")
    private String serverName;

    @GetMapping("/change-test")
    public ResponseEntity<?> changeTest() {
        Map<String, String> responseDate = new TreeMap<>();
        responseDate.put("ServerName", serverName);
        responseDate.put("ServerPort", serverPort);
        responseDate.put("ServerEnv", env);
        responseDate.put("Port", serverPort);
        return ResponseEntity.ok(responseDate);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(env);
    }
}