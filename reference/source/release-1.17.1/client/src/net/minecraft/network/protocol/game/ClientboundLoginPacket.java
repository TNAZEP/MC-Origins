package net.minecraft.network.protocol.game;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ClientboundLoginPacket implements Packet<ClientGamePacketListener> {
   private static final int HARDCORE_FLAG = 8;
   private final int playerId;
   private final long seed;
   private final boolean hardcore;
   private final GameType gameType;
   @Nullable
   private final GameType previousGameType;
   private final Set<ResourceKey<Level>> levels;
   private final RegistryAccess.RegistryHolder registryHolder;
   private final DimensionType dimensionType;
   private final ResourceKey<Level> dimension;
   private final int maxPlayers;
   private final int chunkRadius;
   private final boolean reducedDebugInfo;
   private final boolean showDeathScreen;
   private final boolean isDebug;
   private final boolean isFlat;

   public ClientboundLoginPacket(
      int var1,
      GameType var2,
      @Nullable GameType var3,
      long var4,
      boolean var6,
      Set<ResourceKey<Level>> var7,
      RegistryAccess.RegistryHolder var8,
      DimensionType var9,
      ResourceKey<Level> var10,
      int var11,
      int var12,
      boolean var13,
      boolean var14,
      boolean var15,
      boolean var16
   ) {
      this.playerId = â˜ƒ;
      this.levels = â˜ƒ;
      this.registryHolder = â˜ƒ;
      this.dimensionType = â˜ƒ;
      this.dimension = â˜ƒ;
      this.seed = â˜ƒ;
      this.gameType = â˜ƒ;
      this.previousGameType = â˜ƒ;
      this.maxPlayers = â˜ƒ;
      this.hardcore = â˜ƒ;
      this.chunkRadius = â˜ƒ;
      this.reducedDebugInfo = â˜ƒ;
      this.showDeathScreen = â˜ƒ;
      this.isDebug = â˜ƒ;
      this.isFlat = â˜ƒ;
   }

   public ClientboundLoginPacket(FriendlyByteBuf var1) {
      this.playerId = â˜ƒ.readInt();
      this.hardcore = â˜ƒ.readBoolean();
      this.gameType = GameType.byId(â˜ƒ.readByte());
      this.previousGameType = GameType.byNullableId(â˜ƒ.readByte());
      this.levels = â˜ƒ.readCollection(Sets::newHashSetWithExpectedSize, var0 -> ResourceKey.create(Registry.DIMENSION_REGISTRY, var0.readResourceLocation()));
      this.registryHolder = â˜ƒ.readWithCodec(RegistryAccess.RegistryHolder.NETWORK_CODEC);
      this.dimensionType = (DimensionType)((Supplier)â˜ƒ.readWithCodec(DimensionType.CODEC)).get();
      this.dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, â˜ƒ.readResourceLocation());
      this.seed = â˜ƒ.readLong();
      this.maxPlayers = â˜ƒ.readVarInt();
      this.chunkRadius = â˜ƒ.readVarInt();
      this.reducedDebugInfo = â˜ƒ.readBoolean();
      this.showDeathScreen = â˜ƒ.readBoolean();
      this.isDebug = â˜ƒ.readBoolean();
      this.isFlat = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.playerId);
      â˜ƒ.writeBoolean(this.hardcore);
      â˜ƒ.writeByte(this.gameType.getId());
      â˜ƒ.writeByte(GameType.getNullableId(this.previousGameType));
      â˜ƒ.writeCollection(this.levels, (var0, var1x) -> var0.writeResourceLocation(var1x.location()));
      â˜ƒ.writeWithCodec(RegistryAccess.RegistryHolder.NETWORK_CODEC, this.registryHolder);
      â˜ƒ.writeWithCodec(DimensionType.CODEC, (Supplier)() -> this.dimensionType);
      â˜ƒ.writeResourceLocation(this.dimension.location());
      â˜ƒ.writeLong(this.seed);
      â˜ƒ.writeVarInt(this.maxPlayers);
      â˜ƒ.writeVarInt(this.chunkRadius);
      â˜ƒ.writeBoolean(this.reducedDebugInfo);
      â˜ƒ.writeBoolean(this.showDeathScreen);
      â˜ƒ.writeBoolean(this.isDebug);
      â˜ƒ.writeBoolean(this.isFlat);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleLogin(this);
   }

   public int getPlayerId() {
      return this.playerId;
   }

   public long getSeed() {
      return this.seed;
   }

   public boolean isHardcore() {
      return this.hardcore;
   }

   public GameType getGameType() {
      return this.gameType;
   }

   @Nullable
   public GameType getPreviousGameType() {
      return this.previousGameType;
   }

   public Set<ResourceKey<Level>> levels() {
      return this.levels;
   }

   public RegistryAccess registryAccess() {
      return this.registryHolder;
   }

   public DimensionType getDimensionType() {
      return this.dimensionType;
   }

   public ResourceKey<Level> getDimension() {
      return this.dimension;
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public int getChunkRadius() {
      return this.chunkRadius;
   }

   public boolean isReducedDebugInfo() {
      return this.reducedDebugInfo;
   }

   public boolean shouldShowDeathScreen() {
      return this.showDeathScreen;
   }

   public boolean isDebug() {
      return this.isDebug;
   }

   public boolean isFlat() {
      return this.isFlat;
   }
}
