package com.bolero.boleroteam.service.impl;

import com.bolero.boleroteam.model.Song;
import com.bolero.boleroteam.repository.PlayListRepository;
import com.bolero.boleroteam.model.PlayList;
import com.bolero.boleroteam.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PlayListServiceImpl implements PlayListService {
    @Autowired
    private PlayListRepository playListRepository;

    @Override
    public List<PlayList> findAll() {
        return (List<PlayList>) playListRepository.findAll();
    }

    @Override
    public Optional<PlayList> findById(Long id) {
        return playListRepository.findById(id);
    }

    @Override
    public void save(PlayList playList) {
        playListRepository.save(playList);
    }

    @Override
    public void remove(Long id) {
        playListRepository.deleteById(id);
    }

    @Override
    public List<PlayList> findAllAlbumByUserId(Long id) {
        return playListRepository.findAllByUserId(id);
    }

    @Override
    public List<Song> findAllSongByAlbumId(Long id) {
        return playListRepository.findAllById(id);
    }
}
