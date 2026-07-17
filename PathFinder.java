/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package robotcontrol1;

/**
 *
 * @author Hp
 */
import java.awt.Point;//Store coordinates
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;//Return the final path
import java.util.Queue;//BFS Exploration
public class PathFinder {
    
    int[] dx = {0,0,-1,1};

    int[] dy = {-1,1,0,0};
    

    
    //PARENT FIELD
    private static class Node{

    int x;
    int y;
    Node parent;

    public Node(int x, int y, Node parent){

        this.x = x;
        this.y = y;
        this.parent = parent;

    }

}
    
    private final GameMap gameMap;

    public PathFinder(GameMap gameMap){

        this.gameMap = gameMap;

    }
    
    
    //BFS Method
    public List<Point> findPath(int startX, int startY,
                            int goalX, int goalY){
        
        boolean[][] visited =
        new boolean[10][10];

    Queue<Node> queue =
        new LinkedList<>();

    queue.add(new Node(startX,startY,null));

    visited[startY][startX] = true;
    
    while(!queue.isEmpty()){

    Node current = queue.poll();

    if(current.x == goalX &&
       current.y == goalY){

        return buildPath(current);

    }

    for(int i=0;i<4;i++){

        int newX = current.x + dx[i];

        int newY = current.y + dy[i];

        if(gameMap.isInsideMap(newX,newY)
                &&
           !gameMap.isObstacle(newX,newY)
                &&
           !visited[newY][newX]){

            visited[newY][newX] = true;

            queue.add(
                new Node(
                    newX,
                    newY,
                    current
                )
            );

        }

    }

}
    
    return new ArrayList<>();
    }
    
    
    //Build Path
    private List<Point> buildPath(Node goal){

    List<Point> path =
            new ArrayList<>();

    Node current = goal;

    while(current != null){

        path.add(0,
                new Point(
                        current.x,
                        current.y));

        current = current.parent;

    }

    return path;

}
    
    
    
        



    
}
