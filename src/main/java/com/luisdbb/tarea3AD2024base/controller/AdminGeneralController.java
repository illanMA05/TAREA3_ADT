package com.luisdbb.tarea3AD2024base.controller;


import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

@Controller
public class AdminGeneralController implements Initializable{

	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Button btnAñadirParada;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void clickBtnAñadirParada(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.NUEVAPARADA);
	}
	
	
	@FXML
	public void clickBtnCerrarSesion(ActionEvent event) throws IOException{
		
		Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
		mensaje.setTitle("	CERRAR SESION	");
		mensaje.setContentText("VA A CERRAR SESION EN SU CUENTA."
				+ "¿ESTA SEGURO DE ESTA ACCION?");
		Optional <ButtonType> opcion = mensaje.showAndWait();
		if(opcion.isPresent() &&opcion.get().equals(ButtonType.OK)) {
		stageManager.switchScene(FxmlView.LOGIN);
		}
	}
}
