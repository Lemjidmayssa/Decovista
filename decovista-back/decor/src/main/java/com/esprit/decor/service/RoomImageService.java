package com.esprit.decor.service;

import com.esprit.decor.entity.*;
import com.esprit.decor.repository.*;
import lombok.RequiredArgsConstructor;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomImageService {

    private final RoomImageRepository roomImageRepository;
    private final SuggestionRepository suggestionRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public RoomImage uploadImage(MultipartFile file, String username, String roomType) throws IOException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        byte[] imageBytes = file.getBytes();
        RoomImage image = RoomImage.builder()
                .image(imageBytes)
                .uploadDate(LocalDateTime.now())
                .utilisateur(user)
                .build();

        double surface = calculerSurfaceDepuisImage(imageBytes);
        image.setSurface(surface);

        RoomImage saved = roomImageRepository.save(image);

        List<Article> suggestions = articleRepository.findByTypeAndSurfaceMax(roomType, surface * 10000);
        for (Article article : suggestions) {
            Suggestion s = new Suggestion();
            s.setRoomImage(saved);
            s.setArticle(article);
            s.setSurface(surface);
            suggestionRepository.save(s);
        }

        return saved;
    }

    private double calculerSurfaceDepuisImage(byte[] imageBytes) throws IOException {

        File tempFile = File.createTempFile("room", ".jpg");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(imageBytes);
        }


        Mat src = Imgcodecs.imread(tempFile.getAbsolutePath());
        if (src.empty()) throw new IOException("Échec de lecture de l’image");

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);

        List<MatOfPoint> contours = new java.util.ArrayList<>();
        Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
            }
        }

        double refAreaCm2 = 21 * 29.7;
        double refPixelArea = 30000;
        double ratio = refAreaCm2 / refPixelArea;

        double surfaceCm2 = maxArea * ratio;
        return surfaceCm2 / 10000.0;
    }

    private List<Article> suggereArticles(double surface) {
        return articleRepository.findAll().stream()
                .filter(a -> a.getLongueur() * a.getLargeur() <= surface * 10000)
                .limit(5)
                .toList();
    }
}
