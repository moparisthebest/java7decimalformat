Java 7 DecimalFormat
--------------------

If you upgrade to Java 8 you may be bit by [this bug fix](https://bugs.openjdk.java.net/browse/JDK-7131459).  A temporary JVM-wide fix that works is to copy java/text/DigitList.class from Java 7's rt.jar to Java 8's rt.jar, but who wants to run that way forever?

This project takes GPLv2 code from jdk7 and jdk8 to create a class that extends DecimalFormat, but retains the old rounding behavior of Java 6 and 7.

[This](http://hg.openjdk.java.net/jdk8/jdk8/jdk/rev/bc1f16f5566f) is the offending bug fix we are trying to revert, it only changes DigitList.java, but since it's package private and not an interface, there is no way to just change it's usages in only *some* instances of DecimalFormat, even with reflection.  I took [DecimalFormat.java and some classes it depends on from jdk8](http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/text) and [DigitList.java from jdk7](http://hg.openjdk.java.net/jdk7/jdk7/jdk/file/9b8c96f96a0f/src/share/classes/java/text), which you will find unchanged in the first commit.  The changes I had to make to get everything to work are included in the second and subsequent commits.

To use this, replace your java.text.DecimalFormat usages with com.moparisthebest.text.DecimalFormat (or com.moparisthebest.text.OldDecimalFormat, they are the same).  These classes are instances of java.text.DecimalFormat so only the constructors really need changed.  com.moparisthebest.text.OldDecimalFormat has a main method that allows you to test various calculations and their output using the 'correct' method of BigDecimal for decimal math, and the two Decimalformat implementations, here is some example output:

    $ /usr/lib/jvm/java-8-oracle/bin/java com.moparisthebest.text.OldDecimalFormat 50 0.0259 2
    50 * 0.0259 with precision of 2:
    java.math.BigDecimal:                  1.30
    com.moparisthebest.text.DecimalFormat: 1.30
    java.text.DecimalFormat:               1.29

License
-------

By necessity, this must inherit the license Sun/Oracle provided the classes under, which is GPLv2 only, the headers in the classes are unchanged.  A copy of the full license is available in LICENSE.

    This code is free software; you can redistribute it and/or modify it
    under the terms of the GNU General Public License version 2 only, as
    published by the Free Software Foundation.  Oracle designates this
    particular file as subject to the "Classpath" exception as provided
    by Oracle in the LICENSE file that accompanied this code.

    This code is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
    version 2 for more details (a copy is included in the LICENSE file that
    accompanied this code).

    You should have received a copy of the GNU General Public License version
    2 along with this work; if not, write to the Free Software Foundation,
    Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
