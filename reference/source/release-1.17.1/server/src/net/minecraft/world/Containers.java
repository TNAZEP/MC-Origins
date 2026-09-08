package net.minecraft.world;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Containers {
   private static final Random RANDOM = new Random();

   public static void dropContents(Level var0, BlockPos var1, Container var2) {
      dropContents(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), â˜ƒ);
   }

   public static void dropContents(Level var0, Entity var1, Container var2) {
      dropContents(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ);
   }

   private static void dropContents(Level var0, double var1, double var3, double var5, Container var7) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getContainerSize(); ++â˜ƒ) {
         dropItemStack(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getItem(â˜ƒ));
      }
   }

   public static void dropContents(Level var0, BlockPos var1, NonNullList<ItemStack> var2) {
      â˜ƒ.forEach(var2x -> dropItemStack(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), var2x));
   }

   public static void dropItemStack(Level var0, double var1, double var3, double var5, ItemStack var7) {
      double â˜ƒ = (double)EntityType.ITEM.getWidth();
      double â˜ƒx = 1.0 - â˜ƒ;
      double â˜ƒxx = â˜ƒ / 2.0;
      double â˜ƒxxx = Math.floor(â˜ƒ) + RANDOM.nextDouble() * â˜ƒx + â˜ƒxx;
      double â˜ƒxxxx = Math.floor(â˜ƒ) + RANDOM.nextDouble() * â˜ƒx;
      double â˜ƒxxxxx = Math.floor(â˜ƒ) + RANDOM.nextDouble() * â˜ƒx + â˜ƒxx;

      while(!â˜ƒ.isEmpty()) {
         ItemEntity â˜ƒxxxxxx = new ItemEntity(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ.split(RANDOM.nextInt(21) + 10));
         float â˜ƒxxxxxxx = 0.05F;
         â˜ƒxxxxxx.setDeltaMovement(RANDOM.nextGaussian() * 0.05F, RANDOM.nextGaussian() * 0.05F + 0.2F, RANDOM.nextGaussian() * 0.05F);
         â˜ƒ.addFreshEntity(â˜ƒxxxxxx);
      }
   }
}
