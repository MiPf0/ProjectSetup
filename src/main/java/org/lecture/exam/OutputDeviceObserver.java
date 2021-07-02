package org.lecture.exam;

import lombok.Getter;

import java.util.Objects;

@Getter
public class OutputDeviceObserver {

    private final String name;
    public Song currentSong;

    public OutputDeviceObserver(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null||getClass()!=o.getClass()) return false;
        OutputDeviceObserver outputDeviceObserver = (OutputDeviceObserver) o;
        return Objects.equals(name, outputDeviceObserver.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}