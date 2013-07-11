/*
** decorr_pass.java
**
** Copyright (c) 2007 - 2013 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/

package com.beatofthedrum.wvdecoder;

class decorr_pass
{
    int term, delta, weight_A, weight_B;
    int[] samples_A = new int[Defines.MAX_TERM];
    int[] samples_B = new int[Defines.MAX_TERM];
}