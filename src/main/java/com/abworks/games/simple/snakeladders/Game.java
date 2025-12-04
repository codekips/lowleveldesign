package com.abworks.games.simple.snakeladders;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.IntStream;


@RequiredArgsConstructor
class Player {
    final String name;
    public int position;
    public String toString(){
        return name;
    }
}
abstract class JumpPoint {
    int from;
    int to;
    public JumpPoint(int from, int to){
        this.from = from;
        this.to = to;
        validate();

    }
    public int to(){
        callout();
        return to;
    }

    protected abstract void callout();

    private void validate(){
        if (from == to)
            throw new RuntimeException("Jump point can not be at the same place. " + this);
    }
    public String toString(){
        return String.format("[%d - %d]", from, to);
    }
}
class SnakeJumppoint extends JumpPoint{

    public SnakeJumppoint(int from, int to){
        super(from, to);
        if (from < to)
            throw new RuntimeException("Snake must have from > to");

    }

    @Override
    protected void callout() {
        System.out.println("Hisss...!! Snake here. Next position is "+to);
    }
}
class LadderJumppoint extends JumpPoint{

    public LadderJumppoint(int from, int to){
        super(from, to);
        if (from > to)
            throw new RuntimeException("Ladder must have from < to");
    }

    @Override
    protected void callout() {
        System.out.println("Zoop...!! Ladder here. Next position is "+to);
    }
}

class Board {
    final int maxTile;
    private final Map<Integer, JumpPoint> jumpPointStarts;

    public static Board createValidBoard(int maxTile, List<SnakeJumppoint> snakes,
                                         List<LadderJumppoint> ladders){
        return new Board(maxTile, snakes, ladders);
    }

    private Board(int maxTile, List<SnakeJumppoint> snakes, List<LadderJumppoint> ladders){
        this.maxTile = maxTile;
        Map<Integer, SnakeJumppoint> snakeMapping = mapSnakesOnBoard(snakes);
        Map<Integer, LadderJumppoint> laddersMapping = mapLaddersOnBoard(ladders);
        this.jumpPointStarts = new HashMap<>(laddersMapping);
        this.jumpPointStarts.putAll(snakeMapping);
    }

    private Map<Integer, LadderJumppoint> mapLaddersOnBoard(List<LadderJumppoint> ladders) {
        Map<Integer, LadderJumppoint> laddersJumppointMap = new HashMap<>();
        for (LadderJumppoint ladder: ladders) {
            if (ladder.from == maxTile || ladder.to == 0) { // not valid board. ignore this snake
                System.err.println("Ignoring snake %s. Cannot place on the board");
                continue;
            }
            laddersJumppointMap.put(ladder.from, ladder);
        }
        return laddersJumppointMap;
    }

    private Map<Integer, SnakeJumppoint> mapSnakesOnBoard(List<SnakeJumppoint> snakes) {
        Map<Integer, SnakeJumppoint> snakeJumppointMap = new HashMap<>();
        for (SnakeJumppoint snake: snakes){
            if (snake.from == maxTile || snake.to == 0) { // not valid board. ignore this snake
                System.err.println("Ignoring snake %s. Cannot place on the board");
                continue;
            }
            snakeJumppointMap.put(snake.from, snake);
        }
        return snakeJumppointMap;
    }

    public void movePlayer(Player current, int moveOffset) {
        int nextProbable = current.position + moveOffset;
        if (nextProbable > maxTile) return;
        Optional<JumpPoint> jumppoint = getJumpAt(nextProbable);
        if (jumppoint.isPresent()) {
            nextProbable = jumppoint.get().to();
        }
        current.position = nextProbable;
    }

    private Optional<JumpPoint> getJumpAt(int from) {
        return Optional.ofNullable(jumpPointStarts.get(from));
    }
}


public class Game {
    private Board board;
    private List<Player> players;
    private boolean isFinished;
    private Random random = new Random();

    public Game(Board board, List<Player> players){
        this.board = board;
        this.players = players;
        this.isFinished = false;
    }

    public void start(){
        int turn = 0;
        int numPlayers = players.size();
        while (!isFinished){
            Player current = players.get(turn);
            int moveOffset = randomDice();
            board.movePlayer(current, moveOffset);
            if (hasWon(current)) {
                System.out.printf("Yayyy... %s wins \n", current);
                isFinished = true;
            }
            turn = (turn + 1 )%numPlayers;
        }
    }

    private boolean hasWon(Player current) {
        return  (current.position == board.maxTile);

    }

    private int randomDice(){
        return random.nextInt(1,6);
    }



    public static void main(String[] args) {
        int numPlayers = 4;
        int maxTile = 100;
        Map<Integer, Integer> snakesInput = Map.of(10, 2,30, 15, 45, 2, 85, 26, 99, 70);
        Map<Integer, Integer> laddersInput = Map.of(4,8, 15,36,57, 82, 90,94);

        List<Player> players = IntStream.range(1,numPlayers)
                .mapToObj(i-> new Player("Player"+i))
                .toList();

        List<SnakeJumppoint> snakes = snakesInput
                .entrySet().stream()
                .map(e-> new SnakeJumppoint(e.getKey(), e.getValue()))
                .toList();

        List<LadderJumppoint> ladders = laddersInput
                .entrySet().stream()
                .map(e-> new LadderJumppoint(e.getKey(), e.getValue()))
                .toList();


        Board b = Board.createValidBoard(maxTile, snakes, ladders);
        Game game = new Game(b, players);
        game.start();

    }


}

