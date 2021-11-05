import java.util.Stack;

public class Player {

    private String name;
    private Item heldItem;
    private Room currentRoom;
    private Room previousRoom;
    private final Stack<Room> roomStack = new Stack<>();

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
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
