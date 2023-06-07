package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.client.gui.screen.world.WorldIcon;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldIcon.class)
public interface WorldIconAccessor {
    @Accessor
    NativeImageBackedTexture getTexture();
}
