package com.mewhz.generator.controller;

import com.mewhz.generator.model.DatabaseConfig;
import com.mewhz.generator.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/generator")
@RestController
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    @PostMapping("/test-connection")
    public List<String> testConnection(@RequestBody DatabaseConfig config) {
        return generatorService.testConnection(config);
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateCode(@RequestBody DatabaseConfig config) {
        byte[] zipFile = generatorService.generateCode(config);

        return new ResponseEntity<>(zipFile, HttpStatus.OK);
    }

}
