package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireworkRocketItem extends Item {
   public static final String TAG_FIREWORKS = "Fireworks";
   public static final String TAG_EXPLOSION = "Explosion";
   public static final String TAG_EXPLOSIONS = "Explosions";
   public static final String TAG_FLIGHT = "Flight";
   public static final String TAG_EXPLOSION_TYPE = "Type";
   public static final String TAG_EXPLOSION_TRAIL = "Trail";
   public static final String TAG_EXPLOSION_FLICKER = "Flicker";
   public static final String TAG_EXPLOSION_COLORS = "Colors";
   public static final String TAG_EXPLOSION_FADECOLORS = "FadeColors";
   public static final double ROCKET_PLACEMENT_OFFSET = 0.15;

   public FireworkRocketItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      if (!â˜ƒ.isClientSide) {
         ItemStack â˜ƒx = â˜ƒ.getItemInHand();
         Vec3 â˜ƒxx = â˜ƒ.getClickLocation();
         Direction â˜ƒxxx = â˜ƒ.getClickedFace();
         FireworkRocketEntity â˜ƒxxxx = new FireworkRocketEntity(
            â˜ƒ,
            â˜ƒ.getPlayer(),
            â˜ƒxx.x + (double)â˜ƒxxx.getStepX() * 0.15,
            â˜ƒxx.y + (double)â˜ƒxxx.getStepY() * 0.15,
            â˜ƒxx.z + (double)â˜ƒxxx.getStepZ() * 0.15,
            â˜ƒx
         );
         â˜ƒ.addFreshEntity(â˜ƒxxxx);
         â˜ƒx.shrink(1);
      }

      return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      if (â˜ƒ.isFallFlying()) {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
         if (!â˜ƒ.isClientSide) {
            FireworkRocketEntity â˜ƒx = new FireworkRocketEntity(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.addFreshEntity(â˜ƒx);
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒ.shrink(1);
            }

            â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         }

         return InteractionResultHolder.sidedSuccess(â˜ƒ.getItemInHand(â˜ƒ), â˜ƒ.isClientSide());
      } else {
         return InteractionResultHolder.pass(â˜ƒ.getItemInHand(â˜ƒ));
      }
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("Fireworks");
      if (â˜ƒ != null) {
         if (â˜ƒ.contains("Flight", 99)) {
            â˜ƒ.add(
               new TranslatableComponent("item.minecraft.firework_rocket.flight")
                  .append(" ")
                  .append(String.valueOf(â˜ƒ.getByte("Flight")))
                  .withStyle(ChatFormatting.GRAY)
            );
         }

         ListTag â˜ƒx = â˜ƒ.getList("Explosions", 10);
         if (!â˜ƒx.isEmpty()) {
            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
               CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
               List<Component> â˜ƒxxxx = Lists.<Component>newArrayList();
               FireworkStarItem.appendHoverText(â˜ƒxxx, â˜ƒxxxx);
               if (!â˜ƒxxxx.isEmpty()) {
                  for(int â˜ƒxxxxx = 1; â˜ƒxxxxx < â˜ƒxxxx.size(); ++â˜ƒxxxxx) {
                     â˜ƒxxxx.set(â˜ƒxxxxx, new TextComponent("  ").append((Component)â˜ƒxxxx.get(â˜ƒxxxxx)).withStyle(ChatFormatting.GRAY));
                  }

                  â˜ƒ.addAll(â˜ƒxxxx);
               }
            }
         }
      }
   }

   @Override
   public ItemStack getDefaultInstance() {
      ItemStack â˜ƒ = new ItemStack(this);
      â˜ƒ.getOrCreateTag().putByte("Flight", (byte)1);
      return â˜ƒ;
   }

   public static enum Shape {
      SMALL_BALL(0, "small_ball"),
      LARGE_BALL(1, "large_ball"),
      STAR(2, "star"),
      CREEPER(3, "creeper"),
      BURST(4, "burst");

      private static final FireworkRocketItem.Shape[] BY_ID = (FireworkRocketItem.Shape[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(var0 -> var0.id))
         .toArray(var0 -> new FireworkRocketItem.Shape[var0]);
      private final int id;
      private final String name;

      private Shape(int var3, String var4) {
         this.id = â˜ƒ;
         this.name = â˜ƒ;
      }

      public int getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public static FireworkRocketItem.Shape byId(int var0) {
         return â˜ƒ >= 0 && â˜ƒ < BY_ID.length ? BY_ID[â˜ƒ] : SMALL_BALL;
      }
   }
}
