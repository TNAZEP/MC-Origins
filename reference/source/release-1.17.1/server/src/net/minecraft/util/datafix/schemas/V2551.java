package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V2551 extends NamespacedSchema {
   public V2551(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerType(
         false,
         References.WORLD_GEN_SETTINGS,
         () -> DSL.fields(
               "dimensions",
               DSL.compoundList(
                  DSL.constType(namespacedString()),
                  DSL.fields(
                     "generator",
                     DSL.taggedChoiceLazy(
                        "type",
                        DSL.string(),
                        ImmutableMap.of(
                           "minecraft:debug",
                           DSL::remainder,
                           "minecraft:flat",
                           (Supplier)() -> DSL.optionalFields(
                                 "settings",
                                 DSL.optionalFields(
                                    "biome", References.BIOME.in(â˜ƒ), "layers", DSL.list(DSL.optionalFields("block", References.BLOCK_NAME.in(â˜ƒ)))
                                 )
                              ),
                           "minecraft:noise",
                           (Supplier)() -> DSL.optionalFields(
                                 "biome_source",
                                 DSL.taggedChoiceLazy(
                                    "type",
                                    DSL.string(),
                                    ImmutableMap.of(
                                       "minecraft:fixed",
                                       (Supplier)() -> DSL.fields("biome", References.BIOME.in(â˜ƒ)),
                                       "minecraft:multi_noise",
                                       (Supplier)() -> DSL.list(DSL.fields("biome", References.BIOME.in(â˜ƒ))),
                                       "minecraft:checkerboard",
                                       (Supplier)() -> DSL.fields("biomes", DSL.list(References.BIOME.in(â˜ƒ))),
                                       "minecraft:vanilla_layered",
                                       DSL::remainder,
                                       "minecraft:the_end",
                                       DSL::remainder
                                    )
                                 ),
                                 "settings",
                                 DSL.or(
                                    DSL.constType(DSL.string()),
                                    DSL.optionalFields("default_block", References.BLOCK_NAME.in(â˜ƒ), "default_fluid", References.BLOCK_NAME.in(â˜ƒ))
                                 )
                              )
                        )
                     )
                  )
               )
            )
      );
   }
}
