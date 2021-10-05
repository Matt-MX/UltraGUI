package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class UltraOutline extends UltraQuad{
    public int t;

    public UltraOutline(Vector2f pos1, Vector2f pos2, Vector4f col, int thickness) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = col;
        this.t = thickness;
        this.isShown = true;
    }
    @Override
    public void draw(MatrixStack matrices) {
        if (!isShown) return;
        DrawUtils.outline(matrices, pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), ColorUtils.vec4fTo4FloatArray(color), t);
    }
}
