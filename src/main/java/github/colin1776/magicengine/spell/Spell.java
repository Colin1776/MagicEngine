package github.colin1776.magicengine.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * TODO description for class
 */

public abstract class Spell
{
    // TODO change this to be a single class, no inheritance, everything is done here, create a class for the particle effects, and then a class for spell result
    // TODO both can be extended so that people using this lib can make their own particle effects and spell results, come up with better names for those btw

    private final String name;
    private final Lore lore;
    private final int castDuration;
    private final int cooldown;
    private final int range;
    private final boolean directTarget;
    private final CastType castType;
    private final TargetType targetType;
    private final SpellCost cost;
    private final SpellEffect effect;

    public enum CastType {CHARGED, CONTINUOUS}
    public enum TargetType {ALLY, ALLY_NEUTRAL, NEUTRAL_ENEMY, ENEMY, ALL}

    // TODO description for constructor
    public Spell(String name, Lore lore, int castDuration, int cooldown, int range, boolean directTarget, CastType castType, TargetType targetType, SpellCost cost, SpellEffect effect)
    {
        this.name = name;
        this.lore = lore;
        this.castDuration = castDuration;
        this.cooldown = cooldown;
        this.range = range;
        this.directTarget = directTarget;
        this.castType = castType;
        this.targetType = targetType;
        this.cost = cost;
        this.effect = effect;
    }

    // TODO give these methods proper parameters pls
    public abstract boolean canCast(LivingEntity caster, ItemStack castingItem);

    public abstract void cast(LivingEntity caster, ItemStack castingItem);

    // TODO once ally system is implemented this method can be completed
    public boolean validTarget(LivingEntity caster, LivingEntity target)
    {
        return true;
    }

    public HitResult getLook(LivingEntity caster)
    {
        double range = getRange();
        HitResult res = caster.pick(range, 0.0f, false);

        Vec3 eyePos = caster.getEyePosition();
        double distSquared = res.getLocation().distanceToSqr(eyePos);

        Vec3 lookVec = caster.getViewVector(1.0f);
        Vec3 castVec = eyePos.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);

        AABB aabb = caster.getBoundingBox().expandTowards(lookVec.scale(range)).inflate(1.0D);
        EntityHitResult eres = ProjectileUtil.getEntityHitResult(caster, eyePos, castVec, aabb, (yeah) -> !yeah.isSpectator() && yeah.isPickable(), distSquared);

        if (eres != null)
            res = eres;

        return res;
    }

    // TODO finish getters pls
    // getters
    public String getName()
    {
        return name;
    }

    public Lore getLore()
    {
        return lore;
    }

    public int getCastDuration()
    {
        return castDuration;
    }

    public int getCooldown()
    {
        return cooldown;
    }

    public int getRange()
    {
        return range;
    }

    public boolean isDirectTarget()
    {
        return directTarget;
    }

    public CastType getCastType()
    {
        return castType;
    }

    public TargetType getTargetType()
    {
        return targetType;
    }

    public SpellCost getCost()
    {
        return cost;
    }

    public SpellEffect getEffect()
    {
        return effect;
    }
}
