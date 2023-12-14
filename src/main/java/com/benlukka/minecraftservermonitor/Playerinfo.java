package com.benlukka.minecraftservermonitor;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.imageio.ImageIO;

public class Playerinfo {
    @FXML
    private Label playername;
    @FXML
    public ImageView imgv;
    private String PlayerName = HelloController.Playername;
    private String UUID = HelloController.UUID;

    // Constructor to accept text
 public void initialize() throws IOException{
     set();

 }
    public void set() throws IOException {
        System.out.println(PlayerName);
        if (PlayerName == null) {
            playername.setText("No Player Selected");
        } else {
            try {
                playername.setText(PlayerName);
                JSONObject getplayername = new JSONObject(IOUtils.toString(new URL("https://api.mojang.com/users/profiles/minecraft/" + PlayerName), Charset.forName("UTF-8")));
                System.out.println(getplayername.toString());
                UUID = getplayername.getString("id");
                JSONObject uuid = new JSONObject(IOUtils.toString(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUID), Charset.forName("UTF-8")));
                String encodedtexture = String.valueOf(uuid.getJSONArray("properties").getJSONObject(0).getString("value"));
                byte[] decoded = Base64.getDecoder().decode(encodedtexture);
                String decodedStr = new String(decoded, StandardCharsets.UTF_8);
                JSONObject texture = new JSONObject(decodedStr);
                String skinurl = String.valueOf(texture.getJSONObject("textures").getJSONObject("SKIN").getString("url"));
                URL url = new URL(skinurl);
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.connect();
                InputStream urlStream = conn.getInputStream();
                javafx.scene.image.Image image = new javafx.scene.image.Image(urlStream);
                imgv.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




}