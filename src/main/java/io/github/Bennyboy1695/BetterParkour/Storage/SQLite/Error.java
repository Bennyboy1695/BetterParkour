package io.github.Bennyboy1695.BetterParkour.Storage.SQLite;

import io.github.Bennyboy1695.BetterParkour.BetterParkour;

import java.util.logging.Level;

/**
 * io.github.Bennyboy1695.BetterParkour.Storage.SQLite was created by Bennyboy1695 on 19/08/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class Error {

    public static void execute(BetterParkour plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }

    public static void close(BetterParkour plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
