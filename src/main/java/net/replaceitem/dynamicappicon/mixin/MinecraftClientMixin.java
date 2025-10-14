package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.input.SystemKeycodes;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.DefaultResourcePack;
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

import java.io.IOException;
import java.nio.ByteBuffer;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IconSetter {
    @Shadow @Final private Window window;
    @Shadow @Nullable public Screen currentScreen;
    @Shadow public abstract DefaultResourcePack getDefaultResourcePack();
    
    @Override
    public void setIcon(NativeImage icon) {
        if (SystemKeycodes.IS_MAC_OS) {
            DynamicAppIcon.LOGGER.error("Mac is not yet supported");
            return;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            GLFWImage.Buffer buffer = GLFWImage.malloc(1, memoryStack);
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(icon.getWidth() * icon.getHeight() * 4);
            byteBuffer.asIntBuffer().put(icon.copyPixelsAbgr());
            buffer.position(0);
            buffer.width(icon.getWidth());
            buffer.height(icon.getHeight());
            buffer.pixels(byteBuffer);
            GLFW.glfwSetWindowIcon(this.window.getHandle(), buffer.position(0));
            MemoryUtil.memFree(byteBuffer);
        }
    }
    
    @Override
    public void resetIcon() {
        try {
            this.window.setIcon(this.getDefaultResourcePack(), SharedConstants.getGameVersion().stable() ? Icons.RELEASE : Icons.SNAPSHOT);
        } catch (IOException iOException) {
            DynamicAppIcon.LOGGER.error("Could not set icon back to default");
        }
    }
    
    
    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;updateWindowTitle()V"))
    private void onScreenChanged(Screen screen, CallbackInfo ci) {
        if(this.currentScreen instanceof TitleScreen || this.currentScreen instanceof SelectWorldScreen || this.currentScreen instanceof MultiplayerScreen) {
            resetIcon();
        }
    }
}
