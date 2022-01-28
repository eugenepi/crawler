package com.moviedb.crawler;

import com.moviedb.crawler.model.MovieDto;
import com.moviedb.crawler.runners.DBServiceRunner;
import com.moviedb.crawler.runners.MovieServiceRunner;
import com.moviedb.crawler.services.DBService;
import com.moviedb.crawler.services.MovieService;
import com.moviedb.crawler.services.impl.DBServiceImpl;
import com.moviedb.crawler.services.impl.MovieServiceImpl;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieDBApplication {

    private static final Logger logger = Logger.getLogger(MovieDBApplication.class.getName());

  public static void main(String[] args) {
      logger.log(Level.INFO, "Application started");
      BlockingQueue<MovieDto> blockingQueue = new LinkedBlockingDeque<>();
      ExecutorService executorService = Executors.newSingleThreadExecutor();

      MovieService movieService = new MovieServiceImpl();
      MovieServiceRunner movieServiceRunner = new MovieServiceRunner(movieService, blockingQueue);
      executorService.submit(movieServiceRunner);

      String dbPath = System.getenv("DB_PATH");

      if (dbPath == null || "".equals(dbPath)) throw new RuntimeException("DB_PATH variable i absent");

      DB db = Base.open("org.sqlite.JDBC", "jdbc:sqlite:" + dbPath, null);
      DBService dbService = new DBServiceImpl(db);
      DBServiceRunner dbServiceRunner = new DBServiceRunner(dbService, blockingQueue);
      dbServiceRunner.run();
  }
}
