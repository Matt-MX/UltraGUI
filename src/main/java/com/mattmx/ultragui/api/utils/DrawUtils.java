package com.mattmx.ultragui.api.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vector4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class DrawUtils<GLW_SMALL_ROUNDED_CORNER_SLICES> extends DrawableHelper {
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

    public static void drawImage(Identifier i, double width, double height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        MinecraftClient.getInstance().getTextureManager().bindTexture(i);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(0.0D, height, 0.0D).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(width, height, 0.0D).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(width, 0.0D, 0.0D).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(0.0D, 0.0D, 0.0D).texture(0.0F, 0.0F).color(64, 64, 64, 255).next();
        tessellator.draw();
    }

    public static void drawCircle(MatrixStack matrices, Vector2f pos1, double radius, Vector4f color) {
        drawCircle(matrices.peek().getModel(), pos1, radius, color);
    }

    public static void drawCircle(Matrix4f matrix, Vector2f pos1, double radius, Vector4f color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        double origin_x = pos1.getX();
        double origin_y = pos1.getY();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        for (int i = 0; i <= 30; i++) {
            double angle = 2 * Math.PI * i / 30;
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            bufferBuilder.vertex(matrix, (float)(origin_x + x), (float)(origin_y + y), 0.0F).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
        }
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawQuarterCircle(Matrix4f matrix, Vector2f pos, double radius, double rot, Vector4f color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        double origin_x = pos.getX();
        double origin_y = pos.getY();
        int points = 30;
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)(origin_x), (float)(origin_y), 0.0F).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
        for (int i = 0; i <= points; i++) {
            double angle = (2 * (Math.PI / 4) * i / points) + rot;
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            bufferBuilder.vertex(matrix, (float)(origin_x + x), (float)(origin_y + y), 0.0F).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
        }
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawQuarterCircleOutline(Matrix4f matrix, Vector2f pos, double radius, double rot, Vector4f color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        double origin_x = pos.getX();
        double origin_y = pos.getY();
        int points = 30;
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);
        RenderSystem.lineWidth(2f);
        for (int i = 0; i <= points; i++) {
            double angle = (2 * (Math.PI / 4) * i / points) + rot;
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            bufferBuilder.vertex(matrix, (float)(origin_x + x), (float)(origin_y + y), 0.0F).color(color.getX(), color.getY(), color.getZ(), color.getW()).next();
        }
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    static void createRoundedCorners(Vector2f[] arr, int num) {
        // Generate the corner vertexes
        float slice = (float) Math.PI / 2 / num;
        int i;
        float a = 0;
        for (i = 0; i < num; a += slice, ++i) {
            arr[i] = new Vector2f((float) Math.cos(a), (float)Math.cos(a));
        }
    }

    public static int GLW_SMALL_ROUNDED_CORNER_SLICES = 5;
    public static Vector2f[] roundedCorners = new Vector2f[GLW_SMALL_ROUNDED_CORNER_SLICES];
    public static void drawRoundedRecGradFill(Matrix4f matrix, Vector2f pos1, Vector2f pos2, float radius, Vector4f topCol, Vector4f botCol) {
        createRoundedCorners(roundedCorners, GLW_SMALL_ROUNDED_CORNER_SLICES);
        float width = pos2.getX() - pos1.getX();
        float height = pos2.getY() - pos1.getY();
        float left = pos1.getX();
        float top = pos1.getY();
        float bottom = pos1.getY() + height - 1;
        float right = pos1.getX() + width - 1;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);
        int i;
        for (i = 0; i < GLW_SMALL_ROUNDED_CORNER_SLICES; i++) {
            bufferBuilder.vertex(matrix, left + radius - radius * roundedCorners[i].getX(),
                    bottom - radius + radius * roundedCorners[i].getY(),
                    0.0f).color(botCol.getX(), botCol.getY(), botCol.getZ(), botCol.getW()).next();
            bufferBuilder.vertex(matrix,left + radius - radius * roundedCorners[i].getX(),
                    top + radius - radius * roundedCorners[i].getY(),
                    0.0f).color(topCol.getX(), topCol.getY(), topCol.getZ(), topCol.getW()).next();
        }
        for (i = GLW_SMALL_ROUNDED_CORNER_SLICES - 1; i >=0; i--) {
            bufferBuilder.vertex(matrix,right - radius + radius * roundedCorners[i].getX(),
                    bottom - radius + radius * roundedCorners[i].getY(),
                    0.0f).color(botCol.getX(), botCol.getY(), botCol.getZ(), botCol.getW()).next();
            GL11.glColor4f(topCol.getX(), topCol.getY(), topCol.getZ(), topCol.getW());
            bufferBuilder.vertex(matrix,right - radius + radius * roundedCorners[i].getX(),
                    top + radius - radius * roundedCorners[i].getY(),
                    0.0f).color(topCol.getX(), topCol.getY(), topCol.getZ(), topCol.getW()).next();
        }
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
