package net.minecraft.src;

/** Shared stack value and persistence operations, independent of gameplay hosts. */
public abstract class ItemStackData<T extends ItemStackData<T>> {
    public int stackSize;
    public int animationsToGo;
    public int itemID;
    protected int itemDamage;

    protected ItemStackData(int itemID, int stackSize, int itemDamage) {
        this.itemID = itemID;
        this.stackSize = stackSize;
        this.itemDamage = itemDamage;
    }

    protected ItemStackData(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    protected abstract T createStack(int itemID, int stackSize, int itemDamage);

	public T splitStack(int var1) {
		this.stackSize -= var1;
		return createStack(this.itemID, var1, this.itemDamage);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound var1) {
		var1.setShort("id", (short)this.itemID);
		var1.setByte("Count", (byte)this.stackSize);
		var1.setShort("Damage", (short)this.itemDamage);
		return var1;
	}

	public void readFromNBT(NBTTagCompound var1) {
		this.itemID = var1.getShort("id");
		this.stackSize = var1.getByte("Count");
		this.itemDamage = var1.getShort("Damage");
	}

	public int getItemDamageForDisplay() {
		return this.itemDamage;
	}

	public int getItemDamage() {
		return this.itemDamage;
	}

	public void setItemDamage(int var1) {
		this.itemDamage = var1;
	}

	public T copy() {
		return createStack(this.itemID, this.stackSize, this.itemDamage);
	}

	public boolean isItemEqual(ItemStackData<?> var1) {
		return this.itemID == var1.itemID && this.itemDamage == var1.itemDamage;
	}

	public boolean isStackEqual(ItemStackData<?> var1) {
		return this.itemID == var1.itemID && this.stackSize == var1.stackSize && this.itemDamage == var1.itemDamage;
	}
}
