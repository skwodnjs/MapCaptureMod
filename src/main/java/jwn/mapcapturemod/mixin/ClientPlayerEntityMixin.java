package jwn.mapcapturemod.mixin;

import jwn.mapcapturemod.custom.LightToggleAccess;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements LightToggleAccess {
    public boolean lightToggle = false;

    @Override
    public boolean isLightToggle() {
        return lightToggle;
    }

    @Override
    public void setLightToggle(boolean value) {
        this.lightToggle = value;
    }
}
