package com.bolero.boleroteam.controller;

import com.bolero.boleroteam.model.Likes;
import com.bolero.boleroteam.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class LikesRestController {

    @Autowired
    private LikesService likesService;

    @GetMapping(value = "likes")
    public ResponseEntity<List<Likes>> getAllLikes(){
        List<Likes> likesList = likesService.findAll();
        return new ResponseEntity<List<Likes>>(likesList, HttpStatus.OK);
    }

    @GetMapping(value = "likes/{id}")
    public ResponseEntity<Likes> getLikesById(@PathVariable Long id){
        Optional<Likes> likes = likesService.findById(id);
        Likes likes1 = likes.get();
        if (likes1 == null){
            return new ResponseEntity<Likes>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Likes>(likes1,HttpStatus.OK);
        }
    }

    @PostMapping(value = "likes")
    public ResponseEntity<Void> incrementLikes(@RequestBody Likes likes){
        likesService.save(likes);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
