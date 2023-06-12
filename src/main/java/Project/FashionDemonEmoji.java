package Project;

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

public class FashionDemonEmoji extends Emoji
{
	ImageView okView, demonView, afterView;

	FashionDemonEmoji()
	{
		Image ansImage = new Image(Objects.requireNonNull(App.class.getResource("OK.png")).toExternalForm());
        	Image funiImage = new Image( Objects.requireNonNull(App.class.getResource("demon.png")).toExternalForm() );

		okView = new ImageView(ansImage);
		demonView = new ImageView(funiImage);
		afterView = new ImageView(funiImage);

        	okView.setTranslateX(190);
        	okView.setTranslateY(270);

		okView.setFitHeight(100);
		okView.setFitWidth(200);

		ArrayList<Node> temp = new ArrayList<Node>();
		temp.add(demonView);
		temp.add(okView);
		temp.add(afterView);

		elements = new Group(temp);

		x=350;
		y=70;
	}

	@Override
	public void runAnimation()
	{
		
 	       FadeTransition ft = new FadeTransition(Duration.millis(400),demonView);
 	       ft.setFromValue(0.0);
 	       ft.setToValue(0.8);
 	       ft.setCycleCount(1);
 	       ft.setAutoReverse(false);
		
       	 	FadeTransition hideok = new FadeTransition(Duration.millis(1000),okView);
        	hideok.setFromValue(0.0);
        	hideok.setToValue(0.0);
        	hideok.setCycleCount(1);
        	hideok.setAutoReverse(false);

	 
        	FadeTransition hide = new FadeTransition(Duration.millis(300),afterView);
        	hide.setFromValue(0.0);
        	hide.setToValue(0.0);
        	hide.setCycleCount(1);
        	hide.setAutoReverse(false);

        	ScaleTransition main = new ScaleTransition();
        	main.setDuration(Duration.millis(400));
        	main.setNode(demonView);
        	main.setFromX(0);
        	main.setFromY(0);
        	main.setByY(1);
        	main.setByX(1);
        	main.setCycleCount(1);
        	main.setAutoReverse(false);

        	FadeTransition fft = new FadeTransition(Duration.millis(700),afterView);
        	fft.setFromValue(0.5);
        	fft.setToValue(0.0);
        	fft.setCycleCount(1);
        	fft.setAutoReverse(false);

        	ScaleTransition after = new ScaleTransition(Duration.millis(700),afterView);
        	after.setFromX(1.2);
        	after.setFromY(1.2);
        	after.setByY(30);
        	after.setByX(30);
        	after.setCycleCount(1);
        	after.setAutoReverse(false);
	
        	FadeTransition fadeaway = new FadeTransition(Duration.millis(700),demonView);
        	fadeaway.setFromValue(0.8);
        	fadeaway.setToValue(0.0);
        	fadeaway.setCycleCount(1);
        	fadeaway.setAutoReverse(false);

        	FadeTransition okin = new FadeTransition(Duration.millis(1000),okView);
        	okin.setFromValue(0.0);
        	okin.setToValue(1);
        	okin.setCycleCount(1);
        	okin.setAutoReverse(false);
	

		okin.setOnFinished(t -> { elements.setTranslateX(10000); } );
        	hideok.play();
        	fft.setOnFinished( t-> { fadeaway.play(); okin.play(); } );
       		//play
        	hide.setOnFinished( t -> { after.play();fft.play(); } );
        	hide.play();
        	main.play();
        	ft.play(); 
		
	} 
}
