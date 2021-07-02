package org.lecture.exam;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.lecture.exam.exceptions.NoDeviceFoundException;
import org.lecture.exam.exceptions.PlaylistOutOfBoundsException;
import org.lecture.exam.filehandlers.FileReader;
import org.lecture.exam.filehandlers.PlaylistFileWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class Service {

    private final Player player = new Player();

    @Setter
    private Scanner sc = new Scanner(System.in);

    /**
     * starts the audioplayer program and asks user to register output devices and adjust playlist
     * @param p path
     * @throws IOException
     * @throws NoDeviceFoundException
     * @throws PlaylistOutOfBoundsException
     */
    public void start(Path p) throws IOException, NoDeviceFoundException, PlaylistOutOfBoundsException {

        FileReader fileReader = FileReader.getInstance();
        PlaylistFileWriter fileWriter = PlaylistFileWriter.getInstance();
        player.getSongList().addAll(fileReader.readSongs(p));

        String menu = """
                -----------------------
                ARCTIC AUDIOPLAYER START MENU
                -----------------------
                (1) register output device                (2) deregister output device
                
                (3) show songlist                         (4) show playlist
                
                (5) Add song from songlist to playlist    (6) save playlist in new file
                
                (7) Start player
                """;

        do {
            System.out.println(menu);

            String choice = sc.nextLine().toUpperCase();

            switch (choice) {

                case "1" -> {
                    OutputDeviceObserver outputDeviceObserver = getOutputDeviceObserverWithName();
                    registerOutputDeviceObserver(outputDeviceObserver);
                }
                case "2" -> {
                    OutputDeviceObserver outputDeviceObserver = getOutputDeviceObserverWithName();
                    deregisterObserver(outputDeviceObserver);
                }
                case "3" -> player.printSongList();
                case "4" -> player.printPlayList();
                case "5" -> player.addSongToPlaylist(sc);
                case "6" -> fileWriter.writeToFile(player.getPlayList());
                case "7" -> {
                    try {
                        if(player.getOutputDeviceObservers().size()==0) {
                            System.out.println("No device found. Please register new device.");
                            log.error("No device found.");
                            throw new NoDeviceFoundException();
                        }
                    } catch(NoDeviceFoundException e) {
                        System.out.println("---");
                        break;
                    }
                    player.playSongs(sc);
                }
            }
        } while (true);

    }

    /**
     * lets user add a new output device
     * @return
     */
    private OutputDeviceObserver getOutputDeviceObserverWithName() {
        System.out.println("Enter name of output device:");
        String name = sc.nextLine();
        return new OutputDeviceObserver(name);
    }

    /**
     * adds output device to the player's list of observers
     * @param outputDeviceObserver
     */
    private void registerOutputDeviceObserver(OutputDeviceObserver outputDeviceObserver) {
        player.addObserver(outputDeviceObserver);
    }

    /**
     * removes output device of the player's list of observers
     * @param outputDeviceObserver
     */
    private void deregisterObserver(OutputDeviceObserver outputDeviceObserver) {
        player.removeObserver(outputDeviceObserver);
    }
}