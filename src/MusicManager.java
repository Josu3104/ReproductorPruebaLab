

import javax.swing.JFileChooser;

/**
 *
 * @author Josue Gavidia
 */
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MusicManager {

    private AdvancedPlayer player;
    private JFileChooser fileChooser;
    private File startDir;
    private File cancion;
    private ArrayList Canciones = new ArrayList();

    public MusicManager() throws IOException {
        startDir = new File("Musica");

    }

    public void Add(JLabel artist, JLabel song, JLabel duration, JFrame frame) {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(startDir);
        fileChooser.setMultiSelectionEnabled(true);
        int fileValid = fileChooser.showOpenDialog(frame);

        if (fileValid == javax.swing.JFileChooser.CANCEL_OPTION) {
        } else if (fileValid == javax.swing.JFileChooser.APPROVE_OPTION) {

            File[] file = fileChooser.getSelectedFiles();
            Canciones.addAll(Arrays.asList(file));
            JFileChooser nombreArchivo = fileChooser;
            String[] partes = Arrays.toString(nombreArchivo.getSelectedFiles()).split(" - ");

                String artista = partes[0];
                String sing = partes[1];
                String duracion = partes[2];

                artist.setText(artista);
                song.setText(sing);
                duration.setText(duracion);

          

        }
    }

    public void PLAY() {
        if (cancion == null || !cancion.exists()) {
            System.out.println("Este archivo no existe");
            return;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(cancion);
            player = new AdvancedPlayer(fileInputStream);

            player.setPlayBackListener(new PlaybackListener() {

                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    if (evt.getId() == PlaybackEvent.STOPPED) {
                        System.exit(0);
                    }
                }
            });

            Thread playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                    System.out.println("Error playing the audio.");
                }
            });

            playerThread.start();
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
            System.out.println("Error initializing the player.");
        }
    }

    public ArrayList getCanciones() {
        return Canciones;
    }

}

