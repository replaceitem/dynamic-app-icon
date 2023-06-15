package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.InputSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.IOException;
import java.io.InputStream;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Invoker
    InputSupplier<InputStream> callGetDefaultResourceSupplier(String... segments) throws IOException;
}
