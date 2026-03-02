module main.propgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.propgui to javafx.fxml;
    exports main.propgui;
}