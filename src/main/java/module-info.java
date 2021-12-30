module ru.vsu.cs.bogdanova.game_fool {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.vsu.cs.bogdanova.game_fool to javafx.fxml;
    exports ru.vsu.cs.bogdanova.game_fool;
    exports ru.vsu.cs.bogdanova.game_fool.clientserver;
    opens ru.vsu.cs.bogdanova.game_fool.clientserver to javafx.fxml;
}