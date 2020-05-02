package com.ishland.fabric.autoreconnect.mixin;

import com.ishland.fabric.autoreconnect.LastServerUtils;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MixinConnectScreen extends Screen {

    protected MixinConnectScreen(Text title) {
        super(title);
    }

    @Inject(
            at = @At("HEAD"),
            method = "connect"
    )
    private void onConnect(String address, int port, CallbackInfo ci) {
        LastServerUtils.setLastServer(new ServerInfo(
                "AutoReconnectFabric Last Server",
                address + ":" + port,
                false
        ));
    }

}
