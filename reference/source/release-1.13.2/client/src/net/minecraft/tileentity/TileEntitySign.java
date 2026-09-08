package net.minecraft.tileentity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.WorldServer;

public class TileEntitySign extends TileEntity implements ICommandSource {
   public final ITextComponent[] field_145915_a = new ITextComponent[]{
      new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")
   };
   public int field_145918_i = -1;
   private boolean field_145916_j = true;
   private EntityPlayer field_145917_k;
   private final String[] field_212367_h = new String[4];

   public TileEntitySign() {
      super(TileEntityType.field_200978_i);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);

      for(int ☃ = 0; ☃ < 4; ++☃) {
         String ☃x = ITextComponent.Serializer.func_150696_a(this.field_145915_a[☃]);
         ☃.func_74778_a("Text" + (☃ + 1), ☃x);
      }

      return ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      this.field_145916_j = false;
      super.func_145839_a(☃);

      for(int ☃ = 0; ☃ < 4; ++☃) {
         String ☃x = ☃.func_74779_i("Text" + (☃ + 1));
         ITextComponent ☃xx = ITextComponent.Serializer.func_150699_a(☃x);
         if (this.field_145850_b instanceof WorldServer) {
            try {
               this.field_145915_a[☃] = TextComponentUtils.func_197680_a(this.func_195539_a(null), ☃xx, null);
            } catch (CommandSyntaxException var6) {
               this.field_145915_a[☃] = ☃xx;
            }
         } else {
            this.field_145915_a[☃] = ☃xx;
         }

         this.field_212367_h[☃] = null;
      }
   }

   public ITextComponent func_212366_a(int var1) {
      return this.field_145915_a[☃];
   }

   public void func_212365_a(int var1, ITextComponent var2) {
      this.field_145915_a[☃] = ☃;
      this.field_212367_h[☃] = null;
   }

   @Nullable
   public String func_212364_a(int var1, Function<ITextComponent, String> var2) {
      if (this.field_212367_h[☃] == null && this.field_145915_a[☃] != null) {
         this.field_212367_h[☃] = (String)☃.apply(this.field_145915_a[☃]);
      }

      return this.field_212367_h[☃];
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 9, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   @Override
   public boolean func_183000_F() {
      return true;
   }

   public boolean func_145914_a() {
      return this.field_145916_j;
   }

   public void func_145913_a(boolean var1) {
      this.field_145916_j = ☃;
      if (!☃) {
         this.field_145917_k = null;
      }
   }

   public void func_145912_a(EntityPlayer var1) {
      this.field_145917_k = ☃;
   }

   public EntityPlayer func_145911_b() {
      return this.field_145917_k;
   }

   public boolean func_174882_b(EntityPlayer var1) {
      for(ITextComponent ☃ : this.field_145915_a) {
         Style ☃x = ☃ == null ? null : ☃.func_150256_b();
         if (☃x != null && ☃x.func_150235_h() != null) {
            ClickEvent ☃xx = ☃x.func_150235_h();
            if (☃xx.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
               ☃.func_184102_h().func_195571_aL().func_197059_a(this.func_195539_a((EntityPlayerMP)☃), ☃xx.func_150668_b());
            }
         }
      }

      return true;
   }

   @Override
   public void func_145747_a(ITextComponent var1) {
   }

   public CommandSource func_195539_a(@Nullable EntityPlayerMP var1) {
      String ☃ = ☃ == null ? "Sign" : ☃.func_200200_C_().getString();
      ITextComponent ☃x = (ITextComponent)(☃ == null ? new TextComponentString("Sign") : ☃.func_145748_c_());
      return new CommandSource(
         this,
         new Vec3d(
            (double)this.field_174879_c.func_177958_n() + 0.5,
            (double)this.field_174879_c.func_177956_o() + 0.5,
            (double)this.field_174879_c.func_177952_p() + 0.5
         ),
         Vec2f.field_189974_a,
         (WorldServer)this.field_145850_b,
         2,
         ☃,
         ☃x,
         this.field_145850_b.func_73046_m(),
         ☃
      );
   }

   @Override
   public boolean func_195039_a() {
      return false;
   }

   @Override
   public boolean func_195040_b() {
      return false;
   }

   @Override
   public boolean func_195041_r_() {
      return false;
   }
}
