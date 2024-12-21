package com.Service;

import com.Interfaces.TraceGameIteration;
import com.Models.User;

import java.util.*;

public class TraceGameService {
    private final List<List<Object>> gameMoves;
    public TraceGameService() {
        gameMoves = new LinkedList<>();
    }

    public void saveMove(int x, int y, User player){
        List<Object> currentMove = new ArrayList(3);
        currentMove.add(x);
        currentMove.add(y);
        currentMove.add(player);
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
    public List<Object> pollLastMove(User player){
        for(int i = this.gameMoves.size()-1; i >= 0; i--){
            List<Object> currentMove = (List<Object>) this.gameMoves.get(i);
            if(currentMove.get(2).equals(player)){
                this.gameMoves.remove(i);
                return currentMove;
            }
        }
        return null;
    }

    public void iterateRegular(TraceGameIteration iter){
        for( int i = 0; i < this.gameMoves.size(); i++){
            List<Object> move = this.gameMoves.get(i);
            iter.doAction(move,i);
        }
    }

    public void iterateReverse(TraceGameIteration iter){
        for( int i = this.gameMoves.size() - 1 ; i >= 0; i--){
            List<Object> move = this.gameMoves.get(i);
            iter.doAction(move,i);
        }
    }
}
