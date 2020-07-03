package com.bolero.boleroteam.controller;

import com.bolero.boleroteam.model.Style;
import com.bolero.boleroteam.service.StyleService;
import com.bolero.boleroteam.service.impl.FirebaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin("*")
public class StyleRestController {
    @Autowired
    private StyleService styleService;

    @Autowired
    FirebaseServiceImpl firebaseService;

    @PostMapping("createStyle")
    public String create(@RequestBody Style style) throws ExecutionException, InterruptedException {
        styleService.save(style);
        return firebaseService.saveStyle(style);
    }

    @GetMapping("style")
    public ResponseEntity<List<Style>> getStyleList(){
        List<Style> styleList = styleService.findAll();
        return new ResponseEntity<List<Style>>(styleList, HttpStatus.OK);
    }

    @PostMapping(value = "style/create",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStyle(@RequestBody Style style){
        styleService.save(style);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
