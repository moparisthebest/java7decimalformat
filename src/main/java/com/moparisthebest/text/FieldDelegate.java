/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
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
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 */

package com.moparisthebest.text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.FieldPosition;
import java.text.Format;

/**
 * This implementation delegates to the real interface/implementation
 * found in java.text.Format$FieldDelegate
 * <p/>
 * FieldDelegate is notified by the various <code>Format</code>
 * implementations as they are formatting the Objects. This allows for
 * storage of the individual sections of the formatted String for
 * later use, such as in a <code>FieldPosition</code> or for an
 * <code>AttributedCharacterIterator</code>.
 * <p/>
 * Delegates should NOT assume that the <code>Format</code> will notify
 * the delegate of fields in any particular order.
 *
 * @see java.text.FieldPosition#getFieldDelegate
 * @see java.text.CharacterIteratorFieldDelegate
 */
public class FieldDelegate {

    private static final Method getFieldDelegate;
    private static final Method format1;
    private static final Method format2;

    static {
        Method gfd = null, f1 = null, f2 = null;
        try {
            gfd = FieldPosition.class.getDeclaredMethod("getFieldDelegate");
            gfd.setAccessible(true);

            final Class<?> realFieldDelegate = Class.forName("java.text.Format$FieldDelegate");

            f1 = realFieldDelegate.getDeclaredMethod("formatted", Format.Field.class, Object.class, int.class, int.class, StringBuffer.class);
            f1.setAccessible(true);

            f2 = realFieldDelegate.getDeclaredMethod("formatted", int.class, Format.Field.class, Object.class, int.class, int.class, StringBuffer.class);
            f2.setAccessible(true);
        } catch (Throwable e) {
            throw new RuntimeException(DecimalFormat.DECIMAL_FORMAT_EXCEPTION, e);
        }
        getFieldDelegate = gfd;
        format1 = f1;
        format2 = f2;
    }

    private final Object fieldDelegate;

    public FieldDelegate(final FieldPosition fieldPosition) {
        //this(fieldPosition.getFieldDelegate());
        Object fieldDelegate = null;
        if (fieldPosition != null)
            try {
                fieldDelegate = getFieldDelegate.invoke(fieldPosition);
            } catch (Throwable e) {
                throw new RuntimeException(DecimalFormat.DECIMAL_FORMAT_EXCEPTION, e);
            }
        this.fieldDelegate = fieldDelegate;
    }

    public FieldDelegate(final Object fieldDelegate) {
        this.fieldDelegate = fieldDelegate;
    }

    /**
     * Notified when a particular region of the String is formatted. This
     * method will be invoked if there is no corresponding integer field id
     * matching <code>attr</code>.
     *
     * @param attr   Identifies the field matched
     * @param value  Value associated with the field
     * @param start  Beginning location of the field, will be >= 0
     * @param end    End of the field, will be >= start and <= buffer.length()
     * @param buffer Contains current formatted value, receiver should
     *               NOT modify it.
     */
    public void formatted(Format.Field attr, Object value, int start,
                          int end, StringBuffer buffer) {

        try {
            format1.invoke(this.fieldDelegate, attr, value, start, end, buffer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(DecimalFormat.DECIMAL_FORMAT_EXCEPTION, e);
        }
    }

    /**
     * Notified when a particular region of the String is formatted.
     *
     * @param fieldID Identifies the field by integer
     * @param attr    Identifies the field matched
     * @param value   Value associated with the field
     * @param start   Beginning location of the field, will be >= 0
     * @param end     End of the field, will be >= start and <= buffer.length()
     * @param buffer  Contains current formatted value, receiver should
     *                NOT modify it.
     */
    public void formatted(int fieldID, Format.Field attr, Object value,
                          int start, int end, StringBuffer buffer) {
        try {
            format2.invoke(this.fieldDelegate, fieldID, attr, value, start, end, buffer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(DecimalFormat.DECIMAL_FORMAT_EXCEPTION, e);
        }
    }
}
