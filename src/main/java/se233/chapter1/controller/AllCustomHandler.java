package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;
import se233.chapter1.Launcher;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class AllCustomHandler {

    private static final Logger logger = LoggerFactory.getLogger(AllCustomHandler.class);

    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(equipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();

        if (!dragboard.hasContent(BasedEquipment.DATA_FORMAT)) return;

        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
        BasedCharacter character = Launcher.getMainCharacter();

        if (retrievedEquipment.getClass().getSimpleName().equals(type)) {
            if (retrievedEquipment instanceof Weapon weapon) {
                // เช็คว่า character รองรับ DamageType นี้ไหม
                if (character.getTypes().contains(weapon.getDamageType())) {
                    event.acceptTransferModes(TransferMode.MOVE);
                } else {
                    event.consume();
                }
            } else if (retrievedEquipment instanceof Armor) {
                // BattleMage ห้ามใส่เกราะ
                if (character.getClass().getSimpleName().equals("BattleMageCharacter")) {
                    event.consume();
                } else {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            }
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();
            if(retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                character.equipWeapon((Weapon) retrievedEquipment);
            } else {
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                character.equipArmor((Armor) retrievedEquipment);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();

            logger.info( "{} Equipped item: {}", character.getName(), retrievedEquipment.getName());

            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size()!=1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }
    public static void onEquipDone(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);

        if (event.isAccepted()) {
            int pos = -1;
            for (int i = 0; i < allEquipments.size(); i++) {
                if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                    pos = i;
                }
            }
            if (pos != -1) {
                allEquipments.remove(pos);
            }
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }
}