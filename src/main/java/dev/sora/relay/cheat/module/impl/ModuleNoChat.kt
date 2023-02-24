package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.TextPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.BoolValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.entity.EntityPlayer
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.utils.RandomUtils

class ModuleNoChat : CheatModule("NoChat") {

    private val modeValue = listValue("Mode", arrayOf("All","JustPlayer"), "JustPlayer")
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (packet is TextPacket) {
            if(modeValue.get()=="All") event.cancel() else{
                for (entity in mc.theWorld.entityMap.values) {
                    if (entity is EntityPlayer) {
                        if (packet.message.contains(entity.username)) {
                            event.cancel()
                        }
                    }
                }
            }
        }
    }
}