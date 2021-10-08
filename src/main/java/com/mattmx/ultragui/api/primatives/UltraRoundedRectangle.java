package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class UltraRoundedRectangle extends UltraQuad {
    public double radius;
    public boolean outline;
    public Vector4f outline_color = new Vector4f(1f, 1f, 1f, 1f);

    public UltraRoundedRectangle(Vector2f pos1, Vector2f pos2, double radius, Vector4f col) {
        super();
        this.color = col;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.radius = radius;
    }

    @Override
    public void draw(MatrixStack matrices) {
        // Top quad
        DrawUtils.fill(matrices, pos1.getX() + (float)radius, pos1.getY(),
                                pos2.getX() - (float)radius, pos1.getY() + (float)radius,
                                    ColorUtils.vec4fTo4FloatArray(color));
        // Bottom quad
        DrawUtils.fill(matrices, pos1.getX() + (float)radius, pos2.getY() - (float)radius,
                pos2.getX() - (float)radius, pos2.getY(),
                ColorUtils.vec4fTo4FloatArray(color));
        // Left quad
        DrawUtils.fill(matrices, pos1.getX(), pos1.getY() + (float)radius,
                pos1.getX() + (float)radius, pos2.getY() - (float)radius,
                ColorUtils.vec4fTo4FloatArray(color));
        // Right quad
        DrawUtils.fill(matrices, pos2.getX() - (float)radius, pos1.getY() + (float)radius,
                pos2.getX(), pos2.getY() - (float)radius,
                ColorUtils.vec4fTo4FloatArray(color));
        // Middle quad
        DrawUtils.fill(matrices, pos1.getX() + (float)radius, pos1.getY() + (float)radius,
                pos2.getX() - (float)radius, pos2.getY() - (float)radius,
                ColorUtils.vec4fTo4FloatArray(color));
        // Top left circ
        DrawUtils.drawQuarterCircle(matrices.peek().getModel(),
                new Vector2f(pos1.getX() + (float)radius, pos1.getY() + (float)radius), radius, Math.toRadians(180), color);
        // Top right circ
        DrawUtils.drawQuarterCircle(matrices.peek().getModel(),
                new Vector2f(pos2.getX() - (float)radius, pos1.getY() + (float)radius), radius, Math.toRadians(90), color);
        // Bottom left circ
        DrawUtils.drawQuarterCircle(matrices.peek().getModel(),
                new Vector2f(pos1.getX() + (float)radius, pos2.getY() - (float)radius), radius, Math.toRadians(270), color);
        // Bottom right circ
        DrawUtils.drawQuarterCircle(matrices.peek().getModel(),
                new Vector2f(pos2.getX() - (float)radius, pos2.getY() - (float)radius), radius, 0, color);
        if (outline) drawOutline(matrices);
    }

    public void drawOutline(MatrixStack matrices) {
        // Top quad
        DrawUtils.fill(matrices, pos1.getX() + (float)radius, pos1.getY(),
                pos2.getX() - (float)radius, pos1.getY() + 1f,
                ColorUtils.vec4fTo4FloatArray(outline_color));
        // Bottom quad
        DrawUtils.fill(matrices, pos1.getX() + (float)radius, pos2.getY() - 1f,
                pos2.getX() - (float)radius, pos2.getY(),
                ColorUtils.vec4fTo4FloatArray(outline_color));
        // Left quad
        DrawUtils.fill(matrices, pos1.getX(), pos1.getY() + (float)radius,
                pos1.getX() + 1f, pos2.getY() - (float)radius,
                ColorUtils.vec4fTo4FloatArray(outline_color));
        // Right quad
        DrawUtils.fill(matrices, pos2.getX() - 1f, pos1.getY() + (float)radius,
                pos2.getX(), pos2.getY() - (float)radius,
                ColorUtils.vec4fTo4FloatArray(outline_color));
        // Top left circ
        DrawUtils.drawQuarterCircleOutline(matrices.peek().getModel(),
                new Vector2f(pos1.getX() + (float)radius, pos1.getY() + (float)radius), radius, Math.toRadians(180), outline_color);
        // Top right circ
        DrawUtils.drawQuarterCircleOutline(matrices.peek().getModel(),
                new Vector2f(pos2.getX() - (float)radius, pos1.getY() + (float)radius), radius, Math.toRadians(90), outline_color);
        // Bottom left circ
        DrawUtils.drawQuarterCircleOutline(matrices.peek().getModel(),
                new Vector2f(pos1.getX() + (float)radius, pos2.getY() - (float)radius), radius, Math.toRadians(270), outline_color);
        // Bottom right circ
        DrawUtils.drawQuarterCircleOutline(matrices.peek().getModel(),
                new Vector2f(pos2.getX() - (float)radius, pos2.getY() - (float)radius), radius, 0, outline_color);
    }
}
