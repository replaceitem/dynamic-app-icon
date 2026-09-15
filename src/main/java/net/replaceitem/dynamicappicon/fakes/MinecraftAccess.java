package net.replaceitem.dynamicappicon.fakes;

import com.mojang.blaze3d.platform.NativeImage;

public interface MinecraftAccess {
    void dynamic_app_icon$setCustomIcon(NativeImage icon);
    void dynamic_app_icon$resetIcon();
}
