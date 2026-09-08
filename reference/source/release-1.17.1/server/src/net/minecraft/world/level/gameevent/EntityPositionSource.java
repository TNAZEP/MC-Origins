package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EntityPositionSource implements PositionSource {
   public static final Codec<EntityPositionSource> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.INT.fieldOf("source_entity_id").forGetter(var0x -> var0x.sourceEntityId)).apply(var0, EntityPositionSource::new)
   );
   final int sourceEntityId;
   private Optional<Entity> sourceEntity = Optional.empty();

   public EntityPositionSource(int var1) {
      this.sourceEntityId = â˜ƒ;
   }

   @Override
   public Optional<BlockPos> getPosition(Level var1) {
      if (!this.sourceEntity.isPresent()) {
         this.sourceEntity = Optional.ofNullable(â˜ƒ.getEntity(this.sourceEntityId));
      }

      return this.sourceEntity.map(Entity::blockPosition);
   }

   @Override
   public PositionSourceType<?> getType() {
      return PositionSourceType.ENTITY;
   }

   public static class Type implements PositionSourceType<EntityPositionSource> {
      public EntityPositionSource read(FriendlyByteBuf var1) {
         return new EntityPositionSource(â˜ƒ.readVarInt());
      }

      public void write(FriendlyByteBuf var1, EntityPositionSource var2) {
         â˜ƒ.writeVarInt(â˜ƒ.sourceEntityId);
      }

      @Override
      public Codec<EntityPositionSource> codec() {
         return EntityPositionSource.CODEC;
      }
   }
}
