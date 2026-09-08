package net.minecraft.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadFactory;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldOptimizer {
   private static final Logger field_212219_a = LogManager.getLogger();
   private static final ThreadFactory field_212220_b = new ThreadFactoryBuilder().setDaemon(true).build();
   private final String field_212237_s;
   private final ISaveHandler field_212221_c;
   private final WorldSavedDataStorage field_212222_d;
   private final Thread field_212223_e;
   private volatile boolean field_212224_f = true;
   private volatile boolean field_212225_g = false;
   private volatile float field_212232_n;
   private volatile int field_212233_o;
   private volatile int field_212234_p = 0;
   private volatile int field_212235_q = 0;
   private final Object2FloatMap<DimensionType> field_212544_m = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap<>(Util.func_212443_g()));
   private volatile ITextComponent field_212236_r = new TextComponentTranslation("optimizeWorld.stage.counting");

   public WorldOptimizer(String var1, ISaveFormat var2, WorldInfo var3) {
      this.field_212237_s = ☃.func_76065_j();
      this.field_212221_c = ☃.func_197715_a(☃, null);
      this.field_212221_c.func_75761_a(☃);
      this.field_212222_d = new WorldSavedDataStorage(this.field_212221_c);
      this.field_212223_e = field_212220_b.newThread(this::func_212216_o);
      this.field_212223_e.setUncaughtExceptionHandler(this::func_212206_a);
      this.field_212223_e.start();
   }

   private void func_212206_a(Thread var1, Throwable var2) {
      field_212219_a.error("Error upgrading world", ☃);
      this.field_212224_f = false;
      this.field_212236_r = new TextComponentTranslation("optimizeWorld.stage.failed");
   }

   public void func_212217_a() {
      this.field_212224_f = false;

      try {
         this.field_212223_e.join();
      } catch (InterruptedException var2) {
      }
   }

   private void func_212216_o() {
      File ☃ = this.field_212221_c.func_75765_b();
      WorldChunkEnumerator ☃x = new WorldChunkEnumerator(☃);
      Builder<DimensionType, AnvilChunkLoader> ☃xx = ImmutableMap.builder();

      for(DimensionType ☃xxx : DimensionType.func_212681_b()) {
         ☃xx.put(☃xxx, new AnvilChunkLoader(☃xxx.func_212679_a(☃), this.field_212221_c.func_197718_i()));
      }

      Map<DimensionType, AnvilChunkLoader> ☃xxx = ☃xx.build();
      long ☃xxxx = Util.func_211177_b();
      this.field_212233_o = 0;
      Builder<DimensionType, ListIterator<ChunkPos>> ☃xxxxx = ImmutableMap.builder();

      for(DimensionType ☃xxxxxx : DimensionType.func_212681_b()) {
         List<ChunkPos> ☃xxxxxxx = ☃x.func_212541_a(☃xxxxxx);
         ☃xxxxx.put(☃xxxxxx, ☃xxxxxxx.listIterator());
         this.field_212233_o += ☃xxxxxxx.size();
      }

      ImmutableMap<DimensionType, ListIterator<ChunkPos>> ☃xxxxxx = ☃xxxxx.build();
      float ☃xxxxxxx = (float)this.field_212233_o;
      this.field_212236_r = new TextComponentTranslation("optimizeWorld.stage.structures");

      for(Entry<DimensionType, AnvilChunkLoader> ☃xxxxxxxx : ☃xxx.entrySet()) {
         ((AnvilChunkLoader)☃xxxxxxxx.getValue()).func_212429_a((DimensionType)☃xxxxxxxx.getKey(), this.field_212222_d);
      }

      this.field_212222_d.func_75744_a();
      this.field_212236_r = new TextComponentTranslation("optimizeWorld.stage.upgrading");
      if (☃xxxxxxx <= 0.0F) {
         for(DimensionType ☃xxxxxxxx : DimensionType.func_212681_b()) {
            this.field_212544_m.put(☃xxxxxxxx, 1.0F / (float)☃xxx.size());
         }
      }

      while(this.field_212224_f) {
         boolean ☃xxxxxxxx = false;
         float ☃xxxxxxxxx = 0.0F;

         for(DimensionType ☃xxxxxxxxxx : DimensionType.func_212681_b()) {
            ListIterator<ChunkPos> ☃xxxxxxxxxxx = (ListIterator)☃xxxxxx.get(☃xxxxxxxxxx);
            ☃xxxxxxxx |= this.func_212542_a((AnvilChunkLoader)☃xxx.get(☃xxxxxxxxxx), ☃xxxxxxxxxxx, ☃xxxxxxxxxx);
            if (☃xxxxxxx > 0.0F) {
               float ☃xxxxxxxxxxxx = (float)☃xxxxxxxxxxx.nextIndex() / ☃xxxxxxx;
               this.field_212544_m.put(☃xxxxxxxxxx, ☃xxxxxxxxxxxx);
               ☃xxxxxxxxx += ☃xxxxxxxxxxxx;
            }
         }

         this.field_212232_n = ☃xxxxxxxxx;
         if (!☃xxxxxxxx) {
            this.field_212224_f = false;
         }
      }

      this.field_212236_r = new TextComponentTranslation("optimizeWorld.stage.finished");
      ☃xxxx = Util.func_211177_b() - ☃xxxx;
      field_212219_a.info("World optimizaton finished after {} ms", ☃xxxx);
      ☃xxx.values().forEach(AnvilChunkLoader::func_75818_b);
      this.field_212222_d.func_75744_a();
      this.field_212221_c.func_75759_a();
      this.field_212225_g = true;
   }

   private boolean func_212542_a(AnvilChunkLoader var1, ListIterator<ChunkPos> var2, DimensionType var3) {
      if (☃.hasNext()) {
         boolean ☃;
         synchronized(☃) {
            ☃ = ☃.func_212147_a((ChunkPos)☃.next(), ☃, this.field_212222_d);
         }

         if (☃) {
            ++this.field_212234_p;
         } else {
            ++this.field_212235_q;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_212218_b() {
      return this.field_212225_g;
   }

   public int func_212211_j() {
      return this.field_212233_o;
   }

   public int func_212208_k() {
      return this.field_212234_p;
   }

   public int func_212209_l() {
      return this.field_212235_q;
   }

   public ITextComponent func_212215_m() {
      return this.field_212236_r;
   }
}
