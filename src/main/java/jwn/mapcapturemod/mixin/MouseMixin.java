package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.mapcapture.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void preventOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void preventOnMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
    private void preventOnCursorPos(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }
}
