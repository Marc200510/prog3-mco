package view;

import model.Pokemon;
import model.Move;
import model.Type;
import model.Move.Classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for parsing CSV files
 * 
 * @author Your Name
 */
public class CSVParser {
    
    /**
     * Parses the Pokémon CSV file
     * 
     * @param filePath Path to the CSV file
     * @return List of Pokémon objects
     */
    public static List<Pokemon> parsePokemonCSV(String filePath) {
        List<Pokemon> pokemonList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            String line = br.readLine();
            
            // Read each line
            while ((line = br.readLine()) != null) {
                // Handle quoted CSV properly
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                // Clean up the values
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("\"", "").trim();
                }
                
                // Skip if incomplete data
                if (values.length < 12) continue;
                
                try {
                    int id = Integer.parseInt(values[0]);
                    String name = values[1];
                    String form = values[2].trim();
                    Type type1 = Type.fromString(values[3]);
                    Type type2 = values[4].trim().isEmpty() ? Type.NONE : Type.fromString(values[4]);
                    
                    // Skip alternate forms for simplicity
                    if (!form.equals(" ") && !form.isEmpty()) continue;
                    
                    // Stats
                    int hp = Integer.parseInt(values[6]);
                    int attack = Integer.parseInt(values[7]);
                    int defense = Integer.parseInt(values[8]);
                    int speed = Integer.parseInt(values[11]);
                    
                    // Create Pokémon (evolution data will be set later)
                    Pokemon pokemon = new Pokemon(
                        id, name, type1, type2, 5, 0, 0, 0, hp, attack, defense, speed
                    );
                    
                    pokemonList.add(pokemon);
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                    System.err.println("Error parsing Pokémon #" + values[0] + ": " + e.getMessage());
                }
            }
            
            // Set up evolution chains for common Pokémon
            setupEvolutionChains(pokemonList);
            
        } catch (IOException e) {
            System.err.println("Error reading Pokémon CSV file: " + e.getMessage());
        }
        
        return pokemonList;
    }
    
    /**
     * Parses the Moves CSV file
     * 
     * @param filePath Path to the CSV file
     * @return List of Move objects
     */
    public static List<Move> parseMovesCSV(String filePath) {
        List<Move> moveList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header lines
            String line = br.readLine();
            if ((line = br.readLine()) == null) return moveList;
            
            // Process data lines
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;
                
                String[] values = parseCsvLine(line);
                
                // Skip if invalid data
                if (values.length < 8) continue;
                
                try {
                    // Parse the id and use it in the Move constructor
                    int id = Integer.parseInt(values[0].trim());
                    String name = values[1].trim();
                    String description = values[2].trim();
                    Type type = Type.fromString(values[3].trim());
                    String kind = values[4].trim();
                    
                    int power;
                    try {
                        power = Integer.parseInt(values[5].trim());
                    } catch (NumberFormatException e) {
                        power = 0; // Status moves often have - for power
                    }
                    
                    String accuracy = values[6].trim();
                    int pp = Integer.parseInt(values[7].trim());
                    
                    // Determine classification (most are normal)
                    Classification classification = Classification.NORMAL;
                    
                    // Create move - now including the id
                    Move move = new Move(id, name, description, classification, type, kind, power, accuracy, pp);
                    moveList.add(move);
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                    System.err.println("Error parsing move: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Moves CSV file: " + e.getMessage());
        }
        
        return moveList;
    }
    
    /**
     * Sets up evolution chains for common Pokémon
     * 
     * @param pokemonList List of Pokémon objects
     */
    private static void setupEvolutionChains(List<Pokemon> pokemonList) {
        // Create map by ID for easier lookup
        Map<Integer, Pokemon> pokemonMap = new HashMap<>();
        for (Pokemon p : pokemonList) {
            pokemonMap.put(p.getPokedexNumber(), p);
        }
        
        // Gen 1 starters
        setupEvolutionChain(pokemonMap, 1, 2, 3);      // Bulbasaur line
        setupEvolutionChain(pokemonMap, 4, 5, 6);      // Charmander line
        setupEvolutionChain(pokemonMap, 7, 8, 9);      // Squirtle line
        
        // Other popular evolution chains
        setupEvolutionChain(pokemonMap, 10, 11, 12);   // Caterpie line
        setupEvolutionChain(pokemonMap, 25, 26, 0);    // Pikachu line
        setupEvolutionChain(pokemonMap, 133, 134, 0);  // Eevee -> Vaporeon
        setupEvolutionChain(pokemonMap, 133, 135, 0);  // Eevee -> Jolteon
        setupEvolutionChain(pokemonMap, 133, 136, 0);  // Eevee -> Flareon
    }
    
    /**
     * Sets up a specific evolution chain
     * 
     * @param pokemonMap Map of Pokémon by ID
     * @param basic Basic form ID
     * @param stage1 Stage 1 evolution ID
     * @param stage2 Stage 2 evolution ID
     */
    private static void setupEvolutionChain(Map<Integer, Pokemon> pokemonMap, int basic, int stage1, int stage2) {
        Pokemon basicPokemon = pokemonMap.get(basic);
        Pokemon stage1Pokemon = pokemonMap.get(stage1);
        
        if (basicPokemon != null && stage1Pokemon != null) {
            basicPokemon.setEvolvesTo(stage1);
            basicPokemon.setEvolutionLevel(16); // Default level
            
            stage1Pokemon.setEvolvesFrom(basic);
            
            if (stage2 > 0) {
                Pokemon stage2Pokemon = pokemonMap.get(stage2);
                if (stage2Pokemon != null) {
                    stage1Pokemon.setEvolvesTo(stage2);
                    stage1Pokemon.setEvolutionLevel(32); // Default level
                    stage2Pokemon.setEvolvesFrom(stage1);
                }
            }
        }
    }
    
    /**
     * Parses a line of CSV, handling quotes and escapes
     * 
     * @param line The line of CSV to parse
     * @return Array of parsed values
     */
    private static String[] parseCsvLine(String line) {
        // Handle quoted CSV properly
        String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        
        // Clean up the values
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replaceAll("\"", "").trim();
        }
        
        return values;
    }
}