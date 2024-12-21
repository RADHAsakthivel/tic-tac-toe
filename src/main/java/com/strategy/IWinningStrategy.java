package com.strategy;

import com.Models.User;

public interface IWinningStrategy {
    public boolean isWon(int x, int y, User player);
}
