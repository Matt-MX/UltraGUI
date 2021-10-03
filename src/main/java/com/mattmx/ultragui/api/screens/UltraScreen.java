package com.mattmx.ultragui.api.screens;

import com.mattmx.ultragui.api.objects.QuadWindow;
import com.mattmx.ultragui.api.primatives.UltraElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public abstract class UltraScreen {
    public Screen parent;
    private List<UltraElement> elements = new ArrayList<>();

    protected void init(MatrixStack matrices) {
        for (UltraElement element : elements) {
            element.init(matrices);
            if (element instanceof QuadWindow) {
                QuadWindow q = (QuadWindow) element;
                for (UltraElement c : q.children) {
                    c.init(matrices);
                }
            }
        }
    }

    public final void addElement(UltraElement element) {
        elements.add(element);
    }

    public final UltraElement getElement(String name) {
        for (UltraElement element : elements) {
            if (Objects.equals(element.name, name)) {
                return element;
            }
        }
        return null;
    }

    public final void removeElement(UltraElement element) {
        elements.remove(element);
    }
}
