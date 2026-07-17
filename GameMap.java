package robotcontrol1;

import java.util.Random;

public class GameMap {

    private final int SIZE = 10;

    private int targetX;

    private int targetY;

    private final int[][] obstacles;
    
    
    
    //CONSTRUCTOR:Targets and Obstacles
    public GameMap(){

    targetX = 8;

    targetY = 8;

    obstacles = new int[][]{

        {2,3},
        {4,5},
        {6,1},
        {7,7},
        {1,8}

    };

}
 
    //COLLISION DETECTION
    public boolean isObstacle(int x,int y){

    for(int[] obstacle : obstacles){

        if(obstacle[0]==x && obstacle[1]==y){

            return true;

        }

    }

    return false;

}
  
    //MAP BOUNDARY
    public boolean isInsideMap(int x, int y){

    return x >= 0 &&
           x < SIZE &&
           y >= 0 &&
           y < SIZE;

}
    
    //MISSION COMPLETE
    public boolean reachedTarget(Robot robot){

    return robot.getX()==targetX &&
           robot.getY()==targetY;

}
    
    //MAP DRAWING
    public void displayMap(Robot robot){

    System.out.println("\n========== MAP ==========");

    for(int row=0; row<SIZE; row++){

        for(int col=0; col<SIZE; col++){

            if(robot.getX()==col &&
               robot.getY()==row){

                System.out.print("🤖 ");

            }

            else if(targetX==col &&
                    targetY==row){

                System.out.print("🚩 ");

            }

            else if(isObstacle(col,row)){

                System.out.print("🪨 ");

            }

            else{

                System.out.print(". ");

            }

        }

        System.out.println();

    }

    System.out.println("=========================");
}
    
    //RESET MAP
    public void reset(){

    targetX = random.nextInt(SIZE);
targetY = random.nextInt(SIZE);
}
    
    public int getSize() {
        return SIZE;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    } 

    public int[][] getObstacles() {
        return obstacles;
    }
    private final Random random = new Random();

}

