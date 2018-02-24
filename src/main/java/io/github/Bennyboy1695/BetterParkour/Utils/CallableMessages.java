package io.github.Bennyboy1695.BetterParkour.Utils;

import io.github.Bennyboy1695.BetterParkour.BetterParkour;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * io.github.Bennyboy1695.BetterParkour.Utils was created by Bennyboy1695 on 19/08/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CallableMessages {

    public String parseMessage(final String messagesnode, final String sendername, final String targetname, final String messageString) {
        Configuration Cfg = BetterParkour.getInstance().getConfig();
        final String pfx = Cfg.getString("General.prefix");
        YamlConfiguration Msgs = BetterParkour.getInstance().getMessages();
        String m = Msgs.getString(messagesnode);
        if (m.contains("%prefix%")) {
            m = m.replace("%prefix%", pfx);
        }
        if (!sendername.equals("null")) {
            m = m.replace("%sender%", sendername);
        }
        if (!targetname.equals("null")) {
            m = m.replace("%target%", targetname);
        }
        if (!messageString.equals("null")) {
            m = m.replace("%message%", messageString);
        }
        return m;
    }

    public ItemStack loreItem(final ItemStack litem, final String lname, final String lore) {
        final ItemMeta lmeta = litem.getItemMeta();
        lmeta.setDisplayName(lname);
        final List<String> Lore = new ArrayList<String>();
        Lore.add(lore);
        lmeta.setLore((List)Lore);
        litem.setItemMeta(lmeta);
        return litem;
    }

    public ItemStack loreItem(final Material litem, final String lname, final String lore) {
        return this.loreItem(new ItemStack(litem), lname, lore);
    }
}
