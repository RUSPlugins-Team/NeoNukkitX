package rusplugins.neonukkitx.level.format.generic.serializer;

import rusplugins.neonukkitx.level.DimensionData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NetworkChunkData {

    private int chunkSections;
    private final DimensionData dimensionData;
}
