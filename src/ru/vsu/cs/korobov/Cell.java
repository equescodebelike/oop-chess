package ru.vsu.cs.korobov;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cell {

    private final Map<Directions, Cell> directionsMap = new LinkedHashMap<>();

    private final String horiz;

    private final String vert;

    public Cell(String horiz, String vert) {
        this.horiz = horiz;
        this.vert = vert;
    }

    public Map<Directions, Cell> getDirectionsMap() {
        return directionsMap;
    }

    public String getHoriz() {
        return horiz;
    }

    public String getVert() {
        return vert;
    }
}
