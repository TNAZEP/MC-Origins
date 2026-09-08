package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public class BlockPositionSource implements PositionSource {
   public static final Codec<BlockPositionSource> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(BlockPos.CODEC.fieldOf("pos").xmap(Optional::of, Optional::get).forGetter(var0x -> var0x.pos)).apply(var0, BlockPositionSource::new)
   );
   final Optional<BlockPos> pos;

   public BlockPositionSource(BlockPos var1) {
      this(Optional.of(â˜ƒ));
   }

   public BlockPositionSource(Optional<BlockPos> var1) {
      this.pos = â˜ƒ;
   }

   @Override
   public Optional<BlockPos> getPosition(Level var1) {
      return this.pos;
   }

   @Override
   public PositionSourceType<?> getType() {
      return PositionSourceType.BLOCK;
   }

   public static class Type implements PositionSourceType<BlockPositionSource> {
      public BlockPositionSource read(FriendlyByteBuf var1) {
         return new BlockPositionSource(Optional.of(â˜ƒ.readBlockPos()));
      }

      public void write(FriendlyByteBuf var1, BlockPositionSource var2) {
         â˜ƒ.pos.ifPresent(â˜ƒ::writeBlockPos);
      }

      @Override
      public Codec<BlockPositionSource> codec() {
         return BlockPositionSource.CODEC;
      }
   }
}
