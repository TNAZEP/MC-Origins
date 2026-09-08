package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.LowerStringMap;

public class AttributeMap extends AbstractAttributeMap {
   private final Set<IAttributeInstance> field_111162_d = Sets.<IAttributeInstance>newHashSet();
   protected final Map<String, IAttributeInstance> field_111163_c = new LowerStringMap();

   public ModifiableAttributeInstance func_111151_a(IAttribute var1) {
      return (ModifiableAttributeInstance)super.func_111151_a(☃);
   }

   public ModifiableAttributeInstance func_111152_a(String var1) {
      IAttributeInstance ☃ = super.func_111152_a(☃);
      if (☃ == null) {
         ☃ = (IAttributeInstance)this.field_111163_c.get(☃);
      }

      return (ModifiableAttributeInstance)☃;
   }

   @Override
   public IAttributeInstance func_111150_b(IAttribute var1) {
      IAttributeInstance ☃ = super.func_111150_b(☃);
      if (☃ instanceof RangedAttribute && ((RangedAttribute)☃).func_111116_f() != null) {
         this.field_111163_c.put(((RangedAttribute)☃).func_111116_f(), ☃);
      }

      return ☃;
   }

   @Override
   protected IAttributeInstance func_180376_c(IAttribute var1) {
      return new ModifiableAttributeInstance(this, ☃);
   }

   @Override
   public void func_180794_a(IAttributeInstance var1) {
      if (☃.func_111123_a().func_111111_c()) {
         this.field_111162_d.add(☃);
      }

      for(IAttribute ☃ : this.field_180377_c.get(☃.func_111123_a())) {
         ModifiableAttributeInstance ☃x = this.func_111151_a(☃);
         if (☃x != null) {
            ☃x.func_111131_f();
         }
      }
   }

   public Set<IAttributeInstance> func_111161_b() {
      return this.field_111162_d;
   }

   public Collection<IAttributeInstance> func_111160_c() {
      Set<IAttributeInstance> ☃ = Sets.<IAttributeInstance>newHashSet();

      for(IAttributeInstance ☃x : this.func_111146_a()) {
         if (☃x.func_111123_a().func_111111_c()) {
            ☃.add(☃x);
         }
      }

      return ☃;
   }
}
