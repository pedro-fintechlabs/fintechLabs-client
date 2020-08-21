package com.javalabs.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.javalabs.client.cache.RefDataLoader;
import com.javalabs.client.ui.CenterPanel;
import com.javalabs.client.ui.MenuPanel;
import com.javalabs.client.ui.audit.AuditEvent;
import com.javalabs.client.ui.audit.AuditFactory;
import com.javalabs.client.ui.user.LoggedinPanel;
import com.javalabs.client.ui.user.LoginPanel;
import com.javalabs.shared.dto.User;

public class JavaLabs implements EntryPoint {

	private static JavaLabs INSTANCE;
	private static String HOST;
	private static User USER;
	
	private static CenterPanel centerPanel = new CenterPanel();
	private static LoginPanel loginPanel = new LoginPanel();
	private static Image 
		centerImg,
		centerImgLoggedIn;
	//private static Image loginImg;
	private static HorizontalPanel topPanel;
	private static LoggedinPanel loggedinPanel;
	private static MenuPanel menuPanel;
	
	private static Panel lastViewPanel;
	
	@Override
	public void onModuleLoad() {
		INSTANCE = this;

		detectHOST_IP();
		createUI();
//		loginPanel.autoLogin();

		doSTUFF();
	}

	public static JavaLabs GET() {
		return INSTANCE;
	}

	public static User GET_USER() {
		return USER;
	}
	
	public static String GET_HOST() {
		return HOST;
	}
	
	private void detectHOST_IP() {
		HOST = Window.Location.getHost();
		//Window.alert(HOST);
	}
	
	private void createUI() {
				
		centerImg = new Image("images/open_banking_1.jpg");
		centerImg.setStyleName("centerImg");
		RootPanel.get().add(centerImg, 0, 76);
		
		topPanel = new HorizontalPanel();
		topPanel.setStyleName("topPanel");
		topPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		topPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		//Image logoImg = new Image("images/JavaLabs_Logo.jpg");
		Image logoImg = new Image("images/FintechLabs_Logo.jpg");
		logoImg.setStyleName("logoImg");
		//logoImg.setPixelSize(199, 75);
		logoImg.setPixelSize(257, 62);
		
		RootPanel.get().add(topPanel, 0, 0);
		topPanel.add(logoImg);
		topPanel.setCellHorizontalAlignment(logoImg, HasHorizontalAlignment.ALIGN_LEFT);
		
		loginPanel.setStyleName("loginPanel");
		topPanel.add(loginPanel);
		
		resize();
		
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				resize();				
			}
		});
		
		createCenterPanel();
	}
	
	private void createCenterPanel() {

		RootPanel.get().add(centerPanel, 0, 150);
		
		String userEmail = getCookie();
		if (userEmail != null) {
			USER = new User();
			USER.setEmail(userEmail);
			letsGo(USER.getEmail());
			
		} else {
			topPanel.add(loginPanel);
		}

	}
	
	private static void resize() {
		if (menuPanel != null) {
			menuPanel.setPixelSize(200, Window.getClientHeight() - 75);
		}
	}
	
	public static void letsGo(String email) {
		
//		Window.alert(email);
		
		USER = new User(email);
		//Window.alert("USER: " + USER.toString());
		setCookie();
		
		AuditFactory.log(AuditEvent.LOGIN_SUCCESS);
		
		loginPanel.removeFromParent();

		centerImg.removeFromParent();
		centerImgLoggedIn = new Image("images/open_banking_1_greyscale.jpg");
		centerImgLoggedIn.setStyleName("centerImgLoggedin");
		RootPanel.get().add(centerImgLoggedIn, 201, 76);

		topPanel.removeFromParent();
		topPanel.setStyleName("topPanel");
		topPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		topPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		RootPanel.get().add(topPanel, 0, 0);
		
		loggedinPanel = new LoggedinPanel(USER);
		loggedinPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		topPanel.add(loggedinPanel);
		
		doMenu();

		// Only get RefData after login
		getRefData();

	}
	
	public static void logOut() {
		deleteCookie();
		
		centerImgLoggedIn.removeFromParent();
		centerImg = new Image("images/open_banking_1.jpg");
		centerImg.setStyleName("centerImg");
		RootPanel.get().add(centerImg, 0, 76);
		
		menuPanel.removeFromParent();
		if (lastViewPanel != null) {
			lastViewPanel.removeFromParent();
		}
		
		loginPanel.clear();
		
		topPanel.remove(loggedinPanel);
		topPanel.add(loginPanel);
		
		AuditFactory.log(AuditEvent.LOGOUT);
	}
	
	private static void setCookie() {
		if (Cookies.isCookieEnabled()) {
			final long DURATION = 1000 * 60 * 60 * 24 * 7; //duration remembering login - 1 week
			Date expires = new Date(System.currentTimeMillis() + DURATION);
	
			Cookies.setCookie("FINTECHLABS", USER.getEmail(), expires, null, "/", false);
		}
	}
	
	private static String getCookie() {
		if (Cookies.isCookieEnabled()) {
			return Cookies.getCookie("FINTECHLABS");
		}
		return null;
	}
	
	private static void deleteCookie() {
		if (Cookies.isCookieEnabled()) {
			Cookies.removeCookie("FINTECHLABS");
		}
	}
	
	private static void doMenu() {
		 menuPanel = new MenuPanel();		 
		 RootPanel.get().add(menuPanel, 0, 76);
		 
		 resize();
	}

	public void showView(Panel viewPanel) {
		centerImgLoggedIn.removeFromParent();
		
		if (lastViewPanel != null) {
			lastViewPanel.removeFromParent();
		}
		//Window.alert("showView:\n" + viewPanel.toString());
		lastViewPanel = viewPanel;
		//viewPanel.setPixelSize(Window.getClientWidth()-200, Window.getClientHeight()-75);
		RootPanel.get().add(viewPanel, 201, 76);		
	}

	// REFERENCE DATA
	public static void getRefData() {
		RefDataLoader.callGetCountriesService();
		RefDataLoader.callGetStatesService();
		RefDataLoader.callGetCitiesService();
		RefDataLoader.callGetCountiesService();
		//RefDataLoader.callGetZipcodesService(); // there are 19600 zips in US, do manual input
	}
	
	// EXPERIMENTS *************************************************************************************************
	private void doSTUFF() {
		
	}
}
