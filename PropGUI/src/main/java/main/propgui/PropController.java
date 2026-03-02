package main.propgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class PropController {
    public static ArrayList<Prop> lista = new ArrayList<>();

    @FXML public BorderPane container;
    @FXML public RadioButton part;
    @FXML public RadioButton datum;
    public ListView<String> left_list;
    public ListView<String> right_list;

    @FXML public void fileOpener() {
        Stage currentStage = (Stage) container.getScene().getWindow();

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Fálj kiválasztása...");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV fájl", "*.csv"));
            File selectedFile = fileChooser.showOpenDialog(currentStage);

            if (selectedFile != null) {
                lista = CSVParser.parse(selectedFile.getPath());
                fillUp();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML public void exitPlatform () {
        Platform.exit();
    }


    @FXML public void handleAbout () {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Névjegy");
        alert.setHeaderText("");
        alert.setContentText("Prop v1.0.0\n(C) Kandó");
        alert.showAndWait();
    }

    @FXML public void fillUp(){
        if(datum.isSelected()){
            ArrayList<String> datumok = new ArrayList<>();

            for(Prop prop : lista){
                datumok.add(prop.getKihelyezesDatuma());
            }

            for(String datum : datumok){
                if(datumok.indexOf(datum) != datumok.lastIndexOf(datum)){
                    datumok.remove(datum);
                }
            }

            Collections.sort(datumok);
            datumok.reversed();

            left_list.setItems(FXCollections.observableArrayList(datumok));
        }
        else if(part.isSelected()){
            ArrayList<String> part = new ArrayList<>();

            for(Prop prop : lista){
                part.add(prop.getPart());
            }

            Collections.sort(part);

            left_list.setItems(FXCollections.observableArrayList(part));
        }
    }

    @FXML public void showData(){
        if(datum.isSelected()){
            String selectedDatum = left_list.getSelectionModel().getSelectedItem();

            ArrayList<String> datumsData = new ArrayList<>();

            for(Prop prop : lista){
                if(prop.getKihelyezesDatuma().equals(selectedDatum)){
                    datumsData.add(prop.getPlakatSzoveg());
                }
            }

            right_list.setItems(FXCollections.observableArrayList(datumsData));
        }
        else if(part.isSelected()){
            String selectedPart = left_list.getSelectionModel().getSelectedItem();

            ArrayList<String> partData = new ArrayList<>();

            for(Prop prop : lista){
                if(prop.getPart().equals(selectedPart)){
                    partData.add("sorszám: " + prop.getSorszam());
                    partData.add("kihelyezés dátuma: " + prop.getKihelyezesDatuma());
                    partData.add("párt: " + prop.getPart());
                    partData.add("szélesség_m: " + prop.getPlakatSzelessegM());
                    partData.add("hosszúság_m: " + prop.getPlakatHosszusagM());
                    partData.add("terület_m2: " + (Double.parseDouble(prop.getPlakatSzelessegM().replace(",", ".")) * Double.parseDouble(prop.getPlakatHosszusagM().replace(",", "."))));
                    partData.add("szöveg: " + prop.getPlakatSzoveg());
                    partData.add("");
                }
            }

            right_list.setItems(FXCollections.observableArrayList(partData));
        }
    }

}
