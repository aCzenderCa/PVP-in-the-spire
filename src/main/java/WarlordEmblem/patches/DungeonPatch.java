package WarlordEmblem.patches;

import UI.Chat.ChatFoldPage;
import WarlordEmblem.patches.CardShowPatch.EnergyUpdateSender;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;

//对于游戏地图相关的patch 这其实是为了聊天框的渲染和更新
public class DungeonPatch {

    @SpirePatch(clz = AbstractDungeon.class, method = "render")
    public static class DungeonRenderPatch
    {
        @SpirePostfixPatch
        public static void fix(AbstractDungeon __instance, SpriteBatch sb)
        {
            ChatFoldPage.getInstance().render(sb);
        }
    }

    //更新聊天页面的操作
    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class DungeonUpdatePatch
    {
        @SpirePostfixPatch
        public static void fix(AbstractDungeon __instance)
        {
            //更新聊天框
            ChatFoldPage.getInstance().update();
            //检查当前的能量信息
            EnergyUpdateSender.updatePlayerEnergy();
        }

    }

    //强行指定房间渲染位置的图标
    @SpirePatch(clz = AbstractDungeon.class, method = "setBoss")
    public static class BossLabelPatch
    {
        @SpirePostfixPatch
        public static void fix()
        {
            DungeonMap.boss = ImageMaster.loadImage("pvp/ui/map/pvp.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("pvp/ui/map/pvp_outline.png");
        }
    }

}
