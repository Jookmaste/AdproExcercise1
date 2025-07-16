package se233.chapter1.view;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.item.BasedEquipment;

import java.util.ArrayList;

import static se233.chapter1.controller.AllCustomHandler.onDragDetected;
import static se233.chapter1.controller.AllCustomHandler.onEquipDone;

public class InventoryPane extends ScrollPane {
    private ArrayList<BasedEquipment> equipmentArray;
    public InventoryPane(){  }

    private Pane getDetailsPane(){
        Pane inventoryInfoPane = new HBox(10);
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(25, 25, 25, 25));

        if (equipmentArray != null) {
            for (int i = 0; i < equipmentArray.size(); i++) {
                // สร้าง ImageView สำหรับ item
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(Launcher.class.getResource(equipmentArray.get(i).getImagepath()).toString()));
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                imageView.setPreserveRatio(true);

                StackPane itemBox = new StackPane();
                itemBox.setPrefSize(30, 30);
                itemBox.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                int finalI = i;

                itemBox.setOnDragDetected(event -> {
                    onDragDetected(event, equipmentArray.get(finalI), imageView);
                });

                itemBox.setOnDragDone(event -> {
                    onEquipDone(event);
                });
                itemBox.getChildren().add(imageView);
                inventoryInfoPane.getChildren().add(itemBox);
            }
        }

        return inventoryInfoPane;
    }

/*
        if(equipmentArray!=null){
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];
            for(int i=0 ; i< equipmentArray.size() ; i++) {
                imageViewList[i] = new ImageView();
                imageViewList[i].setImage(new Image(Launcher.class.getResource(equipmentArray.get(i).getImagepath()).toString()));
                int finalI = i;
                imageViewList[i].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        onDragDetected(event, equipmentArray.get(finalI), imageViewList[finalI]);
                    }
                });
                imageViewList[i].setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        onEquipDone(event);
                    }
                });
            }
            inventoryInfoPane.getChildren().addAll(imageViewList);
        }
        return inventoryInfoPane;
    }
*/

    public void drawPane(ArrayList<BasedEquipment> equipmentArray) {
        this.equipmentArray = equipmentArray;
        Pane inventoryInfo = getDetailsPane();
        this.setStyle("-fx-background-color:linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet);");
        this.setContent(inventoryInfo);
    }
}
