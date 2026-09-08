package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import io.netty.util.concurrent.Future;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ComplexItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ServerItemCooldowns;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayer extends Player {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int NEUTRAL_MOB_DEATH_NOTIFICATION_RADII_XZ = 32;
   private static final int NEUTRAL_MOB_DEATH_NOTIFICATION_RADII_Y = 10;
   public ServerGamePacketListenerImpl connection;
   public final MinecraftServer server;
   public final ServerPlayerGameMode gameMode;
   private final PlayerAdvancements advancements;
   private final ServerStatsCounter stats;
   private float lastRecordedHealthAndAbsorption = Float.MIN_VALUE;
   private int lastRecordedFoodLevel = Integer.MIN_VALUE;
   private int lastRecordedAirLevel = Integer.MIN_VALUE;
   private int lastRecordedArmor = Integer.MIN_VALUE;
   private int lastRecordedLevel = Integer.MIN_VALUE;
   private int lastRecordedExperience = Integer.MIN_VALUE;
   private float lastSentHealth = -1.0E8F;
   private int lastSentFood = -99999999;
   private boolean lastFoodSaturationZero = true;
   private int lastSentExp = -99999999;
   private int spawnInvulnerableTime = 60;
   private ChatVisiblity chatVisibility = ChatVisiblity.FULL;
   private boolean canChatColor = true;
   private long lastActionTime = Util.getMillis();
   private Entity camera;
   private boolean isChangingDimension;
   private boolean seenCredits;
   private final ServerRecipeBook recipeBook = new ServerRecipeBook();
   private Vec3 levitationStartPos;
   private int levitationStartTime;
   private boolean disconnected;
   @Nullable
   private Vec3 enteredNetherPosition;
   private SectionPos lastSectionPos = SectionPos.of(0, 0, 0);
   private ResourceKey<Level> respawnDimension = Level.OVERWORLD;
   @Nullable
   private BlockPos respawnPosition;
   private boolean respawnForced;
   private float respawnAngle;
   private final TextFilter textFilter;
   private boolean textFilteringEnabled;
   private final ContainerSynchronizer containerSynchronizer = new ContainerSynchronizer() {
      @Override
      public void sendInitialData(AbstractContainerMenu var1, NonNullList<ItemStack> var2, ItemStack var3, int[] var4) {
         ServerPlayer.this.connection.send(new ClientboundContainerSetContentPacket(â˜ƒ.containerId, â˜ƒ.incrementStateId(), â˜ƒ, â˜ƒ));

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
            this.broadcastDataValue(â˜ƒ, â˜ƒ, â˜ƒ[â˜ƒ]);
         }
      }

      @Override
      public void sendSlotChange(AbstractContainerMenu var1, int var2, ItemStack var3) {
         ServerPlayer.this.connection.send(new ClientboundContainerSetSlotPacket(â˜ƒ.containerId, â˜ƒ.incrementStateId(), â˜ƒ, â˜ƒ));
      }

      @Override
      public void sendCarriedChange(AbstractContainerMenu var1, ItemStack var2) {
         ServerPlayer.this.connection.send(new ClientboundContainerSetSlotPacket(-1, â˜ƒ.incrementStateId(), -1, â˜ƒ));
      }

      @Override
      public void sendDataChange(AbstractContainerMenu var1, int var2, int var3) {
         this.broadcastDataValue(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void broadcastDataValue(AbstractContainerMenu var1, int var2, int var3) {
         ServerPlayer.this.connection.send(new ClientboundContainerSetDataPacket(â˜ƒ.containerId, â˜ƒ, â˜ƒ));
      }
   };
   private final ContainerListener containerListener = new ContainerListener() {
      @Override
      public void slotChanged(AbstractContainerMenu var1, int var2, ItemStack var3) {
         Slot â˜ƒ = â˜ƒ.getSlot(â˜ƒ);
         if (!(â˜ƒ instanceof ResultSlot)) {
            if (â˜ƒ.container == ServerPlayer.this.getInventory()) {
               CriteriaTriggers.INVENTORY_CHANGED.trigger(ServerPlayer.this, ServerPlayer.this.getInventory(), â˜ƒ);
            }
         }
      }

      @Override
      public void dataChanged(AbstractContainerMenu var1, int var2, int var3) {
      }
   };
   private int containerCounter;
   public int latency;
   public boolean wonGame;

   public ServerPlayer(MinecraftServer var1, ServerLevel var2, GameProfile var3) {
      super(â˜ƒ, â˜ƒ.getSharedSpawnPos(), â˜ƒ.getSharedSpawnAngle(), â˜ƒ);
      this.textFilter = â˜ƒ.createTextFilterForPlayer(this);
      this.gameMode = â˜ƒ.createGameModeForPlayer(this);
      this.server = â˜ƒ;
      this.stats = â˜ƒ.getPlayerList().getPlayerStats(this);
      this.advancements = â˜ƒ.getPlayerList().getPlayerAdvancements(this);
      this.maxUpStep = 1.0F;
      this.fudgeSpawnLocation(â˜ƒ);
   }

   private void fudgeSpawnLocation(ServerLevel var1) {
      BlockPos â˜ƒ = â˜ƒ.getSharedSpawnPos();
      if (â˜ƒ.dimensionType().hasSkyLight() && â˜ƒ.getServer().getWorldData().getGameType() != GameType.ADVENTURE) {
         int â˜ƒx = Math.max(0, this.server.getSpawnRadius(â˜ƒ));
         int â˜ƒxx = Mth.floor(â˜ƒ.getWorldBorder().getDistanceToBorder((double)â˜ƒ.getX(), (double)â˜ƒ.getZ()));
         if (â˜ƒxx < â˜ƒx) {
            â˜ƒx = â˜ƒxx;
         }

         if (â˜ƒxx <= 1) {
            â˜ƒx = 1;
         }

         long â˜ƒx = (long)(â˜ƒx * 2 + 1);
         long â˜ƒxx = â˜ƒx * â˜ƒx;
         int â˜ƒxxx = â˜ƒxx > 2147483647L ? Integer.MAX_VALUE : (int)â˜ƒxx;
         int â˜ƒxxxx = this.getCoprime(â˜ƒxxx);
         int â˜ƒxxxxx = new Random().nextInt(â˜ƒxxx);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
            int â˜ƒxxxxxxx = (â˜ƒxxxxx + â˜ƒxxxx * â˜ƒxxxxxx) % â˜ƒxxx;
            int â˜ƒxxxxxxxx = â˜ƒxxxxxxx % (â˜ƒx * 2 + 1);
            int â˜ƒxxxxxxxxx = â˜ƒxxxxxxx / (â˜ƒx * 2 + 1);
            BlockPos â˜ƒxxxxxxxxxx = PlayerRespawnLogic.getOverworldRespawnPos(â˜ƒ, â˜ƒ.getX() + â˜ƒxxxxxxxx - â˜ƒx, â˜ƒ.getZ() + â˜ƒxxxxxxxxx - â˜ƒx, false);
            if (â˜ƒxxxxxxxxxx != null) {
               this.moveTo(â˜ƒxxxxxxxxxx, 0.0F, 0.0F);
               if (â˜ƒ.noCollision(this)) {
                  break;
               }
            }
         }
      } else {
         this.moveTo(â˜ƒ, 0.0F, 0.0F);

         while(!â˜ƒ.noCollision(this) && this.getY() < (double)(â˜ƒ.getMaxBuildHeight() - 1)) {
            this.setPos(this.getX(), this.getY() + 1.0, this.getZ());
         }
      }
   }

   private int getCoprime(int var1) {
      return â˜ƒ <= 16 ? â˜ƒ - 1 : 17;
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("enteredNetherPosition", 10)) {
         CompoundTag â˜ƒ = â˜ƒ.getCompound("enteredNetherPosition");
         this.enteredNetherPosition = new Vec3(â˜ƒ.getDouble("x"), â˜ƒ.getDouble("y"), â˜ƒ.getDouble("z"));
      }

      this.seenCredits = â˜ƒ.getBoolean("seenCredits");
      if (â˜ƒ.contains("recipeBook", 10)) {
         this.recipeBook.fromNbt(â˜ƒ.getCompound("recipeBook"), this.server.getRecipeManager());
      }

      if (this.isSleeping()) {
         this.stopSleeping();
      }

      if (â˜ƒ.contains("SpawnX", 99) && â˜ƒ.contains("SpawnY", 99) && â˜ƒ.contains("SpawnZ", 99)) {
         this.respawnPosition = new BlockPos(â˜ƒ.getInt("SpawnX"), â˜ƒ.getInt("SpawnY"), â˜ƒ.getInt("SpawnZ"));
         this.respawnForced = â˜ƒ.getBoolean("SpawnForced");
         this.respawnAngle = â˜ƒ.getFloat("SpawnAngle");
         if (â˜ƒ.contains("SpawnDimension")) {
            this.respawnDimension = (ResourceKey)Level.RESOURCE_KEY_CODEC
               .parse(NbtOps.INSTANCE, â˜ƒ.get("SpawnDimension"))
               .resultOrPartial(LOGGER::error)
               .orElse(Level.OVERWORLD);
         }
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      this.storeGameTypes(â˜ƒ);
      â˜ƒ.putBoolean("seenCredits", this.seenCredits);
      if (this.enteredNetherPosition != null) {
         CompoundTag â˜ƒ = new CompoundTag();
         â˜ƒ.putDouble("x", this.enteredNetherPosition.x);
         â˜ƒ.putDouble("y", this.enteredNetherPosition.y);
         â˜ƒ.putDouble("z", this.enteredNetherPosition.z);
         â˜ƒ.put("enteredNetherPosition", â˜ƒ);
      }

      Entity â˜ƒ = this.getRootVehicle();
      Entity â˜ƒx = this.getVehicle();
      if (â˜ƒx != null && â˜ƒ != this && â˜ƒ.hasExactlyOnePlayerPassenger()) {
         CompoundTag â˜ƒxx = new CompoundTag();
         CompoundTag â˜ƒxxx = new CompoundTag();
         â˜ƒ.save(â˜ƒxxx);
         â˜ƒxx.putUUID("Attach", â˜ƒx.getUUID());
         â˜ƒxx.put("Entity", â˜ƒxxx);
         â˜ƒ.put("RootVehicle", â˜ƒxx);
      }

      â˜ƒ.put("recipeBook", this.recipeBook.toNbt());
      â˜ƒ.putString("Dimension", this.level.dimension().location().toString());
      if (this.respawnPosition != null) {
         â˜ƒ.putInt("SpawnX", this.respawnPosition.getX());
         â˜ƒ.putInt("SpawnY", this.respawnPosition.getY());
         â˜ƒ.putInt("SpawnZ", this.respawnPosition.getZ());
         â˜ƒ.putBoolean("SpawnForced", this.respawnForced);
         â˜ƒ.putFloat("SpawnAngle", this.respawnAngle);
         ResourceLocation.CODEC
            .encodeStart(NbtOps.INSTANCE, this.respawnDimension.location())
            .resultOrPartial(LOGGER::error)
            .ifPresent(var1x -> â˜ƒ.put("SpawnDimension", var1x));
      }
   }

   public void setExperiencePoints(int var1) {
      float â˜ƒ = (float)this.getXpNeededForNextLevel();
      float â˜ƒx = (â˜ƒ - 1.0F) / â˜ƒ;
      this.experienceProgress = Mth.clamp((float)â˜ƒ / â˜ƒ, 0.0F, â˜ƒx);
      this.lastSentExp = -1;
   }

   public void setExperienceLevels(int var1) {
      this.experienceLevel = â˜ƒ;
      this.lastSentExp = -1;
   }

   @Override
   public void giveExperienceLevels(int var1) {
      super.giveExperienceLevels(â˜ƒ);
      this.lastSentExp = -1;
   }

   @Override
   public void onEnchantmentPerformed(ItemStack var1, int var2) {
      super.onEnchantmentPerformed(â˜ƒ, â˜ƒ);
      this.lastSentExp = -1;
   }

   private void initMenu(AbstractContainerMenu var1) {
      â˜ƒ.addSlotListener(this.containerListener);
      â˜ƒ.setSynchronizer(this.containerSynchronizer);
   }

   public void initInventoryMenu() {
      this.initMenu(this.inventoryMenu);
   }

   @Override
   public void onEnterCombat() {
      super.onEnterCombat();
      this.connection.send(new ClientboundPlayerCombatEnterPacket());
   }

   @Override
   public void onLeaveCombat() {
      super.onLeaveCombat();
      this.connection.send(new ClientboundPlayerCombatEndPacket(this.getCombatTracker()));
   }

   @Override
   protected void onInsideBlock(BlockState var1) {
      CriteriaTriggers.ENTER_BLOCK.trigger(this, â˜ƒ);
   }

   @Override
   protected ItemCooldowns createItemCooldowns() {
      return new ServerItemCooldowns(this);
   }

   @Override
   public void tick() {
      this.gameMode.tick();
      --this.spawnInvulnerableTime;
      if (this.invulnerableTime > 0) {
         --this.invulnerableTime;
      }

      this.containerMenu.broadcastChanges();
      if (!this.level.isClientSide && !this.containerMenu.stillValid(this)) {
         this.closeContainer();
         this.containerMenu = this.inventoryMenu;
      }

      Entity â˜ƒ = this.getCamera();
      if (â˜ƒ != this) {
         if (â˜ƒ.isAlive()) {
            this.absMoveTo(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot());
            this.getLevel().getChunkSource().move(this);
            if (this.wantsToStopRiding()) {
               this.setCamera(this);
            }
         } else {
            this.setCamera(this);
         }
      }

      CriteriaTriggers.TICK.trigger(this);
      if (this.levitationStartPos != null) {
         CriteriaTriggers.LEVITATION.trigger(this, this.levitationStartPos, this.tickCount - this.levitationStartTime);
      }

      this.advancements.flushDirty(this);
   }

   public void doTick() {
      try {
         if (!this.isSpectator() || !this.touchingUnloadedChunk()) {
            super.tick();
         }

         for(int â˜ƒ = 0; â˜ƒ < this.getInventory().getContainerSize(); ++â˜ƒ) {
            ItemStack â˜ƒx = this.getInventory().getItem(â˜ƒ);
            if (â˜ƒx.getItem().isComplex()) {
               Packet<?> â˜ƒxx = ((ComplexItem)â˜ƒx.getItem()).getUpdatePacket(â˜ƒx, this.level, this);
               if (â˜ƒxx != null) {
                  this.connection.send(â˜ƒxx);
               }
            }
         }

         if (this.getHealth() != this.lastSentHealth
            || this.lastSentFood != this.foodData.getFoodLevel()
            || this.foodData.getSaturationLevel() == 0.0F != this.lastFoodSaturationZero) {
            this.connection.send(new ClientboundSetHealthPacket(this.getHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
            this.lastSentHealth = this.getHealth();
            this.lastSentFood = this.foodData.getFoodLevel();
            this.lastFoodSaturationZero = this.foodData.getSaturationLevel() == 0.0F;
         }

         if (this.getHealth() + this.getAbsorptionAmount() != this.lastRecordedHealthAndAbsorption) {
            this.lastRecordedHealthAndAbsorption = this.getHealth() + this.getAbsorptionAmount();
            this.updateScoreForCriteria(ObjectiveCriteria.HEALTH, Mth.ceil(this.lastRecordedHealthAndAbsorption));
         }

         if (this.foodData.getFoodLevel() != this.lastRecordedFoodLevel) {
            this.lastRecordedFoodLevel = this.foodData.getFoodLevel();
            this.updateScoreForCriteria(ObjectiveCriteria.FOOD, Mth.ceil((float)this.lastRecordedFoodLevel));
         }

         if (this.getAirSupply() != this.lastRecordedAirLevel) {
            this.lastRecordedAirLevel = this.getAirSupply();
            this.updateScoreForCriteria(ObjectiveCriteria.AIR, Mth.ceil((float)this.lastRecordedAirLevel));
         }

         if (this.getArmorValue() != this.lastRecordedArmor) {
            this.lastRecordedArmor = this.getArmorValue();
            this.updateScoreForCriteria(ObjectiveCriteria.ARMOR, Mth.ceil((float)this.lastRecordedArmor));
         }

         if (this.totalExperience != this.lastRecordedExperience) {
            this.lastRecordedExperience = this.totalExperience;
            this.updateScoreForCriteria(ObjectiveCriteria.EXPERIENCE, Mth.ceil((float)this.lastRecordedExperience));
         }

         if (this.experienceLevel != this.lastRecordedLevel) {
            this.lastRecordedLevel = this.experienceLevel;
            this.updateScoreForCriteria(ObjectiveCriteria.LEVEL, Mth.ceil((float)this.lastRecordedLevel));
         }

         if (this.totalExperience != this.lastSentExp) {
            this.lastSentExp = this.totalExperience;
            this.connection.send(new ClientboundSetExperiencePacket(this.experienceProgress, this.totalExperience, this.experienceLevel));
         }

         if (this.tickCount % 20 == 0) {
            CriteriaTriggers.LOCATION.trigger(this);
         }
      } catch (Throwable var4) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var4, "Ticking player");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Player being ticked");
         this.fillCrashReportCategory(â˜ƒx);
         throw new ReportedException(â˜ƒ);
      }
   }

   private void updateScoreForCriteria(ObjectiveCriteria var1, int var2) {
      this.getScoreboard().forAllObjectives(â˜ƒ, this.getScoreboardName(), var1x -> var1x.setScore(â˜ƒ));
   }

   @Override
   public void die(DamageSource var1) {
      boolean â˜ƒ = this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES);
      if (â˜ƒ) {
         Component â˜ƒx = this.getCombatTracker().getDeathMessage();
         this.connection
            .send(
               new ClientboundPlayerCombatKillPacket(this.getCombatTracker(), â˜ƒx),
               var2x -> {
                  if (!var2x.isSuccess()) {
                     int â˜ƒ = 256;
                     String â˜ƒx = â˜ƒ.getString(256);
                     Component â˜ƒxx = new TranslatableComponent("death.attack.message_too_long", new TextComponent(â˜ƒx).withStyle(ChatFormatting.YELLOW));
                     Component â˜ƒxxx = new TranslatableComponent("death.attack.even_more_magic", this.getDisplayName())
                        .withStyle(var1x -> var1x.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, â˜ƒ)));
                     this.connection.send(new ClientboundPlayerCombatKillPacket(this.getCombatTracker(), â˜ƒxxx));
                  }
               }
            );
         Team â˜ƒxx = this.getTeam();
         if (â˜ƒxx == null || â˜ƒxx.getDeathMessageVisibility() == Team.Visibility.ALWAYS) {
            this.server.getPlayerList().broadcastMessage(â˜ƒx, ChatType.SYSTEM, Util.NIL_UUID);
         } else if (â˜ƒxx.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OTHER_TEAMS) {
            this.server.getPlayerList().broadcastToTeam(this, â˜ƒx);
         } else if (â˜ƒxx.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OWN_TEAM) {
            this.server.getPlayerList().broadcastToAllExceptTeam(this, â˜ƒx);
         }
      } else {
         this.connection.send(new ClientboundPlayerCombatKillPacket(this.getCombatTracker(), TextComponent.EMPTY));
      }

      this.removeEntitiesOnShoulder();
      if (this.level.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
         this.tellNeutralMobsThatIDied();
      }

      if (!this.isSpectator()) {
         this.dropAllDeathLoot(â˜ƒ);
      }

      this.getScoreboard().forAllObjectives(ObjectiveCriteria.DEATH_COUNT, this.getScoreboardName(), Score::increment);
      LivingEntity â˜ƒ = this.getKillCredit();
      if (â˜ƒ != null) {
         this.awardStat(Stats.ENTITY_KILLED_BY.get(â˜ƒ.getType()));
         â˜ƒ.awardKillScore(this, this.deathScore, â˜ƒ);
         this.createWitherRose(â˜ƒ);
      }

      this.level.broadcastEntityEvent(this, (byte)3);
      this.awardStat(Stats.DEATHS);
      this.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
      this.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
      this.clearFire();
      this.setTicksFrozen(0);
      this.setSharedFlagOnFire(false);
      this.getCombatTracker().recheckStatus();
   }

   private void tellNeutralMobsThatIDied() {
      AABB â˜ƒ = new AABB(this.blockPosition()).inflate(32.0, 10.0, 32.0);
      this.level
         .getEntitiesOfClass(Mob.class, â˜ƒ, EntitySelector.NO_SPECTATORS)
         .stream()
         .filter(var0 -> var0 instanceof NeutralMob)
         .forEach(var1x -> ((NeutralMob)var1x).playerDied(this));
   }

   @Override
   public void awardKillScore(Entity var1, int var2, DamageSource var3) {
      if (â˜ƒ != this) {
         super.awardKillScore(â˜ƒ, â˜ƒ, â˜ƒ);
         this.increaseScore(â˜ƒ);
         String â˜ƒ = this.getScoreboardName();
         String â˜ƒx = â˜ƒ.getScoreboardName();
         this.getScoreboard().forAllObjectives(ObjectiveCriteria.KILL_COUNT_ALL, â˜ƒ, Score::increment);
         if (â˜ƒ instanceof Player) {
            this.awardStat(Stats.PLAYER_KILLS);
            this.getScoreboard().forAllObjectives(ObjectiveCriteria.KILL_COUNT_PLAYERS, â˜ƒ, Score::increment);
         } else {
            this.awardStat(Stats.MOB_KILLS);
         }

         this.handleTeamKill(â˜ƒ, â˜ƒx, ObjectiveCriteria.TEAM_KILL);
         this.handleTeamKill(â˜ƒx, â˜ƒ, ObjectiveCriteria.KILLED_BY_TEAM);
         CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(this, â˜ƒ, â˜ƒ);
      }
   }

   private void handleTeamKill(String var1, String var2, ObjectiveCriteria[] var3) {
      PlayerTeam â˜ƒ = this.getScoreboard().getPlayersTeam(â˜ƒ);
      if (â˜ƒ != null) {
         int â˜ƒx = â˜ƒ.getColor().getId();
         if (â˜ƒx >= 0 && â˜ƒx < â˜ƒ.length) {
            this.getScoreboard().forAllObjectives(â˜ƒ[â˜ƒx], â˜ƒ, Score::increment);
         }
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         boolean â˜ƒ = this.server.isDedicatedServer() && this.isPvpAllowed() && "fall".equals(â˜ƒ.msgId);
         if (!â˜ƒ && this.spawnInvulnerableTime > 0 && â˜ƒ != DamageSource.OUT_OF_WORLD) {
            return false;
         } else {
            if (â˜ƒ instanceof EntityDamageSource) {
               Entity â˜ƒ = â˜ƒ.getEntity();
               if (â˜ƒ instanceof Player && !this.canHarmPlayer((Player)â˜ƒ)) {
                  return false;
               }

               if (â˜ƒ instanceof AbstractArrow â˜ƒ) {
                  Entity â˜ƒx = â˜ƒ.getOwner();
                  if (â˜ƒx instanceof Player && !this.canHarmPlayer((Player)â˜ƒx)) {
                     return false;
                  }
               }
            }

            return super.hurt(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public boolean canHarmPlayer(Player var1) {
      return !this.isPvpAllowed() ? false : super.canHarmPlayer(â˜ƒ);
   }

   private boolean isPvpAllowed() {
      return this.server.isPvpAllowed();
   }

   @Nullable
   @Override
   protected PortalInfo findDimensionEntryPoint(ServerLevel var1) {
      PortalInfo â˜ƒ = super.findDimensionEntryPoint(â˜ƒ);
      if (â˜ƒ != null && this.level.dimension() == Level.OVERWORLD && â˜ƒ.dimension() == Level.END) {
         Vec3 â˜ƒx = â˜ƒ.pos.add(0.0, -1.0, 0.0);
         return new PortalInfo(â˜ƒx, Vec3.ZERO, 90.0F, 0.0F);
      } else {
         return â˜ƒ;
      }
   }

   @Nullable
   @Override
   public Entity changeDimension(ServerLevel var1) {
      this.isChangingDimension = true;
      ServerLevel â˜ƒ = this.getLevel();
      ResourceKey<Level> â˜ƒx = â˜ƒ.dimension();
      if (â˜ƒx == Level.END && â˜ƒ.dimension() == Level.OVERWORLD) {
         this.unRide();
         this.getLevel().removePlayerImmediately(this, Entity.RemovalReason.CHANGED_DIMENSION);
         if (!this.wonGame) {
            this.wonGame = true;
            this.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, this.seenCredits ? 0.0F : 1.0F));
            this.seenCredits = true;
         }

         return this;
      } else {
         LevelData â˜ƒ = â˜ƒ.getLevelData();
         this.connection
            .send(
               new ClientboundRespawnPacket(
                  â˜ƒ.dimensionType(),
                  â˜ƒ.dimension(),
                  BiomeManager.obfuscateSeed(â˜ƒ.getSeed()),
                  this.gameMode.getGameModeForPlayer(),
                  this.gameMode.getPreviousGameModeForPlayer(),
                  â˜ƒ.isDebug(),
                  â˜ƒ.isFlat(),
                  true
               )
            );
         this.connection.send(new ClientboundChangeDifficultyPacket(â˜ƒ.getDifficulty(), â˜ƒ.isDifficultyLocked()));
         PlayerList â˜ƒx = this.server.getPlayerList();
         â˜ƒx.sendPlayerPermissionLevel(this);
         â˜ƒ.removePlayerImmediately(this, Entity.RemovalReason.CHANGED_DIMENSION);
         this.unsetRemoved();
         PortalInfo â˜ƒxx = this.findDimensionEntryPoint(â˜ƒ);
         if (â˜ƒxx != null) {
            â˜ƒ.getProfiler().push("moving");
            if (â˜ƒx == Level.OVERWORLD && â˜ƒ.dimension() == Level.NETHER) {
               this.enteredNetherPosition = this.position();
            } else if (â˜ƒ.dimension() == Level.END) {
               this.createEndPlatform(â˜ƒ, new BlockPos(â˜ƒxx.pos));
            }

            â˜ƒ.getProfiler().pop();
            â˜ƒ.getProfiler().push("placing");
            this.setLevel(â˜ƒ);
            â˜ƒ.addDuringPortalTeleport(this);
            this.setRot(â˜ƒxx.yRot, â˜ƒxx.xRot);
            this.moveTo(â˜ƒxx.pos.x, â˜ƒxx.pos.y, â˜ƒxx.pos.z);
            â˜ƒ.getProfiler().pop();
            this.triggerDimensionChangeTriggers(â˜ƒ);
            this.connection.send(new ClientboundPlayerAbilitiesPacket(this.getAbilities()));
            â˜ƒx.sendLevelInfo(this, â˜ƒ);
            â˜ƒx.sendAllPlayerInfo(this);

            for(MobEffectInstance â˜ƒxxx : this.getActiveEffects()) {
               this.connection.send(new ClientboundUpdateMobEffectPacket(this.getId(), â˜ƒxxx));
            }

            this.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
            this.lastSentExp = -1;
            this.lastSentHealth = -1.0F;
            this.lastSentFood = -1;
         }

         return this;
      }
   }

   private void createEndPlatform(ServerLevel var1, BlockPos var2) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      for(int â˜ƒx = -2; â˜ƒx <= 2; ++â˜ƒx) {
         for(int â˜ƒxx = -2; â˜ƒxx <= 2; ++â˜ƒxx) {
            for(int â˜ƒxxx = -1; â˜ƒxxx < 3; ++â˜ƒxxx) {
               BlockState â˜ƒxxxx = â˜ƒxxx == -1 ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.AIR.defaultBlockState();
               â˜ƒ.setBlockAndUpdate(â˜ƒ.set(â˜ƒ).move(â˜ƒxx, â˜ƒxxx, â˜ƒx), â˜ƒxxxx);
            }
         }
      }
   }

   @Override
   protected Optional<BlockUtil.FoundRectangle> getExitPortal(ServerLevel var1, BlockPos var2, boolean var3) {
      Optional<BlockUtil.FoundRectangle> â˜ƒ = super.getExitPortal(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPresent()) {
         return â˜ƒ;
      } else {
         Direction.Axis â˜ƒ = (Direction.Axis)this.level
            .getBlockState(this.portalEntrancePos)
            .getOptionalValue(NetherPortalBlock.AXIS)
            .orElse(Direction.Axis.X);
         Optional<BlockUtil.FoundRectangle> â˜ƒx = â˜ƒ.getPortalForcer().createPortal(â˜ƒ, â˜ƒ);
         if (!â˜ƒx.isPresent()) {
            LOGGER.error("Unable to create a portal, likely target out of worldborder");
         }

         return â˜ƒx;
      }
   }

   private void triggerDimensionChangeTriggers(ServerLevel var1) {
      ResourceKey<Level> â˜ƒ = â˜ƒ.dimension();
      ResourceKey<Level> â˜ƒx = this.level.dimension();
      CriteriaTriggers.CHANGED_DIMENSION.trigger(this, â˜ƒ, â˜ƒx);
      if (â˜ƒ == Level.NETHER && â˜ƒx == Level.OVERWORLD && this.enteredNetherPosition != null) {
         CriteriaTriggers.NETHER_TRAVEL.trigger(this, this.enteredNetherPosition);
      }

      if (â˜ƒx != Level.NETHER) {
         this.enteredNetherPosition = null;
      }
   }

   @Override
   public boolean broadcastToPlayer(ServerPlayer var1) {
      if (â˜ƒ.isSpectator()) {
         return this.getCamera() == this;
      } else {
         return this.isSpectator() ? false : super.broadcastToPlayer(â˜ƒ);
      }
   }

   private void broadcast(BlockEntity var1) {
      if (â˜ƒ != null) {
         ClientboundBlockEntityDataPacket â˜ƒ = â˜ƒ.getUpdatePacket();
         if (â˜ƒ != null) {
            this.connection.send(â˜ƒ);
         }
      }
   }

   @Override
   public void take(Entity var1, int var2) {
      super.take(â˜ƒ, â˜ƒ);
      this.containerMenu.broadcastChanges();
   }

   @Override
   public Either<Player.BedSleepingProblem, Unit> startSleepInBed(BlockPos var1) {
      Direction â˜ƒ = this.level.getBlockState(â˜ƒ).getValue(HorizontalDirectionalBlock.FACING);
      if (this.isSleeping() || !this.isAlive()) {
         return Either.left(Player.BedSleepingProblem.OTHER_PROBLEM);
      } else if (!this.level.dimensionType().natural()) {
         return Either.left(Player.BedSleepingProblem.NOT_POSSIBLE_HERE);
      } else if (!this.bedInRange(â˜ƒ, â˜ƒ)) {
         return Either.left(Player.BedSleepingProblem.TOO_FAR_AWAY);
      } else if (this.bedBlocked(â˜ƒ, â˜ƒ)) {
         return Either.left(Player.BedSleepingProblem.OBSTRUCTED);
      } else {
         this.setRespawnPosition(this.level.dimension(), â˜ƒ, this.getYRot(), false, true);
         if (this.level.isDay()) {
            return Either.left(Player.BedSleepingProblem.NOT_POSSIBLE_NOW);
         } else {
            if (!this.isCreative()) {
               double â˜ƒ = 8.0;
               double â˜ƒx = 5.0;
               Vec3 â˜ƒxx = Vec3.atBottomCenterOf(â˜ƒ);
               List<Monster> â˜ƒxxx = this.level
                  .getEntitiesOfClass(
                     Monster.class,
                     new AABB(â˜ƒxx.x() - 8.0, â˜ƒxx.y() - 5.0, â˜ƒxx.z() - 8.0, â˜ƒxx.x() + 8.0, â˜ƒxx.y() + 5.0, â˜ƒxx.z() + 8.0),
                     var1x -> var1x.isPreventingPlayerRest(this)
                  );
               if (!â˜ƒxxx.isEmpty()) {
                  return Either.left(Player.BedSleepingProblem.NOT_SAFE);
               }
            }

            Either<Player.BedSleepingProblem, Unit> â˜ƒ = super.startSleepInBed(â˜ƒ).ifRight(var1x -> {
               this.awardStat(Stats.SLEEP_IN_BED);
               CriteriaTriggers.SLEPT_IN_BED.trigger(this);
            });
            if (!this.getLevel().canSleepThroughNights()) {
               this.displayClientMessage(new TranslatableComponent("sleep.not_possible"), true);
            }

            ((ServerLevel)this.level).updateSleepingPlayerList();
            return â˜ƒ;
         }
      }
   }

   @Override
   public void startSleeping(BlockPos var1) {
      this.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
      super.startSleeping(â˜ƒ);
   }

   private boolean bedInRange(BlockPos var1, Direction var2) {
      return this.isReachableBedBlock(â˜ƒ) || this.isReachableBedBlock(â˜ƒ.relative(â˜ƒ.getOpposite()));
   }

   private boolean isReachableBedBlock(BlockPos var1) {
      Vec3 â˜ƒ = Vec3.atBottomCenterOf(â˜ƒ);
      return Math.abs(this.getX() - â˜ƒ.x()) <= 3.0 && Math.abs(this.getY() - â˜ƒ.y()) <= 2.0 && Math.abs(this.getZ() - â˜ƒ.z()) <= 3.0;
   }

   private boolean bedBlocked(BlockPos var1, Direction var2) {
      BlockPos â˜ƒ = â˜ƒ.above();
      return !this.freeAt(â˜ƒ) || !this.freeAt(â˜ƒ.relative(â˜ƒ.getOpposite()));
   }

   @Override
   public void stopSleepInBed(boolean var1, boolean var2) {
      if (this.isSleeping()) {
         this.getLevel().getChunkSource().broadcastAndSend(this, new ClientboundAnimatePacket(this, 2));
      }

      super.stopSleepInBed(â˜ƒ, â˜ƒ);
      if (this.connection != null) {
         this.connection.teleport(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
      }
   }

   @Override
   public boolean startRiding(Entity var1, boolean var2) {
      Entity â˜ƒ = this.getVehicle();
      if (!super.startRiding(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         Entity â˜ƒ = this.getVehicle();
         if (â˜ƒ != â˜ƒ && this.connection != null) {
            this.connection.teleport(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
         }

         return true;
      }
   }

   @Override
   public void stopRiding() {
      Entity â˜ƒ = this.getVehicle();
      super.stopRiding();
      Entity â˜ƒx = this.getVehicle();
      if (â˜ƒx != â˜ƒ && this.connection != null) {
         this.connection.dismount(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
      }
   }

   @Override
   public void dismountTo(double var1, double var3, double var5) {
      this.removeVehicle();
      if (this.connection != null) {
         this.connection.dismount(â˜ƒ, â˜ƒ, â˜ƒ, this.getYRot(), this.getXRot());
      }
   }

   @Override
   public boolean isInvulnerableTo(DamageSource var1) {
      return super.isInvulnerableTo(â˜ƒ) || this.isChangingDimension() || this.getAbilities().invulnerable && â˜ƒ == DamageSource.WITHER;
   }

   @Override
   protected void checkFallDamage(double var1, boolean var3, BlockState var4, BlockPos var5) {
   }

   @Override
   protected void onChangedBlock(BlockPos var1) {
      if (!this.isSpectator()) {
         super.onChangedBlock(â˜ƒ);
      }
   }

   public void doCheckFallDamage(double var1, boolean var3) {
      if (!this.touchingUnloadedChunk()) {
         BlockPos â˜ƒ = this.getOnPos();
         super.checkFallDamage(â˜ƒ, â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ);
      }
   }

   @Override
   public void openTextEdit(SignBlockEntity var1) {
      â˜ƒ.setAllowedPlayerEditor(this.getUUID());
      this.connection.send(new ClientboundBlockUpdatePacket(this.level, â˜ƒ.getBlockPos()));
      this.connection.send(new ClientboundOpenSignEditorPacket(â˜ƒ.getBlockPos()));
   }

   private void nextContainerCounter() {
      this.containerCounter = this.containerCounter % 100 + 1;
   }

   @Override
   public OptionalInt openMenu(@Nullable MenuProvider var1) {
      if (â˜ƒ == null) {
         return OptionalInt.empty();
      } else {
         if (this.containerMenu != this.inventoryMenu) {
            this.closeContainer();
         }

         this.nextContainerCounter();
         AbstractContainerMenu â˜ƒ = â˜ƒ.createMenu(this.containerCounter, this.getInventory(), this);
         if (â˜ƒ == null) {
            if (this.isSpectator()) {
               this.displayClientMessage(new TranslatableComponent("container.spectatorCantOpen").withStyle(ChatFormatting.RED), true);
            }

            return OptionalInt.empty();
         } else {
            this.connection.send(new ClientboundOpenScreenPacket(â˜ƒ.containerId, â˜ƒ.getType(), â˜ƒ.getDisplayName()));
            this.initMenu(â˜ƒ);
            this.containerMenu = â˜ƒ;
            return OptionalInt.of(this.containerCounter);
         }
      }
   }

   @Override
   public void sendMerchantOffers(int var1, MerchantOffers var2, int var3, int var4, boolean var5, boolean var6) {
      this.connection.send(new ClientboundMerchantOffersPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Override
   public void openHorseInventory(AbstractHorse var1, Container var2) {
      if (this.containerMenu != this.inventoryMenu) {
         this.closeContainer();
      }

      this.nextContainerCounter();
      this.connection.send(new ClientboundHorseScreenOpenPacket(this.containerCounter, â˜ƒ.getContainerSize(), â˜ƒ.getId()));
      this.containerMenu = new HorseInventoryMenu(this.containerCounter, this.getInventory(), â˜ƒ, â˜ƒ);
      this.initMenu(this.containerMenu);
   }

   @Override
   public void openItemGui(ItemStack var1, InteractionHand var2) {
      if (â˜ƒ.is(Items.WRITTEN_BOOK)) {
         if (WrittenBookItem.resolveBookComponents(â˜ƒ, this.createCommandSourceStack(), this)) {
            this.containerMenu.broadcastChanges();
         }

         this.connection.send(new ClientboundOpenBookPacket(â˜ƒ));
      }
   }

   @Override
   public void openCommandBlock(CommandBlockEntity var1) {
      â˜ƒ.setSendToClient(true);
      this.broadcast(â˜ƒ);
   }

   @Override
   public void closeContainer() {
      this.connection.send(new ClientboundContainerClosePacket(this.containerMenu.containerId));
      this.doCloseContainer();
   }

   public void doCloseContainer() {
      this.containerMenu.removed(this);
      this.inventoryMenu.transferState(this.containerMenu);
      this.containerMenu = this.inventoryMenu;
   }

   public void setPlayerInput(float var1, float var2, boolean var3, boolean var4) {
      if (this.isPassenger()) {
         if (â˜ƒ >= -1.0F && â˜ƒ <= 1.0F) {
            this.xxa = â˜ƒ;
         }

         if (â˜ƒ >= -1.0F && â˜ƒ <= 1.0F) {
            this.zza = â˜ƒ;
         }

         this.jumping = â˜ƒ;
         this.setShiftKeyDown(â˜ƒ);
      }
   }

   @Override
   public void awardStat(Stat<?> var1, int var2) {
      this.stats.increment(this, â˜ƒ, â˜ƒ);
      this.getScoreboard().forAllObjectives(â˜ƒ, this.getScoreboardName(), var1x -> var1x.add(â˜ƒ));
   }

   @Override
   public void resetStat(Stat<?> var1) {
      this.stats.setValue(this, â˜ƒ, 0);
      this.getScoreboard().forAllObjectives(â˜ƒ, this.getScoreboardName(), Score::reset);
   }

   @Override
   public int awardRecipes(Collection<Recipe<?>> var1) {
      return this.recipeBook.addRecipes(â˜ƒ, this);
   }

   @Override
   public void awardRecipesByKey(ResourceLocation[] var1) {
      List<Recipe<?>> â˜ƒ = Lists.<Recipe<?>>newArrayList();

      for(ResourceLocation â˜ƒx : â˜ƒ) {
         this.server.getRecipeManager().byKey(â˜ƒx).ifPresent(â˜ƒ::add);
      }

      this.awardRecipes(â˜ƒ);
   }

   @Override
   public int resetRecipes(Collection<Recipe<?>> var1) {
      return this.recipeBook.removeRecipes(â˜ƒ, this);
   }

   @Override
   public void giveExperiencePoints(int var1) {
      super.giveExperiencePoints(â˜ƒ);
      this.lastSentExp = -1;
   }

   public void disconnect() {
      this.disconnected = true;
      this.ejectPassengers();
      if (this.isSleeping()) {
         this.stopSleepInBed(true, false);
      }
   }

   public boolean hasDisconnected() {
      return this.disconnected;
   }

   public void resetSentInfo() {
      this.lastSentHealth = -1.0E8F;
   }

   @Override
   public void displayClientMessage(Component var1, boolean var2) {
      this.sendMessage(â˜ƒ, â˜ƒ ? ChatType.GAME_INFO : ChatType.CHAT, Util.NIL_UUID);
   }

   @Override
   protected void completeUsingItem() {
      if (!this.useItem.isEmpty() && this.isUsingItem()) {
         this.connection.send(new ClientboundEntityEventPacket(this, (byte)9));
         super.completeUsingItem();
      }
   }

   @Override
   public void lookAt(EntityAnchorArgument.Anchor var1, Vec3 var2) {
      super.lookAt(â˜ƒ, â˜ƒ);
      this.connection.send(new ClientboundPlayerLookAtPacket(â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z));
   }

   public void lookAt(EntityAnchorArgument.Anchor var1, Entity var2, EntityAnchorArgument.Anchor var3) {
      Vec3 â˜ƒ = â˜ƒ.apply(â˜ƒ);
      super.lookAt(â˜ƒ, â˜ƒ);
      this.connection.send(new ClientboundPlayerLookAtPacket(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void restoreFrom(ServerPlayer var1, boolean var2) {
      this.textFilteringEnabled = â˜ƒ.textFilteringEnabled;
      this.gameMode.setGameModeForPlayer(â˜ƒ.gameMode.getGameModeForPlayer(), â˜ƒ.gameMode.getPreviousGameModeForPlayer());
      if (â˜ƒ) {
         this.getInventory().replaceWith(â˜ƒ.getInventory());
         this.setHealth(â˜ƒ.getHealth());
         this.foodData = â˜ƒ.foodData;
         this.experienceLevel = â˜ƒ.experienceLevel;
         this.totalExperience = â˜ƒ.totalExperience;
         this.experienceProgress = â˜ƒ.experienceProgress;
         this.setScore(â˜ƒ.getScore());
         this.portalEntrancePos = â˜ƒ.portalEntrancePos;
      } else if (this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || â˜ƒ.isSpectator()) {
         this.getInventory().replaceWith(â˜ƒ.getInventory());
         this.experienceLevel = â˜ƒ.experienceLevel;
         this.totalExperience = â˜ƒ.totalExperience;
         this.experienceProgress = â˜ƒ.experienceProgress;
         this.setScore(â˜ƒ.getScore());
      }

      this.enchantmentSeed = â˜ƒ.enchantmentSeed;
      this.enderChestInventory = â˜ƒ.enderChestInventory;
      this.getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, (Byte)â˜ƒ.getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION));
      this.lastSentExp = -1;
      this.lastSentHealth = -1.0F;
      this.lastSentFood = -1;
      this.recipeBook.copyOverData(â˜ƒ.recipeBook);
      this.seenCredits = â˜ƒ.seenCredits;
      this.enteredNetherPosition = â˜ƒ.enteredNetherPosition;
      this.setShoulderEntityLeft(â˜ƒ.getShoulderEntityLeft());
      this.setShoulderEntityRight(â˜ƒ.getShoulderEntityRight());
   }

   @Override
   protected void onEffectAdded(MobEffectInstance var1, @Nullable Entity var2) {
      super.onEffectAdded(â˜ƒ, â˜ƒ);
      this.connection.send(new ClientboundUpdateMobEffectPacket(this.getId(), â˜ƒ));
      if (â˜ƒ.getEffect() == MobEffects.LEVITATION) {
         this.levitationStartTime = this.tickCount;
         this.levitationStartPos = this.position();
      }

      CriteriaTriggers.EFFECTS_CHANGED.trigger(this, â˜ƒ);
   }

   @Override
   protected void onEffectUpdated(MobEffectInstance var1, boolean var2, @Nullable Entity var3) {
      super.onEffectUpdated(â˜ƒ, â˜ƒ, â˜ƒ);
      this.connection.send(new ClientboundUpdateMobEffectPacket(this.getId(), â˜ƒ));
      CriteriaTriggers.EFFECTS_CHANGED.trigger(this, â˜ƒ);
   }

   @Override
   protected void onEffectRemoved(MobEffectInstance var1) {
      super.onEffectRemoved(â˜ƒ);
      this.connection.send(new ClientboundRemoveMobEffectPacket(this.getId(), â˜ƒ.getEffect()));
      if (â˜ƒ.getEffect() == MobEffects.LEVITATION) {
         this.levitationStartPos = null;
      }

      CriteriaTriggers.EFFECTS_CHANGED.trigger(this, null);
   }

   @Override
   public void teleportTo(double var1, double var3, double var5) {
      this.connection.teleport(â˜ƒ, â˜ƒ, â˜ƒ, this.getYRot(), this.getXRot());
   }

   @Override
   public void moveTo(double var1, double var3, double var5) {
      this.teleportTo(â˜ƒ, â˜ƒ, â˜ƒ);
      this.connection.resetPosition();
   }

   @Override
   public void crit(Entity var1) {
      this.getLevel().getChunkSource().broadcastAndSend(this, new ClientboundAnimatePacket(â˜ƒ, 4));
   }

   @Override
   public void magicCrit(Entity var1) {
      this.getLevel().getChunkSource().broadcastAndSend(this, new ClientboundAnimatePacket(â˜ƒ, 5));
   }

   @Override
   public void onUpdateAbilities() {
      if (this.connection != null) {
         this.connection.send(new ClientboundPlayerAbilitiesPacket(this.getAbilities()));
         this.updateInvisibilityStatus();
      }
   }

   public ServerLevel getLevel() {
      return (ServerLevel)this.level;
   }

   public boolean setGameMode(GameType var1) {
      if (!this.gameMode.changeGameModeForPlayer(â˜ƒ)) {
         return false;
      } else {
         this.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.CHANGE_GAME_MODE, (float)â˜ƒ.getId()));
         if (â˜ƒ == GameType.SPECTATOR) {
            this.removeEntitiesOnShoulder();
            this.stopRiding();
         } else {
            this.setCamera(this);
         }

         this.onUpdateAbilities();
         this.updateEffectVisibility();
         return true;
      }
   }

   @Override
   public boolean isSpectator() {
      return this.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
   }

   @Override
   public boolean isCreative() {
      return this.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
   }

   @Override
   public void sendMessage(Component var1, UUID var2) {
      this.sendMessage(â˜ƒ, ChatType.SYSTEM, â˜ƒ);
   }

   public void sendMessage(Component var1, ChatType var2, UUID var3) {
      if (this.acceptsChat(â˜ƒ)) {
         this.connection
            .send(
               new ClientboundChatPacket(â˜ƒ, â˜ƒ, â˜ƒ),
               var4 -> {
                  if (!var4.isSuccess() && (â˜ƒ == ChatType.GAME_INFO || â˜ƒ == ChatType.SYSTEM) && this.acceptsChat(ChatType.SYSTEM)) {
                     int â˜ƒ = 256;
                     String â˜ƒx = â˜ƒ.getString(256);
                     Component â˜ƒxx = new TextComponent(â˜ƒx).withStyle(ChatFormatting.YELLOW);
                     this.connection
                        .send(
                           new ClientboundChatPacket(
                              new TranslatableComponent("multiplayer.message_not_delivered", â˜ƒxx).withStyle(ChatFormatting.RED), ChatType.SYSTEM, â˜ƒ
                           )
                        );
                  }
               }
            );
      }
   }

   public String getIpAddress() {
      String â˜ƒ = this.connection.connection.getRemoteAddress().toString();
      â˜ƒ = â˜ƒ.substring(â˜ƒ.indexOf("/") + 1);
      return â˜ƒ.substring(0, â˜ƒ.indexOf(":"));
   }

   public void updateOptions(ServerboundClientInformationPacket var1) {
      this.chatVisibility = â˜ƒ.getChatVisibility();
      this.canChatColor = â˜ƒ.getChatColors();
      this.textFilteringEnabled = â˜ƒ.isTextFilteringEnabled();
      this.getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, (byte)â˜ƒ.getModelCustomisation());
      this.getEntityData().set(DATA_PLAYER_MAIN_HAND, (byte)(â˜ƒ.getMainHand() == HumanoidArm.LEFT ? 0 : 1));
   }

   public boolean canChatInColor() {
      return this.canChatColor;
   }

   public ChatVisiblity getChatVisibility() {
      return this.chatVisibility;
   }

   private boolean acceptsChat(ChatType var1) {
      switch(this.chatVisibility) {
         case HIDDEN:
            return â˜ƒ == ChatType.GAME_INFO;
         case SYSTEM:
            return â˜ƒ == ChatType.SYSTEM || â˜ƒ == ChatType.GAME_INFO;
         case FULL:
         default:
            return true;
      }
   }

   public void sendTexturePack(String var1, String var2, boolean var3, @Nullable Component var4) {
      this.connection.send(new ClientboundResourcePackPacket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Override
   protected int getPermissionLevel() {
      return this.server.getProfilePermissions(this.getGameProfile());
   }

   public void resetLastActionTime() {
      this.lastActionTime = Util.getMillis();
   }

   public ServerStatsCounter getStats() {
      return this.stats;
   }

   public ServerRecipeBook getRecipeBook() {
      return this.recipeBook;
   }

   @Override
   protected void updateInvisibilityStatus() {
      if (this.isSpectator()) {
         this.removeEffectParticles();
         this.setInvisible(true);
      } else {
         super.updateInvisibilityStatus();
      }
   }

   public Entity getCamera() {
      return (Entity)(this.camera == null ? this : this.camera);
   }

   public void setCamera(Entity var1) {
      Entity â˜ƒ = this.getCamera();
      this.camera = (Entity)(â˜ƒ == null ? this : â˜ƒ);
      if (â˜ƒ != this.camera) {
         this.connection.send(new ClientboundSetCameraPacket(this.camera));
         this.teleportTo(this.camera.getX(), this.camera.getY(), this.camera.getZ());
      }
   }

   @Override
   protected void processPortalCooldown() {
      if (!this.isChangingDimension) {
         super.processPortalCooldown();
      }
   }

   @Override
   public void attack(Entity var1) {
      if (this.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
         this.setCamera(â˜ƒ);
      } else {
         super.attack(â˜ƒ);
      }
   }

   public long getLastActionTime() {
      return this.lastActionTime;
   }

   @Nullable
   public Component getTabListDisplayName() {
      return null;
   }

   @Override
   public void swing(InteractionHand var1) {
      super.swing(â˜ƒ);
      this.resetAttackStrengthTicker();
   }

   public boolean isChangingDimension() {
      return this.isChangingDimension;
   }

   public void hasChangedDimension() {
      this.isChangingDimension = false;
   }

   public PlayerAdvancements getAdvancements() {
      return this.advancements;
   }

   public void teleportTo(ServerLevel var1, double var2, double var4, double var6, float var8, float var9) {
      this.setCamera(this);
      this.stopRiding();
      if (â˜ƒ == this.level) {
         this.connection.teleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         ServerLevel â˜ƒ = this.getLevel();
         LevelData â˜ƒx = â˜ƒ.getLevelData();
         this.connection
            .send(
               new ClientboundRespawnPacket(
                  â˜ƒ.dimensionType(),
                  â˜ƒ.dimension(),
                  BiomeManager.obfuscateSeed(â˜ƒ.getSeed()),
                  this.gameMode.getGameModeForPlayer(),
                  this.gameMode.getPreviousGameModeForPlayer(),
                  â˜ƒ.isDebug(),
                  â˜ƒ.isFlat(),
                  true
               )
            );
         this.connection.send(new ClientboundChangeDifficultyPacket(â˜ƒx.getDifficulty(), â˜ƒx.isDifficultyLocked()));
         this.server.getPlayerList().sendPlayerPermissionLevel(this);
         â˜ƒ.removePlayerImmediately(this, Entity.RemovalReason.CHANGED_DIMENSION);
         this.unsetRemoved();
         this.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.setLevel(â˜ƒ);
         â˜ƒ.addDuringCommandTeleport(this);
         this.triggerDimensionChangeTriggers(â˜ƒ);
         this.connection.teleport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.server.getPlayerList().sendLevelInfo(this, â˜ƒ);
         this.server.getPlayerList().sendAllPlayerInfo(this);
      }
   }

   @Nullable
   public BlockPos getRespawnPosition() {
      return this.respawnPosition;
   }

   public float getRespawnAngle() {
      return this.respawnAngle;
   }

   public ResourceKey<Level> getRespawnDimension() {
      return this.respawnDimension;
   }

   public boolean isRespawnForced() {
      return this.respawnForced;
   }

   public void setRespawnPosition(ResourceKey<Level> var1, @Nullable BlockPos var2, float var3, boolean var4, boolean var5) {
      if (â˜ƒ != null) {
         boolean â˜ƒ = â˜ƒ.equals(this.respawnPosition) && â˜ƒ.equals(this.respawnDimension);
         if (â˜ƒ && !â˜ƒ) {
            this.sendMessage(new TranslatableComponent("block.minecraft.set_spawn"), Util.NIL_UUID);
         }

         this.respawnPosition = â˜ƒ;
         this.respawnDimension = â˜ƒ;
         this.respawnAngle = â˜ƒ;
         this.respawnForced = â˜ƒ;
      } else {
         this.respawnPosition = null;
         this.respawnDimension = Level.OVERWORLD;
         this.respawnAngle = 0.0F;
         this.respawnForced = false;
      }
   }

   public void trackChunk(ChunkPos var1, Packet<?> var2, Packet<?> var3) {
      this.connection.send(â˜ƒ);
      this.connection.send(â˜ƒ);
   }

   public void untrackChunk(ChunkPos var1) {
      if (this.isAlive()) {
         this.connection.send(new ClientboundForgetLevelChunkPacket(â˜ƒ.x, â˜ƒ.z));
      }
   }

   public SectionPos getLastSectionPos() {
      return this.lastSectionPos;
   }

   public void setLastSectionPos(SectionPos var1) {
      this.lastSectionPos = â˜ƒ;
   }

   @Override
   public void playNotifySound(SoundEvent var1, SoundSource var2, float var3, float var4) {
      this.connection.send(new ClientboundSoundPacket(â˜ƒ, â˜ƒ, this.getX(), this.getY(), this.getZ(), â˜ƒ, â˜ƒ));
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddPlayerPacket(this);
   }

   @Override
   public ItemEntity drop(ItemStack var1, boolean var2, boolean var3) {
      ItemEntity â˜ƒ = super.drop(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == null) {
         return null;
      } else {
         this.level.addFreshEntity(â˜ƒ);
         ItemStack â˜ƒ = â˜ƒ.getItem();
         if (â˜ƒ) {
            if (!â˜ƒ.isEmpty()) {
               this.awardStat(Stats.ITEM_DROPPED.get(â˜ƒ.getItem()), â˜ƒ.getCount());
            }

            this.awardStat(Stats.DROP);
         }

         return â˜ƒ;
      }
   }

   public TextFilter getTextFilter() {
      return this.textFilter;
   }

   public void setLevel(ServerLevel var1) {
      this.level = â˜ƒ;
      this.gameMode.setLevel(â˜ƒ);
   }

   @Nullable
   private static GameType readPlayerMode(@Nullable CompoundTag var0, String var1) {
      return â˜ƒ != null && â˜ƒ.contains(â˜ƒ, 99) ? GameType.byId(â˜ƒ.getInt(â˜ƒ)) : null;
   }

   private GameType calculateGameModeForNewPlayer(@Nullable GameType var1) {
      GameType â˜ƒ = this.server.getForcedGameType();
      if (â˜ƒ != null) {
         return â˜ƒ;
      } else {
         return â˜ƒ != null ? â˜ƒ : this.server.getDefaultGameType();
      }
   }

   public void loadGameTypes(@Nullable CompoundTag var1) {
      this.gameMode
         .setGameModeForPlayer(this.calculateGameModeForNewPlayer(readPlayerMode(â˜ƒ, "playerGameType")), readPlayerMode(â˜ƒ, "previousPlayerGameType"));
   }

   private void storeGameTypes(CompoundTag var1) {
      â˜ƒ.putInt("playerGameType", this.gameMode.getGameModeForPlayer().getId());
      GameType â˜ƒ = this.gameMode.getPreviousGameModeForPlayer();
      if (â˜ƒ != null) {
         â˜ƒ.putInt("previousPlayerGameType", â˜ƒ.getId());
      }
   }

   public boolean isTextFilteringEnabled() {
      return this.textFilteringEnabled;
   }

   public boolean shouldFilterMessageTo(ServerPlayer var1) {
      if (â˜ƒ == this) {
         return false;
      } else {
         return this.textFilteringEnabled || â˜ƒ.textFilteringEnabled;
      }
   }

   @Override
   public boolean mayInteract(Level var1, BlockPos var2) {
      return super.mayInteract(â˜ƒ, â˜ƒ) && â˜ƒ.mayInteract(this, â˜ƒ);
   }

   @Override
   protected void updateUsingItem(ItemStack var1) {
      CriteriaTriggers.USING_ITEM.trigger(this, â˜ƒ);
      super.updateUsingItem(â˜ƒ);
   }

   public boolean drop(boolean var1) {
      Inventory â˜ƒ = this.getInventory();
      ItemStack â˜ƒx = â˜ƒ.removeFromSelected(â˜ƒ);
      this.containerMenu.findSlot(â˜ƒ, â˜ƒ.selected).ifPresent(var2x -> this.containerMenu.setRemoteSlot(var2x, â˜ƒ.getSelected()));
      return this.drop(â˜ƒx, false, true) != null;
   }
}
