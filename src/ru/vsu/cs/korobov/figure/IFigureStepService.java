package ru.vsu.cs.korobov.figure;

import ru.vsu.cs.korobov.Cell;
import ru.vsu.cs.korobov.Figure;
import ru.vsu.cs.korobov.FigureType;
import ru.vsu.cs.korobov.Game;

import java.util.List;
import java.util.Map;

public interface IFigureStepService {

    FigureType getType();

    Map<Boolean, List<Cell>> process(Figure figure, Game game);
}
