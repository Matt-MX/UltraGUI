package com.mattmx.ultragui.api.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Matrix4f;

import java.util.Arrays;
import java.util.List;

public class DrawUtils {
    public static void outline (MatrixStack matrices, double x1, double y1, double x2, double y2, List<Float> col, int t) {
        outline(matrices, x1, y1, x2, y2, col.get(0), col.get(1), col.get(2), col.get(3), t);
    }

    public static void outline(MatrixStack matrices, double x1, double y1, double x2, double y2, float r, float g, float b, float a, int t) {
        outline(matrices.peek().getModel(), x1, y1, x2, y2, r, g, b, a, t);
    }

    public static void outline(Matrix4f matrix, double x1, double y1, double x2, double y2, float r, float g, float b, float a, int t) {
        List<Float> c = Arrays.asList(r, g, b, a);
        /* 1----2
         * |    |
         * 3----4
         *
         */
        // Top line 1->2
        fill(matrix, x1 - t, y1 - t, x2, y1, c);
        // Left line 1->3
        fill(matrix, x1 - t, y1 - t, x1, y2, c);
        // Bottom line 3->4
        fill(matrix, x1 - t, y2 + t, x2, y2, c);
        // Right line 2->4
        fill(matrix, x2 + t, y1 - t, x2, y2, c);
    }

    public static void fill(MatrixStack matrices, double x1, double y1, double x2, double y2, List<Float> col) {
        fill(matrices.peek().getModel(), x1, y1, x2, y2, col);
    }

    public static void fill(Matrix4f matrix4f, double x1, double y1, double x2, double y2, List<Float> col) {
        fill(matrix4f, x1, y1, x2, y2, col.get(0), col.get(1), col.get(2), col.get(3));
    }

    public static void fill(MatrixStack matrices, double x1, double y1, double x2, double y2, float r, float g, float b, float a) {
        fill(matrices.peek().getModel(), x1, y1, x2, y2, r, g, b, a);
    }

    public static void fill(Matrix4f matrix, double x1, double y1, double x2, double y2, float r, float g, float b, float a) {
        double j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)x1, (float)y2, 0.0F).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)x2, (float)y2, 0.0F).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)x2, (float)y1, 0.0F).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)x1, (float)y1, 0.0F).color(r, g, b, a).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static Vector2f isOffScreen(Vector2f pos1, Vector2f pos2) {
        double x1 = pos1.getX();
        double y1 = pos1.getY();
        double x2 = pos2.getX();
        double y2 = pos2.getY();
        int w = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int h = MinecraftClient.getInstance().getWindow().getScaledHeight();
        double xChange = 0;
        double yChange = 0;
        if (x1 < 0) xChange = -x1;
        if (y1 < 0) yChange = -y1;
        if (x2 > w) xChange = w - x2;
        if (y2 > h) yChange = h - y2;
        return new Vector2f((float) xChange, (float) yChange);
    }
}
