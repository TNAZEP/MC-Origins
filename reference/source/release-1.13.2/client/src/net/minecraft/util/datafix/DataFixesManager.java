package net.minecraft.util.datafix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import net.minecraft.util.datafix.fixes.AddBedTileEntity;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.AdvancementsFix;
import net.minecraft.util.datafix.fixes.ArmorStandSilent;
import net.minecraft.util.datafix.fixes.BannerItemColor;
import net.minecraft.util.datafix.fixes.BedItemColor;
import net.minecraft.util.datafix.fixes.BiomeRenames;
import net.minecraft.util.datafix.fixes.BlockEntityBannerColor;
import net.minecraft.util.datafix.fixes.BlockEntityKeepPacked;
import net.minecraft.util.datafix.fixes.BlockNameFlattening;
import net.minecraft.util.datafix.fixes.BlockRename;
import net.minecraft.util.datafix.fixes.BlockStateFlattenGenOptions;
import net.minecraft.util.datafix.fixes.BlockStateFlattenStructures;
import net.minecraft.util.datafix.fixes.BlockStateFlattenVillageCrops;
import net.minecraft.util.datafix.fixes.BlockStateFlatternEntities;
import net.minecraft.util.datafix.fixes.BookPagesStrictJSON;
import net.minecraft.util.datafix.fixes.ChunkGenStatus;
import net.minecraft.util.datafix.fixes.ChunkPaletteFormat;
import net.minecraft.util.datafix.fixes.ChunkStructuresTemplateRenameFix;
import net.minecraft.util.datafix.fixes.ColorlessShulkerEntityFix;
import net.minecraft.util.datafix.fixes.CoralFansRenameList;
import net.minecraft.util.datafix.fixes.CustomNameStringToComponentEntity;
import net.minecraft.util.datafix.fixes.CustomNameStringToComponentFixTileEntity;
import net.minecraft.util.datafix.fixes.CustomNameStringToComponentItem;
import net.minecraft.util.datafix.fixes.ElderGuardianSplit;
import net.minecraft.util.datafix.fixes.EntityArmorAndHeld;
import net.minecraft.util.datafix.fixes.EntityCodSalmonFix;
import net.minecraft.util.datafix.fixes.EntityHealth;
import net.minecraft.util.datafix.fixes.EntityId;
import net.minecraft.util.datafix.fixes.EntityItemFrameFacing;
import net.minecraft.util.datafix.fixes.EntityRenameing1510;
import net.minecraft.util.datafix.fixes.ForceVBOOn;
import net.minecraft.util.datafix.fixes.HeightmapRenamingFix;
import net.minecraft.util.datafix.fixes.HorseSaddle;
import net.minecraft.util.datafix.fixes.HorseSplit;
import net.minecraft.util.datafix.fixes.IglooMetadataRemoval;
import net.minecraft.util.datafix.fixes.ItemFilledMapMetadata;
import net.minecraft.util.datafix.fixes.ItemIntIDToString;
import net.minecraft.util.datafix.fixes.ItemRename;
import net.minecraft.util.datafix.fixes.ItemSpawnEggSplit;
import net.minecraft.util.datafix.fixes.ItemStackDataFlattening;
import net.minecraft.util.datafix.fixes.ItemStackEnchantmentFix;
import net.minecraft.util.datafix.fixes.JukeboxRecordItem;
import net.minecraft.util.datafix.fixes.KeyOptionsTranslation;
import net.minecraft.util.datafix.fixes.LWJGL3KeyOptions;
import net.minecraft.util.datafix.fixes.LeavesFix;
import net.minecraft.util.datafix.fixes.LevelDataGeneratorOptionsFix;
import net.minecraft.util.datafix.fixes.MinecartEntityTypes;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.ObjectiveDisplayName;
import net.minecraft.util.datafix.fixes.ObjectiveRenderType;
import net.minecraft.util.datafix.fixes.OptionsLowerCaseLanguage;
import net.minecraft.util.datafix.fixes.PaintingDirection;
import net.minecraft.util.datafix.fixes.PaintingMotive;
import net.minecraft.util.datafix.fixes.PistonPushedBlock;
import net.minecraft.util.datafix.fixes.PotionItems;
import net.minecraft.util.datafix.fixes.PotionWater;
import net.minecraft.util.datafix.fixes.PufferfishRename;
import net.minecraft.util.datafix.fixes.RecipesRenaming;
import net.minecraft.util.datafix.fixes.RedundantChanceTags;
import net.minecraft.util.datafix.fixes.RenamedCoral;
import net.minecraft.util.datafix.fixes.RenamedRecipes;
import net.minecraft.util.datafix.fixes.RidingToPassengers;
import net.minecraft.util.datafix.fixes.ShulkerBoxEntityColor;
import net.minecraft.util.datafix.fixes.ShulkerBoxItemColor;
import net.minecraft.util.datafix.fixes.ShulkerBoxTileColor;
import net.minecraft.util.datafix.fixes.SignStrictJSON;
import net.minecraft.util.datafix.fixes.SkeletonSplit;
import net.minecraft.util.datafix.fixes.SpawnEggNames;
import net.minecraft.util.datafix.fixes.SpawnerEntityTypes;
import net.minecraft.util.datafix.fixes.StatsRenaming;
import net.minecraft.util.datafix.fixes.StringToUUID;
import net.minecraft.util.datafix.fixes.SwimStatsRename;
import net.minecraft.util.datafix.fixes.TeamDisplayName;
import net.minecraft.util.datafix.fixes.TileEntityId;
import net.minecraft.util.datafix.fixes.TippedArrow;
import net.minecraft.util.datafix.fixes.TrappedChestTileEntitySplit;
import net.minecraft.util.datafix.fixes.VillagerTrades;
import net.minecraft.util.datafix.fixes.WolfCollarColor;
import net.minecraft.util.datafix.fixes.ZombieProfToType;
import net.minecraft.util.datafix.fixes.ZombieSplit;
import net.minecraft.util.datafix.versions.V0099;
import net.minecraft.util.datafix.versions.V0100;
import net.minecraft.util.datafix.versions.V0102;
import net.minecraft.util.datafix.versions.V0106;
import net.minecraft.util.datafix.versions.V0107;
import net.minecraft.util.datafix.versions.V0135;
import net.minecraft.util.datafix.versions.V0143;
import net.minecraft.util.datafix.versions.V0501;
import net.minecraft.util.datafix.versions.V0700;
import net.minecraft.util.datafix.versions.V0701;
import net.minecraft.util.datafix.versions.V0702;
import net.minecraft.util.datafix.versions.V0703;
import net.minecraft.util.datafix.versions.V0704;
import net.minecraft.util.datafix.versions.V0705;
import net.minecraft.util.datafix.versions.V0808;
import net.minecraft.util.datafix.versions.V1022;
import net.minecraft.util.datafix.versions.V1125;
import net.minecraft.util.datafix.versions.V1451;
import net.minecraft.util.datafix.versions.V1451_1;
import net.minecraft.util.datafix.versions.V1451_2;
import net.minecraft.util.datafix.versions.V1451_3;
import net.minecraft.util.datafix.versions.V1451_4;
import net.minecraft.util.datafix.versions.V1451_5;
import net.minecraft.util.datafix.versions.V1451_6;
import net.minecraft.util.datafix.versions.V1451_7;
import net.minecraft.util.datafix.versions.V1460;
import net.minecraft.util.datafix.versions.V1466;
import net.minecraft.util.datafix.versions.V1470;
import net.minecraft.util.datafix.versions.V1481;
import net.minecraft.util.datafix.versions.V1483;
import net.minecraft.util.datafix.versions.V1486;
import net.minecraft.util.datafix.versions.V1510;

public class DataFixesManager {
   private static final BiFunction<Integer, Schema, Schema> field_207592_e = Schema::new;
   private static final BiFunction<Integer, Schema, Schema> field_207593_f = NamespacedSchema::new;
   private static final DataFixer field_210902_d = func_188279_a();

   private static DataFixer func_188279_a() {
      DataFixerBuilder ☃ = new DataFixerBuilder(1631);
      func_210891_a(☃);
      return ☃.build(ForkJoinPool.commonPool());
   }

   public static DataFixer func_210901_a() {
      return field_210902_d;
   }

   private static void func_210891_a(DataFixerBuilder var0) {
      Schema ☃ = ☃.addSchema(99, V0099::new);
      Schema ☃x = ☃.addSchema(100, V0100::new);
      ☃.addFixer(new EntityArmorAndHeld(☃x, true));
      Schema ☃xx = ☃.addSchema(101, field_207592_e);
      ☃.addFixer(new SignStrictJSON(☃xx, false));
      Schema ☃xxx = ☃.addSchema(102, V0102::new);
      ☃.addFixer(new ItemIntIDToString(☃xxx, true));
      ☃.addFixer(new PotionItems(☃xxx, false));
      Schema ☃xxxx = ☃.addSchema(105, field_207592_e);
      ☃.addFixer(new SpawnEggNames(☃xxxx, true));
      Schema ☃xxxxx = ☃.addSchema(106, V0106::new);
      ☃.addFixer(new SpawnerEntityTypes(☃xxxxx, true));
      Schema ☃xxxxxx = ☃.addSchema(107, V0107::new);
      ☃.addFixer(new MinecartEntityTypes(☃xxxxxx, true));
      Schema ☃xxxxxxx = ☃.addSchema(108, field_207592_e);
      ☃.addFixer(new StringToUUID(☃xxxxxxx, true));
      Schema ☃xxxxxxxx = ☃.addSchema(109, field_207592_e);
      ☃.addFixer(new EntityHealth(☃xxxxxxxx, true));
      Schema ☃xxxxxxxxx = ☃.addSchema(110, field_207592_e);
      ☃.addFixer(new HorseSaddle(☃xxxxxxxxx, true));
      Schema ☃xxxxxxxxxx = ☃.addSchema(111, field_207592_e);
      ☃.addFixer(new PaintingDirection(☃xxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxx = ☃.addSchema(113, field_207592_e);
      ☃.addFixer(new RedundantChanceTags(☃xxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxx = ☃.addSchema(135, V0135::new);
      ☃.addFixer(new RidingToPassengers(☃xxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxx = ☃.addSchema(143, V0143::new);
      ☃.addFixer(new TippedArrow(☃xxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxx = ☃.addSchema(147, field_207592_e);
      ☃.addFixer(new ArmorStandSilent(☃xxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxx = ☃.addSchema(165, field_207592_e);
      ☃.addFixer(new BookPagesStrictJSON(☃xxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxx = ☃.addSchema(501, V0501::new);
      ☃.addFixer(new AddNewChoices(☃xxxxxxxxxxxxxxxx, "Add 1.10 entities fix", TypeReferences.field_211299_o));
      Schema ☃xxxxxxxxxxxxxxxxx = ☃.addSchema(502, field_207592_e);
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxx,
            "cooked_fished item renamer",
            var0x -> Objects.equals(NamespacedSchema.func_206477_f(var0x), "minecraft:cooked_fished") ? "minecraft:cooked_fish" : var0x
         )
      );
      ☃.addFixer(new ZombieProfToType(☃xxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxx = ☃.addSchema(505, field_207592_e);
      ☃.addFixer(new ForceVBOOn(☃xxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxx = ☃.addSchema(700, V0700::new);
      ☃.addFixer(new ElderGuardianSplit(☃xxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxx = ☃.addSchema(701, V0701::new);
      ☃.addFixer(new SkeletonSplit(☃xxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(702, V0702::new);
      ☃.addFixer(new ZombieSplit(☃xxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(703, V0703::new);
      ☃.addFixer(new HorseSplit(☃xxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(704, V0704::new);
      ☃.addFixer(new TileEntityId(☃xxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(705, V0705::new);
      ☃.addFixer(new EntityId(☃xxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(804, field_207593_f);
      ☃.addFixer(new BannerItemColor(☃xxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(806, field_207593_f);
      ☃.addFixer(new PotionWater(☃xxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(808, V0808::new);
      ☃.addFixer(new AddNewChoices(☃xxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shulker box", TypeReferences.field_211294_j));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(808, 1, field_207593_f);
      ☃.addFixer(new ShulkerBoxEntityColor(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(813, field_207593_f);
      ☃.addFixer(new ShulkerBoxItemColor(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new ShulkerBoxTileColor(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(816, field_207593_f);
      ☃.addFixer(new OptionsLowerCaseLanguage(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(820, field_207593_f);
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "totem item renamer", var0x -> Objects.equals(var0x, "minecraft:totem") ? "minecraft:totem_of_undying" : var0x
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1022, V1022::new);
      ☃.addFixer(new WriteAndReadDataFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shoulder entities to players", TypeReferences.field_211286_b));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1125, V1125::new);
      ☃.addFixer(new AddBedTileEntity(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(new BedItemColor(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1344, field_207593_f);
      ☃.addFixer(new LWJGL3KeyOptions(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1446, field_207593_f);
      ☃.addFixer(new KeyOptionsTranslation(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1450, field_207593_f);
      ☃.addFixer(new BlockStateFlattenStructures(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, V1451::new);
      ☃.addFixer(new AddNewChoices(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "AddTrappedChestFix", TypeReferences.field_211294_j));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 1, V1451_1::new);
      ☃.addFixer(new ChunkPaletteFormat(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 2, V1451_2::new);
      ☃.addFixer(new PistonPushedBlock(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 3, V1451_3::new);
      ☃.addFixer(new BlockStateFlatternEntities(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(new ItemFilledMapMetadata(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 4, V1451_4::new);
      ☃.addFixer(new BlockNameFlattening(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(new ItemStackDataFlattening(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 5, V1451_5::new);
      ☃.addFixer(new AddNewChoices(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "RemoveNoteBlockFlowerPotFix", TypeReferences.field_211294_j));
      ☃.addFixer(new ItemSpawnEggSplit(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new WolfCollarColor(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new BlockEntityBannerColor(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new BlockStateFlattenGenOptions(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 6, V1451_6::new);
      ☃.addFixer(new StatsRenaming(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(new JukeboxRecordItem(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 7, V1451_7::new);
      ☃.addFixer(new BlockStateFlattenVillageCrops(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1451, 7, field_207593_f);
      ☃.addFixer(new VillagerTrades(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1456, field_207593_f);
      ☃.addFixer(new EntityItemFrameFacing(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1458, field_207593_f);
      ☃.addFixer(new CustomNameStringToComponentEntity(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new CustomNameStringToComponentItem(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new CustomNameStringToComponentFixTileEntity(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1460, V1460::new);
      ☃.addFixer(new PaintingMotive(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1466, V1466::new);
      ☃.addFixer(new ChunkGenStatus(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1470, V1470::new);
      ☃.addFixer(new AddNewChoices(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add 1.13 entities fix", TypeReferences.field_211299_o));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1474, field_207593_f);
      ☃.addFixer(new ColorlessShulkerEntityFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker block fixer",
            var0x -> Objects.equals(NamespacedSchema.func_206477_f(var0x), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : var0x
         )
      );
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker item fixer",
            var0x -> Objects.equals(NamespacedSchema.func_206477_f(var0x), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : var0x
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1475, field_207593_f);
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Flowing fixer",
            var0x -> (String)ImmutableMap.of("minecraft:flowing_water", "minecraft:water", "minecraft:flowing_lava", "minecraft:lava")
                  .getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1480, field_207593_f);
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename coral blocks",
            var0x -> (String)RenamedCoral.field_204918_a.getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename coral items",
            var0x -> (String)RenamedCoral.field_204918_a.getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1481, V1481::new);
      ☃.addFixer(new AddNewChoices(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add conduit", TypeReferences.field_211294_j));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1483, V1483::new);
      ☃.addFixer(new PufferfishRename(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename pufferfish egg item",
            var0x -> (String)PufferfishRename.field_207461_a.getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1484, field_207593_f);
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass items",
            var0x -> (String)ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass")
                  .getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass blocks",
            var0x -> (String)ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass")
                  .getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(new HeightmapRenamingFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1486, V1486::new);
      ☃.addFixer(new EntityCodSalmonFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename cod/salmon egg items",
            var0x -> (String)EntityCodSalmonFix.field_209759_b.getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1487, field_207593_f);
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* blocks",
            var0x -> (String)ImmutableMap.of(
                     "minecraft:prismarine_bricks_slab",
                     "minecraft:prismarine_brick_slab",
                     "minecraft:prismarine_bricks_stairs",
                     "minecraft:prismarine_brick_stairs"
                  )
                  .getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* items",
            var0x -> (String)ImmutableMap.of(
                     "minecraft:prismarine_bricks_slab",
                     "minecraft:prismarine_brick_slab",
                     "minecraft:prismarine_bricks_stairs",
                     "minecraft:prismarine_brick_stairs"
                  )
                  .getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1488, field_207593_f);
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename kelp/kelptop",
            var0x -> (String)ImmutableMap.of("minecraft:kelp_top", "minecraft:kelp", "minecraft:kelp", "minecraft:kelp_plant").getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename kelptop",
            var0x -> Objects.equals(var0x, "minecraft:kelp_top") ? "minecraft:kelp" : var0x
         )
      );
      ☃.addFixer(
         new NamedEntityFix(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block block entity custom name fix",
            TypeReferences.field_211294_j,
            "minecraft:command_block"
         ) {
            @Override
            protected Typed<?> func_207419_a(Typed<?> var1) {
               return ☃.update(DSL.remainderFinder(), CustomNameStringToComponentEntity::func_209740_a);
            }
         }
      );
      ☃.addFixer(
         new NamedEntityFix(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block minecart custom name fix",
            TypeReferences.field_211299_o,
            "minecraft:commandblock_minecart"
         ) {
            @Override
            protected Typed<?> func_207419_a(Typed<?> var1) {
               return ☃.update(DSL.remainderFinder(), CustomNameStringToComponentEntity::func_209740_a);
            }
         }
      );
      ☃.addFixer(new IglooMetadataRemoval(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1490, field_207593_f);
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename melon_block",
            var0x -> Objects.equals(var0x, "minecraft:melon_block") ? "minecraft:melon" : var0x
         )
      );
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename melon_block/melon/speckled_melon",
            var0x -> (String)ImmutableMap.of(
                     "minecraft:melon_block",
                     "minecraft:melon",
                     "minecraft:melon",
                     "minecraft:melon_slice",
                     "minecraft:speckled_melon",
                     "minecraft:glistering_melon_slice"
                  )
                  .getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1492, field_207593_f);
      ☃.addFixer(new ChunkStructuresTemplateRenameFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1494, field_207593_f);
      ☃.addFixer(new ItemStackEnchantmentFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1496, field_207593_f);
      ☃.addFixer(new LeavesFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1500, field_207593_f);
      ☃.addFixer(new BlockEntityKeepPacked(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1501, field_207593_f);
      ☃.addFixer(new AdvancementsFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1502, field_207593_f);
      ☃.addFixer(new RenamedRecipes(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1506, field_207593_f);
      ☃.addFixer(new LevelDataGeneratorOptionsFix(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1508, field_207593_f);
      ☃.addFixer(new BiomeRenames(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1510, V1510::new);
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Block renamening fix",
            var0x -> (String)EntityRenameing1510.field_210596_b.getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(
         ItemRename.func_207476_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Item renamening fix",
            var0x -> (String)EntityRenameing1510.field_210597_c.getOrDefault(var0x, var0x)
         )
      );
      ☃.addFixer(new RecipesRenaming(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new EntityRenameing1510(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      ☃.addFixer(new SwimStatsRename(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1514, field_207593_f);
      ☃.addFixer(new ObjectiveDisplayName(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new TeamDisplayName(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      ☃.addFixer(new ObjectiveRenderType(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1515, field_207593_f);
      ☃.addFixer(
         BlockRename.func_207437_a(
            ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename coral fan blocks",
            var0x -> (String)CoralFansRenameList.field_211870_a.getOrDefault(var0x, var0x)
         )
      );
      Schema ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.addSchema(1624, field_207593_f);
      ☃.addFixer(new TrappedChestTileEntitySplit(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
   }
}
