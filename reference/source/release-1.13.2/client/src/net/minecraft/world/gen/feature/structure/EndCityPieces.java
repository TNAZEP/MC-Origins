package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class EndCityPieces {
   private static final PlacementSettings field_186202_b = new PlacementSettings().func_186222_a(true);
   private static final PlacementSettings field_186203_c = new PlacementSettings().func_186222_a(true).func_186225_a(Blocks.field_150350_a);
   private static final EndCityPieces.IGenerator field_186204_d = new EndCityPieces.IGenerator() {
      @Override
      public void func_186184_a() {
      }

      @Override
      public boolean func_191086_a(TemplateManager var1, int var2, EndCityPieces.CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         if (☃ > 8) {
            return false;
         } else {
            Rotation ☃ = ☃.field_186177_b.func_186215_c();
            EndCityPieces.CityTemplate ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃, ☃, "base_floor", ☃, true));
            int ☃xx = ☃.nextInt(3);
            if (☃xx == 0) {
               ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-1, 4, -1), "base_roof", ☃, true));
            } else if (☃xx == 1) {
               ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-1, 0, -1), "second_floor_2", ☃, false));
               ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-1, 8, -1), "second_roof", ☃, false));
               EndCityPieces.func_191088_b(☃, EndCityPieces.field_186206_f, ☃ + 1, ☃x, null, ☃, ☃);
            } else if (☃xx == 2) {
               ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-1, 0, -1), "second_floor_2", ☃, false));
               ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-1, 4, -1), "third_floor_2", ☃, false));
               ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-1, 8, -1), "third_roof", ☃, true));
               EndCityPieces.func_191088_b(☃, EndCityPieces.field_186206_f, ☃ + 1, ☃x, null, ☃, ☃);
            }

            return true;
         }
      }
   };
   private static final List<Tuple<Rotation, BlockPos>> field_186205_e = Lists.<Tuple<Rotation, BlockPos>>newArrayList(
      new Tuple<>(Rotation.NONE, new BlockPos(1, -1, 0)),
      new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)),
      new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)),
      new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6))
   );
   private static final EndCityPieces.IGenerator field_186206_f = new EndCityPieces.IGenerator() {
      @Override
      public void func_186184_a() {
      }

      @Override
      public boolean func_191086_a(TemplateManager var1, int var2, EndCityPieces.CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         Rotation ☃ = ☃.field_186177_b.func_186215_c();
         EndCityPieces.CityTemplate var8 = EndCityPieces.func_189935_b(
            ☃, EndCityPieces.func_191090_b(☃, ☃, new BlockPos(3 + ☃.nextInt(2), -3, 3 + ☃.nextInt(2)), "tower_base", ☃, true)
         );
         var8 = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, var8, new BlockPos(0, 7, 0), "tower_piece", ☃, true));
         EndCityPieces.CityTemplate ☃x = ☃.nextInt(3) == 0 ? var8 : null;
         int ☃xx = 1 + ☃.nextInt(3);

         for(int ☃xxx = 0; ☃xxx < ☃xx; ++☃xxx) {
            var8 = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, var8, new BlockPos(0, 4, 0), "tower_piece", ☃, true));
            if (☃xxx < ☃xx - 1 && ☃.nextBoolean()) {
               ☃x = var8;
            }
         }

         if (☃x != null) {
            for(Tuple<Rotation, BlockPos> ☃xxx : EndCityPieces.field_186205_e) {
               if (☃.nextBoolean()) {
                  EndCityPieces.CityTemplate ☃xxxx = EndCityPieces.func_189935_b(
                     ☃, EndCityPieces.func_191090_b(☃, ☃x, ☃xxx.func_76340_b(), "bridge_end", ☃.func_185830_a((Rotation)☃xxx.func_76341_a()), true)
                  );
                  EndCityPieces.func_191088_b(☃, EndCityPieces.field_186207_g, ☃ + 1, ☃xxxx, null, ☃, ☃);
               }
            }

            var8 = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, var8, new BlockPos(-1, 4, -1), "tower_top", ☃, true));
         } else {
            if (☃ != 7) {
               return EndCityPieces.func_191088_b(☃, EndCityPieces.field_186209_i, ☃ + 1, var8, null, ☃, ☃);
            }

            var8 = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, var8, new BlockPos(-1, 4, -1), "tower_top", ☃, true));
         }

         return true;
      }
   };
   private static final EndCityPieces.IGenerator field_186207_g = new EndCityPieces.IGenerator() {
      public boolean field_186186_a;

      @Override
      public void func_186184_a() {
         this.field_186186_a = false;
      }

      @Override
      public boolean func_191086_a(TemplateManager var1, int var2, EndCityPieces.CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         Rotation ☃ = ☃.field_186177_b.func_186215_c();
         int ☃x = ☃.nextInt(4) + 1;
         EndCityPieces.CityTemplate ☃xx = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃, new BlockPos(0, 0, -4), "bridge_piece", ☃, true));
         ☃xx.field_74886_g = -1;
         int ☃xxx = 0;

         for(int ☃xxxx = 0; ☃xxxx < ☃x; ++☃xxxx) {
            if (☃.nextBoolean()) {
               ☃xx = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃xx, new BlockPos(0, ☃xxx, -4), "bridge_piece", ☃, true));
               ☃xxx = 0;
            } else {
               if (☃.nextBoolean()) {
                  ☃xx = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃xx, new BlockPos(0, ☃xxx, -4), "bridge_steep_stairs", ☃, true));
               } else {
                  ☃xx = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃xx, new BlockPos(0, ☃xxx, -8), "bridge_gentle_stairs", ☃, true));
               }

               ☃xxx = 4;
            }
         }

         if (!this.field_186186_a && ☃.nextInt(10 - ☃) == 0) {
            EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃xx, new BlockPos(-8 + ☃.nextInt(8), ☃xxx, -70 + ☃.nextInt(10)), "ship", ☃, true));
            this.field_186186_a = true;
         } else if (!EndCityPieces.func_191088_b(☃, EndCityPieces.field_186204_d, ☃ + 1, ☃xx, new BlockPos(-3, ☃xxx + 1, -11), ☃, ☃)) {
            return false;
         }

         ☃xx = EndCityPieces.func_189935_b(
            ☃, EndCityPieces.func_191090_b(☃, ☃xx, new BlockPos(4, ☃xxx, 0), "bridge_end", ☃.func_185830_a(Rotation.CLOCKWISE_180), true)
         );
         ☃xx.field_74886_g = -1;
         return true;
      }
   };
   private static final List<Tuple<Rotation, BlockPos>> field_186208_h = Lists.<Tuple<Rotation, BlockPos>>newArrayList(
      new Tuple<>(Rotation.NONE, new BlockPos(4, -1, 0)),
      new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)),
      new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)),
      new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12))
   );
   private static final EndCityPieces.IGenerator field_186209_i = new EndCityPieces.IGenerator() {
      @Override
      public void func_186184_a() {
      }

      @Override
      public boolean func_191086_a(TemplateManager var1, int var2, EndCityPieces.CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6) {
         Rotation ☃ = ☃.field_186177_b.func_186215_c();
         EndCityPieces.CityTemplate ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃, new BlockPos(-3, 4, -3), "fat_tower_base", ☃, true));
         ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(0, 4, 0), "fat_tower_middle", ☃, true));

         for(int ☃xx = 0; ☃xx < 2 && ☃.nextInt(3) != 0; ++☃xx) {
            ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(0, 8, 0), "fat_tower_middle", ☃, true));

            for(Tuple<Rotation, BlockPos> ☃xxx : EndCityPieces.field_186208_h) {
               if (☃.nextBoolean()) {
                  EndCityPieces.CityTemplate ☃xxxx = EndCityPieces.func_189935_b(
                     ☃, EndCityPieces.func_191090_b(☃, ☃x, ☃xxx.func_76340_b(), "bridge_end", ☃.func_185830_a((Rotation)☃xxx.func_76341_a()), true)
                  );
                  EndCityPieces.func_191088_b(☃, EndCityPieces.field_186207_g, ☃ + 1, ☃xxxx, null, ☃, ☃);
               }
            }
         }

         ☃x = EndCityPieces.func_189935_b(☃, EndCityPieces.func_191090_b(☃, ☃x, new BlockPos(-2, 8, -2), "fat_tower_top", ☃, true));
         return true;
      }
   };

   public static void func_186200_a() {
      StructureIO.func_143031_a(EndCityPieces.CityTemplate.class, "ECP");
   }

   private static EndCityPieces.CityTemplate func_191090_b(
      TemplateManager var0, EndCityPieces.CityTemplate var1, BlockPos var2, String var3, Rotation var4, boolean var5
   ) {
      EndCityPieces.CityTemplate ☃ = new EndCityPieces.CityTemplate(☃, ☃, ☃.field_186178_c, ☃, ☃);
      BlockPos ☃x = ☃.field_186176_a.func_186262_a(☃.field_186177_b, ☃, ☃.field_186177_b, BlockPos.field_177992_a);
      ☃.func_181138_a(☃x.func_177958_n(), ☃x.func_177956_o(), ☃x.func_177952_p());
      return ☃;
   }

   public static void func_191087_a(TemplateManager var0, BlockPos var1, Rotation var2, List<StructurePiece> var3, Random var4) {
      field_186209_i.func_186184_a();
      field_186204_d.func_186184_a();
      field_186207_g.func_186184_a();
      field_186206_f.func_186184_a();
      EndCityPieces.CityTemplate ☃ = func_189935_b(☃, new EndCityPieces.CityTemplate(☃, "base_floor", ☃, ☃, true));
      ☃ = func_189935_b(☃, func_191090_b(☃, ☃, new BlockPos(-1, 0, -1), "second_floor_1", ☃, false));
      ☃ = func_189935_b(☃, func_191090_b(☃, ☃, new BlockPos(-1, 4, -1), "third_floor_1", ☃, false));
      ☃ = func_189935_b(☃, func_191090_b(☃, ☃, new BlockPos(-1, 8, -1), "third_roof", ☃, true));
      func_191088_b(☃, field_186206_f, 1, ☃, null, ☃, ☃);
   }

   private static EndCityPieces.CityTemplate func_189935_b(List<StructurePiece> var0, EndCityPieces.CityTemplate var1) {
      ☃.add(☃);
      return ☃;
   }

   private static boolean func_191088_b(
      TemplateManager var0, EndCityPieces.IGenerator var1, int var2, EndCityPieces.CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6
   ) {
      if (☃ > 8) {
         return false;
      } else {
         List<StructurePiece> ☃ = Lists.<StructurePiece>newArrayList();
         if (☃.func_191086_a(☃, ☃, ☃, ☃, ☃, ☃)) {
            boolean ☃x = false;
            int ☃xx = ☃.nextInt();

            for(StructurePiece ☃xxx : ☃) {
               ☃xxx.field_74886_g = ☃xx;
               StructurePiece ☃xxxx = StructurePiece.func_74883_a(☃, ☃xxx.func_74874_b());
               if (☃xxxx != null && ☃xxxx.field_74886_g != ☃.field_74886_g) {
                  ☃x = true;
                  break;
               }
            }

            if (!☃x) {
               ☃.addAll(☃);
               return true;
            }
         }

         return false;
      }
   }

   public static class CityTemplate extends TemplateStructurePiece {
      private String field_186181_d;
      private Rotation field_186182_e;
      private boolean field_186183_f;

      public CityTemplate() {
      }

      public CityTemplate(TemplateManager var1, String var2, BlockPos var3, Rotation var4, boolean var5) {
         super(0);
         this.field_186181_d = ☃;
         this.field_186178_c = ☃;
         this.field_186182_e = ☃;
         this.field_186183_f = ☃;
         this.func_191085_a(☃);
      }

      private void func_191085_a(TemplateManager var1) {
         Template ☃ = ☃.func_200220_a(new ResourceLocation("end_city/" + this.field_186181_d));
         PlacementSettings ☃x = (this.field_186183_f ? EndCityPieces.field_186202_b : EndCityPieces.field_186203_c)
            .func_186217_a()
            .func_186220_a(this.field_186182_e);
         this.func_186173_a(☃, this.field_186178_c, ☃x);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74778_a("Template", this.field_186181_d);
         ☃.func_74778_a("Rot", this.field_186182_e.name());
         ☃.func_74757_a("OW", this.field_186183_f);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_186181_d = ☃.func_74779_i("Template");
         this.field_186182_e = Rotation.valueOf(☃.func_74779_i("Rot"));
         this.field_186183_f = ☃.func_74767_n("OW");
         this.func_191085_a(☃);
      }

      @Override
      protected void func_186175_a(String var1, BlockPos var2, IWorld var3, Random var4, MutableBoundingBox var5) {
         if (☃.startsWith("Chest")) {
            BlockPos ☃ = ☃.func_177977_b();
            if (☃.func_175898_b(☃)) {
               TileEntityLockableLoot.func_195479_a(☃, ☃, ☃, LootTableList.field_186421_c);
            }
         } else if (☃.startsWith("Sentry")) {
            EntityShulker ☃ = new EntityShulker(☃.func_201672_e());
            ☃.func_70107_b((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5);
            ☃.func_184694_g(☃);
            ☃.func_72838_d(☃);
         } else if (☃.startsWith("Elytra")) {
            EntityItemFrame ☃ = new EntityItemFrame(☃.func_201672_e(), ☃, this.field_186182_e.func_185831_a(EnumFacing.SOUTH));
            ☃.func_82334_a(new ItemStack(Items.field_185160_cR));
            ☃.func_72838_d(☃);
         }
      }
   }

   interface IGenerator {
      void func_186184_a();

      boolean func_191086_a(TemplateManager var1, int var2, EndCityPieces.CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6);
   }
}
