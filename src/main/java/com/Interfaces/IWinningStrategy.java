package com.Interfaces;

import com.models.User;

public interface IWinningStrategy {

    boolean isWon(int x, int y, User player);

    void addPlayer(int x, int y, User player);

    void removePlayer(int x, int y, User player);

    void reset();
}
