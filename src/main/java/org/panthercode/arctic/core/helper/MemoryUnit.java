/*
 * Copyright 2016 PantherCode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.panthercode.arctic.core.helper;

/**
 * Enumeration of units to handle memory capacity or the size of a binary object.
 *
 * @author PantherCode
 */
public enum MemoryUnit {
    /**
     * Bit
     */
    BIT("b") {
        public double toBit(double size) {
            return size;
        }

        public double toByte(double size) {
            return size / 8.0;
        }

        public double toKiloByte(double size) {
            return size / 8.0 / KB;
        }

        public double toMegaByte(double size) {
            return size / 8.0 / MB;
        }

        public double toGigaByte(double size) {
            return size / 8.0 / GB;
        }

        public double toTeraByte(double size) {
            return size / 8.0 / TB;
        }

        public double toPetaByte(double size) {
            return size / 8.0 / PB;
        }

        public double toExaByte(double size) {
            return size / 8.0 / EB;
        }

        public double toZettaByte(double size) {
            return size / 8.0 / ZB;
        }

        public double toYottaByte(double size) {
            return size / 8.0 / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toBit(size);
        }
    },

    /**
     * Byte
     */
    BYTE("B") {
        public double toBit(double size) {
            return size * 8.0;
        }

        public double toByte(double size) {
            return size;
        }

        public double toKiloByte(double size) {
            return size / KB;
        }

        public double toMegaByte(double size) {
            return size / MB;
        }

        public double toGigaByte(double size) {
            return size / GB;
        }

        public double toTeraByte(double size) {
            return size / TB;
        }

        public double toPetaByte(double size) {
            return size / PB;
        }

        public double toExaByte(double size) {
            return size / EB;
        }

        public double toZettaByte(double size) {
            return size / ZB;
        }

        public double toYottaByte(double size) {
            return size / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toByte(size);
        }
    },

    /**
     * Kilobyte
     */
    KILOBYTE("KB") {
        public double toBit(double size) {
            return size * KB * 8.0;
        }

        public double toByte(double size) {
            return size * KB;
        }

        public double toKiloByte(double size) {
            return size;
        }

        public double toMegaByte(double size) {
            return size * KB / MB;
        }

        public double toGigaByte(double size) {
            return size * KB / GB;
        }

        public double toTeraByte(double size) {
            return size * KB / TB;
        }

        public double toPetaByte(double size) {
            return size * KB / PB;
        }

        public double toExaByte(double size) {
            return size * KB / EB;
        }

        public double toZettaByte(double size) {
            return size * KB / ZB;
        }

        public double toYottaByte(double size) {
            return size * KB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toKiloByte(size);
        }
    },

    /**
     * Megabyte
     */
    MEGABYTE("MB") {
        public double toBit(double size) {
            return size * MB * 8.0;
        }

        public double toByte(double size) {
            return size * MB;
        }

        public double toKiloByte(double size) {
            return size * MB / KB;
        }

        public double toMegaByte(double size) {
            return size;
        }

        public double toGigaByte(double size) {
            return size * MB / GB;
        }

        public double toTeraByte(double size) {
            return size * MB / TB;
        }

        public double toPetaByte(double size) {
            return size * MB / PB;
        }

        public double toExaByte(double size) {
            return size * MB / EB;
        }

        public double toZettaByte(double size) {
            return size * MB / ZB;
        }

        public double toYottaByte(double size) {
            return size * MB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toMegaByte(size);
        }
    },

    /**
     * Gigabyte
     */
    GIGABYTE("GB") {
        public double toBit(double size) {
            return size * GB * 8.0;
        }

        public double toByte(double size) {
            return size * GB;
        }

        public double toKiloByte(double size) {
            return size * GB / KB;
        }

        public double toMegaByte(double size) {
            return size * GB / MB;
        }

        public double toGigaByte(double size) {
            return size;
        }

        public double toTeraByte(double size) {
            return size * GB / TB;
        }

        public double toPetaByte(double size) {
            return size * GB / PB;
        }

        public double toExaByte(double size) {
            return size * GB / EB;
        }

        public double toZettaByte(double size) {
            return size * GB / ZB;
        }

        public double toYottaByte(double size) {
            return size * GB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toGigaByte(size);
        }
    },

    /**
     * Terabyte
     */
    TERABYTE("TB") {
        public double toBit(double size) {
            return size * TB * 8.0;
        }

        public double toByte(double size) {
            return size * TB;
        }

        public double toKiloByte(double size) {
            return size * TB / KB;
        }

        public double toMegaByte(double size) {
            return size * TB / MB;
        }

        public double toGigaByte(double size) {
            return size * TB / GB;
        }

        public double toTeraByte(double size) {
            return size;
        }

        public double toPetaByte(double size) {
            return size * TB / PB;
        }

        public double toExaByte(double size) {
            return size * TB / EB;
        }

        public double toZettaByte(double size) {
            return size * TB / ZB;
        }

        public double toYottaByte(double size) {
            return size * TB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toTeraByte(size);
        }
    },

    /**
     * Petabyte
     */
    PETABYTE("PT") {
        public double toBit(double size) {
            return size * PB * 8.0;
        }

        public double toByte(double size) {
            return size * PB;
        }

        public double toKiloByte(double size) {
            return size * PB / KB;
        }

        public double toMegaByte(double size) {
            return size * PB / MB;
        }

        public double toGigaByte(double size) {
            return size * PB / GB;
        }

        public double toTeraByte(double size) {
            return size * PB / TB;
        }

        public double toPetaByte(double size) {
            return size;
        }

        public double toExaByte(double size) {
            return size * PB / EB;
        }

        public double toZettaByte(double size) {
            return size * PB / ZB;
        }

        public double toYottaByte(double size) {
            return size * PB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toPetaByte(size);
        }
    },

    /**
     * Exabyte
     */
    EXABYTE("EB") {
        public double toBit(double size) {
            return size * EB * 8.0;
        }

        public double toByte(double size) {
            return size * EB;
        }

        public double toKiloByte(double size) {
            return size * EB / KB;
        }

        public double toMegaByte(double size) {
            return size * EB / MB;
        }

        public double toGigaByte(double size) {
            return size * EB / GB;
        }

        public double toTeraByte(double size) {
            return size * EB / TB;
        }

        public double toPetaByte(double size) {
            return size * EB / PB;
        }

        public double toExaByte(double size) {
            return size;
        }

        public double toZettaByte(double size) {
            return size * EB / ZB;
        }

        public double toYottaByte(double size) {
            return size * EB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toExaByte(size);
        }
    },

    /**
     * Zetabyte
     */
    ZETTABYTE("ZB") {
        public double toBit(double size) {
            return size * ZB * 8.0;
        }

        public double toByte(double size) {
            return size * ZB;
        }

        public double toKiloByte(double size) {
            return size * ZB / KB;
        }

        public double toMegaByte(double size) {
            return size * ZB / MB;
        }

        public double toGigaByte(double size) {
            return size * ZB / GB;
        }

        public double toTeraByte(double size) {
            return size * ZB / TB;
        }

        public double toPetaByte(double size) {
            return size * ZB / PB;
        }

        public double toExaByte(double size) {
            return size * ZB / EB;
        }

        public double toZettaByte(double size) {
            return size;
        }

        public double toYottaByte(double size) {
            return size * ZB / YB;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toZettaByte(size);
        }
    },

    /**
     * Yottabyte
     */
    YOTTABYTE("YB") {
        public double toBit(double size) {
            return size * YB * 8.0;
        }

        public double toByte(double size) {
            return size * YB;
        }

        public double toKiloByte(double size) {
            return size * YB / KB;
        }

        public double toMegaByte(double size) {
            return size * YB / MB;
        }

        public double toGigaByte(double size) {
            return size * YB / GB;
        }

        public double toTeraByte(double size) {
            return size * YB / TB;
        }

        public double toPetaByte(double size) {
            return size * YB / PB;
        }

        public double toExaByte(double size) {
            return size * YB / EB;
        }

        public double toZettaByte(double size) {
            return size * YB / ZB;
        }

        public double toYottaByte(double size) {
            return size;
        }

        public double convert(double size, MemoryUnit unit) {
            return unit.toYottaByte(size);
        }
    };

    /**
     * const size of Kilobyte
     */
    private static final double KB = 1000.0;

    /**
     * const size of Megabyte
     */
    private static final double MB = 1000.0 * KB;

    /**
     * const size of Gigabyte
     */
    private static final double GB = 1000.0 * MB;

    /**
     * const size of Terabyte
     */
    private static final double TB = 1000.0 * GB;

    /**
     * const size of Petabyte
     */
    private static final double PB = 1000.0 * TB;

    /**
     * const size of Exabyte
     */
    private static final double EB = 1000.0 * PB;

    /**
     * const size of Zettabyte
     */
    private static final double ZB = 1000.0 * EB;

    /**
     * const size of Yottabyte
     */
    private static final double YB = 1000.0 * ZB;

    /**
     * Convert value to bit.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toBit(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to byte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Kilobyte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toKiloByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Megabyte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toMegaByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Gigabyte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toGigaByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Terabyte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toTeraByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Petabyte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toPetaByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Exayte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toExaByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Zettabyte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toZettaByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Convert value to Yottayte.
     *
     * @param size value to convert
     * @return Returns the converted value.
     */
    public double toYottaByte(double size) {
        throw new AbstractMethodError();
    }

    /**
     * Converts the value to another unit.
     *
     * @param size value to convert
     * @param unit unit the value should convert to
     * @return Returns the converted value.
     */
    public double convert(double size, MemoryUnit unit) {
        throw new AbstractMethodError();
    }

    /**
     * Convert a given value to the most fitting unit.
     *
     * @param size given value
     * @param unit memory unit of value
     * @return Returns the most fitting unit to given value.
     */
    public static MemoryUnit valueOf(double size, MemoryUnit unit) {

        if (MemoryUnit.KILOBYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.BYTE;
        }

        if (MemoryUnit.MEGABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.KILOBYTE;
        }

        if (MemoryUnit.GIGABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.MEGABYTE;
        }

        if (MemoryUnit.TERABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.GIGABYTE;
        }

        if (MemoryUnit.PETABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.TERABYTE;
        }

        if (MemoryUnit.EXABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.PETABYTE;
        }

        if (MemoryUnit.ZETTABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.EXABYTE;
        }

        if (MemoryUnit.YOTTABYTE.convert(size, unit) < 1.0) {
            return MemoryUnit.EXABYTE;
        }

        return MemoryUnit.YOTTABYTE;
    }

    /**
     * actual value
     */
    private final String unit;

    /**
     * Constructor
     *
     * @param unit value of unit
     */
    MemoryUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns the actual value.
     *
     * @return Returns the actual value.
     */
    public String unit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return this.unit;
    }
}
