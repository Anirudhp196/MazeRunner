=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: anip06
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. I used 2-D Arrays as the main maze in my project, where I had different game objects traversing around based
  on their position and implementation. I iterate through the array when I am setting each MazeElement at their
  specific positions based on the character in the maze text file in the PaintComponent method in Maze.java.
  Specifically, I use a Char array because it is easy to see what MazeElements go in certain positions, and I can
  easily represent a distinction between a wall and an empty square. The 2-D Array was the best way to represent the
  state of my game because there is a fixed number of rows and columns, and I could place characters in the array
  that are representative of the MazeElements I want to go there.

  2. I used FileIO for a big portion of my game, specifically when reading text files that represent the state of
  the maze. Maze0.txt, Maze1.txt and Maze2.txt are the 3 mazes I load into my game, and they each are comprised of
  ' ' and '#', which represent open spaces and walls along with characters representing a specific MazeElement in my
  game. My main "input" method is the setBoard() method in Maze.java that performs two file reads to load the entire
  state of the game into a 2-D array. Other than the wall and empty spaces, when we see a letter in the maze, we load
  the corresponding MazeElement into the maze, while the paintComponent() method actually shows the objects on the
  screen. For the "output" part, I have a saveGame and loadGame feature, where the user can save the game at any time
  and then can come back and load it up again on the next run. I implement this using two methods, saveGame() and
  loadGame(), by using a FileWriter to save the current game data, including state of maze, steps, lives, time
  and position of explorer, in a saved_game.txt file and then a FileReader to load it back up again when the button
  is pressed.

  3. I used Inheritance and Subtyping with all the various elements in my maze. My parent class, MazeElement, is an
  abstract class with a Location, Size, and Image. I have an abstract method move(), which takes in a key and the
  entire maze, which has no implementation in the parent class, as a general mazeElement doesn't need to move.
  For each of the bots, UpDownBot and SideToSideBotI implement the move() method based on their movement around the
  maze, which differs a lot because of their different movements. Additionally, I have an extensive move() method
  for the mazeExplorer, which is dependent on the direction of the image and the keys pressed while moving. I
  use dynamic dispatch extensively in my testing and when initializing all the different mazeElements, but I maintain
  an arraylist(with type MazeElement) of the "obstacles" in my maze that I iterate over to see if any of them intersect
  the main explorer. While labeling each element in my maze with type MazeElement, I still instantiate as its
  specific type that helps me when utilizing the correct move() method in the maze

  4. I used JUnit Testable Component to test the main state of my game by implementing tests on different bot
  movements, intersections and specific methods that are key to the reading of my game. Specifically, I tested the
  movement of the explorer, and two moving bots by creating temporary mazes and checking their location after
  every movement. They are supposed to bounce off walls, so I made sure this worked before implementing the movement
  into the maze. Furthermore, I tested the setBoard() function, especially with invalid files and edge cases that
  could mess up the renderring of the maze. I also tested collisions between the maze and finish line, as well as the
  maze and the bot to check if that key part of my game was working. Lastly, I tested loading and saving my game,
  which are key parts to check if certain information is saved and loaded in properly between runs and resetting.
  Ultimately, this testing helped my game run smoothly when actually implementing the code.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Location.java is used to store the location of any game object, with getters and setters as well as functions to
  increment their position in the maze. Regarding the overall game, this class is very helpful, as every single
  maze element has a Location loc, so we can get important information about their position in the maze at any time,
  and can also move or set it at different locations. Additionally, it has a equals() method, which is important when
  setting elements in the maze. MazeElement.java is an abstract class representing each element in the maze, including
  the explorer, stationary elements, and the 2 bots. This has a Location, size and an image associated with it, which
  helps when displaying each element in the maze. It has an intersects() function that checks if its location is equal
  to another mazeElement. Additionally, it has one function move(), that is implemented by all its subclasses.
  StationaryElement.java represents simple obstacles that don't move and extends MazeElement, and has no specific
  implementation for the move method, so it is very minimal. MazeExplorer.java extends MazeElement, and has a more
  extensive move() method that is based on the key pressed. Additionally, we set the different images for the explorer
  here, where the direction the explorer faces changes the image that gets painted on the screen. Additionally, we
  maintain a state for the steps here, so we can use it in our main class. SideToSideBot.java and UpDownBot,java are
  the two main bots in my maze and extend MazeElement, and each have implement the move() method to move side to side
  or up and down. Maze.java is the main class, and represents the main part of the screen. This loads in the maze with
  setBoard(), sets all the images in paintComponent() and handles all the collisions between the explorer and obstacles
  by maintaining a state of elements in the maze at all times. Additionally, this class has functionality for
  dispalying the end screen either if you win or die in the maze, and also for loading, saving or resetting the maze.
  Lastly, the RunMazeRunner() class is where I set the entire layout of my screen, where I have key information on the
  bottom, and the 4 buttons on the top. The main panel is simply an implementation of the maze Class, and is displayed
  in the center. I have a method to showInstructions(), which is a JDialog that pops up when the instructions button is
  pressed.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  When implementing my game, I had a very hard time loading in the board from the text file, as I kept on struggling
  with how I could correctly store all the data. I settled on doing two file reads, one to find the size, and then the
  other to actually fill the maze with characters. Furthermore, this led to challenges while drawing the maze in
  PaintComponent, as I had to handle each character individually. After completing the main logic of my game, I had a
  hard time actually putting it on the screen with the GUI, but looking at the mushroom game code helped with
  understanding how the layout works. Lastly, I had a hard time simply getting the explorer to move at first, so I spent a
  lot of time designing the move() methods, specifically getting the rotation and movement to sync up with the images.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think the design of the game is good, as I move a lot of the functionality to the other classes of my maze, so my
  main Maze class has very minimal code to get the main state of the game working. I think the private state is
  properly encapsulated, as all my fields in my game are private and can't be directly accessed at any time. If I could
  refactor, I would move some more game logic away from the Maze class, as it is currently handling game logic, input
  events and maze reading. I would do this by potentially creating a MazeLoader class that easily is able to load any
  maze given. I could also create a class to handle resetting, loading and saving the game, which will remove some of
  the bulk of the maze class. Additionally, I would add another bot that moves randomly, possibly using a path-finding
  algorithm that finds the quickest path from the bot to the explorer using graphs. This is an interesting idea that I
  could potentially implement in the future.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used
  while implementing your game.

  https://www.dcode.fr/maze-generator - Used for creating mazes
  https://www.geeksforgeeks.org/java-swing-jdialog-examples/ - Helped with instruction panel
  https://icon-icons.com/icon/stick-figure-brand-man-social/59323 - Explorer Icon
  https://www.pngegg.com/en/png-wgjxr - Green Ghost
  https://www.pinterest.com/pin/855121047968884679/ - Pink Ghost
  https://www.flaticon.com/free-icon/finish-line_1668532 - Finish Line
  https://www.w3schools.com/java/java_files_create.asp - Writing Files
  https://www.w3schools.com/java/java_files_read.asp - Reading Files



