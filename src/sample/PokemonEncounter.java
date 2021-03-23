package sample;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

/**
 * A class the creates a pokemon encounter object. These can be saved in a file with information being separated by a tab ex:
 * Missingno    -1  false
 * This will allow easy loading of previous encounters and this list can easily be saved each time.
 * When you load a pokemon you delete that line from the list? - might be very inefficent
 *
 */
public class PokemonEncounter
{

    private String pokemonName;
    private int numEncounters;
    private boolean shinyFound;

    /**
     * Default Constructor - creates a missingno
     * Should never be called
     */
    public PokemonEncounter()
    {
        this("MissingNo", -1, false);
    }

    /**
     * Used to create a brand new hunt with only the name
     * @param name The name of the pokemon that a new hunt is being started for
     */
    public PokemonEncounter(String name)
    {
        this(name, 0, false);
    }

    /**
     * Used when recreating the Pokemon Encounter objects from the save file
     * @param name  The name of the pokemon that is being hunted
     * @param encounters    The number of encounters so far
     * @param found boolean for if shiny has been found
     */
    public PokemonEncounter(String name, int encounters, boolean found)
    {
        if(name.contains(" ")) {
           name = name.replace(" ", "_");
        }
        this.pokemonName = name;
        this.numEncounters = encounters;
        this.shinyFound = found;

    }

    /**
     * Getter method for pokemon name
     * @return The name of the pokemon being hunted
     */
    String getPokemonName(){
        return this.pokemonName;
    }

    /**
     * *****Not sure if I need this??
     * Setter method for pokemon name
     * @param newName new pokemon name
     */
    public void setPokemonName(String newName) {}

    /**
     * Getter method for the number of encounters
     * @return integer of the number of encounters
     */
    int getNumEncounters(){
        return this.numEncounters;
    }

    /**
     * Increments the number of encounters if the shiny has not been found yet
     * @return the number of encounters
     */
    int updateNumEncounters() {
        /*
        if(!this.shinyFound) {
            this.numEncounters++;
        }

         */
        this.numEncounters++;
        return this.numEncounters;
    }

    void foundTheShiny() {
        this.shinyFound = true;
    }


    public boolean isShinyFound() {
        return shinyFound;
    }

    /**
     * Formats the PokemonEncounter information into a string that can be loaded at a sepeate time.
     * @return Properly formatted string for saving
     */
    @Override
    public String toString()
    {
        return String.format("%s    %d  %b%n", this.pokemonName, this.numEncounters, this.shinyFound);
    }

//TODO KNOWN BUG:
/**
 * if you have 2 hunts of the same pokemon it will only ever display the first
 * one in the list since the way that it is searched is by going
 * through the list from start to finish and grabbing out the first object that shares a name with it.
 * This could be solved by implementing an ID system but would require semi significant reworks
 */


    /**
     * if the name, number of encounters, and shiny status are all equal then it is the same hunt
     *
     * @param obj the object that this is being compared to
     * @return true if they are the same Pokemon Encounter false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PokemonEncounter)){
            return false;
        }

        PokemonEncounter pe = (PokemonEncounter)obj;
        if(this.pokemonName.equals(pe.getPokemonName())){
            if(this.numEncounters==pe.getNumEncounters()){
                if(this.shinyFound == pe.isShinyFound()){
                    return true;
                }
            }
        }
        return false;
        //return super.equals(obj);
    }

    /**
     * Debug main method
     * @param args not used
     */
    public static void main(String [] args)
    {
        PokemonEncounter test = new PokemonEncounter("Tapu Koko");
        System.out.println(test.toString());

    }
}
