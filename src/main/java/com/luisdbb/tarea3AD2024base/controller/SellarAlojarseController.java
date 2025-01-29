package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Credenciales;
import com.luisdbb.tarea3AD2024base.modelo.Estancias;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.PeregrinoParadas;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.CredencialesService;
import com.luisdbb.tarea3AD2024base.services.EstanciasService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

@Controller
public class SellarAlojarseController implements Initializable{

	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnAceptar;
	
	@FXML
	private ChoiceBox<String> cbPeregrino;
	
	@FXML 
	private Label lblVip;
	
	@FXML
	private CheckBox checkEstancia;
	
	@FXML
	private CheckBox checkVip;
	
	@Autowired
	private EstanciasService estanciaService;
	
	@Autowired
	private CarnetService carnetService;
	
	@Autowired
	private PeregrinoService pereService;
	
	@Autowired
	private CredencialesService credenService;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private PeregrinoParadaService ppService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	public List<Peregrino> peres;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		peres = pereService.findAll();
		
		for(Peregrino p: peres) {
			cbPeregrino.getItems().add(p.getNombre());
		}
		
	}
	
	@FXML
	public void clickBtnVolver(ActionEvent event) throws IOException{
		stageManager.switchScene(FxmlView.ADMINPARADA);
	}
	
	

	@FXML
	public void activarVip(ActionEvent evetn) throws IOException{
		if(checkEstancia.isSelected()) {
			lblVip.setDisable(false);
			checkVip.setDisable(false);
			
		}else {
			checkVip.setSelected(false);
			lblVip.setDisable(true);
			checkVip.setDisable(true);
			
		}
	}
	
	@FXML
	public void clickBtnAceptar(ActionEvent event) throws IOException{
		
		Credenciales c = credenService.findByNombre(Sesion.sesion.getUsuario());
		
		boolean pereCorrecto=false;
		
		//validacion del peregrino
		if(cbPeregrino.getValue()!=null) {
			pereCorrecto=true;
		}
		
		
		if(checkEstancia.isSelected()) {
			
			//SITUACION EN LA QUE SELLA Y ALOJA CON VIP
			if(checkVip.isSelected()) {
				Peregrino p = pereService.findByNombre(cbPeregrino.getValue());
				PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
				Carnet carnet = p.getCarnet();
				Estancias es = new Estancias(LocalDate.now(),true);
				
				es.setParadaE(c.getParada());
				es.setPeregrinoE(p);
				estanciaService.save(es);
				
				
				List<PeregrinoParadas> Listpp = new ArrayList<>();
				
				pereService.actualizarEntidad(p);
				
				pp.setPeregrino(p);
				pp.setParadas(c.getParada());			
				Listpp.add(pp);
				p.setPerePara(Listpp);
				ppService.save(pp);
				
				carnet.setDistancia(carnet.getDistancia()+5);
				carnet.setNvips(carnet.getNvips()+1);
				carnetService.actualizarEntidad(carnet);
				
				Alert mensaje = new Alert(Alert.AlertType.WARNING);
				mensaje.setTitle("PEREGRINO SELLADO");
				mensaje.setContentText("EL PEREGRINO SE HA SELLADO CORRECTAMENTE");
				mensaje.showAndWait();
				
				
			}
			//SITUACION EN LA QUE SE SELLA Y ALOJA SIN VIP
			else {
				Peregrino p = pereService.findByNombre(cbPeregrino.getValue());
				PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
				Carnet carnet = p.getCarnet();
				Estancias es = new Estancias(LocalDate.now(),false);
				
				es.setParadaE(c.getParada());
				es.setPeregrinoE(p);
				estanciaService.save(es);
				
				
				List<PeregrinoParadas> Listpp = new ArrayList<>();
				
				pereService.actualizarEntidad(p);
				
				pp.setPeregrino(p);
				pp.setParadas(c.getParada());			
				Listpp.add(pp);
				p.setPerePara(Listpp);
				ppService.save(pp);
				
				carnet.setDistancia(carnet.getDistancia()+5);
				carnetService.actualizarEntidad(carnet);
				
				Alert mensaje = new Alert(Alert.AlertType.WARNING);
				mensaje.setTitle("PEREGRINO SELLADO");
				mensaje.setContentText("EL PEREGRINO SE HA SELLADO CORRECTAMENTE");
				mensaje.showAndWait();
				
			}
			
		}
		//SITUACION EN LA QUE SOLO SE SELLA AL PERE
		else {
			Peregrino p = pereService.findByNombre(cbPeregrino.getValue());
			PeregrinoParadas pp = new PeregrinoParadas(LocalDate.now());
			Carnet carnet = p.getCarnet();
			
			List<PeregrinoParadas> Listpp = new ArrayList<>();
			
			pereService.actualizarEntidad(p);
			
			pp.setPeregrino(p);
			pp.setParadas(c.getParada());			
			Listpp.add(pp);
			p.setPerePara(Listpp);
			ppService.save(pp);
			
			
			
			carnet.setDistancia(carnet.getDistancia()+5);
			carnetService.actualizarEntidad(carnet);
			
			Alert mensaje = new Alert(Alert.AlertType.WARNING);
			mensaje.setTitle("PEREGRINO SELLADO");
			mensaje.setContentText("EL PEREGRINO SE HA SELLADO CORRECTAMENTE");
			mensaje.showAndWait();
			
		}
	}



	

}
