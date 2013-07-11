/*
** WavpackHeader.java
**
** Copyright (c) 2007 - 2013 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/

package com.beatofthedrum.wvdecoder;

class WavpackHeader
{

    char ckID[] = new char[4];
    long ckSize;    // was uint32_t in C
    short version;
    short track_no, index_no;    // was uchar in C
    long total_samples, block_index, block_samples, flags, crc;    // was uint32_t in C
    int status = 0;    // 1 means error
    byte buffer [] = new byte[32]; // 32 is the size of a WavPack Header
    byte temp [] = new byte[32];
}