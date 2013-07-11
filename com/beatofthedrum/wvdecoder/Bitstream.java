/*
** Bitstream.java
**
** Copyright (c) 2007 - 2013 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)
**
*/

package com.beatofthedrum.wvdecoder;

class Bitstream
{
    int end, ptr;	// was uchar in c
    long file_bytes;	// was uint32_t in C
    long sr;
    int error, bc;
    java.io.DataInputStream file;
    int bitval = 0;
    byte[] buf = new byte[Defines.BITSTREAM_BUFFER_SIZE];
    int buf_index = 0;
}