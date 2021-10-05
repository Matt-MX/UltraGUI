package com.mattmx.ultragui.example;

import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import com.mattmx.ultragui.example.gui.ExampleGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Iterator;

public class TestScreen extends Screen {
    ExampleGUI scr = new ExampleGUI(MinecraftClient.getInstance().currentScreen);
    public TestScreen() {
        super(Text.of(""));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        scr.render(matrices);
    }
}
