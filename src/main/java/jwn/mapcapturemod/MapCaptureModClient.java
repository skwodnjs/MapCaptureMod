package jwn.mapcapturemod;

import jwn.mapcapturemod.mapcapture.KeyInputHandler;
import jwn.mapcapturemod.mapcapture.ModSounds;
import jwn.mapcapturemod.mapcapture.MapCapture;
import net.fabricmc.api.ClientModInitializer;

public class MapCaptureModClient implements ClientModInitializer {
    public static final String MOD_ID = "map-capture-mod";

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        MapCapture.register();
        ModSounds.register();
    }
}
