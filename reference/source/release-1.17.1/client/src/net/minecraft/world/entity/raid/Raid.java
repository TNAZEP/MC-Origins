package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Raid {
   private static final int SECTION_RADIUS_FOR_FINDING_NEW_VILLAGE_CENTER = 2;
   private static final int ATTEMPT_RAID_FARTHEST = 0;
   private static final int ATTEMPT_RAID_CLOSE = 1;
   private static final int ATTEMPT_RAID_INSIDE = 2;
   private static final int VILLAGE_SEARCH_RADIUS = 32;
   private static final int RAID_TIMEOUT_TICKS = 48000;
   private static final int NUM_SPAWN_ATTEMPTS = 3;
   private static final String OMINOUS_BANNER_PATTERN_NAME = "block.minecraft.ominous_banner";
   private static final String RAIDERS_REMAINING = "event.minecraft.raid.raiders_remaining";
   public static final int VILLAGE_RADIUS_BUFFER = 16;
   private static final int POST_RAID_TICK_LIMIT = 40;
   private static final int DEFAULT_PRE_RAID_TICKS = 300;
   public static final int MAX_NO_ACTION_TIME = 2400;
   public static final int MAX_CELEBRATION_TICKS = 600;
   private static final int OUTSIDE_RAID_BOUNDS_TIMEOUT = 30;
   public static final int TICKS_PER_DAY = 24000;
   public static final int DEFAULT_MAX_BAD_OMEN_LEVEL = 5;
   private static final int LOW_MOB_THRESHOLD = 2;
   private static final Component RAID_NAME_COMPONENT = new TranslatableComponent("event.minecraft.raid");
   private static final Component VICTORY = new TranslatableComponent("event.minecraft.raid.victory");
   private static final Component DEFEAT = new TranslatableComponent("event.minecraft.raid.defeat");
   private static final Component RAID_BAR_VICTORY_COMPONENT = RAID_NAME_COMPONENT.copy().append(" - ").append(VICTORY);
   private static final Component RAID_BAR_DEFEAT_COMPONENT = RAID_NAME_COMPONENT.copy().append(" - ").append(DEFEAT);
   private static final int HERO_OF_THE_VILLAGE_DURATION = 48000;
   public static final int VALID_RAID_RADIUS_SQR = 9216;
   public static final int RAID_REMOVAL_THRESHOLD_SQR = 12544;
   private final Map<Integer, Raider> groupToLeaderMap = Maps.newHashMap();
   private final Map<Integer, Set<Raider>> groupRaiderMap = Maps.newHashMap();
   private final Set<UUID> heroesOfTheVillage = Sets.newHashSet();
   private long ticksActive;
   private BlockPos center;
   private final ServerLevel level;
   private boolean started;
   private final int id;
   private float totalHealth;
   private int badOmenLevel;
   private boolean active;
   private int groupsSpawned;
   private final ServerBossEvent raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);
   private int postRaidTicks;
   private int raidCooldownTicks;
   private final Random random = new Random();
   private final int numGroups;
   private Raid.RaidStatus status;
   private int celebrationTicks;
   private Optional<BlockPos> waveSpawnPos = Optional.empty();

   public Raid(int var1, ServerLevel var2, BlockPos var3) {
      this.id = â˜ƒ;
      this.level = â˜ƒ;
      this.active = true;
      this.raidCooldownTicks = 300;
      this.raidEvent.setProgress(0.0F);
      this.center = â˜ƒ;
      this.numGroups = this.getNumGroups(â˜ƒ.getDifficulty());
      this.status = Raid.RaidStatus.ONGOING;
   }

   public Raid(ServerLevel var1, CompoundTag var2) {
      this.level = â˜ƒ;
      this.id = â˜ƒ.getInt("Id");
      this.started = â˜ƒ.getBoolean("Started");
      this.active = â˜ƒ.getBoolean("Active");
      this.ticksActive = â˜ƒ.getLong("TicksActive");
      this.badOmenLevel = â˜ƒ.getInt("BadOmenLevel");
      this.groupsSpawned = â˜ƒ.getInt("GroupsSpawned");
      this.raidCooldownTicks = â˜ƒ.getInt("PreRaidTicks");
      this.postRaidTicks = â˜ƒ.getInt("PostRaidTicks");
      this.totalHealth = â˜ƒ.getFloat("TotalHealth");
      this.center = new BlockPos(â˜ƒ.getInt("CX"), â˜ƒ.getInt("CY"), â˜ƒ.getInt("CZ"));
      this.numGroups = â˜ƒ.getInt("NumGroups");
      this.status = Raid.RaidStatus.getByName(â˜ƒ.getString("Status"));
      this.heroesOfTheVillage.clear();
      if (â˜ƒ.contains("HeroesOfTheVillage", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("HeroesOfTheVillage", 11);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            this.heroesOfTheVillage.add(NbtUtils.loadUUID(â˜ƒ.get(â˜ƒx)));
         }
      }
   }

   public boolean isOver() {
      return this.isVictory() || this.isLoss();
   }

   public boolean isBetweenWaves() {
      return this.hasFirstWaveSpawned() && this.getTotalRaidersAlive() == 0 && this.raidCooldownTicks > 0;
   }

   public boolean hasFirstWaveSpawned() {
      return this.groupsSpawned > 0;
   }

   public boolean isStopped() {
      return this.status == Raid.RaidStatus.STOPPED;
   }

   public boolean isVictory() {
      return this.status == Raid.RaidStatus.VICTORY;
   }

   public boolean isLoss() {
      return this.status == Raid.RaidStatus.LOSS;
   }

   public float getTotalHealth() {
      return this.totalHealth;
   }

   public Set<Raider> getAllRaiders() {
      Set<Raider> â˜ƒ = Sets.<Raider>newHashSet();

      for(Set<Raider> â˜ƒx : this.groupRaiderMap.values()) {
         â˜ƒ.addAll(â˜ƒx);
      }

      return â˜ƒ;
   }

   public Level getLevel() {
      return this.level;
   }

   public boolean isStarted() {
      return this.started;
   }

   public int getGroupsSpawned() {
      return this.groupsSpawned;
   }

   private Predicate<ServerPlayer> validPlayer() {
      return var1 -> {
         BlockPos â˜ƒ = var1.blockPosition();
         return var1.isAlive() && this.level.getRaidAt(â˜ƒ) == this;
      };
   }

   private void updatePlayers() {
      Set<ServerPlayer> â˜ƒ = Sets.<ServerPlayer>newHashSet(this.raidEvent.getPlayers());
      List<ServerPlayer> â˜ƒx = this.level.getPlayers(this.validPlayer());

      for(ServerPlayer â˜ƒxx : â˜ƒx) {
         if (!â˜ƒ.contains(â˜ƒxx)) {
            this.raidEvent.addPlayer(â˜ƒxx);
         }
      }

      for(ServerPlayer â˜ƒxx : â˜ƒ) {
         if (!â˜ƒx.contains(â˜ƒxx)) {
            this.raidEvent.removePlayer(â˜ƒxx);
         }
      }
   }

   public int getMaxBadOmenLevel() {
      return 5;
   }

   public int getBadOmenLevel() {
      return this.badOmenLevel;
   }

   public void setBadOmenLevel(int var1) {
      this.badOmenLevel = â˜ƒ;
   }

   public void absorbBadOmen(Player var1) {
      if (â˜ƒ.hasEffect(MobEffects.BAD_OMEN)) {
         this.badOmenLevel += â˜ƒ.getEffect(MobEffects.BAD_OMEN).getAmplifier() + 1;
         this.badOmenLevel = Mth.clamp(this.badOmenLevel, 0, this.getMaxBadOmenLevel());
      }

      â˜ƒ.removeEffect(MobEffects.BAD_OMEN);
   }

   public void stop() {
      this.active = false;
      this.raidEvent.removeAllPlayers();
      this.status = Raid.RaidStatus.STOPPED;
   }

   public void tick() {
      if (!this.isStopped()) {
         if (this.status == Raid.RaidStatus.ONGOING) {
            boolean â˜ƒ = this.active;
            this.active = this.level.hasChunkAt(this.center);
            if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
               this.stop();
               return;
            }

            if (â˜ƒ != this.active) {
               this.raidEvent.setVisible(this.active);
            }

            if (!this.active) {
               return;
            }

            if (!this.level.isVillage(this.center)) {
               this.moveRaidCenterToNearbyVillageSection();
            }

            if (!this.level.isVillage(this.center)) {
               if (this.groupsSpawned > 0) {
                  this.status = Raid.RaidStatus.LOSS;
               } else {
                  this.stop();
               }
            }

            ++this.ticksActive;
            if (this.ticksActive >= 48000L) {
               this.stop();
               return;
            }

            int â˜ƒ = this.getTotalRaidersAlive();
            if (â˜ƒ == 0 && this.hasMoreWaves()) {
               if (this.raidCooldownTicks <= 0) {
                  if (this.raidCooldownTicks == 0 && this.groupsSpawned > 0) {
                     this.raidCooldownTicks = 300;
                     this.raidEvent.setName(RAID_NAME_COMPONENT);
                     return;
                  }
               } else {
                  boolean â˜ƒx = this.waveSpawnPos.isPresent();
                  boolean â˜ƒxx = !â˜ƒx && this.raidCooldownTicks % 5 == 0;
                  if (â˜ƒx && !this.level.isPositionEntityTicking((BlockPos)this.waveSpawnPos.get())) {
                     â˜ƒxx = true;
                  }

                  if (â˜ƒxx) {
                     int â˜ƒx = 0;
                     if (this.raidCooldownTicks < 100) {
                        â˜ƒx = 1;
                     } else if (this.raidCooldownTicks < 40) {
                        â˜ƒx = 2;
                     }

                     this.waveSpawnPos = this.getValidSpawnPos(â˜ƒx);
                  }

                  if (this.raidCooldownTicks == 300 || this.raidCooldownTicks % 20 == 0) {
                     this.updatePlayers();
                  }

                  --this.raidCooldownTicks;
                  this.raidEvent.setProgress(Mth.clamp((float)(300 - this.raidCooldownTicks) / 300.0F, 0.0F, 1.0F));
               }
            }

            if (this.ticksActive % 20L == 0L) {
               this.updatePlayers();
               this.updateRaiders();
               if (â˜ƒ > 0) {
                  if (â˜ƒ <= 2) {
                     this.raidEvent
                        .setName(RAID_NAME_COMPONENT.copy().append(" - ").append(new TranslatableComponent("event.minecraft.raid.raiders_remaining", â˜ƒ)));
                  } else {
                     this.raidEvent.setName(RAID_NAME_COMPONENT);
                  }
               } else {
                  this.raidEvent.setName(RAID_NAME_COMPONENT);
               }
            }

            boolean â˜ƒ = false;
            int â˜ƒx = 0;

            while(this.shouldSpawnGroup()) {
               BlockPos â˜ƒxx = this.waveSpawnPos.isPresent() ? (BlockPos)this.waveSpawnPos.get() : this.findRandomSpawnPos(â˜ƒx, 20);
               if (â˜ƒxx != null) {
                  this.started = true;
                  this.spawnGroup(â˜ƒxx);
                  if (!â˜ƒ) {
                     this.playSound(â˜ƒxx);
                     â˜ƒ = true;
                  }
               } else {
                  ++â˜ƒx;
               }

               if (â˜ƒx > 3) {
                  this.stop();
                  break;
               }
            }

            if (this.isStarted() && !this.hasMoreWaves() && â˜ƒ == 0) {
               if (this.postRaidTicks < 40) {
                  ++this.postRaidTicks;
               } else {
                  this.status = Raid.RaidStatus.VICTORY;

                  for(UUID â˜ƒxx : this.heroesOfTheVillage) {
                     Entity â˜ƒxxxx = this.level.getEntity(â˜ƒxx);
                     if (â˜ƒxxxx instanceof LivingEntity â˜ƒxxx && !â˜ƒxxxx.isSpectator()) {
                        â˜ƒxxx.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true));
                        if (â˜ƒxxx instanceof ServerPlayer â˜ƒxxxxx) {
                           â˜ƒxxxxx.awardStat(Stats.RAID_WIN);
                           CriteriaTriggers.RAID_WIN.trigger(â˜ƒxxxxx);
                        }
                     }
                  }
               }
            }

            this.setDirty();
         } else if (this.isOver()) {
            ++this.celebrationTicks;
            if (this.celebrationTicks >= 600) {
               this.stop();
               return;
            }

            if (this.celebrationTicks % 20 == 0) {
               this.updatePlayers();
               this.raidEvent.setVisible(true);
               if (this.isVictory()) {
                  this.raidEvent.setProgress(0.0F);
                  this.raidEvent.setName(RAID_BAR_VICTORY_COMPONENT);
               } else {
                  this.raidEvent.setName(RAID_BAR_DEFEAT_COMPONENT);
               }
            }
         }
      }
   }

   private void moveRaidCenterToNearbyVillageSection() {
      Stream<SectionPos> â˜ƒ = SectionPos.cube(SectionPos.of(this.center), 2);
      â˜ƒ.filter(this.level::isVillage).map(SectionPos::center).min(Comparator.comparingDouble(var1x -> var1x.distSqr(this.center))).ifPresent(this::setCenter);
   }

   private Optional<BlockPos> getValidSpawnPos(int var1) {
      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         BlockPos â˜ƒx = this.findRandomSpawnPos(â˜ƒ, 1);
         if (â˜ƒx != null) {
            return Optional.of(â˜ƒx);
         }
      }

      return Optional.empty();
   }

   private boolean hasMoreWaves() {
      if (this.hasBonusWave()) {
         return !this.hasSpawnedBonusWave();
      } else {
         return !this.isFinalWave();
      }
   }

   private boolean isFinalWave() {
      return this.getGroupsSpawned() == this.numGroups;
   }

   private boolean hasBonusWave() {
      return this.badOmenLevel > 1;
   }

   private boolean hasSpawnedBonusWave() {
      return this.getGroupsSpawned() > this.numGroups;
   }

   private boolean shouldSpawnBonusGroup() {
      return this.isFinalWave() && this.getTotalRaidersAlive() == 0 && this.hasBonusWave();
   }

   private void updateRaiders() {
      Iterator<Set<Raider>> â˜ƒ = this.groupRaiderMap.values().iterator();
      Set<Raider> â˜ƒx = Sets.<Raider>newHashSet();

      while(â˜ƒ.hasNext()) {
         Set<Raider> â˜ƒxx = (Set)â˜ƒ.next();

         for(Raider â˜ƒxxx : â˜ƒxx) {
            BlockPos â˜ƒxxxx = â˜ƒxxx.blockPosition();
            if (â˜ƒxxx.isRemoved() || â˜ƒxxx.level.dimension() != this.level.dimension() || this.center.distSqr(â˜ƒxxxx) >= 12544.0) {
               â˜ƒx.add(â˜ƒxxx);
            } else if (â˜ƒxxx.tickCount > 600) {
               if (this.level.getEntity(â˜ƒxxx.getUUID()) == null) {
                  â˜ƒx.add(â˜ƒxxx);
               }

               if (!this.level.isVillage(â˜ƒxxxx) && â˜ƒxxx.getNoActionTime() > 2400) {
                  â˜ƒxxx.setTicksOutsideRaid(â˜ƒxxx.getTicksOutsideRaid() + 1);
               }

               if (â˜ƒxxx.getTicksOutsideRaid() >= 30) {
                  â˜ƒx.add(â˜ƒxxx);
               }
            }
         }
      }

      for(Raider â˜ƒxx : â˜ƒx) {
         this.removeFromRaid(â˜ƒxx, true);
      }
   }

   private void playSound(BlockPos var1) {
      float â˜ƒ = 13.0F;
      int â˜ƒx = 64;
      Collection<ServerPlayer> â˜ƒxx = this.raidEvent.getPlayers();

      for(ServerPlayer â˜ƒxxx : this.level.players()) {
         Vec3 â˜ƒxxxx = â˜ƒxxx.position();
         Vec3 â˜ƒxxxxx = Vec3.atCenterOf(â˜ƒ);
         double â˜ƒxxxxxx = Math.sqrt((â˜ƒxxxxx.x - â˜ƒxxxx.x) * (â˜ƒxxxxx.x - â˜ƒxxxx.x) + (â˜ƒxxxxx.z - â˜ƒxxxx.z) * (â˜ƒxxxxx.z - â˜ƒxxxx.z));
         double â˜ƒxxxxxxx = â˜ƒxxxx.x + 13.0 / â˜ƒxxxxxx * (â˜ƒxxxxx.x - â˜ƒxxxx.x);
         double â˜ƒxxxxxxxx = â˜ƒxxxx.z + 13.0 / â˜ƒxxxxxx * (â˜ƒxxxxx.z - â˜ƒxxxx.z);
         if (â˜ƒxxxxxx <= 64.0 || â˜ƒxx.contains(â˜ƒxxx)) {
            â˜ƒxxx.connection.send(new ClientboundSoundPacket(SoundEvents.RAID_HORN, SoundSource.NEUTRAL, â˜ƒxxxxxxx, â˜ƒxxx.getY(), â˜ƒxxxxxxxx, 64.0F, 1.0F));
         }
      }
   }

   private void spawnGroup(BlockPos var1) {
      boolean â˜ƒ = false;
      int â˜ƒx = this.groupsSpawned + 1;
      this.totalHealth = 0.0F;
      DifficultyInstance â˜ƒxx = this.level.getCurrentDifficultyAt(â˜ƒ);
      boolean â˜ƒxxx = this.shouldSpawnBonusGroup();

      for(Raid.RaiderType â˜ƒxxxx : Raid.RaiderType.VALUES) {
         int â˜ƒxxxxx = this.getDefaultNumSpawns(â˜ƒxxxx, â˜ƒx, â˜ƒxxx) + this.getPotentialBonusSpawns(â˜ƒxxxx, this.random, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         int â˜ƒxxxxxx = 0;

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
            Raider â˜ƒxxxxxxxx = â˜ƒxxxx.entityType.create(this.level);
            if (!â˜ƒ && â˜ƒxxxxxxxx.canBeLeader()) {
               â˜ƒxxxxxxxx.setPatrolLeader(true);
               this.setLeader(â˜ƒx, â˜ƒxxxxxxxx);
               â˜ƒ = true;
            }

            this.joinRaid(â˜ƒx, â˜ƒxxxxxxxx, â˜ƒ, false);
            if (â˜ƒxxxx.entityType == EntityType.RAVAGER) {
               Raider â˜ƒxxxxxxxx = null;
               if (â˜ƒx == this.getNumGroups(Difficulty.NORMAL)) {
                  â˜ƒxxxxxxxx = EntityType.PILLAGER.create(this.level);
               } else if (â˜ƒx >= this.getNumGroups(Difficulty.HARD)) {
                  if (â˜ƒxxxxxx == 0) {
                     â˜ƒxxxxxxxx = EntityType.EVOKER.create(this.level);
                  } else {
                     â˜ƒxxxxxxxx = EntityType.VINDICATOR.create(this.level);
                  }
               }

               ++â˜ƒxxxxxx;
               if (â˜ƒxxxxxxxx != null) {
                  this.joinRaid(â˜ƒx, â˜ƒxxxxxxxx, â˜ƒ, false);
                  â˜ƒxxxxxxxx.moveTo(â˜ƒ, 0.0F, 0.0F);
                  â˜ƒxxxxxxxx.startRiding(â˜ƒxxxxxxxx);
               }
            }
         }
      }

      this.waveSpawnPos = Optional.empty();
      ++this.groupsSpawned;
      this.updateBossbar();
      this.setDirty();
   }

   public void joinRaid(int var1, Raider var2, @Nullable BlockPos var3, boolean var4) {
      boolean â˜ƒ = this.addWaveMob(â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.setCurrentRaid(this);
         â˜ƒ.setWave(â˜ƒ);
         â˜ƒ.setCanJoinRaid(true);
         â˜ƒ.setTicksOutsideRaid(0);
         if (!â˜ƒ && â˜ƒ != null) {
            â˜ƒ.setPos((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 1.0, (double)â˜ƒ.getZ() + 0.5);
            â˜ƒ.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(â˜ƒ), MobSpawnType.EVENT, null, null);
            â˜ƒ.applyRaidBuffs(â˜ƒ, false);
            â˜ƒ.setOnGround(true);
            this.level.addFreshEntityWithPassengers(â˜ƒ);
         }
      }
   }

   public void updateBossbar() {
      this.raidEvent.setProgress(Mth.clamp(this.getHealthOfLivingRaiders() / this.totalHealth, 0.0F, 1.0F));
   }

   public float getHealthOfLivingRaiders() {
      float â˜ƒ = 0.0F;

      for(Set<Raider> â˜ƒx : this.groupRaiderMap.values()) {
         for(Raider â˜ƒxx : â˜ƒx) {
            â˜ƒ += â˜ƒxx.getHealth();
         }
      }

      return â˜ƒ;
   }

   private boolean shouldSpawnGroup() {
      return this.raidCooldownTicks == 0 && (this.groupsSpawned < this.numGroups || this.shouldSpawnBonusGroup()) && this.getTotalRaidersAlive() == 0;
   }

   public int getTotalRaidersAlive() {
      return this.groupRaiderMap.values().stream().mapToInt(Set::size).sum();
   }

   public void removeFromRaid(Raider var1, boolean var2) {
      Set<Raider> â˜ƒ = (Set)this.groupRaiderMap.get(â˜ƒ.getWave());
      if (â˜ƒ != null) {
         boolean â˜ƒx = â˜ƒ.remove(â˜ƒ);
         if (â˜ƒx) {
            if (â˜ƒ) {
               this.totalHealth -= â˜ƒ.getHealth();
            }

            â˜ƒ.setCurrentRaid(null);
            this.updateBossbar();
            this.setDirty();
         }
      }
   }

   private void setDirty() {
      this.level.getRaids().setDirty();
   }

   public static ItemStack getLeaderBannerInstance() {
      ItemStack â˜ƒ = new ItemStack(Items.WHITE_BANNER);
      CompoundTag â˜ƒx = â˜ƒ.getOrCreateTagElement("BlockEntityTag");
      ListTag â˜ƒxx = new BannerPattern.Builder()
         .addPattern(BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN)
         .addPattern(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
         .addPattern(BannerPattern.STRIPE_CENTER, DyeColor.GRAY)
         .addPattern(BannerPattern.BORDER, DyeColor.LIGHT_GRAY)
         .addPattern(BannerPattern.STRIPE_MIDDLE, DyeColor.BLACK)
         .addPattern(BannerPattern.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
         .addPattern(BannerPattern.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
         .addPattern(BannerPattern.BORDER, DyeColor.BLACK)
         .toListTag();
      â˜ƒx.put("Patterns", â˜ƒxx);
      â˜ƒ.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
      â˜ƒ.setHoverName(new TranslatableComponent("block.minecraft.ominous_banner").withStyle(ChatFormatting.GOLD));
      return â˜ƒ;
   }

   @Nullable
   public Raider getLeader(int var1) {
      return (Raider)this.groupToLeaderMap.get(â˜ƒ);
   }

   @Nullable
   private BlockPos findRandomSpawnPos(int var1, int var2) {
      int â˜ƒ = â˜ƒ == 0 ? 2 : 2 - â˜ƒ;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         float â˜ƒxxx = this.level.random.nextFloat() * (float) (Math.PI * 2);
         int â˜ƒxxxx = this.center.getX() + Mth.floor(Mth.cos(â˜ƒxxx) * 32.0F * (float)â˜ƒ) + this.level.random.nextInt(5);
         int â˜ƒxxxxx = this.center.getZ() + Mth.floor(Mth.sin(â˜ƒxxx) * 32.0F * (float)â˜ƒ) + this.level.random.nextInt(5);
         int â˜ƒxxxxxx = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒxxxx, â˜ƒxxxxx);
         â˜ƒx.set(â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxx);
         if (!this.level.isVillage(â˜ƒx) || â˜ƒ >= 2) {
            int â˜ƒxxxxxxx = 10;
            if (this.level.hasChunksAt(â˜ƒx.getX() - 10, â˜ƒx.getZ() - 10, â˜ƒx.getX() + 10, â˜ƒx.getZ() + 10)
               && this.level.isPositionEntityTicking(â˜ƒx)
               && (
                  NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, this.level, â˜ƒx, EntityType.RAVAGER)
                     || this.level.getBlockState(â˜ƒx.below()).is(Blocks.SNOW) && this.level.getBlockState(â˜ƒx).isAir()
               )) {
               return â˜ƒx;
            }
         }
      }

      return null;
   }

   private boolean addWaveMob(int var1, Raider var2) {
      return this.addWaveMob(â˜ƒ, â˜ƒ, true);
   }

   public boolean addWaveMob(int var1, Raider var2, boolean var3) {
      this.groupRaiderMap.computeIfAbsent(â˜ƒ, var0 -> Sets.newHashSet());
      Set<Raider> â˜ƒ = (Set)this.groupRaiderMap.get(â˜ƒ);
      Raider â˜ƒx = null;

      for(Raider â˜ƒxx : â˜ƒ) {
         if (â˜ƒxx.getUUID().equals(â˜ƒ.getUUID())) {
            â˜ƒx = â˜ƒxx;
            break;
         }
      }

      if (â˜ƒx != null) {
         â˜ƒ.remove(â˜ƒx);
         â˜ƒ.add(â˜ƒ);
      }

      â˜ƒ.add(â˜ƒ);
      if (â˜ƒ) {
         this.totalHealth += â˜ƒ.getHealth();
      }

      this.updateBossbar();
      this.setDirty();
      return true;
   }

   public void setLeader(int var1, Raider var2) {
      this.groupToLeaderMap.put(â˜ƒ, â˜ƒ);
      â˜ƒ.setItemSlot(EquipmentSlot.HEAD, getLeaderBannerInstance());
      â˜ƒ.setDropChance(EquipmentSlot.HEAD, 2.0F);
   }

   public void removeLeader(int var1) {
      this.groupToLeaderMap.remove(â˜ƒ);
   }

   public BlockPos getCenter() {
      return this.center;
   }

   private void setCenter(BlockPos var1) {
      this.center = â˜ƒ;
   }

   public int getId() {
      return this.id;
   }

   private int getDefaultNumSpawns(Raid.RaiderType var1, int var2, boolean var3) {
      return â˜ƒ ? â˜ƒ.spawnsPerWaveBeforeBonus[this.numGroups] : â˜ƒ.spawnsPerWaveBeforeBonus[â˜ƒ];
   }

   private int getPotentialBonusSpawns(Raid.RaiderType var1, Random var2, int var3, DifficultyInstance var4, boolean var5) {
      Difficulty â˜ƒx = â˜ƒ.getDifficulty();
      boolean â˜ƒxx = â˜ƒx == Difficulty.EASY;
      boolean â˜ƒxxx = â˜ƒx == Difficulty.NORMAL;
      int â˜ƒ;
      switch(â˜ƒ) {
         case WITCH:
            if (â˜ƒxx || â˜ƒ <= 2 || â˜ƒ == 4) {
               return 0;
            }

            â˜ƒ = 1;
            break;
         case PILLAGER:
         case VINDICATOR:
            if (â˜ƒxx) {
               â˜ƒ = â˜ƒ.nextInt(2);
            } else if (â˜ƒxxx) {
               â˜ƒ = 1;
            } else {
               â˜ƒ = 2;
            }
            break;
         case RAVAGER:
            â˜ƒ = !â˜ƒxx && â˜ƒ ? 1 : 0;
            break;
         default:
            return 0;
      }

      return â˜ƒ > 0 ? â˜ƒ.nextInt(â˜ƒ + 1) : 0;
   }

   public boolean isActive() {
      return this.active;
   }

   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.putInt("Id", this.id);
      â˜ƒ.putBoolean("Started", this.started);
      â˜ƒ.putBoolean("Active", this.active);
      â˜ƒ.putLong("TicksActive", this.ticksActive);
      â˜ƒ.putInt("BadOmenLevel", this.badOmenLevel);
      â˜ƒ.putInt("GroupsSpawned", this.groupsSpawned);
      â˜ƒ.putInt("PreRaidTicks", this.raidCooldownTicks);
      â˜ƒ.putInt("PostRaidTicks", this.postRaidTicks);
      â˜ƒ.putFloat("TotalHealth", this.totalHealth);
      â˜ƒ.putInt("NumGroups", this.numGroups);
      â˜ƒ.putString("Status", this.status.getName());
      â˜ƒ.putInt("CX", this.center.getX());
      â˜ƒ.putInt("CY", this.center.getY());
      â˜ƒ.putInt("CZ", this.center.getZ());
      ListTag â˜ƒ = new ListTag();

      for(UUID â˜ƒx : this.heroesOfTheVillage) {
         â˜ƒ.add(NbtUtils.createUUID(â˜ƒx));
      }

      â˜ƒ.put("HeroesOfTheVillage", â˜ƒ);
      return â˜ƒ;
   }

   public int getNumGroups(Difficulty var1) {
      switch(â˜ƒ) {
         case EASY:
            return 3;
         case NORMAL:
            return 5;
         case HARD:
            return 7;
         default:
            return 0;
      }
   }

   public float getEnchantOdds() {
      int â˜ƒ = this.getBadOmenLevel();
      if (â˜ƒ == 2) {
         return 0.1F;
      } else if (â˜ƒ == 3) {
         return 0.25F;
      } else if (â˜ƒ == 4) {
         return 0.5F;
      } else {
         return â˜ƒ == 5 ? 0.75F : 0.0F;
      }
   }

   public void addHeroOfTheVillage(Entity var1) {
      this.heroesOfTheVillage.add(â˜ƒ.getUUID());
   }

   static enum RaidStatus {
      ONGOING,
      VICTORY,
      LOSS,
      STOPPED;

      private static final Raid.RaidStatus[] VALUES = values();

      static Raid.RaidStatus getByName(String var0) {
         for(Raid.RaidStatus â˜ƒ : VALUES) {
            if (â˜ƒ.equalsIgnoreCase(â˜ƒ.name())) {
               return â˜ƒ;
            }
         }

         return ONGOING;
      }

      public String getName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   static enum RaiderType {
      VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
      EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
      PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
      WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
      RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

      static final Raid.RaiderType[] VALUES = values();
      final EntityType<? extends Raider> entityType;
      final int[] spawnsPerWaveBeforeBonus;

      private RaiderType(EntityType<? extends Raider> var3, int[] var4) {
         this.entityType = â˜ƒ;
         this.spawnsPerWaveBeforeBonus = â˜ƒ;
      }
   }
}
