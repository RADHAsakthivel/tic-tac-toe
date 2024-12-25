package com.ticTacToe;

import com.Interfaces.ITraceGame;
import com.Interfaces.IWinningStrategy;
import com.controllers.GameController;
import com.enums.TraceGameStrategyEnum;
import com.enums.WinningStrategyEnum;
import com.models.Board;
import com.models.Game;
import com.models.User;
import com.service.BoardService;
import com.service.GameService;
import com.strategy.traceGameStrategy.TraceGameStrategy;
import com.strategy.winningStrategy.WinningStrategy;
import lombok.Data;

import java.util.ArrayList;
import java.util.Scanner;

@Data
public class App 
{
    public static void main( String[] args ) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        int size;
        ArrayList<User> users = new ArrayList<>();
        System.out.println("Enter the size of board : ");
        size = Integer.parseInt(sc.nextLine());
        System.out.println("NOTE : for undo please type undo in board you can only undo the lastmove");
        int j = 1;
        while (j <= 2) {
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
        BoardService boardService = new BoardService(board);
        boardService.createBoard();
        IWinningStrategy winningStrategy = WinningStrategy.getWinningStrategy(WinningStrategyEnum.ORDER_ONE_STRATEGY, size);
        ITraceGame traceGameService = TraceGameStrategy.getTraceGameStrategy(TraceGameStrategyEnum.ORDER_ONE_TRACE_GAME);
        Game game = new Game(
                board,
                users,
                winningStrategy,
                traceGameService
        );
        GameService gameService = new GameService(game, boardService);
        GameController gameController = new GameController(gameService, boardService, size);
        gameController.startGame();
    }
}
