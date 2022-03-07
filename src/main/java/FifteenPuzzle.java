public class FifteenPuzzle {
    private int[][] board = new int[4][4];
    private int deepthLevel;
    private int createdState;

    public FifteenPuzzle(FifteenPuzzle board) {
        this.board = board.getBoard();
        this.deepthLevel = board.getDeepthLevel() + 1;
        this.createdState = board.getCreatedState() + 1;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getDeepthLevel() {
        return deepthLevel;
    }

    public int getCreatedState() {
        return createdState;
    }
}
