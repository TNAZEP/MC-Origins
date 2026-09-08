package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {
   private final EntityOcelot field_151493_a;

   public EntityAIOcelotSit(EntityOcelot var1, double var2) {
      super(☃, ☃, 8);
      this.field_151493_a = ☃;
   }

   @Override
   public boolean func_75250_a() {
      return this.field_151493_a.func_70909_n() && !this.field_151493_a.func_70906_o() && super.func_75250_a();
   }

   @Override
   public void func_75249_e() {
      super.func_75249_e();
      this.field_151493_a.func_70907_r().func_75270_a(false);
   }

   @Override
   public void func_75251_c() {
      super.func_75251_c();
      this.field_151493_a.func_70904_g(false);
   }

   @Override
   public void func_75246_d() {
      super.func_75246_d();
      this.field_151493_a.func_70907_r().func_75270_a(false);
      if (!this.func_179487_f()) {
         this.field_151493_a.func_70904_g(false);
      } else if (!this.field_151493_a.func_70906_o()) {
         this.field_151493_a.func_70904_g(true);
      }
   }

   @Override
   protected boolean func_179488_a(IWorldReaderBase var1, BlockPos var2) {
      if (!☃.func_175623_d(☃.func_177984_a())) {
         return false;
      } else {
         IBlockState ☃ = ☃.func_180495_p(☃);
         Block ☃x = ☃.func_177230_c();
         if (☃x == Blocks.field_150486_ae) {
            return TileEntityChest.func_195481_a(☃, ☃) < 1;
         } else if (☃x == Blocks.field_150460_al && ☃.func_177229_b(BlockFurnace.field_196325_b)) {
            return true;
         } else {
            return ☃x instanceof BlockBed && ☃.func_177229_b(BlockBed.field_176472_a) != BedPart.HEAD;
         }
      }
   }
}
