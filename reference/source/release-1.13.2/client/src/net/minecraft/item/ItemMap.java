package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase {
   public ItemMap(Item.Properties var1) {
      super(☃);
   }

   public static ItemStack func_195952_a(World var0, int var1, int var2, byte var3, boolean var4, boolean var5) {
      ItemStack ☃ = new ItemStack(Items.field_151098_aY);
      func_195951_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃.field_73011_w.func_186058_p());
      return ☃;
   }

   @Nullable
   public static MapData func_195950_a(ItemStack var0, World var1) {
      MapData ☃ = func_195953_a(☃, "map_" + func_195949_f(☃));
      if (☃ == null && !☃.field_72995_K) {
         ☃ = func_195951_a(☃, ☃, ☃.func_72912_H().func_76079_c(), ☃.func_72912_H().func_76074_e(), 3, false, false, ☃.field_73011_w.func_186058_p());
      }

      return ☃;
   }

   public static int func_195949_f(ItemStack var0) {
      NBTTagCompound ☃ = ☃.func_77978_p();
      return ☃ != null && ☃.func_150297_b("map", 99) ? ☃.func_74762_e("map") : 0;
   }

   private static MapData func_195951_a(ItemStack var0, World var1, int var2, int var3, int var4, boolean var5, boolean var6, DimensionType var7) {
      int ☃ = ☃.func_212410_a(DimensionType.OVERWORLD, "map");
      MapData ☃x = new MapData("map_" + ☃);
      ☃x.func_212440_a(☃, ☃, ☃, ☃, ☃, ☃);
      ☃.func_212409_a(DimensionType.OVERWORLD, ☃x.func_195925_e(), ☃x);
      ☃.func_196082_o().func_74768_a("map", ☃);
      return ☃x;
   }

   @Nullable
   public static MapData func_195953_a(IWorld var0, String var1) {
      return ☃.func_212411_a(DimensionType.OVERWORLD, MapData::new, ☃);
   }

   public void func_77872_a(World var1, Entity var2, MapData var3) {
      if (☃.field_73011_w.func_186058_p() == ☃.field_76200_c && ☃ instanceof EntityPlayer) {
         int ☃ = 1 << ☃.field_76197_d;
         int ☃x = ☃.field_76201_a;
         int ☃xx = ☃.field_76199_b;
         int ☃xxx = MathHelper.func_76128_c(☃.field_70165_t - (double)☃x) / ☃ + 64;
         int ☃xxxx = MathHelper.func_76128_c(☃.field_70161_v - (double)☃xx) / ☃ + 64;
         int ☃xxxxx = 128 / ☃;
         if (☃.field_73011_w.func_177495_o()) {
            ☃xxxxx /= 2;
         }

         MapData.MapInfo ☃ = ☃.func_82568_a((EntityPlayer)☃);
         ++☃.field_82569_d;
         boolean ☃x = false;

         for(int ☃xx = ☃xxx - ☃xxxxx + 1; ☃xx < ☃xxx + ☃xxxxx; ++☃xx) {
            if ((☃xx & 15) == (☃.field_82569_d & 15) || ☃x) {
               ☃x = false;
               double ☃xxx = 0.0;

               for(int ☃xxxx = ☃xxxx - ☃xxxxx - 1; ☃xxxx < ☃xxxx + ☃xxxxx; ++☃xxxx) {
                  if (☃xx >= 0 && ☃xxxx >= -1 && ☃xx < 128 && ☃xxxx < 128) {
                     int ☃xxxxx = ☃xx - ☃xxx;
                     int ☃xxxxxx = ☃xxxx - ☃xxxx;
                     boolean ☃xxxxxxx = ☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx > (☃xxxxx - 2) * (☃xxxxx - 2);
                     int ☃xxxxxxxx = (☃x / ☃ + ☃xx - 64) * ☃;
                     int ☃xxxxxxxxx = (☃xx / ☃ + ☃xxxx - 64) * ☃;
                     Multiset<MaterialColor> ☃xxxxxxxxxx = LinkedHashMultiset.create();
                     Chunk ☃xxxxxxxxxxx = ☃.func_175726_f(new BlockPos(☃xxxxxxxx, 0, ☃xxxxxxxxx));
                     if (!☃xxxxxxxxxxx.func_76621_g()) {
                        int ☃xxxxxxxxxxxx = ☃xxxxxxxx & 15;
                        int ☃xxxxxxxxxxxxx = ☃xxxxxxxxx & 15;
                        int ☃xxxxxxxxxxxxxx = 0;
                        double ☃xxxxxxxxxxxxxxx = 0.0;
                        if (☃.field_73011_w.func_177495_o()) {
                           int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxx + ☃xxxxxxxxx * 231871;
                           ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx * 31287121 + ☃xxxxxxxxxxxxxxxx * 11;
                           if ((☃xxxxxxxxxxxxxxxx >> 20 & 1) == 0) {
                              ☃xxxxxxxxxx.add(Blocks.field_150346_d.func_176223_P().func_185909_g(☃, BlockPos.field_177992_a), 10);
                           } else {
                              ☃xxxxxxxxxx.add(Blocks.field_150348_b.func_176223_P().func_185909_g(☃, BlockPos.field_177992_a), 100);
                           }

                           ☃xxxxxxxxxxxxxxx = 100.0;
                        } else {
                           BlockPos.MutableBlockPos ☃xxxxxxxxxxxx = new BlockPos.MutableBlockPos();

                           for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxxx) {
                              for(int ☃xxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxxxx) {
                                 int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx.func_201576_a(
                                       Heightmap.Type.WORLD_SURFACE, ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx
                                    )
                                    + 1;
                                 IBlockState ☃xxxxxxxxxxxxxxx;
                                 if (☃xxxxxxxxxxxxxxxx <= 1) {
                                    ☃xxxxxxxxxxxxxxx = Blocks.field_150357_h.func_176223_P();
                                 } else {
                                    do {
                                       ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxx.func_186032_a(
                                          ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxx, --☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx
                                       );
                                       ☃xxxxxxxxxxxx.func_181079_c(
                                          (☃xxxxxxxxxxx.field_76635_g << 4) + ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxx,
                                          ☃xxxxxxxxxxxxxxxx,
                                          (☃xxxxxxxxxxx.field_76647_h << 4) + ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx
                                       );
                                    } while(☃xxxxxxxxxxxxxxx.func_185909_g(☃, ☃xxxxxxxxxxxx) == MaterialColor.field_151660_b && ☃xxxxxxxxxxxxxxxx > 0);

                                    if (☃xxxxxxxxxxxxxxxx > 0 && !☃xxxxxxxxxxxxxxx.func_204520_s().func_206888_e()) {
                                       int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx - 1;

                                       IBlockState ☃;
                                       do {
                                          ☃ = ☃xxxxxxxxxxx.func_186032_a(☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx--, ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx);
                                          ++☃xxxxxxxxxxxxxx;
                                       } while(☃xxxxxxxxxxxxxxx > 0 && !☃.func_204520_s().func_206888_e());

                                       ☃xxxxxxxxxxxxxxx = this.func_211698_a(☃, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxx);
                                    }
                                 }

                                 ☃.func_204268_a(
                                    ☃,
                                    (☃xxxxxxxxxxx.field_76635_g << 4) + ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxx,
                                    (☃xxxxxxxxxxx.field_76647_h << 4) + ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxx
                                 );
                                 ☃xxxxxxxxxxxxxxx += (double)☃xxxxxxxxxxxxxxxx / (double)(☃ * ☃);
                                 ☃xxxxxxxxxx.add(☃xxxxxxxxxxxxxxx.func_185909_g(☃, ☃xxxxxxxxxxxx));
                              }
                           }
                        }

                        ☃xxxxxxxxxxxxxx /= ☃ * ☃;
                        double ☃xxxxxxxxxxxx = (☃xxxxxxxxxxxxxxx - ☃xxx) * 4.0 / (double)(☃ + 4) + ((double)(☃xx + ☃xxxx & 1) - 0.5) * 0.4;
                        int ☃xxxxxxxxxxxxx = 1;
                        if (☃xxxxxxxxxxxx > 0.6) {
                           ☃xxxxxxxxxxxxx = 2;
                        }

                        if (☃xxxxxxxxxxxx < -0.6) {
                           ☃xxxxxxxxxxxxx = 0;
                        }

                        MaterialColor ☃xxxxxxxxxxxx = Iterables.getFirst(Multisets.copyHighestCountFirst(☃xxxxxxxxxx), MaterialColor.field_151660_b);
                        if (☃xxxxxxxxxxxx == MaterialColor.field_151662_n) {
                           ☃xxxxxxxxxxxx = (double)☃xxxxxxxxxxxxxx * 0.1 + (double)(☃xx + ☃xxxx & 1) * 0.2;
                           ☃xxxxxxxxxxxxx = 1;
                           if (☃xxxxxxxxxxxx < 0.5) {
                              ☃xxxxxxxxxxxxx = 2;
                           }

                           if (☃xxxxxxxxxxxx > 0.9) {
                              ☃xxxxxxxxxxxxx = 0;
                           }
                        }

                        ☃xxx = ☃xxxxxxxxxxxxxxx;
                        if (☃xxxx >= 0 && ☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx < ☃xxxxx * ☃xxxxx && (!☃xxxxxxx || (☃xx + ☃xxxx & 1) != 0)) {
                           byte ☃xxxxxxxxxxxx = ☃.field_76198_e[☃xx + ☃xxxx * 128];
                           byte ☃xxxxxxxxxxxxx = (byte)(☃xxxxxxxxxxxx.field_76290_q * 4 + ☃xxxxxxxxxxxxx);
                           if (☃xxxxxxxxxxxx != ☃xxxxxxxxxxxxx) {
                              ☃.field_76198_e[☃xx + ☃xxxx * 128] = ☃xxxxxxxxxxxxx;
                              ☃.func_176053_a(☃xx, ☃xxxx);
                              ☃x = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private IBlockState func_211698_a(World var1, IBlockState var2, BlockPos var3) {
      IFluidState ☃ = ☃.func_204520_s();
      return !☃.func_206888_e() && !Block.func_208061_a(☃.func_196952_d(☃, ☃), EnumFacing.UP) ? ☃.func_206883_i() : ☃;
   }

   private static boolean func_195954_a(Biome[] var0, int var1, int var2, int var3) {
      return ☃[☃ * ☃ + ☃ * ☃ * 128 * ☃].func_185355_j() >= 0.0F;
   }

   public static void func_190905_a(World var0, ItemStack var1) {
      MapData ☃ = func_195950_a(☃, ☃);
      if (☃ != null) {
         if (☃.field_73011_w.func_186058_p() == ☃.field_76200_c) {
            int ☃x = 1 << ☃.field_76197_d;
            int ☃xx = ☃.field_76201_a;
            int ☃xxx = ☃.field_76199_b;
            Biome[] ☃xxxx = ☃.func_72863_F()
               .func_201711_g()
               .func_202090_b()
               .func_201537_a((☃xx / ☃x - 64) * ☃x, (☃xxx / ☃x - 64) * ☃x, 128 * ☃x, 128 * ☃x, false);

            for(int ☃xxxxx = 0; ☃xxxxx < 128; ++☃xxxxx) {
               for(int ☃xxxxxx = 0; ☃xxxxxx < 128; ++☃xxxxxx) {
                  if (☃xxxxx > 0 && ☃xxxxxx > 0 && ☃xxxxx < 127 && ☃xxxxxx < 127) {
                     Biome ☃xxxxxxx = ☃xxxx[☃xxxxx * ☃x + ☃xxxxxx * ☃x * 128 * ☃x];
                     int ☃xxxxxxxx = 8;
                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx - 1, ☃xxxxxx - 1)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx - 1, ☃xxxxxx + 1)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx - 1, ☃xxxxxx)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx + 1, ☃xxxxxx - 1)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx + 1, ☃xxxxxx + 1)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx + 1, ☃xxxxxx)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx, ☃xxxxxx - 1)) {
                        --☃xxxxxxxx;
                     }

                     if (func_195954_a(☃xxxx, ☃x, ☃xxxxx, ☃xxxxxx + 1)) {
                        --☃xxxxxxxx;
                     }

                     int ☃xxxxxxx = 3;
                     MaterialColor ☃xxxxxxxx = MaterialColor.field_151660_b;
                     if (☃xxxxxxx.func_185355_j() < 0.0F) {
                        ☃xxxxxxxx = MaterialColor.field_151676_q;
                        if (☃xxxxxxxx > 7 && ☃xxxxxx % 2 == 0) {
                           ☃xxxxxxx = (☃xxxxx + (int)(MathHelper.func_76126_a((float)☃xxxxxx + 0.0F) * 7.0F)) / 8 % 5;
                           if (☃xxxxxxx == 3) {
                              ☃xxxxxxx = 1;
                           } else if (☃xxxxxxx == 4) {
                              ☃xxxxxxx = 0;
                           }
                        } else if (☃xxxxxxxx > 7) {
                           ☃xxxxxxxx = MaterialColor.field_151660_b;
                        } else if (☃xxxxxxxx > 5) {
                           ☃xxxxxxx = 1;
                        } else if (☃xxxxxxxx > 3) {
                           ☃xxxxxxx = 0;
                        } else if (☃xxxxxxxx > 1) {
                           ☃xxxxxxx = 0;
                        }
                     } else if (☃xxxxxxxx > 0) {
                        ☃xxxxxxxx = MaterialColor.field_151650_B;
                        if (☃xxxxxxxx > 3) {
                           ☃xxxxxxx = 1;
                        } else {
                           ☃xxxxxxx = 3;
                        }
                     }

                     if (☃xxxxxxxx != MaterialColor.field_151660_b) {
                        ☃.field_76198_e[☃xxxxx + ☃xxxxxx * 128] = (byte)(☃xxxxxxxx.field_76290_q * 4 + ☃xxxxxxx);
                        ☃.func_176053_a(☃xxxxx, ☃xxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void func_77663_a(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
      if (!☃.field_72995_K) {
         MapData ☃ = func_195950_a(☃, ☃);
         if (☃ instanceof EntityPlayer) {
            EntityPlayer ☃x = (EntityPlayer)☃;
            ☃.func_76191_a(☃x, ☃);
         }

         if (☃ || ☃ instanceof EntityPlayer && ((EntityPlayer)☃).func_184592_cb() == ☃) {
            this.func_77872_a(☃, ☃, ☃);
         }
      }
   }

   @Nullable
   @Override
   public Packet<?> func_150911_c(ItemStack var1, World var2, EntityPlayer var3) {
      return func_195950_a(☃, ☃).func_176052_a(☃, ☃, ☃);
   }

   @Override
   public void func_77622_d(ItemStack var1, World var2, EntityPlayer var3) {
      NBTTagCompound ☃ = ☃.func_77978_p();
      if (☃ != null && ☃.func_150297_b("map_scale_direction", 99)) {
         func_185063_a(☃, ☃, ☃.func_74762_e("map_scale_direction"));
         ☃.func_82580_o("map_scale_direction");
      }
   }

   protected static void func_185063_a(ItemStack var0, World var1, int var2) {
      MapData ☃ = func_195950_a(☃, ☃);
      if (☃ != null) {
         func_195951_a(
            ☃, ☃, ☃.field_76201_a, ☃.field_76199_b, MathHelper.func_76125_a(☃.field_76197_d + ☃, 0, 4), ☃.field_186210_e, ☃.field_191096_f, ☃.field_76200_c
         );
      }
   }

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      if (☃.func_194127_a()) {
         MapData ☃ = ☃ == null ? null : func_195950_a(☃, ☃);
         if (☃ != null) {
            ☃.add(new TextComponentTranslation("filled_map.id", func_195949_f(☃)).func_211708_a(TextFormatting.GRAY));
            ☃.add(new TextComponentTranslation("filled_map.scale", 1 << ☃.field_76197_d).func_211708_a(TextFormatting.GRAY));
            ☃.add(new TextComponentTranslation("filled_map.level", ☃.field_76197_d, 4).func_211708_a(TextFormatting.GRAY));
         } else {
            ☃.add(new TextComponentTranslation("filled_map.unknown").func_211708_a(TextFormatting.GRAY));
         }
      }
   }

   public static int func_190907_h(ItemStack var0) {
      NBTTagCompound ☃ = ☃.func_179543_a("display");
      if (☃ != null && ☃.func_150297_b("MapColor", 99)) {
         int ☃x = ☃.func_74762_e("MapColor");
         return 0xFF000000 | ☃x & 16777215;
      } else {
         return -12173266;
      }
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      if (☃.func_203425_a(BlockTags.field_202897_p)) {
         if (!☃.field_196006_g.field_72995_K) {
            MapData ☃x = func_195950_a(☃.func_195996_i(), ☃.func_195991_k());
            ☃x.func_204269_a(☃.func_195991_k(), ☃.func_195995_a());
         }

         return EnumActionResult.SUCCESS;
      } else {
         return super.func_195939_a(☃);
      }
   }
}
