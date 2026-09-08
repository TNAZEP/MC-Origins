package net.minecraft.world.level.gameevent.vibrations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;

public class VibrationPath {
   public static final Codec<VibrationPath> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockPos.CODEC.fieldOf("origin").forGetter(var0x -> var0x.origin),
               PositionSource.CODEC.fieldOf("destination").forGetter(var0x -> var0x.destination),
               Codec.INT.fieldOf("arrival_in_ticks").forGetter(var0x -> var0x.arrivalInTicks)
            )
            .apply(var0, VibrationPath::new)
   );
   private final BlockPos origin;
   private final PositionSource destination;
   private final int arrivalInTicks;

   public VibrationPath(BlockPos var1, PositionSource var2, int var3) {
      this.origin = â˜ƒ;
      this.destination = â˜ƒ;
      this.arrivalInTicks = â˜ƒ;
   }

   public int getArrivalInTicks() {
      return this.arrivalInTicks;
   }

   public BlockPos getOrigin() {
      return this.origin;
   }

   public PositionSource getDestination() {
      return this.destination;
   }

   public static VibrationPath read(FriendlyByteBuf var0) {
      BlockPos â˜ƒ = â˜ƒ.readBlockPos();
      PositionSource â˜ƒx = PositionSourceType.fromNetwork(â˜ƒ);
      int â˜ƒxx = â˜ƒ.readVarInt();
      return new VibrationPath(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public static void write(FriendlyByteBuf var0, VibrationPath var1) {
      â˜ƒ.writeBlockPos(â˜ƒ.origin);
      PositionSourceType.toNetwork(â˜ƒ.destination, â˜ƒ);
      â˜ƒ.writeVarInt(â˜ƒ.arrivalInTicks);
   }
}
