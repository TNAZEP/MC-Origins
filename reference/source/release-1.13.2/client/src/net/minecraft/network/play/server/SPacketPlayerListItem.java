package net.minecraft.network.play.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;

public class SPacketPlayerListItem implements Packet<INetHandlerPlayClient> {
   private SPacketPlayerListItem.Action field_179770_a;
   private final List<SPacketPlayerListItem.AddPlayerData> field_179769_b = Lists.<SPacketPlayerListItem.AddPlayerData>newArrayList();

   public SPacketPlayerListItem() {
   }

   public SPacketPlayerListItem(SPacketPlayerListItem.Action var1, EntityPlayerMP... var2) {
      this.field_179770_a = ☃;

      for(EntityPlayerMP ☃ : ☃) {
         this.field_179769_b
            .add(new SPacketPlayerListItem.AddPlayerData(☃.func_146103_bH(), ☃.field_71138_i, ☃.field_71134_c.func_73081_b(), ☃.func_175396_E()));
      }
   }

   public SPacketPlayerListItem(SPacketPlayerListItem.Action var1, Iterable<EntityPlayerMP> var2) {
      this.field_179770_a = ☃;

      for(EntityPlayerMP ☃ : ☃) {
         this.field_179769_b
            .add(new SPacketPlayerListItem.AddPlayerData(☃.func_146103_bH(), ☃.field_71138_i, ☃.field_71134_c.func_73081_b(), ☃.func_175396_E()));
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179770_a = ☃.func_179257_a(SPacketPlayerListItem.Action.class);
      int ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         GameProfile ☃xx = null;
         int ☃xxx = 0;
         GameType ☃xxxx = null;
         ITextComponent ☃xxxxx = null;
         switch(this.field_179770_a) {
            case ADD_PLAYER:
               ☃xx = new GameProfile(☃.func_179253_g(), ☃.func_150789_c(16));
               int ☃xxxxxx = ☃.func_150792_a();
               int ☃xxxxxxx = 0;

               for(; ☃xxxxxxx < ☃xxxxxx; ++☃xxxxxxx) {
                  String ☃xxxxxxxx = ☃.func_150789_c(32767);
                  String ☃xxxxxxxxx = ☃.func_150789_c(32767);
                  if (☃.readBoolean()) {
                     ☃xx.getProperties().put(☃xxxxxxxx, new Property(☃xxxxxxxx, ☃xxxxxxxxx, ☃.func_150789_c(32767)));
                  } else {
                     ☃xx.getProperties().put(☃xxxxxxxx, new Property(☃xxxxxxxx, ☃xxxxxxxxx));
                  }
               }

               ☃xxxx = GameType.func_77146_a(☃.func_150792_a());
               ☃xxx = ☃.func_150792_a();
               if (☃.readBoolean()) {
                  ☃xxxxx = ☃.func_179258_d();
               }
               break;
            case UPDATE_GAME_MODE:
               ☃xx = new GameProfile(☃.func_179253_g(), null);
               ☃xxxx = GameType.func_77146_a(☃.func_150792_a());
               break;
            case UPDATE_LATENCY:
               ☃xx = new GameProfile(☃.func_179253_g(), null);
               ☃xxx = ☃.func_150792_a();
               break;
            case UPDATE_DISPLAY_NAME:
               ☃xx = new GameProfile(☃.func_179253_g(), null);
               if (☃.readBoolean()) {
                  ☃xxxxx = ☃.func_179258_d();
               }
               break;
            case REMOVE_PLAYER:
               ☃xx = new GameProfile(☃.func_179253_g(), null);
         }

         this.field_179769_b.add(new SPacketPlayerListItem.AddPlayerData(☃xx, ☃xxx, ☃xxxx, ☃xxxxx));
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179249_a(this.field_179770_a);
      ☃.func_150787_b(this.field_179769_b.size());

      for(SPacketPlayerListItem.AddPlayerData ☃ : this.field_179769_b) {
         switch(this.field_179770_a) {
            case ADD_PLAYER:
               ☃.func_179252_a(☃.func_179962_a().getId());
               ☃.func_180714_a(☃.func_179962_a().getName());
               ☃.func_150787_b(☃.func_179962_a().getProperties().size());

               for(Property ☃x : ☃.func_179962_a().getProperties().values()) {
                  ☃.func_180714_a(☃x.getName());
                  ☃.func_180714_a(☃x.getValue());
                  if (☃x.hasSignature()) {
                     ☃.writeBoolean(true);
                     ☃.func_180714_a(☃x.getSignature());
                  } else {
                     ☃.writeBoolean(false);
                  }
               }

               ☃.func_150787_b(☃.func_179960_c().func_77148_a());
               ☃.func_150787_b(☃.func_179963_b());
               if (☃.func_179961_d() == null) {
                  ☃.writeBoolean(false);
               } else {
                  ☃.writeBoolean(true);
                  ☃.func_179256_a(☃.func_179961_d());
               }
               break;
            case UPDATE_GAME_MODE:
               ☃.func_179252_a(☃.func_179962_a().getId());
               ☃.func_150787_b(☃.func_179960_c().func_77148_a());
               break;
            case UPDATE_LATENCY:
               ☃.func_179252_a(☃.func_179962_a().getId());
               ☃.func_150787_b(☃.func_179963_b());
               break;
            case UPDATE_DISPLAY_NAME:
               ☃.func_179252_a(☃.func_179962_a().getId());
               if (☃.func_179961_d() == null) {
                  ☃.writeBoolean(false);
               } else {
                  ☃.writeBoolean(true);
                  ☃.func_179256_a(☃.func_179961_d());
               }
               break;
            case REMOVE_PLAYER:
               ☃.func_179252_a(☃.func_179962_a().getId());
         }
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147256_a(this);
   }

   public List<SPacketPlayerListItem.AddPlayerData> func_179767_a() {
      return this.field_179769_b;
   }

   public SPacketPlayerListItem.Action func_179768_b() {
      return this.field_179770_a;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.field_179770_a).add("entries", this.field_179769_b).toString();
   }

   public static enum Action {
      ADD_PLAYER,
      UPDATE_GAME_MODE,
      UPDATE_LATENCY,
      UPDATE_DISPLAY_NAME,
      REMOVE_PLAYER;
   }

   public class AddPlayerData {
      private final int field_179966_b;
      private final GameType field_179967_c;
      private final GameProfile field_179964_d;
      private final ITextComponent field_179965_e;

      public AddPlayerData(GameProfile var2, int var3, @Nullable GameType var4, @Nullable ITextComponent var5) {
         this.field_179964_d = ☃;
         this.field_179966_b = ☃;
         this.field_179967_c = ☃;
         this.field_179965_e = ☃;
      }

      public GameProfile func_179962_a() {
         return this.field_179964_d;
      }

      public int func_179963_b() {
         return this.field_179966_b;
      }

      public GameType func_179960_c() {
         return this.field_179967_c;
      }

      @Nullable
      public ITextComponent func_179961_d() {
         return this.field_179965_e;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this)
            .add("latency", this.field_179966_b)
            .add("gameMode", this.field_179967_c)
            .add("profile", this.field_179964_d)
            .add("displayName", this.field_179965_e == null ? null : ITextComponent.Serializer.func_150696_a(this.field_179965_e))
            .toString();
      }
   }
}
