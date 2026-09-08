package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.ITickable;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TileEntitySkull extends TileEntity implements ITickable {
   private GameProfile field_152110_j;
   private int field_184296_h;
   private boolean field_184297_i;
   private boolean field_195488_h = true;
   private static PlayerProfileCache field_184298_j;
   private static MinecraftSessionService field_184299_k;

   public TileEntitySkull() {
      super(TileEntityType.field_200985_p);
   }

   public static void func_184293_a(PlayerProfileCache var0) {
      field_184298_j = ☃;
   }

   public static void func_184294_a(MinecraftSessionService var0) {
      field_184299_k = ☃;
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      if (this.field_152110_j != null) {
         NBTTagCompound ☃ = new NBTTagCompound();
         NBTUtil.func_180708_a(☃, this.field_152110_j);
         ☃.func_74782_a("Owner", ☃);
      }

      return ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      if (☃.func_150297_b("Owner", 10)) {
         this.func_195485_a(NBTUtil.func_152459_a(☃.func_74775_l("Owner")));
      } else if (☃.func_150297_b("ExtraType", 8)) {
         String ☃ = ☃.func_74779_i("ExtraType");
         if (!StringUtils.func_151246_b(☃)) {
            this.func_195485_a(new GameProfile(null, ☃));
         }
      }
   }

   @Override
   public void func_73660_a() {
      Block ☃ = this.func_195044_w().func_177230_c();
      if (☃ == Blocks.field_196716_eW || ☃ == Blocks.field_196715_eV) {
         if (this.field_145850_b.func_175640_z(this.field_174879_c)) {
            this.field_184297_i = true;
            ++this.field_184296_h;
         } else {
            this.field_184297_i = false;
         }
      }
   }

   public float func_184295_a(float var1) {
      return this.field_184297_i ? (float)this.field_184296_h + ☃ : (float)this.field_184296_h;
   }

   @Nullable
   public GameProfile func_152108_a() {
      return this.field_152110_j;
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 4, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public void func_195485_a(@Nullable GameProfile var1) {
      this.field_152110_j = ☃;
      this.func_152109_d();
   }

   private void func_152109_d() {
      this.field_152110_j = func_174884_b(this.field_152110_j);
      this.func_70296_d();
   }

   public static GameProfile func_174884_b(GameProfile var0) {
      if (☃ != null && !StringUtils.func_151246_b(☃.getName())) {
         if (☃.isComplete() && ☃.getProperties().containsKey("textures")) {
            return ☃;
         } else if (field_184298_j != null && field_184299_k != null) {
            GameProfile ☃ = field_184298_j.func_152655_a(☃.getName());
            if (☃ == null) {
               return ☃;
            } else {
               Property ☃ = Iterables.getFirst(☃.getProperties().get("textures"), null);
               if (☃ == null) {
                  ☃ = field_184299_k.fillProfileProperties(☃, true);
               }

               return ☃;
            }
         } else {
            return ☃;
         }
      } else {
         return ☃;
      }
   }

   public static void func_195486_a(IBlockReader var0, BlockPos var1) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntitySkull) {
         TileEntitySkull ☃x = (TileEntitySkull)☃;
         ☃x.field_195488_h = false;
      }
   }

   public boolean func_195487_d() {
      return this.field_195488_h;
   }
}
