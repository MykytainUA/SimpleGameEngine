package org.MykytaInUA.SimpleGameEngine.user_input;

import java.util.HashMap;

public class KeyCodeMapper {
	private static final HashMap<Integer, Integer> newtKeyMap = new HashMap<>();
	private static final HashMap<Integer, Integer> swingKeyMap = new HashMap<>();
	
	public static int getMappedKeyFromNEWT(int keyCode) {
		Integer key = newtKeyMap.get(keyCode);
		if(key == null) {
			return VK_UNDEFINED;
		}
		return newtKeyMap.get(keyCode);
	}
	
	public static int getMappedKeyFromSwing(int keyCode) {
		Integer key = swingKeyMap.get(keyCode);
		if(key == null) {
			return VK_UNDEFINED;
		}
		return swingKeyMap.get(keyCode);
	}
	
	public static final int VK_UNDEFINED = -1;
	
	/*
	 *  Constants for number keys VK_NUMBER
	 */
    public static final int VK_0 = 100;
    public static final int VK_1 = 101;
    public static final int VK_2 = 102;
    public static final int VK_3 = 103;
    public static final int VK_4 = 104;
    public static final int VK_5 = 105;
    public static final int VK_6 = 106;
    public static final int VK_7 = 107;
    public static final int VK_8 = 108;
    public static final int VK_9 = 109;
    
    /* 
     * Constants for letter keys VK_LETTER 
     */   
    public static final int VK_A = 110;
    public static final int VK_B = 111;
    public static final int VK_C = 112;
    public static final int VK_D = 113;
    public static final int VK_E = 114;
    public static final int VK_F = 115;
    public static final int VK_G = 116;
    public static final int VK_H = 117;
    public static final int VK_I = 118;
    public static final int VK_J = 119;
    public static final int VK_K = 120;
    public static final int VK_L = 121;
    public static final int VK_M = 122;
    public static final int VK_N = 123;
    public static final int VK_O = 124;
    public static final int VK_P = 125;
    public static final int VK_Q = 126;
    public static final int VK_R = 127;
    public static final int VK_S = 128;
    public static final int VK_T = 129;
    public static final int VK_U = 130;
    public static final int VK_V = 131;
    public static final int VK_W = 132;
    public static final int VK_X = 133;
    public static final int VK_Y = 134;
    public static final int VK_Z = 135;
    
    /* 
     * Constants for control and function keys VK_FUNCTION
     */ 
    public static final int VK_ENTER            = 136;
    public static final int VK_BACK_SPACE       = 137;
    public static final int VK_TAB              = 138;
    public static final int VK_CANCEL           = 139;
    public static final int VK_CLEAR            = 140;
    public static final int VK_SHIFT            = 141;
    public static final int VK_CONTROL          = 142;
    public static final int VK_ALT              = 143;
    public static final int VK_PAUSE            = 144;
    public static final int VK_CAPS_LOCK        = 145;
    public static final int VK_ESCAPE           = 146;
    public static final int VK_SPACE            = 147;
    public static final int VK_PAGE_UP          = 148;
    public static final int VK_PAGE_DOWN        = 149;
    public static final int VK_END              = 150;
    public static final int VK_HOME             = 151;
    public static final int VK_LEFT             = 152;
    public static final int VK_UP               = 153;
    public static final int VK_RIGHT            = 154;
    public static final int VK_DOWN             = 155;
    public static final int VK_COMMA            = 156;
    public static final int VK_MINUS            = 157;
    public static final int VK_PERIOD           = 158;
    public static final int VK_SLASH            = 159;    
    public static final int VK_SEMICOLON        = 160;
    public static final int VK_EQUALS           = 161;
    public static final int VK_OPEN_BRACKET     = 162;
    public static final int VK_BACK_SLASH       = 163;
    public static final int VK_CLOSE_BRACKET    = 164;
    public static final int VK_DELETE           = 165;
    public static final int VK_NUM_LOCK         = 166;
    public static final int VK_WINDOWS          = 167;
    public static final int VK_QUOTE            = 168;
    public static final int VK_BACK_QUOTE       = 169;

    public static final int VK_PRINTSCREEN      = 170;
    public static final int VK_SCROLL_LOCK      = 171;
    public static final int VK_INSERT           = 172;
    public static final int VK_F1               = 173;
    public static final int VK_F2               = 174;
    public static final int VK_F3               = 175;
    public static final int VK_F4               = 176;
    public static final int VK_F5               = 177;
    public static final int VK_F6               = 178;
    public static final int VK_F7               = 179;
    public static final int VK_F8               = 180;
    public static final int VK_F9               = 181;
    public static final int VK_F10              = 182;
    public static final int VK_F11              = 183;
    public static final int VK_F12              = 184;

    
    
    
    static {
    	
      // Initialization for NEWT KeyMap
    	
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_0, VK_0);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_1, VK_1);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_2, VK_2);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_3, VK_3);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_4, VK_4);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_5, VK_5);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_6, VK_6);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_7, VK_7);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_8, VK_8);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_9, VK_9);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_A, VK_A);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_B, VK_B);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_C, VK_C);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_D, VK_D);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_E, VK_E);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F, VK_F);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_G, VK_G);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_H, VK_H);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_I, VK_I);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_J, VK_J);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_K, VK_K);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_L, VK_L);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_M, VK_M);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_N, VK_N);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_O, VK_O);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_P, VK_P);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_Q, VK_Q);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_R, VK_R);    
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_S, VK_S);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_T, VK_T);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_U, VK_U);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_V, VK_V);  
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_W, VK_W);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_X, VK_X);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_Y, VK_Y);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_Z, VK_Z);      
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_ENTER, VK_ENTER);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_BACK_SPACE, VK_BACK_SPACE);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_TAB, VK_TAB);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_CANCEL, VK_CANCEL); 
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_SHIFT, VK_SHIFT);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_CONTROL, VK_CONTROL);     
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_ALT, VK_ALT);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_PAUSE, VK_PAUSE);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_CAPS_LOCK, VK_CAPS_LOCK);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_ESCAPE, VK_ESCAPE);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_SPACE, VK_SPACE);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_PAGE_UP, VK_PAGE_UP);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_PAGE_DOWN, VK_PAGE_DOWN);     
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_END, VK_END);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_HOME, VK_HOME);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_LEFT, VK_LEFT);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_UP, VK_UP);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_RIGHT, VK_RIGHT);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_DOWN, VK_DOWN); 
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_COMMA, VK_COMMA);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_MINUS, VK_MINUS);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_PERIOD, VK_PERIOD);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_SLASH, VK_SLASH);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_SEMICOLON, VK_SEMICOLON);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_EQUALS, VK_EQUALS);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_OPEN_BRACKET, VK_OPEN_BRACKET);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_CLOSE_BRACKET, VK_CLOSE_BRACKET);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_BACK_SLASH, VK_BACK_SLASH);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_DELETE, VK_DELETE);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_NUM_LOCK, VK_NUM_LOCK);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_WINDOWS, VK_WINDOWS);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_QUOTE, VK_QUOTE);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_BACK_QUOTE, VK_BACK_QUOTE);   
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_PRINTSCREEN, VK_PRINTSCREEN);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_SCROLL_LOCK, VK_SCROLL_LOCK);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_INSERT, VK_INSERT);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F1, VK_F1);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F2, VK_F2);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F3, VK_F3);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F4, VK_F4);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F5, VK_F5);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F6, VK_F6);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F7, VK_F7);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F8, VK_F8);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F9, VK_F9);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F10, VK_F10);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F11, VK_F11);
      newtKeyMap.put((int) com.jogamp.newt.event.KeyEvent.VK_F12, VK_F12);
      
      // Initialization for Swing/AWT KeyMap
      
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_0, VK_0);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_1, VK_1);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_2, VK_2);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_3, VK_3);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_4, VK_4);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_5, VK_5);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_6, VK_6);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_7, VK_7);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_8, VK_8);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_9, VK_9);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_A, VK_A);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_B, VK_B);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_C, VK_C);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_D, VK_D);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_E, VK_E);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F, VK_F);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_G, VK_G);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_H, VK_H);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_I, VK_I);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_J, VK_J);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_K, VK_K);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_L, VK_L);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_M, VK_M);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_N, VK_N);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_O, VK_O);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_P, VK_P);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_Q, VK_Q);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_R, VK_R);    
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_S, VK_S);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_T, VK_T);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_U, VK_U);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_V, VK_V);  
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_W, VK_W);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_X, VK_X);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_Y, VK_Y);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_Z, VK_Z);
      
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_ENTER, VK_ENTER);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_BACK_SPACE, VK_BACK_SPACE);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_TAB, VK_TAB);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_CANCEL, VK_CANCEL); 
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_SHIFT, VK_SHIFT);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_CONTROL, VK_CONTROL);     
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_ALT, VK_ALT);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_PAUSE, VK_PAUSE);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_CAPS_LOCK, VK_CAPS_LOCK);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_ESCAPE, VK_ESCAPE);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_SPACE, VK_SPACE);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_PAGE_UP, VK_PAGE_UP);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_PAGE_DOWN, VK_PAGE_DOWN);     
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_END, VK_END);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_HOME, VK_HOME);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_LEFT, VK_LEFT);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_UP, VK_UP);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_RIGHT, VK_RIGHT);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_DOWN, VK_DOWN); 
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_COMMA, VK_COMMA);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_MINUS, VK_MINUS);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_PERIOD, VK_PERIOD);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_SLASH, VK_SLASH);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_SEMICOLON, VK_SEMICOLON);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_EQUALS, VK_EQUALS);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_OPEN_BRACKET, VK_OPEN_BRACKET);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_CLOSE_BRACKET, VK_CLOSE_BRACKET);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_BACK_SLASH, VK_BACK_SLASH);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_DELETE, VK_DELETE);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_NUM_LOCK, VK_NUM_LOCK);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_WINDOWS, VK_WINDOWS);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_QUOTE, VK_QUOTE);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_BACK_QUOTE, VK_BACK_QUOTE);   
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_PRINTSCREEN, VK_PRINTSCREEN);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_SCROLL_LOCK, VK_SCROLL_LOCK);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_INSERT, VK_INSERT);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F1, VK_F1);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F2, VK_F2);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F3, VK_F3);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F4, VK_F4);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F5, VK_F5);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F6, VK_F6);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F7, VK_F7);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F8, VK_F8);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F9, VK_F9);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F10, VK_F10);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F11, VK_F11);
      swingKeyMap.put((int) java.awt.event.KeyEvent.VK_F12, VK_F12);
  }
}
