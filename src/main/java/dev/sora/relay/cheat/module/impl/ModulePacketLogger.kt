package dev.sora.relay.cheat.module.impl
import com.nukkitx.protocol.bedrock.packet.*
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound

class ModulePacketLogger : CheatModule("PacketLogger") {
    private val modeValue = listValue("Mode", arrayOf("In","Out", "Both"), "Both")
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if(modeValue.get()=="In" || modeValue.get()=="Both") {
            val packet = event.packet
            if (packet is AvailableCommandsPacket) return
            if (packet is MobEquipmentPacket) {
                session.thePlayer.currentItem = packet.item
            }
            if (packet is UpdateBlockPacket) {
                return
            }
            chat(event.packet.toString())
        }
    }

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if(modeValue.get()=="Out") {
            val packet = event.packet
            if (packet is InventoryTransactionPacket) {
                chat(event.packet.toString())
            }
            if (packet is PlayerActionPacket) {
                chat(event.packet.toString())
            }
        }
        if(modeValue.get()=="Both"){
            val packet = event.packet
            if(packet is MovePlayerPacket) return
            chat(event.packet.toString())
        }
    }
}