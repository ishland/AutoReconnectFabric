package com.ishland.fabric.autoreconnect;

import com.google.common.base.Preconditions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;

public class LastServerUtils {

    private static ServerInfo lastServer = null;

    public static ServerInfo getLastServer() {
        return lastServer;
    }

    public static void setLastServer(ServerInfo lastServer) {
        Preconditions.checkArgument(lastServer != null, "Server is null");
        LastServerUtils.lastServer = lastServer;
    }

    public static void reconnect(Screen screen) {
        Preconditions.checkArgument(screen != null, "screen is null");
        Preconditions.checkState(lastServer != null, "LastServer is null");

        MinecraftClient.getInstance().openScreen(
                new ConnectScreen(screen, MinecraftClient.getInstance(), getLastServer())
        );
    }
}
