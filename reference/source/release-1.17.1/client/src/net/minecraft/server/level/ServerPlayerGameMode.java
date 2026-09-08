package net.minecraft.server.level;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockBreakAckPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayerGameMode {
   private static final Logger LOGGER = LogManager.getLogger();
   protected ServerLevel level;
   protected final ServerPlayer player;
   private GameType gameModeForPlayer = GameType.DEFAULT_MODE;
   @Nullable
   private GameType previousGameModeForPlayer;
   private boolean isDestroyingBlock;
   private int destroyProgressStart;
   private BlockPos destroyPos = BlockPos.ZERO;
   private int gameTicks;
   private boolean hasDelayedDestroy;
   private BlockPos delayedDestroyPos = BlockPos.ZERO;
   private int delayedTickStart;
   private int lastSentState = -1;

   public ServerPlayerGameMode(ServerPlayer var1) {
      this.player = â˜ƒ;
      this.level = â˜ƒ.getLevel();
   }

   public boolean changeGameModeForPlayer(GameType var1) {
      if (â˜ƒ == this.gameModeForPlayer) {
         return false;
      } else {
         this.setGameModeForPlayer(â˜ƒ, this.gameModeForPlayer);
         return true;
      }
   }

   protected void setGameModeForPlayer(GameType var1, @Nullable GameType var2) {
      this.previousGameModeForPlayer = â˜ƒ;
      this.gameModeForPlayer = â˜ƒ;
      â˜ƒ.updatePlayerAbilities(this.player.getAbilities());
      this.player.onUpdateAbilities();
      this.player.server.getPlayerList().broadcastAll(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_GAME_MODE, this.player));
      this.level.updateSleepingPlayerList();
   }

   public GameType getGameModeForPlayer() {
      return this.gameModeForPlayer;
   }

   @Nullable
   public GameType getPreviousGameModeForPlayer() {
      return this.previousGameModeForPlayer;
   }

   public boolean isSurvival() {
      return this.gameModeForPlayer.isSurvival();
   }

   public boolean isCreative() {
      return this.gameModeForPlayer.isCreative();
   }

   public void tick() {
      ++this.gameTicks;
      if (this.hasDelayedDestroy) {
         BlockState â˜ƒ = this.level.getBlockState(this.delayedDestroyPos);
         if (â˜ƒ.isAir()) {
            this.hasDelayedDestroy = false;
         } else {
            float â˜ƒ = this.incrementDestroyProgress(â˜ƒ, this.delayedDestroyPos, this.delayedTickStart);
            if (â˜ƒ >= 1.0F) {
               this.hasDelayedDestroy = false;
               this.destroyBlock(this.delayedDestroyPos);
            }
         }
      } else if (this.isDestroyingBlock) {
         BlockState â˜ƒ = this.level.getBlockState(this.destroyPos);
         if (â˜ƒ.isAir()) {
            this.level.destroyBlockProgress(this.player.getId(), this.destroyPos, -1);
            this.lastSentState = -1;
            this.isDestroyingBlock = false;
         } else {
            this.incrementDestroyProgress(â˜ƒ, this.destroyPos, this.destroyProgressStart);
         }
      }
   }

   private float incrementDestroyProgress(BlockState var1, BlockPos var2, int var3) {
      int â˜ƒ = this.gameTicks - â˜ƒ;
      float â˜ƒx = â˜ƒ.getDestroyProgress(this.player, this.player.level, â˜ƒ) * (float)(â˜ƒ + 1);
      int â˜ƒxx = (int)(â˜ƒx * 10.0F);
      if (â˜ƒxx != this.lastSentState) {
         this.level.destroyBlockProgress(this.player.getId(), â˜ƒ, â˜ƒxx);
         this.lastSentState = â˜ƒxx;
      }

      return â˜ƒx;
   }

   public void handleBlockBreakAction(BlockPos var1, ServerboundPlayerActionPacket.Action var2, Direction var3, int var4) {
      double â˜ƒ = this.player.getX() - ((double)â˜ƒ.getX() + 0.5);
      double â˜ƒx = this.player.getY() - ((double)â˜ƒ.getY() + 0.5) + 1.5;
      double â˜ƒxx = this.player.getZ() - ((double)â˜ƒ.getZ() + 0.5);
      double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
      if (â˜ƒxxx > 36.0) {
         this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, false, "too far"));
      } else if (â˜ƒ.getY() >= â˜ƒ) {
         this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, false, "too high"));
      } else {
         if (â˜ƒ == ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK) {
            if (!this.level.mayInteract(this.player, â˜ƒ)) {
               this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, false, "may not interact"));
               return;
            }

            if (this.isCreative()) {
               this.destroyAndAck(â˜ƒ, â˜ƒ, "creative destroy");
               return;
            }

            if (this.player.blockActionRestricted(this.level, â˜ƒ, this.gameModeForPlayer)) {
               this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, false, "block action restricted"));
               return;
            }

            this.destroyProgressStart = this.gameTicks;
            float â˜ƒ = 1.0F;
            BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
            if (!â˜ƒx.isAir()) {
               â˜ƒx.attack(this.level, â˜ƒ, this.player);
               â˜ƒ = â˜ƒx.getDestroyProgress(this.player, this.player.level, â˜ƒ);
            }

            if (!â˜ƒx.isAir() && â˜ƒ >= 1.0F) {
               this.destroyAndAck(â˜ƒ, â˜ƒ, "insta mine");
            } else {
               if (this.isDestroyingBlock) {
                  this.player
                     .connection
                     .send(
                        new ClientboundBlockBreakAckPacket(
                           this.destroyPos,
                           this.level.getBlockState(this.destroyPos),
                           ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK,
                           false,
                           "abort destroying since another started (client insta mine, server disagreed)"
                        )
                     );
               }

               this.isDestroyingBlock = true;
               this.destroyPos = â˜ƒ.immutable();
               int â˜ƒ = (int)(â˜ƒ * 10.0F);
               this.level.destroyBlockProgress(this.player.getId(), â˜ƒ, â˜ƒ);
               this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, true, "actual start of destroying"));
               this.lastSentState = â˜ƒ;
            }
         } else if (â˜ƒ == ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK) {
            if (â˜ƒ.equals(this.destroyPos)) {
               int â˜ƒ = this.gameTicks - this.destroyProgressStart;
               BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
               if (!â˜ƒx.isAir()) {
                  float â˜ƒxx = â˜ƒx.getDestroyProgress(this.player, this.player.level, â˜ƒ) * (float)(â˜ƒ + 1);
                  if (â˜ƒxx >= 0.7F) {
                     this.isDestroyingBlock = false;
                     this.level.destroyBlockProgress(this.player.getId(), â˜ƒ, -1);
                     this.destroyAndAck(â˜ƒ, â˜ƒ, "destroyed");
                     return;
                  }

                  if (!this.hasDelayedDestroy) {
                     this.isDestroyingBlock = false;
                     this.hasDelayedDestroy = true;
                     this.delayedDestroyPos = â˜ƒ;
                     this.delayedTickStart = this.destroyProgressStart;
                  }
               }
            }

            this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, true, "stopped destroying"));
         } else if (â˜ƒ == ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK) {
            this.isDestroyingBlock = false;
            if (!Objects.equals(this.destroyPos, â˜ƒ)) {
               LOGGER.warn("Mismatch in destroy block pos: {} {}", this.destroyPos, â˜ƒ);
               this.level.destroyBlockProgress(this.player.getId(), this.destroyPos, -1);
               this.player
                  .connection
                  .send(
                     new ClientboundBlockBreakAckPacket(this.destroyPos, this.level.getBlockState(this.destroyPos), â˜ƒ, true, "aborted mismatched destroying")
                  );
            }

            this.level.destroyBlockProgress(this.player.getId(), â˜ƒ, -1);
            this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, true, "aborted destroying"));
         }
      }
   }

   public void destroyAndAck(BlockPos var1, ServerboundPlayerActionPacket.Action var2, String var3) {
      if (this.destroyBlock(â˜ƒ)) {
         this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, true, â˜ƒ));
      } else {
         this.player.connection.send(new ClientboundBlockBreakAckPacket(â˜ƒ, this.level.getBlockState(â˜ƒ), â˜ƒ, false, â˜ƒ));
      }
   }

   public boolean destroyBlock(BlockPos var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (!this.player.getMainHandItem().getItem().canAttackBlock(â˜ƒ, this.level, â˜ƒ, this.player)) {
         return false;
      } else {
         BlockEntity â˜ƒ = this.level.getBlockEntity(â˜ƒ);
         Block â˜ƒx = â˜ƒ.getBlock();
         if (â˜ƒx instanceof GameMasterBlock && !this.player.canUseGameMasterBlocks()) {
            this.level.sendBlockUpdated(â˜ƒ, â˜ƒ, â˜ƒ, 3);
            return false;
         } else if (this.player.blockActionRestricted(this.level, â˜ƒ, this.gameModeForPlayer)) {
            return false;
         } else {
            â˜ƒx.playerWillDestroy(this.level, â˜ƒ, â˜ƒ, this.player);
            boolean â˜ƒ = this.level.removeBlock(â˜ƒ, false);
            if (â˜ƒ) {
               â˜ƒx.destroy(this.level, â˜ƒ, â˜ƒ);
            }

            if (this.isCreative()) {
               return true;
            } else {
               ItemStack â˜ƒ = this.player.getMainHandItem();
               ItemStack â˜ƒx = â˜ƒ.copy();
               boolean â˜ƒxx = this.player.hasCorrectToolForDrops(â˜ƒ);
               â˜ƒ.mineBlock(this.level, â˜ƒ, â˜ƒ, this.player);
               if (â˜ƒ && â˜ƒxx) {
                  â˜ƒx.playerDestroy(this.level, this.player, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
               }

               return true;
            }
         }
      }
   }

   public InteractionResult useItem(ServerPlayer var1, Level var2, ItemStack var3, InteractionHand var4) {
      if (this.gameModeForPlayer == GameType.SPECTATOR) {
         return InteractionResult.PASS;
      } else if (â˜ƒ.getCooldowns().isOnCooldown(â˜ƒ.getItem())) {
         return InteractionResult.PASS;
      } else {
         int â˜ƒ = â˜ƒ.getCount();
         int â˜ƒx = â˜ƒ.getDamageValue();
         InteractionResultHolder<ItemStack> â˜ƒxx = â˜ƒ.use(â˜ƒ, â˜ƒ, â˜ƒ);
         ItemStack â˜ƒxxx = â˜ƒxx.getObject();
         if (â˜ƒxxx == â˜ƒ && â˜ƒxxx.getCount() == â˜ƒ && â˜ƒxxx.getUseDuration() <= 0 && â˜ƒxxx.getDamageValue() == â˜ƒx) {
            return â˜ƒxx.getResult();
         } else if (â˜ƒxx.getResult() == InteractionResult.FAIL && â˜ƒxxx.getUseDuration() > 0 && !â˜ƒ.isUsingItem()) {
            return â˜ƒxx.getResult();
         } else {
            â˜ƒ.setItemInHand(â˜ƒ, â˜ƒxxx);
            if (this.isCreative()) {
               â˜ƒxxx.setCount(â˜ƒ);
               if (â˜ƒxxx.isDamageableItem() && â˜ƒxxx.getDamageValue() != â˜ƒx) {
                  â˜ƒxxx.setDamageValue(â˜ƒx);
               }
            }

            if (â˜ƒxxx.isEmpty()) {
               â˜ƒ.setItemInHand(â˜ƒ, ItemStack.EMPTY);
            }

            if (!â˜ƒ.isUsingItem()) {
               â˜ƒ.inventoryMenu.sendAllDataToRemote();
            }

            return â˜ƒxx.getResult();
         }
      }
   }

   public InteractionResult useItemOn(ServerPlayer var1, Level var2, ItemStack var3, InteractionHand var4, BlockHitResult var5) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (this.gameModeForPlayer == GameType.SPECTATOR) {
         MenuProvider â˜ƒxx = â˜ƒx.getMenuProvider(â˜ƒ, â˜ƒ);
         if (â˜ƒxx != null) {
            â˜ƒ.openMenu(â˜ƒxx);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.PASS;
         }
      } else {
         boolean â˜ƒ = !â˜ƒ.getMainHandItem().isEmpty() || !â˜ƒ.getOffhandItem().isEmpty();
         boolean â˜ƒx = â˜ƒ.isSecondaryUseActive() && â˜ƒ;
         ItemStack â˜ƒxx = â˜ƒ.copy();
         if (!â˜ƒx) {
            InteractionResult â˜ƒxxx = â˜ƒx.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒxxx.consumesAction()) {
               CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(â˜ƒ, â˜ƒ, â˜ƒxx);
               return â˜ƒxxx;
            }
         }

         if (!â˜ƒ.isEmpty() && !â˜ƒ.getCooldowns().isOnCooldown(â˜ƒ.getItem())) {
            UseOnContext â˜ƒx = new UseOnContext(â˜ƒ, â˜ƒ, â˜ƒ);
            InteractionResult â˜ƒ;
            if (this.isCreative()) {
               int â˜ƒxx = â˜ƒ.getCount();
               â˜ƒ = â˜ƒ.useOn(â˜ƒx);
               â˜ƒ.setCount(â˜ƒxx);
            } else {
               â˜ƒ = â˜ƒ.useOn(â˜ƒx);
            }

            if (â˜ƒ.consumesAction()) {
               CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(â˜ƒ, â˜ƒ, â˜ƒxx);
            }

            return â˜ƒ;
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   public void setLevel(ServerLevel var1) {
      this.level = â˜ƒ;
   }
}
