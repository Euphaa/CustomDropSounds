package com.mod.util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

public class SoundFilePlayer {

    //plays a sound from outside of the jar. wav's should be stored in .minecraft/euphaasmod/
    //i think this only works with wav files :/
    public static void playCustomSound(String filePath, float volume) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Adjust volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //plays a sound from within the jar. root path is /resources
    public static void playSound(String fileName)
    {
        try {
            InputStream inputStream = SoundFilePlayer.class.getResourceAsStream(fileName);
            if (inputStream == null)
            {
                System.err.println("File not found: " + fileName);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));

            AudioFormat format = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 0;
            while ((bytesRead = audioInputStream.read(buffer)) != -1)
            {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.stop();
            line.close();

            audioInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}