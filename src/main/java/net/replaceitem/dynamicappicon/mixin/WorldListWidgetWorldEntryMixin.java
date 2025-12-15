package net.replaceitem.dynamicappicon.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.gui.screens.FaviconTexture;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.replaceitem.dynamicappicon.DynamicAppIcon;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListWidgetWorldEntryMixin {
    @Shadow @Final private @Nullable FaviconTexture icon;

    @Inject(method = "joinWorld", at = @At("HEAD"))
    private void onSingleplayerStart(CallbackInfo ci) {
        if(this.icon == null) return;
        DynamicTexture texture = ((WorldIconAccessor) this.icon).getTexture();
        if(texture == null) return;
        NativeImage image = texture.getPixels();
        if(image == null) return;
        DynamicAppIcon.setIcon(image);
    }
}
