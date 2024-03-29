////////////////////////////////////////////////////////////////////////////
//            Java Implementation of WavPack Decoder                      //
//              Copyright (c) 2007 - 2013 Peter McQuillan                 //
//                          All Rights Reserved.                          //
//      Distributed under the BSD Software License (see license.txt)      //
////////////////////////////////////////////////////////////////////////////

This package contains a Java implementation of the tiny version of the WavPack 
4.40 decoder. It is packaged with a demo command-line program that accepts a
WavPack audio file as input and outputs a RIFF wav file (with the filename 
output.wav). The program was developed using Java version 1.7.
The Java source code files can be compiled to class files very simply by going 
to the directory where you have downloaded the .java files and running

javac *.java

To run the demo program, use the following command

java WvDemo <input.wv>

where input.wv is the name of the WavPack file you wish to decode to a WAV file.

This decoder will not handle "correction" files, plays only the first two 
channels of multi-channel files, and is limited in resolution in some large 
integer or floating point files (but always provides at least 24 bits of 
resolution). It also will not accept WavPack files from before version 4.0.

Please direct any questions or comments to beatofthedrum@gmail.com