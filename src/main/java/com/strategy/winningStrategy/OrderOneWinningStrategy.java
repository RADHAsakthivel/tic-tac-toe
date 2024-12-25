package com.strategy.winningStrategy;

import com.Interfaces.IWinningStrategy;
import com.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderOneWinningStrategy implements IWinningStrategy {
    ArrayList<HashMap<Character,Integer>> rowList;
    ArrayList<HashMap<Character,Integer>> colList;
    HashMap<Character,Integer> diagonalList;
    HashMap<Character,Integer> antiDiagonalList;
    int size;

    public OrderOneWinningStrategy(int _size){
        this.size = _size;
        rowList = new ArrayList<>();
        colList = new ArrayList<>();
        diagonalList = new HashMap<>();
        antiDiagonalList = new HashMap<>();

        for(int i = 0; i < _size; i++){
            rowList.add(new HashMap<>());
            colList.add(new HashMap<>());
        }
    }

    @Override
    public void addPlayer(int x, int y, User player){
        HashMap<Character,Integer> row = rowList.get(x);
        HashMap<Character,Integer> col = colList.get(y);
        char playerSymbol = player.getSymbol();

        row.put(playerSymbol, row.getOrDefault(playerSymbol,0) + 1);
        col.put(playerSymbol, col.getOrDefault(playerSymbol,0) + 1);

        if(this.isDiagonal(x,y)){
            this.diagonalList.put(playerSymbol, diagonalList.getOrDefault(playerSymbol,0) + 1);
        }

        if(this.isAntiDiagonal(x,y)){
            this.antiDiagonalList.put(playerSymbol, antiDiagonalList.getOrDefault(playerSymbol,0) + 1);
        }
    }

    @Override
    public void removePlayer(int x, int y, User player){
        HashMap<Character,Integer> row = rowList.get(x);
        HashMap<Character,Integer> col = colList.get(y);
        char playerSymbol = player.getSymbol();
        int rowCount = row.getOrDefault(playerSymbol,0);
        int colCount = row.getOrDefault(playerSymbol,0);
        int diagonalCount = diagonalList.getOrDefault(playerSymbol,0);
        int antiDiagonalCount = antiDiagonalList.getOrDefault(playerSymbol,0);

        if(rowCount > 0) row.put(playerSymbol, rowCount - 1);
        if(colCount > 0) col.put(playerSymbol, rowCount - 1);

        if(this.isDiagonal(x,y) && diagonalCount > 0){
            this.diagonalList.put(playerSymbol, diagonalCount - 1);
        }

        if(this.isAntiDiagonal(x,y)){
            this.antiDiagonalList.put(playerSymbol, antiDiagonalCount - 1);
        }
    }

    @Override
    public boolean isWon(int x, int y, User player) {
        char playerSymbol = player.getSymbol();
        if(this.diagonalList.getOrDefault(playerSymbol,0) == size) return true;
        else if(this.antiDiagonalList.getOrDefault(playerSymbol,0) == size) return true;
        else if(this.rowList.get(x).getOrDefault(playerSymbol,0) == size) return true;
        else return this.colList.get(y).getOrDefault(playerSymbol,0) == size;
    }

    @Override
    public void reset(){
        this.rowList = new ArrayList<>();
        this.colList = new ArrayList<>();
        this.diagonalList = new HashMap<>();
        this.antiDiagonalList = new HashMap<>();

        for(int i = 0; i < this.size; i++){
            rowList.add(new HashMap<>());
            colList.add(new HashMap<>());
        }
    }

    private boolean isDiagonal(int row, int col){
        return row == col;
    }

    private boolean isAntiDiagonal(int row, int col){
        return row + col == this.size - 1;
    }
}
