package com.mattmx.ultragui.api.primatives.textures;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigTexture extends ResourceTexture {
    Identifier i = null;

    public ConfigTexture(Identifier location) {
        super(location);
        i = location;
    }
    
    //Implement
    protected ResourceTexture.TextureData getTextureData(ResourceManager resourceManager) {
        try {
            InputStream input = new FileInputStream("config/" + location.toString().replace("minecraft:", ""));
            TextureData texture;

            try {
                texture = new TextureData(new TextureResourceMetadata(true, true), NativeImage.read(input));
            } finally {
                input.close();
            }

            return texture;
        } catch (IOException var18) {
            return new TextureData(var18);
        }
    }
}
