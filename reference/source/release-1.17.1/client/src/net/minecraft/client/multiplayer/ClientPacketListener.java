package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.DebugQueryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.DemoIntroScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.achievement.StatsUpdateListener;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.debug.BeeDebugRenderer;
import net.minecraft.client.renderer.debug.BrainDebugRenderer;
import net.minecraft.client.renderer.debug.GoalSelectorDebugRenderer;
import net.minecraft.client.renderer.debug.NeighborsUpdateRenderer;
import net.minecraft.client.renderer.debug.WorldGenAttemptRenderer;
import net.minecraft.client.resources.sounds.BeeAggressiveSoundInstance;
import net.minecraft.client.resources.sounds.BeeFlyingSoundInstance;
import net.minecraft.client.resources.sounds.BeeSoundInstance;
import net.minecraft.client.resources.sounds.GuardianAttackSoundInstance;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAddVibrationSignalPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockBreakAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.StaticTags;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientPacketListener implements ClientGamePacketListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Component GENERIC_DISCONNECT_MESSAGE = new TranslatableComponent("disconnect.lost");
   private final Connection connection;
   private final GameProfile localGameProfile;
   private final Screen callbackScreen;
   private final Minecraft minecraft;
   private ClientLevel level;
   private ClientLevel.ClientLevelData levelData;
   private boolean started;
   private final Map<UUID, PlayerInfo> playerInfoMap = Maps.newHashMap();
   private final ClientAdvancements advancements;
   private final ClientSuggestionProvider suggestionsProvider;
   private TagContainer tags = TagContainer.EMPTY;
   private final DebugQueryHandler debugQueryHandler = new DebugQueryHandler(this);
   private int serverChunkRadius = 3;
   private final Random random = new Random();
   private CommandDispatcher<SharedSuggestionProvider> commands = new CommandDispatcher<>();
   private final RecipeManager recipeManager = new RecipeManager();
   private final UUID id = UUID.randomUUID();
   private Set<ResourceKey<Level>> levels;
   private RegistryAccess registryAccess = RegistryAccess.builtin();

   public ClientPacketListener(Minecraft var1, Screen var2, Connection var3, GameProfile var4) {
      this.minecraft = â˜ƒ;
      this.callbackScreen = â˜ƒ;
      this.connection = â˜ƒ;
      this.localGameProfile = â˜ƒ;
      this.advancements = new ClientAdvancements(â˜ƒ);
      this.suggestionsProvider = new ClientSuggestionProvider(this, â˜ƒ);
   }

   public ClientSuggestionProvider getSuggestionsProvider() {
      return this.suggestionsProvider;
   }

   public void cleanup() {
      this.level = null;
   }

   public RecipeManager getRecipeManager() {
      return this.recipeManager;
   }

   @Override
   public void handleLogin(ClientboundLoginPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gameMode = new MultiPlayerGameMode(this.minecraft, this);
      if (!this.connection.isMemoryConnection()) {
         StaticTags.resetAllToEmpty();
      }

      List<ResourceKey<Level>> â˜ƒ = Lists.<ResourceKey<Level>>newArrayList(â˜ƒ.levels());
      Collections.shuffle(â˜ƒ);
      this.levels = Sets.<ResourceKey<Level>>newLinkedHashSet(â˜ƒ);
      this.registryAccess = â˜ƒ.registryAccess();
      ResourceKey<Level> â˜ƒx = â˜ƒ.getDimension();
      DimensionType â˜ƒxx = â˜ƒ.getDimensionType();
      this.serverChunkRadius = â˜ƒ.getChunkRadius();
      boolean â˜ƒxxx = â˜ƒ.isDebug();
      boolean â˜ƒxxxx = â˜ƒ.isFlat();
      ClientLevel.ClientLevelData â˜ƒxxxxx = new ClientLevel.ClientLevelData(Difficulty.NORMAL, â˜ƒ.isHardcore(), â˜ƒxxxx);
      this.levelData = â˜ƒxxxxx;
      this.level = new ClientLevel(
         this, â˜ƒxxxxx, â˜ƒx, â˜ƒxx, this.serverChunkRadius, this.minecraft::getProfiler, this.minecraft.levelRenderer, â˜ƒxxx, â˜ƒ.getSeed()
      );
      this.minecraft.setLevel(this.level);
      if (this.minecraft.player == null) {
         this.minecraft.player = this.minecraft.gameMode.createPlayer(this.level, new StatsCounter(), new ClientRecipeBook());
         this.minecraft.player.setYRot(-180.0F);
         if (this.minecraft.getSingleplayerServer() != null) {
            this.minecraft.getSingleplayerServer().setUUID(this.minecraft.player.getUUID());
         }
      }

      this.minecraft.debugRenderer.clear();
      this.minecraft.player.resetPos();
      int â˜ƒ = â˜ƒ.getPlayerId();
      this.minecraft.player.setId(â˜ƒ);
      this.level.addPlayer(â˜ƒ, this.minecraft.player);
      this.minecraft.player.input = new KeyboardInput(this.minecraft.options);
      this.minecraft.gameMode.adjustPlayer(this.minecraft.player);
      this.minecraft.cameraEntity = this.minecraft.player;
      this.minecraft.setScreen(new ReceivingLevelScreen());
      this.minecraft.player.setReducedDebugInfo(â˜ƒ.isReducedDebugInfo());
      this.minecraft.player.setShowDeathScreen(â˜ƒ.shouldShowDeathScreen());
      this.minecraft.gameMode.setLocalMode(â˜ƒ.getGameType(), â˜ƒ.getPreviousGameType());
      this.minecraft.options.broadcastOptions();
      this.connection
         .send(
            new ServerboundCustomPayloadPacket(
               ServerboundCustomPayloadPacket.BRAND, new FriendlyByteBuf(Unpooled.buffer()).writeUtf(ClientBrandRetriever.getClientModName())
            )
         );
      this.minecraft.getGame().onStartGameSession();
   }

   @Override
   public void handleAddEntity(ClientboundAddEntityPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      EntityType<?> â˜ƒ = â˜ƒ.getType();
      Entity â˜ƒx = â˜ƒ.create(this.level);
      if (â˜ƒx != null) {
         â˜ƒx.recreateFromPacket(â˜ƒ);
         int â˜ƒxx = â˜ƒ.getId();
         this.level.putNonPlayerEntity(â˜ƒxx, â˜ƒx);
         if (â˜ƒx instanceof AbstractMinecart) {
            this.minecraft.getSoundManager().play(new MinecartSoundInstance((AbstractMinecart)â˜ƒx));
         }
      }
   }

   @Override
   public void handleAddExperienceOrb(ClientboundAddExperienceOrbPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      double â˜ƒ = â˜ƒ.getX();
      double â˜ƒx = â˜ƒ.getY();
      double â˜ƒxx = â˜ƒ.getZ();
      Entity â˜ƒxxx = new ExperienceOrb(this.level, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ.getValue());
      â˜ƒxxx.setPacketCoordinates(â˜ƒ, â˜ƒx, â˜ƒxx);
      â˜ƒxxx.setYRot(0.0F);
      â˜ƒxxx.setXRot(0.0F);
      â˜ƒxxx.setId(â˜ƒ.getId());
      this.level.putNonPlayerEntity(â˜ƒ.getId(), â˜ƒxxx);
   }

   @Override
   public void handleAddVibrationSignal(ClientboundAddVibrationSignalPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      VibrationPath â˜ƒ = â˜ƒ.getVibrationPath();
      BlockPos â˜ƒx = â˜ƒ.getOrigin();
      this.level
         .addAlwaysVisibleParticle(
            new VibrationParticleOption(â˜ƒ), true, (double)â˜ƒx.getX() + 0.5, (double)â˜ƒx.getY() + 0.5, (double)â˜ƒx.getZ() + 0.5, 0.0, 0.0, 0.0
         );
   }

   @Override
   public void handleAddPainting(ClientboundAddPaintingPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Painting â˜ƒ = new Painting(this.level, â˜ƒ.getPos(), â˜ƒ.getDirection(), â˜ƒ.getMotive());
      â˜ƒ.setId(â˜ƒ.getId());
      â˜ƒ.setUUID(â˜ƒ.getUUID());
      this.level.putNonPlayerEntity(â˜ƒ.getId(), â˜ƒ);
   }

   @Override
   public void handleSetEntityMotion(ClientboundSetEntityMotionPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getId());
      if (â˜ƒ != null) {
         â˜ƒ.lerpMotion((double)â˜ƒ.getXa() / 8000.0, (double)â˜ƒ.getYa() / 8000.0, (double)â˜ƒ.getZa() / 8000.0);
      }
   }

   @Override
   public void handleSetEntityData(ClientboundSetEntityDataPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getId());
      if (â˜ƒ != null && â˜ƒ.getUnpackedData() != null) {
         â˜ƒ.getEntityData().assignValues(â˜ƒ.getUnpackedData());
      }
   }

   @Override
   public void handleAddPlayer(ClientboundAddPlayerPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      double â˜ƒ = â˜ƒ.getX();
      double â˜ƒx = â˜ƒ.getY();
      double â˜ƒxx = â˜ƒ.getZ();
      float â˜ƒxxx = (float)(â˜ƒ.getyRot() * 360) / 256.0F;
      float â˜ƒxxxx = (float)(â˜ƒ.getxRot() * 360) / 256.0F;
      int â˜ƒxxxxx = â˜ƒ.getEntityId();
      RemotePlayer â˜ƒxxxxxx = new RemotePlayer(this.minecraft.level, this.getPlayerInfo(â˜ƒ.getPlayerId()).getProfile());
      â˜ƒxxxxxx.setId(â˜ƒxxxxx);
      â˜ƒxxxxxx.setPacketCoordinates(â˜ƒ, â˜ƒx, â˜ƒxx);
      â˜ƒxxxxxx.absMoveTo(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      â˜ƒxxxxxx.setOldPosAndRot();
      this.level.addPlayer(â˜ƒxxxxx, â˜ƒxxxxxx);
   }

   @Override
   public void handleTeleportEntity(ClientboundTeleportEntityPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getId());
      if (â˜ƒ != null) {
         double â˜ƒx = â˜ƒ.getX();
         double â˜ƒxx = â˜ƒ.getY();
         double â˜ƒxxx = â˜ƒ.getZ();
         â˜ƒ.setPacketCoordinates(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         if (!â˜ƒ.isControlledByLocalInstance()) {
            float â˜ƒxxxx = (float)(â˜ƒ.getyRot() * 360) / 256.0F;
            float â˜ƒxxxxx = (float)(â˜ƒ.getxRot() * 360) / 256.0F;
            â˜ƒ.lerpTo(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 3, true);
            â˜ƒ.setOnGround(â˜ƒ.isOnGround());
         }
      }
   }

   @Override
   public void handleSetCarriedItem(ClientboundSetCarriedItemPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      if (Inventory.isHotbarSlot(â˜ƒ.getSlot())) {
         this.minecraft.player.getInventory().selected = â˜ƒ.getSlot();
      }
   }

   @Override
   public void handleMoveEntity(ClientboundMoveEntityPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = â˜ƒ.getEntity(this.level);
      if (â˜ƒ != null) {
         if (!â˜ƒ.isControlledByLocalInstance()) {
            if (â˜ƒ.hasPosition()) {
               Vec3 â˜ƒx = â˜ƒ.updateEntityPosition(â˜ƒ.getPacketCoordinates());
               â˜ƒ.setPacketCoordinates(â˜ƒx);
               float â˜ƒxx = â˜ƒ.hasRotation() ? (float)(â˜ƒ.getyRot() * 360) / 256.0F : â˜ƒ.getYRot();
               float â˜ƒxxx = â˜ƒ.hasRotation() ? (float)(â˜ƒ.getxRot() * 360) / 256.0F : â˜ƒ.getXRot();
               â˜ƒ.lerpTo(â˜ƒx.x(), â˜ƒx.y(), â˜ƒx.z(), â˜ƒxx, â˜ƒxxx, 3, false);
            } else if (â˜ƒ.hasRotation()) {
               float â˜ƒx = (float)(â˜ƒ.getyRot() * 360) / 256.0F;
               float â˜ƒxx = (float)(â˜ƒ.getxRot() * 360) / 256.0F;
               â˜ƒ.lerpTo(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒx, â˜ƒxx, 3, false);
            }

            â˜ƒ.setOnGround(â˜ƒ.isOnGround());
         }
      }
   }

   @Override
   public void handleRotateMob(ClientboundRotateHeadPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = â˜ƒ.getEntity(this.level);
      if (â˜ƒ != null) {
         float â˜ƒx = (float)(â˜ƒ.getYHeadRot() * 360) / 256.0F;
         â˜ƒ.lerpHeadTo(â˜ƒx, 3);
      }
   }

   @Override
   public void handleRemoveEntities(ClientboundRemoveEntitiesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      â˜ƒ.getEntityIds().forEach(var1x -> this.level.removeEntity(var1x, Entity.RemovalReason.DISCARDED));
   }

   @Override
   public void handleMovePlayer(ClientboundPlayerPositionPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Player â˜ƒ = this.minecraft.player;
      if (â˜ƒ.requestDismountVehicle()) {
         â˜ƒ.removeVehicle();
      }

      Vec3 â˜ƒxx = â˜ƒ.getDeltaMovement();
      boolean â˜ƒxxx = â˜ƒ.getRelativeArguments().contains(ClientboundPlayerPositionPacket.RelativeArgument.X);
      boolean â˜ƒxxxx = â˜ƒ.getRelativeArguments().contains(ClientboundPlayerPositionPacket.RelativeArgument.Y);
      boolean â˜ƒxxxxx = â˜ƒ.getRelativeArguments().contains(ClientboundPlayerPositionPacket.RelativeArgument.Z);
      double â˜ƒ;
      double â˜ƒx;
      if (â˜ƒxxx) {
         â˜ƒ = â˜ƒxx.x();
         â˜ƒx = â˜ƒ.getX() + â˜ƒ.getX();
         â˜ƒ.xOld += â˜ƒ.getX();
      } else {
         â˜ƒ = 0.0;
         â˜ƒx = â˜ƒ.getX();
         â˜ƒ.xOld = â˜ƒx;
      }

      double â˜ƒ;
      double â˜ƒx;
      if (â˜ƒxxxx) {
         â˜ƒ = â˜ƒxx.y();
         â˜ƒx = â˜ƒ.getY() + â˜ƒ.getY();
         â˜ƒ.yOld += â˜ƒ.getY();
      } else {
         â˜ƒ = 0.0;
         â˜ƒx = â˜ƒ.getY();
         â˜ƒ.yOld = â˜ƒx;
      }

      double â˜ƒ;
      double â˜ƒx;
      if (â˜ƒxxxxx) {
         â˜ƒ = â˜ƒxx.z();
         â˜ƒx = â˜ƒ.getZ() + â˜ƒ.getZ();
         â˜ƒ.zOld += â˜ƒ.getZ();
      } else {
         â˜ƒ = 0.0;
         â˜ƒx = â˜ƒ.getZ();
         â˜ƒ.zOld = â˜ƒx;
      }

      â˜ƒ.setPosRaw(â˜ƒx, â˜ƒx, â˜ƒx);
      â˜ƒ.xo = â˜ƒx;
      â˜ƒ.yo = â˜ƒx;
      â˜ƒ.zo = â˜ƒx;
      â˜ƒ.setDeltaMovement(â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = â˜ƒ.getYRot();
      float â˜ƒx = â˜ƒ.getXRot();
      if (â˜ƒ.getRelativeArguments().contains(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT)) {
         â˜ƒx += â˜ƒ.getXRot();
      }

      if (â˜ƒ.getRelativeArguments().contains(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT)) {
         â˜ƒ += â˜ƒ.getYRot();
      }

      â˜ƒ.absMoveTo(â˜ƒx, â˜ƒx, â˜ƒx, â˜ƒ, â˜ƒx);
      this.connection.send(new ServerboundAcceptTeleportationPacket(â˜ƒ.getId()));
      this.connection.send(new ServerboundMovePlayerPacket.PosRot(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot(), false));
      if (!this.started) {
         this.started = true;
         this.minecraft.setScreen(null);
      }
   }

   @Override
   public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      int â˜ƒ = 19 | (â˜ƒ.shouldSuppressLightUpdates() ? 128 : 0);
      â˜ƒ.runUpdates((var2x, var3) -> this.level.setBlock(var2x, var3, â˜ƒ));
   }

   @Override
   public void handleLevelChunk(ClientboundLevelChunkPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      ChunkBiomeContainer â˜ƒxx = new ChunkBiomeContainer(this.registryAccess.registryOrThrow(Registry.BIOME_REGISTRY), this.level, â˜ƒ.getBiomes());
      LevelChunk â˜ƒxxx = this.level
         .getChunkSource()
         .replaceWithPacketData(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ.getReadBuffer(), â˜ƒ.getHeightmaps(), â˜ƒ.getAvailableSections());

      for(int â˜ƒxxxx = this.level.getMinSection(); â˜ƒxxxx < this.level.getMaxSection(); ++â˜ƒxxxx) {
         this.level.setSectionDirtyWithNeighbors(â˜ƒ, â˜ƒxxxx, â˜ƒx);
      }

      if (â˜ƒxxx != null) {
         for(CompoundTag â˜ƒxxxx : â˜ƒ.getBlockEntitiesTags()) {
            BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxxxx.getInt("x"), â˜ƒxxxx.getInt("y"), â˜ƒxxxx.getInt("z"));
            BlockEntity â˜ƒxxxxxx = â˜ƒxxx.getBlockEntity(â˜ƒxxxxx, LevelChunk.EntityCreationType.IMMEDIATE);
            if (â˜ƒxxxxxx != null) {
               â˜ƒxxxxxx.load(â˜ƒxxxx);
            }
         }
      }
   }

   @Override
   public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      ClientChunkCache â˜ƒxx = this.level.getChunkSource();
      â˜ƒxx.drop(â˜ƒ, â˜ƒx);
      LevelLightEngine â˜ƒxxx = â˜ƒxx.getLightEngine();

      for(int â˜ƒxxxx = this.level.getMinSection(); â˜ƒxxxx < this.level.getMaxSection(); ++â˜ƒxxxx) {
         this.level.setSectionDirtyWithNeighbors(â˜ƒ, â˜ƒxxxx, â˜ƒx);
         â˜ƒxxx.updateSectionStatus(SectionPos.of(â˜ƒ, â˜ƒxxxx, â˜ƒx), true);
      }

      â˜ƒxxx.enableLightSources(new ChunkPos(â˜ƒ, â˜ƒx), false);
   }

   @Override
   public void handleBlockUpdate(ClientboundBlockUpdatePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.setKnownState(â˜ƒ.getPos(), â˜ƒ.getBlockState());
   }

   @Override
   public void handleDisconnect(ClientboundDisconnectPacket var1) {
      this.connection.disconnect(â˜ƒ.getReason());
   }

   @Override
   public void onDisconnect(Component var1) {
      this.minecraft.clearLevel();
      if (this.callbackScreen != null) {
         if (this.callbackScreen instanceof RealmsScreen) {
            this.minecraft.setScreen(new DisconnectedRealmsScreen(this.callbackScreen, GENERIC_DISCONNECT_MESSAGE, â˜ƒ));
         } else {
            this.minecraft.setScreen(new DisconnectedScreen(this.callbackScreen, GENERIC_DISCONNECT_MESSAGE, â˜ƒ));
         }
      } else {
         this.minecraft.setScreen(new DisconnectedScreen(new JoinMultiplayerScreen(new TitleScreen()), GENERIC_DISCONNECT_MESSAGE, â˜ƒ));
      }
   }

   public void send(Packet<?> var1) {
      this.connection.send(â˜ƒ);
   }

   @Override
   public void handleTakeItemEntity(ClientboundTakeItemEntityPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getItemId());
      LivingEntity â˜ƒx = (LivingEntity)this.level.getEntity(â˜ƒ.getPlayerId());
      if (â˜ƒx == null) {
         â˜ƒx = this.minecraft.player;
      }

      if (â˜ƒ != null) {
         if (â˜ƒ instanceof ExperienceOrb) {
            this.level
               .playLocalSound(
                  â˜ƒ.getX(),
                  â˜ƒ.getY(),
                  â˜ƒ.getZ(),
                  SoundEvents.EXPERIENCE_ORB_PICKUP,
                  SoundSource.PLAYERS,
                  0.1F,
                  (this.random.nextFloat() - this.random.nextFloat()) * 0.35F + 0.9F,
                  false
               );
         } else {
            this.level
               .playLocalSound(
                  â˜ƒ.getX(),
                  â˜ƒ.getY(),
                  â˜ƒ.getZ(),
                  SoundEvents.ITEM_PICKUP,
                  SoundSource.PLAYERS,
                  0.2F,
                  (this.random.nextFloat() - this.random.nextFloat()) * 1.4F + 2.0F,
                  false
               );
         }

         this.minecraft
            .particleEngine
            .add(new ItemPickupParticle(this.minecraft.getEntityRenderDispatcher(), this.minecraft.renderBuffers(), this.level, â˜ƒ, â˜ƒx));
         if (â˜ƒ instanceof ItemEntity â˜ƒ) {
            ItemStack â˜ƒx = â˜ƒ.getItem();
            â˜ƒx.shrink(â˜ƒ.getAmount());
            if (â˜ƒx.isEmpty()) {
               this.level.removeEntity(â˜ƒ.getItemId(), Entity.RemovalReason.DISCARDED);
            }
         } else if (!(â˜ƒ instanceof ExperienceOrb)) {
            this.level.removeEntity(â˜ƒ.getItemId(), Entity.RemovalReason.DISCARDED);
         }
      }
   }

   @Override
   public void handleChat(ClientboundChatPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.handleChat(â˜ƒ.getType(), â˜ƒ.getMessage(), â˜ƒ.getSender());
   }

   @Override
   public void handleAnimate(ClientboundAnimatePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getId());
      if (â˜ƒ != null) {
         if (â˜ƒ.getAction() == 0) {
            LivingEntity â˜ƒx = (LivingEntity)â˜ƒ;
            â˜ƒx.swing(InteractionHand.MAIN_HAND);
         } else if (â˜ƒ.getAction() == 3) {
            LivingEntity â˜ƒx = (LivingEntity)â˜ƒ;
            â˜ƒx.swing(InteractionHand.OFF_HAND);
         } else if (â˜ƒ.getAction() == 1) {
            â˜ƒ.animateHurt();
         } else if (â˜ƒ.getAction() == 2) {
            Player â˜ƒx = (Player)â˜ƒ;
            â˜ƒx.stopSleepInBed(false, false);
         } else if (â˜ƒ.getAction() == 4) {
            this.minecraft.particleEngine.createTrackingEmitter(â˜ƒ, ParticleTypes.CRIT);
         } else if (â˜ƒ.getAction() == 5) {
            this.minecraft.particleEngine.createTrackingEmitter(â˜ƒ, ParticleTypes.ENCHANTED_HIT);
         }
      }
   }

   @Override
   public void handleAddMob(ClientboundAddMobPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      LivingEntity â˜ƒ = (LivingEntity)EntityType.create(â˜ƒ.getType(), this.level);
      if (â˜ƒ != null) {
         â˜ƒ.recreateFromPacket(â˜ƒ);
         this.level.putNonPlayerEntity(â˜ƒ.getId(), â˜ƒ);
         if (â˜ƒ instanceof Bee) {
            boolean â˜ƒxx = ((Bee)â˜ƒ).isAngry();
            BeeSoundInstance â˜ƒx;
            if (â˜ƒxx) {
               â˜ƒx = new BeeAggressiveSoundInstance((Bee)â˜ƒ);
            } else {
               â˜ƒx = new BeeFlyingSoundInstance((Bee)â˜ƒ);
            }

            this.minecraft.getSoundManager().queueTickingSound(â˜ƒx);
         }
      } else {
         LOGGER.warn("Skipping Entity with id {}", â˜ƒ.getType());
      }
   }

   @Override
   public void handleSetTime(ClientboundSetTimePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.level.setGameTime(â˜ƒ.getGameTime());
      this.minecraft.level.setDayTime(â˜ƒ.getDayTime());
   }

   @Override
   public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.level.setDefaultSpawnPos(â˜ƒ.getPos(), â˜ƒ.getAngle());
   }

   @Override
   public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getVehicle());
      if (â˜ƒ == null) {
         LOGGER.warn("Received passengers for unknown entity");
      } else {
         boolean â˜ƒ = â˜ƒ.hasIndirectPassenger(this.minecraft.player);
         â˜ƒ.ejectPassengers();

         for(int â˜ƒx : â˜ƒ.getPassengers()) {
            Entity â˜ƒxx = this.level.getEntity(â˜ƒx);
            if (â˜ƒxx != null) {
               â˜ƒxx.startRiding(â˜ƒ, true);
               if (â˜ƒxx == this.minecraft.player && !â˜ƒ) {
                  this.minecraft
                     .gui
                     .setOverlayMessage(new TranslatableComponent("mount.onboard", this.minecraft.options.keyShift.getTranslatedKeyMessage()), false);
               }
            }
         }
      }
   }

   @Override
   public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getSourceId());
      if (â˜ƒ instanceof Mob) {
         ((Mob)â˜ƒ).setDelayedLeashHolderId(â˜ƒ.getDestId());
      }
   }

   private static ItemStack findTotem(Player var0) {
      for(InteractionHand â˜ƒ : InteractionHand.values()) {
         ItemStack â˜ƒx = â˜ƒ.getItemInHand(â˜ƒ);
         if (â˜ƒx.is(Items.TOTEM_OF_UNDYING)) {
            return â˜ƒx;
         }
      }

      return new ItemStack(Items.TOTEM_OF_UNDYING);
   }

   @Override
   public void handleEntityEvent(ClientboundEntityEventPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = â˜ƒ.getEntity(this.level);
      if (â˜ƒ != null) {
         if (â˜ƒ.getEventId() == 21) {
            this.minecraft.getSoundManager().play(new GuardianAttackSoundInstance((Guardian)â˜ƒ));
         } else if (â˜ƒ.getEventId() == 35) {
            int â˜ƒx = 40;
            this.minecraft.particleEngine.createTrackingEmitter(â˜ƒ, ParticleTypes.TOTEM_OF_UNDYING, 30);
            this.level.playLocalSound(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.TOTEM_USE, â˜ƒ.getSoundSource(), 1.0F, 1.0F, false);
            if (â˜ƒ == this.minecraft.player) {
               this.minecraft.gameRenderer.displayItemActivation(findTotem(this.minecraft.player));
            }
         } else {
            â˜ƒ.handleEntityEvent(â˜ƒ.getEventId());
         }
      }
   }

   @Override
   public void handleSetHealth(ClientboundSetHealthPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.player.hurtTo(â˜ƒ.getHealth());
      this.minecraft.player.getFoodData().setFoodLevel(â˜ƒ.getFood());
      this.minecraft.player.getFoodData().setSaturation(â˜ƒ.getSaturation());
   }

   @Override
   public void handleSetExperience(ClientboundSetExperiencePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.player.setExperienceValues(â˜ƒ.getExperienceProgress(), â˜ƒ.getTotalExperience(), â˜ƒ.getExperienceLevel());
   }

   @Override
   public void handleRespawn(ClientboundRespawnPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      ResourceKey<Level> â˜ƒ = â˜ƒ.getDimension();
      DimensionType â˜ƒx = â˜ƒ.getDimensionType();
      LocalPlayer â˜ƒxx = this.minecraft.player;
      int â˜ƒxxx = â˜ƒxx.getId();
      this.started = false;
      if (â˜ƒ != â˜ƒxx.level.dimension()) {
         Scoreboard â˜ƒxxxx = this.level.getScoreboard();
         Map<String, MapItemSavedData> â˜ƒxxxxx = this.level.getAllMapData();
         boolean â˜ƒxxxxxx = â˜ƒ.isDebug();
         boolean â˜ƒxxxxxxx = â˜ƒ.isFlat();
         ClientLevel.ClientLevelData â˜ƒxxxxxxxx = new ClientLevel.ClientLevelData(this.levelData.getDifficulty(), this.levelData.isHardcore(), â˜ƒxxxxxxx);
         this.levelData = â˜ƒxxxxxxxx;
         this.level = new ClientLevel(
            this, â˜ƒxxxxxxxx, â˜ƒ, â˜ƒx, this.serverChunkRadius, this.minecraft::getProfiler, this.minecraft.levelRenderer, â˜ƒxxxxxx, â˜ƒ.getSeed()
         );
         this.level.setScoreboard(â˜ƒxxxx);
         this.level.addMapData(â˜ƒxxxxx);
         this.minecraft.setLevel(this.level);
         this.minecraft.setScreen(new ReceivingLevelScreen());
      }

      String â˜ƒ = â˜ƒxx.getServerBrand();
      this.minecraft.cameraEntity = null;
      LocalPlayer â˜ƒx = this.minecraft.gameMode.createPlayer(this.level, â˜ƒxx.getStats(), â˜ƒxx.getRecipeBook(), â˜ƒxx.isShiftKeyDown(), â˜ƒxx.isSprinting());
      â˜ƒx.setId(â˜ƒxxx);
      this.minecraft.player = â˜ƒx;
      if (â˜ƒ != â˜ƒxx.level.dimension()) {
         this.minecraft.getMusicManager().stopPlaying();
      }

      this.minecraft.cameraEntity = â˜ƒx;
      â˜ƒx.getEntityData().assignValues(â˜ƒxx.getEntityData().getAll());
      if (â˜ƒ.shouldKeepAllPlayerData()) {
         â˜ƒx.getAttributes().assignValues(â˜ƒxx.getAttributes());
      }

      â˜ƒx.resetPos();
      â˜ƒx.setServerBrand(â˜ƒ);
      this.level.addPlayer(â˜ƒxxx, â˜ƒx);
      â˜ƒx.setYRot(-180.0F);
      â˜ƒx.input = new KeyboardInput(this.minecraft.options);
      this.minecraft.gameMode.adjustPlayer(â˜ƒx);
      â˜ƒx.setReducedDebugInfo(â˜ƒxx.isReducedDebugInfo());
      â˜ƒx.setShowDeathScreen(â˜ƒxx.shouldShowDeathScreen());
      if (this.minecraft.screen instanceof DeathScreen) {
         this.minecraft.setScreen(null);
      }

      this.minecraft.gameMode.setLocalMode(â˜ƒ.getPlayerGameType(), â˜ƒ.getPreviousPlayerGameType());
   }

   @Override
   public void handleExplosion(ClientboundExplodePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Explosion â˜ƒ = new Explosion(this.minecraft.level, null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getPower(), â˜ƒ.getToBlow());
      â˜ƒ.finalizeExplosion(true);
      this.minecraft
         .player
         .setDeltaMovement(this.minecraft.player.getDeltaMovement().add((double)â˜ƒ.getKnockbackX(), (double)â˜ƒ.getKnockbackY(), (double)â˜ƒ.getKnockbackZ()));
   }

   @Override
   public void handleHorseScreenOpen(ClientboundHorseScreenOpenPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getEntityId());
      if (â˜ƒ instanceof AbstractHorse) {
         LocalPlayer â˜ƒx = this.minecraft.player;
         AbstractHorse â˜ƒxx = (AbstractHorse)â˜ƒ;
         SimpleContainer â˜ƒxxx = new SimpleContainer(â˜ƒ.getSize());
         HorseInventoryMenu â˜ƒxxxx = new HorseInventoryMenu(â˜ƒ.getContainerId(), â˜ƒx.getInventory(), â˜ƒxxx, â˜ƒxx);
         â˜ƒx.containerMenu = â˜ƒxxxx;
         this.minecraft.setScreen(new HorseInventoryScreen(â˜ƒxxxx, â˜ƒx.getInventory(), â˜ƒxx));
      }
   }

   @Override
   public void handleOpenScreen(ClientboundOpenScreenPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      MenuScreens.create(â˜ƒ.getType(), this.minecraft, â˜ƒ.getContainerId(), â˜ƒ.getTitle());
   }

   @Override
   public void handleContainerSetSlot(ClientboundContainerSetSlotPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Player â˜ƒ = this.minecraft.player;
      ItemStack â˜ƒx = â˜ƒ.getItem();
      int â˜ƒxx = â˜ƒ.getSlot();
      this.minecraft.getTutorial().onGetItem(â˜ƒx);
      if (â˜ƒ.getContainerId() == -1) {
         if (!(this.minecraft.screen instanceof CreativeModeInventoryScreen)) {
            â˜ƒ.containerMenu.setCarried(â˜ƒx);
         }
      } else if (â˜ƒ.getContainerId() == -2) {
         â˜ƒ.getInventory().setItem(â˜ƒxx, â˜ƒx);
      } else {
         boolean â˜ƒx = false;
         if (this.minecraft.screen instanceof CreativeModeInventoryScreen â˜ƒ) {
            â˜ƒx = â˜ƒ.getSelectedTab() != CreativeModeTab.TAB_INVENTORY.getId();
         }

         if (â˜ƒ.getContainerId() == 0 && InventoryMenu.isHotbarSlot(â˜ƒxx)) {
            if (!â˜ƒx.isEmpty()) {
               ItemStack â˜ƒ = â˜ƒ.inventoryMenu.getSlot(â˜ƒxx).getItem();
               if (â˜ƒ.isEmpty() || â˜ƒ.getCount() < â˜ƒx.getCount()) {
                  â˜ƒx.setPopTime(5);
               }
            }

            â˜ƒ.inventoryMenu.setItem(â˜ƒxx, â˜ƒ.getStateId(), â˜ƒx);
         } else if (â˜ƒ.getContainerId() == â˜ƒ.containerMenu.containerId && (â˜ƒ.getContainerId() != 0 || !â˜ƒx)) {
            â˜ƒ.containerMenu.setItem(â˜ƒxx, â˜ƒ.getStateId(), â˜ƒx);
         }
      }
   }

   @Override
   public void handleContainerContent(ClientboundContainerSetContentPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Player â˜ƒ = this.minecraft.player;
      if (â˜ƒ.getContainerId() == 0) {
         â˜ƒ.inventoryMenu.initializeContents(â˜ƒ.getStateId(), â˜ƒ.getItems(), â˜ƒ.getCarriedItem());
      } else if (â˜ƒ.getContainerId() == â˜ƒ.containerMenu.containerId) {
         â˜ƒ.containerMenu.initializeContents(â˜ƒ.getStateId(), â˜ƒ.getItems(), â˜ƒ.getCarriedItem());
      }
   }

   @Override
   public void handleOpenSignEditor(ClientboundOpenSignEditorPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      BlockPos â˜ƒ = â˜ƒ.getPos();
      BlockEntity â˜ƒx = this.level.getBlockEntity(â˜ƒ);
      if (!(â˜ƒx instanceof SignBlockEntity)) {
         BlockState â˜ƒxx = this.level.getBlockState(â˜ƒ);
         â˜ƒx = new SignBlockEntity(â˜ƒ, â˜ƒxx);
         â˜ƒx.setLevel(this.level);
      }

      this.minecraft.player.openTextEdit((SignBlockEntity)â˜ƒx);
   }

   @Override
   public void handleBlockEntityData(ClientboundBlockEntityDataPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      BlockPos â˜ƒ = â˜ƒ.getPos();
      BlockEntity â˜ƒx = this.minecraft.level.getBlockEntity(â˜ƒ);
      int â˜ƒxx = â˜ƒ.getType();
      boolean â˜ƒxxx = â˜ƒxx == 2 && â˜ƒx instanceof CommandBlockEntity;
      if (â˜ƒxx == 1 && â˜ƒx instanceof SpawnerBlockEntity
         || â˜ƒxxx
         || â˜ƒxx == 3 && â˜ƒx instanceof BeaconBlockEntity
         || â˜ƒxx == 4 && â˜ƒx instanceof SkullBlockEntity
         || â˜ƒxx == 6 && â˜ƒx instanceof BannerBlockEntity
         || â˜ƒxx == 7 && â˜ƒx instanceof StructureBlockEntity
         || â˜ƒxx == 8 && â˜ƒx instanceof TheEndGatewayBlockEntity
         || â˜ƒxx == 9 && â˜ƒx instanceof SignBlockEntity
         || â˜ƒxx == 11 && â˜ƒx instanceof BedBlockEntity
         || â˜ƒxx == 5 && â˜ƒx instanceof ConduitBlockEntity
         || â˜ƒxx == 12 && â˜ƒx instanceof JigsawBlockEntity
         || â˜ƒxx == 13 && â˜ƒx instanceof CampfireBlockEntity
         || â˜ƒxx == 14 && â˜ƒx instanceof BeehiveBlockEntity) {
         â˜ƒx.load(â˜ƒ.getTag());
      }

      if (â˜ƒxxx && this.minecraft.screen instanceof CommandBlockEditScreen) {
         ((CommandBlockEditScreen)this.minecraft.screen).updateGui();
      }
   }

   @Override
   public void handleContainerSetData(ClientboundContainerSetDataPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Player â˜ƒ = this.minecraft.player;
      if (â˜ƒ.containerMenu != null && â˜ƒ.containerMenu.containerId == â˜ƒ.getContainerId()) {
         â˜ƒ.containerMenu.setData(â˜ƒ.getId(), â˜ƒ.getValue());
      }
   }

   @Override
   public void handleSetEquipment(ClientboundSetEquipmentPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getEntity());
      if (â˜ƒ != null) {
         â˜ƒ.getSlots().forEach(var1x -> â˜ƒ.setItemSlot((EquipmentSlot)var1x.getFirst(), (ItemStack)var1x.getSecond()));
      }
   }

   @Override
   public void handleContainerClose(ClientboundContainerClosePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.player.clientSideCloseContainer();
   }

   @Override
   public void handleBlockEvent(ClientboundBlockEventPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.level.blockEvent(â˜ƒ.getPos(), â˜ƒ.getBlock(), â˜ƒ.getB0(), â˜ƒ.getB1());
   }

   @Override
   public void handleBlockDestruction(ClientboundBlockDestructionPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.level.destroyBlockProgress(â˜ƒ.getId(), â˜ƒ.getPos(), â˜ƒ.getProgress());
   }

   @Override
   public void handleGameEvent(ClientboundGameEventPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Player â˜ƒ = this.minecraft.player;
      ClientboundGameEventPacket.Type â˜ƒx = â˜ƒ.getEvent();
      float â˜ƒxx = â˜ƒ.getParam();
      int â˜ƒxxx = Mth.floor(â˜ƒxx + 0.5F);
      if (â˜ƒx == ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE) {
         â˜ƒ.displayClientMessage(new TranslatableComponent("block.minecraft.spawn.not_valid"), false);
      } else if (â˜ƒx == ClientboundGameEventPacket.START_RAINING) {
         this.level.getLevelData().setRaining(true);
         this.level.setRainLevel(0.0F);
      } else if (â˜ƒx == ClientboundGameEventPacket.STOP_RAINING) {
         this.level.getLevelData().setRaining(false);
         this.level.setRainLevel(1.0F);
      } else if (â˜ƒx == ClientboundGameEventPacket.CHANGE_GAME_MODE) {
         this.minecraft.gameMode.setLocalMode(GameType.byId(â˜ƒxxx));
      } else if (â˜ƒx == ClientboundGameEventPacket.WIN_GAME) {
         if (â˜ƒxxx == 0) {
            this.minecraft.player.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
            this.minecraft.setScreen(new ReceivingLevelScreen());
         } else if (â˜ƒxxx == 1) {
            this.minecraft
               .setScreen(
                  new WinScreen(
                     true,
                     () -> this.minecraft.player.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN))
                  )
               );
         }
      } else if (â˜ƒx == ClientboundGameEventPacket.DEMO_EVENT) {
         Options â˜ƒ = this.minecraft.options;
         if (â˜ƒxx == 0.0F) {
            this.minecraft.setScreen(new DemoIntroScreen());
         } else if (â˜ƒxx == 101.0F) {
            this.minecraft
               .gui
               .getChat()
               .addMessage(
                  new TranslatableComponent(
                     "demo.help.movement",
                     â˜ƒ.keyUp.getTranslatedKeyMessage(),
                     â˜ƒ.keyLeft.getTranslatedKeyMessage(),
                     â˜ƒ.keyDown.getTranslatedKeyMessage(),
                     â˜ƒ.keyRight.getTranslatedKeyMessage()
                  )
               );
         } else if (â˜ƒxx == 102.0F) {
            this.minecraft.gui.getChat().addMessage(new TranslatableComponent("demo.help.jump", â˜ƒ.keyJump.getTranslatedKeyMessage()));
         } else if (â˜ƒxx == 103.0F) {
            this.minecraft.gui.getChat().addMessage(new TranslatableComponent("demo.help.inventory", â˜ƒ.keyInventory.getTranslatedKeyMessage()));
         } else if (â˜ƒxx == 104.0F) {
            this.minecraft.gui.getChat().addMessage(new TranslatableComponent("demo.day.6", â˜ƒ.keyScreenshot.getTranslatedKeyMessage()));
         }
      } else if (â˜ƒx == ClientboundGameEventPacket.ARROW_HIT_PLAYER) {
         this.level.playSound(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getEyeY(), â˜ƒ.getZ(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.18F, 0.45F);
      } else if (â˜ƒx == ClientboundGameEventPacket.RAIN_LEVEL_CHANGE) {
         this.level.setRainLevel(â˜ƒxx);
      } else if (â˜ƒx == ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE) {
         this.level.setThunderLevel(â˜ƒxx);
      } else if (â˜ƒx == ClientboundGameEventPacket.PUFFER_FISH_STING) {
         this.level.playSound(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.PUFFER_FISH_STING, SoundSource.NEUTRAL, 1.0F, 1.0F);
      } else if (â˜ƒx == ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT) {
         this.level.addParticle(ParticleTypes.ELDER_GUARDIAN, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), 0.0, 0.0, 0.0);
         if (â˜ƒxxx == 1) {
            this.level.playSound(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE, 1.0F, 1.0F);
         }
      } else if (â˜ƒx == ClientboundGameEventPacket.IMMEDIATE_RESPAWN) {
         this.minecraft.player.setShowDeathScreen(â˜ƒxx == 0.0F);
      }
   }

   @Override
   public void handleMapItemData(ClientboundMapItemDataPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      MapRenderer â˜ƒ = this.minecraft.gameRenderer.getMapRenderer();
      int â˜ƒx = â˜ƒ.getMapId();
      String â˜ƒxx = MapItem.makeKey(â˜ƒx);
      MapItemSavedData â˜ƒxxx = this.minecraft.level.getMapData(â˜ƒxx);
      if (â˜ƒxxx == null) {
         â˜ƒxxx = MapItemSavedData.createForClient(â˜ƒ.getScale(), â˜ƒ.isLocked(), this.minecraft.level.dimension());
         this.minecraft.level.setMapData(â˜ƒxx, â˜ƒxxx);
      }

      â˜ƒ.applyToMap(â˜ƒxxx);
      â˜ƒ.update(â˜ƒx, â˜ƒxxx);
   }

   @Override
   public void handleLevelEvent(ClientboundLevelEventPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      if (â˜ƒ.isGlobalEvent()) {
         this.minecraft.level.globalLevelEvent(â˜ƒ.getType(), â˜ƒ.getPos(), â˜ƒ.getData());
      } else {
         this.minecraft.level.levelEvent(â˜ƒ.getType(), â˜ƒ.getPos(), â˜ƒ.getData());
      }
   }

   @Override
   public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.advancements.update(â˜ƒ);
   }

   @Override
   public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      ResourceLocation â˜ƒ = â˜ƒ.getTab();
      if (â˜ƒ == null) {
         this.advancements.setSelectedTab(null, false);
      } else {
         Advancement â˜ƒ = this.advancements.getAdvancements().get(â˜ƒ);
         this.advancements.setSelectedTab(â˜ƒ, false);
      }
   }

   @Override
   public void handleCommands(ClientboundCommandsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.commands = new CommandDispatcher<>(â˜ƒ.getRoot());
   }

   @Override
   public void handleStopSoundEvent(ClientboundStopSoundPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.getSoundManager().stop(â˜ƒ.getName(), â˜ƒ.getSource());
   }

   @Override
   public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.suggestionsProvider.completeCustomSuggestions(â˜ƒ.getId(), â˜ƒ.getSuggestions());
   }

   @Override
   public void handleUpdateRecipes(ClientboundUpdateRecipesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.recipeManager.replaceRecipes(â˜ƒ.getRecipes());
      MutableSearchTree<RecipeCollection> â˜ƒ = this.minecraft.getSearchTree(SearchRegistry.RECIPE_COLLECTIONS);
      â˜ƒ.clear();
      ClientRecipeBook â˜ƒx = this.minecraft.player.getRecipeBook();
      â˜ƒx.setupCollections(this.recipeManager.getRecipes());
      â˜ƒx.getCollections().forEach(â˜ƒ::add);
      â˜ƒ.refresh();
   }

   @Override
   public void handleLookAt(ClientboundPlayerLookAtPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Vec3 â˜ƒ = â˜ƒ.getPosition(this.level);
      if (â˜ƒ != null) {
         this.minecraft.player.lookAt(â˜ƒ.getFromAnchor(), â˜ƒ);
      }
   }

   @Override
   public void handleTagQueryPacket(ClientboundTagQueryPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      if (!this.debugQueryHandler.handleResponse(â˜ƒ.getTransactionId(), â˜ƒ.getTag())) {
         LOGGER.debug("Got unhandled response to tag query {}", â˜ƒ.getTransactionId());
      }
   }

   @Override
   public void handleAwardStats(ClientboundAwardStatsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);

      for(Entry<Stat<?>, Integer> â˜ƒ : â˜ƒ.getStats().entrySet()) {
         Stat<?> â˜ƒx = (Stat)â˜ƒ.getKey();
         int â˜ƒxx = â˜ƒ.getValue();
         this.minecraft.player.getStats().setValue(this.minecraft.player, â˜ƒx, â˜ƒxx);
      }

      if (this.minecraft.screen instanceof StatsUpdateListener) {
         ((StatsUpdateListener)this.minecraft.screen).onStatsUpdated();
      }
   }

   @Override
   public void handleAddOrRemoveRecipes(ClientboundRecipePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      ClientRecipeBook â˜ƒ = this.minecraft.player.getRecipeBook();
      â˜ƒ.setBookSettings(â˜ƒ.getBookSettings());
      ClientboundRecipePacket.State â˜ƒx = â˜ƒ.getState();
      switch(â˜ƒx) {
         case REMOVE:
            for(ResourceLocation â˜ƒxx : â˜ƒ.getRecipes()) {
               this.recipeManager.byKey(â˜ƒxx).ifPresent(â˜ƒ::remove);
            }
            break;
         case INIT:
            for(ResourceLocation â˜ƒxx : â˜ƒ.getRecipes()) {
               this.recipeManager.byKey(â˜ƒxx).ifPresent(â˜ƒ::add);
            }

            for(ResourceLocation â˜ƒxx : â˜ƒ.getHighlights()) {
               this.recipeManager.byKey(â˜ƒxx).ifPresent(â˜ƒ::addHighlight);
            }
            break;
         case ADD:
            for(ResourceLocation â˜ƒxx : â˜ƒ.getRecipes()) {
               this.recipeManager.byKey(â˜ƒxx).ifPresent(var2x -> {
                  â˜ƒ.add(var2x);
                  â˜ƒ.addHighlight(var2x);
                  RecipeToast.addOrUpdate(this.minecraft.getToasts(), var2x);
               });
            }
      }

      â˜ƒ.getCollections().forEach(var1x -> var1x.updateKnownRecipes(â˜ƒ));
      if (this.minecraft.screen instanceof RecipeUpdateListener) {
         ((RecipeUpdateListener)this.minecraft.screen).recipesUpdated();
      }
   }

   @Override
   public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getEntityId());
      if (â˜ƒ instanceof LivingEntity) {
         MobEffect â˜ƒx = MobEffect.byId(â˜ƒ.getEffectId());
         if (â˜ƒx != null) {
            MobEffectInstance â˜ƒxx = new MobEffectInstance(
               â˜ƒx, â˜ƒ.getEffectDurationTicks(), â˜ƒ.getEffectAmplifier(), â˜ƒ.isEffectAmbient(), â˜ƒ.isEffectVisible(), â˜ƒ.effectShowsIcon()
            );
            â˜ƒxx.setNoCounter(â˜ƒ.isSuperLongDuration());
            ((LivingEntity)â˜ƒ).forceAddEffect(â˜ƒxx, null);
         }
      }
   }

   @Override
   public void handleUpdateTags(ClientboundUpdateTagsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      TagContainer â˜ƒ = TagContainer.deserializeFromNetwork(this.registryAccess, â˜ƒ.getTags());
      Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> â˜ƒx = StaticTags.getAllMissingTags(â˜ƒ);
      if (!â˜ƒx.isEmpty()) {
         LOGGER.warn("Incomplete server tags, disconnecting. Missing: {}", â˜ƒx);
         this.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.missing_tags"));
      } else {
         this.tags = â˜ƒ;
         if (!this.connection.isMemoryConnection()) {
            â˜ƒ.bindToGlobal();
         }

         this.minecraft.getSearchTree(SearchRegistry.CREATIVE_TAGS).refresh();
      }
   }

   @Override
   public void handlePlayerCombatEnd(ClientboundPlayerCombatEndPacket var1) {
   }

   @Override
   public void handlePlayerCombatEnter(ClientboundPlayerCombatEnterPacket var1) {
   }

   @Override
   public void handlePlayerCombatKill(ClientboundPlayerCombatKillPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getPlayerId());
      if (â˜ƒ == this.minecraft.player) {
         if (this.minecraft.player.shouldShowDeathScreen()) {
            this.minecraft.setScreen(new DeathScreen(â˜ƒ.getMessage(), this.level.getLevelData().isHardcore()));
         } else {
            this.minecraft.player.respawn();
         }
      }
   }

   @Override
   public void handleChangeDifficulty(ClientboundChangeDifficultyPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.levelData.setDifficulty(â˜ƒ.getDifficulty());
      this.levelData.setDifficultyLocked(â˜ƒ.isLocked());
   }

   @Override
   public void handleSetCamera(ClientboundSetCameraPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = â˜ƒ.getEntity(this.level);
      if (â˜ƒ != null) {
         this.minecraft.setCameraEntity(â˜ƒ);
      }
   }

   @Override
   public void handleInitializeBorder(ClientboundInitializeBorderPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      WorldBorder â˜ƒ = this.level.getWorldBorder();
      â˜ƒ.setCenter(â˜ƒ.getNewCenterX(), â˜ƒ.getNewCenterZ());
      long â˜ƒx = â˜ƒ.getLerpTime();
      if (â˜ƒx > 0L) {
         â˜ƒ.lerpSizeBetween(â˜ƒ.getOldSize(), â˜ƒ.getNewSize(), â˜ƒx);
      } else {
         â˜ƒ.setSize(â˜ƒ.getNewSize());
      }

      â˜ƒ.setAbsoluteMaxSize(â˜ƒ.getNewAbsoluteMaxSize());
      â˜ƒ.setWarningBlocks(â˜ƒ.getWarningBlocks());
      â˜ƒ.setWarningTime(â˜ƒ.getWarningTime());
   }

   @Override
   public void handleSetBorderCenter(ClientboundSetBorderCenterPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.getWorldBorder().setCenter(â˜ƒ.getNewCenterX(), â˜ƒ.getNewCenterZ());
   }

   @Override
   public void handleSetBorderLerpSize(ClientboundSetBorderLerpSizePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.getWorldBorder().lerpSizeBetween(â˜ƒ.getOldSize(), â˜ƒ.getNewSize(), â˜ƒ.getLerpTime());
   }

   @Override
   public void handleSetBorderSize(ClientboundSetBorderSizePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.getWorldBorder().setSize(â˜ƒ.getSize());
   }

   @Override
   public void handleSetBorderWarningDistance(ClientboundSetBorderWarningDistancePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.getWorldBorder().setWarningBlocks(â˜ƒ.getWarningBlocks());
   }

   @Override
   public void handleSetBorderWarningDelay(ClientboundSetBorderWarningDelayPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.getWorldBorder().setWarningTime(â˜ƒ.getWarningDelay());
   }

   @Override
   public void handleTitlesClear(ClientboundClearTitlesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.clear();
      if (â˜ƒ.shouldResetTimes()) {
         this.minecraft.gui.resetTitleTimes();
      }
   }

   @Override
   public void setActionBarText(ClientboundSetActionBarTextPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.setOverlayMessage(â˜ƒ.getText(), false);
   }

   @Override
   public void setTitleText(ClientboundSetTitleTextPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.setTitle(â˜ƒ.getText());
   }

   @Override
   public void setSubtitleText(ClientboundSetSubtitleTextPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.setSubtitle(â˜ƒ.getText());
   }

   @Override
   public void setTitlesAnimation(ClientboundSetTitlesAnimationPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.setTimes(â˜ƒ.getFadeIn(), â˜ƒ.getStay(), â˜ƒ.getFadeOut());
   }

   @Override
   public void handleTabListCustomisation(ClientboundTabListPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.getTabList().setHeader(â˜ƒ.getHeader().getString().isEmpty() ? null : â˜ƒ.getHeader());
      this.minecraft.gui.getTabList().setFooter(â˜ƒ.getFooter().getString().isEmpty() ? null : â˜ƒ.getFooter());
   }

   @Override
   public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = â˜ƒ.getEntity(this.level);
      if (â˜ƒ instanceof LivingEntity) {
         ((LivingEntity)â˜ƒ).removeEffectNoUpdate(â˜ƒ.getEffect());
      }
   }

   @Override
   public void handlePlayerInfo(ClientboundPlayerInfoPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);

      for(ClientboundPlayerInfoPacket.PlayerUpdate â˜ƒ : â˜ƒ.getEntries()) {
         if (â˜ƒ.getAction() == ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER) {
            this.minecraft.getPlayerSocialManager().removePlayer(â˜ƒ.getProfile().getId());
            this.playerInfoMap.remove(â˜ƒ.getProfile().getId());
         } else {
            PlayerInfo â˜ƒx = (PlayerInfo)this.playerInfoMap.get(â˜ƒ.getProfile().getId());
            if (â˜ƒ.getAction() == ClientboundPlayerInfoPacket.Action.ADD_PLAYER) {
               â˜ƒx = new PlayerInfo(â˜ƒ);
               this.playerInfoMap.put(â˜ƒx.getProfile().getId(), â˜ƒx);
               this.minecraft.getPlayerSocialManager().addPlayer(â˜ƒx);
            }

            if (â˜ƒx != null) {
               switch(â˜ƒ.getAction()) {
                  case ADD_PLAYER:
                     â˜ƒx.setGameMode(â˜ƒ.getGameMode());
                     â˜ƒx.setLatency(â˜ƒ.getLatency());
                     â˜ƒx.setTabListDisplayName(â˜ƒ.getDisplayName());
                     break;
                  case UPDATE_GAME_MODE:
                     â˜ƒx.setGameMode(â˜ƒ.getGameMode());
                     break;
                  case UPDATE_LATENCY:
                     â˜ƒx.setLatency(â˜ƒ.getLatency());
                     break;
                  case UPDATE_DISPLAY_NAME:
                     â˜ƒx.setTabListDisplayName(â˜ƒ.getDisplayName());
               }
            }
         }
      }
   }

   @Override
   public void handleKeepAlive(ClientboundKeepAlivePacket var1) {
      this.send(new ServerboundKeepAlivePacket(â˜ƒ.getId()));
   }

   @Override
   public void handlePlayerAbilities(ClientboundPlayerAbilitiesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Player â˜ƒ = this.minecraft.player;
      â˜ƒ.getAbilities().flying = â˜ƒ.isFlying();
      â˜ƒ.getAbilities().instabuild = â˜ƒ.canInstabuild();
      â˜ƒ.getAbilities().invulnerable = â˜ƒ.isInvulnerable();
      â˜ƒ.getAbilities().mayfly = â˜ƒ.canFly();
      â˜ƒ.getAbilities().setFlyingSpeed(â˜ƒ.getFlyingSpeed());
      â˜ƒ.getAbilities().setWalkingSpeed(â˜ƒ.getWalkingSpeed());
   }

   @Override
   public void handleSoundEvent(ClientboundSoundPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft
         .level
         .playSound(this.minecraft.player, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getSound(), â˜ƒ.getSource(), â˜ƒ.getVolume(), â˜ƒ.getPitch());
   }

   @Override
   public void handleSoundEntityEvent(ClientboundSoundEntityPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getId());
      if (â˜ƒ != null) {
         this.minecraft.level.playSound(this.minecraft.player, â˜ƒ, â˜ƒ.getSound(), â˜ƒ.getSource(), â˜ƒ.getVolume(), â˜ƒ.getPitch());
      }
   }

   @Override
   public void handleCustomSoundEvent(ClientboundCustomSoundPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft
         .getSoundManager()
         .play(
            new SimpleSoundInstance(
               â˜ƒ.getName(),
               â˜ƒ.getSource(),
               â˜ƒ.getVolume(),
               â˜ƒ.getPitch(),
               false,
               0,
               SoundInstance.Attenuation.LINEAR,
               â˜ƒ.getX(),
               â˜ƒ.getY(),
               â˜ƒ.getZ(),
               false
            )
         );
   }

   @Override
   public void handleResourcePack(ClientboundResourcePackPacket var1) {
      String â˜ƒ = â˜ƒ.getUrl();
      String â˜ƒx = â˜ƒ.getHash();
      boolean â˜ƒxx = â˜ƒ.isRequired();
      if (this.validateResourcePackUrl(â˜ƒ)) {
         if (â˜ƒ.startsWith("level://")) {
            try {
               String â˜ƒxxx = URLDecoder.decode(â˜ƒ.substring("level://".length()), StandardCharsets.UTF_8.toString());
               File â˜ƒxxxx = new File(this.minecraft.gameDirectory, "saves");
               File â˜ƒxxxxx = new File(â˜ƒxxxx, â˜ƒxxx);
               if (â˜ƒxxxxx.isFile()) {
                  this.send(ServerboundResourcePackPacket.Action.ACCEPTED);
                  CompletableFuture<?> â˜ƒxxxxxx = this.minecraft.getClientPackSource().setServerPack(â˜ƒxxxxx, PackSource.WORLD);
                  this.downloadCallback(â˜ƒxxxxxx);
                  return;
               }
            } catch (UnsupportedEncodingException var9) {
            }

            this.send(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
         } else {
            ServerData â˜ƒxxx = this.minecraft.getCurrentServer();
            if (â˜ƒxxx != null && â˜ƒxxx.getResourcePackStatus() == ServerData.ServerPackStatus.ENABLED) {
               this.send(ServerboundResourcePackPacket.Action.ACCEPTED);
               this.downloadCallback(this.minecraft.getClientPackSource().downloadAndSelectResourcePack(â˜ƒ, â˜ƒx, true));
            } else if (â˜ƒxxx != null
               && â˜ƒxxx.getResourcePackStatus() != ServerData.ServerPackStatus.PROMPT
               && (!â˜ƒxx || â˜ƒxxx.getResourcePackStatus() != ServerData.ServerPackStatus.DISABLED)) {
               this.send(ServerboundResourcePackPacket.Action.DECLINED);
               if (â˜ƒxx) {
                  this.connection.disconnect(new TranslatableComponent("multiplayer.requiredTexturePrompt.disconnect"));
               }
            } else {
               this.minecraft
                  .execute(
                     () -> this.minecraft
                           .setScreen(
                              new ConfirmScreen(
                                 var4x -> {
                                    this.minecraft.setScreen(null);
                                    ServerData â˜ƒ = this.minecraft.getCurrentServer();
                                    if (var4x) {
                                       if (â˜ƒ != null) {
                                          â˜ƒ.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
                                       }
               
                                       this.send(ServerboundResourcePackPacket.Action.ACCEPTED);
                                       this.downloadCallback(this.minecraft.getClientPackSource().downloadAndSelectResourcePack(â˜ƒ, â˜ƒ, true));
                                    } else {
                                       this.send(ServerboundResourcePackPacket.Action.DECLINED);
                                       if (â˜ƒ) {
                                          this.connection.disconnect(new TranslatableComponent("multiplayer.requiredTexturePrompt.disconnect"));
                                       } else if (â˜ƒ != null) {
                                          â˜ƒ.setResourcePackStatus(ServerData.ServerPackStatus.DISABLED);
                                       }
                                    }
               
                                    if (â˜ƒ != null) {
                                       ServerList.saveSingleServer(â˜ƒ);
                                    }
                                 },
                                 â˜ƒ
                                    ? new TranslatableComponent("multiplayer.requiredTexturePrompt.line1")
                                    : new TranslatableComponent("multiplayer.texturePrompt.line1"),
                                 preparePackPrompt(
                                    (Component)(â˜ƒ
                                       ? new TranslatableComponent("multiplayer.requiredTexturePrompt.line2")
                                          .withStyle(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.BOLD})
                                       : new TranslatableComponent("multiplayer.texturePrompt.line2")),
                                    â˜ƒ.getPrompt()
                                 ),
                                 â˜ƒ ? CommonComponents.GUI_PROCEED : CommonComponents.GUI_YES,
                                 (Component)(â˜ƒ ? new TranslatableComponent("menu.disconnect") : CommonComponents.GUI_NO)
                              )
                           )
                  );
            }
         }
      }
   }

   private static Component preparePackPrompt(Component var0, @Nullable Component var1) {
      return (Component)(â˜ƒ == null ? â˜ƒ : new TranslatableComponent("multiplayer.texturePrompt.serverPrompt", â˜ƒ, â˜ƒ));
   }

   private boolean validateResourcePackUrl(String var1) {
      try {
         URI â˜ƒ = new URI(â˜ƒ);
         String â˜ƒx = â˜ƒ.getScheme();
         boolean â˜ƒxx = "level".equals(â˜ƒx);
         if (!"http".equals(â˜ƒx) && !"https".equals(â˜ƒx) && !â˜ƒxx) {
            throw new URISyntaxException(â˜ƒ, "Wrong protocol");
         } else if (!â˜ƒxx || !â˜ƒ.contains("..") && â˜ƒ.endsWith("/resources.zip")) {
            return true;
         } else {
            throw new URISyntaxException(â˜ƒ, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException var5) {
         this.send(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
         return false;
      }
   }

   private void downloadCallback(CompletableFuture<?> var1) {
      â˜ƒ.thenRun(() -> this.send(ServerboundResourcePackPacket.Action.SUCCESSFULLY_LOADED)).exceptionally(var1x -> {
         this.send(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
         return null;
      });
   }

   private void send(ServerboundResourcePackPacket.Action var1) {
      this.connection.send(new ServerboundResourcePackPacket(â˜ƒ));
   }

   @Override
   public void handleBossUpdate(ClientboundBossEventPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gui.getBossOverlay().update(â˜ƒ);
   }

   @Override
   public void handleItemCooldown(ClientboundCooldownPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      if (â˜ƒ.getDuration() == 0) {
         this.minecraft.player.getCooldowns().removeCooldown(â˜ƒ.getItem());
      } else {
         this.minecraft.player.getCooldowns().addCooldown(â˜ƒ.getItem(), â˜ƒ.getDuration());
      }
   }

   @Override
   public void handleMoveVehicle(ClientboundMoveVehiclePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.minecraft.player.getRootVehicle();
      if (â˜ƒ != this.minecraft.player && â˜ƒ.isControlledByLocalInstance()) {
         â˜ƒ.absMoveTo(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot());
         this.connection.send(new ServerboundMoveVehiclePacket(â˜ƒ));
      }
   }

   @Override
   public void handleOpenBook(ClientboundOpenBookPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      ItemStack â˜ƒ = this.minecraft.player.getItemInHand(â˜ƒ.getHand());
      if (â˜ƒ.is(Items.WRITTEN_BOOK)) {
         this.minecraft.setScreen(new BookViewScreen(new BookViewScreen.WrittenBookAccess(â˜ƒ)));
      }
   }

   @Override
   public void handleCustomPayload(ClientboundCustomPayloadPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      ResourceLocation â˜ƒ = â˜ƒ.getIdentifier();
      FriendlyByteBuf â˜ƒx = null;

      try {
         â˜ƒx = â˜ƒ.getData();
         if (ClientboundCustomPayloadPacket.BRAND.equals(â˜ƒ)) {
            this.minecraft.player.setServerBrand(â˜ƒx.readUtf());
         } else if (ClientboundCustomPayloadPacket.DEBUG_PATHFINDING_PACKET.equals(â˜ƒ)) {
            int â˜ƒxx = â˜ƒx.readInt();
            float â˜ƒxxx = â˜ƒx.readFloat();
            Path â˜ƒxxxx = Path.createFromStream(â˜ƒx);
            this.minecraft.debugRenderer.pathfindingRenderer.addPath(â˜ƒxx, â˜ƒxxxx, â˜ƒxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_NEIGHBORSUPDATE_PACKET.equals(â˜ƒ)) {
            long â˜ƒxx = â˜ƒx.readVarLong();
            BlockPos â˜ƒxxx = â˜ƒx.readBlockPos();
            ((NeighborsUpdateRenderer)this.minecraft.debugRenderer.neighborsUpdateRenderer).addUpdate(â˜ƒxx, â˜ƒxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_STRUCTURES_PACKET.equals(â˜ƒ)) {
            DimensionType â˜ƒxx = this.registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).get(â˜ƒx.readResourceLocation());
            BoundingBox â˜ƒxxx = new BoundingBox(â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt());
            int â˜ƒxxxx = â˜ƒx.readInt();
            List<BoundingBox> â˜ƒxxxxx = Lists.<BoundingBox>newArrayList();
            List<Boolean> â˜ƒxxxxxx = Lists.newArrayList();

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxx) {
               â˜ƒxxxxx.add(new BoundingBox(â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt(), â˜ƒx.readInt()));
               â˜ƒxxxxxx.add(â˜ƒx.readBoolean());
            }

            this.minecraft.debugRenderer.structureRenderer.addBoundingBox(â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_WORLDGENATTEMPT_PACKET.equals(â˜ƒ)) {
            ((WorldGenAttemptRenderer)this.minecraft.debugRenderer.worldGenAttemptRenderer)
               .addPos(â˜ƒx.readBlockPos(), â˜ƒx.readFloat(), â˜ƒx.readFloat(), â˜ƒx.readFloat(), â˜ƒx.readFloat(), â˜ƒx.readFloat());
         } else if (ClientboundCustomPayloadPacket.DEBUG_VILLAGE_SECTIONS.equals(â˜ƒ)) {
            int â˜ƒxx = â˜ƒx.readInt();

            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx; ++â˜ƒxxx) {
               this.minecraft.debugRenderer.villageSectionsDebugRenderer.setVillageSection(â˜ƒx.readSectionPos());
            }

            int â˜ƒxxx = â˜ƒx.readInt();

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
               this.minecraft.debugRenderer.villageSectionsDebugRenderer.setNotVillageSection(â˜ƒx.readSectionPos());
            }
         } else if (ClientboundCustomPayloadPacket.DEBUG_POI_ADDED_PACKET.equals(â˜ƒ)) {
            BlockPos â˜ƒxx = â˜ƒx.readBlockPos();
            String â˜ƒxxx = â˜ƒx.readUtf();
            int â˜ƒxxxx = â˜ƒx.readInt();
            BrainDebugRenderer.PoiInfo â˜ƒxxxxx = new BrainDebugRenderer.PoiInfo(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
            this.minecraft.debugRenderer.brainDebugRenderer.addPoi(â˜ƒxxxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_POI_REMOVED_PACKET.equals(â˜ƒ)) {
            BlockPos â˜ƒxx = â˜ƒx.readBlockPos();
            this.minecraft.debugRenderer.brainDebugRenderer.removePoi(â˜ƒxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_POI_TICKET_COUNT_PACKET.equals(â˜ƒ)) {
            BlockPos â˜ƒxx = â˜ƒx.readBlockPos();
            int â˜ƒxxx = â˜ƒx.readInt();
            this.minecraft.debugRenderer.brainDebugRenderer.setFreeTicketCount(â˜ƒxx, â˜ƒxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_GOAL_SELECTOR.equals(â˜ƒ)) {
            BlockPos â˜ƒxx = â˜ƒx.readBlockPos();
            int â˜ƒxxx = â˜ƒx.readInt();
            int â˜ƒxxxx = â˜ƒx.readInt();
            List<GoalSelectorDebugRenderer.DebugGoal> â˜ƒxxxxx = Lists.<GoalSelectorDebugRenderer.DebugGoal>newArrayList();

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxx) {
               int â˜ƒxxxxxxx = â˜ƒx.readInt();
               boolean â˜ƒxxxxxxxx = â˜ƒx.readBoolean();
               String â˜ƒxxxxxxxxx = â˜ƒx.readUtf(255);
               â˜ƒxxxxx.add(new GoalSelectorDebugRenderer.DebugGoal(â˜ƒxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx));
            }

            this.minecraft.debugRenderer.goalSelectorRenderer.addGoalSelector(â˜ƒxxx, â˜ƒxxxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_RAIDS.equals(â˜ƒ)) {
            int â˜ƒxx = â˜ƒx.readInt();
            Collection<BlockPos> â˜ƒxxx = Lists.<BlockPos>newArrayList();

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxx; ++â˜ƒxxxx) {
               â˜ƒxxx.add(â˜ƒx.readBlockPos());
            }

            this.minecraft.debugRenderer.raidDebugRenderer.setRaidCenters(â˜ƒxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_BRAIN.equals(â˜ƒ)) {
            double â˜ƒxxx = â˜ƒx.readDouble();
            double â˜ƒxxxx = â˜ƒx.readDouble();
            double â˜ƒxxxxx = â˜ƒx.readDouble();
            Position â˜ƒxxxxxx = new PositionImpl(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            UUID â˜ƒxxxxxxx = â˜ƒx.readUUID();
            int â˜ƒxxxxxxxx = â˜ƒx.readInt();
            String â˜ƒxxxxxxxxx = â˜ƒx.readUtf();
            String â˜ƒxxxxxxxxxx = â˜ƒx.readUtf();
            int â˜ƒxxxxxxxxxxx = â˜ƒx.readInt();
            float â˜ƒxxxxxxxxxxxx = â˜ƒx.readFloat();
            float â˜ƒxxxxxxxxxxxxx = â˜ƒx.readFloat();
            String â˜ƒxxxxxxxxxxxxxx = â˜ƒx.readUtf();
            boolean â˜ƒxxxxxxxxxxxxxxx = â˜ƒx.readBoolean();
            Path â˜ƒxx;
            if (â˜ƒxxxxxxxxxxxxxxx) {
               â˜ƒxx = Path.createFromStream(â˜ƒx);
            } else {
               â˜ƒxx = null;
            }

            boolean â˜ƒxx = â˜ƒx.readBoolean();
            BrainDebugRenderer.BrainDump â˜ƒxxx = new BrainDebugRenderer.BrainDump(
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxx,
               â˜ƒxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxx,
               â˜ƒxx
            );
            int â˜ƒxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx; ++â˜ƒxxxxx) {
               String â˜ƒxxxxxx = â˜ƒx.readUtf();
               â˜ƒxxx.activities.add(â˜ƒxxxxxx);
            }

            int â˜ƒxxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxx) {
               String â˜ƒxxxxxxx = â˜ƒx.readUtf();
               â˜ƒxxx.behaviors.add(â˜ƒxxxxxxx);
            }

            int â˜ƒxxxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxx) {
               String â˜ƒxxxxxxxx = â˜ƒx.readUtf();
               â˜ƒxxx.memories.add(â˜ƒxxxxxxxx);
            }

            int â˜ƒxxxxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxxxxxx; ++â˜ƒxxxxxxxx) {
               BlockPos â˜ƒxxxxxxxxx = â˜ƒx.readBlockPos();
               â˜ƒxxx.pois.add(â˜ƒxxxxxxxxx);
            }

            int â˜ƒxxxxxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxx) {
               BlockPos â˜ƒxxxxxxxxxx = â˜ƒx.readBlockPos();
               â˜ƒxxx.potentialPois.add(â˜ƒxxxxxxxxxx);
            }

            int â˜ƒxxxxxxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < â˜ƒxxxxxxxxx; ++â˜ƒxxxxxxxxxx) {
               String â˜ƒxxxxxxxxxxx = â˜ƒx.readUtf();
               â˜ƒxxx.gossips.add(â˜ƒxxxxxxxxxxx);
            }

            this.minecraft.debugRenderer.brainDebugRenderer.addOrUpdateBrainDump(â˜ƒxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_BEE.equals(â˜ƒ)) {
            double â˜ƒxx = â˜ƒx.readDouble();
            double â˜ƒxxx = â˜ƒx.readDouble();
            double â˜ƒxxxx = â˜ƒx.readDouble();
            Position â˜ƒxxxxx = new PositionImpl(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
            UUID â˜ƒxxxxxx = â˜ƒx.readUUID();
            int â˜ƒxxxxxxx = â˜ƒx.readInt();
            boolean â˜ƒxxxxxxxx = â˜ƒx.readBoolean();
            BlockPos â˜ƒxxxxxxxxx = null;
            if (â˜ƒxxxxxxxx) {
               â˜ƒxxxxxxxxx = â˜ƒx.readBlockPos();
            }

            boolean â˜ƒxx = â˜ƒx.readBoolean();
            BlockPos â˜ƒxxx = null;
            if (â˜ƒxx) {
               â˜ƒxxx = â˜ƒx.readBlockPos();
            }

            int â˜ƒxx = â˜ƒx.readInt();
            boolean â˜ƒxxx = â˜ƒx.readBoolean();
            Path â˜ƒxxxx = null;
            if (â˜ƒxxx) {
               â˜ƒxxxx = Path.createFromStream(â˜ƒx);
            }

            BeeDebugRenderer.BeeInfo â˜ƒxx = new BeeDebugRenderer.BeeInfo(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxxxxxxxx, â˜ƒxxx, â˜ƒxx);
            int â˜ƒxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
               String â˜ƒxxxxx = â˜ƒx.readUtf();
               â˜ƒxx.goals.add(â˜ƒxxxxx);
            }

            int â˜ƒxxxx = â˜ƒx.readVarInt();

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx; ++â˜ƒxxxxx) {
               BlockPos â˜ƒxxxxxx = â˜ƒx.readBlockPos();
               â˜ƒxx.blacklistedHives.add(â˜ƒxxxxxx);
            }

            this.minecraft.debugRenderer.beeDebugRenderer.addOrUpdateBeeInfo(â˜ƒxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_HIVE.equals(â˜ƒ)) {
            BlockPos â˜ƒxx = â˜ƒx.readBlockPos();
            String â˜ƒxxx = â˜ƒx.readUtf();
            int â˜ƒxxxx = â˜ƒx.readInt();
            int â˜ƒxxxxx = â˜ƒx.readInt();
            boolean â˜ƒxxxxxx = â˜ƒx.readBoolean();
            BeeDebugRenderer.HiveInfo â˜ƒxxxxxxx = new BeeDebugRenderer.HiveInfo(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, this.level.getGameTime());
            this.minecraft.debugRenderer.beeDebugRenderer.addOrUpdateHiveInfo(â˜ƒxxxxxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_GAME_TEST_CLEAR.equals(â˜ƒ)) {
            this.minecraft.debugRenderer.gameTestDebugRenderer.clear();
         } else if (ClientboundCustomPayloadPacket.DEBUG_GAME_TEST_ADD_MARKER.equals(â˜ƒ)) {
            BlockPos â˜ƒxx = â˜ƒx.readBlockPos();
            int â˜ƒxxx = â˜ƒx.readInt();
            String â˜ƒxxxx = â˜ƒx.readUtf();
            int â˜ƒxxxxx = â˜ƒx.readInt();
            this.minecraft.debugRenderer.gameTestDebugRenderer.addMarker(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_GAME_EVENT.equals(â˜ƒ)) {
            GameEvent â˜ƒxx = Registry.GAME_EVENT.get(new ResourceLocation(â˜ƒx.readUtf()));
            BlockPos â˜ƒxxx = â˜ƒx.readBlockPos();
            this.minecraft.debugRenderer.gameEventListenerRenderer.trackGameEvent(â˜ƒxx, â˜ƒxxx);
         } else if (ClientboundCustomPayloadPacket.DEBUG_GAME_EVENT_LISTENER.equals(â˜ƒ)) {
            ResourceLocation â˜ƒxx = â˜ƒx.readResourceLocation();
            PositionSource â˜ƒxxx = ((PositionSourceType)Registry.POSITION_SOURCE_TYPE
                  .getOptional(â˜ƒxx)
                  .orElseThrow(() -> new IllegalArgumentException("Unknown position source type " + â˜ƒ)))
               .read(â˜ƒx);
            int â˜ƒxxxx = â˜ƒx.readVarInt();
            this.minecraft.debugRenderer.gameEventListenerRenderer.trackListener(â˜ƒxxx, â˜ƒxxxx);
         } else {
            LOGGER.warn("Unknown custom packed identifier: {}", â˜ƒ);
         }
      } finally {
         if (â˜ƒx != null) {
            â˜ƒx.release();
         }
      }
   }

   @Override
   public void handleAddObjective(ClientboundSetObjectivePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Scoreboard â˜ƒ = this.level.getScoreboard();
      String â˜ƒx = â˜ƒ.getObjectiveName();
      if (â˜ƒ.getMethod() == 0) {
         â˜ƒ.addObjective(â˜ƒx, ObjectiveCriteria.DUMMY, â˜ƒ.getDisplayName(), â˜ƒ.getRenderType());
      } else if (â˜ƒ.hasObjective(â˜ƒx)) {
         Objective â˜ƒ = â˜ƒ.getObjective(â˜ƒx);
         if (â˜ƒ.getMethod() == 1) {
            â˜ƒ.removeObjective(â˜ƒ);
         } else if (â˜ƒ.getMethod() == 2) {
            â˜ƒ.setRenderType(â˜ƒ.getRenderType());
            â˜ƒ.setDisplayName(â˜ƒ.getDisplayName());
         }
      }
   }

   @Override
   public void handleSetScore(ClientboundSetScorePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Scoreboard â˜ƒ = this.level.getScoreboard();
      String â˜ƒx = â˜ƒ.getObjectiveName();
      switch(â˜ƒ.getMethod()) {
         case CHANGE:
            Objective â˜ƒxx = â˜ƒ.getOrCreateObjective(â˜ƒx);
            Score â˜ƒxxx = â˜ƒ.getOrCreatePlayerScore(â˜ƒ.getOwner(), â˜ƒxx);
            â˜ƒxxx.setScore(â˜ƒ.getScore());
            break;
         case REMOVE:
            â˜ƒ.resetPlayerScore(â˜ƒ.getOwner(), â˜ƒ.getObjective(â˜ƒx));
      }
   }

   @Override
   public void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Scoreboard â˜ƒ = this.level.getScoreboard();
      String â˜ƒx = â˜ƒ.getObjectiveName();
      Objective â˜ƒxx = â˜ƒx == null ? null : â˜ƒ.getOrCreateObjective(â˜ƒx);
      â˜ƒ.setDisplayObjective(â˜ƒ.getSlot(), â˜ƒxx);
   }

   @Override
   public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Scoreboard â˜ƒx = this.level.getScoreboard();
      ClientboundSetPlayerTeamPacket.Action â˜ƒxx = â˜ƒ.getTeamAction();
      PlayerTeam â˜ƒ;
      if (â˜ƒxx == ClientboundSetPlayerTeamPacket.Action.ADD) {
         â˜ƒ = â˜ƒx.addPlayerTeam(â˜ƒ.getName());
      } else {
         â˜ƒ = â˜ƒx.getPlayerTeam(â˜ƒ.getName());
         if (â˜ƒ == null) {
            LOGGER.warn("Received packet for unknown team {}: team action: {}, player action: {}", â˜ƒ.getName(), â˜ƒ.getTeamAction(), â˜ƒ.getPlayerAction());
            return;
         }
      }

      Optional<ClientboundSetPlayerTeamPacket.Parameters> â˜ƒ = â˜ƒ.getParameters();
      â˜ƒ.ifPresent(var1x -> {
         â˜ƒ.setDisplayName(var1x.getDisplayName());
         â˜ƒ.setColor(var1x.getColor());
         â˜ƒ.unpackOptions(var1x.getOptions());
         Team.Visibility â˜ƒ = Team.Visibility.byName(var1x.getNametagVisibility());
         if (â˜ƒ != null) {
            â˜ƒ.setNameTagVisibility(â˜ƒ);
         }

         Team.CollisionRule â˜ƒ = Team.CollisionRule.byName(var1x.getCollisionRule());
         if (â˜ƒ != null) {
            â˜ƒ.setCollisionRule(â˜ƒ);
         }

         â˜ƒ.setPlayerPrefix(var1x.getPlayerPrefix());
         â˜ƒ.setPlayerSuffix(var1x.getPlayerSuffix());
      });
      ClientboundSetPlayerTeamPacket.Action â˜ƒx = â˜ƒ.getPlayerAction();
      if (â˜ƒx == ClientboundSetPlayerTeamPacket.Action.ADD) {
         for(String â˜ƒxx : â˜ƒ.getPlayers()) {
            â˜ƒx.addPlayerToTeam(â˜ƒxx, â˜ƒ);
         }
      } else if (â˜ƒx == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
         for(String â˜ƒ : â˜ƒ.getPlayers()) {
            â˜ƒx.removePlayerFromTeam(â˜ƒ, â˜ƒ);
         }
      }

      if (â˜ƒxx == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
         â˜ƒx.removePlayerTeam(â˜ƒ);
      }
   }

   @Override
   public void handleParticleEvent(ClientboundLevelParticlesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      if (â˜ƒ.getCount() == 0) {
         double â˜ƒ = (double)(â˜ƒ.getMaxSpeed() * â˜ƒ.getXDist());
         double â˜ƒx = (double)(â˜ƒ.getMaxSpeed() * â˜ƒ.getYDist());
         double â˜ƒxx = (double)(â˜ƒ.getMaxSpeed() * â˜ƒ.getZDist());

         try {
            this.level.addParticle(â˜ƒ.getParticle(), â˜ƒ.isOverrideLimiter(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ, â˜ƒx, â˜ƒxx);
         } catch (Throwable var17) {
            LOGGER.warn("Could not spawn particle effect {}", â˜ƒ.getParticle());
         }
      } else {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getCount(); ++â˜ƒ) {
            double â˜ƒx = this.random.nextGaussian() * (double)â˜ƒ.getXDist();
            double â˜ƒxx = this.random.nextGaussian() * (double)â˜ƒ.getYDist();
            double â˜ƒxxx = this.random.nextGaussian() * (double)â˜ƒ.getZDist();
            double â˜ƒxxxx = this.random.nextGaussian() * (double)â˜ƒ.getMaxSpeed();
            double â˜ƒxxxxx = this.random.nextGaussian() * (double)â˜ƒ.getMaxSpeed();
            double â˜ƒxxxxxx = this.random.nextGaussian() * (double)â˜ƒ.getMaxSpeed();

            try {
               this.level
                  .addParticle(
                     â˜ƒ.getParticle(), â˜ƒ.isOverrideLimiter(), â˜ƒ.getX() + â˜ƒx, â˜ƒ.getY() + â˜ƒxx, â˜ƒ.getZ() + â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx
                  );
            } catch (Throwable var16) {
               LOGGER.warn("Could not spawn particle effect {}", â˜ƒ.getParticle());
               return;
            }
         }
      }
   }

   @Override
   public void handlePing(ClientboundPingPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.send(new ServerboundPongPacket(â˜ƒ.getId()));
   }

   @Override
   public void handleUpdateAttributes(ClientboundUpdateAttributesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getEntityId());
      if (â˜ƒ != null) {
         if (!(â˜ƒ instanceof LivingEntity)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + â˜ƒ + ")");
         } else {
            AttributeMap â˜ƒx = ((LivingEntity)â˜ƒ).getAttributes();

            for(ClientboundUpdateAttributesPacket.AttributeSnapshot â˜ƒxx : â˜ƒ.getValues()) {
               AttributeInstance â˜ƒxxx = â˜ƒx.getInstance(â˜ƒxx.getAttribute());
               if (â˜ƒxxx == null) {
                  LOGGER.warn("Entity {} does not have attribute {}", â˜ƒ, Registry.ATTRIBUTE.getKey(â˜ƒxx.getAttribute()));
               } else {
                  â˜ƒxxx.setBaseValue(â˜ƒxx.getBase());
                  â˜ƒxxx.removeModifiers();

                  for(AttributeModifier â˜ƒxxx : â˜ƒxx.getModifiers()) {
                     â˜ƒxxx.addTransientModifier(â˜ƒxxx);
                  }
               }
            }
         }
      }
   }

   @Override
   public void handlePlaceRecipe(ClientboundPlaceGhostRecipePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      AbstractContainerMenu â˜ƒ = this.minecraft.player.containerMenu;
      if (â˜ƒ.containerId == â˜ƒ.getContainerId()) {
         this.recipeManager.byKey(â˜ƒ.getRecipe()).ifPresent(var2x -> {
            if (this.minecraft.screen instanceof RecipeUpdateListener) {
               RecipeBookComponent â˜ƒ = ((RecipeUpdateListener)this.minecraft.screen).getRecipeBookComponent();
               â˜ƒ.setupGhostRecipe(var2x, â˜ƒ.slots);
            }
         });
      }
   }

   @Override
   public void handleLightUpdatePacked(ClientboundLightUpdatePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      LevelLightEngine â˜ƒxx = this.level.getChunkSource().getLightEngine();
      BitSet â˜ƒxxx = â˜ƒ.getSkyYMask();
      BitSet â˜ƒxxxx = â˜ƒ.getEmptySkyYMask();
      Iterator<byte[]> â˜ƒxxxxx = â˜ƒ.getSkyUpdates().iterator();
      this.readSectionList(â˜ƒ, â˜ƒx, â˜ƒxx, LightLayer.SKY, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ.getTrustEdges());
      BitSet â˜ƒxxxxxx = â˜ƒ.getBlockYMask();
      BitSet â˜ƒxxxxxxx = â˜ƒ.getEmptyBlockYMask();
      Iterator<byte[]> â˜ƒxxxxxxxx = â˜ƒ.getBlockUpdates().iterator();
      this.readSectionList(â˜ƒ, â˜ƒx, â˜ƒxx, LightLayer.BLOCK, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ.getTrustEdges());
   }

   @Override
   public void handleMerchantOffers(ClientboundMerchantOffersPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      AbstractContainerMenu â˜ƒx = this.minecraft.player.containerMenu;
      if (â˜ƒ.getContainerId() == â˜ƒx.containerId && â˜ƒx instanceof MerchantMenu â˜ƒ) {
         â˜ƒ.setOffers(new MerchantOffers(â˜ƒ.getOffers().createTag()));
         â˜ƒ.setXp(â˜ƒ.getVillagerXp());
         â˜ƒ.setMerchantLevel(â˜ƒ.getVillagerLevel());
         â˜ƒ.setShowProgressBar(â˜ƒ.showProgress());
         â˜ƒ.setCanRestock(â˜ƒ.canRestock());
      }
   }

   @Override
   public void handleSetChunkCacheRadius(ClientboundSetChunkCacheRadiusPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.serverChunkRadius = â˜ƒ.getRadius();
      this.level.getChunkSource().updateViewRadius(â˜ƒ.getRadius());
   }

   @Override
   public void handleSetChunkCacheCenter(ClientboundSetChunkCacheCenterPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.level.getChunkSource().updateViewCenter(â˜ƒ.getX(), â˜ƒ.getZ());
   }

   @Override
   public void handleBlockBreakAck(ClientboundBlockBreakAckPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.minecraft);
      this.minecraft.gameMode.handleBlockBreakAck(this.level, â˜ƒ.getPos(), â˜ƒ.getState(), â˜ƒ.action(), â˜ƒ.allGood());
   }

   private void readSectionList(int var1, int var2, LevelLightEngine var3, LightLayer var4, BitSet var5, BitSet var6, Iterator<byte[]> var7, boolean var8) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getLightSectionCount(); ++â˜ƒ) {
         int â˜ƒx = â˜ƒ.getMinLightSection() + â˜ƒ;
         boolean â˜ƒxx = â˜ƒ.get(â˜ƒ);
         boolean â˜ƒxxx = â˜ƒ.get(â˜ƒ);
         if (â˜ƒxx || â˜ƒxxx) {
            â˜ƒ.queueSectionData(â˜ƒ, SectionPos.of(â˜ƒ, â˜ƒx, â˜ƒ), â˜ƒxx ? new DataLayer((byte[])((byte[])â˜ƒ.next()).clone()) : new DataLayer(), â˜ƒ);
            this.level.setSectionDirtyWithNeighbors(â˜ƒ, â˜ƒx, â˜ƒ);
         }
      }
   }

   @Override
   public Connection getConnection() {
      return this.connection;
   }

   public Collection<PlayerInfo> getOnlinePlayers() {
      return this.playerInfoMap.values();
   }

   public Collection<UUID> getOnlinePlayerIds() {
      return this.playerInfoMap.keySet();
   }

   @Nullable
   public PlayerInfo getPlayerInfo(UUID var1) {
      return (PlayerInfo)this.playerInfoMap.get(â˜ƒ);
   }

   @Nullable
   public PlayerInfo getPlayerInfo(String var1) {
      for(PlayerInfo â˜ƒ : this.playerInfoMap.values()) {
         if (â˜ƒ.getProfile().getName().equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return null;
   }

   public GameProfile getLocalGameProfile() {
      return this.localGameProfile;
   }

   public ClientAdvancements getAdvancements() {
      return this.advancements;
   }

   public CommandDispatcher<SharedSuggestionProvider> getCommands() {
      return this.commands;
   }

   public ClientLevel getLevel() {
      return this.level;
   }

   public TagContainer getTags() {
      return this.tags;
   }

   public DebugQueryHandler getDebugQueryHandler() {
      return this.debugQueryHandler;
   }

   public UUID getId() {
      return this.id;
   }

   public Set<ResourceKey<Level>> levels() {
      return this.levels;
   }

   public RegistryAccess registryAccess() {
      return this.registryAccess;
   }
}
