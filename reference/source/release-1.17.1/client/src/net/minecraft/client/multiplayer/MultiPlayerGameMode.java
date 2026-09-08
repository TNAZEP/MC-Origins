package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiPlayerGameMode {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft minecraft;
   private final ClientPacketListener connection;
   private BlockPos destroyBlockPos = new BlockPos(-1, -1, -1);
   private ItemStack destroyingItem = ItemStack.EMPTY;
   private float destroyProgress;
   private float destroyTicks;
   private int destroyDelay;
   private boolean isDestroying;
   private GameType localPlayerMode = GameType.DEFAULT_MODE;
   @Nullable
   private GameType previousLocalPlayerMode;
   private final Object2ObjectLinkedOpenHashMap<Pair<BlockPos, ServerboundPlayerActionPacket.Action>, Vec3> unAckedActions = new Object2ObjectLinkedOpenHashMap<>(
      
   );
   private static final int MAX_ACTIONS_SIZE = 50;
   private int carriedIndex;

   public MultiPlayerGameMode(Minecraft var1, ClientPacketListener var2) {
      this.minecraft = â˜ƒ;
      this.connection = â˜ƒ;
   }

   public void adjustPlayer(Player var1) {
      this.localPlayerMode.updatePlayerAbilities(â˜ƒ.getAbilities());
   }

   public void setLocalMode(GameType var1, @Nullable GameType var2) {
      this.localPlayerMode = â˜ƒ;
      this.previousLocalPlayerMode = â˜ƒ;
      this.localPlayerMode.updatePlayerAbilities(this.minecraft.player.getAbilities());
   }

   public void setLocalMode(GameType var1) {
      if (â˜ƒ != this.localPlayerMode) {
         this.previousLocalPlayerMode = this.localPlayerMode;
      }

      this.localPlayerMode = â˜ƒ;
      this.localPlayerMode.updatePlayerAbilities(this.minecraft.player.getAbilities());
   }

   public boolean canHurtPlayer() {
      return this.localPlayerMode.isSurvival();
   }

   public boolean destroyBlock(BlockPos var1) {
      if (this.minecraft.player.blockActionRestricted(this.minecraft.level, â˜ƒ, this.localPlayerMode)) {
         return false;
      } else {
         Level â˜ƒ = this.minecraft.level;
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         if (!this.minecraft.player.getMainHandItem().getItem().canAttackBlock(â˜ƒx, â˜ƒ, â˜ƒ, this.minecraft.player)) {
            return false;
         } else {
            Block â˜ƒ = â˜ƒx.getBlock();
            if (â˜ƒ instanceof GameMasterBlock && !this.minecraft.player.canUseGameMasterBlocks()) {
               return false;
            } else if (â˜ƒx.isAir()) {
               return false;
            } else {
               â˜ƒ.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒx, this.minecraft.player);
               FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
               boolean â˜ƒx = â˜ƒ.setBlock(â˜ƒ, â˜ƒ.createLegacyBlock(), 11);
               if (â˜ƒx) {
                  â˜ƒ.destroy(â˜ƒ, â˜ƒ, â˜ƒx);
               }

               return â˜ƒx;
            }
         }
      }
   }

   public boolean startDestroyBlock(BlockPos var1, Direction var2) {
      if (this.minecraft.player.blockActionRestricted(this.minecraft.level, â˜ƒ, this.localPlayerMode)) {
         return false;
      } else if (!this.minecraft.level.getWorldBorder().isWithinBounds(â˜ƒ)) {
         return false;
      } else {
         if (this.localPlayerMode.isCreative()) {
            BlockState â˜ƒ = this.minecraft.level.getBlockState(â˜ƒ);
            this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, â˜ƒ, â˜ƒ, 1.0F);
            this.sendBlockAction(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, â˜ƒ, â˜ƒ);
            this.destroyBlock(â˜ƒ);
            this.destroyDelay = 5;
         } else if (!this.isDestroying || !this.sameDestroyTarget(â˜ƒ)) {
            if (this.isDestroying) {
               this.sendBlockAction(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.destroyBlockPos, â˜ƒ);
            }

            BlockState â˜ƒ = this.minecraft.level.getBlockState(â˜ƒ);
            this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, â˜ƒ, â˜ƒ, 0.0F);
            this.sendBlockAction(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, â˜ƒ, â˜ƒ);
            boolean â˜ƒx = !â˜ƒ.isAir();
            if (â˜ƒx && this.destroyProgress == 0.0F) {
               â˜ƒ.attack(this.minecraft.level, â˜ƒ, this.minecraft.player);
            }

            if (â˜ƒx && â˜ƒ.getDestroyProgress(this.minecraft.player, this.minecraft.player.level, â˜ƒ) >= 1.0F) {
               this.destroyBlock(â˜ƒ);
            } else {
               this.isDestroying = true;
               this.destroyBlockPos = â˜ƒ;
               this.destroyingItem = this.minecraft.player.getMainHandItem();
               this.destroyProgress = 0.0F;
               this.destroyTicks = 0.0F;
               this.minecraft.level.destroyBlockProgress(this.minecraft.player.getId(), this.destroyBlockPos, (int)(this.destroyProgress * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public void stopDestroyBlock() {
      if (this.isDestroying) {
         BlockState â˜ƒ = this.minecraft.level.getBlockState(this.destroyBlockPos);
         this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, this.destroyBlockPos, â˜ƒ, -1.0F);
         this.sendBlockAction(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.destroyBlockPos, Direction.DOWN);
         this.isDestroying = false;
         this.destroyProgress = 0.0F;
         this.minecraft.level.destroyBlockProgress(this.minecraft.player.getId(), this.destroyBlockPos, -1);
         this.minecraft.player.resetAttackStrengthTicker();
      }
   }

   public boolean continueDestroyBlock(BlockPos var1, Direction var2) {
      this.ensureHasSentCarriedItem();
      if (this.destroyDelay > 0) {
         --this.destroyDelay;
         return true;
      } else if (this.localPlayerMode.isCreative() && this.minecraft.level.getWorldBorder().isWithinBounds(â˜ƒ)) {
         this.destroyDelay = 5;
         BlockState â˜ƒ = this.minecraft.level.getBlockState(â˜ƒ);
         this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, â˜ƒ, â˜ƒ, 1.0F);
         this.sendBlockAction(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, â˜ƒ, â˜ƒ);
         this.destroyBlock(â˜ƒ);
         return true;
      } else if (this.sameDestroyTarget(â˜ƒ)) {
         BlockState â˜ƒ = this.minecraft.level.getBlockState(â˜ƒ);
         if (â˜ƒ.isAir()) {
            this.isDestroying = false;
            return false;
         } else {
            this.destroyProgress += â˜ƒ.getDestroyProgress(this.minecraft.player, this.minecraft.player.level, â˜ƒ);
            if (this.destroyTicks % 4.0F == 0.0F) {
               SoundType â˜ƒ = â˜ƒ.getSoundType();
               this.minecraft
                  .getSoundManager()
                  .play(new SimpleSoundInstance(â˜ƒ.getHitSound(), SoundSource.BLOCKS, (â˜ƒ.getVolume() + 1.0F) / 8.0F, â˜ƒ.getPitch() * 0.5F, â˜ƒ));
            }

            ++this.destroyTicks;
            this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, â˜ƒ, â˜ƒ, Mth.clamp(this.destroyProgress, 0.0F, 1.0F));
            if (this.destroyProgress >= 1.0F) {
               this.isDestroying = false;
               this.sendBlockAction(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, â˜ƒ, â˜ƒ);
               this.destroyBlock(â˜ƒ);
               this.destroyProgress = 0.0F;
               this.destroyTicks = 0.0F;
               this.destroyDelay = 5;
            }

            this.minecraft.level.destroyBlockProgress(this.minecraft.player.getId(), this.destroyBlockPos, (int)(this.destroyProgress * 10.0F) - 1);
            return true;
         }
      } else {
         return this.startDestroyBlock(â˜ƒ, â˜ƒ);
      }
   }

   public float getPickRange() {
      return this.localPlayerMode.isCreative() ? 5.0F : 4.5F;
   }

   public void tick() {
      this.ensureHasSentCarriedItem();
      if (this.connection.getConnection().isConnected()) {
         this.connection.getConnection().tick();
      } else {
         this.connection.getConnection().handleDisconnection();
      }
   }

   private boolean sameDestroyTarget(BlockPos var1) {
      ItemStack â˜ƒ = this.minecraft.player.getMainHandItem();
      boolean â˜ƒx = this.destroyingItem.isEmpty() && â˜ƒ.isEmpty();
      if (!this.destroyingItem.isEmpty() && !â˜ƒ.isEmpty()) {
         â˜ƒx = â˜ƒ.is(this.destroyingItem.getItem())
            && ItemStack.tagMatches(â˜ƒ, this.destroyingItem)
            && (â˜ƒ.isDamageableItem() || â˜ƒ.getDamageValue() == this.destroyingItem.getDamageValue());
      }

      return â˜ƒ.equals(this.destroyBlockPos) && â˜ƒx;
   }

   private void ensureHasSentCarriedItem() {
      int â˜ƒ = this.minecraft.player.getInventory().selected;
      if (â˜ƒ != this.carriedIndex) {
         this.carriedIndex = â˜ƒ;
         this.connection.send(new ServerboundSetCarriedItemPacket(this.carriedIndex));
      }
   }

   public InteractionResult useItemOn(LocalPlayer var1, ClientLevel var2, InteractionHand var3, BlockHitResult var4) {
      this.ensureHasSentCarriedItem();
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      if (!this.minecraft.level.getWorldBorder().isWithinBounds(â˜ƒ)) {
         return InteractionResult.FAIL;
      } else {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
         if (this.localPlayerMode == GameType.SPECTATOR) {
            this.connection.send(new ServerboundUseItemOnPacket(â˜ƒ, â˜ƒ));
            return InteractionResult.SUCCESS;
         } else {
            boolean â˜ƒ = !â˜ƒ.getMainHandItem().isEmpty() || !â˜ƒ.getOffhandItem().isEmpty();
            boolean â˜ƒx = â˜ƒ.isSecondaryUseActive() && â˜ƒ;
            if (!â˜ƒx) {
               InteractionResult â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ).use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               if (â˜ƒxx.consumesAction()) {
                  this.connection.send(new ServerboundUseItemOnPacket(â˜ƒ, â˜ƒ));
                  return â˜ƒxx;
               }
            }

            this.connection.send(new ServerboundUseItemOnPacket(â˜ƒ, â˜ƒ));
            if (!â˜ƒ.isEmpty() && !â˜ƒ.getCooldowns().isOnCooldown(â˜ƒ.getItem())) {
               UseOnContext â˜ƒx = new UseOnContext(â˜ƒ, â˜ƒ, â˜ƒ);
               InteractionResult â˜ƒ;
               if (this.localPlayerMode.isCreative()) {
                  int â˜ƒxx = â˜ƒ.getCount();
                  â˜ƒ = â˜ƒ.useOn(â˜ƒx);
                  â˜ƒ.setCount(â˜ƒxx);
               } else {
                  â˜ƒ = â˜ƒ.useOn(â˜ƒx);
               }

               return â˜ƒ;
            } else {
               return InteractionResult.PASS;
            }
         }
      }
   }

   public InteractionResult useItem(Player var1, Level var2, InteractionHand var3) {
      if (this.localPlayerMode == GameType.SPECTATOR) {
         return InteractionResult.PASS;
      } else {
         this.ensureHasSentCarriedItem();
         this.connection.send(new ServerboundMovePlayerPacket.PosRot(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getYRot(), â˜ƒ.getXRot(), â˜ƒ.isOnGround()));
         this.connection.send(new ServerboundUseItemPacket(â˜ƒ));
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
         if (â˜ƒ.getCooldowns().isOnCooldown(â˜ƒ.getItem())) {
            return InteractionResult.PASS;
         } else {
            InteractionResultHolder<ItemStack> â˜ƒ = â˜ƒ.use(â˜ƒ, â˜ƒ, â˜ƒ);
            ItemStack â˜ƒx = â˜ƒ.getObject();
            if (â˜ƒx != â˜ƒ) {
               â˜ƒ.setItemInHand(â˜ƒ, â˜ƒx);
            }

            return â˜ƒ.getResult();
         }
      }
   }

   public LocalPlayer createPlayer(ClientLevel var1, StatsCounter var2, ClientRecipeBook var3) {
      return this.createPlayer(â˜ƒ, â˜ƒ, â˜ƒ, false, false);
   }

   public LocalPlayer createPlayer(ClientLevel var1, StatsCounter var2, ClientRecipeBook var3, boolean var4, boolean var5) {
      return new LocalPlayer(this.minecraft, â˜ƒ, this.connection, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void attack(Player var1, Entity var2) {
      this.ensureHasSentCarriedItem();
      this.connection.send(ServerboundInteractPacket.createAttackPacket(â˜ƒ, â˜ƒ.isShiftKeyDown()));
      if (this.localPlayerMode != GameType.SPECTATOR) {
         â˜ƒ.attack(â˜ƒ);
         â˜ƒ.resetAttackStrengthTicker();
      }
   }

   public InteractionResult interact(Player var1, Entity var2, InteractionHand var3) {
      this.ensureHasSentCarriedItem();
      this.connection.send(ServerboundInteractPacket.createInteractionPacket(â˜ƒ, â˜ƒ.isShiftKeyDown(), â˜ƒ));
      return this.localPlayerMode == GameType.SPECTATOR ? InteractionResult.PASS : â˜ƒ.interactOn(â˜ƒ, â˜ƒ);
   }

   public InteractionResult interactAt(Player var1, Entity var2, EntityHitResult var3, InteractionHand var4) {
      this.ensureHasSentCarriedItem();
      Vec3 â˜ƒ = â˜ƒ.getLocation().subtract(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      this.connection.send(ServerboundInteractPacket.createInteractionPacket(â˜ƒ, â˜ƒ.isShiftKeyDown(), â˜ƒ, â˜ƒ));
      return this.localPlayerMode == GameType.SPECTATOR ? InteractionResult.PASS : â˜ƒ.interactAt(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void handleInventoryMouseClick(int var1, int var2, int var3, ClickType var4, Player var5) {
      AbstractContainerMenu â˜ƒ = â˜ƒ.containerMenu;
      NonNullList<Slot> â˜ƒx = â˜ƒ.slots;
      int â˜ƒxx = â˜ƒx.size();
      List<ItemStack> â˜ƒxxx = Lists.<ItemStack>newArrayListWithCapacity(â˜ƒxx);

      for(Slot â˜ƒxxxx : â˜ƒx) {
         â˜ƒxxx.add(â˜ƒxxxx.getItem().copy());
      }

      â˜ƒ.clicked(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      Int2ObjectMap<ItemStack> â˜ƒxxxx = new Int2ObjectOpenHashMap<>();

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxx; ++â˜ƒxxxxx) {
         ItemStack â˜ƒxxxxxx = (ItemStack)â˜ƒxxx.get(â˜ƒxxxxx);
         ItemStack â˜ƒxxxxxxx = â˜ƒx.get(â˜ƒxxxxx).getItem();
         if (!ItemStack.matches(â˜ƒxxxxxx, â˜ƒxxxxxxx)) {
            â˜ƒxxxx.put(â˜ƒxxxxx, â˜ƒxxxxxxx.copy());
         }
      }

      this.connection.send(new ServerboundContainerClickPacket(â˜ƒ, â˜ƒ.getStateId(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getCarried().copy(), â˜ƒxxxx));
   }

   public void handlePlaceRecipe(int var1, Recipe<?> var2, boolean var3) {
      this.connection.send(new ServerboundPlaceRecipePacket(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void handleInventoryButtonClick(int var1, int var2) {
      this.connection.send(new ServerboundContainerButtonClickPacket(â˜ƒ, â˜ƒ));
   }

   public void handleCreativeModeItemAdd(ItemStack var1, int var2) {
      if (this.localPlayerMode.isCreative()) {
         this.connection.send(new ServerboundSetCreativeModeSlotPacket(â˜ƒ, â˜ƒ));
      }
   }

   public void handleCreativeModeItemDrop(ItemStack var1) {
      if (this.localPlayerMode.isCreative() && !â˜ƒ.isEmpty()) {
         this.connection.send(new ServerboundSetCreativeModeSlotPacket(-1, â˜ƒ));
      }
   }

   public void releaseUsingItem(Player var1) {
      this.ensureHasSentCarriedItem();
      this.connection.send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
      â˜ƒ.releaseUsingItem();
   }

   public boolean hasExperience() {
      return this.localPlayerMode.isSurvival();
   }

   public boolean hasMissTime() {
      return !this.localPlayerMode.isCreative();
   }

   public boolean hasInfiniteItems() {
      return this.localPlayerMode.isCreative();
   }

   public boolean hasFarPickRange() {
      return this.localPlayerMode.isCreative();
   }

   public boolean isServerControlledInventory() {
      return this.minecraft.player.isPassenger() && this.minecraft.player.getVehicle() instanceof AbstractHorse;
   }

   public boolean isAlwaysFlying() {
      return this.localPlayerMode == GameType.SPECTATOR;
   }

   @Nullable
   public GameType getPreviousPlayerMode() {
      return this.previousLocalPlayerMode;
   }

   public GameType getPlayerMode() {
      return this.localPlayerMode;
   }

   public boolean isDestroying() {
      return this.isDestroying;
   }

   public void handlePickItem(int var1) {
      this.connection.send(new ServerboundPickItemPacket(â˜ƒ));
   }

   private void sendBlockAction(ServerboundPlayerActionPacket.Action var1, BlockPos var2, Direction var3) {
      LocalPlayer â˜ƒ = this.minecraft.player;
      this.unAckedActions.put(Pair.of(â˜ƒ, â˜ƒ), â˜ƒ.position());
      this.connection.send(new ServerboundPlayerActionPacket(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void handleBlockBreakAck(ClientLevel var1, BlockPos var2, BlockState var3, ServerboundPlayerActionPacket.Action var4, boolean var5) {
      Vec3 â˜ƒ = this.unAckedActions.remove(Pair.of(â˜ƒ, â˜ƒ));
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if ((â˜ƒ == null || !â˜ƒ || â˜ƒ != ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK && â˜ƒx != â˜ƒ) && â˜ƒx != â˜ƒ) {
         â˜ƒ.setKnownState(â˜ƒ, â˜ƒ);
         Player â˜ƒxx = this.minecraft.player;
         if (â˜ƒ != null && â˜ƒ == â˜ƒxx.level && â˜ƒxx.isColliding(â˜ƒ, â˜ƒ)) {
            â˜ƒxx.absMoveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
         }
      }

      while(this.unAckedActions.size() >= 50) {
         Pair<BlockPos, ServerboundPlayerActionPacket.Action> â˜ƒ = this.unAckedActions.firstKey();
         this.unAckedActions.removeFirst();
         LOGGER.error("Too many unacked block actions, dropping {}", â˜ƒ);
      }
   }
}
