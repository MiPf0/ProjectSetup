package org.lecture.exam;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.lecture.exam.exceptions.PlaylistOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Getter
public class Player {

    private final List<Song> songList = new ArrayList<>();
    public List<Song> playList = new ArrayList<>();
    public List<OutputDeviceObserver> outputDeviceObservers = new ArrayList<>();

    /**
     * removes observer from list of observers of agency
     * @param outputDeviceObserver to be removed
     */
    public void removeObserver(OutputDeviceObserver outputDeviceObserver) {
        outputDeviceObservers.remove(outputDeviceObserver);
    }

    /**
     * adds observer to list of observers of agency
     * @param outputDeviceObserver to be added
     */
    public void addObserver(OutputDeviceObserver outputDeviceObserver) {
        outputDeviceObservers.add(outputDeviceObserver);
    }

    /**
     * adds a song from the songlist to the playList
     * @param sc scanner
     */
    public void addSongToPlaylist(Scanner sc) {
        System.out.println("Which songs you want to add to the playlist? Please type the names of the songs, separated by a comma, e.g. 'Song,Song,Song':");
        for(Song s : songList) {
            System.out.println(s);
        }
        String input = sc.nextLine();
        String[] choices = input.split(",");

        for(String choice : choices) {
            for(Song s : songList) {
                if(s.getTitel().equals(choice)) {
                    this.playList.add(s);
                    System.out.println("Song " + s.getTitel() + " added.");
                    log.info("Song " + s.getTitel() + " added to playlist.");
                }
            }
        }
    }

    /**
     * prints the songlist of this player
     */
    public void printSongList() {
        for (Song song : songList) {
            System.out.println(song);
        }
    }

    /**
     * prints the playlist of this player
     */
    public void printPlayList() {
        for (Song song : playList) {
            System.out.println(song);
        }
    }

    /**
     * enters the submenu of songplaying
     * @param sc Scanner
     * @throws PlaylistOutOfBoundsException
     */
    public void playSongs(Scanner sc) throws PlaylistOutOfBoundsException {

        int currentSongIndex = -1;

        String playMenu = """
                (1) play first or next song
                (2) go back to previous song
                (3) play song again
                (4) start playlist again from beginning
                """;

        do {

            int lastIdx = -1;

            for(Song s : playList) {
                lastIdx++;
            }

            try {
                if (playList.size()==0) {
                    System.out.println("Playlist is empty - therefore not playable.");
                    log.error("Playlist not playable.");
                    throw new PlaylistOutOfBoundsException();
                }
            } catch(PlaylistOutOfBoundsException e) {
                System.out.println("---");
                break;
            }

            System.out.println(playMenu);

            String choice = sc.nextLine().toUpperCase();

            switch(choice) {
                case "1" -> {
                    currentSongIndex++;

                    try {
                        if (currentSongIndex==lastIdx) {
                            System.out.println("You are already at the last song - you can't play a next song.");
                            log.error("Next song not playable.");
                            currentSongIndex--;
                            throw new PlaylistOutOfBoundsException();
                        }
                    } catch(PlaylistOutOfBoundsException e) {
                        System.out.println("---");
                        break;
                    }

                    notifyOutputDeviceObservers(playList.get(currentSongIndex));
                }
                case "2" -> {
                    currentSongIndex--;

                    try {
                        if(currentSongIndex==-1) {
                            System.out.println("You are already at the first song - you can't switch to the last one.");
                            log.error("Previous song not playable.");
                            currentSongIndex++;
                            throw new PlaylistOutOfBoundsException();
                        }
                    } catch(PlaylistOutOfBoundsException e) {
                        System.out.println("---");
                        break;
                    }

                    notifyOutputDeviceObservers(playList.get(currentSongIndex));
                }
                case "3" -> {
                    notifyOutputDeviceObservers(playList.get(currentSongIndex));
                }
                case "4" -> {
                    currentSongIndex=0;
                    notifyOutputDeviceObservers(playList.get(currentSongIndex));
                }
            }
        } while (true);


    }

    /**
     * notifies the output device observers and updates their current song playing
     * @param song Song
     */
    public void notifyOutputDeviceObservers(Song song) {
        for(OutputDeviceObserver o : outputDeviceObservers) {
            o.currentSong = song;
            System.out.println("Output device " + o + " plays song " + song + ".");
        }
    }

}
