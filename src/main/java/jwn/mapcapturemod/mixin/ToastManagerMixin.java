package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.mapcapture.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public class ToastManagerMixin {
    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private void cancelDraw(DrawContext context, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }
}
