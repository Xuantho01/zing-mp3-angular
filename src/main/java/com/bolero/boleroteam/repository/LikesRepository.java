package com.bolero.boleroteam.repository;

import com.bolero.boleroteam.model.Likes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikesRepository extends CrudRepository<Likes, Long> {
    List<Likes> findAllById(Long id);
}
