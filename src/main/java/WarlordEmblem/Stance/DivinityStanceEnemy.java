package WarlordEmblem.Stance;

import WarlordEmblem.character.ControlMoster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

//手动的神格姿态
public class DivinityStanceEnemy extends DivinityStance {

    public ControlMoster monster;

    public DivinityStanceEnemy(ControlMoster monster)
    {
        super();
        this.monster = monster;
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.2F;
                AbstractDungeon.effectsQueue.add(new DivinityParticleEffectEnemy(monster));
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffectEnemy("Divinity",monster));
        }

    }

}
