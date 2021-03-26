package sample;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;





//TODO Create a settings tab that can modify bits of the UI to customize to the user
//TODO Create a button that can cycle through popular chroma key colors based on the colors of the shiny pokemon
public class Controller implements Initializable{
    private static PokemonEncounter currentPokemonHunt;

    //Defualt key for increment encounter
    private static KeyCode incrementEncounter = KeyCode.A;
    private static String GIFURL = "https://projectpokemon.org/images/shiny-sprite/";
    private static Image pokemonGif;

    /**
     * Method stub for if I need different controller class for multiple scenes
     */
    public static void UpdateIncrementCode() {

    }

    //FXML attributes being imported from the Main Panel
    @FXML Text CurrentPokemonName;
    @FXML Text CurrentNumEncounters;
    @FXML GridPane myWindow22;
    @FXML Button foundShiny;
    @FXML ComboBox huntSelector;
    @FXML Button newHunt;
    @FXML Button removeHunt;
    @FXML Button resetHunt;
    @FXML TextField newHuntName;
    @FXML Button OpenOptions;
    @FXML ImageView shinyPokemonGif;
    @FXML ImageView ShinyGif2;


    //FXML Attributes for Options Panel
    @FXML Button OptionIncrementEnccounter;

    /**
     * Increment the number of encounters for each attempt at a shiny
     *
     */
    public void addAttempt(KeyEvent event)
    {
        // if you are not typing in the text field
        if(event.getTarget() != newHuntName) {
            //and if you are hitting the correct key
            if (event.getCode() == incrementEncounter) {

                UpdateCurrentNumEncounters();
            }
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newHunt.setDefaultButton(false);


           // shinyGif.setImage(new Image("https://play.pokemonshowdown.com/sprites/dex/aegislash.png"));

            /**
             * Loading Gif From Download works :)
             * Loading Gif from pokemon showdown site does not work
             * If loaded from a file the Gif will dispaly successfully
             */
            //FileInputStream inputStream = new FileInputStream("C:\\Users\\nickh\\OneDrive\\Desktop\\alakazam.gif");

            //ShinyGif2.setImage(new Image(inputStream));


        //shinyPokemonGif.setImage(new Image("C:\\Users\\nickh\\OneDrive\\Desktop\\alakazam.gif"));

        currentPokemonHunt = PokemonEncounterList.LoadPokemonList();
        //shinyPokemonGif.setImage(new Image(GIFURL + currentPokemonHunt.getPokemonName() + ".gif"));
        UpdatePokemonStats();
        //TODO need to make it that what the first pokemon is loaded in that the found shiny button is
        // correctly enabled or disabled
        foundShiny.setDisable(currentPokemonHunt.isShinyFound());
        if(PokemonEncounterList.getPokemonHuntSize() <=1) {
            removeHunt.setDisable(true);
        }
        updateDropDownList();

        //huntSelector.getItems().addAll(PokemonEncounterList.getPokemonEncounters());
        huntSelector.setValue(PokemonEncounterList.getPokemonEncounterById(0).getPokemonName());
        /**
         * Set actions for when you select a new hunt from the drop down list
         */
        huntSelector.setOnAction(PokemonSelected -> {
            String SelectedItem = (String) huntSelector.getSelectionModel().getSelectedItem();
           // String SelectedItem = SelectedPokemon.getPokemonName();
            System.out.println(SelectedItem);
            if(SelectedItem== null){
                SelectedItem = PokemonEncounterList.getPokemonEncounterById(0).getPokemonName();
                huntSelector.setValue(SelectedItem);
            }
            // if I am giving it the list of all the entries then do I really need to deselect and reselect?
            //not sure how it would work since I am using the copyOf function and if it is giving me a copy with
            //with the actual objects or if it is copying each object
            //if copying each object then it may be worth finding a work aroudn to the data structure to instead use
            //a list that grows as needed rather than a size 20 array
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

        OpenOptions.setOnAction(openOptions -> {
            /*
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("OptionsUI.fxml"));
                Stage optionsWindow = new Stage();
                optionsWindow.setTitle("Options");
                optionsWindow.setScene(new Scene(root, 600, 600));
                optionsWindow.show();
            } catch (IOException e) {
                System.out.println("idk lol");
                System.exit(0);

            }

             */
            System.out.println("Open Options");
            try {
                openOptionsWindow();
            } catch (Exception e) {
                System.out.println("No");
                e.printStackTrace();
            }

        });
/*
        OptionIncrementEnccounter.setOnAction(event -> {
            if(!OptionIncrementEnccounter.getText().equals("Waiting For Input")){
                OptionIncrementEnccounter.setText("Waiting For Input");
            }
        });

        OptionIncrementEnccounter.setOnKeyPressed(event -> {
            if(OptionIncrementEnccounter.getText().equals("Waiting For Input")){
                incrementEncounter = event.getCode();
                OptionIncrementEnccounter.setText(event.getCode().toString());
            }
        });
*/
    }

    private void openOptionsWindow() throws Exception{
        Stage optionsWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ShinyEncounterCounterUIOld.fxml"));
        Parent root = (Parent)loader.load();

        optionsWindow.setTitle("OptionsWindow");
        Scene optionsScene = new Scene(root, 600, 600);
        optionsWindow.setScene(optionsScene);
        optionsWindow.show();
    }

    private void updateDropDownList(int i) {
        updateDropDownList();
        huntSelector.setValue(PokemonEncounterList.getPokemonEncounterById(i-1).getPokemonName());
    }

    private void updateDropDownList() {
        huntSelector.getItems().clear();
       // huntSelector.getItems().addAll(PokemonEncounterList.getPokemonEncounters());
        huntSelector.getItems().addAll(PokemonEncounterList.getPokemonEncountersNames());
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
        pokemonGif = null;
        pokemonGif = new Image(GIFURL + currentPokemonHunt.getPokemonName() + ".gif");
        shinyPokemonGif.setImage(pokemonGif);

    }

    /**
     * called when the correct button is pressed to increment the amount of encouters
     */
    private  void UpdateCurrentNumEncounters() {
        currentPokemonHunt.updateNumEncounters();
        CurrentNumEncounters.setText(String.valueOf(currentPokemonHunt.getNumEncounters()));


    }
}
