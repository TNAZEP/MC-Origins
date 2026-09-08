package net.minecraft.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHoe extends ItemTiered {
   private final float field_185072_b;
   protected static final Map<Block, IBlockState> field_195973_b = Maps.<Block, IBlockState>newHashMap(
      ImmutableMap.of(
         Blocks.field_196658_i,
         Blocks.field_150458_ak.func_176223_P(),
         Blocks.field_185774_da,
         Blocks.field_150458_ak.func_176223_P(),
         Blocks.field_150346_d,
         Blocks.field_150458_ak.func_176223_P(),
         Blocks.field_196660_k,
         Blocks.field_150346_d.func_176223_P()
      )
   );

   public ItemHoe(IItemTier var1, float var2, Item.Properties var3) {
      super(☃, ☃);
      this.field_185072_b = ☃;
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      if (☃.func_196000_l() != EnumFacing.DOWN && ☃.func_180495_p(☃x.func_177984_a()).func_196958_f()) {
         IBlockState ☃xx = (IBlockState)field_195973_b.get(☃.func_180495_p(☃x).func_177230_c());
         if (☃xx != null) {
            EntityPlayer ☃xxx = ☃.func_195999_j();
            ☃.func_184133_a(☃xxx, ☃x, SoundEvents.field_187693_cj, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!☃.field_72995_K) {
               ☃.func_180501_a(☃x, ☃xx, 11);
               if (☃xxx != null) {
                  ☃.func_195996_i().func_77972_a(1, ☃xxx);
               }
            }

            return EnumActionResult.SUCCESS;
         }
      }

      return EnumActionResult.PASS;
   }

   @Override
   public boolean func_77644_a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      ☃.func_77972_a(1, ☃);
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot var1) {
      Multimap<String, AttributeModifier> ☃ = super.func_111205_h(☃);
      if (☃ == EntityEquipmentSlot.MAINHAND) {
         ☃.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", 0.0, 0));
         ☃.put(SharedMonsterAttributes.field_188790_f.func_111108_a(), new AttributeModifier(field_185050_h, "Weapon modifier", (double)this.field_185072_b, 0));
      }

      return ☃;
   }
}
