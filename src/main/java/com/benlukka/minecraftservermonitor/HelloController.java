package com.benlukka.minecraftservermonitor;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static java.lang.System.out;

public class HelloController {
    @FXML
    private Label offlineplayer;
    @FXML
    private Label onlineplayer;
    @FXML
    private Label operatorsonline;
    @FXML
    private ImageView offlinesvg;
    @FXML
    private ImageView onlinesvg;
    @FXML
    private ImageView oponlinesvg;
    @FXML
    private ListView<String> onlinelist;

    public static String Playername;
    public static String UUID;
    public void initialize()  throws IOException {
        Image imagei = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("cross.png")));
        offlinesvg.setImage(imagei);
        onlinesvg.setImage(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("check.png"))));
        oponlinesvg.setImage(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResourceAsStream("settings.png"))));
        update();
        onlinelist.setOnMouseClicked(this::handleMouseClick);
    }

    public void update() throws IOException {
        onlinelist.getItems().clear();
        JSONArray json = new JSONArray(IOUtils.toString(new URL("http://192.168.178.148:25564"), StandardCharsets.UTF_8));
        int jsonLength = json.length();
        if (jsonLength == 1) {
            out.println("No player data");
        }
        if (jsonLength > 1) {
            for (int i = 0; i < jsonLength - 1; i++) {
                JSONObject jo = json.getJSONObject(i);
                out.println("JO:  " + jo.toString());
                if (!onlinelist.getItems().contains(jo.getString("name"))) {
                    onlinelist.getItems().add(jo.getString("name"));
                }
            }
        }
        JSONObject bar = json.getJSONObject(jsonLength - 1);
        out.println(bar.toString());
        offlineplayer.setText(String.valueOf(bar.getInt("offline")));
        onlineplayer.setText(String.valueOf(bar.getInt("online")));
        operatorsonline.setText(String.valueOf(bar.getInt("operators")));
    }


    private  void handleMouseClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            String selectedItem = onlinelist.getSelectionModel().getSelectedItem();
            out.println("Double-clicked on: " + selectedItem);
                try {
                    Playername = selectedItem;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("playerinfo.fxml"));
                    Parent root = loader.load();
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Second Window");
                    primaryStage.setScene(new Scene(root, 300, 200));
                    primaryStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }







        }
    }



}
