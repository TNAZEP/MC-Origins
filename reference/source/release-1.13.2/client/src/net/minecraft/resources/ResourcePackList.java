package net.minecraft.resources;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ResourcePackList<T extends ResourcePackInfo> {
   private final Set<IPackFinder> field_198987_a = Sets.<IPackFinder>newHashSet();
   private final Map<String, T> field_198988_b = Maps.newLinkedHashMap();
   private final List<T> field_198989_c = Lists.<T>newLinkedList();
   private final ResourcePackInfo.IFactory<T> field_198990_d;

   public ResourcePackList(ResourcePackInfo.IFactory<T> var1) {
      this.field_198990_d = ☃;
   }

   public void func_198983_a() {
      Set<String> ☃ = (Set)this.field_198989_c.stream().map(ResourcePackInfo::func_195790_f).collect(Collectors.toCollection(LinkedHashSet::new));
      this.field_198988_b.clear();
      this.field_198989_c.clear();

      for(IPackFinder ☃x : this.field_198987_a) {
         ☃x.func_195730_a(this.field_198988_b, this.field_198990_d);
      }

      this.func_198986_e();
      this.field_198989_c
         .addAll((Collection)☃.stream().map(this.field_198988_b::get).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new)));

      for(T ☃x : this.field_198988_b.values()) {
         if (☃x.func_195797_g() && !this.field_198989_c.contains(☃x)) {
            ☃x.func_195792_i().func_198993_a(this.field_198989_c, ☃x, Functions.identity(), false);
         }
      }
   }

   private void func_198986_e() {
      List<Entry<String, T>> ☃ = Lists.newArrayList(this.field_198988_b.entrySet());
      this.field_198988_b.clear();
      ☃.stream().sorted(Entry.comparingByKey()).forEachOrdered(var1x -> var1x.getKey());
   }

   public void func_198985_a(Collection<T> var1) {
      this.field_198989_c.clear();
      this.field_198989_c.addAll(☃);

      for(T ☃ : this.field_198988_b.values()) {
         if (☃.func_195797_g() && !this.field_198989_c.contains(☃)) {
            ☃.func_195792_i().func_198993_a(this.field_198989_c, ☃, Functions.identity(), false);
         }
      }
   }

   public Collection<T> func_198978_b() {
      return this.field_198988_b.values();
   }

   public Collection<T> func_198979_c() {
      Collection<T> ☃ = Lists.<T>newArrayList(this.field_198988_b.values());
      ☃.removeAll(this.field_198989_c);
      return ☃;
   }

   public Collection<T> func_198980_d() {
      return this.field_198989_c;
   }

   @Nullable
   public T func_198981_a(String var1) {
      return (T)this.field_198988_b.get(☃);
   }

   public void func_198982_a(IPackFinder var1) {
      this.field_198987_a.add(☃);
   }
}
