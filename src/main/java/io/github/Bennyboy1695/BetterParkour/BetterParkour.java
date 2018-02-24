package io.github.Bennyboy1695.BetterParkour;

import io.github.Bennyboy1695.BetterParkour.Commands.CommandFakeCP;
import io.github.Bennyboy1695.BetterParkour.Commands.CommandWand;
import io.github.Bennyboy1695.BetterParkour.Handlers.EventHandlers;
import io.github.Bennyboy1695.BetterParkour.Storage.SQLite.Database;
import io.github.Bennyboy1695.BetterParkour.Storage.SQLite.SQLite;
import io.github.Bennyboy1695.BetterParkour.Utils.RefStrings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

public final class BetterParkour extends JavaPlugin {

    public static BetterParkour instance;

    public Logger logger = Logger.getLogger("BetterParkour");

    private FileConfiguration messages;
    private File messagesFile;
    private FileConfiguration config;
    private File configFile;

    private Database db;

    @Override
    public void onEnable() {
        registerEvents();
        getLogs().info(RefStrings.prefix + "Loading Config!");
        loadConfig();
        registerCommands();
        this.db = new SQLite(this);
        this.db.load();

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BetterParkour getInstance() {
        return instance;
    }

    public Logger getLogs() {
        return this.logger;
    }

    public Database getRDatabase() {
        return this.db;
    }

    public int getCheckpoint(Player player) {
        if (this.db.getCheckpoint(player.getUniqueId().toString()) == null) {
            return 0;
        } else {
            return this.db.getCheckpoint(player.getUniqueId().toString());
        }
    }

    public void setCheckpoint(Player p, int checkpoint) {
        this.db.setCheckpoint(p, checkpoint);
    }

    public Integer getCheckpointFromXYZ(int x, int y, int z) { return this.db.getCheckpointFromXYZ(x,y,z);}

    public Double getCheckPointX(int checkpoint) { return this.db.getCheckpointX(checkpoint);}

    public Double getCheckPointY(int checkpoint) { return this.db.getCheckpointY(checkpoint);}

    public Double getCheckPointZ(int checkpoint) { return this.db.getCheckpointZ(checkpoint);}

    public Integer getRowCount() { return this.db.getRowCount();}

    public void setCheckpointXYZ(int checkpoint, double x, double y, double z) { this.db.setCheckpointXYZ(checkpoint, x, y, z);}

    public void registerEvents() {
        getLogs().info(RefStrings.prefix + "Loading Events!");
        PluginManager manager = Bukkit.getServer().getPluginManager();
        manager.registerEvents(new EventHandlers(), this);
    }

    public void registerCommands() {
        getLogs().info(RefStrings.prefix + "Loading Commands!");
        this.getCommand("parkourwand").setExecutor(new CommandWand());
        this.getCommand("fakecp").setExecutor(new CommandFakeCP());
    }

    public YamlConfiguration getMessages() {
        return YamlConfiguration.loadConfiguration(this.messagesFile);
    }

    private void loadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.getDataFolder(), "config.yml");
        }
        if (!this.configFile.exists()) {
            this.saveResource("config.yml", false);
            this.getLogs().info(RefStrings.prefix + ChatColor.RED + "No config file was found so one was created with default values...");
        }
        else {
            this.loadConfig();
        }
    }


}
