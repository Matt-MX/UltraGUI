package com.mattmx.ultragui.example.gui;

import com.mattmx.ultragui.api.objects.QuadButton;
import com.mattmx.ultragui.api.objects.QuadWindow;
import com.mattmx.ultragui.api.primatives.*;
import com.mattmx.ultragui.api.screens.UltraScreen;
import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import com.mattmx.ultragui.example.gui.widget.CustomSlider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class DebugGUI extends UltraScreen {
    public Vector4f backgroundColor = new Vector4f(1f, 0.5f, 0.5f, 1f);
    public float r_anim = 0;
    public DebugGUI(Screen parent) {
        super();
        int fontHeight = MinecraftClient.getInstance().textRenderer.fontHeight;
        int windowWidth = 200;
        int windowHeight = 200;
        QuadWindow topbar = new QuadWindow(new Vector2f(0,0), new Vector2f(windowWidth, fontHeight + 2), new Vector4f(0.023f, 0f, 0.901f, 1f));
        UltraQuad background = new UltraQuad(new Vector2f(0, fontHeight + 2), new Vector2f(windowWidth, windowHeight), new Vector4f(0f, 0f, 0f, 0.5f));
        CustomSlider rSlider = new CustomSlider(new Vector2f(10f, 50f), new Vector2f(70f, 4), 0, 1, new Vector4f(0.066f, 0.529f, 0.874f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        CustomSlider gSlider = new CustomSlider(new Vector2f(10f, 60f), new Vector2f(70f, 4), 0, 1, new Vector4f(0.066f, 0.529f, 0.874f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        CustomSlider bSlider = new CustomSlider(new Vector2f(10f, 70f), new Vector2f(70f, 4), 0, 1, new Vector4f(0.066f, 0.529f, 0.874f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        UltraRoundedRectangle w = new UltraRoundedRectangle(new Vector2f(11f, 86f), new Vector2f(10 + 69, 85 + 39), 9, new Vector4f(0.066f, 0.529f, 0.874f, 1f));
        UltraRoundedRectangle r = new UltraRoundedRectangle(new Vector2f(10f, 85f), new Vector2f(10 + 70, 85 + 40), 10, new Vector4f(1f, 1f, 1f, 1f));
        UltraRoundedRectangle r_bg = new UltraRoundedRectangle(new Vector2f(10f, 85f), new Vector2f(10 + 70, 85 + 20), 10, new Vector4f(0.066f, 0.529f, 0.874f, 0f));
        topbar.children.add(background);
        topbar.children.add(rSlider);
        topbar.children.add(gSlider);
        topbar.children.add(bSlider);
        topbar.children.add(r_bg);
        topbar.children.add(r);
        topbar.children.add(w);
        this.addElement(topbar);
        this.parent = parent;
    }

    public void render(MatrixStack matrices) {
        Screen s = MinecraftClient.getInstance().currentScreen;
        DrawUtils.fill(matrices, 0, 0, s.width, s.height, ColorUtils.vec4fTo4FloatArray(backgroundColor));
        this.init(matrices);
    }
}
