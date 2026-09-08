package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class ScatteredStructurePiece extends StructurePiece {
   protected int field_202581_a;
   protected int field_202582_b;
   protected int field_202583_c;
   protected int field_202584_d = -1;

   public ScatteredStructurePiece() {
   }

   protected ScatteredStructurePiece(Random var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(0);
      this.field_202581_a = ☃;
      this.field_202582_b = ☃;
      this.field_202583_c = ☃;
      this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(☃));
      if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
         this.field_74887_e = new MutableBoundingBox(☃, ☃, ☃, ☃ + ☃ - 1, ☃ + ☃ - 1, ☃ + ☃ - 1);
      } else {
         this.field_74887_e = new MutableBoundingBox(☃, ☃, ☃, ☃ + ☃ - 1, ☃ + ☃ - 1, ☃ + ☃ - 1);
      }
   }

   @Override
   protected void func_143012_a(NBTTagCompound var1) {
      ☃.func_74768_a("Width", this.field_202581_a);
      ☃.func_74768_a("Height", this.field_202582_b);
      ☃.func_74768_a("Depth", this.field_202583_c);
      ☃.func_74768_a("HPos", this.field_202584_d);
   }

   @Override
   protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      this.field_202581_a = ☃.func_74762_e("Width");
      this.field_202582_b = ☃.func_74762_e("Height");
      this.field_202583_c = ☃.func_74762_e("Depth");
      this.field_202584_d = ☃.func_74762_e("HPos");
   }

   protected boolean func_202580_a(IWorld var1, MutableBoundingBox var2, int var3) {
      if (this.field_202584_d >= 0) {
         return true;
      } else {
         int ☃ = 0;
         int ☃x = 0;
         BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();

         for(int ☃xxx = this.field_74887_e.field_78896_c; ☃xxx <= this.field_74887_e.field_78892_f; ++☃xxx) {
            for(int ☃xxxx = this.field_74887_e.field_78897_a; ☃xxxx <= this.field_74887_e.field_78893_d; ++☃xxxx) {
               ☃xx.func_181079_c(☃xxxx, 64, ☃xxx);
               if (☃.func_175898_b(☃xx)) {
                  ☃ += ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ☃xx).func_177956_o();
                  ++☃x;
               }
            }
         }

         if (☃x == 0) {
            return false;
         } else {
            this.field_202584_d = ☃ / ☃x;
            this.field_74887_e.func_78886_a(0, this.field_202584_d - this.field_74887_e.field_78895_b + ☃, 0);
            return true;
         }
      }
   }
}
