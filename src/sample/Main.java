package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


//TODO Maybe make a timer function?
public class Main extends Application{

    Scene ShinyEncounterTracker;

    @Override
    @FXML
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShinyEncounterCounterUI.fxml"));
        Parent root = loader.load();
        Controller mycontroller =  loader.getController();
        primaryStage.setTitle("Shiny Hunt Tracker");
        ShinyEncounterTracker = new Scene(root, 480, 400);
        primaryStage.setResizable(false);
        primaryStage.setScene(ShinyEncounterTracker);

        /**
         * Override method for closing the window so I can save the hunts that have been changed.
         */
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                PokemonEncounterList.SavePokemonList();

                System.out.println("Shutting down :(");
                System.exit(0);
            }
        });

        /**
         * A key listener that will update please :)
         */
        ShinyEncounterTracker.setOnKeyReleased(event -> {


            System.out.println("Yo");
            mycontroller.addAttempt(event);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }




}
