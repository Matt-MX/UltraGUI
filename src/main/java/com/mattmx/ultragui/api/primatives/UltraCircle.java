package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class UltraCircle extends UltraQuad {
    public double radius;

    public UltraCircle(Vector2f pos, double radius, Vector4f col) {
        super();
        this.pos1 = pos;
        this.pos2 = new Vector2f(0, 0);
        this.radius = radius;
        this.color = col;
    }

    @Override
    public void handleMouse() {
        //(mouseX + -x)^2 + (mouseY + -y)^2 <= radius^2
        this.isHovered = (Math.pow(mouseX + (-pos1.getX()), 2) + Math.pow(mouseY + (-pos1.getY()), 2) <= Math.pow(radius, 2));
    }

    @Override
    public void draw(MatrixStack matrices) {
        DrawUtils.drawCircle(matrices.peek().getModel(), pos1, radius, color);
    }
}
