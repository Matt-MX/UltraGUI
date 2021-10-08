package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.objects.QuadWindow;
import com.mattmx.ultragui.api.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;

public class UltraText extends UltraElement {
    public String text;
    public boolean shadow;
    public boolean centered;

    public UltraText(String text, Vector2f pos1, boolean shadow, boolean centered) {
        super();
        TextRenderer t = MinecraftClient.getInstance().textRenderer;
        int textWidth = t.getWidth(ColorUtils.chat(text));
        this.pos1 = pos1;
        this.pos2 = new Vector2f(pos1.getX() + textWidth,
                pos1.getY() + t.fontHeight);
        this.centered = centered;
        this.shadow = shadow;
        this.text = text;
    }

    @Override
    public void draw(MatrixStack matrices) {
        TextRenderer t = MinecraftClient.getInstance().textRenderer;
        int textWidth = t.getWidth(ColorUtils.chat(text));
        if (shadow) {
            if (centered) drawCenteredTextWithShadow(matrices, t, ColorUtils.chat(text).asOrderedText(), (int)pos1.getX() - textWidth / 2, (int)pos1.getY(), 0);
            else drawTextWithShadow(matrices, t, ColorUtils.chat(text), (int)pos1.getX(), (int)pos1.getY(), 0);
        } else {
            if (centered) drawCenteredText(matrices, t, ColorUtils.chat(text), (int)pos1.getX() - textWidth / 2, (int)pos1.getY(), 0);
            else drawCenteredText(matrices, t, ColorUtils.chat(text), (int)pos1.getX(), (int)pos1.getY(), 0);
        }
    }
}
