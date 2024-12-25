package com.models;

import com.Interfaces.ITraceGame;
import com.Interfaces.IWinningStrategy;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Game {
    private Board board;
    private ArrayList<User> players;
    private int currentPlayerIndex;
    private boolean isLastMoveIsUndo;
    private int lastMoveX;
    private int lastMoveY;
    private final IWinningStrategy winningStrategy;
    private final ITraceGame traceGame;

    public Game(
            Board _board,
            ArrayList<User> _players,
            IWinningStrategy _winningStrategy,
            ITraceGame _traceGame
    ) {
        this.board = _board;
        this.players = _players;
        this.winningStrategy = _winningStrategy;
        this.traceGame = _traceGame;
    }
}
