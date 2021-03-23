package sample;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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

    //Defualt key for increment encounter
    static KeyCode incrementEncounter = KeyCode.A;

    //FXML attributes being imported from the GUI
    @FXML Text CurrentPokemonName;
    @FXML Text CurrentNumEncounters;
    @FXML GridPane myWindow22;
    @FXML Button foundShiny;
    @FXML ComboBox huntSelector;
    @FXML Button newHunt;
    @FXML Button removeHunt;
    @FXML Button resetHunt;
    @FXML TextField newHuntName;

    /**
     * Increment the number of encounters for each attempt at a shiny
     *
     */
    public void addAttempt(KeyEvent event)
    {
        // if you are not typing in the text field
        if(event.getTarget() != newHuntName) {
            //and if you are hitting the correct key
            if (event.getCode() != incrementEncounter) {

                UpdateCurrentNumEncounters();
            }
        }


    }


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
         * Associated FXML fields ---- newHunt, newHuntName
         * Set actions for the New Hunt button
         */
        newHunt.setOnAction(CreateNewHunt ->{
            /**
             * First check to see what the text on the button says -- Two states
             * if Button says new then that means the text field isnt visible or active
             *      set the text field to visible/active
             *      change text on button to start
             * if Button says start then that means the text field is open and ready for input
             *      need to make sure that there is something in text field so we dont get null pointer
             *      if something is typed in then call the addhunt function in PokemonEncounterList
             */

            if(newHunt.getText().equals("NEW HUNT")){
                //activate text field
                newHuntName.setDisable(false);
                newHuntName.setVisible(true);

                newHunt.setText("Start");
            }else {
                //if something is typed in the box then create the new encounter
                if(newHuntName.getText() != "" || newHuntName.getText() != null) {
                    PokemonEncounterList.addPokemonEncounter(newHuntName.getText());
                    //disable text field
                    newHuntName.setDisable(true);
                    newHuntName.setVisible(false);
                    newHunt.setText("NEW HUNT");
                    updateDropDownList(PokemonEncounterList.getPokemonHuntSize());
                    if(PokemonEncounterList.getPokemonHuntSize() > 1) {
                        removeHunt.setDisable(false);
                    }
                }

            }
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
         updateDropDownList(PokemonEncounterList.getPokemonHuntSize());
        });

    }
    private void updateDropDownList(int i) {
        updateDropDownList();
        huntSelector.setValue(PokemonEncounterList.getPokemonEncounterById(i-1).getPokemonName());
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
        if(currentPokemonHunt.isShinyFound()){
            CurrentPokemonName.setFill(Color.GOLD);
        }
        else{
            CurrentPokemonName.setFill(Color.BLACK);
        }
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
