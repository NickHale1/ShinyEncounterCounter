package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is responsible for maintaining the user's save data
 * Two main functions, LoadPokemonList() and SavePokemonList()
 * LoadPokemonList is called at the program launch to compile all saved pokemon hunts into an array (maximum of 20 saved pokemon) rn you need manual delete
 * SavePokemonList is called at the program close to clear out the previous information and rewrite the correct info
 */
public class PokemonEncounterList {

    static String activeHuntSaveFile = "./src/sample/ActiveShinyHunts";

    static PokemonEncounter[] PokemonHunts = new PokemonEncounter[1000];
    static int PokemonHuntsSize = 0;

    public static int getPokemonHuntSize() {
        return PokemonHuntsSize;
    }

    /**
     * Access save file to downlaod all the active pokemon hunts into the array which will be accessed later
     */
    public static PokemonEncounter LoadPokemonList()
    {

        File activeHunts = new File(activeHuntSaveFile);
        Scanner readFile;
        try{
            readFile = new Scanner(activeHunts);
            while(readFile.hasNext()){
                String info = readFile.nextLine();

                if(!info.equals("")) {
                    String[] InfoParameters = info.split("\\s+");


                    PokemonEncounter newPokemon =
                            new PokemonEncounter(InfoParameters[0], Integer.valueOf(InfoParameters[1]), Boolean.parseBoolean(InfoParameters[2]));
                    PokemonHunts[PokemonHuntsSize] = newPokemon;
                    PokemonHuntsSize++;
                }
            }
            return PokemonHunts[0];
        } catch (FileNotFoundException e){
                System.out.println("Error");
                System.exit(-1);
        }
        return null;

    }

    /**
     * Erase the old save file and save the updated information into the same file
     *
     */
    public static void SavePokemonList()
    {

        File activeHunts = new File(activeHuntSaveFile);
        PrintWriter fileWriter;
        try {
            fileWriter = new PrintWriter(activeHunts);
            fileWriter.write("");
            for(int i = 0; i < PokemonHuntsSize; i ++) {
                //fileWriter.write("POkemonDF");
                fileWriter.write(PokemonHunts[i].toString());
            }


            //Debug line
            //fileWriter.write("Saved Correctly:) Yah");


            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            System.exit(-1);
        }


        /**
         * Delete everything in the file --> or move to a backup file just incase??
         * Save all of the current pokemonHunts objects into the active file
         */

    }

    public static PokemonEncounter getPokemonEncounter(String pokemonName){
        for(PokemonEncounter pe : PokemonHunts){
            if(pe.getPokemonName().equals(pokemonName)){
                return pe;
            }

        }

        return new PokemonEncounter();
    }

    public static PokemonEncounter getPokemonEncounterById(int index) {
        return PokemonHunts[index];
    }

    /**
     *
     * This will return a simple list of the names of the pokemon saved in hunts.
     * This lets the UI use a simpler array in the drop down menu WIP
     * that can then be used in reference with the GetPokemonEncounter method to get the
     * actual PokemonEncounter object
     *
     * @return a string array of the names of active pokemon hunts
     */
    public static String[] getPokemonEncounters() {
        String[] pokemonHuntNames = new String[PokemonHuntsSize];
        for(int i =0; i< pokemonHuntNames.length; i++){
            pokemonHuntNames[i] = PokemonHunts[i].getPokemonName();
        }
        return pokemonHuntNames;
    }

    public void addPokemonEncounter(){

    }

    static void removeHunt(PokemonEncounter pe) {
        for(int i = 0; i < PokemonHuntsSize; i++){
            //if index i is the one you are trying to remove
            if(PokemonHunts[i].equals(pe)){
                //move the last entry up to the current entry
                PokemonHunts[i] = PokemonHunts[PokemonHuntsSize -1];
                PokemonHunts[PokemonHuntsSize-1] = null;
                PokemonHuntsSize--;
            }
        }
    }


    public static void main(String[] args) {
//C:\Users\halen\IdeaProjects\ShinyEnocounterCounter\src\sample\ActiveShinyHunts
        //sample/ActiveShinyHunts
        File directt = new File(".//src/sample/ActiveShinyHunts");
        System.out.println(directt.getAbsoluteFile());

        LoadPokemonList();
        for(int i = 0; i < PokemonHuntsSize; i++){
            System.out.print(PokemonHunts[i].toString());
        }
        SavePokemonList();






    }



}
