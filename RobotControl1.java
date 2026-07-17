package robotcontrol1;

public class RobotControl1 {

    public static void main(String[] args) {

        Robot robot = new Robot("WAKHU-BOT");

        GameMap gameMap = new GameMap();

        GUI _ = new GUI(robot, gameMap);

    }

}