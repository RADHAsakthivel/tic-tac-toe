package com.Models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Board {
    ArrayList<ArrayList<Cell>> cells;
    int size;

    public Board(int _size) {
        this.size = _size;
    }

    public void resetBoard() {
        for(ArrayList<Cell> row : cells){
            for(Cell cell : row){
                cell.user = null;
            }
        }
    }

    public ArrayList<ArrayList<Cell>> createBoard() {
        cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < size; j++)
                row.add(new Cell());
            cells.add(row);
        }
        return this.cells;
    }

    public boolean isFull() {
        int i = 0;
        while (i < cells.size()) {
            ArrayList<Cell> row = cells.get(i);
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
        return cells.get(x).get(y);
    }

    public boolean resetCell(int x, int y) {
        ArrayList<Cell> row = cells.get(x);
        Cell cell = row.get(y);
        cell.user = null;
        return cells.get(x).get(y) == null;
    }
}
