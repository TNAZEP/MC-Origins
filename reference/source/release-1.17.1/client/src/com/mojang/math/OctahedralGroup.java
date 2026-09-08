package com.mojang.math;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.util.StringRepresentable;

public enum OctahedralGroup implements StringRepresentable {
   IDENTITY("identity", SymmetricGroup3.P123, false, false, false),
   ROT_180_FACE_XY("rot_180_face_xy", SymmetricGroup3.P123, true, true, false),
   ROT_180_FACE_XZ("rot_180_face_xz", SymmetricGroup3.P123, true, false, true),
   ROT_180_FACE_YZ("rot_180_face_yz", SymmetricGroup3.P123, false, true, true),
   ROT_120_NNN("rot_120_nnn", SymmetricGroup3.P231, false, false, false),
   ROT_120_NNP("rot_120_nnp", SymmetricGroup3.P312, true, false, true),
   ROT_120_NPN("rot_120_npn", SymmetricGroup3.P312, false, true, true),
   ROT_120_NPP("rot_120_npp", SymmetricGroup3.P231, true, false, true),
   ROT_120_PNN("rot_120_pnn", SymmetricGroup3.P312, true, true, false),
   ROT_120_PNP("rot_120_pnp", SymmetricGroup3.P231, true, true, false),
   ROT_120_PPN("rot_120_ppn", SymmetricGroup3.P231, false, true, true),
   ROT_120_PPP("rot_120_ppp", SymmetricGroup3.P312, false, false, false),
   ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", SymmetricGroup3.P213, true, true, true),
   ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", SymmetricGroup3.P213, false, false, true),
   ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", SymmetricGroup3.P321, true, true, true),
   ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", SymmetricGroup3.P321, false, true, false),
   ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", SymmetricGroup3.P132, true, true, true),
   ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", SymmetricGroup3.P132, true, false, false),
   ROT_90_X_NEG("rot_90_x_neg", SymmetricGroup3.P132, false, false, true),
   ROT_90_X_POS("rot_90_x_pos", SymmetricGroup3.P132, false, true, false),
   ROT_90_Y_NEG("rot_90_y_neg", SymmetricGroup3.P321, true, false, false),
   ROT_90_Y_POS("rot_90_y_pos", SymmetricGroup3.P321, false, false, true),
   ROT_90_Z_NEG("rot_90_z_neg", SymmetricGroup3.P213, false, true, false),
   ROT_90_Z_POS("rot_90_z_pos", SymmetricGroup3.P213, true, false, false),
   INVERSION("inversion", SymmetricGroup3.P123, true, true, true),
   INVERT_X("invert_x", SymmetricGroup3.P123, true, false, false),
   INVERT_Y("invert_y", SymmetricGroup3.P123, false, true, false),
   INVERT_Z("invert_z", SymmetricGroup3.P123, false, false, true),
   ROT_60_REF_NNN("rot_60_ref_nnn", SymmetricGroup3.P312, true, true, true),
   ROT_60_REF_NNP("rot_60_ref_nnp", SymmetricGroup3.P231, true, false, false),
   ROT_60_REF_NPN("rot_60_ref_npn", SymmetricGroup3.P231, false, false, true),
   ROT_60_REF_NPP("rot_60_ref_npp", SymmetricGroup3.P312, false, false, true),
   ROT_60_REF_PNN("rot_60_ref_pnn", SymmetricGroup3.P231, false, true, false),
   ROT_60_REF_PNP("rot_60_ref_pnp", SymmetricGroup3.P312, true, false, false),
   ROT_60_REF_PPN("rot_60_ref_ppn", SymmetricGroup3.P312, false, true, false),
   ROT_60_REF_PPP("rot_60_ref_ppp", SymmetricGroup3.P231, true, true, true),
   SWAP_XY("swap_xy", SymmetricGroup3.P213, false, false, false),
   SWAP_YZ("swap_yz", SymmetricGroup3.P132, false, false, false),
   SWAP_XZ("swap_xz", SymmetricGroup3.P321, false, false, false),
   SWAP_NEG_XY("swap_neg_xy", SymmetricGroup3.P213, true, true, false),
   SWAP_NEG_YZ("swap_neg_yz", SymmetricGroup3.P132, false, true, true),
   SWAP_NEG_XZ("swap_neg_xz", SymmetricGroup3.P321, true, false, true),
   ROT_90_REF_X_NEG("rot_90_ref_x_neg", SymmetricGroup3.P132, true, false, true),
   ROT_90_REF_X_POS("rot_90_ref_x_pos", SymmetricGroup3.P132, true, true, false),
   ROT_90_REF_Y_NEG("rot_90_ref_y_neg", SymmetricGroup3.P321, true, true, false),
   ROT_90_REF_Y_POS("rot_90_ref_y_pos", SymmetricGroup3.P321, false, true, true),
   ROT_90_REF_Z_NEG("rot_90_ref_z_neg", SymmetricGroup3.P213, false, true, true),
   ROT_90_REF_Z_POS("rot_90_ref_z_pos", SymmetricGroup3.P213, true, false, true);

   private final Matrix3f transformation;
   private final String name;
   @Nullable
   private Map<Direction, Direction> rotatedDirections;
   private final boolean invertX;
   private final boolean invertY;
   private final boolean invertZ;
   private final SymmetricGroup3 permutation;
   private static final OctahedralGroup[][] cayleyTable = Util.make(
      new OctahedralGroup[values().length][values().length],
      var0 -> {
         Map<Pair<SymmetricGroup3, BooleanList>, OctahedralGroup> â˜ƒ = (Map)Arrays.stream(values())
            .collect(Collectors.toMap(var0x -> Pair.of(var0x.permutation, var0x.packInversions()), var0x -> var0x));
   
         for(OctahedralGroup â˜ƒx : values()) {
            for(OctahedralGroup â˜ƒxx : values()) {
               BooleanList â˜ƒxxx = â˜ƒx.packInversions();
               BooleanList â˜ƒxxxx = â˜ƒxx.packInversions();
               SymmetricGroup3 â˜ƒxxxxx = â˜ƒxx.permutation.compose(â˜ƒx.permutation);
               BooleanArrayList â˜ƒxxxxxx = new BooleanArrayList(3);
   
               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 3; ++â˜ƒxxxxxxx) {
                  â˜ƒxxxxxx.add(â˜ƒxxx.getBoolean(â˜ƒxxxxxxx) ^ â˜ƒxxxx.getBoolean(â˜ƒx.permutation.permutation(â˜ƒxxxxxxx)));
               }
   
               var0[â˜ƒx.ordinal()][â˜ƒxx.ordinal()] = (OctahedralGroup)â˜ƒ.get(Pair.of(â˜ƒxxxxx, â˜ƒxxxxxx));
            }
         }
      }
   );
   private static final OctahedralGroup[] inverseTable = (OctahedralGroup[])Arrays.stream(values())
      .map(var0 -> (OctahedralGroup)Arrays.stream(values()).filter(var1 -> var0.compose(var1) == IDENTITY).findAny().get())
      .toArray(var0 -> new OctahedralGroup[var0]);

   private OctahedralGroup(String var3, SymmetricGroup3 var4, boolean var5, boolean var6, boolean var7) {
      this.name = â˜ƒ;
      this.invertX = â˜ƒ;
      this.invertY = â˜ƒ;
      this.invertZ = â˜ƒ;
      this.permutation = â˜ƒ;
      this.transformation = new Matrix3f();
      this.transformation.m00 = â˜ƒ ? -1.0F : 1.0F;
      this.transformation.m11 = â˜ƒ ? -1.0F : 1.0F;
      this.transformation.m22 = â˜ƒ ? -1.0F : 1.0F;
      this.transformation.mul(â˜ƒ.transformation());
   }

   private BooleanList packInversions() {
      return new BooleanArrayList(new boolean[]{this.invertX, this.invertY, this.invertZ});
   }

   public OctahedralGroup compose(OctahedralGroup var1) {
      return cayleyTable[this.ordinal()][â˜ƒ.ordinal()];
   }

   public OctahedralGroup inverse() {
      return inverseTable[this.ordinal()];
   }

   public Matrix3f transformation() {
      return this.transformation.copy();
   }

   public String toString() {
      return this.name;
   }

   @Override
   public String getSerializedName() {
      return this.name;
   }

   public Direction rotate(Direction var1) {
      if (this.rotatedDirections == null) {
         this.rotatedDirections = Maps.newEnumMap(Direction.class);

         for(Direction â˜ƒ : Direction.values()) {
            Direction.Axis â˜ƒx = â˜ƒ.getAxis();
            Direction.AxisDirection â˜ƒxx = â˜ƒ.getAxisDirection();
            Direction.Axis â˜ƒxxx = Direction.Axis.values()[this.permutation.permutation(â˜ƒx.ordinal())];
            Direction.AxisDirection â˜ƒxxxx = this.inverts(â˜ƒxxx) ? â˜ƒxx.opposite() : â˜ƒxx;
            Direction â˜ƒxxxxx = Direction.fromAxisAndDirection(â˜ƒxxx, â˜ƒxxxx);
            this.rotatedDirections.put(â˜ƒ, â˜ƒxxxxx);
         }
      }

      return (Direction)this.rotatedDirections.get(â˜ƒ);
   }

   public boolean inverts(Direction.Axis var1) {
      switch(â˜ƒ) {
         case X:
            return this.invertX;
         case Y:
            return this.invertY;
         case Z:
         default:
            return this.invertZ;
      }
   }

   public FrontAndTop rotate(FrontAndTop var1) {
      return FrontAndTop.fromFrontAndTop(this.rotate(â˜ƒ.front()), this.rotate(â˜ƒ.top()));
   }
}
