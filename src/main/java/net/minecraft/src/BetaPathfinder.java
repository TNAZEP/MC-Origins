package net.minecraft.src;

/** Beta world/entity adapter for the shared path search. */
public final class BetaPathfinder extends Pathfinder {
	private final IBlockAccess worldMap;

	public BetaPathfinder(IBlockAccess var1) {
		this.worldMap = var1;
	}

	public PathEntity createEntityPathTo(Entity var1, Entity var2, float var3) {
		return this.createEntityPathTo(var1, var2.posX, var2.boundingBox.minY, var2.posZ, var3);
	}

	public PathEntity createEntityPathTo(Entity var1, int var2, int var3, int var4, float var5) {
		return this.createEntityPathTo(var1, (double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), var5);
	}

	private PathEntity createEntityPathTo(Entity entity, double x, double y, double z, float distance) {
		return createPath(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.minZ, entity.width, entity.height, x, y, z, distance);
	}

	protected int getVerticalOffset( int var2, int var3, int var4, PathPoint var5) {
		for(int var6 = var2; var6 < var2 + var5.xCoord; ++var6) {
			for(int var7 = var3; var7 < var3 + var5.yCoord; ++var7) {
				for(int var8 = var4; var8 < var4 + var5.zCoord; ++var8) {
					int var9 = this.worldMap.getBlockId(var6, var7, var8);
					if(var9 > 0) {
						if(var9 != Block.doorSteel.blockID && var9 != Block.doorWood.blockID) {
							Material var11 = Block.blocksList[var9].blockMaterial;
							if(var11.getIsSolid()) {
								return 0;
							}

							if(var11 == Material.water) {
								return -1;
							}

							if(var11 == Material.lava) {
								return -2;
							}
						} else {
							int var10 = this.worldMap.getBlockMetadata(var6, var7, var8);
							if(!BlockDoor.isOpen(var10)) {
								return 0;
							}
						}
					}
				}
			}
		}

		return 1;
	}

}
