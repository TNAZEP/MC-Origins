package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

public class MobBucketItem extends BucketItem {
   private final EntityType<?> type;
   private final SoundEvent emptySound;

   public MobBucketItem(EntityType<?> var1, Fluid var2, SoundEvent var3, Item.Properties var4) {
      super(â˜ƒ, â˜ƒ);
      this.type = â˜ƒ;
      this.emptySound = â˜ƒ;
   }

   @Override
   public void checkExtraContent(@Nullable Player var1, Level var2, ItemStack var3, BlockPos var4) {
      if (â˜ƒ instanceof ServerLevel) {
         this.spawn((ServerLevel)â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.ENTITY_PLACE, â˜ƒ);
      }
   }

   @Override
   protected void playEmptySound(@Nullable Player var1, LevelAccessor var2, BlockPos var3) {
      â˜ƒ.playSound(â˜ƒ, â˜ƒ, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
   }

   private void spawn(ServerLevel var1, ItemStack var2, BlockPos var3) {
      Entity â˜ƒx = this.type.spawn(â˜ƒ, â˜ƒ, null, â˜ƒ, MobSpawnType.BUCKET, true, false);
      if (â˜ƒx instanceof Bucketable â˜ƒ) {
         â˜ƒ.loadFromBucketTag(â˜ƒ.getOrCreateTag());
         â˜ƒ.setFromBucket(true);
      }
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      if (this.type == EntityType.TROPICAL_FISH) {
         CompoundTag â˜ƒ = â˜ƒ.getTag();
         if (â˜ƒ != null && â˜ƒ.contains("BucketVariantTag", 3)) {
            int â˜ƒx = â˜ƒ.getInt("BucketVariantTag");
            ChatFormatting[] â˜ƒxx = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
            String â˜ƒxxx = "color.minecraft." + TropicalFish.getBaseColor(â˜ƒx);
            String â˜ƒxxxx = "color.minecraft." + TropicalFish.getPatternColor(â˜ƒx);

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < TropicalFish.COMMON_VARIANTS.length; ++â˜ƒxxxxx) {
               if (â˜ƒx == TropicalFish.COMMON_VARIANTS[â˜ƒxxxxx]) {
                  â˜ƒ.add(new TranslatableComponent(TropicalFish.getPredefinedName(â˜ƒxxxxx)).withStyle(â˜ƒxx));
                  return;
               }
            }

            â˜ƒ.add(new TranslatableComponent(TropicalFish.getFishTypeName(â˜ƒx)).withStyle(â˜ƒxx));
            MutableComponent â˜ƒxxxxx = new TranslatableComponent(â˜ƒxxx);
            if (!â˜ƒxxx.equals(â˜ƒxxxx)) {
               â˜ƒxxxxx.append(", ").append(new TranslatableComponent(â˜ƒxxxx));
            }

            â˜ƒxxxxx.withStyle(â˜ƒxx);
            â˜ƒ.add(â˜ƒxxxxx);
         }
      }
   }
}
