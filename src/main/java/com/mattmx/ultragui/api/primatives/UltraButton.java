package com.mattmx.ultragui.api.primatives;

import com.mattmx.ultragui.api.objects.QuadWindow;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

public class UltraButton extends UltraQuad {
    public Vector4f defaultCol;

    public UltraButton(Vector2f pos1, Vector2f widhei, Vector4f col, QuadWindow guiParent) {
        super();
        this.isShown = true;
        this.pos1 = new Vector2f(pos1.getX() + guiParent.pos1.getX(),
                pos1.getY() + guiParent.pos1.getY());
        this.pos2 = new Vector2f(this.pos1.getX() + widhei.getX(),
                this.pos1.getY() + widhei.getY());
        this.color = col;
        this.defaultCol = col;
        this.isShown = true;
    }

    public UltraButton() {

    }
}
