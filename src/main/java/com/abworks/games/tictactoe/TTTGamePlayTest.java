package com.abworks.games.tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class TTTGamePlayTest {

    @Test
    void testDeclaresWinnerOnCompletedRow() {
        Game game = new Game(3);
        TTTGamePlay gp = new TTTGamePlay(game);

        // Simulate moves: X at (0,0), O at (1,0), X at (0,1), O at (1,1), X at (0,2) -> X wins on row 0
        String input = "0 0\n1 0\n0 1\n1 1\n0 2\n";
        withSystemIn(input, gp::play);

        assertTrue(game.isHasFinished(), "Game should be finished after a winning row");
        assertEquals("PlayerX", game.getWinner(), "Winner should be PlayerX after completing the top row");
    }

    @Test
    void testDeclaresWinnerOnDiagonal() {
        // Primary diagonal win
        {
            Game game = new Game(3);
            TTTGamePlay gp = new TTTGamePlay(game);
            // X: (0,0), O: (0,1), X: (1,1), O: (0,2), X: (2,2) -> X wins on primary diagonal
            String input = "0 0\n0 1\n1 1\n0 2\n2 2\n";
            withSystemIn(input, gp::play);
            assertTrue(game.isHasFinished());
            assertEquals("PlayerX", game.getWinner());
        }

        // Secondary diagonal win
        {
            Game game = new Game(3);
            TTTGamePlay gp = new TTTGamePlay(game);
            // X: (0,2), O: (0,0), X: (1,1), O: (1,0), X: (2,0) -> X wins on secondary diagonal? No.
            // Correct secondary diagonal for X: (0,2), (1,1), (2,0)
            String input = "0 2\n0 0\n1 1\n1 0\n2 0\n";
            withSystemIn(input, gp::play);
            assertTrue(game.isHasFinished());
            assertEquals("PlayerX", game.getWinner());
        }
    }

    @Test
    void testGameDrawWhenBoardFullWithoutWinner() {
        Game game = new Game(3);
        TTTGamePlay gp = new TTTGamePlay(game);
        // Fill the board with no winning line (classic draw sequence)
        // X O X
        // X X O
        // O X O
        String input = ""
                + "0 0\n" // X
                + "0 1\n" // O
                + "0 2\n" // X
                + "1 2\n" // O
                + "1 0\n" // X
                + "2 0\n" // O
                + "1 1\n" // X
                + "2 2\n" // O
                + "2 1\n"; // X
        withSystemIn(input, gp::play);

        assertTrue(game.isHasFinished(), "Game should be finished when board is full");
        assertNull(game.getWinner(), "Game should be a draw with no winner");
    }

    @Test
    void testOutOfBoundsDetection() throws Exception {
        Game game = new Game(3);
        TTTGamePlay gp = new TTTGamePlay(game);

        var oobMethod = TTTGamePlay.class.getDeclaredMethod("oob", int.class, int.class);
        oobMethod.setAccessible(true);

        // Inside bounds
        assertFalse((boolean) oobMethod.invoke(gp, 0, 0));
        assertFalse((boolean) oobMethod.invoke(gp, 2, 2));
        assertFalse((boolean) oobMethod.invoke(gp, 1, 2));

        // Outside bounds
        assertTrue((boolean) oobMethod.invoke(gp, -1, 0));
        assertTrue((boolean) oobMethod.invoke(gp, 0, -1));
        assertTrue((boolean) oobMethod.invoke(gp, 3, 0));
        assertTrue((boolean) oobMethod.invoke(gp, 0, 3));
        assertTrue((boolean) oobMethod.invoke(gp, -1, -1));
        assertTrue((boolean) oobMethod.invoke(gp, 3, 3));
    }

    @Test
    void testSelectiveLineChecksBasedOnLastMove() throws Exception {
        Game game = new Game(3);
        TTTGamePlay gp = new TTTGamePlay(game);

        // Pre-fill board with X on first column except middle, so placing at (0,1) should not cause diagonal win
        game.getBoard()[0][0] = 'X';
        game.getBoard()[2][2] = 'X';
        // Last move is on (0,1) - not on any diagonal; only row and column should be considered.
        int[] lastMove = new int[]{0, 1};
        var checkGameFinished = TTTGamePlay.class.getDeclaredMethod("checkGameFinished", Marks.class, int[].class);
        checkGameFinished.setAccessible(true);

        boolean ended = (boolean) checkGameFinished.invoke(gp, Marks.X, lastMove);
        assertFalse(ended, "Move not on any diagonal should not trigger diagonal-based win checks resulting in win");
    }

    @Test
    void testMoveOverwritesExistingMark() throws Exception {
        Game game = new Game(3);
        TTTGamePlay gp = new TTTGamePlay(game);

        var makeMove = TTTGamePlay.class.getDeclaredMethod("makeMove", Marks.class, int[].class);
        makeMove.setAccessible(true);

        // Place X at (1,1)
        makeMove.invoke(gp, Marks.X, new int[]{1, 1});
        assertEquals('X', game.getBoard()[1][1], "Cell should contain X after first move");

        // Overwrite with O at the same cell (no validation present)
        makeMove.invoke(gp, Marks.O, new int[]{1, 1});
        assertEquals('O', game.getBoard()[1][1], "Cell should be overwritten by O due to lack of validation");
    }

    // Helper to run gameplay with custom System.in input
    private static void withSystemIn(String data, Runnable runnable) {
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            runnable.run();
        } finally {
            System.setIn(originalIn);
        }
    }
}
