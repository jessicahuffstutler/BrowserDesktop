package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, ChangeListener {
    @FXML
    TextField addressBar;

    @FXML
    WebView webView;

    public void goToAddress() {
        String address = addressBar.getText();
        if (!address.startsWith("http")) {
            address = "http://" + address; //allows us to type in just "google.com" without http
        }
        WebEngine engine = webView.getEngine();
        engine.load(address);
    }

    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            goToAddress();
        }
    }

    public void goBack() {
        try {
            WebEngine engine = webView.getEngine();
            WebHistory history = engine.getHistory();
            history.go(-1);
        } catch (Exception e) {

        }
    }

    public void goForward() { //fix "out of bounds exception" that would occur if someone tried to go forward but there was no forward in the history to go.
        try { //try/catch will allow us to tell the prgram to do nothing if this exception occurs
            WebEngine engine = webView.getEngine();
            WebHistory history = engine.getHistory();
            history.go(1);
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine engine = webView.getEngine();
        Worker worker = engine.getLoadWorker();
        worker.stateProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) { //method, this will run when we load a new website (Changed interface)
        WebEngine engine = webView.getEngine();
        addressBar.setText(engine.getLocation()); //gets new address and updates the address bar
    }
}
