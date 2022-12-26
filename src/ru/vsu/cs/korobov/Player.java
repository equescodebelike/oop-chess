package ru.vsu.cs.korobov;

public class Player {

    private final String name;
    private final FigureColor color;

    public Player(String name, FigureColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public FigureColor getColor() {
        return color;
    }
}
