package net.minecraft.world.chunk;

import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.world.gen.IChunkGenerator;

public interface IChunkProvider extends AutoCloseable {
   @Nullable
   Chunk func_186025_d(int var1, int var2, boolean var3, boolean var4);

   @Nullable
   default IChunk func_201713_d(int var1, int var2, boolean var3) {
      Chunk ☃ = this.func_186025_d(☃, ☃, true, false);
      if (☃ == null && ☃) {
         throw new UnsupportedOperationException("Could not create an empty chunk");
      } else {
         return ☃;
      }
   }

   boolean func_73156_b(BooleanSupplier var1);

   String func_73148_d();

   IChunkGenerator<?> func_201711_g();

   default void close() {
   }
}
