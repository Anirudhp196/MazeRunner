package org.cis1200.mazerunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Maze extends JPanel implements KeyListener, ActionListener {
    private JFrame frame;
    private JLabel statusLabel;
    private int size = 30;
    private char[][] maze;

    //Maze Elements
    private MazeExplorer explorer;
    private MazeElement finish;
    private MazeElement udb1, udb2, udb3, udb4;
    private StationaryElement mushroom1, mushroom2;
    private MazeElement sts1, sts2;

    private boolean isAlive; //Checks if bot has hit an obstacle/monster
    private boolean nextLevel = false; //Checks if a bot has reached the finish line

    private static boolean gameOver;
    private static boolean gameWon;

    private static boolean savedGame;

    int mazeCounter = 0; //Starts at Maze0

    private Timer t;
    private long startTime;
    static ArrayList<MazeElement> gameElements = new ArrayList<MazeElement>();

    public Maze(JLabel statusLabel, JFrame frame) {
        this.statusLabel = statusLabel;
        this.frame = frame;
        setBoard("files/maze0.txt");
        setFocusable(true);
        addKeyListener(this);
        SwingUtilities.invokeLater(this::requestFocusInWindow);

        isAlive = true;
        gameWon = false;
        explorer.setLives(3);
        t = new Timer(500, this);
        t.start();
        startTime = System.currentTimeMillis();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(maze[0].length * size + 60, maze.length * size + 150);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        gameElements.clear();

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                g.setColor(Color.GRAY);
                if (maze[r][c] == '#') {
                    g.fillRect(c * size + size, r * size + size, size, size); //Wall
                } else {
                    g.drawRect(c * size + size, r * size + size, size, size);  //Open
                }

                Location here = new Location(r, c); //Location setting for all the elements
                if (here.equals(finish.getLoc())) {
                    g.drawImage(finish.getImg(), c * size + size, r * size + size,
                            size, size, null);
                }
                if (here.equals(explorer.getLoc())) {
                    g.drawImage(explorer.getImg(), c * size + size, r * size + size,
                            size, size, null);
                }
                if (here.equals(udb1.getLoc())) {
                    g.drawImage(udb1.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(udb1);
                }
                if (here.equals(udb2.getLoc())) {
                    g.drawImage(udb2.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(udb2);
                }
                if (here.equals(udb3.getLoc())) {
                    g.drawImage(udb3.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(udb3);
                }
                if (here.equals(udb4.getLoc())) {
                    g.drawImage(udb4.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(udb4);
                }
                if (here.equals(mushroom1.getLoc())) {
                    g.drawImage(mushroom1.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(mushroom1);
                }
                if (here.equals(mushroom2.getLoc())) {
                    g.drawImage(mushroom2.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(mushroom2);
                }
                if (here.equals(sts1.getLoc())) {
                    g.drawImage(sts1.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(sts1);
                }
                if (here.equals(sts2.getLoc())) {
                    g.drawImage(sts2.getImg(), c * size + size, r * size + size,
                            size, size, null);
                    gameElements.add(sts2);
                }
            }
        }

        Long time = (System.currentTimeMillis() - startTime) / 1000;
        statusLabel.setText("Steps: " + explorer.getSteps() + "\tLives: " + explorer.getLives() +
                "\tTime: " + time);

        if (explorer.intersects(finish) && mazeCounter < 3) {
            t.setInitialDelay(1000);
            statusLabel.setText("LEVEL COMPLETED!!");
            nextLevel = true;
        }

        if (!isAlive) {
            explorer.getLoc().set(1, 1);
            isAlive = true;
        }

        if (gameWon) {
            showGameWonMessage(frame);
            return;
        }

        if (gameOver) {
            showGameOverMessage(frame);
            return;
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        explorer.move(e.getKeyCode(), maze);

        for (MazeElement m : gameElements) {
            if (explorer.intersects(m)) {
                t.setInitialDelay(1000);
                t.restart();
                explorerHit();
            }
        }

        if (explorer.intersects(finish)) { //Checks if explorer hits finish line
            statusLabel.setText("Level Complete!");
            t.setInitialDelay(2000);
            t.restart();
            mazeCounter++;
            if (mazeCounter == 1) {
                nextLevel = true; //Sets next level to true after finish line
                clear(); //See Method
                setBoard("files/maze1.txt");
            }
            if (mazeCounter == 2) {
                nextLevel = true;
                clear();
                setBoard("files/maze2.txt");
            }
            if (mazeCounter >= 3) {
                gameWon = true; //Sets game over screen after all
                // 3 mazes 0,1 and 2 have been completed
            }
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (!savedGame) {
            udb1.move(0, maze); //moving bots
            udb2.move(0, maze);
            udb3.move(0, maze);
            udb4.move(0, maze);

            sts1.move(0, maze);
            sts2.move(0, maze);
        }

        for (MazeElement bot : gameElements) {
            if (bot.intersects(explorer)) {
                t.setInitialDelay(1000);
                t.restart();
                explorerHit(); //See Method
            }
        }
        repaint();
    }

    public void explorerHit() {
        explorer.setLives(explorer.getLives() - 1);
        if (explorer.getLives() == 0) {
            gameOver = true;
            statusLabel.setText("Game Over");
        } else {
            statusLabel.setText("Collision Detected! Lives Left: " + explorer.getLives());
        }
        isAlive = false;
    }

    public void reset() {
        mazeCounter = 0;
        explorer.getLoc().set(1, 1);
        setBoard("files/maze0.txt"); // Reload the initial maze
        statusLabel.setText("Game Reset!");
        t.stop();
        startTime = System.currentTimeMillis();
        t.restart();
        explorer.setLives(3);
        explorer.setSteps(0);
        repaint();
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    public void clear() {
        this.revalidate();
        this.repaint();
    }

    public void setBoard(String fileName) {
        int row = 1;
        int col = 0;

        try {
            File file = new File(fileName);
            if (fileName == null) {
                throw new FileNotFoundException("File is Null");
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            line = br.readLine();
            col = line.length();
            while ((line = br.readLine()) != null) {
                if (col == line.length()) {
                    row++;
                } else {
                    throw new IOException("Rows are not all the same length");
                }
            }
        } catch (IOException e) {
            System.out.println("Error Reading The File");
        }

        char[][] temp = new char[row][col];
        int count = 0;
        try {
            File file = new File(fileName);
            if (fileName == null) {
                throw new FileNotFoundException("File is Null");
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                for (int k = 0; k < col; k++) {
                    temp[count][k] = line.charAt(k);
                    char symbol = line.charAt(k); //Setting all MazeElements based on chars in mazes
                    if (symbol == 'F') {
                        finish = new StationaryElement(new Location(count, k), size,
                                "files/finish.png");
                    }
                    if (symbol == 'E') {
                        if (explorer == null) {
                            explorer = new MazeExplorer(new Location(count, k), size);
                        } else {
                            explorer.getLoc().set(count, k);
                        }
                    }
                    if (symbol == 'U') {
                        udb1 = new UpDownBot(new Location(count, k), size, "files/udb.png");
                    }
                    if (symbol == 'V') {
                        udb2 = new UpDownBot(new Location(count, k), size, "files/udb.png");
                    }
                    if (symbol == 'W') {
                        udb3 = new UpDownBot(new Location(count, k), size, "files/udb.png");
                    }
                    if (symbol == 'X') {
                        udb4 = new UpDownBot(new Location(count, k), size, "files/udb.png");
                    }
                    if (symbol == 'C') {
                        mushroom1 = new StationaryElement(new Location(count, k),
                                size, "files/mushroom.png");
                    }
                    if (symbol == 'D') {
                        mushroom2 = new StationaryElement(new Location(count, k),
                                size, "files/mushroom.png");
                    }
                    if (symbol == 'S') {
                        sts1 = new SideToSideBot(new Location(count, k),
                                size, "files/sts.png");
                    }
                    if (symbol == 'T') {
                        sts2 = new SideToSideBot(new Location(count, k),
                                size, "files/sts.png");
                    }
                }
                count++;
            }

        } catch (IOException e) {
            System.out.println("Error Reading The File");
        }
        maze = temp;

        frame.setSize(getPreferredSize());
        frame.revalidate();
        repaint();
    }

    private void showGameOverMessage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        JLabel messageLabel = new JLabel("Game Over! You have lost all your lives.");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 36));
        messageLabel.setForeground(Color.WHITE);

        frame.add(messageLabel);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(800, 200);
        frame.setVisible(true);
    }

    private void showGameWonMessage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        JLabel messageLabel = new JLabel("Congrats! You have beat all 3 mazes");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 36));
        messageLabel.setForeground(Color.WHITE);

        frame.add(messageLabel);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(800, 200);
        frame.setVisible(true);
    }

    /* FOR TESTING PURPOSES */

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public MazeExplorer getExplorer() {
        return explorer;
    }

    public MazeElement getFinish() {
        return finish;
    }

    public char[][] getMaze() {
        return maze;
    }

    /* FOR TESTING PURPOSES */

    public void saveGame() {
        savedGame = true;
        try (FileWriter writer = new FileWriter("saved_game.txt")) {
            writer.write(mazeCounter + "\n");
            writer.write(maze.length + " " + maze[0].length + "\n");
            for (char[] row : maze) {
                writer.write(new String(row) + "\n");
            }

            writer.write(explorer.getLoc().getRow() + " " + explorer.getLoc().getCol() + "\n");
            writer.write(explorer.getLives() + "\n");
            writer.write(explorer.getSteps() + "\n");

            writer.write((System.currentTimeMillis() - startTime) + "\n");

            statusLabel.setText("Game Saved!");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to save game.");
        }
    }

    public void loadGame() {
        savedGame = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("saved_game.txt"))) {
            mazeCounter = Integer.parseInt(reader.readLine());
            setBoard("files/maze" + mazeCounter + ".txt");
            // Read maze dimensions

            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            // Load maze layout
            maze = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                maze[i] = reader.readLine().toCharArray();
            }

            // Load Explorer's state
            String[] explorerLoc = reader.readLine().split(" ");
            int explorerRow = Integer.parseInt(explorerLoc[0]);
            int explorerCol = Integer.parseInt(explorerLoc[1]);
            explorer.getLoc().set(explorerRow, explorerCol);

            explorer.setLives(Integer.parseInt(reader.readLine()));
            explorer.setSteps(Integer.parseInt(reader.readLine()));

            // Load other game states
            startTime = System.currentTimeMillis() - Long.parseLong(reader.readLine());

            setFocusable(true);
            requestFocusInWindow();

            repaint();
            statusLabel.setText("Game Loaded!");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to load game.");
        }
    }


}