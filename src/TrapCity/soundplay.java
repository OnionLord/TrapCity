package TrapCity;


import java.io.File;
import javax.sound.sampled.*;

class SoundPlay extends Thread {
private File soundFile = null;
public SoundPlay(String fileName) {
 soundFile = new File(fileName);
}
public void run() {
 try {
  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
  AudioFormat audioFormat = audioInputStream.getFormat();
 
  DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
  SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
  line.open(audioFormat);
  line.start();
  int nBytesRead = 0;
  byte[] abData = new byte[128000];
  while (nBytesRead != -1) {
   nBytesRead = audioInputStream.read(abData, 0, abData.length);
   if (nBytesRead >= 0) {
    int nBytesWritten = line.write(abData, 0, nBytesRead);
   }
  }
  line.drain();
  line.close();
 } catch (Exception e) {
 }
}

}


