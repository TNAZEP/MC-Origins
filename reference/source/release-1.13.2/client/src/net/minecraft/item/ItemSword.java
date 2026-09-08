package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSword extends ItemTiered {
   private final float field_150934_a;
   private final float field_200895_b;

   public ItemSword(IItemTier var1, int var2, float var3, Item.Properties var4) {
      super(☃, ☃);
      this.field_200895_b = ☃;
      this.field_150934_a = (float)☃ + ☃.func_200929_c();
   }

   public float func_200894_d() {
      return this.field_150934_a;
   }

   @Override
   public boolean func_195938_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      return !☃.func_184812_l_();
   }

   @Override
   public float func_150893_a(ItemStack var1, IBlockState var2) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == Blocks.field_196553_aF) {
         return 15.0F;
      } else {
         Material ☃ = ☃.func_185904_a();
         return ☃ != Material.field_151585_k
               && ☃ != Material.field_151582_l
               && ☃ != Material.field_151589_v
               && !☃.func_203425_a(BlockTags.field_206952_E)
               && ☃ != Material.field_151572_C
            ? 1.0F
            : 1.5F;
      }
   }

   @Override
   public boolean func_77644_a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      ☃.func_77972_a(1, ☃);
      return true;
   }

   @Override
   public boolean func_179218_a(ItemStack var1, World var2, IBlockState var3, BlockPos var4, EntityLivingBase var5) {
      if (☃.func_185887_b(☃, ☃) != 0.0F) {
         ☃.func_77972_a(2, ☃);
      }

      return true;
   }

   @Override
   public boolean func_150897_b(IBlockState var1) {
      return ☃.func_177230_c() == Blocks.field_196553_aF;
   }

   @Override
   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot var1) {
      Multimap<String, AttributeModifier> ☃ = super.func_111205_h(☃);
      if (☃ == EntityEquipmentSlot.MAINHAND) {
         ☃.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.field_150934_a, 0));
         ☃.put(SharedMonsterAttributes.field_188790_f.func_111108_a(), new AttributeModifier(field_185050_h, "Weapon modifier", (double)this.field_200895_b, 0));
      }

      return ☃;
   }
}
