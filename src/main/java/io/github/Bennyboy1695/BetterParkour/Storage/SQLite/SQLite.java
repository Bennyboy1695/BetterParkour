package io.github.Bennyboy1695.BetterParkour.Storage.SQLite;

/**
 * io.github.Bennyboy1695.BetterParkour.Storage.SQLite was created by Bennyboy1695 on 19/08/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */

import io.github.Bennyboy1695.BetterParkour.BetterParkour;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;


public class SQLite extends Database {
    String player_data;
    String checkpoint_data;

    public SQLite(BetterParkour instance) {
        super(instance);
        player_data = plugin.getConfig().getString("SQLite.Filename", "player_data"); // Set the table name here e.g player_kills
        checkpoint_data = plugin.getConfig().getString("SQLite.Filename", "checkpoint_data");

    }

    public String SQLiteCreatePlayerTable = "CREATE TABLE IF NOT EXISTS player_data (" + // make sure to put your table name in here too.
            "`uuid` varchar(32) NOT NULL," + // This creates the different colums you will save data too. varchar(32) Is a string, int = integer
            "`checkpoint` int(11) NOT NULL," +
            "PRIMARY KEY (`uuid`)" +  // This is creating 3 colums Player, Kills, Total. Primary key is what you are going to use as your indexer. Here we want to use player so
            ");"; // we can search by player, and get kills and total. If you some how were searching kills it would provide total and player.

    public String SQLiteCreateCheckpointTable = "CREATE TABLE IF NOT EXISTS checkpoint_data (" +
            "`checkpoint` int(11) NOT NULL," +
            "`x` int(11) NOT NULL," +
            "`y` int(11) NOT NULL," +
            "`z` int(11) NOT NULL," +
            "PRIMARY KEY (`checkpoint`)" +
            ");";


    // SQL creation stuff, You can leave the blow stuff untouched.
    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), "ParkourData" + ".db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: " + "ParkourData" + ".db");
            }
        }

        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        this.connection = getSQLConnection();
        try {
            Statement s = this.connection.createStatement();
            s.executeUpdate(this.SQLiteCreateCheckpointTable);
            s.executeUpdate(this.SQLiteCreatePlayerTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.initialize();
    }

    //public void load() {
    //    loadCheckpointTable();
    //    loadPlayerTable();
    //}
//
    //public void loadPlayerTable() {
    //    connection = getSQLConnection();
    //    try {
    //        Statement s = connection.createStatement();
    //        s.executeUpdate(SQLiteCreatePlayerTable);
    //        s.close();
    //    } catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //    initialize();
    //}
    //public void loadCheckpointTable() {
    //    connection = getSQLConnection();
    //    try {
    //        Statement s = connection.createStatement();
    //        s.executeUpdate(SQLiteCreateCheckpointTable);
    //        s.close();
    //    } catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //    initialize();
    //}
}
