package me.rina.racc.client.mixins;

public interface RevenantIPlayerControllerMP
{
    void setBlockHitDelay(int delay);

    void setIsHittingBlock(boolean hittingBlock);

    float getCurBlockDamageMP();

    void setCurBlockDamageMP(float curBlockDamageMP);
}
