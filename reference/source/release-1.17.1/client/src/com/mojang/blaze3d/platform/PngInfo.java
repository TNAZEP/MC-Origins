package com.mojang.blaze3d.platform;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class PngInfo {
   public final int width;
   public final int height;

   public PngInfo(String var1, InputStream var2) throws IOException {
      try (
         MemoryStack â˜ƒ = MemoryStack.stackPush();
         PngInfo.StbReader â˜ƒx = createCallbacks(â˜ƒ);
         STBIReadCallback â˜ƒxx = STBIReadCallback.create(â˜ƒx::read);
         STBISkipCallback â˜ƒxxx = STBISkipCallback.create(â˜ƒx::skip);
         STBIEOFCallback â˜ƒxxxx = STBIEOFCallback.create(â˜ƒx::eof);
      ) {
         STBIIOCallbacks â˜ƒxxxxx = STBIIOCallbacks.mallocStack(â˜ƒ);
         â˜ƒxxxxx.read(â˜ƒxx);
         â˜ƒxxxxx.skip(â˜ƒxxx);
         â˜ƒxxxxx.eof(â˜ƒxxxx);
         IntBuffer â˜ƒxxxxxx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxxxxxxx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxxxxxxxx = â˜ƒ.mallocInt(1);
         if (!STBImage.stbi_info_from_callbacks(â˜ƒxxxxx, 0L, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx)) {
            throw new IOException("Could not read info from the PNG file " + â˜ƒ + " " + STBImage.stbi_failure_reason());
         }

         this.width = â˜ƒxxxxxx.get(0);
         this.height = â˜ƒxxxxxxx.get(0);
      }
   }

   private static PngInfo.StbReader createCallbacks(InputStream var0) {
      return (PngInfo.StbReader)(â˜ƒ instanceof FileInputStream
         ? new PngInfo.StbReaderSeekableByteChannel(((FileInputStream)â˜ƒ).getChannel())
         : new PngInfo.StbReaderBufferedChannel(Channels.newChannel(â˜ƒ)));
   }

   abstract static class StbReader implements AutoCloseable {
      protected boolean closed;

      int read(long var1, long var3, int var5) {
         try {
            return this.read(â˜ƒ, â˜ƒ);
         } catch (IOException var7) {
            this.closed = true;
            return 0;
         }
      }

      void skip(long var1, int var3) {
         try {
            this.skip(â˜ƒ);
         } catch (IOException var5) {
            this.closed = true;
         }
      }

      int eof(long var1) {
         return this.closed ? 1 : 0;
      }

      protected abstract int read(long var1, int var3) throws IOException;

      protected abstract void skip(int var1) throws IOException;

      public abstract void close() throws IOException;
   }

   static class StbReaderBufferedChannel extends PngInfo.StbReader {
      private static final int START_BUFFER_SIZE = 128;
      private final ReadableByteChannel channel;
      private long readBufferAddress = MemoryUtil.nmemAlloc(128L);
      private int bufferSize = 128;
      private int read;
      private int consumed;

      StbReaderBufferedChannel(ReadableByteChannel var1) {
         this.channel = â˜ƒ;
      }

      private void fillReadBuffer(int var1) throws IOException {
         ByteBuffer â˜ƒ = MemoryUtil.memByteBuffer(this.readBufferAddress, this.bufferSize);
         if (â˜ƒ + this.consumed > this.bufferSize) {
            this.bufferSize = â˜ƒ + this.consumed;
            â˜ƒ = MemoryUtil.memRealloc(â˜ƒ, this.bufferSize);
            this.readBufferAddress = MemoryUtil.memAddress(â˜ƒ);
         }

         â˜ƒ.position(this.read);

         while(â˜ƒ + this.consumed > this.read) {
            try {
               int â˜ƒ = this.channel.read(â˜ƒ);
               if (â˜ƒ == -1) {
                  break;
               }
            } finally {
               this.read = â˜ƒ.position();
            }
         }
      }

      @Override
      public int read(long var1, int var3) throws IOException {
         this.fillReadBuffer(â˜ƒ);
         if (â˜ƒ + this.consumed > this.read) {
            â˜ƒ = this.read - this.consumed;
         }

         MemoryUtil.memCopy(this.readBufferAddress + (long)this.consumed, â˜ƒ, (long)â˜ƒ);
         this.consumed += â˜ƒ;
         return â˜ƒ;
      }

      @Override
      public void skip(int var1) throws IOException {
         if (â˜ƒ > 0) {
            this.fillReadBuffer(â˜ƒ);
            if (â˜ƒ + this.consumed > this.read) {
               throw new EOFException("Can't skip past the EOF.");
            }
         }

         if (this.consumed + â˜ƒ < 0) {
            throw new IOException("Can't seek before the beginning: " + (this.consumed + â˜ƒ));
         } else {
            this.consumed += â˜ƒ;
         }
      }

      @Override
      public void close() throws IOException {
         MemoryUtil.nmemFree(this.readBufferAddress);
         this.channel.close();
      }
   }

   static class StbReaderSeekableByteChannel extends PngInfo.StbReader {
      private final SeekableByteChannel channel;

      StbReaderSeekableByteChannel(SeekableByteChannel var1) {
         this.channel = â˜ƒ;
      }

      @Override
      public int read(long var1, int var3) throws IOException {
         ByteBuffer â˜ƒ = MemoryUtil.memByteBuffer(â˜ƒ, â˜ƒ);
         return this.channel.read(â˜ƒ);
      }

      @Override
      public void skip(int var1) throws IOException {
         this.channel.position(this.channel.position() + (long)â˜ƒ);
      }

      @Override
      public int eof(long var1) {
         return super.eof(â˜ƒ) != 0 && this.channel.isOpen() ? 1 : 0;
      }

      @Override
      public void close() throws IOException {
         this.channel.close();
      }
   }
}
