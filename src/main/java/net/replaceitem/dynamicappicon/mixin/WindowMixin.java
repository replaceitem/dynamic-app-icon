package net.replaceitem.dynamicappicon.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import net.replaceitem.dynamicappicon.fakes.WindowAccess;
import org.jspecify.annotations.Nullable;
import org.lwjgl.sdl.SDLError;
import org.lwjgl.sdl.SDLSurface;
import org.lwjgl.sdl.SDLVideo;
import org.lwjgl.sdl.SDL_Surface;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Window.class)
public abstract class WindowMixin implements WindowAccess {
    @Shadow private static @Nullable SDL_Surface createIconSurface(NativeImage image) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow @Final private long handle;
    @Shadow @Final private static Logger LOGGER;

    @Override
    public void dynamic_app_icon$setCustomIcon(NativeImage icon) {
        SDL_Surface surface = createIconSurface(icon);
        if (surface == null) {
            LOGGER.warn("Failed to create SDL surface for {}x{} custom icon: {}", icon.getWidth(), icon.getHeight(), SDLError.SDL_GetError());
        }
        if (!SDLVideo.SDL_SetWindowIcon(this.handle, surface)) {
            LOGGER.warn("Failed to set custom window icon: {}", SDLError.SDL_GetError());
        }
        SDLSurface.SDL_DestroySurface(surface);
    }
}
