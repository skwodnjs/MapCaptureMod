package jwn.mapcapturemod.mapcapture;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.min;

public class MapCapture {
    private static int screenshotDelay = -1;

    static void mapCapture() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            System.err.println("MinecraftClient.getInstance().player is null");
            return;
        }
        
        // 전체화면 확인
        if (!client.options.getFullscreen().getValue()) {
            client.player.sendMessage(Text.translatable("message.map-capture-mod.full_screen_warning"), false);
            return;
        }

        // 해상도 확인
        Framebuffer framebuffer = client.getFramebuffer();
        if (framebuffer.viewportWidth != 1920 || framebuffer.viewportHeight != 1080) {
            client.player.sendMessage(Text.translatable("message.map-capture-mod.full_screen_warning"), false);
            return;
        }

        // 지도 들고있는지 확인
        if (client.player.getStackInHand(Hand.MAIN_HAND).getItem() != Items.FILLED_MAP || client.player.getStackInHand(Hand.OFF_HAND).getItem() != Items.AIR) {
            client.player.sendMessage(Text.translatable("message.map-capture-mod.inappropriate_item_in_hands"), false);
            return;
        }
        
        // 소리
        client.player.setAngles(client.player.getYaw(), 50);
        PositionedSoundInstance sound = PositionedSoundInstance.master(ModSounds.CAMERA_SOUND, 1.0f, 10.0f);
        client.getSoundManager().play(sound);
        
        // 촬영 시작 : Delay = 10 (tick)
        if (client.player instanceof LightToggleAccess access) {
            access.setLightToggle(true);
            setScreenshotDelay(10);
        }
    }

    static void takeMapScreenshot() {
        MinecraftClient client = MinecraftClient.getInstance();

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH;mm;ss").format(new Date());
        String coordinate = String.format("%.2f,%.2f,%.2f",
                client.player.getX(),
                client.player.getY(),
                client.player.getZ()
        );
        String file_name = timestamp + "_map(" + coordinate + ")";
        takePartialScreenshot(553, 176, 814, 814, file_name);
    }

    static void takePartialScreenshot(int x, int y, int width, int height, String file_name) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.getFramebuffer() == null) return;

        Framebuffer framebuffer = client.getFramebuffer();
        int screenWidth = framebuffer.viewportWidth;
        int screenHeight = framebuffer.viewportHeight;

        // OpenGL에서 픽셀 데이터 읽기 (RGBA 순서)
        int byteSize = width * height * 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(byteSize);
        RenderSystem.bindTexture(framebuffer.getColorAttachment());
        GL11.glReadPixels(x, y, min(width, screenWidth - x), min(height, screenHeight - y), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        // RGBA 데이터를 BGRA → ARGB 변환
        int[] pixels = new int[width * height];
        IntBuffer intBuffer = buffer.asIntBuffer();
        for (int i = 0; i < pixels.length; i++) {
            int rgba = intBuffer.get(i);
            int r = (rgba >> 0) & 0xFF;
            int g = (rgba >> 8) & 0xFF;
            int b = (rgba >> 16) & 0xFF;
            int a = (rgba >> 24) & 0xFF;
            pixels[i] = (a << 24) | (r << 16) | (g << 8) | b; // RGBA → ARGB 변환
        }

        // 이미지 생성 및 저장
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < pixels.length; i++) {
            image.setRGB(i % width, height - (i / width) - 1, pixels[i]);
        }

        // 파일로 저장
        File screenshotsDir = new File(client.runDirectory, "screenshots/map");
        if (!screenshotsDir.exists()) screenshotsDir.mkdirs();
        File file = new File(screenshotsDir, file_name + ".png");

        try {
            ImageIO.write(image, "PNG", file);
            client.player.sendMessage(Text.translatable("message.map-capture-mod.saved"), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setScreenshotDelay(int screenshotDelay) {
        MapCapture.screenshotDelay = screenshotDelay;
    }

    public static int getScreenshotDelay() {
        return screenshotDelay;
    }

    private static void screenshotDelayTick() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (screenshotDelay > 0) {
                screenshotDelay -= 1;
            } if (screenshotDelay == 0) {
                MapCapture.takeMapScreenshot();
                screenshotDelay = -1;
                if (client.player instanceof LightToggleAccess access) {
                    access.setLightToggle(false);
                }
            }
        });
    }

    public static void register() {
        screenshotDelayTick();
    }
}

