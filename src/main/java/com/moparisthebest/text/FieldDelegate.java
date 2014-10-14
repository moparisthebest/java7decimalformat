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

package java.text;

/**
 * FieldDelegate is notified by the various <code>Format</code>
 * implementations as they are formatting the Objects. This allows for
 * storage of the individual sections of the formatted String for
 * later use, such as in a <code>FieldPosition</code> or for an
 * <code>AttributedCharacterIterator</code>.
 * <p/>
 * Delegates should NOT assume that the <code>Format</code> will notify
 * the delegate of fields in any particular order.
 *
 * @see FieldPosition#getFieldDelegate
 * @see CharacterIteratorFieldDelegate
 */
interface FieldDelegate {
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
                          int end, StringBuffer buffer);

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
                          int start, int end, StringBuffer buffer);
}
