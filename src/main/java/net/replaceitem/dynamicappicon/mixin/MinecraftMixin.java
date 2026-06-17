package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.util.Util;
import net.replaceitem.dynamicappicon.DynamicAppIcon;
import net.replaceitem.dynamicappicon.IconSetter;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import java.io.IOException;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements IconSetter {
    @Shadow @Final private Window window;
    @Shadow public abstract VanillaPackResources getVanillaPackResources();

    @Shadow @Final public Gui gui;

    @Override
    public void setIcon(NativeImage icon) {
        if (Util.getPlatform() == Util.OS.OSX) {
            DynamicAppIcon.LOGGER.error("Mac is not yet supported");
            return;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            GLFWImage.Buffer buffer = GLFWImage.malloc(1, memoryStack);
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(icon.getWidth() * icon.getHeight() * 4);
            byteBuffer.asIntBuffer().put(icon.getPixelsABGR());
            buffer.position(0);
            buffer.width(icon.getWidth());
            buffer.height(icon.getHeight());
            buffer.pixels(byteBuffer);
            GLFW.glfwSetWindowIcon(this.window.handle(), buffer.position(0));
            MemoryUtil.memFree(byteBuffer);
        }
    }
    
    @Override
    public void resetIcon() {
        try {
            this.window.setIcon(this.getVanillaPackResources(), SharedConstants.getCurrentVersion().stable() ? IconSet.RELEASE : IconSet.SNAPSHOT);
        } catch (IOException iOException) {
            DynamicAppIcon.LOGGER.error("Could not set icon back to default");
        }
    }
}
