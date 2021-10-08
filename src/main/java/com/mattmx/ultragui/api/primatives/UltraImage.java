package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.utils.ColorUtils;
import com.mattmx.ultragui.api.utils.DrawUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UltraImage extends UltraElement{
    private String filepath;
    private double width;
    private double height;
    private BufferedImage image = null;

    public UltraImage(String filepath, Vector2f pos1, Vector2f widhei) {
        super();
        this.pos1 = pos1;
        this.pos2 = new Vector2f(pos1.getX() + widhei.getX(), pos1.getY() + widhei.getY());
        this.width = widhei.getX();
        this.height = widhei.getY();
        this.filepath = filepath;
        this.image = null;
        try {
            this.image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(MatrixStack matrices) {

    }
}
