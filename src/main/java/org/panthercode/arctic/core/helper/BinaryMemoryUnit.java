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
 * @author PantherCode
 */
public enum BinaryMemoryUnit {
    BIT("b") {
        public double toBit(double size) {
            return size;
        }

        public double toByte(double size) {
            return size / 8.0;
        }

        public double toKibiByte(double size) {
            return size / 8.0 / KiB;
        }

        public double toMebiByte(double size) {
            return size / 8.0 / MiB;
        }

        public double toGibiByte(double size) {
            return size / 8.0 / GiB;
        }

        public double toTebiByte(double size) {
            return size / 8.0 / TiB;
        }

        public double toPebiByte(double size) {
            return size / 8.0 / PiB;
        }

        public double toExbiByte(double size) {
            return size / 8.0 / EiB;
        }

        public double toZebiByte(double size) {
            return size / 8.0 / ZiB;
        }

        public double toYobiByte(double size) {
            return size / 8.0 / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toBit(size);
        }
    },

    BYTE("B") {
        public double toBit(double size) {
            return size * 8.0;
        }

        public double toByte(double size) {
            return size;
        }

        public double toKibiByte(double size) {
            return size / KiB;
        }

        public double toMebiByte(double size) {
            return size / MiB;
        }

        public double toGibiByte(double size) {
            return size / GiB;
        }

        public double toTebiByte(double size) {
            return size / TiB;
        }

        public double toPebiByte(double size) {
            return size / PiB;
        }

        public double toExbiByte(double size) {
            return size / EiB;
        }

        public double toZebiByte(double size) {
            return size / ZiB;
        }

        public double toYobiByte(double size) {
            return size / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toByte(size);
        }
    },

    KIBIBYTE("KiB") {
        public double toBit(double size) {
            return size * KiB * 8.0;
        }

        public double toByte(double size) {
            return size * KiB;
        }

        public double toKibiByte(double size) {
            return size;
        }

        public double toMebiByte(double size) {
            return size * KiB / MiB;
        }

        public double toGibiByte(double size) {
            return size * KiB / GiB;
        }

        public double toTebiByte(double size) {
            return size * KiB / TiB;
        }

        public double toPebiByte(double size) {
            return size * KiB / PiB;
        }

        public double toExbiByte(double size) {
            return size * KiB / EiB;
        }

        public double toZebiByte(double size) {
            return size * KiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size * KiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toKibiByte(size);
        }
    },

    MEBIBYTE("MiB") {
        public double toBit(double size) {
            return size * MiB * 8.0;
        }

        public double toByte(double size) {
            return size * MiB;
        }

        public double toKibiByte(double size) {
            return size * MiB / KiB;
        }

        public double toMebiByte(double size) {
            return size;
        }

        public double toGibiByte(double size) {
            return size * MiB / GiB;
        }

        public double toTebiByte(double size) {
            return size * MiB / TiB;
        }

        public double toPebiByte(double size) {
            return size * MiB / PiB;
        }

        public double toExbiByte(double size) {
            return size * MiB / EiB;
        }

        public double toZebiByte(double size) {
            return size * MiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size * MiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toMebiByte(size);
        }
    },

    GIBIBYTE("GiB") {
        public double toBit(double size) {
            return size * GiB * 8.0;
        }

        public double toByte(double size) {
            return size * GiB;
        }

        public double toKibiByte(double size) {
            return size * GiB / KiB;
        }

        public double toMebiByte(double size) {
            return size * GiB / MiB;
        }

        public double toGibiByte(double size) {
            return size;
        }

        public double toTebiByte(double size) {
            return size * GiB / TiB;
        }

        public double toPebiByte(double size) {
            return size * GiB / PiB;
        }

        public double toExbiByte(double size) {
            return size * GiB / EiB;
        }

        public double toZebiByte(double size) {
            return size * GiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size * GiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toGibiByte(size);
        }
    },

    TEBIBYTE("TiB") {
        public double toBit(double size) {
            return size * TiB * 8.0;
        }

        public double toByte(double size) {
            return size * TiB;
        }

        public double toKibiByte(double size) {
            return size * TiB / KiB;
        }

        public double toMebiByte(double size) {
            return size * TiB / MiB;
        }

        public double toGibiByte(double size) {
            return size * TiB / GiB;
        }

        public double toTebiByte(double size) {
            return size;
        }

        public double toPebiByte(double size) {
            return size * TiB / PiB;
        }

        public double toExbiByte(double size) {
            return size * TiB / EiB;
        }

        public double toZebiByte(double size) {
            return size * TiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size * TiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toTebiByte(size);
        }
    },

    PEBIBYTE("PiB") {
        public double toBit(double size) {
            return size * PiB * 8.0;
        }

        public double toByte(double size) {
            return size * PiB;
        }

        public double toKibiByte(double size) {
            return size * PiB / KiB;
        }

        public double toMebiByte(double size) {
            return size * PiB / MiB;
        }

        public double toGibiByte(double size) {
            return size * PiB / GiB;
        }

        public double toTebiByte(double size) {
            return size * PiB / TiB;
        }

        public double toPebiByte(double size) {
            return size;
        }

        public double toExbiByte(double size) {
            return size * PiB / EiB;
        }

        public double toZebiByte(double size) {
            return size * PiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size * PiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toPebiByte(size);
        }
    },

    EXBIBYTE("EiB") {
        public double toBit(double size) {
            return size * EiB * 8.0;
        }

        public double toByte(double size) {
            return size * EiB;
        }

        public double toKibiByte(double size) {
            return size * EiB / KiB;
        }

        public double toMebiByte(double size) {
            return size * EiB / MiB;
        }

        public double toGibiByte(double size) {
            return size * EiB / GiB;
        }

        public double toTebiByte(double size) {
            return size * EiB / TiB;
        }

        public double toPebiByte(double size) {
            return size * EiB / PiB;
        }

        public double toExbiByte(double size) {
            return size;
        }

        public double toZebiByte(double size) {
            return size * EiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size * EiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toExbiByte(size);
        }
    },

    ZEBIBYTE("ZiB") {
        public double toBit(double size) {
            return size * ZiB * 8.0;
        }

        public double toByte(double size) {
            return size * ZiB;
        }

        public double toKibiByte(double size) {
            return size * ZiB / KiB;
        }

        public double toMebiByte(double size) {
            return size * ZiB / MiB;
        }

        public double toGibiByte(double size) {
            return size * ZiB / GiB;
        }

        public double toTebiByte(double size) {
            return size * ZiB / TiB;
        }

        public double toPebiByte(double size) {
            return size * ZiB / PiB;
        }

        public double toExbiByte(double size) {
            return size * ZiB / EiB;
        }

        public double toZebiByte(double size) {
            return size;
        }

        public double toYobiByte(double size) {
            return size * ZiB / YiB;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toZebiByte(size);
        }
    },

    YOBIBYTE("YiB") {
        public double toBit(double size) {
            return size * YiB * 8.0;
        }

        public double toByte(double size) {
            return size * YiB;
        }

        public double toKibiByte(double size) {
            return size * YiB / KiB;
        }

        public double toMebiByte(double size) {
            return size * YiB / MiB;
        }

        public double toGibiByte(double size) {
            return size * YiB / GiB;
        }

        public double toTebiByte(double size) {
            return size * YiB / TiB;
        }

        public double toPebiByte(double size) {
            return size * YiB / PiB;
        }

        public double toExbiByte(double size) {
            return size * YiB / EiB;
        }

        public double toZebiByte(double size) {
            return size * YiB / ZiB;
        }

        public double toYobiByte(double size) {
            return size;
        }

        public double convert(double size, BinaryMemoryUnit unit) {
            return unit.toYobiByte(size);
        }
    };

    static double KiB = 1024.0;
    static double MiB = 1024.0 * KiB;
    static double GiB = 1024.0 * MiB;
    static double TiB = 1024.0 * GiB;
    static double PiB = 1024.0 * TiB;
    static double EiB = 1024.0 * PiB;
    static double ZiB = 1024.0 * EiB;
    static double YiB = 1024.0 * ZiB;

    public double toBit(double size) {
        throw new AbstractMethodError();
    }

    public double toByte(double size) {
        throw new AbstractMethodError();
    }

    public double toKibiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toMebiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toGibiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toTebiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toPebiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toExbiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toZebiByte(double size) {
        throw new AbstractMethodError();
    }

    public double toYobiByte(double size) {
        throw new AbstractMethodError();
    }

    public double convert(double size, BinaryMemoryUnit unit) {
        throw new AbstractMethodError();
    }

    public static BinaryMemoryUnit valueOf(double size, BinaryMemoryUnit unit) {

        if (BinaryMemoryUnit.KIBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.BYTE;
        }

        if (BinaryMemoryUnit.MEBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.KIBIBYTE;
        }

        if (BinaryMemoryUnit.GIBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.MEBIBYTE;
        }

        if (BinaryMemoryUnit.TEBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.GIBIBYTE;
        }

        if (BinaryMemoryUnit.PEBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.TEBIBYTE;
        }

        if (BinaryMemoryUnit.EXBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.PEBIBYTE;
        }

        if (BinaryMemoryUnit.ZEBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.EXBIBYTE;
        }

        if (BinaryMemoryUnit.YOBIBYTE.convert(size, unit) < 1.0) {
            return BinaryMemoryUnit.EXBIBYTE;
        }

        return BinaryMemoryUnit.YOBIBYTE;
    }

    private final String unit;

    BinaryMemoryUnit(String unit) {
        this.unit = unit;
    }

    public String unit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return this.unit;
    }
}
