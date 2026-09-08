package net.minecraft.entity.ai.attributes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.LowerStringMap;

public abstract class AbstractAttributeMap {
   protected final Map<IAttribute, IAttributeInstance> field_111154_a = Maps.<IAttribute, IAttributeInstance>newHashMap();
   protected final Map<String, IAttributeInstance> field_111153_b = new LowerStringMap();
   protected final Multimap<IAttribute, IAttribute> field_180377_c = HashMultimap.create();

   public IAttributeInstance func_111151_a(IAttribute var1) {
      return (IAttributeInstance)this.field_111154_a.get(☃);
   }

   @Nullable
   public IAttributeInstance func_111152_a(String var1) {
      return (IAttributeInstance)this.field_111153_b.get(☃);
   }

   public IAttributeInstance func_111150_b(IAttribute var1) {
      if (this.field_111153_b.containsKey(☃.func_111108_a())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      } else {
         IAttributeInstance ☃ = this.func_180376_c(☃);
         this.field_111153_b.put(☃.func_111108_a(), ☃);
         this.field_111154_a.put(☃, ☃);

         for(IAttribute ☃x = ☃.func_180372_d(); ☃x != null; ☃x = ☃x.func_180372_d()) {
            this.field_180377_c.put(☃x, ☃);
         }

         return ☃;
      }
   }

   protected abstract IAttributeInstance func_180376_c(IAttribute var1);

   public Collection<IAttributeInstance> func_111146_a() {
      return this.field_111153_b.values();
   }

   public void func_180794_a(IAttributeInstance var1) {
   }

   public void func_111148_a(Multimap<String, AttributeModifier> var1) {
      for(Entry<String, AttributeModifier> ☃ : ☃.entries()) {
         IAttributeInstance ☃x = this.func_111152_a((String)☃.getKey());
         if (☃x != null) {
            ☃x.func_111124_b((AttributeModifier)☃.getValue());
         }
      }
   }

   public void func_111147_b(Multimap<String, AttributeModifier> var1) {
      for(Entry<String, AttributeModifier> ☃ : ☃.entries()) {
         IAttributeInstance ☃x = this.func_111152_a((String)☃.getKey());
         if (☃x != null) {
            ☃x.func_111124_b((AttributeModifier)☃.getValue());
            ☃x.func_111121_a((AttributeModifier)☃.getValue());
         }
      }
   }
}
