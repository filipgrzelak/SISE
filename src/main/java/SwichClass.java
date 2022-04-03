import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SwichClass {
    public static void doMoveOperation(char s, FifteenPuzzle state, Collection<FifteenPuzzle> list) {
        switch (s) {
            case 'L':
                if (state.getX() != 0 && (state.getLastMove() != 'R')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[board.getY()][board.getX() - 1];
                    board.getBoard()[board.getY()][board.getX() - 1] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('L');
                    board.setLastMove('L');
                    board.setX((byte) (board.getX() - 1));
                    list.add(board);
                }
                break;
            case 'U':
                if (state.getY() != 0 && (state.getLastMove() != 'D')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[board.getY() - 1][board.getX()];
                    board.getBoard()[board.getY() - 1][board.getX()] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('U');
                    board.setLastMove('U');
                    board.setY((byte) (board.getY() - 1));
                    list.add(board);
                }
                break;
            case 'R':
                if (state.getX() != state.getBoard().length - 1 && (state.getLastMove() != 'L')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[state.getY()][state.getX() + 1];
                    board.getBoard()[state.getY()][state.getX() + 1] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('R');
                    board.setLastMove('R');
                    board.setX((byte) (board.getX() + 1));
                    list.add(board);
                }
                break;
            case 'D':
                if (state.getY() != state.getBoard()[0].length - 1 && (state.getLastMove() != 'U')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[state.getY() + 1][state.getX()];
                    board.getBoard()[state.getY() + 1][state.getX()] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('D');
                    board.setLastMove('D');
                    board.setY((byte) (state.getY() + 1));
                    list.add(board);
                }
                break;
            default:
                throw new IllegalArgumentException("Bad name of operation");

        }
    }

    public static void doMoveOperation(char s, FifteenPuzzle state, ArrayList<FifteenPuzzle> list) {
        switch (s) {
            case 'L':
                if (state.getX() != 0 && (state.getLastMove() != 'R')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[board.getY()][board.getX() - 1];
                    board.getBoard()[board.getY()][board.getX() - 1] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('L');
                    board.setLastMove('L');
                    board.setX((byte) (board.getX() - 1));
                    board.countDistance();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            case 'U':
                if (state.getY() != 0 && (state.getLastMove() != 'D')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[board.getY() - 1][board.getX()];
                    board.getBoard()[board.getY() - 1][board.getX()] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('U');
                    board.setLastMove('U');
                    board.setY((byte) (board.getY() - 1));
                    board.countDistance();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            case 'R':
                if (state.getX() != state.getBoard().length - 1 && (state.getLastMove() != 'L')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[state.getY()][state.getX() + 1];
                    board.getBoard()[state.getY()][state.getX() + 1] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('R');
                    board.setLastMove('R');
                    board.setX((byte) (board.getX() + 1));
                    board.countDistance();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            case 'D':
                if (state.getY() != state.getBoard()[0].length - 1 && (state.getLastMove() != 'U')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[state.getY() + 1][state.getX()];
                    board.getBoard()[state.getY() + 1][state.getX()] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('D');
                    board.setLastMove('D');
                    board.setY((byte) (state.getY() + 1));
                    board.countDistance();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Bad name of operation");

        }
    }

    public static void doMoveOperationHam(char s, FifteenPuzzle state, ArrayList<FifteenPuzzle> list) {
        switch (s) {
            case 'L':
                if (state.getX() != 0 && (state.getLastMove() != 'R')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[board.getY()][board.getX() - 1];
                    board.getBoard()[board.getY()][board.getX() - 1] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('L');
                    board.setLastMove('L');
                    board.setX((byte) (board.getX() - 1));
                    board.countWrongPlaces();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            case 'U':
                if (state.getY() != 0 && (state.getLastMove() != 'D')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[board.getY() - 1][board.getX()];
                    board.getBoard()[board.getY() - 1][board.getX()] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('U');
                    board.setLastMove('U');
                    board.setY((byte) (board.getY() - 1));
                    board.countWrongPlaces();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            case 'R':
                if (state.getX() != state.getBoard().length - 1 && (state.getLastMove() != 'L')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[state.getY()][state.getX() + 1];
                    board.getBoard()[state.getY()][state.getX() + 1] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('R');
                    board.setLastMove('R');
                    board.setX((byte) (board.getX() + 1));
                    board.countWrongPlaces();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            case 'D':
                if (state.getY() != state.getBoard()[0].length - 1 && (state.getLastMove() != 'U')) {
                    FifteenPuzzle board = state.clone();
                    byte temp = board.getBoard()[state.getY() + 1][state.getX()];
                    board.getBoard()[state.getY() + 1][state.getX()] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('D');
                    board.setLastMove('D');
                    board.setY((byte) (state.getY() + 1));
                    board.countWrongPlaces();
                    int place = findTheRightPosition(board, list);
                    if (place == -1) {
                        list.add(board);
                    } else {
                        list.add(place, board);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Bad name of operation");
        }
    }

    private static int findTheRightPosition(FifteenPuzzle board, ArrayList<FifteenPuzzle> list) {
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (board.getCountValue() <= list.get(i).getCountValue()) {
                return i;
            }
        }
        return -1;
    }
}
