package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemBoat extends Item {
   private final EntityBoat.Type field_185057_a;

   public ItemBoat(EntityBoat.Type var1, Item.Properties var2) {
      super(☃);
      this.field_185057_a = ☃;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      float ☃x = 1.0F;
      float ☃xx = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * 1.0F;
      float ☃xxx = ☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * 1.0F;
      double ☃xxxx = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * 1.0;
      double ☃xxxxx = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * 1.0 + (double)☃.func_70047_e();
      double ☃xxxxxx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * 1.0;
      Vec3d ☃xxxxxxx = new Vec3d(☃xxxx, ☃xxxxx, ☃xxxxxx);
      float ☃xxxxxxxx = MathHelper.func_76134_b(-☃xxx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃xxxxxxxxx = MathHelper.func_76126_a(-☃xxx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃xxxxxxxxxx = -MathHelper.func_76134_b(-☃xx * (float) (Math.PI / 180.0));
      float ☃xxxxxxxxxxx = MathHelper.func_76126_a(-☃xx * (float) (Math.PI / 180.0));
      float ☃xxxxxxxxxxxx = ☃xxxxxxxxx * ☃xxxxxxxxxx;
      float ☃xxxxxxxxxxxxx = ☃xxxxxxxx * ☃xxxxxxxxxx;
      double ☃xxxxxxxxxxxxxx = 5.0;
      Vec3d ☃xxxxxxxxxxxxxxx = ☃xxxxxxx.func_72441_c((double)☃xxxxxxxxxxxx * 5.0, (double)☃xxxxxxxxxxx * 5.0, (double)☃xxxxxxxxxxxxx * 5.0);
      RayTraceResult ☃xxxxxxxxxxxxxxxx = ☃.func_200260_a(☃xxxxxxx, ☃xxxxxxxxxxxxxxx, RayTraceFluidMode.ALWAYS);
      if (☃xxxxxxxxxxxxxxxx == null) {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      } else {
         Vec3d ☃ = ☃.func_70676_i(1.0F);
         boolean ☃x = false;
         List<Entity> ☃xx = ☃.func_72839_b(
            ☃, ☃.func_174813_aQ().func_72321_a(☃.field_72450_a * 5.0, ☃.field_72448_b * 5.0, ☃.field_72449_c * 5.0).func_186662_g(1.0)
         );

         for(int ☃xxx = 0; ☃xxx < ☃xx.size(); ++☃xxx) {
            Entity ☃xxxx = (Entity)☃xx.get(☃xxx);
            if (☃xxxx.func_70067_L()) {
               AxisAlignedBB ☃xxxxx = ☃xxxx.func_174813_aQ().func_186662_g((double)☃xxxx.func_70111_Y());
               if (☃xxxxx.func_72318_a(☃xxxxxxx)) {
                  ☃x = true;
               }
            }
         }

         if (☃x) {
            return new ActionResult<>(EnumActionResult.PASS, ☃);
         } else if (☃xxxxxxxxxxxxxxxx.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃xxx = ☃xxxxxxxxxxxxxxxx.func_178782_a();
            Block ☃xxxx = ☃.func_180495_p(☃xxx).func_177230_c();
            EntityBoat ☃xxxxx = new EntityBoat(
               ☃, ☃xxxxxxxxxxxxxxxx.field_72307_f.field_72450_a, ☃xxxxxxxxxxxxxxxx.field_72307_f.field_72448_b, ☃xxxxxxxxxxxxxxxx.field_72307_f.field_72449_c
            );
            ☃xxxxx.func_184458_a(this.field_185057_a);
            ☃xxxxx.field_70177_z = ☃.field_70177_z;
            if (!☃.func_195586_b(☃xxxxx, ☃xxxxx.func_174813_aQ().func_186662_g(-0.1))) {
               return new ActionResult<>(EnumActionResult.FAIL, ☃);
            } else {
               if (!☃.field_72995_K) {
                  ☃.func_72838_d(☃xxxxx);
               }

               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
               }

               ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
               return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
            }
         } else {
            return new ActionResult<>(EnumActionResult.PASS, ☃);
         }
      }
   }
}
