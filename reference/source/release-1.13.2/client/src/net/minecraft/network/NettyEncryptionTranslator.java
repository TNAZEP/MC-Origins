package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptionTranslator {
   private final Cipher field_150507_a;
   private byte[] field_150505_b = new byte[0];
   private byte[] field_150506_c = new byte[0];

   protected NettyEncryptionTranslator(Cipher var1) {
      this.field_150507_a = ☃;
   }

   private byte[] func_150502_a(ByteBuf var1) {
      int ☃ = ☃.readableBytes();
      if (this.field_150505_b.length < ☃) {
         this.field_150505_b = new byte[☃];
      }

      ☃.readBytes(this.field_150505_b, 0, ☃);
      return this.field_150505_b;
   }

   protected ByteBuf func_150503_a(ChannelHandlerContext var1, ByteBuf var2) throws ShortBufferException {
      int ☃ = ☃.readableBytes();
      byte[] ☃x = this.func_150502_a(☃);
      ByteBuf ☃xx = ☃.alloc().heapBuffer(this.field_150507_a.getOutputSize(☃));
      ☃xx.writerIndex(this.field_150507_a.update(☃x, 0, ☃, ☃xx.array(), ☃xx.arrayOffset()));
      return ☃xx;
   }

   protected void func_150504_a(ByteBuf var1, ByteBuf var2) throws ShortBufferException {
      int ☃ = ☃.readableBytes();
      byte[] ☃x = this.func_150502_a(☃);
      int ☃xx = this.field_150507_a.getOutputSize(☃);
      if (this.field_150506_c.length < ☃xx) {
         this.field_150506_c = new byte[☃xx];
      }

      ☃.writeBytes(this.field_150506_c, 0, this.field_150507_a.update(☃x, 0, ☃, this.field_150506_c));
   }
}
