package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private final Entity field_76387_p;

   public EntityDamageSourceIndirect(String var1, Entity var2, @Nullable Entity var3) {
      super(☃, ☃);
      this.field_76387_p = ☃;
   }

   @Nullable
   @Override
   public Entity func_76364_f() {
      return this.field_76386_o;
   }

   @Nullable
   @Override
   public Entity func_76346_g() {
      return this.field_76387_p;
   }

   @Override
   public ITextComponent func_151519_b(EntityLivingBase var1) {
      ITextComponent ☃ = this.field_76387_p == null ? this.field_76386_o.func_145748_c_() : this.field_76387_p.func_145748_c_();
      ItemStack ☃x = this.field_76387_p instanceof EntityLivingBase ? ((EntityLivingBase)this.field_76387_p).func_184614_ca() : ItemStack.field_190927_a;
      String ☃xx = "death.attack." + this.field_76373_n;
      String ☃xxx = ☃xx + ".item";
      return !☃x.func_190926_b() && ☃x.func_82837_s()
         ? new TextComponentTranslation(☃xxx, ☃.func_145748_c_(), ☃, ☃x.func_151000_E())
         : new TextComponentTranslation(☃xx, ☃.func_145748_c_(), ☃);
   }
}
