package net.minecraft.src;

import java.io.File;
import java.util.List;

public class SaveHandlerMP implements ISaveHandler {
	/** Remote worlds do not own player save files. */
	public IPlayerFileData func_22090_d() {
		return null;
	}

	public void func_22093_e() {
	}

	public WorldInfo loadWorldInfo() {
		return null;
	}

	public void func_22150_b() {
	}

	public IChunkLoader getChunkLoader(WorldProvider var1) {
		return null;
	}

	public void saveWorldInfoAndPlayer(WorldInfo var1, List var2) {
	}

	public void saveWorldInfo(WorldInfo var1) {
	}

	public File func_28113_a(String var1) {
		return null;
	}
}
