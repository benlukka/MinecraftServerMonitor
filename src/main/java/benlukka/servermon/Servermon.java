package benlukka.servermon;

import benlukka.servermon.commands.ServmonCommand;
import benlukka.servermon.commands.homeCommand;
import benlukka.servermon.commands.sethomeCommand;
import benlukka.servermon.item.ItemEventhandler;
import benlukka.servermon.item.loadItems;
import fi.iki.elonen.NanoHTTPD;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Servermon extends JavaPlugin {



    private WebServer webServer;
    @Override
    public void onEnable() {
        loadItems.load();
        ItemEventhandler myEventHandler = new ItemEventhandler();
        ItemEventhandler villagertradebank = new ItemEventhandler();
        getServer().getPluginManager().registerEvents(myEventHandler, this);
        getServer().getPluginManager().registerEvents(villagertradebank, this);
        getCommand("servermon").setExecutor(new ServmonCommand());
        getCommand("home").setExecutor(new homeCommand());
        getCommand("sethome").setExecutor( new sethomeCommand());

        webServer = new WebServer(25564); // Create a web server on port 8080
        try {
            webServer.start();
            getLogger().info("Started on port: " + webServer.getListeningPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        Servermon.getPlugin(Servermon.class).saveConfig();
        webServer.stop();
    }

    private class WebServer extends NanoHTTPD {

        public WebServer(int port) {
            super(port);
        }

        @Override
        public Response serve(IHTTPSession session) {
            String htmlResponse = generateHTMLResponse(); // Create HTML with online player names
            return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, htmlResponse);
        }
    }

    private String generateHTMLResponse() {

        StringBuilder html = new StringBuilder("<html><body><h1>Online Players:</h1>");

        for (Player player : Bukkit.getOnlinePlayers()) {
            html.append("<li>").append(player.getName()).append("</li>");
        }
        html.append("<h1> Operators: </h1>");
        for (OfflinePlayer operator : Bukkit.getOperators()){
            html.append("<li>").append(operator.getName()).append("</li>");
        }
        html.append("<h1>Server info: </h1>");
        html.append("<li>Ip: " + Bukkit.getIp() + "</li>");
        html.append("<li>Viewdistance: " + Bukkit.getViewDistance() + "</li>");
        html.append("<li>Name: " + Bukkit.getName() + "</li>");
        html.append("<li>version: " + Bukkit.getVersion() + "</li>");
        html.append("<li>Minecraftversion: " + Bukkit.getMinecraftVersion() + "</li>");
        html.append("<li>Bukkitversion: " + Bukkit.getBukkitVersion() + "</li>");
        html.append("<li>Default Gamemode: " + Bukkit.getDefaultGameMode() + "</li>");





        html.append("</body></html>");
        return html.toString();
    }
}
