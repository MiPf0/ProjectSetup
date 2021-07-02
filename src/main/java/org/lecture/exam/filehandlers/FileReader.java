package org.lecture.exam.filehandlers;

import lombok.extern.slf4j.Slf4j;
import org.lecture.exam.Song;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileReader {

    private static FileReader instance;

    private FileReader() {
        //
    }

    public static FileReader getInstance() {
        if (instance == null) {
            instance = new FileReader();
        }
        return instance;
    }

    /**
     * reads the songs file from given path
     * @param path path to load file
     * @return List of all songs
     * @throws IOException
     */
    public List<Song> readSongs(Path path) throws IOException {

        List<String> allLines = Files.readAllLines(path);
        allLines.remove(0);

        List<Song> songs = new ArrayList<>();
        for (String line : allLines) {
            String[] split = line.split(",");

            if(!split[3].equals("")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("m.ss");
                Song newSong = new Song(split[1], Integer.parseInt(split[0]), Double.parseDouble(split[2]), split[3], split[4]);
                songs.add(newSong);
            } else {
                log.error("Song " + split[1] + " had no album - therefore it is not taken to the list.");
            }
        }
        return songs;
    }
}