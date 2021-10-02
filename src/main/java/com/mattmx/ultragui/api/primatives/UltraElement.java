package com.mattmx.ultragui.api.primatives;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class UltraElement extends DrawableHelper {
    public List<UltraElement> children = new ArrayList<>();
    public Vector2f oldpos1 = null;
    public Vector2f pos1;
    public Vector2f pos2;
    public String name = "null";
    public boolean isMouseDown;
    public boolean isMouseClicked;
    public boolean isDragging;
    public boolean isHovered;
    public boolean wasHovered;
    public boolean isShown = true;
    public Vector4f color;
    public double mouseX;
    public double mouseY;
    public Runnable onUnHover;
    public Runnable onHover;
    public Runnable onMouseDown;
    public Runnable onMouseUp;
    public Runnable onDrag;
    public Runnable onRender;
    public Runnable onMove;

    protected abstract void draw(MatrixStack matrices);

    // MOUSE HANDLE (HANDLE isHovered)
    public void handleMouse() {
    }
    public void onMouseDown() {
        if (onMouseDown == null) return;
        Thread click = new Thread(onMouseDown);
        click.start();
    }

    public void onMouseUp() {
        if (onMouseUp == null) return;
        Thread up = new Thread(onMouseUp);
        up.start();
    }

    public void onHover() {
        if (onHover == null) return;
        Thread hover = new Thread(onHover);
        hover.start();
    }

    public void onUnHover() {
        if (onUnHover == null) return;
        new Thread(onUnHover).start();
    }

    public void onRender() {
        if (onRender == null) return;
        Thread render = new Thread(onRender);
        render.start();
    }

    public void onDrag() {
        if (onDrag == null) return;
        Thread dar = new Thread(onDrag);
        dar.start();
    }

    public void onMove() {
        if (onMove == null) return;
        new Thread(onMove).start();
    }

    public final void init(MatrixStack matrices) {
        mouseX = MinecraftClient.getInstance().mouse.getX() / MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY = MinecraftClient.getInstance().mouse.getY() / MinecraftClient.getInstance().getWindow().getScaleFactor();
        this.handleMouse();
        isMouseDown = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        if (isMouseDown && isHovered) wasHovered = true;
        isDragging = wasHovered;
        if (isDragging){
            onDrag();
        } else oldpos1 = null;
        if (isHovered) onHover();
        else onUnHover();
        if (isMouseDown && !isMouseClicked && isHovered) {
            isMouseClicked = true;
            onMouseDown();
        }
        if (!isMouseDown) {
            isMouseClicked = false;
            wasHovered = false;
            if (isHovered) onMouseUp();
        }
        onRender();
        this.draw(matrices);
    }

    public Vector2f followMouse() {
            if (oldpos1 == null) {
                oldpos1 = new Vector2f((float) mouseX, (float) mouseY);
            }
            double movX = mouseX - oldpos1.getX();
            double movY = mouseY - oldpos1.getY();
            pos1 = new Vector2f((float) (pos1.getX() + movX),
                                (float) (pos1.getY() + movY));
            pos2 = new Vector2f((float) (pos2.getX() + movX),
                                (float) (pos2.getY() + movY));
            oldpos1 = new Vector2f((float) mouseX, (float) mouseY);
            onMove();
            return new Vector2f((float)movX, (float)movY);
    }

    public final void changePos(Vector2f change) {
        this.pos1 = new Vector2f(this.pos1.getX() + change.getX(),
                this.pos1.getY() + change.getY());
        this.pos2 = new Vector2f(this.pos2.getX() + change.getX(),
                this.pos2.getY() + change.getY());
        //this.onMove();
    }

    public void updateChildrenPos(Vector2f change) {
        for (UltraElement e : children) {
            e.changePos(change);
            //e.onMove();
        }
    }
}
