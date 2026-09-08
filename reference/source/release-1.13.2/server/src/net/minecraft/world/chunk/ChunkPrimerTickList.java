package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ITickList;
import net.minecraft.world.TickPriority;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public class ChunkPrimerTickList<T> implements ITickList<T> {
   protected final Predicate<T> field_205382_a;
   protected final Function<T, ResourceLocation> field_205383_b;
   protected final Function<ResourceLocation, T> field_205384_c;
   private final ChunkPos field_205385_d;
   private final ShortList[] field_205386_e = new ShortList[16];

   public ChunkPrimerTickList(Predicate<T> var1, Function<T, ResourceLocation> var2, Function<ResourceLocation, T> var3, ChunkPos var4) {
      this.field_205382_a = ☃;
      this.field_205383_b = ☃;
      this.field_205384_c = ☃;
      this.field_205385_d = ☃;
   }

   public NBTTagList func_205379_a() {
      return AnvilChunkLoader.func_202163_a(this.field_205386_e);
   }

   public void func_205380_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagList ☃x = ☃.func_202169_e(☃);

         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            ChunkPrimer.func_205330_a(this.field_205386_e, ☃).add(☃x.func_202170_f(☃xx));
         }
      }
   }

   public void func_205381_a(ITickList<T> var1, Function<BlockPos, T> var2) {
      for(int ☃ = 0; ☃ < this.field_205386_e.length; ++☃) {
         if (this.field_205386_e[☃] != null) {
            for(Short ☃x : this.field_205386_e[☃]) {
               BlockPos ☃xx = ChunkPrimer.func_201635_a(☃x, ☃, this.field_205385_d);
               ☃.func_205360_a(☃xx, (T)☃.apply(☃xx), 0);
            }

            this.field_205386_e[☃].clear();
         }
      }
   }

   @Override
   public boolean func_205359_a(BlockPos var1, T var2) {
      return false;
   }

   @Override
   public void func_205362_a(BlockPos var1, T var2, int var3, TickPriority var4) {
      ChunkPrimer.func_205330_a(this.field_205386_e, ☃.func_177956_o() >> 4).add(ChunkPrimer.func_201651_i(☃));
   }

   @Override
   public boolean func_205361_b(BlockPos var1, T var2) {
      return false;
   }
}
