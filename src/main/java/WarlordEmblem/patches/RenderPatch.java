package WarlordEmblem.patches;

import UI.BattleUI.MonsterPotionPanel;
import UI.DelayBox;
import WarlordEmblem.patches.connection.MeunScreenFadeout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.io.DataInputStream;

//大部分截取render行为的patch都会被放在这里，方便做一些显示和渲染操作
public class RenderPatch {

    //延迟信息框
    public static DelayBox delayBox=null;

    //敌方的药水列表 在这里渲染是为了放在tip栏上面
    public static MonsterPotionPanel potionPanel = null;

    public static void receiveDelayInfo(DataInputStream streamHandle)
    {
        if(delayBox!=null)
        {
            delayBox.receiveDelayInfo(streamHandle);
        }
    }

    //延迟信息框的展示
    @SpirePatch(clz = TipHelper.class, method = "render")
    public static class DelayBoxRenderPath
    {
        @SpirePostfixPatch
        public static void fix(SpriteBatch sb)
        {
            if(potionPanel != null)
            {
                potionPanel.tipRender(sb);
                potionPanel = null;
            }
        }
    }

    //强行关闭reward界面
    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class ForceCloseRewardScreen
    {

        public static boolean forceCloseOnce = false;

        @SpirePostfixPatch
        public static void fix(AbstractDungeon __instance)
        {
            //判断目前的窗口是不是奖励窗口
            if(forceCloseOnce && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD)
            {
                forceCloseOnce = false;
                AbstractDungeon.closeCurrentScreen();
            }
        }
    }
}
