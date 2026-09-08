package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
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

public class WoodlandMansionPieces {
   public static void func_191153_a() {
      StructureIO.func_143031_a(WoodlandMansionPieces.MansionTemplate.class, "WMP");
   }

   public static void func_191152_a(TemplateManager var0, BlockPos var1, Rotation var2, List<WoodlandMansionPieces.MansionTemplate> var3, Random var4) {
      WoodlandMansionPieces.Grid ☃ = new WoodlandMansionPieces.Grid(☃);
      WoodlandMansionPieces.Placer ☃x = new WoodlandMansionPieces.Placer(☃, ☃);
      ☃x.func_191125_a(☃, ☃, ☃, ☃);
   }

   static class FirstFloor extends WoodlandMansionPieces.RoomCollection {
      private FirstFloor() {
      }

      @Override
      public String func_191104_a(Random var1) {
         return "1x1_a" + (☃.nextInt(5) + 1);
      }

      @Override
      public String func_191099_b(Random var1) {
         return "1x1_as" + (☃.nextInt(4) + 1);
      }

      @Override
      public String func_191100_a(Random var1, boolean var2) {
         return "1x2_a" + (☃.nextInt(9) + 1);
      }

      @Override
      public String func_191098_b(Random var1, boolean var2) {
         return "1x2_b" + (☃.nextInt(5) + 1);
      }

      @Override
      public String func_191102_c(Random var1) {
         return "1x2_s" + (☃.nextInt(2) + 1);
      }

      @Override
      public String func_191101_d(Random var1) {
         return "2x2_a" + (☃.nextInt(4) + 1);
      }

      @Override
      public String func_191103_e(Random var1) {
         return "2x2_s1";
      }
   }

   static class Grid {
      private final Random field_191117_a;
      private final WoodlandMansionPieces.SimpleGrid field_191118_b;
      private final WoodlandMansionPieces.SimpleGrid field_191119_c;
      private final WoodlandMansionPieces.SimpleGrid[] field_191120_d;
      private final int field_191121_e;
      private final int field_191122_f;

      public Grid(Random var1) {
         this.field_191117_a = ☃;
         int ☃ = 11;
         this.field_191121_e = 7;
         this.field_191122_f = 4;
         this.field_191118_b = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.field_191118_b.func_191142_a(this.field_191121_e, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 3);
         this.field_191118_b.func_191142_a(this.field_191121_e - 1, this.field_191122_f, this.field_191121_e - 1, this.field_191122_f + 1, 2);
         this.field_191118_b.func_191142_a(this.field_191121_e + 2, this.field_191122_f - 2, this.field_191121_e + 3, this.field_191122_f + 3, 5);
         this.field_191118_b.func_191142_a(this.field_191121_e + 1, this.field_191122_f - 2, this.field_191121_e + 1, this.field_191122_f - 1, 1);
         this.field_191118_b.func_191142_a(this.field_191121_e + 1, this.field_191122_f + 2, this.field_191121_e + 1, this.field_191122_f + 3, 1);
         this.field_191118_b.func_191144_a(this.field_191121_e - 1, this.field_191122_f - 1, 1);
         this.field_191118_b.func_191144_a(this.field_191121_e - 1, this.field_191122_f + 2, 1);
         this.field_191118_b.func_191142_a(0, 0, 11, 1, 5);
         this.field_191118_b.func_191142_a(0, 9, 11, 11, 5);
         this.func_191110_a(this.field_191118_b, this.field_191121_e, this.field_191122_f - 2, EnumFacing.WEST, 6);
         this.func_191110_a(this.field_191118_b, this.field_191121_e, this.field_191122_f + 3, EnumFacing.WEST, 6);
         this.func_191110_a(this.field_191118_b, this.field_191121_e - 2, this.field_191122_f - 1, EnumFacing.WEST, 3);
         this.func_191110_a(this.field_191118_b, this.field_191121_e - 2, this.field_191122_f + 2, EnumFacing.WEST, 3);

         while(this.func_191111_a(this.field_191118_b)) {
         }

         this.field_191120_d = new WoodlandMansionPieces.SimpleGrid[3];
         this.field_191120_d[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.field_191120_d[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.field_191120_d[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
         this.func_191116_a(this.field_191118_b, this.field_191120_d[0]);
         this.func_191116_a(this.field_191118_b, this.field_191120_d[1]);
         this.field_191120_d[0].func_191142_a(this.field_191121_e + 1, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 8388608);
         this.field_191120_d[1].func_191142_a(this.field_191121_e + 1, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 8388608);
         this.field_191119_c = new WoodlandMansionPieces.SimpleGrid(this.field_191118_b.field_191149_b, this.field_191118_b.field_191150_c, 5);
         this.func_191115_b();
         this.func_191116_a(this.field_191119_c, this.field_191120_d[2]);
      }

      public static boolean func_191109_a(WoodlandMansionPieces.SimpleGrid var0, int var1, int var2) {
         int ☃ = ☃.func_191145_a(☃, ☃);
         return ☃ == 1 || ☃ == 2 || ☃ == 3 || ☃ == 4;
      }

      public boolean func_191114_a(WoodlandMansionPieces.SimpleGrid var1, int var2, int var3, int var4, int var5) {
         return (this.field_191120_d[☃].func_191145_a(☃, ☃) & 65535) == ☃;
      }

      @Nullable
      public EnumFacing func_191113_b(WoodlandMansionPieces.SimpleGrid var1, int var2, int var3, int var4, int var5) {
         for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
            if (this.func_191114_a(☃, ☃ + ☃.func_82601_c(), ☃ + ☃.func_82599_e(), ☃, ☃)) {
               return ☃;
            }
         }

         return null;
      }

      private void func_191110_a(WoodlandMansionPieces.SimpleGrid var1, int var2, int var3, EnumFacing var4, int var5) {
         if (☃ > 0) {
            ☃.func_191144_a(☃, ☃, 1);
            ☃.func_197588_a(☃ + ☃.func_82601_c(), ☃ + ☃.func_82599_e(), 0, 1);

            for(int ☃ = 0; ☃ < 8; ++☃) {
               EnumFacing ☃x = EnumFacing.func_176731_b(this.field_191117_a.nextInt(4));
               if (☃x != ☃.func_176734_d() && (☃x != EnumFacing.EAST || !this.field_191117_a.nextBoolean())) {
                  int ☃xx = ☃ + ☃.func_82601_c();
                  int ☃xxx = ☃ + ☃.func_82599_e();
                  if (☃.func_191145_a(☃xx + ☃x.func_82601_c(), ☃xxx + ☃x.func_82599_e()) == 0
                     && ☃.func_191145_a(☃xx + ☃x.func_82601_c() * 2, ☃xxx + ☃x.func_82599_e() * 2) == 0) {
                     this.func_191110_a(☃, ☃ + ☃.func_82601_c() + ☃x.func_82601_c(), ☃ + ☃.func_82599_e() + ☃x.func_82599_e(), ☃x, ☃ - 1);
                     break;
                  }
               }
            }

            EnumFacing ☃ = ☃.func_176746_e();
            EnumFacing ☃x = ☃.func_176735_f();
            ☃.func_197588_a(☃ + ☃.func_82601_c(), ☃ + ☃.func_82599_e(), 0, 2);
            ☃.func_197588_a(☃ + ☃x.func_82601_c(), ☃ + ☃x.func_82599_e(), 0, 2);
            ☃.func_197588_a(☃ + ☃.func_82601_c() + ☃.func_82601_c(), ☃ + ☃.func_82599_e() + ☃.func_82599_e(), 0, 2);
            ☃.func_197588_a(☃ + ☃.func_82601_c() + ☃x.func_82601_c(), ☃ + ☃.func_82599_e() + ☃x.func_82599_e(), 0, 2);
            ☃.func_197588_a(☃ + ☃.func_82601_c() * 2, ☃ + ☃.func_82599_e() * 2, 0, 2);
            ☃.func_197588_a(☃ + ☃.func_82601_c() * 2, ☃ + ☃.func_82599_e() * 2, 0, 2);
            ☃.func_197588_a(☃ + ☃x.func_82601_c() * 2, ☃ + ☃x.func_82599_e() * 2, 0, 2);
         }
      }

      private boolean func_191111_a(WoodlandMansionPieces.SimpleGrid var1) {
         boolean ☃ = false;

         for(int ☃x = 0; ☃x < ☃.field_191150_c; ++☃x) {
            for(int ☃xx = 0; ☃xx < ☃.field_191149_b; ++☃xx) {
               if (☃.func_191145_a(☃xx, ☃x) == 0) {
                  int ☃xxx = 0;
                  ☃xxx += func_191109_a(☃, ☃xx + 1, ☃x) ? 1 : 0;
                  ☃xxx += func_191109_a(☃, ☃xx - 1, ☃x) ? 1 : 0;
                  ☃xxx += func_191109_a(☃, ☃xx, ☃x + 1) ? 1 : 0;
                  ☃xxx += func_191109_a(☃, ☃xx, ☃x - 1) ? 1 : 0;
                  if (☃xxx >= 3) {
                     ☃.func_191144_a(☃xx, ☃x, 2);
                     ☃ = true;
                  } else if (☃xxx == 2) {
                     int ☃xxx = 0;
                     ☃xxx += func_191109_a(☃, ☃xx + 1, ☃x + 1) ? 1 : 0;
                     ☃xxx += func_191109_a(☃, ☃xx - 1, ☃x + 1) ? 1 : 0;
                     ☃xxx += func_191109_a(☃, ☃xx + 1, ☃x - 1) ? 1 : 0;
                     ☃xxx += func_191109_a(☃, ☃xx - 1, ☃x - 1) ? 1 : 0;
                     if (☃xxx <= 1) {
                        ☃.func_191144_a(☃xx, ☃x, 2);
                        ☃ = true;
                     }
                  }
               }
            }
         }

         return ☃;
      }

      private void func_191115_b() {
         List<Tuple<Integer, Integer>> ☃ = Lists.<Tuple<Integer, Integer>>newArrayList();
         WoodlandMansionPieces.SimpleGrid ☃x = this.field_191120_d[1];

         for(int ☃xx = 0; ☃xx < this.field_191119_c.field_191150_c; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < this.field_191119_c.field_191149_b; ++☃xxx) {
               int ☃xxxx = ☃x.func_191145_a(☃xxx, ☃xx);
               int ☃xxxxx = ☃xxxx & 983040;
               if (☃xxxxx == 131072 && (☃xxxx & 2097152) == 2097152) {
                  ☃.add(new Tuple(☃xxx, ☃xx));
               }
            }
         }

         if (☃.isEmpty()) {
            this.field_191119_c.func_191142_a(0, 0, this.field_191119_c.field_191149_b, this.field_191119_c.field_191150_c, 5);
         } else {
            Tuple<Integer, Integer> ☃xx = (Tuple)☃.get(this.field_191117_a.nextInt(☃.size()));
            int ☃xxx = ☃x.func_191145_a(☃xx.func_76341_a(), ☃xx.func_76340_b());
            ☃x.func_191144_a(☃xx.func_76341_a(), ☃xx.func_76340_b(), ☃xxx | 4194304);
            EnumFacing ☃xxxx = this.func_191113_b(this.field_191118_b, ☃xx.func_76341_a(), ☃xx.func_76340_b(), 1, ☃xxx & 65535);
            int ☃xxxxx = ☃xx.func_76341_a() + ☃xxxx.func_82601_c();
            int ☃xxxxxx = ☃xx.func_76340_b() + ☃xxxx.func_82599_e();

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < this.field_191119_c.field_191150_c; ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < this.field_191119_c.field_191149_b; ++☃xxxxxxxx) {
                  if (!func_191109_a(this.field_191118_b, ☃xxxxxxxx, ☃xxxxxxx)) {
                     this.field_191119_c.func_191144_a(☃xxxxxxxx, ☃xxxxxxx, 5);
                  } else if (☃xxxxxxxx == ☃xx.func_76341_a() && ☃xxxxxxx == ☃xx.func_76340_b()) {
                     this.field_191119_c.func_191144_a(☃xxxxxxxx, ☃xxxxxxx, 3);
                  } else if (☃xxxxxxxx == ☃xxxxx && ☃xxxxxxx == ☃xxxxxx) {
                     this.field_191119_c.func_191144_a(☃xxxxxxxx, ☃xxxxxxx, 3);
                     this.field_191120_d[2].func_191144_a(☃xxxxxxxx, ☃xxxxxxx, 8388608);
                  }
               }
            }

            List<EnumFacing> ☃xxxxxxx = Lists.<EnumFacing>newArrayList();

            for(EnumFacing ☃xxxxxxxx : EnumFacing.Plane.HORIZONTAL) {
               if (this.field_191119_c.func_191145_a(☃xxxxx + ☃xxxxxxxx.func_82601_c(), ☃xxxxxx + ☃xxxxxxxx.func_82599_e()) == 0) {
                  ☃xxxxxxx.add(☃xxxxxxxx);
               }
            }

            if (☃xxxxxxx.isEmpty()) {
               this.field_191119_c.func_191142_a(0, 0, this.field_191119_c.field_191149_b, this.field_191119_c.field_191150_c, 5);
               ☃x.func_191144_a(☃xx.func_76341_a(), ☃xx.func_76340_b(), ☃xxx);
            } else {
               EnumFacing ☃xxxxxxxx = (EnumFacing)☃xxxxxxx.get(this.field_191117_a.nextInt(☃xxxxxxx.size()));
               this.func_191110_a(this.field_191119_c, ☃xxxxx + ☃xxxxxxxx.func_82601_c(), ☃xxxxxx + ☃xxxxxxxx.func_82599_e(), ☃xxxxxxxx, 4);

               while(this.func_191111_a(this.field_191119_c)) {
               }
            }
         }
      }

      private void func_191116_a(WoodlandMansionPieces.SimpleGrid var1, WoodlandMansionPieces.SimpleGrid var2) {
         List<Tuple<Integer, Integer>> ☃ = Lists.<Tuple<Integer, Integer>>newArrayList();

         for(int ☃x = 0; ☃x < ☃.field_191150_c; ++☃x) {
            for(int ☃xx = 0; ☃xx < ☃.field_191149_b; ++☃xx) {
               if (☃.func_191145_a(☃xx, ☃x) == 2) {
                  ☃.add(new Tuple(☃xx, ☃x));
               }
            }
         }

         Collections.shuffle(☃, this.field_191117_a);
         int ☃x = 10;

         for(Tuple<Integer, Integer> ☃xx : ☃) {
            int ☃xxx = ☃xx.func_76341_a();
            int ☃xxxx = ☃xx.func_76340_b();
            if (☃.func_191145_a(☃xxx, ☃xxxx) == 0) {
               int ☃xxxxx = ☃xxx;
               int ☃xxxxxx = ☃xxx;
               int ☃xxxxxxx = ☃xxxx;
               int ☃xxxxxxxx = ☃xxxx;
               int ☃xxxxxxxxx = 65536;
               if (☃.func_191145_a(☃xxx + 1, ☃xxxx) == 0
                  && ☃.func_191145_a(☃xxx, ☃xxxx + 1) == 0
                  && ☃.func_191145_a(☃xxx + 1, ☃xxxx + 1) == 0
                  && ☃.func_191145_a(☃xxx + 1, ☃xxxx) == 2
                  && ☃.func_191145_a(☃xxx, ☃xxxx + 1) == 2
                  && ☃.func_191145_a(☃xxx + 1, ☃xxxx + 1) == 2) {
                  ☃xxxxxx = ☃xxx + 1;
                  ☃xxxxxxxx = ☃xxxx + 1;
                  ☃xxxxxxxxx = 262144;
               } else if (☃.func_191145_a(☃xxx - 1, ☃xxxx) == 0
                  && ☃.func_191145_a(☃xxx, ☃xxxx + 1) == 0
                  && ☃.func_191145_a(☃xxx - 1, ☃xxxx + 1) == 0
                  && ☃.func_191145_a(☃xxx - 1, ☃xxxx) == 2
                  && ☃.func_191145_a(☃xxx, ☃xxxx + 1) == 2
                  && ☃.func_191145_a(☃xxx - 1, ☃xxxx + 1) == 2) {
                  ☃xxxxx = ☃xxx - 1;
                  ☃xxxxxxxx = ☃xxxx + 1;
                  ☃xxxxxxxxx = 262144;
               } else if (☃.func_191145_a(☃xxx - 1, ☃xxxx) == 0
                  && ☃.func_191145_a(☃xxx, ☃xxxx - 1) == 0
                  && ☃.func_191145_a(☃xxx - 1, ☃xxxx - 1) == 0
                  && ☃.func_191145_a(☃xxx - 1, ☃xxxx) == 2
                  && ☃.func_191145_a(☃xxx, ☃xxxx - 1) == 2
                  && ☃.func_191145_a(☃xxx - 1, ☃xxxx - 1) == 2) {
                  ☃xxxxx = ☃xxx - 1;
                  ☃xxxxxxx = ☃xxxx - 1;
                  ☃xxxxxxxxx = 262144;
               } else if (☃.func_191145_a(☃xxx + 1, ☃xxxx) == 0 && ☃.func_191145_a(☃xxx + 1, ☃xxxx) == 2) {
                  ☃xxxxxx = ☃xxx + 1;
                  ☃xxxxxxxxx = 131072;
               } else if (☃.func_191145_a(☃xxx, ☃xxxx + 1) == 0 && ☃.func_191145_a(☃xxx, ☃xxxx + 1) == 2) {
                  ☃xxxxxxxx = ☃xxxx + 1;
                  ☃xxxxxxxxx = 131072;
               } else if (☃.func_191145_a(☃xxx - 1, ☃xxxx) == 0 && ☃.func_191145_a(☃xxx - 1, ☃xxxx) == 2) {
                  ☃xxxxx = ☃xxx - 1;
                  ☃xxxxxxxxx = 131072;
               } else if (☃.func_191145_a(☃xxx, ☃xxxx - 1) == 0 && ☃.func_191145_a(☃xxx, ☃xxxx - 1) == 2) {
                  ☃xxxxxxx = ☃xxxx - 1;
                  ☃xxxxxxxxx = 131072;
               }

               int ☃xxxxx = this.field_191117_a.nextBoolean() ? ☃xxxxx : ☃xxxxxx;
               int ☃xxxxxx = this.field_191117_a.nextBoolean() ? ☃xxxxxxx : ☃xxxxxxxx;
               int ☃xxxxxxx = 2097152;
               if (!☃.func_191147_b(☃xxxxx, ☃xxxxxx, 1)) {
                  ☃xxxxx = ☃xxxxx == ☃xxxxx ? ☃xxxxxx : ☃xxxxx;
                  ☃xxxxxx = ☃xxxxxx == ☃xxxxxxx ? ☃xxxxxxxx : ☃xxxxxxx;
                  if (!☃.func_191147_b(☃xxxxx, ☃xxxxxx, 1)) {
                     ☃xxxxxx = ☃xxxxxx == ☃xxxxxxx ? ☃xxxxxxxx : ☃xxxxxxx;
                     if (!☃.func_191147_b(☃xxxxx, ☃xxxxxx, 1)) {
                        ☃xxxxx = ☃xxxxx == ☃xxxxx ? ☃xxxxxx : ☃xxxxx;
                        ☃xxxxxx = ☃xxxxxx == ☃xxxxxxx ? ☃xxxxxxxx : ☃xxxxxxx;
                        if (!☃.func_191147_b(☃xxxxx, ☃xxxxxx, 1)) {
                           ☃xxxxxxx = 0;
                           ☃xxxxx = ☃xxxxx;
                           ☃xxxxxx = ☃xxxxxxx;
                        }
                     }
                  }
               }

               for(int ☃xxxxx = ☃xxxxxxx; ☃xxxxx <= ☃xxxxxxxx; ++☃xxxxx) {
                  for(int ☃xxxxxx = ☃xxxxx; ☃xxxxxx <= ☃xxxxxx; ++☃xxxxxx) {
                     if (☃xxxxxx == ☃xxxxx && ☃xxxxx == ☃xxxxxx) {
                        ☃.func_191144_a(☃xxxxxx, ☃xxxxx, 1048576 | ☃xxxxxxx | ☃xxxxxxxxx | ☃x);
                     } else {
                        ☃.func_191144_a(☃xxxxxx, ☃xxxxx, ☃xxxxxxxxx | ☃x);
                     }
                  }
               }

               ++☃x;
            }
         }
      }
   }

   public static class MansionTemplate extends TemplateStructurePiece {
      private String field_191082_d;
      private Rotation field_191083_e;
      private Mirror field_191084_f;

      public MansionTemplate() {
      }

      public MansionTemplate(TemplateManager var1, String var2, BlockPos var3, Rotation var4) {
         this(☃, ☃, ☃, ☃, Mirror.NONE);
      }

      public MansionTemplate(TemplateManager var1, String var2, BlockPos var3, Rotation var4, Mirror var5) {
         super(0);
         this.field_191082_d = ☃;
         this.field_186178_c = ☃;
         this.field_191083_e = ☃;
         this.field_191084_f = ☃;
         this.func_191081_a(☃);
      }

      private void func_191081_a(TemplateManager var1) {
         Template ☃ = ☃.func_200220_a(new ResourceLocation("woodland_mansion/" + this.field_191082_d));
         PlacementSettings ☃x = new PlacementSettings().func_186222_a(true).func_186220_a(this.field_191083_e).func_186214_a(this.field_191084_f);
         this.func_186173_a(☃, this.field_186178_c, ☃x);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74778_a("Template", this.field_191082_d);
         ☃.func_74778_a("Rot", this.field_186177_b.func_186215_c().name());
         ☃.func_74778_a("Mi", this.field_186177_b.func_186212_b().name());
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_191082_d = ☃.func_74779_i("Template");
         this.field_191083_e = Rotation.valueOf(☃.func_74779_i("Rot"));
         this.field_191084_f = Mirror.valueOf(☃.func_74779_i("Mi"));
         this.func_191081_a(☃);
      }

      @Override
      protected void func_186175_a(String var1, BlockPos var2, IWorld var3, Random var4, MutableBoundingBox var5) {
         if (☃.startsWith("Chest")) {
            Rotation ☃ = this.field_186177_b.func_186215_c();
            IBlockState ☃x = Blocks.field_150486_ae.func_176223_P();
            if ("ChestWest".equals(☃)) {
               ☃x = ☃x.func_206870_a(BlockChest.field_176459_a, ☃.func_185831_a(EnumFacing.WEST));
            } else if ("ChestEast".equals(☃)) {
               ☃x = ☃x.func_206870_a(BlockChest.field_176459_a, ☃.func_185831_a(EnumFacing.EAST));
            } else if ("ChestSouth".equals(☃)) {
               ☃x = ☃x.func_206870_a(BlockChest.field_176459_a, ☃.func_185831_a(EnumFacing.SOUTH));
            } else if ("ChestNorth".equals(☃)) {
               ☃x = ☃x.func_206870_a(BlockChest.field_176459_a, ☃.func_185831_a(EnumFacing.NORTH));
            }

            this.func_191080_a(☃, ☃, ☃, ☃, LootTableList.field_191192_o, ☃x);
         } else if ("Mage".equals(☃)) {
            EntityEvoker ☃ = new EntityEvoker(☃.func_201672_e());
            ☃.func_110163_bv();
            ☃.func_174828_a(☃, 0.0F, 0.0F);
            ☃.func_72838_d(☃);
            ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 2);
         } else if ("Warrior".equals(☃)) {
            EntityVindicator ☃ = new EntityVindicator(☃.func_201672_e());
            ☃.func_110163_bv();
            ☃.func_174828_a(☃, 0.0F, 0.0F);
            ☃.func_204210_a(☃.func_175649_E(new BlockPos(☃)), null, null);
            ☃.func_72838_d(☃);
            ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 2);
         }
      }
   }

   static class PlacementData {
      public Rotation field_191138_a;
      public BlockPos field_191139_b;
      public String field_191140_c;

      private PlacementData() {
      }
   }

   static class Placer {
      private final TemplateManager field_191134_a;
      private final Random field_191135_b;
      private int field_191136_c;
      private int field_191137_d;

      public Placer(TemplateManager var1, Random var2) {
         this.field_191134_a = ☃;
         this.field_191135_b = ☃;
      }

      public void func_191125_a(BlockPos var1, Rotation var2, List<WoodlandMansionPieces.MansionTemplate> var3, WoodlandMansionPieces.Grid var4) {
         WoodlandMansionPieces.PlacementData ☃ = new WoodlandMansionPieces.PlacementData();
         ☃.field_191139_b = ☃;
         ☃.field_191138_a = ☃;
         ☃.field_191140_c = "wall_flat";
         WoodlandMansionPieces.PlacementData ☃x = new WoodlandMansionPieces.PlacementData();
         this.func_191133_a(☃, ☃);
         ☃x.field_191139_b = ☃.field_191139_b.func_177981_b(8);
         ☃x.field_191138_a = ☃.field_191138_a;
         ☃x.field_191140_c = "wall_window";
         if (!☃.isEmpty()) {
         }

         WoodlandMansionPieces.SimpleGrid ☃ = ☃.field_191118_b;
         WoodlandMansionPieces.SimpleGrid ☃x = ☃.field_191119_c;
         this.field_191136_c = ☃.field_191121_e + 1;
         this.field_191137_d = ☃.field_191122_f + 1;
         int ☃xx = ☃.field_191121_e + 1;
         int ☃xxx = ☃.field_191122_f;
         this.func_191130_a(☃, ☃, ☃, EnumFacing.SOUTH, this.field_191136_c, this.field_191137_d, ☃xx, ☃xxx);
         this.func_191130_a(☃, ☃x, ☃, EnumFacing.SOUTH, this.field_191136_c, this.field_191137_d, ☃xx, ☃xxx);
         WoodlandMansionPieces.PlacementData ☃xxxx = new WoodlandMansionPieces.PlacementData();
         ☃xxxx.field_191139_b = ☃.field_191139_b.func_177981_b(19);
         ☃xxxx.field_191138_a = ☃.field_191138_a;
         ☃xxxx.field_191140_c = "wall_window";
         boolean ☃xxxxx = false;

         for(int ☃xxxxxx = 0; ☃xxxxxx < ☃x.field_191150_c && !☃xxxxx; ++☃xxxxxx) {
            for(int ☃xxxxxxx = ☃x.field_191149_b - 1; ☃xxxxxxx >= 0 && !☃xxxxx; --☃xxxxxxx) {
               if (WoodlandMansionPieces.Grid.func_191109_a(☃x, ☃xxxxxxx, ☃xxxxxx)) {
                  ☃xxxx.field_191139_b = ☃xxxx.field_191139_b.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8 + (☃xxxxxx - this.field_191137_d) * 8);
                  ☃xxxx.field_191139_b = ☃xxxx.field_191139_b.func_177967_a(☃.func_185831_a(EnumFacing.EAST), (☃xxxxxxx - this.field_191136_c) * 8);
                  this.func_191131_b(☃, ☃xxxx);
                  this.func_191130_a(☃, ☃xxxx, ☃x, EnumFacing.SOUTH, ☃xxxxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxx);
                  ☃xxxxx = true;
               }
            }
         }

         this.func_191123_a(☃, ☃.func_177981_b(16), ☃, ☃, ☃x);
         this.func_191123_a(☃, ☃.func_177981_b(27), ☃, ☃x, null);
         if (!☃.isEmpty()) {
         }

         WoodlandMansionPieces.RoomCollection[] ☃xxxxxx = new WoodlandMansionPieces.RoomCollection[]{
            new WoodlandMansionPieces.FirstFloor(), new WoodlandMansionPieces.SecondFloor(), new WoodlandMansionPieces.ThirdFloor()
         };

         for(int ☃xxxxxxx = 0; ☃xxxxxxx < 3; ++☃xxxxxxx) {
            BlockPos ☃xxxxxxxx = ☃.func_177981_b(8 * ☃xxxxxxx + (☃xxxxxxx == 2 ? 3 : 0));
            WoodlandMansionPieces.SimpleGrid ☃xxxxxxxxx = ☃.field_191120_d[☃xxxxxxx];
            WoodlandMansionPieces.SimpleGrid ☃xxxxxxxxxx = ☃xxxxxxx == 2 ? ☃x : ☃;
            String ☃xxxxxxxxxxx = ☃xxxxxxx == 0 ? "carpet_south_1" : "carpet_south_2";
            String ☃xxxxxxxxxxxx = ☃xxxxxxx == 0 ? "carpet_west_1" : "carpet_west_2";

            for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < ☃xxxxxxxxxx.field_191150_c; ++☃xxxxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxx < ☃xxxxxxxxxx.field_191149_b; ++☃xxxxxxxxxxxxxx) {
                  if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx) == 1) {
                     BlockPos ☃xxxxxxxxxxxxxxx = ☃xxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8 + (☃xxxxxxxxxxxxx - this.field_191137_d) * 8);
                     ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.EAST), (☃xxxxxxxxxxxxxx - this.field_191136_c) * 8);
                     ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "corridor_floor", ☃xxxxxxxxxxxxxxx, ☃));
                     if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx - 1) == 1
                        || (☃xxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx - 1) & 8388608) == 8388608) {
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a, "carpet_north", ☃xxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1).func_177984_a(), ☃
                           )
                        );
                     }

                     if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx + 1, ☃xxxxxxxxxxxxx) == 1
                        || (☃xxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx + 1, ☃xxxxxxxxxxxxx) & 8388608) == 8388608) {
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a,
                              "carpet_east",
                              ☃xxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 1)
                                 .func_177967_a(☃.func_185831_a(EnumFacing.EAST), 5)
                                 .func_177984_a(),
                              ☃
                           )
                        );
                     }

                     if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx + 1) == 1
                        || (☃xxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx + 1) & 8388608) == 8388608) {
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a,
                              ☃xxxxxxxxxxx,
                              ☃xxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 5).func_177967_a(☃.func_185831_a(EnumFacing.WEST), 1),
                              ☃
                           )
                        );
                     }

                     if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxx) == 1
                        || (☃xxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxx) & 8388608) == 8388608) {
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a,
                              ☃xxxxxxxxxxxx,
                              ☃xxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 1).func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 1),
                              ☃
                           )
                        );
                     }
                  }
               }
            }

            String ☃xxxxxxxxxxxxx = ☃xxxxxxx == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String ☃xxxxxxxxxxxxxx = ☃xxxxxxx == 0 ? "indoors_door_1" : "indoors_door_2";
            List<EnumFacing> ☃xxxxxxxxxxxxxxx = Lists.<EnumFacing>newArrayList();

            for(int ☃xxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxxx.field_191150_c; ++☃xxxxxxxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxx < ☃xxxxxxxxxx.field_191149_b; ++☃xxxxxxxxxxxxxxxxx) {
                  boolean ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxx == 2 && ☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx) == 3;
                  if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx) == 2 || ☃xxxxxxxxxxxxxxxxxx) {
                     int ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx);
                     int ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx & 983040;
                     int ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx & 65535;
                     ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxx && (☃xxxxxxxxxxxxxxxxxxx & 8388608) == 8388608;
                     ☃xxxxxxxxxxxxxxx.clear();
                     if ((☃xxxxxxxxxxxxxxxxxxx & 2097152) == 2097152) {
                        for(EnumFacing ☃xxxxxxxxxxxxxxxxxxxxxx : EnumFacing.Plane.HORIZONTAL) {
                           if (☃xxxxxxxxxx.func_191145_a(
                                 ☃xxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxx.func_82601_c(), ☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxx.func_82599_e()
                              )
                              == 1) {
                              ☃xxxxxxxxxxxxxxx.add(☃xxxxxxxxxxxxxxxxxxxxxx);
                           }
                        }
                     }

                     EnumFacing ☃xxxxxxxxxxxxxxxxxxx = null;
                     if (!☃xxxxxxxxxxxxxxx.isEmpty()) {
                        ☃xxxxxxxxxxxxxxxxxxx = (EnumFacing)☃xxxxxxxxxxxxxxx.get(this.field_191135_b.nextInt(☃xxxxxxxxxxxxxxx.size()));
                     } else if ((☃xxxxxxxxxxxxxxxxxxx & 1048576) == 1048576) {
                        ☃xxxxxxxxxxxxxxxxxxx = EnumFacing.UP;
                     }

                     BlockPos ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxx.func_177967_a(
                        ☃.func_185831_a(EnumFacing.SOUTH), 8 + (☃xxxxxxxxxxxxxxxx - this.field_191137_d) * 8
                     );
                     ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_177967_a(
                        ☃.func_185831_a(EnumFacing.EAST), -1 + (☃xxxxxxxxxxxxxxxxx - this.field_191136_c) * 8
                     );
                     if (WoodlandMansionPieces.Grid.func_191109_a(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxxxxx)
                        && !☃.func_191114_a(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxx)) {
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a, ☃xxxxxxxxxxxxxxxxxxx == EnumFacing.WEST ? ☃xxxxxxxxxxxxxx : ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, ☃
                           )
                        );
                     }

                     if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxxxxx + 1, ☃xxxxxxxxxxxxxxxx) == 1 && !☃xxxxxxxxxxxxxxxxxx) {
                        BlockPos ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 8);
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a, ☃xxxxxxxxxxxxxxxxxxx == EnumFacing.EAST ? ☃xxxxxxxxxxxxxx : ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, ☃
                           )
                        );
                     }

                     if (WoodlandMansionPieces.Grid.func_191109_a(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx + 1)
                        && !☃.func_191114_a(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx + 1, ☃xxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxx)) {
                        BlockPos ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 7);
                        ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a,
                              ☃xxxxxxxxxxxxxxxxxxx == EnumFacing.SOUTH ? ☃xxxxxxxxxxxxxx : ☃xxxxxxxxxxxxx,
                              ☃xxxxxxxxxxxxxxxxxxx,
                              ☃.func_185830_a(Rotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (☃xxxxxxxxxx.func_191145_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx - 1) == 1 && !☃xxxxxxxxxxxxxxxxxx) {
                        BlockPos ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 1);
                        ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a,
                              ☃xxxxxxxxxxxxxxxxxxx == EnumFacing.NORTH ? ☃xxxxxxxxxxxxxx : ☃xxxxxxxxxxxxx,
                              ☃xxxxxxxxxxxxxxxxxxx,
                              ☃.func_185830_a(Rotation.CLOCKWISE_90)
                           )
                        );
                     }

                     if (☃xxxxxxxxxxxxxxxxxxxx == 65536) {
                        this.func_191129_a(☃, ☃xxxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxx[☃xxxxxxx]);
                     } else if (☃xxxxxxxxxxxxxxxxxxxx == 131072 && ☃xxxxxxxxxxxxxxxxxxx != null) {
                        EnumFacing ☃xxxxxxxxxxxxxxxxxxx = ☃.func_191113_b(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxx);
                        boolean ☃xxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxxxxxxx & 4194304) == 4194304;
                        this.func_191132_a(☃, ☃xxxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxx[☃xxxxxxx], ☃xxxxxxxxxxxxxxxxxxxx);
                     } else if (☃xxxxxxxxxxxxxxxxxxxx == 262144 && ☃xxxxxxxxxxxxxxxxxxx != null && ☃xxxxxxxxxxxxxxxxxxx != EnumFacing.UP) {
                        EnumFacing ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_176746_e();
                        if (!☃.func_191114_a(
                           ☃xxxxxxxxxx,
                           ☃xxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxx.func_82601_c(),
                           ☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxx.func_82599_e(),
                           ☃xxxxxxx,
                           ☃xxxxxxxxxxxxxxxxxxxxx
                        )) {
                           ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.func_176734_d();
                        }

                        this.func_191127_a(☃, ☃xxxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxx[☃xxxxxxx]);
                     } else if (☃xxxxxxxxxxxxxxxxxxxx == 262144 && ☃xxxxxxxxxxxxxxxxxxx == EnumFacing.UP) {
                        this.func_191128_a(☃, ☃xxxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxx[☃xxxxxxx]);
                     }
                  }
               }
            }
         }
      }

      private void func_191130_a(
         List<WoodlandMansionPieces.MansionTemplate> var1,
         WoodlandMansionPieces.PlacementData var2,
         WoodlandMansionPieces.SimpleGrid var3,
         EnumFacing var4,
         int var5,
         int var6,
         int var7,
         int var8
      ) {
         int ☃ = ☃;
         int ☃x = ☃;
         EnumFacing ☃xx = ☃;

         do {
            if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃ + ☃.func_82601_c(), ☃x + ☃.func_82599_e())) {
               this.func_191124_c(☃, ☃);
               ☃ = ☃.func_176746_e();
               if (☃ != ☃ || ☃x != ☃ || ☃xx != ☃) {
                  this.func_191131_b(☃, ☃);
               }
            } else if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃ + ☃.func_82601_c(), ☃x + ☃.func_82599_e())
               && WoodlandMansionPieces.Grid.func_191109_a(
                  ☃, ☃ + ☃.func_82601_c() + ☃.func_176735_f().func_82601_c(), ☃x + ☃.func_82599_e() + ☃.func_176735_f().func_82599_e()
               )) {
               this.func_191126_d(☃, ☃);
               ☃ += ☃.func_82601_c();
               ☃x += ☃.func_82599_e();
               ☃ = ☃.func_176735_f();
            } else {
               ☃ += ☃.func_82601_c();
               ☃x += ☃.func_82599_e();
               if (☃ != ☃ || ☃x != ☃ || ☃xx != ☃) {
                  this.func_191131_b(☃, ☃);
               }
            }
         } while(☃ != ☃ || ☃x != ☃ || ☃xx != ☃);
      }

      private void func_191123_a(
         List<WoodlandMansionPieces.MansionTemplate> var1,
         BlockPos var2,
         Rotation var3,
         WoodlandMansionPieces.SimpleGrid var4,
         @Nullable WoodlandMansionPieces.SimpleGrid var5
      ) {
         for(int ☃ = 0; ☃ < ☃.field_191150_c; ++☃) {
            for(int ☃x = 0; ☃x < ☃.field_191149_b; ++☃x) {
               BlockPos var8 = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8 + (☃ - this.field_191137_d) * 8);
               var8 = var8.func_177967_a(☃.func_185831_a(EnumFacing.EAST), (☃x - this.field_191136_c) * 8);
               boolean ☃xx = ☃ != null && WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃);
               if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃) && !☃xx) {
                  ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof", var8.func_177981_b(3), ☃));
                  if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x + 1, ☃)) {
                     BlockPos ☃xxx = var8.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 6);
                     ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", ☃xxx, ☃));
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x - 1, ☃)) {
                     BlockPos ☃xxx = var8.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 0);
                     ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 7);
                     ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_180)));
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ - 1)) {
                     BlockPos ☃xxx = var8.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 1);
                     ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", ☃xxx, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ + 1)) {
                     BlockPos ☃xxx = var8.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 6);
                     ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
                     ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_90)));
                  }
               }
            }
         }

         if (☃ != null) {
            for(int ☃ = 0; ☃ < ☃.field_191150_c; ++☃) {
               for(int ☃x = 0; ☃x < ☃.field_191149_b; ++☃x) {
                  BlockPos var17 = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8 + (☃ - this.field_191137_d) * 8);
                  var17 = var17.func_177967_a(☃.func_185831_a(EnumFacing.EAST), (☃x - this.field_191136_c) * 8);
                  boolean ☃xx = WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃);
                  if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃) && ☃xx) {
                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x + 1, ☃)) {
                        BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", ☃xxx, ☃));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x - 1, ☃)) {
                        BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 1);
                        ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_180)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ - 1)) {
                        BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 0);
                        ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 1);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", ☃xxx, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ + 1)) {
                        BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 6);
                        ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 7);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_90)));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x + 1, ☃)) {
                        if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ - 1)) {
                           BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
                           ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 2);
                           ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", ☃xxx, ☃));
                        }

                        if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ + 1)) {
                           BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 8);
                           ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 7);
                           ☃.add(
                              new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_90))
                           );
                        }
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x - 1, ☃)) {
                        if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ - 1)) {
                           BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 2);
                           ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 1);
                           ☃.add(
                              new WoodlandMansionPieces.MansionTemplate(
                                 this.field_191134_a, "small_wall_corner", ☃xxx, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90)
                              )
                           );
                        }

                        if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ + 1)) {
                           BlockPos ☃xxx = var17.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 1);
                           ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8);
                           ☃.add(
                              new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_180))
                           );
                        }
                     }
                  }
               }
            }
         }

         for(int ☃ = 0; ☃ < ☃.field_191150_c; ++☃) {
            for(int ☃x = 0; ☃x < ☃.field_191149_b; ++☃x) {
               BlockPos var19 = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8 + (☃ - this.field_191137_d) * 8);
               var19 = var19.func_177967_a(☃.func_185831_a(EnumFacing.EAST), (☃x - this.field_191136_c) * 8);
               boolean ☃xx = ☃ != null && WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃);
               if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃) && !☃xx) {
                  if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x + 1, ☃)) {
                     BlockPos ☃xxx = var19.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 6);
                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ + 1)) {
                        BlockPos ☃xxxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", ☃xxxx, ☃));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x + 1, ☃ + 1)) {
                        BlockPos ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 5);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", ☃xxx, ☃));
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ - 1)) {
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", ☃xxx, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90))
                        );
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x + 1, ☃ - 1)) {
                        BlockPos ☃xxx = var19.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 9);
                        ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 2);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_90)));
                     }
                  }

                  if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x - 1, ☃)) {
                     BlockPos ☃xxx = var19.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 0);
                     ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 0);
                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ + 1)) {
                        BlockPos ☃xxxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", ☃xxxx, ☃.func_185830_a(Rotation.CLOCKWISE_90)));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x - 1, ☃ + 1)) {
                        BlockPos ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 8);
                        ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 3);
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(
                              this.field_191134_a, "roof_inner_corner", ☃xxx, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90)
                           )
                        );
                     }

                     if (!WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x, ☃ - 1)) {
                        ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_180)));
                     } else if (WoodlandMansionPieces.Grid.func_191109_a(☃, ☃x - 1, ☃ - 1)) {
                        BlockPos ☃xxx = ☃xxx.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 1);
                        ☃.add(
                           new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", ☃xxx, ☃.func_185830_a(Rotation.CLOCKWISE_180))
                        );
                     }
                  }
               }
            }
         }
      }

      private void func_191133_a(List<WoodlandMansionPieces.MansionTemplate> var1, WoodlandMansionPieces.PlacementData var2) {
         EnumFacing ☃ = ☃.field_191138_a.func_185831_a(EnumFacing.WEST);
         ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "entrance", ☃.field_191139_b.func_177967_a(☃, 9), ☃.field_191138_a));
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.SOUTH), 16);
      }

      private void func_191131_b(List<WoodlandMansionPieces.MansionTemplate> var1, WoodlandMansionPieces.PlacementData var2) {
         ☃.add(
            new WoodlandMansionPieces.MansionTemplate(
               this.field_191134_a, ☃.field_191140_c, ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.EAST), 7), ☃.field_191138_a
            )
         );
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.SOUTH), 8);
      }

      private void func_191124_c(List<WoodlandMansionPieces.MansionTemplate> var1, WoodlandMansionPieces.PlacementData var2) {
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.SOUTH), -1);
         ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "wall_corner", ☃.field_191139_b, ☃.field_191138_a));
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.SOUTH), -7);
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.WEST), -6);
         ☃.field_191138_a = ☃.field_191138_a.func_185830_a(Rotation.CLOCKWISE_90);
      }

      private void func_191126_d(List<WoodlandMansionPieces.MansionTemplate> var1, WoodlandMansionPieces.PlacementData var2) {
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.SOUTH), 6);
         ☃.field_191139_b = ☃.field_191139_b.func_177967_a(☃.field_191138_a.func_185831_a(EnumFacing.EAST), 8);
         ☃.field_191138_a = ☃.field_191138_a.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
      }

      private void func_191129_a(
         List<WoodlandMansionPieces.MansionTemplate> var1, BlockPos var2, Rotation var3, EnumFacing var4, WoodlandMansionPieces.RoomCollection var5
      ) {
         Rotation ☃ = Rotation.NONE;
         String ☃x = ☃.func_191104_a(this.field_191135_b);
         if (☃ != EnumFacing.EAST) {
            if (☃ == EnumFacing.NORTH) {
               ☃ = ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
            } else if (☃ == EnumFacing.WEST) {
               ☃ = ☃.func_185830_a(Rotation.CLOCKWISE_180);
            } else if (☃ == EnumFacing.SOUTH) {
               ☃ = ☃.func_185830_a(Rotation.CLOCKWISE_90);
            } else {
               ☃x = ☃.func_191099_b(this.field_191135_b);
            }
         }

         BlockPos ☃ = Template.func_191157_a(new BlockPos(1, 0, 0), Mirror.NONE, ☃, 7, 7);
         ☃ = ☃.func_185830_a(☃);
         ☃ = ☃.func_190942_a(☃);
         BlockPos ☃x = ☃.func_177982_a(☃.func_177958_n(), 0, ☃.func_177952_p());
         ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃x, ☃x, ☃));
      }

      private void func_191132_a(
         List<WoodlandMansionPieces.MansionTemplate> var1,
         BlockPos var2,
         Rotation var3,
         EnumFacing var4,
         EnumFacing var5,
         WoodlandMansionPieces.RoomCollection var6,
         boolean var7
      ) {
         if (☃ == EnumFacing.EAST && ☃ == EnumFacing.SOUTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
            ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃));
         } else if (☃ == EnumFacing.EAST && ☃ == EnumFacing.NORTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
            ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃, Mirror.LEFT_RIGHT));
         } else if (☃ == EnumFacing.WEST && ☃ == EnumFacing.NORTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_180)
               )
            );
         } else if (☃ == EnumFacing.WEST && ☃ == EnumFacing.SOUTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
            ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃, Mirror.FRONT_BACK));
         } else if (☃ == EnumFacing.SOUTH && ☃ == EnumFacing.EAST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT
               )
            );
         } else if (☃ == EnumFacing.SOUTH && ☃ == EnumFacing.WEST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_90)
               )
            );
         } else if (☃ == EnumFacing.NORTH && ☃ == EnumFacing.WEST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK
               )
            );
         } else if (☃ == EnumFacing.NORTH && ☃ == EnumFacing.EAST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191100_a(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (☃ == EnumFacing.SOUTH && ☃ == EnumFacing.NORTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 8);
            ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191098_b(this.field_191135_b, ☃), ☃, ☃));
         } else if (☃ == EnumFacing.NORTH && ☃ == EnumFacing.SOUTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 7);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 14);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191098_b(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_180)
               )
            );
         } else if (☃ == EnumFacing.WEST && ☃ == EnumFacing.EAST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 15);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191098_b(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_90)
               )
            );
         } else if (☃ == EnumFacing.EAST && ☃ == EnumFacing.WEST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.WEST), 7);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), 6);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(
                  this.field_191134_a, ☃.func_191098_b(this.field_191135_b, ☃), ☃, ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90)
               )
            );
         } else if (☃ == EnumFacing.UP && ☃ == EnumFacing.EAST) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 15);
            ☃.add(
               new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191102_c(this.field_191135_b), ☃, ☃.func_185830_a(Rotation.CLOCKWISE_90))
            );
         } else if (☃ == EnumFacing.UP && ☃ == EnumFacing.SOUTH) {
            BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
            ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.NORTH), 0);
            ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191102_c(this.field_191135_b), ☃, ☃));
         }
      }

      private void func_191127_a(
         List<WoodlandMansionPieces.MansionTemplate> var1,
         BlockPos var2,
         Rotation var3,
         EnumFacing var4,
         EnumFacing var5,
         WoodlandMansionPieces.RoomCollection var6
      ) {
         int ☃ = 0;
         int ☃x = 0;
         Rotation ☃xx = ☃;
         Mirror ☃xxx = Mirror.NONE;
         if (☃ == EnumFacing.EAST && ☃ == EnumFacing.SOUTH) {
            ☃ = -7;
         } else if (☃ == EnumFacing.EAST && ☃ == EnumFacing.NORTH) {
            ☃ = -7;
            ☃x = 6;
            ☃xxx = Mirror.LEFT_RIGHT;
         } else if (☃ == EnumFacing.NORTH && ☃ == EnumFacing.EAST) {
            ☃ = 1;
            ☃x = 14;
            ☃xx = ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
         } else if (☃ == EnumFacing.NORTH && ☃ == EnumFacing.WEST) {
            ☃ = 7;
            ☃x = 14;
            ☃xx = ☃.func_185830_a(Rotation.COUNTERCLOCKWISE_90);
            ☃xxx = Mirror.LEFT_RIGHT;
         } else if (☃ == EnumFacing.SOUTH && ☃ == EnumFacing.WEST) {
            ☃ = 7;
            ☃x = -8;
            ☃xx = ☃.func_185830_a(Rotation.CLOCKWISE_90);
         } else if (☃ == EnumFacing.SOUTH && ☃ == EnumFacing.EAST) {
            ☃ = 1;
            ☃x = -8;
            ☃xx = ☃.func_185830_a(Rotation.CLOCKWISE_90);
            ☃xxx = Mirror.LEFT_RIGHT;
         } else if (☃ == EnumFacing.WEST && ☃ == EnumFacing.NORTH) {
            ☃ = 15;
            ☃x = 6;
            ☃xx = ☃.func_185830_a(Rotation.CLOCKWISE_180);
         } else if (☃ == EnumFacing.WEST && ☃ == EnumFacing.SOUTH) {
            ☃ = 15;
            ☃xxx = Mirror.FRONT_BACK;
         }

         BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), ☃);
         ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.SOUTH), ☃x);
         ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191101_d(this.field_191135_b), ☃, ☃xx, ☃xxx));
      }

      private void func_191128_a(List<WoodlandMansionPieces.MansionTemplate> var1, BlockPos var2, Rotation var3, WoodlandMansionPieces.RoomCollection var4) {
         BlockPos ☃ = ☃.func_177967_a(☃.func_185831_a(EnumFacing.EAST), 1);
         ☃.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, ☃.func_191103_e(this.field_191135_b), ☃, ☃, Mirror.NONE));
      }
   }

   abstract static class RoomCollection {
      private RoomCollection() {
      }

      public abstract String func_191104_a(Random var1);

      public abstract String func_191099_b(Random var1);

      public abstract String func_191100_a(Random var1, boolean var2);

      public abstract String func_191098_b(Random var1, boolean var2);

      public abstract String func_191102_c(Random var1);

      public abstract String func_191101_d(Random var1);

      public abstract String func_191103_e(Random var1);
   }

   static class SecondFloor extends WoodlandMansionPieces.RoomCollection {
      private SecondFloor() {
      }

      @Override
      public String func_191104_a(Random var1) {
         return "1x1_b" + (☃.nextInt(4) + 1);
      }

      @Override
      public String func_191099_b(Random var1) {
         return "1x1_as" + (☃.nextInt(4) + 1);
      }

      @Override
      public String func_191100_a(Random var1, boolean var2) {
         return ☃ ? "1x2_c_stairs" : "1x2_c" + (☃.nextInt(4) + 1);
      }

      @Override
      public String func_191098_b(Random var1, boolean var2) {
         return ☃ ? "1x2_d_stairs" : "1x2_d" + (☃.nextInt(5) + 1);
      }

      @Override
      public String func_191102_c(Random var1) {
         return "1x2_se" + (☃.nextInt(1) + 1);
      }

      @Override
      public String func_191101_d(Random var1) {
         return "2x2_b" + (☃.nextInt(5) + 1);
      }

      @Override
      public String func_191103_e(Random var1) {
         return "2x2_s1";
      }
   }

   static class SimpleGrid {
      private final int[][] field_191148_a;
      private final int field_191149_b;
      private final int field_191150_c;
      private final int field_191151_d;

      public SimpleGrid(int var1, int var2, int var3) {
         this.field_191149_b = ☃;
         this.field_191150_c = ☃;
         this.field_191151_d = ☃;
         this.field_191148_a = new int[☃][☃];
      }

      public void func_191144_a(int var1, int var2, int var3) {
         if (☃ >= 0 && ☃ < this.field_191149_b && ☃ >= 0 && ☃ < this.field_191150_c) {
            this.field_191148_a[☃][☃] = ☃;
         }
      }

      public void func_191142_a(int var1, int var2, int var3, int var4, int var5) {
         for(int ☃ = ☃; ☃ <= ☃; ++☃) {
            for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
               this.func_191144_a(☃x, ☃, ☃);
            }
         }
      }

      public int func_191145_a(int var1, int var2) {
         return ☃ >= 0 && ☃ < this.field_191149_b && ☃ >= 0 && ☃ < this.field_191150_c ? this.field_191148_a[☃][☃] : this.field_191151_d;
      }

      public void func_197588_a(int var1, int var2, int var3, int var4) {
         if (this.func_191145_a(☃, ☃) == ☃) {
            this.func_191144_a(☃, ☃, ☃);
         }
      }

      public boolean func_191147_b(int var1, int var2, int var3) {
         return this.func_191145_a(☃ - 1, ☃) == ☃
            || this.func_191145_a(☃ + 1, ☃) == ☃
            || this.func_191145_a(☃, ☃ + 1) == ☃
            || this.func_191145_a(☃, ☃ - 1) == ☃;
      }
   }

   static class ThirdFloor extends WoodlandMansionPieces.SecondFloor {
      private ThirdFloor() {
      }
   }
}
