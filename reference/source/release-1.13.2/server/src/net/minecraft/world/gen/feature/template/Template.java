package net.minecraft.world.gen.feature.template;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePartBitSet;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class Template {
   private final List<List<Template.BlockInfo>> field_204769_a = Lists.newArrayList();
   private final List<Template.EntityInfo> field_186271_b = Lists.<Template.EntityInfo>newArrayList();
   private BlockPos field_186272_c = BlockPos.field_177992_a;
   private String field_186273_d = "?";

   public BlockPos func_186259_a() {
      return this.field_186272_c;
   }

   public void func_186252_a(String var1) {
      this.field_186273_d = ☃;
   }

   public String func_186261_b() {
      return this.field_186273_d;
   }

   public void func_186254_a(World var1, BlockPos var2, BlockPos var3, boolean var4, @Nullable Block var5) {
      if (☃.func_177958_n() >= 1 && ☃.func_177956_o() >= 1 && ☃.func_177952_p() >= 1) {
         BlockPos ☃ = ☃.func_177971_a(☃).func_177982_a(-1, -1, -1);
         List<Template.BlockInfo> ☃x = Lists.<Template.BlockInfo>newArrayList();
         List<Template.BlockInfo> ☃xx = Lists.<Template.BlockInfo>newArrayList();
         List<Template.BlockInfo> ☃xxx = Lists.<Template.BlockInfo>newArrayList();
         BlockPos ☃xxxx = new BlockPos(
            Math.min(☃.func_177958_n(), ☃.func_177958_n()), Math.min(☃.func_177956_o(), ☃.func_177956_o()), Math.min(☃.func_177952_p(), ☃.func_177952_p())
         );
         BlockPos ☃xxxxx = new BlockPos(
            Math.max(☃.func_177958_n(), ☃.func_177958_n()), Math.max(☃.func_177956_o(), ☃.func_177956_o()), Math.max(☃.func_177952_p(), ☃.func_177952_p())
         );
         this.field_186272_c = ☃;

         for(BlockPos.MutableBlockPos ☃xxxxxx : BlockPos.func_177975_b(☃xxxx, ☃xxxxx)) {
            BlockPos ☃xxxxxxx = ☃xxxxxx.func_177973_b(☃xxxx);
            IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃xxxxxx);
            if (☃ == null || ☃ != ☃xxxxxxxx.func_177230_c()) {
               TileEntity ☃xxxxxxxxx = ☃.func_175625_s(☃xxxxxx);
               if (☃xxxxxxxxx != null) {
                  NBTTagCompound ☃xxxxxxxxxx = ☃xxxxxxxxx.func_189515_b(new NBTTagCompound());
                  ☃xxxxxxxxxx.func_82580_o("x");
                  ☃xxxxxxxxxx.func_82580_o("y");
                  ☃xxxxxxxxxx.func_82580_o("z");
                  ☃xx.add(new Template.BlockInfo(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx));
               } else if (!☃xxxxxxxx.func_200015_d(☃, ☃xxxxxx) && !☃xxxxxxxx.func_185917_h()) {
                  ☃xxx.add(new Template.BlockInfo(☃xxxxxxx, ☃xxxxxxxx, null));
               } else {
                  ☃x.add(new Template.BlockInfo(☃xxxxxxx, ☃xxxxxxxx, null));
               }
            }
         }

         List<Template.BlockInfo> ☃xxxxxx = Lists.<Template.BlockInfo>newArrayList();
         ☃xxxxxx.addAll(☃x);
         ☃xxxxxx.addAll(☃xx);
         ☃xxxxxx.addAll(☃xxx);
         this.field_204769_a.clear();
         this.field_204769_a.add(☃xxxxxx);
         if (☃) {
            this.func_186255_a(☃, ☃xxxx, ☃xxxxx.func_177982_a(1, 1, 1));
         } else {
            this.field_186271_b.clear();
         }
      }
   }

   private void func_186255_a(World var1, BlockPos var2, BlockPos var3) {
      List<Entity> ☃ = ☃.func_175647_a(Entity.class, new AxisAlignedBB(☃, ☃), var0 -> !(var0 instanceof EntityPlayer));
      this.field_186271_b.clear();

      for(Entity ☃x : ☃) {
         Vec3d ☃xxx = new Vec3d(
            ☃x.field_70165_t - (double)☃.func_177958_n(), ☃x.field_70163_u - (double)☃.func_177956_o(), ☃x.field_70161_v - (double)☃.func_177952_p()
         );
         NBTTagCompound ☃xxxx = new NBTTagCompound();
         ☃x.func_70039_c(☃xxxx);
         BlockPos ☃xx;
         if (☃x instanceof EntityPainting) {
            ☃xx = ((EntityPainting)☃x).func_174857_n().func_177973_b(☃);
         } else {
            ☃xx = new BlockPos(☃xxx);
         }

         this.field_186271_b.add(new Template.EntityInfo(☃xxx, ☃xx, ☃xxxx));
      }
   }

   public Map<BlockPos, String> func_186258_a(BlockPos var1, PlacementSettings var2) {
      Map<BlockPos, String> ☃ = Maps.newHashMap();
      MutableBoundingBox ☃x = ☃.func_186213_g();

      for(Template.BlockInfo ☃xx : ☃.func_204764_a(this.field_204769_a, ☃)) {
         BlockPos ☃xxx = func_186266_a(☃, ☃xx.field_186242_a).func_177971_a(☃);
         if (☃x == null || ☃x.func_175898_b(☃xxx)) {
            IBlockState ☃xxxx = ☃xx.field_186243_b;
            if (☃xxxx.func_177230_c() == Blocks.field_185779_df && ☃xx.field_186244_c != null) {
               StructureMode ☃xxxxx = StructureMode.valueOf(☃xx.field_186244_c.func_74779_i("mode"));
               if (☃xxxxx == StructureMode.DATA) {
                  ☃.put(☃xxx, ☃xx.field_186244_c.func_74779_i("metadata"));
               }
            }
         }
      }

      return ☃;
   }

   public BlockPos func_186262_a(PlacementSettings var1, BlockPos var2, PlacementSettings var3, BlockPos var4) {
      BlockPos ☃ = func_186266_a(☃, ☃);
      BlockPos ☃x = func_186266_a(☃, ☃);
      return ☃.func_177973_b(☃x);
   }

   public static BlockPos func_186266_a(PlacementSettings var0, BlockPos var1) {
      return func_207669_a(☃, ☃.func_186212_b(), ☃.func_186215_c(), ☃.func_207664_d());
   }

   public void func_186260_a(IWorld var1, BlockPos var2, PlacementSettings var3) {
      ☃.func_186224_i();
      this.func_186253_b(☃, ☃, ☃);
   }

   public void func_186253_b(IWorld var1, BlockPos var2, PlacementSettings var3) {
      this.func_189960_a(☃, ☃, new IntegrityProcessor(☃, ☃), ☃, 2);
   }

   public boolean func_189962_a(IWorld var1, BlockPos var2, PlacementSettings var3, int var4) {
      return this.func_189960_a(☃, ☃, new IntegrityProcessor(☃, ☃), ☃, ☃);
   }

   public boolean func_189960_a(IWorld var1, BlockPos var2, @Nullable ITemplateProcessor var3, PlacementSettings var4, int var5) {
      if (this.field_204769_a.isEmpty()) {
         return false;
      } else {
         List<Template.BlockInfo> ☃ = ☃.func_204764_a(this.field_204769_a, ☃);
         if ((!☃.isEmpty() || !☃.func_186221_e() && !this.field_186271_b.isEmpty())
            && this.field_186272_c.func_177958_n() >= 1
            && this.field_186272_c.func_177956_o() >= 1
            && this.field_186272_c.func_177952_p() >= 1) {
            Block ☃x = ☃.func_186219_f();
            MutableBoundingBox ☃xx = ☃.func_186213_g();
            List<BlockPos> ☃xxx = Lists.<BlockPos>newArrayListWithCapacity(☃.func_204763_l() ? ☃.size() : 0);
            List<Pair<BlockPos, NBTTagCompound>> ☃xxxx = Lists.<Pair<BlockPos, NBTTagCompound>>newArrayListWithCapacity(☃.size());
            int ☃xxxxx = Integer.MAX_VALUE;
            int ☃xxxxxx = Integer.MAX_VALUE;
            int ☃xxxxxxx = Integer.MAX_VALUE;
            int ☃xxxxxxxx = Integer.MIN_VALUE;
            int ☃xxxxxxxxx = Integer.MIN_VALUE;
            int ☃xxxxxxxxxx = Integer.MIN_VALUE;

            for(Template.BlockInfo ☃xxxxxxxxxxx : ☃) {
               BlockPos ☃xxxxxxxxxxxx = func_186266_a(☃, ☃xxxxxxxxxxx.field_186242_a).func_177971_a(☃);
               Template.BlockInfo ☃xxxxxxxxxxxxx = ☃ != null ? ☃.func_189943_a(☃, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxx) : ☃xxxxxxxxxxx;
               if (☃xxxxxxxxxxxxx != null) {
                  Block ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.field_186243_b.func_177230_c();
                  if ((☃x == null || ☃x != ☃xxxxxxxxxxxxxx)
                     && (!☃.func_186227_h() || ☃xxxxxxxxxxxxxx != Blocks.field_185779_df)
                     && (☃xx == null || ☃xx.func_175898_b(☃xxxxxxxxxxxx))) {
                     IFluidState ☃xxxxxxxxxxxxxxx = ☃.func_204763_l() ? ☃.func_204610_c(☃xxxxxxxxxxxx) : null;
                     IBlockState ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.field_186243_b.func_185902_a(☃.func_186212_b());
                     IBlockState ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx.func_185907_a(☃.func_186215_c());
                     if (☃xxxxxxxxxxxxx.field_186244_c != null) {
                        TileEntity ☃xxxxxxxxxxxxxxxxxx = ☃.func_175625_s(☃xxxxxxxxxxxx);
                        if (☃xxxxxxxxxxxxxxxxxx instanceof IInventory) {
                           ((IInventory)☃xxxxxxxxxxxxxxxxxx).func_174888_l();
                        }

                        ☃.func_180501_a(☃xxxxxxxxxxxx, Blocks.field_180401_cv.func_176223_P(), 4);
                     }

                     if (☃.func_180501_a(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃)) {
                        ☃xxxxx = Math.min(☃xxxxx, ☃xxxxxxxxxxxx.func_177958_n());
                        ☃xxxxxx = Math.min(☃xxxxxx, ☃xxxxxxxxxxxx.func_177956_o());
                        ☃xxxxxxx = Math.min(☃xxxxxxx, ☃xxxxxxxxxxxx.func_177952_p());
                        ☃xxxxxxxx = Math.max(☃xxxxxxxx, ☃xxxxxxxxxxxx.func_177958_n());
                        ☃xxxxxxxxx = Math.max(☃xxxxxxxxx, ☃xxxxxxxxxxxx.func_177956_o());
                        ☃xxxxxxxxxx = Math.max(☃xxxxxxxxxx, ☃xxxxxxxxxxxx.func_177952_p());
                        ☃xxxx.add(Pair.of(☃xxxxxxxxxxxx, ☃xxxxxxxxxxx.field_186244_c));
                        if (☃xxxxxxxxxxxxx.field_186244_c != null) {
                           TileEntity ☃xxxxxxxxxxxxxxx = ☃.func_175625_s(☃xxxxxxxxxxxx);
                           if (☃xxxxxxxxxxxxxxx != null) {
                              ☃xxxxxxxxxxxxx.field_186244_c.func_74768_a("x", ☃xxxxxxxxxxxx.func_177958_n());
                              ☃xxxxxxxxxxxxx.field_186244_c.func_74768_a("y", ☃xxxxxxxxxxxx.func_177956_o());
                              ☃xxxxxxxxxxxxx.field_186244_c.func_74768_a("z", ☃xxxxxxxxxxxx.func_177952_p());
                              ☃xxxxxxxxxxxxxxx.func_145839_a(☃xxxxxxxxxxxxx.field_186244_c);
                              ☃xxxxxxxxxxxxxxx.func_189668_a(☃.func_186212_b());
                              ☃xxxxxxxxxxxxxxx.func_189667_a(☃.func_186215_c());
                           }
                        }

                        if (☃xxxxxxxxxxxxxxx != null && ☃xxxxxxxxxxxxxxxxx.func_177230_c() instanceof ILiquidContainer) {
                           ((ILiquidContainer)☃xxxxxxxxxxxxxxxxx.func_177230_c()).func_204509_a(☃, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx);
                           if (!☃xxxxxxxxxxxxxxx.func_206889_d()) {
                              ☃xxx.add(☃xxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }

            boolean ☃xxxxxxxxxxx = true;
            EnumFacing[] ☃xxxxxxxxxxxx = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};

            while(☃xxxxxxxxxxx && !☃xxx.isEmpty()) {
               ☃xxxxxxxxxxx = false;
               Iterator<BlockPos> ☃xxxxxxxxxxxxx = ☃xxx.iterator();

               while(☃xxxxxxxxxxxxx.hasNext()) {
                  BlockPos ☃xxxxxxxxxxxxxx = (BlockPos)☃xxxxxxxxxxxxx.next();
                  IFluidState ☃xxxxxxxxxxxxxxx = ☃.func_204610_c(☃xxxxxxxxxxxxxx);

                  for(int ☃xxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxx.length && !☃xxxxxxxxxxxxxxx.func_206889_d(); ++☃xxxxxxxxxxxxxxxx) {
                     IFluidState ☃xxxxxxxxxxxxxxxxx = ☃.func_204610_c(☃xxxxxxxxxxxxxx.func_177972_a(☃xxxxxxxxxxxx[☃xxxxxxxxxxxxxxxx]));
                     if (☃xxxxxxxxxxxxxxxxx.func_206885_f() > ☃xxxxxxxxxxxxxxx.func_206885_f()
                        || ☃xxxxxxxxxxxxxxxxx.func_206889_d() && !☃xxxxxxxxxxxxxxx.func_206889_d()) {
                        ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx;
                     }
                  }

                  if (☃xxxxxxxxxxxxxxx.func_206889_d()) {
                     IBlockState ☃xxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxxxx);
                     if (☃xxxxxxxxxxxxxxxx.func_177230_c() instanceof ILiquidContainer) {
                        ((ILiquidContainer)☃xxxxxxxxxxxxxxxx.func_177230_c()).func_204509_a(☃, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx);
                        ☃xxxxxxxxxxx = true;
                        ☃xxxxxxxxxxxxx.remove();
                     }
                  }
               }
            }

            if (☃xxxxx <= ☃xxxxxxxx) {
               VoxelShapePart ☃xxxxxxxxxxxxx = new VoxelShapePartBitSet(☃xxxxxxxx - ☃xxxxx + 1, ☃xxxxxxxxx - ☃xxxxxx + 1, ☃xxxxxxxxxx - ☃xxxxxxx + 1);
               int ☃xxxxxxxxxxxxxx = ☃xxxxx;
               int ☃xxxxxxxxxxxxxxx = ☃xxxxxx;
               int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxx;

               for(Pair<BlockPos, NBTTagCompound> ☃xxxxxxxxxxxxxxxxx : ☃xxxx) {
                  BlockPos ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx.getFirst();
                  ☃xxxxxxxxxxxxx.func_199625_a(
                     ☃xxxxxxxxxxxxxxxxxx.func_177958_n() - ☃xxxxxxxxxxxxxx,
                     ☃xxxxxxxxxxxxxxxxxx.func_177956_o() - ☃xxxxxxxxxxxxxxx,
                     ☃xxxxxxxxxxxxxxxxxx.func_177952_p() - ☃xxxxxxxxxxxxxxxx,
                     true,
                     true
                  );
               }

               ☃xxxxxxxxxxxxx.func_211540_a((var5x, var6x, var7x, var8x) -> {
                  BlockPos ☃ = new BlockPos(☃ + var6x, ☃ + var7x, ☃ + var8x);
                  BlockPos ☃x = ☃.func_177972_a(var5x);
                  IBlockState ☃xx = ☃.func_180495_p(☃);
                  IBlockState ☃xxx = ☃.func_180495_p(☃x);
                  IBlockState ☃xxxx = ☃xx.func_196956_a(var5x, ☃xxx, ☃, ☃, ☃x);
                  if (☃xx != ☃xxxx) {
                     ☃.func_180501_a(☃, ☃xxxx, ☃ & -2 | 16);
                  }

                  IBlockState ☃ = ☃xxx.func_196956_a(var5x.func_176734_d(), ☃xxxx, ☃, ☃x, ☃);
                  if (☃xxx != ☃) {
                     ☃.func_180501_a(☃x, ☃, ☃ & -2 | 16);
                  }
               });

               for(Pair<BlockPos, NBTTagCompound> ☃xxxxxxxxxxxxxxxxx : ☃xxxx) {
                  BlockPos ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx.getFirst();
                  IBlockState ☃xxxxxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxxxxxxxx);
                  IBlockState ☃xxxxxxxxxxxxxxxxxxxx = Block.func_199770_b(☃xxxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxxxxxxxxxxxxxx);
                  if (☃xxxxxxxxxxxxxxxxxxx != ☃xxxxxxxxxxxxxxxxxxxx) {
                     ☃.func_180501_a(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx, ☃ & -2 | 16);
                  }

                  ☃.func_195592_c(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx.func_177230_c());
                  if (☃xxxxxxxxxxxxxxxxx.getSecond() != null) {
                     TileEntity ☃xxxxxxxxxxxxxxxxxx = ☃.func_175625_s(☃xxxxxxxxxxxxxxxxxx);
                     if (☃xxxxxxxxxxxxxxxxxx != null) {
                        ☃xxxxxxxxxxxxxxxxxx.func_70296_d();
                     }
                  }
               }
            }

            if (!☃.func_186221_e()) {
               this.func_207668_a(☃, ☃, ☃.func_186212_b(), ☃.func_186215_c(), ☃.func_207664_d(), ☃xx);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   private void func_207668_a(IWorld var1, BlockPos var2, Mirror var3, Rotation var4, BlockPos var5, @Nullable MutableBoundingBox var6) {
      for(Template.EntityInfo ☃ : this.field_186271_b) {
         BlockPos ☃x = func_207669_a(☃.field_186248_b, ☃, ☃, ☃).func_177971_a(☃);
         if (☃ == null || ☃.func_175898_b(☃x)) {
            NBTTagCompound ☃xx = ☃.field_186249_c;
            Vec3d ☃xxx = func_207667_a(☃.field_186247_a, ☃, ☃, ☃);
            Vec3d ☃xxxx = ☃xxx.func_72441_c((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p());
            NBTTagList ☃xxxxx = new NBTTagList();
            ☃xxxxx.add((INBTBase)(new NBTTagDouble(☃xxxx.field_72450_a)));
            ☃xxxxx.add((INBTBase)(new NBTTagDouble(☃xxxx.field_72448_b)));
            ☃xxxxx.add((INBTBase)(new NBTTagDouble(☃xxxx.field_72449_c)));
            ☃xx.func_74782_a("Pos", ☃xxxxx);
            ☃xx.func_186854_a("UUID", UUID.randomUUID());

            Entity ☃;
            try {
               ☃ = EntityType.func_200716_a(☃xx, ☃.func_201672_e());
            } catch (Exception var16) {
               ☃ = null;
            }

            if (☃ != null) {
               float ☃xxxxxx = ☃.func_184217_a(☃);
               ☃xxxxxx += ☃.field_70177_z - ☃.func_184229_a(☃);
               ☃.func_70012_b(☃xxxx.field_72450_a, ☃xxxx.field_72448_b, ☃xxxx.field_72449_c, ☃xxxxxx, ☃.field_70125_A);
               ☃.func_72838_d(☃);
            }
         }
      }
   }

   public BlockPos func_186257_a(Rotation var1) {
      switch(☃) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            return new BlockPos(this.field_186272_c.func_177952_p(), this.field_186272_c.func_177956_o(), this.field_186272_c.func_177958_n());
         default:
            return this.field_186272_c;
      }
   }

   public static BlockPos func_207669_a(BlockPos var0, Mirror var1, Rotation var2, BlockPos var3) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      boolean ☃xxx = true;
      switch(☃) {
         case LEFT_RIGHT:
            ☃xx = -☃xx;
            break;
         case FRONT_BACK:
            ☃ = -☃;
            break;
         default:
            ☃xxx = false;
      }

      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177952_p();
      switch(☃) {
         case COUNTERCLOCKWISE_90:
            return new BlockPos(☃ - ☃x + ☃xx, ☃x, ☃ + ☃x - ☃);
         case CLOCKWISE_90:
            return new BlockPos(☃ + ☃x - ☃xx, ☃x, ☃x - ☃ + ☃);
         case CLOCKWISE_180:
            return new BlockPos(☃ + ☃ - ☃, ☃x, ☃x + ☃x - ☃xx);
         default:
            return ☃xxx ? new BlockPos(☃, ☃x, ☃xx) : ☃;
      }
   }

   private static Vec3d func_207667_a(Vec3d var0, Mirror var1, Rotation var2, BlockPos var3) {
      double ☃ = ☃.field_72450_a;
      double ☃x = ☃.field_72448_b;
      double ☃xx = ☃.field_72449_c;
      boolean ☃xxx = true;
      switch(☃) {
         case LEFT_RIGHT:
            ☃xx = 1.0 - ☃xx;
            break;
         case FRONT_BACK:
            ☃ = 1.0 - ☃;
            break;
         default:
            ☃xxx = false;
      }

      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177952_p();
      switch(☃) {
         case COUNTERCLOCKWISE_90:
            return new Vec3d((double)(☃ - ☃x) + ☃xx, ☃x, (double)(☃ + ☃x + 1) - ☃);
         case CLOCKWISE_90:
            return new Vec3d((double)(☃ + ☃x + 1) - ☃xx, ☃x, (double)(☃x - ☃) + ☃);
         case CLOCKWISE_180:
            return new Vec3d((double)(☃ + ☃ + 1) - ☃, ☃x, (double)(☃x + ☃x + 1) - ☃xx);
         default:
            return ☃xxx ? new Vec3d(☃, ☃x, ☃xx) : ☃;
      }
   }

   public BlockPos func_189961_a(BlockPos var1, Mirror var2, Rotation var3) {
      return func_191157_a(☃, ☃, ☃, this.func_186259_a().func_177958_n(), this.func_186259_a().func_177952_p());
   }

   public static BlockPos func_191157_a(BlockPos var0, Mirror var1, Rotation var2, int var3, int var4) {
      --☃;
      --☃;
      int ☃ = ☃ == Mirror.FRONT_BACK ? ☃ : 0;
      int ☃x = ☃ == Mirror.LEFT_RIGHT ? ☃ : 0;
      BlockPos ☃xx = ☃;
      switch(☃) {
         case COUNTERCLOCKWISE_90:
            ☃xx = ☃.func_177982_a(☃x, 0, ☃ - ☃);
            break;
         case CLOCKWISE_90:
            ☃xx = ☃.func_177982_a(☃ - ☃x, 0, ☃);
            break;
         case CLOCKWISE_180:
            ☃xx = ☃.func_177982_a(☃ - ☃, 0, ☃ - ☃x);
            break;
         case NONE:
            ☃xx = ☃.func_177982_a(☃, 0, ☃x);
      }

      return ☃xx;
   }

   public NBTTagCompound func_189552_a(NBTTagCompound var1) {
      if (this.field_204769_a.isEmpty()) {
         ☃.func_74782_a("blocks", new NBTTagList());
         ☃.func_74782_a("palette", new NBTTagList());
      } else {
         List<Template.BasicPalette> ☃ = Lists.<Template.BasicPalette>newArrayList();
         Template.BasicPalette ☃x = new Template.BasicPalette();
         ☃.add(☃x);

         for(int ☃xx = 1; ☃xx < this.field_204769_a.size(); ++☃xx) {
            ☃.add(new Template.BasicPalette());
         }

         NBTTagList ☃xx = new NBTTagList();
         List<Template.BlockInfo> ☃xxx = (List)this.field_204769_a.get(0);

         for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
            Template.BlockInfo ☃xxxxx = (Template.BlockInfo)☃xxx.get(☃xxxx);
            NBTTagCompound ☃xxxxxx = new NBTTagCompound();
            ☃xxxxxx.func_74782_a(
               "pos", this.func_186267_a(☃xxxxx.field_186242_a.func_177958_n(), ☃xxxxx.field_186242_a.func_177956_o(), ☃xxxxx.field_186242_a.func_177952_p())
            );
            int ☃xxxxxxx = ☃x.func_189954_a(☃xxxxx.field_186243_b);
            ☃xxxxxx.func_74768_a("state", ☃xxxxxxx);
            if (☃xxxxx.field_186244_c != null) {
               ☃xxxxxx.func_74782_a("nbt", ☃xxxxx.field_186244_c);
            }

            ☃xx.add((INBTBase)☃xxxxxx);

            for(int ☃xxxxx = 1; ☃xxxxx < this.field_204769_a.size(); ++☃xxxxx) {
               Template.BasicPalette ☃xxxxxx = (Template.BasicPalette)☃.get(☃xxxxx);
               ☃xxxxxx.func_189956_a(((Template.BlockInfo)((List)this.field_204769_a.get(☃xxxx)).get(☃xxxx)).field_186243_b, ☃xxxxxxx);
            }
         }

         ☃.func_74782_a("blocks", ☃xx);
         if (☃.size() == 1) {
            NBTTagList ☃xxxx = new NBTTagList();

            for(IBlockState ☃xxxxx : ☃x) {
               ☃xxxx.add((INBTBase)NBTUtil.func_190009_a(☃xxxxx));
            }

            ☃.func_74782_a("palette", ☃xxxx);
         } else {
            NBTTagList ☃xxxx = new NBTTagList();

            for(Template.BasicPalette ☃xxxxx : ☃) {
               NBTTagList ☃xxxxxx = new NBTTagList();

               for(IBlockState ☃xxxxxxx : ☃xxxxx) {
                  ☃xxxxxx.add((INBTBase)NBTUtil.func_190009_a(☃xxxxxxx));
               }

               ☃xxxx.add((INBTBase)☃xxxxxx);
            }

            ☃.func_74782_a("palettes", ☃xxxx);
         }
      }

      NBTTagList ☃ = new NBTTagList();

      for(Template.EntityInfo ☃x : this.field_186271_b) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         ☃xx.func_74782_a("pos", this.func_186264_a(☃x.field_186247_a.field_72450_a, ☃x.field_186247_a.field_72448_b, ☃x.field_186247_a.field_72449_c));
         ☃xx.func_74782_a(
            "blockPos", this.func_186267_a(☃x.field_186248_b.func_177958_n(), ☃x.field_186248_b.func_177956_o(), ☃x.field_186248_b.func_177952_p())
         );
         if (☃x.field_186249_c != null) {
            ☃xx.func_74782_a("nbt", ☃x.field_186249_c);
         }

         ☃.add((INBTBase)☃xx);
      }

      ☃.func_74782_a("entities", ☃);
      ☃.func_74782_a("size", this.func_186267_a(this.field_186272_c.func_177958_n(), this.field_186272_c.func_177956_o(), this.field_186272_c.func_177952_p()));
      ☃.func_74768_a("DataVersion", 1631);
      return ☃;
   }

   public void func_186256_b(NBTTagCompound var1) {
      this.field_204769_a.clear();
      this.field_186271_b.clear();
      NBTTagList ☃ = ☃.func_150295_c("size", 3);
      this.field_186272_c = new BlockPos(☃.func_186858_c(0), ☃.func_186858_c(1), ☃.func_186858_c(2));
      NBTTagList ☃x = ☃.func_150295_c("blocks", 10);
      if (☃.func_150297_b("palettes", 9)) {
         NBTTagList ☃xx = ☃.func_150295_c("palettes", 9);

         for(int ☃xxx = 0; ☃xxx < ☃xx.size(); ++☃xxx) {
            this.func_204768_a(☃xx.func_202169_e(☃xxx), ☃x);
         }
      } else {
         this.func_204768_a(☃.func_150295_c("palette", 10), ☃x);
      }

      NBTTagList ☃ = ☃.func_150295_c("entities", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
         NBTTagList ☃xxx = ☃xx.func_150295_c("pos", 6);
         Vec3d ☃xxxx = new Vec3d(☃xxx.func_150309_d(0), ☃xxx.func_150309_d(1), ☃xxx.func_150309_d(2));
         NBTTagList ☃xxxxx = ☃xx.func_150295_c("blockPos", 3);
         BlockPos ☃xxxxxx = new BlockPos(☃xxxxx.func_186858_c(0), ☃xxxxx.func_186858_c(1), ☃xxxxx.func_186858_c(2));
         if (☃xx.func_74764_b("nbt")) {
            NBTTagCompound ☃xxxxxxx = ☃xx.func_74775_l("nbt");
            this.field_186271_b.add(new Template.EntityInfo(☃xxxx, ☃xxxxxx, ☃xxxxxxx));
         }
      }
   }

   private void func_204768_a(NBTTagList var1, NBTTagList var2) {
      Template.BasicPalette ☃ = new Template.BasicPalette();
      List<Template.BlockInfo> ☃x = Lists.<Template.BlockInfo>newArrayList();

      for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
         ☃.func_189956_a(NBTUtil.func_190008_d(☃.func_150305_b(☃xx)), ☃xx);
      }

      for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
         NBTTagCompound ☃xxxx = ☃.func_150305_b(☃xx);
         NBTTagList ☃xxxxx = ☃xxxx.func_150295_c("pos", 3);
         BlockPos ☃xxxxxx = new BlockPos(☃xxxxx.func_186858_c(0), ☃xxxxx.func_186858_c(1), ☃xxxxx.func_186858_c(2));
         IBlockState ☃xxxxxxx = ☃.func_189955_a(☃xxxx.func_74762_e("state"));
         NBTTagCompound ☃xxx;
         if (☃xxxx.func_74764_b("nbt")) {
            ☃xxx = ☃xxxx.func_74775_l("nbt");
         } else {
            ☃xxx = null;
         }

         ☃x.add(new Template.BlockInfo(☃xxxxxx, ☃xxxxxxx, ☃xxx));
      }

      this.field_204769_a.add(☃x);
   }

   private NBTTagList func_186267_a(int... var1) {
      NBTTagList ☃ = new NBTTagList();

      for(int ☃x : ☃) {
         ☃.add((INBTBase)(new NBTTagInt(☃x)));
      }

      return ☃;
   }

   private NBTTagList func_186264_a(double... var1) {
      NBTTagList ☃ = new NBTTagList();

      for(double ☃x : ☃) {
         ☃.add((INBTBase)(new NBTTagDouble(☃x)));
      }

      return ☃;
   }

   static class BasicPalette implements Iterable<IBlockState> {
      public static final IBlockState field_189957_a = Blocks.field_150350_a.func_176223_P();
      private final ObjectIntIdentityMap<IBlockState> field_189958_b = new ObjectIntIdentityMap<>(16);
      private int field_189959_c;

      private BasicPalette() {
      }

      public int func_189954_a(IBlockState var1) {
         int ☃ = this.field_189958_b.func_148747_b(☃);
         if (☃ == -1) {
            ☃ = this.field_189959_c++;
            this.field_189958_b.func_148746_a(☃, ☃);
         }

         return ☃;
      }

      @Nullable
      public IBlockState func_189955_a(int var1) {
         IBlockState ☃ = this.field_189958_b.func_148745_a(☃);
         return ☃ == null ? field_189957_a : ☃;
      }

      public Iterator<IBlockState> iterator() {
         return this.field_189958_b.iterator();
      }

      public void func_189956_a(IBlockState var1, int var2) {
         this.field_189958_b.func_148746_a(☃, ☃);
      }
   }

   public static class BlockInfo {
      public final BlockPos field_186242_a;
      public final IBlockState field_186243_b;
      public final NBTTagCompound field_186244_c;

      public BlockInfo(BlockPos var1, IBlockState var2, @Nullable NBTTagCompound var3) {
         this.field_186242_a = ☃;
         this.field_186243_b = ☃;
         this.field_186244_c = ☃;
      }
   }

   public static class EntityInfo {
      public final Vec3d field_186247_a;
      public final BlockPos field_186248_b;
      public final NBTTagCompound field_186249_c;

      public EntityInfo(Vec3d var1, BlockPos var2, NBTTagCompound var3) {
         this.field_186247_a = ☃;
         this.field_186248_b = ☃;
         this.field_186249_c = ☃;
      }
   }
}
