package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockGlassPane;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class VillagePieces {
   public static void func_143016_a() {
      StructureIO.func_143031_a(VillagePieces.House1.class, "ViBH");
      StructureIO.func_143031_a(VillagePieces.Field1.class, "ViDF");
      StructureIO.func_143031_a(VillagePieces.Field2.class, "ViF");
      StructureIO.func_143031_a(VillagePieces.Torch.class, "ViL");
      StructureIO.func_143031_a(VillagePieces.Hall.class, "ViPH");
      StructureIO.func_143031_a(VillagePieces.House4Garden.class, "ViSH");
      StructureIO.func_143031_a(VillagePieces.WoodHut.class, "ViSmH");
      StructureIO.func_143031_a(VillagePieces.Church.class, "ViST");
      StructureIO.func_143031_a(VillagePieces.House2.class, "ViS");
      StructureIO.func_143031_a(VillagePieces.Start.class, "ViStart");
      StructureIO.func_143031_a(VillagePieces.Path.class, "ViSR");
      StructureIO.func_143031_a(VillagePieces.House3.class, "ViTRH");
      StructureIO.func_143031_a(VillagePieces.Well.class, "ViW");
   }

   public static List<VillagePieces.PieceWeight> func_75084_a(Random var0, int var1) {
      List<VillagePieces.PieceWeight> ☃ = Lists.<VillagePieces.PieceWeight>newArrayList();
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.House4Garden.class, 4, MathHelper.func_76136_a(☃, 2 + ☃, 4 + ☃ * 2)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.Church.class, 20, MathHelper.func_76136_a(☃, 0 + ☃, 1 + ☃)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.House1.class, 20, MathHelper.func_76136_a(☃, 0 + ☃, 2 + ☃)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.WoodHut.class, 3, MathHelper.func_76136_a(☃, 2 + ☃, 5 + ☃ * 3)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.Hall.class, 15, MathHelper.func_76136_a(☃, 0 + ☃, 2 + ☃)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.Field1.class, 3, MathHelper.func_76136_a(☃, 1 + ☃, 4 + ☃)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.Field2.class, 3, MathHelper.func_76136_a(☃, 2 + ☃, 4 + ☃ * 2)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.House2.class, 15, MathHelper.func_76136_a(☃, 0, 1 + ☃)));
      ☃.add(new VillagePieces.PieceWeight(VillagePieces.House3.class, 8, MathHelper.func_76136_a(☃, 0 + ☃, 3 + ☃ * 2)));
      Iterator<VillagePieces.PieceWeight> ☃x = ☃.iterator();

      while(☃x.hasNext()) {
         if (((VillagePieces.PieceWeight)☃x.next()).field_75087_d == 0) {
            ☃x.remove();
         }
      }

      return ☃;
   }

   private static int func_75079_a(List<VillagePieces.PieceWeight> var0) {
      boolean ☃ = false;
      int ☃x = 0;

      for(VillagePieces.PieceWeight ☃xx : ☃) {
         if (☃xx.field_75087_d > 0 && ☃xx.field_75089_c < ☃xx.field_75087_d) {
            ☃ = true;
         }

         ☃x += ☃xx.field_75088_b;
      }

      return ☃ ? ☃x : -1;
   }

   private static VillagePieces.Village func_176065_a(
      VillagePieces.Start var0, VillagePieces.PieceWeight var1, List<StructurePiece> var2, Random var3, int var4, int var5, int var6, EnumFacing var7, int var8
   ) {
      Class<? extends VillagePieces.Village> ☃ = ☃.field_75090_a;
      VillagePieces.Village ☃x = null;
      if (☃ == VillagePieces.House4Garden.class) {
         ☃x = VillagePieces.House4Garden.func_175858_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.Church.class) {
         ☃x = VillagePieces.Church.func_175854_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.House1.class) {
         ☃x = VillagePieces.House1.func_175850_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.WoodHut.class) {
         ☃x = VillagePieces.WoodHut.func_175853_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.Hall.class) {
         ☃x = VillagePieces.Hall.func_175857_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.Field1.class) {
         ☃x = VillagePieces.Field1.func_175851_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.Field2.class) {
         ☃x = VillagePieces.Field2.func_175852_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.House2.class) {
         ☃x = VillagePieces.House2.func_175855_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } else if (☃ == VillagePieces.House3.class) {
         ☃x = VillagePieces.House3.func_175849_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }

      return ☃x;
   }

   private static VillagePieces.Village func_176067_c(
      VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      int ☃ = func_75079_a(☃.field_74931_h);
      if (☃ <= 0) {
         return null;
      } else {
         int ☃ = 0;

         while(☃ < 5) {
            ++☃;
            int ☃x = ☃.nextInt(☃);

            for(VillagePieces.PieceWeight ☃xx : ☃.field_74931_h) {
               ☃x -= ☃xx.field_75088_b;
               if (☃x < 0) {
                  if (!☃xx.func_75085_a(☃) || ☃xx == ☃.field_74926_d && ☃.field_74931_h.size() > 1) {
                     break;
                  }

                  VillagePieces.Village ☃xxx = func_176065_a(☃, ☃xx, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
                  if (☃xxx != null) {
                     ++☃xx.field_75089_c;
                     ☃.field_74926_d = ☃xx;
                     if (!☃xx.func_75086_a()) {
                        ☃.field_74931_h.remove(☃xx);
                     }

                     return ☃xxx;
                  }
               }
            }
         }

         MutableBoundingBox ☃x = VillagePieces.Torch.func_175856_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         return ☃x != null ? new VillagePieces.Torch(☃, ☃, ☃, ☃x, ☃) : null;
      }
   }

   private static StructurePiece func_176066_d(
      VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (☃ > 50) {
         return null;
      } else if (Math.abs(☃ - ☃.func_74874_b().field_78897_a) <= 112 && Math.abs(☃ - ☃.func_74874_b().field_78896_c) <= 112) {
         StructurePiece ☃ = func_176067_c(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃ + 1);
         if (☃ != null) {
            ☃.add(☃);
            ☃.field_74932_i.add(☃);
            return ☃;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static StructurePiece func_176069_e(
      VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (☃ > 3 + ☃.field_74928_c) {
         return null;
      } else if (Math.abs(☃ - ☃.func_74874_b().field_78897_a) <= 112 && Math.abs(☃ - ☃.func_74874_b().field_78896_c) <= 112) {
         MutableBoundingBox ☃ = VillagePieces.Path.func_175848_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         if (☃ != null && ☃.field_78895_b > 10) {
            StructurePiece ☃x = new VillagePieces.Path(☃, ☃, ☃, ☃, ☃);
            ☃.add(☃x);
            ☃.field_74930_j.add(☃x);
            return ☃x;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static class Church extends VillagePieces.Village {
      public Church() {
      }

      public Church(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static VillagePieces.Church func_175854_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 5, 12, 9, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.Church(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 12 - 1, 0);
         }

         IBlockState ☃ = Blocks.field_150347_e.func_176223_P();
         IBlockState ☃x = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xx = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.EAST));
         this.func_175804_a(☃, ☃, 1, 1, 1, 3, 3, 7, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 5, 1, 3, 9, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 3, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 3, 10, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 1, 1, 0, 10, 3, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 4, 1, 1, 4, 10, 3, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 0, 4, 0, 4, 7, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 4, 0, 4, 4, 4, 7, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 1, 8, 3, 4, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 5, 4, 3, 10, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 5, 5, 3, 5, 7, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 9, 0, 4, 9, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 4, 0, 4, 4, 4, ☃, ☃, false);
         this.func_175811_a(☃, ☃, 0, 11, 2, ☃);
         this.func_175811_a(☃, ☃, 4, 11, 2, ☃);
         this.func_175811_a(☃, ☃, 2, 11, 0, ☃);
         this.func_175811_a(☃, ☃, 2, 11, 4, ☃);
         this.func_175811_a(☃, ☃, 1, 1, 6, ☃);
         this.func_175811_a(☃, ☃, 1, 1, 7, ☃);
         this.func_175811_a(☃, ☃, 2, 1, 7, ☃);
         this.func_175811_a(☃, ☃, 3, 1, 6, ☃);
         this.func_175811_a(☃, ☃, 3, 1, 7, ☃);
         this.func_175811_a(☃, ☃x, 1, 1, 5, ☃);
         this.func_175811_a(☃, ☃x, 2, 1, 6, ☃);
         this.func_175811_a(☃, ☃x, 3, 1, 5, ☃);
         this.func_175811_a(☃, ☃xx, 1, 2, 7, ☃);
         this.func_175811_a(☃, ☃xxx, 3, 2, 7, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            3,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            4,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            4,
            3,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            6,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            7,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            4,
            6,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            4,
            7,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            6,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            7,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            6,
            4,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            7,
            4,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            3,
            6,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            4,
            3,
            6,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            3,
            8,
            ☃
         );
         this.func_189926_a(☃, EnumFacing.SOUTH, 2, 4, 7, ☃);
         this.func_189926_a(☃, EnumFacing.EAST, 1, 4, 6, ☃);
         this.func_189926_a(☃, EnumFacing.WEST, 3, 4, 6, ☃);
         this.func_189926_a(☃, EnumFacing.NORTH, 2, 4, 5, ☃);
         IBlockState ☃xxxx = Blocks.field_150468_ap.func_176223_P().func_206870_a(BlockLadder.field_176382_a, EnumFacing.WEST);

         for(int ☃xxxxx = 1; ☃xxxxx <= 9; ++☃xxxxx) {
            this.func_175811_a(☃, ☃xxxx, 3, ☃xxxxx, 3, ☃);
         }

         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 1, 0, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 2, 0, ☃);
         this.func_189927_a(☃, ☃, ☃, 2, 1, 0, EnumFacing.NORTH);
         if (this.func_175807_a(☃, 2, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, 2, -1, -1, ☃).func_196958_f()) {
            this.func_175811_a(☃, ☃x, 2, 0, -1, ☃);
            if (this.func_175807_a(☃, 2, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
               this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), 2, -1, -1, ☃);
            }
         }

         for(int ☃xxxxx = 0; ☃xxxxx < 9; ++☃xxxxx) {
            for(int ☃xxxxxx = 0; ☃xxxxxx < 5; ++☃xxxxxx) {
               this.func_74871_b(☃, ☃xxxxxx, 12, ☃xxxxx, ☃);
               this.func_175808_b(☃, ☃, ☃xxxxxx, -1, ☃xxxxx, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 2, 1, 2, 1);
         return true;
      }

      @Override
      protected int func_180779_c(int var1, int var2) {
         return 2;
      }
   }

   public static class Field1 extends VillagePieces.Village {
      private IBlockState field_82679_b;
      private IBlockState field_82680_c;
      private IBlockState field_82678_d;
      private IBlockState field_82681_h;

      public Field1() {
      }

      public Field1(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_82679_b = VillagePieces.Field2.func_197529_b(☃);
         this.field_82680_c = VillagePieces.Field2.func_197529_b(☃);
         this.field_82678_d = VillagePieces.Field2.func_197529_b(☃);
         this.field_82681_h = VillagePieces.Field2.func_197529_b(☃);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74782_a("CA", NBTUtil.func_190009_a(this.field_82679_b));
         ☃.func_74782_a("CB", NBTUtil.func_190009_a(this.field_82680_c));
         ☃.func_74782_a("CC", NBTUtil.func_190009_a(this.field_82678_d));
         ☃.func_74782_a("CD", NBTUtil.func_190009_a(this.field_82681_h));
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_82679_b = NBTUtil.func_190008_d(☃.func_74775_l("CA"));
         this.field_82680_c = NBTUtil.func_190008_d(☃.func_74775_l("CB"));
         this.field_82678_d = NBTUtil.func_190008_d(☃.func_74775_l("CC"));
         this.field_82681_h = NBTUtil.func_190008_d(☃.func_74775_l("CD"));
         if (!(this.field_82679_b.func_177230_c() instanceof BlockCrops)) {
            this.field_82679_b = Blocks.field_150464_aj.func_176223_P();
         }

         if (!(this.field_82680_c.func_177230_c() instanceof BlockCrops)) {
            this.field_82680_c = Blocks.field_150459_bM.func_176223_P();
         }

         if (!(this.field_82678_d.func_177230_c() instanceof BlockCrops)) {
            this.field_82678_d = Blocks.field_150469_bN.func_176223_P();
         }

         if (!(this.field_82681_h.func_177230_c() instanceof BlockCrops)) {
            this.field_82681_h = Blocks.field_185773_cZ.func_176223_P();
         }
      }

      public static VillagePieces.Field1 func_175851_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 13, 4, 9, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.Field1(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 4 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         this.func_175804_a(☃, ☃, 0, 1, 0, 12, 4, 8, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 0, 1, 2, 0, 7, Blocks.field_150458_ak.func_176223_P(), Blocks.field_150458_ak.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 0, 1, 5, 0, 7, Blocks.field_150458_ak.func_176223_P(), Blocks.field_150458_ak.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 7, 0, 1, 8, 0, 7, Blocks.field_150458_ak.func_176223_P(), Blocks.field_150458_ak.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 10, 0, 1, 11, 0, 7, Blocks.field_150458_ak.func_176223_P(), Blocks.field_150458_ak.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 0, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 6, 0, 0, 6, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 12, 0, 0, 12, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 11, 0, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 8, 11, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 3, 0, 1, 3, 0, 7, Blocks.field_150355_j.func_176223_P(), Blocks.field_150355_j.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 9, 0, 1, 9, 0, 7, Blocks.field_150355_j.func_176223_P(), Blocks.field_150355_j.func_176223_P(), false);

         for(int ☃x = 1; ☃x <= 7; ++☃x) {
            BlockCrops ☃xx = (BlockCrops)this.field_82679_b.func_177230_c();
            int ☃xxx = ☃xx.func_185526_g();
            int ☃xxxx = ☃xxx / 3;
            this.func_175811_a(☃, this.field_82679_b.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxx, ☃xxx))), 1, 1, ☃x, ☃);
            this.func_175811_a(☃, this.field_82679_b.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxx, ☃xxx))), 2, 1, ☃x, ☃);
            ☃xx = (BlockCrops)this.field_82680_c.func_177230_c();
            int ☃xxxxx = ☃xx.func_185526_g();
            int ☃xxxxxx = ☃xxxxx / 3;
            this.func_175811_a(
               ☃, this.field_82680_c.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxx, ☃xxxxx))), 4, 1, ☃x, ☃
            );
            this.func_175811_a(
               ☃, this.field_82680_c.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxx, ☃xxxxx))), 5, 1, ☃x, ☃
            );
            ☃xx = (BlockCrops)this.field_82678_d.func_177230_c();
            int ☃xxxxxxx = ☃xx.func_185526_g();
            int ☃xxxxxxxx = ☃xxxxxxx / 3;
            this.func_175811_a(
               ☃, this.field_82678_d.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxxxx, ☃xxxxxxx))), 7, 1, ☃x, ☃
            );
            this.func_175811_a(
               ☃, this.field_82678_d.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxxxx, ☃xxxxxxx))), 8, 1, ☃x, ☃
            );
            ☃xx = (BlockCrops)this.field_82681_h.func_177230_c();
            int ☃xxxxxxxxx = ☃xx.func_185526_g();
            int ☃xxxxxxxxxx = ☃xxxxxxxxx / 3;
            this.func_175811_a(
               ☃, this.field_82681_h.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxxxxxx, ☃xxxxxxxxx))), 10, 1, ☃x, ☃
            );
            this.func_175811_a(
               ☃, this.field_82681_h.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxxxxxx, ☃xxxxxxxxx))), 11, 1, ☃x, ☃
            );
         }

         for(int ☃x = 0; ☃x < 9; ++☃x) {
            for(int ☃xx = 0; ☃xx < 13; ++☃xx) {
               this.func_74871_b(☃, ☃xx, 4, ☃x, ☃);
               this.func_175808_b(☃, Blocks.field_150346_d.func_176223_P(), ☃xx, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }

   public static class Field2 extends VillagePieces.Village {
      private IBlockState field_82675_b;
      private IBlockState field_82676_c;

      public Field2() {
      }

      public Field2(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_82675_b = func_197529_b(☃);
         this.field_82676_c = func_197529_b(☃);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74782_a("CA", NBTUtil.func_190009_a(this.field_82675_b));
         ☃.func_74782_a("CB", NBTUtil.func_190009_a(this.field_82676_c));
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_82675_b = NBTUtil.func_190008_d(☃.func_74775_l("CA"));
         this.field_82676_c = NBTUtil.func_190008_d(☃.func_74775_l("CB"));
      }

      private static IBlockState func_197529_b(Random var0) {
         switch(☃.nextInt(10)) {
            case 0:
            case 1:
               return Blocks.field_150459_bM.func_176223_P();
            case 2:
            case 3:
               return Blocks.field_150469_bN.func_176223_P();
            case 4:
               return Blocks.field_185773_cZ.func_176223_P();
            default:
               return Blocks.field_150464_aj.func_176223_P();
         }
      }

      public static VillagePieces.Field2 func_175852_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 7, 4, 9, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.Field2(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 4 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         this.func_175804_a(☃, ☃, 0, 1, 0, 6, 4, 8, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 0, 1, 2, 0, 7, Blocks.field_150458_ak.func_176223_P(), Blocks.field_150458_ak.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 4, 0, 1, 5, 0, 7, Blocks.field_150458_ak.func_176223_P(), Blocks.field_150458_ak.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 0, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 6, 0, 0, 6, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 5, 0, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 8, 5, 0, 8, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 3, 0, 1, 3, 0, 7, Blocks.field_150355_j.func_176223_P(), Blocks.field_150355_j.func_176223_P(), false);

         for(int ☃x = 1; ☃x <= 7; ++☃x) {
            BlockCrops ☃xx = (BlockCrops)this.field_82675_b.func_177230_c();
            int ☃xxx = ☃xx.func_185526_g();
            int ☃xxxx = ☃xxx / 3;
            this.func_175811_a(☃, this.field_82675_b.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxx, ☃xxx))), 1, 1, ☃x, ☃);
            this.func_175811_a(☃, this.field_82675_b.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxx, ☃xxx))), 2, 1, ☃x, ☃);
            ☃xx = (BlockCrops)this.field_82676_c.func_177230_c();
            int ☃xxxxx = ☃xx.func_185526_g();
            int ☃xxxxxx = ☃xxxxx / 3;
            this.func_175811_a(
               ☃, this.field_82676_c.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxx, ☃xxxxx))), 4, 1, ☃x, ☃
            );
            this.func_175811_a(
               ☃, this.field_82676_c.func_206870_a(☃xx.func_185524_e(), Integer.valueOf(MathHelper.func_76136_a(☃, ☃xxxxxx, ☃xxxxx))), 5, 1, ☃x, ☃
            );
         }

         for(int ☃x = 0; ☃x < 9; ++☃x) {
            for(int ☃xx = 0; ☃xx < 7; ++☃xx) {
               this.func_74871_b(☃, ☃xx, 4, ☃x, ☃);
               this.func_175808_b(☃, Blocks.field_150346_d.func_176223_P(), ☃xx, -1, ☃x, ☃);
            }
         }

         return true;
      }
   }

   public static class Hall extends VillagePieces.Village {
      public Hall() {
      }

      public Hall(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static VillagePieces.Hall func_175857_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 9, 7, 11, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.Hall(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 7 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST));
         IBlockState ☃xxxx = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xxxxx = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         IBlockState ☃xxxxxx = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 1, 1, 1, 7, 4, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 1, 6, 8, 4, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 0, 6, 8, 0, 10, Blocks.field_150346_d.func_176223_P(), Blocks.field_150346_d.func_176223_P(), false);
         this.func_175811_a(☃, ☃, 6, 0, 6, ☃);
         IBlockState ☃xxxxxxx = ☃xxxxxx.func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true));
         IBlockState ☃xxxxxxxx = ☃xxxxxx.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true))
            .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true));
         this.func_175804_a(☃, ☃, 2, 1, 6, 2, 1, 9, ☃xxxxxxx, ☃xxxxxxx, false);
         this.func_175811_a(
            ☃,
            ☃xxxxxx.func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)).func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)),
            2,
            1,
            10,
            ☃
         );
         this.func_175804_a(☃, ☃, 8, 1, 6, 8, 1, 9, ☃xxxxxxx, ☃xxxxxxx, false);
         this.func_175811_a(
            ☃,
            ☃xxxxxx.func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(true)).func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)),
            8,
            1,
            10,
            ☃
         );
         this.func_175804_a(☃, ☃, 3, 1, 10, 7, 1, 10, ☃xxxxxxxx, ☃xxxxxxxx, false);
         this.func_175804_a(☃, ☃, 1, 0, 1, 7, 0, 4, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 0, 3, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 8, 0, 0, 8, 3, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 7, 1, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 5, 7, 1, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 7, 3, 0, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 1, 2, 5, 7, 3, 5, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 0, 4, 1, 8, 4, 1, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 0, 4, 4, 8, 4, 4, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 0, 5, 2, 8, 5, 3, ☃xxxx, ☃xxxx, false);
         this.func_175811_a(☃, ☃xxxx, 0, 4, 2, ☃);
         this.func_175811_a(☃, ☃xxxx, 0, 4, 3, ☃);
         this.func_175811_a(☃, ☃xxxx, 8, 4, 2, ☃);
         this.func_175811_a(☃, ☃xxxx, 8, 4, 3, ☃);
         IBlockState ☃xxxxxxxxx = ☃x;
         IBlockState ☃xxxxxxxxxx = ☃xx;

         for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx <= 2; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx <= 8; ++☃xxxxxxxxxxxx) {
               this.func_175811_a(☃, ☃xxxxxxxxx, ☃xxxxxxxxxxxx, 4 + ☃xxxxxxxxxxx, ☃xxxxxxxxxxx, ☃);
               this.func_175811_a(☃, ☃xxxxxxxxxx, ☃xxxxxxxxxxxx, 4 + ☃xxxxxxxxxxx, 5 - ☃xxxxxxxxxxx, ☃);
            }
         }

         this.func_175811_a(☃, ☃xxxxx, 0, 2, 1, ☃);
         this.func_175811_a(☃, ☃xxxxx, 0, 2, 4, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 2, 1, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 2, 4, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            2,
            5,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            3,
            2,
            5,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            5,
            2,
            0,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 2, 1, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196663_cq.func_176223_P(), 2, 2, 3, ☃);
         this.func_175811_a(☃, ☃xxxx, 1, 1, 4, ☃);
         this.func_175811_a(☃, ☃xxxxxxxxx, 2, 1, 4, ☃);
         this.func_175811_a(☃, ☃xxx, 1, 1, 3, ☃);
         IBlockState ☃xxxxxxxxxxx = Blocks.field_150333_U.func_176223_P().func_206870_a(BlockSlab.field_196505_a, SlabType.DOUBLE);
         this.func_175804_a(☃, ☃, 5, 0, 1, 7, 0, 3, ☃xxxxxxxxxxx, ☃xxxxxxxxxxx, false);
         this.func_175811_a(☃, ☃xxxxxxxxxxx, 6, 1, 1, ☃);
         this.func_175811_a(☃, ☃xxxxxxxxxxx, 6, 1, 2, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 1, 0, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 2, 0, ☃);
         this.func_189926_a(☃, EnumFacing.NORTH, 2, 3, 1, ☃);
         this.func_189927_a(☃, ☃, ☃, 2, 1, 0, EnumFacing.NORTH);
         if (this.func_175807_a(☃, 2, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, 2, -1, -1, ☃).func_196958_f()) {
            this.func_175811_a(☃, ☃xxxxxxxxx, 2, 0, -1, ☃);
            if (this.func_175807_a(☃, 2, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
               this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), 2, -1, -1, ☃);
            }
         }

         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 6, 1, 5, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 6, 2, 5, ☃);
         this.func_189926_a(☃, EnumFacing.SOUTH, 6, 3, 4, ☃);
         this.func_189927_a(☃, ☃, ☃, 6, 1, 5, EnumFacing.SOUTH);

         for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < 5; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < 9; ++☃xxxxxxxxxxxx) {
               this.func_74871_b(☃, ☃xxxxxxxxxxxx, 7, ☃xxxxxxxxxxx, ☃);
               this.func_175808_b(☃, ☃, ☃xxxxxxxxxxxx, -1, ☃xxxxxxxxxxx, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 4, 1, 2, 2);
         return true;
      }

      @Override
      protected int func_180779_c(int var1, int var2) {
         return ☃ == 0 ? 4 : super.func_180779_c(☃, ☃);
      }
   }

   public static class House1 extends VillagePieces.Village {
      public House1() {
      }

      public House1(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static VillagePieces.House1 func_175850_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 9, 9, 6, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.House1(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 9 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.EAST));
         IBlockState ☃xxxx = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xxxxx = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xxxxxx = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 1, 1, 1, 7, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 8, 0, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 8, 5, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 6, 1, 8, 6, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 7, 2, 8, 7, 3, ☃, ☃, false);

         for(int ☃xxxxxxx = -1; ☃xxxxxxx <= 2; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx <= 8; ++☃xxxxxxxx) {
               this.func_175811_a(☃, ☃x, ☃xxxxxxxx, 6 + ☃xxxxxxx, ☃xxxxxxx, ☃);
               this.func_175811_a(☃, ☃xx, ☃xxxxxxxx, 6 + ☃xxxxxxx, 5 - ☃xxxxxxx, ☃);
            }
         }

         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 1, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 1, 5, 8, 1, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 8, 1, 0, 8, 1, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 2, 1, 0, 7, 1, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 4, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 2, 5, 0, 4, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 8, 2, 5, 8, 4, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 8, 2, 0, 8, 4, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 2, 1, 0, 4, 4, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 1, 2, 5, 7, 4, 5, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 8, 2, 1, 8, 4, 4, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 7, 4, 0, ☃xxxx, ☃xxxx, false);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            4,
            2,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            5,
            2,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            6,
            2,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            4,
            3,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            5,
            3,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            6,
            3,
            0,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            3,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            3,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            3,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            3,
            3,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            2,
            5,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            3,
            2,
            5,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            5,
            2,
            5,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            6,
            2,
            5,
            ☃
         );
         this.func_175804_a(☃, ☃, 1, 4, 1, 7, 4, 1, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 1, 4, 4, 7, 4, 4, ☃xxxx, ☃xxxx, false);
         this.func_175804_a(☃, ☃, 1, 3, 4, 7, 3, 4, Blocks.field_150342_X.func_176223_P(), Blocks.field_150342_X.func_176223_P(), false);
         this.func_175811_a(☃, ☃xxxx, 7, 1, 4, ☃);
         this.func_175811_a(☃, ☃xxx, 7, 1, 3, ☃);
         this.func_175811_a(☃, ☃x, 6, 1, 4, ☃);
         this.func_175811_a(☃, ☃x, 5, 1, 4, ☃);
         this.func_175811_a(☃, ☃x, 4, 1, 4, ☃);
         this.func_175811_a(☃, ☃x, 3, 1, 4, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 6, 1, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196663_cq.func_176223_P(), 6, 2, 3, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 4, 1, 3, ☃);
         this.func_175811_a(☃, Blocks.field_196663_cq.func_176223_P(), 4, 2, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150462_ai.func_176223_P(), 7, 1, 1, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 1, 1, 0, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 1, 2, 0, ☃);
         this.func_189927_a(☃, ☃, ☃, 1, 1, 0, EnumFacing.NORTH);
         if (this.func_175807_a(☃, 1, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, 1, -1, -1, ☃).func_196958_f()) {
            this.func_175811_a(☃, ☃xxxxx, 1, 0, -1, ☃);
            if (this.func_175807_a(☃, 1, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
               this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), 1, -1, -1, ☃);
            }
         }

         for(int ☃xxxxxxx = 0; ☃xxxxxxx < 6; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 9; ++☃xxxxxxxx) {
               this.func_74871_b(☃, ☃xxxxxxxx, 9, ☃xxxxxxx, ☃);
               this.func_175808_b(☃, ☃, ☃xxxxxxxx, -1, ☃xxxxxxx, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 2, 1, 2, 1);
         return true;
      }

      @Override
      protected int func_180779_c(int var1, int var2) {
         return 1;
      }
   }

   public static class House2 extends VillagePieces.Village {
      private boolean field_74917_c;

      public House2() {
      }

      public House2(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static VillagePieces.House2 func_175855_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 10, 6, 7, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.House2(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Chest", this.field_74917_c);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74917_c = ☃.func_74767_n("Chest");
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 6 - 1, 0);
         }

         IBlockState ☃ = Blocks.field_150347_e.func_176223_P();
         IBlockState ☃x = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xxxx = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xxxxx = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         IBlockState ☃xxxxxx = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 0, 1, 0, 9, 4, 6, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 9, 0, 6, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 4, 0, 9, 4, 6, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 5, 0, 9, 5, 6, Blocks.field_150333_U.func_176223_P(), Blocks.field_150333_U.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 5, 1, 8, 5, 5, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 2, 3, 0, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 4, 0, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 3, 1, 0, 3, 4, 0, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 0, 1, 6, 0, 4, 6, ☃xxxxx, ☃xxxxx, false);
         this.func_175811_a(☃, ☃xxx, 3, 3, 1, ☃);
         this.func_175804_a(☃, ☃, 3, 1, 2, 3, 3, 2, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 4, 1, 3, 5, 3, 3, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 0, 1, 1, 0, 3, 5, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 1, 1, 6, 5, 3, 6, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 5, 1, 0, 5, 3, 0, ☃xxxxxx, ☃xxxxxx, false);
         this.func_175804_a(☃, ☃, 9, 1, 0, 9, 3, 0, ☃xxxxxx, ☃xxxxxx, false);
         this.func_175804_a(☃, ☃, 6, 1, 4, 9, 4, 6, ☃, ☃, false);
         this.func_175811_a(☃, Blocks.field_150353_l.func_176223_P(), 7, 1, 5, ☃);
         this.func_175811_a(☃, Blocks.field_150353_l.func_176223_P(), 8, 1, 5, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150411_aY
               .func_176223_P()
               .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true))
               .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(true)),
            9,
            2,
            5,
            ☃
         );
         this.func_175811_a(☃, Blocks.field_150411_aY.func_176223_P().func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(true)), 9, 2, 4, ☃);
         this.func_175804_a(☃, ☃, 7, 2, 4, 8, 2, 5, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(☃, ☃, 6, 1, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150460_al.func_176223_P().func_206870_a(BlockFurnace.field_176447_a, EnumFacing.SOUTH), 6, 2, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150460_al.func_176223_P().func_206870_a(BlockFurnace.field_176447_a, EnumFacing.SOUTH), 6, 3, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150333_U.func_176223_P().func_206870_a(BlockSlab.field_196505_a, SlabType.DOUBLE), 8, 1, 1, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            4,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            2,
            6,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            4,
            2,
            6,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 2, 1, 4, ☃);
         this.func_175811_a(☃, Blocks.field_196663_cq.func_176223_P(), 2, 2, 4, ☃);
         this.func_175811_a(☃, ☃xxx, 1, 1, 5, ☃);
         this.func_175811_a(☃, ☃x, 2, 1, 5, ☃);
         this.func_175811_a(☃, ☃xx, 1, 1, 4, ☃);
         if (!this.field_74917_c && ☃.func_175898_b(new BlockPos(this.func_74865_a(5, 5), this.func_74862_a(1), this.func_74873_b(5, 5)))) {
            this.field_74917_c = true;
            this.func_186167_a(☃, ☃, ☃, 5, 1, 5, LootTableList.field_186423_e);
         }

         for(int ☃ = 6; ☃ <= 8; ++☃) {
            if (this.func_175807_a(☃, ☃, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, ☃, -1, -1, ☃).func_196958_f()) {
               this.func_175811_a(☃, ☃xxxx, ☃, 0, -1, ☃);
               if (this.func_175807_a(☃, ☃, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
                  this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), ☃, -1, -1, ☃);
               }
            }
         }

         for(int ☃ = 0; ☃ < 7; ++☃) {
            for(int ☃x = 0; ☃x < 10; ++☃x) {
               this.func_74871_b(☃, ☃x, 6, ☃, ☃);
               this.func_175808_b(☃, ☃, ☃x, -1, ☃, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 7, 1, 1, 1);
         return true;
      }

      @Override
      protected int func_180779_c(int var1, int var2) {
         return 3;
      }
   }

   public static class House3 extends VillagePieces.Village {
      public House3() {
      }

      public House3(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static VillagePieces.House3 func_175849_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 9, 7, 12, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.House3(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 7 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.SOUTH));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.EAST));
         IBlockState ☃xxxx = this.func_175847_a(Blocks.field_150476_ad.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.WEST));
         IBlockState ☃xxxxx = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xxxxxx = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         this.func_175804_a(☃, ☃, 1, 1, 1, 7, 4, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 1, 6, 8, 4, 10, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 2, 0, 5, 8, 0, 10, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 1, 0, 1, 7, 0, 4, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 0, 3, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 8, 0, 0, 8, 3, 10, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 7, 2, 0, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 5, 2, 1, 5, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 2, 0, 6, 2, 3, 10, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 3, 0, 10, 7, 3, 10, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 7, 3, 0, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 1, 2, 5, 2, 3, 5, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 0, 4, 1, 8, 4, 1, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 0, 4, 4, 3, 4, 4, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 0, 5, 2, 8, 5, 3, ☃xxxxx, ☃xxxxx, false);
         this.func_175811_a(☃, ☃xxxxx, 0, 4, 2, ☃);
         this.func_175811_a(☃, ☃xxxxx, 0, 4, 3, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 4, 2, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 4, 3, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 4, 4, ☃);
         IBlockState ☃xxxxxxx = ☃x;
         IBlockState ☃xxxxxxxx = ☃xx;
         IBlockState ☃xxxxxxxxx = ☃xxxx;
         IBlockState ☃xxxxxxxxxx = ☃xxx;

         for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx <= 2; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx <= 8; ++☃xxxxxxxxxxxx) {
               this.func_175811_a(☃, ☃xxxxxxx, ☃xxxxxxxxxxxx, 4 + ☃xxxxxxxxxxx, ☃xxxxxxxxxxx, ☃);
               if ((☃xxxxxxxxxxx > -1 || ☃xxxxxxxxxxxx <= 1)
                  && (☃xxxxxxxxxxx > 0 || ☃xxxxxxxxxxxx <= 3)
                  && (☃xxxxxxxxxxx > 1 || ☃xxxxxxxxxxxx <= 4 || ☃xxxxxxxxxxxx >= 6)) {
                  this.func_175811_a(☃, ☃xxxxxxxx, ☃xxxxxxxxxxxx, 4 + ☃xxxxxxxxxxx, 5 - ☃xxxxxxxxxxx, ☃);
               }
            }
         }

         this.func_175804_a(☃, ☃, 3, 4, 5, 3, 4, 10, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 7, 4, 2, 7, 4, 10, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 4, 5, 4, 4, 5, 10, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 6, 5, 4, 6, 5, 10, ☃xxxxx, ☃xxxxx, false);
         this.func_175804_a(☃, ☃, 5, 6, 3, 5, 6, 10, ☃xxxxx, ☃xxxxx, false);

         for(int ☃xxxxxxxxxxx = 4; ☃xxxxxxxxxxx >= 1; --☃xxxxxxxxxxx) {
            this.func_175811_a(☃, ☃xxxxx, ☃xxxxxxxxxxx, 2 + ☃xxxxxxxxxxx, 7 - ☃xxxxxxxxxxx, ☃);

            for(int ☃xxxxxxxxxxxx = 8 - ☃xxxxxxxxxxx; ☃xxxxxxxxxxxx <= 10; ++☃xxxxxxxxxxxx) {
               this.func_175811_a(☃, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, 2 + ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃);
            }
         }

         this.func_175811_a(☃, ☃xxxxx, 6, 6, 3, ☃);
         this.func_175811_a(☃, ☃xxxxx, 7, 5, 4, ☃);
         this.func_175811_a(☃, ☃xxxx, 6, 6, 4, ☃);

         for(int ☃xxxxxxxxxxx = 6; ☃xxxxxxxxxxx <= 8; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 5; ☃xxxxxxxxxxxx <= 10; ++☃xxxxxxxxxxxx) {
               this.func_175811_a(☃, ☃xxxxxxxxx, ☃xxxxxxxxxxx, 12 - ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃);
            }
         }

         this.func_175811_a(☃, ☃xxxxxx, 0, 2, 1, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 0, 2, 4, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            3,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 4, 2, 0, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            5,
            2,
            0,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 6, 2, 0, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 8, 2, 1, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            3,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 8, 2, 4, ☃);
         this.func_175811_a(☃, ☃xxxxx, 8, 2, 5, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 8, 2, 6, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            7,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            8,
            2,
            8,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 8, 2, 9, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 2, 2, 6, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            2,
            2,
            7,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            2,
            2,
            8,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 2, 2, 9, ☃);
         this.func_175811_a(☃, ☃xxxxxx, 4, 4, 10, ☃);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            5,
            4,
            10,
            ☃
         );
         this.func_175811_a(☃, ☃xxxxxx, 6, 4, 10, ☃);
         this.func_175811_a(☃, ☃xxxxx, 5, 5, 10, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 1, 0, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 2, 0, ☃);
         this.func_189926_a(☃, EnumFacing.NORTH, 2, 3, 1, ☃);
         this.func_189927_a(☃, ☃, ☃, 2, 1, 0, EnumFacing.NORTH);
         this.func_175804_a(☃, ☃, 1, 0, -1, 3, 2, -1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         if (this.func_175807_a(☃, 2, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, 2, -1, -1, ☃).func_196958_f()) {
            this.func_175811_a(☃, ☃xxxxxxx, 2, 0, -1, ☃);
            if (this.func_175807_a(☃, 2, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
               this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), 2, -1, -1, ☃);
            }
         }

         for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < 5; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < 9; ++☃xxxxxxxxxxxx) {
               this.func_74871_b(☃, ☃xxxxxxxxxxxx, 7, ☃xxxxxxxxxxx, ☃);
               this.func_175808_b(☃, ☃, ☃xxxxxxxxxxxx, -1, ☃xxxxxxxxxxx, ☃);
            }
         }

         for(int ☃xxxxxxxxxxx = 5; ☃xxxxxxxxxxx < 11; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 2; ☃xxxxxxxxxxxx < 9; ++☃xxxxxxxxxxxx) {
               this.func_74871_b(☃, ☃xxxxxxxxxxxx, 7, ☃xxxxxxxxxxx, ☃);
               this.func_175808_b(☃, ☃, ☃xxxxxxxxxxxx, -1, ☃xxxxxxxxxxx, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 4, 1, 2, 2);
         return true;
      }
   }

   public static class House4Garden extends VillagePieces.Village {
      private boolean field_74913_b;

      public House4Garden() {
      }

      public House4Garden(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_74913_b = ☃.nextBoolean();
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("Terrace", this.field_74913_b);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74913_b = ☃.func_74767_n("Terrace");
      }

      public static VillagePieces.House4Garden func_175858_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 5, 6, 5, ☃);
         return StructurePiece.func_74883_a(☃, ☃) != null ? null : new VillagePieces.House4Garden(☃, ☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 6 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xx = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         IBlockState ☃xxxx = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 0, 0, 0, 4, 0, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 0, 4, 0, 4, 4, 4, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 1, 4, 1, 3, 4, 3, ☃x, ☃x, false);
         this.func_175811_a(☃, ☃, 0, 1, 0, ☃);
         this.func_175811_a(☃, ☃, 0, 2, 0, ☃);
         this.func_175811_a(☃, ☃, 0, 3, 0, ☃);
         this.func_175811_a(☃, ☃, 4, 1, 0, ☃);
         this.func_175811_a(☃, ☃, 4, 2, 0, ☃);
         this.func_175811_a(☃, ☃, 4, 3, 0, ☃);
         this.func_175811_a(☃, ☃, 0, 1, 4, ☃);
         this.func_175811_a(☃, ☃, 0, 2, 4, ☃);
         this.func_175811_a(☃, ☃, 0, 3, 4, ☃);
         this.func_175811_a(☃, ☃, 4, 1, 4, ☃);
         this.func_175811_a(☃, ☃, 4, 2, 4, ☃);
         this.func_175811_a(☃, ☃, 4, 3, 4, ☃);
         this.func_175804_a(☃, ☃, 0, 1, 1, 0, 3, 3, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 4, 1, 1, 4, 3, 3, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 1, 1, 4, 3, 3, 4, ☃x, ☃x, false);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196411_b, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196414_y, Boolean.valueOf(true)),
            2,
            2,
            4,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            4,
            2,
            2,
            ☃
         );
         this.func_175811_a(☃, ☃x, 1, 1, 0, ☃);
         this.func_175811_a(☃, ☃x, 1, 2, 0, ☃);
         this.func_175811_a(☃, ☃x, 1, 3, 0, ☃);
         this.func_175811_a(☃, ☃x, 2, 3, 0, ☃);
         this.func_175811_a(☃, ☃x, 3, 3, 0, ☃);
         this.func_175811_a(☃, ☃x, 3, 2, 0, ☃);
         this.func_175811_a(☃, ☃x, 3, 1, 0, ☃);
         if (this.func_175807_a(☃, 2, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, 2, -1, -1, ☃).func_196958_f()) {
            this.func_175811_a(☃, ☃xx, 2, 0, -1, ☃);
            if (this.func_175807_a(☃, 2, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
               this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), 2, -1, -1, ☃);
            }
         }

         this.func_175804_a(☃, ☃, 1, 1, 1, 3, 3, 3, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         if (this.field_74913_b) {
            int ☃ = 0;
            int ☃x = 4;

            for(int ☃xx = 0; ☃xx <= 4; ++☃xx) {
               for(int ☃xxx = 0; ☃xxx <= 4; ++☃xxx) {
                  boolean ☃xxxx = ☃xx == 0 || ☃xx == 4;
                  boolean ☃xxxxx = ☃xxx == 0 || ☃xxx == 4;
                  if (☃xxxx || ☃xxxxx) {
                     boolean ☃xxxxxx = ☃xx == 0 || ☃xx == 4;
                     boolean ☃xxxxxxx = ☃xxx == 0 || ☃xxx == 4;
                     IBlockState ☃xxxxxxxx = ☃xxxx.func_206870_a(BlockFence.field_196413_c, Boolean.valueOf(☃xxxxxx && ☃xxx != 0))
                        .func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(☃xxxxxx && ☃xxx != 4))
                        .func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(☃xxxxxxx && ☃xx != 0))
                        .func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(☃xxxxxxx && ☃xx != 4));
                     this.func_175811_a(☃, ☃xxxxxxxx, ☃xx, 5, ☃xxx, ☃);
                  }
               }
            }
         }

         if (this.field_74913_b) {
            IBlockState ☃ = Blocks.field_150468_ap.func_176223_P().func_206870_a(BlockLadder.field_176382_a, EnumFacing.SOUTH);
            this.func_175811_a(☃, ☃, 3, 1, 3, ☃);
            this.func_175811_a(☃, ☃, 3, 2, 3, ☃);
            this.func_175811_a(☃, ☃, 3, 3, 3, ☃);
            this.func_175811_a(☃, ☃, 3, 4, 3, ☃);
         }

         this.func_189926_a(☃, EnumFacing.NORTH, 2, 3, 1, ☃);

         for(int ☃ = 0; ☃ < 5; ++☃) {
            for(int ☃x = 0; ☃x < 5; ++☃x) {
               this.func_74871_b(☃, ☃x, 6, ☃, ☃);
               this.func_175808_b(☃, ☃, ☃x, -1, ☃, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 1, 1, 2, 1);
         return true;
      }
   }

   public static class Path extends VillagePieces.Road {
      private int field_74934_a;

      public Path() {
      }

      public Path(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_74934_a = Math.max(☃.func_78883_b(), ☃.func_78880_d());
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74768_a("Length", this.field_74934_a);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74934_a = ☃.func_74762_e("Length");
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         boolean ☃ = false;

         for(int ☃x = ☃.nextInt(5); ☃x < this.field_74934_a - 8; ☃x += 2 + ☃.nextInt(5)) {
            StructurePiece ☃xx = this.func_74891_a((VillagePieces.Start)☃, ☃, ☃, 0, ☃x);
            if (☃xx != null) {
               ☃x += Math.max(☃xx.field_74887_e.func_78883_b(), ☃xx.field_74887_e.func_78880_d());
               ☃ = true;
            }
         }

         for(int var7 = ☃.nextInt(5); var7 < this.field_74934_a - 8; var7 += 2 + ☃.nextInt(5)) {
            StructurePiece ☃x = this.func_74894_b((VillagePieces.Start)☃, ☃, ☃, 0, var7);
            if (☃x != null) {
               var7 += Math.max(☃x.field_74887_e.func_78883_b(), ☃x.field_74887_e.func_78880_d());
               ☃ = true;
            }
         }

         EnumFacing ☃x = this.func_186165_e();
         if (☃ && ☃.nextInt(3) > 0 && ☃x != null) {
            switch(☃x) {
               case NORTH:
               default:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78896_c,
                     EnumFacing.WEST,
                     this.func_74877_c()
                  );
                  break;
               case SOUTH:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78892_f - 2,
                     EnumFacing.WEST,
                     this.func_74877_c()
                  );
                  break;
               case WEST:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c()
                  );
                  break;
               case EAST:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d - 2,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c()
                  );
            }
         }

         if (☃ && ☃.nextInt(3) > 0 && ☃x != null) {
            switch(☃x) {
               case NORTH:
               default:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78896_c,
                     EnumFacing.EAST,
                     this.func_74877_c()
                  );
                  break;
               case SOUTH:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78892_f - 2,
                     EnumFacing.EAST,
                     this.func_74877_c()
                  );
                  break;
               case WEST:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c()
                  );
                  break;
               case EAST:
                  VillagePieces.func_176069_e(
                     (VillagePieces.Start)☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d - 2,
                     this.field_74887_e.field_78895_b,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c()
                  );
            }
         }
      }

      public static MutableBoundingBox func_175848_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6
      ) {
         for(int ☃ = 7 * MathHelper.func_76136_a(☃, 3, 5); ☃ >= 7; ☃ -= 7) {
            MutableBoundingBox ☃x = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 3, 3, ☃, ☃);
            if (StructurePiece.func_74883_a(☃, ☃x) == null) {
               return ☃x;
            }
         }

         return null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         IBlockState ☃ = this.func_175847_a(Blocks.field_185774_da.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xx = this.func_175847_a(Blocks.field_150351_n.func_176223_P());
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos();
         this.field_74887_e.field_78895_b = 1000;
         this.field_74887_e.field_78894_e = 0;

         for(int ☃xxxxx = this.field_74887_e.field_78897_a; ☃xxxxx <= this.field_74887_e.field_78893_d; ++☃xxxxx) {
            for(int ☃xxxxxx = this.field_74887_e.field_78896_c; ☃xxxxxx <= this.field_74887_e.field_78892_f; ++☃xxxxxx) {
               ☃xxxx.func_181079_c(☃xxxxx, 64, ☃xxxxxx);
               if (☃.func_175898_b(☃xxxx)) {
                  int ☃xxxxxxx = ☃.func_201676_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ☃xxxx.func_177958_n(), ☃xxxx.func_177952_p());
                  ☃xxxx.func_181079_c(☃xxxx.func_177958_n(), ☃xxxxxxx, ☃xxxx.func_177952_p()).func_189536_c(EnumFacing.DOWN);
                  if (☃xxxx.func_177956_o() < ☃.func_181545_F()) {
                     ☃xxxx.func_185336_p(☃.func_181545_F() - 1);
                  }

                  while(☃xxxx.func_177956_o() >= ☃.func_181545_F() - 1) {
                     IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxx);
                     Block ☃xxxxxxxx = ☃xxxxxxx.func_177230_c();
                     if (☃xxxxxxxx == Blocks.field_196658_i && ☃.func_175623_d(☃xxxx.func_177984_a())) {
                        ☃.func_180501_a(☃xxxx, ☃, 2);
                        break;
                     }

                     if (☃xxxxxxx.func_185904_a().func_76224_d()) {
                        ☃.func_180501_a(new BlockPos(☃xxxx), ☃x, 2);
                        break;
                     }

                     if (☃xxxxxxxx == Blocks.field_150354_m
                        || ☃xxxxxxxx == Blocks.field_196611_F
                        || ☃xxxxxxxx == Blocks.field_150322_A
                        || ☃xxxxxxxx == Blocks.field_196583_aj
                        || ☃xxxxxxxx == Blocks.field_196585_ak
                        || ☃xxxxxxxx == Blocks.field_180395_cM
                        || ☃xxxxxxxx == Blocks.field_196583_aj
                        || ☃xxxxxxxx == Blocks.field_196585_ak) {
                        ☃.func_180501_a(☃xxxx, ☃xx, 2);
                        ☃.func_180501_a(☃xxxx.func_177977_b(), ☃xxx, 2);
                        break;
                     }

                     ☃xxxx.func_189536_c(EnumFacing.DOWN);
                  }

                  this.field_74887_e.field_78895_b = Math.min(this.field_74887_e.field_78895_b, ☃xxxx.func_177956_o());
                  this.field_74887_e.field_78894_e = Math.max(this.field_74887_e.field_78894_e, ☃xxxx.func_177956_o());
               }
            }
         }

         return true;
      }
   }

   public static class PieceWeight {
      public Class<? extends VillagePieces.Village> field_75090_a;
      public final int field_75088_b;
      public int field_75089_c;
      public int field_75087_d;

      public PieceWeight(Class<? extends VillagePieces.Village> var1, int var2, int var3) {
         this.field_75090_a = ☃;
         this.field_75088_b = ☃;
         this.field_75087_d = ☃;
      }

      public boolean func_75085_a(int var1) {
         return this.field_75087_d == 0 || this.field_75089_c < this.field_75087_d;
      }

      public boolean func_75086_a() {
         return this.field_75087_d == 0 || this.field_75089_c < this.field_75087_d;
      }
   }

   public abstract static class Road extends VillagePieces.Village {
      public Road() {
      }

      protected Road(VillagePieces.Start var1, int var2) {
         super(☃, ☃);
      }
   }

   public static class Start extends VillagePieces.Well {
      public int field_74928_c;
      public VillagePieces.PieceWeight field_74926_d;
      public List<VillagePieces.PieceWeight> field_74931_h;
      public List<StructurePiece> field_74932_i = Lists.<StructurePiece>newArrayList();
      public List<StructurePiece> field_74930_j = Lists.<StructurePiece>newArrayList();

      public Start() {
      }

      public Start(int var1, Random var2, int var3, int var4, List<VillagePieces.PieceWeight> var5, VillageConfig var6) {
         super(null, 0, ☃, ☃, ☃);
         this.field_74931_h = ☃;
         this.field_74928_c = ☃.field_202461_a;
         this.field_189928_h = ☃.field_202462_b;
         this.func_202579_a(this.field_189928_h);
         this.field_189929_i = ☃.nextInt(50) == 0;
      }
   }

   public static class Torch extends VillagePieces.Village {
      public Torch() {
      }

      public Torch(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static MutableBoundingBox func_175856_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 3, 4, 2, ☃);
         return StructurePiece.func_74883_a(☃, ☃) != null ? null : ☃;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 4 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 0, 0, 0, 2, 3, 1, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175811_a(☃, ☃, 1, 0, 0, ☃);
         this.func_175811_a(☃, ☃, 1, 1, 0, ☃);
         this.func_175811_a(☃, ☃, 1, 2, 0, ☃);
         this.func_175811_a(☃, Blocks.field_196602_ba.func_176223_P(), 1, 3, 0, ☃);
         this.func_189926_a(☃, EnumFacing.EAST, 2, 3, 0, ☃);
         this.func_189926_a(☃, EnumFacing.NORTH, 1, 3, 1, ☃);
         this.func_189926_a(☃, EnumFacing.WEST, 0, 3, 0, ☃);
         this.func_189926_a(☃, EnumFacing.SOUTH, 1, 3, -1, ☃);
         return true;
      }
   }

   public static enum Type {
      OAK(0),
      SANDSTONE(1),
      ACACIA(2),
      SPRUCE(3);

      private final int field_202605_e;

      private Type(int var3) {
         this.field_202605_e = ☃;
      }

      public int func_202604_a() {
         return this.field_202605_e;
      }

      public static VillagePieces.Type func_202603_a(int var0) {
         VillagePieces.Type[] ☃ = values();
         return ☃ >= 0 && ☃ < ☃.length ? ☃[☃] : OAK;
      }
   }

   abstract static class Village extends StructurePiece {
      protected int field_143015_k = -1;
      private int field_74896_a;
      protected VillagePieces.Type field_189928_h;
      protected boolean field_189929_i;

      public Village() {
      }

      protected Village(VillagePieces.Start var1, int var2) {
         super(☃);
         if (☃ != null) {
            this.field_189928_h = ☃.field_189928_h;
            this.field_189929_i = ☃.field_189929_i;
         }
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         ☃.func_74768_a("HPos", this.field_143015_k);
         ☃.func_74768_a("VCount", this.field_74896_a);
         ☃.func_74774_a("Type", (byte)this.field_189928_h.func_202604_a());
         ☃.func_74757_a("Zombie", this.field_189929_i);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         this.field_143015_k = ☃.func_74762_e("HPos");
         this.field_74896_a = ☃.func_74762_e("VCount");
         this.field_189928_h = VillagePieces.Type.func_202603_a(☃.func_74771_c("Type"));
         if (☃.func_74767_n("Desert")) {
            this.field_189928_h = VillagePieces.Type.SANDSTONE;
         }

         this.field_189929_i = ☃.func_74767_n("Zombie");
      }

      @Nullable
      protected StructurePiece func_74891_a(VillagePieces.Start var1, List<StructurePiece> var2, Random var3, int var4, int var5) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
               default:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.WEST,
                     this.func_74877_c()
                  );
               case SOUTH:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a - 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.WEST,
                     this.func_74877_c()
                  );
               case WEST:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c()
                  );
               case EAST:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c - 1,
                     EnumFacing.NORTH,
                     this.func_74877_c()
                  );
            }
         } else {
            return null;
         }
      }

      @Nullable
      protected StructurePiece func_74894_b(VillagePieces.Start var1, List<StructurePiece> var2, Random var3, int var4, int var5) {
         EnumFacing ☃ = this.func_186165_e();
         if (☃ != null) {
            switch(☃) {
               case NORTH:
               default:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.EAST,
                     this.func_74877_c()
                  );
               case SOUTH:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78893_d + 1,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78896_c + ☃,
                     EnumFacing.EAST,
                     this.func_74877_c()
                  );
               case WEST:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c()
                  );
               case EAST:
                  return VillagePieces.func_176066_d(
                     ☃,
                     ☃,
                     ☃,
                     this.field_74887_e.field_78897_a + ☃,
                     this.field_74887_e.field_78895_b + ☃,
                     this.field_74887_e.field_78892_f + 1,
                     EnumFacing.SOUTH,
                     this.func_74877_c()
                  );
            }
         } else {
            return null;
         }
      }

      protected int func_74889_b(IWorld var1, MutableBoundingBox var2) {
         int ☃ = 0;
         int ☃x = 0;
         BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();

         for(int ☃xxx = this.field_74887_e.field_78896_c; ☃xxx <= this.field_74887_e.field_78892_f; ++☃xxx) {
            for(int ☃xxxx = this.field_74887_e.field_78897_a; ☃xxxx <= this.field_74887_e.field_78893_d; ++☃xxxx) {
               ☃xx.func_181079_c(☃xxxx, 64, ☃xxx);
               if (☃.func_175898_b(☃xx)) {
                  ☃ += ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ☃xx).func_177956_o();
                  ++☃x;
               }
            }
         }

         return ☃x == 0 ? -1 : ☃ / ☃x;
      }

      protected static boolean func_74895_a(MutableBoundingBox var0) {
         return ☃ != null && ☃.field_78895_b > 10;
      }

      protected void func_74893_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6) {
         if (this.field_74896_a < ☃) {
            for(int ☃ = this.field_74896_a; ☃ < ☃; ++☃) {
               int ☃x = this.func_74865_a(☃ + ☃, ☃);
               int ☃xx = this.func_74862_a(☃);
               int ☃xxx = this.func_74873_b(☃ + ☃, ☃);
               if (!☃.func_175898_b(new BlockPos(☃x, ☃xx, ☃xxx))) {
                  break;
               }

               ++this.field_74896_a;
               if (this.field_189929_i) {
                  EntityZombieVillager ☃x = new EntityZombieVillager(☃.func_201672_e());
                  ☃x.func_70012_b((double)☃x + 0.5, (double)☃xx, (double)☃xxx + 0.5, 0.0F, 0.0F);
                  ☃x.func_204210_a(☃.func_175649_E(new BlockPos(☃x)), null, null);
                  ☃x.func_190733_a(this.func_180779_c(☃, 0));
                  ☃x.func_110163_bv();
                  ☃.func_72838_d(☃x);
               } else {
                  EntityVillager ☃x = new EntityVillager(☃.func_201672_e());
                  ☃x.func_70012_b((double)☃x + 0.5, (double)☃xx, (double)☃xxx + 0.5, 0.0F, 0.0F);
                  ☃x.func_70938_b(this.func_180779_c(☃, ☃.func_201674_k().nextInt(6)));
                  ☃x.func_190672_a(☃.func_175649_E(new BlockPos(☃x)), null, null, false);
                  ☃.func_72838_d(☃x);
               }
            }
         }
      }

      protected int func_180779_c(int var1, int var2) {
         return ☃;
      }

      protected IBlockState func_175847_a(IBlockState var1) {
         Block ☃ = ☃.func_177230_c();
         if (this.field_189928_h == VillagePieces.Type.SANDSTONE) {
            if (☃.func_203417_a(BlockTags.field_200031_h) || ☃ == Blocks.field_150347_e) {
               return Blocks.field_150322_A.func_176223_P();
            }

            if (☃.func_203417_a(BlockTags.field_199898_b)) {
               return Blocks.field_196585_ak.func_176223_P();
            }

            if (☃ == Blocks.field_150476_ad) {
               return Blocks.field_150372_bz.func_176223_P().func_206870_a(BlockStairs.field_176309_a, ☃.func_177229_b(BlockStairs.field_176309_a));
            }

            if (☃ == Blocks.field_196659_cl) {
               return Blocks.field_150372_bz.func_176223_P().func_206870_a(BlockStairs.field_176309_a, ☃.func_177229_b(BlockStairs.field_176309_a));
            }

            if (☃ == Blocks.field_150351_n) {
               return Blocks.field_150322_A.func_176223_P();
            }

            if (☃ == Blocks.field_196663_cq) {
               return Blocks.field_196667_cs.func_176223_P();
            }
         } else if (this.field_189928_h == VillagePieces.Type.SPRUCE) {
            if (☃.func_203417_a(BlockTags.field_200031_h)) {
               return Blocks.field_196618_L.func_176223_P().func_206870_a(BlockLog.field_176298_M, ☃.func_177229_b(BlockLog.field_176298_M));
            }

            if (☃.func_203417_a(BlockTags.field_199898_b)) {
               return Blocks.field_196664_o.func_176223_P();
            }

            if (☃ == Blocks.field_150476_ad) {
               return Blocks.field_150485_bF.func_176223_P().func_206870_a(BlockStairs.field_176309_a, ☃.func_177229_b(BlockStairs.field_176309_a));
            }

            if (☃ == Blocks.field_180407_aO) {
               return Blocks.field_180408_aP.func_176223_P();
            }

            if (☃ == Blocks.field_196663_cq) {
               return Blocks.field_196665_cr.func_176223_P();
            }
         } else if (this.field_189928_h == VillagePieces.Type.ACACIA) {
            if (☃.func_203417_a(BlockTags.field_200031_h)) {
               return Blocks.field_196621_O.func_176223_P().func_206870_a(BlockLog.field_176298_M, ☃.func_177229_b(BlockLog.field_176298_M));
            }

            if (☃.func_203417_a(BlockTags.field_199898_b)) {
               return Blocks.field_196670_r.func_176223_P();
            }

            if (☃ == Blocks.field_150476_ad) {
               return Blocks.field_150400_ck.func_176223_P().func_206870_a(BlockStairs.field_176309_a, ☃.func_177229_b(BlockStairs.field_176309_a));
            }

            if (☃ == Blocks.field_150347_e) {
               return Blocks.field_196621_O.func_176223_P().func_206870_a(BlockLog.field_176298_M, EnumFacing.Axis.Y);
            }

            if (☃ == Blocks.field_180407_aO) {
               return Blocks.field_180405_aT.func_176223_P();
            }

            if (☃ == Blocks.field_196663_cq) {
               return Blocks.field_196671_cu.func_176223_P();
            }
         }

         return ☃;
      }

      protected BlockDoor func_189925_i() {
         if (this.field_189928_h == VillagePieces.Type.ACACIA) {
            return (BlockDoor)Blocks.field_180410_as;
         } else {
            return this.field_189928_h == VillagePieces.Type.SPRUCE ? (BlockDoor)Blocks.field_180414_ap : (BlockDoor)Blocks.field_180413_ao;
         }
      }

      protected void func_189927_a(IWorld var1, MutableBoundingBox var2, Random var3, int var4, int var5, int var6, EnumFacing var7) {
         if (!this.field_189929_i) {
            this.func_189915_a(☃, ☃, ☃, ☃, ☃, ☃, EnumFacing.NORTH, this.func_189925_i());
         }
      }

      protected void func_189926_a(IWorld var1, EnumFacing var2, int var3, int var4, int var5, MutableBoundingBox var6) {
         if (!this.field_189929_i) {
            this.func_175811_a(☃, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, ☃), ☃, ☃, ☃, ☃);
         }
      }

      @Override
      protected void func_175808_b(IWorld var1, IBlockState var2, int var3, int var4, int var5, MutableBoundingBox var6) {
         IBlockState ☃ = this.func_175847_a(☃);
         super.func_175808_b(☃, ☃, ☃, ☃, ☃, ☃);
      }

      protected void func_202579_a(VillagePieces.Type var1) {
         this.field_189928_h = ☃;
      }
   }

   public static class Well extends VillagePieces.Village {
      public Well() {
      }

      public Well(VillagePieces.Start var1, int var2, Random var3, int var4, int var5) {
         super(☃, ☃);
         this.func_186164_a(EnumFacing.Plane.HORIZONTAL.func_179518_a(☃));
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new MutableBoundingBox(☃, 64, ☃, ☃ + 6 - 1, 78, ☃ + 6 - 1);
         } else {
            this.field_74887_e = new MutableBoundingBox(☃, 64, ☃, ☃ + 6 - 1, 78, ☃ + 6 - 1);
         }
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         VillagePieces.func_176069_e(
            (VillagePieces.Start)☃,
            ☃,
            ☃,
            this.field_74887_e.field_78897_a - 1,
            this.field_74887_e.field_78894_e - 4,
            this.field_74887_e.field_78896_c + 1,
            EnumFacing.WEST,
            this.func_74877_c()
         );
         VillagePieces.func_176069_e(
            (VillagePieces.Start)☃,
            ☃,
            ☃,
            this.field_74887_e.field_78893_d + 1,
            this.field_74887_e.field_78894_e - 4,
            this.field_74887_e.field_78896_c + 1,
            EnumFacing.EAST,
            this.func_74877_c()
         );
         VillagePieces.func_176069_e(
            (VillagePieces.Start)☃,
            ☃,
            ☃,
            this.field_74887_e.field_78897_a + 1,
            this.field_74887_e.field_78894_e - 4,
            this.field_74887_e.field_78896_c - 1,
            EnumFacing.NORTH,
            this.func_74877_c()
         );
         VillagePieces.func_176069_e(
            (VillagePieces.Start)☃,
            ☃,
            ☃,
            this.field_74887_e.field_78897_a + 1,
            this.field_74887_e.field_78894_e - 4,
            this.field_74887_e.field_78892_f + 1,
            EnumFacing.SOUTH,
            this.func_74877_c()
         );
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 3, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 1, 0, 1, 4, 12, 4, ☃, Blocks.field_150355_j.func_176223_P(), false);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 12, 2, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 3, 12, 2, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 2, 12, 3, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 3, 12, 3, ☃);
         this.func_175811_a(☃, ☃x, 1, 13, 1, ☃);
         this.func_175811_a(☃, ☃x, 1, 14, 1, ☃);
         this.func_175811_a(☃, ☃x, 4, 13, 1, ☃);
         this.func_175811_a(☃, ☃x, 4, 14, 1, ☃);
         this.func_175811_a(☃, ☃x, 1, 13, 4, ☃);
         this.func_175811_a(☃, ☃x, 1, 14, 4, ☃);
         this.func_175811_a(☃, ☃x, 4, 13, 4, ☃);
         this.func_175811_a(☃, ☃x, 4, 14, 4, ☃);
         this.func_175804_a(☃, ☃, 1, 15, 1, 4, 15, 4, ☃, ☃, false);

         for(int ☃xx = 0; ☃xx <= 5; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= 5; ++☃xxx) {
               if (☃xxx == 0 || ☃xxx == 5 || ☃xx == 0 || ☃xx == 5) {
                  this.func_175811_a(☃, ☃, ☃xxx, 11, ☃xx, ☃);
                  this.func_74871_b(☃, ☃xxx, 12, ☃xx, ☃);
               }
            }
         }

         return true;
      }
   }

   public static class WoodHut extends VillagePieces.Village {
      private boolean field_74909_b;
      private int field_74910_c;

      public WoodHut() {
      }

      public WoodHut(VillagePieces.Start var1, int var2, Random var3, MutableBoundingBox var4, EnumFacing var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_74909_b = ☃.nextBoolean();
         this.field_74910_c = ☃.nextInt(3);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74768_a("T", this.field_74910_c);
         ☃.func_74757_a("C", this.field_74909_b);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74910_c = ☃.func_74762_e("T");
         this.field_74909_b = ☃.func_74767_n("C");
      }

      public static VillagePieces.WoodHut func_175853_a(
         VillagePieces.Start var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         MutableBoundingBox ☃ = MutableBoundingBox.func_175897_a(☃, ☃, ☃, 0, 0, 0, 4, 6, 5, ☃);
         return func_74895_a(☃) && StructurePiece.func_74883_a(☃, ☃) == null ? new VillagePieces.WoodHut(☃, ☃, ☃, ☃, ☃) : null;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.func_74889_b(☃, ☃);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.field_74887_e.func_78886_a(0, this.field_143015_k - this.field_74887_e.field_78894_e + 6 - 1, 0);
         }

         IBlockState ☃ = this.func_175847_a(Blocks.field_150347_e.func_176223_P());
         IBlockState ☃x = this.func_175847_a(Blocks.field_196662_n.func_176223_P());
         IBlockState ☃xx = this.func_175847_a(Blocks.field_196659_cl.func_176223_P().func_206870_a(BlockStairs.field_176309_a, EnumFacing.NORTH));
         IBlockState ☃xxx = this.func_175847_a(Blocks.field_196617_K.func_176223_P());
         IBlockState ☃xxxx = this.func_175847_a(Blocks.field_180407_aO.func_176223_P());
         this.func_175804_a(☃, ☃, 1, 1, 1, 3, 5, 4, Blocks.field_150350_a.func_176223_P(), Blocks.field_150350_a.func_176223_P(), false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 3, 0, 4, ☃, ☃, false);
         this.func_175804_a(☃, ☃, 1, 0, 1, 2, 0, 3, Blocks.field_150346_d.func_176223_P(), Blocks.field_150346_d.func_176223_P(), false);
         if (this.field_74909_b) {
            this.func_175804_a(☃, ☃, 1, 4, 1, 2, 4, 3, ☃xxx, ☃xxx, false);
         } else {
            this.func_175804_a(☃, ☃, 1, 5, 1, 2, 5, 3, ☃xxx, ☃xxx, false);
         }

         this.func_175811_a(☃, ☃xxx, 1, 4, 0, ☃);
         this.func_175811_a(☃, ☃xxx, 2, 4, 0, ☃);
         this.func_175811_a(☃, ☃xxx, 1, 4, 4, ☃);
         this.func_175811_a(☃, ☃xxx, 2, 4, 4, ☃);
         this.func_175811_a(☃, ☃xxx, 0, 4, 1, ☃);
         this.func_175811_a(☃, ☃xxx, 0, 4, 2, ☃);
         this.func_175811_a(☃, ☃xxx, 0, 4, 3, ☃);
         this.func_175811_a(☃, ☃xxx, 3, 4, 1, ☃);
         this.func_175811_a(☃, ☃xxx, 3, 4, 2, ☃);
         this.func_175811_a(☃, ☃xxx, 3, 4, 3, ☃);
         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 3, 0, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 3, 1, 0, 3, 3, 0, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 0, 1, 4, 0, 3, 4, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 3, 1, 4, 3, 3, 4, ☃xxx, ☃xxx, false);
         this.func_175804_a(☃, ☃, 0, 1, 1, 0, 3, 3, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 3, 1, 1, 3, 3, 3, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 2, 3, 0, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 1, 1, 4, 2, 3, 4, ☃x, ☃x, false);
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            0,
            2,
            2,
            ☃
         );
         this.func_175811_a(
            ☃,
            Blocks.field_150410_aZ
               .func_176223_P()
               .func_206870_a(BlockGlassPane.field_196413_c, Boolean.valueOf(true))
               .func_206870_a(BlockGlassPane.field_196409_a, Boolean.valueOf(true)),
            3,
            2,
            2,
            ☃
         );
         if (this.field_74910_c > 0) {
            this.func_175811_a(
               ☃,
               ☃xxxx.func_206870_a(BlockFence.field_196409_a, Boolean.valueOf(true))
                  .func_206870_a(this.field_74910_c == 1 ? BlockFence.field_196414_y : BlockFence.field_196411_b, Boolean.valueOf(true)),
               this.field_74910_c,
               1,
               3,
               ☃
            );
            this.func_175811_a(☃, Blocks.field_196663_cq.func_176223_P(), this.field_74910_c, 2, 3, ☃);
         }

         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 1, 1, 0, ☃);
         this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), 1, 2, 0, ☃);
         this.func_189927_a(☃, ☃, ☃, 1, 1, 0, EnumFacing.NORTH);
         if (this.func_175807_a(☃, 1, 0, -1, ☃).func_196958_f() && !this.func_175807_a(☃, 1, -1, -1, ☃).func_196958_f()) {
            this.func_175811_a(☃, ☃xx, 1, 0, -1, ☃);
            if (this.func_175807_a(☃, 1, -1, -1, ☃).func_177230_c() == Blocks.field_185774_da) {
               this.func_175811_a(☃, Blocks.field_196658_i.func_176223_P(), 1, -1, -1, ☃);
            }
         }

         for(int ☃ = 0; ☃ < 5; ++☃) {
            for(int ☃x = 0; ☃x < 4; ++☃x) {
               this.func_74871_b(☃, ☃x, 6, ☃, ☃);
               this.func_175808_b(☃, ☃, ☃x, -1, ☃, ☃);
            }
         }

         this.func_74893_a(☃, ☃, 1, 1, 2, 1);
         return true;
      }
   }
}
