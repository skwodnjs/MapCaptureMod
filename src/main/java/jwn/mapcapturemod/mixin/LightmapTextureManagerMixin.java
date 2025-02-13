package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.custom.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float overrideGamma(Double gamma) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                return 100.0F;
            }
        }
        return MinecraftClient.getInstance().options.getGamma().getValue().floatValue();
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getSkyBrightness(F)F"))
    private float overrideSkyBrightness(ClientWorld clientWorld, float tickDelta) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                return 1.0F;
            }
        }
        return clientWorld.getSkyBrightness(1.0F);
    }
}
