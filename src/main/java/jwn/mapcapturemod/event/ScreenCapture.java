package jwn.mapcapturemod.event;

import com.mojang.blaze3d.systems.RenderSystem;
import jwn.mapcapturemod.access.LightToggleAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
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

public class ScreenCapture {
    static void takeMapScreenshot(MinecraftClient client) {
        client.player.sendMessage(Text.literal("It only works normally when the screen size is 1920 x 1080."), false);

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH;mm;ss").format(new Date());
        String coordinate = String.format("%.2f,%.2f,%.2f",
                client.player.getX(),
                client.player.getY(),
                client.player.getZ()
        );
        String file_name = timestamp + "_Map(" + coordinate + ")";
        takePartialScreenshot(client, 553, 176, 814, 814, file_name);
    }

    static void takePartialScreenshot(MinecraftClient client, int x, int y, int width, int height, String file_name) {
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
        File screenshotsDir = new File(client.runDirectory, "screenshots");
        if (!screenshotsDir.exists()) screenshotsDir.mkdirs();
        File file = new File(screenshotsDir, file_name + ".png");

        try {
            ImageIO.write(image, "PNG", file);
            client.player.sendMessage(Text.literal("Screenshot saved!"), false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MemoryUtil.memFree(buffer); // 메모리 해제
    }
}

