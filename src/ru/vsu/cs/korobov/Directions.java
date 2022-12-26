package ru.vsu.cs.korobov;

public enum Directions {

    UP (DirectionType.DIRECT),
    UP_RIGHT (DirectionType.DIAGONAL),
    RIGHT (DirectionType.DIRECT),
    DOWN_RIGHT (DirectionType.DIAGONAL),
    DOWN (DirectionType.DIRECT),
    DOWN_LEFT (DirectionType.DIAGONAL),
    LEFT (DirectionType.DIRECT),
    UP_LEFT (DirectionType.DIAGONAL);

    private final DirectionType type;

    Directions(DirectionType type) {
        this.type = type;
    }

    public DirectionType getType() {
        return type;
    }
}
