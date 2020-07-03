package com.bolero.boleroteam.repository;

import com.bolero.boleroteam.model.PlayList;
import com.bolero.boleroteam.model.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayListRepository extends CrudRepository<PlayList, Long> {
    List<PlayList> findAllByUserId(Long id);
    List<Song> findAllById(Long id);
}
