package se233.chapter1.view;

import se233.chapter1.controller.AllCustomHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.BasedEquipment;


public class CharacterPane extends ScrollPane {
    private BasedCharacter character;

    public CharacterPane() {
    }

    private Pane getDetailPane() {
        Pane characterInfoPane = new VBox(10);
        characterInfoPane.setBorder(null);
        characterInfoPane.setPadding(new Insets(30, 30, 30, 30));
        Label name, type, hp, atk, def, res;
        ImageView mainImage = new ImageView();
        if (this.character != null) {
            name = new Label("Name: " + character.getName());
            mainImage.setImage(new Image(Launcher.class.getResource(character.getImagepath()).toString()));
            hp = new Label("HP: " + character.getHp().toString() + "/" + character.getFullHp().toString());
            type = new Label("Type: " + character.getTypes().toString());
            atk = new Label("ATK: " + character.getPower());
            def = new Label("DEF: " + character.getDefense());
            res = new Label("RES: " + character.getResistance());
        } else {
            name = new Label("Name: ");
            mainImage.setImage(new Image(Launcher.class.getResource("assets/unknown.png").toString()));
            hp = new Label("HP: ");
            type = new Label("Type: ");
            atk = new Label("ATK: ");
            def = new Label("DEF: ");
            res = new Label("RES: ");
        }
        Button genCharacter = new Button();
        genCharacter.setText("Generate Character");

        genCharacter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Launcher.getEquippedWeapon() != null) {
                    BasedEquipment retrievedEquipment = Launcher.getEquippedWeapon();
                    Launcher.getAllEquipments().add(retrievedEquipment);
                    Launcher.setEquippedWeapon(null);
                }

                if (Launcher.getEquippedArmor() != null) {
                    BasedEquipment retrievedEquipment = Launcher.getEquippedArmor();
                    Launcher.getAllEquipments().add(retrievedEquipment);
                    Launcher.setEquippedArmor(null);
                }

                Launcher.setMainCharacter(se233.chapter1.controller.GenCharacter.setUpCharacter());

                Launcher.refreshPane();
            }
        });

        characterInfoPane.getChildren().addAll(name, mainImage, type, hp, atk, def, res, genCharacter);

        return characterInfoPane;

    }

    public void drawPane(BasedCharacter character) {
        this.character = character;
        Pane characterInfo = getDetailPane();
        this.setStyle(
                "-fx-border-color: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet);" +
                        "-fx-border-width: 5px;" +
                        "-fx-border-style: solid;" +
                        "-fx-background-color: white;"
        );
        this.setContent(characterInfo);
    }
}
