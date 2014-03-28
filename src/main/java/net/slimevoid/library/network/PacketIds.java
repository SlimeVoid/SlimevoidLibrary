/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.library.network;

/**
 * Packet ID list
 * 
 * LOGIN Packet ID for Login information UPDATE Packet ID for Basic Update
 * information GUI Packet ID for GUI update/open TILE Packet ID for TileEntity
 * information ENTITY Packet ID for Entity information
 * 
 * @author Eurymachus
 * 
 */
public class PacketIds {
    // Packet ID for Login information
    public static final int LOGIN  = 0;
    // Packet ID for Basic Update information
    public static final int UPDATE = 1;
    // Packet ID for GUI update/open
    public static final int GUI    = 2;
    // Packet ID for TileEntity information
    public static final int TILE   = 3;
    // Packet ID for Entity information
    public static final int ENTITY = 4;
    // Packet ID for Player notification e.g. Client side messages
    public static final int PLAYER = 5;
}
