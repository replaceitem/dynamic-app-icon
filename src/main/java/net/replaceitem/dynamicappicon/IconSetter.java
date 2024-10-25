package net.replaceitem.dynamicappicon;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.InputSupplier;

import java.io.InputStream;

public interface IconSetter {
    void setIcon(NativeImage icon);
    void resetIcon();
}
