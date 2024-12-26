package org.cis1200.mazerunner;


import javax.swing.*;
import java.awt.*;

public class RunMazeRunner implements Runnable {
    @Override
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Maze Runner");
        frame.setLocation(300, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel statusLabel = new JLabel("Running...");
        status_panel.add(statusLabel);

        // Main playing area
        final Maze maze = new Maze(statusLabel, frame);
        frame.add(maze, BorderLayout.CENTER);

        final JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.NORTH);

        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> maze.reset()); // Reset action
        controlPanel.add(resetButton); // Add button to the control panel

        final JButton instructionButton = new JButton("Instructions");
        instructionButton.addActionListener(e -> showInstructions(frame, maze));
        controlPanel.add(instructionButton);

        final JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> maze.saveGame());
        controlPanel.add(saveButton);

        final JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(e -> maze.loadGame());
        controlPanel.add(loadButton);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        maze.reset();
    }

    private void showInstructions(JFrame screen, Maze maze) {
        JDialog instructions = new JDialog(screen, "Instructions", true);
        instructions.setLocationRelativeTo(screen);
        instructions.setSize(800, 500);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JTextPane instructionText = new JTextPane();
        instructionText.setText("Welcome To Maze Runner!! \n \n" + "In this game, you will be " +
                "using the arrow keys to navigate your explorer around the maze. \nThe " +
                "left and right arrow keys change the direction of the explorer, while the up \n" +
                "arrow is used to move the explorer in that direction. There will be many " +
                "obstacles \n" +
                "and bots moving around the maze, and it is your job to avoid them " +
                "and reach the finish line. \n\n" +
                "You only have 3 lives, so be careful and try to complete all mazes. \n" +
                "Click the X button to close this window and return to the game. ");

        instructionText.setFont(new Font("Serif", Font.BOLD, 16));
        instructionText.setBackground(new Color(243, 219, 219));
        instructionText.setMargin(new Insets(10, 10, 10, 10));
        instructionText.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(100, 149, 237));

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Serif", Font.BOLD, 16));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(Color.WHITE);
        closeButton.setOpaque(true);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(e -> {
            instructions.dispose();
            maze.requestFocusInWindow();
        });

        buttonPanel.add(closeButton);

        mainPanel.add(new JScrollPane(instructionText), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        instructions.add(mainPanel);
        instructions.setVisible(true);
    }
}
