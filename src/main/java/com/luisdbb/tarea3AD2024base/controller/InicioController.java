package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

@Controller
public class InicioController implements Initializable{

	@FXML
	private TextField usuario;
	
	@FXML
	private TextField contrasenia;
	
	@FXML
	private Button btnAcceder;
	
	@FXML
	private Button btnRegistrarse;
	
	@Autowired
	private CredencialesService credenService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	

	@FXML
	private void InicioSesion() {
		
	}
	
	@FXML
	public void clickBtnRegistro(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.NUEVOPERE);
	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@FXML
	public void clickBtnAcceder(ActionEvent event) throws IOException{
		if(usuario.getText().equals(null) || contrasenia.getText().equals(null)) {
			
		}else {
		
		if(credenService.CredencialesExisten(usuario.getText(), contrasenia.getText())) {
			
			Sesion.sesion = new Sesion(usuario.getText());
			
			if(credenService.perfilCredenciales(usuario.getText())==0){
				stageManager.switchScene(FxmlView.PEREGRINOIDENTIFICADO);
			}
				else if(credenService.perfilCredenciales(usuario.getText())==1) {
					stageManager.switchScene(FxmlView.ADMINPARADA);
				}
				else stageManager.switchScene(FxmlView.ADMIN);		
			
		}
		
		else {
			Alert mensa = new Alert(Alert.AlertType.WARNING);
			mensa.setTitle("USUARIO O CONTRASEÑA INCORRECTOS");
			mensa.setContentText("ESTE USUARIO O CONTRASEÑA NO EXISTE");
			mensa.showAndWait();
		}
	}
	}
	
	
	
}
