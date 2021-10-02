package com.mattmx.ultragui.api.objects;

import com.mattmx.ultragui.api.primatives.UltraElement;
import com.mattmx.ultragui.api.primatives.UltraQuad;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vector4f;

import java.util.*;

public class QuadWindow extends UltraQuad {

    public QuadWindow(Vector2f pos1, Vector2f widhei, Vector4f col) {
        Vector2f pos2 = new Vector2f(widhei.getX() + pos1.getX(),
                widhei.getY() + pos1.getY());
        this.isShown = true;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = col;
    }

    @Override
    public void onDrag() {
        Vector2f change = this.followMouse();
        updateChildrenPos(change);
    }
}
