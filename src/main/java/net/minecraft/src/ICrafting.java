package net.minecraft.src;

import java.util.List;

public interface ICrafting {
	void func_20159_a(Container var1, int var2, ItemStack var3);

	void func_20158_a(Container var1, int var2, int var3);

	void updateCraftingInventory(Container var1, List var2);
}
