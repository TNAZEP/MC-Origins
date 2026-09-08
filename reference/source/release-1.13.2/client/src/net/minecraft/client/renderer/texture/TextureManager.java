package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger field_147646_a = LogManager.getLogger();
   public static final ResourceLocation field_194008_a = new ResourceLocation("");
   private final Map<ResourceLocation, ITextureObject> field_110585_a = Maps.<ResourceLocation, ITextureObject>newHashMap();
   private final List<ITickable> field_110583_b = Lists.<ITickable>newArrayList();
   private final Map<String, Integer> field_110584_c = Maps.newHashMap();
   private final IResourceManager field_110582_d;

   public TextureManager(IResourceManager var1) {
      this.field_110582_d = ☃;
   }

   public void func_110577_a(ResourceLocation var1) {
      ITextureObject ☃ = (ITextureObject)this.field_110585_a.get(☃);
      if (☃ == null) {
         ☃ = new SimpleTexture(☃);
         this.func_110579_a(☃, ☃);
      }

      ☃.func_195412_h();
   }

   public boolean func_110580_a(ResourceLocation var1, ITickableTextureObject var2) {
      if (this.func_110579_a(☃, ☃)) {
         this.field_110583_b.add(☃);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_110579_a(ResourceLocation var1, ITextureObject var2) {
      boolean ☃ = true;

      try {
         ☃.func_195413_a(this.field_110582_d);
      } catch (IOException var8) {
         if (☃ != field_194008_a) {
            field_147646_a.warn("Failed to load texture: {}", ☃, var8);
         }

         ☃ = MissingTextureSprite.func_195676_d();
         this.field_110585_a.put(☃, ☃);
         ☃ = false;
      } catch (Throwable var9) {
         CrashReport ☃x = CrashReport.func_85055_a(var9, "Registering texture");
         CrashReportCategory ☃xx = ☃x.func_85058_a("Resource location being registered");
         ☃xx.func_71507_a("Resource location", ☃);
         ☃xx.func_189529_a("Texture object class", () -> ☃.getClass().getName());
         throw new ReportedException(☃x);
      }

      this.field_110585_a.put(☃, ☃);
      return ☃;
   }

   public ITextureObject func_110581_b(ResourceLocation var1) {
      return (ITextureObject)this.field_110585_a.get(☃);
   }

   public ResourceLocation func_110578_a(String var1, DynamicTexture var2) {
      Integer ☃ = (Integer)this.field_110584_c.get(☃);
      if (☃ == null) {
         ☃ = 1;
      } else {
         ☃ = ☃ + 1;
      }

      this.field_110584_c.put(☃, ☃);
      ResourceLocation ☃ = new ResourceLocation(String.format("dynamic/%s_%d", ☃, ☃));
      this.func_110579_a(☃, ☃);
      return ☃;
   }

   @Override
   public void func_110550_d() {
      for(ITickable ☃ : this.field_110583_b) {
         ☃.func_110550_d();
      }
   }

   public void func_147645_c(ResourceLocation var1) {
      ITextureObject ☃ = this.func_110581_b(☃);
      if (☃ != null) {
         TextureUtil.func_147942_a(☃.func_110552_b());
      }
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      MissingTextureSprite.func_195676_d();
      Iterator<Entry<ResourceLocation, ITextureObject>> ☃ = this.field_110585_a.entrySet().iterator();

      while(☃.hasNext()) {
         Entry<ResourceLocation, ITextureObject> ☃x = (Entry)☃.next();
         ResourceLocation ☃xx = (ResourceLocation)☃x.getKey();
         ITextureObject ☃xxx = (ITextureObject)☃x.getValue();
         if (☃xxx == MissingTextureSprite.func_195676_d() && !☃xx.equals(MissingTextureSprite.func_195675_b())) {
            ☃.remove();
         } else {
            this.func_110579_a((ResourceLocation)☃x.getKey(), ☃xxx);
         }
      }
   }
}
