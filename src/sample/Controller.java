package sample;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
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
    Scene ShinyEncounterTracker;
    static PokemonEncounter currentPokemonHunt;
    @FXML
    Text CurrentPokemonName;
    @FXML
    Text CurrentNumEncounters;
    @FXML
    GridPane myWindow22;
    @FXML
    Button testBtn;
    @FXML
    ComboBox huntSelector;

    /**
     * Increment the number of encounters for each attempt at a shiny
     *
     */
    public void addAttempt() { UpdateCurrentNumEncounters(); }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPokemonHunt = PokemonEncounterList.LoadPokemonList();
        UpdatePokemonStats();

        huntSelector.getItems().addAll(PokemonEncounterList.getPokemonEncounters());
        huntSelector.setValue(PokemonEncounterList.getPokemonEncounterById(0).getPokemonName());

        huntSelector.setOnAction(PokemonSelected -> {
            String SelectedItem = (String) huntSelector.getSelectionModel().getSelectedItem();
            System.out.println(SelectedItem);
            currentPokemonHunt = PokemonEncounterList.getPokemonEncounter(SelectedItem);
            UpdatePokemonStats();
        });

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
