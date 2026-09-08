package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RecordItem extends Item {
   private static final Map<SoundEvent, RecordItem> BY_NAME = Maps.<SoundEvent, RecordItem>newHashMap();
   private final int analogOutput;
   private final SoundEvent sound;

   protected RecordItem(int var1, SoundEvent var2, Item.Properties var3) {
      super(â˜ƒ);
      this.analogOutput = â˜ƒ;
      this.sound = â˜ƒ;
      BY_NAME.put(this.sound, this);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (â˜ƒxx.is(Blocks.JUKEBOX) && !â˜ƒxx.getValue(JukeboxBlock.HAS_RECORD)) {
         ItemStack â˜ƒxxx = â˜ƒ.getItemInHand();
         if (!â˜ƒ.isClientSide) {
            ((JukeboxBlock)Blocks.JUKEBOX).setRecord(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
            â˜ƒ.levelEvent(null, 1010, â˜ƒx, Item.getId(this));
            â˜ƒxxx.shrink(1);
            Player â˜ƒxxxx = â˜ƒ.getPlayer();
            if (â˜ƒxxxx != null) {
               â˜ƒxxxx.awardStat(Stats.PLAY_RECORD);
            }
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public int getAnalogOutput() {
      return this.analogOutput;
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      â˜ƒ.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
   }

   public MutableComponent getDisplayName() {
      return new TranslatableComponent(this.getDescriptionId() + ".desc");
   }

   @Nullable
   public static RecordItem getBySound(SoundEvent var0) {
      return (RecordItem)BY_NAME.get(â˜ƒ);
   }

   public SoundEvent getSound() {
      return this.sound;
   }
}
