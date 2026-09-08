package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandBlock extends BaseEntityBlock implements GameMasterBlock {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final DirectionProperty FACING = DirectionalBlock.FACING;
   public static final BooleanProperty CONDITIONAL = BlockStateProperties.CONDITIONAL;
   private final boolean automatic;

   public CommandBlock(BlockBehaviour.Properties var1, boolean var2) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CONDITIONAL, Boolean.valueOf(false)));
      this.automatic = â˜ƒ;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      CommandBlockEntity â˜ƒ = new CommandBlockEntity(â˜ƒ, â˜ƒ);
      â˜ƒ.setAutomatic(this.automatic);
      return â˜ƒ;
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof CommandBlockEntity) {
            CommandBlockEntity â˜ƒx = (CommandBlockEntity)â˜ƒ;
            boolean â˜ƒxx = â˜ƒ.hasNeighborSignal(â˜ƒ);
            boolean â˜ƒxxx = â˜ƒx.isPowered();
            â˜ƒx.setPowered(â˜ƒxx);
            if (!â˜ƒxxx && !â˜ƒx.isAutomatic() && â˜ƒx.getMode() != CommandBlockEntity.Mode.SEQUENCE) {
               if (â˜ƒxx) {
                  â˜ƒx.markConditionMet();
                  â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
               }
            }
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof CommandBlockEntity â˜ƒ) {
         BaseCommandBlock â˜ƒxx = â˜ƒ.getCommandBlock();
         boolean â˜ƒxxx = !StringUtil.isNullOrEmpty(â˜ƒxx.getCommand());
         CommandBlockEntity.Mode â˜ƒxxxx = â˜ƒ.getMode();
         boolean â˜ƒxxxxx = â˜ƒ.wasConditionMet();
         if (â˜ƒxxxx == CommandBlockEntity.Mode.AUTO) {
            â˜ƒ.markConditionMet();
            if (â˜ƒxxxxx) {
               this.execute(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx);
            } else if (â˜ƒ.isConditional()) {
               â˜ƒxx.setSuccessCount(0);
            }

            if (â˜ƒ.isPowered() || â˜ƒ.isAutomatic()) {
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
            }
         } else if (â˜ƒxxxx == CommandBlockEntity.Mode.REDSTONE) {
            if (â˜ƒxxxxx) {
               this.execute(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx);
            } else if (â˜ƒ.isConditional()) {
               â˜ƒxx.setSuccessCount(0);
            }
         }

         â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, this);
      }
   }

   private void execute(BlockState var1, Level var2, BlockPos var3, BaseCommandBlock var4, boolean var5) {
      if (â˜ƒ) {
         â˜ƒ.performCommand(â˜ƒ);
      } else {
         â˜ƒ.setSuccessCount(0);
      }

      executeChain(â˜ƒ, â˜ƒ, â˜ƒ.getValue(FACING));
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof CommandBlockEntity && â˜ƒ.canUseGameMasterBlocks()) {
         â˜ƒ.openCommandBlock((CommandBlockEntity)â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ instanceof CommandBlockEntity ? ((CommandBlockEntity)â˜ƒ).getCommandBlock().getSuccessCount() : 0;
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof CommandBlockEntity) {
         CommandBlockEntity â˜ƒx = (CommandBlockEntity)â˜ƒ;
         BaseCommandBlock â˜ƒxx = â˜ƒx.getCommandBlock();
         if (â˜ƒ.hasCustomHoverName()) {
            â˜ƒxx.setName(â˜ƒ.getHoverName());
         }

         if (!â˜ƒ.isClientSide) {
            if (â˜ƒ.getTagElement("BlockEntityTag") == null) {
               â˜ƒxx.setTrackOutput(â˜ƒ.getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK));
               â˜ƒx.setAutomatic(this.automatic);
            }

            if (â˜ƒx.getMode() == CommandBlockEntity.Mode.SEQUENCE) {
               boolean â˜ƒx = â˜ƒ.hasNeighborSignal(â˜ƒ);
               â˜ƒx.setPowered(â˜ƒx);
            }
         }
      }
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, CONDITIONAL);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getNearestLookingDirection().getOpposite());
   }

   private static void executeChain(Level var0, BlockPos var1, Direction var2) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      GameRules â˜ƒx = â˜ƒ.getGameRules();

      int â˜ƒ;
      BlockState â˜ƒ;
      for(â˜ƒ = â˜ƒx.getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH); â˜ƒ-- > 0; â˜ƒ = â˜ƒ.getValue(FACING)) {
         â˜ƒ.move(â˜ƒ);
         â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         Block â˜ƒxx = â˜ƒ.getBlock();
         if (!â˜ƒ.is(Blocks.CHAIN_COMMAND_BLOCK)) {
            break;
         }

         BlockEntity â˜ƒxx = â˜ƒ.getBlockEntity(â˜ƒ);
         if (!(â˜ƒxx instanceof CommandBlockEntity)) {
            break;
         }

         CommandBlockEntity â˜ƒxx = (CommandBlockEntity)â˜ƒxx;
         if (â˜ƒxx.getMode() != CommandBlockEntity.Mode.SEQUENCE) {
            break;
         }

         if (â˜ƒxx.isPowered() || â˜ƒxx.isAutomatic()) {
            BaseCommandBlock â˜ƒxx = â˜ƒxx.getCommandBlock();
            if (â˜ƒxx.markConditionMet()) {
               if (!â˜ƒxx.performCommand(â˜ƒ)) {
                  break;
               }

               â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, â˜ƒxx);
            } else if (â˜ƒxx.isConditional()) {
               â˜ƒxx.setSuccessCount(0);
            }
         }
      }

      if (â˜ƒ <= 0) {
         int â˜ƒxx = Math.max(â˜ƒx.getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH), 0);
         LOGGER.warn("Command Block chain tried to execute more than {} steps!", â˜ƒxx);
      }
   }
}
