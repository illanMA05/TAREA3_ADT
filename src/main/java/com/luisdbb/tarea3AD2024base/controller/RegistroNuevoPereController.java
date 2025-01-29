package com.luisdbb.tarea3AD2024base.controller;



import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Paises;
import com.luisdbb.tarea3AD2024base.modelo.Paradas;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

@Controller
public class RegistroNuevoPereController implements Initializable{
	
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnAceptar;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtContra;
	
	@FXML
	private TextField txtContra2;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private ChoiceBox<String> pais;
	
	@FXML
	private ChoiceBox<String> paradaIni;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private PeregrinoService pereService;
	
	@Autowired
	private PeregrinoParadaService ppService;
	
	@Autowired
	private CredencialesService credenService;
	
	@Autowired
	private CarnetService carnetService;

	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	public List <Paradas> paradas;
	public HashMap<String, String> mapPaises;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		paradas =paradaService.findAll();

	    mapPaises = Paises.mapaPaises;
		
	     mapPaises = Paises.mapaPaises;
		Set<String> s = mapPaises.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()) {
			String aux = (String) it.next();
			mapPaises.put(aux, mapPaises.get(aux));
			pais.getItems().add(mapPaises.get(aux));
		}
		
		for(Paradas p : paradas) {
			
			paradaIni.getItems().add(p.getNombre());
		}
		
	}
	
	/**
	 * Metodo que devuelve a la pantalla inicial
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	/**
	 * Metodo para validar campos y si estos son correcto añade a la base de datos el peregrino resultante
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void clickBtnAceptar(ActionEvent event) throws IOException{
	
		boolean nomCorrecto=false;	
		boolean nomUsuCorrecto=false;
		boolean contraCorrecto=false;
		boolean emailCorrecto=false;
		boolean paradaCorrecto=false;
		boolean paisCorrecto= false;
		
		
		String nom= txtNombre.getText();
		
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
					if(!mal) nomCorrecto=true;
				}
				
				
		//validacion del nombre de usuario
				String nomUsu = txtUsuario.getText();
				
				if(nomUsu==null) {
					
				}
				else {
					boolean mal= false;
					for(int i=0; i<nomUsu.length();i++ ) {
						if(Character.isWhitespace(nomUsu.charAt(i))) {
							mal = true;
						}
					}
					if(!mal) nomUsuCorrecto=true;
				}
				
				
				//validacion de la contraseña
				
				String contra = txtContra.getText();
				String contra2= txtContra2.getText();
				
				if(contra==null) {
					
				}
				else {
					boolean mal=false;
					for(int i=0; i<contra.length();i++ ) {
						if(Character.isWhitespace(contra.charAt(i))) {
							mal = true;
						}
							
					}
					if(!contra2.equals(contra)) { mal=true;
					
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("LAS CONTRASEÑAS NO COINCIDEN");
					mensaje.setContentText("LAS CONTRASEÑAS NO COINCIDEN");
					mensaje.showAndWait();
					}
				
					
					if(!mal) contraCorrecto = true;
				}
				
				//validacion e-mail
				String email = txtEmail.getText();
				
				if(validarCorreo(email)==true) {
					emailCorrecto=true;
				}
				else emailCorrecto=false;
		
		//validacion de la parada Inicial del peregrino
				if( paradaIni.getValue() == null) {
					
				}else
					paradaCorrecto=true;
				
		//validacion de la nacionalidad del peregrino
				if( pais.getValue() == null) {
				
				}else 
					paisCorrecto=true;
					
				if(nomCorrecto && nomUsuCorrecto && contraCorrecto && emailCorrecto && paradaCorrecto && paisCorrecto) {
					//hacer comprobacion de que no exista ya el peregrino a introducir
					if(!credenService.CredencialesExistenPorNombre(txtUsuario.getText())) {
						
						Paradas pIni = paradaService.findByNombre(paradaIni.getValue().toString());	
						
						Peregrino p = new Peregrino(pais.getValue().toString(),txtNombre.getText(),txtUsuario.getText(),txtEmail.getText());
						Carnet c = new Carnet (0.0,LocalDate.now(),0);
						Credenciales creden = new Credenciales (txtUsuario.getText(),txtContra.getText(), "peregrino");
						PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
					
						pereService.save(p);
						//guardamos peregrino
						p.setCarnet(c);
						c.setIdPere(p);					
						c.setInicio(pIni);							
						//guardamos las credenciales
						creden.setPere(p);
						p.setCreden(creden);
						carnetService.actualizarEntidad(c);						
						//guardamos la peregrinoParada
					List<PeregrinoParadas> Listpp = new ArrayList<>();

					pp.setPeregrino(p);
					pp.setParadas(pIni);			
					Listpp.add(pp);
					p.setPerePara(Listpp);
					ppService.save(pp);
					
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("INSERTADO CORRECTAMENTE");
					mensaje.setContentText("PEREGRINO INSERTADO CORRECTAMENTE, DIRIGIENDO AL MENU DEL PEREGRINO");
					mensaje.showAndWait();
					Sesion.sesion = new Sesion(txtUsuario.getText().toString());
					stageManager.switchScene(FxmlView.PEREGRINOIDENTIFICADO);
					
					}
					else {
						Alert mensaje = new Alert(Alert.AlertType.WARNING);
						mensaje.setTitle("YA ESXISTE UN USUARIO CON ESTE NOMBRE DE USUARIO");
						mensaje.setContentText("CAMBIE EL NOMBRE DE USUARIO PARA PODER COMPLETAR EL REGISTRO");
						mensaje.showAndWait();
					}
					
				}
				else {
					
					Alert mensaje = new Alert(Alert.AlertType.WARNING);
					mensaje.setTitle("CAMPOS MAL");
					mensaje.setContentText("CAMPOS MAL, RIVESELOS");
					mensaje.showAndWait();
				}
	}
	
		/**
		 * metodo para la validacion del correo electronico
		 * @param Cadeda de caracteres correspondiente al correo
		 * @return
		 */
		public static boolean validarCorreo(String correo) {
        
        String expresionRegular = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(correo);
        
        return matcher.matches();
    }

		
}
