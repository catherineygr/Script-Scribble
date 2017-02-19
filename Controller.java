package gameview;

//Author: Catherine Yeager
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.script.*;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	@FXML
	TextField tfield;
	@FXML
	Canvas maincanvas;
	@FXML
	MenuBar menubar;
	@FXML
	Button submitbut;
	@FXML
	StackPane back;
	
	ScriptEngine engine;
	GraphicsContext gc;
	Stage stage;
	Brush brush;
	public void start(Stage mainstage) throws ScriptException {
		stage=mainstage;
		tfield.setPromptText("write your script here");
		brush=new Brush();
		brush.setSize(1);
		importJavaScript();
		gc = maincanvas.getGraphicsContext2D();
		BoxBlur blur = new BoxBlur();
		blur.setWidth(10);
		blur.setHeight(10);
		blur.setIterations(1);
		gc.setEffect(blur);
		awaitDraw(gc);
		back.setStyle("-fx-background-color: white");
	}
	public void importJavaScript() throws ScriptException {
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("nashorn");
		engine.put("brush", brush);
		engine.put("canvas", this);
		
		
	}
	public void setBackColor(String c) {
		String style= "-fx-background-color: " + c;
		back.setStyle(style);
	}
	public void submitTest() {
		try {
			engine.eval(tfield.getText());
		} catch (ScriptException e) {
			Alert alert = new Alert(AlertType.WARNING, "Script is invalid", ButtonType.OK);
			alert.showAndWait();
			
		}
		tfield.clear();
		
	}
	public void clear() {
		gc.clearRect(0, 0, maincanvas.getWidth(), maincanvas.getHeight());
	}
	public void awaitDraw(GraphicsContext gc) {
		gc.setLineWidth(5);
		maincanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e->
			{
//				gc.setFill(brush.getColor());
//				gc.fillOval(e.getX(), e.getY(), 10*brush.getSize() ,10*brush.getSize());
				BoxBlur blur = new BoxBlur();
				blur.setWidth(brush.getSize()*10);
				blur.setHeight(brush.getSize()*10);
				blur.setIterations(1);
				gc.setEffect(blur);
				gc.setStroke(brush.getColor());
				gc.beginPath();
				gc.setLineWidth(brush.getSize());
                gc.moveTo(e.getX(), e.getY());
                gc.stroke();
			});
		maincanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e->
			{
//				gc.setFill(brush.getColor());
//				gc.fillOval(e.getX(), e.getY(), 10*brush.getSize() ,10*brush.getSize());
				BoxBlur blur = new BoxBlur();
				blur.setWidth(brush.getSize()*10);
				blur.setHeight(brush.getSize()*10);
				blur.setIterations(1);
				gc.setEffect(blur);
				gc.setStroke(brush.getColor());
				gc.setLineWidth(brush.getSize());
				gc.lineTo(e.getX(), e.getY());
				gc.stroke();
			});
		maincanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e->
			{
//			gc.setFill(brush.getColor());
//			gc.fillOval(e.getX(), e.getY(), 10*brush.getSize() ,10*brush.getSize());
			
			});
		}
	
		public void saveas(){
			FileChooser fc = new FileChooser();
			File file = fc.showSaveDialog(stage);
            if (file != null) {
                try {
                	WritableImage image = maincanvas.snapshot(new SnapshotParameters(), null);
                	RenderedImage ri = SwingFXUtils.fromFXImage(image, null);
                	ImageIO.write(ri, "png",file);
                	
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
		}
		public void getColorHelp() {
			Alert alert = new Alert(AlertType.INFORMATION, "To change color, enter brush.setColor(\"COLOR HERE\"). Colors include the following:" +
				"red, blue, black, green, yellow, white, orange", ButtonType.OK);
			alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));

			alert.showAndWait();
		}
		
		public void getSizeHelp() {
			Alert alert = new Alert(AlertType.INFORMATION, "To change color, enter brush.setSize(\"INTEGER HERE\").", ButtonType.OK);
			alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));

				alert.showAndWait();
		}
		
		public void getBackHelp() {
			Alert alert = new Alert(AlertType.INFORMATION, "To change color, enter canvas.setSize(\"COLOR HERE\"). Only css colors available.", ButtonType.OK);
			alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));

			alert.showAndWait();
		}
		
	}


