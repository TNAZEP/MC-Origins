package net.minecraft.server.rcon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetworkDataOutputStream {
   private final ByteArrayOutputStream outputStream;
   private final DataOutputStream dataOutputStream;

   public NetworkDataOutputStream(int var1) {
      this.outputStream = new ByteArrayOutputStream(â˜ƒ);
      this.dataOutputStream = new DataOutputStream(this.outputStream);
   }

   public void writeBytes(byte[] var1) throws IOException {
      this.dataOutputStream.write(â˜ƒ, 0, â˜ƒ.length);
   }

   public void writeString(String var1) throws IOException {
      this.dataOutputStream.writeBytes(â˜ƒ);
      this.dataOutputStream.write(0);
   }

   public void write(int var1) throws IOException {
      this.dataOutputStream.write(â˜ƒ);
   }

   public void writeShort(short var1) throws IOException {
      this.dataOutputStream.writeShort(Short.reverseBytes(â˜ƒ));
   }

   public void writeInt(int var1) throws IOException {
      this.dataOutputStream.writeInt(Integer.reverseBytes(â˜ƒ));
   }

   public void writeFloat(float var1) throws IOException {
      this.dataOutputStream.writeInt(Integer.reverseBytes(Float.floatToIntBits(â˜ƒ)));
   }

   public byte[] toByteArray() {
      return this.outputStream.toByteArray();
   }

   public void reset() {
      this.outputStream.reset();
   }
}
