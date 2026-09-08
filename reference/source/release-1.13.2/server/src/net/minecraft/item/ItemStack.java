package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemStack {
   private static final Logger field_199558_c = LogManager.getLogger();
   public static final ItemStack field_190927_a = new ItemStack((Item)null);
   public static final DecimalFormat field_111284_a = func_208306_D();
   private int field_77994_a;
   private int field_77992_b;
   @Deprecated
   private final Item field_151002_e;
   private NBTTagCompound field_77990_d;
   private boolean field_190928_g;
   private EntityItemFrame field_82843_f;
   private BlockWorldState field_179552_h;
   private boolean field_179553_i;
   private BlockWorldState field_179550_j;
   private boolean field_179551_k;

   private static DecimalFormat func_208306_D() {
      DecimalFormat ☃ = new DecimalFormat("#.##");
      ☃.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
      return ☃;
   }

   public ItemStack(IItemProvider var1) {
      this(☃, 1);
   }

   public ItemStack(IItemProvider var1, int var2) {
      this.field_151002_e = ☃ == null ? null : ☃.func_199767_j();
      this.field_77994_a = ☃;
      this.func_190923_F();
   }

   private void func_190923_F() {
      this.field_190928_g = false;
      this.field_190928_g = this.func_190926_b();
   }

   private ItemStack(NBTTagCompound var1) {
      Item ☃ = IRegistry.field_212630_s.func_212608_b(new ResourceLocation(☃.func_74779_i("id")));
      this.field_151002_e = ☃ == null ? Items.field_190931_a : ☃;
      this.field_77994_a = ☃.func_74771_c("Count");
      if (☃.func_150297_b("tag", 10)) {
         this.field_77990_d = ☃.func_74775_l("tag");
         this.func_77973_b().func_179215_a(☃);
      }

      if (this.func_77973_b().func_77645_m()) {
         this.func_196085_b(this.func_77952_i());
      }

      this.func_190923_F();
   }

   public static ItemStack func_199557_a(NBTTagCompound var0) {
      try {
         return new ItemStack(☃);
      } catch (RuntimeException var2) {
         field_199558_c.debug("Tried to load invalid item: {}", ☃, var2);
         return field_190927_a;
      }
   }

   public boolean func_190926_b() {
      if (this == field_190927_a) {
         return true;
      } else if (this.func_77973_b() == null || this.func_77973_b() == Items.field_190931_a) {
         return true;
      } else {
         return this.field_77994_a <= 0;
      }
   }

   public ItemStack func_77979_a(int var1) {
      int ☃ = Math.min(☃, this.field_77994_a);
      ItemStack ☃x = this.func_77946_l();
      ☃x.func_190920_e(☃);
      this.func_190918_g(☃);
      return ☃x;
   }

   public Item func_77973_b() {
      return this.field_190928_g ? Items.field_190931_a : this.field_151002_e;
   }

   public EnumActionResult func_196084_a(ItemUseContext var1) {
      EntityPlayer ☃ = ☃.func_195999_j();
      BlockPos ☃x = ☃.func_195995_a();
      BlockWorldState ☃xx = new BlockWorldState(☃.func_195991_k(), ☃x, false);
      if (☃ != null && !☃.field_71075_bZ.field_75099_e && !this.func_206847_b(☃.func_195991_k().func_205772_D(), ☃xx)) {
         return EnumActionResult.PASS;
      } else {
         Item ☃ = this.func_77973_b();
         EnumActionResult ☃x = ☃.func_195939_a(☃);
         if (☃ != null && ☃x == EnumActionResult.SUCCESS) {
            ☃.func_71029_a(StatList.field_75929_E.func_199076_b(☃));
         }

         return ☃x;
      }
   }

   public float func_150997_a(IBlockState var1) {
      return this.func_77973_b().func_150893_a(this, ☃);
   }

   public ActionResult<ItemStack> func_77957_a(World var1, EntityPlayer var2, EnumHand var3) {
      return this.func_77973_b().func_77659_a(☃, ☃, ☃);
   }

   public ItemStack func_77950_b(World var1, EntityLivingBase var2) {
      return this.func_77973_b().func_77654_b(this, ☃, ☃);
   }

   public NBTTagCompound func_77955_b(NBTTagCompound var1) {
      ResourceLocation ☃ = IRegistry.field_212630_s.func_177774_c(this.func_77973_b());
      ☃.func_74778_a("id", ☃ == null ? "minecraft:air" : ☃.toString());
      ☃.func_74774_a("Count", (byte)this.field_77994_a);
      if (this.field_77990_d != null) {
         ☃.func_74782_a("tag", this.field_77990_d);
      }

      return ☃;
   }

   public int func_77976_d() {
      return this.func_77973_b().func_77639_j();
   }

   public boolean func_77985_e() {
      return this.func_77976_d() > 1 && (!this.func_77984_f() || !this.func_77951_h());
   }

   public boolean func_77984_f() {
      if (!this.field_190928_g && this.func_77973_b().func_77612_l() > 0) {
         NBTTagCompound ☃ = this.func_77978_p();
         return ☃ == null || !☃.func_74767_n("Unbreakable");
      } else {
         return false;
      }
   }

   public boolean func_77951_h() {
      return this.func_77984_f() && this.func_77952_i() > 0;
   }

   public int func_77952_i() {
      return this.field_77990_d == null ? 0 : this.field_77990_d.func_74762_e("Damage");
   }

   public void func_196085_b(int var1) {
      this.func_196082_o().func_74768_a("Damage", Math.max(0, ☃));
   }

   public int func_77958_k() {
      return this.func_77973_b().func_77612_l();
   }

   public boolean func_96631_a(int var1, Random var2, @Nullable EntityPlayerMP var3) {
      if (!this.func_77984_f()) {
         return false;
      } else {
         if (☃ > 0) {
            int ☃ = EnchantmentHelper.func_77506_a(Enchantments.field_185307_s, this);
            int ☃x = 0;

            for(int ☃xx = 0; ☃ > 0 && ☃xx < ☃; ++☃xx) {
               if (EnchantmentDurability.func_92097_a(this, ☃, ☃)) {
                  ++☃x;
               }
            }

            ☃ -= ☃x;
            if (☃ <= 0) {
               return false;
            }
         }

         if (☃ != null && ☃ != 0) {
            CriteriaTriggers.field_193132_s.func_193158_a(☃, this, this.func_77952_i() + ☃);
         }

         int ☃ = this.func_77952_i() + ☃;
         this.func_196085_b(☃);
         return ☃ >= this.func_77958_k();
      }
   }

   public void func_77972_a(int var1, EntityLivingBase var2) {
      if (!(☃ instanceof EntityPlayer) || !((EntityPlayer)☃).field_71075_bZ.field_75098_d) {
         if (this.func_77984_f()) {
            if (this.func_96631_a(☃, ☃.func_70681_au(), ☃ instanceof EntityPlayerMP ? (EntityPlayerMP)☃ : null)) {
               ☃.func_70669_a(this);
               Item ☃ = this.func_77973_b();
               this.func_190918_g(1);
               if (☃ instanceof EntityPlayer) {
                  ((EntityPlayer)☃).func_71029_a(StatList.field_199088_e.func_199076_b(☃));
               }

               this.func_196085_b(0);
            }
         }
      }
   }

   public void func_77961_a(EntityLivingBase var1, EntityPlayer var2) {
      Item ☃ = this.func_77973_b();
      if (☃.func_77644_a(this, ☃, ☃)) {
         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(☃));
      }
   }

   public void func_179548_a(World var1, IBlockState var2, BlockPos var3, EntityPlayer var4) {
      Item ☃ = this.func_77973_b();
      if (☃.func_179218_a(this, ☃, ☃, ☃, ☃)) {
         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(☃));
      }
   }

   public boolean func_150998_b(IBlockState var1) {
      return this.func_77973_b().func_150897_b(☃);
   }

   public boolean func_111282_a(EntityPlayer var1, EntityLivingBase var2, EnumHand var3) {
      return this.func_77973_b().func_111207_a(this, ☃, ☃, ☃);
   }

   public ItemStack func_77946_l() {
      ItemStack ☃ = new ItemStack(this.func_77973_b(), this.field_77994_a);
      ☃.func_190915_d(this.func_190921_D());
      if (this.field_77990_d != null) {
         ☃.field_77990_d = this.field_77990_d.func_74737_b();
      }

      return ☃;
   }

   public static boolean func_77970_a(ItemStack var0, ItemStack var1) {
      if (☃.func_190926_b() && ☃.func_190926_b()) {
         return true;
      } else if (☃.func_190926_b() || ☃.func_190926_b()) {
         return false;
      } else if (☃.field_77990_d == null && ☃.field_77990_d != null) {
         return false;
      } else {
         return ☃.field_77990_d == null || ☃.field_77990_d.equals(☃.field_77990_d);
      }
   }

   public static boolean func_77989_b(ItemStack var0, ItemStack var1) {
      if (☃.func_190926_b() && ☃.func_190926_b()) {
         return true;
      } else {
         return !☃.func_190926_b() && !☃.func_190926_b() ? ☃.func_77959_d(☃) : false;
      }
   }

   private boolean func_77959_d(ItemStack var1) {
      if (this.field_77994_a != ☃.field_77994_a) {
         return false;
      } else if (this.func_77973_b() != ☃.func_77973_b()) {
         return false;
      } else if (this.field_77990_d == null && ☃.field_77990_d != null) {
         return false;
      } else {
         return this.field_77990_d == null || this.field_77990_d.equals(☃.field_77990_d);
      }
   }

   public static boolean func_179545_c(ItemStack var0, ItemStack var1) {
      if (☃ == ☃) {
         return true;
      } else {
         return !☃.func_190926_b() && !☃.func_190926_b() ? ☃.func_77969_a(☃) : false;
      }
   }

   public static boolean func_185132_d(ItemStack var0, ItemStack var1) {
      if (☃ == ☃) {
         return true;
      } else {
         return !☃.func_190926_b() && !☃.func_190926_b() ? ☃.func_185136_b(☃) : false;
      }
   }

   public boolean func_77969_a(ItemStack var1) {
      return !☃.func_190926_b() && this.func_77973_b() == ☃.func_77973_b();
   }

   public boolean func_185136_b(ItemStack var1) {
      if (!this.func_77984_f()) {
         return this.func_77969_a(☃);
      } else {
         return !☃.func_190926_b() && this.func_77973_b() == ☃.func_77973_b();
      }
   }

   public String func_77977_a() {
      return this.func_77973_b().func_77667_c(this);
   }

   public String toString() {
      return this.field_77994_a + "x" + this.func_77973_b().func_77658_a();
   }

   public void func_77945_a(World var1, Entity var2, int var3, boolean var4) {
      if (this.field_77992_b > 0) {
         --this.field_77992_b;
      }

      if (this.func_77973_b() != null) {
         this.func_77973_b().func_77663_a(this, ☃, ☃, ☃, ☃);
      }
   }

   public void func_77980_a(World var1, EntityPlayer var2, int var3) {
      ☃.func_71064_a(StatList.field_188066_af.func_199076_b(this.func_77973_b()), ☃);
      this.func_77973_b().func_77622_d(this, ☃, ☃);
   }

   public int func_77988_m() {
      return this.func_77973_b().func_77626_a(this);
   }

   public EnumAction func_77975_n() {
      return this.func_77973_b().func_77661_b(this);
   }

   public void func_77974_b(World var1, EntityLivingBase var2, int var3) {
      this.func_77973_b().func_77615_a(this, ☃, ☃, ☃);
   }

   public boolean func_77942_o() {
      return !this.field_190928_g && this.field_77990_d != null && !this.field_77990_d.isEmpty();
   }

   @Nullable
   public NBTTagCompound func_77978_p() {
      return this.field_77990_d;
   }

   public NBTTagCompound func_196082_o() {
      if (this.field_77990_d == null) {
         this.func_77982_d(new NBTTagCompound());
      }

      return this.field_77990_d;
   }

   public NBTTagCompound func_190925_c(String var1) {
      if (this.field_77990_d != null && this.field_77990_d.func_150297_b(☃, 10)) {
         return this.field_77990_d.func_74775_l(☃);
      } else {
         NBTTagCompound ☃ = new NBTTagCompound();
         this.func_77983_a(☃, ☃);
         return ☃;
      }
   }

   @Nullable
   public NBTTagCompound func_179543_a(String var1) {
      return this.field_77990_d != null && this.field_77990_d.func_150297_b(☃, 10) ? this.field_77990_d.func_74775_l(☃) : null;
   }

   public void func_196083_e(String var1) {
      if (this.field_77990_d != null && this.field_77990_d.func_74764_b(☃)) {
         this.field_77990_d.func_82580_o(☃);
         if (this.field_77990_d.isEmpty()) {
            this.field_77990_d = null;
         }
      }
   }

   public NBTTagList func_77986_q() {
      return this.field_77990_d != null ? this.field_77990_d.func_150295_c("Enchantments", 10) : new NBTTagList();
   }

   public void func_77982_d(@Nullable NBTTagCompound var1) {
      this.field_77990_d = ☃;
   }

   public ITextComponent func_200301_q() {
      NBTTagCompound ☃ = this.func_179543_a("display");
      if (☃ != null && ☃.func_150297_b("Name", 8)) {
         try {
            ITextComponent ☃x = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("Name"));
            if (☃x != null) {
               return ☃x;
            }

            ☃.func_82580_o("Name");
         } catch (JsonParseException var3) {
            ☃.func_82580_o("Name");
         }
      }

      return this.func_77973_b().func_200295_i(this);
   }

   public ItemStack func_200302_a(@Nullable ITextComponent var1) {
      NBTTagCompound ☃ = this.func_190925_c("display");
      if (☃ != null) {
         ☃.func_74778_a("Name", ITextComponent.Serializer.func_150696_a(☃));
      } else {
         ☃.func_82580_o("Name");
      }

      return this;
   }

   public void func_135074_t() {
      NBTTagCompound ☃ = this.func_179543_a("display");
      if (☃ != null) {
         ☃.func_82580_o("Name");
         if (☃.isEmpty()) {
            this.func_196083_e("display");
         }
      }

      if (this.field_77990_d != null && this.field_77990_d.isEmpty()) {
         this.field_77990_d = null;
      }
   }

   public boolean func_82837_s() {
      NBTTagCompound ☃ = this.func_179543_a("display");
      return ☃ != null && ☃.func_150297_b("Name", 8);
   }

   public EnumRarity func_77953_t() {
      return this.func_77973_b().func_77613_e(this);
   }

   public boolean func_77956_u() {
      if (!this.func_77973_b().func_77616_k(this)) {
         return false;
      } else {
         return !this.func_77948_v();
      }
   }

   public void func_77966_a(Enchantment var1, int var2) {
      this.func_196082_o();
      if (!this.field_77990_d.func_150297_b("Enchantments", 9)) {
         this.field_77990_d.func_74782_a("Enchantments", new NBTTagList());
      }

      NBTTagList ☃ = this.field_77990_d.func_150295_c("Enchantments", 10);
      NBTTagCompound ☃x = new NBTTagCompound();
      ☃x.func_74778_a("id", String.valueOf(IRegistry.field_212628_q.func_177774_c(☃)));
      ☃x.func_74777_a("lvl", (short)((byte)☃));
      ☃.add((INBTBase)☃x);
   }

   public boolean func_77948_v() {
      if (this.field_77990_d != null && this.field_77990_d.func_150297_b("Enchantments", 9)) {
         return !this.field_77990_d.func_150295_c("Enchantments", 10).isEmpty();
      } else {
         return false;
      }
   }

   public void func_77983_a(String var1, INBTBase var2) {
      this.func_196082_o().func_74782_a(☃, ☃);
   }

   public boolean func_82839_y() {
      return this.field_82843_f != null;
   }

   public void func_82842_a(@Nullable EntityItemFrame var1) {
      this.field_82843_f = ☃;
   }

   @Nullable
   public EntityItemFrame func_82836_z() {
      return this.field_190928_g ? null : this.field_82843_f;
   }

   public int func_82838_A() {
      return this.func_77942_o() && this.field_77990_d.func_150297_b("RepairCost", 3) ? this.field_77990_d.func_74762_e("RepairCost") : 0;
   }

   public void func_82841_c(int var1) {
      this.func_196082_o().func_74768_a("RepairCost", ☃);
   }

   public Multimap<String, AttributeModifier> func_111283_C(EntityEquipmentSlot var1) {
      Multimap<String, AttributeModifier> ☃;
      if (this.func_77942_o() && this.field_77990_d.func_150297_b("AttributeModifiers", 9)) {
         ☃ = HashMultimap.create();
         NBTTagList ☃x = this.field_77990_d.func_150295_c("AttributeModifiers", 10);

         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            NBTTagCompound ☃xxx = ☃x.func_150305_b(☃xx);
            AttributeModifier ☃xxxx = SharedMonsterAttributes.func_111259_a(☃xxx);
            if (☃xxxx != null
               && (!☃xxx.func_150297_b("Slot", 8) || ☃xxx.func_74779_i("Slot").equals(☃.func_188450_d()))
               && ☃xxxx.func_111167_a().getLeastSignificantBits() != 0L
               && ☃xxxx.func_111167_a().getMostSignificantBits() != 0L) {
               ☃.put(☃xxx.func_74779_i("AttributeName"), ☃xxxx);
            }
         }
      } else {
         ☃ = this.func_77973_b().func_111205_h(☃);
      }

      return ☃;
   }

   public void func_185129_a(String var1, AttributeModifier var2, @Nullable EntityEquipmentSlot var3) {
      this.func_196082_o();
      if (!this.field_77990_d.func_150297_b("AttributeModifiers", 9)) {
         this.field_77990_d.func_74782_a("AttributeModifiers", new NBTTagList());
      }

      NBTTagList ☃ = this.field_77990_d.func_150295_c("AttributeModifiers", 10);
      NBTTagCompound ☃x = SharedMonsterAttributes.func_111262_a(☃);
      ☃x.func_74778_a("AttributeName", ☃);
      if (☃ != null) {
         ☃x.func_74778_a("Slot", ☃.func_188450_d());
      }

      ☃.add((INBTBase)☃x);
   }

   public ITextComponent func_151000_E() {
      ITextComponent ☃ = new TextComponentString("").func_150257_a(this.func_200301_q());
      if (this.func_82837_s()) {
         ☃.func_211708_a(TextFormatting.ITALIC);
      }

      ITextComponent ☃ = TextComponentUtils.func_197676_a(☃);
      if (!this.field_190928_g) {
         NBTTagCompound ☃x = this.func_77955_b(new NBTTagCompound());
         ☃.func_211708_a(this.func_77953_t().field_77937_e)
            .func_211710_a(var1x -> var1x.func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new TextComponentString(☃.toString()))));
      }

      return ☃;
   }

   private static boolean func_206846_a(BlockWorldState var0, @Nullable BlockWorldState var1) {
      if (☃ == null || ☃.func_177509_a() != ☃.func_177509_a()) {
         return false;
      } else if (☃.func_177507_b() == null && ☃.func_177507_b() == null) {
         return true;
      } else {
         return ☃.func_177507_b() != null && ☃.func_177507_b() != null
            ? Objects.equals(☃.func_177507_b().func_189515_b(new NBTTagCompound()), ☃.func_177507_b().func_189515_b(new NBTTagCompound()))
            : false;
      }
   }

   public boolean func_206848_a(NetworkTagManager var1, BlockWorldState var2) {
      if (func_206846_a(☃, this.field_179552_h)) {
         return this.field_179553_i;
      } else {
         this.field_179552_h = ☃;
         if (this.func_77942_o() && this.field_77990_d.func_150297_b("CanDestroy", 9)) {
            NBTTagList ☃ = this.field_77990_d.func_150295_c("CanDestroy", 8);

            for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
               String ☃xx = ☃.func_150307_f(☃x);

               try {
                  Predicate<BlockWorldState> ☃xxx = BlockPredicateArgument.func_199824_a().parse(new StringReader(☃xx)).create(☃);
                  if (☃xxx.test(☃)) {
                     this.field_179553_i = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.field_179553_i = false;
         return false;
      }
   }

   public boolean func_206847_b(NetworkTagManager var1, BlockWorldState var2) {
      if (func_206846_a(☃, this.field_179550_j)) {
         return this.field_179551_k;
      } else {
         this.field_179550_j = ☃;
         if (this.func_77942_o() && this.field_77990_d.func_150297_b("CanPlaceOn", 9)) {
            NBTTagList ☃ = this.field_77990_d.func_150295_c("CanPlaceOn", 8);

            for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
               String ☃xx = ☃.func_150307_f(☃x);

               try {
                  Predicate<BlockWorldState> ☃xxx = BlockPredicateArgument.func_199824_a().parse(new StringReader(☃xx)).create(☃);
                  if (☃xxx.test(☃)) {
                     this.field_179551_k = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.field_179551_k = false;
         return false;
      }
   }

   public int func_190921_D() {
      return this.field_77992_b;
   }

   public void func_190915_d(int var1) {
      this.field_77992_b = ☃;
   }

   public int func_190916_E() {
      return this.field_190928_g ? 0 : this.field_77994_a;
   }

   public void func_190920_e(int var1) {
      this.field_77994_a = ☃;
      this.func_190923_F();
   }

   public void func_190917_f(int var1) {
      this.func_190920_e(this.field_77994_a + ☃);
   }

   public void func_190918_g(int var1) {
      this.func_190917_f(-☃);
   }
}
