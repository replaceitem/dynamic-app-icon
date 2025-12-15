package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.client.gui.screens.FaviconTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FaviconTexture.class)
public interface WorldIconAccessor {
    @Accessor
    DynamicTexture getTexture();
}
