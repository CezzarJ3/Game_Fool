module ru.vsu.cs.bogdanova.game_fool {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.vsu.cs.bogdanova.game_fool to javafx.fxml;
    exports ru.vsu.cs.bogdanova.game_fool;
}