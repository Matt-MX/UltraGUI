package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import com.mattmx.ultragui.api.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vector4f;
import org.lwjgl.glfw.GLFW;
import java.util.*;
public class UltraTextInput extends UltraButton{
    public String input = "";
    public boolean isTyping = false;
    public boolean drawIndex = true;
    public boolean showPlaceholder = true;
    public String placeHolder = "&7Sample text";
    public int index = 0;
    public int maxInput = 999;
    public List<String> lastKeys = new ArrayList<>();
    public List<String> currentKeys = new ArrayList<>();
    public Runnable onInput;

    public UltraTextInput(Vector2f pos1, Vector2f widhei, Vector4f col) {
        this.pos1 = pos1;
        this.pos2 = new Vector2f(pos1.getX() + widhei.getX(), pos1.getY() + widhei.getY());
        this.color = col;
    }

    @Override
    public void onLeftKeyDown() {
        if (!isShown) return;
        isTyping = true;
        // get index clicked
        TextRenderer t = MinecraftClient.getInstance().textRenderer;
        try {
            int indexes = t.getWidth(input) / input.length();
            double w = mouseX - pos1.getX();
            index = (int) w / indexes;
            if (index > input.length() - 1) index = input.length();
        } catch (ArithmeticException e) {
            index = 0;
        }
    }

    @Override
    public void draw(MatrixStack matrices) {
        TextRenderer t = MinecraftClient.getInstance().textRenderer;
        if (GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS
        && !isHovered) {
            isTyping = false;
        }
        if (!isShown) {
            isTyping = false;
            return;
        }
        if (isTyping) getInputs();
        lastKeys.addAll(currentKeys);
        if (isTyping) handleInputs();
        int padding = 1;
        Formatting f = Formatting.WHITE;
        if (pos1.getX() +  MinecraftClient.getInstance().textRenderer.getWidth(input) >= pos2.getX() || input.length() >= maxInput) f = Formatting.RED;
        DrawUtils.fill(matrices, pos1.getX() - padding, pos1.getY() - padding, pos2.getX() + padding, pos2.getY() + padding, ColorUtils.vec4fTo4FloatArray(color));
        drawTextWithShadow(matrices, t,  new LiteralText(input).formatted(f), (int)pos1.getX(), (int)pos1.getY(), 0);
        if (drawIndex && isTyping) drawIndex(matrices);
        if (showPlaceholder && input.length() == 0) drawPlaceholder(matrices);
    }

    public void handleInputs() {
        if (currentKeys.size() > 0) onInput();
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        boolean cap = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS
                || GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_CAPS_LOCK) == GLFW.GLFW_REPEAT;
        boolean backsp = currentKeys.contains("BKSP");
        if (backsp) currentKeys.remove("BKSP");
        boolean del = currentKeys.contains("DEL");
        if (del) currentKeys.remove("DEL");
        if (backsp && index - 1 >= 0) {
            input = input.substring(0, index - 1) + input.substring(index);
            index--;
        }
        if (del && index + 1 <= input.length()) {
            input = input.substring(0, index) + input.substring(index + 1);
        }
        if (pos1.getX() +  MinecraftClient.getInstance().textRenderer.getWidth(input) >= pos2.getX() || input.length() >= maxInput) return;
        for (String s : currentKeys) {
            if (cap) input = input.substring(0, index) + s.toUpperCase() + input.substring(index);
            else input = input.substring(0, index) + s + input.substring(index);
            index++;
        }
    }

    public void getInputs() {
        List<String> keys = Utils.getKeysPressed();
        List<String> ckeys = new ArrayList<>(keys);
        keys.removeIf(k -> lastKeys.contains(k));
        lastKeys.removeIf(k -> !ckeys.contains(k));
        currentKeys = keys;
    }

    float anim_state = 0.05f;
    boolean anim_inc = true;
    public void drawIndex(MatrixStack matrices) {
        TextRenderer t = MinecraftClient.getInstance().textRenderer;
        double x = pos1.getX() + t.getWidth(input.substring(0, index));
        DrawUtils.fill(matrices, x, pos1.getY(), x + 1, pos1.getY() + t.fontHeight, Arrays.asList(1f, 1f, 1f, anim_state));
        if (anim_inc) {
            anim_state = anim_state + 0.01f;
            if (anim_state >= 0.95f) anim_inc = false;
        } else {
            anim_state = anim_state - 0.01f;
            if (anim_state <= 0.05f) anim_inc = true;
        }
    }

    public void drawPlaceholder(MatrixStack matrices) {
        TextRenderer t = MinecraftClient.getInstance().textRenderer;
        drawTextWithShadow(matrices, t, ColorUtils.chat(placeHolder), (int)pos1.getX() + 2, (int)pos1.getY(), 0);
    }

    public void onInput() {
        if (onInput == null) return;
        new Thread(onInput).start();
    }
}
