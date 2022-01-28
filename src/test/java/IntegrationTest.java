import com.moviedb.crawler.model.MovieDto;
import com.moviedb.crawler.runners.MovieServiceRunner;
import com.moviedb.crawler.services.DBService;
import com.moviedb.crawler.services.MovieService;
import com.moviedb.crawler.services.impl.DBServiceImpl;
import com.moviedb.crawler.services.impl.MovieServiceImpl;
import org.awaitility.Awaitility;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.*;

public class IntegrationTest {

    @Test
    public void moviesSuccessfullyProcessedTest() {
        BlockingQueue<MovieDto> blockingQueue = new LinkedBlockingDeque<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        MovieService movieService = new MovieServiceImpl();
        MovieServiceRunner movieServiceRunner = new MovieServiceRunner(movieService, blockingQueue);
        executorService.submit(movieServiceRunner);

        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .pollInterval(Duration.ofMillis(100))
                .until(() -> !blockingQueue.isEmpty());

        DB db = Base.open("org.sqlite.JDBC",
                "jdbc:sqlite:src/test/resources/movies_test.db", null);
        db.exec("delete from movies;");
        DBService dbService = new DBServiceImpl(db);

        dbService.save(new ArrayList<>(blockingQueue));

        Assertions.assertEquals(20, db.findAll("select * from movies").size());
    }
}
