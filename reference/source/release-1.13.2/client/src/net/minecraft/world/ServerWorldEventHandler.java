package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.particles.IParticleData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class ServerWorldEventHandler implements IWorldEventListener {
   private final MinecraftServer field_72783_a;
   private final WorldServer field_72782_b;

   public ServerWorldEventHandler(MinecraftServer var1, WorldServer var2) {
      this.field_72783_a = ☃;
      this.field_72782_b = ☃;
   }

   @Override
   public void func_195461_a(IParticleData var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
   }

   @Override
   public void func_195462_a(IParticleData var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
   }

   @Override
   public void func_72703_a(Entity var1) {
      this.field_72782_b.func_73039_n().func_72786_a(☃);
      if (☃ instanceof EntityPlayerMP) {
         this.field_72782_b.field_73011_w.func_186061_a((EntityPlayerMP)☃);
      }
   }

   @Override
   public void func_72709_b(Entity var1) {
      this.field_72782_b.func_73039_n().func_72790_b(☃);
      this.field_72782_b.func_96441_U().func_181140_a(☃);
      if (☃ instanceof EntityPlayerMP) {
         this.field_72782_b.field_73011_w.func_186062_b((EntityPlayerMP)☃);
      }
   }

   @Override
   public void func_184375_a(@Nullable EntityPlayer var1, SoundEvent var2, SoundCategory var3, double var4, double var6, double var8, float var10, float var11) {
      this.field_72783_a
         .func_184103_al()
         .func_148543_a(
            ☃, ☃, ☃, ☃, ☃ > 1.0F ? (double)(16.0F * ☃) : 16.0, this.field_72782_b.field_73011_w.func_186058_p(), new SPacketSoundEffect(☃, ☃, ☃, ☃, ☃, ☃, ☃)
         );
   }

   @Override
   public void func_147585_a(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   @Override
   public void func_184376_a(IBlockReader var1, BlockPos var2, IBlockState var3, IBlockState var4, int var5) {
      this.field_72782_b.func_184164_w().func_180244_a(☃);
   }

   @Override
   public void func_174959_b(BlockPos var1) {
   }

   @Override
   public void func_184377_a(SoundEvent var1, BlockPos var2) {
   }

   @Override
   public void func_180439_a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      this.field_72783_a
         .func_184103_al()
         .func_148543_a(
            ☃,
            (double)☃.func_177958_n(),
            (double)☃.func_177956_o(),
            (double)☃.func_177952_p(),
            64.0,
            this.field_72782_b.field_73011_w.func_186058_p(),
            new SPacketEffect(☃, ☃, ☃, false)
         );
   }

   @Override
   public void func_180440_a(int var1, BlockPos var2, int var3) {
      this.field_72783_a.func_184103_al().func_148540_a(new SPacketEffect(☃, ☃, ☃, true));
   }

   @Override
   public void func_180441_b(int var1, BlockPos var2, int var3) {
      for(EntityPlayerMP ☃ : this.field_72783_a.func_184103_al().func_181057_v()) {
         if (☃ != null && ☃.field_70170_p == this.field_72782_b && ☃.func_145782_y() != ☃) {
            double ☃x = (double)☃.func_177958_n() - ☃.field_70165_t;
            double ☃xx = (double)☃.func_177956_o() - ☃.field_70163_u;
            double ☃xxx = (double)☃.func_177952_p() - ☃.field_70161_v;
            if (☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx < 1024.0) {
               ☃.field_71135_a.func_147359_a(new SPacketBlockBreakAnim(☃, ☃, ☃));
            }
         }
      }
   }
}
