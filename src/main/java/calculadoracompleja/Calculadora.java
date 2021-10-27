package calculadoracompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Calculadora extends Application {
	private TextField aTextField, bTextField, cTextField, dTextField;
	private TextField resultATextField = new TextField();
	private TextField resultBTextField = new TextField();
	private ComboBox<String> SignoCombo;
	private StringProperty aProperty = new SimpleStringProperty();
	private Complejo aNum = new Complejo();
    private Complejo bNum = new Complejo();
    private Complejo cNum = new Complejo();
    private Complejo dNum = new Complejo();
    private Complejo resultAnum = new Complejo();
    private Complejo resultBnum = new Complejo();


	@Override
	public void start(Stage primaryStage) throws Exception {
		aTextField = new TextField();
		aTextField.setPrefColumnCount(6);

		bTextField = new TextField();
		bTextField.setPrefColumnCount(6);

		cTextField = new TextField();
		cTextField.setPrefColumnCount(6);

		dTextField = new TextField();
		dTextField.setPrefColumnCount(6);

		resultATextField = new TextField();
		resultATextField.setPrefColumnCount(6);
		resultATextField.setDisable(true);

		resultBTextField = new TextField();
		resultBTextField.setPrefColumnCount(6);
		resultBTextField.setDisable(true);

		SignoCombo = new ComboBox<String>();
		SignoCombo.getItems().addAll("+", "-", "*", "/");
	
		HBox aHBox = new HBox(aTextField, new Label(" + "), bTextField, new Label(" i "));
		HBox bHBox = new HBox(cTextField, new Label(" + "), dTextField, new Label(" i "));
		HBox dHBox = new HBox(resultATextField, new Label(" + "), resultBTextField, new Label(" i "));

		VBox aVBox = new VBox(aHBox, bHBox, new Separator(), dHBox);
		VBox bVBox = new VBox(SignoCombo);
		aVBox.setAlignment(Pos.CENTER);
		bVBox.setAlignment(Pos.CENTER);

		HBox root = new HBox(bVBox, aVBox);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);
		Scene scene = new Scene(root, 320, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Calculadora.fxml");
		primaryStage.show();

		Bindings.bindBidirectional(aTextField.textProperty(), aNum.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(bTextField.textProperty(), bNum.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(cTextField.textProperty(), cNum.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(dTextField.textProperty(), dNum.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultATextField.textProperty(), resultAnum.realProperty(),
				new NumberStringConverter());
		Bindings.bindBidirectional(resultBTextField.textProperty(), resultBnum.imaginarioProperty(),
				new NumberStringConverter());

		aProperty.bind(SignoCombo.getSelectionModel().selectedItemProperty());
		aProperty.addListener((o, ov, nv) -> onOperacion(nv));
		SignoCombo.getSelectionModel().selectFirst();
	}

	private void onOperacion(String nv) {
		switch (nv) {
		case "+":
			resultAnum.realProperty().bind(aNum.realProperty().add(cNum.realProperty()));
			resultBnum.imaginarioProperty().bind(bNum.imaginarioProperty().add(dNum.imaginarioProperty()));

			break;
		case "-":
			resultAnum.realProperty().bind(aNum.realProperty().subtract(cNum.realProperty()));
			resultBnum.imaginarioProperty().bind(bNum.imaginarioProperty().subtract(dNum.imaginarioProperty()));
			break;
		case "*":
			resultAnum.realProperty().bind(aNum.realProperty().multiply(cNum.realProperty()));
			resultBnum.imaginarioProperty().bind(bNum.imaginarioProperty().multiply(dNum.imaginarioProperty()));
			break;
		case "/":
			resultAnum.realProperty().bind(((aNum.realProperty().multiply(cNum.realProperty())).add(bNum.imaginarioProperty().multiply(dNum.imaginarioProperty()))).divide((cNum.realProperty().multiply(cNum.realProperty())).add((dNum.imaginarioProperty()).multiply(dNum.imaginarioProperty()))));
			resultBnum.imaginarioProperty().bind(((bNum.imaginarioProperty().multiply(cNum.realProperty())).subtract(aNum.realProperty().multiply(dNum.imaginarioProperty()))).divide((cNum.realProperty().multiply(cNum.realProperty())).add((dNum.imaginarioProperty()).multiply(dNum.imaginarioProperty()))));
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
