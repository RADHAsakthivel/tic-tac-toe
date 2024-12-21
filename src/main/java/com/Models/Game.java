package com.Models;

import com.Service.TraceGameService;
import com.strategy.WinningStrategy;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
                char symbolOfCurUser = (currUser != null && currUser.getSymbol() != '\u0000') ? currUser.getSymbol() : '_';
                System.out.print(MessageFormat.format(" | {0}", symbolOfCurUser));
            }
            System.out.println(" |");
            System.out.println();
        }
    }

    public void replayGame(){
        AtomicInteger j = new AtomicInteger(1);
        this.traceGame.iterateRegular((item,index) -> {
            if(j.get() == this.board.size){
                System.out.print(MessageFormat.format(" | {0} |", item.get(2)));
                System.out.println();
                j.set(0);
            }else {
                System.out.print(MessageFormat.format(" | {0}", item.get(2)));
            }
        });
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
            List<Object> removedData =  traceGame.pollLastMove(getCurrentPlayer());
            int row = Integer.parseInt(removedData.get(0).toString());
            int col = Integer.parseInt(removedData.get(1).toString());
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
    }
}
