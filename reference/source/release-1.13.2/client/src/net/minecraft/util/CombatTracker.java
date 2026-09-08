package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CombatTracker {
   private final List<CombatEntry> field_94556_a = Lists.<CombatEntry>newArrayList();
   private final EntityLivingBase field_94554_b;
   private int field_94555_c;
   private int field_152775_d;
   private int field_152776_e;
   private boolean field_94552_d;
   private boolean field_94553_e;
   private String field_94551_f;

   public CombatTracker(EntityLivingBase var1) {
      this.field_94554_b = ☃;
   }

   public void func_94545_a() {
      this.func_94542_g();
      if (this.field_94554_b.func_70617_f_()) {
         Block ☃ = this.field_94554_b
            .field_70170_p
            .func_180495_p(new BlockPos(this.field_94554_b.field_70165_t, this.field_94554_b.func_174813_aQ().field_72338_b, this.field_94554_b.field_70161_v))
            .func_177230_c();
         if (☃ == Blocks.field_150468_ap) {
            this.field_94551_f = "ladder";
         } else if (☃ == Blocks.field_150395_bd) {
            this.field_94551_f = "vines";
         }
      } else if (this.field_94554_b.func_70090_H()) {
         this.field_94551_f = "water";
      }
   }

   public void func_94547_a(DamageSource var1, float var2, float var3) {
      this.func_94549_h();
      this.func_94545_a();
      CombatEntry ☃ = new CombatEntry(☃, this.field_94554_b.field_70173_aa, ☃, ☃, this.field_94551_f, this.field_94554_b.field_70143_R);
      this.field_94556_a.add(☃);
      this.field_94555_c = this.field_94554_b.field_70173_aa;
      this.field_94553_e = true;
      if (☃.func_94559_f() && !this.field_94552_d && this.field_94554_b.func_70089_S()) {
         this.field_94552_d = true;
         this.field_152775_d = this.field_94554_b.field_70173_aa;
         this.field_152776_e = this.field_152775_d;
         this.field_94554_b.func_152111_bt();
      }
   }

   public ITextComponent func_151521_b() {
      if (this.field_94556_a.isEmpty()) {
         return new TextComponentTranslation("death.attack.generic", this.field_94554_b.func_145748_c_());
      } else {
         CombatEntry ☃x = this.func_94544_f();
         CombatEntry ☃xx = (CombatEntry)this.field_94556_a.get(this.field_94556_a.size() - 1);
         ITextComponent ☃xxx = ☃xx.func_151522_h();
         Entity ☃xxxx = ☃xx.func_94560_a().func_76346_g();
         ITextComponent ☃;
         if (☃x != null && ☃xx.func_94560_a() == DamageSource.field_76379_h) {
            ITextComponent ☃xxxxx = ☃x.func_151522_h();
            if (☃x.func_94560_a() == DamageSource.field_76379_h || ☃x.func_94560_a() == DamageSource.field_76380_i) {
               ☃ = new TextComponentTranslation("death.fell.accident." + this.func_94548_b(☃x), this.field_94554_b.func_145748_c_());
            } else if (☃xxxxx != null && (☃xxx == null || !☃xxxxx.equals(☃xxx))) {
               Entity ☃xxxxx = ☃x.func_94560_a().func_76346_g();
               ItemStack ☃xxxxxx = ☃xxxxx instanceof EntityLivingBase ? ((EntityLivingBase)☃xxxxx).func_184614_ca() : ItemStack.field_190927_a;
               if (!☃xxxxxx.func_190926_b() && ☃xxxxxx.func_82837_s()) {
                  ☃ = new TextComponentTranslation("death.fell.assist.item", this.field_94554_b.func_145748_c_(), ☃xxxxx, ☃xxxxxx.func_151000_E());
               } else {
                  ☃ = new TextComponentTranslation("death.fell.assist", this.field_94554_b.func_145748_c_(), ☃xxxxx);
               }
            } else if (☃xxx != null) {
               ItemStack ☃xxxxx = ☃xxxx instanceof EntityLivingBase ? ((EntityLivingBase)☃xxxx).func_184614_ca() : ItemStack.field_190927_a;
               if (!☃xxxxx.func_190926_b() && ☃xxxxx.func_82837_s()) {
                  ☃ = new TextComponentTranslation("death.fell.finish.item", this.field_94554_b.func_145748_c_(), ☃xxx, ☃xxxxx.func_151000_E());
               } else {
                  ☃ = new TextComponentTranslation("death.fell.finish", this.field_94554_b.func_145748_c_(), ☃xxx);
               }
            } else {
               ☃ = new TextComponentTranslation("death.fell.killer", this.field_94554_b.func_145748_c_());
            }
         } else {
            ☃ = ☃xx.func_94560_a().func_151519_b(this.field_94554_b);
         }

         return ☃;
      }
   }

   @Nullable
   public EntityLivingBase func_94550_c() {
      EntityLivingBase ☃ = null;
      EntityPlayer ☃x = null;
      float ☃xx = 0.0F;
      float ☃xxx = 0.0F;

      for(CombatEntry ☃xxxx : this.field_94556_a) {
         if (☃xxxx.func_94560_a().func_76346_g() instanceof EntityPlayer && (☃x == null || ☃xxxx.func_94563_c() > ☃xxx)) {
            ☃xxx = ☃xxxx.func_94563_c();
            ☃x = (EntityPlayer)☃xxxx.func_94560_a().func_76346_g();
         }

         if (☃xxxx.func_94560_a().func_76346_g() instanceof EntityLivingBase && (☃ == null || ☃xxxx.func_94563_c() > ☃xx)) {
            ☃xx = ☃xxxx.func_94563_c();
            ☃ = (EntityLivingBase)☃xxxx.func_94560_a().func_76346_g();
         }
      }

      return (EntityLivingBase)(☃x != null && ☃xxx >= ☃xx / 3.0F ? ☃x : ☃);
   }

   @Nullable
   private CombatEntry func_94544_f() {
      CombatEntry ☃ = null;
      CombatEntry ☃x = null;
      float ☃xx = 0.0F;
      float ☃xxx = 0.0F;

      for(int ☃xxxx = 0; ☃xxxx < this.field_94556_a.size(); ++☃xxxx) {
         CombatEntry ☃xxxxx = (CombatEntry)this.field_94556_a.get(☃xxxx);
         CombatEntry ☃xxxxxx = ☃xxxx > 0 ? (CombatEntry)this.field_94556_a.get(☃xxxx - 1) : null;
         if ((☃xxxxx.func_94560_a() == DamageSource.field_76379_h || ☃xxxxx.func_94560_a() == DamageSource.field_76380_i)
            && ☃xxxxx.func_94561_i() > 0.0F
            && (☃ == null || ☃xxxxx.func_94561_i() > ☃xxx)) {
            if (☃xxxx > 0) {
               ☃ = ☃xxxxxx;
            } else {
               ☃ = ☃xxxxx;
            }

            ☃xxx = ☃xxxxx.func_94561_i();
         }

         if (☃xxxxx.func_94562_g() != null && (☃x == null || ☃xxxxx.func_94563_c() > ☃xx)) {
            ☃x = ☃xxxxx;
            ☃xx = ☃xxxxx.func_94563_c();
         }
      }

      if (☃xxx > 5.0F && ☃ != null) {
         return ☃;
      } else {
         return ☃xx > 5.0F && ☃x != null ? ☃x : null;
      }
   }

   private String func_94548_b(CombatEntry var1) {
      return ☃.func_94562_g() == null ? "generic" : ☃.func_94562_g();
   }

   public int func_180134_f() {
      return this.field_94552_d ? this.field_94554_b.field_70173_aa - this.field_152775_d : this.field_152776_e - this.field_152775_d;
   }

   private void func_94542_g() {
      this.field_94551_f = null;
   }

   public void func_94549_h() {
      int ☃ = this.field_94552_d ? 300 : 100;
      if (this.field_94553_e && (!this.field_94554_b.func_70089_S() || this.field_94554_b.field_70173_aa - this.field_94555_c > ☃)) {
         boolean ☃x = this.field_94552_d;
         this.field_94553_e = false;
         this.field_94552_d = false;
         this.field_152776_e = this.field_94554_b.field_70173_aa;
         if (☃x) {
            this.field_94554_b.func_152112_bu();
         }

         this.field_94556_a.clear();
      }
   }

   public EntityLivingBase func_180135_h() {
      return this.field_94554_b;
   }
}
