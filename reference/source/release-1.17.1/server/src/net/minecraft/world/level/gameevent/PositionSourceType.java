package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface PositionSourceType<T extends PositionSource> {
   PositionSourceType<BlockPositionSource> BLOCK = register("block", new BlockPositionSource.Type());
   PositionSourceType<EntityPositionSource> ENTITY = register("entity", new EntityPositionSource.Type());

   T read(FriendlyByteBuf var1);

   void write(FriendlyByteBuf var1, T var2);

   Codec<T> codec();

   static <S extends PositionSourceType<T>, T extends PositionSource> S register(String var0, S var1) {
      return Registry.register(Registry.POSITION_SOURCE_TYPE, â˜ƒ, â˜ƒ);
   }

   static PositionSource fromNetwork(FriendlyByteBuf var0) {
      ResourceLocation â˜ƒ = â˜ƒ.readResourceLocation();
      return ((PositionSourceType)Registry.POSITION_SOURCE_TYPE
            .getOptional(â˜ƒ)
            .orElseThrow(() -> new IllegalArgumentException("Unknown position source type " + â˜ƒ)))
         .read(â˜ƒ);
   }

   static <T extends PositionSource> void toNetwork(T var0, FriendlyByteBuf var1) {
      â˜ƒ.writeResourceLocation(Registry.POSITION_SOURCE_TYPE.getKey(â˜ƒ.getType()));
      â˜ƒ.getType().write(â˜ƒ, â˜ƒ);
   }
}
