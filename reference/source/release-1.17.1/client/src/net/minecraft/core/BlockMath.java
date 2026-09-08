package net.minecraft.core;

import com.google.common.collect.Maps;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockMath {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Map<Direction, Transformation> VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL = Util.make(Maps.newEnumMap(Direction.class), var0 -> {
      var0.put(Direction.SOUTH, Transformation.identity());
      var0.put(Direction.EAST, new Transformation(null, Vector3f.YP.rotationDegrees(90.0F), null, null));
      var0.put(Direction.WEST, new Transformation(null, Vector3f.YP.rotationDegrees(-90.0F), null, null));
      var0.put(Direction.NORTH, new Transformation(null, Vector3f.YP.rotationDegrees(180.0F), null, null));
      var0.put(Direction.UP, new Transformation(null, Vector3f.XP.rotationDegrees(-90.0F), null, null));
      var0.put(Direction.DOWN, new Transformation(null, Vector3f.XP.rotationDegrees(90.0F), null, null));
   });
   public static final Map<Direction, Transformation> VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL = Util.make(Maps.newEnumMap(Direction.class), var0 -> {
      for(Direction â˜ƒ : Direction.values()) {
         var0.put(â˜ƒ, ((Transformation)VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL.get(â˜ƒ)).inverse());
      }
   });

   public static Transformation blockCenterToCorner(Transformation var0) {
      Matrix4f â˜ƒ = Matrix4f.createTranslateMatrix(0.5F, 0.5F, 0.5F);
      â˜ƒ.multiply(â˜ƒ.getMatrix());
      â˜ƒ.multiply(Matrix4f.createTranslateMatrix(-0.5F, -0.5F, -0.5F));
      return new Transformation(â˜ƒ);
   }

   public static Transformation blockCornerToCenter(Transformation var0) {
      Matrix4f â˜ƒ = Matrix4f.createTranslateMatrix(-0.5F, -0.5F, -0.5F);
      â˜ƒ.multiply(â˜ƒ.getMatrix());
      â˜ƒ.multiply(Matrix4f.createTranslateMatrix(0.5F, 0.5F, 0.5F));
      return new Transformation(â˜ƒ);
   }

   public static Transformation getUVLockTransform(Transformation var0, Direction var1, Supplier<String> var2) {
      Direction â˜ƒ = Direction.rotate(â˜ƒ.getMatrix(), â˜ƒ);
      Transformation â˜ƒx = â˜ƒ.inverse();
      if (â˜ƒx == null) {
         LOGGER.warn((String)â˜ƒ.get());
         return new Transformation(null, null, new Vector3f(0.0F, 0.0F, 0.0F), null);
      } else {
         Transformation â˜ƒ = ((Transformation)VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL.get(â˜ƒ))
            .compose(â˜ƒx)
            .compose((Transformation)VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL.get(â˜ƒ));
         return blockCenterToCorner(â˜ƒ);
      }
   }
}
