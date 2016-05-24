package rocket.app.view;

import java.text.NumberFormat;

import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)

	private static NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	// TextBox  - 	txtIncome
	@FXML
	private TextField txtIncome;
	
	// TextBox  - 	txtExpenses
	@FXML
	private TextField txtExpenses;
	
	// TextBox  - 	txtCreditScore
	@FXML
	private TextField txtCreditScore;
	
	// TextBox  - 	txtHouseCost
	@FXML
	private TextField txtHouseCost;
	
	// TextBox  -   txtDownPayment
	@FXML
	private TextField txtDownPayment;
	
	// ComboBox  -  loan term
	@FXML
	private ComboBox<String> comboTerm;
	
	//ComboBox itemses
	ObservableList<String> termList= FXCollections.observableArrayList("15 Years", "30 Years");
	@FXML
	private void initialize() {
		comboTerm.setValue(termList.get(0));
		comboTerm.setItems(termList);
		}
	
	// Label - Income
	@FXML
	private Label Income;
	
	// Label - Expenses
	@FXML
	private Label Expenses;
	
	// Label - Credit Score
	@FXML
	private Label CreditScore;
	
	// Label - House Cost
	@FXML
	private Label HouseCost;
	
	// Label - Down Payment;
	@FXML
	private Label DownPayment;
	
	// Button - Calculate Button
	@FXML
	private Button LoanPaymentCalculator;
	
	// Label - Error messages and such
	@FXML 
	private Label ExceptionThrown; 
	
	// Label - Errors cont
	@FXML
	private Label PaymentException;
	
	// Label - lblMortgagePayment
	@FXML 
	private Label lblMortgagePayment;
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq

		
		// Expenses
		lq.setiExpenses(Double.parseDouble(txtExpenses.getText()));
		
		// Credit Score
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
				
		// House Cost
		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()));
		
		// Down Payment
		lq.setiDownPayment(Integer.parseInt(txtDownPayment.getText()));

		// Rate exceptions
		try {
			lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
		} catch (RateException e) {
			lq.setdRate(-1);
			ExceptionThrown.setText("Invalid Credit Score");
		}
		
		// Term
		if(comboTerm.getValue() == "15 Years"){
			lq.setiTerm(15);
		}
		else{
			lq.setiTerm(30);
		}
		a.setLoanRequest(lq);
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		double payment = lRequest.getdPayment();
		double PITI;
		double PITIa= (lRequest.getiIncome()*.28);
		double PITIb= ((lRequest.getiIncome()*.36)- lRequest.getiExpenses());
		
		if (PITIa < PITIb){
			PITI = PITIa;
		}
		else{
			PITI=PITIb;
		}	
		if (payment>PITI)
		{
			PaymentException.setText(payment + PITI + "House Cost too high");
			
		}

	else
	{
	 lblMortgagePayment.setText(nf.format(lRequest.getdPayment()));
	}
}
		@FXML
		public void Exit(ActionEvent event)
		{
		try 
		{
			mainApp.stop();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
