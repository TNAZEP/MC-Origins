package net.minecraft.client.resources.model;

import com.mojang.math.OctahedralGroup;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.Mth;

public enum BlockModelRotation implements ModelState {
   X0_Y0(0, 0),
   X0_Y90(0, 90),
   X0_Y180(0, 180),
   X0_Y270(0, 270),
   X90_Y0(90, 0),
   X90_Y90(90, 90),
   X90_Y180(90, 180),
   X90_Y270(90, 270),
   X180_Y0(180, 0),
   X180_Y90(180, 90),
   X180_Y180(180, 180),
   X180_Y270(180, 270),
   X270_Y0(270, 0),
   X270_Y90(270, 90),
   X270_Y180(270, 180),
   X270_Y270(270, 270);

   private static final int DEGREES = 360;
   private static final Map<Integer, BlockModelRotation> BY_INDEX = (Map<Integer, BlockModelRotation>)Arrays.stream(values())
      .collect(Collectors.toMap(var0 -> var0.index, var0 -> var0));
   private final Transformation transformation;
   private final OctahedralGroup actualRotation;
   private final int index;

   private static int getIndex(int var0, int var1) {
      return â˜ƒ * 360 + â˜ƒ;
   }

   private BlockModelRotation(int var3, int var4) {
      this.index = getIndex(â˜ƒ, â˜ƒ);
      Quaternion â˜ƒ = Vector3f.YP.rotationDegrees((float)(-â˜ƒ));
      â˜ƒ.mul(Vector3f.XP.rotationDegrees((float)(-â˜ƒ)));
      OctahedralGroup â˜ƒx = OctahedralGroup.IDENTITY;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; â˜ƒxx += 90) {
         â˜ƒx = â˜ƒx.compose(OctahedralGroup.ROT_90_Y_NEG);
      }

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; â˜ƒxx += 90) {
         â˜ƒx = â˜ƒx.compose(OctahedralGroup.ROT_90_X_NEG);
      }

      this.transformation = new Transformation(null, â˜ƒ, null, null);
      this.actualRotation = â˜ƒx;
   }

   @Override
   public Transformation getRotation() {
      return this.transformation;
   }

   public static BlockModelRotation by(int var0, int var1) {
      return (BlockModelRotation)BY_INDEX.get(getIndex(Mth.positiveModulo(â˜ƒ, 360), Mth.positiveModulo(â˜ƒ, 360)));
   }

   public OctahedralGroup actualRotation() {
      return this.actualRotation;
   }
}
