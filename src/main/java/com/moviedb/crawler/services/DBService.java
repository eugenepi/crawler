package com.moviedb.crawler.services;

import com.moviedb.crawler.model.MovieDto;

import java.util.List;

public interface DBService {

    void save(List<MovieDto> movieDtoList);
}
