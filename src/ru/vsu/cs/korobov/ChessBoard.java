package ru.vsu.cs.korobov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {

    public final Map<String, Cell> map = new HashMap<>();

    public void init() {
        List<List<Cell>> rows = new ArrayList<>();
        for(int i = 1; i < 9; i++) {
            List<Cell> row = new ArrayList<>();
            for(char j = 'A'; j < 'I'; j++) {
                Cell cell = new Cell(String.valueOf(i), Character.toString(j));
                map.put(j + String.valueOf(i), cell);
                row.add(cell);
            }
            for(int j = 1; j < 8; j++) {
                Cell cell = row.get(j);
                Cell prevCell = row.get(j - 1);
                cell.getDirectionsMap().put(Directions.LEFT, prevCell);
                prevCell.getDirectionsMap().put(Directions.RIGHT, cell);
            }
            rows.add(row);
        }
        for(int i = 0; i < 8; i++) { //перебор row
            for(int j = 1; j < 8; j++) { //перебор rows
                Cell cell = rows.get(j).get(i);
                Cell prevCell = rows.get(j-1).get(i);
                cell.getDirectionsMap().put(Directions.DOWN, prevCell);
                prevCell.getDirectionsMap().put(Directions.UP, cell);
            }
        }
        for(int i = 1; i < 8; i++) { //перебор rows
            for(int j = 0; j < 8; j++) { //перебор row
                Cell cell = rows.get(i).get(j);
                if(j!=0) {
                    Cell ldCell = rows.get(i - 1).get(j - 1);
                    cell.getDirectionsMap().put(Directions.DOWN_LEFT, ldCell);
                    ldCell.getDirectionsMap().put(Directions.UP_RIGHT, cell);
                }
                if(j!=7) {
                    Cell rdCell = rows.get(i - 1).get(j + 1);
                    cell.getDirectionsMap().put(Directions.DOWN_RIGHT, rdCell);
                    rdCell.getDirectionsMap().put(Directions.UP_LEFT, cell);
                }
            }
        }
    }

    private Cell getCellByIndex(Cell c, int index, Directions directions) {
        Cell result = c;
        for(int i = 0; i < index; i++) {
            if(result == null) {
                break;
            }
            result = result.getDirectionsMap().get(directions);
        }
        return result;
    }
}
