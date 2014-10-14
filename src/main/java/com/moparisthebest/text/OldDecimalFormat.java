/*
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.moparisthebest.text;

import java.text.DecimalFormatSymbols;

/**
 * This class can be used if you don't want to import two
 * classes named the same, it simply extends
 * com.moparisthebest.text.DecimalFormat
 * and changes no behavior.
 */
public class OldDecimalFormat extends DecimalFormat {

    public static void main(String[] args) {
        final String num1 = args.length > 0 ? args[0] : "50";
        final String num2 = args.length > 1 ? args[1] : "0.0259";
        final int precision = Integer.parseInt(args.length > 2 ? args[2] : "2");

        System.out.printf("%s * %s with precision of %d:%n", num1, num2, precision);

        final java.math.BigDecimal bd = new java.math.BigDecimal(num1).multiply(new java.math.BigDecimal(num2));
        System.out.println("java.math.BigDecimal:                  " + bd.setScale(precision, java.math.RoundingMode.HALF_EVEN).toString());

        final double value = Double.parseDouble(num1) * Double.parseDouble(num2);

        java.text.DecimalFormat df = new com.moparisthebest.text.DecimalFormat();
        df.setMaximumFractionDigits(precision);
        df.setMinimumFractionDigits(precision);
        System.out.println("com.moparisthebest.text.DecimalFormat: " + df.format(value));

        df = new java.text.DecimalFormat();
        df.setMaximumFractionDigits(precision);
        df.setMinimumFractionDigits(precision);
        System.out.println("java.text.DecimalFormat:               " + df.format(value));
    }

    public OldDecimalFormat() {
        super();
    }

    public OldDecimalFormat(String pattern) {
        super(pattern);
    }

    public OldDecimalFormat(String pattern, DecimalFormatSymbols symbols) {
        super(pattern, symbols);
    }

}
