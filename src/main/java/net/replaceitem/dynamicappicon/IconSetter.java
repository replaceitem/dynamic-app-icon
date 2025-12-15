package net.replaceitem.dynamicappicon;

import com.mojang.blaze3d.platform.NativeImage;

public interface IconSetter {
    void setIcon(NativeImage icon);
    void resetIcon();
}
