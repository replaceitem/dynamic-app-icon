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

    public static void setIcon(InputSupplier<InputStream> smallIcon, InputSupplier<InputStream> bigIcon) {
        ((IconSetter) MinecraftClient.getInstance()).setIcon(smallIcon, bigIcon);
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
        // using unscaled icon twice, might want to scale to 16 and 32
        setIcon(() -> new ByteArrayInputStream(favicon), () -> new ByteArrayInputStream(favicon));
    }
}
