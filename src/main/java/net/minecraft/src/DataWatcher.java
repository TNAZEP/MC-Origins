package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataWatcher {
	private static final HashMap dataTypes = new HashMap();
	private final Map watchedObjects = new HashMap();
	private boolean objectChanged;
    private final ItemCodec itemCodec;

    /** Host adapter until the full ItemStack model is shared. */
    public interface ItemCodec {
        Class itemClass();
        default Object toHost(Object value) { return value; }
        Object read(DataInputStream input) throws IOException;
        void write(Object item, DataOutputStream output) throws IOException;
    }

    public DataWatcher(ItemCodec itemCodec) {
        this.itemCodec = itemCodec;
    }

	public void addObject(int var1, Object var2) {
		Integer var3 = var2.getClass() == this.itemCodec.itemClass() ? Integer.valueOf(5) : (Integer)dataTypes.get(var2.getClass());
		if(var3 == null) {
			throw new IllegalArgumentException("Unknown data type: " + var2.getClass());
		} else if(var1 > 31) {
			throw new IllegalArgumentException("Data value id is too big with " + var1 + "! (Max is " + 31 + ")");
		} else if(this.watchedObjects.containsKey(Integer.valueOf(var1))) {
			throw new IllegalArgumentException("Duplicate id value for " + var1 + "!");
		} else {
			WatchableObject var4 = new WatchableObject(var3.intValue(), var1, var2);
			this.watchedObjects.put(Integer.valueOf(var1), var4);
		}
	}

	public byte getWatchableObjectByte(int var1) {
		return ((Byte)((WatchableObject)this.watchedObjects.get(Integer.valueOf(var1))).getObject()).byteValue();
	}

	public int getWatchableObjectInteger(int var1) {
		return ((Integer)((WatchableObject)this.watchedObjects.get(Integer.valueOf(var1))).getObject()).intValue();
	}

	public String getWatchableObjectString(int var1) {
		return (String)((WatchableObject)this.watchedObjects.get(Integer.valueOf(var1))).getObject();
	}

	public void updateObject(int var1, Object var2) {
		WatchableObject var3 = (WatchableObject)this.watchedObjects.get(Integer.valueOf(var1));
		if(!var2.equals(var3.getObject())) {
			var3.setObject(var2);
			var3.setWatching(true);
			this.objectChanged = true;
		}

	}

	public boolean hasObjectChanged() {
		return this.objectChanged;
	}

	public static void writeObjectsInListToStream(List var0, DataOutputStream var1, ItemCodec itemCodec) throws IOException {
		if(var0 != null) {
			Iterator var2 = var0.iterator();

			while(var2.hasNext()) {
				WatchableObject var3 = (WatchableObject)var2.next();
				writeWatchableObject(var1, var3, itemCodec);
			}
		}

		var1.writeByte(127);
	}

	public ArrayList getChangedObjects() {
		ArrayList var1 = null;
		if(this.objectChanged) {
			Iterator var2 = this.watchedObjects.values().iterator();

			while(var2.hasNext()) {
				WatchableObject var3 = (WatchableObject)var2.next();
				if(var3.getWatching()) {
					var3.setWatching(false);
					if(var1 == null) {
						var1 = new ArrayList();
					}

					var1.add(var3);
				}
			}
		}

		this.objectChanged = false;
		return var1;
	}

	public void writeWatchableObjects(DataOutputStream var1) throws IOException {
		Iterator var2 = this.watchedObjects.values().iterator();

		while(var2.hasNext()) {
			WatchableObject var3 = (WatchableObject)var2.next();
			writeWatchableObject(var1, var3, itemCodec);
		}

		var1.writeByte(127);
	}

	private static void writeWatchableObject(DataOutputStream var0, WatchableObject var1, ItemCodec itemCodec) throws IOException {
		int var2 = (var1.getObjectType() << 5 | var1.getDataValueId() & 31) & 255;
		var0.writeByte(var2);
		switch(var1.getObjectType()) {
		case 0:
			var0.writeByte(((Byte)var1.getObject()).byteValue());
			break;
		case 1:
			var0.writeShort(((Short)var1.getObject()).shortValue());
			break;
		case 2:
			var0.writeInt(((Integer)var1.getObject()).intValue());
			break;
		case 3:
			var0.writeFloat(((Float)var1.getObject()).floatValue());
			break;
		case 4:
			BetaStringCodec.writeString((String)var1.getObject(), var0);
			break;
		case 5:
			itemCodec.write(var1.getObject(), var0);
			break;
		case 6:
			ChunkCoordinates var3 = (ChunkCoordinates)var1.getObject();
			var0.writeInt(var3.x);
			var0.writeInt(var3.y);
			var0.writeInt(var3.z);
		}

	}

	public static List readWatchableObjects(DataInputStream var0, ItemCodec itemCodec) throws IOException {
		ArrayList var1 = null;

		for(byte var2 = var0.readByte(); var2 != 127; var2 = var0.readByte()) {
			if(var1 == null) {
				var1 = new ArrayList();
			}

			int var3 = (var2 & 224) >> 5;
			int var4 = var2 & 31;
			WatchableObject var5 = null;
			switch(var3) {
			case 0:
				var5 = new WatchableObject(var3, var4, Byte.valueOf(var0.readByte()));
				break;
			case 1:
				var5 = new WatchableObject(var3, var4, Short.valueOf(var0.readShort()));
				break;
			case 2:
				var5 = new WatchableObject(var3, var4, Integer.valueOf(var0.readInt()));
				break;
			case 3:
				var5 = new WatchableObject(var3, var4, Float.valueOf(var0.readFloat()));
				break;
			case 4:
				var5 = new WatchableObject(var3, var4, BetaStringCodec.readString(var0, 64));
				break;
			case 5:
				var5 = new WatchableObject(var3, var4, itemCodec.read(var0));
				break;
			case 6:
				int var6 = var0.readInt();
				int var7 = var0.readInt();
				int var8 = var0.readInt();
				var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
			}

			var1.add(var5);
		}

		return var1;
	}

	public void updateWatchedObjectsFromList(List var1) {
		Iterator var2 = var1.iterator();

		while(var2.hasNext()) {
			WatchableObject var3 = (WatchableObject)var2.next();
			WatchableObject var4 = (WatchableObject)this.watchedObjects.get(Integer.valueOf(var3.getDataValueId()));
			if(var4 != null) {
				var4.setObject(var3.getObjectType() == 5 ? this.itemCodec.toHost(var3.getObject()) : var3.getObject());
			}
		}

	}

	static {
		dataTypes.put(Byte.class, Integer.valueOf(0));
		dataTypes.put(Short.class, Integer.valueOf(1));
		dataTypes.put(Integer.class, Integer.valueOf(2));
		dataTypes.put(Float.class, Integer.valueOf(3));
		dataTypes.put(String.class, Integer.valueOf(4));
		dataTypes.put(ChunkCoordinates.class, Integer.valueOf(6));
	}
}
