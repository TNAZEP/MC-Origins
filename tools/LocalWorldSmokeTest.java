package net.minecraft.src;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Headless local-world simulation/save check; does not certify the graphical client. */
public final class LocalWorldSmokeTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) throws Exception {
        Path root = Files.createTempDirectory(Paths.get("run/smoke"), "local-world-");
        SaveOldDir save = new SaveOldDir(root.toFile(), "world", false);
        World world = new World(save, "Origins smoke", 8675309L);
        EntityPlayer player = new EntityPlayer(world) {};
        player.username = "LocalProbe";
        player.setPosition(8.5, 101, 8.5);
        world.playerEntities.add(player);
        check(!world.multiplayerWorld, "Local world became remote");
        check(world.setBlockWithNotify(8, 100, 8, Block.stone.blockID), "Place support");
        check(world.setBlockWithNotify(9, 100, 8, Block.chest.blockID), "Place chest");
        TileEntityChest chest = (TileEntityChest)world.getBlockTileEntity(9, 100, 8);
        chest.setInventorySlotContents(0, new ItemStack(Item.diamond, 7));
        player.inventory.mainInventory[0] = new ItemStack(Item.pickaxeSteel);
        player.inventory.mainInventory[1] = new ItemStack(Block.cobblestone, 23);
        check(world.setBlockWithNotify(10, 100, 8, Block.dirt.blockID), "Place dirt");
        check(world.setBlockWithNotify(10, 100, 8, 0), "Remove dirt");
        check(!world.setBlockWithNotify(8, 128, 8, Block.stone.blockID), "Beta height bound changed");
        world.saveWorld(true, null);
        save.func_22093_e();

        SaveOldDir reopenedSave = new SaveOldDir(root.toFile(), "world", false);
        World reopened = new World(reopenedSave, "Origins smoke", 1L);
        check(reopened.getBlockId(8, 100, 8) == Block.stone.blockID, "Placed block lost");
        check(reopened.getBlockId(10, 100, 8) == 0, "Removed block returned");
        TileEntityChest restored = (TileEntityChest)reopened.getBlockTileEntity(9, 100, 8);
        check(restored != null && restored.getStackInSlot(0).itemID == Item.diamond.shiftedIndex
            && restored.getStackInSlot(0).stackSize == 7, "Chest contents lost");
        EntityPlayer restoredPlayer = new EntityPlayer(reopened) {};
        restoredPlayer.readFromNBT(reopenedSave.loadWorldInfo().getPlayerNBTTagCompound());
        check(restoredPlayer.inventory.mainInventory[1].stackSize == 23
            && restoredPlayer.inventory.mainInventory[0].itemID == Item.pickaxeSteel.shiftedIndex,
            "Local player inventory lost");
        check(restoredPlayer.posX == player.posX && restoredPlayer.posZ == player.posZ,
            "Local player position lost");
        reopenedSave.func_22093_e();
        System.out.println("PASS: local world generation, block edits, chest/player inventory and position save/reload; Beta height bound; " + root);
    }
}
