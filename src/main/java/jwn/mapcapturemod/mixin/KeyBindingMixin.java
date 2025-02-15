package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.mapcapture.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Inject(method = "onKeyPressed", at = @At("HEAD"), cancellable = true)
    private static void preventOnKeyPressed(InputUtil.Key key, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "isPressed", at = @At("HEAD"), cancellable = true)
    private void preventIsPressed(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                cir.cancel();
            }
        }
    }
}
