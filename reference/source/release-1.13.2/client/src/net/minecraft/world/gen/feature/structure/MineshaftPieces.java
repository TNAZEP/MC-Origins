package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class MineshaftPieces {
   public static void func_143048_a() {
      StructureIO.func_143031_a(MineshaftPieces.Corridor.class, "MSCorridor");
      StructureIO.func_143031_a(MineshaftPieces.Cross.class, "MSCrossing");
      StructureIO.func_143031_a(MineshaftPieces.Room.class, "MSRoom");
      StructureIO.func_143031_a(MineshaftPieces.Stairs.class, "MSStairs");
   }

   private static MineshaftPieces.Piece func_189940_a(
      List<StructurePiece> var0, Random var1, int var2, int var3, int var4, @Nullable EnumFacing var5, int var6, MineshaftStructure.Type var7
   ) {
      int ☃ = ☃.nextInt(100);
      if (☃ >= 80) {
         MutableBoundingBox ☃x = MineshaftPieces.Cross.func_175813_a(☃, ☃, ☃, ☃, ☃, ☃);
         if (☃x != null) {
            return new MineshaftPieces.Cross(☃, ☃, ☃x, ☃, ☃);
         }
      } else if (☃ >= 70) {
         MutableBoundingBox ☃ = MineshaftPieces.Stairs.func_175812_a(☃, ☃, ☃, ☃, ☃, ☃);
         if (☃ != null) {
            return new MineshaftPieces.Stairs(☃, ☃, ☃, ☃, ☃);
         }
      } else {
         MutableBoundingBox ☃ = MineshaftPieces.Corridor.func_175814_a(☃, ☃, ☃, ☃, ☃, ☃);
         if (☃ != null) {
            return new MineshaftPieces.Corridor(☃, ☃, ☃, ☃, ☃);
         }
      }

      return null;
   }

   private static MineshaftPieces.Piece func_189938_b(
      StructurePiece var0, List<StructurePiece> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (☃ > 8) {
         return null;
      } else if (Math.abs(☃ - ☃.func_74874_b().field_78897_a) <= 80 && Math.abs(☃ - ☃.func_74874_b().field_78896_c) <= 80) {
         MineshaftStructure.Type ☃ = ((MineshaftPieces.Piece)☃).field_189920_a;
         MineshaftPieces.Piece ☃x = func_189940_a(☃, ☃, ☃, ☃, ☃, ☃, ☃ + 1, ☃);
         if (☃x != null) {
            ☃.add(☃x);
            ☃x.func_74861_a(☃, ☃, ☃);
         }

         return ☃x;
      } else {
         return null;
      }
   }

   public static class Corridor extends MineshaftPieces.Piece {
      private boolean field_74958_a;
      private boolean field_74956_b;
      private boolean field_74957_c;
      private int field_74955_d;

      public Corridor() {
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("hr", this.field_74958_a);
         ☃.func_74757_a("sc", this.field_74956_b);
         ☃.func_74757_a("hps", this.field_74957_c);
         ☃.func_74768_a("Num", this.field_74955_d);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74958_a = ☃.func_74767_n("hr");
         this.field_74956_b = ☃.func_74767_n("sc");
         this.field_74957_c = ☃.func_74767_n("hps");
         this.field_74955_d = ☃.func_74762_e("Num");
      }

      public Corridor(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4, MineshaftStructure.Type var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
         this.field_74958_a = ☃.nextInt(3) == 0;
         this.field_74956_b = !this.field_74958_a && ☃.nextInt(23) == 0;
         if (this.func_186165_e().func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74955_d = ☃.func_78880_d() / 5;
         } else {
            this.field_74955_d = ☃.func_78883_b() / 5;
         }
      }

      public static MutableBoundingBox func_175814_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         MutableBoundingBox ☃ = new MutableBoundingBox(☃, ☃, ☃, ☃, ☃ + 3 - 1, ☃);

         int ☃;
         for(☃ = ☃.nextInt(3) + 2; ☃ > 0; --☃) {
            int ☃x = ☃ * 5;
            switch(☃) {
               case NORTH:
               default:
                  ☃.field_78893_d = ☃ + 3 - 1;
                  ☃.field_78896_c = ☃ - (☃x - 1);
                  break;
               case SOUTH:
                  ☃.field_78893_d = ☃ + 3 - 1;
                  ☃.field_78892_f = ☃ + ☃x - 1;
                  break;
               case WEST:
                  ☃.field_78897_a = ☃ - (☃x - 1);
                  ☃.field_78892_f = ☃ + 3 - 1;
                  break;
               case EAST:
                  ☃.field_78893_d = ☃ + ☃x - 1;
                  ☃.field_78892_f = ☃ + 3 - 1;
            }

            if (StructurePiece.func_74883_a(☃, ☃) == null) {
               break;
            }
         }

         return ☃ > 0 ? ☃ : null;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         int ☃ = this.func_74877_c();
         int ☃x = ☃.nextInt(4);
         EnumFacing ☃xx = this.func_186165_e();
         if (☃xx != null) {
            switch(☃xx) {
               case NORTH:
               default:
                  if (☃x <= 1) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c - 1,
                        ☃xx,
                        ☃
                     );
                  } else if (☃x == 2) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a - 1,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c,
                        EnumFacing.WEST,
                        ☃
                     );
                  } else {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78893_d + 1,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c,
                        EnumFacing.EAST,
                        ☃
                     );
                  }
                  break;
               case SOUTH:
                  if (☃x <= 1) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78892_f + 1,
                        ☃xx,
                        ☃
                     );
                  } else if (☃x == 2) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a - 1,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78892_f - 3,
                        EnumFacing.WEST,
                        ☃
                     );
                  } else {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78893_d + 1,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78892_f - 3,
                        EnumFacing.EAST,
                        ☃
                     );
                  }
                  break;
               case WEST:
                  if (☃x <= 1) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a - 1,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c,
                        ☃xx,
                        ☃
                     );
                  } else if (☃x == 2) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c - 1,
                        EnumFacing.NORTH,
                        ☃
                     );
                  } else {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78897_a,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78892_f + 1,
                        EnumFacing.SOUTH,
                        ☃
                     );
                  }
                  break;
               case EAST:
                  if (☃x <= 1) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78893_d + 1,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c,
                        ☃xx,
                        ☃
                     );
                  } else if (☃x == 2) {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78893_d - 3,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78896_c - 1,
                        EnumFacing.NORTH,
                        ☃
                     );
                  } else {
                     MineshaftPieces.func_189938_b(
                        ☃,
                        ☃,
                        ☃,
                        this.field_74887_e.field_78893_d - 3,
                        this.field_74887_e.field_78895_b - 1 + ☃.nextInt(3),
                        this.field_74887_e.field_78892_f + 1,
                        EnumFacing.SOUTH,
                        ☃
                     );
                  }
            }
         }

         if (☃ < 8) {
            if (☃xx != EnumFacing.NORTH && ☃xx != EnumFacing.SOUTH) {
               for(int ☃ = this.field_74887_e.field_78897_a + 3; ☃ + 3 <= this.field_74887_e.field_78893_d; ☃ += 5) {
                  int ☃x = ☃.nextInt(5);
                  if (☃x == 0) {
                     MineshaftPieces.func_189938_b(☃, ☃, ☃, ☃, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, ☃ + 1);
                  } else if (☃x == 1) {
                     MineshaftPieces.func_189938_b(☃, ☃, ☃, ☃, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, ☃ + 1);
                  }
               }
            } else {
               for(int ☃ = this.field_74887_e.field_78896_c + 3; ☃ + 3 <= this.field_74887_e.field_78892_f; ☃ += 5) {
                  int ☃x = ☃.nextInt(5);
                  if (☃x == 0) {
                     MineshaftPieces.func_189938_b(☃, ☃, ☃, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, ☃, EnumFacing.WEST, ☃ + 1);
                  } else if (☃x == 1) {
                     MineshaftPieces.func_189938_b(☃, ☃, ☃, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, ☃, EnumFacing.EAST, ☃ + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean func_186167_a(IWorld var1, MutableBoundingBox var2, Random var3, int var4, int var5, int var6, ResourceLocation var7) {
         BlockPos ☃ = new BlockPos(this.func_74865_a(☃, ☃), this.func_74862_a(☃), this.func_74873_b(☃, ☃));
         if (☃.func_175898_b(☃) && ☃.func_180495_p(☃).func_196958_f() && !☃.func_180495_p(☃.func_177977_b()).func_196958_f()) {
            IBlockState ☃x = Blocks.field_150448_aq
               .func_176223_P()
               .func_206870_a(BlockRail.field_176565_b, ☃.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
            this.func_175811_a(☃, ☃x, ☃, ☃, ☃, ☃);
            EntityMinecartChest ☃xx = new EntityMinecartChest(
               ☃.func_201672_e(),
               (double)((float)☃.func_177958_n() + 0.5F),
               (double)((float)☃.func_177956_o() + 0.5F),
               (double)((float)☃.func_177952_p() + 0.5F)
            );
            ☃xx.func_184289_a(☃, ☃.nextLong());
            ☃.func_72838_d(☃xx);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.func_74860_a(☃, ☃)) {
            return false;
         } else {
            int ☃ = 0;
            int ☃x = 2;
            int ☃xx = 0;
            int ☃xxx = 2;
            int ☃xxxx = this.field_74955_d * 5 - 1;
            IBlockState ☃xxxxx = this.func_189917_F_();
            this.func_175804_a(☃, ☃, 0, 0, 0, 2, 1, ☃xxxx, field_202556_l, field_202556_l, false);
            this.func_189914_a(☃, ☃, ☃, 0.8F, 0, 2, 0, 2, 2, ☃xxxx, field_202556_l, field_202556_l, false, false);
            if (this.field_74956_b) {
               this.func_189914_a(☃, ☃, ☃, 0.6F, 0, 0, 0, 2, 1, ☃xxxx, Blocks.field_196553_aF.func_176223_P(), field_202556_l, false, true);
            }

            for(int ☃ = 0; ☃ < this.field_74955_d; ++☃) {
               int ☃x = 2 + ☃ * 5;
               this.func_189921_a(☃, ☃, 0, 0, ☃x, 2, 2, ☃);
               this.func_189922_a(☃, ☃, ☃, 0.1F, 0, 2, ☃x - 1);
               this.func_189922_a(☃, ☃, ☃, 0.1F, 2, 2, ☃x - 1);
               this.func_189922_a(☃, ☃, ☃, 0.1F, 0, 2, ☃x + 1);
               this.func_189922_a(☃, ☃, ☃, 0.1F, 2, 2, ☃x + 1);
               this.func_189922_a(☃, ☃, ☃, 0.05F, 0, 2, ☃x - 2);
               this.func_189922_a(☃, ☃, ☃, 0.05F, 2, 2, ☃x - 2);
               this.func_189922_a(☃, ☃, ☃, 0.05F, 0, 2, ☃x + 2);
               this.func_189922_a(☃, ☃, ☃, 0.05F, 2, 2, ☃x + 2);
               if (☃.nextInt(100) == 0) {
                  this.func_186167_a(☃, ☃, ☃, 2, 0, ☃x - 1, LootTableList.field_186424_f);
               }

               if (☃.nextInt(100) == 0) {
                  this.func_186167_a(☃, ☃, ☃, 0, 0, ☃x + 1, LootTableList.field_186424_f);
               }

               if (this.field_74956_b && !this.field_74957_c) {
                  int ☃x = this.func_74862_a(0);
                  int ☃xx = ☃x - 1 + ☃.nextInt(3);
                  int ☃xxx = this.func_74865_a(1, ☃xx);
                  int ☃xxxx = this.func_74873_b(1, ☃xx);
                  BlockPos ☃xxxxx = new BlockPos(☃xxx, ☃x, ☃xxxx);
                  if (☃.func_175898_b(☃xxxxx) && this.func_189916_b(☃, 1, 0, ☃xx, ☃)) {
                     this.field_74957_c = true;
                     ☃.func_180501_a(☃xxxxx, Blocks.field_150474_ac.func_176223_P(), 2);
                     TileEntity ☃xxxxxx = ☃.func_175625_s(☃xxxxx);
                     if (☃xxxxxx instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)☃xxxxxx).func_145881_a().func_200876_a(EntityType.field_200794_h);
                     }
                  }
               }
            }

            for(int ☃ = 0; ☃ <= 2; ++☃) {
               for(int ☃x = 0; ☃x <= ☃xxxx; ++☃x) {
                  int ☃xx = -1;
                  IBlockState ☃xxx = this.func_175807_a(☃, ☃, -1, ☃x, ☃);
                  if (☃xxx.func_196958_f() && this.func_189916_b(☃, ☃, -1, ☃x, ☃)) {
                     int ☃xxxx = -1;
                     this.func_175811_a(☃, ☃xxxxx, ☃, -1, ☃x, ☃);
                  }
               }
            }

            if (this.field_74958_a) {
               IBlockState ☃ = Blocks.field_150448_aq.func_176223_P().func_206870_a(BlockRail.field_176565_b, RailShape.NORTH_SOUTH);

               for(int ☃x = 0; ☃x <= ☃xxxx; ++☃x) {
                  IBlockState ☃xx = this.func_175807_a(☃, 1, -1, ☃x, ☃);
                  if (!☃xx.func_196958_f() && ☃xx.func_200015_d(☃, new BlockPos(this.func_74865_a(1, ☃x), this.func_74862_a(-1), this.func_74873_b(1, ☃x)))) {
                     float ☃xxx = this.func_189916_b(☃, 1, 0, ☃x, ☃) ? 0.7F : 0.9F;
                     this.func_175809_a(☃, ☃, ☃, ☃xxx, 1, 0, ☃x, ☃);
                  }
               }
            }

            return true;
         }
      }

      private void func_189921_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6, int var7, Random var8) {
         if (this.func_189918_a(☃, ☃, ☃, ☃, ☃, ☃)) {
            IBlockState ☃ = this.func_189917_F_();
            IBlockState ☃x = this.func_189919_b();
            this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃, ☃ - 1, ☃, ☃x.func_206870_a(BlockFence.field_196414_y, Boolean.valueOf(true)), field_202556_l, false);
            this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃, ☃ - 1, ☃, ☃x.func_206870_a(BlockFence.field_196411_b, Boolean.valueOf(true)), field_202556_l, false);
            if (☃.nextInt(4) == 0) {
               this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, field_202556_l, false);
               this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, field_202556_l, false);
            } else {
               this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, field_202556_l, false);
               this.func_175809_a(
                  ☃, ☃, ☃, 0.05F, ☃ + 1, ☃, ☃ - 1, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.NORTH)
               );
               this.func_175809_a(
                  ☃, ☃, ☃, 0.05F, ☃ + 1, ☃, ☃ + 1, Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, EnumFacing.SOUTH)
               );
            }
         }
      }

      private void func_189922_a(IWorld var1, MutableBoundingBox var2, Random var3, float var4, int var5, int var6, int var7) {
         if (this.func_189916_b(☃, ☃, ☃, ☃, ☃)) {
            this.func_175809_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, Blocks.field_196553_aF.func_176223_P());
         }
      }
   }

   public static class Cross extends MineshaftPieces.Piece {
      private EnumFacing field_74953_a;
      private boolean field_74952_b;

      public Cross() {
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74757_a("tf", this.field_74952_b);
         ☃.func_74768_a("D", this.field_74953_a.func_176736_b());
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_74952_b = ☃.func_74767_n("tf");
         this.field_74953_a = EnumFacing.func_176731_b(☃.func_74762_e("D"));
      }

      public Cross(int var1, Random var2, MutableBoundingBox var3, @Nullable EnumFacing var4, MineshaftStructure.Type var5) {
         super(☃, ☃);
         this.field_74953_a = ☃;
         this.field_74887_e = ☃;
         this.field_74952_b = ☃.func_78882_c() > 3;
      }

      public static MutableBoundingBox func_175813_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         MutableBoundingBox ☃ = new MutableBoundingBox(☃, ☃, ☃, ☃, ☃ + 3 - 1, ☃);
         if (☃.nextInt(4) == 0) {
            ☃.field_78894_e += 4;
         }

         switch(☃) {
            case NORTH:
            default:
               ☃.field_78897_a = ☃ - 1;
               ☃.field_78893_d = ☃ + 3;
               ☃.field_78896_c = ☃ - 4;
               break;
            case SOUTH:
               ☃.field_78897_a = ☃ - 1;
               ☃.field_78893_d = ☃ + 3;
               ☃.field_78892_f = ☃ + 3 + 1;
               break;
            case WEST:
               ☃.field_78897_a = ☃ - 4;
               ☃.field_78896_c = ☃ - 1;
               ☃.field_78892_f = ☃ + 3;
               break;
            case EAST:
               ☃.field_78893_d = ☃ + 3 + 1;
               ☃.field_78896_c = ☃ - 1;
               ☃.field_78892_f = ☃ + 3;
         }

         return StructurePiece.func_74883_a(☃, ☃) != null ? null : ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         int ☃ = this.func_74877_c();
         switch(this.field_74953_a) {
            case NORTH:
            default:
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, ☃
               );
               break;
            case SOUTH:
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, ☃
               );
               break;
            case WEST:
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.WEST, ☃
               );
               break;
            case EAST:
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, ☃
               );
               MineshaftPieces.func_189938_b(
                  ☃, ☃, ☃, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, EnumFacing.EAST, ☃
               );
         }

         if (this.field_74952_b) {
            if (☃.nextBoolean()) {
               MineshaftPieces.func_189938_b(
                  ☃,
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a + 1,
                  this.field_74887_e.field_78895_b + 3 + 1,
                  this.field_74887_e.field_78896_c - 1,
                  EnumFacing.NORTH,
                  ☃
               );
            }

            if (☃.nextBoolean()) {
               MineshaftPieces.func_189938_b(
                  ☃,
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a - 1,
                  this.field_74887_e.field_78895_b + 3 + 1,
                  this.field_74887_e.field_78896_c + 1,
                  EnumFacing.WEST,
                  ☃
               );
            }

            if (☃.nextBoolean()) {
               MineshaftPieces.func_189938_b(
                  ☃,
                  ☃,
                  ☃,
                  this.field_74887_e.field_78893_d + 1,
                  this.field_74887_e.field_78895_b + 3 + 1,
                  this.field_74887_e.field_78896_c + 1,
                  EnumFacing.EAST,
                  ☃
               );
            }

            if (☃.nextBoolean()) {
               MineshaftPieces.func_189938_b(
                  ☃,
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a + 1,
                  this.field_74887_e.field_78895_b + 3 + 1,
                  this.field_74887_e.field_78892_f + 1,
                  EnumFacing.SOUTH,
                  ☃
               );
            }
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.func_74860_a(☃, ☃)) {
            return false;
         } else {
            IBlockState ☃ = this.func_189917_F_();
            if (this.field_74952_b) {
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a + 1,
                  this.field_74887_e.field_78895_b,
                  this.field_74887_e.field_78896_c,
                  this.field_74887_e.field_78893_d - 1,
                  this.field_74887_e.field_78895_b + 3 - 1,
                  this.field_74887_e.field_78892_f,
                  field_202556_l,
                  field_202556_l,
                  false
               );
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a,
                  this.field_74887_e.field_78895_b,
                  this.field_74887_e.field_78896_c + 1,
                  this.field_74887_e.field_78893_d,
                  this.field_74887_e.field_78895_b + 3 - 1,
                  this.field_74887_e.field_78892_f - 1,
                  field_202556_l,
                  field_202556_l,
                  false
               );
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a + 1,
                  this.field_74887_e.field_78894_e - 2,
                  this.field_74887_e.field_78896_c,
                  this.field_74887_e.field_78893_d - 1,
                  this.field_74887_e.field_78894_e,
                  this.field_74887_e.field_78892_f,
                  field_202556_l,
                  field_202556_l,
                  false
               );
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a,
                  this.field_74887_e.field_78894_e - 2,
                  this.field_74887_e.field_78896_c + 1,
                  this.field_74887_e.field_78893_d,
                  this.field_74887_e.field_78894_e,
                  this.field_74887_e.field_78892_f - 1,
                  field_202556_l,
                  field_202556_l,
                  false
               );
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a + 1,
                  this.field_74887_e.field_78895_b + 3,
                  this.field_74887_e.field_78896_c + 1,
                  this.field_74887_e.field_78893_d - 1,
                  this.field_74887_e.field_78895_b + 3,
                  this.field_74887_e.field_78892_f - 1,
                  field_202556_l,
                  field_202556_l,
                  false
               );
            } else {
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a + 1,
                  this.field_74887_e.field_78895_b,
                  this.field_74887_e.field_78896_c,
                  this.field_74887_e.field_78893_d - 1,
                  this.field_74887_e.field_78894_e,
                  this.field_74887_e.field_78892_f,
                  field_202556_l,
                  field_202556_l,
                  false
               );
               this.func_175804_a(
                  ☃,
                  ☃,
                  this.field_74887_e.field_78897_a,
                  this.field_74887_e.field_78895_b,
                  this.field_74887_e.field_78896_c + 1,
                  this.field_74887_e.field_78893_d,
                  this.field_74887_e.field_78894_e,
                  this.field_74887_e.field_78892_f - 1,
                  field_202556_l,
                  field_202556_l,
                  false
               );
            }

            this.func_189923_b(
               ☃,
               ☃,
               this.field_74887_e.field_78897_a + 1,
               this.field_74887_e.field_78895_b,
               this.field_74887_e.field_78896_c + 1,
               this.field_74887_e.field_78894_e
            );
            this.func_189923_b(
               ☃,
               ☃,
               this.field_74887_e.field_78897_a + 1,
               this.field_74887_e.field_78895_b,
               this.field_74887_e.field_78892_f - 1,
               this.field_74887_e.field_78894_e
            );
            this.func_189923_b(
               ☃,
               ☃,
               this.field_74887_e.field_78893_d - 1,
               this.field_74887_e.field_78895_b,
               this.field_74887_e.field_78896_c + 1,
               this.field_74887_e.field_78894_e
            );
            this.func_189923_b(
               ☃,
               ☃,
               this.field_74887_e.field_78893_d - 1,
               this.field_74887_e.field_78895_b,
               this.field_74887_e.field_78892_f - 1,
               this.field_74887_e.field_78894_e
            );

            for(int ☃ = this.field_74887_e.field_78897_a; ☃ <= this.field_74887_e.field_78893_d; ++☃) {
               for(int ☃x = this.field_74887_e.field_78896_c; ☃x <= this.field_74887_e.field_78892_f; ++☃x) {
                  if (this.func_175807_a(☃, ☃, this.field_74887_e.field_78895_b - 1, ☃x, ☃).func_196958_f()
                     && this.func_189916_b(☃, ☃, this.field_74887_e.field_78895_b - 1, ☃x, ☃)) {
                     this.func_175811_a(☃, ☃, ☃, this.field_74887_e.field_78895_b - 1, ☃x, ☃);
                  }
               }
            }

            return true;
         }
      }

      private void func_189923_b(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6) {
         if (!this.func_175807_a(☃, ☃, ☃ + 1, ☃, ☃).func_196958_f()) {
            this.func_175804_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, this.func_189917_F_(), field_202556_l, false);
         }
      }
   }

   abstract static class Piece extends StructurePiece {
      protected MineshaftStructure.Type field_189920_a;

      public Piece() {
      }

      public Piece(int var1, MineshaftStructure.Type var2) {
         super(☃);
         this.field_189920_a = ☃;
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         ☃.func_74768_a("MST", this.field_189920_a.ordinal());
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         this.field_189920_a = MineshaftStructure.Type.func_189910_a(☃.func_74762_e("MST"));
      }

      protected IBlockState func_189917_F_() {
         switch(this.field_189920_a) {
            case NORMAL:
            default:
               return Blocks.field_196662_n.func_176223_P();
            case MESA:
               return Blocks.field_196672_s.func_176223_P();
         }
      }

      protected IBlockState func_189919_b() {
         switch(this.field_189920_a) {
            case NORMAL:
            default:
               return Blocks.field_180407_aO.func_176223_P();
            case MESA:
               return Blocks.field_180406_aS.func_176223_P();
         }
      }

      protected boolean func_189918_a(IBlockReader var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6) {
         for(int ☃ = ☃; ☃ <= ☃; ++☃) {
            if (this.func_175807_a(☃, ☃, ☃ + 1, ☃, ☃).func_196958_f()) {
               return false;
            }
         }

         return true;
      }
   }

   public static class Room extends MineshaftPieces.Piece {
      private final List<MutableBoundingBox> field_74949_a = Lists.<MutableBoundingBox>newLinkedList();

      public Room() {
      }

      public Room(int var1, Random var2, int var3, int var4, MineshaftStructure.Type var5) {
         super(☃, ☃);
         this.field_189920_a = ☃;
         this.field_74887_e = new MutableBoundingBox(☃, 50, ☃, ☃ + 7 + ☃.nextInt(6), 54 + ☃.nextInt(6), ☃ + 7 + ☃.nextInt(6));
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         int ☃ = this.func_74877_c();
         int ☃x = this.field_74887_e.func_78882_c() - 3 - 1;
         if (☃x <= 0) {
            ☃x = 1;
         }

         int ☃;
         for(☃ = 0; ☃ < this.field_74887_e.func_78883_b(); ☃ += 4) {
            ☃ += ☃.nextInt(this.field_74887_e.func_78883_b());
            if (☃ + 3 > this.field_74887_e.func_78883_b()) {
               break;
            }

            MineshaftPieces.Piece ☃ = MineshaftPieces.func_189938_b(
               ☃,
               ☃,
               ☃,
               this.field_74887_e.field_78897_a + ☃,
               this.field_74887_e.field_78895_b + ☃.nextInt(☃x) + 1,
               this.field_74887_e.field_78896_c - 1,
               EnumFacing.NORTH,
               ☃
            );
            if (☃ != null) {
               MutableBoundingBox ☃x = ☃.func_74874_b();
               this.field_74949_a
                  .add(
                     new MutableBoundingBox(
                        ☃x.field_78897_a,
                        ☃x.field_78895_b,
                        this.field_74887_e.field_78896_c,
                        ☃x.field_78893_d,
                        ☃x.field_78894_e,
                        this.field_74887_e.field_78896_c + 1
                     )
                  );
            }
         }

         for(☃ = 0; ☃ < this.field_74887_e.func_78883_b(); ☃ += 4) {
            ☃ += ☃.nextInt(this.field_74887_e.func_78883_b());
            if (☃ + 3 > this.field_74887_e.func_78883_b()) {
               break;
            }

            MineshaftPieces.Piece ☃ = MineshaftPieces.func_189938_b(
               ☃,
               ☃,
               ☃,
               this.field_74887_e.field_78897_a + ☃,
               this.field_74887_e.field_78895_b + ☃.nextInt(☃x) + 1,
               this.field_74887_e.field_78892_f + 1,
               EnumFacing.SOUTH,
               ☃
            );
            if (☃ != null) {
               MutableBoundingBox ☃x = ☃.func_74874_b();
               this.field_74949_a
                  .add(
                     new MutableBoundingBox(
                        ☃x.field_78897_a,
                        ☃x.field_78895_b,
                        this.field_74887_e.field_78892_f - 1,
                        ☃x.field_78893_d,
                        ☃x.field_78894_e,
                        this.field_74887_e.field_78892_f
                     )
                  );
            }
         }

         for(☃ = 0; ☃ < this.field_74887_e.func_78880_d(); ☃ += 4) {
            ☃ += ☃.nextInt(this.field_74887_e.func_78880_d());
            if (☃ + 3 > this.field_74887_e.func_78880_d()) {
               break;
            }

            MineshaftPieces.Piece ☃ = MineshaftPieces.func_189938_b(
               ☃,
               ☃,
               ☃,
               this.field_74887_e.field_78897_a - 1,
               this.field_74887_e.field_78895_b + ☃.nextInt(☃x) + 1,
               this.field_74887_e.field_78896_c + ☃,
               EnumFacing.WEST,
               ☃
            );
            if (☃ != null) {
               MutableBoundingBox ☃x = ☃.func_74874_b();
               this.field_74949_a
                  .add(
                     new MutableBoundingBox(
                        this.field_74887_e.field_78897_a,
                        ☃x.field_78895_b,
                        ☃x.field_78896_c,
                        this.field_74887_e.field_78897_a + 1,
                        ☃x.field_78894_e,
                        ☃x.field_78892_f
                     )
                  );
            }
         }

         for(☃ = 0; ☃ < this.field_74887_e.func_78880_d(); ☃ += 4) {
            ☃ += ☃.nextInt(this.field_74887_e.func_78880_d());
            if (☃ + 3 > this.field_74887_e.func_78880_d()) {
               break;
            }

            StructurePiece ☃ = MineshaftPieces.func_189938_b(
               ☃,
               ☃,
               ☃,
               this.field_74887_e.field_78893_d + 1,
               this.field_74887_e.field_78895_b + ☃.nextInt(☃x) + 1,
               this.field_74887_e.field_78896_c + ☃,
               EnumFacing.EAST,
               ☃
            );
            if (☃ != null) {
               MutableBoundingBox ☃x = ☃.func_74874_b();
               this.field_74949_a
                  .add(
                     new MutableBoundingBox(
                        this.field_74887_e.field_78893_d - 1,
                        ☃x.field_78895_b,
                        ☃x.field_78896_c,
                        this.field_74887_e.field_78893_d,
                        ☃x.field_78894_e,
                        ☃x.field_78892_f
                     )
                  );
            }
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.func_74860_a(☃, ☃)) {
            return false;
         } else {
            this.func_175804_a(
               ☃,
               ☃,
               this.field_74887_e.field_78897_a,
               this.field_74887_e.field_78895_b,
               this.field_74887_e.field_78896_c,
               this.field_74887_e.field_78893_d,
               this.field_74887_e.field_78895_b,
               this.field_74887_e.field_78892_f,
               Blocks.field_150346_d.func_176223_P(),
               field_202556_l,
               true
            );
            this.func_175804_a(
               ☃,
               ☃,
               this.field_74887_e.field_78897_a,
               this.field_74887_e.field_78895_b + 1,
               this.field_74887_e.field_78896_c,
               this.field_74887_e.field_78893_d,
               Math.min(this.field_74887_e.field_78895_b + 3, this.field_74887_e.field_78894_e),
               this.field_74887_e.field_78892_f,
               field_202556_l,
               field_202556_l,
               false
            );

            for(MutableBoundingBox ☃ : this.field_74949_a) {
               this.func_175804_a(
                  ☃,
                  ☃,
                  ☃.field_78897_a,
                  ☃.field_78894_e - 2,
                  ☃.field_78896_c,
                  ☃.field_78893_d,
                  ☃.field_78894_e,
                  ☃.field_78892_f,
                  field_202556_l,
                  field_202556_l,
                  false
               );
            }

            this.func_180777_a(
               ☃,
               ☃,
               this.field_74887_e.field_78897_a,
               this.field_74887_e.field_78895_b + 4,
               this.field_74887_e.field_78896_c,
               this.field_74887_e.field_78893_d,
               this.field_74887_e.field_78894_e,
               this.field_74887_e.field_78892_f,
               field_202556_l,
               false
            );
            return true;
         }
      }

      @Override
      public void func_181138_a(int var1, int var2, int var3) {
         super.func_181138_a(☃, ☃, ☃);

         for(MutableBoundingBox ☃ : this.field_74949_a) {
            ☃.func_78886_a(☃, ☃, ☃);
         }
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         NBTTagList ☃ = new NBTTagList();

         for(MutableBoundingBox ☃x : this.field_74949_a) {
            ☃.add((INBTBase)☃x.func_151535_h());
         }

         ☃.func_74782_a("Entrances", ☃);
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         NBTTagList ☃ = ☃.func_150295_c("Entrances", 11);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_74949_a.add(new MutableBoundingBox(☃.func_150306_c(☃x)));
         }
      }
   }

   public static class Stairs extends MineshaftPieces.Piece {
      public Stairs() {
      }

      public Stairs(int var1, Random var2, MutableBoundingBox var3, EnumFacing var4, MineshaftStructure.Type var5) {
         super(☃, ☃);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      public static MutableBoundingBox func_175812_a(List<StructurePiece> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         MutableBoundingBox ☃ = new MutableBoundingBox(☃, ☃ - 5, ☃, ☃, ☃ + 3 - 1, ☃);
         switch(☃) {
            case NORTH:
            default:
               ☃.field_78893_d = ☃ + 3 - 1;
               ☃.field_78896_c = ☃ - 8;
               break;
            case SOUTH:
               ☃.field_78893_d = ☃ + 3 - 1;
               ☃.field_78892_f = ☃ + 8;
               break;
            case WEST:
               ☃.field_78897_a = ☃ - 8;
               ☃.field_78892_f = ☃ + 3 - 1;
               break;
            case EAST:
               ☃.field_78893_d = ☃ + 8;
               ☃.field_78892_f = ☃ + 3 - 1;
         }

         return StructurePiece.func_74883_a(☃, ☃) != null ? null : ☃;
      }

      @Override
      public void func_74861_a(StructurePiece var1, List<StructurePiece> var2, Random var3) {
         int ☃ = this.func_74877_c();
         EnumFacing ☃x = this.func_186165_e();
         if (☃x != null) {
            switch(☃x) {
               case NORTH:
               default:
                  MineshaftPieces.func_189938_b(
                     ☃, ☃, ☃, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, EnumFacing.NORTH, ☃
                  );
                  break;
               case SOUTH:
                  MineshaftPieces.func_189938_b(
                     ☃, ☃, ☃, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, EnumFacing.SOUTH, ☃
                  );
                  break;
               case WEST:
                  MineshaftPieces.func_189938_b(
                     ☃, ☃, ☃, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, EnumFacing.WEST, ☃
                  );
                  break;
               case EAST:
                  MineshaftPieces.func_189938_b(
                     ☃, ☃, ☃, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, EnumFacing.EAST, ☃
                  );
            }
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.func_74860_a(☃, ☃)) {
            return false;
         } else {
            this.func_175804_a(☃, ☃, 0, 5, 0, 2, 7, 1, field_202556_l, field_202556_l, false);
            this.func_175804_a(☃, ☃, 0, 0, 7, 2, 2, 8, field_202556_l, field_202556_l, false);

            for(int ☃ = 0; ☃ < 5; ++☃) {
               this.func_175804_a(☃, ☃, 0, 5 - ☃ - (☃ < 4 ? 1 : 0), 2 + ☃, 2, 7 - ☃, 2 + ☃, field_202556_l, field_202556_l, false);
            }

            return true;
         }
      }
   }
}
