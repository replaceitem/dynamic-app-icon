package net.replaceitem.dynamicappicon.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.replaceitem.dynamicappicon.IconSetter;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow private @Nullable Screen screen;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateTitle()V"))
    private void onScreenChanged(Screen screen, CallbackInfo ci) {
        if(this.screen instanceof TitleScreen || this.screen instanceof SelectWorldScreen || this.screen instanceof JoinMultiplayerScreen) {
            ((IconSetter) this.minecraft).resetIcon();
        }
    }
}
