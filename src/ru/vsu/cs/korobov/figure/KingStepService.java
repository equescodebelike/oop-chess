package ru.vsu.cs.korobov.figure;

import ru.vsu.cs.korobov.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KingStepService implements IFigureStepService{

    @Override
    public FigureType getType() {
        return FigureType.KING;
    }

    @Override
    public Map<Boolean, List<Cell>> process(Figure figure, Game game) {
        if(figure.getType() != getType()) {
            throw new RuntimeException();
        }
        Cell cell = game.getFigureCellMap().get(figure);
        Map<Directions, Cell> cells = cell.getDirectionsMap();
        Map<Boolean, List<Cell>> cellsForStep = new HashMap<>();
        List<Cell> trues = new ArrayList<>();
        List<Cell> falses = new ArrayList<>();
        for(Directions dir: Directions.values()) {
            if(cells.get(dir) == null) {
                continue;
            }
            Figure checkFigure = game.getCellToFigureMap().get(cell.getDirectionsMap().get(dir));
            if(checkFigure == null) {
                falses.add(cell.getDirectionsMap().get(dir));
            } else {
                if(checkFigure.getColor() == figure.getColor()) {
                    continue;
                } else {
                    trues.add(cell.getDirectionsMap().get(dir));
                }
            }
        }
        cellsForStep.put(true, trues);
        cellsForStep.put(false, falses);
        return cellsForStep;
    }
}
