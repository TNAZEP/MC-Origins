package net.minecraft.entity.passive;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable {
   protected static final DataParameter<Byte> field_184755_bv = EntityDataManager.func_187226_a(EntityTameable.class, DataSerializers.field_187191_a);
   protected static final DataParameter<Optional<UUID>> field_184756_bw = EntityDataManager.func_187226_a(EntityTameable.class, DataSerializers.field_187203_m);
   protected EntityAISit field_70911_d;

   protected EntityTameable(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_175544_ck();
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184755_bv, (byte)0);
      this.field_70180_af.func_187214_a(field_184756_bw, Optional.empty());
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      if (this.func_184753_b() == null) {
         ☃.func_74778_a("OwnerUUID", "");
      } else {
         ☃.func_74778_a("OwnerUUID", this.func_184753_b().toString());
      }

      ☃.func_74757_a("Sitting", this.func_70906_o());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      String ☃;
      if (☃.func_150297_b("OwnerUUID", 8)) {
         ☃ = ☃.func_74779_i("OwnerUUID");
      } else {
         String ☃ = ☃.func_74779_i("Owner");
         ☃ = PreYggdrasilConverter.func_187473_a(this.func_184102_h(), ☃);
      }

      if (!☃.isEmpty()) {
         try {
            this.func_184754_b(UUID.fromString(☃));
            this.func_70903_f(true);
         } catch (Throwable var4) {
            this.func_70903_f(false);
         }
      }

      if (this.field_70911_d != null) {
         this.field_70911_d.func_75270_a(☃.func_74767_n("Sitting"));
      }

      this.func_70904_g(☃.func_74767_n("Sitting"));
   }

   @Override
   public boolean func_184652_a(EntityPlayer var1) {
      return !this.func_110167_bD();
   }

   protected void func_70908_e(boolean var1) {
      IParticleData ☃ = Particles.field_197633_z;
      if (!☃) {
         ☃ = Particles.field_197601_L;
      }

      for(int ☃ = 0; ☃ < 7; ++☃) {
         double ☃x = this.field_70146_Z.nextGaussian() * 0.02;
         double ☃xx = this.field_70146_Z.nextGaussian() * 0.02;
         double ☃xxx = this.field_70146_Z.nextGaussian() * 0.02;
         this.field_70170_p
            .func_195594_a(
               ☃,
               this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               this.field_70163_u + 0.5 + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O),
               this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               ☃x,
               ☃xx,
               ☃xxx
            );
      }
   }

   public boolean func_70909_n() {
      return (this.field_70180_af.func_187225_a(field_184755_bv) & 4) != 0;
   }

   public void func_70903_f(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184755_bv);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184755_bv, (byte)(☃ | 4));
      } else {
         this.field_70180_af.func_187227_b(field_184755_bv, (byte)(☃ & -5));
      }

      this.func_175544_ck();
   }

   protected void func_175544_ck() {
   }

   public boolean func_70906_o() {
      return (this.field_70180_af.func_187225_a(field_184755_bv) & 1) != 0;
   }

   public void func_70904_g(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184755_bv);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184755_bv, (byte)(☃ | 1));
      } else {
         this.field_70180_af.func_187227_b(field_184755_bv, (byte)(☃ & -2));
      }
   }

   @Nullable
   @Override
   public UUID func_184753_b() {
      return (UUID)((Optional)this.field_70180_af.func_187225_a(field_184756_bw)).orElse(null);
   }

   public void func_184754_b(@Nullable UUID var1) {
      this.field_70180_af.func_187227_b(field_184756_bw, Optional.ofNullable(☃));
   }

   public void func_193101_c(EntityPlayer var1) {
      this.func_70903_f(true);
      this.func_184754_b(☃.func_110124_au());
      if (☃ instanceof EntityPlayerMP) {
         CriteriaTriggers.field_193136_w.func_193178_a((EntityPlayerMP)☃, this);
      }
   }

   @Nullable
   public EntityLivingBase func_70902_q() {
      try {
         UUID ☃ = this.func_184753_b();
         return ☃ == null ? null : this.field_70170_p.func_152378_a(☃);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public boolean func_152114_e(EntityLivingBase var1) {
      return ☃ == this.func_70902_q();
   }

   public EntityAISit func_70907_r() {
      return this.field_70911_d;
   }

   public boolean func_142018_a(EntityLivingBase var1, EntityLivingBase var2) {
      return true;
   }

   @Override
   public Team func_96124_cp() {
      if (this.func_70909_n()) {
         EntityLivingBase ☃ = this.func_70902_q();
         if (☃ != null) {
            return ☃.func_96124_cp();
         }
      }

      return super.func_96124_cp();
   }

   @Override
   public boolean func_184191_r(Entity var1) {
      if (this.func_70909_n()) {
         EntityLivingBase ☃ = this.func_70902_q();
         if (☃ == ☃) {
            return true;
         }

         if (☃ != null) {
            return ☃.func_184191_r(☃);
         }
      }

      return super.func_184191_r(☃);
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      if (!this.field_70170_p.field_72995_K
         && this.field_70170_p.func_82736_K().func_82766_b("showDeathMessages")
         && this.func_70902_q() instanceof EntityPlayerMP) {
         this.func_70902_q().func_145747_a(this.func_110142_aN().func_151521_b());
      }

      super.func_70645_a(☃);
   }
}
