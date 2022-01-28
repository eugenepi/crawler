Movie crawler that navigates to "https://www.themoviedb.org".<br>
The service iterates through movie?page=page_number srarting from page 1 until we get response with data for current page.<br>
From web page the service gets movie name and movie image.<br>
These data then saves in SQLite database(movies table).<br>


Run test:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gradle clean test

Build jar:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;gradle clean shadowJar

Build docker:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;docker build -t movie-crawler .

Run docker-compose:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Open docker-compose.yml <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;replace - path_to_database_folder:/database to folder location on your host <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Example: D:/crawler/database:/database <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Execute startup command: docker-compose up<br>
<br>
TODO:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For now when we restart service it starts parsing page from the first page.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Would be good implement mechanism to continue parsing from last saved movie.
