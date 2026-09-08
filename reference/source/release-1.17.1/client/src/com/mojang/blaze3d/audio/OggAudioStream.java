package com.mojang.blaze3d.audio;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.util.Mth;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class OggAudioStream implements AudioStream {
   private static final int EXPECTED_MAX_FRAME_SIZE = 8192;
   private long handle;
   private final AudioFormat audioFormat;
   private final InputStream input;
   private ByteBuffer buffer = MemoryUtil.memAlloc(8192);

   public OggAudioStream(InputStream var1) throws IOException {
      this.input = â˜ƒ;
      this.buffer.limit(0);

      try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
         IntBuffer â˜ƒx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxx = â˜ƒ.mallocInt(1);

         while(this.handle == 0L) {
            if (!this.refillFromStream()) {
               throw new IOException("Failed to find Ogg header");
            }

            int â˜ƒxxx = this.buffer.position();
            this.buffer.position(0);
            this.handle = STBVorbis.stb_vorbis_open_pushdata(this.buffer, â˜ƒx, â˜ƒxx, null);
            this.buffer.position(â˜ƒxxx);
            int â˜ƒxxxx = â˜ƒxx.get(0);
            if (â˜ƒxxxx == 1) {
               this.forwardBuffer();
            } else if (â˜ƒxxxx != 0) {
               throw new IOException("Failed to read Ogg file " + â˜ƒxxxx);
            }
         }

         this.buffer.position(this.buffer.position() + â˜ƒx.get(0));
         STBVorbisInfo â˜ƒxxx = STBVorbisInfo.mallocStack(â˜ƒ);
         STBVorbis.stb_vorbis_get_info(this.handle, â˜ƒxxx);
         this.audioFormat = new AudioFormat((float)â˜ƒxxx.sample_rate(), 16, â˜ƒxxx.channels(), true, false);
      }
   }

   private boolean refillFromStream() throws IOException {
      int â˜ƒ = this.buffer.limit();
      int â˜ƒx = this.buffer.capacity() - â˜ƒ;
      if (â˜ƒx == 0) {
         return true;
      } else {
         byte[] â˜ƒ = new byte[â˜ƒx];
         int â˜ƒx = this.input.read(â˜ƒ);
         if (â˜ƒx == -1) {
            return false;
         } else {
            int â˜ƒ = this.buffer.position();
            this.buffer.limit(â˜ƒ + â˜ƒx);
            this.buffer.position(â˜ƒ);
            this.buffer.put(â˜ƒ, 0, â˜ƒx);
            this.buffer.position(â˜ƒ);
            return true;
         }
      }
   }

   private void forwardBuffer() {
      boolean â˜ƒ = this.buffer.position() == 0;
      boolean â˜ƒx = this.buffer.position() == this.buffer.limit();
      if (â˜ƒx && !â˜ƒ) {
         this.buffer.position(0);
         this.buffer.limit(0);
      } else {
         ByteBuffer â˜ƒ = MemoryUtil.memAlloc(â˜ƒ ? 2 * this.buffer.capacity() : this.buffer.capacity());
         â˜ƒ.put(this.buffer);
         MemoryUtil.memFree(this.buffer);
         â˜ƒ.flip();
         this.buffer = â˜ƒ;
      }
   }

   private boolean readFrame(OggAudioStream.OutputConcat var1) throws IOException {
      if (this.handle == 0L) {
         return false;
      } else {
         try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
            PointerBuffer â˜ƒx = â˜ƒ.mallocPointer(1);
            IntBuffer â˜ƒxx = â˜ƒ.mallocInt(1);
            IntBuffer â˜ƒxxx = â˜ƒ.mallocInt(1);

            while(true) {
               int â˜ƒxxxx = STBVorbis.stb_vorbis_decode_frame_pushdata(this.handle, this.buffer, â˜ƒxx, â˜ƒx, â˜ƒxxx);
               this.buffer.position(this.buffer.position() + â˜ƒxxxx);
               int â˜ƒxxxxx = STBVorbis.stb_vorbis_get_error(this.handle);
               if (â˜ƒxxxxx == 1) {
                  this.forwardBuffer();
                  if (!this.refillFromStream()) {
                     return false;
                  }
               } else {
                  if (â˜ƒxxxxx != 0) {
                     throw new IOException("Failed to read Ogg file " + â˜ƒxxxxx);
                  }

                  int â˜ƒxxxx = â˜ƒxxx.get(0);
                  if (â˜ƒxxxx != 0) {
                     int â˜ƒxxxxx = â˜ƒxx.get(0);
                     PointerBuffer â˜ƒxxxxxx = â˜ƒx.getPointerBuffer(â˜ƒxxxxx);
                     if (â˜ƒxxxxx == 1) {
                        this.convertMono(â˜ƒxxxxxx.getFloatBuffer(0, â˜ƒxxxx), â˜ƒ);
                        return true;
                     }

                     if (â˜ƒxxxxx != 2) {
                        throw new IllegalStateException("Invalid number of channels: " + â˜ƒxxxxx);
                     }

                     this.convertStereo(â˜ƒxxxxxx.getFloatBuffer(0, â˜ƒxxxx), â˜ƒxxxxxx.getFloatBuffer(1, â˜ƒxxxx), â˜ƒ);
                     return true;
                  }
               }
            }
         }
      }
   }

   private void convertMono(FloatBuffer var1, OggAudioStream.OutputConcat var2) {
      while(â˜ƒ.hasRemaining()) {
         â˜ƒ.put(â˜ƒ.get());
      }
   }

   private void convertStereo(FloatBuffer var1, FloatBuffer var2, OggAudioStream.OutputConcat var3) {
      while(â˜ƒ.hasRemaining() && â˜ƒ.hasRemaining()) {
         â˜ƒ.put(â˜ƒ.get());
         â˜ƒ.put(â˜ƒ.get());
      }
   }

   public void close() throws IOException {
      if (this.handle != 0L) {
         STBVorbis.stb_vorbis_close(this.handle);
         this.handle = 0L;
      }

      MemoryUtil.memFree(this.buffer);
      this.input.close();
   }

   @Override
   public AudioFormat getFormat() {
      return this.audioFormat;
   }

   @Override
   public ByteBuffer read(int var1) throws IOException {
      OggAudioStream.OutputConcat â˜ƒ = new OggAudioStream.OutputConcat(â˜ƒ + 8192);

      while(this.readFrame(â˜ƒ) && â˜ƒ.byteCount < â˜ƒ) {
      }

      return â˜ƒ.get();
   }

   public ByteBuffer readAll() throws IOException {
      OggAudioStream.OutputConcat â˜ƒ = new OggAudioStream.OutputConcat(16384);

      while(this.readFrame(â˜ƒ)) {
      }

      return â˜ƒ.get();
   }

   static class OutputConcat {
      private final List<ByteBuffer> buffers = Lists.newArrayList();
      private final int bufferSize;
      int byteCount;
      private ByteBuffer currentBuffer;

      public OutputConcat(int var1) {
         this.bufferSize = â˜ƒ + 1 & -2;
         this.createNewBuffer();
      }

      private void createNewBuffer() {
         this.currentBuffer = BufferUtils.createByteBuffer(this.bufferSize);
      }

      public void put(float var1) {
         if (this.currentBuffer.remaining() == 0) {
            this.currentBuffer.flip();
            this.buffers.add(this.currentBuffer);
            this.createNewBuffer();
         }

         int â˜ƒ = Mth.clamp((int)(â˜ƒ * 32767.5F - 0.5F), -32768, 32767);
         this.currentBuffer.putShort((short)â˜ƒ);
         this.byteCount += 2;
      }

      public ByteBuffer get() {
         this.currentBuffer.flip();
         if (this.buffers.isEmpty()) {
            return this.currentBuffer;
         } else {
            ByteBuffer â˜ƒ = BufferUtils.createByteBuffer(this.byteCount);
            this.buffers.forEach(â˜ƒ::put);
            â˜ƒ.put(this.currentBuffer);
            â˜ƒ.flip();
            return â˜ƒ;
         }
      }
   }
}
