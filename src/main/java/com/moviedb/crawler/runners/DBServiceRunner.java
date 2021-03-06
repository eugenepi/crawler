package com.moviedb.crawler.runners;

import com.moviedb.crawler.model.MovieDto;
import com.moviedb.crawler.services.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServiceRunner {

    private final DBService dbService;
    private final BlockingQueue<MovieDto> blockingQueue;

    private final static int BATCH_SIZE = 10;

    private static final Logger logger = Logger.getLogger(DBServiceRunner.class.getName());

    public DBServiceRunner(DBService dbService, BlockingQueue<MovieDto> blockingQueue) {
        this.dbService = dbService;
        this.blockingQueue = blockingQueue;
    }

    /**
     * Runs database service.
     * Create batches to save in database.
     *
     */
  public void run() {
        List<MovieDto> movieDtoList = new ArrayList<>();
        while (true) {
            try {
                MovieDto movieDto = blockingQueue.take();
                movieDtoList.add(movieDto);
                if (movieDtoList.size() >= BATCH_SIZE) {
                    dbService.save(movieDtoList);
                    movieDtoList.clear();
                }
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Error during processing movie. Reason is: ", e);
            }
        }
    }
}
