package org.cis1200.mazeRunner;

import org.cis1200.mazerunner.Location;
import org.cis1200.mazerunner.MazeElement;
import org.cis1200.mazerunner.MazeExplorer;
import org.cis1200.mazerunner.UpDownBot;
import org.cis1200.mazerunner.StationaryElement;
import org.cis1200.mazerunner.Maze;
import org.cis1200.mazerunner.SideToSideBot;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MazeRunnerTest {

    @Test
    public void testLocationEquals() {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 0);

        assertTrue(loc1.equals(loc2));
    }

    @Test
    public void createExplorer() {
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);

        assertEquals(3, explorer.getLives());
        assertEquals(0, explorer.getSteps());

        assertEquals(1, explorer.getLoc().getRow());
        assertEquals(1, explorer.getLoc().getCol());
    }

    @Test
    public void testExplorerMovement() {
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);
        char[][] maze = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', '#', ' '}
        };

        explorer.move(38, maze); //moving right
        assertEquals(1, explorer.getLoc().getRow());
        assertEquals(2, explorer.getLoc().getCol());
        assertEquals(1, explorer.getSteps());

        explorer.move(39, maze); //Looking down
        explorer.move(38, maze); //moving down
        assertEquals(2, explorer.getLoc().getRow());
        assertEquals(2, explorer.getLoc().getCol());
        assertEquals(2, explorer.getSteps());

        explorer.move(38, maze); //moving down should be blocked by wall
        assertEquals(2, explorer.getLoc().getRow());
        assertEquals(2, explorer.getLoc().getCol());
        assertEquals(2, explorer.getSteps());
    }

    @Test
    public void testExplorerIntersectsBot() {
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);
        MazeElement bot = new UpDownBot(new Location(1, 1), 30, "files/udb.png");

        assertTrue(explorer.intersects(bot));
    }

    @Test
    public void testExplorerLives() {
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);
        MazeElement bot = new UpDownBot(new Location(1, 1), 30, "files/udb.png");

        if (explorer.intersects(bot)) {
            explorer.setLives(explorer.getLives() - 1);
        }

        assertEquals(2, explorer.getLives());
    }

    @Test
    public void testExplorerCollision() {
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);
        StationaryElement obstacle = new StationaryElement(new Location(1, 2),
                30, "files/mushroom.png");

        assertFalse(explorer.intersects(obstacle));
        explorer.getLoc().incC(1);
        assertTrue(explorer.intersects(obstacle));
    }

    @Test
    public void testMazeInitialization() {
        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);

        assertEquals(3, explorer.getLives());
        assertEquals(0, explorer.getSteps());
        assertFalse(maze.isGameOver());
        assertFalse(maze.isGameWon());
    }

    @Test
    public void testSettingPosition() {
        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);
        maze.setBoard("files/maze0.txt");

        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);
        MazeElement finishLine = new StationaryElement(new Location(11, 17),
                30, "files/finish.png");
        explorer.getLoc().set(finishLine.getLoc().getRow(), finishLine.getLoc().getCol());
        maze.repaint();

        assertTrue(explorer.getLoc().equals(new Location(11, 17)));
    }

    @Test
    public void testSetBoardValid() {
        File tempMazeFile = new File("test_maze.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempMazeFile))) {
            writer.write("#####\n");
            writer.write("#E  #\n");
            writer.write("#  F#\n");
            writer.write("#####\n");
        } catch (IOException e) {
            System.out.println("Unable to write file");
        }

        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);
        maze.setBoard("test_maze.txt");

        char[][] expectedMaze = {
                {'#', '#', '#', '#', '#'},
                {'#', 'E', ' ', ' ', '#'},
                {'#', ' ', ' ', 'F', '#'},
                {'#', '#', '#', '#', '#'}
        };

        assertNotNull(maze.getExplorer());
        assertNotNull(maze.getFinish());

        assertArrayEquals(expectedMaze, maze.getMaze());

        assertEquals(expectedMaze.length, maze.getMaze().length);
        assertEquals(expectedMaze[0].length, maze.getMaze()[0].length);

        assertEquals(1, maze.getExplorer().getLoc().getRow());
        assertEquals(1, maze.getExplorer().getLoc().getCol());

        assertEquals(2, maze.getFinish().getLoc().getRow());
        assertEquals(3, maze.getFinish().getLoc().getCol());
    }

    @Test
    public void testSetBoardInvalid() {
        File tempMazeFile = new File("test_maze.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempMazeFile))) {
            writer.write("#####\n");
            writer.write("#    #\n");
            writer.write("#  F#\n");
            writer.write("#####\n");
        } catch (IOException e) {
            System.out.println("Unable to write file");
        }

        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> maze.setBoard("test_maze.txt"));
    }

    @Test
    public void testResetGame() {
        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);

        maze.setBoard("files/maze0.txt");
        maze.getExplorer().getLoc().set(2, 2);
        maze.getExplorer().setLives(1);
        maze.getExplorer().setSteps(5);

        maze.reset();

        assertEquals(1, maze.getExplorer().getLoc().getRow());
        assertEquals(1, maze.getExplorer().getLoc().getCol());

        assertEquals(3, maze.getExplorer().getLives());
        assertEquals(0, maze.getExplorer().getSteps());
    }

    @Test
    public void testBotInitialization() {
        UpDownBot udb = new UpDownBot(new Location(2, 2), 30, "files/udb.png");
        SideToSideBot s2s = new SideToSideBot(new Location(3, 3), 30, "files/sts.png");

        assertEquals(2, udb.getLoc().getRow());
        assertEquals(2, udb.getLoc().getCol());
        assertNotNull(udb.getImg());

        assertEquals(3, s2s.getLoc().getRow());
        assertEquals(3, s2s.getLoc().getCol());
        assertNotNull(s2s.getImg());
    }

    @Test
    public void testSideToSideBotMovement() {
        SideToSideBot s2s = new SideToSideBot(new Location(1, 1), 30, "files/sts.png");

        char[][] maze = {
                {'#', '#', '#', '#', '#'},
                {'#', ' ', ' ', ' ', '#'},
                {'#', '#', '#', '#', '#'}
        };

        assertEquals(1, s2s.getLoc().getRow());
        assertEquals(1, s2s.getLoc().getCol());

        s2s.move(0, maze);
        assertEquals(1, s2s.getLoc().getRow());
        assertEquals(2, s2s.getLoc().getCol());

        s2s.move(0, maze);
        assertEquals(1, s2s.getLoc().getRow());
        assertEquals(3, s2s.getLoc().getCol());

        s2s.move(0, maze);
        assertEquals(1, s2s.getLoc().getRow());
        assertEquals(3, s2s.getLoc().getCol());

        s2s.move(0, maze);
        assertEquals(1, s2s.getLoc().getRow());
        assertEquals(2, s2s.getLoc().getCol());

        s2s.move(0, maze);
        assertEquals(1, s2s.getLoc().getRow());
        assertEquals(1, s2s.getLoc().getCol());
    }

    @Test
    public void testUpDownBotMovement() {
        UpDownBot udb = new UpDownBot(new Location(1, 1), 30, "files/udb.png");

        char[][] maze = {
                {'#', '#', '#'},
                {'#', ' ', '#'},
                {'#', ' ', '#'},
                {'#', ' ', '#'},
                {'#', '#', '#'}
        };

        assertEquals(1, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());

        udb.move(0, maze);
        assertEquals(1, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());

        udb.move(0, maze);
        assertEquals(2, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());

        udb.move(0, maze);
        assertEquals(3, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());

        udb.move(0, maze);
        assertEquals(3, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());

        udb.move(0, maze);
        assertEquals(2, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());

        udb.move(0, maze);
        assertEquals(1, udb.getLoc().getRow());
        assertEquals(1, udb.getLoc().getCol());
    }

    @Test
    public void testSavingAndLoadingGame() {
        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);

        maze.getExplorer().getLoc().set(5, 5);
        maze.getExplorer().setLives(2);
        maze.getExplorer().setSteps(10);
        maze.saveGame();

        maze.reset();
        maze.loadGame();

        assertEquals(5, maze.getExplorer().getLoc().getRow());
        assertEquals(5, maze.getExplorer().getLoc().getCol());
        assertEquals(2, maze.getExplorer().getLives());
        assertEquals(10, maze.getExplorer().getSteps());
    }

    @Test
    public void testExplorerDirectionChange() {
        MazeExplorer explorer = new MazeExplorer(new Location(1, 1), 30);

        assertEquals(1, explorer.getDir());

        explorer.move(37, null);
        assertEquals(0, explorer.getDir());

        explorer.move(39, null);
        assertEquals(1, explorer.getDir());

        explorer.move(39, null);
        assertEquals(2, explorer.getDir());

        explorer.move(37, null);
        assertEquals(1, explorer.getDir());
    }

    @Test
    public void testMazeWithNoWalls() {
        File tempMazeFile = new File("test_maze.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempMazeFile))) {
            writer.write("     \n");
            writer.write("     \n");
            writer.write("     \n");
        } catch (IOException e) {
            System.out.println("Unable to write file");
        }
        JLabel statusLabel = new JLabel();
        JFrame frame = new JFrame();
        Maze maze = new Maze(statusLabel, frame);
        maze.setBoard("test_maze.txt");

        assertNotNull(maze.getExplorer());
        assertNotNull(maze.getFinish());
    }


}
