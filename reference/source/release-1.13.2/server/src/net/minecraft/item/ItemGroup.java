package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnumEnchantmentType;

public abstract class ItemGroup {
   public static final ItemGroup[] field_78032_a = new ItemGroup[12];
   public static final ItemGroup field_78030_b = (new ItemGroup(0, "buildingBlocks") {
   }).func_199783_b("building_blocks");
   public static final ItemGroup field_78031_c = new ItemGroup(1, "decorations") {
   };
   public static final ItemGroup field_78028_d = new ItemGroup(2, "redstone") {
   };
   public static final ItemGroup field_78029_e = new ItemGroup(3, "transportation") {
   };
   public static final ItemGroup field_78026_f = new ItemGroup(6, "misc") {
   };
   public static final ItemGroup field_78027_g = (new ItemGroup(5, "search") {
   }).func_78025_a("item_search.png");
   public static final ItemGroup field_78039_h = new ItemGroup(7, "food") {
   };
   public static final ItemGroup field_78040_i = (new ItemGroup(8, "tools") {
      })
      .func_111229_a(
         new EnumEnchantmentType[]{EnumEnchantmentType.ALL, EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE}
      );
   public static final ItemGroup field_78037_j = (new ItemGroup(9, "combat") {
      })
      .func_111229_a(
         new EnumEnchantmentType[]{
            EnumEnchantmentType.ALL,
            EnumEnchantmentType.ARMOR,
            EnumEnchantmentType.ARMOR_FEET,
            EnumEnchantmentType.ARMOR_HEAD,
            EnumEnchantmentType.ARMOR_LEGS,
            EnumEnchantmentType.ARMOR_CHEST,
            EnumEnchantmentType.BOW,
            EnumEnchantmentType.WEAPON,
            EnumEnchantmentType.WEARABLE,
            EnumEnchantmentType.BREAKABLE,
            EnumEnchantmentType.TRIDENT
         }
      );
   public static final ItemGroup field_78038_k = new ItemGroup(10, "brewing") {
   };
   public static final ItemGroup field_78035_l = field_78026_f;
   public static final ItemGroup field_192395_m = new ItemGroup(4, "hotbar") {
   };
   public static final ItemGroup field_78036_m = (new ItemGroup(11, "inventory") {
   }).func_78025_a("inventory.png").func_78022_j().func_78014_h();
   private final int field_78033_n;
   private final String field_78034_o;
   private String field_199784_q;
   private String field_78043_p = "items.png";
   private boolean field_78042_q = true;
   private boolean field_78041_r = true;
   private EnumEnchantmentType[] field_111230_s = new EnumEnchantmentType[0];
   private ItemStack field_151245_t;

   public ItemGroup(int var1, String var2) {
      this.field_78033_n = ☃;
      this.field_78034_o = ☃;
      this.field_151245_t = ItemStack.field_190927_a;
      field_78032_a[☃] = this;
   }

   public String func_200300_c() {
      return this.field_199784_q == null ? this.field_78034_o : this.field_199784_q;
   }

   public ItemGroup func_78025_a(String var1) {
      this.field_78043_p = ☃;
      return this;
   }

   public ItemGroup func_199783_b(String var1) {
      this.field_199784_q = ☃;
      return this;
   }

   public ItemGroup func_78014_h() {
      this.field_78041_r = false;
      return this;
   }

   public ItemGroup func_78022_j() {
      this.field_78042_q = false;
      return this;
   }

   public EnumEnchantmentType[] func_111225_m() {
      return this.field_111230_s;
   }

   public ItemGroup func_111229_a(EnumEnchantmentType... var1) {
      this.field_111230_s = ☃;
      return this;
   }

   public boolean func_111226_a(@Nullable EnumEnchantmentType var1) {
      if (☃ != null) {
         for(EnumEnchantmentType ☃ : this.field_111230_s) {
            if (☃ == ☃) {
               return true;
            }
         }
      }

      return false;
   }
}
