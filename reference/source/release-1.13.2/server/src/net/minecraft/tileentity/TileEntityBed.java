package net.minecraft.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileEntityBed extends TileEntity {
   private EnumDyeColor field_193053_a;

   public TileEntityBed() {
      super(TileEntityType.field_200994_y);
   }

   public TileEntityBed(EnumDyeColor var1) {
      this();
      this.func_193052_a(☃);
   }

   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 11, this.func_189517_E_());
   }

   public void func_193052_a(EnumDyeColor var1) {
      this.field_193053_a = ☃;
   }
}
