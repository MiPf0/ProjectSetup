package org.lecture.exam;

import lombok.Getter;

@Getter
public class Song {

    private final String titel;
    private final int trackId;
    private final double time;
    private final String album;
    private final String interpret;

    public Song(String titel, int trackId, double time, String album, String interpret) {
        this.titel = titel;
        this.trackId = trackId;
        this.time = time;
        this.album = album;
        this.interpret = interpret;
    }

    @Override
    public String toString() {
        return "Song -- Titel: " + titel + "; Track-ID: " + trackId + "; Dauer: " + time + "; Album: " + album + "; Interpret: " + interpret;
    }
}
