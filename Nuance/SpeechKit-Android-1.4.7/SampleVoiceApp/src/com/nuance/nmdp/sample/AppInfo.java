package com.nuance.nmdp.sample;

public class AppInfo
{
    /**
     * The login parameters should be specified in the following manner:
     * 
     * public static final String SpeechKitServer = "ndev.server.name";
     * 
     * public static final int SpeechKitPort = 1000;
     * 
     * public static final String SpeechKitAppId = "ExampleSpeechKitSampleID";
     * 
     * public static final byte[] SpeechKitApplicationKey =
     * {
     *     (byte)0x38, (byte)0x32, (byte)0x0e, (byte)0x46, (byte)0x4e, (byte)0x46, (byte)0x12, (byte)0x5c, (byte)0x50, (byte)0x1d,
     *     (byte)0x4a, (byte)0x39, (byte)0x4f, (byte)0x12, (byte)0x48, (byte)0x53, (byte)0x3e, (byte)0x5b, (byte)0x31, (byte)0x22,
     *     (byte)0x5d, (byte)0x4b, (byte)0x22, (byte)0x09, (byte)0x13, (byte)0x46, (byte)0x61, (byte)0x19, (byte)0x1f, (byte)0x2d,
     *     (byte)0x13, (byte)0x47, (byte)0x3d, (byte)0x58, (byte)0x30, (byte)0x29, (byte)0x56, (byte)0x04, (byte)0x20, (byte)0x33,
     *     (byte)0x27, (byte)0x0f, (byte)0x57, (byte)0x45, (byte)0x61, (byte)0x5f, (byte)0x25, (byte)0x0d, (byte)0x48, (byte)0x21,
     *     (byte)0x2a, (byte)0x62, (byte)0x46, (byte)0x64, (byte)0x54, (byte)0x4a, (byte)0x10, (byte)0x36, (byte)0x4f, (byte)0x64
     * };
     * 
     * Please note that all the specified values are non-functional
     * and are provided solely as an illustrative example.
     * 
     */

    /* Please contact Nuance to receive the necessary connection and login parameters */
    public static final String SpeechKitServer = "cso.nmdp.nuancemobility.net" /* Enter your server here */;

    public static final int SpeechKitPort = 443/* Enter your port here */;
    
    public static final boolean SpeechKitSsl = false;

    public static final String SpeechKitAppId = "NMDPPRODUCTION_Miroslav_Byrtus_prore_20141111225201"; // Enter your ID here

    public static final byte[] SpeechKitApplicationKey = {
        // Enter your application key here: (byte)0x00, (byte)0x01, ... (byte)0x00
    	
    	(byte)0x7e , (byte)0x20 , (byte)0x77 , (byte)0xb2 , (byte)0xf6 , (byte)0x37 , (byte)0x45 , (byte)0xfb , 
    	(byte)0x49 , (byte)0xaa , (byte)0xbe , (byte)0x66 , (byte)0xd2 , (byte)0xba , (byte)0x7e , (byte)0x5c , 
    	(byte)0x3d , (byte)0x53 , (byte)0xea , (byte)0x5b , (byte)0x8e , (byte)0x9c , (byte)0xaf , (byte)0xbb , 
    	(byte)0x35 , (byte)0x09 , (byte)0x74 , (byte)0xae , (byte)0x51 , (byte)0xfa , (byte)0x6b , (byte)0xcf , 
    	(byte)0x1a , (byte)0xe2 , (byte)0x82 , (byte)0x11 , (byte)0x1a , (byte)0xc7 , (byte)0x0c , (byte)0x63 , 
    	(byte)0x71 , (byte)0xca , (byte)0xc9 , (byte)0x44 , (byte)0x84 , (byte)0x47 , (byte)0xa0 , (byte)0xc1 , 
    	(byte)0x9b , (byte)0x8a , (byte)0x1d , (byte)0x29 , (byte)0x27 , (byte)0xcc , (byte)0xe4 , (byte)0x5c , 
    	(byte)0xd5 , (byte)0x59 , (byte)0x0a , (byte)0x26 , (byte)0x53 , (byte)0x75 , (byte)0xf6 , (byte)0x6e
    };
}
