package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CommandBlockEntity extends BlockEntity {
   private boolean powered;
   private boolean auto;
   private boolean conditionMet;
   private boolean sendToClient;
   private final BaseCommandBlock commandBlock = new BaseCommandBlock() {
      @Override
      public void setCommand(String var1) {
         super.setCommand(â˜ƒ);
         CommandBlockEntity.this.setChanged();
      }

      @Override
      public ServerLevel getLevel() {
         return (ServerLevel)CommandBlockEntity.this.level;
      }

      @Override
      public void onUpdated() {
         BlockState â˜ƒ = CommandBlockEntity.this.level.getBlockState(CommandBlockEntity.this.worldPosition);
         this.getLevel().sendBlockUpdated(CommandBlockEntity.this.worldPosition, â˜ƒ, â˜ƒ, 3);
      }

      @Override
      public Vec3 getPosition() {
         return Vec3.atCenterOf(CommandBlockEntity.this.worldPosition);
      }

      @Override
      public CommandSourceStack createCommandSourceStack() {
         return new CommandSourceStack(
            this,
            Vec3.atCenterOf(CommandBlockEntity.this.worldPosition),
            Vec2.ZERO,
            this.getLevel(),
            2,
            this.getName().getString(),
            this.getName(),
            this.getLevel().getServer(),
            null
         );
      }
   };

   public CommandBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.COMMAND_BLOCK, â˜ƒ, â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      this.commandBlock.save(â˜ƒ);
      â˜ƒ.putBoolean("powered", this.isPowered());
      â˜ƒ.putBoolean("conditionMet", this.wasConditionMet());
      â˜ƒ.putBoolean("auto", this.isAutomatic());
      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.commandBlock.load(â˜ƒ);
      this.powered = â˜ƒ.getBoolean("powered");
      this.conditionMet = â˜ƒ.getBoolean("conditionMet");
      this.setAutomatic(â˜ƒ.getBoolean("auto"));
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      if (this.isSendToClient()) {
         this.setSendToClient(false);
         CompoundTag â˜ƒ = this.save(new CompoundTag());
         return new ClientboundBlockEntityDataPacket(this.worldPosition, 2, â˜ƒ);
      } else {
         return null;
      }
   }

   @Override
   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public BaseCommandBlock getCommandBlock() {
      return this.commandBlock;
   }

   public void setPowered(boolean var1) {
      this.powered = â˜ƒ;
   }

   public boolean isPowered() {
      return this.powered;
   }

   public boolean isAutomatic() {
      return this.auto;
   }

   public void setAutomatic(boolean var1) {
      boolean â˜ƒ = this.auto;
      this.auto = â˜ƒ;
      if (!â˜ƒ && â˜ƒ && !this.powered && this.level != null && this.getMode() != CommandBlockEntity.Mode.SEQUENCE) {
         this.scheduleTick();
      }
   }

   public void onModeSwitch() {
      CommandBlockEntity.Mode â˜ƒ = this.getMode();
      if (â˜ƒ == CommandBlockEntity.Mode.AUTO && (this.powered || this.auto) && this.level != null) {
         this.scheduleTick();
      }
   }

   private void scheduleTick() {
      Block â˜ƒ = this.getBlockState().getBlock();
      if (â˜ƒ instanceof CommandBlock) {
         this.markConditionMet();
         this.level.getBlockTicks().scheduleTick(this.worldPosition, â˜ƒ, 1);
      }
   }

   public boolean wasConditionMet() {
      return this.conditionMet;
   }

   public boolean markConditionMet() {
      this.conditionMet = true;
      if (this.isConditional()) {
         BlockPos â˜ƒ = this.worldPosition.relative(((Direction)this.level.getBlockState(this.worldPosition).getValue(CommandBlock.FACING)).getOpposite());
         if (this.level.getBlockState(â˜ƒ).getBlock() instanceof CommandBlock) {
            BlockEntity â˜ƒx = this.level.getBlockEntity(â˜ƒ);
            this.conditionMet = â˜ƒx instanceof CommandBlockEntity && ((CommandBlockEntity)â˜ƒx).getCommandBlock().getSuccessCount() > 0;
         } else {
            this.conditionMet = false;
         }
      }

      return this.conditionMet;
   }

   public boolean isSendToClient() {
      return this.sendToClient;
   }

   public void setSendToClient(boolean var1) {
      this.sendToClient = â˜ƒ;
   }

   public CommandBlockEntity.Mode getMode() {
      BlockState â˜ƒ = this.getBlockState();
      if (â˜ƒ.is(Blocks.COMMAND_BLOCK)) {
         return CommandBlockEntity.Mode.REDSTONE;
      } else if (â˜ƒ.is(Blocks.REPEATING_COMMAND_BLOCK)) {
         return CommandBlockEntity.Mode.AUTO;
      } else {
         return â˜ƒ.is(Blocks.CHAIN_COMMAND_BLOCK) ? CommandBlockEntity.Mode.SEQUENCE : CommandBlockEntity.Mode.REDSTONE;
      }
   }

   public boolean isConditional() {
      BlockState â˜ƒ = this.level.getBlockState(this.getBlockPos());
      return â˜ƒ.getBlock() instanceof CommandBlock ? â˜ƒ.getValue(CommandBlock.CONDITIONAL) : false;
   }

   public static enum Mode {
      SEQUENCE,
      AUTO,
      REDSTONE;
   }
}
