package com.moviedb.crawler.services.impl;

import com.moviedb.crawler.model.MovieDto;
import com.moviedb.crawler.services.MovieService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieServiceImpl implements MovieService {

    private static final String URL = "https://www.themoviedb.org";

    private static final Logger logger = Logger.getLogger(MovieServiceImpl.class.getName());

    @Override
    public List<MovieDto> getMovieInfo(Integer page) {
        Document document = null;
        List<MovieDto> movieDtos = new ArrayList<>();
        try {
            document = Jsoup.connect("https://www.themoviedb.org/movie?page=" + page).get();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error during connection to page. Reason: ", e);
            return Collections.emptyList();
        }

        Elements movieCards = document.select("div[class='card style_1']");

        for (Element element : movieCards) {
            String name = getName(element);
            byte[] image = getImage(element);
            MovieDto movieDto = new MovieDto(name, image);
            movieDtos.add(movieDto);
        }

        return movieDtos;
    }

    private String getName(Element element) {
       return element.getElementsByClass("wrapper").get(0).getElementsByClass("image")
               .get(0).attr("title");
    }

    private byte[] getImage(Element element) {
        String imageLink = element.getElementsByClass("wrapper").get(0).getElementsByClass("image")
                .get(0).getElementsByAttribute("src").get(0).attr("src");
        try {
            return Jsoup.connect(URL + imageLink)
                    .ignoreContentType(true).execute().bodyAsBytes();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error during getting image. Reason ", e);
        }
        return null;
    }
}
