package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerGamePacketListenerImpl implements ServerPlayerConnection, ServerGamePacketListener {
   static final Logger LOGGER = LogManager.getLogger();
   private static final int LATENCY_CHECK_INTERVAL = 15000;
   public final Connection connection;
   private final MinecraftServer server;
   public ServerPlayer player;
   private int tickCount;
   private long keepAliveTime;
   private boolean keepAlivePending;
   private long keepAliveChallenge;
   private int chatSpamTickCount;
   private int dropSpamTickCount;
   private double firstGoodX;
   private double firstGoodY;
   private double firstGoodZ;
   private double lastGoodX;
   private double lastGoodY;
   private double lastGoodZ;
   @Nullable
   private Entity lastVehicle;
   private double vehicleFirstGoodX;
   private double vehicleFirstGoodY;
   private double vehicleFirstGoodZ;
   private double vehicleLastGoodX;
   private double vehicleLastGoodY;
   private double vehicleLastGoodZ;
   @Nullable
   private Vec3 awaitingPositionFromClient;
   private int awaitingTeleport;
   private int awaitingTeleportTime;
   private boolean clientIsFloating;
   private int aboveGroundTickCount;
   private boolean clientVehicleIsFloating;
   private int aboveGroundVehicleTickCount;
   private int receivedMovePacketCount;
   private int knownMovePacketCount;

   public ServerGamePacketListenerImpl(MinecraftServer var1, Connection var2, ServerPlayer var3) {
      this.server = â˜ƒ;
      this.connection = â˜ƒ;
      â˜ƒ.setListener(this);
      this.player = â˜ƒ;
      â˜ƒ.connection = this;
      â˜ƒ.getTextFilter().join();
   }

   public void tick() {
      this.resetPosition();
      this.player.xo = this.player.getX();
      this.player.yo = this.player.getY();
      this.player.zo = this.player.getZ();
      this.player.doTick();
      this.player.absMoveTo(this.firstGoodX, this.firstGoodY, this.firstGoodZ, this.player.getYRot(), this.player.getXRot());
      ++this.tickCount;
      this.knownMovePacketCount = this.receivedMovePacketCount;
      if (this.clientIsFloating && !this.player.isSleeping()) {
         if (++this.aboveGroundTickCount > 80) {
            LOGGER.warn("{} was kicked for floating too long!", this.player.getName().getString());
            this.disconnect(new TranslatableComponent("multiplayer.disconnect.flying"));
            return;
         }
      } else {
         this.clientIsFloating = false;
         this.aboveGroundTickCount = 0;
      }

      this.lastVehicle = this.player.getRootVehicle();
      if (this.lastVehicle != this.player && this.lastVehicle.getControllingPassenger() == this.player) {
         this.vehicleFirstGoodX = this.lastVehicle.getX();
         this.vehicleFirstGoodY = this.lastVehicle.getY();
         this.vehicleFirstGoodZ = this.lastVehicle.getZ();
         this.vehicleLastGoodX = this.lastVehicle.getX();
         this.vehicleLastGoodY = this.lastVehicle.getY();
         this.vehicleLastGoodZ = this.lastVehicle.getZ();
         if (this.clientVehicleIsFloating && this.player.getRootVehicle().getControllingPassenger() == this.player) {
            if (++this.aboveGroundVehicleTickCount > 80) {
               LOGGER.warn("{} was kicked for floating a vehicle too long!", this.player.getName().getString());
               this.disconnect(new TranslatableComponent("multiplayer.disconnect.flying"));
               return;
            }
         } else {
            this.clientVehicleIsFloating = false;
            this.aboveGroundVehicleTickCount = 0;
         }
      } else {
         this.lastVehicle = null;
         this.clientVehicleIsFloating = false;
         this.aboveGroundVehicleTickCount = 0;
      }

      this.server.getProfiler().push("keepAlive");
      long â˜ƒ = Util.getMillis();
      if (â˜ƒ - this.keepAliveTime >= 15000L) {
         if (this.keepAlivePending) {
            this.disconnect(new TranslatableComponent("disconnect.timeout"));
         } else {
            this.keepAlivePending = true;
            this.keepAliveTime = â˜ƒ;
            this.keepAliveChallenge = â˜ƒ;
            this.send(new ClientboundKeepAlivePacket(this.keepAliveChallenge));
         }
      }

      this.server.getProfiler().pop();
      if (this.chatSpamTickCount > 0) {
         --this.chatSpamTickCount;
      }

      if (this.dropSpamTickCount > 0) {
         --this.dropSpamTickCount;
      }

      if (this.player.getLastActionTime() > 0L
         && this.server.getPlayerIdleTimeout() > 0
         && Util.getMillis() - this.player.getLastActionTime() > (long)(this.server.getPlayerIdleTimeout() * 1000 * 60)) {
         this.disconnect(new TranslatableComponent("multiplayer.disconnect.idling"));
      }
   }

   public void resetPosition() {
      this.firstGoodX = this.player.getX();
      this.firstGoodY = this.player.getY();
      this.firstGoodZ = this.player.getZ();
      this.lastGoodX = this.player.getX();
      this.lastGoodY = this.player.getY();
      this.lastGoodZ = this.player.getZ();
   }

   @Override
   public Connection getConnection() {
      return this.connection;
   }

   private boolean isSingleplayerOwner() {
      return this.server.isSingleplayerOwner(this.player.getGameProfile());
   }

   public void disconnect(Component var1) {
      this.connection.send(new ClientboundDisconnectPacket(â˜ƒ), var2 -> this.connection.disconnect(â˜ƒ));
      this.connection.setReadOnly();
      this.server.executeBlocking(this.connection::handleDisconnection);
   }

   private <T, R> void filterTextPacket(T var1, Consumer<R> var2, BiFunction<TextFilter, T, CompletableFuture<R>> var3) {
      BlockableEventLoop<?> â˜ƒ = this.player.getLevel().getServer();
      Consumer<R> â˜ƒx = var2x -> {
         if (this.getConnection().isConnected()) {
            â˜ƒ.accept(var2x);
         } else {
            LOGGER.debug("Ignoring packet due to disconnection");
         }
      };
      ((CompletableFuture)â˜ƒ.apply(this.player.getTextFilter(), â˜ƒ)).thenAcceptAsync(â˜ƒx, â˜ƒ);
   }

   private void filterTextPacket(String var1, Consumer<TextFilter.FilteredText> var2) {
      this.filterTextPacket(â˜ƒ, â˜ƒ, TextFilter::processStreamMessage);
   }

   private void filterTextPacket(List<String> var1, Consumer<List<TextFilter.FilteredText>> var2) {
      this.filterTextPacket(â˜ƒ, â˜ƒ, TextFilter::processMessageBundle);
   }

   @Override
   public void handlePlayerInput(ServerboundPlayerInputPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.setPlayerInput(â˜ƒ.getXxa(), â˜ƒ.getZza(), â˜ƒ.isJumping(), â˜ƒ.isShiftKeyDown());
   }

   private static boolean containsInvalidValues(double var0, double var2, double var4, float var6, float var7) {
      return Double.isNaN(â˜ƒ) || Double.isNaN(â˜ƒ) || Double.isNaN(â˜ƒ) || !Floats.isFinite(â˜ƒ) || !Floats.isFinite(â˜ƒ);
   }

   private static double clampHorizontal(double var0) {
      return Mth.clamp(â˜ƒ, -3.0E7, 3.0E7);
   }

   private static double clampVertical(double var0) {
      return Mth.clamp(â˜ƒ, -2.0E7, 2.0E7);
   }

   @Override
   public void handleMoveVehicle(ServerboundMoveVehiclePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (containsInvalidValues(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot())) {
         this.disconnect(new TranslatableComponent("multiplayer.disconnect.invalid_vehicle_movement"));
      } else {
         Entity â˜ƒ = this.player.getRootVehicle();
         if (â˜ƒ != this.player && â˜ƒ.getControllingPassenger() == this.player && â˜ƒ == this.lastVehicle) {
            ServerLevel â˜ƒx = this.player.getLevel();
            double â˜ƒxx = â˜ƒ.getX();
            double â˜ƒxxx = â˜ƒ.getY();
            double â˜ƒxxxx = â˜ƒ.getZ();
            double â˜ƒxxxxx = clampHorizontal(â˜ƒ.getX());
            double â˜ƒxxxxxx = clampVertical(â˜ƒ.getY());
            double â˜ƒxxxxxxx = clampHorizontal(â˜ƒ.getZ());
            float â˜ƒxxxxxxxx = Mth.wrapDegrees(â˜ƒ.getYRot());
            float â˜ƒxxxxxxxxx = Mth.wrapDegrees(â˜ƒ.getXRot());
            double â˜ƒxxxxxxxxxx = â˜ƒxxxxx - this.vehicleFirstGoodX;
            double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxx - this.vehicleFirstGoodY;
            double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxx - this.vehicleFirstGoodZ;
            double â˜ƒxxxxxxxxxxxxx = â˜ƒ.getDeltaMovement().lengthSqr();
            double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx;
            if (â˜ƒxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxx > 100.0 && !this.isSingleplayerOwner()) {
               LOGGER.warn(
                  "{} (vehicle of {}) moved too quickly! {},{},{}",
                  â˜ƒ.getName().getString(),
                  this.player.getName().getString(),
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx
               );
               this.connection.send(new ClientboundMoveVehiclePacket(â˜ƒ));
               return;
            }

            boolean â˜ƒx = â˜ƒx.noCollision(â˜ƒ, â˜ƒ.getBoundingBox().deflate(0.0625));
            â˜ƒxxxxxxxxxx = â˜ƒxxxxx - this.vehicleLastGoodX;
            â˜ƒxxxxxxxxxxx = â˜ƒxxxxxx - this.vehicleLastGoodY - 1.0E-6;
            â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxx - this.vehicleLastGoodZ;
            â˜ƒ.move(MoverType.PLAYER, new Vec3(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx));
            â˜ƒxxxxxxxxxx = â˜ƒxxxxx - â˜ƒ.getX();
            â˜ƒxxxxxxxxxxx = â˜ƒxxxxxx - â˜ƒ.getY();
            if (â˜ƒxxxxxxxxxxx > -0.5 || â˜ƒxxxxxxxxxxx < 0.5) {
               â˜ƒxxxxxxxxxxx = 0.0;
            }

            â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxx - â˜ƒ.getZ();
            â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx;
            boolean â˜ƒx = false;
            if (â˜ƒxxxxxxxxxxxxxx > 0.0625) {
               â˜ƒx = true;
               LOGGER.warn("{} (vehicle of {}) moved wrongly! {}", â˜ƒ.getName().getString(), this.player.getName().getString(), Math.sqrt(â˜ƒxxxxxxxxxxxxxx));
            }

            â˜ƒ.absMoveTo(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            boolean â˜ƒx = â˜ƒx.noCollision(â˜ƒ, â˜ƒ.getBoundingBox().deflate(0.0625));
            if (â˜ƒx && (â˜ƒx || !â˜ƒx)) {
               â˜ƒ.absMoveTo(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
               this.connection.send(new ClientboundMoveVehiclePacket(â˜ƒ));
               return;
            }

            this.player.getLevel().getChunkSource().move(this.player);
            this.player.checkMovementStatistics(this.player.getX() - â˜ƒxx, this.player.getY() - â˜ƒxxx, this.player.getZ() - â˜ƒxxxx);
            this.clientVehicleIsFloating = â˜ƒxxxxxxxxxxx >= -0.03125 && !this.server.isFlightAllowed() && this.noBlocksAround(â˜ƒ);
            this.vehicleLastGoodX = â˜ƒ.getX();
            this.vehicleLastGoodY = â˜ƒ.getY();
            this.vehicleLastGoodZ = â˜ƒ.getZ();
         }
      }
   }

   private boolean noBlocksAround(Entity var1) {
      return â˜ƒ.level.getBlockStates(â˜ƒ.getBoundingBox().inflate(0.0625).expandTowards(0.0, -0.55, 0.0)).allMatch(BlockBehaviour.BlockStateBase::isAir);
   }

   @Override
   public void handleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (â˜ƒ.getId() == this.awaitingTeleport) {
         this.player
            .absMoveTo(
               this.awaitingPositionFromClient.x,
               this.awaitingPositionFromClient.y,
               this.awaitingPositionFromClient.z,
               this.player.getYRot(),
               this.player.getXRot()
            );
         this.lastGoodX = this.awaitingPositionFromClient.x;
         this.lastGoodY = this.awaitingPositionFromClient.y;
         this.lastGoodZ = this.awaitingPositionFromClient.z;
         if (this.player.isChangingDimension()) {
            this.player.hasChangedDimension();
         }

         this.awaitingPositionFromClient = null;
      }
   }

   @Override
   public void handleRecipeBookSeenRecipePacket(ServerboundRecipeBookSeenRecipePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.server.getRecipeManager().byKey(â˜ƒ.getRecipe()).ifPresent(this.player.getRecipeBook()::removeHighlight);
   }

   @Override
   public void handleRecipeBookChangeSettingsPacket(ServerboundRecipeBookChangeSettingsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.getRecipeBook().setBookSetting(â˜ƒ.getBookType(), â˜ƒ.isOpen(), â˜ƒ.isFiltering());
   }

   @Override
   public void handleSeenAdvancements(ServerboundSeenAdvancementsPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (â˜ƒ.getAction() == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB) {
         ResourceLocation â˜ƒ = â˜ƒ.getTab();
         Advancement â˜ƒx = this.server.getAdvancements().getAdvancement(â˜ƒ);
         if (â˜ƒx != null) {
            this.player.getAdvancements().setSelectedTab(â˜ƒx);
         }
      }
   }

   @Override
   public void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      StringReader â˜ƒ = new StringReader(â˜ƒ.getCommand());
      if (â˜ƒ.canRead() && â˜ƒ.peek() == '/') {
         â˜ƒ.skip();
      }

      ParseResults<CommandSourceStack> â˜ƒ = this.server.getCommands().getDispatcher().parse(â˜ƒ, this.player.createCommandSourceStack());
      this.server
         .getCommands()
         .getDispatcher()
         .getCompletionSuggestions(â˜ƒ)
         .thenAccept(var2x -> this.connection.send(new ClientboundCommandSuggestionsPacket(â˜ƒ.getId(), var2x)));
   }

   @Override
   public void handleSetCommandBlock(ServerboundSetCommandBlockPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (!this.server.isCommandBlockEnabled()) {
         this.player.sendMessage(new TranslatableComponent("advMode.notEnabled"), Util.NIL_UUID);
      } else if (!this.player.canUseGameMasterBlocks()) {
         this.player.sendMessage(new TranslatableComponent("advMode.notAllowed"), Util.NIL_UUID);
      } else {
         BaseCommandBlock â˜ƒ = null;
         CommandBlockEntity â˜ƒx = null;
         BlockPos â˜ƒxx = â˜ƒ.getPos();
         BlockEntity â˜ƒxxx = this.player.level.getBlockEntity(â˜ƒxx);
         if (â˜ƒxxx instanceof CommandBlockEntity) {
            â˜ƒx = (CommandBlockEntity)â˜ƒxxx;
            â˜ƒ = â˜ƒx.getCommandBlock();
         }

         String â˜ƒ = â˜ƒ.getCommand();
         boolean â˜ƒx = â˜ƒ.isTrackOutput();
         if (â˜ƒ != null) {
            CommandBlockEntity.Mode â˜ƒ = â˜ƒx.getMode();
            BlockState â˜ƒ = this.player.level.getBlockState(â˜ƒxx);
            Direction â˜ƒ = â˜ƒ.getValue(CommandBlock.FACING);

            BlockState â˜ƒxx = (switch(â˜ƒ.getMode()) {
               case SEQUENCE -> Blocks.CHAIN_COMMAND_BLOCK.defaultBlockState();
               case AUTO -> Blocks.REPEATING_COMMAND_BLOCK.defaultBlockState();
               default -> Blocks.COMMAND_BLOCK.defaultBlockState();
            }).setValue(CommandBlock.FACING, â˜ƒ).setValue(CommandBlock.CONDITIONAL, Boolean.valueOf(â˜ƒ.isConditional()));
            if (â˜ƒxx != â˜ƒ) {
               this.player.level.setBlock(â˜ƒxx, â˜ƒxx, 2);
               â˜ƒxxx.setBlockState(â˜ƒxx);
               this.player.level.getChunkAt(â˜ƒxx).setBlockEntity(â˜ƒxxx);
            }

            â˜ƒ.setCommand(â˜ƒ);
            â˜ƒ.setTrackOutput(â˜ƒx);
            if (!â˜ƒx) {
               â˜ƒ.setLastOutput(null);
            }

            â˜ƒx.setAutomatic(â˜ƒ.isAutomatic());
            if (â˜ƒ != â˜ƒ.getMode()) {
               â˜ƒx.onModeSwitch();
            }

            â˜ƒ.onUpdated();
            if (!StringUtil.isNullOrEmpty(â˜ƒ)) {
               this.player.sendMessage(new TranslatableComponent("advMode.setCommand.success", â˜ƒ), Util.NIL_UUID);
            }
         }
      }
   }

   @Override
   public void handleSetCommandMinecart(ServerboundSetCommandMinecartPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (!this.server.isCommandBlockEnabled()) {
         this.player.sendMessage(new TranslatableComponent("advMode.notEnabled"), Util.NIL_UUID);
      } else if (!this.player.canUseGameMasterBlocks()) {
         this.player.sendMessage(new TranslatableComponent("advMode.notAllowed"), Util.NIL_UUID);
      } else {
         BaseCommandBlock â˜ƒ = â˜ƒ.getCommandBlock(this.player.level);
         if (â˜ƒ != null) {
            â˜ƒ.setCommand(â˜ƒ.getCommand());
            â˜ƒ.setTrackOutput(â˜ƒ.isTrackOutput());
            if (!â˜ƒ.isTrackOutput()) {
               â˜ƒ.setLastOutput(null);
            }

            â˜ƒ.onUpdated();
            this.player.sendMessage(new TranslatableComponent("advMode.setCommand.success", â˜ƒ.getCommand()), Util.NIL_UUID);
         }
      }
   }

   @Override
   public void handlePickItem(ServerboundPickItemPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.getInventory().pickSlot(â˜ƒ.getSlot());
      this.player
         .connection
         .send(
            new ClientboundContainerSetSlotPacket(
               -2, 0, this.player.getInventory().selected, this.player.getInventory().getItem(this.player.getInventory().selected)
            )
         );
      this.player.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, â˜ƒ.getSlot(), this.player.getInventory().getItem(â˜ƒ.getSlot())));
      this.player.connection.send(new ClientboundSetCarriedItemPacket(this.player.getInventory().selected));
   }

   @Override
   public void handleRenameItem(ServerboundRenameItemPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.containerMenu instanceof AnvilMenu â˜ƒ) {
         String â˜ƒx = SharedConstants.filterText(â˜ƒ.getName());
         if (â˜ƒx.length() <= 50) {
            â˜ƒ.setItemName(â˜ƒx);
         }
      }
   }

   @Override
   public void handleSetBeaconPacket(ServerboundSetBeaconPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.containerMenu instanceof BeaconMenu) {
         ((BeaconMenu)this.player.containerMenu).updateEffects(â˜ƒ.getPrimary(), â˜ƒ.getSecondary());
      }
   }

   @Override
   public void handleSetStructureBlock(ServerboundSetStructureBlockPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.canUseGameMasterBlocks()) {
         BlockPos â˜ƒx = â˜ƒ.getPos();
         BlockState â˜ƒxx = this.player.level.getBlockState(â˜ƒx);
         BlockEntity â˜ƒxxx = this.player.level.getBlockEntity(â˜ƒx);
         if (â˜ƒxxx instanceof StructureBlockEntity â˜ƒ) {
            â˜ƒ.setMode(â˜ƒ.getMode());
            â˜ƒ.setStructureName(â˜ƒ.getName());
            â˜ƒ.setStructurePos(â˜ƒ.getOffset());
            â˜ƒ.setStructureSize(â˜ƒ.getSize());
            â˜ƒ.setMirror(â˜ƒ.getMirror());
            â˜ƒ.setRotation(â˜ƒ.getRotation());
            â˜ƒ.setMetaData(â˜ƒ.getData());
            â˜ƒ.setIgnoreEntities(â˜ƒ.isIgnoreEntities());
            â˜ƒ.setShowAir(â˜ƒ.isShowAir());
            â˜ƒ.setShowBoundingBox(â˜ƒ.isShowBoundingBox());
            â˜ƒ.setIntegrity(â˜ƒ.getIntegrity());
            â˜ƒ.setSeed(â˜ƒ.getSeed());
            if (â˜ƒ.hasStructureName()) {
               String â˜ƒxxxx = â˜ƒ.getStructureName();
               if (â˜ƒ.getUpdateType() == StructureBlockEntity.UpdateType.SAVE_AREA) {
                  if (â˜ƒ.saveStructure()) {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.save_success", â˜ƒxxxx), false);
                  } else {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.save_failure", â˜ƒxxxx), false);
                  }
               } else if (â˜ƒ.getUpdateType() == StructureBlockEntity.UpdateType.LOAD_AREA) {
                  if (!â˜ƒ.isStructureLoadable()) {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.load_not_found", â˜ƒxxxx), false);
                  } else if (â˜ƒ.loadStructure(this.player.getLevel())) {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.load_success", â˜ƒxxxx), false);
                  } else {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.load_prepare", â˜ƒxxxx), false);
                  }
               } else if (â˜ƒ.getUpdateType() == StructureBlockEntity.UpdateType.SCAN_AREA) {
                  if (â˜ƒ.detectSize()) {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.size_success", â˜ƒxxxx), false);
                  } else {
                     this.player.displayClientMessage(new TranslatableComponent("structure_block.size_failure"), false);
                  }
               }
            } else {
               this.player.displayClientMessage(new TranslatableComponent("structure_block.invalid_structure_name", â˜ƒ.getName()), false);
            }

            â˜ƒ.setChanged();
            this.player.level.sendBlockUpdated(â˜ƒx, â˜ƒxx, â˜ƒxx, 3);
         }
      }
   }

   @Override
   public void handleSetJigsawBlock(ServerboundSetJigsawBlockPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.canUseGameMasterBlocks()) {
         BlockPos â˜ƒx = â˜ƒ.getPos();
         BlockState â˜ƒxx = this.player.level.getBlockState(â˜ƒx);
         BlockEntity â˜ƒxxx = this.player.level.getBlockEntity(â˜ƒx);
         if (â˜ƒxxx instanceof JigsawBlockEntity â˜ƒ) {
            â˜ƒ.setName(â˜ƒ.getName());
            â˜ƒ.setTarget(â˜ƒ.getTarget());
            â˜ƒ.setPool(â˜ƒ.getPool());
            â˜ƒ.setFinalState(â˜ƒ.getFinalState());
            â˜ƒ.setJoint(â˜ƒ.getJoint());
            â˜ƒ.setChanged();
            this.player.level.sendBlockUpdated(â˜ƒx, â˜ƒxx, â˜ƒxx, 3);
         }
      }
   }

   @Override
   public void handleJigsawGenerate(ServerboundJigsawGeneratePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.canUseGameMasterBlocks()) {
         BlockPos â˜ƒx = â˜ƒ.getPos();
         BlockEntity â˜ƒxx = this.player.level.getBlockEntity(â˜ƒx);
         if (â˜ƒxx instanceof JigsawBlockEntity â˜ƒ) {
            â˜ƒ.generate(this.player.getLevel(), â˜ƒ.levels(), â˜ƒ.keepJigsaws());
         }
      }
   }

   @Override
   public void handleSelectTrade(ServerboundSelectTradePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      int â˜ƒx = â˜ƒ.getItem();
      AbstractContainerMenu â˜ƒxx = this.player.containerMenu;
      if (â˜ƒxx instanceof MerchantMenu â˜ƒ) {
         â˜ƒ.setSelectionHint(â˜ƒx);
         â˜ƒ.tryMoveItems(â˜ƒx);
      }
   }

   @Override
   public void handleEditBook(ServerboundEditBookPacket var1) {
      int â˜ƒ = â˜ƒ.getSlot();
      if (Inventory.isHotbarSlot(â˜ƒ) || â˜ƒ == 40) {
         List<String> â˜ƒx = Lists.newArrayList();
         Optional<String> â˜ƒxx = â˜ƒ.getTitle();
         â˜ƒxx.ifPresent(â˜ƒx::add);
         â˜ƒ.getPages().stream().limit(100L).forEach(â˜ƒx::add);
         this.filterTextPacket(
            â˜ƒx,
            â˜ƒxx.isPresent()
               ? var2x -> this.signBook((TextFilter.FilteredText)var2x.get(0), var2x.subList(1, var2x.size()), â˜ƒ)
               : var2x -> this.updateBookContents(var2x, â˜ƒ)
         );
      }
   }

   private void updateBookContents(List<TextFilter.FilteredText> var1, int var2) {
      ItemStack â˜ƒ = this.player.getInventory().getItem(â˜ƒ);
      if (â˜ƒ.is(Items.WRITABLE_BOOK)) {
         this.updateBookPages(â˜ƒ, UnaryOperator.identity(), â˜ƒ);
      }
   }

   private void signBook(TextFilter.FilteredText var1, List<TextFilter.FilteredText> var2, int var3) {
      ItemStack â˜ƒ = this.player.getInventory().getItem(â˜ƒ);
      if (â˜ƒ.is(Items.WRITABLE_BOOK)) {
         ItemStack â˜ƒx = new ItemStack(Items.WRITTEN_BOOK);
         CompoundTag â˜ƒxx = â˜ƒ.getTag();
         if (â˜ƒxx != null) {
            â˜ƒx.setTag(â˜ƒxx.copy());
         }

         â˜ƒx.addTagElement("author", StringTag.valueOf(this.player.getName().getString()));
         if (this.player.isTextFilteringEnabled()) {
            â˜ƒx.addTagElement("title", StringTag.valueOf(â˜ƒ.getFiltered()));
         } else {
            â˜ƒx.addTagElement("filtered_title", StringTag.valueOf(â˜ƒ.getFiltered()));
            â˜ƒx.addTagElement("title", StringTag.valueOf(â˜ƒ.getRaw()));
         }

         this.updateBookPages(â˜ƒ, var0 -> Component.Serializer.toJson(new TextComponent(var0)), â˜ƒx);
         this.player.getInventory().setItem(â˜ƒ, â˜ƒx);
      }
   }

   private void updateBookPages(List<TextFilter.FilteredText> var1, UnaryOperator<String> var2, ItemStack var3) {
      ListTag â˜ƒ = new ListTag();
      if (this.player.isTextFilteringEnabled()) {
         â˜ƒ.stream().map(var1x -> StringTag.valueOf((String)â˜ƒ.apply(var1x.getFiltered()))).forEach(â˜ƒ::add);
      } else {
         CompoundTag â˜ƒ = new CompoundTag();
         int â˜ƒx = 0;

         for(int â˜ƒxx = â˜ƒ.size(); â˜ƒx < â˜ƒxx; ++â˜ƒx) {
            TextFilter.FilteredText â˜ƒxxx = (TextFilter.FilteredText)â˜ƒ.get(â˜ƒx);
            String â˜ƒxxxx = â˜ƒxxx.getRaw();
            â˜ƒ.add(StringTag.valueOf((String)â˜ƒ.apply(â˜ƒxxxx)));
            String â˜ƒxxxxx = â˜ƒxxx.getFiltered();
            if (!â˜ƒxxxx.equals(â˜ƒxxxxx)) {
               â˜ƒ.putString(String.valueOf(â˜ƒx), (String)â˜ƒ.apply(â˜ƒxxxxx));
            }
         }

         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.addTagElement("filtered_pages", â˜ƒ);
         }
      }

      â˜ƒ.addTagElement("pages", â˜ƒ);
   }

   @Override
   public void handleEntityTagQuery(ServerboundEntityTagQuery var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.hasPermissions(2)) {
         Entity â˜ƒ = this.player.getLevel().getEntity(â˜ƒ.getEntityId());
         if (â˜ƒ != null) {
            CompoundTag â˜ƒx = â˜ƒ.saveWithoutId(new CompoundTag());
            this.player.connection.send(new ClientboundTagQueryPacket(â˜ƒ.getTransactionId(), â˜ƒx));
         }
      }
   }

   @Override
   public void handleBlockEntityTagQuery(ServerboundBlockEntityTagQuery var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.hasPermissions(2)) {
         BlockEntity â˜ƒ = this.player.getLevel().getBlockEntity(â˜ƒ.getPos());
         CompoundTag â˜ƒx = â˜ƒ != null ? â˜ƒ.save(new CompoundTag()) : null;
         this.player.connection.send(new ClientboundTagQueryPacket(â˜ƒ.getTransactionId(), â˜ƒx));
      }
   }

   @Override
   public void handleMovePlayer(ServerboundMovePlayerPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (containsInvalidValues(â˜ƒ.getX(0.0), â˜ƒ.getY(0.0), â˜ƒ.getZ(0.0), â˜ƒ.getYRot(0.0F), â˜ƒ.getXRot(0.0F))) {
         this.disconnect(new TranslatableComponent("multiplayer.disconnect.invalid_player_movement"));
      } else {
         ServerLevel â˜ƒ = this.player.getLevel();
         if (!this.player.wonGame) {
            if (this.tickCount == 0) {
               this.resetPosition();
            }

            if (this.awaitingPositionFromClient != null) {
               if (this.tickCount - this.awaitingTeleportTime > 20) {
                  this.awaitingTeleportTime = this.tickCount;
                  this.teleport(
                     this.awaitingPositionFromClient.x,
                     this.awaitingPositionFromClient.y,
                     this.awaitingPositionFromClient.z,
                     this.player.getYRot(),
                     this.player.getXRot()
                  );
               }
            } else {
               this.awaitingTeleportTime = this.tickCount;
               double â˜ƒx = clampHorizontal(â˜ƒ.getX(this.player.getX()));
               double â˜ƒxx = clampVertical(â˜ƒ.getY(this.player.getY()));
               double â˜ƒxxx = clampHorizontal(â˜ƒ.getZ(this.player.getZ()));
               float â˜ƒxxxx = Mth.wrapDegrees(â˜ƒ.getYRot(this.player.getYRot()));
               float â˜ƒxxxxx = Mth.wrapDegrees(â˜ƒ.getXRot(this.player.getXRot()));
               if (this.player.isPassenger()) {
                  this.player.absMoveTo(this.player.getX(), this.player.getY(), this.player.getZ(), â˜ƒxxxx, â˜ƒxxxxx);
                  this.player.getLevel().getChunkSource().move(this.player);
               } else {
                  double â˜ƒx = this.player.getX();
                  double â˜ƒxx = this.player.getY();
                  double â˜ƒxxx = this.player.getZ();
                  double â˜ƒxxxx = this.player.getY();
                  double â˜ƒxxxxx = â˜ƒx - this.firstGoodX;
                  double â˜ƒxxxxxx = â˜ƒxx - this.firstGoodY;
                  double â˜ƒxxxxxxx = â˜ƒxxx - this.firstGoodZ;
                  double â˜ƒxxxxxxxx = this.player.getDeltaMovement().lengthSqr();
                  double â˜ƒxxxxxxxxx = â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxx * â˜ƒxxxxxxx;
                  if (this.player.isSleeping()) {
                     if (â˜ƒxxxxxxxxx > 1.0) {
                        this.teleport(this.player.getX(), this.player.getY(), this.player.getZ(), â˜ƒxxxx, â˜ƒxxxxx);
                     }
                  } else {
                     ++this.receivedMovePacketCount;
                     int â˜ƒx = this.receivedMovePacketCount - this.knownMovePacketCount;
                     if (â˜ƒx > 5) {
                        LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", this.player.getName().getString(), â˜ƒx);
                        â˜ƒx = 1;
                     }

                     if (!this.player.isChangingDimension()
                        && (!this.player.getLevel().getGameRules().getBoolean(GameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK) || !this.player.isFallFlying())) {
                        float â˜ƒx = this.player.isFallFlying() ? 300.0F : 100.0F;
                        if (â˜ƒxxxxxxxxx - â˜ƒxxxxxxxx > (double)(â˜ƒx * (float)â˜ƒx) && !this.isSingleplayerOwner()) {
                           LOGGER.warn("{} moved too quickly! {},{},{}", this.player.getName().getString(), â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
                           this.teleport(this.player.getX(), this.player.getY(), this.player.getZ(), this.player.getYRot(), this.player.getXRot());
                           return;
                        }
                     }

                     AABB â˜ƒx = this.player.getBoundingBox();
                     â˜ƒxxxxx = â˜ƒx - this.lastGoodX;
                     â˜ƒxxxxxx = â˜ƒxx - this.lastGoodY;
                     â˜ƒxxxxxxx = â˜ƒxxx - this.lastGoodZ;
                     boolean â˜ƒxx = â˜ƒxxxxxx > 0.0;
                     if (this.player.isOnGround() && !â˜ƒ.isOnGround() && â˜ƒxx) {
                        this.player.jumpFromGround();
                     }

                     this.player.move(MoverType.PLAYER, new Vec3(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx));
                     â˜ƒxxxxx = â˜ƒx - this.player.getX();
                     â˜ƒxxxxxx = â˜ƒxx - this.player.getY();
                     if (â˜ƒxxxxxx > -0.5 || â˜ƒxxxxxx < 0.5) {
                        â˜ƒxxxxxx = 0.0;
                     }

                     â˜ƒxxxxxxx = â˜ƒxxx - this.player.getZ();
                     â˜ƒxxxxxxxxx = â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxx * â˜ƒxxxxxxx;
                     boolean â˜ƒx = false;
                     if (!this.player.isChangingDimension()
                        && â˜ƒxxxxxxxxx > 0.0625
                        && !this.player.isSleeping()
                        && !this.player.gameMode.isCreative()
                        && this.player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
                        â˜ƒx = true;
                        LOGGER.warn("{} moved wrongly!", this.player.getName().getString());
                     }

                     this.player.absMoveTo(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
                     if (this.player.noPhysics
                        || this.player.isSleeping()
                        || (!â˜ƒx || !â˜ƒ.noCollision(this.player, â˜ƒx)) && !this.isPlayerCollidingWithAnythingNew(â˜ƒ, â˜ƒx)) {
                        this.clientIsFloating = â˜ƒxxxxxx >= -0.03125
                           && this.player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR
                           && !this.server.isFlightAllowed()
                           && !this.player.getAbilities().mayfly
                           && !this.player.hasEffect(MobEffects.LEVITATION)
                           && !this.player.isFallFlying()
                           && this.noBlocksAround(this.player);
                        this.player.getLevel().getChunkSource().move(this.player);
                        this.player.doCheckFallDamage(this.player.getY() - â˜ƒxxxx, â˜ƒ.isOnGround());
                        this.player.setOnGround(â˜ƒ.isOnGround());
                        if (â˜ƒxx) {
                           this.player.fallDistance = 0.0F;
                        }

                        this.player.checkMovementStatistics(this.player.getX() - â˜ƒx, this.player.getY() - â˜ƒxx, this.player.getZ() - â˜ƒxxx);
                        this.lastGoodX = this.player.getX();
                        this.lastGoodY = this.player.getY();
                        this.lastGoodZ = this.player.getZ();
                     } else {
                        this.teleport(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isPlayerCollidingWithAnythingNew(LevelReader var1, AABB var2) {
      Stream<VoxelShape> â˜ƒ = â˜ƒ.getCollisions(this.player, this.player.getBoundingBox().deflate(1.0E-5F), var0 -> true);
      VoxelShape â˜ƒx = Shapes.create(â˜ƒ.deflate(1.0E-5F));
      return â˜ƒ.anyMatch(var1x -> !Shapes.joinIsNotEmpty(var1x, â˜ƒ, BooleanOp.AND));
   }

   public void dismount(double var1, double var3, double var5, float var7, float var8) {
      this.teleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Collections.emptySet(), true);
   }

   public void teleport(double var1, double var3, double var5, float var7, float var8) {
      this.teleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Collections.emptySet(), false);
   }

   public void teleport(double var1, double var3, double var5, float var7, float var8, Set<ClientboundPlayerPositionPacket.RelativeArgument> var9) {
      this.teleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public void teleport(
      double var1, double var3, double var5, float var7, float var8, Set<ClientboundPlayerPositionPacket.RelativeArgument> var9, boolean var10
   ) {
      double â˜ƒ = â˜ƒ.contains(ClientboundPlayerPositionPacket.RelativeArgument.X) ? this.player.getX() : 0.0;
      double â˜ƒx = â˜ƒ.contains(ClientboundPlayerPositionPacket.RelativeArgument.Y) ? this.player.getY() : 0.0;
      double â˜ƒxx = â˜ƒ.contains(ClientboundPlayerPositionPacket.RelativeArgument.Z) ? this.player.getZ() : 0.0;
      float â˜ƒxxx = â˜ƒ.contains(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT) ? this.player.getYRot() : 0.0F;
      float â˜ƒxxxx = â˜ƒ.contains(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT) ? this.player.getXRot() : 0.0F;
      this.awaitingPositionFromClient = new Vec3(â˜ƒ, â˜ƒ, â˜ƒ);
      if (++this.awaitingTeleport == Integer.MAX_VALUE) {
         this.awaitingTeleport = 0;
      }

      this.awaitingTeleportTime = this.tickCount;
      this.player.absMoveTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.player
         .connection
         .send(new ClientboundPlayerPositionPacket(â˜ƒ - â˜ƒ, â˜ƒ - â˜ƒx, â˜ƒ - â˜ƒxx, â˜ƒ - â˜ƒxxx, â˜ƒ - â˜ƒxxxx, â˜ƒ, this.awaitingTeleport, â˜ƒ));
   }

   @Override
   public void handlePlayerAction(ServerboundPlayerActionPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      BlockPos â˜ƒ = â˜ƒ.getPos();
      this.player.resetLastActionTime();
      ServerboundPlayerActionPacket.Action â˜ƒx = â˜ƒ.getAction();
      switch(â˜ƒx) {
         case SWAP_ITEM_WITH_OFFHAND:
            if (!this.player.isSpectator()) {
               ItemStack â˜ƒxx = this.player.getItemInHand(InteractionHand.OFF_HAND);
               this.player.setItemInHand(InteractionHand.OFF_HAND, this.player.getItemInHand(InteractionHand.MAIN_HAND));
               this.player.setItemInHand(InteractionHand.MAIN_HAND, â˜ƒxx);
               this.player.stopUsingItem();
            }

            return;
         case DROP_ITEM:
            if (!this.player.isSpectator()) {
               this.player.drop(false);
            }

            return;
         case DROP_ALL_ITEMS:
            if (!this.player.isSpectator()) {
               this.player.drop(true);
            }

            return;
         case RELEASE_USE_ITEM:
            this.player.releaseUsingItem();
            return;
         case START_DESTROY_BLOCK:
         case ABORT_DESTROY_BLOCK:
         case STOP_DESTROY_BLOCK:
            this.player.gameMode.handleBlockBreakAction(â˜ƒ, â˜ƒx, â˜ƒ.getDirection(), this.player.level.getMaxBuildHeight());
            return;
         default:
            throw new IllegalArgumentException("Invalid player action");
      }
   }

   private static boolean wasBlockPlacementAttempt(ServerPlayer var0, ItemStack var1) {
      if (â˜ƒ.isEmpty()) {
         return false;
      } else {
         Item â˜ƒ = â˜ƒ.getItem();
         return (â˜ƒ instanceof BlockItem || â˜ƒ instanceof BucketItem) && !â˜ƒ.getCooldowns().isOnCooldown(â˜ƒ);
      }
   }

   @Override
   public void handleUseItemOn(ServerboundUseItemOnPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      ServerLevel â˜ƒ = this.player.getLevel();
      InteractionHand â˜ƒx = â˜ƒ.getHand();
      ItemStack â˜ƒxx = this.player.getItemInHand(â˜ƒx);
      BlockHitResult â˜ƒxxx = â˜ƒ.getHitResult();
      BlockPos â˜ƒxxxx = â˜ƒxxx.getBlockPos();
      Direction â˜ƒxxxxx = â˜ƒxxx.getDirection();
      this.player.resetLastActionTime();
      int â˜ƒxxxxxx = this.player.level.getMaxBuildHeight();
      if (â˜ƒxxxx.getY() < â˜ƒxxxxxx) {
         if (this.awaitingPositionFromClient == null
            && this.player.distanceToSqr((double)â˜ƒxxxx.getX() + 0.5, (double)â˜ƒxxxx.getY() + 0.5, (double)â˜ƒxxxx.getZ() + 0.5) < 64.0
            && â˜ƒ.mayInteract(this.player, â˜ƒxxxx)) {
            InteractionResult â˜ƒxxxxxxx = this.player.gameMode.useItemOn(this.player, â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒxxx);
            if (â˜ƒxxxxx == Direction.UP && !â˜ƒxxxxxxx.consumesAction() && â˜ƒxxxx.getY() >= â˜ƒxxxxxx - 1 && wasBlockPlacementAttempt(this.player, â˜ƒxx)) {
               Component â˜ƒxxxxxxxx = new TranslatableComponent("build.tooHigh", â˜ƒxxxxxx - 1).withStyle(ChatFormatting.RED);
               this.player.sendMessage(â˜ƒxxxxxxxx, ChatType.GAME_INFO, Util.NIL_UUID);
            } else if (â˜ƒxxxxxxx.shouldSwing()) {
               this.player.swing(â˜ƒx, true);
            }
         }
      } else {
         Component â˜ƒ = new TranslatableComponent("build.tooHigh", â˜ƒxxxxxx - 1).withStyle(ChatFormatting.RED);
         this.player.sendMessage(â˜ƒ, ChatType.GAME_INFO, Util.NIL_UUID);
      }

      this.player.connection.send(new ClientboundBlockUpdatePacket(â˜ƒ, â˜ƒxxxx));
      this.player.connection.send(new ClientboundBlockUpdatePacket(â˜ƒ, â˜ƒxxxx.relative(â˜ƒxxxxx)));
   }

   @Override
   public void handleUseItem(ServerboundUseItemPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      ServerLevel â˜ƒ = this.player.getLevel();
      InteractionHand â˜ƒx = â˜ƒ.getHand();
      ItemStack â˜ƒxx = this.player.getItemInHand(â˜ƒx);
      this.player.resetLastActionTime();
      if (!â˜ƒxx.isEmpty()) {
         InteractionResult â˜ƒxxx = this.player.gameMode.useItem(this.player, â˜ƒ, â˜ƒxx, â˜ƒx);
         if (â˜ƒxxx.shouldSwing()) {
            this.player.swing(â˜ƒx, true);
         }
      }
   }

   @Override
   public void handleTeleportToEntityPacket(ServerboundTeleportToEntityPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.isSpectator()) {
         for(ServerLevel â˜ƒ : this.server.getAllLevels()) {
            Entity â˜ƒx = â˜ƒ.getEntity(â˜ƒ);
            if (â˜ƒx != null) {
               this.player.teleportTo(â˜ƒ, â˜ƒx.getX(), â˜ƒx.getY(), â˜ƒx.getZ(), â˜ƒx.getYRot(), â˜ƒx.getXRot());
               return;
            }
         }
      }
   }

   @Override
   public void handleResourcePackResponse(ServerboundResourcePackPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (â˜ƒ.getAction() == ServerboundResourcePackPacket.Action.DECLINED && this.server.isResourcePackRequired()) {
         LOGGER.info("Disconnecting {} due to resource pack rejection", this.player.getName());
         this.disconnect(new TranslatableComponent("multiplayer.requiredTexturePrompt.disconnect"));
      }
   }

   @Override
   public void handlePaddleBoat(ServerboundPaddleBoatPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      Entity â˜ƒ = this.player.getVehicle();
      if (â˜ƒ instanceof Boat) {
         ((Boat)â˜ƒ).setPaddleState(â˜ƒ.getLeft(), â˜ƒ.getRight());
      }
   }

   @Override
   public void handlePong(ServerboundPongPacket var1) {
   }

   @Override
   public void onDisconnect(Component var1) {
      LOGGER.info("{} lost connection: {}", this.player.getName().getString(), â˜ƒ.getString());
      this.server.invalidateStatus();
      this.server
         .getPlayerList()
         .broadcastMessage(
            new TranslatableComponent("multiplayer.player.left", this.player.getDisplayName()).withStyle(ChatFormatting.YELLOW), ChatType.SYSTEM, Util.NIL_UUID
         );
      this.player.disconnect();
      this.server.getPlayerList().remove(this.player);
      this.player.getTextFilter().leave();
      if (this.isSingleplayerOwner()) {
         LOGGER.info("Stopping singleplayer server as player logged out");
         this.server.halt(false);
      }
   }

   @Override
   public void send(Packet<?> var1) {
      this.send(â˜ƒ, null);
   }

   public void send(Packet<?> var1, @Nullable GenericFutureListener<? extends Future<? super Void>> var2) {
      try {
         this.connection.send(â˜ƒ, â˜ƒ);
      } catch (Throwable var6) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var6, "Sending packet");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Packet being sent");
         â˜ƒx.setDetail("Packet class", (CrashReportDetail<String>)(() -> â˜ƒ.getClass().getCanonicalName()));
         throw new ReportedException(â˜ƒ);
      }
   }

   @Override
   public void handleSetCarriedItem(ServerboundSetCarriedItemPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (â˜ƒ.getSlot() >= 0 && â˜ƒ.getSlot() < Inventory.getSelectionSize()) {
         if (this.player.getInventory().selected != â˜ƒ.getSlot() && this.player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
            this.player.stopUsingItem();
         }

         this.player.getInventory().selected = â˜ƒ.getSlot();
         this.player.resetLastActionTime();
      } else {
         LOGGER.warn("{} tried to set an invalid carried item", this.player.getName().getString());
      }
   }

   @Override
   public void handleChat(ServerboundChatPacket var1) {
      String â˜ƒ = StringUtils.normalizeSpace(â˜ƒ.getMessage());

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length(); ++â˜ƒx) {
         if (!SharedConstants.isAllowedChatCharacter(â˜ƒ.charAt(â˜ƒx))) {
            this.disconnect(new TranslatableComponent("multiplayer.disconnect.illegal_characters"));
            return;
         }
      }

      if (â˜ƒ.startsWith("/")) {
         PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
         this.handleChat(TextFilter.FilteredText.passThrough(â˜ƒ));
      } else {
         this.filterTextPacket(â˜ƒ, this::handleChat);
      }
   }

   private void handleChat(TextFilter.FilteredText var1) {
      if (this.player.getChatVisibility() == ChatVisiblity.HIDDEN) {
         this.send(new ClientboundChatPacket(new TranslatableComponent("chat.disabled.options").withStyle(ChatFormatting.RED), ChatType.SYSTEM, Util.NIL_UUID));
      } else {
         this.player.resetLastActionTime();
         String â˜ƒ = â˜ƒ.getRaw();
         if (â˜ƒ.startsWith("/")) {
            this.handleCommand(â˜ƒ);
         } else {
            String â˜ƒ = â˜ƒ.getFiltered();
            Component â˜ƒx = â˜ƒ.isEmpty() ? null : new TranslatableComponent("chat.type.text", this.player.getDisplayName(), â˜ƒ);
            Component â˜ƒxx = new TranslatableComponent("chat.type.text", this.player.getDisplayName(), â˜ƒ);
            this.server
               .getPlayerList()
               .broadcastMessage(â˜ƒxx, var3x -> this.player.shouldFilterMessageTo(var3x) ? â˜ƒ : â˜ƒ, ChatType.CHAT, this.player.getUUID());
         }

         this.chatSpamTickCount += 20;
         if (this.chatSpamTickCount > 200 && !this.server.getPlayerList().isOp(this.player.getGameProfile())) {
            this.disconnect(new TranslatableComponent("disconnect.spam"));
         }
      }
   }

   private void handleCommand(String var1) {
      this.server.getCommands().performCommand(this.player.createCommandSourceStack(), â˜ƒ);
   }

   @Override
   public void handleAnimate(ServerboundSwingPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.resetLastActionTime();
      this.player.swing(â˜ƒ.getHand());
   }

   @Override
   public void handlePlayerCommand(ServerboundPlayerCommandPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.resetLastActionTime();
      switch(â˜ƒ.getAction()) {
         case PRESS_SHIFT_KEY:
            this.player.setShiftKeyDown(true);
            break;
         case RELEASE_SHIFT_KEY:
            this.player.setShiftKeyDown(false);
            break;
         case START_SPRINTING:
            this.player.setSprinting(true);
            break;
         case STOP_SPRINTING:
            this.player.setSprinting(false);
            break;
         case STOP_SLEEPING:
            if (this.player.isSleeping()) {
               this.player.stopSleepInBed(false, true);
               this.awaitingPositionFromClient = this.player.position();
            }
            break;
         case START_RIDING_JUMP:
            if (this.player.getVehicle() instanceof PlayerRideableJumping â˜ƒ) {
               int â˜ƒx = â˜ƒ.getData();
               if (â˜ƒ.canJump() && â˜ƒx > 0) {
                  â˜ƒ.handleStartJump(â˜ƒx);
               }
            }
            break;
         case STOP_RIDING_JUMP:
            if (this.player.getVehicle() instanceof PlayerRideableJumping â˜ƒ) {
               â˜ƒ.handleStopJump();
            }
            break;
         case OPEN_INVENTORY:
            if (this.player.getVehicle() instanceof AbstractHorse) {
               ((AbstractHorse)this.player.getVehicle()).openInventory(this.player);
            }
            break;
         case START_FALL_FLYING:
            if (!this.player.tryToStartFallFlying()) {
               this.player.stopFallFlying();
            }
            break;
         default:
            throw new IllegalArgumentException("Invalid client command!");
      }
   }

   @Override
   public void handleInteract(ServerboundInteractPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      ServerLevel â˜ƒ = this.player.getLevel();
      final Entity â˜ƒx = â˜ƒ.getTarget(â˜ƒ);
      this.player.resetLastActionTime();
      this.player.setShiftKeyDown(â˜ƒ.isUsingSecondaryAction());
      if (â˜ƒx != null) {
         double â˜ƒxx = 36.0;
         if (this.player.distanceToSqr(â˜ƒx) < 36.0) {
            â˜ƒ.dispatch(
               new ServerboundInteractPacket.Handler() {
                  private void performInteraction(InteractionHand var1, ServerGamePacketListenerImpl.EntityInteraction var2) {
                     ItemStack â˜ƒ = ServerGamePacketListenerImpl.this.player.getItemInHand(â˜ƒ).copy();
                     InteractionResult â˜ƒx = â˜ƒ.run(ServerGamePacketListenerImpl.this.player, â˜ƒ, â˜ƒ);
                     if (â˜ƒx.consumesAction()) {
                        CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(ServerGamePacketListenerImpl.this.player, â˜ƒ, â˜ƒ);
                        if (â˜ƒx.shouldSwing()) {
                           ServerGamePacketListenerImpl.this.player.swing(â˜ƒ, true);
                        }
                     }
                  }
   
                  @Override
                  public void onInteraction(InteractionHand var1) {
                     this.performInteraction(â˜ƒ, Player::interactOn);
                  }
   
                  @Override
                  public void onInteraction(InteractionHand var1, Vec3 var2) {
                     this.performInteraction(â˜ƒ, (var1x, var2x, var3x) -> var2x.interactAt(var1x, â˜ƒ, var3x));
                  }
   
                  @Override
                  public void onAttack() {
                     if (!(â˜ƒ instanceof ItemEntity)
                        && !(â˜ƒ instanceof ExperienceOrb)
                        && !(â˜ƒ instanceof AbstractArrow)
                        && â˜ƒ != ServerGamePacketListenerImpl.this.player) {
                        ServerGamePacketListenerImpl.this.player.attack(â˜ƒ);
                     } else {
                        ServerGamePacketListenerImpl.this.disconnect(new TranslatableComponent("multiplayer.disconnect.invalid_entity_attacked"));
                        ServerGamePacketListenerImpl.LOGGER
                           .warn("Player {} tried to attack an invalid entity", ServerGamePacketListenerImpl.this.player.getName().getString());
                     }
                  }
               }
            );
         }
      }
   }

   @Override
   public void handleClientCommand(ServerboundClientCommandPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.resetLastActionTime();
      ServerboundClientCommandPacket.Action â˜ƒ = â˜ƒ.getAction();
      switch(â˜ƒ) {
         case PERFORM_RESPAWN:
            if (this.player.wonGame) {
               this.player.wonGame = false;
               this.player = this.server.getPlayerList().respawn(this.player, true);
               CriteriaTriggers.CHANGED_DIMENSION.trigger(this.player, Level.END, Level.OVERWORLD);
            } else {
               if (this.player.getHealth() > 0.0F) {
                  return;
               }

               this.player = this.server.getPlayerList().respawn(this.player, false);
               if (this.server.isHardcore()) {
                  this.player.setGameMode(GameType.SPECTATOR);
                  this.player.getLevel().getGameRules().getRule(GameRules.RULE_SPECTATORSGENERATECHUNKS).set(false, this.server);
               }
            }
            break;
         case REQUEST_STATS:
            this.player.getStats().sendStats(this.player);
      }
   }

   @Override
   public void handleContainerClose(ServerboundContainerClosePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.doCloseContainer();
   }

   @Override
   public void handleContainerClick(ServerboundContainerClickPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.resetLastActionTime();
      if (this.player.containerMenu.containerId == â˜ƒ.getContainerId()) {
         if (this.player.isSpectator()) {
            this.player.containerMenu.sendAllDataToRemote();
         } else {
            boolean â˜ƒ = â˜ƒ.getStateId() != this.player.containerMenu.getStateId();
            this.player.containerMenu.suppressRemoteUpdates();
            this.player.containerMenu.clicked(â˜ƒ.getSlotNum(), â˜ƒ.getButtonNum(), â˜ƒ.getClickType(), this.player);

            for(Entry<ItemStack> â˜ƒx : Int2ObjectMaps.fastIterable(â˜ƒ.getChangedSlots())) {
               this.player.containerMenu.setRemoteSlotNoCopy(â˜ƒx.getIntKey(), (ItemStack)â˜ƒx.getValue());
            }

            this.player.containerMenu.setRemoteCarried(â˜ƒ.getCarriedItem());
            this.player.containerMenu.resumeRemoteUpdates();
            if (â˜ƒ) {
               this.player.containerMenu.broadcastFullState();
            } else {
               this.player.containerMenu.broadcastChanges();
            }
         }
      }
   }

   @Override
   public void handlePlaceRecipe(ServerboundPlaceRecipePacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.resetLastActionTime();
      if (!this.player.isSpectator() && this.player.containerMenu.containerId == â˜ƒ.getContainerId() && this.player.containerMenu instanceof RecipeBookMenu) {
         this.server
            .getRecipeManager()
            .byKey(â˜ƒ.getRecipe())
            .ifPresent(var2 -> ((RecipeBookMenu)this.player.containerMenu).handlePlacement(â˜ƒ.isShiftDown(), var2, this.player));
      }
   }

   @Override
   public void handleContainerButtonClick(ServerboundContainerButtonClickPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.resetLastActionTime();
      if (this.player.containerMenu.containerId == â˜ƒ.getContainerId() && !this.player.isSpectator()) {
         this.player.containerMenu.clickMenuButton(this.player, â˜ƒ.getButtonId());
         this.player.containerMenu.broadcastChanges();
      }
   }

   @Override
   public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.gameMode.isCreative()) {
         boolean â˜ƒ = â˜ƒ.getSlotNum() < 0;
         ItemStack â˜ƒx = â˜ƒ.getItem();
         CompoundTag â˜ƒxx = â˜ƒx.getTagElement("BlockEntityTag");
         if (!â˜ƒx.isEmpty() && â˜ƒxx != null && â˜ƒxx.contains("x") && â˜ƒxx.contains("y") && â˜ƒxx.contains("z")) {
            BlockPos â˜ƒxxx = new BlockPos(â˜ƒxx.getInt("x"), â˜ƒxx.getInt("y"), â˜ƒxx.getInt("z"));
            BlockEntity â˜ƒxxxx = this.player.level.getBlockEntity(â˜ƒxxx);
            if (â˜ƒxxxx != null) {
               CompoundTag â˜ƒxxxxx = â˜ƒxxxx.save(new CompoundTag());
               â˜ƒxxxxx.remove("x");
               â˜ƒxxxxx.remove("y");
               â˜ƒxxxxx.remove("z");
               â˜ƒx.addTagElement("BlockEntityTag", â˜ƒxxxxx);
            }
         }

         boolean â˜ƒ = â˜ƒ.getSlotNum() >= 1 && â˜ƒ.getSlotNum() <= 45;
         boolean â˜ƒx = â˜ƒx.isEmpty() || â˜ƒx.getDamageValue() >= 0 && â˜ƒx.getCount() <= 64 && !â˜ƒx.isEmpty();
         if (â˜ƒ && â˜ƒx) {
            this.player.inventoryMenu.getSlot(â˜ƒ.getSlotNum()).set(â˜ƒx);
            this.player.inventoryMenu.broadcastChanges();
         } else if (â˜ƒ && â˜ƒx && this.dropSpamTickCount < 200) {
            this.dropSpamTickCount += 20;
            this.player.drop(â˜ƒx, true);
         }
      }
   }

   @Override
   public void handleSignUpdate(ServerboundSignUpdatePacket var1) {
      List<String> â˜ƒ = (List)Stream.of(â˜ƒ.getLines()).map(ChatFormatting::stripFormatting).collect(Collectors.toList());
      this.filterTextPacket(â˜ƒ, var2x -> this.updateSignText(â˜ƒ, var2x));
   }

   private void updateSignText(ServerboundSignUpdatePacket var1, List<TextFilter.FilteredText> var2) {
      this.player.resetLastActionTime();
      ServerLevel â˜ƒ = this.player.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getPos();
      if (â˜ƒ.hasChunkAt(â˜ƒx)) {
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         BlockEntity â˜ƒxxx = â˜ƒ.getBlockEntity(â˜ƒx);
         if (!(â˜ƒxxx instanceof SignBlockEntity)) {
            return;
         }

         SignBlockEntity â˜ƒxx = (SignBlockEntity)â˜ƒxxx;
         if (!â˜ƒxx.isEditable() || !this.player.getUUID().equals(â˜ƒxx.getPlayerWhoMayEdit())) {
            LOGGER.warn("Player {} just tried to change non-editable sign", this.player.getName().getString());
            return;
         }

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
            TextFilter.FilteredText â˜ƒxxx = (TextFilter.FilteredText)â˜ƒ.get(â˜ƒxx);
            if (this.player.isTextFilteringEnabled()) {
               â˜ƒxx.setMessage(â˜ƒxx, new TextComponent(â˜ƒxxx.getFiltered()));
            } else {
               â˜ƒxx.setMessage(â˜ƒxx, new TextComponent(â˜ƒxxx.getRaw()), new TextComponent(â˜ƒxxx.getFiltered()));
            }
         }

         â˜ƒxx.setChanged();
         â˜ƒ.sendBlockUpdated(â˜ƒx, â˜ƒxx, â˜ƒxx, 3);
      }
   }

   @Override
   public void handleKeepAlive(ServerboundKeepAlivePacket var1) {
      if (this.keepAlivePending && â˜ƒ.getId() == this.keepAliveChallenge) {
         int â˜ƒ = (int)(Util.getMillis() - this.keepAliveTime);
         this.player.latency = (this.player.latency * 3 + â˜ƒ) / 4;
         this.keepAlivePending = false;
      } else if (!this.isSingleplayerOwner()) {
         this.disconnect(new TranslatableComponent("disconnect.timeout"));
      }
   }

   @Override
   public void handlePlayerAbilities(ServerboundPlayerAbilitiesPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.getAbilities().flying = â˜ƒ.isFlying() && this.player.getAbilities().mayfly;
   }

   @Override
   public void handleClientInformation(ServerboundClientInformationPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      this.player.updateOptions(â˜ƒ);
   }

   @Override
   public void handleCustomPayload(ServerboundCustomPayloadPacket var1) {
   }

   @Override
   public void handleChangeDifficulty(ServerboundChangeDifficultyPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.hasPermissions(2) || this.isSingleplayerOwner()) {
         this.server.setDifficulty(â˜ƒ.getDifficulty(), false);
      }
   }

   @Override
   public void handleLockDifficulty(ServerboundLockDifficultyPacket var1) {
      PacketUtils.ensureRunningOnSameThread(â˜ƒ, this, this.player.getLevel());
      if (this.player.hasPermissions(2) || this.isSingleplayerOwner()) {
         this.server.setDifficultyLocked(â˜ƒ.isLocked());
      }
   }

   @Override
   public ServerPlayer getPlayer() {
      return this.player;
   }

   @FunctionalInterface
   interface EntityInteraction {
      InteractionResult run(ServerPlayer var1, Entity var2, InteractionHand var3);
   }
}
