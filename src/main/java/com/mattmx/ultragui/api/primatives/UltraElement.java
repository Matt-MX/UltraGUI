package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class UltraElement extends DrawableHelper {
    public static List<UltraElement> layers = new ArrayList<>();
    public List<UltraElement> children = new ArrayList<>();
    public Vector2f oldpos1 = null;
    public Vector2f pos1;
    public Vector2f pos2;
    public String name = "null";
    public boolean isLMBPressed;
    public boolean isRMBPressed;
    public boolean isLMBClicked;
    public boolean isRMBClicked;
    public boolean isDragging;
    public boolean isHovered;
    public boolean wasHovered;
    public boolean isShown = true;
    public Vector4f color;
    public double mouseX;
    public double mouseY;
    public Runnable onUnHover;
    public Runnable onHover;
    public Runnable onLeftKeyDown;
    public Runnable onLeftKeyReleased;
    public Runnable onRightKeyDown;
    public Runnable onRightKeyReleased;
    public Runnable onDrag;
    public Runnable onRender;
    public Runnable onMove;
    public Runnable onChangePos;
    public boolean isLayer = true;

    public UltraElement() {
        layers.add(this);
    }

    public void draw(MatrixStack matrices) {
        layers.add(this);
    }

    // MOUSE HANDLE (HANDLE isHovered)
    public void handleMouse() {
    }

    public void onChangePos() {
        if (onChangePos == null) return;
        new Thread(onChangePos).start();
    }
    public void onRightKeyDown() {
        if (onRightKeyDown == null) return;
        new Thread(onRightKeyDown).start();
    }

    public void onRightKeyReleased() {
        if (onRightKeyReleased == null) return;
        new Thread(onRightKeyReleased).start();
    }

    public void onLeftKeyDown() {
        if (onLeftKeyDown == null) return;
        new Thread(onLeftKeyDown).start();
    }

    public void onLeftKeyReleased() {
        if (onLeftKeyReleased == null) return;
        new Thread(onLeftKeyReleased).start();
    }

    public void onHover() {
        if (onHover == null) return;
        new Thread(onHover).start();
    }

    public void onUnHover() {
        if (onUnHover == null) return;
        new Thread(onUnHover).start();
    }

    public void onRender() {
        if (onRender == null) return;
        new Thread(onRender).start();
    }

    public void onDrag() {
        if (onDrag == null) return;
        new Thread(onDrag).start();
    }

    public void onMove() {
        if (onMove == null) return;
        new Thread(onMove).start();
    }

    public final void init(MatrixStack matrices) {
        mouseX = MinecraftClient.getInstance().mouse.getX() / MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY = MinecraftClient.getInstance().mouse.getY() / MinecraftClient.getInstance().getWindow().getScaleFactor();
        this.handleMouse();
        isLMBPressed = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        isRMBPressed = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        if (isLMBPressed && isHovered) wasHovered = true;
        isDragging = wasHovered;
        if (isDragging){
            onDrag();
        } else oldpos1 = null;
        if (isHovered) onHover();
        else onUnHover();
        if (isLMBPressed && !isLMBClicked && isHovered) {
            isLMBClicked = true;
            onLeftKeyDown();
        }
        if (isRMBPressed && !isRMBClicked && isHovered) {
            isRMBClicked = true;
            onRightKeyDown();
        }
        if (!isLMBPressed) {
            isLMBClicked = false;
            wasHovered = false;
            if (isHovered) onLeftKeyReleased();
        }
        if (!isRMBPressed) {
            isRMBClicked = false;
            if (isHovered) onRightKeyReleased();
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
            Vector2f w = DrawUtils.isOffScreen(new Vector2f((float) (pos1.getX() + movX), (float)(pos1.getY() + movY)), new Vector2f((float)(pos2.getX() + movX), (float)(pos2.getY() + movY)));
            movX = movX + w.getX();
            movY = movY + w.getY();
            pos1 = new Vector2f((float) (pos1.getX() + movX),
                                (float) (pos1.getY() + movY));
            pos2 = new Vector2f((float) (pos2.getX() + movX),
                                (float) (pos2.getY() + movY));
            oldpos1 = new Vector2f((float) mouseX, (float) mouseY);
            onMove();
            return new Vector2f((float)movX, (float)movY);
    }

    public void changePos(Vector2f change) {
        this.pos1 = new Vector2f(this.pos1.getX() + change.getX(),
                this.pos1.getY() + change.getY());
        this.pos2 = new Vector2f(this.pos2.getX() + change.getX(),
                this.pos2.getY() + change.getY());
        //this.onMove();
    }

    public void updateChildrenPos(Vector2f change) {
        for (UltraElement e : children) {
            e.changePos(change);
            e.onChangePos();
        }
    }

    public static UltraElement getElementClicked() {
        List<UltraElement> possible = new ArrayList<>();
        for (UltraElement e : layers) {
            if (e.isHovered && e.isLayer || e.isDragging && e.isLayer) {
                possible.add(e);
            }
        }
        if (possible.isEmpty()) return null;
        return possible.get(possible.size() - 1);
    }

    public boolean isThisTop() {
        //return true; //Testing
        UltraElement e = getElementClicked();
        if (e == null) return false;
        return e.equals(this);
    }
}
