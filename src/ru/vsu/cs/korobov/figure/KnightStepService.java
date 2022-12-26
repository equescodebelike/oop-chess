package ru.vsu.cs.korobov.figure;

import ru.vsu.cs.korobov.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnightStepService implements IFigureStepService {

    @Override
    public FigureType getType() {
        return FigureType.KNIGHT;
    }

    @Override
    public Map<Boolean, List<Cell>> process(Figure figure, Game game) {
        if(figure.getType() != getType()) {
            throw new RuntimeException();
        }

        Cell cell = game.getFigureCellMap().get(figure);
        Map<Boolean, List<Cell>> cellsForStep = new HashMap<>();
        List<Cell> trues = new ArrayList<>();
        List<Cell> falses = new ArrayList<>();

        List<Cell> res = new ArrayList<>();
        findAvailableCells(cell, game, res);

        for(Cell c: res) {
            if(game.getCellToFigureMap().get(c) == null) {
                falses.add(c);
            } else {
                if(game.getCellToFigureMap().get(c).getColor() != figure.getColor()) {
                    trues.add(c);
                }
            }
        }

        cellsForStep.put(true, trues);
        cellsForStep.put(false, falses);

        return cellsForStep;
    }

    private void findAvailableCells(Cell cell, Game game, List<Cell> result) {
        if(upCell(cell) != null) {
            if (upRightCell(upCell(cell)) != null) {
                result.add(upRightCell(upCell(cell)));
            }
            if (upLeftCell(upCell(cell)) != null) {
                result.add(upLeftCell(upCell(cell)));
            }
        }
        if(downCell(cell) != null) {
            if (downRightCell(downCell(cell)) != null) {
                result.add(downRightCell(downCell(cell)));
            }
            if (downLeftCell(downCell(cell)) != null) {
                result.add(downLeftCell(downCell(cell)));
            }
        }
        if(rightCell(cell) != null) {
            if(upRightCell(rightCell(cell)) != null) {
                result.add(upRightCell(rightCell(cell)));
            }
            if(downRightCell(rightCell(cell)) != null) {
                result.add(downRightCell(rightCell(cell)));
            }
        }
        if(leftCell(cell) != null) {
            if(upLeftCell(leftCell(cell)) != null) {
                result.add(upLeftCell(leftCell(cell)));
            }
            if(downLeftCell(leftCell(cell)) != null) {
                result.add(downLeftCell(leftCell(cell)));
            }
        }
    }

    private Cell upCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.UP);
    }

    private Cell downCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.DOWN);
    }

    private Cell rightCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.RIGHT);
    }

    private Cell leftCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.LEFT);
    }

    private Cell upRightCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.UP_RIGHT);
    }

    private Cell downLeftCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.DOWN_LEFT);
    }

    private Cell downRightCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.DOWN_RIGHT);
    }

    private Cell upLeftCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.UP_LEFT);
    }
}
