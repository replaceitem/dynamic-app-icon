package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import net.replaceitem.dynamicappicon.DynamicAppIcon;
import net.replaceitem.dynamicappicon.IconSetter;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IconSetter {
    @Shadow @Final private Window window;

    @Shadow @Nullable public Screen currentScreen;

    @Shadow protected abstract InputSupplier<InputStream> getDefaultResourceSupplier(String... segments) throws IOException;

    @Override
    public void setIcon(InputSupplier<InputStream> smallIcon, InputSupplier<InputStream> bigIcon) {
        if (MinecraftClient.IS_SYSTEM_MAC) {
            DynamicAppIcon.LOGGER.error("Mac is not yet supported");
            // MacWindowUtil.setApplicationIconImage();
        } else {
            this.window.setIcon(smallIcon, bigIcon);
        }
    }
    
    @Override
    public void resetIcon() {
        try {
            this.window.setIcon(this.getDefaultResourceSupplier("icons", "icon_16x16.png"), this.getDefaultResourceSupplier("icons", "icon_32x32.png"));
        } catch (IOException e) {
            DynamicAppIcon.LOGGER.error("Could not set icon back to default");
        }
    }
    
    
    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;updateWindowTitle()V"))
    private void onScreenChanged(Screen screen, CallbackInfo ci) {
        if(this.currentScreen instanceof TitleScreen || this.currentScreen instanceof SelectWorldScreen || this.currentScreen instanceof MultiplayerScreen) {
            resetIcon();
        }
    }
}