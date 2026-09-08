package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class DamageSourceNetherBed extends DamageSource {
   protected DamageSourceNetherBed() {
      super("netherBed");
      this.func_76351_m();
      this.func_94540_d();
   }

   @Override
   public ITextComponent func_151519_b(EntityLivingBase var1) {
      ITextComponent ☃ = TextComponentUtils.func_197676_a(new TextComponentTranslation("death.attack.netherBed.link"))
         .func_211710_a(
            var0 -> var0.func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MCPE-28723"))
                  .func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("MCPE-28723")))
         );
      return new TextComponentTranslation("death.attack.netherBed.message", ☃.func_145748_c_(), ☃);
   }
}
