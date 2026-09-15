package net.replaceitem.dynamicappicon.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.util.Util;
import net.replaceitem.dynamicappicon.DynamicAppIcon;
import net.replaceitem.dynamicappicon.fakes.MinecraftAccess;
import net.replaceitem.dynamicappicon.fakes.WindowAccess;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import java.io.IOException;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements MinecraftAccess {
    @Shadow @Final private Window window;

    @Shadow @Final private VanillaPackResources vanillaPackResources;
    @Shadow @Final private static Logger LOGGER;

    @Override
    public void dynamic_app_icon$setCustomIcon(NativeImage icon) {
        try {
            Util.OS platform = Util.getPlatform();
            switch (platform) {
                case WINDOWS:
                case LINUX:
                case SOLARIS:
                case OSX:
                    //noinspection DataFlowIssue
                    ((WindowAccess) (Object) this.window).dynamic_app_icon$setCustomIcon(icon);
                    break;
                default:
                    LOGGER.warn("Not setting icon for unrecognized platform: {}", platform);
            }
        } catch (RuntimeException e) {
            DynamicAppIcon.LOGGER.error("Could not set dynamic icon", e);
        }
    }
    
    @Override
    public void dynamic_app_icon$resetIcon() {
        try {
            this.window.setIcon(this.vanillaPackResources.fullResources(), SharedConstants.getCurrentVersion().stable() ? IconSet.RELEASE : IconSet.SNAPSHOT);
        } catch (RuntimeException | IOException e) {
            DynamicAppIcon.LOGGER.error("Could not set icon back to default", e);
        }
    }
}
