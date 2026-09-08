package net.minecraft.world.level.block.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantmentTableBlockEntity extends BlockEntity implements Nameable {
   public int time;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   public float rot;
   public float oRot;
   public float tRot;
   private static final Random RANDOM = new Random();
   private Component name;

   public EnchantmentTableBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.ENCHANTING_TABLE, â˜ƒ, â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (this.hasCustomName()) {
         â˜ƒ.putString("CustomName", Component.Serializer.toJson(this.name));
      }

      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      if (â˜ƒ.contains("CustomName", 8)) {
         this.name = Component.Serializer.fromJson(â˜ƒ.getString("CustomName"));
      }
   }

   public static void bookAnimationTick(Level var0, BlockPos var1, BlockState var2, EnchantmentTableBlockEntity var3) {
      â˜ƒ.oOpen = â˜ƒ.open;
      â˜ƒ.oRot = â˜ƒ.rot;
      Player â˜ƒ = â˜ƒ.getNearestPlayer((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, 3.0, false);
      if (â˜ƒ != null) {
         double â˜ƒx = â˜ƒ.getX() - ((double)â˜ƒ.getX() + 0.5);
         double â˜ƒxx = â˜ƒ.getZ() - ((double)â˜ƒ.getZ() + 0.5);
         â˜ƒ.tRot = (float)Mth.atan2(â˜ƒxx, â˜ƒx);
         â˜ƒ.open += 0.1F;
         if (â˜ƒ.open < 0.5F || RANDOM.nextInt(40) == 0) {
            float â˜ƒxxx = â˜ƒ.flipT;

            do {
               â˜ƒ.flipT += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
            } while(â˜ƒxxx == â˜ƒ.flipT);
         }
      } else {
         â˜ƒ.tRot += 0.02F;
         â˜ƒ.open -= 0.1F;
      }

      while(â˜ƒ.rot >= (float) Math.PI) {
         â˜ƒ.rot -= (float) (Math.PI * 2);
      }

      while(â˜ƒ.rot < (float) -Math.PI) {
         â˜ƒ.rot += (float) (Math.PI * 2);
      }

      while(â˜ƒ.tRot >= (float) Math.PI) {
         â˜ƒ.tRot -= (float) (Math.PI * 2);
      }

      while(â˜ƒ.tRot < (float) -Math.PI) {
         â˜ƒ.tRot += (float) (Math.PI * 2);
      }

      float â˜ƒ = â˜ƒ.tRot - â˜ƒ.rot;

      while(â˜ƒ >= (float) Math.PI) {
         â˜ƒ -= (float) (Math.PI * 2);
      }

      while(â˜ƒ < (float) -Math.PI) {
         â˜ƒ += (float) (Math.PI * 2);
      }

      â˜ƒ.rot += â˜ƒ * 0.4F;
      â˜ƒ.open = Mth.clamp(â˜ƒ.open, 0.0F, 1.0F);
      ++â˜ƒ.time;
      â˜ƒ.oFlip = â˜ƒ.flip;
      float â˜ƒx = (â˜ƒ.flipT - â˜ƒ.flip) * 0.4F;
      float â˜ƒxx = 0.2F;
      â˜ƒx = Mth.clamp(â˜ƒx, -0.2F, 0.2F);
      â˜ƒ.flipA += (â˜ƒx - â˜ƒ.flipA) * 0.9F;
      â˜ƒ.flip += â˜ƒ.flipA;
   }

   @Override
   public Component getName() {
      return (Component)(this.name != null ? this.name : new TranslatableComponent("container.enchant"));
   }

   public void setCustomName(@Nullable Component var1) {
      this.name = â˜ƒ;
   }

   @Nullable
   @Override
   public Component getCustomName() {
      return this.name;
   }
}
