package com.abworks.games.tictactoe;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public class Game {

    @Setter
    private String winner;
    char[][] board;
    @Setter
    boolean hasFinished;

    public void setWinner(String player){
        this.winner = player;
        this.hasFinished = true;
    }

    public Game(int n){
        this.board = createBoard(n);
    }

    private char[][] createBoard(int n) {
        char[][] mat = new char[n][n];
        for (int i=0;i<n; i++)
            Arrays.fill(mat[i], '-');
        return mat;
    }


    public void print(){
        int n = board.length;
        for(int i=0; i<n; i++){
            System.out.print("| ");
            for(int j=0; j<n; j++){
                System.out.printf(" %c ", board[i][j]);
            }
            System.out.println(" |");
        }
    }
}
