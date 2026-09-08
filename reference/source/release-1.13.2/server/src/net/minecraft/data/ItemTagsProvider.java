package net.minecraft.data;

import com.google.common.collect.Lists;
import java.nio.file.Path;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemTagsProvider extends TagsProvider<Item> {
   private static final Logger field_203426_d = LogManager.getLogger();

   public ItemTagsProvider(DataGenerator var1) {
      super(☃, IRegistry.field_212630_s);
   }

   @Override
   protected void func_200432_c() {
      this.func_200438_a(BlockTags.field_199897_a, ItemTags.field_199904_a);
      this.func_200438_a(BlockTags.field_199898_b, ItemTags.field_199905_b);
      this.func_200438_a(BlockTags.field_200026_c, ItemTags.field_200033_c);
      this.func_200438_a(BlockTags.field_200151_d, ItemTags.field_200153_d);
      this.func_200438_a(BlockTags.field_200027_d, ItemTags.field_200034_d);
      this.func_200438_a(BlockTags.field_200028_e, ItemTags.field_200035_e);
      this.func_200438_a(BlockTags.field_200152_g, ItemTags.field_200154_g);
      this.func_200438_a(BlockTags.field_202894_h, ItemTags.field_202898_h);
      this.func_200438_a(BlockTags.field_202895_i, ItemTags.field_202899_i);
      this.func_200438_a(BlockTags.field_202896_j, ItemTags.field_202900_j);
      this.func_200438_a(BlockTags.field_200029_f, ItemTags.field_200036_f);
      this.func_200438_a(BlockTags.field_200030_g, ItemTags.field_200037_g);
      this.func_200438_a(BlockTags.field_203286_o, ItemTags.field_203295_o);
      this.func_200438_a(BlockTags.field_203285_n, ItemTags.field_203294_n);
      this.func_200438_a(BlockTags.field_203287_p, ItemTags.field_203296_p);
      this.func_200438_a(BlockTags.field_203288_q, ItemTags.field_203297_q);
      this.func_200438_a(BlockTags.field_203290_s, ItemTags.field_203299_s);
      this.func_200438_a(BlockTags.field_203289_r, ItemTags.field_203298_r);
      this.func_200438_a(BlockTags.field_200031_h, ItemTags.field_200038_h);
      this.func_200438_a(BlockTags.field_203436_u, ItemTags.field_203440_u);
      this.func_200438_a(BlockTags.field_203292_x, ItemTags.field_203442_w);
      this.func_200438_a(BlockTags.field_203291_w, ItemTags.field_203441_v);
      this.func_200438_a(BlockTags.field_200572_k, ItemTags.field_203443_x);
      this.func_200438_a(BlockTags.field_203437_y, ItemTags.field_203444_y);
      this.func_200438_a(BlockTags.field_206952_E, ItemTags.field_206963_E);
      this.func_200438_a(BlockTags.field_212186_k, ItemTags.field_212188_k);
      this.func_200438_a(BlockTags.field_212185_E, ItemTags.field_212187_B);
      this.func_200426_a(ItemTags.field_202901_n)
         .func_200573_a(
            Items.field_196191_eg,
            Items.field_196192_eh,
            Items.field_196193_ei,
            Items.field_196194_ej,
            Items.field_196195_ek,
            Items.field_196196_el,
            Items.field_196197_em,
            Items.field_196198_en,
            Items.field_196199_eo,
            Items.field_196200_ep,
            Items.field_196201_eq,
            Items.field_196202_er,
            Items.field_196203_es,
            Items.field_196204_et,
            Items.field_196205_eu,
            Items.field_196206_ev
         );
      this.func_200426_a(ItemTags.field_202902_o)
         .func_200573_a(
            Items.field_151124_az, Items.field_185150_aH, Items.field_185151_aI, Items.field_185152_aJ, Items.field_185153_aK, Items.field_185154_aL
         );
      this.func_200426_a(ItemTags.field_206964_G)
         .func_200573_a(
            Items.field_196086_aW, Items.field_196102_ba, Items.field_196087_aX, Items.field_196104_bb, Items.field_196089_aZ, Items.field_196088_aY
         );
   }

   protected void func_200438_a(Tag<Block> var1, Tag<Item> var2) {
      Tag.Builder<Item> ☃ = this.func_200426_a(☃);

      for(Tag.ITagEntry<Block> ☃x : ☃.func_200570_b()) {
         Tag.ITagEntry<Item> ☃xx = this.func_200439_a(☃x);
         ☃.func_200575_a(☃xx);
      }
   }

   private Tag.ITagEntry<Item> func_200439_a(Tag.ITagEntry<Block> var1) {
      if (☃ instanceof Tag.TagEntry) {
         return new Tag.TagEntry<>(((Tag.TagEntry)☃).func_200577_a());
      } else if (☃ instanceof Tag.ListEntry) {
         List<Item> ☃ = Lists.<Item>newArrayList();

         for(Block ☃x : ((Tag.ListEntry)☃).func_200578_a()) {
            Item ☃xx = ☃x.func_199767_j();
            if (☃xx == Items.field_190931_a) {
               field_203426_d.warn("Itemless block copied to item tag: {}", IRegistry.field_212618_g.func_177774_c(☃x));
            } else {
               ☃.add(☃xx);
            }
         }

         return new Tag.ListEntry<>(☃);
      } else {
         throw new UnsupportedOperationException("Unknown tag entry " + ☃);
      }
   }

   @Override
   protected Path func_200431_a(ResourceLocation var1) {
      return this.field_200433_a.func_200391_b().resolve("data/" + ☃.func_110624_b() + "/tags/items/" + ☃.func_110623_a() + ".json");
   }

   @Override
   public String func_200397_b() {
      return "Item Tags";
   }

   @Override
   protected void func_200429_a(TagCollection<Item> var1) {
      ItemTags.func_199902_a(☃);
   }
}
