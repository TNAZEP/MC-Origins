package com.mojang.blaze3d.audio;

import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;

public class Listener {
   private float gain = 1.0F;
   private Vec3 position = Vec3.ZERO;

   public void setListenerPosition(Vec3 var1) {
      this.position = â˜ƒ;
      AL10.alListener3f(4100, (float)â˜ƒ.x, (float)â˜ƒ.y, (float)â˜ƒ.z);
   }

   public Vec3 getListenerPosition() {
      return this.position;
   }

   public void setListenerOrientation(Vector3f var1, Vector3f var2) {
      AL10.alListenerfv(4111, new float[]{â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z(), â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z()});
   }

   public void setGain(float var1) {
      AL10.alListenerf(4106, â˜ƒ);
      this.gain = â˜ƒ;
   }

   public float getGain() {
      return this.gain;
   }

   public void reset() {
      this.setListenerPosition(Vec3.ZERO);
      this.setListenerOrientation(Vector3f.ZN, Vector3f.YP);
   }
}
