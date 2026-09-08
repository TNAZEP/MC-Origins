package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
   private static final Logger field_147635_d = LogManager.getLogger();
   public static final ResourceLocation field_110575_b = new ResourceLocation("textures/atlas/blocks.png");
   private final List<TextureAtlasSprite> field_94258_i = Lists.<TextureAtlasSprite>newArrayList();
   private final Set<ResourceLocation> field_195427_i = Sets.<ResourceLocation>newHashSet();
   private final Map<ResourceLocation, TextureAtlasSprite> field_94252_e = Maps.<ResourceLocation, TextureAtlasSprite>newHashMap();
   private final String field_94254_c;
   private int field_147636_j;
   private final TextureAtlasSprite field_94249_f = MissingTextureSprite.func_195677_a();

   public TextureMap(String var1) {
      this.field_94254_c = ☃;
   }

   @Override
   public void func_195413_a(IResourceManager var1) throws IOException {
   }

   public void func_195426_a(IResourceManager var1, Iterable<ResourceLocation> var2) {
      this.field_195427_i.clear();
      ☃.forEach(var2x -> this.func_199362_a(☃, var2x));
      this.func_195421_b(☃);
   }

   public void func_195421_b(IResourceManager var1) {
      int ☃ = Minecraft.func_71369_N();
      Stitcher ☃x = new Stitcher(☃, ☃, 0, this.field_147636_j);
      this.func_195419_g();
      int ☃xx = Integer.MAX_VALUE;
      int ☃xxx = 1 << this.field_147636_j;

      for(ResourceLocation ☃xxxx : this.field_195427_i) {
         if (!this.field_94249_f.func_195668_m().equals(☃xxxx)) {
            ResourceLocation ☃xxxxx = this.func_195420_b(☃xxxx);

            TextureAtlasSprite ☃;
            try {
               IResource ☃xxxxxx = ☃.func_199002_a(☃xxxxx);
               Throwable var11 = null;

               try {
                  PngSizeInfo ☃xxxxxxx = new PngSizeInfo(☃xxxxxx);
                  AnimationMetadataSection ☃xxxxxxxx = ☃xxxxxx.func_199028_a(AnimationMetadataSection.field_195817_a);
                  ☃ = new TextureAtlasSprite(☃xxxx, ☃xxxxxxx, ☃xxxxxxxx);
               } catch (Throwable var27) {
                  var11 = var27;
                  throw var27;
               } finally {
                  if (☃xxxxxx != null) {
                     if (var11 != null) {
                        try {
                           ☃xxxxxx.close();
                        } catch (Throwable var26) {
                           var11.addSuppressed(var26);
                        }
                     } else {
                        ☃xxxxxx.close();
                     }
                  }
               }
            } catch (RuntimeException var29) {
               field_147635_d.error("Unable to parse metadata from {} : {}", ☃xxxxx, var29);
               continue;
            } catch (IOException var30) {
               field_147635_d.error("Using missing texture, unable to load {} : {}", ☃xxxxx, var30);
               continue;
            }

            ☃xx = Math.min(☃xx, Math.min(☃.func_94211_a(), ☃.func_94216_b()));
            int ☃xxxxxx = Math.min(Integer.lowestOneBit(☃.func_94211_a()), Integer.lowestOneBit(☃.func_94216_b()));
            if (☃xxxxxx < ☃xxx) {
               field_147635_d.warn(
                  "Texture {} with size {}x{} limits mip level from {} to {}",
                  ☃xxxxx,
                  ☃.func_94211_a(),
                  ☃.func_94216_b(),
                  MathHelper.func_151239_c(☃xxx),
                  MathHelper.func_151239_c(☃xxxxxx)
               );
               ☃xxx = ☃xxxxxx;
            }

            ☃x.func_110934_a(☃);
         }
      }

      int ☃xxxx = Math.min(☃xx, ☃xxx);
      int ☃xxxxx = MathHelper.func_151239_c(☃xxxx);
      if (☃xxxxx < this.field_147636_j) {
         field_147635_d.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.field_94254_c, this.field_147636_j, ☃xxxxx, ☃xxxx);
         this.field_147636_j = ☃xxxxx;
      }

      this.field_94249_f.func_147963_d(this.field_147636_j);
      ☃x.func_110934_a(this.field_94249_f);

      try {
         ☃x.func_94305_f();
      } catch (StitcherException var25) {
         throw var25;
      }

      field_147635_d.info("Created: {}x{} {}-atlas", ☃x.func_110935_a(), ☃x.func_110936_b(), this.field_94254_c);
      TextureUtil.func_180600_a(this.func_110552_b(), this.field_147636_j, ☃x.func_110935_a(), ☃x.func_110936_b());

      for(TextureAtlasSprite ☃xxxx : ☃x.func_94309_g()) {
         if (☃xxxx == this.field_94249_f || this.func_195422_a(☃, ☃xxxx)) {
            this.field_94252_e.put(☃xxxx.func_195668_m(), ☃xxxx);

            try {
               ☃xxxx.func_195663_q();
            } catch (Throwable var24) {
               CrashReport ☃xxxxx = CrashReport.func_85055_a(var24, "Stitching texture atlas");
               CrashReportCategory ☃xxxxxx = ☃xxxxx.func_85058_a("Texture being stitched together");
               ☃xxxxxx.func_71507_a("Atlas path", this.field_94254_c);
               ☃xxxxxx.func_71507_a("Sprite", ☃xxxx);
               throw new ReportedException(☃xxxxx);
            }

            if (☃xxxx.func_130098_m()) {
               this.field_94258_i.add(☃xxxx);
            }
         }
      }
   }

   private boolean func_195422_a(IResourceManager var1, TextureAtlasSprite var2) {
      ResourceLocation ☃ = this.func_195420_b(☃.func_195668_m());
      IResource ☃x = null;

      label52: {
         boolean var6;
         try {
            ☃x = ☃.func_199002_a(☃);
            ☃.func_195664_a(☃x, this.field_147636_j + 1);
            break label52;
         } catch (RuntimeException var13) {
            field_147635_d.error("Unable to parse metadata from {}", ☃, var13);
            return false;
         } catch (IOException var14) {
            field_147635_d.error("Using missing texture, unable to load {}", ☃, var14);
            var6 = false;
         } finally {
            IOUtils.closeQuietly(☃x);
         }

         return var6;
      }

      try {
         ☃.func_147963_d(this.field_147636_j);
         return true;
      } catch (Throwable var12) {
         CrashReport ☃xx = CrashReport.func_85055_a(var12, "Applying mipmap");
         CrashReportCategory ☃xxx = ☃xx.func_85058_a("Sprite being mipmapped");
         ☃xxx.func_189529_a("Sprite name", () -> ☃.func_195668_m().toString());
         ☃xxx.func_189529_a("Sprite size", () -> ☃.func_94211_a() + " x " + ☃.func_94216_b());
         ☃xxx.func_189529_a("Sprite frames", () -> ☃.func_110970_k() + " frames");
         ☃xxx.func_71507_a("Mipmap levels", this.field_147636_j);
         throw new ReportedException(☃xx);
      }
   }

   private ResourceLocation func_195420_b(ResourceLocation var1) {
      return new ResourceLocation(☃.func_110624_b(), String.format("%s/%s%s", this.field_94254_c, ☃.func_110623_a(), ".png"));
   }

   public TextureAtlasSprite func_110572_b(String var1) {
      return this.func_195424_a(new ResourceLocation(☃));
   }

   public void func_94248_c() {
      this.func_195412_h();

      for(TextureAtlasSprite ☃ : this.field_94258_i) {
         ☃.func_94219_l();
      }
   }

   public void func_199362_a(IResourceManager var1, ResourceLocation var2) {
      if (☃ == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      } else {
         this.field_195427_i.add(☃);
      }
   }

   @Override
   public void func_110550_d() {
      this.func_94248_c();
   }

   public void func_147633_a(int var1) {
      this.field_147636_j = ☃;
   }

   public TextureAtlasSprite func_195424_a(ResourceLocation var1) {
      TextureAtlasSprite ☃ = (TextureAtlasSprite)this.field_94252_e.get(☃);
      return ☃ == null ? this.field_94249_f : ☃;
   }

   public void func_195419_g() {
      for(TextureAtlasSprite ☃ : this.field_94252_e.values()) {
         ☃.func_130103_l();
      }

      this.field_94252_e.clear();
      this.field_94258_i.clear();
   }
}
