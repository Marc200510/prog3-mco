package model.database;

import model.Pokemon;
import model.Type;
import view.CSVParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;

/**
 * Database for storing and retrieving Pokémon information
 * Implemented as a singleton
 * 
 * @author Your Name
 */
public class PokemonDatabase {
    private static PokemonDatabase instance;
    private List<Pokemon> pokemons;
    private static final String POKEMON_CSV_PATH = "Pokemon.csv";
    
    /**
     * Private constructor to enforce singleton pattern
     */
    private PokemonDatabase() {
        pokemons = new ArrayList<>();
    }
    
    /**
     * Gets the singleton instance of the PokemonDatabase
     * 
     * @return The PokemonDatabase instance
     */
    public static PokemonDatabase getInstance() {
        if (instance == null) {
            instance = new PokemonDatabase();
        }
        return instance;
    }
    
    /**
     * Sets the instance (primarily for testing purposes)
     * 
     * @param newInstance The new instance to set
     */
    public static void setInstance(PokemonDatabase newInstance) {
        instance = newInstance;
    }
    
    /**
     * Gets the list of Pokémon
     * 
     * @return The list of Pokémon
     */
    public List<Pokemon> getPokemons() {
        return pokemons;
    }
    
    /**
     * Sets the list of Pokémon
     * 
     * @param pokemons The list of Pokémon to set
     */
    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    
    /**
     * Initializes with a curated selection of popular Pokémon
     * 
     * @return The number of Pokémon added
     */
    public int initializeDefaultPokemon() {
        int initialCount = pokemons.size();

        // Gen 1 starters
        addDefaultPokemon(1, "Bulbasaur", "GRASS", "POISON", 5, 0, 2, 16, 45, 49, 49, 45);
        addDefaultPokemon(2, "Ivysaur", "GRASS", "POISON", 16, 1, 3, 32, 60, 62, 63, 60);
        addDefaultPokemon(3, "Venusaur", "GRASS", "POISON", 32, 2, 0, 0, 80, 82, 83, 80);

        addDefaultPokemon(4, "Charmander", "FIRE", "", 5, 0, 5, 16, 39, 52, 43, 65);
        addDefaultPokemon(5, "Charmeleon", "FIRE", "", 16, 4, 6, 36, 58, 64, 58, 80);
        addDefaultPokemon(6, "Charizard", "FIRE", "FLYING", 36, 5, 0, 0, 78, 84, 78, 100);

        addDefaultPokemon(7, "Squirtle", "WATER", "", 5, 0, 8, 16, 44, 48, 65, 43);
        addDefaultPokemon(8, "Wartortle", "WATER", "", 16, 7, 9, 36, 59, 63, 80, 58);
        addDefaultPokemon(9, "Blastoise", "WATER", "", 36, 8, 0, 0, 79, 83, 100, 78);

        // Popular Pokémon
        addDefaultPokemon(25, "Pikachu", "ELECTRIC", "", 5, 0, 26, 0, 35, 55, 40, 90);
        addDefaultPokemon(26, "Raichu", "ELECTRIC", "", 0, 25, 0, 0, 60, 90, 55, 110);

        // Legendary
        addDefaultPokemon(150, "Mewtwo", "PSYCHIC", "", 70, 0, 0, 0, 106, 110, 90, 130);

        // Return the number of Pokémon added
        return pokemons.size() - initialCount;
    }
    
    /**
     * Helper method to add default Pokémon
     */
    private void addDefaultPokemon(int pokedexNumber, String name, String type1, String type2, 
                                  int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel,
                                  int hp, int attack, int defense, int speed) {
        Type primaryType = Type.fromString(type1);
        Type secondaryType = type2.isEmpty() ? Type.NONE : Type.fromString(type2);
        
        Pokemon pokemon = new Pokemon(pokedexNumber, name, primaryType, secondaryType, baseLevel,
                                     evolvesFrom, evolvesTo, evolutionLevel, hp, attack, defense, speed);
        pokemons.add(pokemon);
    }
    
    /**
     * Imports all Pokémon from the CSV file
     * 
     * @return Number of Pokémon imported
     */
    public int importFromCSV() {
        File csvFile = new File(POKEMON_CSV_PATH);
        if (!csvFile.exists()) {
            System.err.println("Error: Pokémon CSV file not found at " + csvFile.getAbsolutePath());
            return 0;
        }
        
        List<Pokemon> importedPokemon = CSVParser.parsePokemonCSV(POKEMON_CSV_PATH);
        
        if (importedPokemon.isEmpty()) {
            return 0;
        }
        
        // Clear existing Pokémon and add all from CSV
        pokemons.clear();
        pokemons.addAll(importedPokemon);
        
        return pokemons.size();
    }
    
    /**
     * Adds a new Pokémon to the database
     * 
     * @param pokemon The Pokémon to add
     * @return true if the Pokémon was added, false if a Pokémon with the same Pokédex number or name already exists
     */
    public boolean addPokemon(Pokemon pokemon) {
        // Check for duplicate Pokédex numbers or names
        for (Pokemon p : pokemons) {
            if (p.getPokedexNumber() == pokemon.getPokedexNumber() || 
                p.getName().equalsIgnoreCase(pokemon.getName())) {
                return false;
            }
        }
        
        pokemons.add(pokemon);
        return true;
    }
    
    /**
     * Gets all Pokémon in the database
     * 
     * @return A list of all Pokémon
     */
    public List<Pokemon> getAllPokemon() {
        return new ArrayList<>(pokemons);
    }
    
    /**
     * Gets a Pokémon by its Pokédex number
     * 
     * @param pokedexNumber The Pokédex number
     * @return The Pokémon, or null if not found
     */
    public Pokemon getPokemonByNumber(int pokedexNumber) {
        for (Pokemon p : pokemons) {
            if (p.getPokedexNumber() == pokedexNumber) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Gets a Pokémon by its name
     * 
     * @param name The Pokémon's name
     * @return The Pokémon, or null if not found
     */
    public Pokemon getPokemonByName(String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Searches for Pokémon by type
     * 
     * @param type The type to search for
     * @return A list of Pokémon with the specified type
     */
    public List<Pokemon> searchByType(Type type) {
        return pokemons.stream()
                .filter(p -> p.getType1() == type || p.getType2() == type)
                .collect(Collectors.toList());
    }
    
    /**
     * Searches for Pokémon by name (partial match)
     * 
     * @param nameQuery The name query
     * @return A list of Pokémon whose names contain the query
     */
    public List<Pokemon> searchByName(String nameQuery) {
        return pokemons.stream()
                .filter(p -> p.getName().toLowerCase().contains(nameQuery.toLowerCase()))
                .collect(Collectors.toList());
    }
}