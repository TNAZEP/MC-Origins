package net.minecraft.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class GlobalPos {
   public static final Codec<GlobalPos> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(GlobalPos::dimension), BlockPos.CODEC.fieldOf("pos").forGetter(GlobalPos::pos))
            .apply(var0, GlobalPos::of)
   );
   private final ResourceKey<Level> dimension;
   private final BlockPos pos;

   private GlobalPos(ResourceKey<Level> var1, BlockPos var2) {
      this.dimension = â˜ƒ;
      this.pos = â˜ƒ;
   }

   public static GlobalPos of(ResourceKey<Level> var0, BlockPos var1) {
      return new GlobalPos(â˜ƒ, â˜ƒ);
   }

   public ResourceKey<Level> dimension() {
      return this.dimension;
   }

   public BlockPos pos() {
      return this.pos;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         GlobalPos â˜ƒ = (GlobalPos)â˜ƒ;
         return Objects.equals(this.dimension, â˜ƒ.dimension) && Objects.equals(this.pos, â˜ƒ.pos);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.dimension, this.pos});
   }

   public String toString() {
      return this.dimension + " " + this.pos;
   }
}
