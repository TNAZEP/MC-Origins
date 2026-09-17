package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet27Position extends Packet {
	private float field_22039_a;
	private float field_22038_b;
	private boolean field_22043_c;
	private boolean field_22042_d;
	private float field_22041_e;
	private float field_22040_f;

	public void readPacketData(DataInputStream var1) throws IOException {
		this.field_22039_a = var1.readFloat();
		this.field_22038_b = var1.readFloat();
		this.field_22041_e = var1.readFloat();
		this.field_22040_f = var1.readFloat();
		this.field_22043_c = var1.readBoolean();
		this.field_22042_d = var1.readBoolean();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeFloat(this.field_22039_a);
		var1.writeFloat(this.field_22038_b);
		var1.writeFloat(this.field_22041_e);
		var1.writeFloat(this.field_22040_f);
		var1.writeBoolean(this.field_22043_c);
		var1.writeBoolean(this.field_22042_d);
	}

	public void processPacket(NetHandler var1) {
		var1.func_22185_a(this);
	}

	public int getPacketSize() {
		return 18;
	}
	public float func_22031_c() {
		return this.field_22039_a;
	}
	public float func_22029_d() {
		return this.field_22041_e;
	}
	public float func_22028_e() {
		return this.field_22038_b;
	}
	public float func_22033_f() {
		return this.field_22040_f;
	}
	public boolean func_22032_g() {
		return this.field_22043_c;
	}
	public boolean func_22030_h() {
		return this.field_22042_d;
	}
}
