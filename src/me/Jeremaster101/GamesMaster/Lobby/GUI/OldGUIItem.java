package me.Jeremaster101.GamesMaster.Lobby.GUI;

import me.Jeremaster101.GamesMaster.Message;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class OldGUIItem {

    public ItemStack cosmeticUI() {
        ItemStack s = new ItemStack(Material.ENDER_CHEST, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Cosmetics");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to view cosmetics!");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    public ItemStack gameUI() {
        ItemStack s = new ItemStack(Material.BEACON, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Games");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to view games!");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    public ItemStack filler(GUIColor color) {
        ItemStack filler = new ItemStack(Material.AIR, 1);
        switch (color) {
            case RED:
                filler.setType(Material.RED_STAINED_GLASS_PANE);
                break;
            case ORANGE:
                filler.setType(Material.ORANGE_STAINED_GLASS_PANE);
                break;
            case YELLOW:
                filler.setType(Material.YELLOW_STAINED_GLASS_PANE);
                break;
            case LIME:
                filler.setType(Material.LIME_STAINED_GLASS_PANE);
                break;
            case GREEN:
                filler.setType(Material.GREEN_STAINED_GLASS_PANE);
                break;
            case LIGHT_BLUE:
                filler.setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                break;
            case BLUE:
                filler.setType(Material.BLUE_STAINED_GLASS_PANE);
                break;
            case CYAN:
                filler.setType(Material.CYAN_STAINED_GLASS_PANE);
                break;
            case PURPLE:
                filler.setType(Material.PURPLE_STAINED_GLASS_PANE);
                break;
            case MAGENTA:
                filler.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                break;
            case PINK:
                filler.setType(Material.PINK_STAINED_GLASS_PANE);
                break;
            case BLACK:
                filler.setType(Material.BLACK_STAINED_GLASS_PANE);
                break;
            case BROWN:
                filler.setType(Material.BROWN_STAINED_GLASS_PANE);
                break;
            case LIGHT_GRAY:
                filler.setType(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                break;
            case WHITE:
                filler.setType(Material.WHITE_STAINED_GLASS_PANE);
                break;
        }
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);
        return filler;
    }

    public ItemStack fillerBack(GUIColor color, String ui) {
        ItemStack filler = new ItemStack(Material.AIR, 1);
        switch (color) {
            case RED:
                filler.setType(Material.RED_STAINED_GLASS);
                break;
            case ORANGE:
                filler.setType(Material.ORANGE_STAINED_GLASS);
                break;
            case YELLOW:
                filler.setType(Material.YELLOW_STAINED_GLASS);
                break;
            case LIME:
                filler.setType(Material.LIME_STAINED_GLASS);
                break;
            case GREEN:
                filler.setType(Material.GREEN_STAINED_GLASS);
                break;
            case LIGHT_BLUE:
                filler.setType(Material.LIGHT_BLUE_STAINED_GLASS);
                break;
            case BLUE:
                filler.setType(Material.BLUE_STAINED_GLASS);
                break;
            case CYAN:
                filler.setType(Material.CYAN_STAINED_GLASS);
                break;
            case PURPLE:
                filler.setType(Material.PURPLE_STAINED_GLASS);
                break;
            case MAGENTA:
                filler.setType(Material.MAGENTA_STAINED_GLASS);
                break;
            case PINK:
                filler.setType(Material.PINK_STAINED_GLASS);
                break;
            case BLACK:
                filler.setType(Material.BLACK_STAINED_GLASS);
                break;
            case BROWN:
                filler.setType(Material.BROWN_STAINED_GLASS);
                break;
            case LIGHT_GRAY:
                filler.setType(Material.LIGHT_GRAY_STAINED_GLASS);
                break;
            case WHITE:
                filler.setType(Material.WHITE_STAINED_GLASS);
                break;
        }
    
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Back");
        meta.setLocalizedName(ui);
        filler.setItemMeta(meta);
        return filler;
    }

    ItemStack musicUI() {
        ItemStack s = new ItemStack(Material.JUKEBOX, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Music");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "A jukebox to play all of");
        lore.add(ChatColor.WHITE + "your favorites from C418!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Left click to view");
        lore.add(ChatColor.RED + "Right click to mute");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack musicUImuted() {
        ItemStack s = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Music");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "A jukebox to play all of");
        lore.add(ChatColor.WHITE + "your favorites from C418!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Right click to unmute");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack discBlocks() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_BLOCKS, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Blocks");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discChirp() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_CHIRP, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Chirp");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discFar() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_FAR, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Far");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discMall() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_MALL, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Mall");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discMellohi() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_MELLOHI, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Mellohi");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discStal() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_STAL, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Stal");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discStrad() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_STRAD, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Strad");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discWard() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_WARD, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Ward");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discWait() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_WAIT, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Wait");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack disc13() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_13, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "13");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack discCat() {
        ItemStack i = new ItemStack(Material.MUSIC_DISC_CAT, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Cat");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to play");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    ItemStack gadgetUI() {
        ItemStack s = new ItemStack(Material.PISTON, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Gadgets");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Some gadgets to mess");
        lore.add(ChatColor.WHITE + "with your friends!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Left click to view");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack gadgetActiveUI() {
        ItemStack s = new ItemStack(Material.PISTON, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Gadgets");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Some gadgets to mess");
        lore.add(ChatColor.WHITE + "with your friends!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Left click to view");
        lore.add(ChatColor.RED + "Right click to unequip");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack slimeLauncherEnabled() {
        ItemStack s = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Slime Launcher");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Shoot a slime at players");
        lore.add(ChatColor.WHITE + "to knock them back!");
        lore.add("");
        lore.add(ChatColor.RED + "Click to disable");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack paintballGunEnabled() {
        ItemStack s = new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Paintball Gun");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Paint the world or");
        lore.add(ChatColor.WHITE + "even other players!");
        lore.add("");
        lore.add(ChatColor.RED + "Click to disable");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack fireworkEnabled() {
        ItemStack s = new ItemStack(Material.FIREWORK_ROCKET, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Firework Cannon");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Launch fireworks in");
        lore.add(ChatColor.WHITE + "celebration!");
        lore.add("");
        lore.add(ChatColor.RED + "Click to disable");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack grapplingHookEnabled() {
        ItemStack s = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Grappling Hook");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Shoot this at any block to");
        lore.add(ChatColor.WHITE + "ride the rope to the block!");
        lore.add("");
        lore.add(ChatColor.RED + "Click to disable");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack fizzyLiftingDrinkEnabled() {
        ItemStack s = new ItemStack(Material.POTION, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Fizzy Lifting Drink");
        ((PotionMeta) sm).setColor(Color.fromRGB(0, 140, 255));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Where'd you get this?");
        lore.add(ChatColor.WHITE + "Did you steal it?");
        lore.add("");
        lore.add(ChatColor.RED + "Click to disable");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack stormbreakerEnabled() {
        ItemStack s = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Stormbreaker");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "UNLOCKED");
        lore.add("");
        lore.add(ChatColor.WHITE + "Become Thor! Left click");
        lore.add(ChatColor.WHITE + "to fly. Right click to ");
        lore.add(ChatColor.WHITE + "use lightning smash.");
        lore.add("");
        lore.add(ChatColor.RED + "Click to disable");
        sm.setLore(lore);
        sm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack stormbreakerDisabled() {
        ItemStack s = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Stormbreaker");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "UNLOCKED");
        lore.add("");
        lore.add(ChatColor.WHITE + "Become Thor! Left click");
        lore.add(ChatColor.WHITE + "to fly. Right click to ");
        lore.add(ChatColor.WHITE + "use lightning smash.");
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to enable");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack stormbreakerLockedOntime() {
        ItemStack s = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Stormbreaker");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "LOCKED");
        lore.add("");
        lore.add(ChatColor.WHITE + "10 days of ontime is required");
        lore.add(ChatColor.WHITE + "to unlock this gadget!");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack stormbreakerLockedEconomy() {
        ItemStack s = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Stormbreaker");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "LOCKED");
        lore.add("");
        lore.add(ChatColor.WHITE + "10 days of ontime is required");
        lore.add(ChatColor.WHITE + "to unlock this gadget!");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack slimeLauncherDisabled() {
        ItemStack s = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Slime Launcher");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Shoot a slime at players");
        lore.add(ChatColor.WHITE + "to knock them back!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to enable");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack paintballGunDisabled() {
        ItemStack s = new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Paintball Gun");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Paint the world or");
        lore.add(ChatColor.WHITE + "even other players!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to enable");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack fireworkDisabled() {
        ItemStack s = new ItemStack(Material.FIREWORK_ROCKET, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Fireworks");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Launch fireworks in");
        lore.add(ChatColor.WHITE + "celebration!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to enable");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack grapplingHookDisabled() {
        ItemStack s = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Grappling Hook");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Shoot this at any block to");
        lore.add(ChatColor.WHITE + "ride the rope to the block!");
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to enable");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack fizzyLiftingDrinkDisabled() {
        ItemStack s = new ItemStack(Material.POTION, 1);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Fizzy Lifting Drink");
        ((PotionMeta) sm).setColor(Color.fromRGB(0, 140, 255));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.WHITE + "Where'd you get this?");
        lore.add(ChatColor.WHITE + "Did you steal it?");
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to enable");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack arenaEnabled(int arena, String mapname) {
        ItemStack s = new ItemStack(Material.QUARTZ_BLOCK, arena);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Arena " + arena);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + mapname.replace("_", " "));
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to join");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    ItemStack arenaDisabled(int arena, String mapname) {
        ItemStack s = new ItemStack(Material.REDSTONE_BLOCK, arena);
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Arena " + arena);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + mapname.replace("_", " "));
        lore.add("");
        lore.add(ChatColor.GRAY + "Arena disabled");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }

    public ItemStack game(String displayname, String icon, String description) {
        ItemStack s = new ItemStack(Material.getMaterial(icon.toUpperCase()));
        ItemMeta sm = s.getItemMeta();
        sm.setDisplayName(Message.colorize(displayname));
        ArrayList<String> lore = new ArrayList<>();
        String wrapped = WordUtils.wrap(description, 24, "\n", true);
        lore.add("");
        for (String line : wrapped.split("\n")) lore.add(ChatColor.WHITE + line);
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to join");
        sm.setLore(lore);
        s.setItemMeta(sm);
        return s;
    }
}
