package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class PhaseHoldingPattern extends PhaseBase {
   private Path field_188677_b;
   private Vec3d field_188678_c;
   private boolean field_188679_d;

   public PhaseHoldingPattern(EntityDragon var1) {
      super(☃);
   }

   @Override
   public PhaseType<PhaseHoldingPattern> func_188652_i() {
      return PhaseType.field_188741_a;
   }

   @Override
   public void func_188659_c() {
      double ☃ = this.field_188678_c == null
         ? 0.0
         : this.field_188678_c.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
      if (☃ < 100.0 || ☃ > 22500.0 || this.field_188661_a.field_70123_F || this.field_188661_a.field_70124_G) {
         this.func_188675_j();
      }
   }

   @Override
   public void func_188660_d() {
      this.field_188677_b = null;
      this.field_188678_c = null;
   }

   @Nullable
   @Override
   public Vec3d func_188650_g() {
      return this.field_188678_c;
   }

   private void func_188675_j() {
      if (this.field_188677_b != null && this.field_188677_b.func_75879_b()) {
         BlockPos ☃ = this.field_188661_a.field_70170_p.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.field_186139_a));
         int ☃x = this.field_188661_a.func_184664_cU() == null ? 0 : this.field_188661_a.func_184664_cU().func_186092_c();
         if (this.field_188661_a.func_70681_au().nextInt(☃x + 3) == 0) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188743_c);
            return;
         }

         double ☃ = 64.0;
         EntityPlayer ☃x = this.field_188661_a.field_70170_p.func_184139_a(☃, ☃, ☃);
         if (☃x != null) {
            ☃ = ☃x.func_174831_c(☃) / 512.0;
         }

         if (☃x != null
            && (
               this.field_188661_a.func_70681_au().nextInt(MathHelper.func_76130_a((int)☃) + 2) == 0
                  || this.field_188661_a.func_70681_au().nextInt(☃x + 2) == 0
            )) {
            this.func_188674_a(☃x);
            return;
         }
      }

      if (this.field_188677_b == null || this.field_188677_b.func_75879_b()) {
         int ☃ = this.field_188661_a.func_184671_o();
         int ☃x = ☃;
         if (this.field_188661_a.func_70681_au().nextInt(8) == 0) {
            this.field_188679_d = !this.field_188679_d;
            ☃x = ☃ + 6;
         }

         if (this.field_188679_d) {
            ++☃x;
         } else {
            --☃x;
         }

         if (this.field_188661_a.func_184664_cU() != null && this.field_188661_a.func_184664_cU().func_186092_c() >= 0) {
            ☃x %= 12;
            if (☃x < 0) {
               ☃x += 12;
            }
         } else {
            ☃x -= 12;
            ☃x &= 7;
            ☃x += 12;
         }

         this.field_188677_b = this.field_188661_a.func_184666_a(☃, ☃x, null);
         if (this.field_188677_b != null) {
            this.field_188677_b.func_75875_a();
         }
      }

      this.func_188676_k();
   }

   private void func_188674_a(EntityPlayer var1) {
      this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188742_b);
      this.field_188661_a.func_184670_cT().func_188757_b(PhaseType.field_188742_b).func_188686_a(☃);
   }

   private void func_188676_k() {
      if (this.field_188677_b != null && !this.field_188677_b.func_75879_b()) {
         Vec3d ☃ = this.field_188677_b.func_186310_f();
         this.field_188677_b.func_75875_a();
         double ☃x = ☃.field_72450_a;
         double ☃xx = ☃.field_72449_c;

         double ☃;
         do {
            ☃ = ☃.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
         } while(☃ < ☃.field_72448_b);

         this.field_188678_c = new Vec3d(☃x, ☃, ☃xx);
      }
   }

   @Override
   public void func_188655_a(EntityEnderCrystal var1, BlockPos var2, DamageSource var3, @Nullable EntityPlayer var4) {
      if (☃ != null && !☃.field_71075_bZ.field_75102_a) {
         this.func_188674_a(☃);
      }
   }
}
