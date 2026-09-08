package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureIO {
   private static final Logger field_151687_a = LogManager.getLogger();
   private static final Map<String, Class<? extends StructureStart>> field_143040_a = Maps.newHashMap();
   private static final Map<Class<? extends StructureStart>, String> field_143038_b = Maps.newHashMap();
   private static final Map<String, Class<? extends StructurePiece>> field_143039_c = Maps.newHashMap();
   private static final Map<Class<? extends StructurePiece>, String> field_143037_d = Maps.newHashMap();

   private static void func_143034_b(Class<? extends StructureStart> var0, String var1) {
      field_143040_a.put(☃, ☃);
      field_143038_b.put(☃, ☃);
   }

   public static void func_143031_a(Class<? extends StructurePiece> var0, String var1) {
      field_143039_c.put(☃, ☃);
      field_143037_d.put(☃, ☃);
   }

   public static String func_143033_a(StructureStart var0) {
      return (String)field_143038_b.get(☃.getClass());
   }

   public static String func_143036_a(StructurePiece var0) {
      return (String)field_143037_d.get(☃.getClass());
   }

   @Nullable
   public static StructureStart func_202602_a(NBTTagCompound var0, IWorld var1) {
      StructureStart ☃ = null;
      String ☃x = ☃.func_74779_i("id");
      if ("INVALID".equals(☃x)) {
         return Structure.field_202376_c;
      } else {
         try {
            Class<? extends StructureStart> ☃ = (Class)field_143040_a.get(☃x);
            if (☃ != null) {
               ☃ = (StructureStart)☃.newInstance();
            }
         } catch (Exception var5) {
            field_151687_a.warn("Failed Start with id {}", ☃x);
            var5.printStackTrace();
         }

         if (☃ != null) {
            ☃.func_143020_a(☃, ☃);
         } else {
            field_151687_a.warn("Skipping Structure with id {}", ☃x);
         }

         return ☃;
      }
   }

   public static StructurePiece func_143032_b(NBTTagCompound var0, IWorld var1) {
      StructurePiece ☃ = null;

      try {
         Class<? extends StructurePiece> ☃x = (Class)field_143039_c.get(☃.func_74779_i("id"));
         if (☃x != null) {
            ☃ = (StructurePiece)☃x.newInstance();
         }
      } catch (Exception var4) {
         field_151687_a.warn("Failed Piece with id {}", ☃.func_74779_i("id"));
         var4.printStackTrace();
      }

      if (☃ != null) {
         ☃.func_143009_a(☃, ☃);
      } else {
         field_151687_a.warn("Skipping Piece with id {}", ☃.func_74779_i("id"));
      }

      return ☃;
   }

   static {
      func_143034_b(MineshaftStructure.Start.class, "Mineshaft");
      func_143034_b(VillageStructure.Start.class, "Village");
      func_143034_b(FortressStructure.Start.class, "Fortress");
      func_143034_b(StrongholdStructure.Start.class, "Stronghold");
      func_143034_b(JunglePyramidStructure.Start.class, "Jungle_Pyramid");
      func_143034_b(OceanRuinStructure.Start.class, "Ocean_Ruin");
      func_143034_b(DesertPyramidStructure.Start.class, "Desert_Pyramid");
      func_143034_b(IglooStructure.Start.class, "Igloo");
      func_143034_b(SwampHutStructure.Start.class, "Swamp_Hut");
      func_143034_b(OceanMonumentStructure.Start.class, "Monument");
      func_143034_b(EndCityStructure.Start.class, "EndCity");
      func_143034_b(WoodlandMansionStructure.Start.class, "Mansion");
      func_143034_b(BuriedTreasureStructure.Start.class, "Buried_Treasure");
      func_143034_b(ShipwreckStructure.Start.class, "Shipwreck");
      MineshaftPieces.func_143048_a();
      VillagePieces.func_143016_a();
      FortressPieces.func_143049_a();
      StrongholdPieces.func_143046_a();
      JunglePyramidPiece.func_202585_af_();
      OceanRuinPieces.func_204046_a();
      IglooPieces.func_202591_ae_();
      SwampHutPiece.func_202595_b();
      DesertPyramidPiece.func_202597_ad_();
      OceanMonumentPieces.func_175970_a();
      EndCityPieces.func_186200_a();
      WoodlandMansionPieces.func_191153_a();
      BuriedTreasurePieces.func_204296_a();
      ShipwreckPieces.func_204759_a();
   }
}
