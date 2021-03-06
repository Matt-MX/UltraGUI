package com.mattmx.ultragui.example;

import com.mattmx.ultragui.example.gui.DebugGUI;
import com.mattmx.ultragui.example.gui.ExampleGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TestScreen extends Screen {
    DebugGUI scr = new DebugGUI(MinecraftClient.getInstance().currentScreen);
    public TestScreen() {
        super(Text.of(""));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        scr.render(matrices);
    }
}
