package com.bolero.boleroteam.controller;

import com.bolero.boleroteam.model.Likes;
import com.bolero.boleroteam.model.Song;
import com.bolero.boleroteam.service.LikesService;
import com.bolero.boleroteam.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class SongRestController {
    @Autowired
    private SongService songService;

    @Autowired
    private LikesService likesService;

    @PostMapping(value = "song/create",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSong(@RequestBody Song song){
        LocalDateTime now = LocalDateTime.now();
        song.setDateSubmitted(now);
        songService.save(song);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "song")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Song>> listSongs(){
        List<Song> songs;
        songs = songService.findAll();
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }

    @GetMapping(value = "song/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id){
        Optional<Song> song = songService.findById(id);
        System.out.println("ok");
        Song song1 = song.get();
        if (song1 == null){
            return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
        }else {
            song1.increment();
            songService.save(song1);
            return new ResponseEntity<Song>(song1,HttpStatus.OK);
        }
    }

    @PutMapping("song/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id,@RequestBody Song song){
        Optional<Song> song1 = songService.findById(id);
        Song song2 = song1.get();
        if (song2 == null){
            return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
        }else {
            song2.setName(song.getName());
            song2.setImage(song.getImage());
            song2.setLyrics(song.getLyrics());
            song2.setFileMp3(song.getFileMp3());
            song2.setSinger(song.getSinger());
            song2.setDateSubmitted(LocalDateTime.now());
            song2.setAuthor(song.getAuthor());
            song2.setStyle(song.getStyle());
            songService.save(song2);
            return new ResponseEntity<Song>(song2,HttpStatus.OK);
        }
    }

    @DeleteMapping("song/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id){
        Optional<Song> song = songService.findById(id);
        if (song == null){
            return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
        }else {
            songService.remove(id);
            return new ResponseEntity<Song>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("findByLyrics/{lyric}")
    public ResponseEntity<List<Song>> findByLyrics(@PathVariable("lyric") String lyrics){
        List<Song> songs = songService.findByLyrics(lyrics);
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }
    @GetMapping("findByName/{name}")
    public ResponseEntity<List<Song>> findByName(@PathVariable("name") String name){
        List<Song> songs = songService.findByName(name);
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }

    @GetMapping(value ="song/mylist/{id}")
    public ResponseEntity<List<Song>> findAllSongById(@PathVariable Long id){
        List<Song> songs = songService.findAllSongByUserId(id);
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }

    @GetMapping(value ="latest-song")
    public ResponseEntity<List<Song>> findLatestSong(){
        List<Song> songs = songService.find3LatestSong();
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }
    @GetMapping(value = "most-views-song") // API lấy danh sách bài hát nhiều lượt nghe nhất
    public ResponseEntity<List<Song>> findMostViewSong(){
        List<Song> songs = songService.findAllByOrderByViewsDesc();
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }

    @PutMapping(value = "like/{songId}")
    public ResponseEntity<Song> incrementLike(@RequestBody Likes likes,@PathVariable Long songId){
        Optional<Song> songOptional = songService.findById(songId);
        if(!songOptional.isPresent()){
            return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
        }
        Song song = songOptional.get();
        Long currentLikeId = -1L;
        int lastIndex = -1;
        List<Likes> likesList = song.getLikes();
        for(int i = 0; i < likesList.size(); i++) {
            if (likesList.get(i).getUser().getId().equals(likes.getUser().getId())){
                currentLikeId = likesList.get(i).getId();
                lastIndex = i;
                break;
            }
        }
        if(currentLikeId > 0) {
            likesService.remove(currentLikeId);
            likesList.remove(lastIndex);
            songService.save(song);
            return new ResponseEntity<Song>(song,HttpStatus.NO_CONTENT);
        }
        likesService.save(likes);
        likesList.add(likes);
        songService.save(song);
        return new ResponseEntity<Song>(song,HttpStatus.OK);
    }

    @GetMapping(value = "most-like-song")
    public ResponseEntity<List<Song>> get6SongHaveMostLike(){
        List<Song> songs = songService.findAllSongByLikes();
        if (songs.isEmpty()){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(songs,HttpStatus.OK);
        }
    }
}
