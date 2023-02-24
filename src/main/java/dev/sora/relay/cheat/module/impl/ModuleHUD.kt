package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.SetTitlePacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.event.Listen

class ModuleHUD : CheatModule("HUD") {

    @Listen
    fun ontick(event: EventTick) {
        event.session.netSession.inboundPacket(SetTitlePacket().apply {
            type = SetTitlePacket.Type.ACTIONBAR
            text = "${eps.username} | ${eps.posX},${eps.posY},${eps.posZ}"
            xuid = eps.xuid.toString()
        })
    }

}