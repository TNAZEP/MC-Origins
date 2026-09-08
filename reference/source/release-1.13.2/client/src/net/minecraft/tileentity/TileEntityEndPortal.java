package net.minecraft.tileentity;

import net.minecraft.util.EnumFacing;

public class TileEntityEndPortal extends TileEntity {
   public TileEntityEndPortal(TileEntityType<?> var1) {
      super(☃);
   }

   public TileEntityEndPortal() {
      this(TileEntityType.field_200983_n);
   }

   public boolean func_184313_a(EnumFacing var1) {
      return ☃ == EnumFacing.UP;
   }
}
