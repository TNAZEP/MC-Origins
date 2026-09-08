package com.mojang.math;

import java.util.Arrays;
import net.minecraft.Util;

public enum SymmetricGroup3 {
   P123(0, 1, 2),
   P213(1, 0, 2),
   P132(0, 2, 1),
   P231(1, 2, 0),
   P312(2, 0, 1),
   P321(2, 1, 0);

   private final int[] permutation;
   private final Matrix3f transformation;
   private static final int ORDER = 3;
   private static final SymmetricGroup3[][] cayleyTable = Util.make(new SymmetricGroup3[values().length][values().length], var0 -> {
      for(SymmetricGroup3 â˜ƒ : values()) {
         for(SymmetricGroup3 â˜ƒx : values()) {
            int[] â˜ƒxx = new int[3];

            for(int â˜ƒxxx = 0; â˜ƒxxx < 3; ++â˜ƒxxx) {
               â˜ƒxx[â˜ƒxxx] = â˜ƒ.permutation[â˜ƒx.permutation[â˜ƒxxx]];
            }

            SymmetricGroup3 â˜ƒxxx = (SymmetricGroup3)Arrays.stream(values()).filter(var1 -> Arrays.equals(var1.permutation, â˜ƒ)).findFirst().get();
            var0[â˜ƒ.ordinal()][â˜ƒx.ordinal()] = â˜ƒxxx;
         }
      }
   });

   private SymmetricGroup3(int var3, int var4, int var5) {
      this.permutation = new int[]{â˜ƒ, â˜ƒ, â˜ƒ};
      this.transformation = new Matrix3f();
      this.transformation.set(0, this.permutation(0), 1.0F);
      this.transformation.set(1, this.permutation(1), 1.0F);
      this.transformation.set(2, this.permutation(2), 1.0F);
   }

   public SymmetricGroup3 compose(SymmetricGroup3 var1) {
      return cayleyTable[this.ordinal()][â˜ƒ.ordinal()];
   }

   public int permutation(int var1) {
      return this.permutation[â˜ƒ];
   }

   public Matrix3f transformation() {
      return this.transformation;
   }
}
