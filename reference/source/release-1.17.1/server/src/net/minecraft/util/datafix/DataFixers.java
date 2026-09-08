package net.minecraft.util.datafix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.AbstractArrowPickupFix;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.AdvancementsFix;
import net.minecraft.util.datafix.fixes.AdvancementsRenameFix;
import net.minecraft.util.datafix.fixes.AttributesRename;
import net.minecraft.util.datafix.fixes.BedBlockEntityInjecter;
import net.minecraft.util.datafix.fixes.BedItemColorFix;
import net.minecraft.util.datafix.fixes.BeehivePoiRenameFix;
import net.minecraft.util.datafix.fixes.BiomeFix;
import net.minecraft.util.datafix.fixes.BitStorageAlignFix;
import net.minecraft.util.datafix.fixes.BlockEntityBannerColorFix;
import net.minecraft.util.datafix.fixes.BlockEntityBlockStateFix;
import net.minecraft.util.datafix.fixes.BlockEntityCustomNameToComponentFix;
import net.minecraft.util.datafix.fixes.BlockEntityIdFix;
import net.minecraft.util.datafix.fixes.BlockEntityJukeboxFix;
import net.minecraft.util.datafix.fixes.BlockEntityKeepPacked;
import net.minecraft.util.datafix.fixes.BlockEntityShulkerBoxColorFix;
import net.minecraft.util.datafix.fixes.BlockEntitySignTextStrictJsonFix;
import net.minecraft.util.datafix.fixes.BlockEntityUUIDFix;
import net.minecraft.util.datafix.fixes.BlockNameFlatteningFix;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.BlockRenameFixWithJigsaw;
import net.minecraft.util.datafix.fixes.BlockStateStructureTemplateFix;
import net.minecraft.util.datafix.fixes.CatTypeFix;
import net.minecraft.util.datafix.fixes.CauldronRenameFix;
import net.minecraft.util.datafix.fixes.ChunkBiomeFix;
import net.minecraft.util.datafix.fixes.ChunkLightRemoveFix;
import net.minecraft.util.datafix.fixes.ChunkPalettedStorageFix;
import net.minecraft.util.datafix.fixes.ChunkStatusFix;
import net.minecraft.util.datafix.fixes.ChunkStatusFix2;
import net.minecraft.util.datafix.fixes.ChunkStructuresTemplateRenameFix;
import net.minecraft.util.datafix.fixes.ChunkToProtochunkFix;
import net.minecraft.util.datafix.fixes.ColorlessShulkerEntityFix;
import net.minecraft.util.datafix.fixes.DyeItemRenameFix;
import net.minecraft.util.datafix.fixes.EntityArmorStandSilentFix;
import net.minecraft.util.datafix.fixes.EntityBlockStateFix;
import net.minecraft.util.datafix.fixes.EntityCatSplitFix;
import net.minecraft.util.datafix.fixes.EntityCodSalmonFix;
import net.minecraft.util.datafix.fixes.EntityCustomNameToComponentFix;
import net.minecraft.util.datafix.fixes.EntityElderGuardianSplitFix;
import net.minecraft.util.datafix.fixes.EntityEquipmentToArmorAndHandFix;
import net.minecraft.util.datafix.fixes.EntityHealthFix;
import net.minecraft.util.datafix.fixes.EntityHorseSaddleFix;
import net.minecraft.util.datafix.fixes.EntityHorseSplitFix;
import net.minecraft.util.datafix.fixes.EntityIdFix;
import net.minecraft.util.datafix.fixes.EntityItemFrameDirectionFix;
import net.minecraft.util.datafix.fixes.EntityMinecartIdentifiersFix;
import net.minecraft.util.datafix.fixes.EntityPaintingItemFrameDirectionFix;
import net.minecraft.util.datafix.fixes.EntityPaintingMotiveFix;
import net.minecraft.util.datafix.fixes.EntityProjectileOwnerFix;
import net.minecraft.util.datafix.fixes.EntityPufferfishRenameFix;
import net.minecraft.util.datafix.fixes.EntityRavagerRenameFix;
import net.minecraft.util.datafix.fixes.EntityRedundantChanceTagsFix;
import net.minecraft.util.datafix.fixes.EntityRidingToPassengersFix;
import net.minecraft.util.datafix.fixes.EntityShulkerColorFix;
import net.minecraft.util.datafix.fixes.EntityShulkerRotationFix;
import net.minecraft.util.datafix.fixes.EntitySkeletonSplitFix;
import net.minecraft.util.datafix.fixes.EntityStringUuidFix;
import net.minecraft.util.datafix.fixes.EntityTheRenameningFix;
import net.minecraft.util.datafix.fixes.EntityTippedArrowFix;
import net.minecraft.util.datafix.fixes.EntityUUIDFix;
import net.minecraft.util.datafix.fixes.EntityWolfColorFix;
import net.minecraft.util.datafix.fixes.EntityZombieSplitFix;
import net.minecraft.util.datafix.fixes.EntityZombieVillagerTypeFix;
import net.minecraft.util.datafix.fixes.EntityZombifiedPiglinRenameFix;
import net.minecraft.util.datafix.fixes.ForcePoiRebuild;
import net.minecraft.util.datafix.fixes.FurnaceRecipeFix;
import net.minecraft.util.datafix.fixes.GossipUUIDFix;
import net.minecraft.util.datafix.fixes.HeightmapRenamingFix;
import net.minecraft.util.datafix.fixes.IglooMetadataRemovalFix;
import net.minecraft.util.datafix.fixes.ItemBannerColorFix;
import net.minecraft.util.datafix.fixes.ItemCustomNameToComponentFix;
import net.minecraft.util.datafix.fixes.ItemIdFix;
import net.minecraft.util.datafix.fixes.ItemLoreFix;
import net.minecraft.util.datafix.fixes.ItemPotionFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.util.datafix.fixes.ItemShulkerBoxColorFix;
import net.minecraft.util.datafix.fixes.ItemSpawnEggFix;
import net.minecraft.util.datafix.fixes.ItemStackEnchantmentNamesFix;
import net.minecraft.util.datafix.fixes.ItemStackMapIdFix;
import net.minecraft.util.datafix.fixes.ItemStackSpawnEggFix;
import net.minecraft.util.datafix.fixes.ItemStackTheFlatteningFix;
import net.minecraft.util.datafix.fixes.ItemStackUUIDFix;
import net.minecraft.util.datafix.fixes.ItemWaterPotionFix;
import net.minecraft.util.datafix.fixes.ItemWrittenBookPagesStrictJsonFix;
import net.minecraft.util.datafix.fixes.JigsawPropertiesFix;
import net.minecraft.util.datafix.fixes.JigsawRotationFix;
import net.minecraft.util.datafix.fixes.LeavesFix;
import net.minecraft.util.datafix.fixes.LevelDataGeneratorOptionsFix;
import net.minecraft.util.datafix.fixes.LevelFlatGeneratorInfoFix;
import net.minecraft.util.datafix.fixes.LevelUUIDFix;
import net.minecraft.util.datafix.fixes.MapIdFix;
import net.minecraft.util.datafix.fixes.MemoryExpiryDataFix;
import net.minecraft.util.datafix.fixes.MissingDimensionFix;
import net.minecraft.util.datafix.fixes.MobSpawnerEntityIdentifiersFix;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.NewVillageFix;
import net.minecraft.util.datafix.fixes.ObjectiveDisplayNameFix;
import net.minecraft.util.datafix.fixes.ObjectiveRenderTypeFix;
import net.minecraft.util.datafix.fixes.OminousBannerBlockEntityRenameFix;
import net.minecraft.util.datafix.fixes.OminousBannerRenameFix;
import net.minecraft.util.datafix.fixes.OptionsAddTextBackgroundFix;
import net.minecraft.util.datafix.fixes.OptionsForceVBOFix;
import net.minecraft.util.datafix.fixes.OptionsKeyLwjgl3Fix;
import net.minecraft.util.datafix.fixes.OptionsKeyTranslationFix;
import net.minecraft.util.datafix.fixes.OptionsLowerCaseLanguageFix;
import net.minecraft.util.datafix.fixes.OptionsRenameFieldFix;
import net.minecraft.util.datafix.fixes.PlayerUUIDFix;
import net.minecraft.util.datafix.fixes.RecipesFix;
import net.minecraft.util.datafix.fixes.RecipesRenameFix;
import net.minecraft.util.datafix.fixes.RecipesRenameningFix;
import net.minecraft.util.datafix.fixes.RedstoneWireConnectionsFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.fixes.RemoveGolemGossipFix;
import net.minecraft.util.datafix.fixes.RenameBiomesFix;
import net.minecraft.util.datafix.fixes.RenamedCoralFansFix;
import net.minecraft.util.datafix.fixes.RenamedCoralFix;
import net.minecraft.util.datafix.fixes.ReorganizePoi;
import net.minecraft.util.datafix.fixes.SavedDataFeaturePoolElementFix;
import net.minecraft.util.datafix.fixes.SavedDataUUIDFix;
import net.minecraft.util.datafix.fixes.SavedDataVillageCropFix;
import net.minecraft.util.datafix.fixes.StatsCounterFix;
import net.minecraft.util.datafix.fixes.StatsRenameFix;
import net.minecraft.util.datafix.fixes.StriderGravityFix;
import net.minecraft.util.datafix.fixes.StructureReferenceCountFix;
import net.minecraft.util.datafix.fixes.TeamDisplayNameFix;
import net.minecraft.util.datafix.fixes.TrappedChestBlockEntityFix;
import net.minecraft.util.datafix.fixes.VillagerDataFix;
import net.minecraft.util.datafix.fixes.VillagerFollowRangeFix;
import net.minecraft.util.datafix.fixes.VillagerRebuildLevelAndXpFix;
import net.minecraft.util.datafix.fixes.VillagerTradeFix;
import net.minecraft.util.datafix.fixes.WallPropertyFix;
import net.minecraft.util.datafix.fixes.WorldGenSettingsFix;
import net.minecraft.util.datafix.fixes.WriteAndReadFix;
import net.minecraft.util.datafix.fixes.ZombieVillagerRebuildXpFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;
import net.minecraft.util.datafix.schemas.V102;
import net.minecraft.util.datafix.schemas.V1022;
import net.minecraft.util.datafix.schemas.V106;
import net.minecraft.util.datafix.schemas.V107;
import net.minecraft.util.datafix.schemas.V1125;
import net.minecraft.util.datafix.schemas.V135;
import net.minecraft.util.datafix.schemas.V143;
import net.minecraft.util.datafix.schemas.V1451;
import net.minecraft.util.datafix.schemas.V1451_1;
import net.minecraft.util.datafix.schemas.V1451_2;
import net.minecraft.util.datafix.schemas.V1451_3;
import net.minecraft.util.datafix.schemas.V1451_4;
import net.minecraft.util.datafix.schemas.V1451_5;
import net.minecraft.util.datafix.schemas.V1451_6;
import net.minecraft.util.datafix.schemas.V1451_7;
import net.minecraft.util.datafix.schemas.V1460;
import net.minecraft.util.datafix.schemas.V1466;
import net.minecraft.util.datafix.schemas.V1470;
import net.minecraft.util.datafix.schemas.V1481;
import net.minecraft.util.datafix.schemas.V1483;
import net.minecraft.util.datafix.schemas.V1486;
import net.minecraft.util.datafix.schemas.V1510;
import net.minecraft.util.datafix.schemas.V1800;
import net.minecraft.util.datafix.schemas.V1801;
import net.minecraft.util.datafix.schemas.V1904;
import net.minecraft.util.datafix.schemas.V1906;
import net.minecraft.util.datafix.schemas.V1909;
import net.minecraft.util.datafix.schemas.V1920;
import net.minecraft.util.datafix.schemas.V1928;
import net.minecraft.util.datafix.schemas.V1929;
import net.minecraft.util.datafix.schemas.V1931;
import net.minecraft.util.datafix.schemas.V2100;
import net.minecraft.util.datafix.schemas.V2501;
import net.minecraft.util.datafix.schemas.V2502;
import net.minecraft.util.datafix.schemas.V2505;
import net.minecraft.util.datafix.schemas.V2509;
import net.minecraft.util.datafix.schemas.V2519;
import net.minecraft.util.datafix.schemas.V2522;
import net.minecraft.util.datafix.schemas.V2551;
import net.minecraft.util.datafix.schemas.V2568;
import net.minecraft.util.datafix.schemas.V2571;
import net.minecraft.util.datafix.schemas.V2684;
import net.minecraft.util.datafix.schemas.V2686;
import net.minecraft.util.datafix.schemas.V2688;
import net.minecraft.util.datafix.schemas.V2704;
import net.minecraft.util.datafix.schemas.V2707;
import net.minecraft.util.datafix.schemas.V501;
import net.minecraft.util.datafix.schemas.V700;
import net.minecraft.util.datafix.schemas.V701;
import net.minecraft.util.datafix.schemas.V702;
import net.minecraft.util.datafix.schemas.V703;
import net.minecraft.util.datafix.schemas.V704;
import net.minecraft.util.datafix.schemas.V705;
import net.minecraft.util.datafix.schemas.V808;
import net.minecraft.util.datafix.schemas.V99;

public class DataFixers {
   private static final BiFunction<Integer, Schema, Schema> SAME = Schema::new;
   private static final BiFunction<Integer, Schema, Schema> SAME_NAMESPACED = NamespacedSchema::new;
   private static final DataFixer DATA_FIXER = createFixerUpper();

   private static DataFixer createFixerUpper() {
      DataFixerBuilder â˜ƒ = new DataFixerBuilder(SharedConstants.getCurrentVersion().getWorldVersion());
      addFixers(â˜ƒ);
      return â˜ƒ.build(Util.bootstrapExecutor());
   }

   public static DataFixer getDataFixer() {
      return DATA_FIXER;
   }

   private static void addFixers(DataFixerBuilder var0) {
      Schema â˜ƒ = â˜ƒ.addSchema(99, V99::new);
      Schema â˜ƒx = â˜ƒ.addSchema(100, V100::new);
      â˜ƒ.addFixer(new EntityEquipmentToArmorAndHandFix(â˜ƒx, true));
      Schema â˜ƒxx = â˜ƒ.addSchema(101, SAME);
      â˜ƒ.addFixer(new BlockEntitySignTextStrictJsonFix(â˜ƒxx, false));
      Schema â˜ƒxxx = â˜ƒ.addSchema(102, V102::new);
      â˜ƒ.addFixer(new ItemIdFix(â˜ƒxxx, true));
      â˜ƒ.addFixer(new ItemPotionFix(â˜ƒxxx, false));
      Schema â˜ƒxxxx = â˜ƒ.addSchema(105, SAME);
      â˜ƒ.addFixer(new ItemSpawnEggFix(â˜ƒxxxx, true));
      Schema â˜ƒxxxxx = â˜ƒ.addSchema(106, V106::new);
      â˜ƒ.addFixer(new MobSpawnerEntityIdentifiersFix(â˜ƒxxxxx, true));
      Schema â˜ƒxxxxxx = â˜ƒ.addSchema(107, V107::new);
      â˜ƒ.addFixer(new EntityMinecartIdentifiersFix(â˜ƒxxxxxx, true));
      Schema â˜ƒxxxxxxx = â˜ƒ.addSchema(108, SAME);
      â˜ƒ.addFixer(new EntityStringUuidFix(â˜ƒxxxxxxx, true));
      Schema â˜ƒxxxxxxxx = â˜ƒ.addSchema(109, SAME);
      â˜ƒ.addFixer(new EntityHealthFix(â˜ƒxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxx = â˜ƒ.addSchema(110, SAME);
      â˜ƒ.addFixer(new EntityHorseSaddleFix(â˜ƒxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxx = â˜ƒ.addSchema(111, SAME);
      â˜ƒ.addFixer(new EntityPaintingItemFrameDirectionFix(â˜ƒxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxx = â˜ƒ.addSchema(113, SAME);
      â˜ƒ.addFixer(new EntityRedundantChanceTagsFix(â˜ƒxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxx = â˜ƒ.addSchema(135, V135::new);
      â˜ƒ.addFixer(new EntityRidingToPassengersFix(â˜ƒxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxx = â˜ƒ.addSchema(143, V143::new);
      â˜ƒ.addFixer(new EntityTippedArrowFix(â˜ƒxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxx = â˜ƒ.addSchema(147, SAME);
      â˜ƒ.addFixer(new EntityArmorStandSilentFix(â˜ƒxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.addSchema(165, SAME);
      â˜ƒ.addFixer(new ItemWrittenBookPagesStrictJsonFix(â˜ƒxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(501, V501::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxx, "Add 1.10 entities fix", References.ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(502, SAME);
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxx,
            "cooked_fished item renamer",
            var0x -> Objects.equals(NamespacedSchema.ensureNamespaced(var0x), "minecraft:cooked_fished") ? "minecraft:cooked_fish" : var0x
         )
      );
      â˜ƒ.addFixer(new EntityZombieVillagerTypeFix(â˜ƒxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(505, SAME);
      â˜ƒ.addFixer(new OptionsForceVBOFix(â˜ƒxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(700, V700::new);
      â˜ƒ.addFixer(new EntityElderGuardianSplitFix(â˜ƒxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(701, V701::new);
      â˜ƒ.addFixer(new EntitySkeletonSplitFix(â˜ƒxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(702, V702::new);
      â˜ƒ.addFixer(new EntityZombieSplitFix(â˜ƒxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(703, V703::new);
      â˜ƒ.addFixer(new EntityHorseSplitFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(704, V704::new);
      â˜ƒ.addFixer(new BlockEntityIdFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(705, V705::new);
      â˜ƒ.addFixer(new EntityIdFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(804, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ItemBannerColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(806, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ItemWaterPotionFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(808, V808::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shulker box", References.BLOCK_ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(808, 1, SAME_NAMESPACED);
      â˜ƒ.addFixer(new EntityShulkerColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(813, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ItemShulkerBoxColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new BlockEntityShulkerBoxColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(816, SAME_NAMESPACED);
      â˜ƒ.addFixer(new OptionsLowerCaseLanguageFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(820, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         ItemRenameFix.create(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "totem item renamer", createRenamer("minecraft:totem", "minecraft:totem_of_undying"))
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1022, V1022::new);
      â˜ƒ.addFixer(new WriteAndReadFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shoulder entities to players", References.PLAYER));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1125, V1125::new);
      â˜ƒ.addFixer(new BedBlockEntityInjecter(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(new BedItemColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1344, SAME_NAMESPACED);
      â˜ƒ.addFixer(new OptionsKeyLwjgl3Fix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1446, SAME_NAMESPACED);
      â˜ƒ.addFixer(new OptionsKeyTranslationFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1450, SAME_NAMESPACED);
      â˜ƒ.addFixer(new BlockStateStructureTemplateFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, V1451::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "AddTrappedChestFix", References.BLOCK_ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 1, V1451_1::new);
      â˜ƒ.addFixer(new ChunkPalettedStorageFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 2, V1451_2::new);
      â˜ƒ.addFixer(new BlockEntityBlockStateFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 3, V1451_3::new);
      â˜ƒ.addFixer(new EntityBlockStateFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(new ItemStackMapIdFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 4, V1451_4::new);
      â˜ƒ.addFixer(new BlockNameFlatteningFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(new ItemStackTheFlatteningFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 5, V1451_5::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "RemoveNoteBlockFlowerPotFix", References.BLOCK_ENTITY));
      â˜ƒ.addFixer(new ItemStackSpawnEggFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new EntityWolfColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new BlockEntityBannerColorFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new LevelFlatGeneratorInfoFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 6, V1451_6::new);
      â˜ƒ.addFixer(new StatsCounterFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(new WriteAndReadFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rewrite objectives", References.OBJECTIVE));
      â˜ƒ.addFixer(new BlockEntityJukeboxFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 7, V1451_7::new);
      â˜ƒ.addFixer(new SavedDataVillageCropFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1451, 7, SAME_NAMESPACED);
      â˜ƒ.addFixer(new VillagerTradeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1456, SAME_NAMESPACED);
      â˜ƒ.addFixer(new EntityItemFrameDirectionFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1458, SAME_NAMESPACED);
      â˜ƒ.addFixer(new EntityCustomNameToComponentFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new ItemCustomNameToComponentFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new BlockEntityCustomNameToComponentFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1460, V1460::new);
      â˜ƒ.addFixer(new EntityPaintingMotiveFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1466, V1466::new);
      â˜ƒ.addFixer(new ChunkToProtochunkFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1470, V1470::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add 1.13 entities fix", References.ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1474, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ColorlessShulkerEntityFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker block fixer",
            var0x -> Objects.equals(NamespacedSchema.ensureNamespaced(var0x), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : var0x
         )
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker item fixer",
            var0x -> Objects.equals(NamespacedSchema.ensureNamespaced(var0x), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : var0x
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1475, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Flowing fixer",
            createRenamer(ImmutableMap.of("minecraft:flowing_water", "minecraft:water", "minecraft:flowing_lava", "minecraft:lava"))
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1480, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         BlockRenameFix.create(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral blocks", createRenamer(RenamedCoralFix.RENAMED_IDS))
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral items", createRenamer(RenamedCoralFix.RENAMED_IDS))
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1481, V1481::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add conduit", References.BLOCK_ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1483, V1483::new);
      â˜ƒ.addFixer(new EntityPufferfishRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename pufferfish egg item", createRenamer(EntityPufferfishRenameFix.RENAMED_IDS)
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1484, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass items",
            createRenamer(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass blocks",
            createRenamer(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
         )
      );
      â˜ƒ.addFixer(new HeightmapRenamingFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1486, V1486::new);
      â˜ƒ.addFixer(new EntityCodSalmonFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename cod/salmon egg items", createRenamer(EntityCodSalmonFix.RENAMED_EGG_IDS)
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1487, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* blocks",
            createRenamer(
               ImmutableMap.of(
                  "minecraft:prismarine_bricks_slab",
                  "minecraft:prismarine_brick_slab",
                  "minecraft:prismarine_bricks_stairs",
                  "minecraft:prismarine_brick_stairs"
               )
            )
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* items",
            createRenamer(
               ImmutableMap.of(
                  "minecraft:prismarine_bricks_slab",
                  "minecraft:prismarine_brick_slab",
                  "minecraft:prismarine_bricks_stairs",
                  "minecraft:prismarine_brick_stairs"
               )
            )
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1488, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename kelp/kelptop",
            createRenamer(ImmutableMap.of("minecraft:kelp_top", "minecraft:kelp", "minecraft:kelp", "minecraft:kelp_plant"))
         )
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename kelptop", createRenamer("minecraft:kelp_top", "minecraft:kelp")
         )
      );
      â˜ƒ.addFixer(
         new NamedEntityFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block block entity custom name fix",
            References.BLOCK_ENTITY,
            "minecraft:command_block"
         ) {
            @Override
            protected Typed<?> fix(Typed<?> var1) {
               return â˜ƒ.update(DSL.remainderFinder(), EntityCustomNameToComponentFix::fixTagCustomName);
            }
         }
      );
      â˜ƒ.addFixer(
         new NamedEntityFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block minecart custom name fix",
            References.ENTITY,
            "minecraft:commandblock_minecart"
         ) {
            @Override
            protected Typed<?> fix(Typed<?> var1) {
               return â˜ƒ.update(DSL.remainderFinder(), EntityCustomNameToComponentFix::fixTagCustomName);
            }
         }
      );
      â˜ƒ.addFixer(new IglooMetadataRemovalFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1490, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename melon_block", createRenamer("minecraft:melon_block", "minecraft:melon")
         )
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename melon_block/melon/speckled_melon",
            createRenamer(
               ImmutableMap.of(
                  "minecraft:melon_block",
                  "minecraft:melon",
                  "minecraft:melon",
                  "minecraft:melon_slice",
                  "minecraft:speckled_melon",
                  "minecraft:glistering_melon_slice"
               )
            )
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1492, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ChunkStructuresTemplateRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1494, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ItemStackEnchantmentNamesFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1496, SAME_NAMESPACED);
      â˜ƒ.addFixer(new LeavesFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1500, SAME_NAMESPACED);
      â˜ƒ.addFixer(new BlockEntityKeepPacked(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1501, SAME_NAMESPACED);
      â˜ƒ.addFixer(new AdvancementsFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1502, SAME_NAMESPACED);
      â˜ƒ.addFixer(new RecipesFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1506, SAME_NAMESPACED);
      â˜ƒ.addFixer(new LevelDataGeneratorOptionsFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1510, V1510::new);
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Block renamening fix",
            createRenamer(EntityTheRenameningFix.RENAMED_BLOCKS)
         )
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Item renamening fix", createRenamer(EntityTheRenameningFix.RENAMED_ITEMS)
         )
      );
      â˜ƒ.addFixer(new RecipesRenameningFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new EntityTheRenameningFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(
         new StatsRenameFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "SwimStatsRenameFix",
            ImmutableMap.of("minecraft:swim_one_cm", "minecraft:walk_on_water_one_cm", "minecraft:dive_one_cm", "minecraft:walk_under_water_one_cm")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1514, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ObjectiveDisplayNameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new TeamDisplayNameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new ObjectiveRenderTypeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1515, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename coral fan blocks",
            createRenamer(RenamedCoralFansFix.RENAMED_IDS)
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1624, SAME_NAMESPACED);
      â˜ƒ.addFixer(new TrappedChestBlockEntityFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1800, V1800::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added 1.14 mobs fix", References.ENTITY));
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename dye items", createRenamer(DyeItemRenameFix.RENAMED_IDS)
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1801, V1801::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Illager Beast", References.ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1802, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename sign blocks & stone slabs",
            createRenamer(
               ImmutableMap.of(
                  "minecraft:stone_slab",
                  "minecraft:smooth_stone_slab",
                  "minecraft:sign",
                  "minecraft:oak_sign",
                  "minecraft:wall_sign",
                  "minecraft:oak_wall_sign"
               )
            )
         )
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename sign item & stone slabs",
            createRenamer(ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign"))
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1803, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ItemLoreFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1904, V1904::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Cats", References.ENTITY));
      â˜ƒ.addFixer(new EntityCatSplitFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1905, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ChunkStatusFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1906, V1906::new);
      â˜ƒ.addFixer(
         new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add POI Blocks", References.BLOCK_ENTITY)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1909, V1909::new);
      â˜ƒ.addFixer(new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add jigsaw", References.BLOCK_ENTITY));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1911, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ChunkStatusFix2(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1917, SAME_NAMESPACED);
      â˜ƒ.addFixer(new CatTypeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1918, SAME_NAMESPACED);
      â˜ƒ.addFixer(new VillagerDataFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"));
      â˜ƒ.addFixer(new VillagerDataFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:zombie_villager"));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1920, V1920::new);
      â˜ƒ.addFixer(new NewVillageFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(
         new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add campfire", References.BLOCK_ENTITY)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1925, SAME_NAMESPACED);
      â˜ƒ.addFixer(new MapIdFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1928, V1928::new);
      â˜ƒ.addFixer(new EntityRavagerRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename ravager egg item",
            createRenamer(EntityRavagerRenameFix.RENAMED_IDS)
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1929, V1929::new);
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Add Wandering Trader and Trader Llama",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1931, V1931::new);
      â˜ƒ.addFixer(
         new AddNewChoices(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Fox", References.ENTITY)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1936, SAME_NAMESPACED);
      â˜ƒ.addFixer(new OptionsAddTextBackgroundFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1946, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ReorganizePoi(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1948, SAME_NAMESPACED);
      â˜ƒ.addFixer(new OminousBannerRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1953, SAME_NAMESPACED);
      â˜ƒ.addFixer(new OminousBannerBlockEntityRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1955, SAME_NAMESPACED);
      â˜ƒ.addFixer(new VillagerRebuildLevelAndXpFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(new ZombieVillagerRebuildXpFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1961, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ChunkLightRemoveFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(1963, SAME_NAMESPACED);
      â˜ƒ.addFixer(new RemoveGolemGossipFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2100, V2100::new);
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Bee and Bee Stinger", References.ENTITY
         )
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add beehive", References.BLOCK_ENTITY
         )
      );
      â˜ƒ.addFixer(
         new RecipesRenameFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename sugar recipe",
            createRenamer("minecraft:sugar", "sugar_from_sugar_cane")
         )
      );
      â˜ƒ.addFixer(
         new AdvancementsRenameFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename sugar recipe advancement",
            createRenamer("minecraft:recipes/misc/sugar", "minecraft:recipes/misc/sugar_from_sugar_cane")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2202, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ChunkBiomeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2209, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename bee_hive item to beehive",
            createRenamer("minecraft:bee_hive", "minecraft:beehive")
         )
      );
      â˜ƒ.addFixer(new BeehivePoiRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename bee_hive block to beehive",
            createRenamer("minecraft:bee_hive", "minecraft:beehive")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2211, SAME_NAMESPACED);
      â˜ƒ.addFixer(new StructureReferenceCountFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2218, SAME_NAMESPACED);
      â˜ƒ.addFixer(new ForcePoiRebuild(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2501, V2501::new);
      â˜ƒ.addFixer(new FurnaceRecipeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2502, V2502::new);
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Hoglin", References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2503, SAME_NAMESPACED);
      â˜ƒ.addFixer(new WallPropertyFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      â˜ƒ.addFixer(
         new AdvancementsRenameFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Composter category change",
            createRenamer("minecraft:recipes/misc/composter", "minecraft:recipes/decorations/composter")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2505, V2505::new);
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Piglin", References.ENTITY
         )
      );
      â˜ƒ.addFixer(
         new MemoryExpiryDataFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2508, SAME_NAMESPACED);
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed fungi items to fungus",
            createRenamer(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed fungi blocks to fungus",
            createRenamer(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2509, V2509::new);
      â˜ƒ.addFixer(
         new EntityZombifiedPiglinRenameFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename zombie pigman egg item",
            createRenamer(EntityZombifiedPiglinRenameFix.RENAMED_IDS)
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2511, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(new EntityProjectileOwnerFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2514, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(new EntityUUIDFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      â˜ƒ.addFixer(new BlockEntityUUIDFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      â˜ƒ.addFixer(new PlayerUUIDFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      â˜ƒ.addFixer(new LevelUUIDFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      â˜ƒ.addFixer(new SavedDataUUIDFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      â˜ƒ.addFixer(new ItemStackUUIDFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2516, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new GossipUUIDFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"
         )
      );
      â˜ƒ.addFixer(
         new GossipUUIDFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:zombie_villager"
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2518, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new JigsawPropertiesFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      â˜ƒ.addFixer(
         new JigsawRotationFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(2519, V2519::new);
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Strider",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2522, V2522::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Zoglin",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2523, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(new AttributesRename(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2527, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(new BitStorageAlignFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2528, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename soul fire torch and soul fire lantern",
            createRenamer(ImmutableMap.of("minecraft:soul_fire_torch", "minecraft:soul_torch", "minecraft:soul_fire_lantern", "minecraft:soul_lantern"))
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename soul fire torch and soul fire lantern",
            createRenamer(
               ImmutableMap.of(
                  "minecraft:soul_fire_torch",
                  "minecraft:soul_torch",
                  "minecraft:soul_fire_wall_torch",
                  "minecraft:soul_wall_torch",
                  "minecraft:soul_fire_lantern",
                  "minecraft:soul_lantern"
               )
            )
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2529, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new StriderGravityFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2531, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new RedstoneWireConnectionsFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2533, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new VillagerFollowRangeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2535, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new EntityShulkerRotationFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2550, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new WorldGenSettingsFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2551, V2551::new
      );
      â˜ƒ.addFixer(
         new WriteAndReadFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "add types to WorldGenData",
            References.WORLD_GEN_SETTINGS
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2552, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new RenameBiomesFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Nether biome rename",
            ImmutableMap.of("minecraft:nether", "minecraft:nether_wastes")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2553, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new BiomeFix(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2558, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new MissingDimensionFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false
         )
      );
      â˜ƒ.addFixer(
         new OptionsRenameFieldFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename swapHands setting",
            "key_key.swapHands",
            "key_key.swapOffhand"
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2568, V2568::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Piglin Brute",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2571, V2571::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Goat",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2679, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new CauldronRenameFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2680, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed grass path item to dirt path",
            createRenamer("minecraft:grass_path", "minecraft:dirt_path")
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFixWithJigsaw.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed grass path block to dirt path",
            createRenamer("minecraft:grass_path", "minecraft:dirt_path")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2684, V2684::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Sculk Sensor",
            References.BLOCK_ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2686, V2686::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Axolotl",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2688, V2688::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Glow Squid",
            References.ENTITY
         )
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Glow Item Frame",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2690, SAME_NAMESPACED
      );
      ImmutableMap<String, String> â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ImmutableMap.builder(
            
         )
         .put("minecraft:weathered_copper_block", "minecraft:oxidized_copper_block")
         .put("minecraft:semi_weathered_copper_block", "minecraft:weathered_copper_block")
         .put("minecraft:lightly_weathered_copper_block", "minecraft:exposed_copper_block")
         .put("minecraft:weathered_cut_copper", "minecraft:oxidized_cut_copper")
         .put("minecraft:semi_weathered_cut_copper", "minecraft:weathered_cut_copper")
         .put("minecraft:lightly_weathered_cut_copper", "minecraft:exposed_cut_copper")
         .put("minecraft:weathered_cut_copper_stairs", "minecraft:oxidized_cut_copper_stairs")
         .put("minecraft:semi_weathered_cut_copper_stairs", "minecraft:weathered_cut_copper_stairs")
         .put("minecraft:lightly_weathered_cut_copper_stairs", "minecraft:exposed_cut_copper_stairs")
         .put("minecraft:weathered_cut_copper_slab", "minecraft:oxidized_cut_copper_slab")
         .put("minecraft:semi_weathered_cut_copper_slab", "minecraft:weathered_cut_copper_slab")
         .put("minecraft:lightly_weathered_cut_copper_slab", "minecraft:exposed_cut_copper_slab")
         .put("minecraft:waxed_semi_weathered_copper", "minecraft:waxed_weathered_copper")
         .put("minecraft:waxed_lightly_weathered_copper", "minecraft:waxed_exposed_copper")
         .put("minecraft:waxed_semi_weathered_cut_copper", "minecraft:waxed_weathered_cut_copper")
         .put("minecraft:waxed_lightly_weathered_cut_copper", "minecraft:waxed_exposed_cut_copper")
         .put("minecraft:waxed_semi_weathered_cut_copper_stairs", "minecraft:waxed_weathered_cut_copper_stairs")
         .put("minecraft:waxed_lightly_weathered_cut_copper_stairs", "minecraft:waxed_exposed_cut_copper_stairs")
         .put("minecraft:waxed_semi_weathered_cut_copper_slab", "minecraft:waxed_weathered_cut_copper_slab")
         .put("minecraft:waxed_lightly_weathered_cut_copper_slab", "minecraft:waxed_exposed_cut_copper_slab")
         .build();
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed copper block items to new oxidized terms",
            createRenamer(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            )
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFixWithJigsaw.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed copper blocks to new oxidized terms",
            createRenamer(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            )
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2691, SAME_NAMESPACED
      );
      ImmutableMap<String, String> â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ImmutableMap.builder(
            
         )
         .put("minecraft:waxed_copper", "minecraft:waxed_copper_block")
         .put("minecraft:oxidized_copper_block", "minecraft:oxidized_copper")
         .put("minecraft:weathered_copper_block", "minecraft:weathered_copper")
         .put("minecraft:exposed_copper_block", "minecraft:exposed_copper")
         .build();
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename copper item suffixes",
            createRenamer(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            )
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFixWithJigsaw.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename copper blocks suffixes",
            createRenamer(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            )
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2696, SAME_NAMESPACED
      );
      ImmutableMap<String, String> â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ImmutableMap.builder(
            
         )
         .put("minecraft:grimstone", "minecraft:deepslate")
         .put("minecraft:grimstone_slab", "minecraft:cobbled_deepslate_slab")
         .put("minecraft:grimstone_stairs", "minecraft:cobbled_deepslate_stairs")
         .put("minecraft:grimstone_wall", "minecraft:cobbled_deepslate_wall")
         .put("minecraft:polished_grimstone", "minecraft:polished_deepslate")
         .put("minecraft:polished_grimstone_slab", "minecraft:polished_deepslate_slab")
         .put("minecraft:polished_grimstone_stairs", "minecraft:polished_deepslate_stairs")
         .put("minecraft:polished_grimstone_wall", "minecraft:polished_deepslate_wall")
         .put("minecraft:grimstone_tiles", "minecraft:deepslate_tiles")
         .put("minecraft:grimstone_tile_slab", "minecraft:deepslate_tile_slab")
         .put("minecraft:grimstone_tile_stairs", "minecraft:deepslate_tile_stairs")
         .put("minecraft:grimstone_tile_wall", "minecraft:deepslate_tile_wall")
         .put("minecraft:grimstone_bricks", "minecraft:deepslate_bricks")
         .put("minecraft:grimstone_brick_slab", "minecraft:deepslate_brick_slab")
         .put("minecraft:grimstone_brick_stairs", "minecraft:deepslate_brick_stairs")
         .put("minecraft:grimstone_brick_wall", "minecraft:deepslate_brick_wall")
         .put("minecraft:chiseled_grimstone", "minecraft:chiseled_deepslate")
         .build();
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed grimstone block items to deepslate",
            createRenamer(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            )
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFixWithJigsaw.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed grimstone blocks to deepslate",
            createRenamer(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            )
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2700, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         BlockRenameFixWithJigsaw.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed cave vines blocks",
            createRenamer(ImmutableMap.of("minecraft:cave_vines_head", "minecraft:cave_vines", "minecraft:cave_vines_body", "minecraft:cave_vines_plant"))
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2701, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new SavedDataFeaturePoolElementFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2702, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new AbstractArrowPickupFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2704, V2704::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Goat",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2707, V2707::new
      );
      â˜ƒ.addFixer(
         new AddNewChoices(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Marker",
            References.ENTITY
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2710, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         new StatsRenameFix(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed play_one_minute stat to play_time",
            ImmutableMap.of("minecraft:play_one_minute", "minecraft:play_time")
         )
      );
      Schema â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.addSchema(
         2717, SAME_NAMESPACED
      );
      â˜ƒ.addFixer(
         ItemRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename azalea_leaves_flowers",
            createRenamer(ImmutableMap.of("minecraft:azalea_leaves_flowers", "minecraft:flowering_azalea_leaves"))
         )
      );
      â˜ƒ.addFixer(
         BlockRenameFix.create(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename azalea_leaves_flowers items",
            createRenamer(ImmutableMap.of("minecraft:azalea_leaves_flowers", "minecraft:flowering_azalea_leaves"))
         )
      );
   }

   private static UnaryOperator<String> createRenamer(Map<String, String> var0) {
      return var1 -> (String)â˜ƒ.getOrDefault(var1, var1);
   }

   private static UnaryOperator<String> createRenamer(String var0, String var1) {
      return var2 -> Objects.equals(var2, â˜ƒ) ? â˜ƒ : var2;
   }
}
