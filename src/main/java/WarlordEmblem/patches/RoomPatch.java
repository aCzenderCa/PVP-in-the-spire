package WarlordEmblem.patches;

import WarlordEmblem.GlobalManager;
import WarlordEmblem.SocketServer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.TinyHouse;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

//和战斗地图转移相关的patch会放在这里
public class RoomPatch {

    //从第一局的战斗结束后，询问进入的下一个刻意，返回的永远是开端
    @SpirePatch(clz = TreasureRoomBoss.class,method = "getNextDungeonName")
    public static class NextDungeonPatch
    {
        //截取这个函数，返回的永远是开局
        @SpirePrefixPatch
        public static SpireReturn<String> fix(TreasureRoomBoss __instance)
        {
            //固定返回开局，不需要设定
            return SpireReturn.Return("Exordium");
        }
    }

    //boss房间的奖励patch,如果刚刚经过了失败，这里就没办法获得奖励
    @SpirePatch(clz = AbstractDungeon.class,method = "returnRandomRelic")
    public static class RemoveBossRelicForLoser
    {

        //当它为true的时候，所有逻辑都不生效
        public static boolean isDisable = false;

        //当需要获得遗物的时候，尤其是需要获得boss遗物的时候，阻止它获得
        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic> fix(AbstractRelic.RelicTier tier)
        {
            if(isDisable || GlobalManager.loserRewardFlag != 0)
            {
                return SpireReturn.Continue();
            }
            //如果返回的是boss遗物的时候，需要做特殊处理
            if(tier.equals(AbstractRelic.RelicTier.BOSS) && !SocketServer.isJustWin())
            {
                //固定返回小房子
                return SpireReturn.Return(new TinyHouse());
            }
            return SpireReturn.Continue();
        }
    }

}
