package robotcontrol1;
public class Robot{
    
    //ENCAPSULATION
    private final String name;
    
    private int x;
    
    private int y;
    
    private int turns;

    private int jumps;

    private int dances;

    private int missions;
    
    private int battery;
    
    private String direction;
    
    private String status;
    
    private int moves;
    
    private int missionsCompleted;

    public int getTurns() {
        return turns;
    }

    public int getJumps() {
        return jumps;
    }

    public int getDances() {
        return dances;
    }

    public int getMissions() {
        return missions;
    }
    
    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBattery() {
        return battery;
    }

    public String getDirection() {
        return direction;
    }

    public String getStatus() {
        return status;
    }

    public int getMoves() {
        return moves;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void moveForward(GameMap gameMap){

    int newX = x;
    int newY = y;

    switch(direction){

        case "North" -> newY--;

        case "South" -> newY++;

        case "East"  -> newX++;

        case "West"  -> newX--;

    }

    // Check map boundary
    if(!gameMap.isInsideMap(newX, newY)){

        status = "Boundary Reached";
        return;

    }

    // Check obstacle
    if(gameMap.isObstacle(newX, newY)){

        status = "Obstacle Detected";
        return;

    }

    // Safe to move
    x = newX;
    y = newY;

    battery -= 2;
    moves++;
    status = "Moving";
}
    
    public void turnLeft(){

    switch(direction){

        case "North" -> direction = "West";

        case "West" -> direction = "South";

        case "South" -> direction = "East";

        case "East" -> direction = "North";

    }

    battery--;
    
    turns++;

    status = "Turning Left";

}
    
    public void turnRight(){

    switch(direction){

        case "North" -> direction = "West";

        case "West" -> direction = "South";

        case "South" -> direction = "East";

        case "East" -> direction = "North";

    }

    battery--;

    status = "Turning Right";

}
    
    public void charge(){

    battery = 100;

    status = "Charging";

}
    
    public void dance(){

    battery -= 10;
    
    dances++;

    status = "Dancing";

}
    
    public void jump(){

    battery -= 5;
    
    jumps++;

    status = "Jumping";

}
    public void mission(){
        missions++;
    }
    
    public void stop(){

    status = "Stopped";

}
    
    
    public String speak(){

        return switch (status) {
            case "Moving" -> "Moving to destination...";
            case "Charging" -> "Battery charging.";
            case "Idle" -> "Awaiting command.";
            case "Dancing" -> "Enjoying the beat!";
            case "Jumping" -> "Jump complete!";
            default -> "Ready.";
        };
        
        

}
    
    //POSITION METHOD
    public void setPosition(int x, int y){

    this.x = x;
    this.y = y;

    battery -= 2;
    moves++;
    status = "Autonomous Navigation";

}
    
    public void faceDirection(int newX, int newY){

    if(newX > x){

        direction = "East";

    }

    else if(newX < x){

        direction = "West";

    }

    else if(newY > y){

        direction = "South";

    }

    else if(newY < y){

        direction = "North";

    }

}
    
    
    //CONSTRUCTOR
    public Robot(String name){
        
        this.name = name;
        
        x = 0;
        
        y = 0;
        
        battery = 100;
        
        direction = "North";
        
        status= "Idle";
        
        moves= 0;
        
        missionsCompleted++;
    }
    
    
}
