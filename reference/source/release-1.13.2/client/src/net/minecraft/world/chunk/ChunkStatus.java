package net.minecraft.world.chunk;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.util.ITaskType;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.tasks.BaseChunkTask;
import net.minecraft.world.gen.tasks.CarveChunkTask;
import net.minecraft.world.gen.tasks.ChunkTask;
import net.minecraft.world.gen.tasks.DecorateChunkTask;
import net.minecraft.world.gen.tasks.DummyChunkTask;
import net.minecraft.world.gen.tasks.FinializeChunkTask;
import net.minecraft.world.gen.tasks.LightChunkTask;
import net.minecraft.world.gen.tasks.LiquidCarveChunkTask;
import net.minecraft.world.gen.tasks.SpawnMobsTask;

public enum ChunkStatus implements ITaskType<ChunkPos, ChunkStatus> {
   EMPTY("empty", null, -1, false, ChunkStatus.Type.PROTOCHUNK),
   BASE("base", new BaseChunkTask(), 0, false, ChunkStatus.Type.PROTOCHUNK),
   CARVED("carved", new CarveChunkTask(), 0, false, ChunkStatus.Type.PROTOCHUNK),
   LIQUID_CARVED("liquid_carved", new LiquidCarveChunkTask(), 1, false, ChunkStatus.Type.PROTOCHUNK),
   DECORATED("decorated", new DecorateChunkTask(), 1, true, ChunkStatus.Type.PROTOCHUNK) {
      @Override
      public void func_201492_a_(ChunkPos var1, BiConsumer<ChunkPos, ChunkStatus> var2) {
         int ☃ = ☃.field_77276_a;
         int ☃x = ☃.field_77275_b;
         ChunkStatus ☃xx = this.func_201497_a_();
         int ☃xxx = 8;

         for(int ☃xxxx = ☃ - 8; ☃xxxx <= ☃ + 8; ++☃xxxx) {
            if (☃xxxx < ☃ - 1 || ☃xxxx > ☃ + 1) {
               for(int ☃xxxxx = ☃x - 8; ☃xxxxx <= ☃x + 8; ++☃xxxxx) {
                  if (☃xxxxx < ☃x - 1 || ☃xxxxx > ☃x + 1) {
                     ChunkPos ☃xxxxxx = new ChunkPos(☃xxxx, ☃xxxxx);
                     ☃.accept(☃xxxxxx, EMPTY);
                  }
               }
            }
         }

         for(int ☃xxxx = ☃ - 1; ☃xxxx <= ☃ + 1; ++☃xxxx) {
            for(int ☃xxxxx = ☃x - 1; ☃xxxxx <= ☃x + 1; ++☃xxxxx) {
               ChunkPos ☃xxxxxx = new ChunkPos(☃xxxx, ☃xxxxx);
               ☃.accept(☃xxxxxx, ☃xx);
            }
         }
      }
   },
   LIGHTED("lighted", new LightChunkTask(), 1, true, ChunkStatus.Type.PROTOCHUNK),
   MOBS_SPAWNED("mobs_spawned", new SpawnMobsTask(), 0, true, ChunkStatus.Type.PROTOCHUNK),
   FINALIZED("finalized", new FinializeChunkTask(), 0, true, ChunkStatus.Type.PROTOCHUNK),
   FULLCHUNK("fullchunk", new DummyChunkTask(), 0, true, ChunkStatus.Type.LEVELCHUNK),
   POSTPROCESSED("postprocessed", new DummyChunkTask(), 0, true, ChunkStatus.Type.LEVELCHUNK);

   private static final Map<String, ChunkStatus> field_202131_k = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      for(ChunkStatus ☃ : values()) {
         var0.put(☃.func_202125_b(), ☃);
      }
   });
   private final String field_202130_j;
   @Nullable
   private final ChunkTask field_202132_l;
   private final int field_202133_m;
   private final ChunkStatus.Type field_202134_n;
   private final boolean field_207795_p;

   private ChunkStatus(String var3, @Nullable ChunkTask var4, int var5, boolean var6, ChunkStatus.Type var7) {
      this.field_202130_j = ☃;
      this.field_202132_l = ☃;
      this.field_202133_m = ☃;
      this.field_202134_n = ☃;
      this.field_207795_p = ☃;
   }

   public String func_202125_b() {
      return this.field_202130_j;
   }

   public ChunkPrimer func_202126_a(World var1, IChunkGenerator<?> var2, Map<ChunkPos, ChunkPrimer> var3, int var4, int var5) {
      return this.field_202132_l.func_202839_a(this, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_201492_a_(ChunkPos var1, BiConsumer<ChunkPos, ChunkStatus> var2) {
      int ☃ = ☃.field_77276_a;
      int ☃x = ☃.field_77275_b;
      ChunkStatus ☃xx = this.func_201497_a_();

      for(int ☃xxx = ☃ - this.field_202133_m; ☃xxx <= ☃ + this.field_202133_m; ++☃xxx) {
         for(int ☃xxxx = ☃x - this.field_202133_m; ☃xxxx <= ☃x + this.field_202133_m; ++☃xxxx) {
            ☃.accept(new ChunkPos(☃xxx, ☃xxxx), ☃xx);
         }
      }
   }

   public int func_202128_c() {
      return this.field_202133_m;
   }

   public ChunkStatus.Type func_202129_d() {
      return this.field_202134_n;
   }

   @Nullable
   public static ChunkStatus func_202127_a(String var0) {
      return (ChunkStatus)field_202131_k.get(☃);
   }

   @Nullable
   public ChunkStatus func_201497_a_() {
      return this.ordinal() == 0 ? null : values()[this.ordinal() - 1];
   }

   public boolean func_207794_f() {
      return this.field_207795_p;
   }

   public boolean func_209003_a(ChunkStatus var1) {
      return this.ordinal() >= ☃.ordinal();
   }

   public static enum Type {
      PROTOCHUNK,
      LEVELCHUNK;
   }
}
