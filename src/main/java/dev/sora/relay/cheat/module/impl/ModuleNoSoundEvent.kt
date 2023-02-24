package dev.sora.relay.cheat.module.impl

import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent1Packet
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent2Packet
import com.nukkitx.protocol.bedrock.packet.LevelSoundEventPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen


class ModuleNoSoundEvent : CheatModule("NoSoundEvent") {
    private val soundeventvalue = listValue("Packet", arrayOf("SoundEvent", "SoundEvent1", "SoundEvent2","ALL"), "SoundEvent")
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if(soundeventvalue.get()=="SoundEvent") {
            if (event.packet is LevelSoundEventPacket) {
                event.cancel()
            }
        }
        if(soundeventvalue.get()=="SoundEvent1"){
            if (event.packet is LevelSoundEvent1Packet) {
                event.cancel()
            }
        }
        if(soundeventvalue.get()=="SoundEvent2"){
            if (event.packet is LevelSoundEvent2Packet) {
                event.cancel()
            }
        }
        if (soundeventvalue.get()=="ALL") {

            if (event.packet is LevelSoundEventPacket) {
                event.cancel()
            }
            if (event.packet is LevelSoundEvent1Packet) {
                event.cancel()
            }
            if (event.packet is LevelSoundEvent2Packet) {
                event.cancel()
            }
        }
    }
}