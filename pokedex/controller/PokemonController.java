package controller;

import model.Move; // Added import for Move class
import model.Pokemon;
import model.Type;
import model.database.PokemonDatabase;

import java.util.List;

/**
 * Controller for managing Pokémon operations
 * 
 * @author Your Name
 */
public class PokemonController {
    private PokemonDatabase pokemonDb;
    
    /**
     * Creates a new PokemonController
     */
    public PokemonController() {
        pokemonDb = PokemonDatabase.getInstance();
    }
    
    /**
     * Imports all Pokémon from the CSV file
     * 
     * @return The number of Pokémon loaded
     */
    public int importPokemonFromCSV() {
        return pokemonDb.importFromCSV();
    }

    /**
     * Initializes the default starter Pokémon
     * 
     * @return The number of Pokémon initialized
     */
    public int initializeDefaultPokemon() {
        return pokemonDb.initializeDefaultPokemon();
    }
    
    /**
     * Adds a new Pokémon to the database with a move set and held item
     * 
     * @param pokedexNumber The Pokédex number
     * @param name The Pokémon's name
     * @param type1 The primary type
     * @param type2 The secondary type
     * @param baseLevel The base level
     * @param evolvesFrom The Pokédex number of the pre-evolution
     * @param evolvesTo The Pokédex number of the evolution
     * @param evolutionLevel The level at which it evolves
     * @param hp The base HP stat
     * @param attack The base Attack stat
     * @param defense The base Defense stat
     * @param speed The base Speed stat
     * @param moveSet The move set for the Pokémon
     * @param heldItem The item held by the Pokémon
     * @return true if the Pokémon was added, false if a Pokémon with the same Pokédex number or name already exists
     */
    public boolean addPokemon(int pokedexNumber, String name, String type1, String type2, 
                             int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, 
                             int hp, int attack, int defense, int speed,
                             List<model.Move> moveSet, model.Item heldItem) {
        // Validate input
        if (pokedexNumber <= 0 || name == null || name.trim().isEmpty()) {
            return false;
        }
        
        Type primaryType = Type.fromString(type1);
        Type secondaryType = Type.fromString(type2);
        
        if (primaryType == Type.NONE) {
            return false; // Primary type cannot be NONE
        }
        
        // Create Pokémon with moveSet and heldItem
        Pokemon pokemon = new Pokemon(pokedexNumber, name, primaryType, secondaryType, 
                                      baseLevel, evolvesFrom, evolvesTo, evolutionLevel, 
                                      hp, attack, defense, speed);
        if (moveSet != null) pokemon.setMoveSet(moveSet);
        if (heldItem != null) pokemon.setHeldItem(heldItem);

        return pokemonDb.addPokemon(pokemon);
    }
    
    /**
     * Gets all Pokémon in the database
     * 
     * @return A list of all Pokémon
     */
    public List<Pokemon> getAllPokemon() {
        return pokemonDb.getAllPokemon();
    }
    
    /**
     * Searches for Pokémon by name
     * 
     * @param name The name to search for
     * @return A list of matching Pokémon
     */
    public List<Pokemon> searchByName(String name) {
        return pokemonDb.searchByName(name);
    }
    
    /**
     * Searches for Pokémon by type
     * 
     * @param typeName The type name to search for
     * @return A list of matching Pokémon
     */
    public List<Pokemon> searchByType(String typeName) {
        Type type = Type.fromString(typeName);
        if (type == Type.NONE) {
            return List.of(); // Return empty list for invalid type
        }
        return pokemonDb.searchByType(type);
    }
    
    /**
     * Gets a Pokémon by its Pokédex number
     * 
     * @param pokedexNumber The Pokédex number
     * @return The Pokémon, or null if not found
     */
    public Pokemon getPokemonByNumber(int pokedexNumber) {
        return pokemonDb.getPokemonByNumber(pokedexNumber);
    }
    
    /**
     * Gets a Pokémon by its name
     * 
     * @param name The Pokémon's name
     * @return The Pokémon, or null if not found
     */
    public Pokemon getPokemonByName(String name) {
        return pokemonDb.getPokemonByName(name);
    }
    
    /**
     * Adds a move to a Pokémon's move set
     * 
     * @param pokemon The Pokémon
     * @param move The move to add
     * @return true if the move was added, false if the move set is full
     */
    public boolean addMoveToPokemon(Pokemon pokemon, Move move) {
        if (pokemon == null || move == null) {
            return false;
        }
        
        if (pokemon.getMoveSetSize() < 4) {
            pokemon.addMoveToSet(move);
            return true;
        }
        
        return false;
    }

    /**
     * Removes a move from a Pokémon's move set
     * 
     * @param pokemon The Pokémon
     * @param moveIndex The index of the move to remove
     * @return true if the move was removed, false otherwise
     */
    public boolean removeMoveFromPokemon(Pokemon pokemon, int moveIndex) {
        if (pokemon == null || moveIndex < 0 || moveIndex >= pokemon.getMoveSetSize()) {
            return false;
        }
        
        Move move = pokemon.getMoveAt(moveIndex);
        if (move.getClassification() == Move.Classification.HM) {
            return false; // Cannot remove HM moves
        }
        
        pokemon.removeMoveFromSet(moveIndex);
        return true;
    }

    /**
     * Makes a Pokémon cry
     * 
     * @param pokemonName The name of the Pokémon
     * @return true if the Pokémon was found and made to cry, false otherwise
     */
    public boolean makePokemonCry(String pokemonName) {
        Pokemon pokemon = pokemonDb.getPokemonByName(pokemonName);
        if (pokemon != null) {
            pokemon.cry();
            return true;
        }
        return false;
    }
}
