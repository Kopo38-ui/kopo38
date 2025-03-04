package com.example.demo7;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.effect.Glow;

public class HelloApplication  extends Application {
    private static final String[] thumbnailPaths = {
            "C:src\\main\\resources\\Images\\world.jfif",
            "c:src\\main\\resources\\images\\k1.jfif",
            "C:src\\main\\resources\\images\\k3.jfif",
            "C:src\\main\\resources\\images\\k4.jfif",
            "C:src\\main\\resources\\images\\k2.jfif",
            "C:src\\main\\resources\\images\\k5.jfif",
    };


    private static final String[] fullSizePaths = {
            "C:src\\main\\resources\\Images\\world.jfif",
            "c:src\\main\\resources\\images\\k1.jfif",
            "C:src\\main\\resources\\images\\k3.jfif",
            "C:src\\main\\resources\\images\\k4.jfif",
            "C:src\\main\\resources\\images\\k2.jfif",
            "C:src\\main\\resources\\images\\k5.jfif",
    };

    private int currentIndex = -1;

    @Override
    public void start(Stage primaryStage)
    {
        // GridPane to display the thumbnails
        GridPane grid = new GridPane();

        /* gap between images*/
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color:yellow");

        for (int i = 0; i < thumbnailPaths.length; i++) {
            Image thumbnailImage = new Image("file:" + thumbnailPaths[i]);
            ImageView thumbnailImageView = new ImageView(thumbnailImage);
            thumbnailImageView.setFitWidth(150);
            thumbnailImageView.setFitHeight(150);

            /* Add hover effect on thumbnails */
            thumbnailImageView.setOnMouseEntered(event -> {
                thumbnailImageView.setEffect(new Glow(0.8));
            });
            thumbnailImageView.setOnMouseExited(event -> {
                thumbnailImageView.setEffect(null); // Remove effect
            });
            grid.add(thumbnailImageView, i % 3, i / 3);

            int index = i;  // Store the index for later use
            thumbnailImageView.setOnMouseClicked(event -> {
                showFullImage(index);
            });
        }
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();
    }
    /*Functions*/
    private void showFullImage(int index) {
        currentIndex = index;
        Stage fullImageStage = new Stage();
        Image fullImage = new Image("file:" + fullSizePaths[currentIndex]);
        ImageView fullImageView = new ImageView(fullImage);
        fullImageView.setFitWidth(500);
        fullImageView.setFitHeight(500);

// Add animations for the full image
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), fullImageView);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
// Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> fullImageStage.close());
// Next button
        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            currentIndex = (currentIndex + 1) % fullSizePaths.length;  // Move to next image (circular)
            fullImageView.setImage(new Image("file:" + fullSizePaths[currentIndex]));
            scaleTransition.play();
        });
// show the previous image
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(e -> {
            currentIndex = (currentIndex - 1 + fullSizePaths.length) % fullSizePaths.length;  // Move to previous image (circular)
            fullImageView.setImage(new Image("file:" + fullSizePaths[currentIndex]));
            scaleTransition.play();
        });
        // Apply hover effect to buttons
        applyButtonHoverEffect(backButton);
        applyButtonHoverEffect(nextButton);
        applyButtonHoverEffect(prevButton);

        HBox buttonBox = new HBox(10, prevButton, backButton, nextButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10, fullImageView, buttonBox);
        vBox.setAlignment(Pos.CENTER);

        Scene fullImageScene = new Scene(vBox, 600, 600);
        fullImageStage.setScene(fullImageScene);
        fullImageStage.setTitle("Full Image");
        fullImageStage.show();
    }
    private void applyButtonHoverEffect(Button button) {
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: lightblue;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: blue;");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
