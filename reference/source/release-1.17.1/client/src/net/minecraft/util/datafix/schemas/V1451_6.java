package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;

public class V1451_6 extends NamespacedSchema {
   public static final String SPECIAL_OBJECTIVE_MARKER = "_special";
   protected static final HookFunction UNPACK_OBJECTIVE_ID = new HookFunction() {
      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         Dynamic<T> â˜ƒ = new Dynamic<>(â˜ƒ, â˜ƒ);
         return DataFixUtils.orElse(
               â˜ƒ.get("CriteriaName")
                  .asString()
                  .get()
                  .left()
                  .map(var0 -> {
                     int â˜ƒ = var0.indexOf(58);
                     if (â˜ƒ < 0) {
                        return Pair.of("_special", var0);
                     } else {
                        try {
                           ResourceLocation â˜ƒ = ResourceLocation.of(var0.substring(0, â˜ƒ), '.');
                           ResourceLocation â˜ƒx = ResourceLocation.of(var0.substring(â˜ƒ + 1), '.');
                           return Pair.of(â˜ƒ.toString(), â˜ƒx.toString());
                        } catch (Exception var4) {
                           return Pair.of("_special", var0);
                        }
                     }
                  })
                  .map(
                     var1x -> â˜ƒ.set(
                           "CriteriaType",
                           â˜ƒ.createMap(
                              ImmutableMap.of(
                                 â˜ƒ.createString("type"),
                                 â˜ƒ.createString((String)var1x.getFirst()),
                                 â˜ƒ.createString("id"),
                                 â˜ƒ.createString((String)var1x.getSecond())
                              )
                           )
                        )
                  ),
               â˜ƒ
            )
            .getValue();
      }
   };
   protected static final HookFunction REPACK_OBJECTIVE_ID = new HookFunction() {
      private String packWithDot(String var1) {
         ResourceLocation â˜ƒ = ResourceLocation.tryParse(â˜ƒ);
         return â˜ƒ != null ? â˜ƒ.getNamespace() + "." + â˜ƒ.getPath() : â˜ƒ;
      }

      @Override
      public <T> T apply(DynamicOps<T> var1, T var2) {
         Dynamic<T> â˜ƒ = new Dynamic<>(â˜ƒ, â˜ƒ);
         Optional<Dynamic<T>> â˜ƒx = â˜ƒ.get("CriteriaType")
            .get()
            .get()
            .left()
            .flatMap(
               var2x -> {
                  Optional<String> â˜ƒ = var2x.get("type").asString().get().left();
                  Optional<String> â˜ƒx = var2x.get("id").asString().get().left();
                  if (â˜ƒ.isPresent() && â˜ƒx.isPresent()) {
                     String â˜ƒxx = (String)â˜ƒ.get();
                     return â˜ƒxx.equals("_special")
                        ? Optional.of(â˜ƒ.createString((String)â˜ƒx.get()))
                        : Optional.of(var2x.createString(this.packWithDot(â˜ƒxx) + ":" + this.packWithDot((String)â˜ƒx.get())));
                  } else {
                     return Optional.empty();
                  }
               }
            );
         return DataFixUtils.orElse(â˜ƒx.map(var1x -> â˜ƒ.set("CriteriaName", var1x).remove("CriteriaType")), â˜ƒ).getValue();
      }
   };

   public V1451_6(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      Supplier<TypeTemplate> â˜ƒ = () -> DSL.compoundList(References.ITEM_NAME.in(â˜ƒ), DSL.constType(DSL.intType()));
      â˜ƒ.registerType(
         false,
         References.STATS,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(References.BLOCK_NAME.in(â˜ƒ), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  (TypeTemplate)â˜ƒ.get(),
                  "minecraft:used",
                  (TypeTemplate)â˜ƒ.get(),
                  "minecraft:broken",
                  (TypeTemplate)â˜ƒ.get(),
                  "minecraft:picked_up",
                  (TypeTemplate)â˜ƒ.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     (TypeTemplate)â˜ƒ.get(),
                     "minecraft:killed",
                     DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(References.ENTITY_NAME.in(â˜ƒ), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(namespacedString()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
      Map<String, Supplier<TypeTemplate>> â˜ƒx = createCriterionTypes(â˜ƒ);
      â˜ƒ.registerType(
         false,
         References.OBJECTIVE,
         () -> DSL.hook(DSL.optionalFields("CriteriaType", DSL.taggedChoiceLazy("type", DSL.string(), â˜ƒ)), UNPACK_OBJECTIVE_ID, REPACK_OBJECTIVE_ID)
      );
   }

   protected static Map<String, Supplier<TypeTemplate>> createCriterionTypes(Schema var0) {
      Supplier<TypeTemplate> â˜ƒ = () -> DSL.optionalFields("id", References.ITEM_NAME.in(â˜ƒ));
      Supplier<TypeTemplate> â˜ƒx = () -> DSL.optionalFields("id", References.BLOCK_NAME.in(â˜ƒ));
      Supplier<TypeTemplate> â˜ƒxx = () -> DSL.optionalFields("id", References.ENTITY_NAME.in(â˜ƒ));
      Map<String, Supplier<TypeTemplate>> â˜ƒxxx = Maps.newHashMap();
      â˜ƒxxx.put("minecraft:mined", â˜ƒx);
      â˜ƒxxx.put("minecraft:crafted", â˜ƒ);
      â˜ƒxxx.put("minecraft:used", â˜ƒ);
      â˜ƒxxx.put("minecraft:broken", â˜ƒ);
      â˜ƒxxx.put("minecraft:picked_up", â˜ƒ);
      â˜ƒxxx.put("minecraft:dropped", â˜ƒ);
      â˜ƒxxx.put("minecraft:killed", â˜ƒxx);
      â˜ƒxxx.put("minecraft:killed_by", â˜ƒxx);
      â˜ƒxxx.put("minecraft:custom", (Supplier)() -> DSL.optionalFields("id", DSL.constType(namespacedString())));
      â˜ƒxxx.put("_special", (Supplier)() -> DSL.optionalFields("id", DSL.constType(DSL.string())));
      return â˜ƒxxx;
   }
}
