package com.luisdbb.tarea3AD2024base.controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
public class NuevaParadaController {
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnAceptar;
	
	@FXML
	private TextField txtNombreParada;
	
	@FXML
	private TextField txtRegion;
	
	@FXML
	private TextField txtNombreResponsable;
	
	@FXML
	private TextField txtContrasenia;
	
	@Autowired
	private CredencialesService credenService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException {
		
		stageManager.switchScene(FxmlView.ADMIN);
	}
	
	@FXML
	public void clickBtnAceptar(ActionEvent event) throws IOException{
		boolean nomParadaCorrecto=false;
		boolean regionCorrecto = false;
		boolean nomResponCorrecto = false;
		boolean contraCorrecto = false;
		
		String nom= txtNombreParada.getText();
		
		//validacion del nombre
				if(nom==null) {
					//poner algo para que envie mensaje al  usuario d q esta mal
				}
				else {
					boolean mal=false;
					for(int i=0; i<nom.length();i++ ) {
						
						if(Character.isDigit(nom.charAt(i))|| !Character.isAlphabetic(nom.charAt(i))) {
							//poner algo para que envie mensaje al  usuario d q esta mal
							mal=true;
						}					
					}	
					if(!mal) nomParadaCorrecto=true;
				}
				
			//validacion de la region de la parada
				String region = txtRegion.getText();
				if(region.equals(null) || region.length()>=2 || !Character.isAlphabetic(region.charAt(0))) {
					
				}else regionCorrecto=true;
				
			//validacion del nombre del responsable
				String nomUsu = txtNombreResponsable.getText();
				if(nomUsu==null) {
				
					}
					else {
						boolean mal= false;
						for(int i=0; i<nomUsu.length();i++ ) {
							if(Character.isWhitespace(nomUsu.charAt(i))) {
								mal = true;
							}
						}
						if(!mal) nomResponCorrecto=true;
					}
				
		//validacion de la contraseña del responsable
				String contra = txtContrasenia.getText();
				if(contra==null) {
								
				}
				else {
					boolean mal=false;
					for(int i=0; i<contra.length();i++ ) {
						if(Character.isWhitespace(contra.charAt(i))) {
							mal = true;
						}
										
					}
					if(!mal) contraCorrecto = true;
				}
				
				if(contraCorrecto && nomResponCorrecto && nomParadaCorrecto && regionCorrecto) {
					
					if(!credenService.CredencialesExistenPorNombre(txtNombreResponsable.getText())) {
						if(!paradaService.ParadaExistePorNombre(txtNombreParada.getText())) {
							char c = txtRegion.getText().charAt(0);
							Paradas p = new Paradas(txtNombreParada.getText(), c , txtNombreResponsable.getText());
							Credenciales creden = new Credenciales (txtNombreResponsable.getText(), txtContrasenia.getText(), "parada");
							
							
							
							p.setCredenciales(creden);
							creden.setParada(p);
							
							paradaService.save(p);
							
							Alert mensaje = new Alert(Alert.AlertType.WARNING);
							mensaje.setTitle("PARADA CREADA CORRECTAMENTE");
							mensaje.setContentText("PARADA CREADA CORRECTAMENTE, VOLVIENDO AL MENU...");
							mensaje.showAndWait();
							
							stageManager.switchScene(FxmlView.ADMIN);
							
							
						}else {
							Alert mensaje = new Alert(Alert.AlertType.WARNING);
							mensaje.setTitle("YA ESXISTE UNA PARADA CON ESTE NOMBRE");
							mensaje.setContentText("CAMBIE EL NOMBRE DE LA PARADA PARA PODER COMPLETAR EL REGISTRO");
							mensaje.showAndWait();
						}	
						
					}
					else {
						Alert mensaje = new Alert(Alert.AlertType.WARNING);
						mensaje.setTitle("YA ESXISTE UN USUARIO CON ESTE NOMBRE DE USUARIO");
						mensaje.setContentText("CAMBIE EL NOMBRE DE USUARIO PARA PODER COMPLETAR EL REGISTRO");
						mensaje.showAndWait();
					}
					
				}else {
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("CAMPOS MAL");
					mensaje.setContentText("CAMPOS MAL, RIVESELOS");
					mensaje.showAndWait();
				}
	}

}
