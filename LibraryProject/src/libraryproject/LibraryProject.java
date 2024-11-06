import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LibraryProject extends JFrame {

    private static LibraryProject library_library;
    private static long last_frame_time;
    private static Image book;
    private static Image synergy;
    private static Image libraryBackground; // Фон library
    private static List<BookDrop> books = new ArrayList<>(); // Список падающих книг
    private static Random random = new Random();

    public static void main(String[] args) throws IOException {
        // Загружаем изображения
        book = ImageIO.read(LibraryProject.class.getResourceAsStream("/book.jpeg"));
        synergy = ImageIO.read(LibraryProject.class.getResourceAsStream("/synergy.png"));
        libraryBackground = ImageIO.read(LibraryProject.class.getResourceAsStream("/library.jpg"));
        
        library_library = new LibraryProject();
        library_library.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        library_library.setLocation(200, 50);
        library_library.setSize(900, 900);
        library_library.setResizable(false);

        last_frame_time = System.nanoTime();

  
        for (int i = 0; i < 20; i++) {
            books.add(new BookDrop(random.nextInt(900), -random.nextInt(900), random.nextInt(200) + 100));
        }

        LibraryField library_field = new LibraryField();
        library_library.add(library_field);
        library_library.setVisible(true);
    }

 
    public static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

  
        g.drawImage(libraryBackground, 0, 0, null);

  
        g.drawImage(synergy, 0, 0, null);

 
        for (BookDrop drop : books) {
            drop.update(delta_time);
            g.drawImage(book, (int) drop.x, (int) drop.y, 50, 50, null); // Рисуем маленькие книги
        }
    }

  
    public static class BookDrop {
        public double x;
        public double y;
        public double speed;

        public BookDrop(double x, double y, double speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }


        public void update(double deltaTime) {
            y += speed * deltaTime;
            if (y > 900) {  
                y = -50;
                x = random.nextInt(900);
            }
        }
    }

    public static class LibraryField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}