package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.IntValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.utils.TimerUtil

class ModuleAntiCrasher : CheatModule("AntiCrasher") {
    private var packetCount = 0
    private val timer = TimerUtil()

    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if (event.packet is AnimatePacket) {
            if (event.packet.action == AnimatePacket.Action.CRITICAL_HIT) {
                packetCount++
            }
        }
        if (timer.delay(200F)) {
            packetCount = 0
            timer.reset()
        }
        if (packetCount >= 10) {
            event.cancel()
        }
    }
}