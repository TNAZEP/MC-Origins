package net.minecraft.world.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class DebugStickItem extends Item {
   public DebugStickItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isFoil(ItemStack var1) {
      return true;
   }

   @Override
   public boolean canAttackBlock(BlockState var1, Level var2, BlockPos var3, Player var4) {
      if (!â˜ƒ.isClientSide) {
         this.handleInteraction(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ.getItemInHand(InteractionHand.MAIN_HAND));
      }

      return false;
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Player â˜ƒ = â˜ƒ.getPlayer();
      Level â˜ƒx = â˜ƒ.getLevel();
      if (!â˜ƒx.isClientSide && â˜ƒ != null) {
         BlockPos â˜ƒxx = â˜ƒ.getClickedPos();
         if (!this.handleInteraction(â˜ƒ, â˜ƒx.getBlockState(â˜ƒxx), â˜ƒx, â˜ƒxx, true, â˜ƒ.getItemInHand())) {
            return InteractionResult.FAIL;
         }
      }

      return InteractionResult.sidedSuccess(â˜ƒx.isClientSide);
   }

   private boolean handleInteraction(Player var1, BlockState var2, LevelAccessor var3, BlockPos var4, boolean var5, ItemStack var6) {
      if (!â˜ƒ.canUseGameMasterBlocks()) {
         return false;
      } else {
         Block â˜ƒ = â˜ƒ.getBlock();
         StateDefinition<Block, BlockState> â˜ƒx = â˜ƒ.getStateDefinition();
         Collection<Property<?>> â˜ƒxx = â˜ƒx.getProperties();
         String â˜ƒxxx = Registry.BLOCK.getKey(â˜ƒ).toString();
         if (â˜ƒxx.isEmpty()) {
            message(â˜ƒ, new TranslatableComponent(this.getDescriptionId() + ".empty", â˜ƒxxx));
            return false;
         } else {
            CompoundTag â˜ƒ = â˜ƒ.getOrCreateTagElement("DebugProperty");
            String â˜ƒx = â˜ƒ.getString(â˜ƒxxx);
            Property<?> â˜ƒxx = â˜ƒx.getProperty(â˜ƒx);
            if (â˜ƒ) {
               if (â˜ƒxx == null) {
                  â˜ƒxx = (Property)â˜ƒxx.iterator().next();
               }

               BlockState â˜ƒxxx = cycleState(â˜ƒ, â˜ƒxx, â˜ƒ.isSecondaryUseActive());
               â˜ƒ.setBlock(â˜ƒ, â˜ƒxxx, 18);
               message(â˜ƒ, new TranslatableComponent(this.getDescriptionId() + ".update", â˜ƒxx.getName(), getNameHelper(â˜ƒxxx, â˜ƒxx)));
            } else {
               â˜ƒxx = getRelative(â˜ƒxx, â˜ƒxx, â˜ƒ.isSecondaryUseActive());
               String â˜ƒ = â˜ƒxx.getName();
               â˜ƒ.putString(â˜ƒxxx, â˜ƒ);
               message(â˜ƒ, new TranslatableComponent(this.getDescriptionId() + ".select", â˜ƒ, getNameHelper(â˜ƒ, â˜ƒxx)));
            }

            return true;
         }
      }
   }

   private static <T extends Comparable<T>> BlockState cycleState(BlockState var0, Property<T> var1, boolean var2) {
      return â˜ƒ.setValue(â˜ƒ, getRelative(â˜ƒ.getPossibleValues(), â˜ƒ.getValue(â˜ƒ), â˜ƒ));
   }

   private static <T> T getRelative(Iterable<T> var0, @Nullable T var1, boolean var2) {
      return (T)(â˜ƒ ? Util.findPreviousInIterable(â˜ƒ, â˜ƒ) : Util.findNextInIterable(â˜ƒ, â˜ƒ));
   }

   private static void message(Player var0, Component var1) {
      ((ServerPlayer)â˜ƒ).sendMessage(â˜ƒ, ChatType.GAME_INFO, Util.NIL_UUID);
   }

   private static <T extends Comparable<T>> String getNameHelper(BlockState var0, Property<T> var1) {
      return â˜ƒ.getName(â˜ƒ.getValue(â˜ƒ));
   }
}
