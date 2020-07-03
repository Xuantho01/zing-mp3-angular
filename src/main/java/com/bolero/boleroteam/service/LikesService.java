package com.bolero.boleroteam.service;

import com.bolero.boleroteam.model.Likes;
import com.bolero.boleroteam.model.PlayList;

import java.util.List;
import java.util.Optional;

public interface LikesService {
    List<Likes> findAll();
    Optional<Likes> findById(Long id);
    void save(Likes likes);
    void remove(Long id);
    List<Likes> findAllLikesById(Long id);
}
