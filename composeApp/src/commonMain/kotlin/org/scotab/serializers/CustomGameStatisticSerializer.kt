import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.scotab.models.GameStatistics

object CustomGameStatisticsSerializer : KSerializer<GameStatistics> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GameStatistics") {
        element<Int>("id")
        element<String>("name")
        element<String>("nickname")
        element<String>("code")
        element<String>("logo")
        element<Map<String, String?>>("statistics")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: GameStatistics) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.id)
            encodeStringElement(descriptor, 1, value.name)
            encodeStringElement(descriptor, 2, value.nickname)
            encodeStringElement(descriptor, 3, value.code)
            encodeStringElement(descriptor, 4, value.logo)
            encodeSerializableElement(
                descriptor,
                5,
                MapSerializer(String.serializer(), String.serializer().nullable),
                value.statistics
            )
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): GameStatistics {
        return decoder.decodeStructure(descriptor) {
            var id = 0
            var name = ""
            var nickname = ""
            var code = ""
            var logo = ""
            var statistics: Map<String, String?> = emptyMap()

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> id = decodeIntElement(descriptor, 0)
                    1 -> name = decodeStringElement(descriptor, 1)
                    2 -> nickname = decodeStringElement(descriptor, 2)
                    3 -> code = decodeStringElement(descriptor, 3)
                    4 -> logo = decodeStringElement(descriptor, 4)
                    5 -> {
                        val statisticsString = decodeStringElement(descriptor, 5)
                        statistics = Json.parseToJsonElement(statisticsString).jsonObject.mapValues { it.value.toString() }
                    }
                    else -> break@loop
                }
            }

            GameStatistics(id, name, nickname, code, logo, statistics)
        }
    }
}