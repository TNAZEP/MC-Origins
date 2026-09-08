package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;

public class ModifiableAttributeInstance implements IAttributeInstance {
   private final AbstractAttributeMap field_111138_a;
   private final IAttribute field_111136_b;
   private final Map<Integer, Set<AttributeModifier>> field_111137_c = Maps.newHashMap();
   private final Map<String, Set<AttributeModifier>> field_111134_d = Maps.newHashMap();
   private final Map<UUID, AttributeModifier> field_111135_e = Maps.newHashMap();
   private double field_111132_f;
   private boolean field_111133_g = true;
   private double field_111139_h;

   public ModifiableAttributeInstance(AbstractAttributeMap var1, IAttribute var2) {
      this.field_111138_a = ☃;
      this.field_111136_b = ☃;
      this.field_111132_f = ☃.func_111110_b();

      for(int ☃ = 0; ☃ < 3; ++☃) {
         this.field_111137_c.put(☃, Sets.newHashSet());
      }
   }

   @Override
   public IAttribute func_111123_a() {
      return this.field_111136_b;
   }

   @Override
   public double func_111125_b() {
      return this.field_111132_f;
   }

   @Override
   public void func_111128_a(double var1) {
      if (☃ != this.func_111125_b()) {
         this.field_111132_f = ☃;
         this.func_111131_f();
      }
   }

   @Override
   public Collection<AttributeModifier> func_111130_a(int var1) {
      return (Collection<AttributeModifier>)this.field_111137_c.get(☃);
   }

   @Override
   public Collection<AttributeModifier> func_111122_c() {
      Set<AttributeModifier> ☃ = Sets.<AttributeModifier>newHashSet();

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         ☃.addAll(this.func_111130_a(☃x));
      }

      return ☃;
   }

   @Nullable
   @Override
   public AttributeModifier func_111127_a(UUID var1) {
      return (AttributeModifier)this.field_111135_e.get(☃);
   }

   @Override
   public boolean func_180374_a(AttributeModifier var1) {
      return this.field_111135_e.get(☃.func_111167_a()) != null;
   }

   @Override
   public void func_111121_a(AttributeModifier var1) {
      if (this.func_111127_a(☃.func_111167_a()) != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         Set<AttributeModifier> ☃ = (Set)this.field_111134_d.get(☃.func_111166_b());
         if (☃ == null) {
            ☃ = Sets.<AttributeModifier>newHashSet();
            this.field_111134_d.put(☃.func_111166_b(), ☃);
         }

         ((Set)this.field_111137_c.get(☃.func_111169_c())).add(☃);
         ☃.add(☃);
         this.field_111135_e.put(☃.func_111167_a(), ☃);
         this.func_111131_f();
      }
   }

   protected void func_111131_f() {
      this.field_111133_g = true;
      this.field_111138_a.func_180794_a(this);
   }

   @Override
   public void func_111124_b(AttributeModifier var1) {
      for(int ☃ = 0; ☃ < 3; ++☃) {
         Set<AttributeModifier> ☃x = (Set)this.field_111137_c.get(☃);
         ☃x.remove(☃);
      }

      Set<AttributeModifier> ☃ = (Set)this.field_111134_d.get(☃.func_111166_b());
      if (☃ != null) {
         ☃.remove(☃);
         if (☃.isEmpty()) {
            this.field_111134_d.remove(☃.func_111166_b());
         }
      }

      this.field_111135_e.remove(☃.func_111167_a());
      this.func_111131_f();
   }

   @Override
   public void func_188479_b(UUID var1) {
      AttributeModifier ☃ = this.func_111127_a(☃);
      if (☃ != null) {
         this.func_111124_b(☃);
      }
   }

   @Override
   public double func_111126_e() {
      if (this.field_111133_g) {
         this.field_111139_h = this.func_111129_g();
         this.field_111133_g = false;
      }

      return this.field_111139_h;
   }

   private double func_111129_g() {
      double ☃ = this.func_111125_b();

      for(AttributeModifier ☃x : this.func_180375_b(0)) {
         ☃ += ☃x.func_111164_d();
      }

      double ☃x = ☃;

      for(AttributeModifier ☃xx : this.func_180375_b(1)) {
         ☃x += ☃ * ☃xx.func_111164_d();
      }

      for(AttributeModifier ☃xx : this.func_180375_b(2)) {
         ☃x *= 1.0 + ☃xx.func_111164_d();
      }

      return this.field_111136_b.func_111109_a(☃x);
   }

   private Collection<AttributeModifier> func_180375_b(int var1) {
      Set<AttributeModifier> ☃ = Sets.<AttributeModifier>newHashSet(this.func_111130_a(☃));

      for(IAttribute ☃x = this.field_111136_b.func_180372_d(); ☃x != null; ☃x = ☃x.func_180372_d()) {
         IAttributeInstance ☃xx = this.field_111138_a.func_111151_a(☃x);
         if (☃xx != null) {
            ☃.addAll(☃xx.func_111130_a(☃));
         }
      }

      return ☃;
   }
}
