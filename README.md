# UltraGUI

A GUI library for FabricMC projects, with full customization!

## Installation

### Maven

```maven
```
### Gradle
```gradle
repositories {
    [...]
    maven { url "https://jitpack.io" }
}
dependencies {
    [...]
    implementation 'com.github.MangroveTFX:UltraGUI:{version}'
    // where {version} is the latest version of this.
}
```

## Usage

Example of how to use a custom GUI, here we are mixing into the class TitleScreen, defining an instance of ExampleGUI and calling render inside of the injected function.

```java
@Mixin(TitleScreen.class)
public abstract class MainMenuScreenMixin {
    ExampleGUI scr = new ExampleGUI(MinecraftClient.getInstance().currentScreen);

    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        scr.render(matrices);
    }
}
```
Your code doesn't need to look like this. This is just an example and you can decide how and where you call your methods to render and handle gui events.
```java
public class ExampleGUI extends UltraScreen {

    public ExampleGUI(Screen parent) {
        int fontHeight = MinecraftClient.getInstance().textRenderer.fontHeight;
        int windowWidth = 200;
        int windowHeight = 100;
        QuadWindow topbar = new QuadWindow(new Vector2f(0,0), new Vector2f(windowWidth, fontHeight + 2), new Vector4f(0.023f, 0f, 0.901f, 1f));
        UltraQuad background = new UltraQuad(new Vector2f(0, 0), new Vector2f(windowWidth, windowHeight), new Vector4f(0f, 0f, 0f, 0.5f));
        UltraText text = new UltraText("&f&l&oTest Window", new Vector2f(1, 2), true, false, topbar);
        UltraOutline outline = new UltraOutline(new Vector2f(0, 0), new Vector2f(windowWidth, windowHeight), new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 1);
        QuadButton b = new QuadButton(new Vector2f(windowWidth - fontHeight - 1, 1), new Vector2f(fontHeight, fontHeight), new Vector4f(0.901f, 0f, 0.203f, 1f), topbar);
        b.onMouseDown = () -> {
            background.isShown = !background.isShown;
            outline.isShown = background.isShown;
        };
        topbar.children.add(background);
        topbar.children.add(outline);
        this.parent = parent;
        this.addElement(outline);
        this.addElement(background);
        this.addElement(topbar);
        this.addElement(b);
        this.addElement(text);
    }

    public void render(MatrixStack matrices) {
        this.init(matrices);
    }
}
```
This code will output this when ran:
![ExampleImage](2021-10-02_20.17.32.png?raw=true "Title")


## Forks
Feel free to fork the project and make changes how you please etc. Please leave some credit in the original files. Thanks!

## [Website](https://www.mattmx.com/) | [YouTube](https:/youtube.com/mattmx)
@MattMX#0001
