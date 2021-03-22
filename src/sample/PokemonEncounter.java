package sample;

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

    public PokemonEncounter()
    {
        this("MissingNo", -1, false);
    }

    public PokemonEncounter(String name)
    {
        this(name, 0, false);
    }

    public PokemonEncounter(String name, int encounters, boolean found)
    {
        if(name.contains(" ")) {
           name = name.replace(" ", "_");
        }
        this.pokemonName = name;
        this.numEncounters = encounters;
        this.shinyFound = found;

    }

    public String getPokemonName(){
        return this.pokemonName;
    }

    public void setPokemonName(String newName) {
        this.pokemonName = newName;
    }

    public int getNumEncounters(){
        return this.numEncounters;
    }

    public int updateNumEncounters() {
        this.numEncounters++;
        return this.numEncounters;
    }

    /**
     * Formats the PokemonEncounter information into a string that can be loaded at a sepeate time.
     * @return
     */
    @Override
    public String toString()
    {
        return String.format("%s    %d  %b%n", this.pokemonName, this.numEncounters, this.shinyFound);
    }

    public static void main(String [] args)
    {
        PokemonEncounter test = new PokemonEncounter("Tapu Koko");
        System.out.println(test.toString());

    }
}
