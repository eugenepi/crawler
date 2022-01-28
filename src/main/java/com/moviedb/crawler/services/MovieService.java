package com.moviedb.crawler.services;

import com.moviedb.crawler.model.MovieDto;

import java.util.List;

public interface MovieService {

    List<MovieDto> getMovieInfo(Integer page);
}
