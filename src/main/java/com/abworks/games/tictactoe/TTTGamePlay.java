package com.abworks.games.tictactoe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


enum Marks {
    X('X'), O('O');
    @Getter
    final char boardMark;
    Marks(char boardMark){
        this.boardMark = boardMark;
    }

}
@RequiredArgsConstructor
public class TTTGamePlay {
    private final Game g;
    final Scanner s = new Scanner(System.in);

    public void play(){
        System.out.println("Starting game");
        g.print();
        int turn = 0;
        int maxTurns = g.getBoard().length * g.getBoard().length;
        while (!g.hasFinished){
            Marks currMark = (turn %2 == 0)?  Marks.X: Marks.O;
            System.out.println("Player " + currMark + " to move");
            int[] coords = getCoordsFromUser();
            makeMove(currMark, coords);
            boolean hasGameEnded = checkGameFinished(currMark, coords);
            if (hasGameEnded){
                markWinner(currMark);
            }
            turn = (turn + 1);
            if (turn == maxTurns)
                g.setHasFinished(true);
        }
        if (g.getWinner() == null)
            System.out.println("Game drawn");
    }

    private void markWinner(Marks currMark) {
        g.setWinner("Player"+currMark);
        System.out.println("Player "+ currMark +" wins the game.");

    }

    private boolean checkGameFinished(Marks currMark, int[] coords) {
        int row = coords[0];
        int col = coords[1];
        int size = g.getBoard().length ;

        if (row == col) {
            boolean isPDiagSame = checkSame(new int[]{0, 0}, currMark, new int[]{1, 1});
            if (isPDiagSame) return true;
        }
        if ((row + col) == (size - 1)){
            boolean isNDiagSame = checkSame(new int[]{0, size-1}, currMark, new int[]{1, -1});
            if (isNDiagSame) return true;
        }
        boolean isRowSame = checkSame(new int[]{row,0}, currMark, new int[]{0,1});
        if (isRowSame) return true;
        return checkSame(new int[]{0,col}, currMark, new int[]{1,0});

    }

    private boolean checkSame(int[] initial, Marks currMark, int[] offset) {
        int x = initial[0];
        int y = initial[1];
        while (!oob(x,y)){
            if (g.getBoard()[x][y] != currMark.getBoardMark())
                return false;
            x += offset[0];
            y += offset[1];
        }
        return true;
    }

    private boolean oob(int x, int y) {
        int size = g.getBoard().length ;
        return (x<0||y<0||x>=size||y>=size);

    }


    private void makeMove(Marks currMark, int[] coords) {
        int x = coords[0];
        int y = coords[1];
        g.getBoard()[x][y] = currMark.getBoardMark();
        g.print();
    }

    private int[] getCoordsFromUser() {
        System.out.print("Enter coords as row column: ");
        String command = s.nextLine();
        List<Integer> coords = Arrays
                .stream(command.split(" "))
                .map(Integer::parseInt)
                .toList();
        System.out.println();
        return new int[]{coords.get(0), coords.get(1)};
    }

    public static void main(String[] args) {
        Game newgame = new Game(3);
        TTTGamePlay gamePlay = new TTTGamePlay(newgame);
        gamePlay.play();
    }


}
