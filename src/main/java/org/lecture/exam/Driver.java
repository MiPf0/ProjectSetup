package org.lecture.exam;

import lombok.extern.slf4j.Slf4j;
import org.lecture.exam.exceptions.NoDeviceFoundException;
import org.lecture.exam.exceptions.PlaylistOutOfBoundsException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Driver {

    public static void main(String[] args) throws IOException, NoDeviceFoundException, PlaylistOutOfBoundsException {
        Service service = new Service();
        log.info("new Service created.");
        Path path = Paths.get("src/main/resources/input/songs.csv");
        service.start(path);
        log.info("new Service started.");
    }
}