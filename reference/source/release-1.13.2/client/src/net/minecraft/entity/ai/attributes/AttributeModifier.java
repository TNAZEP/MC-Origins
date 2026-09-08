package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double field_111174_a;
   private final int field_111172_b;
   private final Supplier<String> field_111173_c;
   private final UUID field_111170_d;
   private boolean field_111171_e = true;

   public AttributeModifier(String var1, double var2, int var4) {
      this(MathHelper.func_180182_a(ThreadLocalRandom.current()), (Supplier<String>)(() -> ☃), ☃, ☃);
   }

   public AttributeModifier(UUID var1, String var2, double var3, int var5) {
      this(☃, (Supplier<String>)(() -> ☃), ☃, ☃);
   }

   public AttributeModifier(UUID var1, Supplier<String> var2, double var3, int var5) {
      this.field_111170_d = ☃;
      this.field_111173_c = ☃;
      this.field_111174_a = ☃;
      this.field_111172_b = ☃;
      Validate.inclusiveBetween(0L, 2L, (long)☃, "Invalid operation");
   }

   public UUID func_111167_a() {
      return this.field_111170_d;
   }

   public String func_111166_b() {
      return (String)this.field_111173_c.get();
   }

   public int func_111169_c() {
      return this.field_111172_b;
   }

   public double func_111164_d() {
      return this.field_111174_a;
   }

   public boolean func_111165_e() {
      return this.field_111171_e;
   }

   public AttributeModifier func_111168_a(boolean var1) {
      this.field_111171_e = ☃;
      return this;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         AttributeModifier ☃ = (AttributeModifier)☃;
         return this.field_111170_d != null ? this.field_111170_d.equals(☃.field_111170_d) : ☃.field_111170_d == null;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_111170_d != null ? this.field_111170_d.hashCode() : 0;
   }

   public String toString() {
      return "AttributeModifier{amount="
         + this.field_111174_a
         + ", operation="
         + this.field_111172_b
         + ", name='"
         + (String)this.field_111173_c.get()
         + '\''
         + ", id="
         + this.field_111170_d
         + ", serialize="
         + this.field_111171_e
         + '}';
   }
}
