package com.Service;

import com.Interfaces.TraceGameIteration;
import com.Models.GameMove;
import com.Models.User;

import java.util.*;

public class TraceGameService {
    private final List<GameMove> gameMoves;
    public TraceGameService() {
        gameMoves = new LinkedList<>();
    }

    public void saveMove(int x, int y, User player){
//        List<Object> currentMove = new ArrayList(3);
//        currentMove.add(x);
//        currentMove.add(y);
//        currentMove.add(player);
        GameMove currentMove = new GameMove();
        currentMove.setX(x);
        currentMove.setY(y);
        currentMove.setUser(player);
        this.gameMoves.add(currentMove);
    }

    public void reset(){
        this.gameMoves.clear();
    }

    /**
     * This method will remove the last move of given user not the last move game
     * @param player of the games
     * @return true if the last move is successfully removed else return false
     */
    public GameMove pollLastMove(User player){
        for(int i = this.gameMoves.size()-1; i >= 0; i--){
            GameMove currentMove = this.gameMoves.get(i);
            if(currentMove.getUser().equals(player)){
                this.gameMoves.remove(i);
                return currentMove;
            }
        }
        return null;
    }

    public GameMove getSpecificMove(int ithMove){
        return gameMoves.get(ithMove);
    }

    public int getLength(){
        return this.gameMoves.size();
    }
}
