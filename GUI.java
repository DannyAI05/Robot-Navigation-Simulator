/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package robotcontrol1;

/**
 *
 * @author Hp
 */
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{//GUI is a JFrame
    
    private final Robot robot;
                          //Objects GUI will control
    private final GameMap gameMap;
    
    private final PathFinder pathFinder;
    
    private final JTextArea logArea;
    
    private final JProgressBar batteryBar;
    
    private final JProgressBar batteryProgress;
    
    
    private final JPanel mapPanel;
    
    private final JPanel controlPanel;
    
    private final JPanel statusPanel;
    
    
    //LABELS
    private final JLabel robotNameLabel;

    private final JLabel positionLabel;

    private final JLabel directionLabel;

    private final JLabel batteryLabel;

    private final JLabel movesLabel;

    private final JLabel statusLabel;
    
    private final JLabel turnsLabel;

    private final JLabel jumpsLabel;

    private final JLabel danceLabel;

    private final JLabel missionLabel;
    
    private final JLabel[][] cells = new JLabel[10][10];//Accessing the labels
    
    private final Font appFont = new Font("Segoe UI", Font.BOLD, 15);
    
    private final ImageIcon rockIcon = loadIcon("/robotcontrol1/rock.png");
    private final ImageIcon flagIcon = loadIcon("/robotcontrol1/flag.png");
    
    private final ImageIcon robotIcon =
    loadIcon("/robotcontrol1/robot.png");

private final ImageIcon idleIcon = robotIcon;
private final ImageIcon walkIcon = robotIcon;
private final ImageIcon chargeIcon = robotIcon;
private final ImageIcon danceIcon = robotIcon;
    
    
    private ImageIcon getRobotIcon(){

        return switch (robot.getStatus()) {
            case "Moving" -> walkIcon;
            case "Charging" -> chargeIcon;
            case "Dancing" -> danceIcon;
            default -> idleIcon;
        };

}
    
    //RESIZE ICONS
    private ImageIcon loadIcon(String path) {

    java.net.URL location = getClass().getResource(path);

    if (location == null) {
        throw new RuntimeException("Image not found: " + path);
    }

    ImageIcon icon = new ImageIcon(location);

    Image image = icon.getImage()
            .getScaledInstance(40, 40, Image.SCALE_SMOOTH);

    return new ImageIcon(image);
}
    
    
    private void refreshMap() {

    // 1. Clear every cell
    for (int row = 0; row < 10; row++) {

        for (int col = 0; col < 10; col++) {

            cells[row][col].setBackground(Color.WHITE);
            cells[row][col].setIcon(null);
            cells[row][col].setText("");
        }

    }

    // 2. Draw obstacles
    for (int[] obstacle : gameMap.getObstacles()) {

        int x = obstacle[0];
        int y = obstacle[1];

        cells[y][x].setIcon(rockIcon);
    }

    // 3. Draw target
    cells[gameMap.getTargetY()][gameMap.getTargetX()]
        .setIcon(flagIcon);
    
    // 4. Draw robot
    if(robot.getX() >= 0 &&
   robot.getX() < 10 &&
   robot.getY() >= 0 &&
   robot.getY() < 10){

    cells[robot.getY()][robot.getX()]
            .setIcon(getRobotIcon());

}
    
    updateStatusPanel();

}
    //UPDATING STATUS PANEL
    private void updateStatusPanel(){

    robotNameLabel.setText("🤖 " + robot.getName());

    positionLabel.setText("📍 Position : (" +
            robot.getX() + "," +
            robot.getY() + ")");

    directionLabel.setText("🧭 Direction : " +
            robot.getDirection());

    int battery = robot.getBattery();

    int bars = battery / 10;

    StringBuilder batteryBar1 = new StringBuilder();

    for(int i = 0; i < 10; i++){

        if(i < bars){

           batteryBar1.append("█");

    }

         else{

           batteryBar1.append("░");

    }

}

    batteryLabel.setText(
        "🔋 " + batteryBar1 + " " + battery + "%");

    movesLabel.setText("🚶 Moves : " +
            robot.getMoves());

    statusLabel.setText("📡 Status : " +
            robot.getStatus());
    
    
    if(robot.getBattery()<=20){

      batteryLabel.setForeground(Color.RED);

}
    else if(robot.getBattery()<=50){

      batteryLabel.setForeground(Color.ORANGE);

}
    else{

       batteryLabel.setForeground(Color.GREEN);

}

}
    
    private final Color backgroundColor =
        new Color(15,23,42);

    private final Color panelColor =
        new Color(30,41,59);

    private final Color buttonColor =
        new Color(59,130,246);

    private final Color cyanGlow =
        new Color(34,211,238);
    
    //STYLED BUTTONS
    private void styleButton(JButton button){

    button.setBackground(new Color(59,130,246));

    button.setForeground(Color.WHITE);

    button.setFocusPainted(false);

    button.setFont(new Font("Segoe UI", Font.BOLD,14));
    
    button.setBackground(buttonColor);

    button.setForeground(Color.WHITE);
    

}
    //MISSION RANKING
    private void showMissionResults(){

    String grade;

    if(robot.getBattery() >= 80){

        grade = "A+";

    }

    else if(robot.getBattery() >= 60){

        grade = "A";

    }

    else if(robot.getBattery() >= 40){

        grade = "B";

    }

    else{

        grade = "C";

    }

    JOptionPane.showMessageDialog(

            this,

            """
            \ud83c\udfc6 MISSION COMPLETE!
            
            Moves: """ + robot.getMoves() +

            "\nBattery Left: " +

            robot.getBattery() + "%" +

            "\nGrade: " + grade,

            "Mission Results",

            JOptionPane.INFORMATION_MESSAGE);
    
    
}
    
        
    //KEYBOARD CONTROLS
    private void registerKeyboardControls() {

    addKeyListener(new java.awt.event.KeyAdapter() {

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {

            switch(e.getKeyCode()){

                case java.awt.event.KeyEvent.VK_W -> {
                    robot.moveForward();
                    refreshMap();
                }

                case java.awt.event.KeyEvent.VK_A -> {
                    robot.turnLeft();
                    refreshMap();
                }

                case java.awt.event.KeyEvent.VK_D -> {
                    robot.turnRight();
                    refreshMap();
                }

                case java.awt.event.KeyEvent.VK_R -> {
                    robot.charge();
                    refreshMap();
                }
            }

        }

    });

}
    
    
    //CONSTRUCTOR
    public GUI(Robot robot, GameMap gameMap){

        //INITIALIZATION
    this.robot = robot;
                          //Stores the objects Main.java created
    this.gameMap = gameMap;
    
    this.pathFinder = new PathFinder(gameMap);
    
    
    logArea = new JTextArea();
    
    batteryBar = new JProgressBar();
    
    logArea.append("Moved Forward\n");
    
    batteryBar.setValue(robot.getBattery());
    
    
    
    if(gameMap.reachedTarget(robot)){

    showMissionResults();

}
    
    batteryProgress = new JProgressBar(0,100);

    batteryProgress.setStringPainted(true);

    batteryProgress.setValue(robot.getBattery());
    
    batteryProgress.setValue(robot.getBattery());

if(robot.getBattery()>50){

    batteryProgress.setForeground(Color.GREEN);

}
else if(robot.getBattery()>20){

    batteryProgress.setForeground(Color.ORANGE);

}
else{

    batteryProgress.setForeground(Color.RED);

}

    
    
    
    //WINDOW
    setTitle("🤖 WAKHU Robot Control Simulator");
    
    setSize(900,700);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLocationRelativeTo(null);

    setLayout(new BorderLayout());
    
    setFocusable(true);
    
    requestFocusInWindow();

    
    //GRID
    mapPanel = new JPanel();
    
    controlPanel = new JPanel();
    
    statusPanel = new JPanel();
    
    mapPanel.setLayout(new GridLayout(10,10));

    statusPanel.setLayout(new GridLayout(6,1,5,5));
    
    mapPanel.setBackground(new Color(30,30,30));
    
    statusPanel.setBackground(new Color(25,25,25));
    
    controlPanel.setBackground(new Color(25,25,25));
    
    
    
    statusPanel.setBorder(BorderFactory.createTitledBorder(

        BorderFactory.createLineBorder(Color.GRAY),

        "Robot Status"

    ));
    
    controlPanel.setBorder(BorderFactory.createTitledBorder(

        BorderFactory.createLineBorder(Color.GRAY),

        "Controls"

    ));
    
    for(int row = 0; row < gameMap.getSize(); row++){

    for(int col = 0; col < gameMap.getSize(); col++){

        JLabel cell = new JLabel("",SwingConstants.CENTER);
        
        cell.setPreferredSize(new Dimension(55,55));

        cell.setOpaque(true);



        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        mapPanel.add(cell);
        
       cells[row][col] = cell; //Saves the labels
       
       
    }
    add(controlPanel, BorderLayout.SOUTH);
       
    add(statusPanel, BorderLayout.EAST);

}
    
    getContentPane().setBackground(backgroundColor);

    mapPanel.setBackground(panelColor);

    controlPanel.setBackground(panelColor);

    statusPanel.setBackground(panelColor);
    
    
    //BUTTON CREATION
    JButton moveButton = new JButton("⬆ Move");

    JButton leftButton = new JButton("⬅ Left");

    JButton rightButton = new JButton("➡ Right");

    JButton stopButton = new JButton("■ Stop");

    JButton danceButton = new JButton("💃 Dance");

    JButton jumpButton = new JButton("🦘 Jump");

    JButton chargeButton = new JButton("🔋 Charge");

    JButton resetButton = new JButton("🔄 Reset");
    
    JButton autoButton = new JButton("🤖 Auto");

    add(mapPanel,BorderLayout.CENTER);
    

    controlPanel.setLayout(new GridLayout(2,4,10,10));//Spacing btn buttons
    
    controlPanel.add(moveButton);

    controlPanel.add(leftButton);

    controlPanel.add(rightButton);

    controlPanel.add(stopButton);

    controlPanel.add(danceButton);

    controlPanel.add(jumpButton);

    controlPanel.add(chargeButton);

    controlPanel.add(resetButton);
    
    controlPanel.add(autoButton);
    
    
    
    //GUI EVENTS
    moveButton.addActionListener(e -> {

    robot.moveForward(gameMap);

    refreshMap();

    updateStatusPanel();

    if(robot.getStatus().equals("Obstacle Detected")){

        JOptionPane.showMessageDialog(
            this,
            "🚧 Obstacle Ahead!"
        );

    }

    else if(robot.getStatus().equals("Boundary Reached")){

        JOptionPane.showMessageDialog(
            this,
            "🗺 You reached the edge of the map!"
        );

    }

    if(gameMap.reachedTarget(robot)){

        showMissionResults();

    }

});
    
    leftButton.addActionListener(e -> {

    robot.turnLeft();

    refreshMap();
    
    updateStatusPanel();

});
    
    rightButton.addActionListener(e -> {

    robot.turnRight();

    refreshMap();
    
    updateStatusPanel();

});
    
    chargeButton.addActionListener(e -> {

    robot.charge();
    
    refreshMap();
    
    updateStatusPanel();

});
    
    autoButton.addActionListener(e -> {

    java.util.List<Point> path =
            pathFinder.findPath(
                    robot.getX(),
                    robot.getY(),
                    gameMap.getTargetX(),
                    gameMap.getTargetY()
            );

    System.out.println(path);

});
    
    robotNameLabel = new JLabel();

    positionLabel = new JLabel();

    directionLabel = new JLabel();

    batteryLabel = new JLabel();

    movesLabel = new JLabel();

    statusLabel = new JLabel();
    
    //ADDING LABELS
    statusPanel.add(robotNameLabel);

    statusPanel.add(positionLabel);

    statusPanel.add(directionLabel);

    statusPanel.add(batteryLabel);

    statusPanel.add(movesLabel);

    statusPanel.add(statusLabel);
    
    //FONTS
    robotNameLabel.setFont(appFont);

    positionLabel.setFont(appFont);

    directionLabel.setFont(appFont);

    batteryLabel.setFont(appFont);

    movesLabel.setFont(appFont);

    statusLabel.setFont(appFont);
    
    //WHITE TEXT
    robotNameLabel.setForeground(Color.WHITE);

    positionLabel.setForeground(Color.WHITE);

    directionLabel.setForeground(Color.WHITE);

    batteryLabel.setForeground(Color.WHITE);

    movesLabel.setForeground(Color.WHITE);

    statusLabel.setForeground(Color.WHITE);
    
    robotNameLabel.setForeground(cyanGlow);
    
    
    styleButton(moveButton);

    styleButton(leftButton);

    styleButton(rightButton);

    styleButton(stopButton);

    styleButton(danceButton);

    styleButton(jumpButton);

    styleButton(chargeButton);

    styleButton(resetButton);
    
    styleButton(autoButton);
    
    turnsLabel = new JLabel();
    jumpsLabel = new JLabel();
    danceLabel = new JLabel();
    missionLabel = new JLabel();
    
    
    turnsLabel.setText(
        "↩ Turns : " +
        robot.getTurns());

    jumpsLabel.setText(
        "🦘 Jumps : " +
        robot.getJumps());

    danceLabel.setText(
        "💃 Dances : " +
        robot.getDances());

    missionLabel.setText(
        "🏆 Missions : " +
        robot.getMissions());
    
    
    
    refreshMap();
    
    registerKeyboardControls();
    
    
    setVisible(true);

}
    
}