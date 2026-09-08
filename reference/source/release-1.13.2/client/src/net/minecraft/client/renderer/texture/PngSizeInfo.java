package net.minecraft.client.renderer.texture;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import net.minecraft.resources.IResource;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class PngSizeInfo {
   public final int field_188533_a;
   public final int field_188534_b;

   public PngSizeInfo(IResource var1) throws IOException {
      try (
         MemoryStack ☃ = MemoryStack.stackPush();
         PngSizeInfo.Reader ☃x = func_195695_a(☃.func_199027_b());
         STBIReadCallback ☃xx = STBIReadCallback.create(☃x::func_195682_a);
         STBISkipCallback ☃xxx = STBISkipCallback.create(☃x::func_195686_a);
         STBIEOFCallback ☃xxxx = STBIEOFCallback.create(☃x::func_195685_a);
      ) {
         STBIIOCallbacks ☃xxxxx = STBIIOCallbacks.mallocStack(☃);
         ☃xxxxx.read(☃xx);
         ☃xxxxx.skip(☃xxx);
         ☃xxxxx.eof(☃xxxx);
         IntBuffer ☃xxxxxx = ☃.mallocInt(1);
         IntBuffer ☃xxxxxxx = ☃.mallocInt(1);
         IntBuffer ☃xxxxxxxx = ☃.mallocInt(1);
         if (!STBImage.stbi_info_from_callbacks(☃xxxxx, 0L, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx)) {
            throw new IOException("Could not read info from the PNG file " + ☃ + " " + STBImage.stbi_failure_reason());
         }

         this.field_188533_a = ☃xxxxxx.get(0);
         this.field_188534_b = ☃xxxxxxx.get(0);
      }
   }

   private static PngSizeInfo.Reader func_195695_a(InputStream var0) {
      return (PngSizeInfo.Reader)(☃ instanceof FileInputStream
         ? new PngSizeInfo.ReaderSeekable(((FileInputStream)☃).getChannel())
         : new PngSizeInfo.ReaderBuffer(Channels.newChannel(☃)));
   }

   abstract static class Reader implements AutoCloseable {
      protected boolean field_195687_a;

      private Reader() {
      }

      int func_195682_a(long var1, long var3, int var5) {
         try {
            return this.func_195683_b(☃, ☃);
         } catch (IOException var7) {
            this.field_195687_a = true;
            return 0;
         }
      }

      void func_195686_a(long var1, int var3) {
         try {
            this.func_195684_a(☃);
         } catch (IOException var5) {
            this.field_195687_a = true;
         }
      }

      int func_195685_a(long var1) {
         return this.field_195687_a ? 1 : 0;
      }

      protected abstract int func_195683_b(long var1, int var3) throws IOException;

      protected abstract void func_195684_a(int var1) throws IOException;

      public abstract void close() throws IOException;
   }

   static class ReaderBuffer extends PngSizeInfo.Reader {
      private final ReadableByteChannel field_195689_b;
      private long field_195690_c = MemoryUtil.nmemAlloc(128L);
      private int field_195691_d = 128;
      private int field_195692_e;
      private int field_195693_f;

      private ReaderBuffer(ReadableByteChannel var1) {
         this.field_195689_b = ☃;
      }

      private void func_195688_b(int var1) throws IOException {
         ByteBuffer ☃ = MemoryUtil.memByteBuffer(this.field_195690_c, this.field_195691_d);
         if (☃ + this.field_195693_f > this.field_195691_d) {
            this.field_195691_d = ☃ + this.field_195693_f;
            ☃ = MemoryUtil.memRealloc(☃, this.field_195691_d);
            this.field_195690_c = MemoryUtil.memAddress(☃);
         }

         ☃.position(this.field_195692_e);

         while(☃ + this.field_195693_f > this.field_195692_e) {
            try {
               int ☃ = this.field_195689_b.read(☃);
               if (☃ == -1) {
                  break;
               }
            } finally {
               this.field_195692_e = ☃.position();
            }
         }
      }

      @Override
      public int func_195683_b(long var1, int var3) throws IOException {
         this.func_195688_b(☃);
         if (☃ + this.field_195693_f > this.field_195692_e) {
            ☃ = this.field_195692_e - this.field_195693_f;
         }

         MemoryUtil.memCopy(this.field_195690_c + (long)this.field_195693_f, ☃, (long)☃);
         this.field_195693_f += ☃;
         return ☃;
      }

      @Override
      public void func_195684_a(int var1) throws IOException {
         if (☃ > 0) {
            this.func_195688_b(☃);
            if (☃ + this.field_195693_f > this.field_195692_e) {
               throw new EOFException("Can't skip past the EOF.");
            }
         }

         if (this.field_195693_f + ☃ < 0) {
            throw new IOException("Can't seek before the beginning: " + (this.field_195693_f + ☃));
         } else {
            this.field_195693_f += ☃;
         }
      }

      @Override
      public void close() throws IOException {
         MemoryUtil.nmemFree(this.field_195690_c);
         this.field_195689_b.close();
      }
   }

   static class ReaderSeekable extends PngSizeInfo.Reader {
      private final SeekableByteChannel field_195694_b;

      private ReaderSeekable(SeekableByteChannel var1) {
         this.field_195694_b = ☃;
      }

      @Override
      public int func_195683_b(long var1, int var3) throws IOException {
         ByteBuffer ☃ = MemoryUtil.memByteBuffer(☃, ☃);
         return this.field_195694_b.read(☃);
      }

      @Override
      public void func_195684_a(int var1) throws IOException {
         this.field_195694_b.position(this.field_195694_b.position() + (long)☃);
      }

      @Override
      public int func_195685_a(long var1) {
         return super.func_195685_a(☃) != 0 && this.field_195694_b.isOpen() ? 1 : 0;
      }

      @Override
      public void close() throws IOException {
         this.field_195694_b.close();
      }
   }
}
