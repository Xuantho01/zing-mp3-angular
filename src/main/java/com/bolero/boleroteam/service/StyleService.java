package com.bolero.boleroteam.service;

import com.bolero.boleroteam.model.Style;

import java.util.List;
import java.util.Optional;

public interface StyleService {
    List<Style> findAll();
    Optional<Style> findById(Long id);
    void save(Style style);
    void remove(Long id);
}
