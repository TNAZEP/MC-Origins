package net.minecraft.client.player;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.MinecartCommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler;
import net.minecraft.client.resources.sounds.BubbleColumnAmbientSoundHandler;
import net.minecraft.client.resources.sounds.ElytraOnPlayerSoundInstance;
import net.minecraft.client.resources.sounds.RidingMinecartSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundHandler;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LocalPlayer extends AbstractClientPlayer {
   private static final int POSITION_REMINDER_INTERVAL = 20;
   private static final int WATER_VISION_MAX_TIME = 600;
   private static final int WATER_VISION_QUICK_TIME = 100;
   private static final float WATER_VISION_QUICK_PERCENT = 0.6F;
   private static final double SUFFOCATING_COLLISION_CHECK_SCALE = 0.35;
   public final ClientPacketListener connection;
   private final StatsCounter stats;
   private final ClientRecipeBook recipeBook;
   private final List<AmbientSoundHandler> ambientSoundHandlers = Lists.<AmbientSoundHandler>newArrayList();
   private int permissionLevel = 0;
   private double xLast;
   private double yLast1;
   private double zLast;
   private float yRotLast;
   private float xRotLast;
   private boolean lastOnGround;
   private boolean crouching;
   private boolean wasShiftKeyDown;
   private boolean wasSprinting;
   private int positionReminder;
   private boolean flashOnSetHealth;
   private String serverBrand;
   public Input input;
   protected final Minecraft minecraft;
   protected int sprintTriggerTime;
   public int sprintTime;
   public float yBob;
   public float xBob;
   public float yBobO;
   public float xBobO;
   private int jumpRidingTicks;
   private float jumpRidingScale;
   public float portalTime;
   public float oPortalTime;
   private boolean startedUsingItem;
   private InteractionHand usingItemHand;
   private boolean handsBusy;
   private boolean autoJumpEnabled = true;
   private int autoJumpTime;
   private boolean wasFallFlying;
   private int waterVisionTime;
   private boolean showDeathScreen = true;

   public LocalPlayer(Minecraft var1, ClientLevel var2, ClientPacketListener var3, StatsCounter var4, ClientRecipeBook var5, boolean var6, boolean var7) {
      super(â˜ƒ, â˜ƒ.getLocalGameProfile());
      this.minecraft = â˜ƒ;
      this.connection = â˜ƒ;
      this.stats = â˜ƒ;
      this.recipeBook = â˜ƒ;
      this.wasShiftKeyDown = â˜ƒ;
      this.wasSprinting = â˜ƒ;
      this.ambientSoundHandlers.add(new UnderwaterAmbientSoundHandler(this, â˜ƒ.getSoundManager()));
      this.ambientSoundHandlers.add(new BubbleColumnAmbientSoundHandler(this));
      this.ambientSoundHandlers.add(new BiomeAmbientSoundsHandler(this, â˜ƒ.getSoundManager(), â˜ƒ.getBiomeManager()));
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      return false;
   }

   @Override
   public void heal(float var1) {
   }

   @Override
   public boolean startRiding(Entity var1, boolean var2) {
      if (!super.startRiding(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         if (â˜ƒ instanceof AbstractMinecart) {
            this.minecraft.getSoundManager().play(new RidingMinecartSoundInstance(this, (AbstractMinecart)â˜ƒ, true));
            this.minecraft.getSoundManager().play(new RidingMinecartSoundInstance(this, (AbstractMinecart)â˜ƒ, false));
         }

         if (â˜ƒ instanceof Boat) {
            this.yRotO = â˜ƒ.getYRot();
            this.setYRot(â˜ƒ.getYRot());
            this.setYHeadRot(â˜ƒ.getYRot());
         }

         return true;
      }
   }

   @Override
   public void removeVehicle() {
      super.removeVehicle();
      this.handsBusy = false;
   }

   @Override
   public float getViewXRot(float var1) {
      return this.getXRot();
   }

   @Override
   public float getViewYRot(float var1) {
      return this.isPassenger() ? super.getViewYRot(â˜ƒ) : this.getYRot();
   }

   @Override
   public void tick() {
      if (this.level.hasChunkAt(this.getBlockX(), this.getBlockZ())) {
         super.tick();
         if (this.isPassenger()) {
            this.connection.send(new ServerboundMovePlayerPacket.Rot(this.getYRot(), this.getXRot(), this.onGround));
            this.connection.send(new ServerboundPlayerInputPacket(this.xxa, this.zza, this.input.jumping, this.input.shiftKeyDown));
            Entity â˜ƒ = this.getRootVehicle();
            if (â˜ƒ != this && â˜ƒ.isControlledByLocalInstance()) {
               this.connection.send(new ServerboundMoveVehiclePacket(â˜ƒ));
            }
         } else {
            this.sendPosition();
         }

         for(AmbientSoundHandler â˜ƒ : this.ambientSoundHandlers) {
            â˜ƒ.tick();
         }
      }
   }

   public float getCurrentMood() {
      for(AmbientSoundHandler â˜ƒ : this.ambientSoundHandlers) {
         if (â˜ƒ instanceof BiomeAmbientSoundsHandler) {
            return ((BiomeAmbientSoundsHandler)â˜ƒ).getMoodiness();
         }
      }

      return 0.0F;
   }

   private void sendPosition() {
      boolean â˜ƒ = this.isSprinting();
      if (â˜ƒ != this.wasSprinting) {
         ServerboundPlayerCommandPacket.Action â˜ƒx = â˜ƒ
            ? ServerboundPlayerCommandPacket.Action.START_SPRINTING
            : ServerboundPlayerCommandPacket.Action.STOP_SPRINTING;
         this.connection.send(new ServerboundPlayerCommandPacket(this, â˜ƒx));
         this.wasSprinting = â˜ƒ;
      }

      boolean â˜ƒ = this.isShiftKeyDown();
      if (â˜ƒ != this.wasShiftKeyDown) {
         ServerboundPlayerCommandPacket.Action â˜ƒx = â˜ƒ
            ? ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY
            : ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY;
         this.connection.send(new ServerboundPlayerCommandPacket(this, â˜ƒx));
         this.wasShiftKeyDown = â˜ƒ;
      }

      if (this.isControlledCamera()) {
         double â˜ƒ = this.getX() - this.xLast;
         double â˜ƒx = this.getY() - this.yLast1;
         double â˜ƒxx = this.getZ() - this.zLast;
         double â˜ƒxxx = (double)(this.getYRot() - this.yRotLast);
         double â˜ƒxxxx = (double)(this.getXRot() - this.xRotLast);
         ++this.positionReminder;
         boolean â˜ƒxxxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx > 9.0E-4 || this.positionReminder >= 20;
         boolean â˜ƒxxxxxx = â˜ƒxxx != 0.0 || â˜ƒxxxx != 0.0;
         if (this.isPassenger()) {
            Vec3 â˜ƒxxxxxxx = this.getDeltaMovement();
            this.connection.send(new ServerboundMovePlayerPacket.PosRot(â˜ƒxxxxxxx.x, -999.0, â˜ƒxxxxxxx.z, this.getYRot(), this.getXRot(), this.onGround));
            â˜ƒxxxxx = false;
         } else if (â˜ƒxxxxx && â˜ƒxxxxxx) {
            this.connection.send(new ServerboundMovePlayerPacket.PosRot(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot(), this.onGround));
         } else if (â˜ƒxxxxx) {
            this.connection.send(new ServerboundMovePlayerPacket.Pos(this.getX(), this.getY(), this.getZ(), this.onGround));
         } else if (â˜ƒxxxxxx) {
            this.connection.send(new ServerboundMovePlayerPacket.Rot(this.getYRot(), this.getXRot(), this.onGround));
         } else if (this.lastOnGround != this.onGround) {
            this.connection.send(new ServerboundMovePlayerPacket.StatusOnly(this.onGround));
         }

         if (â˜ƒxxxxx) {
            this.xLast = this.getX();
            this.yLast1 = this.getY();
            this.zLast = this.getZ();
            this.positionReminder = 0;
         }

         if (â˜ƒxxxxxx) {
            this.yRotLast = this.getYRot();
            this.xRotLast = this.getXRot();
         }

         this.lastOnGround = this.onGround;
         this.autoJumpEnabled = this.minecraft.options.autoJump;
      }
   }

   public boolean drop(boolean var1) {
      ServerboundPlayerActionPacket.Action â˜ƒ = â˜ƒ ? ServerboundPlayerActionPacket.Action.DROP_ALL_ITEMS : ServerboundPlayerActionPacket.Action.DROP_ITEM;
      ItemStack â˜ƒx = this.getInventory().removeFromSelected(â˜ƒ);
      this.connection.send(new ServerboundPlayerActionPacket(â˜ƒ, BlockPos.ZERO, Direction.DOWN));
      return !â˜ƒx.isEmpty();
   }

   public void chat(String var1) {
      this.connection.send(new ServerboundChatPacket(â˜ƒ));
   }

   @Override
   public void swing(InteractionHand var1) {
      super.swing(â˜ƒ);
      this.connection.send(new ServerboundSwingPacket(â˜ƒ));
   }

   @Override
   public void respawn() {
      this.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
   }

   @Override
   protected void actuallyHurt(DamageSource var1, float var2) {
      if (!this.isInvulnerableTo(â˜ƒ)) {
         this.setHealth(this.getHealth() - â˜ƒ);
      }
   }

   @Override
   public void closeContainer() {
      this.connection.send(new ServerboundContainerClosePacket(this.containerMenu.containerId));
      this.clientSideCloseContainer();
   }

   public void clientSideCloseContainer() {
      super.closeContainer();
      this.minecraft.setScreen(null);
   }

   public void hurtTo(float var1) {
      if (this.flashOnSetHealth) {
         float â˜ƒ = this.getHealth() - â˜ƒ;
         if (â˜ƒ <= 0.0F) {
            this.setHealth(â˜ƒ);
            if (â˜ƒ < 0.0F) {
               this.invulnerableTime = 10;
            }
         } else {
            this.lastHurt = â˜ƒ;
            this.invulnerableTime = 20;
            this.setHealth(â˜ƒ);
            this.hurtDuration = 10;
            this.hurtTime = this.hurtDuration;
         }
      } else {
         this.setHealth(â˜ƒ);
         this.flashOnSetHealth = true;
      }
   }

   @Override
   public void onUpdateAbilities() {
      this.connection.send(new ServerboundPlayerAbilitiesPacket(this.getAbilities()));
   }

   @Override
   public boolean isLocalPlayer() {
      return true;
   }

   @Override
   public boolean isSuppressingSlidingDownLadder() {
      return !this.getAbilities().flying && super.isSuppressingSlidingDownLadder();
   }

   @Override
   public boolean canSpawnSprintParticle() {
      return !this.getAbilities().flying && super.canSpawnSprintParticle();
   }

   @Override
   public boolean canSpawnSoulSpeedParticle() {
      return !this.getAbilities().flying && super.canSpawnSoulSpeedParticle();
   }

   protected void sendRidingJump() {
      this.connection
         .send(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.START_RIDING_JUMP, Mth.floor(this.getJumpRidingScale() * 100.0F)));
   }

   public void sendOpenInventory() {
      this.connection.send(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.OPEN_INVENTORY));
   }

   public void setServerBrand(String var1) {
      this.serverBrand = â˜ƒ;
   }

   public String getServerBrand() {
      return this.serverBrand;
   }

   public StatsCounter getStats() {
      return this.stats;
   }

   public ClientRecipeBook getRecipeBook() {
      return this.recipeBook;
   }

   public void removeRecipeHighlight(Recipe<?> var1) {
      if (this.recipeBook.willHighlight(â˜ƒ)) {
         this.recipeBook.removeHighlight(â˜ƒ);
         this.connection.send(new ServerboundRecipeBookSeenRecipePacket(â˜ƒ));
      }
   }

   @Override
   protected int getPermissionLevel() {
      return this.permissionLevel;
   }

   public void setPermissionLevel(int var1) {
      this.permissionLevel = â˜ƒ;
   }

   @Override
   public void displayClientMessage(Component var1, boolean var2) {
      if (â˜ƒ) {
         this.minecraft.gui.setOverlayMessage(â˜ƒ, false);
      } else {
         this.minecraft.gui.getChat().addMessage(â˜ƒ);
      }
   }

   private void moveTowardsClosestSpace(double var1, double var3) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ, this.getY(), â˜ƒ);
      if (this.suffocatesAt(â˜ƒ)) {
         double â˜ƒx = â˜ƒ - (double)â˜ƒ.getX();
         double â˜ƒxx = â˜ƒ - (double)â˜ƒ.getZ();
         Direction â˜ƒxxx = null;
         double â˜ƒxxxx = Double.MAX_VALUE;
         Direction[] â˜ƒxxxxx = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};

         for(Direction â˜ƒxxxxxx : â˜ƒxxxxx) {
            double â˜ƒxxxxxxx = â˜ƒxxxxxx.getAxis().choose(â˜ƒx, 0.0, â˜ƒxx);
            double â˜ƒxxxxxxxx = â˜ƒxxxxxx.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - â˜ƒxxxxxxx : â˜ƒxxxxxxx;
            if (â˜ƒxxxxxxxx < â˜ƒxxxx && !this.suffocatesAt(â˜ƒ.relative(â˜ƒxxxxxx))) {
               â˜ƒxxxx = â˜ƒxxxxxxxx;
               â˜ƒxxx = â˜ƒxxxxxx;
            }
         }

         if (â˜ƒxxx != null) {
            Vec3 â˜ƒxxxxxx = this.getDeltaMovement();
            if (â˜ƒxxx.getAxis() == Direction.Axis.X) {
               this.setDeltaMovement(0.1 * (double)â˜ƒxxx.getStepX(), â˜ƒxxxxxx.y, â˜ƒxxxxxx.z);
            } else {
               this.setDeltaMovement(â˜ƒxxxxxx.x, â˜ƒxxxxxx.y, 0.1 * (double)â˜ƒxxx.getStepZ());
            }
         }
      }
   }

   private boolean suffocatesAt(BlockPos var1) {
      AABB â˜ƒ = this.getBoundingBox();
      AABB â˜ƒx = new AABB((double)â˜ƒ.getX(), â˜ƒ.minY, (double)â˜ƒ.getZ(), (double)â˜ƒ.getX() + 1.0, â˜ƒ.maxY, (double)â˜ƒ.getZ() + 1.0).deflate(1.0E-7);
      return this.level.hasBlockCollision(this, â˜ƒx, (var1x, var2x) -> var1x.isSuffocating(this.level, var2x));
   }

   @Override
   public void setSprinting(boolean var1) {
      super.setSprinting(â˜ƒ);
      this.sprintTime = 0;
   }

   public void setExperienceValues(float var1, int var2, int var3) {
      this.experienceProgress = â˜ƒ;
      this.totalExperience = â˜ƒ;
      this.experienceLevel = â˜ƒ;
   }

   @Override
   public void sendMessage(Component var1, UUID var2) {
      this.minecraft.gui.getChat().addMessage(â˜ƒ);
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ >= 24 && â˜ƒ <= 28) {
         this.setPermissionLevel(â˜ƒ - 24);
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   public void setShowDeathScreen(boolean var1) {
      this.showDeathScreen = â˜ƒ;
   }

   public boolean shouldShowDeathScreen() {
      return this.showDeathScreen;
   }

   @Override
   public void playSound(SoundEvent var1, float var2, float var3) {
      this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), â˜ƒ, this.getSoundSource(), â˜ƒ, â˜ƒ, false);
   }

   @Override
   public void playNotifySound(SoundEvent var1, SoundSource var2, float var3, float var4) {
      this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   @Override
   public boolean isEffectiveAi() {
      return true;
   }

   @Override
   public void startUsingItem(InteractionHand var1) {
      ItemStack â˜ƒ = this.getItemInHand(â˜ƒ);
      if (!â˜ƒ.isEmpty() && !this.isUsingItem()) {
         super.startUsingItem(â˜ƒ);
         this.startedUsingItem = true;
         this.usingItemHand = â˜ƒ;
      }
   }

   @Override
   public boolean isUsingItem() {
      return this.startedUsingItem;
   }

   @Override
   public void stopUsingItem() {
      super.stopUsingItem();
      this.startedUsingItem = false;
   }

   @Override
   public InteractionHand getUsedItemHand() {
      return this.usingItemHand;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      super.onSyncedDataUpdated(â˜ƒ);
      if (DATA_LIVING_ENTITY_FLAGS.equals(â˜ƒ)) {
         boolean â˜ƒ = (this.entityData.get(DATA_LIVING_ENTITY_FLAGS) & 1) > 0;
         InteractionHand â˜ƒx = (this.entityData.get(DATA_LIVING_ENTITY_FLAGS) & 2) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
         if (â˜ƒ && !this.startedUsingItem) {
            this.startUsingItem(â˜ƒx);
         } else if (!â˜ƒ && this.startedUsingItem) {
            this.stopUsingItem();
         }
      }

      if (DATA_SHARED_FLAGS_ID.equals(â˜ƒ) && this.isFallFlying() && !this.wasFallFlying) {
         this.minecraft.getSoundManager().play(new ElytraOnPlayerSoundInstance(this));
      }
   }

   public boolean isRidingJumpable() {
      Entity â˜ƒ = this.getVehicle();
      return this.isPassenger() && â˜ƒ instanceof PlayerRideableJumping && ((PlayerRideableJumping)â˜ƒ).canJump();
   }

   public float getJumpRidingScale() {
      return this.jumpRidingScale;
   }

   @Override
   public void openTextEdit(SignBlockEntity var1) {
      this.minecraft.setScreen(new SignEditScreen(â˜ƒ, this.minecraft.isTextFilteringEnabled()));
   }

   @Override
   public void openMinecartCommandBlock(BaseCommandBlock var1) {
      this.minecraft.setScreen(new MinecartCommandBlockEditScreen(â˜ƒ));
   }

   @Override
   public void openCommandBlock(CommandBlockEntity var1) {
      this.minecraft.setScreen(new CommandBlockEditScreen(â˜ƒ));
   }

   @Override
   public void openStructureBlock(StructureBlockEntity var1) {
      this.minecraft.setScreen(new StructureBlockEditScreen(â˜ƒ));
   }

   @Override
   public void openJigsawBlock(JigsawBlockEntity var1) {
      this.minecraft.setScreen(new JigsawBlockEditScreen(â˜ƒ));
   }

   @Override
   public void openItemGui(ItemStack var1, InteractionHand var2) {
      if (â˜ƒ.is(Items.WRITABLE_BOOK)) {
         this.minecraft.setScreen(new BookEditScreen(this, â˜ƒ, â˜ƒ));
      }
   }

   @Override
   public void crit(Entity var1) {
      this.minecraft.particleEngine.createTrackingEmitter(â˜ƒ, ParticleTypes.CRIT);
   }

   @Override
   public void magicCrit(Entity var1) {
      this.minecraft.particleEngine.createTrackingEmitter(â˜ƒ, ParticleTypes.ENCHANTED_HIT);
   }

   @Override
   public boolean isShiftKeyDown() {
      return this.input != null && this.input.shiftKeyDown;
   }

   @Override
   public boolean isCrouching() {
      return this.crouching;
   }

   public boolean isMovingSlowly() {
      return this.isCrouching() || this.isVisuallyCrawling();
   }

   @Override
   public void serverAiStep() {
      super.serverAiStep();
      if (this.isControlledCamera()) {
         this.xxa = this.input.leftImpulse;
         this.zza = this.input.forwardImpulse;
         this.jumping = this.input.jumping;
         this.yBobO = this.yBob;
         this.xBobO = this.xBob;
         this.xBob = (float)((double)this.xBob + (double)(this.getXRot() - this.xBob) * 0.5);
         this.yBob = (float)((double)this.yBob + (double)(this.getYRot() - this.yBob) * 0.5);
      }
   }

   protected boolean isControlledCamera() {
      return this.minecraft.getCameraEntity() == this;
   }

   public void resetPos() {
      this.setPose(Pose.STANDING);
      if (this.level != null) {
         for(double â˜ƒ = this.getY(); â˜ƒ > (double)this.level.getMinBuildHeight() && â˜ƒ < (double)this.level.getMaxBuildHeight(); ++â˜ƒ) {
            this.setPos(this.getX(), â˜ƒ, this.getZ());
            if (this.level.noCollision(this)) {
               break;
            }
         }

         this.setDeltaMovement(Vec3.ZERO);
         this.setXRot(0.0F);
      }

      this.setHealth(this.getMaxHealth());
      this.deathTime = 0;
   }

   @Override
   public void aiStep() {
      ++this.sprintTime;
      if (this.sprintTriggerTime > 0) {
         --this.sprintTriggerTime;
      }

      this.handleNetherPortalClient();
      boolean â˜ƒ = this.input.jumping;
      boolean â˜ƒx = this.input.shiftKeyDown;
      boolean â˜ƒxx = this.hasEnoughImpulseToStartSprinting();
      this.crouching = !this.getAbilities().flying
         && !this.isSwimming()
         && this.canEnterPose(Pose.CROUCHING)
         && (this.isShiftKeyDown() || !this.isSleeping() && !this.canEnterPose(Pose.STANDING));
      this.input.tick(this.isMovingSlowly());
      this.minecraft.getTutorial().onInput(this.input);
      if (this.isUsingItem() && !this.isPassenger()) {
         this.input.leftImpulse *= 0.2F;
         this.input.forwardImpulse *= 0.2F;
         this.sprintTriggerTime = 0;
      }

      boolean â˜ƒ = false;
      if (this.autoJumpTime > 0) {
         --this.autoJumpTime;
         â˜ƒ = true;
         this.input.jumping = true;
      }

      if (!this.noPhysics) {
         this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35, this.getZ() + (double)this.getBbWidth() * 0.35);
         this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35, this.getZ() - (double)this.getBbWidth() * 0.35);
         this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35, this.getZ() - (double)this.getBbWidth() * 0.35);
         this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35, this.getZ() + (double)this.getBbWidth() * 0.35);
      }

      if (â˜ƒx) {
         this.sprintTriggerTime = 0;
      }

      boolean â˜ƒ = (float)this.getFoodData().getFoodLevel() > 6.0F || this.getAbilities().mayfly;
      if ((this.onGround || this.isUnderWater())
         && !â˜ƒx
         && !â˜ƒxx
         && this.hasEnoughImpulseToStartSprinting()
         && !this.isSprinting()
         && â˜ƒ
         && !this.isUsingItem()
         && !this.hasEffect(MobEffects.BLINDNESS)) {
         if (this.sprintTriggerTime <= 0 && !this.minecraft.options.keySprint.isDown()) {
            this.sprintTriggerTime = 7;
         } else {
            this.setSprinting(true);
         }
      }

      if (!this.isSprinting()
         && (!this.isInWater() || this.isUnderWater())
         && this.hasEnoughImpulseToStartSprinting()
         && â˜ƒ
         && !this.isUsingItem()
         && !this.hasEffect(MobEffects.BLINDNESS)
         && this.minecraft.options.keySprint.isDown()) {
         this.setSprinting(true);
      }

      if (this.isSprinting()) {
         boolean â˜ƒ = !this.input.hasForwardImpulse() || !â˜ƒ;
         boolean â˜ƒx = â˜ƒ || this.horizontalCollision || this.isInWater() && !this.isUnderWater();
         if (this.isSwimming()) {
            if (!this.onGround && !this.input.shiftKeyDown && â˜ƒ || !this.isInWater()) {
               this.setSprinting(false);
            }
         } else if (â˜ƒx) {
            this.setSprinting(false);
         }
      }

      boolean â˜ƒ = false;
      if (this.getAbilities().mayfly) {
         if (this.minecraft.gameMode.isAlwaysFlying()) {
            if (!this.getAbilities().flying) {
               this.getAbilities().flying = true;
               â˜ƒ = true;
               this.onUpdateAbilities();
            }
         } else if (!â˜ƒ && this.input.jumping && !â˜ƒ) {
            if (this.jumpTriggerTime == 0) {
               this.jumpTriggerTime = 7;
            } else if (!this.isSwimming()) {
               this.getAbilities().flying = !this.getAbilities().flying;
               â˜ƒ = true;
               this.onUpdateAbilities();
               this.jumpTriggerTime = 0;
            }
         }
      }

      if (this.input.jumping && !â˜ƒ && !â˜ƒ && !this.getAbilities().flying && !this.isPassenger() && !this.onClimbable()) {
         ItemStack â˜ƒ = this.getItemBySlot(EquipmentSlot.CHEST);
         if (â˜ƒ.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(â˜ƒ) && this.tryToStartFallFlying()) {
            this.connection.send(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
         }
      }

      this.wasFallFlying = this.isFallFlying();
      if (this.isInWater() && this.input.shiftKeyDown && this.isAffectedByFluids()) {
         this.goDownInWater();
      }

      if (this.isEyeInFluid(FluidTags.WATER)) {
         int â˜ƒ = this.isSpectator() ? 10 : 1;
         this.waterVisionTime = Mth.clamp(this.waterVisionTime + â˜ƒ, 0, 600);
      } else if (this.waterVisionTime > 0) {
         this.isEyeInFluid(FluidTags.WATER);
         this.waterVisionTime = Mth.clamp(this.waterVisionTime - 10, 0, 600);
      }

      if (this.getAbilities().flying && this.isControlledCamera()) {
         int â˜ƒ = 0;
         if (this.input.shiftKeyDown) {
            --â˜ƒ;
         }

         if (this.input.jumping) {
            ++â˜ƒ;
         }

         if (â˜ƒ != 0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, (double)((float)â˜ƒ * this.getAbilities().getFlyingSpeed() * 3.0F), 0.0));
         }
      }

      if (this.isRidingJumpable()) {
         PlayerRideableJumping â˜ƒ = (PlayerRideableJumping)this.getVehicle();
         if (this.jumpRidingTicks < 0) {
            ++this.jumpRidingTicks;
            if (this.jumpRidingTicks == 0) {
               this.jumpRidingScale = 0.0F;
            }
         }

         if (â˜ƒ && !this.input.jumping) {
            this.jumpRidingTicks = -10;
            â˜ƒ.onPlayerJump(Mth.floor(this.getJumpRidingScale() * 100.0F));
            this.sendRidingJump();
         } else if (!â˜ƒ && this.input.jumping) {
            this.jumpRidingTicks = 0;
            this.jumpRidingScale = 0.0F;
         } else if (â˜ƒ) {
            ++this.jumpRidingTicks;
            if (this.jumpRidingTicks < 10) {
               this.jumpRidingScale = (float)this.jumpRidingTicks * 0.1F;
            } else {
               this.jumpRidingScale = 0.8F + 2.0F / (float)(this.jumpRidingTicks - 9) * 0.1F;
            }
         }
      } else {
         this.jumpRidingScale = 0.0F;
      }

      super.aiStep();
      if (this.onGround && this.getAbilities().flying && !this.minecraft.gameMode.isAlwaysFlying()) {
         this.getAbilities().flying = false;
         this.onUpdateAbilities();
      }
   }

   @Override
   protected void tickDeath() {
      ++this.deathTime;
      if (this.deathTime == 20) {
         this.remove(Entity.RemovalReason.KILLED);
      }
   }

   private void handleNetherPortalClient() {
      this.oPortalTime = this.portalTime;
      if (this.isInsidePortal) {
         if (this.minecraft.screen != null && !this.minecraft.screen.isPauseScreen() && !(this.minecraft.screen instanceof DeathScreen)) {
            if (this.minecraft.screen instanceof AbstractContainerScreen) {
               this.closeContainer();
            }

            this.minecraft.setScreen(null);
         }

         if (this.portalTime == 0.0F) {
            this.minecraft
               .getSoundManager()
               .play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, this.random.nextFloat() * 0.4F + 0.8F, 0.25F));
         }

         this.portalTime += 0.0125F;
         if (this.portalTime >= 1.0F) {
            this.portalTime = 1.0F;
         }

         this.isInsidePortal = false;
      } else if (this.hasEffect(MobEffects.CONFUSION) && this.getEffect(MobEffects.CONFUSION).getDuration() > 60) {
         this.portalTime += 0.006666667F;
         if (this.portalTime > 1.0F) {
            this.portalTime = 1.0F;
         }
      } else {
         if (this.portalTime > 0.0F) {
            this.portalTime -= 0.05F;
         }

         if (this.portalTime < 0.0F) {
            this.portalTime = 0.0F;
         }
      }

      this.processPortalCooldown();
   }

   @Override
   public void rideTick() {
      super.rideTick();
      this.handsBusy = false;
      if (this.getVehicle() instanceof Boat â˜ƒ) {
         â˜ƒ.setInput(this.input.left, this.input.right, this.input.up, this.input.down);
         this.handsBusy |= this.input.left || this.input.right || this.input.up || this.input.down;
      }
   }

   public boolean isHandsBusy() {
      return this.handsBusy;
   }

   @Nullable
   @Override
   public MobEffectInstance removeEffectNoUpdate(@Nullable MobEffect var1) {
      if (â˜ƒ == MobEffects.CONFUSION) {
         this.oPortalTime = 0.0F;
         this.portalTime = 0.0F;
      }

      return super.removeEffectNoUpdate(â˜ƒ);
   }

   @Override
   public void move(MoverType var1, Vec3 var2) {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getZ();
      super.move(â˜ƒ, â˜ƒ);
      this.updateAutoJump((float)(this.getX() - â˜ƒ), (float)(this.getZ() - â˜ƒx));
   }

   public boolean isAutoJumpEnabled() {
      return this.autoJumpEnabled;
   }

   protected void updateAutoJump(float var1, float var2) {
      if (this.canAutoJump()) {
         Vec3 â˜ƒ = this.position();
         Vec3 â˜ƒx = â˜ƒ.add((double)â˜ƒ, 0.0, (double)â˜ƒ);
         Vec3 â˜ƒxx = new Vec3((double)â˜ƒ, 0.0, (double)â˜ƒ);
         float â˜ƒxxx = this.getSpeed();
         float â˜ƒxxxx = (float)â˜ƒxx.lengthSqr();
         if (â˜ƒxxxx <= 0.001F) {
            Vec2 â˜ƒxxxxx = this.input.getMoveVector();
            float â˜ƒxxxxxx = â˜ƒxxx * â˜ƒxxxxx.x;
            float â˜ƒxxxxxxx = â˜ƒxxx * â˜ƒxxxxx.y;
            float â˜ƒxxxxxxxx = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
            float â˜ƒxxxxxxxxx = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
            â˜ƒxx = new Vec3(
               (double)(â˜ƒxxxxxx * â˜ƒxxxxxxxxx - â˜ƒxxxxxxx * â˜ƒxxxxxxxx), â˜ƒxx.y, (double)(â˜ƒxxxxxxx * â˜ƒxxxxxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxxxx)
            );
            â˜ƒxxxx = (float)â˜ƒxx.lengthSqr();
            if (â˜ƒxxxx <= 0.001F) {
               return;
            }
         }

         float â˜ƒ = Mth.fastInvSqrt(â˜ƒxxxx);
         Vec3 â˜ƒx = â˜ƒxx.scale((double)â˜ƒ);
         Vec3 â˜ƒxx = this.getForward();
         float â˜ƒxxx = (float)(â˜ƒxx.x * â˜ƒx.x + â˜ƒxx.z * â˜ƒx.z);
         if (!(â˜ƒxxx < -0.15F)) {
            CollisionContext â˜ƒxxxx = CollisionContext.of(this);
            BlockPos â˜ƒxxxxx = new BlockPos(this.getX(), this.getBoundingBox().maxY, this.getZ());
            BlockState â˜ƒxxxxxx = this.level.getBlockState(â˜ƒxxxxx);
            if (â˜ƒxxxxxx.getCollisionShape(this.level, â˜ƒxxxxx, â˜ƒxxxx).isEmpty()) {
               â˜ƒxxxxx = â˜ƒxxxxx.above();
               BlockState â˜ƒxxxxxxx = this.level.getBlockState(â˜ƒxxxxx);
               if (â˜ƒxxxxxxx.getCollisionShape(this.level, â˜ƒxxxxx, â˜ƒxxxx).isEmpty()) {
                  float â˜ƒxxxxxxxx = 7.0F;
                  float â˜ƒxxxxxxxxx = 1.2F;
                  if (this.hasEffect(MobEffects.JUMP)) {
                     â˜ƒxxxxxxxxx += (float)(this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.75F;
                  }

                  float â˜ƒxxxxxxxx = Math.max(â˜ƒxxx * 7.0F, 1.0F / â˜ƒ);
                  Vec3 â˜ƒxxxxxxxxx = â˜ƒx.add(â˜ƒx.scale((double)â˜ƒxxxxxxxx));
                  float â˜ƒxxxxxxxxxx = this.getBbWidth();
                  float â˜ƒxxxxxxxxxxx = this.getBbHeight();
                  AABB â˜ƒxxxxxxxxxxxx = new AABB(â˜ƒ, â˜ƒxxxxxxxxx.add(0.0, (double)â˜ƒxxxxxxxxxxx, 0.0))
                     .inflate((double)â˜ƒxxxxxxxxxx, 0.0, (double)â˜ƒxxxxxxxxxx);
                  Vec3 var19 = â˜ƒ.add(0.0, 0.51F, 0.0);
                  â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx.add(0.0, 0.51F, 0.0);
                  Vec3 â˜ƒxxxxxxxxxxxxx = â˜ƒx.cross(new Vec3(0.0, 1.0, 0.0));
                  Vec3 â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.scale((double)(â˜ƒxxxxxxxxxx * 0.5F));
                  Vec3 â˜ƒxxxxxxxxxxxxxxx = var19.subtract(â˜ƒxxxxxxxxxxxxxx);
                  Vec3 â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.subtract(â˜ƒxxxxxxxxxxxxxx);
                  Vec3 â˜ƒxxxxxxxxxxxxxxxxx = var19.add(â˜ƒxxxxxxxxxxxxxx);
                  Vec3 â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx.add(â˜ƒxxxxxxxxxxxxxx);
                  Iterator<AABB> â˜ƒxxxxxxxxxxxxxxxxxxx = this.level
                     .getCollisions(this, â˜ƒxxxxxxxxxxxx, var0 -> true)
                     .flatMap(var0 -> var0.toAabbs().stream())
                     .iterator();
                  float â˜ƒxxxxxxxxxxxxxxxxxxxx = Float.MIN_VALUE;

                  while(â˜ƒxxxxxxxxxxxxxxxxxxx.hasNext()) {
                     AABB â˜ƒxxxxxxxxxxxxxxxxxxxxx = (AABB)â˜ƒxxxxxxxxxxxxxxxxxxx.next();
                     if (â˜ƒxxxxxxxxxxxxxxxxxxxxx.intersects(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx)
                        || â˜ƒxxxxxxxxxxxxxxxxxxxxx.intersects(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx)) {
                        â˜ƒxxxxxxxxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxxxxxxxxxxxxx.maxY;
                        Vec3 â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx.getCenter();
                        BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxxxxxxxxxxxxxxxx);

                        for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = 1; (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx) {
                           BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.above(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx);
                           BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.level.getBlockState(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           VoxelShape â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx;
                           if (!(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx.getCollisionShape(
                                 this.level, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxx
                              ))
                              .isEmpty()) {
                              â˜ƒxxxxxxxxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx.max(Direction.Axis.Y) + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx.getY();
                              if ((double)â˜ƒxxxxxxxxxxxxxxxxxxxx - this.getY() > (double)â˜ƒxxxxxxxxx) {
                                 return;
                              }
                           }

                           if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx > 1) {
                              â˜ƒxxxxx = â˜ƒxxxxx.above();
                              BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = this.level.getBlockState(â˜ƒxxxxx);
                              if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx.getCollisionShape(this.level, â˜ƒxxxxx, â˜ƒxxxx).isEmpty()) {
                                 return;
                              }
                           }
                        }
                        break;
                     }
                  }

                  if (â˜ƒxxxxxxxxxxxxxxxxxxxx != Float.MIN_VALUE) {
                     float â˜ƒxxxxxxxxxxxxxxxxxxxxx = (float)((double)â˜ƒxxxxxxxxxxxxxxxxxxxx - this.getY());
                     if (!(â˜ƒxxxxxxxxxxxxxxxxxxxxx <= 0.5F) && !(â˜ƒxxxxxxxxxxxxxxxxxxxxx > â˜ƒxxxxxxxxx)) {
                        this.autoJumpTime = 1;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean canAutoJump() {
      return this.isAutoJumpEnabled()
         && this.autoJumpTime <= 0
         && this.onGround
         && !this.isStayingOnGroundSurface()
         && !this.isPassenger()
         && this.isMoving()
         && (double)this.getBlockJumpFactor() >= 1.0;
   }

   private boolean isMoving() {
      Vec2 â˜ƒ = this.input.getMoveVector();
      return â˜ƒ.x != 0.0F || â˜ƒ.y != 0.0F;
   }

   private boolean hasEnoughImpulseToStartSprinting() {
      double â˜ƒ = 0.8;
      return this.isUnderWater() ? this.input.hasForwardImpulse() : (double)this.input.forwardImpulse >= 0.8;
   }

   public float getWaterVision() {
      if (!this.isEyeInFluid(FluidTags.WATER)) {
         return 0.0F;
      } else {
         float â˜ƒ = 600.0F;
         float â˜ƒx = 100.0F;
         if ((float)this.waterVisionTime >= 600.0F) {
            return 1.0F;
         } else {
            float â˜ƒ = Mth.clamp((float)this.waterVisionTime / 100.0F, 0.0F, 1.0F);
            float â˜ƒx = (float)this.waterVisionTime < 100.0F ? 0.0F : Mth.clamp(((float)this.waterVisionTime - 100.0F) / 500.0F, 0.0F, 1.0F);
            return â˜ƒ * 0.6F + â˜ƒx * 0.39999998F;
         }
      }
   }

   @Override
   public boolean isUnderWater() {
      return this.wasUnderwater;
   }

   @Override
   protected boolean updateIsUnderwater() {
      boolean â˜ƒ = this.wasUnderwater;
      boolean â˜ƒx = super.updateIsUnderwater();
      if (this.isSpectator()) {
         return this.wasUnderwater;
      } else {
         if (!â˜ƒ && â˜ƒx) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.AMBIENT, 1.0F, 1.0F, false);
            this.minecraft.getSoundManager().play(new UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance(this));
         }

         if (â˜ƒ && !â˜ƒx) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.AMBIENT, 1.0F, 1.0F, false);
         }

         return this.wasUnderwater;
      }
   }

   @Override
   public Vec3 getRopeHoldPosition(float var1) {
      if (this.minecraft.options.getCameraType().isFirstPerson()) {
         float â˜ƒ = Mth.lerp(â˜ƒ * 0.5F, this.getYRot(), this.yRotO) * (float) (Math.PI / 180.0);
         float â˜ƒx = Mth.lerp(â˜ƒ * 0.5F, this.getXRot(), this.xRotO) * (float) (Math.PI / 180.0);
         double â˜ƒxx = this.getMainArm() == HumanoidArm.RIGHT ? -1.0 : 1.0;
         Vec3 â˜ƒxxx = new Vec3(0.39 * â˜ƒxx, -0.6, 0.3);
         return â˜ƒxxx.xRot(-â˜ƒx).yRot(-â˜ƒ).add(this.getEyePosition(â˜ƒ));
      } else {
         return super.getRopeHoldPosition(â˜ƒ);
      }
   }

   @Override
   public void updateTutorialInventoryAction(ItemStack var1, ItemStack var2, ClickAction var3) {
      this.minecraft.getTutorial().onInventoryAction(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
