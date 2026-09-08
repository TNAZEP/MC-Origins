package net.minecraft.potion;

import com.google.common.collect.ComparisonChain;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect implements Comparable<PotionEffect> {
   private static final Logger field_180155_a = LogManager.getLogger();
   private final Potion field_188420_b;
   private int field_76460_b;
   private int field_76461_c;
   private boolean field_82723_d;
   private boolean field_82724_e;
   private boolean field_100013_f;
   private boolean field_188421_h;
   private boolean field_205349_i;

   public PotionEffect(Potion var1) {
      this(☃, 0, 0);
   }

   public PotionEffect(Potion var1, int var2) {
      this(☃, ☃, 0);
   }

   public PotionEffect(Potion var1, int var2, int var3) {
      this(☃, ☃, ☃, false, true);
   }

   public PotionEffect(Potion var1, int var2, int var3, boolean var4, boolean var5) {
      this(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public PotionEffect(Potion var1, int var2, int var3, boolean var4, boolean var5, boolean var6) {
      this.field_188420_b = ☃;
      this.field_76460_b = ☃;
      this.field_76461_c = ☃;
      this.field_82724_e = ☃;
      this.field_188421_h = ☃;
      this.field_205349_i = ☃;
   }

   public PotionEffect(PotionEffect var1) {
      this.field_188420_b = ☃.field_188420_b;
      this.field_76460_b = ☃.field_76460_b;
      this.field_76461_c = ☃.field_76461_c;
      this.field_82724_e = ☃.field_82724_e;
      this.field_188421_h = ☃.field_188421_h;
      this.field_205349_i = ☃.field_205349_i;
   }

   public boolean func_199308_a(PotionEffect var1) {
      if (this.field_188420_b != ☃.field_188420_b) {
         field_180155_a.warn("This method should only be called for matching effects!");
      }

      boolean ☃ = false;
      if (☃.field_76461_c > this.field_76461_c) {
         this.field_76461_c = ☃.field_76461_c;
         this.field_76460_b = ☃.field_76460_b;
         ☃ = true;
      } else if (☃.field_76461_c == this.field_76461_c && this.field_76460_b < ☃.field_76460_b) {
         this.field_76460_b = ☃.field_76460_b;
         ☃ = true;
      }

      if (!☃.field_82724_e && this.field_82724_e || ☃) {
         this.field_82724_e = ☃.field_82724_e;
         ☃ = true;
      }

      if (☃.field_188421_h != this.field_188421_h) {
         this.field_188421_h = ☃.field_188421_h;
         ☃ = true;
      }

      if (☃.field_205349_i != this.field_205349_i) {
         this.field_205349_i = ☃.field_205349_i;
         ☃ = true;
      }

      return ☃;
   }

   public Potion func_188419_a() {
      return this.field_188420_b;
   }

   public int func_76459_b() {
      return this.field_76460_b;
   }

   public int func_76458_c() {
      return this.field_76461_c;
   }

   public boolean func_82720_e() {
      return this.field_82724_e;
   }

   public boolean func_188418_e() {
      return this.field_188421_h;
   }

   public boolean func_205348_f() {
      return this.field_205349_i;
   }

   public boolean func_76455_a(EntityLivingBase var1) {
      if (this.field_76460_b > 0) {
         if (this.field_188420_b.func_76397_a(this.field_76460_b, this.field_76461_c)) {
            this.func_76457_b(☃);
         }

         this.func_76454_e();
      }

      return this.field_76460_b > 0;
   }

   private int func_76454_e() {
      return --this.field_76460_b;
   }

   public void func_76457_b(EntityLivingBase var1) {
      if (this.field_76460_b > 0) {
         this.field_188420_b.func_76394_a(☃, this.field_76461_c);
      }
   }

   public String func_76453_d() {
      return this.field_188420_b.func_76393_a();
   }

   public String toString() {
      String ☃;
      if (this.field_76461_c > 0) {
         ☃ = this.func_76453_d() + " x " + (this.field_76461_c + 1) + ", Duration: " + this.field_76460_b;
      } else {
         ☃ = this.func_76453_d() + ", Duration: " + this.field_76460_b;
      }

      if (this.field_82723_d) {
         ☃ = ☃ + ", Splash: true";
      }

      if (!this.field_188421_h) {
         ☃ = ☃ + ", Particles: false";
      }

      if (!this.field_205349_i) {
         ☃ = ☃ + ", Show Icon: false";
      }

      return ☃;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof PotionEffect)) {
         return false;
      } else {
         PotionEffect ☃ = (PotionEffect)☃;
         return this.field_76460_b == ☃.field_76460_b
            && this.field_76461_c == ☃.field_76461_c
            && this.field_82723_d == ☃.field_82723_d
            && this.field_82724_e == ☃.field_82724_e
            && this.field_188420_b.equals(☃.field_188420_b);
      }
   }

   public int hashCode() {
      int ☃ = this.field_188420_b.hashCode();
      ☃ = 31 * ☃ + this.field_76460_b;
      ☃ = 31 * ☃ + this.field_76461_c;
      ☃ = 31 * ☃ + (this.field_82723_d ? 1 : 0);
      return 31 * ☃ + (this.field_82724_e ? 1 : 0);
   }

   public NBTTagCompound func_82719_a(NBTTagCompound var1) {
      ☃.func_74774_a("Id", (byte)Potion.func_188409_a(this.func_188419_a()));
      ☃.func_74774_a("Amplifier", (byte)this.func_76458_c());
      ☃.func_74768_a("Duration", this.func_76459_b());
      ☃.func_74757_a("Ambient", this.func_82720_e());
      ☃.func_74757_a("ShowParticles", this.func_188418_e());
      ☃.func_74757_a("ShowIcon", this.func_205348_f());
      return ☃;
   }

   public static PotionEffect func_82722_b(NBTTagCompound var0) {
      int ☃ = ☃.func_74771_c("Id");
      Potion ☃x = Potion.func_188412_a(☃);
      if (☃x == null) {
         return null;
      } else {
         int ☃ = ☃.func_74771_c("Amplifier");
         int ☃x = ☃.func_74762_e("Duration");
         boolean ☃xx = ☃.func_74767_n("Ambient");
         boolean ☃xxx = true;
         if (☃.func_150297_b("ShowParticles", 1)) {
            ☃xxx = ☃.func_74767_n("ShowParticles");
         }

         boolean ☃ = ☃xxx;
         if (☃.func_150297_b("ShowIcon", 1)) {
            ☃ = ☃.func_74767_n("ShowIcon");
         }

         return new PotionEffect(☃x, ☃x, ☃ < 0 ? 0 : ☃, ☃xx, ☃xxx, ☃);
      }
   }

   public void func_100012_b(boolean var1) {
      this.field_100013_f = ☃;
   }

   public boolean func_100011_g() {
      return this.field_100013_f;
   }

   public int compareTo(PotionEffect var1) {
      int ☃ = 32147;
      return (this.func_76459_b() <= 32147 || ☃.func_76459_b() <= 32147) && (!this.func_82720_e() || !☃.func_82720_e())
         ? ComparisonChain.start()
            .compare(this.func_82720_e(), ☃.func_82720_e())
            .compare(this.func_76459_b(), ☃.func_76459_b())
            .compare(this.func_188419_a().func_76401_j(), ☃.func_188419_a().func_76401_j())
            .result()
         : ComparisonChain.start()
            .compare(this.func_82720_e(), ☃.func_82720_e())
            .compare(this.func_188419_a().func_76401_j(), ☃.func_188419_a().func_76401_j())
            .result();
   }
}
