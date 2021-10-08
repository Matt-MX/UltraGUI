package com.mattmx.ultragui.example.gui;

import com.mattmx.ultragui.api.objects.QuadButton;
import com.mattmx.ultragui.api.objects.QuadWindow;
import com.mattmx.ultragui.api.primatives.*;
import com.mattmx.ultragui.api.screens.UltraScreen;
import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import com.mattmx.ultragui.example.gui.widget.CustomSlider;
import net.fabricmc.loader.util.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

/* # TODO (matt)
 * - Add layer system
 * - Improve UltraTextInput system
 * - UltraElement add to see if element is SELECTED, to avoid errors while dragging mouse while holding LMB
 */

public class ExampleGUI extends UltraScreen {
    public Vector4f backgroundColor = new Vector4f(1f, 0.5f, 0.5f, 1f);
    public float r_anim = 0;
    public ExampleGUI(Screen parent) {
        int fontHeight = MinecraftClient.getInstance().textRenderer.fontHeight;
        int windowWidth = 200;
        int windowHeight = 200;
        QuadWindow topbar = new QuadWindow(new Vector2f(0,0), new Vector2f(windowWidth, fontHeight + 2), new Vector4f(0.023f, 0f, 0.901f, 1f));
        UltraQuad background = new UltraQuad(new Vector2f(0, fontHeight + 2), new Vector2f(windowWidth, windowHeight), new Vector4f(0f, 0f, 0f, 0.5f));
        UltraText text = new UltraText("&f&l&oTest Window", new Vector2f(1, 2), true, false);
        UltraOutline outline = new UltraOutline(new Vector2f(0, 0), new Vector2f(windowWidth, windowHeight), new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 1);
        QuadButton b = new QuadButton(new Vector2f(windowWidth - fontHeight - 1, 1), new Vector2f(fontHeight, fontHeight), new Vector4f(0.901f, 0f, 0.203f, 1f), topbar);
        UltraTextInput input = new UltraTextInput(new Vector2f(10, 15), new Vector2f(windowWidth / 2, fontHeight), new Vector4f(0f, 0f, 0f, 1f));
        UltraSlider slider = new UltraSlider(new Vector2f(10, 40), new Vector2f(windowWidth / 2, fontHeight), 0, 1, new Vector4f(0f, 0f, 0f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        //UltraImage bg = new UltraImage("F:/PC/under-bridge-anime-girl-Zero-Two.jpg", new Vector2f(0, 0), new Vector2f(parent.width, parent.height));
        CustomSlider rSlider = new CustomSlider(new Vector2f(10f, 50f), new Vector2f(70f, 4), 0, 1, new Vector4f(0.066f, 0.529f, 0.874f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        CustomSlider gSlider = new CustomSlider(new Vector2f(10f, 60f), new Vector2f(70f, 4), 0, 1, new Vector4f(0.066f, 0.529f, 0.874f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        CustomSlider bSlider = new CustomSlider(new Vector2f(10f, 70f), new Vector2f(70f, 4), 0, 1, new Vector4f(0.066f, 0.529f, 0.874f, 1f), new Vector4f(1f, 1f, 1f, 1f));
        UltraRoundedRectangle w = new UltraRoundedRectangle(new Vector2f(11f, 86f), new Vector2f(10 + 69, 85 + 39), 9, new Vector4f(0.066f, 0.529f, 0.874f, 1f));
        UltraRoundedRectangle r = new UltraRoundedRectangle(new Vector2f(10f, 85f), new Vector2f(10 + 70, 85 + 40), 10, new Vector4f(1f, 1f, 1f, 1f));
        UltraRoundedRectangle r_bg = new UltraRoundedRectangle(new Vector2f(10f, 85f), new Vector2f(10 + 70, 85 + 20), 10, new Vector4f(0.066f, 0.529f, 0.874f, 0f));
        b.onLeftKeyDown = () -> {
            if (!b.isThisTop()) return;
            background.isShown = !background.isShown;
            outline.isShown = background.isShown;
            input.isShown = background.isShown;
            slider.isShown = background.isShown;
        };
        slider.onValueChanged = () -> {
            backgroundColor = new Vector4f((float)slider.currentVal, 0.5f, 0.5f, 1f);
        };
        r.onHover = () -> {
            if (!r.isThisTop()) return;
            if (r_anim < 1f) r_anim = r_anim + 0.1f;
            w.color = new Vector4f(0.039f, 0.450f, 0.760f, 1f);
        };
        r.onUnHover = () -> {
            if (!r.isThisTop()) return;
            if (r_anim > 0f) r_anim = r_anim - 0.1f;
            w.color = new Vector4f(0.066f, 0.529f, 0.874f, 1f);
        };
        r_bg.onRender = () -> {
            Vector2f pos1 = r.pos1;
            Vector2f pos2 = r.pos2;
            r_bg.color = new Vector4f(0.066f, 0.529f, 0.874f, 0.5f * r_anim);
            r_bg.pos1 = new Vector2f(pos1.getX() - 3 * r_anim, pos1.getY() - 3 * r_anim);
            r_bg.pos2 = new Vector2f(pos2.getX() + 3 * r_anim, pos2.getY() + 3 * r_anim);
        };
        rSlider.onValueChanged = () -> backgroundColor = new Vector4f((float)rSlider.currentVal, (float)gSlider.currentVal, (float)bSlider.currentVal, 1f);
        gSlider.onValueChanged = () -> backgroundColor = new Vector4f((float)rSlider.currentVal, (float)gSlider.currentVal, (float)bSlider.currentVal, 1f);
        bSlider.onValueChanged = () -> backgroundColor = new Vector4f((float)rSlider.currentVal, (float)gSlider.currentVal, (float)bSlider.currentVal, 1f);
        text.onRender = () -> text.text = "&4" +(int) (rSlider.currentVal * 255) + "&a" + (int) (gSlider.currentVal * 255) + "&1" + (int) (bSlider.currentVal * 255);
        topbar.children.add(background);
        topbar.children.add(outline);
        topbar.children.add(b);
        topbar.children.add(text);
        topbar.children.add(input);
        topbar.children.add(slider);
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
