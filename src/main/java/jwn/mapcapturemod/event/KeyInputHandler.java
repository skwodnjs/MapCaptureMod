package jwn.mapcapturemod.event;

import jwn.mapcapturemod.MapCaptureModClient;
import jwn.mapcapturemod.access.LightToggleAccess;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CONTROL = "key.category." + MapCaptureModClient.MOD_ID + ".control";
    public static final String KEY_LIGHT_TOGGLE = "key." + MapCaptureModClient.MOD_ID + ".light_toggle";

    public static KeyBinding lightToggleKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (lightToggleKey.wasPressed()) {
                // This happens when our custom key is pressed
                if (client.player instanceof LightToggleAccess access) {
                    boolean currentValue = access.isLightToggle();
                    access.setLightToggle(!currentValue);
                    client.player.sendMessage(Text.literal("Light Toggle: " + (access.isLightToggle() ? "on" : "off")), true);
                }
            }
        });
    }

    public static void register() {
        lightToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_LIGHT_TOGGLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_CONTROL
        ));

        registerKeyInputs();
    }
}
