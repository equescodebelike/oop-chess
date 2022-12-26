package ru.vsu.cs.korobov;

import ru.vsu.cs.korobov.figure.*;

import java.util.*;

import static ru.vsu.cs.korobov.FigureColor.BLACK;
import static ru.vsu.cs.korobov.FigureColor.WHITE;

public class Game {

    private Queue<Player> players;

    private ChessBoard board;

    boolean check = false;

    private Map<Cell, Figure> cellToFigureMap = new HashMap<>();

    private Map<Figure, Cell> figureCellMap = new HashMap<>();

    private Map<Figure, Player> figurePlayerMap = new HashMap<>();

    private Map<Player, Map<FigureType, List<Figure>>> playerFigureMap = new HashMap<>();

    private Map<FigureType, IFigureStepService> serviceMap = new HashMap<>();


    public void init() {
        board = new ChessBoard();
        board.init();
        players = new LinkedList<>();
        players.add(new Player("white", WHITE));
        players.add(new Player("black", BLACK));
        setFiguresOnBoard();
    }

    private void setFiguresOnBoard() {
        // белые
        Player white = players.poll();
        List<Figure> whiteFiguresToSet = new ArrayList<>();
        List<Figure> whitePawns = new ArrayList<>();
        List<Figure> whiteKnights = new ArrayList<>();
        List<Figure> whiteRooks = new ArrayList<>();
        List<Figure> whiteBishops = new ArrayList<>();
        List<Figure> whiteQueens = new ArrayList<>();
        List<Figure> whiteKings = new ArrayList<>();
        Map<FigureType, List<Figure>> whiteFigures = new HashMap<>();
        whiteFigures.put(FigureType.ROOK, whiteRooks);
        whiteFigures.put(FigureType.PAWN, whitePawns);
        whiteFigures.put(FigureType.KNIGHT, whiteKnights);
        whiteFigures.put(FigureType.BISHOP, whiteBishops);
        whiteFigures.put(FigureType.QUEEN, whiteQueens);
        whiteFigures.put(FigureType.KING, whiteKings);
        playerFigureMap.put(white, whiteFigures);
        whiteFiguresToSet.add(new Figure(FigureType.ROOK, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.KNIGHT, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.BISHOP, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.KING, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.QUEEN, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.BISHOP, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.KNIGHT, WHITE));
        whiteFiguresToSet.add(new Figure(FigureType.ROOK, WHITE));
        for (int i = 0; i < 9; i++) {
            whiteFiguresToSet.add(new Figure(FigureType.PAWN, WHITE));
        }
        int k = 0;
        for (int i = 1; i < 3; i++) {
            for (char j = 'A'; j < 'I'; j++) {
                String cellName = j + Integer.toString(i);
                cellToFigureMap.put(board.map.get(cellName), whiteFiguresToSet.get(k));
                figureCellMap.put(cellToFigureMap.get(board.map.get(cellName)), board.map.get(cellName));
                playerFigureMap.get(white).get(whiteFiguresToSet.get(k).getType()).add(whiteFiguresToSet.get(k));
                k++;
            }
        }

        players.add(white);

        //чёрные
        Player black = players.poll();
        List<Figure> blackFiguresToSet = new ArrayList<>();
        List<Figure> blackPawns = new ArrayList<>();
        List<Figure> blackKnights = new ArrayList<>();
        List<Figure> blackRooks = new ArrayList<>();
        List<Figure> blackBishops = new ArrayList<>();
        List<Figure> blackQueens = new ArrayList<>();
        List<Figure> blackKings = new ArrayList<>();
        Map<FigureType, List<Figure>> blackFigures = new HashMap<>();
        blackFigures.put(FigureType.ROOK, blackRooks);
        blackFigures.put(FigureType.PAWN, blackPawns);
        blackFigures.put(FigureType.KNIGHT, blackKnights);
        blackFigures.put(FigureType.BISHOP, blackBishops);
        blackFigures.put(FigureType.QUEEN, blackQueens);
        blackFigures.put(FigureType.KING, blackKings);
        playerFigureMap.put(black, blackFigures);
        blackFiguresToSet.add(new Figure(FigureType.ROOK, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.KNIGHT, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.BISHOP, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.KING, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.QUEEN, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.BISHOP, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.KNIGHT, BLACK));
        blackFiguresToSet.add(new Figure(FigureType.ROOK, BLACK));
        for (int i = 0; i < 9; i++) {
            blackFiguresToSet.add(new Figure(FigureType.PAWN, BLACK));
        }
        k = 0;
        for (int i = 8; i > 6; i--) {
            for (char j = 'H'; j >= 'A'; j--) {
                String cellName = j + Integer.toString(i);
                cellToFigureMap.put(board.map.get(cellName), blackFiguresToSet.get(k));
                figureCellMap.put(cellToFigureMap.get(board.map.get(cellName)), board.map.get(cellName));
                playerFigureMap.get(black).get(blackFiguresToSet.get(k).getType()).add(blackFiguresToSet.get(k));
                k++;
            }
        }

        players.add(black);

        serviceMap.put(FigureType.KING, new KingStepService());
        serviceMap.put(FigureType.ROOK, new RookStepService());
        serviceMap.put(FigureType.BISHOP, new BishopStepService());
        serviceMap.put(FigureType.QUEEN, new QueenStepService());
        serviceMap.put(FigureType.KNIGHT, new KnightStepService());
        serviceMap.put(FigureType.PAWN, new PawnStepService());
    }

    public void process() {
        printStateOfBoard(null);
        int k = 0;
        while (players.size() > 1) {
            Player currentPlayer = players.poll();
            Player enemy = players.peek();
            Map<FigureType, List<Figure>> figureTypeListMap = playerFigureMap.get(currentPlayer);
            Figure currentFigure = defineFigureForStep(figureTypeListMap);
            IFigureStepService figureStepService = serviceMap.get(currentFigure.getType());
            Map<Boolean, List<Cell>> cellsForStep = figureStepService.process(currentFigure, this);
            Cell targetCell = defineFinishCellToStep(cellsForStep, currentFigure);
            doStep(currentFigure, targetCell, enemy);

            Map<FigureType, List<Figure>> enemyFigureTypeListMap = playerFigureMap.get(enemy);
            checkCheck(enemyFigureTypeListMap);
            checkCheck(figureTypeListMap);
            printStateOfBoard(null);
            players.add(currentPlayer);
            if (check) {
                break;
            }
            k++;
        }
    }

    private void checkCheck(Map<FigureType, List<Figure>> figureTypeListMap) {
        FigureType[] figures = FigureType.values();
        int maxFigureValue = 0;
        for (FigureType ft : figures) {
            for (Figure currFigure : figureTypeListMap.get(ft)) {
                IFigureStepService figureStepService = serviceMap.get(currFigure.getType());
                Map<Boolean, List<Cell>> cellsForStep = figureStepService.process(currFigure, this);
                if (cellsForStep.get(true).size() != 0) {
                    for (Cell cell : cellsForStep.get(true)) {
                        if (cellToFigureMap.get(cell).getType().getValue() > maxFigureValue) {
                            maxFigureValue = cellToFigureMap.get(cell).getType().getValue();
                        }
                    }
                }
            }
        }
        if (maxFigureValue == 100) {
            check = true;
        }
    }

    private void doStep(Figure figure, Cell targetCell, Player player) {
        Cell sourceCell = figureCellMap.get(figure);
        cellToFigureMap.remove(sourceCell);
        Figure killedFigure = cellToFigureMap.get(targetCell);
        cellToFigureMap.remove(targetCell);
        cellToFigureMap.put(targetCell, figure);
        if (killedFigure != null) {
            figureCellMap.remove(killedFigure);
            figurePlayerMap.remove(killedFigure);
            playerFigureMap.get(player).get(killedFigure.getType()).remove(killedFigure);
        }
        figureCellMap.put(figure, targetCell);
        System.out.println("Turn: " + sourceCell.getVert() + sourceCell.getHoriz() + " -> " + targetCell.getVert() + targetCell.getHoriz());
        if (killedFigure != null) {
            System.out.println("Killed figure: " + killedFigure.getColor().toString() + " " + killedFigure.getType().toString());
        }
    }

    private Cell defineFinishCellToStep(Map<Boolean, List<Cell>> cellsForStep, Figure figure) {
        System.out.println();
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println();
        System.out.println("Figure makes turn: " + figure.getColor().toString() + " " + figure.getType().toString());
        // # - возможные ходы, X - убитая фигура
        printStateOfBoard(cellsForStep);
        System.out.println();
        if (cellsForStep.get(true).size() != 0) {
            int biggestEnemyValue = 0;
            Cell strongest = figureCellMap.get(figure);
            for (Cell cell : cellsForStep.get(true)) {
                if (cellToFigureMap.get(cell).getType().getValue() > biggestEnemyValue) {
                    strongest = cell;
                    biggestEnemyValue = cellToFigureMap.get(cell).getType().getValue();
                }
            }
            return strongest;
        } else {
            return cellsForStep.get(false).get((int) (Math.random() * ((cellsForStep.get(false).size()))));
        }
    }

    private Figure defineFigureForStep(Map<FigureType, List<Figure>> figureTypeListMap) {
        FigureType[] figures = FigureType.values();
        List<Figure> empty = new ArrayList<>();
        Figure strongest = null;
        int biggestEnemyValue = 0;
        for (FigureType ft : figures) {
            for (Figure currFigure : figureTypeListMap.get(ft)) {
                IFigureStepService figureStepService = serviceMap.get(currFigure.getType());
                Map<Boolean, List<Cell>> cellsForStep = figureStepService.process(currFigure, this);
                if (cellsForStep.get(true).size() != 0) {
                    for (Cell cell : cellsForStep.get(true)) {
                        int currEnemyValue = cellToFigureMap.get(cell).getType().getValue();
                        if (currEnemyValue > biggestEnemyValue) {
                            strongest = currFigure;
                            biggestEnemyValue = currEnemyValue;
                        }
                    }
                } else if (cellsForStep.get(false).size() != 0) {
                    empty.add(currFigure);
                }
            }
        }
        if (strongest != null) {
            return strongest;
        } else {
            return empty.get((int) (Math.random() * (empty.size())));
        }
    }

    public void printStateOfBoard(Map<Boolean, List<Cell>> cellsForStep) {
        System.out.println();
        if (cellsForStep == null) {
            for (int i = 8; i > 0; i--) {
                if (i == 8) {
                    System.out.print(i + "  ");
                } else {
                    System.out.print(i + "  ");
                }
                for (char j = 'A'; j < 'I'; j++) {
                    String cellName = j + Integer.toString(i);
                    if (cellToFigureMap.containsKey(board.map.get(cellName))) {
                        if (cellToFigureMap.get(board.map.get(cellName)).getColor() == WHITE) {
                            System.out.print(cellToFigureMap.get(board.map.get(cellName)).getType().getWhite() + " ");
                        } else {
                            System.out.print(cellToFigureMap.get(board.map.get(cellName)).getType().getBlack() + " ");
                        }
                    } else {
                        System.out.print("[] ");
                    }
                }
                System.out.println();
            }
            System.out.print("    ");
            for (char h = 'A'; h < 'I'; h++) {
                System.out.print(h + "| ");
            }
            System.out.println();
        } else {
            for (int i = 8; i > 0; i--) {
                if (i == 8) {
                    System.out.print(i + "  ");
                } else {
                    System.out.print(i + "  ");
                }
                for (char j = 'A'; j < 'I'; j++) {
                    String cellName = j + Integer.toString(i);
                    if (cellToFigureMap.containsKey(board.map.get(cellName))) {
                        if (cellToFigureMap.get(board.map.get(cellName)).getColor() == WHITE) {
                            if (cellsForStep.get(true).contains(board.map.get(cellName)) && cellToFigureMap.get(board.map.get(cellName)).getColor() == players.peek().getColor()) {
                                System.out.print("X ");

                            } else {
                                System.out.print(cellToFigureMap.get(board.map.get(cellName)).getType().getWhite() + " ");
                            }
                        } else {
                            if (cellsForStep.get(true).contains(board.map.get(cellName)) && cellToFigureMap.get(board.map.get(cellName)).getColor() == players.peek().getColor()) {
                                System.out.print("X ");
                            } else {
                                System.out.print(cellToFigureMap.get(board.map.get(cellName)).getType().getBlack() + " ");
                            }
                        }
                    } else {
                        if (cellsForStep.get(false).contains(board.map.get(cellName))) {
                            System.out.print(" # ");
                        } else {
                            System.out.print("[] ");
                        }
                    }
                }
                System.out.println();
            }
            System.out.print("   ");
            for (char h = 'A'; h < 'I'; h++) {
                System.out.print(h + "| ");
            }
            System.out.println();
        }
    }

    public Queue<Player> getPlayers() {
        return players;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public Map<Cell, Figure> getCellToFigureMap() {
        return cellToFigureMap;
    }

    public Map<Figure, Cell> getFigureCellMap() {
        return figureCellMap;
    }

    public Map<Figure, Player> getFigurePlayerMap() {
        return figurePlayerMap;
    }

    public Map<Player, Map<FigureType, List<Figure>>> getPlayerFigureMap() {
        return playerFigureMap;
    }
}
