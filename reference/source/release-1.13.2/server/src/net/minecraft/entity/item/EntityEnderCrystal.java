package net.minecraft.entity.item;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.end.DragonFightManager;

public class EntityEnderCrystal extends Entity {
   private static final DataParameter<Optional<BlockPos>> field_184521_b = EntityDataManager.func_187226_a(
      EntityEnderCrystal.class, DataSerializers.field_187201_k
   );
   private static final DataParameter<Boolean> field_184522_c = EntityDataManager.func_187226_a(EntityEnderCrystal.class, DataSerializers.field_187198_h);
   public int field_70261_a;

   public EntityEnderCrystal(World var1) {
      super(EntityType.field_200801_o, ☃);
      this.field_70156_m = true;
      this.func_70105_a(2.0F, 2.0F);
      this.field_70261_a = this.field_70146_Z.nextInt(100000);
   }

   public EntityEnderCrystal(World var1, double var2, double var4, double var6) {
      this(☃);
      this.func_70107_b(☃, ☃, ☃);
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184521_b, Optional.empty());
      this.func_184212_Q().func_187214_a(field_184522_c, true);
   }

   @Override
   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      ++this.field_70261_a;
      if (!this.field_70170_p.field_72995_K) {
         BlockPos ☃ = new BlockPos(this);
         if (this.field_70170_p.field_73011_w instanceof EndDimension && this.field_70170_p.func_180495_p(☃).func_196958_f()) {
            this.field_70170_p.func_175656_a(☃, Blocks.field_150480_ab.func_176223_P());
         }
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      if (this.func_184518_j() != null) {
         ☃.func_74782_a("BeamTarget", NBTUtil.func_186859_a(this.func_184518_j()));
      }

      ☃.func_74757_a("ShowBottom", this.func_184520_k());
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      if (☃.func_150297_b("BeamTarget", 10)) {
         this.func_184516_a(NBTUtil.func_186861_c(☃.func_74775_l("BeamTarget")));
      }

      if (☃.func_150297_b("ShowBottom", 1)) {
         this.func_184517_a(☃.func_74767_n("ShowBottom"));
      }
   }

   @Override
   public boolean func_70067_L() {
      return true;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (☃.func_76346_g() instanceof EntityDragon) {
         return false;
      } else {
         if (!this.field_70128_L && !this.field_70170_p.field_72995_K) {
            this.func_70106_y();
            if (!this.field_70170_p.field_72995_K) {
               if (!☃.func_94541_c()) {
                  this.field_70170_p.func_72876_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 6.0F, true);
               }

               this.func_184519_a(☃);
            }
         }

         return true;
      }
   }

   @Override
   public void func_174812_G() {
      this.func_184519_a(DamageSource.field_76377_j);
      super.func_174812_G();
   }

   private void func_184519_a(DamageSource var1) {
      if (this.field_70170_p.field_73011_w instanceof EndDimension) {
         EndDimension ☃ = (EndDimension)this.field_70170_p.field_73011_w;
         DragonFightManager ☃x = ☃.func_186063_s();
         if (☃x != null) {
            ☃x.func_186090_a(this, ☃);
         }
      }
   }

   public void func_184516_a(@Nullable BlockPos var1) {
      this.func_184212_Q().func_187227_b(field_184521_b, Optional.ofNullable(☃));
   }

   @Nullable
   public BlockPos func_184518_j() {
      return (BlockPos)((Optional)this.func_184212_Q().func_187225_a(field_184521_b)).orElse(null);
   }

   public void func_184517_a(boolean var1) {
      this.func_184212_Q().func_187227_b(field_184522_c, ☃);
   }

   public boolean func_184520_k() {
      return this.func_184212_Q().func_187225_a(field_184522_c);
   }
}
