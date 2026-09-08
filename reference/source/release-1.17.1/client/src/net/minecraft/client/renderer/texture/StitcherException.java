package net.minecraft.client.renderer.texture;

import java.util.Collection;

public class StitcherException extends RuntimeException {
   private final Collection<TextureAtlasSprite.Info> allSprites;

   public StitcherException(TextureAtlasSprite.Info var1, Collection<TextureAtlasSprite.Info> var2) {
      super(String.format("Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", â˜ƒ.name(), â˜ƒ.width(), â˜ƒ.height()));
      this.allSprites = â˜ƒ;
   }

   public Collection<TextureAtlasSprite.Info> getAllSprites() {
      return this.allSprites;
   }
}
