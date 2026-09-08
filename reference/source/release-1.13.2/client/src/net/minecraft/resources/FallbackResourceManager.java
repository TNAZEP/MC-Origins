package net.minecraft.resources;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager {
   private static final Logger field_199024_b = LogManager.getLogger();
   protected final List<IResourcePack> field_199023_a = Lists.<IResourcePack>newArrayList();
   private final ResourcePackType field_199025_c;

   public FallbackResourceManager(ResourcePackType var1) {
      this.field_199025_c = ☃;
   }

   public void func_199021_a(IResourcePack var1) {
      this.field_199023_a.add(☃);
   }

   @Override
   public Set<String> func_199001_a() {
      return Collections.emptySet();
   }

   @Override
   public IResource func_199002_a(ResourceLocation var1) throws IOException {
      this.func_199022_d(☃);
      IResourcePack ☃ = null;
      ResourceLocation ☃x = func_199020_c(☃);

      for(int ☃xx = this.field_199023_a.size() - 1; ☃xx >= 0; --☃xx) {
         IResourcePack ☃xxx = (IResourcePack)this.field_199023_a.get(☃xx);
         if (☃ == null && ☃xxx.func_195764_b(this.field_199025_c, ☃x)) {
            ☃ = ☃xxx;
         }

         if (☃xxx.func_195764_b(this.field_199025_c, ☃)) {
            InputStream ☃xxx = null;
            if (☃ != null) {
               ☃xxx = this.func_199019_a(☃x, ☃);
            }

            return new SimpleResource(☃xxx.func_195762_a(), ☃, this.func_199019_a(☃, ☃xxx), ☃xxx);
         }
      }

      throw new FileNotFoundException(☃.toString());
   }

   protected InputStream func_199019_a(ResourceLocation var1, IResourcePack var2) throws IOException {
      InputStream ☃ = ☃.func_195761_a(this.field_199025_c, ☃);
      return (InputStream)(field_199024_b.isDebugEnabled() ? new FallbackResourceManager.LeakComplainerInputStream(☃, ☃, ☃.func_195762_a()) : ☃);
   }

   private void func_199022_d(ResourceLocation var1) throws IOException {
      if (☃.func_110623_a().contains("..")) {
         throw new IOException("Invalid relative path to resource: " + ☃);
      }
   }

   @Override
   public List<IResource> func_199004_b(ResourceLocation var1) throws IOException {
      this.func_199022_d(☃);
      List<IResource> ☃ = Lists.<IResource>newArrayList();
      ResourceLocation ☃x = func_199020_c(☃);

      for(IResourcePack ☃xx : this.field_199023_a) {
         if (☃xx.func_195764_b(this.field_199025_c, ☃)) {
            InputStream ☃xxx = ☃xx.func_195764_b(this.field_199025_c, ☃x) ? this.func_199019_a(☃x, ☃xx) : null;
            ☃.add(new SimpleResource(☃xx.func_195762_a(), ☃, this.func_199019_a(☃, ☃xx), ☃xxx));
         }
      }

      if (☃.isEmpty()) {
         throw new FileNotFoundException(☃.toString());
      } else {
         return ☃;
      }
   }

   @Override
   public Collection<ResourceLocation> func_199003_a(String var1, Predicate<String> var2) {
      List<ResourceLocation> ☃ = Lists.<ResourceLocation>newArrayList();

      for(IResourcePack ☃x : this.field_199023_a) {
         ☃.addAll(☃x.func_195758_a(this.field_199025_c, ☃, Integer.MAX_VALUE, ☃));
      }

      Collections.sort(☃);
      return ☃;
   }

   static ResourceLocation func_199020_c(ResourceLocation var0) {
      return new ResourceLocation(☃.func_110624_b(), ☃.func_110623_a() + ".mcmeta");
   }

   static class LeakComplainerInputStream extends InputStream {
      private final InputStream field_198998_a;
      private final String field_198999_b;
      private boolean field_199000_c;

      public LeakComplainerInputStream(InputStream var1, ResourceLocation var2, String var3) {
         this.field_198998_a = ☃;
         ByteArrayOutputStream ☃ = new ByteArrayOutputStream();
         new Exception().printStackTrace(new PrintStream(☃));
         this.field_198999_b = "Leaked resource: '" + ☃ + "' loaded from pack: '" + ☃ + "'\n" + ☃;
      }

      public void close() throws IOException {
         this.field_198998_a.close();
         this.field_199000_c = true;
      }

      protected void finalize() throws Throwable {
         if (!this.field_199000_c) {
            FallbackResourceManager.field_199024_b.warn(this.field_198999_b);
         }

         super.finalize();
      }

      public int read() throws IOException {
         return this.field_198998_a.read();
      }
   }
}
