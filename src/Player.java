import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Player {

    private String name;
    private HashMap<String, Item> inventory = new HashMap<>();
    private Room currentRoom;
    private Room previousRoom;
    private final Stack<Room> roomStack = new Stack<>();

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public Player(String name) {
        this.name = name;
    }

    public Stack<Room> getRoomStack() {
        return roomStack;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    public void setPreviousRoom(Room previousRoom) {
        this.previousRoom = previousRoom;
    }
}
