package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.mapcapture.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.JumpingMount;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    private void removeVignette(DrawContext context, @Nullable Entity entity, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void removeHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void removeExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderMountJumpBar", at = @At("HEAD"), cancellable = true)
    private void removeMountJumpBar(JumpingMount mount, DrawContext context, int x, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void removeStatusBars(DrawContext context, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderChat", at = @At("HEAD"), cancellable = true)
    private void removeChat(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void removeCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    private void removeHeldItemTooltip(DrawContext context, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }
}
