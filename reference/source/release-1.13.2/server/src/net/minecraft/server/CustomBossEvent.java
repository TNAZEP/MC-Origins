package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;

public class CustomBossEvent extends BossInfoServer {
   private final ResourceLocation field_201373_h;
   private final Set<UUID> field_201374_i = Sets.newHashSet();
   private int field_201375_j;
   private int field_201376_k = 100;

   public CustomBossEvent(ResourceLocation var1, ITextComponent var2) {
      super(☃, BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
      this.field_201373_h = ☃;
      this.func_186735_a(0.0F);
   }

   public ResourceLocation func_201364_a() {
      return this.field_201373_h;
   }

   @Override
   public void func_186760_a(EntityPlayerMP var1) {
      super.func_186760_a(☃);
      this.field_201374_i.add(☃.func_110124_au());
   }

   public void func_201372_a(UUID var1) {
      this.field_201374_i.add(☃);
   }

   @Override
   public void func_186761_b(EntityPlayerMP var1) {
      super.func_186761_b(☃);
      this.field_201374_i.remove(☃.func_110124_au());
   }

   @Override
   public void func_201360_b() {
      super.func_201360_b();
      this.field_201374_i.clear();
   }

   public int func_201365_c() {
      return this.field_201375_j;
   }

   public int func_201367_d() {
      return this.field_201376_k;
   }

   public void func_201362_a(int var1) {
      this.field_201375_j = ☃;
      this.func_186735_a(MathHelper.func_76131_a((float)☃ / (float)this.field_201376_k, 0.0F, 1.0F));
   }

   public void func_201366_b(int var1) {
      this.field_201376_k = ☃;
      this.func_186735_a(MathHelper.func_76131_a((float)this.field_201375_j / (float)☃, 0.0F, 1.0F));
   }

   public final ITextComponent func_201369_e() {
      return TextComponentUtils.func_197676_a(this.func_186744_e())
         .func_211710_a(
            var1 -> var1.func_150238_a(this.func_186736_g().func_201482_a())
                  .func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(this.func_201364_a().toString())))
                  .func_179989_a(this.func_201364_a().toString())
         );
   }

   public boolean func_201368_a(Collection<EntityPlayerMP> var1) {
      Set<UUID> ☃ = Sets.newHashSet();
      Set<EntityPlayerMP> ☃x = Sets.<EntityPlayerMP>newHashSet();

      for(UUID ☃xx : this.field_201374_i) {
         boolean ☃xxx = false;

         for(EntityPlayerMP ☃xxxx : ☃) {
            if (☃xxxx.func_110124_au().equals(☃xx)) {
               ☃xxx = true;
               break;
            }
         }

         if (!☃xxx) {
            ☃.add(☃xx);
         }
      }

      for(EntityPlayerMP ☃xx : ☃) {
         boolean ☃xxx = false;

         for(UUID ☃xxxx : this.field_201374_i) {
            if (☃xx.func_110124_au().equals(☃xxxx)) {
               ☃xxx = true;
               break;
            }
         }

         if (!☃xxx) {
            ☃x.add(☃xx);
         }
      }

      for(UUID ☃xx : ☃) {
         for(EntityPlayerMP ☃xxx : this.func_186757_c()) {
            if (☃xxx.func_110124_au().equals(☃xx)) {
               this.func_186761_b(☃xxx);
               break;
            }
         }

         this.field_201374_i.remove(☃xx);
      }

      for(EntityPlayerMP ☃xx : ☃x) {
         this.func_186760_a(☃xx);
      }

      return !☃.isEmpty() || !☃x.isEmpty();
   }

   public NBTTagCompound func_201370_f() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74778_a("Name", ITextComponent.Serializer.func_150696_a(this.field_186749_a));
      ☃.func_74757_a("Visible", this.func_201359_g());
      ☃.func_74768_a("Value", this.field_201375_j);
      ☃.func_74768_a("Max", this.field_201376_k);
      ☃.func_74778_a("Color", this.func_186736_g().func_201480_b());
      ☃.func_74778_a("Overlay", this.func_186740_h().func_201486_a());
      ☃.func_74757_a("DarkenScreen", this.func_186734_i());
      ☃.func_74757_a("PlayBossMusic", this.func_186747_j());
      ☃.func_74757_a("CreateWorldFog", this.func_186748_k());
      NBTTagList ☃x = new NBTTagList();

      for(UUID ☃xx : this.field_201374_i) {
         ☃x.add((INBTBase)NBTUtil.func_186862_a(☃xx));
      }

      ☃.func_74782_a("Players", ☃x);
      return ☃;
   }

   public static CustomBossEvent func_201371_a(NBTTagCompound var0, ResourceLocation var1) {
      CustomBossEvent ☃ = new CustomBossEvent(☃, ITextComponent.Serializer.func_150699_a(☃.func_74779_i("Name")));
      ☃.func_186758_d(☃.func_74767_n("Visible"));
      ☃.func_201362_a(☃.func_74762_e("Value"));
      ☃.func_201366_b(☃.func_74762_e("Max"));
      ☃.func_186745_a(BossInfo.Color.func_201481_a(☃.func_74779_i("Color")));
      ☃.func_186746_a(BossInfo.Overlay.func_201485_a(☃.func_74779_i("Overlay")));
      ☃.func_186741_a(☃.func_74767_n("DarkenScreen"));
      ☃.func_186742_b(☃.func_74767_n("PlayBossMusic"));
      ☃.func_186743_c(☃.func_74767_n("CreateWorldFog"));
      NBTTagList ☃x = ☃.func_150295_c("Players", 10);

      for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
         ☃.func_201372_a(NBTUtil.func_186860_b(☃x.func_150305_b(☃xx)));
      }

      return ☃;
   }

   public void func_201361_c(EntityPlayerMP var1) {
      if (this.field_201374_i.contains(☃.func_110124_au())) {
         this.func_186760_a(☃);
      }
   }

   public void func_201363_d(EntityPlayerMP var1) {
      super.func_186761_b(☃);
   }
}
