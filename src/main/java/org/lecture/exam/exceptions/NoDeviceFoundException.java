package org.lecture.exam.exceptions;

public class NoDeviceFoundException extends Exception {

    @Override
    public String getMessage() {
        return "This Exception is thrown because no device was found.";
    }

}
