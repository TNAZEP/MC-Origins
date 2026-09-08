package com.mojang.blaze3d.audio;

import java.nio.ByteBuffer;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.openal.AL10;

public class SoundBuffer {
   @Nullable
   private ByteBuffer data;
   private final AudioFormat format;
   private boolean hasAlBuffer;
   private int alBuffer;

   public SoundBuffer(ByteBuffer var1, AudioFormat var2) {
      this.data = â˜ƒ;
      this.format = â˜ƒ;
   }

   OptionalInt getAlBuffer() {
      if (!this.hasAlBuffer) {
         if (this.data == null) {
            return OptionalInt.empty();
         }

         int â˜ƒ = OpenAlUtil.audioFormatToOpenAl(this.format);
         int[] â˜ƒx = new int[1];
         AL10.alGenBuffers(â˜ƒx);
         if (OpenAlUtil.checkALError("Creating buffer")) {
            return OptionalInt.empty();
         }

         AL10.alBufferData(â˜ƒx[0], â˜ƒ, this.data, (int)this.format.getSampleRate());
         if (OpenAlUtil.checkALError("Assigning buffer data")) {
            return OptionalInt.empty();
         }

         this.alBuffer = â˜ƒx[0];
         this.hasAlBuffer = true;
         this.data = null;
      }

      return OptionalInt.of(this.alBuffer);
   }

   public void discardAlBuffer() {
      if (this.hasAlBuffer) {
         AL10.alDeleteBuffers(new int[]{this.alBuffer});
         if (OpenAlUtil.checkALError("Deleting stream buffers")) {
            return;
         }
      }

      this.hasAlBuffer = false;
   }

   public OptionalInt releaseAlBuffer() {
      OptionalInt â˜ƒ = this.getAlBuffer();
      this.hasAlBuffer = false;
      return â˜ƒ;
   }
}
