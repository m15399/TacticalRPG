package tests;
import songPlayer.SongPlayer;

public class PlaySongTest {

  /**
   * Play one audio file with no listener for the end of song event.
   * A println is included to indicate the song is playing in a separate Thread.
   */
  public static void main(String[] args) {
    SongPlayer.playFile("./songFiles/space_clips.mp3");

    System.out
        .println("Song played in a separate Thread so this appears immediately");
    System.out.println("This program terminates only when song finishes...");
  }

}