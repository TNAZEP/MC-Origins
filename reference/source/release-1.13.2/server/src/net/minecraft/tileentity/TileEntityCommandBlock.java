package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;

public class TileEntityCommandBlock extends TileEntity {
   private boolean field_184259_a;
   private boolean field_184260_f;
   private boolean field_184261_g;
   private boolean field_184262_h;
   private final CommandBlockBaseLogic field_145994_a = new CommandBlockBaseLogic() {
      @Override
      public void func_145752_a(String var1) {
         super.func_145752_a(☃);
         TileEntityCommandBlock.this.func_70296_d();
      }

      @Override
      public WorldServer func_195043_d() {
         return (WorldServer)TileEntityCommandBlock.this.field_145850_b;
      }

      @Override
      public void func_145756_e() {
         IBlockState ☃ = TileEntityCommandBlock.this.field_145850_b.func_180495_p(TileEntityCommandBlock.this.field_174879_c);
         this.func_195043_d().func_184138_a(TileEntityCommandBlock.this.field_174879_c, ☃, ☃, 3);
      }

      @Override
      public CommandSource func_195042_h() {
         return new CommandSource(
            this,
            new Vec3d(
               (double)TileEntityCommandBlock.this.field_174879_c.func_177958_n() + 0.5,
               (double)TileEntityCommandBlock.this.field_174879_c.func_177956_o() + 0.5,
               (double)TileEntityCommandBlock.this.field_174879_c.func_177952_p() + 0.5
            ),
            Vec2f.field_189974_a,
            this.func_195043_d(),
            2,
            this.func_207404_l().getString(),
            this.func_207404_l(),
            this.func_195043_d().func_73046_m(),
            null
         );
      }
   };

   public TileEntityCommandBlock() {
      super(TileEntityType.field_200992_w);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      this.field_145994_a.func_189510_a(☃);
      ☃.func_74757_a("powered", this.func_184255_d());
      ☃.func_74757_a("conditionMet", this.func_184256_g());
      ☃.func_74757_a("auto", this.func_184254_e());
      return ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145994_a.func_145759_b(☃);
      this.field_184259_a = ☃.func_74767_n("powered");
      this.field_184261_g = ☃.func_74767_n("conditionMet");
      this.func_184253_b(☃.func_74767_n("auto"));
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      if (this.func_184257_h()) {
         this.func_184252_d(false);
         NBTTagCompound ☃ = this.func_189515_b(new NBTTagCompound());
         return new SPacketUpdateTileEntity(this.field_174879_c, 2, ☃);
      } else {
         return null;
      }
   }

   @Override
   public boolean func_183000_F() {
      return true;
   }

   public CommandBlockBaseLogic func_145993_a() {
      return this.field_145994_a;
   }

   public void func_184250_a(boolean var1) {
      this.field_184259_a = ☃;
   }

   public boolean func_184255_d() {
      return this.field_184259_a;
   }

   public boolean func_184254_e() {
      return this.field_184260_f;
   }

   public void func_184253_b(boolean var1) {
      boolean ☃ = this.field_184260_f;
      this.field_184260_f = ☃;
      if (!☃ && ☃ && !this.field_184259_a && this.field_145850_b != null && this.func_184251_i() != TileEntityCommandBlock.Mode.SEQUENCE) {
         Block ☃x = this.func_195044_w().func_177230_c();
         if (☃x instanceof BlockCommandBlock) {
            this.func_184249_c();
            this.field_145850_b.func_205220_G_().func_205360_a(this.field_174879_c, ☃x, ☃x.func_149738_a(this.field_145850_b));
         }
      }
   }

   public boolean func_184256_g() {
      return this.field_184261_g;
   }

   public boolean func_184249_c() {
      this.field_184261_g = true;
      if (this.func_184258_j()) {
         BlockPos ☃ = this.field_174879_c
            .func_177972_a(((EnumFacing)this.field_145850_b.func_180495_p(this.field_174879_c).func_177229_b(BlockCommandBlock.field_185564_a)).func_176734_d());
         if (this.field_145850_b.func_180495_p(☃).func_177230_c() instanceof BlockCommandBlock) {
            TileEntity ☃x = this.field_145850_b.func_175625_s(☃);
            this.field_184261_g = ☃x instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)☃x).func_145993_a().func_145760_g() > 0;
         } else {
            this.field_184261_g = false;
         }
      }

      return this.field_184261_g;
   }

   public boolean func_184257_h() {
      return this.field_184262_h;
   }

   public void func_184252_d(boolean var1) {
      this.field_184262_h = ☃;
   }

   public TileEntityCommandBlock.Mode func_184251_i() {
      Block ☃ = this.func_195044_w().func_177230_c();
      if (☃ == Blocks.field_150483_bI) {
         return TileEntityCommandBlock.Mode.REDSTONE;
      } else if (☃ == Blocks.field_185776_dc) {
         return TileEntityCommandBlock.Mode.AUTO;
      } else {
         return ☃ == Blocks.field_185777_dd ? TileEntityCommandBlock.Mode.SEQUENCE : TileEntityCommandBlock.Mode.REDSTONE;
      }
   }

   public boolean func_184258_j() {
      IBlockState ☃ = this.field_145850_b.func_180495_p(this.func_174877_v());
      return ☃.func_177230_c() instanceof BlockCommandBlock ? ☃.func_177229_b(BlockCommandBlock.field_185565_b) : false;
   }

   @Override
   public void func_145829_t() {
      this.func_145836_u();
      super.func_145829_t();
   }

   public static enum Mode {
      SEQUENCE,
      AUTO,
      REDSTONE;
   }
}
