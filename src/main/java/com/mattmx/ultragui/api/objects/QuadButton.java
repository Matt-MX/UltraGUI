package com.mattmx.ultragui.api.objects;

import com.mattmx.ultragui.api.primatives.UltraButton;
import com.mattmx.ultragui.api.primatives.UltraQuad;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class QuadButton extends UltraButton {

    public QuadButton(Vector2f pos1, Vector2f widhei, Vector4f col, QuadWindow guiParent) {
        super();
        this.isShown = true;
        this.pos1 = new Vector2f(pos1.getX() + guiParent.pos1.getX(),
                pos1.getY() + guiParent.pos1.getY());
        this.pos2 = new Vector2f(this.pos1.getX() + widhei.getX(),
                this.pos1.getY() + widhei.getY());
        this.color = col;
        this.isShown = true;
    }
}