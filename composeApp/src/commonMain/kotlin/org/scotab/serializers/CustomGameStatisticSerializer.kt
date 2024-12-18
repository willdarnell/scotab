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
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.scotab.models.GameStatistics

object CustomGameStatisticsSerializer : KSerializer<GameStatistics> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GameStatistics") {
        element<Map<String, String>>("team")
        element<String>("statistics")
    }

    override fun serialize(encoder: Encoder, value: GameStatistics) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, MapSerializer(String.serializer(), String.serializer()), value.team)
            encodeStringElement(descriptor, 1, Json.encodeToString(MapSerializer(String.serializer(), String.serializer().nullable), value.statistics))
        }
    }

    override fun deserialize(decoder: Decoder): GameStatistics {
        return decoder.decodeStructure(descriptor) {
            var team: Map<String, String> = emptyMap()
            var statistics: Map<String, String?> = emptyMap()

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> team = decodeSerializableElement(descriptor, 0, MapSerializer(String.serializer(), String.serializer()))
                    1 -> {
                        val statisticsString = decodeStringElement(descriptor, 1)
                        statistics = Json.decodeFromString(JsonObject.serializer(), statisticsString).mapValues { it.value.jsonPrimitive.contentOrNull }
                    }
                    else -> break
                }
            }
            GameStatistics(team, statistics)
        }
    }
}