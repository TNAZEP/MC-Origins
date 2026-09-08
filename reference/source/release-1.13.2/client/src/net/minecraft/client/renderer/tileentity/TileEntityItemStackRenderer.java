package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAbstractSkull;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ModelShield;
import net.minecraft.client.renderer.entity.model.ModelTrident;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityConduit;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.tileentity.TileEntityTrappedChest;
import org.apache.commons.lang3.StringUtils;

public class TileEntityItemStackRenderer {
   private static final TileEntityShulkerBox[] field_191274_b = (TileEntityShulkerBox[])Arrays.stream(EnumDyeColor.values())
      .sorted(Comparator.comparingInt(EnumDyeColor::func_196059_a))
      .map(TileEntityShulkerBox::new)
      .toArray(var0 -> new TileEntityShulkerBox[var0]);
   private static final TileEntityShulkerBox field_204401_c = new TileEntityShulkerBox(null);
   public static TileEntityItemStackRenderer field_147719_a = new TileEntityItemStackRenderer();
   private final TileEntityChest field_147717_b = new TileEntityChest();
   private final TileEntityChest field_147718_c = new TileEntityTrappedChest();
   private final TileEntityEnderChest field_147716_d = new TileEntityEnderChest();
   private final TileEntityBanner field_179024_e = new TileEntityBanner();
   private final TileEntityBed field_193843_g = new TileEntityBed();
   private final TileEntitySkull field_179023_f = new TileEntitySkull();
   private final TileEntityConduit field_205085_j = new TileEntityConduit();
   private final ModelShield field_187318_g = new ModelShield();
   private final ModelTrident field_203084_j = new ModelTrident();

   public void func_179022_a(ItemStack var1) {
      Item ☃ = ☃.func_77973_b();
      if (☃ instanceof ItemBanner) {
         this.field_179024_e.func_195534_a(☃, ((ItemBanner)☃).func_195948_b());
         TileEntityRendererDispatcher.field_147556_a.func_203601_b(this.field_179024_e);
      } else if (☃ instanceof ItemBlock && ((ItemBlock)☃).func_179223_d() instanceof BlockBed) {
         this.field_193843_g.func_193052_a(((BlockBed)((ItemBlock)☃).func_179223_d()).func_196350_d());
         TileEntityRendererDispatcher.field_147556_a.func_203601_b(this.field_193843_g);
      } else if (☃ == Items.field_185159_cQ) {
         if (☃.func_179543_a("BlockEntityTag") != null) {
            this.field_179024_e.func_195534_a(☃, ItemShield.func_195979_f(☃));
            Minecraft.func_71410_x()
               .func_110434_K()
               .func_110577_a(
                  BannerTextures.field_187485_b
                     .func_187478_a(this.field_179024_e.func_175116_e(), this.field_179024_e.func_175114_c(), this.field_179024_e.func_175110_d())
               );
         } else {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(BannerTextures.field_187486_c);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(1.0F, -1.0F, -1.0F);
         this.field_187318_g.func_187062_a();
         if (☃.func_77962_s()) {
            this.func_211271_a(this.field_187318_g::func_187062_a);
         }

         GlStateManager.func_179121_F();
      } else if (☃ instanceof ItemBlock && ((ItemBlock)☃).func_179223_d() instanceof BlockAbstractSkull) {
         GameProfile ☃ = null;
         if (☃.func_77942_o()) {
            NBTTagCompound ☃x = ☃.func_77978_p();
            if (☃x.func_150297_b("SkullOwner", 10)) {
               ☃ = NBTUtil.func_152459_a(☃x.func_74775_l("SkullOwner"));
            } else if (☃x.func_150297_b("SkullOwner", 8) && !StringUtils.isBlank(☃x.func_74779_i("SkullOwner"))) {
               GameProfile var6 = new GameProfile(null, ☃x.func_74779_i("SkullOwner"));
               ☃ = TileEntitySkull.func_174884_b(var6);
               ☃x.func_82580_o("SkullOwner");
               ☃x.func_74782_a("SkullOwner", NBTUtil.func_180708_a(new NBTTagCompound(), ☃));
            }
         }

         if (TileEntitySkullRenderer.field_147536_b != null) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179129_p();
            TileEntitySkullRenderer.field_147536_b
               .func_199355_a(0.0F, 0.0F, 0.0F, null, 180.0F, ((BlockAbstractSkull)((ItemBlock)☃).func_179223_d()).func_196292_N_(), ☃, -1, 0.0F);
            GlStateManager.func_179089_o();
            GlStateManager.func_179121_F();
         }
      } else if (☃ == Items.field_203184_eO) {
         Minecraft.func_71410_x().func_110434_K().func_110577_a(ModelTrident.field_203080_a);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(1.0F, -1.0F, -1.0F);
         this.field_203084_j.func_203079_a();
         if (☃.func_77962_s()) {
            this.func_211271_a(this.field_203084_j::func_203079_a);
         }

         GlStateManager.func_179121_F();
      } else if (☃ instanceof ItemBlock && ((ItemBlock)☃).func_179223_d() == Blocks.field_205165_jY) {
         TileEntityRendererDispatcher.field_147556_a.func_203601_b(this.field_205085_j);
      } else if (☃ == Blocks.field_150477_bB.func_199767_j()) {
         TileEntityRendererDispatcher.field_147556_a.func_203601_b(this.field_147716_d);
      } else if (☃ == Blocks.field_150447_bR.func_199767_j()) {
         TileEntityRendererDispatcher.field_147556_a.func_203601_b(this.field_147718_c);
      } else if (Block.func_149634_a(☃) instanceof BlockShulkerBox) {
         EnumDyeColor ☃ = BlockShulkerBox.func_190955_b(☃);
         if (☃ == null) {
            TileEntityRendererDispatcher.field_147556_a.func_203601_b(field_204401_c);
         } else {
            TileEntityRendererDispatcher.field_147556_a.func_203601_b(field_191274_b[☃.func_196059_a()]);
         }
      } else {
         TileEntityRendererDispatcher.field_147556_a.func_203601_b(this.field_147717_b);
      }
   }

   private void func_211271_a(Runnable var1) {
      GlStateManager.func_179124_c(0.5019608F, 0.2509804F, 0.8F);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(ItemRenderer.field_110798_h);
      ItemRenderer.func_211128_a(Minecraft.func_71410_x().func_110434_K(), ☃, 1);
   }
}
