package jwn.mapcapturemod.mapcapture;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static jwn.mapcapturemod.MapCaptureModClient.MOD_ID;
import static jwn.mapcapturemod.mapcapture.MapCapture.getScreenshotDelay;
import static jwn.mapcapturemod.mapcapture.MapCapture.mapCapture;


public class KeyInputHandler {
    public static final String KEY_CATEGORY_CONTROL = "key.category." + MOD_ID + ".control";
    public static final String KEY_MAP_CAPTURE = "key." + MOD_ID + ".map_capture";

    public static KeyBinding MapCaptureKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MapCaptureKey.wasPressed()) {
                if (getScreenshotDelay() == -1) {
                    mapCapture();
                }
            }
        });
    }

    public static void register() {
        MapCaptureKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_MAP_CAPTURE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_CONTROL
        ));

        registerKeyInputs();
    }
}
