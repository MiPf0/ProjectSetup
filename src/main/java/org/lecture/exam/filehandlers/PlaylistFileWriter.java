package org.lecture.exam.filehandlers;

import org.lecture.exam.Song;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PlaylistFileWriter {

    public static PlaylistFileWriter instance;

    private PlaylistFileWriter() {
        //
    }

    public static PlaylistFileWriter getInstance() {
        if(instance==null) {
            instance = new PlaylistFileWriter();
        }
        return instance;
    }

    //TrackId,Song,Duration,Album,Artist

    public void writeToFile(List<Song> playlist) throws IOException {
        FileWriter csvFw = new FileWriter(new File("src/main/resources/output","playlist.csv"));
        csvFw.append("TrackId");
        csvFw.append(";");
        csvFw.append("Titel");
        csvFw.append(";");
        csvFw.append("Duration");
        csvFw.append(";");
        csvFw.append("Album");
        csvFw.append(";");
        csvFw.append("Artist");
        csvFw.append("\n");

        for(Song s : playlist) {
            csvFw.append((char) s.getTrackId());
            csvFw.append(";");
            csvFw.append(s.getTitel());
            csvFw.append(";");
            csvFw.append((char) s.getTime());
            csvFw.append(";");
            csvFw.append(s.getAlbum());
            csvFw.append(";");
            csvFw.append(s.getInterpret());
            csvFw.append("\n");
        }

        csvFw.flush();
        csvFw.close();
    }
}
