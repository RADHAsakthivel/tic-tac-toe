package com.Interfaces;


import com.models.GameMove;
import com.models.User;

public interface ITraceGame {

    void saveMove(int x, int y, User player);

    void reset();

    GameMove pollLastMove(User player);

    GameMove getSpecificMove(int ithMove);

    int getLength();
}
