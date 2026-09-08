package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class CipherBase {
   private final Cipher cipher;
   private byte[] heapIn = new byte[0];
   private byte[] heapOut = new byte[0];

   protected CipherBase(Cipher var1) {
      this.cipher = â˜ƒ;
   }

   private byte[] bufToByte(ByteBuf var1) {
      int â˜ƒ = â˜ƒ.readableBytes();
      if (this.heapIn.length < â˜ƒ) {
         this.heapIn = new byte[â˜ƒ];
      }

      â˜ƒ.readBytes(this.heapIn, 0, â˜ƒ);
      return this.heapIn;
   }

   protected ByteBuf decipher(ChannelHandlerContext var1, ByteBuf var2) throws ShortBufferException {
      int â˜ƒ = â˜ƒ.readableBytes();
      byte[] â˜ƒx = this.bufToByte(â˜ƒ);
      ByteBuf â˜ƒxx = â˜ƒ.alloc().heapBuffer(this.cipher.getOutputSize(â˜ƒ));
      â˜ƒxx.writerIndex(this.cipher.update(â˜ƒx, 0, â˜ƒ, â˜ƒxx.array(), â˜ƒxx.arrayOffset()));
      return â˜ƒxx;
   }

   protected void encipher(ByteBuf var1, ByteBuf var2) throws ShortBufferException {
      int â˜ƒ = â˜ƒ.readableBytes();
      byte[] â˜ƒx = this.bufToByte(â˜ƒ);
      int â˜ƒxx = this.cipher.getOutputSize(â˜ƒ);
      if (this.heapOut.length < â˜ƒxx) {
         this.heapOut = new byte[â˜ƒxx];
      }

      â˜ƒ.writeBytes(this.heapOut, 0, this.cipher.update(â˜ƒx, 0, â˜ƒ, this.heapOut));
   }
}
