package com.mattmx.ultragui.example.gui.widget;

import com.mattmx.ultragui.api.primatives.UltraCircle;
import com.mattmx.ultragui.api.primatives.UltraSlider;
import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;
/********************************************************
 *              Example of how to create                *
 *                  your own widgets                    *
 ********************************************************
 * Created as an example, a slider complete with animations
 * for hover and click events.
 *
 */

public class CustomSlider extends UltraSlider {
    public Vector4f hoverCol = new Vector4f(0.223f, 0.466f, 0.858f, 0.5f);
    public Vector4f dragCol = new Vector4f(0.419f, 0.596f, 0.882f, 0.5f);
    public Vector4f bgColor;
    public Vector4f sliderColor;
    public UltraCircle circle;
    public CustomSlider(Vector2f pos1, Vector2f widhei, double minVal, double maxVal, Vector4f backgroundCol, Vector4f sliderCol) {
        super(pos1, widhei, minVal, maxVal, new Vector4f(0f, 0f, 0f, 0f), new Vector4f(0f, 0f, 0f, 0f));
        this.circle = new UltraCircle(new Vector2f(sliderWidget.pos1.getX(), sliderWidget.pos1.getY() + (sliderWidget.pos2.getY() - sliderWidget.pos1.getY()) / 2), widhei.getY() / 2 + 1, sliderCol);
        this.bgColor = backgroundCol;
        this.sliderColor = sliderCol;
    }

    @Override
    public void draw(MatrixStack matrices) {
        if (!isShown) return;
        super.draw(matrices);
        circle.pos1 = new Vector2f(sliderWidget.pos1.getX(), sliderWidget.pos1.getY() + (sliderWidget.pos2.getY() - sliderWidget.pos1.getY()) / 2);
        renderCircles(matrices);
        DrawUtils.fill(matrices, background.pos1.getX(), background.pos1.getY() + 4,
                background.pos2.getX(), background.pos2.getY() - 4, ColorUtils.vec4fTo4FloatArray(bgColor));
        renderHover(matrices);
        //Make this more efficient
        circle.init(matrices);
    }

    float anim_hv = 0;
    float anim_cl = 0;
    public void renderHover(MatrixStack matrices) {
        double r = circle.radius * 2 * anim_hv;
        double r2 = r * 1.5 * anim_cl;
        if (circle.isLMBClicked) {
            if (anim_cl < 1) anim_cl = anim_cl + 0.1f;
        } else {
            if (anim_cl > 0) anim_cl = anim_cl - 0.1f;
        }
        if (anim_cl != 0) DrawUtils.drawCircle(matrices.peek().getModel(), circle.pos1, r2, dragCol);
        if ((circle.isHovered || background.isDragging)) {
            if (anim_hv < 1) anim_hv = anim_hv + 0.1f;
        } else {
            if (anim_hv > 0) anim_hv = anim_hv - 0.2f;
        }
        if (anim_hv != 0) DrawUtils.drawCircle(matrices.peek().getModel(), circle.pos1, r, hoverCol);
    }

    public void renderCircles(MatrixStack matrices) {
        DrawUtils.drawCircle(matrices, new Vector2f(background.pos1.getX(), background.pos1.getY() + (background.pos2.getY() - background.pos1.getY()) / 2), (background.pos2.getY() - background.pos1.getY()) / 2, bgColor);
        DrawUtils.drawCircle(matrices, new Vector2f(background.pos2.getX(), background.pos1.getY() + (background.pos2.getY() - background.pos1.getY()) / 2), (background.pos2.getY() - background.pos1.getY()) / 2, bgColor);
    }
}
