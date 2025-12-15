package net.replaceitem.dynamicappicon;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;

public class DynamicAppIcon implements ClientModInitializer {
    
    public static final Logger LOGGER = LoggerFactory.getLogger("Dynamic App Icon");

    @Override
    public void onInitializeClient() {
        
    }

    public static void setIcon(NativeImage icon) {
        ((IconSetter) Minecraft.getInstance()).setIcon(icon);
    }

    public static void setIcon(byte[] favicon) {
        if(favicon == null) {
            resetIcon();
            return;
        }

        try (NativeImage image = NativeImage.read(favicon)) {
            setIcon(image);
        } catch (IOException e) {
            LOGGER.error("Could not set icon", e);
        }
    }

    public static void resetIcon() {
        ((IconSetter) Minecraft.getInstance()).resetIcon();
    }
}
