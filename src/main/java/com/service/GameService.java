package com.service;

import com.Interfaces.ITraceGame;
import com.models.Board;
import com.models.Game;
import com.models.GameMove;
import com.models.User;

import java.text.MessageFormat;

public class GameService {
    BoardService boardService;
    Game game;
    Board board;

    public GameService(
            Game _game,
            BoardService _boardService
    ) {
        this.game = _game;
        this.boardService = _boardService;
        this.board = _game.getBoard();
    }

    public void renderBoard(){
        int sizeOfBoard = board.getSize();
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
                User currUser = boardService.getCell(i, j).getUser();
                char symbol = (currUser != null && currUser.getSymbol() != '\u0000') ? currUser.getSymbol() : '_';
                System.out.print(MessageFormat.format(" | {0}", symbol));
            }
            System.out.println(" |");
            System.out.println();
        }
    }

    public void replayGame(){
        int n = board.getSize();
        boardService.resetBoard();
        ITraceGame traceGame = game.getTraceGame();
        for(int move = 0; move < (n * n) && move < traceGame.getLength(); move++){
            GameMove currentMove = move < traceGame.getLength() ? traceGame.getSpecificMove(move) : null;
            System.out.println();
            System.out.println();
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    if(currentMove != null && currentMove.getX() == x && currentMove.getY() == y){
                        boardService.getCell(x, y).user = currentMove.getUser();
                        char symbol = currentMove.getUser().getSymbol();
                        System.out.print(MessageFormat.format(" | {0}", symbol));
                    }else{
                        User currUser = boardService.getCell(x, y).getUser();
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
        if(boardService.isCellOccupied(x,y)){
            System.out.println("the position is already occupied please choose another place");
            return false;
        }else{
            User currentPlayer = getCurrentPlayer();
            boardService.getCell(x, y).setUser(currentPlayer);
            game.getWinningStrategy().addPlayer(x,y,currentPlayer);
            game.getTraceGame().saveMove(x,y,currentPlayer);
            game.setLastMoveX(x);
            game.setLastMoveY(y);
            game.setLastMoveIsUndo(false);
            return true;
        }
    }

    public void assignCurrentPlayer(){
        game.setCurrentPlayerIndex(game.getCurrentPlayerIndex()+1);
        if(game.getPlayers().size() == game.getCurrentPlayerIndex()){
            game.setCurrentPlayerIndex(0);
        }
    }

    public User getCurrentPlayer(){
        return game.getPlayers().get(game.getCurrentPlayerIndex());
    }

    public boolean isWon(int x, int y, User player) {
        return game.getWinningStrategy().isWon(x,y,player);
    }

    public boolean isDraw(){
        return boardService.isFull();
    }

    public boolean undoLastMove(){
        if(!game.isLastMoveIsUndo()){
            GameMove removedData =  game.getTraceGame().pollLastMove(getCurrentPlayer());
            int row = removedData.getX();
            int col = removedData.getY();
            boardService.resetCell(row,col);
            game.getWinningStrategy().removePlayer(row,col,getCurrentPlayer());
            game.setLastMoveIsUndo(true);
            return true;
        }
        return false;
    }

    public void resetGame(){
        boardService.resetBoard();
        game.getWinningStrategy().reset();
        game.getTraceGame().reset();
        game.setLastMoveIsUndo(false);
        game.setLastMoveX(0);
        game.setLastMoveY(0);
        game.setCurrentPlayerIndex(0);
    }
}
