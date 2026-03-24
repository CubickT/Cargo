import com.cargo.model.ShapeModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static com.cargo.util.GeometryUtils.generateCirclePoints;

public class Controller {


    @FXML private ComboBox<ShapeModel> cargoComboBox;
    @FXML private TextField radiusField;
    @FXML private TextField elevationField;
    @FXML private TextField innerField;
    @FXML private TextField outerField;

    @FXML
    private void initialize() {
        cargoComboBox.getItems().addAll(
                new ShapeModel("Габаритный прямоугольник",
                        ShapeModel.ObjectType.CARGO,
                        new double[][]{{300, 300}, {300, 3000}, {-300, 3000}, {-300, 300}}),
                new ShapeModel("Негабаритный многоугольник",
                        ShapeModel.ObjectType.CARGO,
                        new double[][]{{0, 1000}, {1800, 1500}, {1800, 3000}, {500, 3000}, {500, 4000}, {-500, 4000}, {-500, 3000}, {-1800, 3000}, {-1800, 1500}, {0, 1000}}),

                new ShapeModel("Груз из задачи",
                        ShapeModel.ObjectType.CARGO,
                        new double[][]{{-2100, 1500}, {2100, 1500}, {2100, 2500}, {-2100, 2500}}),
                new ShapeModel("Проверка производительности",
                        ShapeModel.ObjectType.CARGO,
                        generateCirclePoints(2000000, 1800, 3000))

        );
        cargoComboBox.getSelectionModel().selectFirst();
    }

}
