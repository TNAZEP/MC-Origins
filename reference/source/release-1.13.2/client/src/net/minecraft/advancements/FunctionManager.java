package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.FunctionObject;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.SimpleResource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FunctionManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger field_193067_a = LogManager.getLogger();
   private static final ResourceLocation field_200001_d = new ResourceLocation("tick");
   private static final ResourceLocation field_200222_e = new ResourceLocation("load");
   public static final int field_195454_a = "functions/".length();
   public static final int field_195455_b = ".mcfunction".length();
   private final MinecraftServer field_193069_c;
   private final Map<ResourceLocation, FunctionObject> field_193070_d = Maps.<ResourceLocation, FunctionObject>newHashMap();
   private final ArrayDeque<FunctionManager.QueuedCommand> field_194020_g = new ArrayDeque();
   private boolean field_194021_h;
   private final TagCollection<FunctionObject> field_200002_i = new TagCollection<>(
      var1x -> this.func_193058_a(var1x) != null, this::func_193058_a, "tags/functions", true, "function"
   );
   private final List<FunctionObject> field_200003_j = Lists.<FunctionObject>newArrayList();
   private boolean field_200223_l;

   public FunctionManager(MinecraftServer var1) {
      this.field_193069_c = ☃;
   }

   @Nullable
   public FunctionObject func_193058_a(ResourceLocation var1) {
      return (FunctionObject)this.field_193070_d.get(☃);
   }

   public MinecraftServer func_195450_a() {
      return this.field_193069_c;
   }

   public int func_193065_c() {
      return this.field_193069_c.func_200252_aR().func_180263_c("maxCommandChainLength");
   }

   public Map<ResourceLocation, FunctionObject> func_193066_d() {
      return this.field_193070_d;
   }

   public CommandDispatcher<CommandSource> func_195446_d() {
      return this.field_193069_c.func_195571_aL().func_197054_a();
   }

   @Override
   public void func_73660_a() {
      this.field_193069_c.field_71304_b.func_194340_a(field_200001_d::toString);

      for(FunctionObject ☃ : this.field_200003_j) {
         this.func_195447_a(☃, this.func_195448_f());
      }

      this.field_193069_c.field_71304_b.func_76319_b();
      if (this.field_200223_l) {
         this.field_200223_l = false;
         Collection<FunctionObject> ☃ = this.func_200000_g().func_199915_b(field_200222_e).func_199885_a();
         this.field_193069_c.field_71304_b.func_194340_a(field_200222_e::toString);

         for(FunctionObject ☃x : ☃) {
            this.func_195447_a(☃x, this.func_195448_f());
         }

         this.field_193069_c.field_71304_b.func_76319_b();
      }
   }

   public int func_195447_a(FunctionObject var1, CommandSource var2) {
      int ☃ = this.func_193065_c();
      if (this.field_194021_h) {
         if (this.field_194020_g.size() < ☃) {
            this.field_194020_g.addFirst(new FunctionManager.QueuedCommand(this, ☃, new FunctionObject.FunctionEntry(☃)));
         }

         return 0;
      } else {
         try {
            this.field_194021_h = true;
            int ☃ = 0;
            FunctionObject.Entry[] ☃x = ☃.func_193528_a();

            for(int ☃xx = ☃x.length - 1; ☃xx >= 0; --☃xx) {
               this.field_194020_g.push(new FunctionManager.QueuedCommand(this, ☃, ☃x[☃xx]));
            }

            while(!this.field_194020_g.isEmpty()) {
               try {
                  FunctionManager.QueuedCommand ☃xx = (FunctionManager.QueuedCommand)this.field_194020_g.removeFirst();
                  this.field_193069_c.field_71304_b.func_194340_a(☃xx::toString);
                  ☃xx.func_194222_a(this.field_194020_g, ☃);
               } finally {
                  this.field_193069_c.field_71304_b.func_76319_b();
               }

               if (++☃ >= ☃) {
                  return ☃;
               }
            }

            return ☃;
         } finally {
            this.field_194020_g.clear();
            this.field_194021_h = false;
         }
      }
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.field_193070_d.clear();
      this.field_200003_j.clear();
      this.field_200002_i.func_199917_b();
      Collection<ResourceLocation> ☃ = ☃.func_199003_a("functions", var0 -> var0.endsWith(".mcfunction"));
      List<CompletableFuture<FunctionObject>> ☃x = Lists.newArrayList();

      for(ResourceLocation ☃xx : ☃) {
         String ☃xxx = ☃xx.func_110623_a();
         ResourceLocation ☃xxxx = new ResourceLocation(☃xx.func_110624_b(), ☃xxx.substring(field_195454_a, ☃xxx.length() - field_195455_b));
         ☃x.add(
            CompletableFuture.supplyAsync(() -> func_195449_a(☃, ☃), SimpleResource.field_199031_a)
               .thenApplyAsync(var2x -> FunctionObject.func_197000_a(☃, this, var2x))
               .handle((var2x, var3x) -> this.func_212250_a(var2x, var3x, ☃))
         );
      }

      CompletableFuture.allOf((CompletableFuture[])☃x.toArray(new CompletableFuture[0])).join();
      if (!this.field_193070_d.isEmpty()) {
         field_193067_a.info("Loaded {} custom command functions", this.field_193070_d.size());
      }

      this.field_200002_i.func_199909_a(☃);
      this.field_200003_j.addAll(this.field_200002_i.func_199915_b(field_200001_d).func_199885_a());
      this.field_200223_l = true;
   }

   @Nullable
   private FunctionObject func_212250_a(FunctionObject var1, @Nullable Throwable var2, ResourceLocation var3) {
      if (☃ != null) {
         field_193067_a.error("Couldn't load function at {}", ☃, ☃);
         return null;
      } else {
         synchronized(this.field_193070_d) {
            this.field_193070_d.put(☃.func_197001_a(), ☃);
            return ☃;
         }
      }
   }

   private static List<String> func_195449_a(IResourceManager var0, ResourceLocation var1) {
      try {
         IResource ☃ = ☃.func_199002_a(☃);
         Throwable var3 = null;

         List var4;
         try {
            var4 = IOUtils.readLines(☃.func_199027_b(), StandardCharsets.UTF_8);
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (☃ != null) {
               if (var3 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  ☃.close();
               }
            }
         }

         return var4;
      } catch (IOException var16) {
         throw new CompletionException(var16);
      }
   }

   public CommandSource func_195448_f() {
      return this.field_193069_c.func_195573_aM().func_197033_a(2).func_197031_a();
   }

   public TagCollection<FunctionObject> func_200000_g() {
      return this.field_200002_i;
   }

   public static class QueuedCommand {
      private final FunctionManager field_194223_a;
      private final CommandSource field_194224_b;
      private final FunctionObject.Entry field_194225_c;

      public QueuedCommand(FunctionManager var1, CommandSource var2, FunctionObject.Entry var3) {
         this.field_194223_a = ☃;
         this.field_194224_b = ☃;
         this.field_194225_c = ☃;
      }

      public void func_194222_a(ArrayDeque<FunctionManager.QueuedCommand> var1, int var2) {
         try {
            this.field_194225_c.func_196998_a(this.field_194223_a, this.field_194224_b, ☃, ☃);
         } catch (Throwable var4) {
         }
      }

      public String toString() {
         return this.field_194225_c.toString();
      }
   }
}
