package net.minecraft.world.gen.feature.template;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;

public class PlacementSettings {
   private Mirror field_186228_a = Mirror.NONE;
   private Rotation field_186229_b = Rotation.NONE;
   private BlockPos field_207666_c = new BlockPos(0, 0, 0);
   private boolean field_186230_c;
   @Nullable
   private Block field_186231_d;
   @Nullable
   private ChunkPos field_186232_e;
   @Nullable
   private MutableBoundingBox field_186233_f;
   private boolean field_186234_g = true;
   private boolean field_204765_h = true;
   private float field_189951_h = 1.0F;
   @Nullable
   private Random field_189952_i;
   @Nullable
   private Long field_189953_j;
   @Nullable
   private Integer field_204766_l;
   private int field_204767_m;

   public PlacementSettings func_186217_a() {
      PlacementSettings ☃ = new PlacementSettings();
      ☃.field_186228_a = this.field_186228_a;
      ☃.field_186229_b = this.field_186229_b;
      ☃.field_207666_c = this.field_207666_c;
      ☃.field_186230_c = this.field_186230_c;
      ☃.field_186231_d = this.field_186231_d;
      ☃.field_186232_e = this.field_186232_e;
      ☃.field_186233_f = this.field_186233_f;
      ☃.field_186234_g = this.field_186234_g;
      ☃.field_204765_h = this.field_204765_h;
      ☃.field_189951_h = this.field_189951_h;
      ☃.field_189952_i = this.field_189952_i;
      ☃.field_189953_j = this.field_189953_j;
      ☃.field_204766_l = this.field_204766_l;
      ☃.field_204767_m = this.field_204767_m;
      return ☃;
   }

   public PlacementSettings func_186214_a(Mirror var1) {
      this.field_186228_a = ☃;
      return this;
   }

   public PlacementSettings func_186220_a(Rotation var1) {
      this.field_186229_b = ☃;
      return this;
   }

   public PlacementSettings func_207665_a(BlockPos var1) {
      this.field_207666_c = ☃;
      return this;
   }

   public PlacementSettings func_186222_a(boolean var1) {
      this.field_186230_c = ☃;
      return this;
   }

   public PlacementSettings func_186225_a(Block var1) {
      this.field_186231_d = ☃;
      return this;
   }

   public PlacementSettings func_186218_a(ChunkPos var1) {
      this.field_186232_e = ☃;
      return this;
   }

   public PlacementSettings func_186223_a(MutableBoundingBox var1) {
      this.field_186233_f = ☃;
      return this;
   }

   public PlacementSettings func_189949_a(@Nullable Long var1) {
      this.field_189953_j = ☃;
      return this;
   }

   public PlacementSettings func_189950_a(@Nullable Random var1) {
      this.field_189952_i = ☃;
      return this;
   }

   public PlacementSettings func_189946_a(float var1) {
      this.field_189951_h = ☃;
      return this;
   }

   public Mirror func_186212_b() {
      return this.field_186228_a;
   }

   public PlacementSettings func_186226_b(boolean var1) {
      this.field_186234_g = ☃;
      return this;
   }

   public Rotation func_186215_c() {
      return this.field_186229_b;
   }

   public BlockPos func_207664_d() {
      return this.field_207666_c;
   }

   public Random func_189947_a(@Nullable BlockPos var1) {
      if (this.field_189952_i != null) {
         return this.field_189952_i;
      } else if (this.field_189953_j != null) {
         return this.field_189953_j == 0L ? new Random(Util.func_211177_b()) : new Random(this.field_189953_j);
      } else {
         return ☃ == null ? new Random(Util.func_211177_b()) : SharedSeedRandom.func_205190_a(☃.func_177958_n(), ☃.func_177952_p(), 0L, 987234911L);
      }
   }

   public float func_189948_f() {
      return this.field_189951_h;
   }

   public boolean func_186221_e() {
      return this.field_186230_c;
   }

   @Nullable
   public Block func_186219_f() {
      return this.field_186231_d;
   }

   @Nullable
   public MutableBoundingBox func_186213_g() {
      if (this.field_186233_f == null && this.field_186232_e != null) {
         this.func_186224_i();
      }

      return this.field_186233_f;
   }

   public boolean func_186227_h() {
      return this.field_186234_g;
   }

   void func_186224_i() {
      if (this.field_186232_e != null) {
         this.field_186233_f = this.func_186216_b(this.field_186232_e);
      }
   }

   public boolean func_204763_l() {
      return this.field_204765_h;
   }

   public List<Template.BlockInfo> func_204764_a(List<List<Template.BlockInfo>> var1, @Nullable BlockPos var2) {
      this.field_204766_l = 8;
      if (this.field_204766_l != null && this.field_204766_l >= 0 && this.field_204766_l < ☃.size()) {
         return (List<Template.BlockInfo>)☃.get(this.field_204766_l);
      } else {
         this.field_204766_l = this.func_189947_a(☃).nextInt(☃.size());
         return (List<Template.BlockInfo>)☃.get(this.field_204766_l);
      }
   }

   @Nullable
   private MutableBoundingBox func_186216_b(@Nullable ChunkPos var1) {
      if (☃ == null) {
         return this.field_186233_f;
      } else {
         int ☃ = ☃.field_77276_a * 16;
         int ☃x = ☃.field_77275_b * 16;
         return new MutableBoundingBox(☃, 0, ☃x, ☃ + 16 - 1, 255, ☃x + 16 - 1);
      }
   }
}
