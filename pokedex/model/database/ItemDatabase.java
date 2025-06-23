package model.database;

import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Database for storing and retrieving item information
 * Implemented as a singleton
 * 
 * @author Your Name
 */
public class ItemDatabase {
    private static ItemDatabase instance;
    private List<Item> items;
    
    /**
     * Private constructor to enforce singleton pattern
     */
    private ItemDatabase() {
        items = new ArrayList<>();
        initializeDefaultItems();
    }
    
    /**
     * Gets the singleton instance of the ItemDatabase
     * 
     * @return The ItemDatabase instance
     */
    public static ItemDatabase getInstance() {
        if (instance == null) {
            instance = new ItemDatabase();
        }
        return instance;
    }
    
    /**
     * Sets the instance (primarily for testing purposes)
     * 
     * @param newInstance The new instance to set
     */
    public static void setInstance(ItemDatabase newInstance) {
        instance = newInstance;
    }
    
    /**
     * Gets the list of items
     * 
     * @return The list of items
     */
    public List<Item> getItems() {
        return items;
    }
    
    /**
     * Sets the list of items
     * 
     * @param items The list of items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    /**
     * Initializes the default items
     */
    private void initializeDefaultItems() {
        // Vitamins and Training Items
        addItem(new Item("HP Up", "Vitamin", "A nutritious drink for Pokémon.", 
                "+10 HP EVs", 10000, 5000));
        addItem(new Item("Protein", "Vitamin", "A nutritious drink for Pokémon.", 
                "+10 Attack EVs", 10000, 5000));
        addItem(new Item("Iron", "Vitamin", "A nutritious drink for Pokémon.", 
                "+10 Defense EVs", 10000, 5000));
        addItem(new Item("Carbos", "Vitamin", "A nutritious drink for Pokémon.", 
                "+10 Speed EVs", 10000, 5000));
        addItem(new Item("Zinc", "Vitamin", "A nutritious drink for Pokémon.", 
                "+10 Special Defense EVs", 10000, 5000));
        addItem(new Item("Rare Candy", "Leveling Item", "A candy that is packed with energy.", 
                "Increases level by 1 (stat gain depends on Pokémon's base stats and EVs)", 0, 2400));
        addItem(new Item("Health Feather", "Feather", "A feather that slightly increases HP.", 
                "+1 HP EV", 300, 150));
        addItem(new Item("Muscle Feather", "Feather", "A feather that slightly increases Attack.", 
                "+1 Attack EV", 300, 150));
        addItem(new Item("Resist Feather", "Feather", "A feather that slightly increases Defense.", 
                "+1 Defense EV", 300, 150));
        addItem(new Item("Swift Feather", "Feather", "A feather that slightly increases Speed.", 
                "+1 Speed EV", 300, 150));
        
        // Evolution Stones
        addItem(new Item("Fire Stone", "Evolution Stone", "A stone that radiates heat.", 
                "Evolves Pokémon like Vulpix, Growlithe, Eevee (into Flareon), etc.", 3000, 1500));
        addItem(new Item("Water Stone", "Evolution Stone", "A stone with a blue, watery appearance.", 
                "Evolves Pokémon like Poliwhirl, Shellder, Eevee (into Vaporeon), etc.", 3000, 1500));
        addItem(new Item("Thunder Stone", "Evolution Stone", "A stone that sparkles with electricity.", 
                "Evolves Pokémon like Pikachu, Eevee (into Jolteon), Eelektrik, etc.", 3000, 1500));
        addItem(new Item("Leaf Stone", "Evolution Stone", "A stone with a leaf pattern.", 
                "Evolves Pokémon like Gloom, Weepinbell, Exeggcute, etc.", 3000, 1500));
        addItem(new Item("Moon Stone", "Evolution Stone", "A stone that glows faintly in the moonlight.", 
                "Evolves Pokémon like Nidorina, Clefairy, Jigglypuff, etc.", 0, 1500));
        addItem(new Item("Sun Stone", "Evolution Stone", "A stone that glows like the sun.", 
                "Evolves Pokémon like Gloom (into Bellossom), Sunkern, Cottonee, etc.", 3000, 1500));
        addItem(new Item("Shiny Stone", "Evolution Stone", "A stone that sparkles brightly.", 
                "Evolves Pokémon like Togetic, Roselia, Minccino, etc.", 3000, 1500));
        addItem(new Item("Dusk Stone", "Evolution Stone", "A dark stone that is ominous in appearance.", 
                "Evolves Pokémon like Murkrow, Misdreavus, Doublade, etc.", 3000, 1500));
        addItem(new Item("Dawn Stone", "Evolution Stone", "A stone that sparkles like the morning sky.", 
                "Evolves male Kirlia into Gallade, female Snorunt into Froslass.", 3000, 1500));
        addItem(new Item("Ice Stone", "Evolution Stone", "A stone that is cold to the touch.", 
                "Evolves Pokémon like Alolan Vulpix, Galarian Darumaka, Eevee (into Glaceon)", 3000, 1500));
    }
    
    /**
     * Adds a new item to the database
     * 
     * @param item The item to add
     * @return true if the item was added, false if an item with the same name already exists
     */
    public boolean addItem(Item item) {
        // Check for duplicate names
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(item.getName())) {
                return false;
            }
        }
        
        items.add(item);
        return true;
    }
    
    /**
     * Gets all items in the database
     * 
     * @return A list of all items
     */
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }
    
    /**
     * Gets an item by its name
     * 
     * @param name The item's name
     * @return The item, or null if not found
     */
    public Item getItem(String name) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }
    
    /**
     * Searches for items by category
     * 
     * @param category The category to search for
     * @return A list of items with the specified category
     */
    public List<Item> searchByCategory(String category) {
        return items.stream()
                .filter(i -> i.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Searches for items by name (partial match)
     * 
     * @param nameQuery The name query
     * @return A list of items whose names contain the query
     */
    public List<Item> searchByName(String nameQuery) {
        return items.stream()
                .filter(i -> i.getName().toLowerCase().contains(nameQuery.toLowerCase()))
                .collect(Collectors.toList());
    }
}