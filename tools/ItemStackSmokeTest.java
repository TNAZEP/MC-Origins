package net.minecraft.src;

/** Practical inventory data operations using either built side's concrete stack. */
public final class ItemStackSmokeTest {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) throws Exception {
        Class.forName("net.minecraft.src.StatList");
        ItemStack stack = new ItemStack(1, 32, 2);
        stack.animationsToGo = 5;
        ItemStack split = stack.splitStack(12);
        check(stack.stackSize == 20 && split.stackSize == 12 && split.itemID == 1
            && split.getItemDamage() == 2, "Splitting lost stack data");
        ItemStack copy = stack.copy();
        check(copy != stack && copy.isStackEqual(stack) && copy.animationsToGo == 0,
            "Copy must be independent and omit transient animation state");
        copy.setItemDamage(3);
        check(stack.getItemDamage() == 2 && !copy.isItemEqual(stack), "Copy mutation affected source");
        NBTTagCompound tag = split.writeToNBT(new NBTTagCompound());
        ItemStack loaded = new ItemStack(tag);
        check(loaded.isStackEqual(split) && loaded.animationsToGo == 0, "NBT inventory restore failed");
        check(ItemStack.copyItemStack(null) == null && ItemStack.areItemStacksEqual(null, null)
            && !ItemStack.areItemStacksEqual(stack, null), "Null stack handling failed");
        check(ItemStack.areItemStacksEqual(split, loaded), "Restored stack differs");
        check(new ItemStack(1, 2, 0).isStackable(), "Block stack should be stackable");
        ItemStack shovel = new ItemStack(Item.shovelWood);
        check(shovel.getItem().onBlockDestroyed(shovel, Block.dirt.blockID, 0, 64, 0, null)
            && shovel.getItemDamage() == 1, "Tool callback override lost");
        ItemStack shears = new ItemStack(Item.shears);
        shears.getItem().onBlockDestroyed(shears, Block.leaves.blockID, 0, 64, 0, null);
        check(shears.getItemDamage() == 1, "Shears leaf callback lost");
        shears.getItem().onBlockDestroyed(shears, Block.stone.blockID, 0, 64, 0, null);
        check(shears.getItemDamage() == 1, "Shears incorrectly damaged on stone");
        check(Item.itemsList[Block.wood.blockID].getPlacedBlockMetadata(2) == 2,
            "Log placement metadata override lost");
        check(Item.itemsList[Block.pistonBase.blockID].getPlacedBlockMetadata(0) == 7,
            "Piston placement metadata override lost");
        System.out.println("PASS: stack data, inventory restore, tool/shears callbacks and placement metadata");
    }
}
