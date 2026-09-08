package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemFood extends Item {
   private final int field_77853_b;
   private final float field_77854_c;
   private final boolean field_77856_bY;
   private boolean field_77852_bZ;
   private boolean field_203175_k;
   private PotionEffect field_77851_ca;
   private float field_77858_cd;

   public ItemFood(int var1, float var2, boolean var3, Item.Properties var4) {
      super(☃);
      this.field_77853_b = ☃;
      this.field_77856_bY = ☃;
      this.field_77854_c = ☃;
   }

   @Override
   public ItemStack func_77654_b(ItemStack var1, World var2, EntityLivingBase var3) {
      if (☃ instanceof EntityPlayer) {
         EntityPlayer ☃ = (EntityPlayer)☃;
         ☃.func_71024_bL().func_151686_a(this, ☃);
         ☃.func_184148_a(
            null,
            ☃.field_70165_t,
            ☃.field_70163_u,
            ☃.field_70161_v,
            SoundEvents.field_187739_dZ,
            SoundCategory.PLAYERS,
            0.5F,
            ☃.field_73012_v.nextFloat() * 0.1F + 0.9F
         );
         this.func_77849_c(☃, ☃, ☃);
         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
         if (☃ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)☃, ☃);
         }
      }

      ☃.func_190918_g(1);
      return ☃;
   }

   protected void func_77849_c(ItemStack var1, World var2, EntityPlayer var3) {
      if (!☃.field_72995_K && this.field_77851_ca != null && ☃.field_73012_v.nextFloat() < this.field_77858_cd) {
         ☃.func_195064_c(new PotionEffect(this.field_77851_ca));
      }
   }

   @Override
   public int func_77626_a(ItemStack var1) {
      return this.field_203175_k ? 16 : 32;
   }

   @Override
   public EnumAction func_77661_b(ItemStack var1) {
      return EnumAction.EAT;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_71043_e(this.field_77852_bZ)) {
         ☃.func_184598_c(☃);
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      } else {
         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      }
   }

   public int func_150905_g(ItemStack var1) {
      return this.field_77853_b;
   }

   public float func_150906_h(ItemStack var1) {
      return this.field_77854_c;
   }

   public boolean func_77845_h() {
      return this.field_77856_bY;
   }

   public ItemFood func_185070_a(PotionEffect var1, float var2) {
      this.field_77851_ca = ☃;
      this.field_77858_cd = ☃;
      return this;
   }

   public ItemFood func_77848_i() {
      this.field_77852_bZ = true;
      return this;
   }

   public ItemFood func_203174_f() {
      this.field_203175_k = true;
      return this;
   }
}
