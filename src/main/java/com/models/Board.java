package com.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Board {
    ArrayList<ArrayList<Cell>> cells;
    int size;

    public Board(int _size) {
        this.size = _size;
    }
}
