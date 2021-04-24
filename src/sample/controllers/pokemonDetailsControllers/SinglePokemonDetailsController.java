package sample.controllers.pokemonDetailsControllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.components.pokemonDetailsControls.MainInfoBoxControl;
import sample.components.pokemonDetailsControls.TypesBoxControl;
import sample.controllers.SceneSwitchController;
import sample.model.datamodels.PokemonType;
import sample.model.fetchers.PokemonTypeFetcher;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

public class SinglePokemonDetailsController implements Initializable {
    private PokemonType pokemon;
    private String url;

    private final MainInfoBoxControl mainInfoBoxControl = new MainInfoBoxControl();
    private final TypesBoxControl typesBoxControl = new TypesBoxControl();

    private final SceneSwitchController sceneController = new SceneSwitchController();

    public SinglePokemonDetailsController() {
    }

    @FXML
    Button singlePokemonDetailsGoBackButton;

    @FXML
    Pane anchorPane;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainInfoBoxControl.relocate(20, 20);
        anchorPane.getChildren().add(mainInfoBoxControl);
        anchorPane.getChildren().get(anchorPane.getChildren().size()-1).toFront();

        typesBoxControl.relocate(590, 20);
        anchorPane.getChildren().add(typesBoxControl);
        anchorPane.getChildren().get(anchorPane.getChildren().size()-1).toFront();
    }

    public void updateInfo() {
        mainInfoBoxControl.setPokemon(pokemon);
        typesBoxControl.setIcons(pokemon.getTypes());
    }

    public void setPokemon(String url) {
        this.url = url;

        Task<Void> fetchPokemon = new Task<>() {
            @Override
            public Void call() {
                pokemon = (PokemonType) new PokemonTypeFetcher().fetchAndParse(url);
                return null;
            }
        };
        fetchPokemon.setOnSucceeded(workerStateEvent -> updateInfo());

        Thread thread = new Thread(fetchPokemon);
        thread.setDaemon(true);
        thread.start();
    }

    public void onSinglePokemonDetailsGoBackClick(ActionEvent event) {
        try {
            sceneController.switchToLibraryView(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
