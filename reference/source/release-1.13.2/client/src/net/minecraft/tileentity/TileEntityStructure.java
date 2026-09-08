package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TileEntityStructure extends TileEntity {
   private ResourceLocation field_184420_a;
   private String field_184421_f = "";
   private String field_184422_g = "";
   private BlockPos field_184423_h = new BlockPos(0, 1, 0);
   private BlockPos field_184424_i = BlockPos.field_177992_a;
   private Mirror field_184425_j = Mirror.NONE;
   private Rotation field_184426_k = Rotation.NONE;
   private StructureMode field_184427_l = StructureMode.DATA;
   private boolean field_184428_m = true;
   private boolean field_189727_n;
   private boolean field_189728_o;
   private boolean field_189729_p = true;
   private float field_189730_q = 1.0F;
   private long field_189731_r;

   public TileEntityStructure() {
      super(TileEntityType.field_200990_u);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74778_a("name", this.func_189715_d());
      ☃.func_74778_a("author", this.field_184421_f);
      ☃.func_74778_a("metadata", this.field_184422_g);
      ☃.func_74768_a("posX", this.field_184423_h.func_177958_n());
      ☃.func_74768_a("posY", this.field_184423_h.func_177956_o());
      ☃.func_74768_a("posZ", this.field_184423_h.func_177952_p());
      ☃.func_74768_a("sizeX", this.field_184424_i.func_177958_n());
      ☃.func_74768_a("sizeY", this.field_184424_i.func_177956_o());
      ☃.func_74768_a("sizeZ", this.field_184424_i.func_177952_p());
      ☃.func_74778_a("rotation", this.field_184426_k.toString());
      ☃.func_74778_a("mirror", this.field_184425_j.toString());
      ☃.func_74778_a("mode", this.field_184427_l.toString());
      ☃.func_74757_a("ignoreEntities", this.field_184428_m);
      ☃.func_74757_a("powered", this.field_189727_n);
      ☃.func_74757_a("showair", this.field_189728_o);
      ☃.func_74757_a("showboundingbox", this.field_189729_p);
      ☃.func_74776_a("integrity", this.field_189730_q);
      ☃.func_74772_a("seed", this.field_189731_r);
      return ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.func_184404_a(☃.func_74779_i("name"));
      this.field_184421_f = ☃.func_74779_i("author");
      this.field_184422_g = ☃.func_74779_i("metadata");
      int ☃ = MathHelper.func_76125_a(☃.func_74762_e("posX"), -32, 32);
      int ☃x = MathHelper.func_76125_a(☃.func_74762_e("posY"), -32, 32);
      int ☃xx = MathHelper.func_76125_a(☃.func_74762_e("posZ"), -32, 32);
      this.field_184423_h = new BlockPos(☃, ☃x, ☃xx);
      int ☃xxx = MathHelper.func_76125_a(☃.func_74762_e("sizeX"), 0, 32);
      int ☃xxxx = MathHelper.func_76125_a(☃.func_74762_e("sizeY"), 0, 32);
      int ☃xxxxx = MathHelper.func_76125_a(☃.func_74762_e("sizeZ"), 0, 32);
      this.field_184424_i = new BlockPos(☃xxx, ☃xxxx, ☃xxxxx);

      try {
         this.field_184426_k = Rotation.valueOf(☃.func_74779_i("rotation"));
      } catch (IllegalArgumentException var11) {
         this.field_184426_k = Rotation.NONE;
      }

      try {
         this.field_184425_j = Mirror.valueOf(☃.func_74779_i("mirror"));
      } catch (IllegalArgumentException var10) {
         this.field_184425_j = Mirror.NONE;
      }

      try {
         this.field_184427_l = StructureMode.valueOf(☃.func_74779_i("mode"));
      } catch (IllegalArgumentException var9) {
         this.field_184427_l = StructureMode.DATA;
      }

      this.field_184428_m = ☃.func_74767_n("ignoreEntities");
      this.field_189727_n = ☃.func_74767_n("powered");
      this.field_189728_o = ☃.func_74767_n("showair");
      this.field_189729_p = ☃.func_74767_n("showboundingbox");
      if (☃.func_74764_b("integrity")) {
         this.field_189730_q = ☃.func_74760_g("integrity");
      } else {
         this.field_189730_q = 1.0F;
      }

      this.field_189731_r = ☃.func_74763_f("seed");
      this.func_189704_J();
   }

   private void func_189704_J() {
      if (this.field_145850_b != null) {
         BlockPos ☃ = this.func_174877_v();
         IBlockState ☃x = this.field_145850_b.func_180495_p(☃);
         if (☃x.func_177230_c() == Blocks.field_185779_df) {
            this.field_145850_b.func_180501_a(☃, ☃x.func_206870_a(BlockStructure.field_185587_a, this.field_184427_l), 2);
         }
      }
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 7, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public boolean func_189701_a(EntityPlayer var1) {
      if (!☃.func_195070_dx()) {
         return false;
      } else {
         if (☃.func_130014_f_().field_72995_K) {
            ☃.func_189807_a(this);
         }

         return true;
      }
   }

   public String func_189715_d() {
      return this.field_184420_a == null ? "" : this.field_184420_a.toString();
   }

   public boolean func_208404_d() {
      return this.field_184420_a != null;
   }

   public void func_184404_a(@Nullable String var1) {
      this.func_210163_a(StringUtils.func_151246_b(☃) ? null : ResourceLocation.func_208304_a(☃));
   }

   public void func_210163_a(@Nullable ResourceLocation var1) {
      this.field_184420_a = ☃;
   }

   public void func_189720_a(EntityLivingBase var1) {
      this.field_184421_f = ☃.func_200200_C_().getString();
   }

   public BlockPos func_189711_e() {
      return this.field_184423_h;
   }

   public void func_184414_b(BlockPos var1) {
      this.field_184423_h = ☃;
   }

   public BlockPos func_189717_g() {
      return this.field_184424_i;
   }

   public void func_184409_c(BlockPos var1) {
      this.field_184424_i = ☃;
   }

   public Mirror func_189716_h() {
      return this.field_184425_j;
   }

   public void func_184411_a(Mirror var1) {
      this.field_184425_j = ☃;
   }

   public Rotation func_189726_i() {
      return this.field_184426_k;
   }

   public void func_184408_a(Rotation var1) {
      this.field_184426_k = ☃;
   }

   public String func_189708_j() {
      return this.field_184422_g;
   }

   public void func_184410_b(String var1) {
      this.field_184422_g = ☃;
   }

   public StructureMode func_189700_k() {
      return this.field_184427_l;
   }

   public void func_184405_a(StructureMode var1) {
      this.field_184427_l = ☃;
      IBlockState ☃ = this.field_145850_b.func_180495_p(this.func_174877_v());
      if (☃.func_177230_c() == Blocks.field_185779_df) {
         this.field_145850_b.func_180501_a(this.func_174877_v(), ☃.func_206870_a(BlockStructure.field_185587_a, ☃), 2);
      }
   }

   public void func_189724_l() {
      switch(this.func_189700_k()) {
         case SAVE:
            this.func_184405_a(StructureMode.LOAD);
            break;
         case LOAD:
            this.func_184405_a(StructureMode.CORNER);
            break;
         case CORNER:
            this.func_184405_a(StructureMode.DATA);
            break;
         case DATA:
            this.func_184405_a(StructureMode.SAVE);
      }
   }

   public boolean func_189713_m() {
      return this.field_184428_m;
   }

   public void func_184406_a(boolean var1) {
      this.field_184428_m = ☃;
   }

   public float func_189702_n() {
      return this.field_189730_q;
   }

   public void func_189718_a(float var1) {
      this.field_189730_q = ☃;
   }

   public long func_189719_o() {
      return this.field_189731_r;
   }

   public void func_189725_a(long var1) {
      this.field_189731_r = ☃;
   }

   public boolean func_184417_l() {
      if (this.field_184427_l != StructureMode.SAVE) {
         return false;
      } else {
         BlockPos ☃ = this.func_174877_v();
         int ☃x = 80;
         BlockPos ☃xx = new BlockPos(☃.func_177958_n() - 80, 0, ☃.func_177952_p() - 80);
         BlockPos ☃xxx = new BlockPos(☃.func_177958_n() + 80, 255, ☃.func_177952_p() + 80);
         List<TileEntityStructure> ☃xxxx = this.func_184418_a(☃xx, ☃xxx);
         List<TileEntityStructure> ☃xxxxx = this.func_184415_a(☃xxxx);
         if (☃xxxxx.size() < 1) {
            return false;
         } else {
            MutableBoundingBox ☃ = this.func_184416_a(☃, ☃xxxxx);
            if (☃.field_78893_d - ☃.field_78897_a > 1 && ☃.field_78894_e - ☃.field_78895_b > 1 && ☃.field_78892_f - ☃.field_78896_c > 1) {
               this.field_184423_h = new BlockPos(
                  ☃.field_78897_a - ☃.func_177958_n() + 1, ☃.field_78895_b - ☃.func_177956_o() + 1, ☃.field_78896_c - ☃.func_177952_p() + 1
               );
               this.field_184424_i = new BlockPos(
                  ☃.field_78893_d - ☃.field_78897_a - 1, ☃.field_78894_e - ☃.field_78895_b - 1, ☃.field_78892_f - ☃.field_78896_c - 1
               );
               this.func_70296_d();
               IBlockState ☃x = this.field_145850_b.func_180495_p(☃);
               this.field_145850_b.func_184138_a(☃, ☃x, ☃x, 3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private List<TileEntityStructure> func_184415_a(List<TileEntityStructure> var1) {
      Predicate<TileEntityStructure> ☃ = var1x -> var1x.field_184427_l == StructureMode.CORNER && Objects.equals(this.field_184420_a, var1x.field_184420_a);
      return (List<TileEntityStructure>)☃.stream().filter(☃).collect(Collectors.toList());
   }

   private List<TileEntityStructure> func_184418_a(BlockPos var1, BlockPos var2) {
      List<TileEntityStructure> ☃ = Lists.<TileEntityStructure>newArrayList();

      for(BlockPos.MutableBlockPos ☃x : BlockPos.func_177975_b(☃, ☃)) {
         IBlockState ☃xx = this.field_145850_b.func_180495_p(☃x);
         if (☃xx.func_177230_c() == Blocks.field_185779_df) {
            TileEntity ☃xxx = this.field_145850_b.func_175625_s(☃x);
            if (☃xxx != null && ☃xxx instanceof TileEntityStructure) {
               ☃.add((TileEntityStructure)☃xxx);
            }
         }
      }

      return ☃;
   }

   private MutableBoundingBox func_184416_a(BlockPos var1, List<TileEntityStructure> var2) {
      MutableBoundingBox ☃;
      if (☃.size() > 1) {
         BlockPos ☃x = ((TileEntityStructure)☃.get(0)).func_174877_v();
         ☃ = new MutableBoundingBox(☃x, ☃x);
      } else {
         ☃ = new MutableBoundingBox(☃, ☃);
      }

      for(TileEntityStructure ☃ : ☃) {
         BlockPos ☃x = ☃.func_174877_v();
         if (☃x.func_177958_n() < ☃.field_78897_a) {
            ☃.field_78897_a = ☃x.func_177958_n();
         } else if (☃x.func_177958_n() > ☃.field_78893_d) {
            ☃.field_78893_d = ☃x.func_177958_n();
         }

         if (☃x.func_177956_o() < ☃.field_78895_b) {
            ☃.field_78895_b = ☃x.func_177956_o();
         } else if (☃x.func_177956_o() > ☃.field_78894_e) {
            ☃.field_78894_e = ☃x.func_177956_o();
         }

         if (☃x.func_177952_p() < ☃.field_78896_c) {
            ☃.field_78896_c = ☃x.func_177952_p();
         } else if (☃x.func_177952_p() > ☃.field_78892_f) {
            ☃.field_78892_f = ☃x.func_177952_p();
         }
      }

      return ☃;
   }

   public boolean func_184419_m() {
      return this.func_189712_b(true);
   }

   public boolean func_189712_b(boolean var1) {
      if (this.field_184427_l == StructureMode.SAVE && !this.field_145850_b.field_72995_K && this.field_184420_a != null) {
         BlockPos ☃ = this.func_174877_v().func_177971_a(this.field_184423_h);
         WorldServer ☃x = (WorldServer)this.field_145850_b;
         TemplateManager ☃xx = ☃x.func_184163_y();

         Template ☃;
         try {
            ☃ = ☃xx.func_200220_a(this.field_184420_a);
         } catch (ResourceLocationException var8) {
            return false;
         }

         ☃.func_186254_a(this.field_145850_b, ☃, this.field_184424_i, !this.field_184428_m, Blocks.field_189881_dj);
         ☃.func_186252_a(this.field_184421_f);
         if (☃) {
            try {
               return ☃xx.func_195429_b(this.field_184420_a);
            } catch (ResourceLocationException var7) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_184412_n() {
      return this.func_189714_c(true);
   }

   public boolean func_189714_c(boolean var1) {
      if (this.field_184427_l == StructureMode.LOAD && !this.field_145850_b.field_72995_K && this.field_184420_a != null) {
         BlockPos ☃ = this.func_174877_v();
         BlockPos ☃x = ☃.func_177971_a(this.field_184423_h);
         WorldServer ☃xx = (WorldServer)this.field_145850_b;
         TemplateManager ☃xxx = ☃xx.func_184163_y();

         Template ☃;
         try {
            ☃ = ☃xxx.func_200219_b(this.field_184420_a);
         } catch (ResourceLocationException var10) {
            return false;
         }

         if (☃ == null) {
            return false;
         } else {
            if (!StringUtils.func_151246_b(☃.func_186261_b())) {
               this.field_184421_f = ☃.func_186261_b();
            }

            BlockPos ☃xxxx = ☃.func_186259_a();
            boolean ☃xxxxx = this.field_184424_i.equals(☃xxxx);
            if (!☃xxxxx) {
               this.field_184424_i = ☃xxxx;
               this.func_70296_d();
               IBlockState ☃xxxxxx = this.field_145850_b.func_180495_p(☃);
               this.field_145850_b.func_184138_a(☃, ☃xxxxxx, ☃xxxxxx, 3);
            }

            if (☃ && !☃xxxxx) {
               return false;
            } else {
               PlacementSettings ☃xxxx = new PlacementSettings()
                  .func_186214_a(this.field_184425_j)
                  .func_186220_a(this.field_184426_k)
                  .func_186222_a(this.field_184428_m)
                  .func_186218_a(null)
                  .func_186225_a(null)
                  .func_186226_b(false);
               if (this.field_189730_q < 1.0F) {
                  ☃xxxx.func_189946_a(MathHelper.func_76131_a(this.field_189730_q, 0.0F, 1.0F)).func_189949_a(this.field_189731_r);
               }

               ☃.func_186260_a(this.field_145850_b, ☃x, ☃xxxx);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void func_189706_E() {
      if (this.field_184420_a != null) {
         WorldServer ☃ = (WorldServer)this.field_145850_b;
         TemplateManager ☃x = ☃.func_184163_y();
         ☃x.func_189941_a(this.field_184420_a);
      }
   }

   public boolean func_189709_F() {
      if (this.field_184427_l == StructureMode.LOAD && !this.field_145850_b.field_72995_K && this.field_184420_a != null) {
         WorldServer ☃ = (WorldServer)this.field_145850_b;
         TemplateManager ☃x = ☃.func_184163_y();

         try {
            return ☃x.func_200219_b(this.field_184420_a) != null;
         } catch (ResourceLocationException var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean func_189722_G() {
      return this.field_189727_n;
   }

   public void func_189723_d(boolean var1) {
      this.field_189727_n = ☃;
   }

   public boolean func_189707_H() {
      return this.field_189728_o;
   }

   public void func_189703_e(boolean var1) {
      this.field_189728_o = ☃;
   }

   public boolean func_189721_I() {
      return this.field_189729_p;
   }

   public void func_189710_f(boolean var1) {
      this.field_189729_p = ☃;
   }

   public static enum UpdateCommand {
      UPDATE_DATA,
      SAVE_AREA,
      LOAD_AREA,
      SCAN_AREA;
   }
}
