package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public abstract class TextureSheetParticle extends SingleQuadParticle {
   protected TextureAtlasSprite sprite;

   protected TextureSheetParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected TextureSheetParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void setSprite(TextureAtlasSprite var1) {
      this.sprite = â˜ƒ;
   }

   @Override
   protected float getU0() {
      return this.sprite.getU0();
   }

   @Override
   protected float getU1() {
      return this.sprite.getU1();
   }

   @Override
   protected float getV0() {
      return this.sprite.getV0();
   }

   @Override
   protected float getV1() {
      return this.sprite.getV1();
   }

   public void pickSprite(SpriteSet var1) {
      this.setSprite(â˜ƒ.get(this.random));
   }

   public void setSpriteFromAge(SpriteSet var1) {
      if (!this.removed) {
         this.setSprite(â˜ƒ.get(this.age, this.lifetime));
      }
   }
}
