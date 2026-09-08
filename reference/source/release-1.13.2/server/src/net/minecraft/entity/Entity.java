package net.minecraft.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.INameable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity implements INameable, ICommandSource {
   protected static final Logger field_184243_a = LogManager.getLogger();
   private static final List<ItemStack> field_190535_b = Collections.emptyList();
   private static final AxisAlignedBB field_174836_a = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static double field_70155_l = 1.0;
   private static int field_70152_a;
   private final EntityType<?> field_200606_g;
   private int field_145783_c;
   public boolean field_70156_m;
   private final List<Entity> field_184244_h;
   protected int field_184245_j;
   private Entity field_184239_as;
   public boolean field_98038_p;
   public World field_70170_p;
   public double field_70169_q;
   public double field_70167_r;
   public double field_70166_s;
   public double field_70165_t;
   public double field_70163_u;
   public double field_70161_v;
   public double field_70159_w;
   public double field_70181_x;
   public double field_70179_y;
   public float field_70177_z;
   public float field_70125_A;
   public float field_70126_B;
   public float field_70127_C;
   private AxisAlignedBB field_70121_D;
   public boolean field_70122_E;
   public boolean field_70123_F;
   public boolean field_70124_G;
   public boolean field_70132_H;
   public boolean field_70133_I;
   protected boolean field_70134_J;
   private boolean field_174835_g;
   public boolean field_70128_L;
   public float field_70130_N;
   public float field_70131_O;
   public float field_70141_P;
   public float field_70140_Q;
   public float field_82151_R;
   public float field_70143_R;
   private float field_70150_b;
   private float field_191959_ay;
   public double field_70142_S;
   public double field_70137_T;
   public double field_70136_U;
   public float field_70138_W;
   public boolean field_70145_X;
   public float field_70144_Y;
   protected Random field_70146_Z;
   public int field_70173_aa;
   private int field_190534_ay;
   protected boolean field_70171_ac;
   protected double field_211517_W;
   protected boolean field_205013_W;
   public int field_70172_ad;
   protected boolean field_70148_d;
   protected boolean field_70178_ae;
   protected EntityDataManager field_70180_af;
   protected static final DataParameter<Byte> field_184240_ax = EntityDataManager.func_187226_a(Entity.class, DataSerializers.field_187191_a);
   private static final DataParameter<Integer> field_184241_ay = EntityDataManager.func_187226_a(Entity.class, DataSerializers.field_187192_b);
   private static final DataParameter<Optional<ITextComponent>> field_184242_az = EntityDataManager.func_187226_a(Entity.class, DataSerializers.field_200544_f);
   private static final DataParameter<Boolean> field_184233_aA = EntityDataManager.func_187226_a(Entity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_184234_aB = EntityDataManager.func_187226_a(Entity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_189655_aD = EntityDataManager.func_187226_a(Entity.class, DataSerializers.field_187198_h);
   public boolean field_70175_ag;
   public int field_70176_ah;
   public int field_70162_ai;
   public int field_70164_aj;
   public boolean field_70158_ak;
   public boolean field_70160_al;
   public int field_71088_bW;
   protected boolean field_71087_bX;
   protected int field_82153_h;
   public DimensionType field_71093_bK;
   protected BlockPos field_181016_an;
   protected Vec3d field_181017_ao;
   protected EnumFacing field_181018_ap;
   private boolean field_83001_bt;
   protected UUID field_96093_i;
   protected String field_189513_ar;
   protected boolean field_184238_ar;
   private final Set<String> field_184236_aF;
   private boolean field_184237_aG;
   private final double[] field_191505_aI;
   private long field_191506_aJ;

   public Entity(EntityType<?> var1, World var2) {
      this.field_145783_c = field_70152_a++;
      this.field_184244_h = Lists.<Entity>newArrayList();
      this.field_70121_D = field_174836_a;
      this.field_70130_N = 0.6F;
      this.field_70131_O = 1.8F;
      this.field_70150_b = 1.0F;
      this.field_191959_ay = 1.0F;
      this.field_70146_Z = new Random();
      this.field_190534_ay = -this.func_190531_bD();
      this.field_70148_d = true;
      this.field_96093_i = MathHelper.func_180182_a(this.field_70146_Z);
      this.field_189513_ar = this.field_96093_i.toString();
      this.field_184236_aF = Sets.newHashSet();
      this.field_191505_aI = new double[]{0.0, 0.0, 0.0};
      this.field_200606_g = ☃;
      this.field_70170_p = ☃;
      this.func_70107_b(0.0, 0.0, 0.0);
      if (☃ != null) {
         this.field_71093_bK = ☃.field_73011_w.func_186058_p();
      }

      this.field_70180_af = new EntityDataManager(this);
      this.field_70180_af.func_187214_a(field_184240_ax, (byte)0);
      this.field_70180_af.func_187214_a(field_184241_ay, this.func_205010_bg());
      this.field_70180_af.func_187214_a(field_184233_aA, false);
      this.field_70180_af.func_187214_a(field_184242_az, Optional.empty());
      this.field_70180_af.func_187214_a(field_184234_aB, false);
      this.field_70180_af.func_187214_a(field_189655_aD, false);
      this.func_70088_a();
   }

   public EntityType<?> func_200600_R() {
      return this.field_200606_g;
   }

   public int func_145782_y() {
      return this.field_145783_c;
   }

   public void func_145769_d(int var1) {
      this.field_145783_c = ☃;
   }

   public Set<String> func_184216_O() {
      return this.field_184236_aF;
   }

   public boolean func_184211_a(String var1) {
      return this.field_184236_aF.size() >= 1024 ? false : this.field_184236_aF.add(☃);
   }

   public boolean func_184197_b(String var1) {
      return this.field_184236_aF.remove(☃);
   }

   public void func_174812_G() {
      this.func_70106_y();
   }

   protected abstract void func_70088_a();

   public EntityDataManager func_184212_Q() {
      return this.field_70180_af;
   }

   public boolean equals(Object var1) {
      if (☃ instanceof Entity) {
         return ((Entity)☃).field_145783_c == this.field_145783_c;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_145783_c;
   }

   public void func_70106_y() {
      this.field_70128_L = true;
   }

   public void func_184174_b(boolean var1) {
   }

   protected void func_70105_a(float var1, float var2) {
      if (☃ != this.field_70130_N || ☃ != this.field_70131_O) {
         float ☃ = this.field_70130_N;
         this.field_70130_N = ☃;
         this.field_70131_O = ☃;
         if (this.field_70130_N < ☃) {
            double ☃x = (double)☃ / 2.0;
            this.func_174826_a(
               new AxisAlignedBB(
                  this.field_70165_t - ☃x,
                  this.field_70163_u,
                  this.field_70161_v - ☃x,
                  this.field_70165_t + ☃x,
                  this.field_70163_u + (double)this.field_70131_O,
                  this.field_70161_v + ☃x
               )
            );
            return;
         }

         AxisAlignedBB ☃ = this.func_174813_aQ();
         this.func_174826_a(
            new AxisAlignedBB(
               ☃.field_72340_a,
               ☃.field_72338_b,
               ☃.field_72339_c,
               ☃.field_72340_a + (double)this.field_70130_N,
               ☃.field_72338_b + (double)this.field_70131_O,
               ☃.field_72339_c + (double)this.field_70130_N
            )
         );
         if (this.field_70130_N > ☃ && !this.field_70148_d && !this.field_70170_p.field_72995_K) {
            this.func_70091_d(MoverType.SELF, (double)(☃ - this.field_70130_N), 0.0, (double)(☃ - this.field_70130_N));
         }
      }
   }

   protected void func_70101_b(float var1, float var2) {
      this.field_70177_z = ☃ % 360.0F;
      this.field_70125_A = ☃ % 360.0F;
   }

   public void func_70107_b(double var1, double var3, double var5) {
      this.field_70165_t = ☃;
      this.field_70163_u = ☃;
      this.field_70161_v = ☃;
      float ☃ = this.field_70130_N / 2.0F;
      float ☃x = this.field_70131_O;
      this.func_174826_a(new AxisAlignedBB(☃ - (double)☃, ☃, ☃ - (double)☃, ☃ + (double)☃, ☃ + (double)☃x, ☃ + (double)☃));
   }

   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(6, this.func_184202_aL());
      }

      this.func_70030_z();
   }

   public void func_70030_z() {
      this.field_70170_p.field_72984_F.func_76320_a("entityBaseTick");
      if (this.func_184218_aH() && this.func_184187_bx().field_70128_L) {
         this.func_184210_p();
      }

      if (this.field_184245_j > 0) {
         --this.field_184245_j;
      }

      this.field_70141_P = this.field_70140_Q;
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70127_C = this.field_70125_A;
      this.field_70126_B = this.field_70177_z;
      if (!this.field_70170_p.field_72995_K && this.field_70170_p instanceof WorldServer) {
         this.field_70170_p.field_72984_F.func_76320_a("portal");
         if (this.field_71087_bX) {
            MinecraftServer ☃ = this.field_70170_p.func_73046_m();
            if (☃.func_71255_r()) {
               if (!this.func_184218_aH()) {
                  int ☃x = this.func_82145_z();
                  if (this.field_82153_h++ >= ☃x) {
                     this.field_82153_h = ☃x;
                     this.field_71088_bW = this.func_82147_ab();
                     DimensionType ☃xx;
                     if (this.field_70170_p.field_73011_w.func_186058_p() == DimensionType.NETHER) {
                        ☃xx = DimensionType.OVERWORLD;
                     } else {
                        ☃xx = DimensionType.NETHER;
                     }

                     this.func_212321_a(☃xx);
                  }
               }

               this.field_71087_bX = false;
            }
         } else {
            if (this.field_82153_h > 0) {
               this.field_82153_h -= 4;
            }

            if (this.field_82153_h < 0) {
               this.field_82153_h = 0;
            }
         }

         this.func_184173_H();
         this.field_70170_p.field_72984_F.func_76319_b();
      }

      this.func_174830_Y();
      this.func_205011_p();
      if (this.field_70170_p.field_72995_K) {
         this.func_70066_B();
      } else if (this.field_190534_ay > 0) {
         if (this.field_70178_ae) {
            this.field_190534_ay -= 4;
            if (this.field_190534_ay < 0) {
               this.func_70066_B();
            }
         } else {
            if (this.field_190534_ay % 20 == 0) {
               this.func_70097_a(DamageSource.field_76370_b, 1.0F);
            }

            --this.field_190534_ay;
         }
      }

      if (this.func_180799_ab()) {
         this.func_70044_A();
         this.field_70143_R *= 0.5F;
      }

      if (this.field_70163_u < -64.0) {
         this.func_70076_C();
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(0, this.field_190534_ay > 0);
      }

      this.field_70148_d = false;
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_184173_H() {
      if (this.field_71088_bW > 0) {
         --this.field_71088_bW;
      }
   }

   public int func_82145_z() {
      return 1;
   }

   protected void func_70044_A() {
      if (!this.field_70178_ae) {
         this.func_70015_d(15);
         this.func_70097_a(DamageSource.field_76371_c, 4.0F);
      }
   }

   public void func_70015_d(int var1) {
      int ☃ = ☃ * 20;
      if (this instanceof EntityLivingBase) {
         ☃ = EnchantmentProtection.func_92093_a((EntityLivingBase)this, ☃);
      }

      if (this.field_190534_ay < ☃) {
         this.field_190534_ay = ☃;
      }
   }

   public void func_70066_B() {
      this.field_190534_ay = 0;
   }

   protected void func_70076_C() {
      this.func_70106_y();
   }

   public boolean func_70038_c(double var1, double var3, double var5) {
      return this.func_174809_b(this.func_174813_aQ().func_72317_d(☃, ☃, ☃));
   }

   private boolean func_174809_b(AxisAlignedBB var1) {
      return this.field_70170_p.func_195586_b(this, ☃) && !this.field_70170_p.func_72953_d(☃);
   }

   public void func_70091_d(MoverType var1, double var2, double var4, double var6) {
      if (this.field_70145_X) {
         this.func_174826_a(this.func_174813_aQ().func_72317_d(☃, ☃, ☃));
         this.func_174829_m();
      } else {
         if (☃ == MoverType.PISTON) {
            long ☃ = this.field_70170_p.func_82737_E();
            if (☃ != this.field_191506_aJ) {
               Arrays.fill(this.field_191505_aI, 0.0);
               this.field_191506_aJ = ☃;
            }

            if (☃ != 0.0) {
               int ☃ = EnumFacing.Axis.X.ordinal();
               double ☃x = MathHelper.func_151237_a(☃ + this.field_191505_aI[☃], -0.51, 0.51);
               ☃ = ☃x - this.field_191505_aI[☃];
               this.field_191505_aI[☃] = ☃x;
               if (Math.abs(☃) <= 1.0E-5F) {
                  return;
               }
            } else if (☃ != 0.0) {
               int ☃ = EnumFacing.Axis.Y.ordinal();
               double ☃x = MathHelper.func_151237_a(☃ + this.field_191505_aI[☃], -0.51, 0.51);
               ☃ = ☃x - this.field_191505_aI[☃];
               this.field_191505_aI[☃] = ☃x;
               if (Math.abs(☃) <= 1.0E-5F) {
                  return;
               }
            } else {
               if (☃ == 0.0) {
                  return;
               }

               int ☃ = EnumFacing.Axis.Z.ordinal();
               double ☃x = MathHelper.func_151237_a(☃ + this.field_191505_aI[☃], -0.51, 0.51);
               ☃ = ☃x - this.field_191505_aI[☃];
               this.field_191505_aI[☃] = ☃x;
               if (Math.abs(☃) <= 1.0E-5F) {
                  return;
               }
            }
         }

         this.field_70170_p.field_72984_F.func_76320_a("move");
         double ☃ = this.field_70165_t;
         double ☃x = this.field_70163_u;
         double ☃xx = this.field_70161_v;
         if (this.field_70134_J) {
            this.field_70134_J = false;
            ☃ *= 0.25;
            ☃ *= 0.05F;
            ☃ *= 0.25;
            this.field_70159_w = 0.0;
            this.field_70181_x = 0.0;
            this.field_70179_y = 0.0;
         }

         double ☃ = ☃;
         double ☃x = ☃;
         double ☃xx = ☃;
         if ((☃ == MoverType.SELF || ☃ == MoverType.PLAYER) && this.field_70122_E && this.func_70093_af() && this instanceof EntityPlayer) {
            for(double ☃xxx = 0.05;
               ☃ != 0.0 && this.field_70170_p.func_195586_b(this, this.func_174813_aQ().func_72317_d(☃, (double)(-this.field_70138_W), 0.0));
               ☃ = ☃
            ) {
               if (☃ < 0.05 && ☃ >= -0.05) {
                  ☃ = 0.0;
               } else if (☃ > 0.0) {
                  ☃ -= 0.05;
               } else {
                  ☃ += 0.05;
               }
            }

            for(; ☃ != 0.0 && this.field_70170_p.func_195586_b(this, this.func_174813_aQ().func_72317_d(0.0, (double)(-this.field_70138_W), ☃)); ☃xx = ☃) {
               if (☃ < 0.05 && ☃ >= -0.05) {
                  ☃ = 0.0;
               } else if (☃ > 0.0) {
                  ☃ -= 0.05;
               } else {
                  ☃ += 0.05;
               }
            }

            for(;
               ☃ != 0.0 && ☃ != 0.0 && this.field_70170_p.func_195586_b(this, this.func_174813_aQ().func_72317_d(☃, (double)(-this.field_70138_W), ☃));
               ☃xx = ☃
            ) {
               if (☃ < 0.05 && ☃ >= -0.05) {
                  ☃ = 0.0;
               } else if (☃ > 0.0) {
                  ☃ -= 0.05;
               } else {
                  ☃ += 0.05;
               }

               ☃ = ☃;
               if (☃ < 0.05 && ☃ >= -0.05) {
                  ☃ = 0.0;
               } else if (☃ > 0.0) {
                  ☃ -= 0.05;
               } else {
                  ☃ += 0.05;
               }
            }
         }

         AxisAlignedBB ☃ = this.func_174813_aQ();
         if (☃ != 0.0 || ☃ != 0.0 || ☃ != 0.0) {
            ReuseableStream<VoxelShape> ☃x = new ReuseableStream<>(this.field_70170_p.func_199406_a(this, this.func_174813_aQ(), ☃, ☃, ☃));
            if (☃ != 0.0) {
               ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.Y, this.func_174813_aQ(), ☃x.func_212761_a(), ☃);
               this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0, ☃, 0.0));
            }

            if (☃ != 0.0) {
               ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.X, this.func_174813_aQ(), ☃x.func_212761_a(), ☃);
               if (☃ != 0.0) {
                  this.func_174826_a(this.func_174813_aQ().func_72317_d(☃, 0.0, 0.0));
               }
            }

            if (☃ != 0.0) {
               ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.Z, this.func_174813_aQ(), ☃x.func_212761_a(), ☃);
               if (☃ != 0.0) {
                  this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0, 0.0, ☃));
               }
            }
         }

         boolean ☃ = this.field_70122_E || ☃x != ☃ && ☃x < 0.0;
         if (this.field_70138_W > 0.0F && ☃ && (☃ != ☃ || ☃xx != ☃)) {
            double ☃x = ☃;
            double ☃xx = ☃;
            double ☃xxx = ☃;
            AxisAlignedBB ☃xxxx = this.func_174813_aQ();
            this.func_174826_a(☃);
            ☃ = ☃;
            ☃ = (double)this.field_70138_W;
            ☃ = ☃xx;
            if (☃ != 0.0 || ☃ != 0.0 || ☃xx != 0.0) {
               ReuseableStream<VoxelShape> ☃xxxxx = new ReuseableStream<>(this.field_70170_p.func_199406_a(this, this.func_174813_aQ(), ☃, ☃, ☃xx));
               AxisAlignedBB ☃xxxxxx = this.func_174813_aQ();
               AxisAlignedBB ☃xxxxxxx = ☃xxxxxx.func_72321_a(☃, 0.0, ☃xx);
               double ☃xxxxxxxx = VoxelShapes.func_212437_a(EnumFacing.Axis.Y, ☃xxxxxxx, ☃xxxxx.func_212761_a(), ☃);
               if (☃xxxxxxxx != 0.0) {
                  ☃xxxxxx = ☃xxxxxx.func_72317_d(0.0, ☃xxxxxxxx, 0.0);
               }

               double ☃xxxxx = VoxelShapes.func_212437_a(EnumFacing.Axis.X, ☃xxxxxx, ☃xxxxx.func_212761_a(), ☃);
               if (☃xxxxx != 0.0) {
                  ☃xxxxxx = ☃xxxxxx.func_72317_d(☃xxxxx, 0.0, 0.0);
               }

               double ☃xxxxx = VoxelShapes.func_212437_a(EnumFacing.Axis.Z, ☃xxxxxx, ☃xxxxx.func_212761_a(), ☃xx);
               if (☃xxxxx != 0.0) {
                  ☃xxxxxx = ☃xxxxxx.func_72317_d(0.0, 0.0, ☃xxxxx);
               }

               AxisAlignedBB ☃xxxxx = this.func_174813_aQ();
               double ☃xxxxxx = VoxelShapes.func_212437_a(EnumFacing.Axis.Y, ☃xxxxx, ☃xxxxx.func_212761_a(), ☃);
               if (☃xxxxxx != 0.0) {
                  ☃xxxxx = ☃xxxxx.func_72317_d(0.0, ☃xxxxxx, 0.0);
               }

               double ☃xxxxx = VoxelShapes.func_212437_a(EnumFacing.Axis.X, ☃xxxxx, ☃xxxxx.func_212761_a(), ☃);
               if (☃xxxxx != 0.0) {
                  ☃xxxxx = ☃xxxxx.func_72317_d(☃xxxxx, 0.0, 0.0);
               }

               double ☃xxxxx = VoxelShapes.func_212437_a(EnumFacing.Axis.Z, ☃xxxxx, ☃xxxxx.func_212761_a(), ☃xx);
               if (☃xxxxx != 0.0) {
                  ☃xxxxx = ☃xxxxx.func_72317_d(0.0, 0.0, ☃xxxxx);
               }

               double ☃xxxxx = ☃xxxxx * ☃xxxxx + ☃xxxxx * ☃xxxxx;
               double ☃xxxxxx = ☃xxxxx * ☃xxxxx + ☃xxxxx * ☃xxxxx;
               if (☃xxxxx > ☃xxxxxx) {
                  ☃ = ☃xxxxx;
                  ☃ = ☃xxxxx;
                  ☃ = -☃xxxxxxxx;
                  this.func_174826_a(☃xxxxxx);
               } else {
                  ☃ = ☃xxxxx;
                  ☃ = ☃xxxxx;
                  ☃ = -☃xxxxxx;
                  this.func_174826_a(☃xxxxx);
               }

               ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.Y, this.func_174813_aQ(), ☃xxxxx.func_212761_a(), ☃);
               if (☃ != 0.0) {
                  this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0, ☃, 0.0));
               }
            }

            if (☃x * ☃x + ☃xxx * ☃xxx >= ☃ * ☃ + ☃ * ☃) {
               ☃ = ☃x;
               ☃ = ☃xx;
               ☃ = ☃xxx;
               this.func_174826_a(☃xxxx);
            }
         }

         this.field_70170_p.field_72984_F.func_76319_b();
         this.field_70170_p.field_72984_F.func_76320_a("rest");
         this.func_174829_m();
         this.field_70123_F = ☃ != ☃ || ☃xx != ☃;
         this.field_70124_G = ☃x != ☃;
         this.field_70122_E = this.field_70124_G && ☃x < 0.0;
         this.field_70132_H = this.field_70123_F || this.field_70124_G;
         int ☃ = MathHelper.func_76128_c(this.field_70165_t);
         int ☃x = MathHelper.func_76128_c(this.field_70163_u - 0.2F);
         int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
         BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
         IBlockState ☃xxxx = this.field_70170_p.func_180495_p(☃xxx);
         if (☃xxxx.func_196958_f()) {
            BlockPos ☃xxxxx = ☃xxx.func_177977_b();
            IBlockState ☃xxxxxx = this.field_70170_p.func_180495_p(☃xxxxx);
            Block ☃xxxxxxx = ☃xxxxxx.func_177230_c();
            if (☃xxxxxxx instanceof BlockFence || ☃xxxxxxx instanceof BlockWall || ☃xxxxxxx instanceof BlockFenceGate) {
               ☃xxxx = ☃xxxxxx;
               ☃xxx = ☃xxxxx;
            }
         }

         this.func_184231_a(☃, this.field_70122_E, ☃xxxx, ☃xxx);
         if (☃ != ☃) {
            this.field_70159_w = 0.0;
         }

         if (☃xx != ☃) {
            this.field_70179_y = 0.0;
         }

         Block ☃ = ☃xxxx.func_177230_c();
         if (☃x != ☃) {
            ☃.func_176216_a(this.field_70170_p, this);
         }

         if (this.func_70041_e_() && (!this.field_70122_E || !this.func_70093_af() || !(this instanceof EntityPlayer)) && !this.func_184218_aH()) {
            double ☃ = this.field_70165_t - ☃;
            double ☃x = this.field_70163_u - ☃x;
            double ☃xx = this.field_70161_v - ☃xx;
            if (☃ != Blocks.field_150468_ap) {
               ☃x = 0.0;
            }

            if (☃ != null && this.field_70122_E) {
               ☃.func_176199_a(this.field_70170_p, ☃xxx, this);
            }

            this.field_70140_Q = (float)((double)this.field_70140_Q + (double)MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx) * 0.6);
            this.field_82151_R = (float)((double)this.field_82151_R + (double)MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx) * 0.6);
            if (this.field_82151_R > this.field_70150_b && !☃xxxx.func_196958_f()) {
               this.field_70150_b = this.func_203009_ad();
               if (this.func_70090_H()) {
                  Entity ☃ = this.func_184207_aI() && this.func_184179_bs() != null ? this.func_184179_bs() : this;
                  float ☃x = ☃ == this ? 0.35F : 0.4F;
                  float ☃xx = MathHelper.func_76133_a(
                        ☃.field_70159_w * ☃.field_70159_w * 0.2F + ☃.field_70181_x * ☃.field_70181_x + ☃.field_70179_y * ☃.field_70179_y * 0.2F
                     )
                     * ☃x;
                  if (☃xx > 1.0F) {
                     ☃xx = 1.0F;
                  }

                  this.func_203006_d(☃xx);
               } else {
                  this.func_180429_a(☃xxx, ☃xxxx);
               }
            } else if (this.field_82151_R > this.field_191959_ay && this.func_191957_ae() && ☃xxxx.func_196958_f()) {
               this.field_191959_ay = this.func_191954_d(this.field_82151_R);
            }
         }

         try {
            this.func_145775_I();
         } catch (Throwable var49) {
            CrashReport ☃ = CrashReport.func_85055_a(var49, "Checking entity block collision");
            CrashReportCategory ☃x = ☃.func_85058_a("Entity being checked for collision");
            this.func_85029_a(☃x);
            throw new ReportedException(☃);
         }

         boolean ☃ = this.func_203008_ap();
         if (this.field_70170_p.func_147470_e(this.func_174813_aQ().func_186664_h(0.001))) {
            if (!☃) {
               ++this.field_190534_ay;
               if (this.field_190534_ay == 0) {
                  this.func_70015_d(8);
               }
            }

            this.func_70081_e(1);
         } else if (this.field_190534_ay <= 0) {
            this.field_190534_ay = -this.func_190531_bD();
         }

         if (☃ && this.func_70027_ad()) {
            this.func_184185_a(SoundEvents.field_187541_bC, 0.7F, 1.6F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
            this.field_190534_ay = -this.func_190531_bD();
         }

         this.field_70170_p.field_72984_F.func_76319_b();
      }
   }

   protected float func_203009_ad() {
      return (float)((int)this.field_82151_R + 1);
   }

   public void func_174829_m() {
      AxisAlignedBB ☃ = this.func_174813_aQ();
      this.field_70165_t = (☃.field_72340_a + ☃.field_72336_d) / 2.0;
      this.field_70163_u = ☃.field_72338_b;
      this.field_70161_v = (☃.field_72339_c + ☃.field_72334_f) / 2.0;
   }

   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_187549_bG;
   }

   protected SoundEvent func_184181_aa() {
      return SoundEvents.field_187547_bF;
   }

   protected SoundEvent func_204208_ah() {
      return SoundEvents.field_187547_bF;
   }

   protected void func_145775_I() {
      AxisAlignedBB ☃ = this.func_174813_aQ();

      try (
         BlockPos.PooledMutableBlockPos ☃x = BlockPos.PooledMutableBlockPos.func_185345_c(
            ☃.field_72340_a + 0.001, ☃.field_72338_b + 0.001, ☃.field_72339_c + 0.001
         );
         BlockPos.PooledMutableBlockPos ☃xx = BlockPos.PooledMutableBlockPos.func_185345_c(
            ☃.field_72336_d - 0.001, ☃.field_72337_e - 0.001, ☃.field_72334_f - 0.001
         );
         BlockPos.PooledMutableBlockPos ☃xxx = BlockPos.PooledMutableBlockPos.func_185346_s();
      ) {
         if (this.field_70170_p.func_175707_a(☃x, ☃xx)) {
            for(int ☃xxxx = ☃x.func_177958_n(); ☃xxxx <= ☃xx.func_177958_n(); ++☃xxxx) {
               for(int ☃xxxxx = ☃x.func_177956_o(); ☃xxxxx <= ☃xx.func_177956_o(); ++☃xxxxx) {
                  for(int ☃xxxxxx = ☃x.func_177952_p(); ☃xxxxxx <= ☃xx.func_177952_p(); ++☃xxxxxx) {
                     ☃xxx.func_181079_c(☃xxxx, ☃xxxxx, ☃xxxxxx);
                     IBlockState ☃xxxxxxx = this.field_70170_p.func_180495_p(☃xxx);

                     try {
                        ☃xxxxxxx.func_196950_a(this.field_70170_p, ☃xxx, this);
                        this.func_191955_a(☃xxxxxxx);
                     } catch (Throwable var60) {
                        CrashReport ☃xxxxxxxx = CrashReport.func_85055_a(var60, "Colliding entity with block");
                        CrashReportCategory ☃xxxxxxxxx = ☃xxxxxxxx.func_85058_a("Block being collided with");
                        CrashReportCategory.func_175750_a(☃xxxxxxxxx, ☃xxx, ☃xxxxxxx);
                        throw new ReportedException(☃xxxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   protected void func_191955_a(IBlockState var1) {
   }

   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      if (!☃.func_185904_a().func_76224_d()) {
         SoundType ☃ = this.field_70170_p.func_180495_p(☃.func_177984_a()).func_177230_c() == Blocks.field_150433_aE
            ? Blocks.field_150433_aE.func_185467_w()
            : ☃.func_177230_c().func_185467_w();
         this.func_184185_a(☃.func_185844_d(), ☃.func_185843_a() * 0.15F, ☃.func_185847_b());
      }
   }

   protected void func_203006_d(float var1) {
      this.func_184185_a(this.func_184184_Z(), ☃, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
   }

   protected float func_191954_d(float var1) {
      return 0.0F;
   }

   protected boolean func_191957_ae() {
      return false;
   }

   public void func_184185_a(SoundEvent var1, float var2, float var3) {
      if (!this.func_174814_R()) {
         this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃, this.func_184176_by(), ☃, ☃);
      }
   }

   public boolean func_174814_R() {
      return this.field_70180_af.func_187225_a(field_184234_aB);
   }

   public void func_174810_b(boolean var1) {
      this.field_70180_af.func_187227_b(field_184234_aB, ☃);
   }

   public boolean func_189652_ae() {
      return this.field_70180_af.func_187225_a(field_189655_aD);
   }

   public void func_189654_d(boolean var1) {
      this.field_70180_af.func_187227_b(field_189655_aD, ☃);
   }

   protected boolean func_70041_e_() {
      return true;
   }

   protected void func_184231_a(double var1, boolean var3, IBlockState var4, BlockPos var5) {
      if (☃) {
         if (this.field_70143_R > 0.0F) {
            ☃.func_177230_c().func_180658_a(this.field_70170_p, ☃, this, this.field_70143_R);
         }

         this.field_70143_R = 0.0F;
      } else if (☃ < 0.0) {
         this.field_70143_R = (float)((double)this.field_70143_R - ☃);
      }
   }

   @Nullable
   public AxisAlignedBB func_70046_E() {
      return null;
   }

   protected void func_70081_e(int var1) {
      if (!this.field_70178_ae) {
         this.func_70097_a(DamageSource.field_76372_a, (float)☃);
      }
   }

   public final boolean func_70045_F() {
      return this.field_70178_ae;
   }

   public void func_180430_e(float var1, float var2) {
      if (this.func_184207_aI()) {
         for(Entity ☃ : this.func_184188_bt()) {
            ☃.func_180430_e(☃, ☃);
         }
      }
   }

   public boolean func_70090_H() {
      return this.field_70171_ac;
   }

   private boolean func_209511_p() {
      boolean var3;
      try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_209907_b(this)) {
         var3 = this.field_70170_p.func_175727_C(☃)
            || this.field_70170_p.func_175727_C(☃.func_189532_c(this.field_70165_t, this.field_70163_u + (double)this.field_70131_O, this.field_70161_v));
      }

      return var3;
   }

   private boolean func_209512_q() {
      return this.field_70170_p.func_180495_p(new BlockPos(this)).func_177230_c() == Blocks.field_203203_C;
   }

   public boolean func_70026_G() {
      return this.func_70090_H() || this.func_209511_p();
   }

   public boolean func_203008_ap() {
      return this.func_70090_H() || this.func_209511_p() || this.func_209512_q();
   }

   public boolean func_203005_aq() {
      return this.func_70090_H() || this.func_209512_q();
   }

   public boolean func_204231_K() {
      return this.field_205013_W && this.func_70090_H();
   }

   private void func_205011_p() {
      this.func_70072_I();
      this.func_205012_q();
      this.func_205343_av();
   }

   public void func_205343_av() {
      if (this.func_203007_ba()) {
         this.func_204711_a(this.func_70051_ag() && this.func_70090_H() && !this.func_184218_aH());
      } else {
         this.func_204711_a(this.func_70051_ag() && this.func_204231_K() && !this.func_184218_aH());
      }
   }

   public boolean func_70072_I() {
      if (this.func_184187_bx() instanceof EntityBoat) {
         this.field_70171_ac = false;
      } else if (this.func_210500_b(FluidTags.field_206959_a)) {
         if (!this.field_70171_ac && !this.field_70148_d) {
            this.func_71061_d_();
         }

         this.field_70143_R = 0.0F;
         this.field_70171_ac = true;
         this.func_70066_B();
      } else {
         this.field_70171_ac = false;
      }

      return this.field_70171_ac;
   }

   private void func_205012_q() {
      this.field_205013_W = this.func_208600_a(FluidTags.field_206959_a);
   }

   protected void func_71061_d_() {
      Entity ☃ = this.func_184207_aI() && this.func_184179_bs() != null ? this.func_184179_bs() : this;
      float ☃x = ☃ == this ? 0.2F : 0.9F;
      float ☃xx = MathHelper.func_76133_a(
            ☃.field_70159_w * ☃.field_70159_w * 0.2F + ☃.field_70181_x * ☃.field_70181_x + ☃.field_70179_y * ☃.field_70179_y * 0.2F
         )
         * ☃x;
      if (☃xx > 1.0F) {
         ☃xx = 1.0F;
      }

      if ((double)☃xx < 0.25) {
         this.func_184185_a(this.func_184181_aa(), ☃xx, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
      } else {
         this.func_184185_a(this.func_204208_ah(), ☃xx, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
      }

      float ☃ = (float)MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);

      for(int ☃x = 0; (float)☃x < 1.0F + this.field_70130_N * 20.0F; ++☃x) {
         float ☃xx = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         float ☃xxx = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         this.field_70170_p
            .func_195594_a(
               Particles.field_197612_e,
               this.field_70165_t + (double)☃xx,
               (double)(☃ + 1.0F),
               this.field_70161_v + (double)☃xxx,
               this.field_70159_w,
               this.field_70181_x - (double)(this.field_70146_Z.nextFloat() * 0.2F),
               this.field_70179_y
            );
      }

      for(int ☃x = 0; (float)☃x < 1.0F + this.field_70130_N * 20.0F; ++☃x) {
         float ☃xx = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         float ☃xxx = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         this.field_70170_p
            .func_195594_a(
               Particles.field_197606_Q,
               this.field_70165_t + (double)☃xx,
               (double)(☃ + 1.0F),
               this.field_70161_v + (double)☃xxx,
               this.field_70159_w,
               this.field_70181_x,
               this.field_70179_y
            );
      }
   }

   public void func_174830_Y() {
      if (this.func_70051_ag() && !this.func_70090_H()) {
         this.func_174808_Z();
      }
   }

   protected void func_174808_Z() {
      int ☃ = MathHelper.func_76128_c(this.field_70165_t);
      int ☃x = MathHelper.func_76128_c(this.field_70163_u - 0.2F);
      int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
      IBlockState ☃xxxx = this.field_70170_p.func_180495_p(☃xxx);
      if (☃xxxx.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
         this.field_70170_p
            .func_195594_a(
               new BlockParticleData(Particles.field_197611_d, ☃xxxx),
               this.field_70165_t + ((double)this.field_70146_Z.nextFloat() - 0.5) * (double)this.field_70130_N,
               this.func_174813_aQ().field_72338_b + 0.1,
               this.field_70161_v + ((double)this.field_70146_Z.nextFloat() - 0.5) * (double)this.field_70130_N,
               -this.field_70159_w * 4.0,
               1.5,
               -this.field_70179_y * 4.0
            );
      }
   }

   public boolean func_208600_a(Tag<Fluid> var1) {
      if (this.func_184187_bx() instanceof EntityBoat) {
         return false;
      } else {
         double ☃ = this.field_70163_u + (double)this.func_70047_e();
         BlockPos ☃x = new BlockPos(this.field_70165_t, ☃, this.field_70161_v);
         IFluidState ☃xx = this.field_70170_p.func_204610_c(☃x);
         return ☃xx.func_206884_a(☃) && ☃ < (double)((float)☃x.func_177956_o() + ☃xx.func_206885_f() + 0.11111111F);
      }
   }

   public boolean func_180799_ab() {
      return this.field_70170_p.func_72875_a(this.func_174813_aQ().func_211539_f(0.1F, 0.4F, 0.1F), Material.field_151587_i);
   }

   public void func_191958_b(float var1, float var2, float var3, float var4) {
      float ☃ = ☃ * ☃ + ☃ * ☃ + ☃ * ☃;
      if (!(☃ < 1.0E-4F)) {
         ☃ = MathHelper.func_76129_c(☃);
         if (☃ < 1.0F) {
            ☃ = 1.0F;
         }

         ☃ = ☃ / ☃;
         ☃ *= ☃;
         ☃ *= ☃;
         ☃ *= ☃;
         float ☃x = MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0));
         float ☃xx = MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0));
         this.field_70159_w += (double)(☃ * ☃xx - ☃ * ☃x);
         this.field_70181_x += (double)☃;
         this.field_70179_y += (double)(☃ * ☃xx + ☃ * ☃x);
      }
   }

   public float func_70013_c() {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(this.field_70165_t), 0, MathHelper.func_76128_c(this.field_70161_v));
      if (this.field_70170_p.func_175667_e(☃)) {
         ☃.func_185336_p(MathHelper.func_76128_c(this.field_70163_u + (double)this.func_70047_e()));
         return this.field_70170_p.func_205052_D(☃);
      } else {
         return 0.0F;
      }
   }

   public void func_70029_a(World var1) {
      this.field_70170_p = ☃;
   }

   public void func_70080_a(double var1, double var3, double var5, float var7, float var8) {
      this.field_70165_t = MathHelper.func_151237_a(☃, -3.0E7, 3.0E7);
      this.field_70163_u = ☃;
      this.field_70161_v = MathHelper.func_151237_a(☃, -3.0E7, 3.0E7);
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      ☃ = MathHelper.func_76131_a(☃, -90.0F, 90.0F);
      this.field_70177_z = ☃;
      this.field_70125_A = ☃;
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      double ☃ = (double)(this.field_70126_B - ☃);
      if (☃ < -180.0) {
         this.field_70126_B += 360.0F;
      }

      if (☃ >= 180.0) {
         this.field_70126_B -= 360.0F;
      }

      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.func_70101_b(☃, ☃);
   }

   public void func_174828_a(BlockPos var1, float var2, float var3) {
      this.func_70012_b((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o(), (double)☃.func_177952_p() + 0.5, ☃, ☃);
   }

   public void func_70012_b(double var1, double var3, double var5, float var7, float var8) {
      this.field_70165_t = ☃;
      this.field_70163_u = ☃;
      this.field_70161_v = ☃;
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      this.field_70177_z = ☃;
      this.field_70125_A = ☃;
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public float func_70032_d(Entity var1) {
      float ☃ = (float)(this.field_70165_t - ☃.field_70165_t);
      float ☃x = (float)(this.field_70163_u - ☃.field_70163_u);
      float ☃xx = (float)(this.field_70161_v - ☃.field_70161_v);
      return MathHelper.func_76129_c(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
   }

   public double func_70092_e(double var1, double var3, double var5) {
      double ☃ = this.field_70165_t - ☃;
      double ☃x = this.field_70163_u - ☃;
      double ☃xx = this.field_70161_v - ☃;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public double func_174818_b(BlockPos var1) {
      return ☃.func_177954_c(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public double func_174831_c(BlockPos var1) {
      return ☃.func_177957_d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public double func_70011_f(double var1, double var3, double var5) {
      double ☃ = this.field_70165_t - ☃;
      double ☃x = this.field_70163_u - ☃;
      double ☃xx = this.field_70161_v - ☃;
      return (double)MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
   }

   public double func_70068_e(Entity var1) {
      double ☃ = this.field_70165_t - ☃.field_70165_t;
      double ☃x = this.field_70163_u - ☃.field_70163_u;
      double ☃xx = this.field_70161_v - ☃.field_70161_v;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public double func_195048_a(Vec3d var1) {
      double ☃ = this.field_70165_t - ☃.field_72450_a;
      double ☃x = this.field_70163_u - ☃.field_72448_b;
      double ☃xx = this.field_70161_v - ☃.field_72449_c;
      return ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
   }

   public void func_70100_b_(EntityPlayer var1) {
   }

   public void func_70108_f(Entity var1) {
      if (!this.func_184223_x(☃)) {
         if (!☃.field_70145_X && !this.field_70145_X) {
            double ☃ = ☃.field_70165_t - this.field_70165_t;
            double ☃x = ☃.field_70161_v - this.field_70161_v;
            double ☃xx = MathHelper.func_76132_a(☃, ☃x);
            if (☃xx >= 0.01F) {
               ☃xx = (double)MathHelper.func_76133_a(☃xx);
               ☃ /= ☃xx;
               ☃x /= ☃xx;
               double ☃xxx = 1.0 / ☃xx;
               if (☃xxx > 1.0) {
                  ☃xxx = 1.0;
               }

               ☃ *= ☃xxx;
               ☃x *= ☃xxx;
               ☃ *= 0.05F;
               ☃x *= 0.05F;
               ☃ *= (double)(1.0F - this.field_70144_Y);
               ☃x *= (double)(1.0F - this.field_70144_Y);
               if (!this.func_184207_aI()) {
                  this.func_70024_g(-☃, 0.0, -☃x);
               }

               if (!☃.func_184207_aI()) {
                  ☃.func_70024_g(☃, 0.0, ☃x);
               }
            }
         }
      }
   }

   public void func_70024_g(double var1, double var3, double var5) {
      this.field_70159_w += ☃;
      this.field_70181_x += ☃;
      this.field_70179_y += ☃;
      this.field_70160_al = true;
   }

   protected void func_70018_K() {
      this.field_70133_I = true;
   }

   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         this.func_70018_K();
         return false;
      }
   }

   public final Vec3d func_70676_i(float var1) {
      return this.func_174806_f(this.func_195050_f(☃), this.func_195046_g(☃));
   }

   public float func_195050_f(float var1) {
      return ☃ == 1.0F ? this.field_70125_A : this.field_70127_C + (this.field_70125_A - this.field_70127_C) * ☃;
   }

   public float func_195046_g(float var1) {
      return ☃ == 1.0F ? this.field_70177_z : this.field_70126_B + (this.field_70177_z - this.field_70126_B) * ☃;
   }

   protected final Vec3d func_174806_f(float var1, float var2) {
      float ☃ = ☃ * (float) (Math.PI / 180.0);
      float ☃x = -☃ * (float) (Math.PI / 180.0);
      float ☃xx = MathHelper.func_76134_b(☃x);
      float ☃xxx = MathHelper.func_76126_a(☃x);
      float ☃xxxx = MathHelper.func_76134_b(☃);
      float ☃xxxxx = MathHelper.func_76126_a(☃);
      return new Vec3d((double)(☃xxx * ☃xxxx), (double)(-☃xxxxx), (double)(☃xx * ☃xxxx));
   }

   public Vec3d func_174824_e(float var1) {
      if (☃ == 1.0F) {
         return new Vec3d(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
      } else {
         double ☃ = this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)☃;
         double ☃x = this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)☃ + (double)this.func_70047_e();
         double ☃xx = this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)☃;
         return new Vec3d(☃, ☃x, ☃xx);
      }
   }

   public boolean func_70067_L() {
      return false;
   }

   public boolean func_70104_M() {
      return false;
   }

   public void func_191956_a(Entity var1, int var2, DamageSource var3) {
      if (☃ instanceof EntityPlayerMP) {
         CriteriaTriggers.field_192123_c.func_192211_a((EntityPlayerMP)☃, this, ☃);
      }
   }

   public boolean func_184198_c(NBTTagCompound var1) {
      String ☃ = this.func_70022_Q();
      if (!this.field_70128_L && ☃ != null) {
         ☃.func_74778_a("id", ☃);
         this.func_189511_e(☃);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_70039_c(NBTTagCompound var1) {
      return this.func_184218_aH() ? false : this.func_184198_c(☃);
   }

   public NBTTagCompound func_189511_e(NBTTagCompound var1) {
      try {
         ☃.func_74782_a("Pos", this.func_70087_a(this.field_70165_t, this.field_70163_u, this.field_70161_v));
         ☃.func_74782_a("Motion", this.func_70087_a(this.field_70159_w, this.field_70181_x, this.field_70179_y));
         ☃.func_74782_a("Rotation", this.func_70049_a(this.field_70177_z, this.field_70125_A));
         ☃.func_74776_a("FallDistance", this.field_70143_R);
         ☃.func_74777_a("Fire", (short)this.field_190534_ay);
         ☃.func_74777_a("Air", (short)this.func_70086_ai());
         ☃.func_74757_a("OnGround", this.field_70122_E);
         ☃.func_74768_a("Dimension", this.field_71093_bK.func_186068_a());
         ☃.func_74757_a("Invulnerable", this.field_83001_bt);
         ☃.func_74768_a("PortalCooldown", this.field_71088_bW);
         ☃.func_186854_a("UUID", this.func_110124_au());
         ITextComponent ☃ = this.func_200201_e();
         if (☃ != null) {
            ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(☃));
         }

         if (this.func_174833_aM()) {
            ☃.func_74757_a("CustomNameVisible", this.func_174833_aM());
         }

         if (this.func_174814_R()) {
            ☃.func_74757_a("Silent", this.func_174814_R());
         }

         if (this.func_189652_ae()) {
            ☃.func_74757_a("NoGravity", this.func_189652_ae());
         }

         if (this.field_184238_ar) {
            ☃.func_74757_a("Glowing", this.field_184238_ar);
         }

         if (!this.field_184236_aF.isEmpty()) {
            NBTTagList ☃ = new NBTTagList();

            for(String ☃x : this.field_184236_aF) {
               ☃.add((INBTBase)(new NBTTagString(☃x)));
            }

            ☃.func_74782_a("Tags", ☃);
         }

         this.func_70014_b(☃);
         if (this.func_184207_aI()) {
            NBTTagList ☃ = new NBTTagList();

            for(Entity ☃x : this.func_184188_bt()) {
               NBTTagCompound ☃xx = new NBTTagCompound();
               if (☃x.func_184198_c(☃xx)) {
                  ☃.add((INBTBase)☃xx);
               }
            }

            if (!☃.isEmpty()) {
               ☃.func_74782_a("Passengers", ☃);
            }
         }

         return ☃;
      } catch (Throwable var7) {
         CrashReport ☃ = CrashReport.func_85055_a(var7, "Saving entity NBT");
         CrashReportCategory ☃x = ☃.func_85058_a("Entity being saved");
         this.func_85029_a(☃x);
         throw new ReportedException(☃);
      }
   }

   public void func_70020_e(NBTTagCompound var1) {
      try {
         NBTTagList ☃ = ☃.func_150295_c("Pos", 6);
         NBTTagList ☃x = ☃.func_150295_c("Motion", 6);
         NBTTagList ☃xx = ☃.func_150295_c("Rotation", 5);
         this.field_70159_w = ☃x.func_150309_d(0);
         this.field_70181_x = ☃x.func_150309_d(1);
         this.field_70179_y = ☃x.func_150309_d(2);
         if (Math.abs(this.field_70159_w) > 10.0) {
            this.field_70159_w = 0.0;
         }

         if (Math.abs(this.field_70181_x) > 10.0) {
            this.field_70181_x = 0.0;
         }

         if (Math.abs(this.field_70179_y) > 10.0) {
            this.field_70179_y = 0.0;
         }

         this.field_70165_t = ☃.func_150309_d(0);
         this.field_70163_u = ☃.func_150309_d(1);
         this.field_70161_v = ☃.func_150309_d(2);
         this.field_70142_S = this.field_70165_t;
         this.field_70137_T = this.field_70163_u;
         this.field_70136_U = this.field_70161_v;
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         this.field_70177_z = ☃xx.func_150308_e(0);
         this.field_70125_A = ☃xx.func_150308_e(1);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
         this.func_70034_d(this.field_70177_z);
         this.func_181013_g(this.field_70177_z);
         this.field_70143_R = ☃.func_74760_g("FallDistance");
         this.field_190534_ay = ☃.func_74765_d("Fire");
         this.func_70050_g(☃.func_74765_d("Air"));
         this.field_70122_E = ☃.func_74767_n("OnGround");
         if (☃.func_74764_b("Dimension")) {
            this.field_71093_bK = DimensionType.func_186069_a(☃.func_74762_e("Dimension"));
         }

         this.field_83001_bt = ☃.func_74767_n("Invulnerable");
         this.field_71088_bW = ☃.func_74762_e("PortalCooldown");
         if (☃.func_186855_b("UUID")) {
            this.field_96093_i = ☃.func_186857_a("UUID");
            this.field_189513_ar = this.field_96093_i.toString();
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         if (☃.func_150297_b("CustomName", 8)) {
            this.func_200203_b(ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName")));
         }

         this.func_174805_g(☃.func_74767_n("CustomNameVisible"));
         this.func_174810_b(☃.func_74767_n("Silent"));
         this.func_189654_d(☃.func_74767_n("NoGravity"));
         this.func_184195_f(☃.func_74767_n("Glowing"));
         if (☃.func_150297_b("Tags", 9)) {
            this.field_184236_aF.clear();
            NBTTagList ☃ = ☃.func_150295_c("Tags", 8);
            int ☃x = Math.min(☃.size(), 1024);

            for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
               this.field_184236_aF.add(☃.func_150307_f(☃xx));
            }
         }

         this.func_70037_a(☃);
         if (this.func_142008_O()) {
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         }
      } catch (Throwable var8) {
         CrashReport ☃ = CrashReport.func_85055_a(var8, "Loading entity NBT");
         CrashReportCategory ☃x = ☃.func_85058_a("Entity being loaded");
         this.func_85029_a(☃x);
         throw new ReportedException(☃);
      }
   }

   protected boolean func_142008_O() {
      return true;
   }

   @Nullable
   protected final String func_70022_Q() {
      EntityType<?> ☃ = this.func_200600_R();
      ResourceLocation ☃x = EntityType.func_200718_a(☃);
      return ☃.func_200715_a() && ☃x != null ? ☃x.toString() : null;
   }

   protected abstract void func_70037_a(NBTTagCompound var1);

   protected abstract void func_70014_b(NBTTagCompound var1);

   protected NBTTagList func_70087_a(double... var1) {
      NBTTagList ☃ = new NBTTagList();

      for(double ☃x : ☃) {
         ☃.add((INBTBase)(new NBTTagDouble(☃x)));
      }

      return ☃;
   }

   protected NBTTagList func_70049_a(float... var1) {
      NBTTagList ☃ = new NBTTagList();

      for(float ☃x : ☃) {
         ☃.add((INBTBase)(new NBTTagFloat(☃x)));
      }

      return ☃;
   }

   @Nullable
   public EntityItem func_199703_a(IItemProvider var1) {
      return this.func_199702_a(☃, 0);
   }

   @Nullable
   public EntityItem func_199702_a(IItemProvider var1, int var2) {
      return this.func_70099_a(new ItemStack(☃), (float)☃);
   }

   @Nullable
   public EntityItem func_199701_a_(ItemStack var1) {
      return this.func_70099_a(☃, 0.0F);
   }

   @Nullable
   public EntityItem func_70099_a(ItemStack var1, float var2) {
      if (☃.func_190926_b()) {
         return null;
      } else {
         EntityItem ☃ = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + (double)☃, this.field_70161_v, ☃);
         ☃.func_174869_p();
         this.field_70170_p.func_72838_d(☃);
         return ☃;
      }
   }

   public boolean func_70089_S() {
      return !this.field_70128_L;
   }

   public boolean func_70094_T() {
      if (this.field_70145_X) {
         return false;
      } else {
         try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(int ☃x = 0; ☃x < 8; ++☃x) {
               int ☃xx = MathHelper.func_76128_c(this.field_70163_u + (double)(((float)((☃x >> 0) % 2) - 0.5F) * 0.1F) + (double)this.func_70047_e());
               int ☃xxx = MathHelper.func_76128_c(this.field_70165_t + (double)(((float)((☃x >> 1) % 2) - 0.5F) * this.field_70130_N * 0.8F));
               int ☃xxxx = MathHelper.func_76128_c(this.field_70161_v + (double)(((float)((☃x >> 2) % 2) - 0.5F) * this.field_70130_N * 0.8F));
               if (☃.func_177958_n() != ☃xxx || ☃.func_177956_o() != ☃xx || ☃.func_177952_p() != ☃xxxx) {
                  ☃.func_181079_c(☃xxx, ☃xx, ☃xxxx);
                  if (this.field_70170_p.func_180495_p(☃).func_191058_s()) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public boolean func_184230_a(EntityPlayer var1, EnumHand var2) {
      return false;
   }

   @Nullable
   public AxisAlignedBB func_70114_g(Entity var1) {
      return null;
   }

   public void func_70098_U() {
      Entity ☃ = this.func_184187_bx();
      if (this.func_184218_aH() && ☃.field_70128_L) {
         this.func_184210_p();
      } else {
         this.field_70159_w = 0.0;
         this.field_70181_x = 0.0;
         this.field_70179_y = 0.0;
         this.func_70071_h_();
         if (this.func_184218_aH()) {
            ☃.func_184232_k(this);
         }
      }
   }

   public void func_184232_k(Entity var1) {
      if (this.func_184196_w(☃)) {
         ☃.func_70107_b(this.field_70165_t, this.field_70163_u + this.func_70042_X() + ☃.func_70033_W(), this.field_70161_v);
      }
   }

   public double func_70033_W() {
      return 0.0;
   }

   public double func_70042_X() {
      return (double)this.field_70131_O * 0.75;
   }

   public boolean func_184220_m(Entity var1) {
      return this.func_184205_a(☃, false);
   }

   public boolean func_184205_a(Entity var1, boolean var2) {
      for(Entity ☃ = ☃; ☃.field_184239_as != null; ☃ = ☃.field_184239_as) {
         if (☃.field_184239_as == this) {
            return false;
         }
      }

      if (☃ || this.func_184228_n(☃) && ☃.func_184219_q(this)) {
         if (this.func_184218_aH()) {
            this.func_184210_p();
         }

         this.field_184239_as = ☃;
         this.field_184239_as.func_184200_o(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_184228_n(Entity var1) {
      return this.field_184245_j <= 0;
   }

   public void func_184226_ay() {
      for(int ☃ = this.field_184244_h.size() - 1; ☃ >= 0; --☃) {
         ((Entity)this.field_184244_h.get(☃)).func_184210_p();
      }
   }

   public void func_184210_p() {
      if (this.field_184239_as != null) {
         Entity ☃ = this.field_184239_as;
         this.field_184239_as = null;
         ☃.func_184225_p(this);
      }
   }

   protected void func_184200_o(Entity var1) {
      if (☃.func_184187_bx() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         if (!this.field_70170_p.field_72995_K && ☃ instanceof EntityPlayer && !(this.func_184179_bs() instanceof EntityPlayer)) {
            this.field_184244_h.add(0, ☃);
         } else {
            this.field_184244_h.add(☃);
         }
      }
   }

   protected void func_184225_p(Entity var1) {
      if (☃.func_184187_bx() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         this.field_184244_h.remove(☃);
         ☃.field_184245_j = 60;
      }
   }

   protected boolean func_184219_q(Entity var1) {
      return this.func_184188_bt().size() < 1;
   }

   public float func_70111_Y() {
      return 0.0F;
   }

   public Vec3d func_70040_Z() {
      return this.func_174806_f(this.field_70125_A, this.field_70177_z);
   }

   public Vec2f func_189653_aC() {
      return new Vec2f(this.field_70125_A, this.field_70177_z);
   }

   public void func_181015_d(BlockPos var1) {
      if (this.field_71088_bW > 0) {
         this.field_71088_bW = this.func_82147_ab();
      } else {
         if (!this.field_70170_p.field_72995_K && !☃.equals(this.field_181016_an)) {
            this.field_181016_an = new BlockPos(☃);
            BlockPattern.PatternHelper ☃ = ((BlockPortal)Blocks.field_150427_aO).func_181089_f(this.field_70170_p, this.field_181016_an);
            double ☃x = ☃.func_177669_b().func_176740_k() == EnumFacing.Axis.X
               ? (double)☃.func_181117_a().func_177952_p()
               : (double)☃.func_181117_a().func_177958_n();
            double ☃xx = ☃.func_177669_b().func_176740_k() == EnumFacing.Axis.X ? this.field_70161_v : this.field_70165_t;
            ☃xx = Math.abs(
               MathHelper.func_181160_c(
                  ☃xx - (double)(☃.func_177669_b().func_176746_e().func_176743_c() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0),
                  ☃x,
                  ☃x - (double)☃.func_181118_d()
               )
            );
            double ☃xxx = MathHelper.func_181160_c(
               this.field_70163_u - 1.0, (double)☃.func_181117_a().func_177956_o(), (double)(☃.func_181117_a().func_177956_o() - ☃.func_181119_e())
            );
            this.field_181017_ao = new Vec3d(☃xx, ☃xxx, 0.0);
            this.field_181018_ap = ☃.func_177669_b();
         }

         this.field_71087_bX = true;
      }
   }

   public int func_82147_ab() {
      return 300;
   }

   public Iterable<ItemStack> func_184214_aD() {
      return field_190535_b;
   }

   public Iterable<ItemStack> func_184193_aE() {
      return field_190535_b;
   }

   public Iterable<ItemStack> func_184209_aF() {
      return Iterables.concat(this.func_184214_aD(), this.func_184193_aE());
   }

   public void func_184201_a(EntityEquipmentSlot var1, ItemStack var2) {
   }

   public boolean func_70027_ad() {
      boolean ☃ = this.field_70170_p != null && this.field_70170_p.field_72995_K;
      return !this.field_70178_ae && (this.field_190534_ay > 0 || ☃ && this.func_70083_f(0));
   }

   public boolean func_184218_aH() {
      return this.func_184187_bx() != null;
   }

   public boolean func_184207_aI() {
      return !this.func_184188_bt().isEmpty();
   }

   public boolean func_205710_ba() {
      return true;
   }

   public boolean func_70093_af() {
      return this.func_70083_f(1);
   }

   public void func_70095_a(boolean var1) {
      this.func_70052_a(1, ☃);
   }

   public boolean func_70051_ag() {
      return this.func_70083_f(3);
   }

   public void func_70031_b(boolean var1) {
      this.func_70052_a(3, ☃);
   }

   public boolean func_203007_ba() {
      return this.func_70083_f(4);
   }

   public void func_204711_a(boolean var1) {
      this.func_70052_a(4, ☃);
   }

   public boolean func_184202_aL() {
      return this.field_184238_ar || this.field_70170_p.field_72995_K && this.func_70083_f(6);
   }

   public void func_184195_f(boolean var1) {
      this.field_184238_ar = ☃;
      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(6, this.field_184238_ar);
      }
   }

   public boolean func_82150_aj() {
      return this.func_70083_f(5);
   }

   @Nullable
   public Team func_96124_cp() {
      return this.field_70170_p.func_96441_U().func_96509_i(this.func_195047_I_());
   }

   public boolean func_184191_r(Entity var1) {
      return this.func_184194_a(☃.func_96124_cp());
   }

   public boolean func_184194_a(Team var1) {
      return this.func_96124_cp() != null ? this.func_96124_cp().func_142054_a(☃) : false;
   }

   public void func_82142_c(boolean var1) {
      this.func_70052_a(5, ☃);
   }

   protected boolean func_70083_f(int var1) {
      return (this.field_70180_af.func_187225_a(field_184240_ax) & 1 << ☃) != 0;
   }

   protected void func_70052_a(int var1, boolean var2) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184240_ax);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184240_ax, (byte)(☃ | 1 << ☃));
      } else {
         this.field_70180_af.func_187227_b(field_184240_ax, (byte)(☃ & ~(1 << ☃)));
      }
   }

   public int func_205010_bg() {
      return 300;
   }

   public int func_70086_ai() {
      return this.field_70180_af.func_187225_a(field_184241_ay);
   }

   public void func_70050_g(int var1) {
      this.field_70180_af.func_187227_b(field_184241_ay, ☃);
   }

   public void func_70077_a(EntityLightningBolt var1) {
      ++this.field_190534_ay;
      if (this.field_190534_ay == 0) {
         this.func_70015_d(8);
      }

      this.func_70097_a(DamageSource.field_180137_b, 5.0F);
   }

   public void func_203002_i(boolean var1) {
      if (☃) {
         this.field_70181_x = Math.max(-0.9, this.field_70181_x - 0.03);
      } else {
         this.field_70181_x = Math.min(1.8, this.field_70181_x + 0.1);
      }
   }

   public void func_203004_j(boolean var1) {
      if (☃) {
         this.field_70181_x = Math.max(-0.3, this.field_70181_x - 0.03);
      } else {
         this.field_70181_x = Math.min(0.7, this.field_70181_x + 0.06);
      }

      this.field_70143_R = 0.0F;
   }

   public void func_70074_a(EntityLivingBase var1) {
   }

   protected boolean func_145771_j(double var1, double var3, double var5) {
      BlockPos ☃ = new BlockPos(☃, ☃, ☃);
      double ☃x = ☃ - (double)☃.func_177958_n();
      double ☃xx = ☃ - (double)☃.func_177956_o();
      double ☃xxx = ☃ - (double)☃.func_177952_p();
      if (this.field_70170_p.func_195586_b(null, this.func_174813_aQ())) {
         return false;
      } else {
         EnumFacing ☃ = EnumFacing.UP;
         double ☃x = Double.MAX_VALUE;
         if (!this.field_70170_p.func_175665_u(☃.func_177976_e()) && ☃x < ☃x) {
            ☃x = ☃x;
            ☃ = EnumFacing.WEST;
         }

         if (!this.field_70170_p.func_175665_u(☃.func_177974_f()) && 1.0 - ☃x < ☃x) {
            ☃x = 1.0 - ☃x;
            ☃ = EnumFacing.EAST;
         }

         if (!this.field_70170_p.func_175665_u(☃.func_177978_c()) && ☃xxx < ☃x) {
            ☃x = ☃xxx;
            ☃ = EnumFacing.NORTH;
         }

         if (!this.field_70170_p.func_175665_u(☃.func_177968_d()) && 1.0 - ☃xxx < ☃x) {
            ☃x = 1.0 - ☃xxx;
            ☃ = EnumFacing.SOUTH;
         }

         if (!this.field_70170_p.func_175665_u(☃.func_177984_a()) && 1.0 - ☃xx < ☃x) {
            ☃x = 1.0 - ☃xx;
            ☃ = EnumFacing.UP;
         }

         float ☃ = this.field_70146_Z.nextFloat() * 0.2F + 0.1F;
         float ☃x = (float)☃.func_176743_c().func_179524_a();
         if (☃.func_176740_k() == EnumFacing.Axis.X) {
            this.field_70159_w = (double)(☃x * ☃);
            this.field_70181_x *= 0.75;
            this.field_70179_y *= 0.75;
         } else if (☃.func_176740_k() == EnumFacing.Axis.Y) {
            this.field_70159_w *= 0.75;
            this.field_70181_x = (double)(☃x * ☃);
            this.field_70179_y *= 0.75;
         } else if (☃.func_176740_k() == EnumFacing.Axis.Z) {
            this.field_70159_w *= 0.75;
            this.field_70181_x *= 0.75;
            this.field_70179_y = (double)(☃x * ☃);
         }

         return true;
      }
   }

   public void func_70110_aj() {
      this.field_70134_J = true;
      this.field_70143_R = 0.0F;
   }

   private static void func_207712_c(ITextComponent var0) {
      ☃.func_211710_a(var0x -> var0x.func_150241_a(null)).func_150253_a().forEach(Entity::func_207712_c);
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      if (☃ != null) {
         ITextComponent ☃x = ☃.func_212638_h();
         func_207712_c(☃x);
         return ☃x;
      } else {
         return this.field_200606_g.func_212546_e();
      }
   }

   @Nullable
   public Entity[] func_70021_al() {
      return null;
   }

   public boolean func_70028_i(Entity var1) {
      return this == ☃;
   }

   public float func_70079_am() {
      return 0.0F;
   }

   public void func_70034_d(float var1) {
   }

   public void func_181013_g(float var1) {
   }

   public boolean func_70075_an() {
      return true;
   }

   public boolean func_85031_j(Entity var1) {
      return false;
   }

   public String toString() {
      return String.format(
         Locale.ROOT,
         "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
         this.getClass().getSimpleName(),
         this.func_200200_C_().func_150261_e(),
         this.field_145783_c,
         this.field_70170_p == null ? "~NULL~" : this.field_70170_p.func_72912_H().func_76065_j(),
         this.field_70165_t,
         this.field_70163_u,
         this.field_70161_v
      );
   }

   public boolean func_180431_b(DamageSource var1) {
      return this.field_83001_bt && ☃ != DamageSource.field_76380_i && !☃.func_180136_u();
   }

   public boolean func_190530_aW() {
      return this.field_83001_bt;
   }

   public void func_184224_h(boolean var1) {
      this.field_83001_bt = ☃;
   }

   public void func_82149_j(Entity var1) {
      this.func_70012_b(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70177_z, ☃.field_70125_A);
   }

   public void func_180432_n(Entity var1) {
      NBTTagCompound ☃ = ☃.func_189511_e(new NBTTagCompound());
      ☃.func_82580_o("Dimension");
      this.func_70020_e(☃);
      this.field_71088_bW = ☃.field_71088_bW;
      this.field_181016_an = ☃.field_181016_an;
      this.field_181017_ao = ☃.field_181017_ao;
      this.field_181018_ap = ☃.field_181018_ap;
   }

   @Nullable
   public Entity func_212321_a(DimensionType var1) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         this.field_70170_p.field_72984_F.func_76320_a("changeDimension");
         MinecraftServer ☃ = this.func_184102_h();
         DimensionType ☃x = this.field_71093_bK;
         WorldServer ☃xx = ☃.func_71218_a(☃x);
         WorldServer ☃xxx = ☃.func_71218_a(☃);
         this.field_71093_bK = ☃;
         if (☃x == DimensionType.THE_END && ☃ == DimensionType.THE_END) {
            ☃xxx = ☃.func_71218_a(DimensionType.OVERWORLD);
            this.field_71093_bK = DimensionType.OVERWORLD;
         }

         this.field_70170_p.func_72900_e(this);
         this.field_70128_L = false;
         this.field_70170_p.field_72984_F.func_76320_a("reposition");
         BlockPos ☃;
         if (☃ == DimensionType.THE_END) {
            ☃ = ☃xxx.func_180504_m();
         } else {
            double ☃ = this.field_70165_t;
            double ☃x = this.field_70161_v;
            double ☃xx = 8.0;
            if (☃ == DimensionType.NETHER) {
               ☃ = MathHelper.func_151237_a(☃ / 8.0, ☃xxx.func_175723_af().func_177726_b() + 16.0, ☃xxx.func_175723_af().func_177728_d() - 16.0);
               ☃x = MathHelper.func_151237_a(☃x / 8.0, ☃xxx.func_175723_af().func_177736_c() + 16.0, ☃xxx.func_175723_af().func_177733_e() - 16.0);
            } else if (☃ == DimensionType.OVERWORLD) {
               ☃ = MathHelper.func_151237_a(☃ * 8.0, ☃xxx.func_175723_af().func_177726_b() + 16.0, ☃xxx.func_175723_af().func_177728_d() - 16.0);
               ☃x = MathHelper.func_151237_a(☃x * 8.0, ☃xxx.func_175723_af().func_177736_c() + 16.0, ☃xxx.func_175723_af().func_177733_e() - 16.0);
            }

            ☃ = (double)MathHelper.func_76125_a((int)☃, -29999872, 29999872);
            ☃x = (double)MathHelper.func_76125_a((int)☃x, -29999872, 29999872);
            float ☃ = this.field_70177_z;
            this.func_70012_b(☃, this.field_70163_u, ☃x, 90.0F, 0.0F);
            Teleporter ☃x = ☃xxx.func_85176_s();
            ☃x.func_180620_b(this, ☃);
            ☃ = new BlockPos(this);
         }

         ☃xx.func_72866_a(this, false);
         this.field_70170_p.field_72984_F.func_76318_c("reloading");
         Entity ☃ = this.func_200600_R().func_200721_a(☃xxx);
         if (☃ != null) {
            ☃.func_180432_n(this);
            if (☃x == DimensionType.THE_END && ☃ == DimensionType.THE_END) {
               BlockPos ☃x = ☃xxx.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ☃xxx.func_175694_M());
               ☃.func_174828_a(☃x, ☃.field_70177_z, ☃.field_70125_A);
            } else {
               ☃.func_174828_a(☃, ☃.field_70177_z, ☃.field_70125_A);
            }

            boolean ☃x = ☃.field_98038_p;
            ☃.field_98038_p = true;
            ☃xxx.func_72838_d(☃);
            ☃.field_98038_p = ☃x;
            ☃xxx.func_72866_a(☃, false);
         }

         this.field_70128_L = true;
         this.field_70170_p.field_72984_F.func_76319_b();
         ☃xx.func_82742_i();
         ☃xxx.func_82742_i();
         this.field_70170_p.field_72984_F.func_76319_b();
         return ☃;
      } else {
         return null;
      }
   }

   public boolean func_184222_aU() {
      return true;
   }

   public float func_180428_a(Explosion var1, IBlockReader var2, BlockPos var3, IBlockState var4, IFluidState var5, float var6) {
      return ☃;
   }

   public boolean func_174816_a(Explosion var1, IBlockReader var2, BlockPos var3, IBlockState var4, float var5) {
      return true;
   }

   public int func_82143_as() {
      return 3;
   }

   public Vec3d func_181014_aG() {
      return this.field_181017_ao;
   }

   public EnumFacing func_181012_aH() {
      return this.field_181018_ap;
   }

   public boolean func_145773_az() {
      return false;
   }

   public void func_85029_a(CrashReportCategory var1) {
      ☃.func_189529_a("Entity Type", () -> EntityType.func_200718_a(this.func_200600_R()) + " (" + this.getClass().getCanonicalName() + ")");
      ☃.func_71507_a("Entity ID", this.field_145783_c);
      ☃.func_189529_a("Entity Name", () -> this.func_200200_C_().getString());
      ☃.func_71507_a("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.field_70165_t, this.field_70163_u, this.field_70161_v));
      ☃.func_71507_a(
         "Entity's Block location",
         CrashReportCategory.func_184876_a(
            MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)
         )
      );
      ☃.func_71507_a("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.field_70159_w, this.field_70181_x, this.field_70179_y));
      ☃.func_189529_a("Entity's Passengers", () -> this.func_184188_bt().toString());
      ☃.func_189529_a("Entity's Vehicle", () -> this.func_184187_bx().toString());
   }

   public void func_184221_a(UUID var1) {
      this.field_96093_i = ☃;
      this.field_189513_ar = this.field_96093_i.toString();
   }

   public UUID func_110124_au() {
      return this.field_96093_i;
   }

   public String func_189512_bd() {
      return this.field_189513_ar;
   }

   public String func_195047_I_() {
      return this.field_189513_ar;
   }

   public boolean func_96092_aw() {
      return true;
   }

   @Override
   public ITextComponent func_145748_c_() {
      return ScorePlayerTeam.func_200541_a(this.func_96124_cp(), this.func_200200_C_())
         .func_211710_a(var1 -> var1.func_150209_a(this.func_174823_aP()).func_179989_a(this.func_189512_bd()));
   }

   public void func_200203_b(@Nullable ITextComponent var1) {
      this.field_70180_af.func_187227_b(field_184242_az, Optional.ofNullable(☃));
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return (ITextComponent)((Optional)this.field_70180_af.func_187225_a(field_184242_az)).orElse(null);
   }

   @Override
   public boolean func_145818_k_() {
      return ((Optional)this.field_70180_af.func_187225_a(field_184242_az)).isPresent();
   }

   public void func_174805_g(boolean var1) {
      this.field_70180_af.func_187227_b(field_184233_aA, ☃);
   }

   public boolean func_174833_aM() {
      return this.field_70180_af.func_187225_a(field_184233_aA);
   }

   public void func_70634_a(double var1, double var3, double var5) {
      this.field_184237_aG = true;
      this.func_70012_b(☃, ☃, ☃, this.field_70177_z, this.field_70125_A);
      this.field_70170_p.func_72866_a(this, false);
   }

   public void func_184206_a(DataParameter<?> var1) {
   }

   public EnumFacing func_174811_aO() {
      return EnumFacing.func_176733_a((double)this.field_70177_z);
   }

   public EnumFacing func_184172_bi() {
      return this.func_174811_aO();
   }

   protected HoverEvent func_174823_aP() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ResourceLocation ☃x = EntityType.func_200718_a(this.func_200600_R());
      ☃.func_74778_a("id", this.func_189512_bd());
      if (☃x != null) {
         ☃.func_74778_a("type", ☃x.toString());
      }

      ☃.func_74778_a("name", ITextComponent.Serializer.func_150696_a(this.func_200200_C_()));
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new TextComponentString(☃.toString()));
   }

   public boolean func_174827_a(EntityPlayerMP var1) {
      return true;
   }

   public AxisAlignedBB func_174813_aQ() {
      return this.field_70121_D;
   }

   public void func_174826_a(AxisAlignedBB var1) {
      this.field_70121_D = ☃;
   }

   public float func_70047_e() {
      return this.field_70131_O * 0.85F;
   }

   public boolean func_174832_aS() {
      return this.field_174835_g;
   }

   public void func_174821_h(boolean var1) {
      this.field_174835_g = ☃;
   }

   public boolean func_174820_d(int var1, ItemStack var2) {
      return false;
   }

   @Override
   public void func_145747_a(ITextComponent var1) {
   }

   public BlockPos func_180425_c() {
      return new BlockPos(this);
   }

   public Vec3d func_174791_d() {
      return new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public World func_130014_f_() {
      return this.field_70170_p;
   }

   @Nullable
   public MinecraftServer func_184102_h() {
      return this.field_70170_p.func_73046_m();
   }

   public EnumActionResult func_184199_a(EntityPlayer var1, Vec3d var2, EnumHand var3) {
      return EnumActionResult.PASS;
   }

   public boolean func_180427_aV() {
      return false;
   }

   protected void func_174815_a(EntityLivingBase var1, Entity var2) {
      if (☃ instanceof EntityLivingBase) {
         EnchantmentHelper.func_151384_a((EntityLivingBase)☃, ☃);
      }

      EnchantmentHelper.func_151385_b(☃, ☃);
   }

   public void func_184178_b(EntityPlayerMP var1) {
   }

   public void func_184203_c(EntityPlayerMP var1) {
   }

   public float func_184229_a(Rotation var1) {
      float ☃ = MathHelper.func_76142_g(this.field_70177_z);
      switch(☃) {
         case CLOCKWISE_180:
            return ☃ + 180.0F;
         case COUNTERCLOCKWISE_90:
            return ☃ + 270.0F;
         case CLOCKWISE_90:
            return ☃ + 90.0F;
         default:
            return ☃;
      }
   }

   public float func_184217_a(Mirror var1) {
      float ☃ = MathHelper.func_76142_g(this.field_70177_z);
      switch(☃) {
         case LEFT_RIGHT:
            return -☃;
         case FRONT_BACK:
            return 180.0F - ☃;
         default:
            return ☃;
      }
   }

   public boolean func_184213_bq() {
      return false;
   }

   public boolean func_184189_br() {
      boolean ☃ = this.field_184237_aG;
      this.field_184237_aG = false;
      return ☃;
   }

   @Nullable
   public Entity func_184179_bs() {
      return null;
   }

   public List<Entity> func_184188_bt() {
      return (List<Entity>)(this.field_184244_h.isEmpty() ? Collections.emptyList() : Lists.<Entity>newArrayList(this.field_184244_h));
   }

   public boolean func_184196_w(Entity var1) {
      for(Entity ☃ : this.func_184188_bt()) {
         if (☃.equals(☃)) {
            return true;
         }
      }

      return false;
   }

   public boolean func_205708_a(Class<? extends Entity> var1) {
      for(Entity ☃ : this.func_184188_bt()) {
         if (☃.isAssignableFrom(☃.getClass())) {
            return true;
         }
      }

      return false;
   }

   public Collection<Entity> func_184182_bu() {
      Set<Entity> ☃ = Sets.<Entity>newHashSet();

      for(Entity ☃x : this.func_184188_bt()) {
         ☃.add(☃x);
         ☃x.func_200604_a(false, ☃);
      }

      return ☃;
   }

   public boolean func_200601_bK() {
      Set<Entity> ☃ = Sets.<Entity>newHashSet();
      this.func_200604_a(true, ☃);
      return ☃.size() == 1;
   }

   private void func_200604_a(boolean var1, Set<Entity> var2) {
      for(Entity ☃ : this.func_184188_bt()) {
         if (!☃ || EntityPlayerMP.class.isAssignableFrom(☃.getClass())) {
            ☃.add(☃);
         }

         ☃.func_200604_a(☃, ☃);
      }
   }

   public Entity func_184208_bv() {
      Entity ☃ = this;

      while(☃.func_184218_aH()) {
         ☃ = ☃.func_184187_bx();
      }

      return ☃;
   }

   public boolean func_184223_x(Entity var1) {
      return this.func_184208_bv() == ☃.func_184208_bv();
   }

   public boolean func_184215_y(Entity var1) {
      for(Entity ☃ : this.func_184188_bt()) {
         if (☃.equals(☃)) {
            return true;
         }

         if (☃.func_184215_y(☃)) {
            return true;
         }
      }

      return false;
   }

   public boolean func_184186_bw() {
      Entity ☃ = this.func_184179_bs();
      if (☃ instanceof EntityPlayer) {
         return ((EntityPlayer)☃).func_175144_cb();
      } else {
         return !this.field_70170_p.field_72995_K;
      }
   }

   @Nullable
   public Entity func_184187_bx() {
      return this.field_184239_as;
   }

   public EnumPushReaction func_184192_z() {
      return EnumPushReaction.NORMAL;
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.NEUTRAL;
   }

   protected int func_190531_bD() {
      return 1;
   }

   public CommandSource func_195051_bN() {
      return new CommandSource(
         this,
         new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v),
         this.func_189653_aC(),
         this.field_70170_p instanceof WorldServer ? (WorldServer)this.field_70170_p : null,
         this.func_184840_I(),
         this.func_200200_C_().getString(),
         this.func_145748_c_(),
         this.field_70170_p.func_73046_m(),
         this
      );
   }

   protected int func_184840_I() {
      return 0;
   }

   public boolean func_211513_k(int var1) {
      return this.func_184840_I() >= ☃;
   }

   @Override
   public boolean func_195039_a() {
      return this.field_70170_p.func_82736_K().func_82766_b("sendCommandFeedback");
   }

   @Override
   public boolean func_195040_b() {
      return true;
   }

   @Override
   public boolean func_195041_r_() {
      return true;
   }

   public void func_200602_a(EntityAnchorArgument.Type var1, Vec3d var2) {
      Vec3d ☃ = ☃.func_201017_a(this);
      double ☃x = ☃.field_72450_a - ☃.field_72450_a;
      double ☃xx = ☃.field_72448_b - ☃.field_72448_b;
      double ☃xxx = ☃.field_72449_c - ☃.field_72449_c;
      double ☃xxxx = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx);
      this.field_70125_A = MathHelper.func_76142_g((float)(-(MathHelper.func_181159_b(☃xx, ☃xxxx) * 180.0F / (float)Math.PI)));
      this.field_70177_z = MathHelper.func_76142_g((float)(MathHelper.func_181159_b(☃xxx, ☃x) * 180.0F / (float)Math.PI) - 90.0F);
      this.func_70034_d(this.field_70177_z);
      this.field_70127_C = this.field_70125_A;
      this.field_70126_B = this.field_70177_z;
   }

   public boolean func_210500_b(Tag<Fluid> var1) {
      AxisAlignedBB ☃ = this.func_174813_aQ().func_186664_h(0.001);
      int ☃x = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃xx = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xxx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxxx = MathHelper.func_76143_f(☃.field_72337_e);
      int ☃xxxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxxx = MathHelper.func_76143_f(☃.field_72334_f);
      if (!this.field_70170_p.func_175663_a(☃x, ☃xxx, ☃xxxxx, ☃xx, ☃xxxx, ☃xxxxxx, true)) {
         return false;
      } else {
         double ☃ = 0.0;
         boolean ☃x = this.func_96092_aw();
         boolean ☃xx = false;
         Vec3d ☃xxx = Vec3d.field_186680_a;
         int ☃xxxx = 0;

         try (BlockPos.PooledMutableBlockPos ☃xxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(int ☃xxxxxx = ☃x; ☃xxxxxx < ☃xx; ++☃xxxxxx) {
               for(int ☃xxxxxxx = ☃xxx; ☃xxxxxxx < ☃xxxx; ++☃xxxxxxx) {
                  for(int ☃xxxxxxxx = ☃xxxxx; ☃xxxxxxxx < ☃xxxxxx; ++☃xxxxxxxx) {
                     ☃xxxxx.func_181079_c(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
                     IFluidState ☃xxxxxxxxx = this.field_70170_p.func_204610_c(☃xxxxx);
                     if (☃xxxxxxxxx.func_206884_a(☃)) {
                        double ☃xxxxxxxxxx = (double)((float)☃xxxxxxx + ☃xxxxxxxxx.func_206885_f());
                        if (☃xxxxxxxxxx >= ☃.field_72338_b) {
                           ☃xx = true;
                           ☃ = Math.max(☃xxxxxxxxxx - ☃.field_72338_b, ☃);
                           if (☃x) {
                              Vec3d ☃xxxxxxxxxxx = ☃xxxxxxxxx.func_206887_a(this.field_70170_p, ☃xxxxx);
                              if (☃ < 0.4) {
                                 ☃xxxxxxxxxxx = ☃xxxxxxxxxxx.func_186678_a(☃);
                              }

                              ☃xxx = ☃xxx.func_178787_e(☃xxxxxxxxxxx);
                              ++☃xxxx;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (☃xxx.func_72433_c() > 0.0) {
            if (☃xxxx > 0) {
               ☃xxx = ☃xxx.func_186678_a(1.0 / (double)☃xxxx);
            }

            if (!(this instanceof EntityPlayer)) {
               ☃xxx = ☃xxx.func_72432_b();
            }

            double ☃xxxxx = 0.014;
            this.field_70159_w += ☃xxx.field_72450_a * 0.014;
            this.field_70181_x += ☃xxx.field_72448_b * 0.014;
            this.field_70179_y += ☃xxx.field_72449_c * 0.014;
         }

         this.field_211517_W = ☃;
         return ☃xx;
      }
   }

   public double func_212107_bY() {
      return this.field_211517_W;
   }
}
