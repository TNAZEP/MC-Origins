package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class PotionUtils {
   public static final String TAG_CUSTOM_POTION_EFFECTS = "CustomPotionEffects";
   public static final String TAG_CUSTOM_POTION_COLOR = "CustomPotionColor";
   public static final String TAG_POTION = "Potion";
   private static final int EMPTY_COLOR = 16253176;
   private static final Component NO_EFFECT = new TranslatableComponent("effect.none").withStyle(ChatFormatting.GRAY);

   public static List<MobEffectInstance> getMobEffects(ItemStack var0) {
      return getAllEffects(â˜ƒ.getTag());
   }

   public static List<MobEffectInstance> getAllEffects(Potion var0, Collection<MobEffectInstance> var1) {
      List<MobEffectInstance> â˜ƒ = Lists.<MobEffectInstance>newArrayList();
      â˜ƒ.addAll(â˜ƒ.getEffects());
      â˜ƒ.addAll(â˜ƒ);
      return â˜ƒ;
   }

   public static List<MobEffectInstance> getAllEffects(@Nullable CompoundTag var0) {
      List<MobEffectInstance> â˜ƒ = Lists.<MobEffectInstance>newArrayList();
      â˜ƒ.addAll(getPotion(â˜ƒ).getEffects());
      getCustomEffects(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static List<MobEffectInstance> getCustomEffects(ItemStack var0) {
      return getCustomEffects(â˜ƒ.getTag());
   }

   public static List<MobEffectInstance> getCustomEffects(@Nullable CompoundTag var0) {
      List<MobEffectInstance> â˜ƒ = Lists.<MobEffectInstance>newArrayList();
      getCustomEffects(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static void getCustomEffects(@Nullable CompoundTag var0, List<MobEffectInstance> var1) {
      if (â˜ƒ != null && â˜ƒ.contains("CustomPotionEffects", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("CustomPotionEffects", 10);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
            MobEffectInstance â˜ƒxxx = MobEffectInstance.load(â˜ƒxx);
            if (â˜ƒxxx != null) {
               â˜ƒ.add(â˜ƒxxx);
            }
         }
      }
   }

   public static int getColor(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null && â˜ƒ.contains("CustomPotionColor", 99)) {
         return â˜ƒ.getInt("CustomPotionColor");
      } else {
         return getPotion(â˜ƒ) == Potions.EMPTY ? 16253176 : getColor(getMobEffects(â˜ƒ));
      }
   }

   public static int getColor(Potion var0) {
      return â˜ƒ == Potions.EMPTY ? 16253176 : getColor(â˜ƒ.getEffects());
   }

   public static int getColor(Collection<MobEffectInstance> var0) {
      int â˜ƒ = 3694022;
      if (â˜ƒ.isEmpty()) {
         return 3694022;
      } else {
         float â˜ƒ = 0.0F;
         float â˜ƒx = 0.0F;
         float â˜ƒxx = 0.0F;
         int â˜ƒxxx = 0;

         for(MobEffectInstance â˜ƒxxxx : â˜ƒ) {
            if (â˜ƒxxxx.isVisible()) {
               int â˜ƒxxxxx = â˜ƒxxxx.getEffect().getColor();
               int â˜ƒxxxxxx = â˜ƒxxxx.getAmplifier() + 1;
               â˜ƒ += (float)(â˜ƒxxxxxx * (â˜ƒxxxxx >> 16 & 0xFF)) / 255.0F;
               â˜ƒx += (float)(â˜ƒxxxxxx * (â˜ƒxxxxx >> 8 & 0xFF)) / 255.0F;
               â˜ƒxx += (float)(â˜ƒxxxxxx * (â˜ƒxxxxx >> 0 & 0xFF)) / 255.0F;
               â˜ƒxxx += â˜ƒxxxxxx;
            }
         }

         if (â˜ƒxxx == 0) {
            return 0;
         } else {
            â˜ƒ = â˜ƒ / (float)â˜ƒxxx * 255.0F;
            â˜ƒx = â˜ƒx / (float)â˜ƒxxx * 255.0F;
            â˜ƒxx = â˜ƒxx / (float)â˜ƒxxx * 255.0F;
            return (int)â˜ƒ << 16 | (int)â˜ƒx << 8 | (int)â˜ƒxx;
         }
      }
   }

   public static Potion getPotion(ItemStack var0) {
      return getPotion(â˜ƒ.getTag());
   }

   public static Potion getPotion(@Nullable CompoundTag var0) {
      return â˜ƒ == null ? Potions.EMPTY : Potion.byName(â˜ƒ.getString("Potion"));
   }

   public static ItemStack setPotion(ItemStack var0, Potion var1) {
      ResourceLocation â˜ƒ = Registry.POTION.getKey(â˜ƒ);
      if (â˜ƒ == Potions.EMPTY) {
         â˜ƒ.removeTagKey("Potion");
      } else {
         â˜ƒ.getOrCreateTag().putString("Potion", â˜ƒ.toString());
      }

      return â˜ƒ;
   }

   public static ItemStack setCustomEffects(ItemStack var0, Collection<MobEffectInstance> var1) {
      if (â˜ƒ.isEmpty()) {
         return â˜ƒ;
      } else {
         CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
         ListTag â˜ƒx = â˜ƒ.getList("CustomPotionEffects", 9);

         for(MobEffectInstance â˜ƒxx : â˜ƒ) {
            â˜ƒx.add(â˜ƒxx.save(new CompoundTag()));
         }

         â˜ƒ.put("CustomPotionEffects", â˜ƒx);
         return â˜ƒ;
      }
   }

   public static void addPotionTooltip(ItemStack var0, List<Component> var1, float var2) {
      List<MobEffectInstance> â˜ƒ = getMobEffects(â˜ƒ);
      List<Pair<Attribute, AttributeModifier>> â˜ƒx = Lists.<Pair<Attribute, AttributeModifier>>newArrayList();
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.add(NO_EFFECT);
      } else {
         for(MobEffectInstance â˜ƒ : â˜ƒ) {
            MutableComponent â˜ƒx = new TranslatableComponent(â˜ƒ.getDescriptionId());
            MobEffect â˜ƒxx = â˜ƒ.getEffect();
            Map<Attribute, AttributeModifier> â˜ƒxxx = â˜ƒxx.getAttributeModifiers();
            if (!â˜ƒxxx.isEmpty()) {
               for(Entry<Attribute, AttributeModifier> â˜ƒxxxx : â˜ƒxxx.entrySet()) {
                  AttributeModifier â˜ƒxxxxx = (AttributeModifier)â˜ƒxxxx.getValue();
                  AttributeModifier â˜ƒxxxxxx = new AttributeModifier(
                     â˜ƒxxxxx.getName(), â˜ƒxx.getAttributeModifierValue(â˜ƒ.getAmplifier(), â˜ƒxxxxx), â˜ƒxxxxx.getOperation()
                  );
                  â˜ƒx.add(new Pair<>((Attribute)â˜ƒxxxx.getKey(), â˜ƒxxxxxx));
               }
            }

            if (â˜ƒ.getAmplifier() > 0) {
               â˜ƒx = new TranslatableComponent("potion.withAmplifier", â˜ƒx, new TranslatableComponent("potion.potency." + â˜ƒ.getAmplifier()));
            }

            if (â˜ƒ.getDuration() > 20) {
               â˜ƒx = new TranslatableComponent("potion.withDuration", â˜ƒx, MobEffectUtil.formatDuration(â˜ƒ, â˜ƒ));
            }

            â˜ƒ.add(â˜ƒx.withStyle(â˜ƒxx.getCategory().getTooltipFormatting()));
         }
      }

      if (!â˜ƒx.isEmpty()) {
         â˜ƒ.add(TextComponent.EMPTY);
         â˜ƒ.add(new TranslatableComponent("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

         for(Pair<Attribute, AttributeModifier> â˜ƒ : â˜ƒx) {
            AttributeModifier â˜ƒxx = â˜ƒ.getSecond();
            double â˜ƒxxx = â˜ƒxx.getAmount();
            double â˜ƒx;
            if (â˜ƒxx.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && â˜ƒxx.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
               â˜ƒx = â˜ƒxx.getAmount();
            } else {
               â˜ƒx = â˜ƒxx.getAmount() * 100.0;
            }

            if (â˜ƒxxx > 0.0) {
               â˜ƒ.add(
                  new TranslatableComponent(
                        "attribute.modifier.plus." + â˜ƒxx.getOperation().toValue(),
                        ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(â˜ƒx),
                        new TranslatableComponent(â˜ƒ.getFirst().getDescriptionId())
                     )
                     .withStyle(ChatFormatting.BLUE)
               );
            } else if (â˜ƒxxx < 0.0) {
               â˜ƒx *= -1.0;
               â˜ƒ.add(
                  new TranslatableComponent(
                        "attribute.modifier.take." + â˜ƒxx.getOperation().toValue(),
                        ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(â˜ƒx),
                        new TranslatableComponent(â˜ƒ.getFirst().getDescriptionId())
                     )
                     .withStyle(ChatFormatting.RED)
               );
            }
         }
      }
   }
}
