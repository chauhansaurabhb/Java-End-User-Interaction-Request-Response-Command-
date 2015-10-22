package logic;


import com.google.gson.JsonObject;

import iotsuite.common.GlobalVariable;
import iotsuite.localmiddleware.PubSubsSensingFramework;
import iotsuite.pubsubmiddleware.PubSubMiddleware;
import android.app.Activity;
import android.content.Context;
import iotsuite.semanticmodel.*;
import framework.*;
import factory.*;
import deviceImpl.*;

public class LogicSmartHomeApp extends SmartHomeApp {

	ISmartHomeApp objSmartHomeApp;
	Activity ui;
	public LogicSmartHomeApp obj = this;
	public String deviceType;
	JavaSESmartHomeApp objJavaSESmartHomeApp = null;
	public static PubSubsSensingFramework pubSubSensingFramework;

	public LogicSmartHomeApp(PubSubMiddleware myPubSubMiddleware,
			final Device deviceInfo, final Object ui, Context myContext) {
		super(myPubSubMiddleware, deviceInfo);
		deviceType = deviceInfo.getType();
		pubSubSensingFramework = PubSubsSensingFramework.getInstance();

		if (deviceType.equals(GlobalVariable.deviceJAVASEType)) {

			objSmartHomeApp = SmartHomeAppFactory.getSmartHomeAppDriver(
					deviceInfo.getType(), (Activity) ui, obj);

			objSmartHomeApp.OffGUI(handlerCommand);

			objSmartHomeApp.SetTempGUI(handlerCommand);

			objSmartHomeApp.ProfileGUI(handlerCommand);

		}

	}

	ListenerSmartHomeApp handlerCommand = new ListenerSmartHomeApp() {

		@Override
		public void onNewOffCommand() {
			OffFromGUI();
		}

		@Override
		public void onNewSetTempCommand(TempStruct newValue) {
			SetTempFromGUI(newValue);
		}

		@Override
		public void onNewProfileRequest(String request) {
			ProfileRequest(request);
		}

		/* */

	};

	@Override
	public void onNewProfileResponse(TempStruct arg) {
		System.out.println("Temp Value is"+arg.gettempValue());
		//objJavaSESmartHomeApp.ProfileResponseReceived(arg);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("tempValue", arg.gettempValue());
		obj.addProperty("unitOfMeasurement", arg.getunitOfMeasurement());
		pubSubSensingFramework.publish("profileResponse", obj);
		

	}

}