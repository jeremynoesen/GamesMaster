package jndev.gamesmaster.gadget.gadgets;

import jndev.gamesmaster.GamesMaster;
import jndev.gamesmaster.gadget.GadgetItem;
import jndev.gamesmaster.lobby.LobbyHandler;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FireworkCannon {

    private static LobbyHandler lh = new LobbyHandler();

    private static Color getColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLUE;
        }
        if (i == 3) {
            c = Color.FUCHSIA;
        }
        if (i == 4) {
            c = Color.LIME;
        }
        if (i == 5) {
            c = Color.ORANGE;
        }
        if (i == 6) {
            c = Color.PURPLE;
        }
        if (i == 7) {
            c = Color.RED;
        }
        if (i == 8) {
            c = Color.TEAL;
        }
        if (i == 9) {
            c = Color.YELLOW;
        }

        return c;
    }

    public static void use(Player p) {
        lh.setGadget(p, GadgetItem.FIREWORK_CANNON_RELOAD.getItem());
        org.bukkit.entity.Firework fw = (org.bukkit.entity.Firework) p.getWorld().spawnEntity(p.getEyeLocation()
                        .add(p.getLocation().getDirection().normalize()),
                EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        Random r = new Random();
        int rt = r.nextInt(5) + 1;
        Type type = Type.BALL;
        if (rt == 1) type = Type.BALL;
        if (rt == 2) type = Type.BALL_LARGE;
        if (rt == 3) type = Type.BURST;
        if (rt == 4) type = Type.CREEPER;
        if (rt == 5) type = Type.STAR;
        int r1i = r.nextInt(9) + 1;
        Color c1 = getColor(r1i);
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).with(type).trail(false).build();
        fwm.addEffect(effect);
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);
        fw.setFireworkMeta(fwm);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (lh.activeGadget(p, GadgetItem.FIREWORK_CANNON_RELOAD.getItem())) {
                    lh.setGadget(p, GadgetItem.FIREWORK_CANNON.getItem());
                }
            }
        }.runTaskLater(GamesMaster.getInstance(), 10);
    }
}
