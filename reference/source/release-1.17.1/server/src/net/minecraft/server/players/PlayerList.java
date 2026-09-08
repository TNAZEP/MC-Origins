package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerList {
   public static final File USERBANLIST_FILE = new File("banned-players.json");
   public static final File IPBANLIST_FILE = new File("banned-ips.json");
   public static final File OPLIST_FILE = new File("ops.json");
   public static final File WHITELIST_FILE = new File("whitelist.json");
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SEND_PLAYER_INFO_INTERVAL = 600;
   private static final SimpleDateFormat BAN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer server;
   private final List<ServerPlayer> players = Lists.<ServerPlayer>newArrayList();
   private final Map<UUID, ServerPlayer> playersByUUID = Maps.newHashMap();
   private final UserBanList bans = new UserBanList(USERBANLIST_FILE);
   private final IpBanList ipBans = new IpBanList(IPBANLIST_FILE);
   private final ServerOpList ops = new ServerOpList(OPLIST_FILE);
   private final UserWhiteList whitelist = new UserWhiteList(WHITELIST_FILE);
   private final Map<UUID, ServerStatsCounter> stats = Maps.newHashMap();
   private final Map<UUID, PlayerAdvancements> advancements = Maps.newHashMap();
   private final PlayerDataStorage playerIo;
   private boolean doWhiteList;
   private final RegistryAccess.RegistryHolder registryHolder;
   protected final int maxPlayers;
   private int viewDistance;
   private boolean allowCheatsForAllPlayers;
   private static final boolean ALLOW_LOGOUTIVATOR = false;
   private int sendAllPlayerInfoIn;

   public PlayerList(MinecraftServer var1, RegistryAccess.RegistryHolder var2, PlayerDataStorage var3, int var4) {
      this.server = â˜ƒ;
      this.registryHolder = â˜ƒ;
      this.maxPlayers = â˜ƒ;
      this.playerIo = â˜ƒ;
   }

   public void placeNewPlayer(Connection var1, ServerPlayer var2) {
      GameProfile â˜ƒx = â˜ƒ.getGameProfile();
      GameProfileCache â˜ƒxx = this.server.getProfileCache();
      Optional<GameProfile> â˜ƒxxx = â˜ƒxx.get(â˜ƒx.getId());
      String â˜ƒxxxx = (String)â˜ƒxxx.map(GameProfile::getName).orElse(â˜ƒx.getName());
      â˜ƒxx.add(â˜ƒx);
      CompoundTag â˜ƒxxxxx = this.load(â˜ƒ);
      ResourceKey<Level> â˜ƒxxxxxx = â˜ƒxxxxx != null
         ? (ResourceKey)DimensionType.parseLegacy(new Dynamic<>(NbtOps.INSTANCE, â˜ƒxxxxx.get("Dimension")))
            .resultOrPartial(LOGGER::error)
            .orElse(Level.OVERWORLD)
         : Level.OVERWORLD;
      ServerLevel â˜ƒxxxxxxx = this.server.getLevel(â˜ƒxxxxxx);
      ServerLevel â˜ƒ;
      if (â˜ƒxxxxxxx == null) {
         LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", â˜ƒxxxxxx);
         â˜ƒ = this.server.overworld();
      } else {
         â˜ƒ = â˜ƒxxxxxxx;
      }

      â˜ƒ.setLevel(â˜ƒ);
      String â˜ƒ = "local";
      if (â˜ƒ.getRemoteAddress() != null) {
         â˜ƒ = â˜ƒ.getRemoteAddress().toString();
      }

      LOGGER.info("{}[{}] logged in with entity id {} at ({}, {}, {})", â˜ƒ.getName().getString(), â˜ƒ, â˜ƒ.getId(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      LevelData â˜ƒx = â˜ƒ.getLevelData();
      â˜ƒ.loadGameTypes(â˜ƒxxxxx);
      ServerGamePacketListenerImpl â˜ƒxx = new ServerGamePacketListenerImpl(this.server, â˜ƒ, â˜ƒ);
      GameRules â˜ƒxxx = â˜ƒ.getGameRules();
      boolean â˜ƒxxxx = â˜ƒxxx.getBoolean(GameRules.RULE_DO_IMMEDIATE_RESPAWN);
      boolean â˜ƒxxxxx = â˜ƒxxx.getBoolean(GameRules.RULE_REDUCEDDEBUGINFO);
      â˜ƒxx.send(
         new ClientboundLoginPacket(
            â˜ƒ.getId(),
            â˜ƒ.gameMode.getGameModeForPlayer(),
            â˜ƒ.gameMode.getPreviousGameModeForPlayer(),
            BiomeManager.obfuscateSeed(â˜ƒ.getSeed()),
            â˜ƒx.isHardcore(),
            this.server.levelKeys(),
            this.registryHolder,
            â˜ƒ.dimensionType(),
            â˜ƒ.dimension(),
            this.getMaxPlayers(),
            this.viewDistance,
            â˜ƒxxxxx,
            !â˜ƒxxxx,
            â˜ƒ.isDebug(),
            â˜ƒ.isFlat()
         )
      );
      â˜ƒxx.send(
         new ClientboundCustomPayloadPacket(
            ClientboundCustomPayloadPacket.BRAND, new FriendlyByteBuf(Unpooled.buffer()).writeUtf(this.getServer().getServerModName())
         )
      );
      â˜ƒxx.send(new ClientboundChangeDifficultyPacket(â˜ƒx.getDifficulty(), â˜ƒx.isDifficultyLocked()));
      â˜ƒxx.send(new ClientboundPlayerAbilitiesPacket(â˜ƒ.getAbilities()));
      â˜ƒxx.send(new ClientboundSetCarriedItemPacket(â˜ƒ.getInventory().selected));
      â˜ƒxx.send(new ClientboundUpdateRecipesPacket(this.server.getRecipeManager().getRecipes()));
      â˜ƒxx.send(new ClientboundUpdateTagsPacket(this.server.getTags().serializeToNetwork(this.registryHolder)));
      this.sendPlayerPermissionLevel(â˜ƒ);
      â˜ƒ.getStats().markAllDirty();
      â˜ƒ.getRecipeBook().sendInitialRecipeBook(â˜ƒ);
      this.updateEntireScoreboard(â˜ƒ.getScoreboard(), â˜ƒ);
      this.server.invalidateStatus();
      MutableComponent â˜ƒ;
      if (â˜ƒ.getGameProfile().getName().equalsIgnoreCase(â˜ƒxxxx)) {
         â˜ƒ = new TranslatableComponent("multiplayer.player.joined", â˜ƒ.getDisplayName());
      } else {
         â˜ƒ = new TranslatableComponent("multiplayer.player.joined.renamed", â˜ƒ.getDisplayName(), â˜ƒxxxx);
      }

      this.broadcastMessage(â˜ƒ.withStyle(ChatFormatting.YELLOW), ChatType.SYSTEM, Util.NIL_UUID);
      â˜ƒxx.teleport(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot());
      this.players.add(â˜ƒ);
      this.playersByUUID.put(â˜ƒ.getUUID(), â˜ƒ);
      this.broadcastAll(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, â˜ƒ));

      for(int â˜ƒ = 0; â˜ƒ < this.players.size(); ++â˜ƒ) {
         â˜ƒ.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, (ServerPlayer)this.players.get(â˜ƒ)));
      }

      â˜ƒ.addNewPlayer(â˜ƒ);
      this.server.getCustomBossEvents().onPlayerConnect(â˜ƒ);
      this.sendLevelInfo(â˜ƒ, â˜ƒ);
      if (!this.server.getResourcePack().isEmpty()) {
         â˜ƒ.sendTexturePack(
            this.server.getResourcePack(), this.server.getResourcePackHash(), this.server.isResourcePackRequired(), this.server.getResourcePackPrompt()
         );
      }

      for(MobEffectInstance â˜ƒ : â˜ƒ.getActiveEffects()) {
         â˜ƒxx.send(new ClientboundUpdateMobEffectPacket(â˜ƒ.getId(), â˜ƒ));
      }

      if (â˜ƒxxxxx != null && â˜ƒxxxxx.contains("RootVehicle", 10)) {
         CompoundTag â˜ƒ = â˜ƒxxxxx.getCompound("RootVehicle");
         Entity â˜ƒx = EntityType.loadEntityRecursive(â˜ƒ.getCompound("Entity"), â˜ƒ, var1x -> !â˜ƒ.addWithUUID(var1x) ? null : var1x);
         if (â˜ƒx != null) {
            UUID â˜ƒxx;
            if (â˜ƒ.hasUUID("Attach")) {
               â˜ƒxx = â˜ƒ.getUUID("Attach");
            } else {
               â˜ƒxx = null;
            }

            if (â˜ƒx.getUUID().equals(â˜ƒxx)) {
               â˜ƒ.startRiding(â˜ƒx, true);
            } else {
               for(Entity â˜ƒxx : â˜ƒx.getIndirectPassengers()) {
                  if (â˜ƒxx.getUUID().equals(â˜ƒxx)) {
                     â˜ƒ.startRiding(â˜ƒxx, true);
                     break;
                  }
               }
            }

            if (!â˜ƒ.isPassenger()) {
               LOGGER.warn("Couldn't reattach entity to player");
               â˜ƒx.discard();

               for(Entity â˜ƒxx : â˜ƒx.getIndirectPassengers()) {
                  â˜ƒxx.discard();
               }
            }
         }
      }

      â˜ƒ.initInventoryMenu();
   }

   protected void updateEntireScoreboard(ServerScoreboard var1, ServerPlayer var2) {
      Set<Objective> â˜ƒ = Sets.<Objective>newHashSet();

      for(PlayerTeam â˜ƒx : â˜ƒ.getPlayerTeams()) {
         â˜ƒ.connection.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(â˜ƒx, true));
      }

      for(int â˜ƒx = 0; â˜ƒx < 19; ++â˜ƒx) {
         Objective â˜ƒxx = â˜ƒ.getDisplayObjective(â˜ƒx);
         if (â˜ƒxx != null && !â˜ƒ.contains(â˜ƒxx)) {
            for(Packet<?> â˜ƒxxx : â˜ƒ.getStartTrackingPackets(â˜ƒxx)) {
               â˜ƒ.connection.send(â˜ƒxxx);
            }

            â˜ƒ.add(â˜ƒxx);
         }
      }
   }

   public void setLevel(ServerLevel var1) {
      â˜ƒ.getWorldBorder().addListener(new BorderChangeListener() {
         @Override
         public void onBorderSizeSet(WorldBorder var1, double var2) {
            PlayerList.this.broadcastAll(new ClientboundSetBorderSizePacket(â˜ƒ));
         }

         @Override
         public void onBorderSizeLerping(WorldBorder var1, double var2, double var4, long var6) {
            PlayerList.this.broadcastAll(new ClientboundSetBorderLerpSizePacket(â˜ƒ));
         }

         @Override
         public void onBorderCenterSet(WorldBorder var1, double var2, double var4) {
            PlayerList.this.broadcastAll(new ClientboundSetBorderCenterPacket(â˜ƒ));
         }

         @Override
         public void onBorderSetWarningTime(WorldBorder var1, int var2) {
            PlayerList.this.broadcastAll(new ClientboundSetBorderWarningDelayPacket(â˜ƒ));
         }

         @Override
         public void onBorderSetWarningBlocks(WorldBorder var1, int var2) {
            PlayerList.this.broadcastAll(new ClientboundSetBorderWarningDistancePacket(â˜ƒ));
         }

         @Override
         public void onBorderSetDamagePerBlock(WorldBorder var1, double var2) {
         }

         @Override
         public void onBorderSetDamageSafeZOne(WorldBorder var1, double var2) {
         }
      });
   }

   @Nullable
   public CompoundTag load(ServerPlayer var1) {
      CompoundTag â˜ƒx = this.server.getWorldData().getLoadedPlayerTag();
      CompoundTag â˜ƒ;
      if (â˜ƒ.getName().getString().equals(this.server.getSingleplayerName()) && â˜ƒx != null) {
         â˜ƒ = â˜ƒx;
         â˜ƒ.load(â˜ƒx);
         LOGGER.debug("loading single player");
      } else {
         â˜ƒ = this.playerIo.load(â˜ƒ);
      }

      return â˜ƒ;
   }

   protected void save(ServerPlayer var1) {
      this.playerIo.save(â˜ƒ);
      ServerStatsCounter â˜ƒ = (ServerStatsCounter)this.stats.get(â˜ƒ.getUUID());
      if (â˜ƒ != null) {
         â˜ƒ.save();
      }

      PlayerAdvancements â˜ƒ = (PlayerAdvancements)this.advancements.get(â˜ƒ.getUUID());
      if (â˜ƒ != null) {
         â˜ƒ.save();
      }
   }

   public void remove(ServerPlayer var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      â˜ƒ.awardStat(Stats.LEAVE_GAME);
      this.save(â˜ƒ);
      if (â˜ƒ.isPassenger()) {
         Entity â˜ƒx = â˜ƒ.getRootVehicle();
         if (â˜ƒx.hasExactlyOnePlayerPassenger()) {
            LOGGER.debug("Removing player mount");
            â˜ƒ.stopRiding();
            â˜ƒx.getPassengersAndSelf().forEach(var0 -> var0.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER));
         }
      }

      â˜ƒ.unRide();
      â˜ƒ.removePlayerImmediately(â˜ƒ, Entity.RemovalReason.UNLOADED_WITH_PLAYER);
      â˜ƒ.getAdvancements().stopListening();
      this.players.remove(â˜ƒ);
      this.server.getCustomBossEvents().onPlayerDisconnect(â˜ƒ);
      UUID â˜ƒ = â˜ƒ.getUUID();
      ServerPlayer â˜ƒx = (ServerPlayer)this.playersByUUID.get(â˜ƒ);
      if (â˜ƒx == â˜ƒ) {
         this.playersByUUID.remove(â˜ƒ);
         this.stats.remove(â˜ƒ);
         this.advancements.remove(â˜ƒ);
      }

      this.broadcastAll(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, â˜ƒ));
   }

   @Nullable
   public Component canPlayerLogin(SocketAddress var1, GameProfile var2) {
      if (this.bans.isBanned(â˜ƒ)) {
         UserBanListEntry â˜ƒ = this.bans.get(â˜ƒ);
         MutableComponent â˜ƒx = new TranslatableComponent("multiplayer.disconnect.banned.reason", â˜ƒ.getReason());
         if (â˜ƒ.getExpires() != null) {
            â˜ƒx.append(new TranslatableComponent("multiplayer.disconnect.banned.expiration", BAN_DATE_FORMAT.format(â˜ƒ.getExpires())));
         }

         return â˜ƒx;
      } else if (!this.isWhiteListed(â˜ƒ)) {
         return new TranslatableComponent("multiplayer.disconnect.not_whitelisted");
      } else if (this.ipBans.isBanned(â˜ƒ)) {
         IpBanListEntry â˜ƒ = this.ipBans.get(â˜ƒ);
         MutableComponent â˜ƒx = new TranslatableComponent("multiplayer.disconnect.banned_ip.reason", â˜ƒ.getReason());
         if (â˜ƒ.getExpires() != null) {
            â˜ƒx.append(new TranslatableComponent("multiplayer.disconnect.banned_ip.expiration", BAN_DATE_FORMAT.format(â˜ƒ.getExpires())));
         }

         return â˜ƒx;
      } else {
         return this.players.size() >= this.maxPlayers && !this.canBypassPlayerLimit(â˜ƒ)
            ? new TranslatableComponent("multiplayer.disconnect.server_full")
            : null;
      }
   }

   public ServerPlayer getPlayerForLogin(GameProfile var1) {
      UUID â˜ƒ = Player.createPlayerUUID(â˜ƒ);
      List<ServerPlayer> â˜ƒx = Lists.<ServerPlayer>newArrayList();

      for(int â˜ƒxx = 0; â˜ƒxx < this.players.size(); ++â˜ƒxx) {
         ServerPlayer â˜ƒxxx = (ServerPlayer)this.players.get(â˜ƒxx);
         if (â˜ƒxxx.getUUID().equals(â˜ƒ)) {
            â˜ƒx.add(â˜ƒxxx);
         }
      }

      ServerPlayer â˜ƒxx = (ServerPlayer)this.playersByUUID.get(â˜ƒ.getId());
      if (â˜ƒxx != null && !â˜ƒx.contains(â˜ƒxx)) {
         â˜ƒx.add(â˜ƒxx);
      }

      for(ServerPlayer â˜ƒxx : â˜ƒx) {
         â˜ƒxx.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.duplicate_login"));
      }

      return new ServerPlayer(this.server, this.server.overworld(), â˜ƒ);
   }

   public ServerPlayer respawn(ServerPlayer var1, boolean var2) {
      this.players.remove(â˜ƒ);
      â˜ƒ.getLevel().removePlayerImmediately(â˜ƒ, Entity.RemovalReason.DISCARDED);
      BlockPos â˜ƒx = â˜ƒ.getRespawnPosition();
      float â˜ƒxx = â˜ƒ.getRespawnAngle();
      boolean â˜ƒxxx = â˜ƒ.isRespawnForced();
      ServerLevel â˜ƒxxxx = this.server.getLevel(â˜ƒ.getRespawnDimension());
      Optional<Vec3> â˜ƒ;
      if (â˜ƒxxxx != null && â˜ƒx != null) {
         â˜ƒ = Player.findRespawnPositionAndUseSpawnBlock(â˜ƒxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ);
      } else {
         â˜ƒ = Optional.empty();
      }

      ServerLevel â˜ƒ = â˜ƒxxxx != null && â˜ƒ.isPresent() ? â˜ƒxxxx : this.server.overworld();
      ServerPlayer â˜ƒx = new ServerPlayer(this.server, â˜ƒ, â˜ƒ.getGameProfile());
      â˜ƒx.connection = â˜ƒ.connection;
      â˜ƒx.restoreFrom(â˜ƒ, â˜ƒ);
      â˜ƒx.setId(â˜ƒ.getId());
      â˜ƒx.setMainArm(â˜ƒ.getMainArm());

      for(String â˜ƒxx : â˜ƒ.getTags()) {
         â˜ƒx.addTag(â˜ƒxx);
      }

      boolean â˜ƒxx = false;
      if (â˜ƒ.isPresent()) {
         BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒx);
         boolean â˜ƒxxxxx = â˜ƒxxxx.is(Blocks.RESPAWN_ANCHOR);
         Vec3 â˜ƒxxxxxx = (Vec3)â˜ƒ.get();
         float â˜ƒxxx;
         if (!â˜ƒxxxx.is(BlockTags.BEDS) && !â˜ƒxxxxx) {
            â˜ƒxxx = â˜ƒxx;
         } else {
            Vec3 â˜ƒxxx = Vec3.atBottomCenterOf(â˜ƒx).subtract(â˜ƒxxxxxx).normalize();
            â˜ƒxxx = (float)Mth.wrapDegrees(Mth.atan2(â˜ƒxxx.z, â˜ƒxxx.x) * 180.0F / (float)Math.PI - 90.0);
         }

         â˜ƒx.moveTo(â˜ƒxxxxxx.x, â˜ƒxxxxxx.y, â˜ƒxxxxxx.z, â˜ƒxxx, 0.0F);
         â˜ƒx.setRespawnPosition(â˜ƒ.dimension(), â˜ƒx, â˜ƒxx, â˜ƒxxx, false);
         â˜ƒxx = !â˜ƒ && â˜ƒxxxxx;
      } else if (â˜ƒx != null) {
         â˜ƒx.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0.0F));
      }

      while(!â˜ƒ.noCollision(â˜ƒx) && â˜ƒx.getY() < (double)â˜ƒ.getMaxBuildHeight()) {
         â˜ƒx.setPos(â˜ƒx.getX(), â˜ƒx.getY() + 1.0, â˜ƒx.getZ());
      }

      LevelData â˜ƒxx = â˜ƒx.level.getLevelData();
      â˜ƒx.connection
         .send(
            new ClientboundRespawnPacket(
               â˜ƒx.level.dimensionType(),
               â˜ƒx.level.dimension(),
               BiomeManager.obfuscateSeed(â˜ƒx.getLevel().getSeed()),
               â˜ƒx.gameMode.getGameModeForPlayer(),
               â˜ƒx.gameMode.getPreviousGameModeForPlayer(),
               â˜ƒx.getLevel().isDebug(),
               â˜ƒx.getLevel().isFlat(),
               â˜ƒ
            )
         );
      â˜ƒx.connection.teleport(â˜ƒx.getX(), â˜ƒx.getY(), â˜ƒx.getZ(), â˜ƒx.getYRot(), â˜ƒx.getXRot());
      â˜ƒx.connection.send(new ClientboundSetDefaultSpawnPositionPacket(â˜ƒ.getSharedSpawnPos(), â˜ƒ.getSharedSpawnAngle()));
      â˜ƒx.connection.send(new ClientboundChangeDifficultyPacket(â˜ƒxx.getDifficulty(), â˜ƒxx.isDifficultyLocked()));
      â˜ƒx.connection.send(new ClientboundSetExperiencePacket(â˜ƒx.experienceProgress, â˜ƒx.totalExperience, â˜ƒx.experienceLevel));
      this.sendLevelInfo(â˜ƒx, â˜ƒ);
      this.sendPlayerPermissionLevel(â˜ƒx);
      â˜ƒ.addRespawnedPlayer(â˜ƒx);
      this.players.add(â˜ƒx);
      this.playersByUUID.put(â˜ƒx.getUUID(), â˜ƒx);
      â˜ƒx.initInventoryMenu();
      â˜ƒx.setHealth(â˜ƒx.getHealth());
      if (â˜ƒxx) {
         â˜ƒx.connection
            .send(
               new ClientboundSoundPacket(
                  SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.BLOCKS, (double)â˜ƒx.getX(), (double)â˜ƒx.getY(), (double)â˜ƒx.getZ(), 1.0F, 1.0F
               )
            );
      }

      return â˜ƒx;
   }

   public void sendPlayerPermissionLevel(ServerPlayer var1) {
      GameProfile â˜ƒ = â˜ƒ.getGameProfile();
      int â˜ƒx = this.server.getProfilePermissions(â˜ƒ);
      this.sendPlayerPermissionLevel(â˜ƒ, â˜ƒx);
   }

   public void tick() {
      if (++this.sendAllPlayerInfoIn > 600) {
         this.broadcastAll(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_LATENCY, this.players));
         this.sendAllPlayerInfoIn = 0;
      }
   }

   public void broadcastAll(Packet<?> var1) {
      for(ServerPlayer â˜ƒ : this.players) {
         â˜ƒ.connection.send(â˜ƒ);
      }
   }

   public void broadcastAll(Packet<?> var1, ResourceKey<Level> var2) {
      for(ServerPlayer â˜ƒ : this.players) {
         if (â˜ƒ.level.dimension() == â˜ƒ) {
            â˜ƒ.connection.send(â˜ƒ);
         }
      }
   }

   public void broadcastToTeam(Player var1, Component var2) {
      Team â˜ƒ = â˜ƒ.getTeam();
      if (â˜ƒ != null) {
         for(String â˜ƒx : â˜ƒ.getPlayers()) {
            ServerPlayer â˜ƒxx = this.getPlayerByName(â˜ƒx);
            if (â˜ƒxx != null && â˜ƒxx != â˜ƒ) {
               â˜ƒxx.sendMessage(â˜ƒ, â˜ƒ.getUUID());
            }
         }
      }
   }

   public void broadcastToAllExceptTeam(Player var1, Component var2) {
      Team â˜ƒ = â˜ƒ.getTeam();
      if (â˜ƒ == null) {
         this.broadcastMessage(â˜ƒ, ChatType.SYSTEM, â˜ƒ.getUUID());
      } else {
         for(int â˜ƒ = 0; â˜ƒ < this.players.size(); ++â˜ƒ) {
            ServerPlayer â˜ƒx = (ServerPlayer)this.players.get(â˜ƒ);
            if (â˜ƒx.getTeam() != â˜ƒ) {
               â˜ƒx.sendMessage(â˜ƒ, â˜ƒ.getUUID());
            }
         }
      }
   }

   public String[] getPlayerNamesArray() {
      String[] â˜ƒ = new String[this.players.size()];

      for(int â˜ƒx = 0; â˜ƒx < this.players.size(); ++â˜ƒx) {
         â˜ƒ[â˜ƒx] = ((ServerPlayer)this.players.get(â˜ƒx)).getGameProfile().getName();
      }

      return â˜ƒ;
   }

   public UserBanList getBans() {
      return this.bans;
   }

   public IpBanList getIpBans() {
      return this.ipBans;
   }

   public void op(GameProfile var1) {
      this.ops.add(new ServerOpListEntry(â˜ƒ, this.server.getOperatorUserPermissionLevel(), this.ops.canBypassPlayerLimit(â˜ƒ)));
      ServerPlayer â˜ƒ = this.getPlayer(â˜ƒ.getId());
      if (â˜ƒ != null) {
         this.sendPlayerPermissionLevel(â˜ƒ);
      }
   }

   public void deop(GameProfile var1) {
      this.ops.remove(â˜ƒ);
      ServerPlayer â˜ƒ = this.getPlayer(â˜ƒ.getId());
      if (â˜ƒ != null) {
         this.sendPlayerPermissionLevel(â˜ƒ);
      }
   }

   private void sendPlayerPermissionLevel(ServerPlayer var1, int var2) {
      if (â˜ƒ.connection != null) {
         byte â˜ƒ;
         if (â˜ƒ <= 0) {
            â˜ƒ = 24;
         } else if (â˜ƒ >= 4) {
            â˜ƒ = 28;
         } else {
            â˜ƒ = (byte)(24 + â˜ƒ);
         }

         â˜ƒ.connection.send(new ClientboundEntityEventPacket(â˜ƒ, â˜ƒ));
      }

      this.server.getCommands().sendCommands(â˜ƒ);
   }

   public boolean isWhiteListed(GameProfile var1) {
      return !this.doWhiteList || this.ops.contains(â˜ƒ) || this.whitelist.contains(â˜ƒ);
   }

   public boolean isOp(GameProfile var1) {
      return this.ops.contains(â˜ƒ) || this.server.isSingleplayerOwner(â˜ƒ) && this.server.getWorldData().getAllowCommands() || this.allowCheatsForAllPlayers;
   }

   @Nullable
   public ServerPlayer getPlayerByName(String var1) {
      for(ServerPlayer â˜ƒ : this.players) {
         if (â˜ƒ.getGameProfile().getName().equalsIgnoreCase(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return null;
   }

   public void broadcast(@Nullable Player var1, double var2, double var4, double var6, double var8, ResourceKey<Level> var10, Packet<?> var11) {
      for(int â˜ƒ = 0; â˜ƒ < this.players.size(); ++â˜ƒ) {
         ServerPlayer â˜ƒx = (ServerPlayer)this.players.get(â˜ƒ);
         if (â˜ƒx != â˜ƒ && â˜ƒx.level.dimension() == â˜ƒ) {
            double â˜ƒxx = â˜ƒ - â˜ƒx.getX();
            double â˜ƒxxx = â˜ƒ - â˜ƒx.getY();
            double â˜ƒxxxx = â˜ƒ - â˜ƒx.getZ();
            if (â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx < â˜ƒ * â˜ƒ) {
               â˜ƒx.connection.send(â˜ƒ);
            }
         }
      }
   }

   public void saveAll() {
      for(int â˜ƒ = 0; â˜ƒ < this.players.size(); ++â˜ƒ) {
         this.save((ServerPlayer)this.players.get(â˜ƒ));
      }
   }

   public UserWhiteList getWhiteList() {
      return this.whitelist;
   }

   public String[] getWhiteListNames() {
      return this.whitelist.getUserList();
   }

   public ServerOpList getOps() {
      return this.ops;
   }

   public String[] getOpNames() {
      return this.ops.getUserList();
   }

   public void reloadWhiteList() {
   }

   public void sendLevelInfo(ServerPlayer var1, ServerLevel var2) {
      WorldBorder â˜ƒ = this.server.overworld().getWorldBorder();
      â˜ƒ.connection.send(new ClientboundInitializeBorderPacket(â˜ƒ));
      â˜ƒ.connection.send(new ClientboundSetTimePacket(â˜ƒ.getGameTime(), â˜ƒ.getDayTime(), â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
      â˜ƒ.connection.send(new ClientboundSetDefaultSpawnPositionPacket(â˜ƒ.getSharedSpawnPos(), â˜ƒ.getSharedSpawnAngle()));
      if (â˜ƒ.isRaining()) {
         â˜ƒ.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0F));
         â˜ƒ.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, â˜ƒ.getRainLevel(1.0F)));
         â˜ƒ.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, â˜ƒ.getThunderLevel(1.0F)));
      }
   }

   public void sendAllPlayerInfo(ServerPlayer var1) {
      â˜ƒ.inventoryMenu.sendAllDataToRemote();
      â˜ƒ.resetSentInfo();
      â˜ƒ.connection.send(new ClientboundSetCarriedItemPacket(â˜ƒ.getInventory().selected));
   }

   public int getPlayerCount() {
      return this.players.size();
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public boolean isUsingWhitelist() {
      return this.doWhiteList;
   }

   public void setUsingWhiteList(boolean var1) {
      this.doWhiteList = â˜ƒ;
   }

   public List<ServerPlayer> getPlayersWithAddress(String var1) {
      List<ServerPlayer> â˜ƒ = Lists.<ServerPlayer>newArrayList();

      for(ServerPlayer â˜ƒx : this.players) {
         if (â˜ƒx.getIpAddress().equals(â˜ƒ)) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   public int getViewDistance() {
      return this.viewDistance;
   }

   public MinecraftServer getServer() {
      return this.server;
   }

   public CompoundTag getSingleplayerData() {
      return null;
   }

   public void setAllowCheatsForAllPlayers(boolean var1) {
      this.allowCheatsForAllPlayers = â˜ƒ;
   }

   public void removeAll() {
      for(int â˜ƒ = 0; â˜ƒ < this.players.size(); ++â˜ƒ) {
         ((ServerPlayer)this.players.get(â˜ƒ)).connection.disconnect(new TranslatableComponent("multiplayer.disconnect.server_shutdown"));
      }
   }

   public void broadcastMessage(Component var1, ChatType var2, UUID var3) {
      this.server.sendMessage(â˜ƒ, â˜ƒ);

      for(ServerPlayer â˜ƒ : this.players) {
         â˜ƒ.sendMessage(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void broadcastMessage(Component var1, Function<ServerPlayer, Component> var2, ChatType var3, UUID var4) {
      this.server.sendMessage(â˜ƒ, â˜ƒ);

      for(ServerPlayer â˜ƒ : this.players) {
         Component â˜ƒx = (Component)â˜ƒ.apply(â˜ƒ);
         if (â˜ƒx != null) {
            â˜ƒ.sendMessage(â˜ƒx, â˜ƒ, â˜ƒ);
         }
      }
   }

   public ServerStatsCounter getPlayerStats(Player var1) {
      UUID â˜ƒ = â˜ƒ.getUUID();
      ServerStatsCounter â˜ƒx = â˜ƒ == null ? null : (ServerStatsCounter)this.stats.get(â˜ƒ);
      if (â˜ƒx == null) {
         File â˜ƒxx = this.server.getWorldPath(LevelResource.PLAYER_STATS_DIR).toFile();
         File â˜ƒxxx = new File(â˜ƒxx, â˜ƒ + ".json");
         if (!â˜ƒxxx.exists()) {
            File â˜ƒxxxx = new File(â˜ƒxx, â˜ƒ.getName().getString() + ".json");
            Path â˜ƒxxxxx = â˜ƒxxxx.toPath();
            if (FileUtil.isPathNormalized(â˜ƒxxxxx) && FileUtil.isPathPortable(â˜ƒxxxxx) && â˜ƒxxxxx.startsWith(â˜ƒxx.getPath()) && â˜ƒxxxx.isFile()) {
               â˜ƒxxxx.renameTo(â˜ƒxxx);
            }
         }

         â˜ƒx = new ServerStatsCounter(this.server, â˜ƒxxx);
         this.stats.put(â˜ƒ, â˜ƒx);
      }

      return â˜ƒx;
   }

   public PlayerAdvancements getPlayerAdvancements(ServerPlayer var1) {
      UUID â˜ƒ = â˜ƒ.getUUID();
      PlayerAdvancements â˜ƒx = (PlayerAdvancements)this.advancements.get(â˜ƒ);
      if (â˜ƒx == null) {
         File â˜ƒxx = this.server.getWorldPath(LevelResource.PLAYER_ADVANCEMENTS_DIR).toFile();
         File â˜ƒxxx = new File(â˜ƒxx, â˜ƒ + ".json");
         â˜ƒx = new PlayerAdvancements(this.server.getFixerUpper(), this, this.server.getAdvancements(), â˜ƒxxx, â˜ƒ);
         this.advancements.put(â˜ƒ, â˜ƒx);
      }

      â˜ƒx.setPlayer(â˜ƒ);
      return â˜ƒx;
   }

   public void setViewDistance(int var1) {
      this.viewDistance = â˜ƒ;
      this.broadcastAll(new ClientboundSetChunkCacheRadiusPacket(â˜ƒ));

      for(ServerLevel â˜ƒ : this.server.getAllLevels()) {
         if (â˜ƒ != null) {
            â˜ƒ.getChunkSource().setViewDistance(â˜ƒ);
         }
      }
   }

   public List<ServerPlayer> getPlayers() {
      return this.players;
   }

   @Nullable
   public ServerPlayer getPlayer(UUID var1) {
      return (ServerPlayer)this.playersByUUID.get(â˜ƒ);
   }

   public boolean canBypassPlayerLimit(GameProfile var1) {
      return false;
   }

   public void reloadResources() {
      for(PlayerAdvancements â˜ƒ : this.advancements.values()) {
         â˜ƒ.reload(this.server.getAdvancements());
      }

      this.broadcastAll(new ClientboundUpdateTagsPacket(this.server.getTags().serializeToNetwork(this.registryHolder)));
      ClientboundUpdateRecipesPacket â˜ƒ = new ClientboundUpdateRecipesPacket(this.server.getRecipeManager().getRecipes());

      for(ServerPlayer â˜ƒx : this.players) {
         â˜ƒx.connection.send(â˜ƒ);
         â˜ƒx.getRecipeBook().sendInitialRecipeBook(â˜ƒx);
      }
   }

   public boolean isAllowCheatsForAllPlayers() {
      return this.allowCheatsForAllPlayers;
   }
}
