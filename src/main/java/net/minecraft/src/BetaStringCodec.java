package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/** Beta 1.7.3 packet strings; extracted verbatim from both Packet implementations. */
public final class BetaStringCodec {
	private BetaStringCodec() {
	}

	public static void writeString(String var0, DataOutputStream var1) throws IOException {
		if(var0.length() > Short.MAX_VALUE) {
			throw new IOException("String too big");
		} else {
			var1.writeShort(var0.length());
			var1.writeChars(var0);
		}
	}

	public static String readString(DataInputStream var0, int var1) throws IOException {
		short var2 = var0.readShort();
		if(var2 > var1) {
			throw new IOException("Received string length longer than maximum allowed (" + var2 + " > " + var1 + ")");
		} else if(var2 < 0) {
			throw new IOException("Received string length is less than zero! Weird string!");
		} else {
			StringBuilder var3 = new StringBuilder();

			for(int var4 = 0; var4 < var2; ++var4) {
				var3.append(var0.readChar());
			}

			return var3.toString();
		}
	}

}
