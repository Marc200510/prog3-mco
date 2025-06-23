package controller;

import model.Move;
import model.Type;
import model.database.MoveDatabase;

import java.util.List;

/**
 * Controller for managing move operations
 * 
 * @author Your Name
 */
public class MoveController {
    private MoveDatabase moveDb;
    
    /**
     * Creates a new MoveController
     */
    public MoveController() {
        moveDb = MoveDatabase.getInstance();
    }
    
    /**
     * Imports all moves from the CSV file
     * 
     * @return The number of moves loaded
     */
    public int importMovesFromCSV() {
        return moveDb.importFromCSV();
    }
    
    /**
     * Adds a new move to the database
     * 
     * @param name The move name
     * @param description The move description
     * @param classification The move classification
     * @param type The move type
     * @param kind The move kind
     * @param power The move power
     * @param accuracy The move accuracy
     * @param pp The move PP
     * @return true if the move was added, false if a move with the same name already exists
     */
    public boolean addMove(String name, String description, String classificationStr, 
                          String typeStr, String kind, int power, String accuracy, int pp) {
        // Validate input
        if (name == null || name.trim().isEmpty() || description == null) {
            return false;
        }
        
        Type moveType = Type.fromString(typeStr);
        if (moveType == Type.NONE) {
            return false; // Invalid type
        }
        
        Move.Classification moveClass;
        try {
            moveClass = Move.Classification.valueOf(classificationStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false; // Invalid classification
        }
        
        // Generate a unique ID for the new move
        int nextId = moveDb.getNextAvailableId();
        
        // Create move with the ID
        Move move = new Move(nextId, name, description, moveClass, moveType, kind, power, accuracy, pp);
        
        return moveDb.addMove(move);
    }
    
    /**
     * Gets all moves in the database
     * 
     * @return A list of all moves
     */
    public List<Move> getAllMoves() {
        return moveDb.getAllMoves();
    }
    
    /**
     * Searches for moves by name
     * 
     * @param name The name to search for
     * @return A list of matching moves
     */
    public List<Move> searchByName(String name) {
        return moveDb.searchByName(name);
    }
    
    /**
     * Searches for moves by type
     * 
     * @param typeName The type name to search for
     * @return A list of matching moves
     */
    public List<Move> searchByType(String typeName) {
        Type type = Type.fromString(typeName);
        if (type == Type.NONE) {
            return List.of(); // Return empty list for invalid type
        }
        return moveDb.searchByType(type);
    }
    
    /**
     * Searches for moves by classification
     * 
     * @param classification The classification to search for
     * @return A list of matching moves
     */
    public List<Move> searchByClassification(String classification) {
        try {
            Move.Classification moveClass = Move.Classification.valueOf(classification.toUpperCase());
            return moveDb.searchByClassification(moveClass);
        } catch (IllegalArgumentException e) {
            return List.of(); // Return empty list for invalid classification
        }
    }
    
    /**
     * Gets a move by its name
     * 
     * @param name The move's name
     * @return The move, or null if not found
     */
    public Move getMoveByName(String name) {
        return moveDb.getMove(name);
    }
}