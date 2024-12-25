package com.controllers;

import com.service.BoardService;
import com.service.GameService;

import java.util.Scanner;

public class GameController {
    GameService gameService;
    BoardService boardService;
    int boardSize;

    public GameController(
            GameService _gameService,
            BoardService _boardService,
            int _boardSize
    ) {
        gameService = _gameService;
        boardService = _boardService;
        boardSize = _boardSize;
    }

    public void startGame(){
        gameService.renderBoard();
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter the row number " + gameService.getCurrentPlayer() + " : ");
            String rowInput = sc.next();
            System.out.println("Enter the column number " + gameService.getCurrentPlayer() + " : ");
            String columnInput = sc.next();

            if(rowInput.equalsIgnoreCase("UNDO") || columnInput.equalsIgnoreCase("UNDO")){
                boolean isUndoSuccess = gameService.undoLastMove();
                gameService.renderBoard();
                if(!isUndoSuccess){
                    System.out.println("Only one undo available for move");
                }
            }else{
                int row = Integer.parseInt(rowInput);
                int column = Integer.parseInt(columnInput);
                if(row >= boardSize || column >= boardSize || row < 0 || column < 0){
                    System.out.println("Invalid row number please enter the row and column number under " + boardSize);
                }else{
                    if(gameService.makeMove(row,column)){
                        gameService.renderBoard();
                        if(gameService.isWon(row,column,gameService.getCurrentPlayer())) {
                            System.out.println("Congratulations! You won!");
                            replayGame(gameService,sc);
                            if(resetGame(gameService,sc))continue;
                            break;

                        };
                        if(gameService.isDraw()) {
                            System.out.println("All the cells are fill hence the match is draw");
                            replayGame(gameService,sc);
                            if(resetGame(gameService,sc))continue;
                            break;
                        };
                        gameService.assignCurrentPlayer();
                    }
                }
            }
        }
        System.out.println("ThankYou for playing Tic-Tac-Toe byeeee :)!");
        System.exit(0);
    }



    private static boolean replayGame(GameService gameService, Scanner sc){
        System.out.println("Do you want to replay the game ?? Y ? N");
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        String replayInput = sc.nextLine();
        if(replayInput.equalsIgnoreCase("Y")){
            gameService.replayGame();
            return true;
        }
        return false;
    }

    private static boolean resetGame(GameService gameService, Scanner sc){
        System.out.println("Do you want to play again ?? Y ? N");
        String playAgainInput = sc.nextLine();
        if(playAgainInput.equalsIgnoreCase("Y")){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            gameService.resetGame();
            return true;
        }else{
            return false;
        }
    }
}
