package com.Models;

import lombok.Data;

@Data
public class Cell {
    public User user;

    public void fillCell(User _user){
        this.user = _user;
    }

    public void emptyCell(){
        this.user = null;
    }

    public boolean isOccupied(){
        return this.user != null;
    }
}
