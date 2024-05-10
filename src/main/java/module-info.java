module mmocanu.fr.contactmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.prefs;

    opens mmocanu.fr.contactmanager to javafx.fxml;
    exports mmocanu.fr.contactmanager;
    exports mmocanu.fr.contactmanager.user;
    opens mmocanu.fr.contactmanager.user to javafx.fxml;
    exports mmocanu.fr.contactmanager.contact;
    opens mmocanu.fr.contactmanager.contact to javafx.fxml;
}