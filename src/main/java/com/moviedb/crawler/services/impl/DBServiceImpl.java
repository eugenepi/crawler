package com.moviedb.crawler.services.impl;

import com.moviedb.crawler.model.MovieDto;
import com.moviedb.crawler.services.DBService;
import org.javalite.activejdbc.DB;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServiceImpl implements DBService {

    private final DB db;

    private static final Logger logger = Logger.getLogger(DBServiceImpl.class.getName());

    public DBServiceImpl(DB db) {
        this.db = db;
    }

    @Override
    public void save(List<MovieDto> movieDtoList) {
        PreparedStatement preparedStatement
                = db.startBatch("insert into movies(name,image) values(?,?)");
        for (MovieDto movieDto : movieDtoList) {
            db.addBatch(preparedStatement, movieDto.getName(), movieDto.getImage());
        }
        db.executeBatch(preparedStatement);
        logger.log(Level.INFO, "Batch successfully saved.");
    }
}
