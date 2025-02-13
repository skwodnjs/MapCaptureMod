package jwn.mapcapturemod;

import jwn.mapcapturemod.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class MapCaptureModClient implements ClientModInitializer {
    public static final String MOD_ID = "map-capture-mod";

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
