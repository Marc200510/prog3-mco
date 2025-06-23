package model;

/**
 * Represents a move that can be learned by Pokémon
 * 
 * @author Your Name
 */
public class Move {
    /**
     * Classification of moves (HM, TM, or normal move)
     */
    public enum Classification {
        HM, TM, NORMAL
    }
    
    private int id;
    private String name;
    private String description;
    private Classification classification;
    private Type type;
    private String kind; // Physical, Special, Status
    private int power;
    private String accuracy;
    private int pp;
    
    /**
     * Creates a new move with the specified attributes
     * 
     * @param id The move ID
     * @param name The move name
     * @param description The move description
     * @param classification The move classification (HM, TM, NORMAL)
     * @param type The move type
     * @param kind The move kind (Physical, Special, Status)
     * @param power The move power
     * @param accuracy The move accuracy
     * @param pp The move PP (Power Points)
     */
    public Move(int id, String name, String description, Classification classification, 
                Type type, String kind, int power, String accuracy, int pp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.classification = classification;
        this.type = type;
        this.kind = kind;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public String getKind() {
        return kind;
    }
    
    public void setKind(String kind) {
        this.kind = kind;
    }
    
    public int getPower() {
        return power;
    }
    
    public void setPower(int power) {
        this.power = power;
    }
    
    public String getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
    
    public int getPP() {
        return pp;
    }
    
    public void setPP(int pp) {
        this.pp = pp;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Move: %s (%s)\n", name, classification));
        sb.append(String.format("Type: %s (%s)\n", type, kind));
        sb.append(String.format("Power: %d | Accuracy: %s | PP: %d\n", power, accuracy, pp));
        sb.append(String.format("Description: %s\n", description));
        
        return sb.toString();
    }
}