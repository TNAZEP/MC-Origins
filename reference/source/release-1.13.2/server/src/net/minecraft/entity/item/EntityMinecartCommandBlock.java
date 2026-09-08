package net.minecraft.entity.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityMinecartCommandBlock extends EntityMinecart {
   private static final DataParameter<String> field_184273_a = EntityDataManager.func_187226_a(EntityMinecartCommandBlock.class, DataSerializers.field_187194_d);
   private static final DataParameter<ITextComponent> field_184274_b = EntityDataManager.func_187226_a(
      EntityMinecartCommandBlock.class, DataSerializers.field_187195_e
   );
   private final CommandBlockBaseLogic field_145824_a = new EntityMinecartCommandBlock.MinecartCommandLogic();
   private int field_145823_b;

   public EntityMinecartCommandBlock(World var1) {
      super(EntityType.field_200774_N, ☃);
   }

   public EntityMinecartCommandBlock(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200774_N, ☃, ☃, ☃, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.func_184212_Q().func_187214_a(field_184273_a, "");
      this.func_184212_Q().func_187214_a(field_184274_b, new TextComponentString(""));
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_145824_a.func_145759_b(☃);
      this.func_184212_Q().func_187227_b(field_184273_a, this.func_145822_e().func_145753_i());
      this.func_184212_Q().func_187227_b(field_184274_b, this.func_145822_e().func_145749_h());
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      this.field_145824_a.func_189510_a(☃);
   }

   @Override
   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.COMMAND_BLOCK;
   }

   @Override
   public IBlockState func_180457_u() {
      return Blocks.field_150483_bI.func_176223_P();
   }

   public CommandBlockBaseLogic func_145822_e() {
      return this.field_145824_a;
   }

   @Override
   public void func_96095_a(int var1, int var2, int var3, boolean var4) {
      if (☃ && this.field_70173_aa - this.field_145823_b >= 4) {
         this.func_145822_e().func_145755_a(this.field_70170_p);
         this.field_145823_b = this.field_70173_aa;
      }
   }

   @Override
   public boolean func_184230_a(EntityPlayer var1, EnumHand var2) {
      this.field_145824_a.func_175574_a(☃);
      return true;
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      super.func_184206_a(☃);
      if (field_184274_b.equals(☃)) {
         try {
            this.field_145824_a.func_145750_b(this.func_184212_Q().func_187225_a(field_184274_b));
         } catch (Throwable var3) {
         }
      } else if (field_184273_a.equals(☃)) {
         this.field_145824_a.func_145752_a(this.func_184212_Q().func_187225_a(field_184273_a));
      }
   }

   @Override
   public boolean func_184213_bq() {
      return true;
   }

   public class MinecartCommandLogic extends CommandBlockBaseLogic {
      @Override
      public WorldServer func_195043_d() {
         return (WorldServer)EntityMinecartCommandBlock.this.field_70170_p;
      }

      @Override
      public void func_145756_e() {
         EntityMinecartCommandBlock.this.func_184212_Q().func_187227_b(EntityMinecartCommandBlock.field_184273_a, this.func_145753_i());
         EntityMinecartCommandBlock.this.func_184212_Q().func_187227_b(EntityMinecartCommandBlock.field_184274_b, this.func_145749_h());
      }

      @Override
      public CommandSource func_195042_h() {
         return new CommandSource(
            this,
            new Vec3d(
               EntityMinecartCommandBlock.this.field_70165_t, EntityMinecartCommandBlock.this.field_70163_u, EntityMinecartCommandBlock.this.field_70161_v
            ),
            EntityMinecartCommandBlock.this.func_189653_aC(),
            this.func_195043_d(),
            2,
            this.func_207404_l().getString(),
            EntityMinecartCommandBlock.this.func_145748_c_(),
            this.func_195043_d().func_73046_m(),
            EntityMinecartCommandBlock.this
         );
      }
   }
}
