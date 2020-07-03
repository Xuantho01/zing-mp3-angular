package com.bolero.boleroteam.service.impl;

import com.bolero.boleroteam.repository.LikesRepository;
import com.bolero.boleroteam.model.Likes;
import com.bolero.boleroteam.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LikesServiceImpl implements LikesService {
    @Autowired
    private LikesRepository likesRepository;

    @Override
    public List<Likes> findAll() {
        return (List<Likes>) likesRepository.findAll();
    }

    @Override
    public Optional<Likes> findById(Long id) {
        return likesRepository.findById(id);
    }

    @Override
    public void save(Likes likes) {
        likesRepository.save(likes);
    }

    @Override
    public void remove(Long id) {
        likesRepository.deleteById(id);
    }

    @Override
    public List<Likes> findAllLikesById(Long id) {
        return likesRepository.findAllById(id);
    }
}
