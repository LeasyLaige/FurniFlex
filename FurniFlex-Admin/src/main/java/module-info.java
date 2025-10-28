module com.suarez.furniflexadmin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.suarez.furniflexadmin to javafx.fxml;
    exports com.suarez.furniflexadmin;
}