package net.minecraft.resources;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Logger field_199012_a = LogManager.getLogger();
   private final Map<String, FallbackResourceManager> field_199014_c = Maps.newHashMap();
   private final List<IResourceManagerReloadListener> field_199015_d = Lists.<IResourceManagerReloadListener>newArrayList();
   private final Set<String> field_199016_e = Sets.newLinkedHashSet();
   private final ResourcePackType field_199017_f;

   public SimpleReloadableResourceManager(ResourcePackType var1) {
      this.field_199017_f = ☃;
   }

   public void func_199009_a(IResourcePack var1) {
      for(String ☃ : ☃.func_195759_a(this.field_199017_f)) {
         this.field_199016_e.add(☃);
         FallbackResourceManager ☃x = (FallbackResourceManager)this.field_199014_c.get(☃);
         if (☃x == null) {
            ☃x = new FallbackResourceManager(this.field_199017_f);
            this.field_199014_c.put(☃, ☃x);
         }

         ☃x.func_199021_a(☃);
      }
   }

   @Override
   public IResource func_199002_a(ResourceLocation var1) throws IOException {
      IResourceManager ☃ = (IResourceManager)this.field_199014_c.get(☃.func_110624_b());
      if (☃ != null) {
         return ☃.func_199002_a(☃);
      } else {
         throw new FileNotFoundException(☃.toString());
      }
   }

   @Override
   public List<IResource> func_199004_b(ResourceLocation var1) throws IOException {
      IResourceManager ☃ = (IResourceManager)this.field_199014_c.get(☃.func_110624_b());
      if (☃ != null) {
         return ☃.func_199004_b(☃);
      } else {
         throw new FileNotFoundException(☃.toString());
      }
   }

   @Override
   public Collection<ResourceLocation> func_199003_a(String var1, Predicate<String> var2) {
      Set<ResourceLocation> ☃ = Sets.<ResourceLocation>newHashSet();

      for(FallbackResourceManager ☃x : this.field_199014_c.values()) {
         ☃.addAll(☃x.func_199003_a(☃, ☃));
      }

      List<ResourceLocation> ☃x = Lists.<ResourceLocation>newArrayList(☃);
      Collections.sort(☃x);
      return ☃x;
   }

   private void func_199008_b() {
      this.field_199014_c.clear();
      this.field_199016_e.clear();
   }

   @Override
   public void func_199005_a(List<IResourcePack> var1) {
      this.func_199008_b();
      field_199012_a.info("Reloading ResourceManager: {}", ☃.stream().map(IResourcePack::func_195762_a).collect(Collectors.joining(", ")));

      for(IResourcePack ☃ : ☃) {
         this.func_199009_a(☃);
      }

      if (field_199012_a.isDebugEnabled()) {
         this.func_199011_d();
      } else {
         this.func_199010_c();
      }
   }

   @Override
   public void func_199006_a(IResourceManagerReloadListener var1) {
      this.field_199015_d.add(☃);
      if (field_199012_a.isDebugEnabled()) {
         field_199012_a.info(this.func_199007_b(☃));
      } else {
         ☃.func_195410_a(this);
      }
   }

   private void func_199010_c() {
      for(IResourceManagerReloadListener ☃ : this.field_199015_d) {
         ☃.func_195410_a(this);
      }
   }

   private void func_199011_d() {
      field_199012_a.info("Reloading all resources! {} listeners to update.", this.field_199015_d.size());
      List<String> ☃ = Lists.newArrayList();
      Stopwatch ☃x = Stopwatch.createStarted();

      for(IResourceManagerReloadListener ☃xx : this.field_199015_d) {
         ☃.add(this.func_199007_b(☃xx));
      }

      ☃x.stop();
      field_199012_a.info("----");
      field_199012_a.info("Complete resource reload took {} ms", ☃x.elapsed(TimeUnit.MILLISECONDS));

      for(String ☃xx : ☃) {
         field_199012_a.info(☃xx);
      }

      field_199012_a.info("----");
   }

   private String func_199007_b(IResourceManagerReloadListener var1) {
      Stopwatch ☃ = Stopwatch.createStarted();
      ☃.func_195410_a(this);
      ☃.stop();
      return "Resource reload for " + ☃.getClass().getSimpleName() + " took " + ☃.elapsed(TimeUnit.MILLISECONDS) + " ms";
   }
}
