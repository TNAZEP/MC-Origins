package net.minecraft.server.management;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DemoPlayerInteractionManager extends PlayerInteractionManager {
   private boolean field_73105_c;
   private boolean field_73103_d;
   private int field_73104_e;
   private int field_73102_f;

   public DemoPlayerInteractionManager(World var1) {
      super(☃);
   }

   @Override
   public void func_73075_a() {
      super.func_73075_a();
      ++this.field_73102_f;
      long ☃ = this.field_73092_a.func_82737_E();
      long ☃x = ☃ / 24000L + 1L;
      if (!this.field_73105_c && this.field_73102_f > 20) {
         this.field_73105_c = true;
         this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 0.0F));
      }

      this.field_73103_d = ☃ > 120500L;
      if (this.field_73103_d) {
         ++this.field_73104_e;
      }

      if (☃ % 24000L == 500L) {
         if (☃x <= 6L) {
            if (☃x == 6L) {
               this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 104.0F));
            } else {
               this.field_73090_b.func_145747_a(new TextComponentTranslation("demo.day." + ☃x));
            }
         }
      } else if (☃x == 1L) {
         if (☃ == 100L) {
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 101.0F));
         } else if (☃ == 175L) {
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 102.0F));
         } else if (☃ == 250L) {
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 103.0F));
         }
      } else if (☃x == 5L && ☃ % 24000L == 22000L) {
         this.field_73090_b.func_145747_a(new TextComponentTranslation("demo.day.warning"));
      }
   }

   private void func_73101_e() {
      if (this.field_73104_e > 100) {
         this.field_73090_b.func_145747_a(new TextComponentTranslation("demo.reminder"));
         this.field_73104_e = 0;
      }
   }

   @Override
   public void func_180784_a(BlockPos var1, EnumFacing var2) {
      if (this.field_73103_d) {
         this.func_73101_e();
      } else {
         super.func_180784_a(☃, ☃);
      }
   }

   @Override
   public void func_180785_a(BlockPos var1) {
      if (!this.field_73103_d) {
         super.func_180785_a(☃);
      }
   }

   @Override
   public boolean func_180237_b(BlockPos var1) {
      return this.field_73103_d ? false : super.func_180237_b(☃);
   }

   @Override
   public EnumActionResult func_187250_a(EntityPlayer var1, World var2, ItemStack var3, EnumHand var4) {
      if (this.field_73103_d) {
         this.func_73101_e();
         return EnumActionResult.PASS;
      } else {
         return super.func_187250_a(☃, ☃, ☃, ☃);
      }
   }

   @Override
   public EnumActionResult func_187251_a(
      EntityPlayer var1, World var2, ItemStack var3, EnumHand var4, BlockPos var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (this.field_73103_d) {
         this.func_73101_e();
         return EnumActionResult.PASS;
      } else {
         return super.func_187251_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
