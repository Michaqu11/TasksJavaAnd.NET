package platformy_technoloczine;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        int poolSize =  Integer.parseInt(args[0]);

        ForkJoinPool pool = new ForkJoinPool(poolSize);
        long time = System.currentTimeMillis();
        try {
            pool.submit(()->{

                Path source = Path.of("src/main/resources/orginal");

                try (Stream<Path> stream = Files.list(source).parallel()){
                    List<Path> files = null;
                    files = stream.collect(Collectors.toList());
                    Stream<Pair<String, BufferedImage>> loadImages = files.stream().parallel()
                            .map(value -> {
                                String name = value.getFileName().toString();
                                BufferedImage image = null;
                                try {
                                     image = ImageIO.read(value.toFile());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return Pair.of(name, image);
                            });

                    Stream<Pair<String, BufferedImage>> editImages = loadImages.parallel()
                            .map(value -> {
                                BufferedImage original = value.getRight();
                                BufferedImage image = new BufferedImage(original.getWidth(),
                                        original.getHeight(),
                                        original.getType());

                                for (int i = 0; i < original.getWidth(); i++) {
                                    for (int j = 0; j < original.getHeight(); j++) {
                                        int rgb = original.getRGB(i, j);
                                        Color color = new Color(rgb);
                                        int red = color.getRed();
                                        int green = color.getGreen();
                                        int blue = color.getBlue();
                                        int newRgb = new Color(red,blue,green).getRGB();
                                        image.setRGB(i, j, newRgb);
                                    }
                                }
                                return Pair.of(value.getLeft(),image);
                            });

                        editImages.parallel().forEach(value -> {
                        File file = new File("src/main/resources/edit/" + value.getLeft());
                        try {
                            ImageIO.write(value.getRight(),"jpg",file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pool.shutdown();
        System.out.println("Czas wykonwyania na " + poolSize + " watkach: " + (System.currentTimeMillis() - time) + " ms");
    }
}
