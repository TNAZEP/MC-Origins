package net.minecraft.src;

import java.io.File;
import java.util.List;

public interface ISaveHandler extends MapFileAccess {
	WorldInfo loadWorldInfo();

	void func_22150_b();

	IChunkLoader getChunkLoader(WorldProvider var1);

	void saveWorldInfoAndPlayer(WorldInfo var1, List var2);

	void saveWorldInfo(WorldInfo var1);

	File func_28113_a(String var1);

	IPlayerFileData func_22090_d();

	void func_22093_e();
}
