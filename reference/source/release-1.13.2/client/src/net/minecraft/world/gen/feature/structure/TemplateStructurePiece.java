package net.minecraft.world.gen.feature.structure;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class TemplateStructurePiece extends StructurePiece {
   private static final PlacementSettings field_186179_d = new PlacementSettings();
   protected Template field_186176_a;
   protected PlacementSettings field_186177_b = field_186179_d.func_186222_a(true).func_186225_a(Blocks.field_150350_a);
   protected BlockPos field_186178_c;

   public TemplateStructurePiece() {
   }

   public TemplateStructurePiece(int var1) {
      super(☃);
   }

   protected void func_186173_a(Template var1, BlockPos var2, PlacementSettings var3) {
      this.field_186176_a = ☃;
      this.func_186164_a(EnumFacing.NORTH);
      this.field_186178_c = ☃;
      this.field_186177_b = ☃;
      this.func_186174_h();
   }

   @Override
   protected void func_143012_a(NBTTagCompound var1) {
      ☃.func_74768_a("TPX", this.field_186178_c.func_177958_n());
      ☃.func_74768_a("TPY", this.field_186178_c.func_177956_o());
      ☃.func_74768_a("TPZ", this.field_186178_c.func_177952_p());
   }

   @Override
   protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      this.field_186178_c = new BlockPos(☃.func_74762_e("TPX"), ☃.func_74762_e("TPY"), ☃.func_74762_e("TPZ"));
   }

   @Override
   public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
      this.field_186177_b.func_186223_a(☃);
      if (this.field_186176_a.func_189962_a(☃, this.field_186178_c, this.field_186177_b, 2)) {
         Map<BlockPos, String> ☃ = this.field_186176_a.func_186258_a(this.field_186178_c, this.field_186177_b);

         for(Entry<BlockPos, String> ☃x : ☃.entrySet()) {
            String ☃xx = (String)☃x.getValue();
            this.func_186175_a(☃xx, (BlockPos)☃x.getKey(), ☃, ☃, ☃);
         }
      }

      return true;
   }

   protected abstract void func_186175_a(String var1, BlockPos var2, IWorld var3, Random var4, MutableBoundingBox var5);

   private void func_186174_h() {
      Rotation ☃ = this.field_186177_b.func_186215_c();
      BlockPos ☃x = this.field_186177_b.func_207664_d();
      BlockPos ☃xx = this.field_186176_a.func_186257_a(☃);
      Mirror ☃xxx = this.field_186177_b.func_186212_b();
      int ☃xxxx = ☃x.func_177958_n();
      int ☃xxxxx = ☃x.func_177952_p();
      int ☃xxxxxx = ☃xx.func_177958_n() - 1;
      int ☃xxxxxxx = ☃xx.func_177956_o() - 1;
      int ☃xxxxxxxx = ☃xx.func_177952_p() - 1;
      switch(☃) {
         case NONE:
            this.field_74887_e = new MutableBoundingBox(0, 0, 0, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
            break;
         case CLOCKWISE_180:
            this.field_74887_e = new MutableBoundingBox(☃xxxx + ☃xxxx - ☃xxxxxx, 0, ☃xxxxx + ☃xxxxx - ☃xxxxxxxx, ☃xxxx + ☃xxxx, ☃xxxxxxx, ☃xxxxx + ☃xxxxx);
            break;
         case COUNTERCLOCKWISE_90:
            this.field_74887_e = new MutableBoundingBox(☃xxxx - ☃xxxxx, 0, ☃xxxx + ☃xxxxx - ☃xxxxxxxx, ☃xxxx - ☃xxxxx + ☃xxxxxx, ☃xxxxxxx, ☃xxxx + ☃xxxxx);
            break;
         case CLOCKWISE_90:
            this.field_74887_e = new MutableBoundingBox(☃xxxx + ☃xxxxx - ☃xxxxxx, 0, ☃xxxxx - ☃xxxx, ☃xxxx + ☃xxxxx, ☃xxxxxxx, ☃xxxxx - ☃xxxx + ☃xxxxxxxx);
      }

      switch(☃xxx) {
         case NONE:
         default:
            break;
         case FRONT_BACK:
            BlockPos ☃ = BlockPos.field_177992_a;
            if (☃ == Rotation.CLOCKWISE_90 || ☃ == Rotation.COUNTERCLOCKWISE_90) {
               ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.WEST), ☃xxxxxxxx);
            } else if (☃ == Rotation.CLOCKWISE_180) {
               ☃ = ☃.func_177967_a(EnumFacing.EAST, ☃xxxxxx);
            } else {
               ☃ = ☃.func_177967_a(EnumFacing.WEST, ☃xxxxxx);
            }

            this.field_74887_e.func_78886_a(☃.func_177958_n(), 0, ☃.func_177952_p());
            break;
         case LEFT_RIGHT:
            BlockPos ☃ = BlockPos.field_177992_a;
            if (☃ == Rotation.CLOCKWISE_90 || ☃ == Rotation.COUNTERCLOCKWISE_90) {
               ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), ☃xxxxxx);
            } else if (☃ == Rotation.CLOCKWISE_180) {
               ☃ = ☃.func_177967_a(EnumFacing.SOUTH, ☃xxxxxxxx);
            } else {
               ☃ = ☃.func_177967_a(EnumFacing.NORTH, ☃xxxxxxxx);
            }

            this.field_74887_e.func_78886_a(☃.func_177958_n(), 0, ☃.func_177952_p());
      }

      this.field_74887_e.func_78886_a(this.field_186178_c.func_177958_n(), this.field_186178_c.func_177956_o(), this.field_186178_c.func_177952_p());
   }

   @Override
   public void func_181138_a(int var1, int var2, int var3) {
      super.func_181138_a(☃, ☃, ☃);
      this.field_186178_c = this.field_186178_c.func_177982_a(☃, ☃, ☃);
   }
}
