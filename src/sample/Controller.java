package sample;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;


//TODO Create new encounter button that will create a new encounter and set the current hunt to the new one
//TODO Fix the UI and make it more user friendly
//TODO Create options to change the background color -- to chroma key it out on streamlabs
//TODO Create a settings tab that can modify bits of the UI to customize to the user
//TODO Create the ability to load an image or gif when you open a new hunt
public class Controller implements Initializable {
    static PokemonEncounter currentPokemonHunt;

    //FXML attributes being imported from the GUI
    @FXML Text CurrentPokemonName;
    @FXML Text CurrentNumEncounters;
    @FXML GridPane myWindow22;
    @FXML Button foundShiny;
    @FXML ComboBox huntSelector;
    @FXML Button newHunt;
    @FXML Button removeHunt;
    @FXML Button resetHunt;

    /**
     * Increment the number of encounters for each attempt at a shiny
     *
     */
    public void addAttempt() { UpdateCurrentNumEncounters(); }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPokemonHunt = PokemonEncounterList.LoadPokemonList();
        UpdatePokemonStats();
        //TODO need to make it that what the first pokemon is loaded in that the found shiny button is
        // correctly enabled or disabled
        foundShiny.setDisable(currentPokemonHunt.isShinyFound());
        updateDropDownList();

        //huntSelector.getItems().addAll(PokemonEncounterList.getPokemonEncounters());
        huntSelector.setValue(PokemonEncounterList.getPokemonEncounterById(0).getPokemonName());
        /**
         * Set actions for when you select a new hunt from the drop down list
         */
        huntSelector.setOnAction(PokemonSelected -> {
            String SelectedItem = (String) huntSelector.getSelectionModel().getSelectedItem();
            System.out.println(SelectedItem);
            if(SelectedItem== null){
                SelectedItem = PokemonEncounterList.getPokemonEncounterById(0).getPokemonName();
                huntSelector.setValue(SelectedItem);
            }
            currentPokemonHunt = PokemonEncounterList.getPokemonEncounter(SelectedItem);
            UpdatePokemonStats();
            foundShiny.setDisable(currentPokemonHunt.isShinyFound());
        });

        /**
         * Set actions for the found shiny button
         */
        foundShiny.setOnAction(FoundAShiny -> {
            currentPokemonHunt.foundTheShiny();
            foundShiny.setDisable(true);

        });

        /**
         * Set actions for the New Hunt button
         */
        newHunt.setOnAction(CreateNewHunt ->{
            //TODO create new hunt information
        });

        /**
         * Set actions for the remove hunt button
         */
        removeHunt.setOnAction(RemoveHunt -> {
            // will not remove if there is not more than one thing in the list
         if(PokemonEncounterList.getPokemonHuntSize() > 1) {
             PokemonEncounterList.removeHunt(currentPokemonHunt);
             currentPokemonHunt = PokemonEncounterList.getPokemonEncounterById(0);
             huntSelector.setValue(PokemonEncounterList.getPokemonEncounterById(0).getPokemonName());
         }
         if(PokemonEncounterList.getPokemonHuntSize() <= 1){
             removeHunt.setDisable(true);
         }
         updateDropDownList();
        });

    }

    private void updateDropDownList() {
        huntSelector.getItems().clear();
        huntSelector.getItems().addAll(PokemonEncounterList.getPokemonEncounters());
    }


    /**
     * called when swapping pokemon to properly display name and number of encounters
     */
    public  void UpdatePokemonStats() {
        CurrentPokemonName.setText(currentPokemonHunt.getPokemonName());
        CurrentNumEncounters.setText(String.valueOf(currentPokemonHunt.getNumEncounters()));

    }

    /**
     * called when the correct button is pressed to increment the amount of encouters
     */
    private  void UpdateCurrentNumEncounters() {
        currentPokemonHunt.updateNumEncounters();
        CurrentNumEncounters.setText(String.valueOf(currentPokemonHunt.getNumEncounters()));


    }
}
