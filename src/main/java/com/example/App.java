package com.example;
import com.Models.Board;
import com.Models.Game;
import com.Models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */

@Data
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        int size;
        ArrayList<User> users = new ArrayList<>();
        System.out.println("Enter the size of board : ");
        size = Integer.parseInt(sc.nextLine());
        System.out.println("NOTE : for undo please type undo in board you can only undo the lastmove");
        int j = 1;
        while(j <= 2){
            System.out.println("Enter the name of user number " + j + " : ");
            String userName = sc.nextLine();
            char symbol = userName.charAt(0);
            User user = new User();
            user.setName(userName);
            user.setSymbol(symbol);
            users.add(user);
            j++;
        }
        Board board = new Board(size);
        Game game = new Game(board,users);
        game.renderBoard();
        while(true){
            System.out.println("Enter the row number " + game.getCurrentPlayer() + " : ");
            String rowInput = sc.next();
            System.out.println("Enter the column number " + game.getCurrentPlayer() + " : ");
            String columnInput = sc.next();

            if(rowInput.equalsIgnoreCase("UNDO") || columnInput.equalsIgnoreCase("UNDO")){
                boolean isUndoSuccess = game.undoLastMove();
                game.renderBoard();
                if(!isUndoSuccess){
                    System.out.println("Only one undo available for move");
                }
            }else{
                int row = Integer.parseInt(rowInput);
                int column = Integer.parseInt(columnInput);
                if(row >= size || column >= size || row < 0 || column < 0){
                    System.out.println("Invalid row number please enter the row and column number under " + size);
                }else{
                    if(game.makeMove(row,column)){
                        game.renderBoard();
                        if(game.isWon(row,column,game.getCurrentPlayer())) {
                            System.out.println("Congratulations! You won!");
                            if(resetGame(game,sc))continue;
                            break;

                        };
                        if(game.isDraw()) {
                            System.out.println("All the cells are fill hence the match is draw");
                            if(resetGame(game,sc))continue;
                            break;
                        };
                        game.assignCurrentPlayer();
                    }
                }
            }
        }
    }

    private static boolean replayGame(Game game, Scanner sc){
        System.out.println("Do you want to replay the game ?? Y ? N");
        if (sc.hasNextLine()) {
            sc.nextLine(); // Consume leftover newline
        }
        String replayInput = sc.nextLine();
        if(replayInput.equalsIgnoreCase("Y")){
            game.replayGame();
            return true;
        }
        return false;
    }

    private static boolean resetGame(Game game, Scanner sc){
        System.out.println("Do you want to play again ?? Y ? N");
        String playAgainInput = sc.nextLine();
        if(playAgainInput.equalsIgnoreCase("Y")){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            game.resetGame();
            return true;
        }
        return false;
    }
}
