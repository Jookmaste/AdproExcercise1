package se233.chapter1.view;

import se233.chapter1.controller.AllCustomHandler;
import se233.chapter1.controller.GenCharacter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import static se233.chapter1.controller.AllCustomHandler.onDragDropped;
import static se233.chapter1.controller.AllCustomHandler.onDragOver;

public class EquipPane extends ScrollPane {
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    public EquipPane(){}

    private Pane getDetailsPane(){
        Pane equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setBorder(null);
        ((VBox)equipmentInfoPane).setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label weaponLbl,armorLbl;
        StackPane weaponImgGroup = new StackPane();
        StackPane armorImgGroup = new StackPane();
        ImageView bg1 = new ImageView();
        ImageView bg2 = new ImageView();
        ImageView weaponImg = new ImageView();
        ImageView armorImg = new ImageView();
        bg1.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        bg2.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        weaponImgGroup.getChildren().add(bg1);
        armorImgGroup.getChildren().add(bg2);
        if (equippedWeapon != null){
            weaponLbl = new Label("Weapon:\n"+equippedWeapon.getName());
            weaponImg.setImage(new Image(Launcher.class.getResource(equippedWeapon.getImagepath()).toString()));
            weaponImgGroup.getChildren().add(weaponImg);
        } else {
            weaponLbl = new Label("Weapon:");
            weaponImg.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        }
        if (equippedArmor != null) {
            armorLbl = new Label("Armor: \n"+equippedArmor.getName());
            armorImg.setImage(new Image(Launcher.class.getResource(equippedArmor.getImagepath()).toString()));
            armorImgGroup.getChildren().add(armorImg);
        } else {
            armorLbl = new Label("Armor:");
            armorImg.setImage(new Image(Launcher.class.getResource("assets/blank.png" ).toString()));
        }
        weaponImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragOver(e,"Weapon"); }
        });
        armorImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragOver(e, "Armor"); }
        });
        weaponImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragDropped(e, weaponLbl, weaponImgGroup); }
        });
        armorImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragDropped(e, armorLbl, armorImgGroup); }
        });

        Button disableEquipment = new Button();
        disableEquipment.setText("Disable Equipment");
        disableEquipment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BasedCharacter character = Launcher.getMainCharacter();

                if (Launcher.getEquippedWeapon() != null) {
                    BasedEquipment retrievedEquipment = Launcher.getEquippedWeapon();
                    Launcher.getAllEquipments().add(retrievedEquipment);
                    Launcher.setEquippedWeapon(null);
                    character.unequipWeapon();
                }

                if (Launcher.getEquippedArmor() != null) {
                    BasedEquipment retrievedEquipment = Launcher.getEquippedArmor();
                    Launcher.getAllEquipments().add(retrievedEquipment);
                    Launcher.setEquippedArmor(null);
                    character.unequipArmor();
                }

                Launcher.refreshPane();
            }
        });

        equipmentInfoPane.getChildren().addAll(weaponLbl,weaponImgGroup,armorLbl, armorImgGroup,disableEquipment);

        return equipmentInfoPane;
    }

    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipmentInfo = getDetailsPane();
        this.setStyle("-fx-background-color:linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet);");
        this.setContent(equipmentInfo);
    }
}
