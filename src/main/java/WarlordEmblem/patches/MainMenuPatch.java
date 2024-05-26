package WarlordEmblem.patches;

import UI.Lobby.LobbyConfig;
import WarlordEmblem.GlobalManager;
import WarlordEmblem.helpers.FieldHelper;
import WarlordEmblem.network.Lobby.LobbyManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen;

import java.util.ArrayList;

//用于添加主菜单按钮的patch，为了给后面弄多人联机大厅做铺垫
public class MainMenuPatch {

    public static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("MenuScreen");

    @SpirePatch(clz = MainMenuScreen.class, method = "setMainMenuButtons")
    public static class AddLobbyButton
    {
        //设置按钮后，打印找到的按钮
        @SpirePostfixPatch
        public static void fix(MainMenuScreen __instance)
        {
            //添加一个游戏大厅的选项
            ArrayList<MenuButton> buttonList = __instance.buttons;
            //游戏大厅对应的按钮
            MenuButton lobbyButton = new MenuButton(Enums.GAME_LOBBY,buttonList.size());
            //把它添加到按钮列表中
            buttonList.add(lobbyButton);
        }
    }

    //截流设置按钮内容的事件，写上游戏大厅里的游戏内容
    @SpirePatch(clz = MenuButton.class, method = "setLabel")
    public static class SetLobbyPatch
    {
        @SpirePostfixPatch
        public static void fix(MenuButton __instance)
        {
            //判断是不是游戏大厅的选项
            if(__instance.result == Enums.GAME_LOBBY)
            {
                FieldHelper.setPrivateFieldValue(__instance,"label",
                uiStrings.TEXT[0]);
            }
            else if(__instance.result == MenuButton.ClickResult.PLAY)
            {
                FieldHelper.setPrivateFieldValue(__instance,"label",
                        uiStrings.TEXT[1]);
            }
            else if(__instance.result == MenuButton.ClickResult.RESUME_GAME)
            {
                //把它改成play的常规操作
                __instance.result = MenuButton.ClickResult.PLAY;
                FieldHelper.setPrivateFieldValue(__instance,"label",
                        uiStrings.TEXT[1]);
            }
        }
    }

    @SpirePatch(clz = MenuButton.class, method = "buttonEffect")
    //点击下按钮时的响应事件
    public static class ButtonClickPatch
    {
        @SpirePostfixPatch
        public static void fix(MenuButton __instance)
        {
            //判断按钮是不是游戏大厅
            if(__instance.result == Enums.GAME_LOBBY)
            {
                //执行正常打开游戏窗口的逻辑
                CardCrawlGame.mainMenuScreen.panelScreen.open(MenuPanelScreen.PanelScreen.PLAY);
                //指定渲染模式
                PanelScreenPatch.lobbyFlag = true;
                //初始化全局参数
                GlobalManager.initGlobal();
                //临时处理lobby的配置页面
                LobbyConfig.instance = new LobbyConfig();
                //初始化与房间相关的steam信息
                LobbyManager.initManager();
            }
        }
    }

    //游戏大厅的枚举类型
    public static class Enums
    {
        @SpireEnum
        public static MenuButton.ClickResult GAME_LOBBY;
    }

}
