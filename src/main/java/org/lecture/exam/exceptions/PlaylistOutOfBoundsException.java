package org.lecture.exam.exceptions;

public class PlaylistOutOfBoundsException extends Exception {

    @Override
    public String getMessage() {
        return "This Exception is thrown because playlist is out of bounds.";
    }
}