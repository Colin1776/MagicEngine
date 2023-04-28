package github.colin1776.magicengine.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public final class SpellEffect
{
    private int damage;
    private int heal;
    private int casterDamage;
    private int casterHeal;
    private ArrayList<MobEffectInstance> effects = new ArrayList<>();
    private ArrayList<MobEffectInstance> casterEffects = new ArrayList<>();
    // TODO i need an enum for determining of caster effects should be applied on entity or block or both but idk what to call it :skull:

    public void applyEntityEffects(LivingEntity caster, LivingEntity... targets)
    {
        for (LivingEntity entity : targets)
        {
            entity.hurt(DamageSource.indirectMagic(caster, caster), damage);
            entity.heal(heal);

            for (MobEffectInstance e : effects)
                entity.addEffect(e);
        }

        caster.hurt(DamageSource.MAGIC, casterDamage);
        caster.heal(casterHeal);

        for (MobEffectInstance e : effects)
            caster.addEffect(e);
    }

    public void applyBlockEffects(LivingEntity caster, Direction direction, BlockPos pos)
    {
        // block stuff here lol

        caster.hurt(DamageSource.MAGIC, casterDamage);
        caster.heal(casterHeal);

        for (MobEffectInstance e : effects)
            caster.addEffect(e);
    }

    public SpellEffect setDamage(int amount)
    {
        damage = amount;
        return this;
    }

    public SpellEffect setCasterDamage(int amount)
    {
        casterDamage = amount;
        return this;
    }

    public SpellEffect setHeal(int amount)
    {
        heal = amount;
        return this;
    }

    public SpellEffect setCasterHeal(int amount)
    {
        casterHeal = amount;
        return this;
    }

    public SpellEffect addEffect(MobEffectInstance effect)
    {
        effects.add(effect);
        return this;
    }

    public SpellEffect addCasterEffect(MobEffectInstance effect)
    {
        casterEffects.add(effect);
        return this;
    }
}
