# Specentre

Plugin for Minecraft JE servers that fixes [MC-148993](https://bugs.mojang.com/browse/MC-148993) by sending a [Teleport Entity](https://wiki.vg/Protocol#Teleport_Entity) packet to players who:
- moves between chunks (this will cause the server to send [Set Center Chunk](https://wiki.vg/Protocol#Set_Center_Chunk) packet) and
- spectates another entity, i.e. can't control camera view

### Notes
- Requires https://www.spigotmc.org/resources/protocollib.1997/  
- Tested only on [Paper](https://github.com/PaperMC/Paper) 1.18.2, but I think that it should work on 1.19+ too.
