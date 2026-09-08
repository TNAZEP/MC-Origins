package net.minecraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SuspiciousStewItem extends Item {
   public static final String EFFECTS_TAG = "Effects";
   public static final String EFFECT_ID_TAG = "EffectId";
   public static final String EFFECT_DURATION_TAG = "EffectDuration";

   public SuspiciousStewItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public static void saveMobEffect(ItemStack var0, MobEffect var1, int var2) {
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      ListTag â˜ƒx = â˜ƒ.getList("Effects", 9);
      CompoundTag â˜ƒxx = new CompoundTag();
      â˜ƒxx.putByte("EffectId", (byte)MobEffect.getId(â˜ƒ));
      â˜ƒxx.putInt("EffectDuration", â˜ƒ);
      â˜ƒx.add(â˜ƒxx);
      â˜ƒ.put("Effects", â˜ƒx);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      ItemStack â˜ƒ = super.finishUsingItem(â˜ƒ, â˜ƒ, â˜ƒ);
      CompoundTag â˜ƒx = â˜ƒ.getTag();
      if (â˜ƒx != null && â˜ƒx.contains("Effects", 9)) {
         ListTag â˜ƒxx = â˜ƒx.getList("Effects", 10);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
            int â˜ƒxxxx = 160;
            CompoundTag â˜ƒxxxxx = â˜ƒxx.getCompound(â˜ƒxxx);
            if (â˜ƒxxxxx.contains("EffectDuration", 3)) {
               â˜ƒxxxx = â˜ƒxxxxx.getInt("EffectDuration");
            }

            MobEffect â˜ƒxxxx = MobEffect.byId(â˜ƒxxxxx.getByte("EffectId"));
            if (â˜ƒxxxx != null) {
               â˜ƒ.addEffect(new MobEffectInstance(â˜ƒxxxx, â˜ƒxxxx));
            }
         }
      }

      return â˜ƒ instanceof Player && ((Player)â˜ƒ).getAbilities().instabuild ? â˜ƒ : new ItemStack(Items.BOWL);
   }
}
