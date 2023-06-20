package dev.xpple.serverconsents;

import io.netty.buffer.Unpooled;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ServerConsents extends JavaPlugin {

    private static final String SOME_UNIVERSAL_NAMESPACE = "noconsent";
    private static final NamespacedKey MODS_CHANNEL = new NamespacedKey(SOME_UNIVERSAL_NAMESPACE, "mods");
    private static final NamespacedKey FEATURES_CHANNEL = new NamespacedKey(SOME_UNIVERSAL_NAMESPACE, "features");

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        boolean enabled = this.getConfig().getBoolean("enabled");
        if (enabled) {
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, MODS_CHANNEL.toString());
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, FEATURES_CHANNEL.toString());
            this.getServer().getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void playerRegisterChannel(PlayerRegisterChannelEvent event) {
                    if (event.getChannel().equals(MODS_CHANNEL.toString())) {
                        List<String> illegalMods = ServerConsents.this.getConfig().getStringList("illegalMods");
                        FriendlyByteBuf modsBuf = new FriendlyByteBuf(Unpooled.buffer());
                        modsBuf.writeCollection(illegalMods, FriendlyByteBuf::writeUtf);
                        event.getPlayer().sendPluginMessage(ServerConsents.this, MODS_CHANNEL.toString(), modsBuf.array());
                    } else if (event.getChannel().equals(FEATURES_CHANNEL.toString())) {
                        List<String> illegalFeatures = ServerConsents.this.getConfig().getStringList("illegalFeatures");
                        FriendlyByteBuf featuresBuf = new FriendlyByteBuf(Unpooled.buffer());
                        featuresBuf.writeCollection(illegalFeatures, FriendlyByteBuf::writeUtf);
                        event.getPlayer().sendPluginMessage(ServerConsents.this, FEATURES_CHANNEL.toString(), featuresBuf.array());
                    }
                }
            }, this);
        }
    }
}
