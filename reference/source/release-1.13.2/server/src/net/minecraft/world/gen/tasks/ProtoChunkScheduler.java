package net.minecraft.world.gen.tasks;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.ExpiringMap;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.Scheduler;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.SessionLockException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtoChunkScheduler extends Scheduler<ChunkPos, ChunkStatus, ChunkPrimer> {
   private static final Logger field_202873_b = LogManager.getLogger();
   private final World field_202874_c;
   private final IChunkGenerator<?> field_202875_d;
   private final IChunkLoader field_202876_e;
   private final IThreadListener field_202877_f;
   private final Long2ObjectMap<Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a> field_202878_g = new ExpiringMap<Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a>(
      8192, 5000
   ) {
      protected boolean func_205609_a_(Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a var1) {
         ChunkPrimer ☃ = (ChunkPrimer)☃.func_202917_a();
         return !☃.func_205748_B() && !☃.func_201593_f();
      }
   };

   public ProtoChunkScheduler(int var1, World var2, IChunkGenerator<?> var3, IChunkLoader var4, IThreadListener var5) {
      super("WorldGen", ☃, ChunkStatus.FINALIZED, () -> new EnumMap(ChunkStatus.class), () -> new EnumMap(ChunkStatus.class));
      this.field_202874_c = ☃;
      this.field_202875_d = ☃;
      this.field_202876_e = ☃;
      this.field_202877_f = ☃;
   }

   @Nullable
   protected Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a func_212252_a_(ChunkPos var1, boolean var2) {
      synchronized(this.field_202876_e) {
         return ☃ ? (Scheduler.FutureWrapper)this.field_202878_g.computeIfAbsent(☃.func_201841_a(), var2x -> {
            ChunkPrimer ☃;
            try {
               ☃ = this.field_202876_e.func_202152_b(this.field_202874_c, ☃.field_77276_a, ☃.field_77275_b, var0 -> {
               });
            } catch (ReportedException var6) {
               throw var6;
            } catch (Exception var7) {
               field_202873_b.error("Couldn't load protochunk", var7);
               ☃ = null;
            }

            if (☃ != null) {
               ☃.func_177432_b(this.field_202874_c.func_82737_E());
               return new Scheduler.FutureWrapper(☃, ☃, ☃.func_201589_g());
            } else {
               return new Scheduler.FutureWrapper(☃, new ChunkPrimer(☃, UpgradeData.field_196994_a), ChunkStatus.EMPTY);
            }
         }) : (Scheduler.FutureWrapper)this.field_202878_g.get(☃.func_201841_a());
      }
   }

   protected ChunkPrimer func_201493_a_(ChunkPos var1, ChunkStatus var2, Map<ChunkPos, ChunkPrimer> var3) {
      return ☃.func_202126_a(this.field_202874_c, this.field_202875_d, ☃, ☃.field_77276_a, ☃.field_77275_b);
   }

   protected Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a func_205606_a_(ChunkPos var1, Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a var2) {
      ((ChunkPrimer)☃.func_202917_a()).func_205747_a(1);
      return ☃;
   }

   protected void func_205607_b_(ChunkPos var1, Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.a var2) {
      ((ChunkPrimer)☃.func_202917_a()).func_205747_a(-1);
   }

   public void func_208484_a(BooleanSupplier var1) {
      synchronized(this.field_202876_e) {
         for(Scheduler<ChunkPos, ChunkStatus, ChunkPrimer>.FutureWrapper ☃ : this.field_202878_g.values()) {
            ChunkPrimer ☃x = (ChunkPrimer)☃.func_202917_a();
            if (☃x.func_201593_f() && ☃x.func_201589_g().func_202129_d() == ChunkStatus.Type.PROTOCHUNK) {
               try {
                  ☃x.func_177432_b(this.field_202874_c.func_82737_E());
                  this.field_202876_e.func_75816_a(this.field_202874_c, ☃x);
                  ☃x.func_177427_f(false);
               } catch (IOException var8) {
                  field_202873_b.error("Couldn't save chunk", var8);
               } catch (SessionLockException var9) {
                  field_202873_b.error("Couldn't save chunk; already in use by another instance of Minecraft?", var9);
               }
            }

            if (!☃.getAsBoolean()) {
               return;
            }
         }
      }
   }
}
