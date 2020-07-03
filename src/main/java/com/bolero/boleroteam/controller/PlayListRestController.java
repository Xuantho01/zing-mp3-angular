package com.bolero.boleroteam.controller;

import com.bolero.boleroteam.model.PlayList;
import com.bolero.boleroteam.model.Song;
import com.bolero.boleroteam.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class PlayListRestController {
    @Autowired
    private PlayListService playListService;

    @GetMapping(value = "album")
    public ResponseEntity<List<PlayList>> getAllAlbum(){
        List<PlayList> playLists = playListService.findAll();
        if (playLists == null){
            return new ResponseEntity<List<PlayList>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<PlayList>>(playLists,HttpStatus.OK);
        }
    }

    @GetMapping(value = "album/{id}")
    public ResponseEntity<PlayList> getAlbum(@PathVariable Long id){
        Optional<PlayList> playList = playListService.findById(id);
        PlayList album = playList.get();
        if (album == null){
            return new ResponseEntity<PlayList>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<PlayList>(album,HttpStatus.OK);
        }
    }

    @GetMapping(value = "album/song/{id}")
    public ResponseEntity<List<Song>> getAllSongByAlbumId(@PathVariable Long id){
        List<Song> album = playListService.findAllSongByAlbumId(id);
        if (album == null){
            return new ResponseEntity<List<Song>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<Song>>(album,HttpStatus.OK);
        }
    }

    @GetMapping(value = "album/user/{id}")
    public ResponseEntity<List<PlayList>> getAlbumByUserId(@PathVariable Long id){
        List<PlayList> album = playListService.findAllAlbumByUserId(id);
        if (album == null){
            return new ResponseEntity<List<PlayList>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<List<PlayList>>(album,HttpStatus.OK);
        }
    }

    @PostMapping(value = "album/create")
    public ResponseEntity<Void> createAlbum(@RequestBody PlayList playList){
        if (playList.getId() == null){
            playListService.save(playList);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
        }else {
            Optional<PlayList> playList1 = playListService.findById(playList.getId());
            PlayList album = playList1.get();
            List<Song> songList = playList.getSongList();
            for (Song song: album.getSongList()) {
                songList.add(song);
            }
            album.setSongList(songList);
            playListService.save(album);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
        }

    }

    @PutMapping(value = "album/{id}")
    public ResponseEntity<PlayList> updateAlbum(@PathVariable Long id, @RequestBody PlayList playList){
        Optional<PlayList> album = playListService.findById(id);
        PlayList playList1 = album.get();
        if (playList1 == null){
            return new ResponseEntity<PlayList>(HttpStatus.NOT_FOUND);
        }else {
            playList1.setName(playList.getName());
//            playList1.setSongList(playList.getSongList());
            playListService.save(playList1);
            return new ResponseEntity<PlayList>(playList1,HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "album/{id}")
    public ResponseEntity<PlayList> deleteAlbum(@PathVariable Long id){
        Optional<PlayList> playList = playListService.findById(id);
        if (playList == null){
            return new ResponseEntity<PlayList>(HttpStatus.NOT_FOUND);
        }else {
            playListService.remove(id);
            return new ResponseEntity<PlayList>(HttpStatus.OK);
        }
    }
}
