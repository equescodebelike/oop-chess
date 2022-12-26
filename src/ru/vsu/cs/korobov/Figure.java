package ru.vsu.cs.korobov;

public class Figure {

    private final FigureType type;

    private final FigureColor color;

    public Figure(FigureType type, FigureColor color) {
        this.type = type;
        this.color = color;
    }

    public FigureType getType() {
        return type;
    }

    public FigureColor getColor() {
        return color;
    }

}
