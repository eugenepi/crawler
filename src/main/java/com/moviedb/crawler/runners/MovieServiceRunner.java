package com.moviedb.crawler.runners;

import com.moviedb.crawler.model.MovieDto;
import com.moviedb.crawler.services.MovieService;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieServiceRunner implements Runnable{

    private final MovieService movieService;
    private final BlockingQueue<MovieDto> blockingQueue;
    private static final long RATE = 30000; //every 30 seconds
    private static final Logger logger = Logger.getLogger(MovieServiceRunner.class.getName());

    public MovieServiceRunner(MovieService movieService, BlockingQueue<MovieDto> blockingQueue) {
        this.movieService = movieService;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Starting movie runner service");
        int page = 1;
        boolean isRunning = true;
        while (isRunning) {
            List<MovieDto> movieDtoList = this.movieService.getMovieInfo(page);
            if (movieDtoList.isEmpty()) {
                isRunning = false;
            } else {
                this.blockingQueue.addAll(movieDtoList);
            }
            logger.log(Level.INFO, "Processed page = " + page);
            page++;
            try {
                Thread.sleep(RATE);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "InterruptedException", e);
            }
        }
    }
}
