package com.mattmx.ultragui.api.primatives;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vector4f;

import java.util.Vector;

public class UltraSlider extends UltraElement {
    public UltraQuad background;
    public UltraQuad sliderWidget;
    public double maxVal;
    public double minVal;
    public double currentVal;
    public float thickness = 2f;
    public Runnable onValueChanged;

    public void onValueChanged() {
        if (onValueChanged == null) return;
        new Thread(onValueChanged).start();
    }

    public UltraSlider(Vector2f pos1, Vector2f widhei, double minVal, double maxVal, Vector4f backgroundCol, Vector4f sliderCol) {
        this.pos1 = pos1;
        this.pos2 = new Vector2f(pos1.getX() + widhei.getX(), pos1.getY() + widhei.getY());
        background = new UltraQuad(pos1, pos2, backgroundCol);
        sliderWidget = new UltraQuad(pos1, new Vector2f(pos1.getX() + thickness, pos2.getY()), sliderCol);
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.currentVal = minVal;
        background.onDrag = () -> {
            if (background.isHovered) {
                sliderWidget.pos1 = new Vector2f((float)mouseX, sliderWidget.pos1.getY());
                sliderWidget.pos2 = new Vector2f((float)(mouseX + thickness), sliderWidget.pos2.getY());
                this.onValueChanged();
                sliderWidget.onMove();
            }
        };
    }

    @Override
    public void changePos(Vector2f change) {
        this.pos1 = new Vector2f(this.pos1.getX() + change.getX(),
                this.pos1.getY() + change.getY());
        this.pos2 = new Vector2f(this.pos2.getX() + change.getX(),
                this.pos2.getY() + change.getY());
        background.pos1 = this.pos1;
        background.pos2 = this.pos2;
        sliderWidget.pos1 = new Vector2f(sliderWidget.pos1.getX() + change.getX(),
                sliderWidget.pos1.getY() + change.getY());
        sliderWidget.pos2 = new Vector2f(sliderWidget.pos2.getX() + change.getX(),
                sliderWidget.pos2.getY() + change.getY());
    }

    @Override
    public void draw(MatrixStack matrices) {
        if (!isShown) return;
        background.init(matrices);
        calcValue();
        sliderWidget.init(matrices);
    }

    public void calcValue() {
        double xOff = sliderWidget.pos1.getX() - background.pos1.getX();
        double max = (background.pos2.getX() - background.pos1.getX());
        currentVal = ((xOff / max) * maxVal) + minVal;
    }
}
