package WarlordEmblem.Events;

import WarlordEmblem.GlobalManager;
import WarlordEmblem.PVPApi.BaseEvent;
import WarlordEmblem.character.PlayerMonster;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//增加球位的操作
public class IncreaseOrbSlotEvent extends BaseEvent {

    int slotNum;

    public IncreaseOrbSlotEvent(int slotNum)
    {
        this.slotNum = slotNum;
        this.eventId = "IncreaseOrbSlotEvent";
    }

    @Override
    public void encode(DataOutputStream streamHandle) {
        try
        {
            GlobalManager.playerManager.encodePlayer(streamHandle);
            streamHandle.writeInt(this.slotNum);
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
            PlayerMonster playerMonster = GlobalManager.playerManager.decodePlayer(streamHandle);
            if(playerMonster == null)
                return;
            int slotNum = streamHandle.readInt();
            if(slotNum == -1)
                playerMonster.decreaseOrbSlot();
            else
                playerMonster.increaseOrbSlot(slotNum);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
