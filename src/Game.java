import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player = new Player("Player 1", 10);
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, cellar;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the cellar... spooky...");
        
        // initialise room exits
        outside.setExits("east", theater);
        outside.setExits("south", lab);
        outside.setExits("west", pub);
        theater.setExits("west", outside);
        pub.setExits("east", outside);
        lab.setExits("north", outside);
        lab.setExits("east", office);
        office.setExits("west", lab);
        office.setExits("down", cellar);
        cellar.setExits("up", office);
        outside.addItems("tree", new Item("Tree", "a tall tree" , 150));
        outside.addItems("bush",new Item("Bush", "a bushy bush" , 12.6));
        outside.addItems("rock", new Item("Rock", "don't throw this", 0.2));
        theater.addItems("prop", new Item("Prop", "a prop for acting" ,6));
        pub.addItems("glass", new Item("Glass", "beer... tasty" , 0.5));
        pub.addItems("powder", new Item("Powder", "a mysterious white powder...", 0.01));
        lab.addItems("beaker", new Item("Beaker", "probably used for science stuff" , 0.5));
        lab.addItems("coat", new Item("Coat", "put this on to become a scientist" , 0.7));
        lab.addItems("goggles", new Item("Goggles", "safety first" , 0.2));
        office.addItems("desk", new Item("Desk", "I could write on this" , 15));
        office.addItems("chair", new Item("Chair", "I could sit on this" , 5));
        cellar.addItems("chest", new Item("Chest", "Spooooooky..." , 15));

        player.setCurrentRoom(outside);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look"))
        {
            look();
        }
        else if (commandWord.equals("eat"))
        {
            eat(command);
        }
        else if(commandWord.equals("back"))
        {
            back();
        }
        else if(commandWord.equals("take"))
        {
            take(command);
        }
        else if(commandWord.equals("drop"))
        {
            drop(command);
        }
        else if(commandWord.equals("items"))
        {
            items();
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setPreviousRoom(player.getCurrentRoom());
            player.getRoomStack().push(player.getPreviousRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    //looks around the room by printing a long description.
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private void eat(Command command)
    {
        String itemToEat = command.getSecondWord();
        String itemToEatLC = itemToEat.toLowerCase(Locale.ROOT);

        if (!command.hasSecondWord())
        {
            System.out.println("Eat what?");
        }
        if (itemToEatLC.equals("powder") && itemInInventory(itemToEatLC))
        {
            player.setCarryCapacity(player.getCarryCapacity()+10);
            player.getInventory().remove(itemToEatLC);
            System.out.println("Damn that's some good shit.");
            System.out.println("I feel so strong!!!");
        }
        else
        {
            System.out.println("You can't eat that.");
        }

    }

    private void back()
    {
        try {
            player.setCurrentRoom(player.getRoomStack().pop());
        }
        catch(EmptyStackException e) {
            System.out.println("Cannot go further back");
        }
        printLocationInfo();
    }

    private void take(Command command)
    {
        String itemTaken = command.getSecondWord();
        String itemTakenLC = itemTaken.toLowerCase(Locale.ROOT);
        HashMap<String, Item> itemsInRoom= player.getCurrentRoom().getItemsInRoom();
        Item pickedItem = itemsInRoom.get(itemTakenLC);

        if (!command.hasSecondWord())
        {
            System.out.println("Take what?");
        }
        if(itemInInventory(itemTakenLC)) {
            System.out.println("That item doesn't exist.");
        }
        else if(pickedItem != null && pickedItem.getItemWeight() <= player.getCarryCapacity()){
            player.getInventory().put(itemTakenLC, itemsInRoom.get(itemTakenLC));
            itemsInRoom.remove(itemTakenLC);
            player.setCarryCapacity(player.getCarryCapacity()-pickedItem.getItemWeight());
        }
        else
        {
            System.out.println("That item is too heavy");
        }

    }

    private void items()
    {
        double totalWeight = 0;
        for (String i: player.getInventory().keySet())
        {
            System.out.println(player.getInventory().get(i));
            double weight = player.getInventory().get(i).getItemWeight();
            totalWeight += weight;
        }
        System.out.printf("%1.27s","Total Weight of Items: " + totalWeight);
        System.out.println();
    }

    private void drop(Command command)
    {
        String itemDropped = command.getSecondWord();
        String itemDroppedLC = itemDropped.toLowerCase(Locale.ROOT);

        if (!command.hasSecondWord())
        {
            System.out.println("Drop what?");
        }
        if (!itemInInventory(itemDroppedLC))
        {
            System.out.println("You don't have that item");
        }
        else
        {
            Item itemWeDrop = player.getInventory().get(itemDroppedLC);
            player.getCurrentRoom().addItems(itemDroppedLC, itemWeDrop);
            player.setCarryCapacity(player.getCarryCapacity() + itemWeDrop.getItemWeight());
            player.getInventory().remove(itemDroppedLC);
        }
    }

    private boolean itemInInventory(String itemCheck)
    {
        if(player.getInventory().containsKey(itemCheck))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void printLocationInfo()
    {
        System.out.print(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }
}
