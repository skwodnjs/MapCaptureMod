package jwn.mapcapturemod;

import jwn.mapcapturemod.custom.KeyInputHandler;
import jwn.mapcapturemod.custom.ModSounds;
import jwn.mapcapturemod.custom.ScreenCapture;
import net.fabricmc.api.ClientModInitializer;

public class MapCaptureModClient implements ClientModInitializer {
    public static final String MOD_ID = "map-capture-mod";

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ScreenCapture.register();
        ModSounds.register();
    }
}
