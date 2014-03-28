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
 * Keeps track of the indices to use when writing data to payload arrays.
 * Internal use only.
 */
public class IndexInPayload {

    /**
     * Instantiates a payload of information to be sent in a packet
     * 
     * @param intIndex
     *            how many Integer values are within the payload
     * @param floatIndex
     *            how many Float values are within the payload
     * @param stringIndex
     *            how many String values are within the payload
     * @param boolIndex
     *            how many Boolean values are within the payload
     */
    public IndexInPayload(int intIndex, int floatIndex, int stringIndex, int boolIndex) {
        this.intIndex = intIndex;
        this.floatIndex = floatIndex;
        this.stringIndex = stringIndex;
        this.boolIndex = boolIndex;
    }

    /**
     * Instantiates a payload of information to be sent in a packet
     * 
     * @param intIndex
     *            how many Integer values are within the payload
     * @param floatIndex
     *            how many Float values are within the payload
     * @param stringIndex
     *            how many String values are within the payload
     * @param boolIndex
     *            how many Boolean values are within the payload
     * @param doubleIndex
     *            how many Double values are within the payload
     */
    public IndexInPayload(int intIndex, int floatIndex, int stringIndex, int boolIndex, int doubleIndex) {
        this(intIndex, floatIndex, stringIndex, boolIndex);
        this.doubleIndex = doubleIndex;
    }

    /**
     * Default values for payload
     */
    public int intIndex    = 0;
    public int floatIndex  = 0;
    public int stringIndex = 0;
    public int boolIndex   = 0;
    public int doubleIndex = 0;
}
