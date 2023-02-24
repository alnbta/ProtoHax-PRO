package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.entity.EntityPlayerSP
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.constants.Effect

class ModuleGodmode : CheatModule("GodMode") {
    private val modeValue = listValue("Mode", arrayOf("HYT"), "HYT")
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        val packet = event.packet
        if(modeValue.toString()=="HYT") {
            if (packet is PlayerAuthInputPacket) {
                session.thePlayer.attackEntity(mc.thePlayer, EntityPlayerSP.SwingMode.NONE, false)
            }
        }
    }
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (packet is AnimatePacket) {
            if (packet.action == AnimatePacket.Action.CRITICAL_HIT && packet.runtimeEntityId==mc.thePlayer.entityId) {
                if(mc.thePlayer.tickExists%5==0L){
                    event.cancel()
                }
            }
        }
    }
}