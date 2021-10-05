package com.mattmx.ultragui.example.mixin;

import com.mattmx.ultragui.example.TestScreen;
import com.mattmx.ultragui.example.gui.ExampleGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class MainMenuScreenMixin {
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MinecraftClient.getInstance().setScreen(new TestScreen());
        ci.cancel();
    }
}
