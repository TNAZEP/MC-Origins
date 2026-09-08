package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;

public class MapFrame {
   private final BlockPos field_212771_a;
   private int field_212772_b;
   private int field_212773_c;

   public MapFrame(BlockPos var1, int var2, int var3) {
      this.field_212771_a = ☃;
      this.field_212772_b = ☃;
      this.field_212773_c = ☃;
   }

   public static MapFrame func_212765_a(NBTTagCompound var0) {
      BlockPos ☃ = NBTUtil.func_186861_c(☃.func_74775_l("Pos"));
      int ☃x = ☃.func_74762_e("Rotation");
      int ☃xx = ☃.func_74762_e("EntityId");
      return new MapFrame(☃, ☃x, ☃xx);
   }

   public NBTTagCompound func_212770_a() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74782_a("Pos", NBTUtil.func_186859_a(this.field_212771_a));
      ☃.func_74768_a("Rotation", this.field_212772_b);
      ☃.func_74768_a("EntityId", this.field_212773_c);
      return ☃;
   }

   public BlockPos func_212764_b() {
      return this.field_212771_a;
   }

   public int func_212768_c() {
      return this.field_212772_b;
   }

   public int func_212769_d() {
      return this.field_212773_c;
   }

   public String func_212767_e() {
      return func_212766_a(this.field_212771_a);
   }

   public static String func_212766_a(BlockPos var0) {
      return "frame-" + ☃.func_177958_n() + "," + ☃.func_177956_o() + "," + ☃.func_177952_p();
   }
}
