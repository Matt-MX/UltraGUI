package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.objects.QuadWindow;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class UltraButton extends UltraQuad {
    public Runnable onUnHover;
    public Runnable onHover;
    public Runnable onMouseDown;
    public Runnable onMouseUp;
    public Runnable onDrag;
    public Runnable onRender;
    public Vector4f defaultCol;
    public QuadWindow guiParent;

    public UltraButton(Vector2f pos1, Vector2f widhei, Vector4f col, QuadWindow guiParent) {
        this.isShown = true;
        this.guiParent = guiParent;
        this.pos1 = new Vector2f(pos1.getX() + guiParent.pos1.getX(),
                pos1.getY() + guiParent.pos1.getY());
        this.pos2 = new Vector2f(this.pos1.getX() + widhei.getX(),
                this.pos1.getY() + widhei.getY());
        this.color = col;
        this.defaultCol = col;
        this.isShown = true;
        guiParent.children.add(this);
    }

    public UltraButton() {

    }

    @Override
    public void onMouseDown() {
        if (onMouseDown == null) return;
        Thread click = new Thread(onMouseDown);
        click.start();
    }

    @Override
    public void onMouseUp() {
        if (onMouseUp == null) return;
        Thread up = new Thread(onMouseUp);
        up.start();
    }

    @Override
    public void onHover() {
        if (onHover == null) return;
        Thread hover = new Thread(onHover);
        hover.start();
    }

    @Override
    public void onUnHover() {
        if (onUnHover == null) return;
        new Thread(onUnHover).start();
    }

    @Override
    public void onRender() {
        if (onRender == null) return;
        Thread render = new Thread(onRender);
        render.start();
    }

    @Override
    public void onDrag() {
        if (onDrag == null) return;
        Thread dar = new Thread(onDrag);
        dar.start();
    }
}
