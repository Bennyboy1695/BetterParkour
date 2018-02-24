package io.github.Bennyboy1695.BetterParkour.Storage.SQLite;

/**
 * io.github.Bennyboy1695.BetterParkour.Storage.SQLite was created by Bennyboy1695 on 19/08/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */

import java.sql.*;
import java.util.logging.Level;

import io.github.Bennyboy1695.BetterParkour.BetterParkour;
import org.bukkit.entity.Player;


public abstract class Database {

    BetterParkour plugin;
    Connection connection;
    public String player_data = "player_data";
    public String checkpoint_data = "checkpoint_data";
    String sqlcheckpoint = "SELECT * FROM " + checkpoint_data + " WHERE checkpoint = ?";
    String sqlplayer = "SELECT * FROM " + player_data + " WHERE uuid = ?";

    public Database(BetterParkour instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        this.connection = this.getSQLConnection();

        PreparedStatement ex;
        ResultSet rs;
        try {
            ex = this.connection.prepareStatement(this.sqlplayer);
            rs = ex.executeQuery();
            this.close(ex, rs);
        } catch (SQLException var4) {
            var4.printStackTrace();
            this.plugin.getLogger().log(Level.SEVERE, "Unable to retreive players_data connection", var4);
        }

        try {
            ex = this.connection.prepareStatement(this.sqlcheckpoint);
            rs = ex.executeQuery();
            this.close(ex, rs);
        } catch (SQLException var3) {
            var3.printStackTrace();
            this.plugin.getLogger().log(Level.SEVERE, "Unable to retreive checkpoints_data connection", var3);
        }

    }

    public Integer getCheckpoint(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + player_data + " WHERE uuid = '" + string + "';");

            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("uuid").equalsIgnoreCase(string)) {
                    return rs.getInt("checkpoint");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getRowCount() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM " + checkpoint_data);

            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public void setCheckpoint(Player player, Integer checkpoint) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + player_data + " (uuid,checkpoint) VALUES(?,?)");
            ps.setString(1, player.getUniqueId().toString());
            ps.setInt(2, checkpoint);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;
    }

    public Double getCheckpointX(int checkpoint) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + checkpoint_data + " WHERE uuid = '"+checkpoint+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("checkpoint").equals(checkpoint)){
                    return rs.getDouble("x");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0.0;
    }

    public Integer getCheckpointFromXYZ(int x, int y, int z) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + checkpoint_data + " WHERE (x,y,z) = '"+x + y + z +"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getDouble(x) == x && rs.getDouble(y) == y && rs.getDouble(z) == z){
                    return rs.getInt("checkpoint");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Double getCheckpointY(int checkpoint) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + checkpoint_data + " WHERE uuid = '"+checkpoint+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("checkpoint").equals(checkpoint)){
                    return rs.getDouble("y");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0.0;
    }

    public Double getCheckpointZ(int checkpoint) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + checkpoint_data + " WHERE uuid = '"+checkpoint+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("checkpoint").equals(checkpoint)){
                    return rs.getDouble("z");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0.0;
    }

    public void setCheckpointXYZ(int checkpoint, double x, double y, double z) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO " + checkpoint_data + " (checkpoint,x,y,z) VALUES(?,?,?,?)");
            ps.setInt(1, checkpoint);
            ps.setDouble(2, x);
            ps.setDouble(3, y);
            ps.setDouble(4, z);

            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;
    }


    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}

