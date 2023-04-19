package net.replaceitem.dynamicappicon;

import net.minecraft.resource.InputSupplier;

import java.io.InputStream;

public interface IconSetter {
    void setIcon(InputSupplier<InputStream> smallIcon, InputSupplier<InputStream> bigIcon);
}
