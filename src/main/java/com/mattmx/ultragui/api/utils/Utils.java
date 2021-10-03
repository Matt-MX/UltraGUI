package com.mattmx.ultragui.api.utils;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<Integer> extra = Arrays.asList(GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_BACKSPACE, GLFW.GLFW_KEY_DELETE);
    public static List<String> getKeysPressed() {
        List<String> cp = new ArrayList<>();
        for (int t = 32; t < 347; t++) {
            String s = GLFW.glfwGetKeyName(t, GLFW.glfwGetKeyScancode(t));
            if (s != null || extra.contains(t)) {
                if (GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), t) == GLFW.GLFW_PRESS) {
                    if (t == GLFW.GLFW_KEY_SPACE) cp.add(" ");
                    else if (t == GLFW.GLFW_KEY_BACKSPACE) cp.add("BKSP");
                    else if (t == GLFW.GLFW_KEY_DELETE) cp.add("DEL");
                    else cp.add(s);
                }
            }
        }
        return cp;
    }
}
