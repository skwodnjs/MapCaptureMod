package jwn.mapcapturemod.mapcapture;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static jwn.mapcapturemod.MapCaptureModClient.MOD_ID;

public class ModSounds {
    public static final Identifier CAMERA_SOUND_ID = Identifier.of(MOD_ID, "camera");
    public static final SoundEvent CAMERA_SOUND = SoundEvent.of(CAMERA_SOUND_ID);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, CAMERA_SOUND_ID, CAMERA_SOUND);
    }
}