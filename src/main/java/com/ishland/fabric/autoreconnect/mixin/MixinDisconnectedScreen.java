package com.ishland.fabric.autoreconnect.mixin;

import com.ishland.fabric.autoreconnect.LastServerUtils;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class MixinDisconnectedScreen extends Screen {

    @Shadow
    private int reasonHeight;

    @Shadow
    @Final
    private Screen parent;

    private int reconnectTimer;
    private ButtonWidget reconnectButton;

    protected MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(
            at = @At("TAIL"),
            method = "init()V"
    )
    private void onInit(CallbackInfo ci) {
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(height / 2 + reasonHeight / 2 + 9, height - 30);

        reconnectButton = addButton(new ButtonWidget(backButtonX, backButtonY + 24,
                200, 20, new LiteralText("Reconnect"),
                b -> LastServerUtils.reconnect(parent)));

        reconnectTimer = 120;
    }

    @Override
    public void tick() {

        reconnectButton.setMessage(
                new LiteralText(
                        "Reconnect (Auto reconnect in " +
                                String.format("%.2f", reconnectTimer / 20.0) + "s)"
                )
        );

        reconnectTimer--;

        if (reconnectTimer <= 0)
            LastServerUtils.reconnect(parent);

    }
}
