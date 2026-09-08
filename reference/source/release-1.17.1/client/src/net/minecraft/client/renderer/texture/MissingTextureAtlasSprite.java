package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.NativeImage;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;

public final class MissingTextureAtlasSprite extends TextureAtlasSprite {
   private static final int MISSING_IMAGE_WIDTH = 16;
   private static final int MISSING_IMAGE_HEIGHT = 16;
   private static final String MISSING_TEXTURE_NAME = "missingno";
   private static final ResourceLocation MISSING_TEXTURE_LOCATION = new ResourceLocation("missingno");
   @Nullable
   private static DynamicTexture missingTexture;
   private static final LazyLoadedValue<NativeImage> MISSING_IMAGE_DATA = new LazyLoadedValue<>(() -> {
      NativeImage â˜ƒ = new NativeImage(16, 16, false);
      int â˜ƒx = -16777216;
      int â˜ƒxx = -524040;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 16; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
            if (â˜ƒxxx < 8 ^ â˜ƒxxxx < 8) {
               â˜ƒ.setPixelRGBA(â˜ƒxxxx, â˜ƒxxx, -524040);
            } else {
               â˜ƒ.setPixelRGBA(â˜ƒxxxx, â˜ƒxxx, -16777216);
            }
         }
      }

      â˜ƒ.untrack();
      return â˜ƒ;
   });
   private static final TextureAtlasSprite.Info INFO = new TextureAtlasSprite.Info(
      MISSING_TEXTURE_LOCATION, 16, 16, new AnimationMetadataSection(ImmutableList.of(new AnimationFrame(0, -1)), 16, 16, 1, false)
   );

   private MissingTextureAtlasSprite(TextureAtlas var1, int var2, int var3, int var4, int var5, int var6) {
      super(â˜ƒ, INFO, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, MISSING_IMAGE_DATA.get());
   }

   public static MissingTextureAtlasSprite newInstance(TextureAtlas var0, int var1, int var2, int var3, int var4, int var5) {
      return new MissingTextureAtlasSprite(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static ResourceLocation getLocation() {
      return MISSING_TEXTURE_LOCATION;
   }

   public static TextureAtlasSprite.Info info() {
      return INFO;
   }

   @Override
   public void close() {
      for(int â˜ƒ = 1; â˜ƒ < this.mainImage.length; ++â˜ƒ) {
         this.mainImage[â˜ƒ].close();
      }
   }

   public static DynamicTexture getTexture() {
      if (missingTexture == null) {
         missingTexture = new DynamicTexture(MISSING_IMAGE_DATA.get());
         Minecraft.getInstance().getTextureManager().register(MISSING_TEXTURE_LOCATION, missingTexture);
      }

      return missingTexture;
   }
}
