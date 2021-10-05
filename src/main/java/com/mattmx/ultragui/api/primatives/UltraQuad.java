package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class UltraQuad extends UltraElement {

    public UltraQuad(Vector2f pos1, Vector2f pos2, Vector4f col) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = col;
        this.isShown = true;
    }

    public UltraQuad() {
    }

    @Override
    public void draw(MatrixStack matrices) {
        if (!isShown) return;
        DrawUtils.fill(matrices, pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), ColorUtils.vec4fTo4FloatArray(color));
    }

    @Override
    public void handleMouse() {
        if (!isShown) {
            isHovered = false;
            return;
        };
        this.isHovered =
                mouseX >= pos1.getX() &&
                mouseY >= pos1.getY() &&
                mouseX <= pos2.getX() &&
                mouseY <= pos2.getY();
    }

    public final void setSize(Vector2f size) {
        this.pos2 = new Vector2f(this.pos1.getX() + size.getX(),
                this.pos1.getY() + size.getY());
    }
}
