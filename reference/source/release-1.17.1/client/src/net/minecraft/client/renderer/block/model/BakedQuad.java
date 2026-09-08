package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

public class BakedQuad {
   protected final int[] vertices;
   protected final int tintIndex;
   protected final Direction direction;
   protected final TextureAtlasSprite sprite;
   private final boolean shade;

   public BakedQuad(int[] var1, int var2, Direction var3, TextureAtlasSprite var4, boolean var5) {
      this.vertices = â˜ƒ;
      this.tintIndex = â˜ƒ;
      this.direction = â˜ƒ;
      this.sprite = â˜ƒ;
      this.shade = â˜ƒ;
   }

   public TextureAtlasSprite getSprite() {
      return this.sprite;
   }

   public int[] getVertices() {
      return this.vertices;
   }

   public boolean isTinted() {
      return this.tintIndex != -1;
   }

   public int getTintIndex() {
      return this.tintIndex;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public boolean isShade() {
      return this.shade;
   }
}
