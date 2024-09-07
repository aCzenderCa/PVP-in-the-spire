package WarlordEmblem.Events;

import UI.Chat.ChatFoldPage;
import UI.Text.AdvTextManager;
import WarlordEmblem.GlobalManager;
import WarlordEmblem.PVPApi.BaseEvent;
import WarlordEmblem.network.PlayerInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//接收和发送聊天消息的事件
public class ChatMessageEvent extends BaseEvent {

    //即将被发送的text
    AdvTextManager sendingMessage = null;

    public ChatMessageEvent(AdvTextManager textManager)
    {
        eventId = "ChatMessageEvent";
        sendingMessage = textManager;
    }

    @Override
    public void encode(DataOutputStream streamHandle) {
        //发送这个消息的完整内容
        try
        {
            //发送本地玩家的tag
            GlobalManager.playerManager.encodePlayer(streamHandle);
            streamHandle.writeUTF(this.sendingMessage.getTotalString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void decode(DataInputStream streamHandle) {
        try
        {
            //解码玩家的信息
            PlayerInfo info = GlobalManager.playerManager.decodePlayerInfo(streamHandle);
            if(info == null)
                return;
            String message = streamHandle.readUTF();
            //把消息送进chat box
            ChatFoldPage.getInstance().receiveMessage(message,info.getName());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
