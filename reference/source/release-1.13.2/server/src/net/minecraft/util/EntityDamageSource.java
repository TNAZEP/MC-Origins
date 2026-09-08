package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityDamageSource extends DamageSource {
   @Nullable
   protected Entity field_76386_o;
   private boolean field_180140_r;

   public EntityDamageSource(String var1, @Nullable Entity var2) {
      super(☃);
      this.field_76386_o = ☃;
   }

   public EntityDamageSource func_180138_v() {
      this.field_180140_r = true;
      return this;
   }

   public boolean func_180139_w() {
      return this.field_180140_r;
   }

   @Nullable
   @Override
   public Entity func_76346_g() {
      return this.field_76386_o;
   }

   @Override
   public ITextComponent func_151519_b(EntityLivingBase var1) {
      ItemStack ☃ = this.field_76386_o instanceof EntityLivingBase ? ((EntityLivingBase)this.field_76386_o).func_184614_ca() : ItemStack.field_190927_a;
      String ☃x = "death.attack." + this.field_76373_n;
      return !☃.func_190926_b() && ☃.func_82837_s()
         ? new TextComponentTranslation(☃x + ".item", ☃.func_145748_c_(), this.field_76386_o.func_145748_c_(), ☃.func_151000_E())
         : new TextComponentTranslation(☃x, ☃.func_145748_c_(), this.field_76386_o.func_145748_c_());
   }

   @Override
   public boolean func_76350_n() {
      return this.field_76386_o != null && this.field_76386_o instanceof EntityLivingBase && !(this.field_76386_o instanceof EntityPlayer);
   }

   @Nullable
   @Override
   public Vec3d func_188404_v() {
      return new Vec3d(this.field_76386_o.field_70165_t, this.field_76386_o.field_70163_u, this.field_76386_o.field_70161_v);
   }
}
