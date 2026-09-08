package net.minecraft.world.entity.boss.enderdragon;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragon extends Mob implements Enemy {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(EnderDragon.class, EntityDataSerializers.INT);
   private static final TargetingConditions CRYSTAL_DESTROY_TARGETING = TargetingConditions.forCombat().range(64.0);
   private static final int GROWL_INTERVAL_MIN = 200;
   private static final int GROWL_INTERVAL_MAX = 400;
   private static final float SITTING_ALLOWED_DAMAGE_PERCENTAGE = 0.25F;
   private static final String DRAGON_DEATH_TIME_KEY = "DragonDeathTime";
   private static final String DRAGON_PHASE_KEY = "DragonPhase";
   public final double[][] positions = new double[64][3];
   public int posPointer = -1;
   private final EnderDragonPart[] subEntities;
   public final EnderDragonPart head;
   private final EnderDragonPart neck;
   private final EnderDragonPart body;
   private final EnderDragonPart tail1;
   private final EnderDragonPart tail2;
   private final EnderDragonPart tail3;
   private final EnderDragonPart wing1;
   private final EnderDragonPart wing2;
   public float oFlapTime;
   public float flapTime;
   public boolean inWall;
   public int dragonDeathTime;
   public float yRotA;
   @Nullable
   public EndCrystal nearestCrystal;
   @Nullable
   private final EndDragonFight dragonFight;
   private final EnderDragonPhaseManager phaseManager;
   private int growlTime = 100;
   private int sittingDamageReceived;
   private final Node[] nodes = new Node[24];
   private final int[] nodeAdjacency = new int[24];
   private final BinaryHeap openSet = new BinaryHeap();

   public EnderDragon(EntityType<? extends EnderDragon> var1, Level var2) {
      super(EntityType.ENDER_DRAGON, â˜ƒ);
      this.head = new EnderDragonPart(this, "head", 1.0F, 1.0F);
      this.neck = new EnderDragonPart(this, "neck", 3.0F, 3.0F);
      this.body = new EnderDragonPart(this, "body", 5.0F, 3.0F);
      this.tail1 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.tail2 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.tail3 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.wing1 = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
      this.wing2 = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
      this.subEntities = new EnderDragonPart[]{this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.wing1, this.wing2};
      this.setHealth(this.getMaxHealth());
      this.noPhysics = true;
      this.noCulling = true;
      if (â˜ƒ instanceof ServerLevel) {
         this.dragonFight = ((ServerLevel)â˜ƒ).dragonFight();
      } else {
         this.dragonFight = null;
      }

      this.phaseManager = new EnderDragonPhaseManager(this);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0);
   }

   @Override
   public boolean isFlapping() {
      float â˜ƒ = Mth.cos(this.flapTime * (float) (Math.PI * 2));
      float â˜ƒx = Mth.cos(this.oFlapTime * (float) (Math.PI * 2));
      return â˜ƒx <= -0.3F && â˜ƒ >= -0.3F;
   }

   @Override
   public void onFlap() {
      if (this.level.isClientSide && !this.isSilent()) {
         this.level
            .playLocalSound(
               this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_FLAP, this.getSoundSource(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false
            );
      }
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.getEntityData().define(DATA_PHASE, EnderDragonPhase.HOVERING.getId());
   }

   public double[] getLatencyPos(int var1, float var2) {
      if (this.isDeadOrDying()) {
         â˜ƒ = 0.0F;
      }

      â˜ƒ = 1.0F - â˜ƒ;
      int â˜ƒ = this.posPointer - â˜ƒ & 63;
      int â˜ƒx = this.posPointer - â˜ƒ - 1 & 63;
      double[] â˜ƒxx = new double[3];
      double â˜ƒxxx = this.positions[â˜ƒ][0];
      double â˜ƒxxxx = Mth.wrapDegrees(this.positions[â˜ƒx][0] - â˜ƒxxx);
      â˜ƒxx[0] = â˜ƒxxx + â˜ƒxxxx * (double)â˜ƒ;
      â˜ƒxxx = this.positions[â˜ƒ][1];
      â˜ƒxxxx = this.positions[â˜ƒx][1] - â˜ƒxxx;
      â˜ƒxx[1] = â˜ƒxxx + â˜ƒxxxx * (double)â˜ƒ;
      â˜ƒxx[2] = Mth.lerp((double)â˜ƒ, this.positions[â˜ƒ][2], this.positions[â˜ƒx][2]);
      return â˜ƒxx;
   }

   @Override
   public void aiStep() {
      this.processFlappingMovement();
      if (this.level.isClientSide) {
         this.setHealth(this.getHealth());
         if (!this.isSilent() && !this.phaseManager.getCurrentPhase().isSitting() && --this.growlTime < 0) {
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.ENDER_DRAGON_GROWL,
                  this.getSoundSource(),
                  2.5F,
                  0.8F + this.random.nextFloat() * 0.3F,
                  false
               );
            this.growlTime = 200 + this.random.nextInt(200);
         }
      }

      this.oFlapTime = this.flapTime;
      if (this.isDeadOrDying()) {
         float â˜ƒ = (this.random.nextFloat() - 0.5F) * 8.0F;
         float â˜ƒx = (this.random.nextFloat() - 0.5F) * 4.0F;
         float â˜ƒxx = (this.random.nextFloat() - 0.5F) * 8.0F;
         this.level
            .addParticle(ParticleTypes.EXPLOSION, this.getX() + (double)â˜ƒ, this.getY() + 2.0 + (double)â˜ƒx, this.getZ() + (double)â˜ƒxx, 0.0, 0.0, 0.0);
      } else {
         this.checkCrystals();
         Vec3 â˜ƒ = this.getDeltaMovement();
         float â˜ƒx = 0.2F / ((float)â˜ƒ.horizontalDistance() * 10.0F + 1.0F);
         â˜ƒx *= (float)Math.pow(2.0, â˜ƒ.y);
         if (this.phaseManager.getCurrentPhase().isSitting()) {
            this.flapTime += 0.1F;
         } else if (this.inWall) {
            this.flapTime += â˜ƒx * 0.5F;
         } else {
            this.flapTime += â˜ƒx;
         }

         this.setYRot(Mth.wrapDegrees(this.getYRot()));
         if (this.isNoAi()) {
            this.flapTime = 0.5F;
         } else {
            if (this.posPointer < 0) {
               for(int â˜ƒ = 0; â˜ƒ < this.positions.length; ++â˜ƒ) {
                  this.positions[â˜ƒ][0] = (double)this.getYRot();
                  this.positions[â˜ƒ][1] = this.getY();
               }
            }

            if (++this.posPointer == this.positions.length) {
               this.posPointer = 0;
            }

            this.positions[this.posPointer][0] = (double)this.getYRot();
            this.positions[this.posPointer][1] = this.getY();
            if (this.level.isClientSide) {
               if (this.lerpSteps > 0) {
                  double â˜ƒ = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
                  double â˜ƒx = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
                  double â˜ƒxx = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
                  double â˜ƒxxx = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
                  this.setYRot(this.getYRot() + (float)â˜ƒxxx / (float)this.lerpSteps);
                  this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
                  --this.lerpSteps;
                  this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
                  this.setRot(this.getYRot(), this.getXRot());
               }

               this.phaseManager.getCurrentPhase().doClientTick();
            } else {
               DragonPhaseInstance â˜ƒ = this.phaseManager.getCurrentPhase();
               â˜ƒ.doServerTick();
               if (this.phaseManager.getCurrentPhase() != â˜ƒ) {
                  â˜ƒ = this.phaseManager.getCurrentPhase();
                  â˜ƒ.doServerTick();
               }

               Vec3 â˜ƒ = â˜ƒ.getFlyTargetLocation();
               if (â˜ƒ != null) {
                  double â˜ƒx = â˜ƒ.x - this.getX();
                  double â˜ƒxx = â˜ƒ.y - this.getY();
                  double â˜ƒxxx = â˜ƒ.z - this.getZ();
                  double â˜ƒxxxx = â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx;
                  float â˜ƒxxxxx = â˜ƒ.getFlySpeed();
                  double â˜ƒxxxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxxx * â˜ƒxxx);
                  if (â˜ƒxxxxxx > 0.0) {
                     â˜ƒxx = Mth.clamp(â˜ƒxx / â˜ƒxxxxxx, (double)(-â˜ƒxxxxx), (double)â˜ƒxxxxx);
                  }

                  this.setDeltaMovement(this.getDeltaMovement().add(0.0, â˜ƒxx * 0.01, 0.0));
                  this.setYRot(Mth.wrapDegrees(this.getYRot()));
                  Vec3 â˜ƒx = â˜ƒ.subtract(this.getX(), this.getY(), this.getZ()).normalize();
                  Vec3 â˜ƒxx = new Vec3(
                        (double)Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)),
                        this.getDeltaMovement().y,
                        (double)(-Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)))
                     )
                     .normalize();
                  float â˜ƒxxx = Math.max(((float)â˜ƒxx.dot(â˜ƒx) + 0.5F) / 1.5F, 0.0F);
                  if (Math.abs(â˜ƒx) > 1.0E-5F || Math.abs(â˜ƒxxx) > 1.0E-5F) {
                     double â˜ƒxxxx = Mth.clamp(
                        Mth.wrapDegrees(180.0 - Mth.atan2(â˜ƒx, â˜ƒxxx) * 180.0F / (float)Math.PI - (double)this.getYRot()), -50.0, 50.0
                     );
                     this.yRotA *= 0.8F;
                     this.yRotA = (float)((double)this.yRotA + â˜ƒxxxx * (double)â˜ƒ.getTurnSpeed());
                     this.setYRot(this.getYRot() + this.yRotA * 0.1F);
                  }

                  float â˜ƒx = (float)(2.0 / (â˜ƒxxxx + 1.0));
                  float â˜ƒxx = 0.06F;
                  this.moveRelative(0.06F * (â˜ƒxxx * â˜ƒx + (1.0F - â˜ƒx)), new Vec3(0.0, 0.0, -1.0));
                  if (this.inWall) {
                     this.move(MoverType.SELF, this.getDeltaMovement().scale(0.8F));
                  } else {
                     this.move(MoverType.SELF, this.getDeltaMovement());
                  }

                  Vec3 â˜ƒx = this.getDeltaMovement().normalize();
                  double â˜ƒxx = 0.8 + 0.15 * (â˜ƒx.dot(â˜ƒxx) + 1.0) / 2.0;
                  this.setDeltaMovement(this.getDeltaMovement().multiply(â˜ƒxx, 0.91F, â˜ƒxx));
               }
            }

            this.yBodyRot = this.getYRot();
            Vec3[] â˜ƒ = new Vec3[this.subEntities.length];

            for(int â˜ƒx = 0; â˜ƒx < this.subEntities.length; ++â˜ƒx) {
               â˜ƒ[â˜ƒx] = new Vec3(this.subEntities[â˜ƒx].getX(), this.subEntities[â˜ƒx].getY(), this.subEntities[â˜ƒx].getZ());
            }

            float â˜ƒx = (float)(this.getLatencyPos(5, 1.0F)[1] - this.getLatencyPos(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
            float â˜ƒxx = Mth.cos(â˜ƒx);
            float â˜ƒxxx = Mth.sin(â˜ƒx);
            float â˜ƒxxxx = this.getYRot() * (float) (Math.PI / 180.0);
            float â˜ƒxxxxx = Mth.sin(â˜ƒxxxx);
            float â˜ƒxxxxxx = Mth.cos(â˜ƒxxxx);
            this.tickPart(this.body, (double)(â˜ƒxxxxx * 0.5F), 0.0, (double)(-â˜ƒxxxxxx * 0.5F));
            this.tickPart(this.wing1, (double)(â˜ƒxxxxxx * 4.5F), 2.0, (double)(â˜ƒxxxxx * 4.5F));
            this.tickPart(this.wing2, (double)(â˜ƒxxxxxx * -4.5F), 2.0, (double)(â˜ƒxxxxx * -4.5F));
            if (!this.level.isClientSide && this.hurtTime == 0) {
               this.knockBack(
                  this.level
                     .getEntities(this, this.wing1.getBoundingBox().inflate(4.0, 2.0, 4.0).move(0.0, -2.0, 0.0), EntitySelector.NO_CREATIVE_OR_SPECTATOR)
               );
               this.knockBack(
                  this.level
                     .getEntities(this, this.wing2.getBoundingBox().inflate(4.0, 2.0, 4.0).move(0.0, -2.0, 0.0), EntitySelector.NO_CREATIVE_OR_SPECTATOR)
               );
               this.hurt(this.level.getEntities(this, this.head.getBoundingBox().inflate(1.0), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
               this.hurt(this.level.getEntities(this, this.neck.getBoundingBox().inflate(1.0), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            }

            float â˜ƒx = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0) - this.yRotA * 0.01F);
            float â˜ƒxx = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0) - this.yRotA * 0.01F);
            float â˜ƒxxx = this.getHeadYOffset();
            this.tickPart(this.head, (double)(â˜ƒx * 6.5F * â˜ƒxx), (double)(â˜ƒxxx + â˜ƒxxx * 6.5F), (double)(-â˜ƒxx * 6.5F * â˜ƒxx));
            this.tickPart(this.neck, (double)(â˜ƒx * 5.5F * â˜ƒxx), (double)(â˜ƒxxx + â˜ƒxxx * 5.5F), (double)(-â˜ƒxx * 5.5F * â˜ƒxx));
            double[] â˜ƒxxxx = this.getLatencyPos(5, 1.0F);

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 3; ++â˜ƒxxxxx) {
               EnderDragonPart â˜ƒxxxxxx = null;
               if (â˜ƒxxxxx == 0) {
                  â˜ƒxxxxxx = this.tail1;
               }

               if (â˜ƒxxxxx == 1) {
                  â˜ƒxxxxxx = this.tail2;
               }

               if (â˜ƒxxxxx == 2) {
                  â˜ƒxxxxxx = this.tail3;
               }

               double[] â˜ƒxxxxxx = this.getLatencyPos(12 + â˜ƒxxxxx * 2, 1.0F);
               float â˜ƒxxxxxxx = this.getYRot() * (float) (Math.PI / 180.0) + this.rotWrap(â˜ƒxxxxxx[0] - â˜ƒxxxx[0]) * (float) (Math.PI / 180.0);
               float â˜ƒxxxxxxxx = Mth.sin(â˜ƒxxxxxxx);
               float â˜ƒxxxxxxxxx = Mth.cos(â˜ƒxxxxxxx);
               float â˜ƒxxxxxxxxxx = 1.5F;
               float â˜ƒxxxxxxxxxxx = (float)(â˜ƒxxxxx + 1) * 2.0F;
               this.tickPart(
                  â˜ƒxxxxxx,
                  (double)(-(â˜ƒxxxxx * 1.5F + â˜ƒxxxxxxxx * â˜ƒxxxxxxxxxxx) * â˜ƒxx),
                  â˜ƒxxxxxx[1] - â˜ƒxxxx[1] - (double)((â˜ƒxxxxxxxxxxx + 1.5F) * â˜ƒxxx) + 1.5,
                  (double)((â˜ƒxxxxxx * 1.5F + â˜ƒxxxxxxxxx * â˜ƒxxxxxxxxxxx) * â˜ƒxx)
               );
            }

            if (!this.level.isClientSide) {
               this.inWall = this.checkWalls(this.head.getBoundingBox())
                  | this.checkWalls(this.neck.getBoundingBox())
                  | this.checkWalls(this.body.getBoundingBox());
               if (this.dragonFight != null) {
                  this.dragonFight.updateDragon(this);
               }
            }

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < this.subEntities.length; ++â˜ƒxxxxx) {
               this.subEntities[â˜ƒxxxxx].xo = â˜ƒ[â˜ƒxxxxx].x;
               this.subEntities[â˜ƒxxxxx].yo = â˜ƒ[â˜ƒxxxxx].y;
               this.subEntities[â˜ƒxxxxx].zo = â˜ƒ[â˜ƒxxxxx].z;
               this.subEntities[â˜ƒxxxxx].xOld = â˜ƒ[â˜ƒxxxxx].x;
               this.subEntities[â˜ƒxxxxx].yOld = â˜ƒ[â˜ƒxxxxx].y;
               this.subEntities[â˜ƒxxxxx].zOld = â˜ƒ[â˜ƒxxxxx].z;
            }
         }
      }
   }

   private void tickPart(EnderDragonPart var1, double var2, double var4, double var6) {
      â˜ƒ.setPos(this.getX() + â˜ƒ, this.getY() + â˜ƒ, this.getZ() + â˜ƒ);
   }

   private float getHeadYOffset() {
      if (this.phaseManager.getCurrentPhase().isSitting()) {
         return -1.0F;
      } else {
         double[] â˜ƒ = this.getLatencyPos(5, 1.0F);
         double[] â˜ƒx = this.getLatencyPos(0, 1.0F);
         return (float)(â˜ƒ[1] - â˜ƒx[1]);
      }
   }

   private void checkCrystals() {
      if (this.nearestCrystal != null) {
         if (this.nearestCrystal.isRemoved()) {
            this.nearestCrystal = null;
         } else if (this.tickCount % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.setHealth(this.getHealth() + 1.0F);
         }
      }

      if (this.random.nextInt(10) == 0) {
         List<EndCrystal> â˜ƒ = this.level.getEntitiesOfClass(EndCrystal.class, this.getBoundingBox().inflate(32.0));
         EndCrystal â˜ƒx = null;
         double â˜ƒxx = Double.MAX_VALUE;

         for(EndCrystal â˜ƒxxx : â˜ƒ) {
            double â˜ƒxxxx = â˜ƒxxx.distanceToSqr(this);
            if (â˜ƒxxxx < â˜ƒxx) {
               â˜ƒxx = â˜ƒxxxx;
               â˜ƒx = â˜ƒxxx;
            }
         }

         this.nearestCrystal = â˜ƒx;
      }
   }

   private void knockBack(List<Entity> var1) {
      double â˜ƒ = (this.body.getBoundingBox().minX + this.body.getBoundingBox().maxX) / 2.0;
      double â˜ƒx = (this.body.getBoundingBox().minZ + this.body.getBoundingBox().maxZ) / 2.0;

      for(Entity â˜ƒxx : â˜ƒ) {
         if (â˜ƒxx instanceof LivingEntity) {
            double â˜ƒxxx = â˜ƒxx.getX() - â˜ƒ;
            double â˜ƒxxxx = â˜ƒxx.getZ() - â˜ƒx;
            double â˜ƒxxxxx = Math.max(â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx, 0.1);
            â˜ƒxx.push(â˜ƒxxx / â˜ƒxxxxx * 4.0, 0.2F, â˜ƒxxxx / â˜ƒxxxxx * 4.0);
            if (!this.phaseManager.getCurrentPhase().isSitting() && ((LivingEntity)â˜ƒxx).getLastHurtByMobTimestamp() < â˜ƒxx.tickCount - 2) {
               â˜ƒxx.hurt(DamageSource.mobAttack(this), 5.0F);
               this.doEnchantDamageEffects(this, â˜ƒxx);
            }
         }
      }
   }

   private void hurt(List<Entity> var1) {
      for(Entity â˜ƒ : â˜ƒ) {
         if (â˜ƒ instanceof LivingEntity) {
            â˜ƒ.hurt(DamageSource.mobAttack(this), 10.0F);
            this.doEnchantDamageEffects(this, â˜ƒ);
         }
      }
   }

   private float rotWrap(double var1) {
      return (float)Mth.wrapDegrees(â˜ƒ);
   }

   private boolean checkWalls(AABB var1) {
      int â˜ƒ = Mth.floor(â˜ƒ.minX);
      int â˜ƒx = Mth.floor(â˜ƒ.minY);
      int â˜ƒxx = Mth.floor(â˜ƒ.minZ);
      int â˜ƒxxx = Mth.floor(â˜ƒ.maxX);
      int â˜ƒxxxx = Mth.floor(â˜ƒ.maxY);
      int â˜ƒxxxxx = Mth.floor(â˜ƒ.maxZ);
      boolean â˜ƒxxxxxx = false;
      boolean â˜ƒxxxxxxx = false;

      for(int â˜ƒxxxxxxxx = â˜ƒ; â˜ƒxxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxxx) {
         for(int â˜ƒxxxxxxxxx = â˜ƒx; â˜ƒxxxxxxxxx <= â˜ƒxxxx; ++â˜ƒxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxx = â˜ƒxx; â˜ƒxxxxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxxxxx) {
               BlockPos â˜ƒxxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
               BlockState â˜ƒxxxxxxxxxxxx = this.level.getBlockState(â˜ƒxxxxxxxxxxx);
               if (!â˜ƒxxxxxxxxxxxx.isAir() && â˜ƒxxxxxxxxxxxx.getMaterial() != Material.FIRE) {
                  if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && !â˜ƒxxxxxxxxxxxx.is(BlockTags.DRAGON_IMMUNE)) {
                     â˜ƒxxxxxxx = this.level.removeBlock(â˜ƒxxxxxxxxxxx, false) || â˜ƒxxxxxxx;
                  } else {
                     â˜ƒxxxxxx = true;
                  }
               }
            }
         }
      }

      if (â˜ƒxxxxxxx) {
         BlockPos â˜ƒxxxxxxxx = new BlockPos(
            â˜ƒ + this.random.nextInt(â˜ƒxxx - â˜ƒ + 1), â˜ƒx + this.random.nextInt(â˜ƒxxxx - â˜ƒx + 1), â˜ƒxx + this.random.nextInt(â˜ƒxxxxx - â˜ƒxx + 1)
         );
         this.level.levelEvent(2008, â˜ƒxxxxxxxx, 0);
      }

      return â˜ƒxxxxxx;
   }

   public boolean hurt(EnderDragonPart var1, DamageSource var2, float var3) {
      if (this.phaseManager.getCurrentPhase().getPhase() == EnderDragonPhase.DYING) {
         return false;
      } else {
         â˜ƒ = this.phaseManager.getCurrentPhase().onHurt(â˜ƒ, â˜ƒ);
         if (â˜ƒ != this.head) {
            â˜ƒ = â˜ƒ / 4.0F + Math.min(â˜ƒ, 1.0F);
         }

         if (â˜ƒ < 0.01F) {
            return false;
         } else {
            if (â˜ƒ.getEntity() instanceof Player || â˜ƒ.isExplosion()) {
               float â˜ƒ = this.getHealth();
               this.reallyHurt(â˜ƒ, â˜ƒ);
               if (this.isDeadOrDying() && !this.phaseManager.getCurrentPhase().isSitting()) {
                  this.setHealth(1.0F);
                  this.phaseManager.setPhase(EnderDragonPhase.DYING);
               }

               if (this.phaseManager.getCurrentPhase().isSitting()) {
                  this.sittingDamageReceived = (int)((float)this.sittingDamageReceived + (â˜ƒ - this.getHealth()));
                  if ((float)this.sittingDamageReceived > 0.25F * this.getMaxHealth()) {
                     this.sittingDamageReceived = 0;
                     this.phaseManager.setPhase(EnderDragonPhase.TAKEOFF);
                  }
               }
            }

            return true;
         }
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (â˜ƒ instanceof EntityDamageSource && ((EntityDamageSource)â˜ƒ).isThorns()) {
         this.hurt(this.body, â˜ƒ, â˜ƒ);
      }

      return false;
   }

   protected boolean reallyHurt(DamageSource var1, float var2) {
      return super.hurt(â˜ƒ, â˜ƒ);
   }

   @Override
   public void kill() {
      this.remove(Entity.RemovalReason.KILLED);
      if (this.dragonFight != null) {
         this.dragonFight.updateDragon(this);
         this.dragonFight.setDragonKilled(this);
      }
   }

   @Override
   protected void tickDeath() {
      if (this.dragonFight != null) {
         this.dragonFight.updateDragon(this);
      }

      ++this.dragonDeathTime;
      if (this.dragonDeathTime >= 180 && this.dragonDeathTime <= 200) {
         float â˜ƒ = (this.random.nextFloat() - 0.5F) * 8.0F;
         float â˜ƒx = (this.random.nextFloat() - 0.5F) * 4.0F;
         float â˜ƒxx = (this.random.nextFloat() - 0.5F) * 8.0F;
         this.level
            .addParticle(
               ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double)â˜ƒ, this.getY() + 2.0 + (double)â˜ƒx, this.getZ() + (double)â˜ƒxx, 0.0, 0.0, 0.0
            );
      }

      boolean â˜ƒ = this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
      int â˜ƒx = 500;
      if (this.dragonFight != null && !this.dragonFight.hasPreviouslyKilledDragon()) {
         â˜ƒx = 12000;
      }

      if (this.level instanceof ServerLevel) {
         if (this.dragonDeathTime > 150 && this.dragonDeathTime % 5 == 0 && â˜ƒ) {
            ExperienceOrb.award((ServerLevel)this.level, this.position(), Mth.floor((float)â˜ƒx * 0.08F));
         }

         if (this.dragonDeathTime == 1 && !this.isSilent()) {
            this.level.globalLevelEvent(1028, this.blockPosition(), 0);
         }
      }

      this.move(MoverType.SELF, new Vec3(0.0, 0.1F, 0.0));
      this.setYRot(this.getYRot() + 20.0F);
      this.yBodyRot = this.getYRot();
      if (this.dragonDeathTime == 200 && this.level instanceof ServerLevel) {
         if (â˜ƒ) {
            ExperienceOrb.award((ServerLevel)this.level, this.position(), Mth.floor((float)â˜ƒx * 0.2F));
         }

         if (this.dragonFight != null) {
            this.dragonFight.setDragonKilled(this);
         }

         this.remove(Entity.RemovalReason.KILLED);
      }
   }

   public int findClosestNode() {
      if (this.nodes[0] == null) {
         for(int â˜ƒ = 0; â˜ƒ < 24; ++â˜ƒ) {
            int â˜ƒxxx = 5;
            int â˜ƒx;
            int â˜ƒxx;
            if (â˜ƒ < 12) {
               â˜ƒx = Mth.floor(60.0F * Mth.cos(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)â˜ƒ)));
               â˜ƒxx = Mth.floor(60.0F * Mth.sin(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)â˜ƒ)));
            } else if (â˜ƒ < 20) {
               int var3 = â˜ƒ - 12;
               â˜ƒx = Mth.floor(40.0F * Mth.cos(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               â˜ƒxx = Mth.floor(40.0F * Mth.sin(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               â˜ƒxxx += 10;
            } else {
               int var7 = â˜ƒ - 20;
               â˜ƒx = Mth.floor(20.0F * Mth.cos(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
               â˜ƒxx = Mth.floor(20.0F * Mth.sin(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
            }

            int â˜ƒx = Math.max(
               this.level.getSeaLevel() + 10,
               this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(â˜ƒx, 0, â˜ƒxx)).getY() + â˜ƒxxx
            );
            this.nodes[â˜ƒ] = new Node(â˜ƒx, â˜ƒx, â˜ƒxx);
         }

         this.nodeAdjacency[0] = 6146;
         this.nodeAdjacency[1] = 8197;
         this.nodeAdjacency[2] = 8202;
         this.nodeAdjacency[3] = 16404;
         this.nodeAdjacency[4] = 32808;
         this.nodeAdjacency[5] = 32848;
         this.nodeAdjacency[6] = 65696;
         this.nodeAdjacency[7] = 131392;
         this.nodeAdjacency[8] = 131712;
         this.nodeAdjacency[9] = 263424;
         this.nodeAdjacency[10] = 526848;
         this.nodeAdjacency[11] = 525313;
         this.nodeAdjacency[12] = 1581057;
         this.nodeAdjacency[13] = 3166214;
         this.nodeAdjacency[14] = 2138120;
         this.nodeAdjacency[15] = 6373424;
         this.nodeAdjacency[16] = 4358208;
         this.nodeAdjacency[17] = 12910976;
         this.nodeAdjacency[18] = 9044480;
         this.nodeAdjacency[19] = 9706496;
         this.nodeAdjacency[20] = 15216640;
         this.nodeAdjacency[21] = 13688832;
         this.nodeAdjacency[22] = 11763712;
         this.nodeAdjacency[23] = 8257536;
      }

      return this.findClosestNode(this.getX(), this.getY(), this.getZ());
   }

   public int findClosestNode(double var1, double var3, double var5) {
      float â˜ƒ = 10000.0F;
      int â˜ƒx = 0;
      Node â˜ƒxx = new Node(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ), Mth.floor(â˜ƒ));
      int â˜ƒxxx = 0;
      if (this.dragonFight == null || this.dragonFight.getCrystalsAlive() == 0) {
         â˜ƒxxx = 12;
      }

      for(int â˜ƒ = â˜ƒxxx; â˜ƒ < 24; ++â˜ƒ) {
         if (this.nodes[â˜ƒ] != null) {
            float â˜ƒx = this.nodes[â˜ƒ].distanceToSqr(â˜ƒxx);
            if (â˜ƒx < â˜ƒ) {
               â˜ƒ = â˜ƒx;
               â˜ƒx = â˜ƒ;
            }
         }
      }

      return â˜ƒx;
   }

   @Nullable
   public Path findPath(int var1, int var2, @Nullable Node var3) {
      for(int â˜ƒ = 0; â˜ƒ < 24; ++â˜ƒ) {
         Node â˜ƒx = this.nodes[â˜ƒ];
         â˜ƒx.closed = false;
         â˜ƒx.f = 0.0F;
         â˜ƒx.g = 0.0F;
         â˜ƒx.h = 0.0F;
         â˜ƒx.cameFrom = null;
         â˜ƒx.heapIdx = -1;
      }

      Node â˜ƒ = this.nodes[â˜ƒ];
      Node â˜ƒx = this.nodes[â˜ƒ];
      â˜ƒ.g = 0.0F;
      â˜ƒ.h = â˜ƒ.distanceTo(â˜ƒx);
      â˜ƒ.f = â˜ƒ.h;
      this.openSet.clear();
      this.openSet.insert(â˜ƒ);
      Node â˜ƒxx = â˜ƒ;
      int â˜ƒxxx = 0;
      if (this.dragonFight == null || this.dragonFight.getCrystalsAlive() == 0) {
         â˜ƒxxx = 12;
      }

      while(!this.openSet.isEmpty()) {
         Node â˜ƒ = this.openSet.pop();
         if (â˜ƒ.equals(â˜ƒx)) {
            if (â˜ƒ != null) {
               â˜ƒ.cameFrom = â˜ƒx;
               â˜ƒx = â˜ƒ;
            }

            return this.reconstructPath(â˜ƒ, â˜ƒx);
         }

         if (â˜ƒ.distanceTo(â˜ƒx) < â˜ƒxx.distanceTo(â˜ƒx)) {
            â˜ƒxx = â˜ƒ;
         }

         â˜ƒ.closed = true;
         int â˜ƒ = 0;

         for(int â˜ƒx = 0; â˜ƒx < 24; ++â˜ƒx) {
            if (this.nodes[â˜ƒx] == â˜ƒ) {
               â˜ƒ = â˜ƒx;
               break;
            }
         }

         for(int â˜ƒx = â˜ƒxxx; â˜ƒx < 24; ++â˜ƒx) {
            if ((this.nodeAdjacency[â˜ƒ] & 1 << â˜ƒx) > 0) {
               Node â˜ƒxx = this.nodes[â˜ƒx];
               if (!â˜ƒxx.closed) {
                  float â˜ƒxxx = â˜ƒ.g + â˜ƒ.distanceTo(â˜ƒxx);
                  if (!â˜ƒxx.inOpenSet() || â˜ƒxxx < â˜ƒxx.g) {
                     â˜ƒxx.cameFrom = â˜ƒ;
                     â˜ƒxx.g = â˜ƒxxx;
                     â˜ƒxx.h = â˜ƒxx.distanceTo(â˜ƒx);
                     if (â˜ƒxx.inOpenSet()) {
                        this.openSet.changeCost(â˜ƒxx, â˜ƒxx.g + â˜ƒxx.h);
                     } else {
                        â˜ƒxx.f = â˜ƒxx.g + â˜ƒxx.h;
                        this.openSet.insert(â˜ƒxx);
                     }
                  }
               }
            }
         }
      }

      if (â˜ƒxx == â˜ƒ) {
         return null;
      } else {
         LOGGER.debug("Failed to find path from {} to {}", â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            â˜ƒ.cameFrom = â˜ƒxx;
            â˜ƒxx = â˜ƒ;
         }

         return this.reconstructPath(â˜ƒ, â˜ƒxx);
      }
   }

   private Path reconstructPath(Node var1, Node var2) {
      List<Node> â˜ƒ = Lists.<Node>newArrayList();
      Node â˜ƒx = â˜ƒ;
      â˜ƒ.add(0, â˜ƒ);

      while(â˜ƒx.cameFrom != null) {
         â˜ƒx = â˜ƒx.cameFrom;
         â˜ƒ.add(0, â˜ƒx);
      }

      return new Path(â˜ƒ, new BlockPos(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z), true);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("DragonPhase", this.phaseManager.getCurrentPhase().getPhase().getId());
      â˜ƒ.putInt("DragonDeathTime", this.dragonDeathTime);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("DragonPhase")) {
         this.phaseManager.setPhase(EnderDragonPhase.getById(â˜ƒ.getInt("DragonPhase")));
      }

      if (â˜ƒ.contains("DragonDeathTime")) {
         this.dragonDeathTime = â˜ƒ.getInt("DragonDeathTime");
      }
   }

   @Override
   public void checkDespawn() {
   }

   public EnderDragonPart[] getSubEntities() {
      return this.subEntities;
   }

   @Override
   public boolean isPickable() {
      return false;
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENDER_DRAGON_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.ENDER_DRAGON_HURT;
   }

   @Override
   protected float getSoundVolume() {
      return 5.0F;
   }

   public float getHeadPartYOffset(int var1, double[] var2, double[] var3) {
      DragonPhaseInstance â˜ƒx = this.phaseManager.getCurrentPhase();
      EnderDragonPhase<? extends DragonPhaseInstance> â˜ƒxx = â˜ƒx.getPhase();
      double â˜ƒ;
      if (â˜ƒxx == EnderDragonPhase.LANDING || â˜ƒxx == EnderDragonPhase.TAKEOFF) {
         BlockPos â˜ƒxxx = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
         double â˜ƒxxxx = Math.max(Math.sqrt(â˜ƒxxx.distSqr(this.position(), true)) / 4.0, 1.0);
         â˜ƒ = (double)â˜ƒ / â˜ƒxxxx;
      } else if (â˜ƒx.isSitting()) {
         â˜ƒ = (double)â˜ƒ;
      } else if (â˜ƒ == 6) {
         â˜ƒ = 0.0;
      } else {
         â˜ƒ = â˜ƒ[1] - â˜ƒ[1];
      }

      return (float)â˜ƒ;
   }

   public Vec3 getHeadLookVector(float var1) {
      DragonPhaseInstance â˜ƒx = this.phaseManager.getCurrentPhase();
      EnderDragonPhase<? extends DragonPhaseInstance> â˜ƒxx = â˜ƒx.getPhase();
      Vec3 â˜ƒ;
      if (â˜ƒxx == EnderDragonPhase.LANDING || â˜ƒxx == EnderDragonPhase.TAKEOFF) {
         BlockPos â˜ƒxxx = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION);
         float â˜ƒxxxx = Math.max((float)Math.sqrt(â˜ƒxxx.distSqr(this.position(), true)) / 4.0F, 1.0F);
         float â˜ƒxxxxx = 6.0F / â˜ƒxxxx;
         float â˜ƒxxxxxx = this.getXRot();
         float â˜ƒxxxxxxx = 1.5F;
         this.setXRot(-â˜ƒxxxxx * 1.5F * 5.0F);
         â˜ƒ = this.getViewVector(â˜ƒ);
         this.setXRot(â˜ƒxxxxxx);
      } else if (â˜ƒx.isSitting()) {
         float â˜ƒ = this.getXRot();
         float â˜ƒx = 1.5F;
         this.setXRot(-45.0F);
         â˜ƒ = this.getViewVector(â˜ƒ);
         this.setXRot(â˜ƒ);
      } else {
         â˜ƒ = this.getViewVector(â˜ƒ);
      }

      return â˜ƒ;
   }

   public void onCrystalDestroyed(EndCrystal var1, BlockPos var2, DamageSource var3) {
      Player â˜ƒ;
      if (â˜ƒ.getEntity() instanceof Player) {
         â˜ƒ = (Player)â˜ƒ.getEntity();
      } else {
         â˜ƒ = this.level.getNearestPlayer(CRYSTAL_DESTROY_TARGETING, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
      }

      if (â˜ƒ == this.nearestCrystal) {
         this.hurt(this.head, DamageSource.explosion(â˜ƒ), 10.0F);
      }

      this.phaseManager.getCurrentPhase().onCrystalDestroyed(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_PHASE.equals(â˜ƒ) && this.level.isClientSide) {
         this.phaseManager.setPhase(EnderDragonPhase.getById(this.getEntityData().get(DATA_PHASE)));
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   public EnderDragonPhaseManager getPhaseManager() {
      return this.phaseManager;
   }

   @Nullable
   public EndDragonFight getDragonFight() {
      return this.dragonFight;
   }

   @Override
   public boolean addEffect(MobEffectInstance var1, @Nullable Entity var2) {
      return false;
   }

   @Override
   protected boolean canRide(Entity var1) {
      return false;
   }

   @Override
   public boolean canChangeDimensions() {
      return false;
   }

   @Override
   public void recreateFromPacket(ClientboundAddMobPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      EnderDragonPart[] â˜ƒ = this.getSubEntities();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx].setId(â˜ƒx + â˜ƒ.getId());
      }
   }

   @Override
   public boolean canAttack(LivingEntity var1) {
      return â˜ƒ.canBeSeenAsEnemy();
   }
}
