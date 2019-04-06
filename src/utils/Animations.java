package utils;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {


	public static void makeButtonOutAnimations(double duration, Node node){
		TranslateTransition transition = new TranslateTransition(Duration.millis(duration),node);
		transition.setToX(8);
		transition.play();

	}

	public static void makeButtonInAnimations(double duration, Node node){
		TranslateTransition transition = new TranslateTransition(Duration.millis(duration),node);
		transition.setToX(0);
		transition.play();
	}

	public static void makeFadeOutTransition(double duration, Node node){
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration),node);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.play();
	}

	public static void makeFadeInTransition(double duration, Node node){
		node.setOpacity(0);
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration),node);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();
	}

}
