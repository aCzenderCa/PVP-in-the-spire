package WarlordEmblem.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

//虚假的仪式
public class FakeRitual extends RitualPower {

    public FakeRitual(AbstractCreature owner, int strAmt) {
        super(owner,strAmt,owner.isPlayer);
    }

    //加力量相关的事件由施加力量的那边结算
    public void atEndOfTurn(boolean isPlayer) {

    }

    public void atEndOfRound() {

    }

}
