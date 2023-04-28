package github.colin1776.magicengine.spell;

import github.colin1776.magicengine.MagicEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class RaySpell extends Spell
{
    private final ParticleType particleType;

    public enum ParticleType {BEAM, SPIRAL, STRAIGHT}

    public RaySpell(String name, Lore lore, int castDuration, int cooldown, int range, boolean directTarget, ParticleType particleType, CastType castType, TargetType targetType, SpellCost cost, SpellEffect effect)
    {
        super(name, lore, castDuration, cooldown, range, directTarget, castType, targetType, cost, effect);
        this.particleType = particleType;
    }

    @Override
    public boolean canCast(LivingEntity caster, ItemStack castingItem)
    {
        if (isDirectTarget())
        {
            HitResult result = getLook(caster);

            if (!(result instanceof EntityHitResult e))
                return false;

            if (!(e.getEntity() instanceof LivingEntity le))
                return false;

            if (!validTarget(caster, le))
                return false;
        }

        if (!getCost().meetsCost(caster, castingItem))
            return false;

        return true;
    }

    @Override
    public void cast(LivingEntity caster, ItemStack castingItem)
    {
        HitResult result = getLook(caster);

        if (result instanceof EntityHitResult entityRes && entityRes.getEntity() instanceof LivingEntity e)
        {
            getEffect().applyEntityEffects(caster, e);
        }
        else if (result instanceof BlockHitResult blockRes)
        {
            getEffect().applyBlockEffects(caster, blockRes.getDirection(), blockRes.getBlockPos());
        }
        else
        {
            MagicEngine.LOGGER.debug("No hit result for this cast.");
        }

        // gonna test some particles now lol
        Vec3 eyePos = caster.getEyePosition();
        Vec3 targetPos = result.getLocation();

        double x = eyePos.x;
        double y = eyePos.y;
        double z = eyePos.z;

        double xo = targetPos.x - x;
        double yo = targetPos.y - y;
        double zo = targetPos.z - z;

        int iterations = 10;

        xo /= iterations;
        yo /= iterations;
        zo /= iterations;

        for (int i = 0; i < iterations; i++)
        {
            Level level = caster.getLevel();
            level.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0, 0);

            x += xo;
            y += yo;
            z += zo;
        }

        getCost().applyCost();
    }
}
