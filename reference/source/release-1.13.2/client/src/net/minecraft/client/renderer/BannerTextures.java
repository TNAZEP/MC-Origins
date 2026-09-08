package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class BannerTextures {
   public static final BannerTextures.Cache field_178466_c = new BannerTextures.Cache(
      "banner_", new ResourceLocation("textures/entity/banner_base.png"), "textures/entity/banner/"
   );
   public static final BannerTextures.Cache field_187485_b = new BannerTextures.Cache(
      "shield_", new ResourceLocation("textures/entity/shield_base.png"), "textures/entity/shield/"
   );
   public static final ResourceLocation field_187486_c = new ResourceLocation("textures/entity/shield_base_nopattern.png");
   public static final ResourceLocation field_187487_d = new ResourceLocation("textures/entity/banner/base.png");

   public static class Cache {
      private final Map<String, BannerTextures.CacheEntry> field_187479_a = Maps.newLinkedHashMap();
      private final ResourceLocation field_187480_b;
      private final String field_187481_c;
      private final String field_187482_d;

      public Cache(String var1, ResourceLocation var2, String var3) {
         this.field_187482_d = ☃;
         this.field_187480_b = ☃;
         this.field_187481_c = ☃;
      }

      @Nullable
      public ResourceLocation func_187478_a(String var1, List<BannerPattern> var2, List<EnumDyeColor> var3) {
         if (☃.isEmpty()) {
            return null;
         } else if (!☃.isEmpty() && !☃.isEmpty()) {
            ☃ = this.field_187482_d + ☃;
            BannerTextures.CacheEntry ☃ = (BannerTextures.CacheEntry)this.field_187479_a.get(☃);
            if (☃ == null) {
               if (this.field_187479_a.size() >= 256 && !this.func_187477_a()) {
                  return BannerTextures.field_187487_d;
               }

               List<String> ☃x = Lists.newArrayList();

               for(BannerPattern ☃xx : ☃) {
                  ☃x.add(this.field_187481_c + ☃xx.func_190997_a() + ".png");
               }

               ☃ = new BannerTextures.CacheEntry();
               ☃.field_187484_b = new ResourceLocation(☃);
               Minecraft.func_71410_x().func_110434_K().func_110579_a(☃.field_187484_b, new LayeredColorMaskTexture(this.field_187480_b, ☃x, ☃));
               this.field_187479_a.put(☃, ☃);
            }

            ☃.field_187483_a = Util.func_211177_b();
            return ☃.field_187484_b;
         } else {
            return MissingTextureSprite.func_195675_b();
         }
      }

      private boolean func_187477_a() {
         long ☃ = Util.func_211177_b();
         Iterator<String> ☃x = this.field_187479_a.keySet().iterator();

         while(☃x.hasNext()) {
            String ☃xx = (String)☃x.next();
            BannerTextures.CacheEntry ☃xxx = (BannerTextures.CacheEntry)this.field_187479_a.get(☃xx);
            if (☃ - ☃xxx.field_187483_a > 5000L) {
               Minecraft.func_71410_x().func_110434_K().func_147645_c(☃xxx.field_187484_b);
               ☃x.remove();
               return true;
            }
         }

         return this.field_187479_a.size() < 256;
      }
   }

   static class CacheEntry {
      public long field_187483_a;
      public ResourceLocation field_187484_b;

      private CacheEntry() {
      }
   }
}
