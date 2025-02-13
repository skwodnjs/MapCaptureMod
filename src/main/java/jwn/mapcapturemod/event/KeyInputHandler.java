package jwn.mapcapturemod.event;

import jwn.mapcapturemod.MapCaptureModClient;
import jwn.mapcapturemod.access.LightToggleAccess;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CONTROL = "key.category." + MapCaptureModClient.MOD_ID + ".control";
    public static final String KEY_MAP_CAPTURE = "key." + MapCaptureModClient.MOD_ID + ".map_capture";

    public static KeyBinding MapCaptureKey;

    private static int screenshotDelay = -1;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (screenshotDelay > 0) {
                screenshotDelay -= 1;
            } if (screenshotDelay == 0) {
                ScreenCapture.takeMapScreenshot(client);
                screenshotDelay = -1;
                if (client.player instanceof LightToggleAccess access) {
                    access.setLightToggle(false);
                }
            }
            if (MapCaptureKey.wasPressed()) {
                client.player.setAngles(client.player.getYaw(), 50);
                if (client.player instanceof LightToggleAccess access) {
                    access.setLightToggle(true);
                    screenshotDelay = 10;
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
