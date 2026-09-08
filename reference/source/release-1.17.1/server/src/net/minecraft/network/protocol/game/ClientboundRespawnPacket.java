package net.minecraft.network.protocol.game;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ClientboundRespawnPacket implements Packet<ClientGamePacketListener> {
   private final DimensionType dimensionType;
   private final ResourceKey<Level> dimension;
   private final long seed;
   private final GameType playerGameType;
   @Nullable
   private final GameType previousPlayerGameType;
   private final boolean isDebug;
   private final boolean isFlat;
   private final boolean keepAllPlayerData;

   public ClientboundRespawnPacket(
      DimensionType var1, ResourceKey<Level> var2, long var3, GameType var5, @Nullable GameType var6, boolean var7, boolean var8, boolean var9
   ) {
      this.dimensionType = â˜ƒ;
      this.dimension = â˜ƒ;
      this.seed = â˜ƒ;
      this.playerGameType = â˜ƒ;
      this.previousPlayerGameType = â˜ƒ;
      this.isDebug = â˜ƒ;
      this.isFlat = â˜ƒ;
      this.keepAllPlayerData = â˜ƒ;
   }

   public ClientboundRespawnPacket(FriendlyByteBuf var1) {
      this.dimensionType = (DimensionType)((Supplier)â˜ƒ.readWithCodec(DimensionType.CODEC)).get();
      this.dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, â˜ƒ.readResourceLocation());
      this.seed = â˜ƒ.readLong();
      this.playerGameType = GameType.byId(â˜ƒ.readUnsignedByte());
      this.previousPlayerGameType = GameType.byNullableId(â˜ƒ.readByte());
      this.isDebug = â˜ƒ.readBoolean();
      this.isFlat = â˜ƒ.readBoolean();
      this.keepAllPlayerData = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeWithCodec(DimensionType.CODEC, (Supplier)() -> this.dimensionType);
      â˜ƒ.writeResourceLocation(this.dimension.location());
      â˜ƒ.writeLong(this.seed);
      â˜ƒ.writeByte(this.playerGameType.getId());
      â˜ƒ.writeByte(GameType.getNullableId(this.previousPlayerGameType));
      â˜ƒ.writeBoolean(this.isDebug);
      â˜ƒ.writeBoolean(this.isFlat);
      â˜ƒ.writeBoolean(this.keepAllPlayerData);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleRespawn(this);
   }

   public DimensionType getDimensionType() {
      return this.dimensionType;
   }

   public ResourceKey<Level> getDimension() {
      return this.dimension;
   }

   public long getSeed() {
      return this.seed;
   }

   public GameType getPlayerGameType() {
      return this.playerGameType;
   }

   @Nullable
   public GameType getPreviousPlayerGameType() {
      return this.previousPlayerGameType;
   }

   public boolean isDebug() {
      return this.isDebug;
   }

   public boolean isFlat() {
      return this.isFlat;
   }

   public boolean shouldKeepAllPlayerData() {
      return this.keepAllPlayerData;
   }
}
