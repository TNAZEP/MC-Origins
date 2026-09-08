package net.minecraft.client.sounds;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

public class LoopingAudioStream implements AudioStream {
   private final LoopingAudioStream.AudioStreamProvider provider;
   private AudioStream stream;
   private final BufferedInputStream bufferedInputStream;

   public LoopingAudioStream(LoopingAudioStream.AudioStreamProvider var1, InputStream var2) throws IOException {
      this.provider = â˜ƒ;
      this.bufferedInputStream = new BufferedInputStream(â˜ƒ);
      this.bufferedInputStream.mark(Integer.MAX_VALUE);
      this.stream = â˜ƒ.create(new LoopingAudioStream.NoCloseBuffer(this.bufferedInputStream));
   }

   @Override
   public AudioFormat getFormat() {
      return this.stream.getFormat();
   }

   @Override
   public ByteBuffer read(int var1) throws IOException {
      ByteBuffer â˜ƒ = this.stream.read(â˜ƒ);
      if (!â˜ƒ.hasRemaining()) {
         this.stream.close();
         this.bufferedInputStream.reset();
         this.stream = this.provider.create(new LoopingAudioStream.NoCloseBuffer(this.bufferedInputStream));
         â˜ƒ = this.stream.read(â˜ƒ);
      }

      return â˜ƒ;
   }

   public void close() throws IOException {
      this.stream.close();
      this.bufferedInputStream.close();
   }

   @FunctionalInterface
   public interface AudioStreamProvider {
      AudioStream create(InputStream var1) throws IOException;
   }

   static class NoCloseBuffer extends FilterInputStream {
      NoCloseBuffer(InputStream var1) {
         super(â˜ƒ);
      }

      public void close() {
      }
   }
}
