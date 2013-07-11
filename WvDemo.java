/*
** WvDemo.java
**
** Copyright (c) 2007 - 2013 Peter McQuillan
**
** All Rights Reserved.
**                       
** Distributed under the BSD Software License (see license.txt)  
**
*/

import com.beatofthedrum.wvdecoder.*;

public class WvDemo
{
    static int [] temp_buffer = new int[Defines.SAMPLE_BUFFER_SIZE];

    static byte [] pcm_buffer = new byte[4 * Defines.SAMPLE_BUFFER_SIZE];

    public static void main(String [] args)
    {
        long total_unpacked_samples = 0, total_samples; // was uint32_t in C
        int num_channels, bps;
        WavpackContext wpc = new WavpackContext();
        java.io.FileInputStream fistream;
        java.io.BufferedOutputStream fostream;
        java.io.DataInputStream in;
        long start, end;

        String inputWVFile;

        if (args.length == 0)
        {
            inputWVFile = "input.wv";
        }
        else
        {
            inputWVFile = args[0];
        }

        try
        {
            fistream = new java.io.FileInputStream(inputWVFile);
            in = new java.io.DataInputStream(new java.io.BufferedInputStream(fistream));
            wpc = WavPackUtils.WavpackOpenFileInput(in);
        }
        catch (java.io.FileNotFoundException fe)
        {
            System.err.println("Input file not found");
            System.exit(1);
        }

        if (wpc.error)
        {
            System.err.println("Sorry an error has occured");
            System.err.println(wpc.error_message);
            System.exit(1);
        }

        num_channels = WavPackUtils.WavpackGetReducedChannels(wpc);

        System.out.println("The wavpack file has " + num_channels + " channels");

        total_samples = WavPackUtils.WavpackGetNumSamples(wpc);

        System.out.println("The wavpack file has " + total_samples + " samples");

        bps = WavPackUtils.WavpackGetBytesPerSample(wpc);

        System.out.println("The wavpack file has " + bps + " bytes per sample");

        try
        {

            fostream = new java.io.BufferedOutputStream(new java.io.FileOutputStream("output.wav"));
            
            WavWriter.wavwriter_writeheaders(fostream, (int)(total_samples * bps * num_channels), num_channels, (int)WavPackUtils.WavpackGetSampleRate(wpc), bps, WavPackUtils.WavpackGetBitsPerSample(wpc));
            
            start = System.currentTimeMillis();

            while (true)
            {
                long samples_unpacked; // was uint32_t in C

                samples_unpacked = WavPackUtils.WavpackUnpackSamples(wpc, temp_buffer, Defines.SAMPLE_BUFFER_SIZE / num_channels);

                total_unpacked_samples += samples_unpacked;

                if (samples_unpacked > 0)
                {
                    samples_unpacked = samples_unpacked * num_channels;

                    format_samples(bps, temp_buffer, samples_unpacked);
                    fostream.write(pcm_buffer, 0, (int) samples_unpacked * bps);
                }

                if (samples_unpacked == 0)
                    break;

            } // end of while
            

            // invokes flush to force bytes to be written out to fostream
            fostream.flush();

            end = System.currentTimeMillis();

            System.out.println(end - start + " milli seconds to process WavPack file in main loop");
        }
        catch (Exception e)
        {
            System.err.println("Error when writing wav file, sorry: ");
            e.printStackTrace();
            System.exit(1);
        }

        if ((WavPackUtils.WavpackGetNumSamples(wpc) != -1)
            && (total_unpacked_samples != WavPackUtils.WavpackGetNumSamples(wpc)))
        {
            System.err.println("Incorrect number of samples");
            System.exit(1);
        }

        if (WavPackUtils.WavpackGetNumErrors(wpc) > 0)
        {
            System.err.println("CRC errors detected");
            java.lang.System.exit(1);
        }

        java.lang.System.exit(0);
    }


    // Reformat samples from longs in processor's native endian mode to
    // little-endian data with (possibly) less than 4 bytes / sample.

    static void format_samples(final int bps, int src [], long samcnt)
    {
        int temp;
        int counter = 0;
        int counter2 = 0;

        switch (bps)
        {
            case 1:
                while (samcnt > 0)
                {
                	pcm_buffer[counter] = (byte) (0x00FF & (src[counter] + 128));
                    counter++;
                    samcnt--;
                }
                break;

            case 2:
                while (samcnt > 0)
                {
                    temp = src[counter2];
                    pcm_buffer[counter] = (byte) temp;
                    counter++;
                    pcm_buffer[counter] = (byte) (temp >>> 8);
                    counter++;
                    counter2++;
                    samcnt--;
                }

                break;

            case 3:
                while (samcnt > 0)
                {
                    temp = src[counter2];
                    pcm_buffer[counter] = (byte) temp;
                    counter++;
                    pcm_buffer[counter] = (byte) (temp >>> 8);
                    counter++;
                    pcm_buffer[counter] = (byte) (temp >>> 16);
                    counter++;
                    counter2++;
                    samcnt--;
                }

                break;

            case 4:
                while (samcnt > 0)
                {
                    temp = src[counter2];
                    pcm_buffer[counter] = (byte) temp;
                    counter++;
                    pcm_buffer[counter] = (byte) (temp >>> 8);
                    counter++;
                    pcm_buffer[counter] = (byte) (temp >>> 16);
                    counter++;
                    pcm_buffer[counter] = (byte) (temp >>> 24);
                    counter++;
                    counter2++;
                    samcnt--;
                }

                break;
        }
    }
}