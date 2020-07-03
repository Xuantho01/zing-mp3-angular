package com.bolero.boleroteam.service.impl;

import com.bolero.boleroteam.repository.SongRepository;
import com.bolero.boleroteam.model.Likes;
import com.bolero.boleroteam.repository.SongRepository;

import com.bolero.boleroteam.model.Song;

import com.bolero.boleroteam.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;

    @Override
    public List<Song> findAll() {
        return (List<Song>) songRepository.findAll();
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public void save(Song song) {
        songRepository.save(song);
    }

    @Override
    public void remove(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public List<Song> findByLyrics(String lyrics) {
        return songRepository.findByLyricsContaining(lyrics);
    }

    @Override
    public List<Song> findByName(String name) {
        return songRepository.findByNameContaining(name);
    }

//    @Override
//    public List<Song> findByDateSubmitted() {
//        return songRepository.findByOrderByDateSubmittedDesc();
//    }

    @Override
    public List<Song> find3LatestSong() {
        return songRepository.findTop6ByOrderByDateSubmittedDesc();
    }

    @Override
    public List<Song> findAllByOrderByViewsDesc() {
        return songRepository.findAllByOrderByViewsDesc();
    }

    @Override
    public List<Song> findAllSongByUserId(Long id) {
        return songRepository.findAllByUserId(id);
    }

    @Override
    public List<Song> findAllSongByLikes() {
        return songRepository.findTop1ByOrderByLikesDesc();
    }

    //    @Override
//    public List<Likes> findAllListLikeByUserId(Long id) {
//        return songRepository.findAllByUserId(id);
//    }
}
