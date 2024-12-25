package com.Models;

import com.Service.TraceGameService;
import com.strategy.WinningStrategy;
import lombok.Data;

import java.text.MessageFormat;
import java.util.ArrayList;

@Data
public class Game {
    Board board;
    ArrayList<User> players;
    int currentPlayerIndex;
    boolean isLastMoveIsUndo;
    int lastMoveX;
    int lastMoveY;
    private final WinningStrategy winningStrategy;
    private final TraceGameService traceGame;

    public Game(Board _board, ArrayList<User> _players) {
        this.board = _board;
        this.players = _players;
        this.board.createBoard();
        this.winningStrategy = new WinningStrategy(_board.size);
        this.traceGame = new TraceGameService();
    }

    public void renderBoard(){
        int sizeOfBoard = this.board.size;
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
                User currUser = this.board.getCell(i, j).user;
                char symbol = (currUser != null && currUser.getSymbol() != '\u0000') ? currUser.getSymbol() : '_';
                System.out.print(MessageFormat.format(" | {0}", symbol));
            }
            System.out.println(" |");
            System.out.println();
        }
    }

    public void replayGame(){
        int n = this.board.size;
        board.resetBoard();
        for(int move = 0; move < (n * n); move++){
            GameMove currentMove = move < traceGame.getLength() ? traceGame.getSpecificMove(move) : null;
            System.out.println();
            System.out.println();
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    if(currentMove != null && currentMove.getX() == x && currentMove.getY() == y){
                        board.getCell(x, y).user = currentMove.user;
                        char symbol = currentMove.getUser().getSymbol();
                        System.out.print(MessageFormat.format(" | {0}", symbol));
                    }else{
                        User currUser = this.board.getCell(x, y).user;
                        char symbol = (currUser != null && currUser.getSymbol() != '\u0000') ? currUser.getSymbol() : '_';
                        System.out.print(MessageFormat.format(" | {0}", symbol));
                    }
                }
                System.out.println(" |");
                System.out.println();
                System.out.println();
            }
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted!");
            }
        }
    }

    public boolean makeMove(int x, int y) {
        if(board.getCell(x,y).isOccupied()){
            System.out.println("the position is already occupied please choose another place");
            return false;
        }else{
            User currentPlayer = getCurrentPlayer();
            this.board.getCell(x, y).user = currentPlayer;
            this.winningStrategy.addPlayer(x,y,currentPlayer);
            traceGame.saveMove(x,y,currentPlayer);
            this.lastMoveX = x;
            this.lastMoveY = y;
            this.isLastMoveIsUndo = false;
            return true;
        }
    }

    public void assignCurrentPlayer(){
        currentPlayerIndex++;
        if(players.size() == currentPlayerIndex){
            currentPlayerIndex = 0;
        }
    }

    public User getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public boolean isWon(int x, int y, User player) {
        return this.winningStrategy.isWon(x,y,player);
    }

    public boolean isDraw(){
        return this.board.isFull();
    }

    public boolean undoLastMove(){
        if(!isLastMoveIsUndo){
            GameMove removedData =  traceGame.pollLastMove(getCurrentPlayer());
            int row = removedData.getX();
            int col = removedData.getY();
            board.resetCell(row,col);
            this.winningStrategy.removePlayer(row,col,getCurrentPlayer());
            isLastMoveIsUndo = true;
            return true;
        }
        return false;
    }

    public void resetGame(){
        board.resetBoard();
        winningStrategy.reset();
        traceGame.reset();
        isLastMoveIsUndo = false;
        lastMoveX = lastMoveY = 0;
        currentPlayerIndex = 0;
    }
}
