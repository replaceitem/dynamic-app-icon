package net.replaceitem.dynamicappicon;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.InputSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DynamicAppIcon implements ClientModInitializer {
    
    public static final Logger LOGGER = LoggerFactory.getLogger("Dynamic App Icon");

    @Override
    public void onInitializeClient() {
        
    }

    public static void setIcon(InputSupplier<InputStream> icon) {
        ((IconSetter) MinecraftClient.getInstance()).setIcon(icon);
    }

    public static void resetIcon() {
        ((IconSetter) MinecraftClient.getInstance()).resetIcon();
    }

    public static void setIcon(NativeImage nativeImage) {
        try {
            byte[] bytes = nativeImage.getBytes();
            setIcon(bytes);
        } catch (IOException e) {
            LOGGER.error("Could not set icon: ", e);
        }
    }

    public static void setIcon(byte[] favicon) {
        if(favicon == null) resetIcon();
        else setIcon(() -> new ByteArrayInputStream(favicon));
    }
}
