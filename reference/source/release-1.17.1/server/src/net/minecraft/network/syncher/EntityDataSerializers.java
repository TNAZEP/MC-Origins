package net.minecraft.network.syncher;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EntityDataSerializers {
   private static final CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> SERIALIZERS = new CrudeIncrementalIntIdentityHashBiMap<>(16);
   public static final EntityDataSerializer<Byte> BYTE = new EntityDataSerializer<Byte>() {
      public void write(FriendlyByteBuf var1, Byte var2) {
         â˜ƒ.writeByte(â˜ƒ);
      }

      public Byte read(FriendlyByteBuf var1) {
         return â˜ƒ.readByte();
      }

      public Byte copy(Byte var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Integer> INT = new EntityDataSerializer<Integer>() {
      public void write(FriendlyByteBuf var1, Integer var2) {
         â˜ƒ.writeVarInt(â˜ƒ);
      }

      public Integer read(FriendlyByteBuf var1) {
         return â˜ƒ.readVarInt();
      }

      public Integer copy(Integer var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Float> FLOAT = new EntityDataSerializer<Float>() {
      public void write(FriendlyByteBuf var1, Float var2) {
         â˜ƒ.writeFloat(â˜ƒ);
      }

      public Float read(FriendlyByteBuf var1) {
         return â˜ƒ.readFloat();
      }

      public Float copy(Float var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<String> STRING = new EntityDataSerializer<String>() {
      public void write(FriendlyByteBuf var1, String var2) {
         â˜ƒ.writeUtf(â˜ƒ);
      }

      public String read(FriendlyByteBuf var1) {
         return â˜ƒ.readUtf();
      }

      public String copy(String var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Component> COMPONENT = new EntityDataSerializer<Component>() {
      public void write(FriendlyByteBuf var1, Component var2) {
         â˜ƒ.writeComponent(â˜ƒ);
      }

      public Component read(FriendlyByteBuf var1) {
         return â˜ƒ.readComponent();
      }

      public Component copy(Component var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Optional<Component>> OPTIONAL_COMPONENT = new EntityDataSerializer<Optional<Component>>() {
      public void write(FriendlyByteBuf var1, Optional<Component> var2) {
         if (â˜ƒ.isPresent()) {
            â˜ƒ.writeBoolean(true);
            â˜ƒ.writeComponent((Component)â˜ƒ.get());
         } else {
            â˜ƒ.writeBoolean(false);
         }
      }

      public Optional<Component> read(FriendlyByteBuf var1) {
         return â˜ƒ.readBoolean() ? Optional.of(â˜ƒ.readComponent()) : Optional.empty();
      }

      public Optional<Component> copy(Optional<Component> var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<ItemStack> ITEM_STACK = new EntityDataSerializer<ItemStack>() {
      public void write(FriendlyByteBuf var1, ItemStack var2) {
         â˜ƒ.writeItem(â˜ƒ);
      }

      public ItemStack read(FriendlyByteBuf var1) {
         return â˜ƒ.readItem();
      }

      public ItemStack copy(ItemStack var1) {
         return â˜ƒ.copy();
      }
   };
   public static final EntityDataSerializer<Optional<BlockState>> BLOCK_STATE = new EntityDataSerializer<Optional<BlockState>>() {
      public void write(FriendlyByteBuf var1, Optional<BlockState> var2) {
         if (â˜ƒ.isPresent()) {
            â˜ƒ.writeVarInt(Block.getId((BlockState)â˜ƒ.get()));
         } else {
            â˜ƒ.writeVarInt(0);
         }
      }

      public Optional<BlockState> read(FriendlyByteBuf var1) {
         int â˜ƒ = â˜ƒ.readVarInt();
         return â˜ƒ == 0 ? Optional.empty() : Optional.of(Block.stateById(â˜ƒ));
      }

      public Optional<BlockState> copy(Optional<BlockState> var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Boolean> BOOLEAN = new EntityDataSerializer<Boolean>() {
      public void write(FriendlyByteBuf var1, Boolean var2) {
         â˜ƒ.writeBoolean(â˜ƒ);
      }

      public Boolean read(FriendlyByteBuf var1) {
         return â˜ƒ.readBoolean();
      }

      public Boolean copy(Boolean var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<ParticleOptions> PARTICLE = new EntityDataSerializer<ParticleOptions>() {
      public void write(FriendlyByteBuf var1, ParticleOptions var2) {
         â˜ƒ.writeVarInt(Registry.PARTICLE_TYPE.getId(â˜ƒ.getType()));
         â˜ƒ.writeToNetwork(â˜ƒ);
      }

      public ParticleOptions read(FriendlyByteBuf var1) {
         return this.readParticle(â˜ƒ, (ParticleType<ParticleOptions>)Registry.PARTICLE_TYPE.byId(â˜ƒ.readVarInt()));
      }

      private <T extends ParticleOptions> T readParticle(FriendlyByteBuf var1, ParticleType<T> var2) {
         return â˜ƒ.getDeserializer().fromNetwork(â˜ƒ, â˜ƒ);
      }

      public ParticleOptions copy(ParticleOptions var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Rotations> ROTATIONS = new EntityDataSerializer<Rotations>() {
      public void write(FriendlyByteBuf var1, Rotations var2) {
         â˜ƒ.writeFloat(â˜ƒ.getX());
         â˜ƒ.writeFloat(â˜ƒ.getY());
         â˜ƒ.writeFloat(â˜ƒ.getZ());
      }

      public Rotations read(FriendlyByteBuf var1) {
         return new Rotations(â˜ƒ.readFloat(), â˜ƒ.readFloat(), â˜ƒ.readFloat());
      }

      public Rotations copy(Rotations var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<BlockPos> BLOCK_POS = new EntityDataSerializer<BlockPos>() {
      public void write(FriendlyByteBuf var1, BlockPos var2) {
         â˜ƒ.writeBlockPos(â˜ƒ);
      }

      public BlockPos read(FriendlyByteBuf var1) {
         return â˜ƒ.readBlockPos();
      }

      public BlockPos copy(BlockPos var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new EntityDataSerializer<Optional<BlockPos>>() {
      public void write(FriendlyByteBuf var1, Optional<BlockPos> var2) {
         â˜ƒ.writeBoolean(â˜ƒ.isPresent());
         if (â˜ƒ.isPresent()) {
            â˜ƒ.writeBlockPos((BlockPos)â˜ƒ.get());
         }
      }

      public Optional<BlockPos> read(FriendlyByteBuf var1) {
         return !â˜ƒ.readBoolean() ? Optional.empty() : Optional.of(â˜ƒ.readBlockPos());
      }

      public Optional<BlockPos> copy(Optional<BlockPos> var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Direction> DIRECTION = new EntityDataSerializer<Direction>() {
      public void write(FriendlyByteBuf var1, Direction var2) {
         â˜ƒ.writeEnum(â˜ƒ);
      }

      public Direction read(FriendlyByteBuf var1) {
         return â˜ƒ.readEnum(Direction.class);
      }

      public Direction copy(Direction var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Optional<UUID>> OPTIONAL_UUID = new EntityDataSerializer<Optional<UUID>>() {
      public void write(FriendlyByteBuf var1, Optional<UUID> var2) {
         â˜ƒ.writeBoolean(â˜ƒ.isPresent());
         if (â˜ƒ.isPresent()) {
            â˜ƒ.writeUUID((UUID)â˜ƒ.get());
         }
      }

      public Optional<UUID> read(FriendlyByteBuf var1) {
         return !â˜ƒ.readBoolean() ? Optional.empty() : Optional.of(â˜ƒ.readUUID());
      }

      public Optional<UUID> copy(Optional<UUID> var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<CompoundTag> COMPOUND_TAG = new EntityDataSerializer<CompoundTag>() {
      public void write(FriendlyByteBuf var1, CompoundTag var2) {
         â˜ƒ.writeNbt(â˜ƒ);
      }

      public CompoundTag read(FriendlyByteBuf var1) {
         return â˜ƒ.readNbt();
      }

      public CompoundTag copy(CompoundTag var1) {
         return â˜ƒ.copy();
      }
   };
   public static final EntityDataSerializer<VillagerData> VILLAGER_DATA = new EntityDataSerializer<VillagerData>() {
      public void write(FriendlyByteBuf var1, VillagerData var2) {
         â˜ƒ.writeVarInt(Registry.VILLAGER_TYPE.getId(â˜ƒ.getType()));
         â˜ƒ.writeVarInt(Registry.VILLAGER_PROFESSION.getId(â˜ƒ.getProfession()));
         â˜ƒ.writeVarInt(â˜ƒ.getLevel());
      }

      public VillagerData read(FriendlyByteBuf var1) {
         return new VillagerData(Registry.VILLAGER_TYPE.byId(â˜ƒ.readVarInt()), Registry.VILLAGER_PROFESSION.byId(â˜ƒ.readVarInt()), â˜ƒ.readVarInt());
      }

      public VillagerData copy(VillagerData var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<OptionalInt> OPTIONAL_UNSIGNED_INT = new EntityDataSerializer<OptionalInt>() {
      public void write(FriendlyByteBuf var1, OptionalInt var2) {
         â˜ƒ.writeVarInt(â˜ƒ.orElse(-1) + 1);
      }

      public OptionalInt read(FriendlyByteBuf var1) {
         int â˜ƒ = â˜ƒ.readVarInt();
         return â˜ƒ == 0 ? OptionalInt.empty() : OptionalInt.of(â˜ƒ - 1);
      }

      public OptionalInt copy(OptionalInt var1) {
         return â˜ƒ;
      }
   };
   public static final EntityDataSerializer<Pose> POSE = new EntityDataSerializer<Pose>() {
      public void write(FriendlyByteBuf var1, Pose var2) {
         â˜ƒ.writeEnum(â˜ƒ);
      }

      public Pose read(FriendlyByteBuf var1) {
         return â˜ƒ.readEnum(Pose.class);
      }

      public Pose copy(Pose var1) {
         return â˜ƒ;
      }
   };

   public static void registerSerializer(EntityDataSerializer<?> var0) {
      SERIALIZERS.add(â˜ƒ);
   }

   @Nullable
   public static EntityDataSerializer<?> getSerializer(int var0) {
      return SERIALIZERS.byId(â˜ƒ);
   }

   public static int getSerializedId(EntityDataSerializer<?> var0) {
      return SERIALIZERS.getId(â˜ƒ);
   }

   private EntityDataSerializers() {
   }

   static {
      registerSerializer(BYTE);
      registerSerializer(INT);
      registerSerializer(FLOAT);
      registerSerializer(STRING);
      registerSerializer(COMPONENT);
      registerSerializer(OPTIONAL_COMPONENT);
      registerSerializer(ITEM_STACK);
      registerSerializer(BOOLEAN);
      registerSerializer(ROTATIONS);
      registerSerializer(BLOCK_POS);
      registerSerializer(OPTIONAL_BLOCK_POS);
      registerSerializer(DIRECTION);
      registerSerializer(OPTIONAL_UUID);
      registerSerializer(BLOCK_STATE);
      registerSerializer(COMPOUND_TAG);
      registerSerializer(PARTICLE);
      registerSerializer(VILLAGER_DATA);
      registerSerializer(OPTIONAL_UNSIGNED_INT);
      registerSerializer(POSE);
   }
}
