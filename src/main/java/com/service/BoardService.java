package com.service;

import com.models.Board;
import com.models.Cell;

import java.util.ArrayList;

public class BoardService {
    Board board;

    public BoardService(Board board) {
        this.board = board;
    }

    public void resetBoard() {
        for(ArrayList<Cell> row : board.getCells()){
            for(Cell cell : row){
                cell.user = null;
            }
        }
    }

    public ArrayList<ArrayList<Cell>> createBoard() {
        board.setCells(new ArrayList<>());
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < size; j++)
                row.add(new Cell());
            board.getCells().add(row);
        }
        return board.getCells();
    }
//
    public boolean isFull() {
        int i = 0;
        while (i < board.getCells().size()) {
            ArrayList<Cell> row = board.getCells().get(i);
            for(Cell cell : row){
                if(cell.user == null){
                    return false;
                }
            }
            i++;
        }
        return true;
    }

    public Cell getCell(int x, int y) {
        return board.getCells().get(x).get(y);
    }

    public boolean isCellOccupied(int x, int y){
        return board.getCells().get(x).get(y).getUser() != null;
    }

    public boolean resetCell(int x, int y) {
        ArrayList<Cell> row = board.getCells().get(x);
        Cell cell = row.get(y);
        cell.user = null;
        return board.getCells().get(x).get(y) == null;
    }
}
