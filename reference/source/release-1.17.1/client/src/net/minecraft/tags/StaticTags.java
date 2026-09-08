package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class StaticTags {
   private static final Set<ResourceKey<?>> HELPERS_IDS = Sets.<ResourceKey<?>>newHashSet();
   private static final List<StaticTagHelper<?>> HELPERS = Lists.<StaticTagHelper<?>>newArrayList();

   public static <T> StaticTagHelper<T> create(ResourceKey<? extends Registry<T>> var0, String var1) {
      if (!HELPERS_IDS.add(â˜ƒ)) {
         throw new IllegalStateException("Duplicate entry for static tag collection: " + â˜ƒ);
      } else {
         StaticTagHelper<T> â˜ƒ = new StaticTagHelper<>(â˜ƒ, â˜ƒ);
         HELPERS.add(â˜ƒ);
         return â˜ƒ;
      }
   }

   public static void resetAll(TagContainer var0) {
      HELPERS.forEach(var1 -> var1.reset(â˜ƒ));
   }

   public static void resetAllToEmpty() {
      HELPERS.forEach(StaticTagHelper::resetToEmpty);
   }

   public static Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> getAllMissingTags(TagContainer var0) {
      Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> â˜ƒ = HashMultimap.create();
      HELPERS.forEach(var2 -> â˜ƒ.putAll(var2.getKey(), var2.getMissingTags(â˜ƒ)));
      return â˜ƒ;
   }

   public static void bootStrap() {
      makeSureAllKnownHelpersAreLoaded();
   }

   private static Set<StaticTagHelper<?>> getAllKnownHelpers() {
      return ImmutableSet.of(BlockTags.HELPER, ItemTags.HELPER, FluidTags.HELPER, EntityTypeTags.HELPER, GameEventTags.HELPER);
   }

   private static void makeSureAllKnownHelpersAreLoaded() {
      Set<ResourceKey<?>> â˜ƒ = (Set)getAllKnownHelpers().stream().map(StaticTagHelper::getKey).collect(Collectors.toSet());
      if (!Sets.difference(HELPERS_IDS, â˜ƒ).isEmpty()) {
         throw new IllegalStateException("Missing helper registrations");
      }
   }

   public static void visitHelpers(Consumer<StaticTagHelper<?>> var0) {
      HELPERS.forEach(â˜ƒ);
   }

   public static TagContainer createCollection() {
      TagContainer.Builder â˜ƒ = new TagContainer.Builder();
      makeSureAllKnownHelpersAreLoaded();
      HELPERS.forEach(var1 -> var1.addToCollection(â˜ƒ));
      return â˜ƒ.build();
   }
}
