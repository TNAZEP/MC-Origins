package net.minecraft.server.packs.repository;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class PackRepository implements AutoCloseable {
   private final Set<RepositorySource> sources;
   private Map<String, Pack> available = ImmutableMap.of();
   private List<Pack> selected = ImmutableList.of();
   private final Pack.PackConstructor constructor;

   public PackRepository(Pack.PackConstructor var1, RepositorySource... var2) {
      this.constructor = â˜ƒ;
      this.sources = ImmutableSet.copyOf(â˜ƒ);
   }

   public PackRepository(PackType var1, RepositorySource... var2) {
      this((var1x, var2x, var3, var4, var5, var6, var7) -> new Pack(var1x, var2x, var3, var4, var5, â˜ƒ, var6, var7), â˜ƒ);
   }

   public void reload() {
      List<String> â˜ƒ = (List)this.selected.stream().map(Pack::getId).collect(ImmutableList.toImmutableList());
      this.close();
      this.available = this.discoverAvailable();
      this.selected = this.rebuildSelected(â˜ƒ);
   }

   private Map<String, Pack> discoverAvailable() {
      Map<String, Pack> â˜ƒ = Maps.newTreeMap();

      for(RepositorySource â˜ƒx : this.sources) {
         â˜ƒx.loadPacks(var1x -> â˜ƒ.put(var1x.getId(), var1x), this.constructor);
      }

      return ImmutableMap.copyOf(â˜ƒ);
   }

   public void setSelected(Collection<String> var1) {
      this.selected = this.rebuildSelected(â˜ƒ);
   }

   private List<Pack> rebuildSelected(Collection<String> var1) {
      List<Pack> â˜ƒ = (List)this.getAvailablePacks(â˜ƒ).collect(Collectors.toList());

      for(Pack â˜ƒx : this.available.values()) {
         if (â˜ƒx.isRequired() && !â˜ƒ.contains(â˜ƒx)) {
            â˜ƒx.getDefaultPosition().insert(â˜ƒ, â˜ƒx, Functions.identity(), false);
         }
      }

      return ImmutableList.copyOf(â˜ƒ);
   }

   private Stream<Pack> getAvailablePacks(Collection<String> var1) {
      return â˜ƒ.stream().map(this.available::get).filter(Objects::nonNull);
   }

   public Collection<String> getAvailableIds() {
      return this.available.keySet();
   }

   public Collection<Pack> getAvailablePacks() {
      return this.available.values();
   }

   public Collection<String> getSelectedIds() {
      return (Collection<String>)this.selected.stream().map(Pack::getId).collect(ImmutableSet.toImmutableSet());
   }

   public Collection<Pack> getSelectedPacks() {
      return this.selected;
   }

   @Nullable
   public Pack getPack(String var1) {
      return (Pack)this.available.get(â˜ƒ);
   }

   public void close() {
      this.available.values().forEach(Pack::close);
   }

   public boolean isAvailable(String var1) {
      return this.available.containsKey(â˜ƒ);
   }

   public List<PackResources> openAllSelected() {
      return (List<PackResources>)this.selected.stream().map(Pack::open).collect(ImmutableList.toImmutableList());
   }
}
