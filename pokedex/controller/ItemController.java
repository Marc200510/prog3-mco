package controller;

import model.Item;
import model.database.ItemDatabase;

import java.util.List;

/**
 * Controller for managing item operations
 * 
 * @author Your Name
 */
public class ItemController {
    private ItemDatabase itemDb;
    
    /**
     * Creates a new ItemController
     */
    public ItemController() {
        itemDb = ItemDatabase.getInstance();
    }
    
    /**
     * Gets all items in the database
     * 
     * @return A list of all items
     */
    public List<Item> getAllItems() {
        return itemDb.getAllItems();
    }
    
    /**
     * Searches for items by name
     * 
     * @param name The name to search for
     * @return A list of matching items
     */
    public List<Item> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return List.of();
        }
        return itemDb.searchByName(name);
    }
    
    /**
     * Searches for items by category
     * 
     * @param category The category to search for
     * @return A list of matching items
     */
    public List<Item> searchByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return List.of();
        }
        return itemDb.searchByCategory(category);
    }
    
    /**
     * Gets an item by its name
     * 
     * @param name The item's name
     * @return The item, or null if not found
     */
    public Item getItemByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return itemDb.getItem(name);
    }
    
    /**
     * Adds a new item to the database
     * 
     * @param name The item name
     * @param category The item category
     * @param description The item description
     * @param effect The item effect
     * @param buyingPrice The buying price
     * @param sellingPrice The selling price
     * @return true if the item was added, false if an item with the same name already exists
     */
    public boolean addItem(String name, String category, String description, String effect,
                          int buyingPrice, int sellingPrice) {
        // Validate input
        if (name == null || name.trim().isEmpty() || category == null || category.trim().isEmpty()) {
            return false;
        }
        
        Item item = new Item(name, category, description, effect, buyingPrice, sellingPrice);
        return itemDb.addItem(item);
    }
}