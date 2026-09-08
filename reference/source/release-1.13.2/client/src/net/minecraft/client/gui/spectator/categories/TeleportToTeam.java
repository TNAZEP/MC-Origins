package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject {
   private final List<ISpectatorMenuObject> field_178672_a = Lists.<ISpectatorMenuObject>newArrayList();

   public TeleportToTeam() {
      Minecraft ☃ = Minecraft.func_71410_x();

      for(ScorePlayerTeam ☃x : ☃.field_71441_e.func_96441_U().func_96525_g()) {
         this.field_178672_a.add(new TeleportToTeam.TeamSelectionObject(☃x));
      }
   }

   @Override
   public List<ISpectatorMenuObject> func_178669_a() {
      return this.field_178672_a;
   }

   @Override
   public ITextComponent func_178670_b() {
      return new TextComponentTranslation("spectatorMenu.team_teleport.prompt");
   }

   @Override
   public void func_178661_a(SpectatorMenu var1) {
      ☃.func_178647_a(this);
   }

   @Override
   public ITextComponent func_178664_z_() {
      return new TextComponentTranslation("spectatorMenu.team_teleport");
   }

   @Override
   public void func_178663_a(float var1, int var2) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiSpectator.field_175269_a);
      Gui.func_146110_a(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   @Override
   public boolean func_178662_A_() {
      for(ISpectatorMenuObject ☃ : this.field_178672_a) {
         if (☃.func_178662_A_()) {
            return true;
         }
      }

      return false;
   }

   class TeamSelectionObject implements ISpectatorMenuObject {
      private final ScorePlayerTeam field_178676_b;
      private final ResourceLocation field_178677_c;
      private final List<NetworkPlayerInfo> field_178675_d;

      public TeamSelectionObject(ScorePlayerTeam var2) {
         this.field_178676_b = ☃;
         this.field_178675_d = Lists.<NetworkPlayerInfo>newArrayList();

         for(String ☃ : ☃.func_96670_d()) {
            NetworkPlayerInfo ☃x = Minecraft.func_71410_x().func_147114_u().func_175104_a(☃);
            if (☃x != null) {
               this.field_178675_d.add(☃x);
            }
         }

         if (this.field_178675_d.isEmpty()) {
            this.field_178677_c = DefaultPlayerSkin.func_177335_a();
         } else {
            String ☃ = ((NetworkPlayerInfo)this.field_178675_d.get(new Random().nextInt(this.field_178675_d.size()))).func_178845_a().getName();
            this.field_178677_c = AbstractClientPlayer.func_110311_f(☃);
            AbstractClientPlayer.func_110304_a(this.field_178677_c, ☃);
         }
      }

      @Override
      public void func_178661_a(SpectatorMenu var1) {
         ☃.func_178647_a(new TeleportToPlayer(this.field_178675_d));
      }

      @Override
      public ITextComponent func_178664_z_() {
         return this.field_178676_b.func_96669_c();
      }

      @Override
      public void func_178663_a(float var1, int var2) {
         Integer ☃ = this.field_178676_b.func_178775_l().func_211163_e();
         if (☃ != null) {
            float ☃x = (float)(☃ >> 16 & 0xFF) / 255.0F;
            float ☃xx = (float)(☃ >> 8 & 0xFF) / 255.0F;
            float ☃xxx = (float)(☃ & 0xFF) / 255.0F;
            Gui.func_73734_a(1, 1, 15, 15, MathHelper.func_180183_b(☃x * ☃, ☃xx * ☃, ☃xxx * ☃) | ☃ << 24);
         }

         Minecraft.func_71410_x().func_110434_K().func_110577_a(this.field_178677_c);
         GlStateManager.func_179131_c(☃, ☃, ☃, (float)☃ / 255.0F);
         Gui.func_152125_a(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
         Gui.func_152125_a(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      }

      @Override
      public boolean func_178662_A_() {
         return !this.field_178675_d.isEmpty();
      }
   }
}
