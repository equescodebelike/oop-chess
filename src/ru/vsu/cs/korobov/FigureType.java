package ru.vsu.cs.korobov;

public enum FigureType {

    PAWN("WP", "BP", 1), // пешка
    BISHOP("WB", "BB", 2), // слон
    KNIGHT("WK", "BK", 2), // конь
    ROOK("WR", "BR", 3), // ладья
    QUEEN("WQ", "BQ", 6), // ферзь
    KING("wK", "bK", 100); // король

    private final String white;
    private final String black;
    private final int value;

    FigureType(String white, String black, int value) {
        this.white = white;
        this.black = black;
        this.value = value;
    }

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }

    public int getValue() {
        return value;
    }
}
