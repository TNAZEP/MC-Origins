package net.minecraft.world.gen;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.TaskManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.tasks.ProtoChunkScheduler;
import net.minecraft.world.storage.SessionLockException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider {
   private static final Logger field_147417_b = LogManager.getLogger();
   private final LongSet field_73248_b = new LongOpenHashSet();
   private final IChunkGenerator<?> field_186029_c;
   private final IChunkLoader field_73247_e;
   private final Long2ObjectMap<Chunk> field_73244_f = Long2ObjectMaps.synchronize(new ChunkCacheNeighborNotification(8192));
   private Chunk field_212472_f;
   private final ProtoChunkScheduler field_201723_f;
   private final TaskManager<ChunkPos, ChunkStatus, ChunkPrimer> field_201724_g;
   private final WorldServer field_73251_h;
   private final IThreadListener field_212473_j;

   public ChunkProviderServer(WorldServer var1, IChunkLoader var2, IChunkGenerator<?> var3, IThreadListener var4) {
      this.field_73251_h = ☃;
      this.field_73247_e = ☃;
      this.field_186029_c = ☃;
      this.field_212473_j = ☃;
      this.field_201723_f = new ProtoChunkScheduler(2, ☃, ☃, ☃, ☃);
      this.field_201724_g = new TaskManager<>(this.field_201723_f);
   }

   public Collection<Chunk> func_189548_a() {
      return this.field_73244_f.values();
   }

   public void func_189549_a(Chunk var1) {
      if (this.field_73251_h.field_73011_w.func_186056_c(☃.field_76635_g, ☃.field_76647_h)) {
         this.field_73248_b.add(ChunkPos.func_77272_a(☃.field_76635_g, ☃.field_76647_h));
      }
   }

   public void func_73240_a() {
      for(Chunk ☃ : this.field_73244_f.values()) {
         this.func_189549_a(☃);
      }
   }

   public void func_212469_a(int var1, int var2) {
      this.field_73248_b.remove(ChunkPos.func_77272_a(☃, ☃));
   }

   @Nullable
   @Override
   public Chunk func_186025_d(int var1, int var2, boolean var3, boolean var4) {
      Chunk ☃;
      synchronized(this.field_73247_e) {
         if (this.field_212472_f != null && this.field_212472_f.func_76632_l().field_77276_a == ☃ && this.field_212472_f.func_76632_l().field_77275_b == ☃) {
            return this.field_212472_f;
         }

         long ☃x = ChunkPos.func_77272_a(☃, ☃);
         ☃ = this.field_73244_f.get(☃x);
         if (☃ != null) {
            this.field_212472_f = ☃;
            return ☃;
         }

         if (☃) {
            try {
               ☃ = this.field_73247_e.func_199813_a(this.field_73251_h, ☃, ☃, var3x -> {
                  var3x.func_177432_b(this.field_73251_h.func_82737_E());
                  this.field_73244_f.put(ChunkPos.func_77272_a(☃, ☃), var3x);
               });
            } catch (Exception var12) {
               field_147417_b.error("Couldn't load chunk", var12);
            }
         }
      }

      if (☃ != null) {
         this.field_212473_j.func_152344_a(☃::func_76631_c);
         return ☃;
      } else if (☃) {
         try {
            this.field_201724_g.func_202928_b();
            this.field_201724_g.func_202926_a(new ChunkPos(☃, ☃));
            CompletableFuture<ChunkPrimer> ☃ = this.field_201724_g.func_202927_c();
            return (Chunk)☃.thenApply(this::func_201719_a).join();
         } catch (RuntimeException var11) {
            throw this.func_201722_a(☃, ☃, var11);
         }
      } else {
         return null;
      }
   }

   @Override
   public IChunk func_201713_d(int var1, int var2, boolean var3) {
      IChunk ☃ = this.func_186025_d(☃, ☃, true, false);
      return ☃ != null ? ☃ : this.field_201723_f.func_212537_b(new ChunkPos(☃, ☃), ☃);
   }

   public CompletableFuture<ChunkPrimer> func_201720_a(Iterable<ChunkPos> var1, Consumer<Chunk> var2) {
      this.field_201724_g.func_202928_b();

      for(ChunkPos ☃ : ☃) {
         Chunk ☃x = this.func_186025_d(☃.field_77276_a, ☃.field_77275_b, true, false);
         if (☃x != null) {
            ☃.accept(☃x);
         } else {
            this.field_201724_g.func_202926_a(☃).thenApply(this::func_201719_a).thenAccept(☃);
         }
      }

      return this.field_201724_g.func_202927_c();
   }

   private ReportedException func_201722_a(int var1, int var2, Throwable var3) {
      CrashReport ☃ = CrashReport.func_85055_a(☃, "Exception generating new chunk");
      CrashReportCategory ☃x = ☃.func_85058_a("Chunk to be generated");
      ☃x.func_71507_a("Location", String.format("%d,%d", ☃, ☃));
      ☃x.func_71507_a("Position hash", ChunkPos.func_77272_a(☃, ☃));
      ☃x.func_71507_a("Generator", this.field_186029_c);
      return new ReportedException(☃);
   }

   private Chunk func_201719_a(IChunk var1) {
      ChunkPos ☃x = ☃.func_76632_l();
      int ☃xx = ☃x.field_77276_a;
      int ☃xxx = ☃x.field_77275_b;
      long ☃xxxx = ChunkPos.func_77272_a(☃xx, ☃xxx);
      Chunk ☃;
      synchronized(this.field_73244_f) {
         Chunk ☃xxxxx = this.field_73244_f.get(☃xxxx);
         if (☃xxxxx != null) {
            return ☃xxxxx;
         }

         if (☃ instanceof Chunk) {
            ☃ = (Chunk)☃;
         } else {
            if (!(☃ instanceof ChunkPrimer)) {
               throw new IllegalStateException();
            }

            ☃ = new Chunk(this.field_73251_h, (ChunkPrimer)☃, ☃xx, ☃xxx);
         }

         this.field_73244_f.put(☃xxxx, ☃);
         this.field_212472_f = ☃;
      }

      this.field_212473_j.func_152344_a(☃::func_76631_c);
      return ☃;
   }

   private void func_73242_b(IChunk var1) {
      try {
         ☃.func_177432_b(this.field_73251_h.func_82737_E());
         this.field_73247_e.func_75816_a(this.field_73251_h, ☃);
      } catch (IOException var3) {
         field_147417_b.error("Couldn't save chunk", var3);
      } catch (SessionLockException var4) {
         field_147417_b.error("Couldn't save chunk; already in use by another instance of Minecraft?", var4);
      }
   }

   public boolean func_186027_a(boolean var1) {
      int ☃ = 0;
      this.field_201723_f.func_208484_a(() -> true);
      synchronized(this.field_73247_e) {
         for(Chunk ☃x : this.field_73244_f.values()) {
            if (☃x.func_76601_a(☃)) {
               this.func_73242_b(☃x);
               ☃x.func_177427_f(false);
               if (++☃ == 24 && !☃) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   @Override
   public void close() {
      try {
         this.field_201724_g.func_202925_a();
      } catch (InterruptedException var2) {
         field_147417_b.error("Couldn't stop taskManager", var2);
      }
   }

   public void func_104112_b() {
      synchronized(this.field_73247_e) {
         this.field_73247_e.func_75818_b();
      }
   }

   @Override
   public boolean func_73156_b(BooleanSupplier var1) {
      if (!this.field_73251_h.field_73058_d) {
         if (!this.field_73248_b.isEmpty()) {
            Iterator<Long> ☃ = this.field_73248_b.iterator();

            for(int ☃x = 0; ☃.hasNext() && (☃.getAsBoolean() || ☃x < 200 || this.field_73248_b.size() > 2000); ☃.remove()) {
               Long ☃xx = (Long)☃.next();
               synchronized(this.field_73247_e) {
                  Chunk ☃xxx = this.field_73244_f.get(☃xx);
                  if (☃xxx != null) {
                     ☃xxx.func_76623_d();
                     this.func_73242_b(☃xxx);
                     this.field_73244_f.remove(☃xx);
                     this.field_212472_f = null;
                     ++☃x;
                  }
               }
            }
         }

         this.field_201723_f.func_208484_a(☃);
      }

      return false;
   }

   public boolean func_73157_c() {
      return !this.field_73251_h.field_73058_d;
   }

   @Override
   public String func_73148_d() {
      return "ServerChunkCache: " + this.field_73244_f.size() + " Drop: " + this.field_73248_b.size();
   }

   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType var1, BlockPos var2) {
      return this.field_186029_c.func_177458_a(☃, ☃);
   }

   public int func_203082_a(World var1, boolean var2, boolean var3) {
      return this.field_186029_c.func_203222_a(☃, ☃, ☃);
   }

   @Nullable
   public BlockPos func_211268_a(World var1, String var2, BlockPos var3, int var4, boolean var5) {
      return this.field_186029_c.func_211403_a(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public IChunkGenerator<?> func_201711_g() {
      return this.field_186029_c;
   }

   public int func_73152_e() {
      return this.field_73244_f.size();
   }

   public boolean func_73149_a(int var1, int var2) {
      return this.field_73244_f.containsKey(ChunkPos.func_77272_a(☃, ☃));
   }
}
