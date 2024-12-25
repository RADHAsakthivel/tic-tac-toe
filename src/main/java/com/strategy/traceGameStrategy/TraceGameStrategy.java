package com.strategy.traceGameStrategy;

import com.enums.TraceGameStrategyEnum;
import com.Interfaces.ITraceGame;

public class TraceGameStrategy {
    public  static ITraceGame getTraceGameStrategy(TraceGameStrategyEnum traceGameStrategyEnum){
        if(TraceGameStrategyEnum.ORDER_ONE_TRACE_GAME == traceGameStrategyEnum){
            return  new OrderOneTraceGameStrategy();
        }
        return null;
    }
}
//    private final List<GameMove> gameMoves;
//    public TraceGameStrategy() {
//        gameMoves = new LinkedList<>();
//    }
//
//    /**
//     * Will store the player of current move in trace array
//     * @param x int row
//     * @param y int column
//     * @param player user or player of current move
//     */
//    @Override
//    public void saveMove(int x, int y, User player){
//        GameMove currentMove = new GameMove();
//        currentMove.setX(x);
//        currentMove.setY(y);
//        currentMove.setUser(player);
//        this.gameMoves.add(currentMove);
//    }
//
//    /**
//     * Remove all trace element which stored in from trace array
//     */
//    @Override
//    public void reset(){
//        this.gameMoves.clear();
//    }
//
//    /**
//     * This method will remove the last move of given user not the last move game
//     * @param player of the games
//     * @return GameMove
//     */
//    @Override
//    public GameMove pollLastMove(User player){
//        for(int i = this.gameMoves.size()-1; i >= 0; i--){
//            GameMove currentMove = this.gameMoves.get(i);
//            if(currentMove.getUser().equals(player)){
//                this.gameMoves.remove(i);
//                return currentMove;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * This method will retrieve the specific move from trace array
//     * @param ithMove index of specific move to locate the trace array
//     * @return will return the GameMove object according to input
//     */
//    @Override
//    public GameMove getSpecificMove(int ithMove){
//        return gameMoves.get(ithMove);
//    }
//
//    /**
//     * Returns the length of the trace array
//     * @return Returns the length of the trace array
//     */
//    @Override
//    public int getLength(){
//        return this.gameMoves.size();
//    }
//}
