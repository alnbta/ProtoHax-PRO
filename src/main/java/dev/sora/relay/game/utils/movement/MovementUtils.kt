package dev.sora.relay.game.utils.movement

import com.nukkitx.math.vector.Vector3f
import dev.sora.relay.cheat.BasicThing
import dev.sora.relay.game.GameSession
import dev.sora.relay.game.utils.math.MathHelper
import kotlin.math.cos
import kotlin.math.sin

object MovementUtils {
    @JvmStatic
    fun calculateLookAtMovement(vec: Vector3f, mc: GameSession): Vector3f {
        val yaw = MathHelper.wrapDegrees(
            MathHelper.floor_double(
                Math.toDegrees(
                    Math.atan2(
                        vec.x - mc.thePlayer.posX,
                        vec.z - mc.thePlayer.posZ
                    )
                )
            ) - 90.0f
        )
        val pitch = MathHelper.wrapDegrees(
            -MathHelper.floor_double(
                Math.toDegrees(
                    Math.atan2(
                        vec.y - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight().toDouble()),
                        Math.sqrt((vec.x - mc.thePlayer.posX) * (vec.x - mc.thePlayer.posX) + (vec.z - mc.thePlayer.posZ) * (vec.z - mc.thePlayer.posZ))
                    )
                )
            )
        )
        return Vector3f.from(
            ((-sin(Math.toRadians(yaw.toDouble())) * cos(Math.toRadians(pitch))).toFloat()),
            ((-sin(Math.toRadians(pitch))).toFloat()),
            ((cos(Math.toRadians(yaw.toDouble())) * cos(Math.toRadians(pitch))).toFloat())
        )
    }
    fun isMoving(mc:GameSession): Boolean {
        return mc.thePlayer.moveForward != 0f || mc.thePlayer.moveStrafing != 0f
    }
}