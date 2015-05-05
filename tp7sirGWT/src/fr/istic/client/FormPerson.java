package fr.istic.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class FormPerson implements EntryPoint {
	
	
	@Override
	public void onModuleLoad() {
	    // Create a FormPanel and point it at a service.
	    final FormPanel form = new FormPanel();
	    form.setAction("/myFormHandler");
	    form.setMethod(FormPanel.METHOD_POST);
	    //final Label nomLabel = new Label("Nom de la personne");
		
	    // Create a panel to hold all of the form widgets.
		final VerticalPanel mainPanel = new  VerticalPanel();
		form.setWidget(mainPanel);
		
		
		//final HorizontalPanel visuPanel = new HorizontalPanel();
		//final HorizontalPanel buttonPanel = new HorizontalPanel();
		
		
		 // Create a TextBox, giving it a name so that it will be submitted.
		final TextBox visuNom = new TextBox();
		visuNom.setText("Nom : ");
		mainPanel.add(visuNom);
		
		final TextBox visuPrenom = new TextBox();
		visuPrenom.setText("Prenom : ");
		mainPanel.add(visuPrenom);
		
		final TextBox visuMail = new TextBox();
		visuMail.setText("Mail : ");
		mainPanel.add(visuMail);
		
		final DatePicker visuDateNaiss = new DatePicker();
		//visuDateNaiss.setText("Date de Naissance : ");
		mainPanel.add(visuDateNaiss);
		
		final TextBox visuProfil = new TextBox();
		visuProfil.setText("Profil : ");
		mainPanel.add(visuProfil);
		
		// Add a 'submit' button.
		mainPanel.add(new Button("Valider", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        form.submit();
	      }
	    }));

	    // Add an event handler to the form.
	    form.addSubmitHandler(new FormPanel.SubmitHandler() {
	      public void onSubmit(SubmitEvent event) {
	        // This event is fired just before the form is submitted. We can take
	        // this opportunity to perform validation.
	        if (visuNom.getText().length() == 0 || visuPrenom.getText().length() == 0 || visuMail.getText().length() == 0) {
	          Window.alert("Les champs nom, prenom et mail sont obligatoires ");
	          event.cancel();
	        }
	      }
	    });
	    
	    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
	      public void onSubmitComplete(SubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is
	        // fired. Assuming the service returned a response of type text/html,
	        // we can get the result text here (see the FormPanel documentation for
	        // further explanation).
	        Window.alert(event.getResults());
	      }
	    });

	    mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    RootPanel.get("mainperson").add(form);
		
	} 
	 
}
