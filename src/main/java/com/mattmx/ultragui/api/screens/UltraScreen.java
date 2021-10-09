package com.mattmx.ultragui.api.screens;

import com.mattmx.ultragui.api.objects.QuadWindow;
import com.mattmx.ultragui.api.primatives.UltraElement;
import com.mattmx.ultragui.api.primatives.UltraText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public abstract class UltraScreen {
    public Screen parent;
    private List<UltraElement> elements = new ArrayList<>();

    public UltraScreen() {
        UltraText debugText = new UltraText( "2", new Vector2f(-100f, -100f), true, false);
        this.addElement(debugText);
    }

    public void init(MatrixStack matrices) {
        if (!elements.isEmpty()) {
            for (UltraElement element : elements) {
                element.init(matrices);
                if (element instanceof QuadWindow) {
                    QuadWindow q = (QuadWindow) element;
                    if (!q.children.isEmpty()) {
                        for (UltraElement c : q.children) {
                            c.init(matrices);
                        }
                    }
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

    public void render(MatrixStack matrices) {

    }
}
