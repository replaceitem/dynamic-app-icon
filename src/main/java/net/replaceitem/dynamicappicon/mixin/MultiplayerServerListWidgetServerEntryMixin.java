package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.replaceitem.dynamicappicon.DynamicAppIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JoinMultiplayerScreen.class)
public class MultiplayerServerListWidgetServerEntryMixin {
    @Inject(method = "join(Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("HEAD"))
    private void onMultiplayerStart(ServerData entry, CallbackInfo ci) {
        DynamicAppIcon.setIcon(entry.getIconBytes());
    }
}
