package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.mapcapture.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.MapRenderState;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.item.map.MapDecoration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MapRenderer.class)
public abstract class MapRendererMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/MapRenderer;createDecoration(Lnet/minecraft/item/map/MapDecoration;)Lnet/minecraft/client/render/MapRenderState$Decoration;"))
    private MapRenderState.Decoration removePlayerIcons(MapRenderer mapRenderer, MapDecoration decoration) {
        if (MinecraftClient.getInstance().player instanceof LightToggleAccess access) {
            if (access.isLightToggle()) {
                return new MapRenderState.Decoration();
            }
        } return invokeCreateDecoration(decoration);

    }

    @Invoker("createDecoration")
    abstract MapRenderState.Decoration invokeCreateDecoration(MapDecoration decoration);
}
