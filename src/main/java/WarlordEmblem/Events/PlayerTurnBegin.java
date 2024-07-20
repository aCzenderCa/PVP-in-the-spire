package WarlordEmblem.Events;

import WarlordEmblem.GlobalManager;
import WarlordEmblem.PVPApi.BaseEvent;
import WarlordEmblem.character.PlayerMonster;

import java.io.DataInputStream;
import java.io.DataOutputStream;

//玩家回合开始时的信息
public class PlayerTurnBegin extends BaseEvent {

    public PlayerTurnBegin()
    {
        this.eventId = "PlayerTurnBegin";
    }

    @Override
    public void encode(DataOutputStream streamHandle) {
        GlobalManager.playerManager.encodePlayer(streamHandle);
    }

    @Override
    public void decode(DataInputStream streamHandle) {
        //获取对应的monster
        PlayerMonster playerMonster = GlobalManager.playerManager.decodePlayer(streamHandle);
        //非战斗状态下不用管
        if(playerMonster == null)
            return;
        playerMonster.applyStartOfTurnPowers();
        //强制失去格挡
        playerMonster.forceLoseBlock();
    }
}
