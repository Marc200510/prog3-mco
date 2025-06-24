package view;

import controller.ItemController;
import controller.MoveController;
import controller.PokemonController;
import model.Item;
import model.Move;
import model.Pokemon;
import model.Type;

import java.util.List;
import java.util.Scanner;

/**
 * Main application interface for the Pokédex system
 * Provides text-based menu system for user interaction
 * 
 * @author Your Name
 */
public class App {
    private Scanner scanner;
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;

    /**
     * Creates a new App instance and initializes controllers
     */
    public App() {
        scanner = new Scanner(System.in);
        pokemonController = new PokemonController();
        moveController = new MoveController();
        itemController = new ItemController();
    }

    /**
     * Starts the application
     */
    public void start() {
        displayWelcomeMessage();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    pokemonMenu();
                    break;
                case 2:
                    moveMenu();
                    break;
                case 3:
                    itemMenu();
                    break;
                case 4:
                    importCSVMenu();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        System.out.println("\nThank you for using the Pokédex System! Goodbye!");
    }

    /**
     * Displays the welcome message
     */
    private void displayWelcomeMessage() {
        System.out.println("==============================================");
        System.out.println("          WELCOME TO THE POKÉDEX SYSTEM        ");
        System.out.println("==============================================");
        System.out.println("This system allows you to manage Pokémon, moves, and items.");
        System.out.println("Use the menu options to navigate through the system.");
    }

    /**
     * Displays the main menu
     */
    private void displayMainMenu() {
        System.out.println("\n==============================================");
        System.out.println("                  MAIN MENU                  ");
        System.out.println("==============================================");
        System.out.println("1. Pokémon Menu");
        System.out.println("2. Moves Menu");
        System.out.println("3. Items Menu");
        System.out.println("4. Import Data from CSV Files");
        System.out.println("0. Exit");
        System.out.println("==============================================");
    }

    /**
     * Handles the Pokémon menu
     */
    private void pokemonMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n==============================================");
            System.out.println("                POKÉMON MENU                ");
            System.out.println("==============================================");
            System.out.println("1. Add New Pokémon");
            System.out.println("2. Display All Pokémon");
            System.out.println("3. Search Pokémon");
            System.out.println("4. Make Pokémon Cry");
            System.out.println("0. Back to Main Menu");
            System.out.println("==============================================");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addPokemon();
                    break;
                case 2:
                    displayAllPokemon();
                    break;
                case 3:
                    searchPokemonMenu();
                    break;
                case 4:
                    makePokemonCry();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the moves menu
     */
    private void moveMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n==============================================");
            System.out.println("                 MOVES MENU                  ");
            System.out.println("==============================================");
            System.out.println("1. Add New Move");
            System.out.println("2. Display All Moves");
            System.out.println("3. Search Moves");
            System.out.println("0. Back to Main Menu");
            System.out.println("==============================================");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addMove();
                    break;
                case 2:
                    displayAllMoves();
                    break;
                case 3:
                    searchMovesMenu();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the items menu
     */
    private void itemMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n==============================================");
            System.out.println("                 ITEMS MENU                  ");
            System.out.println("==============================================");
            System.out.println("1. Display All Items");
            System.out.println("2. Search Items");
            System.out.println("0. Back to Main Menu");
            System.out.println("==============================================");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    displayAllItems();
                    break;
                case 2:
                    searchItemsMenu();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the CSV import menu
     */
    private void importCSVMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n==============================================");
            System.out.println("                IMPORT MENU                  ");
            System.out.println("==============================================");
            System.out.println("1. Import All Pokémon from CSV");
            System.out.println("2. Import All Moves from CSV");
            System.out.println("3. Initialize Starter Pokémons");
            System.out.println("0. Back to Main Menu");
            System.out.println("==============================================");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    importPokemonFromCSV();
                    break;
                case 2:
                    importMovesFromCSV();
                    break;
                case 3:
                    initializeStarterPokemon();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    /**
     * Imports Pokémon from CSV
     */
    private void importPokemonFromCSV() {
        System.out.println("\n=== IMPORT POKÉMON FROM CSV ===");
        System.out.println("Warning: This will replace all existing Pokémon data.");
        System.out.print("Continue? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            int count = pokemonController.importPokemonFromCSV();
            if (count > 0) {
                System.out.println("Success! " + count + " Pokémon imported from CSV.");
            } else {
                System.out.println("Import failed or no Pokémon found in CSV file.");
            }
        } else {
            System.out.println("Import cancelled.");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Imports moves from CSV
     */
    private void importMovesFromCSV() {
        System.out.println("\n=== IMPORT MOVES FROM CSV ===");
        System.out.println("Warning: This will replace all existing move data.");
        System.out.print("Continue? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            int count = moveController.importMovesFromCSV();
            if (count > 0) {
                System.out.println("Success! " + count + " moves imported from CSV.");
            } else {
                System.out.println("Import failed or no moves found in CSV file.");
            }
        } else {
            System.out.println("Import cancelled.");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Initializes the starter Pokémon
     */
    private void initializeStarterPokemon() {
        System.out.println("\n=== INITIALIZE STARTER POKÉMON ===");
        System.out.println("This will add the default starter Pokémon to the database.");
        System.out.print("Continue? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            int count = pokemonController.initializeDefaultPokemon();
            if (count > 0) {
                System.out.println("Success! " + count + " starter Pokémon initialized.");
            } else {
                System.out.println("Initialization failed or no Pokémon were added.");
            }
        } else {
            System.out.println("Initialization cancelled.");
        }

        pressEnterToContinue();
    }


    /**
     * Adds a new Pokémon
     */
    private void addPokemon() {
        System.out.println("\n=== ADD NEW POKÉMON ===");

        int pokedexNumber;
        boolean validPokedexNumber = false;
        String type1 = ""; 
        String type2 = ""; 

        while (!validPokedexNumber) {
            pokedexNumber = getIntInput("Enter Pokédex Number: ");

            // Check if this Pokédex number already exists
            Pokemon existing = pokemonController.getPokemonByNumber(pokedexNumber);
            if (existing != null) {
                System.out.println(
                        "ERROR: Pokédex Number " + pokedexNumber + " already assigned to " + existing.getName());
                System.out.println("Please enter a different Pokédex Number.");
                continue;
            }

            // If we get here, the Pokédex number is valid
            validPokedexNumber = true;

            // Now proceed with the rest of the data collection
            String name = getStringInput("Enter Name: ");

            // Check for duplicate name
            Pokemon existingName = pokemonController.getPokemonByName(name);
            if (existingName != null) {
                System.out.println("ERROR: Pokémon name '" + name + "' already exists.");
                pressEnterToContinue();
                return;
            }

            System.out.println("\nAvailable types:");
            for (Type type : Type.values()) {
                if (type != Type.NONE) {
                    System.out.print(type + " ");
                }
            }

            boolean validType1 = false;
            while (!validType1) {
                type1 = getStringInput("\nEnter Type 1: ");
                try {
                    Type testType = Type.valueOf(type1.toUpperCase());
                    if (testType != Type.NONE) {
                        validType1 = true;
                    } else {
                        System.out.println("ERROR: NONE is not a valid primary type. Please enter a valid type.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("ERROR: Invalid type. Please enter one of the available types listed above.");
                }
            }

            boolean validType2 = false;
            while (!validType2) {
                type2 = getStringInput("Enter Type 2 (leave blank if none): ");
                if (type2.trim().isEmpty()) {
                    type2 = "";  // Empty string for no secondary type
                    validType2 = true;
                } else {
                    try {
                        Type.valueOf(type2.toUpperCase());
                        validType2 = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: Invalid type. Please enter one of the available types or leave blank.");
                    }
                }
            }

            int baseLevel;
            do {
                baseLevel = getIntInput("Enter Base Level (minimum 1): ");
                if (baseLevel < 1) {
                    System.out.println("ERROR: Base Level must be at least 1.");
                }
            } while (baseLevel < 1);

            int evolvesFrom = getIntInput("Enter Evolves From (Pokédex Number, 0 if none): ");
            int evolvesTo = getIntInput("Enter Evolves To (Pokédex Number, 0 if none): ");
            int evolutionLevel = getIntInput("Enter Evolution Level (0 if not applicable): ");
            int hp = getIntInput("Enter HP: ");
            int attack = getIntInput("Enter Attack: ");
            int defense = getIntInput("Enter Defense: ");
            int speed = getIntInput("Enter Speed: ");

            // --- Prompt for Move Set ---
            List<Move> availableMoves = moveController.getAllMoves();
            List<Move> moveSet = new java.util.ArrayList<>();

            System.out.println("\nHere's the list of the available moves:");
            for (int i = 0; i < availableMoves.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, availableMoves.get(i).getName());
            }
            /* (mco2 req)
            System.out.println("By default, new Pokémon will have 'Tackle' and 'Defend' as their moves.");

            // Try to add Tackle and Defend if available
            Move tackle = null, defend = null;
            for (Move m : availableMoves) {
                if (m.getName().equalsIgnoreCase("Tackle")) tackle = m;
                if (m.getName().equalsIgnoreCase("Defend")) defend = m;
            }
            if (tackle != null) moveSet.add(tackle);
            if (defend != null) moveSet.add(defend);

            */

            // Optionally, let user add more moves (up to 4)
          
            while (moveSet.size() < 4) {
                int moveChoice = getIntInput("Enter move number to add: ");
                if (moveChoice < 1 || moveChoice > availableMoves.size()) {
                    System.out.println("Invalid move number.");
                    continue;
                }

                Move chosen = availableMoves.get(moveChoice - 1);
                if (moveSet.contains(chosen)) {
                    System.out.println("Move already in move set.");
                    continue;
                }

                moveSet.add(chosen);
                System.out.println(chosen.getName() + " added.");

                if (moveSet.size() < 4) {
                    String addMore = getStringInput("Do you want to add another move? (y/n): ").trim().toLowerCase();
                    if (!addMore.equals("y"))
                        break;
                }
            }
            // --- Prompt for Held Item ---
            List<Item> items = itemController.getAllItems();
            Item heldItem = null;
            System.out.println("\nAvailable Items:");
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, items.get(i).getName());
            }
            String itemChoice = getStringInput("Enter item number to hold (leave blank for none): ").trim();
            if (!itemChoice.isEmpty()) {
                try {
                    int itemNum = Integer.parseInt(itemChoice);
                    if (itemNum >= 1 && itemNum <= items.size()) {
                        heldItem = items.get(itemNum - 1);
                    } else {
                        System.out.println("Invalid item number. No item will be held.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. No item will be held.");
                }
            }

            // Add Pokémon using controller
            boolean added = pokemonController.addPokemon(
                pokedexNumber, name, type1, type2,
                baseLevel, evolvesFrom, evolvesTo, evolutionLevel,
                hp, attack, defense, speed,
                moveSet, heldItem // <-- Pass move set and held item
            );

            if (added) {
                System.out.println("SUCCESS: Pokémon added successfully!");
            } else {
                System.out.println("ERROR: Failed to add Pokémon. Invalid type or other data issue.");
            }

            pressEnterToContinue();
        }
    }

    /**
     * Displays all Pokémon in a compact tabular format
     */
    private void displayAllPokemon() {
        System.out.println("\n=== ALL POKÉMON ===");

        List<Pokemon> pokemons = pokemonController.getAllPokemon();

        if (pokemons.isEmpty()) {
            System.out.println("No Pokémon found in the database.");
            pressEnterToContinue();
            return;
        }

        System.out.println("Found " + pokemons.size() + " Pokémon:");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-12s | %-14s | %-5s | %-26s | %-12s | %-12s |\n",
                "ID", "Name", "Type", "Level", "Stats (HP/ATK/DEF/SPD)", "Evolves From", "Evolves To");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------");

        for (Pokemon pokemon : pokemons) {
            String typeStr = pokemon.getType1().toString();
            if (pokemon.getType2() != Type.NONE) {
                typeStr += "/" + pokemon.getType2().toString();
            }

            String statsStr = String.format("%d/%d/%d/%d",
                    pokemon.getHp(), pokemon.getAttack(),
                    pokemon.getDefense(), pokemon.getSpeed());

            String evolvesFromStr = (pokemon.getEvolvesFrom() > 0)
                    ? "#" + String.format("%03d", pokemon.getEvolvesFrom())
                    : "-";

            String evolvesToStr = (pokemon.getEvolvesTo() > 0) ? "#" + String.format("%03d", pokemon.getEvolvesTo()) +
                    " (Lvl " + pokemon.getEvolutionLevel() + ")" : "-";

            System.out.printf("| #%-3s | %-12s | %-14s | %-5d | %-26s | %-12s | %-12s |\n",
                    String.format("%03d", pokemon.getPokedexNumber()),
                    pokemon.getName(),
                    typeStr,
                    pokemon.getBaseLevel(),
                    statsStr,
                    evolvesFromStr,
                    evolvesToStr);
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------------");

        pressEnterToContinue();
    }

    /**
     * Shows menu for searching Pokémon
     */
    private void searchPokemonMenu() {
        System.out.println("\n=== SEARCH POKÉMON ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Type");
        System.out.println("3. Search by Pokédex Number");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                searchPokemonByName();
                break;
            case 2:
                searchPokemonByType();
                break;
            case 3:
                searchPokemonByNumber();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    /**
     * Searches for Pokémon by name
     */
    private void searchPokemonByName() {
        String name = getStringInput("Enter Pokémon name to search: ");
        List<Pokemon> results = pokemonController.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No Pokémon found with that name.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " Pokémon found):");
        System.out.println("---------------------------------------------");
        for (Pokemon pokemon : results) {
            System.out.println(pokemon);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Searches for Pokémon by type
     */
    private void searchPokemonByType() {
        System.out.println("\nAvailable types:");
        for (Type type : Type.values()) {
            if (type != Type.NONE) {
                System.out.print(type + " ");
            }
        }

        String type = getStringInput("\nEnter Pokémon type to search: ");
        List<Pokemon> results = pokemonController.searchByType(type);

        if (results.isEmpty()) {
            System.out.println("No Pokémon found with that type.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " Pokémon found):");
        System.out.println("---------------------------------------------");
        for (Pokemon pokemon : results) {
            System.out.println(pokemon);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Searches for Pokémon by Pokédex number
     */
    private void searchPokemonByNumber() {
        int number = getIntInput("Enter Pokédex Number to search: ");
        Pokemon pokemon = pokemonController.getPokemonByNumber(number);

        if (pokemon == null) {
            System.out.println("No Pokémon found with that Pokédex Number.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Result:");
        System.out.println("---------------------------------------------");
        System.out.println(pokemon);
        System.out.println("---------------------------------------------");

        pressEnterToContinue(); // Add pause
    }

    /**
     * Makes a Pokémon cry
     */
    private void makePokemonCry() {
        String name = getStringInput("Enter the name of the Pokémon to make cry: ");
        boolean success = pokemonController.makePokemonCry(name);

        if (!success) {
            System.out.println("ERROR: Pokémon not found!");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Adds a new move
     */
    private void addMove() {
        System.out.println("\n=== ADD NEW MOVE ===");

        String name = getStringInput("Enter Name: ");
        String description = getStringInput("Enter Description: ");

        System.out.println("Classifications: HM, TM, NORMAL");
        String classification = getStringInput("Enter Classification: ");

        System.out.println("\nAvailable types:");
        for (Type type : Type.values()) {
            if (type != Type.NONE) {
                System.out.print(type + " ");
            }
        }

        String type = getStringInput("\nEnter Type: ");
        System.out.println("\nKind options: Physical, Special, Status");
        String kind = getStringInput("Enter Kind: ");
        int power = getIntInput("Enter Power (0 for status moves): ");
        String accuracy = getStringInput("Enter Accuracy (e.g., 100%, 85%): ");
        int pp = getIntInput("Enter PP (Power Points): ");

        boolean added = moveController.addMove(name, description, classification, type, kind, power, accuracy, pp);

        if (added) {
            System.out.println("SUCCESS: Move added successfully!");
        } else {
            System.out.println("ERROR: Failed to add Move. Name might already exist, or invalid Type/Classification.");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Displays all moves in a horizontal tabular format
     */
    private void displayAllMoves() {
        System.out.println("\n=== ALL MOVES ===");

        List<Move> moves = moveController.getAllMoves();

        if (moves.isEmpty()) {
            System.out.println("No moves found in the database.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("Found " + moves.size() + " moves:");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-15s | %-10s | %-10s | %-6s | %-9s | %-4s | %-30s |\n",
                "ID", "Name", "Type", "Kind", "Power", "Accuracy", "PP", "Description");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------");

        for (Move move : moves) {
            // Truncate description if too long
            String description = move.getDescription();
            if (description.length() > 30) {
                description = description.substring(0, 27) + "...";
            }

            System.out.printf("| %-4d | %-15s | %-10s | %-10s | %-6d | %-9s | %-4d | %-30s |\n",
                    move.getId(),
                    move.getName(),
                    move.getType(),
                    move.getKind(),
                    move.getPower(),
                    move.getAccuracy(),
                    move.getPP(),
                    description);
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------");

        pressEnterToContinue();
    }

    /**
     * Shows menu for searching moves
     */
    private void searchMovesMenu() {
        System.out.println("\n=== SEARCH MOVES ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Type");
        System.out.println("3. Search by Classification");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                searchMovesByName();
                break;
            case 2:
                searchMovesByType();
                break;
            case 3:
                searchMovesByClassification();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    /**
     * Searches for moves by name
     */
    private void searchMovesByName() {
        String name = getStringInput("Enter move name to search: ");
        List<Move> results = moveController.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No moves found with that name.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " moves found):");
        System.out.println("---------------------------------------------");
        for (Move move : results) {
            System.out.println(move);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Searches for moves by type
     */
    private void searchMovesByType() {
        System.out.println("\nAvailable types:");
        for (Type type : Type.values()) {
            if (type != Type.NONE) {
                System.out.print(type + " ");
            }
        }

        String type = getStringInput("\nEnter move type to search: ");
        List<Move> results = moveController.searchByType(type);

        if (results.isEmpty()) {
            System.out.println("No moves found with that type.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " moves found):");
        System.out.println("---------------------------------------------");
        for (Move move : results) {
            System.out.println(move);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Searches for moves by classification
     */
    private void searchMovesByClassification() {
        System.out.println("Classifications: HM, TM, NORMAL");
        String classification = getStringInput("Enter move classification to search: ");
        List<Move> results = moveController.searchByClassification(classification);

        if (results.isEmpty()) {
            System.out.println("No moves found with that classification.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " moves found):");
        System.out.println("---------------------------------------------");
        for (Move move : results) {
            System.out.println(move);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Displays all items in a horizontal tabular format
     */
    private void displayAllItems() {
        System.out.println("\n=== ALL ITEMS ===");

        List<Item> items = itemController.getAllItems();

        if (items.isEmpty()) {
            System.out.println("No items found in the database.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("Found " + items.size() + " items:");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-15s | %-30s | %-10s | %-10s |\n",
                "Name", "Category", "Description", "Buy Price", "Sell Price");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");

        for (Item item : items) {
            // Truncate description if too long
            String description = item.getDescription();
            if (description.length() > 30) {
                description = description.substring(0, 27) + "...";
            }

            System.out.printf("| %-20s | %-15s | %-30s | ₽%-9d | ₽%-9d |\n",
                    item.getName(),
                    item.getCategory(),
                    description,
                    item.getBuyingPrice(),
                    item.getSellingPrice());
        }
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");

        pressEnterToContinue();
    }

    /**
     * Shows menu for searching items
     */
    private void searchItemsMenu() {
        System.out.println("\n=== SEARCH ITEMS ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Category");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                searchItemsByName();
                break;
            case 2:
                searchItemsByCategory();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    /**
     * Searches for items by name
     */
    private void searchItemsByName() {
        String name = getStringInput("Enter item name to search: ");
        List<Item> results = itemController.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No items found with that name.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " items found):");
        System.out.println("---------------------------------------------");
        for (Item item : results) {
            System.out.println(item);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Searches for items by category
     */
    private void searchItemsByCategory() {
        System.out.println("Common categories: Vitamin, Evolution Stone, Feather, Leveling Item");
        String category = getStringInput("Enter item category to search: ");
        List<Item> results = itemController.searchByCategory(category);

        if (results.isEmpty()) {
            System.out.println("No items found in that category.");
            pressEnterToContinue(); // Add pause
            return;
        }

        System.out.println("\nSearch Results (" + results.size() + " items found):");
        System.out.println("---------------------------------------------");
        for (Item item : results) {
            System.out.println(item);
            System.out.println("---------------------------------------------");
        }

        pressEnterToContinue(); // Add pause
    }

    /**
     * Gets an integer input from the user
     * 
     * @param prompt The prompt to display
     * @return The integer input
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);

        while (!scanner.hasNextInt()) {
            System.out.println("ERROR: Please enter a valid number.");
            scanner.next(); // Consume the invalid input
            System.out.print(prompt);
        }

        int input = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return input;
    }

    /**
     * Gets a string input from the user
     * 
     * @param prompt The prompt to display
     * @return The string input
     */
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Pauses execution until the user presses Enter
     */
    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

}
