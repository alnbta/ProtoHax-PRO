package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.MobEffectPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.IntValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.utils.constants.Effect

class ModuleHighJump : CheatModule("HighJump") {
    private val amplifierValue = intValue("levels", 5, 1, 10)

    @Listen
    fun onTick(event: EventTick) {
        if (event.session.thePlayer.tickExists % 20 != 0L) return
        event.session.netSession.inboundPacket(MobEffectPacket().apply {
            setEvent(MobEffectPacket.Event.ADD)
            runtimeEntityId = event.session.thePlayer.entityId
            effectId = Effect.JUMP_BOOST
            amplifier = amplifierValue.get() - 1
            isParticles = false
            duration = 21 * 20
        })
    }

    override fun onDisable() {
        if (!session.netSessionInitialized) return
        session.netSession.inboundPacket(MobEffectPacket().apply {
            runtimeEntityId = session.thePlayer.entityId
            event = MobEffectPacket.Event.REMOVE
            effectId = Effect.JUMP_BOOST
        })
    }
}