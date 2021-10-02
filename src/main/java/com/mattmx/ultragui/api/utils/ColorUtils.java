package com.mattmx.ultragui.api.utils;

import net.minecraft.text.Text;
import net.minecraft.util.math.Vector4f;

import java.util.*;

public class ColorUtils {
    public static List<Float> vector4fToColorAray(Vector4f vec) {
        return Arrays.asList(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }
    public static Text chat(String str) {
        return Text.of(str.replace("&", "§"));
    }
}
