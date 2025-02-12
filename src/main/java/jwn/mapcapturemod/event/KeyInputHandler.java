package jwn.mapcapturemod.event;

import jwn.mapcapturemod.MapCaptureMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CONTROL = "key.category." + MapCaptureMod.MOD_ID + ".control";
    public static final String KEY_GAMMA = "key." + MapCaptureMod.MOD_ID + ".gamma";

    public static KeyBinding drinkingKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(drinkingKey.wasPressed()) {
                // This happens when our custom key is pressed
                client.player.sendMessage(Text.literal("Hello I pressed a Key"), true);
            }
        });
    }

    public static void register() {
        drinkingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_GAMMA,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_CONTROL
        ));

        registerKeyInputs();
    }
}
