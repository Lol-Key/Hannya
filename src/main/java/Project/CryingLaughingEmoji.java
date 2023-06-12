package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CryingLaughingEmoji extends Emoji
{
	ImageView ansView, funiView;

	CryingLaughingEmoji()
	{
		Image ansImage = new Image(Objects.requireNonNull(App.class.getResource("ANS.png")).toExternalForm());
        	Image funiImage = new Image( Objects.requireNonNull(App.class.getResource("veryfuni.png")).toExternalForm() );

		ansView = new ImageView(ansImage);
		funiView = new ImageView(funiImage);

        	ansView.setTranslateX(400);
        	ansView.setTranslateY(400);

		ansView.setFitHeight(180);
		ansView.setFitWidth(320);

		ArrayList<Node> temp = new ArrayList<Node>();
		temp.add(funiView);
		temp.add(ansView);

		elements = new Group(temp);

		x=100;
		y=-100;
	}

	@Override
	public void runAnimation()
	{
		FadeTransition ft = new FadeTransition(Duration.millis(1000),ansView);
		ft.setFromValue(0.75);
		ft.setToValue(0.0);
		ft.setCycleCount(8);
		ft.setAutoReverse(true);
		ft.play();

        	ScaleTransition jakTowarNaRejon = new ScaleTransition();
        	jakTowarNaRejon.setDuration(Duration.millis(1000));
        	jakTowarNaRejon.setNode(funiView);
        	jakTowarNaRejon.setFromX(0);
        	jakTowarNaRejon.setFromY(0);
        	jakTowarNaRejon.setByY(1);
        	jakTowarNaRejon.setByX(1);
        	jakTowarNaRejon.setCycleCount(1);
        	jakTowarNaRejon.setAutoReverse(false);

        	ScaleTransition scaleTransition = new ScaleTransition();
        	scaleTransition.setDuration(Duration.millis(1000));
        	scaleTransition.setNode(funiView);
        	scaleTransition.setByY(2);
        	scaleTransition.setByX(2);
        	scaleTransition.setCycleCount(7);
        	scaleTransition.setAutoReverse(true);

        	RotateTransition rotateTransition = new RotateTransition();
        	rotateTransition.setDuration(Duration.millis(2000));
        	rotateTransition.setNode(funiView);
        	rotateTransition.setByAngle(76);
        	rotateTransition.setFromAngle(-38);
        	rotateTransition.setCycleCount(4);
        	rotateTransition.setAutoReverse(true);


        	jakTowarNaRejon.setOnFinished(t -> { scaleTransition.play(); } );
        	rotateTransition.setOnFinished( t -> { elements.setTranslateX(10000); } );
		rotateTransition.play();
        	jakTowarNaRejon.play();
	} 
}
