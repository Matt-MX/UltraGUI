package com.mattmx.ultragui.example.gui;

import com.mattmx.ultragui.api.objects.QuadButton;
import com.mattmx.ultragui.api.objects.QuadWindow;
import com.mattmx.ultragui.api.primatives.UltraOutline;
import com.mattmx.ultragui.api.primatives.UltraQuad;
import com.mattmx.ultragui.api.primatives.UltraText;
import com.mattmx.ultragui.api.primatives.UltraTextInput;
import com.mattmx.ultragui.api.screens.UltraScreen;
import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

import java.util.Vector;

/* # TODO (matt)
 * - Add layer system
 * - Improve UltraTextInput system
 * - UltraElement add to see if element is SELECTED, to avoid errors while dragging mouse while holding LMB
 */

public class ExampleGUI extends UltraScreen {

    public ExampleGUI(Screen parent) {
        int fontHeight = MinecraftClient.getInstance().textRenderer.fontHeight;
        int windowWidth = 200;
        int windowHeight = 100;
        QuadWindow topbar = new QuadWindow(new Vector2f(0,0), new Vector2f(windowWidth, fontHeight + 2), new Vector4f(0.023f, 0f, 0.901f, 1f));
        UltraQuad background = new UltraQuad(new Vector2f(0, fontHeight + 2), new Vector2f(windowWidth, windowHeight), new Vector4f(0f, 0f, 0f, 0.5f));
        UltraText text = new UltraText("&f&l&oTest Window", new Vector2f(1, 2), true, false);
        UltraOutline outline = new UltraOutline(new Vector2f(0, 0), new Vector2f(windowWidth, windowHeight), new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 1);
        QuadButton b = new QuadButton(new Vector2f(windowWidth - fontHeight - 1, 1), new Vector2f(fontHeight, fontHeight), new Vector4f(0.901f, 0f, 0.203f, 1f), topbar);
        UltraTextInput input = new UltraTextInput(new Vector2f(10, 20), new Vector2f(windowWidth / 2, fontHeight), new Vector4f(0f, 0f, 0f, 1f));
        b.onLeftKeyDown = () -> {
            background.isShown = !background.isShown;
            outline.isShown = background.isShown;
            input.isShown = background.isShown;
        };
        topbar.onRender = () -> {
            Vector2f c = DrawUtils.isOffScreen(topbar.pos1, topbar.pos2);
            if (!(c.getX() == 0 && c.getY() == 0)) {
                topbar.changePos(c);
                topbar.updateChildrenPos(c);
            }
        };
        topbar.children.add(background);
        topbar.children.add(outline);
        topbar.children.add(b);
        topbar.children.add(text);
        topbar.children.add(input);
        this.addElement(topbar);
        this.parent = parent;
    }

    public void render(MatrixStack matrices) {
        this.init(matrices);
    }
}
