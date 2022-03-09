import java.util.Collection;

public class SwichClass {
    public static void doMoveOperation(char s, FifteenPuzzle state, Collection<FifteenPuzzle> list) {
        switch (s) {
            case 'L':
                if (state.getX() != 0 && (state.getLastMove() != 'R')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[board.getY()][board.getX() - 1];
                    board.getBoard()[board.getY()][board.getX() - 1] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('L');
                    board.setLastMove('L');
                    board.setX(board.getX() - 1);
                    list.add(board);
                }
                break;
            case 'U':
                if (state.getY() != 0 && (state.getLastMove() != 'D')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[board.getY() - 1][board.getX()];
                    board.getBoard()[board.getY() - 1][board.getX()] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('U');
                    board.setLastMove('U');
                    board.setY(board.getY() - 1);
                    list.add(board);
                }
                break;
            case 'R':
                if (state.getX() != 3 && (state.getLastMove() != 'L')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[state.getY()][state.getX() + 1];
                    board.getBoard()[state.getY()][state.getX() + 1] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('R');
                    board.setLastMove('R');
                    board.setX(board.getX() + 1);
                    list.add(board);
                }
                break;
            case 'D':
                if (state.getY() != 3 && (state.getLastMove() != 'U')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[state.getY() + 1][state.getX()];
                    board.getBoard()[state.getY() + 1][state.getX()] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('D');
                    board.setLastMove('D');
                    board.setY(state.getY() + 1);
                    list.add(board);
                }
                break;
            default:
                throw new IllegalArgumentException("Bad name of operation");

        }
    }
}
