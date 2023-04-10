package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.replaceitem.dynamicappicon.DynamicAppIcon;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldListWidget.WorldEntry.class)
public class WorldListWidgetWorldEntryMixin {
    @Shadow @Final private @Nullable NativeImageBackedTexture icon;

    @Inject(method = "start", at = @At("HEAD"))
    private void onSingleplayerStart(CallbackInfo ci) {
        if(this.icon == null) return;
        NativeImage image = this.icon.getImage();
        if(image == null) return;
        DynamicAppIcon.setIcon(image);
    }
}
