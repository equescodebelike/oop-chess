package ru.vsu.cs.korobov.figure;

import ru.vsu.cs.korobov.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PawnStepService implements IFigureStepService {

    @Override
    public FigureType getType() {
        return FigureType.PAWN;
    }

    @Override
    public Map<Boolean, List<Cell>> process(Figure figure, Game game) {
        if (figure.getType() != getType()) {
            throw new RuntimeException();
        }

        Cell cell = game.getFigureCellMap().get(figure);
        Map<Boolean, List<Cell>> cellsForStep = new HashMap<>();
        List<Cell> trues = new ArrayList<>();
        List<Cell> falses = new ArrayList<>();

        List<Cell> res = new ArrayList<>();
        if (figure.getColor() == FigureColor.WHITE) {
            if (upCell(cell) != null && game.getCellToFigureMap().get(upCell(cell)) == null) {
                res.add(upCell(cell));
            }
            if (upRightCell(cell) != null && game.getCellToFigureMap().get(upRightCell(cell)) != null) {
                res.add(upRightCell(cell));
            }
            if (upLeftCell(cell) != null && game.getCellToFigureMap().get(upLeftCell(cell)) != null) {
                res.add(upLeftCell(cell));
            }
        } else {
            if (downCell(cell) != null && game.getCellToFigureMap().get(downCell(cell)) == null) {
                res.add(downCell(cell));
            }
            if (downRightCell(cell) != null && game.getCellToFigureMap().get(downRightCell(cell)) != null) {
                res.add(downRightCell(cell));
            }
            if (downLeftCell(cell) != null && game.getCellToFigureMap().get(downLeftCell(cell)) != null) {
                res.add(downLeftCell(cell));
            }
        }
        for (Cell c : res) {
            if (game.getCellToFigureMap().get(c) == null) {
                falses.add(c);
            } else {
                if (game.getCellToFigureMap().get(c).getColor() != figure.getColor()) {
                    trues.add(c);
                }
            }
        }

        cellsForStep.put(true, trues);
        cellsForStep.put(false, falses);

        return cellsForStep;
    }

    private Cell upCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.UP);
    }

    private Cell downCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.DOWN);
    }

    private Cell upRightCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.UP_RIGHT);
    }

    private Cell upLeftCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.UP_LEFT);
    }

    private Cell downLeftCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.DOWN_LEFT);
    }

    private Cell downRightCell(Cell cell) {
        return cell.getDirectionsMap().get(Directions.DOWN_RIGHT);
    }
}
