package ru.vsu.cs.korobov.figure;

import ru.vsu.cs.korobov.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RookStepService implements IFigureStepService{

    @Override
    public FigureType getType() {
        return FigureType.ROOK;
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
        findAvailableCellsDirect(cell, game, res);

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

    private void findAvailableCellsDirect(Cell cell, Game game, List<Cell> result) {
        Cell start = cell;
        while(upCell(cell) != null) {
            if(game.getCellToFigureMap().get(upCell(cell)) != null) {
                result.add(upCell(cell));
                break;
            }
            result.add(upCell(cell));
            cell = upCell(cell);
        }
        cell = start;
        while(downCell(cell) != null) {
            if(game.getCellToFigureMap().get(downCell(cell)) != null) {
                result.add(downCell(cell));
                break;
            }
            result.add(downCell(cell));
            cell = downCell(cell);
        }
        cell = start;
        while(rightCell(cell) != null) {
            if(game.getCellToFigureMap().get(rightCell(cell)) != null) {
                result.add(rightCell(cell));
                break;
            }
            result.add(rightCell(cell));
            cell = rightCell(cell);
        }
        cell = start;
        while(leftCell(cell) != null) {
            if(game.getCellToFigureMap().get(leftCell(cell)) != null) {
                result.add(leftCell(cell));
                break;
            }
            result.add(leftCell(cell));
            cell = leftCell(cell);
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

}
