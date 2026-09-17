package net.minecraft.src;

public final class ItemStack extends ItemStackData<ItemStack> {
    /** Materialize a decoded packet value at the gameplay boundary. */
    public static ItemStack fromPacket(ItemStackData<?> data) {
        if (data == null) return null;
        if (data instanceof ItemStack) return (ItemStack)data;
        ItemStack result = new ItemStack(data.itemID, data.stackSize, data.getItemDamage());
        result.animationsToGo = data.animationsToGo;
        return result;
    }

    public static ItemStack[] fromPacket(ItemStackData<?>[] data) {
        ItemStack[] result = new ItemStack[data.length];
        for (int i = 0; i < data.length; i++) result[i] = fromPacket(data[i]);
        return result;
    }


	public ItemStack(Block var1) {
		this((Block)var1, 1);
	}

	public ItemStack(Block var1, int var2) {
		this(var1.blockID, var2, 0);
	}

	public ItemStack(Block var1, int var2, int var3) {
		this(var1.blockID, var2, var3);
	}

	public ItemStack(Item var1) {
		this(var1.shiftedIndex, 1, 0);
	}

	public ItemStack(Item var1, int var2) {
		this(var1.shiftedIndex, var2, 0);
	}

	public ItemStack(Item var1, int var2, int var3) {
		this(var1.shiftedIndex, var2, var3);
	}

	public ItemStack(int var1, int var2, int var3) {
		super(var1, var2, var3);
	}

	public ItemStack(NBTTagCompound var1) {
		super(var1);
	}

	protected ItemStack createStack(int itemID, int stackSize, int itemDamage) {
		return new ItemStack(itemID, stackSize, itemDamage);
	}

	public Item getItem() {
		return Item.itemsList[this.itemID];
	}

	public int getIconIndex() {
		return this.getItem().getIconIndex(this);
	}

	public boolean useItem(EntityPlayer var1, World var2, int var3, int var4, int var5, int var6) {
		boolean var7 = this.getItem().onItemUse(this, var1, var2, var3, var4, var5, var6);
		if(var7) {
			var1.addStat(StatList.field_25172_A[this.itemID], 1);
		}

		return var7;
	}

	public float getStrVsBlock(Block var1) {
		return this.getItem().getStrVsBlock(this, var1);
	}

	public ItemStack useItemRightClick(World var1, EntityPlayer var2) {
		return this.getItem().onItemRightClick(this, var1, var2);
	}

	public int getMaxStackSize() {
		return this.getItem().getItemStackLimit();
	}

	public boolean isStackable() {
		return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
	}

	public boolean isItemStackDamageable() {
		return Item.itemsList[this.itemID].getMaxDamage() > 0;
	}

	public boolean getHasSubtypes() {
		return Item.itemsList[this.itemID].getHasSubtypes();
	}

	public boolean isItemDamaged() {
		return this.isItemStackDamageable() && this.itemDamage > 0;
	}

	public int getMaxDamage() {
		return Item.itemsList[this.itemID].getMaxDamage();
	}

	public void damageItem(int var1, Entity var2) {
		if(this.isItemStackDamageable()) {
			this.itemDamage += var1;
			if(this.itemDamage > this.getMaxDamage()) {
				if(var2 instanceof EntityPlayer) {
					((EntityPlayer)var2).addStat(StatList.field_25170_B[this.itemID], 1);
				}

				--this.stackSize;
				if(this.stackSize < 0) {
					this.stackSize = 0;
				}

				this.itemDamage = 0;
			}

		}
	}

	public void hitEntity(EntityLiving var1, EntityPlayer var2) {
		boolean var3 = Item.itemsList[this.itemID].hitEntity(this, var1, var2);
		if(var3) {
			var2.addStat(StatList.field_25172_A[this.itemID], 1);
		}

	}

	public void onDestroyBlock(int var1, int var2, int var3, int var4, EntityPlayer var5) {
		boolean var6 = Item.itemsList[this.itemID].onBlockDestroyed(this, var1, var2, var3, var4, var5);
		if(var6) {
			var5.addStat(StatList.field_25172_A[this.itemID], 1);
		}

	}

	public int getDamageVsEntity(Entity var1) {
		return Item.itemsList[this.itemID].getDamageVsEntity(var1);
	}

	public boolean canHarvestBlock(Block var1) {
		return Item.itemsList[this.itemID].canHarvestBlock(var1);
	}

	public void onItemDestroyed(EntityPlayer var1) {
	}

	public void useItemOnEntity(EntityLiving var1) {
		Item.itemsList[this.itemID].saddleEntity(this, var1);
	}

	public static boolean areItemStacksEqual(ItemStackData<?> var0, ItemStackData<?> var1) {
		return var0 == null && var1 == null ? true : (var0 != null && var1 != null ? var0.isStackEqual(var1) : false);
	}

	private boolean isItemStackEqual(ItemStack var1) {
		return this.isStackEqual(var1);
	}

	public String getItemName() {
		return Item.itemsList[this.itemID].getItemNameIS(this);
	}

	public static ItemStack copyItemStack(ItemStack var0) {
		return var0 == null ? null : var0.copy();
	}

	public String toString() {
		return this.stackSize + "x" + Item.itemsList[this.itemID].getItemName() + "@" + this.itemDamage;
	}

	public void updateAnimation(World var1, Entity var2, int var3, boolean var4) {
		if(this.animationsToGo > 0) {
			--this.animationsToGo;
		}

		Item.itemsList[this.itemID].onUpdate(this, var1, var2, var3, var4);
	}

	public void onCrafting(World var1, EntityPlayer var2) {
		var2.addStat(StatList.field_25158_z[this.itemID], this.stackSize);
		Item.itemsList[this.itemID].onCreated(this, var1, var2);
	}

}
