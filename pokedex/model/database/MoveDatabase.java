package model.database;

import model.Move;
import model.Type;
import view.CSVParser; 

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;

/**
 * Database for storing and retrieving move information
 * Implemented as a singleton
 * 
 * @author Your Name
 */
public class MoveDatabase {
    private static MoveDatabase instance;
    private List<Move> moves;
    private static final String MOVES_CSV_PATH = "Pokemon Moves.csv";
    
    /**
     * Private constructor to enforce singleton pattern
     */
    private MoveDatabase() {
        moves = new ArrayList<>();
        initializeDefaultMoves();
    }
    
    /**
     * Gets the singleton instance of the MoveDatabase
     * 
     * @return The MoveDatabase instance
     */
    public static MoveDatabase getInstance() {
        if (instance == null) {
            instance = new MoveDatabase();
        }
        return instance;
    }
    
    /**
     * Sets the instance (primarily for testing purposes)
     * 
     * @param newInstance The new instance to set
     */
    public static void setInstance(MoveDatabase newInstance) {
        instance = newInstance;
    }
    
    /**
     * Gets the list of moves
     * 
     * @return The list of moves
     */
    public List<Move> getMoves() {
        return moves;
    }
    
    /**
     * Sets the list of moves
     * 
     * @param moves The list of moves to set
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
    
    /**
     * Initialize default moves
     */
    private void initializeDefaultMoves() {
        // Add some common moves
        addMove(new Move(1, "Tackle", "Deals damage with no additional effect.", 
                        Move.Classification.NORMAL, Type.NORMAL, "Physical", 40, "100%", 35));
        
        addMove(new Move(2, "Growl", "Lowers the opponent's Attack stat.", 
                        Move.Classification.NORMAL, Type.NORMAL, "Status", 0, "100%", 40));
        
        addMove(new Move(3, "Ember", "May inflict a burn.", 
                        Move.Classification.NORMAL, Type.FIRE, "Special", 40, "100%", 25));
        
        addMove(new Move(4, "Water Gun", "Deals damage with no additional effect.", 
                        Move.Classification.NORMAL, Type.WATER, "Special", 40, "100%", 25));
        
        addMove(new Move(5, "Vine Whip", "Deals damage with no additional effect.", 
                        Move.Classification.NORMAL, Type.GRASS, "Physical", 45, "100%", 25));
    }
    
    /**
     * Imports all moves from the CSV file
     * 
     * @return Number of moves imported
     */
    public int importFromCSV() {
        File csvFile = new File(MOVES_CSV_PATH);
        if (!csvFile.exists()) {
            System.err.println("Error: Moves CSV file not found at " + csvFile.getAbsolutePath());
            return 0;
        }
        
        List<Move> importedMoves = CSVParser.parseMovesCSV(MOVES_CSV_PATH);
        
        if (importedMoves.isEmpty()) {
            return 0;
        }
        
        // Clear existing moves and add all from CSV
        moves.clear();
        moves.addAll(importedMoves);
        
        return moves.size();
    }
    
    /**
     * Adds a new move to the database
     * 
     * @param move The move to add
     * @return true if the move was added, false if a move with the same name already exists
     */
    public boolean addMove(Move move) {
        // Check for duplicate names
        for (Move m : moves) {
            if (m.getName().equalsIgnoreCase(move.getName())) {
                return false;
            }
        }
        
        moves.add(move);
        return true;
    }
    
    /**
     * Gets all moves in the database
     * 
     * @return A list of all moves
     */
    public List<Move> getAllMoves() {
        return new ArrayList<>(moves);
    }
    
    /**
     * Gets a move by its name
     * 
     * @param name The move's name
     * @return The move, or null if not found
     */
    public Move getMove(String name) {
        for (Move m : moves) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
    
    /**
     * Searches for moves by type
     * 
     * @param type The type to search for
     * @return A list of moves with the specified type
     */
    public List<Move> searchByType(Type type) {
        return moves.stream()
                .filter(m -> m.getType() == type)
                .collect(Collectors.toList());
    }
    
    /**
     * Searches for moves by classification
     * 
     * @param classification The classification to search for
     * @return A list of moves with the specified classification
     */
    public List<Move> searchByClassification(Move.Classification classification) {
        return moves.stream()
                .filter(m -> m.getClassification() == classification)
                .collect(Collectors.toList());
    }
    
    /**
     * Searches for moves by name (partial match)
     * 
     * @param nameQuery The name query
     * @return A list of moves whose names contain the query
     */
    public List<Move> searchByName(String nameQuery) {
        return moves.stream()
                .filter(m -> m.getName().toLowerCase().contains(nameQuery.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the next available ID for a new move
     * 
     * @return The next available ID
     */
    public int getNextAvailableId() {
        if (moves.isEmpty()) {
            return 1; // Start with ID 1 if no moves exist
        }
        
        // Find the highest current ID and add 1
        int maxId = 0;
        for (Move move : moves) {
            if (move.getId() > maxId) {
                maxId = move.getId();
            }
        }
        
        return maxId + 1;
    }
}