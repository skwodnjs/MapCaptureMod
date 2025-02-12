package jwn.mapcapturemod;

import jwn.mapcapturemod.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class MapCaptureModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
